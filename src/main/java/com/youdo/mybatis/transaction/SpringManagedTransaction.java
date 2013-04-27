package com.youdo.mybatis.transaction;

import org.apache.ibatis.transaction.Transaction;

import org.apache.ibatis.logging.jdbc.ConnectionLogger;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import org.springframework.util.Assert;

import javax.sql.DataSource;

import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class SpringManagedTransaction implements Transaction {

	private final Connection connection;

	private final boolean shouldManageConnection;

	public SpringManagedTransaction(Connection connection) {
		Assert.notNull(connection, "Property 'connection' is required");

		this.connection = connection;

		Connection nonLoggingConnection;

		// unwrap the connection if it is a ConnectionLogger for use with Spring
		if (Proxy.isProxyClass(connection.getClass())) {
			InvocationHandler handler = Proxy.getInvocationHandler(connection);

			if (handler instanceof ConnectionLogger) {
				nonLoggingConnection = ((ConnectionLogger) handler).getConnection();
			} else {
				nonLoggingConnection = connection;
			}
		} else {
			nonLoggingConnection = connection;
		}

		boolean manageConnection = true;

		for (Object o : TransactionSynchronizationManager.getResourceMap().keySet()) {
			if (o instanceof DataSource) {
				manageConnection = !DataSourceUtils.isConnectionTransactional(nonLoggingConnection, (DataSource) o);
			}
		}

		this.shouldManageConnection = manageConnection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void commit() throws SQLException {
		if (shouldManageConnection) {
			connection.commit();
		}
	}

	public void rollback() throws SQLException {
		if (shouldManageConnection) {
			connection.rollback();
		}
	}

	public void close() throws SQLException {
		if (shouldManageConnection) {
			connection.close();
		}
	}
}

package com.youdo.mybatis.transaction;

import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.Transaction;

import java.util.Properties;

import java.sql.Connection;

public class SpringManagedTransactionFactory implements TransactionFactory {

	public SpringManagedTransactionFactory() {
	}

	public Transaction newTransaction(Connection conn, boolean autoCommit) {
		return new SpringManagedTransaction(conn);
	}

	public void setProperties(Properties props) {
	}
}

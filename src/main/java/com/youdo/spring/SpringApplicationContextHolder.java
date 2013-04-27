package com.youdo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author shsun
 * 
 */
public class SpringApplicationContextHolder implements ApplicationContextAware, DisposableBean {
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SpringApplicationContextHolder.class);

	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringApplicationContextHolder.context = context;
		logger.info("setApplicationContext");
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) context.getBean(name);
	}

	public static <T> T getBean(Class<T> cls) {
		return (T) context.getBean(cls);
	}

	public void destroy() throws Exception {
		SpringApplicationContextHolder.cleanApplicationContext();
	}

	public static void cleanApplicationContext() {
		context = null;
	}
}

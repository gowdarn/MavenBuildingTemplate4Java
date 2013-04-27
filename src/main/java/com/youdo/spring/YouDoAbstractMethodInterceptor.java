package com.youdo.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author shsun
 * 
 */
public abstract class YouDoAbstractMethodInterceptor implements MethodInterceptor, InitializingBean {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Object returnObject = null;
		try {
			this.doBeforeInvokeAdvice(methodInvocation);
			//
			returnObject = methodInvocation.proceed();
			//
			this.doAfterReturnAdvice(returnObject, methodInvocation);
		} catch (Throwable throwable) {
			this.doThrowsAdvice(throwable);
		} finally {
			this.doFinallyAdvice(returnObject, methodInvocation);
		}
		return returnObject;
	}

	/**
	 * 
	 * @param methodInvocation
	 */
	abstract protected void doBeforeInvokeAdvice(MethodInvocation methodInvocation);

	/**
	 * 
	 * @param returnObject
	 * @param methodInvocation
	 */
	abstract protected void doAfterReturnAdvice(Object returnObject, MethodInvocation methodInvocation);

	/**
	 * 
	 * @param throwable
	 */
	abstract protected void doThrowsAdvice(Throwable throwable);

	/**
	 * 
	 * @param returnObject
	 * @param methodInvocation
	 */
	abstract protected void doFinallyAdvice(Object returnObject, MethodInvocation methodInvocation);
}

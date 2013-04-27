package com.youdo.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

/**
 * 
 * @author shsun
 * 
 */
public abstract class AbstractHttpServletTestCase extends TestCase {

	@org.mockito.Mock
	protected HttpServletRequest request;

	@org.mockito.Mock
	protected HttpServletResponse response;

	@org.mockito.Mock
	protected HttpSession session;

	@SuppressWarnings("rawtypes")
	@org.mockito.Mock
	protected Map attributes;

	@SuppressWarnings("rawtypes")
	@org.mockito.Mock
	protected Map parameters;

	@SuppressWarnings({ "rawtypes" })
	@Before
	public void setUp() throws Exception {
		super.setUp();
		//
		this.parameters = new HashMap();
		this.attributes = new HashMap();
		//
		this.request = PowerMockito.mock(HttpServletRequest.class);
		this.response = PowerMockito.mock(HttpServletResponse.class);
		this.session = PowerMockito.mock(HttpSession.class);
		//
		PowerMockito.doReturn(this.parameters).when(this.request).getParameterMap();
		PowerMockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];
				return parameters.get(key);
			}
		}).when(this.request).getParameter(Mockito.anyString());
		//
		PowerMockito.doReturn(this.session).when(this.request).getSession();
		PowerMockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];
				return attributes.get(key);
			}
		}).when(this.session).getAttribute(Mockito.anyString());
		//
		PowerMockito.doAnswer(new Answer<Object>() {
			@SuppressWarnings("unchecked")
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];
				Object value = invocation.getArguments()[1];
				attributes.put(key, value);
				return null;
			}
		}).when(session).setAttribute(Mockito.anyString(), Mockito.anyObject());
		//
		this.onSetUpCompleted();
	}

	/**
	 * instantiate the servlet which under test
	 */
	protected abstract void onSetUpCompleted();

	protected abstract void onTearDownCompleted();

	@After
	public void tearDown() throws Exception {
		this.onTearDownCompleted();
		super.tearDown();
	}
}
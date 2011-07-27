package org.morozko.java.core.ent.servlet.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext {

	public static ActionContext newContext( HttpServletRequest request, HttpServletResponse response, ServletContext context ) {
		return new ActionContext(request, response, context);
	}
	
	private ActionContext(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		this.request = request;
		this.response = response;
		this.context = context;
	}

	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private ServletContext context;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
	
}

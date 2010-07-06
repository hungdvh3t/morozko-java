/*****************************************************************
<copyright>
	Morozko Java Library 

	Copyright (c) 2007 Morozko

	All rights reserved. This program and the accompanying materials
	are made available under the terms of the Apache License v2.0
	which accompanies this distribution, and is available at
	http://www.apache.org/licenses/
	(txt version : http://www.apache.org/licenses/LICENSE-2.0.txt
	html version : http://www.apache.org/licenses/LICENSE-2.0.html)

   This product includes software developed at
   The Apache Software Foundation (http://www.apache.org/).
</copyright>
*****************************************************************/
/*
 * @(#)HttpFilter.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.web.filter
 * @creation	: 14/lug/07
 * @release		: xxxx.xx.xx
 */
package org.morozko.java.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>/p>
 *
 * @author Morozko
 *
 */
public abstract class HttpFilter extends LogObjectFilter {

	public abstract void init(FilterConfig config) throws ServletException;

	public abstract void doFilter( HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if ( request instanceof HttpServletRequest && response instanceof HttpServletResponse ) {
			this.getLog().debug( "Http request and response : doFilter http" );
			this.doFilter( (HttpServletRequest)request, (HttpServletResponse)response, chain );
		} else {
			this.getLog().debug( "Not http request and response : proceeding with chain" );
			chain.doFilter( request, response );
		}
	}

	public abstract void destroy();

}

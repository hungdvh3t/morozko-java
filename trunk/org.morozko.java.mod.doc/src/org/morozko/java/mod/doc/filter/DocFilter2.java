/*****************************************************************
<copyright>
	Morozko Java Library 

	Copyright (c) 2008 Morozko

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
 * @(#)DocFilter2.java
 *
 * @project     : org.morozko.java.mod.doc
 * @package     : org.morozko.java.mod.doc.filter
 * @creation	: 18/gen/08
 * @release		: xxxx.xx.xx
 */
package org.morozko.java.mod.doc.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.ent.servlet.filter.HttpFilter;
import org.morozko.java.core.io.StreamIO;
import org.morozko.java.core.lang.helpers.ClassHelper;
import org.morozko.java.core.xml.dom.DOMIO;
import org.morozko.java.core.xml.dom.DOMUtils;
import org.morozko.java.core.xml.dom.SearchDOM;
import org.morozko.java.mod.doc.DocBase;
import org.morozko.java.mod.doc.DocFacade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <p></p>
 *
 * @author Morozko
 *
 */
public class DocFilter2 extends HttpFilter {

	private ServletContext context;
	
	private HashMap docHandlerMap;
	
	private String jspPath;
	
	private boolean debug;
	
	/* (non-Javadoc)
	 * @see org.opinf.jlib.ent.servlet.filter.HttpFilter#destroy()
	 */
	public void destroy() {
		this.context = null;
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.ent.servlet.filter.HttpFilter#doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		this.getLog().info( "start processing version 0.9.1 (2007-12-20)" );
		String uri = request.getRequestURI();
		String render = request.getParameter( "render-type" );
		String truncate = request.getParameter( "truncate" );
		this.getLog().info( "uri : "+uri );
		this.getLog().info( "render-type : "+render );
		this.getLog().info( "truncate    : "+truncate );
		String data = uri.substring( uri.lastIndexOf( "/" )+1 );
		String name = data;
		String type = render;

		int index = data.lastIndexOf( "." );
		if ( render == null ) {
			name = data.substring( 0, index );
			type = data.substring( index+1 );
		} else if ( index != -1 ) {
			name = data.substring( 0, index );	
		}
		if ( truncate != null ) {
			name = name.substring( 0, Integer.parseInt( truncate ) );
		}
		this.getLog().info( "name : "+name );
		this.getLog().info( "type : "+type );
		
		if ( "jsp".equalsIgnoreCase( type ) ) {
			// fine processamento
			DocHandler docHandler = (DocHandler)this.docHandlerMap.get( name );
			try {
				docHandler.handleDoc( request, response, this.context );
				String jspPath = this.jspPath+"/"+name+".jsp";
				RequestDispatcher rd = request.getRequestDispatcher( jspPath );
				this.getLog().info( "jspPath : '"+jspPath+"'" );
				rd.forward( request , response );
			} catch (Exception e) {
				throw ( new ServletException( e ) );
			}			
		} else {
			String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+this.jspPath+"/"+name+".jsp?docFilter=1";
			StringWriter writer = new StringWriter();
			Enumeration e = request.getParameterNames();
			while ( e.hasMoreElements() ) {
				String key = (String)e.nextElement();
				String value = request.getParameter( key );
				url+= "&"+key+"="+value;
			}
			this.getLog().info( "URL : "+url );
			URL urlConn = new URL( url );
			StreamIO.pipeChar( new InputStreamReader( urlConn.openStream() ), writer, StreamIO.MODE_CLOSE_BOTH );
			String xmlData = writer.toString();
			this.getLog().info( "xmlData char : \n"+xmlData );
			try {
				DocBase docBase = DocFacade.parse( new ByteArrayInputStream( xmlData.getBytes() ) );
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				String contentType = "text/xml";
				if ( type.equalsIgnoreCase( "xml" ) ) {
					baos.write( xmlData.getBytes() );
				} else if ( type.equalsIgnoreCase( "pdf" ) ) {
					contentType = "application/pdf";
					DocFacade.createPDF( docBase , baos );
				} else if ( type.equalsIgnoreCase( "rtf" ) ) {
					contentType = "application/msword";
					DocFacade.createRTF( docBase , baos );
				} else if ( type.equalsIgnoreCase( "html" ) ) {
					contentType = "text/html";
					DocFacade.createHTML( docBase , baos );				
				}
				this.getLog().info("content type : "+contentType );
				response.setContentType( contentType );
				baos.writeTo( response.getOutputStream() );				
			} catch (Exception e1) {
				throw ( new ServletException( e1 ) );
			}	
 		}
		
		this.getLog().info( "end processing" );
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.ent.servlet.filter.HttpFilter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		this.getLog().info( "init start" );
		this.context = config.getServletContext();
		this.docHandlerMap = new HashMap();
		try {
			String configPath = config.getInitParameter( "config" );
			File configFile = new File( this.context.getRealPath( "/" ), configPath );
			Document doc = DOMIO.loadDOMDoc( configFile );
			Element root = doc.getDocumentElement();
			SearchDOM searchDOM = SearchDOM.newInstance( true , true );
			Element generalConfigTag = searchDOM.findTag( root , "general-config" );
			Properties generalConfigAtts = DOMUtils.attributesToProperties( generalConfigTag );
			this.jspPath = generalConfigAtts.getProperty( "jsp-path" );
			this.debug = Boolean.valueOf( generalConfigAtts.getProperty( "debug" ) ).booleanValue();
			this.getLog().info( "jsp-path : "+this.jspPath );
			this.getLog().info( "debug    : "+this.debug );
			List docHandlerTagList = searchDOM.findAllTags( root , "doc-handler" );
			Iterator docHandlerTagIt = docHandlerTagList.iterator();
			while ( docHandlerTagIt.hasNext() ) {
				Element docHandlerTag = (Element) docHandlerTagIt.next();
				Properties atts = DOMUtils.attributesToProperties( docHandlerTag );
				String name = atts.getProperty( "name" );
				String type = atts.getProperty( "type" );
				this.getLog().info( "DocHandler : "+name+" -> "+type );
				DocHandler docHandler = (DocHandler)ClassHelper.newInstance( type );
				this.docHandlerMap.put( name , docHandler );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getLog().info( "init end" );
	}

}
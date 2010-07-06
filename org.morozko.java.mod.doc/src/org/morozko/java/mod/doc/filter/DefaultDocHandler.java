/*
 * @(#)DefaultDocHandler.java
 *
 * @project    : serviceapp
 * @package    : net.jsomnium.jlib.mod.doc.filter
 * @creation   : 12/lug/07
 * @license	   : META-INF/LICENSE.TXT
 */
package org.morozko.java.mod.doc.filter;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.cfg.ConfigException;
import org.morozko.java.core.log.BasicLogObject;
import org.morozko.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

/**
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author mfranci
 *
 */
public class DefaultDocHandler extends BasicLogObject implements DocHandler {

	private String encoding;
	
	/* (non-Javadoc)
	 * @see net.jsomnium.jlib.mod.doc.filter.DocHandler#handleDoc(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.ServletContext)
	 */
	public void handleDoc(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws Exception {
		this.getLog().info( "handleDoc" );
	}

	public void handleDocPost(HttpServletRequest request,HttpServletResponse response, ServletContext context) throws Exception {
		this.getLog().info( "handleDocPost" );
	}

	public String getEncoding() {
		return encoding;
	}

	public void init(Element config) throws ConfigException {
		Properties props = DOMUtils.attributesToProperties( config );
		this.encoding = props.getProperty( "encoding", "ISO-8859-15" );
		this.getLog().info( "init() encofing : "+this.encoding );
	}
	
}

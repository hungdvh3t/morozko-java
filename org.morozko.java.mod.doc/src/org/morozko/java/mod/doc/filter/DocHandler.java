/*
 * @(#)DocHandler.java
 *
 * @project    : serviceapp
 * @package    : net.jsomnium.jlib.mod.doc.filter
 * @creation   : 12/lug/07
 * @license	   : META-INF/LICENSE.TXT
 */
package org.morozko.java.mod.doc.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.cfg.ConfigException;
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
public interface DocHandler {

	public void handleDoc( HttpServletRequest request, HttpServletResponse response, ServletContext context ) throws Exception;
	
	public void handleDocPost( HttpServletRequest request, HttpServletResponse response, ServletContext context ) throws Exception;
	
	public void init( Element config ) throws ConfigException;
	
	public String getEncoding();
	
}

package org.morozko.java.mod.web.servlet.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class SessionContext {

	
	public static SessionContext getHttpSessionContext( HttpServletRequest request ) {
		HttpSession session = request.getSession();
		SessionContext context = (SessionContext)session.getAttribute( HttpSessionContext.ATT_NAME );
		if ( context == null ) {
			context = new HttpSessionContext( session );
			session.setAttribute( HttpSessionContext.ATT_NAME , context );
		}
		return context;
	}
	
	public SessionContext() {
	}
	
	public abstract String getId();
	
	public abstract void removeAttribute( String name );
	
	public abstract Object getAttribute( String name );
	
	public Object getAttribute( String name, Object value ) {
		Object result = this.getAttribute( name );
		if ( result == null ) {
			result = value;
			this.setAttribute( name , value );
		}
		return result;
	}
	
	public abstract void setAttribute( String name, Object value );
	
	public abstract Iterator attributeNames();
	
	
//	public static SessionContext getHttpSessionContext( HttpServletRequest request ) {
//		return SessionContext.getHttpSessionContext( request.getSession() );
//	}
//	
//	private static SessionContext getHttpSessionContext( HttpSession session ) {
//		SessionContext context = (SessionContext)session.getAttribute( HttpSessionContext.ATT_NAME );
//		if ( context == null ) {
//			context = new HttpSessionContext( session );
//			session.setAttribute( HttpSessionContext.ATT_NAME , context );
//		}
//		return context;
//	}
//	
//	public SessionContext() {
//		this.map = new HashMap();
//	}
//	
//	private HashMap map;
//	
//	public abstract String getId();
//	
//	public void removeAttribute( String name ) {
//		this.map.remove( name );
//	}
//	
//	public Object getAttribute( String name ) {
//		return this.map.get( name );
//	}
//	
//	public Object getAttribute( String name, Object value ) {
//		Object result = this.map.get( name );
//		if ( result == null ) {
//			result = value;
//			this.setAttribute( name , value );
//		}
//		return result;
//	}
//	
//	public void setAttribute( String name, Object value ) {
//		this.map.put( name , value );
//	}
//	
//	public Iterator attributeNames() {
//		return this.map.keySet().iterator();
//	}
	
}


class HttpSessionContext extends SessionContext {

	private HttpSession session;
	
	private String id;
	
	public Iterator attributeNames() {
		return Collections.list( this.session.getAttributeNames() ).iterator();
	}

	public Object getAttribute(String name) {
		return this.session.getAttribute( name );
	}

	public void removeAttribute(String name) {
		this.session.removeAttribute( name );
	}

	public void setAttribute(String name, Object value) {
		this.session.setAttribute( name , value );
	}

	public static final String ATT_NAME = "org.morozko.java.mod.web.servlet.config.HttpSessionContext.ATT_NAME";
	
	public HttpSessionContext( HttpSession session ) {
		super();
		this.session = session;
	}

	public String getId() {
		return this.session.getId();
	}

}

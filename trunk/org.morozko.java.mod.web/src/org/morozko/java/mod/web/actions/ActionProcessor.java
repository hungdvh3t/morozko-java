package org.morozko.java.mod.web.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.morozko.java.core.log.LogFacade;

public class ActionProcessor {

	public static final String ATT_NAME = "org.morozko.java.mod.web.actions.ActionProcessor.ATT_NAME";
	
	public static final ActionProcessor DEFAULT_PROCESSOR = new ActionProcessor();
	
	public static ActionProcessor getProcessor( ServletContext context )  {
		ActionProcessor ap = (ActionProcessor)context.getAttribute( ATT_NAME );
		if ( ap == null ) {
			ap = DEFAULT_PROCESSOR;
		}
		return ap;
	}
	
	public static void setProcessor( ServletContext context, ActionProcessor ap) {
		context.setAttribute( ATT_NAME , ap );
	}

	
	public void before( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Object caller ) {
		LogFacade.getLog().info( "ActionProcessor."+caller.getClass().getName()+" start" );
	}
	
	public void after( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Object caller ) {
		LogFacade.getLog().info( "ActionProcessor."+caller.getClass().getName()+" end" );
	}	
	
	public ActionForward error( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Throwable t, Object caller ) throws Exception {
		int sc = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		LogFacade.getLog().error( "ActionProcessor."+caller.getClass().getName()+" sc: "+sc+", error : "+t, t );
		response.sendError( sc );
		return null;
	}
	
	
}

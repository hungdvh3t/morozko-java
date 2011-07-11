package org.morozko.java.mod.web.servlet.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.cfg.ConfigException;
import org.morozko.java.core.cfg.VersionUtils;
import org.morozko.java.mod.daogen.helpers.model.BasicModel;
import org.morozko.java.mod.web.servlet.ConfigServlet;

public class VersionConfig extends BasicConfig {


	private static List appList = Collections.synchronizedList( new ArrayList() );
	
	public static final String ATT_NAME = "VERSION_BEAN";
	
	private VersionBean versionBean;
	
	public static final String OPERATION_VERSION = "version";
	
	public void configure(Properties props) throws ConfigException {
		this.getLog().info( "start" );
		this.versionBean = new VersionBean( props.getProperty( "app-name" ), 
									props.getProperty( "app-version" ), 
									props.getProperty( "app-date" ), 
									BasicModel.TIMESTAMP_FORMAT.format( new Timestamp( System.currentTimeMillis() ) ) );
		appList.add( this.versionBean );
		this.getConfigContext().getContext().setAttribute( ATT_NAME , this.versionBean );
		this.getLog().info( "end" );
	}	
	
	public void renderVersion( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		response.setContentType( "text/html" );
		PrintWriter pw = response.getWriter();
		pw.println( "<html>" );
		pw.println( "<head>" );
		pw.println( "<title>Version page: "+this.versionBean.getAppName()+"</title>" );
		pw.println( "</head>" );
		pw.println( "<body>" );
		pw.println( "<h3>Version page (ConfigServlet load time : "+ConfigServlet.LOAD_TIME+")</h3>" );
		pw.println( "<p>Application : "+this.versionBean.getAppName()+"</p>" );
		pw.println( "<p>Version : "+this.versionBean.getAppVersion()+"</p>" );
		pw.println( "<p>Date : "+this.versionBean.getAppDate()+"</p>" );
		pw.println( "<p>Last startup : "+this.versionBean.getLastStartup()+"</p>" );
		Properties moduleList = VersionUtils.getModuleList();
		Iterator moduleIt = moduleList.keySet().iterator();
		pw.println( "<ul>module list:" );
		while ( moduleIt.hasNext() ) {
			String key = (String) moduleIt.next();
			String value = VersionUtils.getVersionString( key );
			pw.println( "<li>"+value+"</li>" );
		}
		pw.println( "</ul>" );
		if ( this.getInitLog() != null ) {
			pw.println( "<ul>init log:" );
			Iterator it = this.getInitLog().iterator();
			while ( it.hasNext() ) {
				pw.println( "<li>"+it.next()+"</li>" );
			}	
		}
		pw.println( "</ul>" );
		if ( "1".equalsIgnoreCase( request.getParameter( "applist" ) ) ) {
			pw.println( "<ul>application list:" );
			Iterator itApp = appList.iterator();
			while ( itApp.hasNext() ) {
				VersionBean currentApp = (VersionBean)itApp.next();
				pw.println( "<li>"+currentApp.getAppName()+" - "+currentApp.getAppDate()+" - "+currentApp.getAppVersion()+"</li>" );
			}
			pw.println( "</ul>" );
		}
		pw.println( "</body>" );
		pw.println( "</html>" );
	}
	
	private List initLog;

	public List getInitLog() {
		return initLog;
	}

	public void setInitLog(List initLog) {
		this.initLog = initLog;
	}
	
}

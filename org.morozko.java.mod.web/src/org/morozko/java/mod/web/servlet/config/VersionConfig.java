package org.morozko.java.mod.web.servlet.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.cfg.ConfigException;
import org.morozko.java.core.cfg.VersionUtils;
import org.morozko.java.mod.daogen.helpers.model.BasicModel;

public class VersionConfig extends BasicConfig {

	public static final String ATT_NAME = "VERSION_BEAN";
	
	private VersionBean versionBean;
	
	public static final String OPERATION_VERSION = "version";
	
	public void configure(Properties props) throws ConfigException {
		this.getLog().info( "start" );
		this.versionBean = new VersionBean( props.getProperty( "app-name" ), 
									props.getProperty( "app-version" ), 
									props.getProperty( "app-date" ), 
									BasicModel.TIMESTAMP_FORMAT.format( new Timestamp( System.currentTimeMillis() ) ) );
		this.getConfigContext().getContext().setAttribute( ATT_NAME , this.versionBean );
		this.getLog().info( "end" );
	}	
	
	public void renderVersion( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		PrintWriter pw = response.getWriter();
		pw.println( "<html>" );
		pw.println( "<head>" );
		pw.println( "<title>Version page: "+this.versionBean.getAppName()+"</title>" );
		pw.println( "</head>" );
		pw.println( "<body>" );
		pw.println( "<h3>Version page</h3>" );
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
		pw.println( "<ul>" );
		pw.println( "</body>" );
		pw.println( "</html>" );
	}
	
}

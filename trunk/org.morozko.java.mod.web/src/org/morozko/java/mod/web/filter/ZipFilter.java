package org.morozko.java.mod.web.filter;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.ent.servlet.filter.HttpFilter;
import org.morozko.java.mod.doc.filter.HttpServletResponseByteData;

public class ZipFilter extends HttpFilter {

	public void init(FilterConfig config) throws ServletException {
		
	}
	
	public static final String EXT = ".zip";

	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String url = request.getRequestURI();
		int index = url.lastIndexOf( EXT );
		if ( index == url.length()-EXT.length() ) {
			int index1 = url.lastIndexOf( "/" );
			String text = url.substring( index1+1 );
			String replace = text.substring( 0, text.length()-EXT.length() );
			HttpServletRequest req = new HttpServletRequestFilter( request, text, replace );
			HttpServletResponseByteData res = new HttpServletResponseByteData( response );
			chain.doFilter( req, res );
			response.setContentType( "application/zip" );
			String contentDisposition = "attachment; filename="+text;
			response.addHeader("Content-Disposition", contentDisposition );
			ZipOutputStream zos = new ZipOutputStream( response.getOutputStream() );
			ZipEntry ze = new ZipEntry( replace );
			zos.putNextEntry( ze );
			zos.write( res.getBaos().toByteArray() );
			zos.closeEntry();
			zos.close();
		} else {
			chain.doFilter( request, response );	
		}
		
	}

	public void destroy() {
		
	}

}

class HttpServletRequestFilter extends HttpServletRequestWrapper {

	private String text;
	
	private String replace;
	
	public HttpServletRequestFilter( HttpServletRequest request, String text, String replace ) {
		super(request);
		this.text = text;
		this.replace = replace;
	}

	public String getRequestURI() {
		return super.getRequestURI().replaceAll( this.text , this.replace );
	}

	public StringBuffer getRequestURL() {
		String s = super.getRequestURL().toString().replaceAll( this.text , this.replace );
		StringBuffer b = new StringBuffer();
		b.append( s );
		return b;
	}
	
}

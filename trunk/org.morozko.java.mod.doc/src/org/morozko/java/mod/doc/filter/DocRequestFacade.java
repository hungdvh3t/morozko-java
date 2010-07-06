package org.morozko.java.mod.doc.filter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.morozko.java.core.ent.tld.html.HtmlTagUtils;
import org.morozko.java.core.lang.helpers.ClassHelper;
import org.morozko.java.core.log.BasicLogObject;
import org.morozko.java.core.text.DefaultTextFilter;
import org.morozko.java.core.text.TextFilter;
import org.morozko.java.core.xml.dom.DOMIO;
import org.morozko.java.core.xml.dom.DOMUtils;
import org.morozko.java.core.xml.dom.SearchDOM;
import org.morozko.java.mod.doc.DocBase;
import org.morozko.java.mod.doc.DocFacade;
import org.morozko.java.mod.doc.DocHelper;
import org.morozko.java.mod.doc.config.DocConfig;
import org.morozko.java.mod.doc.config.DocConstants;
import org.morozko.java.mod.doc.itext.ITextDocHandler;
import org.morozko.java.mod.web.servlet.config.ConfigContext;
import org.w3c.dom.Element;

public class DocRequestFacade extends BasicLogObject {

	public void handleDoc( HttpServletRequest request, HttpServletResponse response ) throws ServletException {
		this.getLog().info( "start processing "+DocConfig.VERSION );
		
		request.setAttribute( "docConsts" , DocConstants.DEF );
		this.getLog().info( "out-mode    : "+this.outMode );
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
		
		request.setAttribute( "doc.render.type" , type );
		
		String contentType = this.mime.getProperty( type, "text/xml" );
		
		if ( !"SERVER".equals( contentType ) ) {
			response.setContentType( contentType );	
		}

		DocTypeHandler docTypeHandler = (DocTypeHandler)this.typeHandlerMap.get( type );

		DocContext docContext = new DocContext();
		docContext.setName(name);
		
		// fine processamento
		DocHandler docHandler = (DocHandler)this.docHandlerMap.get( name );
		try {
			
			if ( docTypeHandler != null ) {
				docTypeHandler.handleDocTypeInit(request, response, docContext);
			}
			
			HttpServletResponse resp = null;
			if ( "pushbody".equalsIgnoreCase( this.outMode ) ) {
				resp = new HttpServletResponsePush( response );
			} else {
				resp = new HttpServletResponseByteData( response );
			}
					
			docHandler.handleDoc( request, resp, this.context.getContext() );
			
			String encoding = docHandler.getEncoding();
			
			try {
				response.setCharacterEncoding( encoding );	
			} catch ( Throwable t  ) {
				this.getLog().info( "failed setting character encoding : "+t );
			}

			String jspPath = this.jspPath+"/doc-handler.jsp";
			if ( this.processingPage != null ) {
				request.setAttribute( "doc-handler-name" , name );
				jspPath = this.jspPath+"/"+this.processingPage;
			}
			RequestDispatcher rd = request.getRequestDispatcher( jspPath );
			this.getLog().info( "jspPath : '"+jspPath+"'" );
			rd.forward( request , resp );
			String xmlData = null;
			if ( "pushbody".equalsIgnoreCase( this.outMode ) ) {
				StringWriter sw = (StringWriter)request.getAttribute( "doc.writer" );
				xmlData = sw.toString();
			} else {
				HttpServletResponseByteData re = (HttpServletResponseByteData) resp;
				re.flush();
				xmlData = re.getBaos().toString();	
			}
			
			if ( debug ) {
				this.getLog().info( "xmlData 1 : \n"+xmlData );
			}
			if ( !this.skipFilter ) {
				if ( debug ) {
					this.getLog().info( "skip filter : true" );
				}
			} else {
				xmlData = this.filter.filterTo( xmlData );
				if ( debug ) {
					this.getLog().info( "xmlData 2 : \n"+xmlData );
				}
			}		
			

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			docContext.setXmlData(xmlData);
			docContext.setBufferStream( baos );
			
			if ( docTypeHandler == null ) {
				DocBase docBase = DocFacade.parse( new StringReader( xmlData  ), this.docHelper );
				String contentDisposition = null;
				if ( type.equalsIgnoreCase( "xml" ) ) {
					baos.write( xmlData.getBytes() );
					contentDisposition = "attachment; filename="+name+".xml";
				} else if ( type.equalsIgnoreCase( "pdf" ) ) {
					DocFacade.createPDF( docBase , baos );
					contentDisposition = "attachment; filename="+name+".pdf";
				} else if ( type.equalsIgnoreCase( "pdf2" ) ) {
					DocFacade.createPDFItext( docBase , baos );
					contentDisposition = "attachment; filename="+name+".pdf";
				} else if ( type.equalsIgnoreCase( "rtf" ) ) {
					DocFacade.createRTF( docBase , baos );
					contentDisposition = "attachment; filename="+name+".rtf";
				} else if ( type.equalsIgnoreCase( "html" ) ) {
					DocFacade.createHTML( docBase , baos );		
					contentDisposition = "attachment; filename="+name+".html";
				}
				this.getLog().info("content type doc  : "+contentType+" : "+encoding );
				this.getLog().info("contentDisposition  : "+contentDisposition );
				if ( contentDisposition != null ) {
					response.addHeader("Content-Disposition", contentDisposition );
				}	
			} else {
				docTypeHandler.handleDocType(request, response, docContext);
			}
			
			OutputStream os = response.getOutputStream();
			baos.writeTo( os );
			baos.flush();
			os.flush();
			docHandler.handleDocPost(request, response, context.getContext());
			if ( docTypeHandler != null ) {
				docTypeHandler.handleDocTypePost(request, response, docContext);
			}
		} catch (Exception e) {
			this.getLog().error( "Error generating doc", e );
			throw ( new ServletException( e ) );
		}
		this.getLog().info( "end processing" );
	}
	
	private void addTypeHandlers( SearchDOM searchDOM, Element rootTag ) {
		List typeHandlerTagList = searchDOM.findAllTags( rootTag , "type-handler" );
		Iterator typeHandlerTagIt = typeHandlerTagList.iterator();
		while ( typeHandlerTagIt.hasNext() ) {
			Element docHandlerTag = (Element) typeHandlerTagIt.next();
			Properties atts = DOMUtils.attributesToProperties( docHandlerTag );
			String name = atts.getProperty( "name" );
			String type = atts.getProperty( "type" );
			this.getLog().info( "TypeHandler : "+name+" -> "+type );
			try {
				DocTypeHandler typeHandler = (DocTypeHandler)ClassHelper.newInstance( type );
				typeHandler.init( docHandlerTag );
				this.typeHandlerMap.put( name , typeHandler );
			} catch (Throwable t1) {
				this.getLog().warn( "Error on type handler init : "+name+" : "+type, t1 );
			}
		}
	}
	
	public void configure( Element root, ConfigContext context ) {
		this.docHandlerMap = new HashMap();
		Properties defMime = new Properties();
		try {
			this.context = context;
			this.typeHandlerMap = new HashMap();
			SearchDOM searchDOM = SearchDOM.newInstance( true , true );
			Element generalConfigTag = searchDOM.findTag( root , "general-config" );
			Properties generalConfigAtts = DOMUtils.attributesToProperties( generalConfigTag );
			this.jspPath = generalConfigAtts.getProperty( "jsp-path" );
			this.outMode = generalConfigAtts.getProperty( "out-mode" );
			this.debug = Boolean.valueOf( generalConfigAtts.getProperty( "debug" ) ).booleanValue();
			this.debug = Boolean.valueOf( generalConfigAtts.getProperty( "skip-filter" ) ).booleanValue();
			this.processingPage = generalConfigAtts.getProperty( "processing-page" );
			this.getLog().info( "jsp-path : "+this.jspPath );
			this.getLog().info( "debug    : "+this.debug );
			this.getLog().info( "processing-page    : "+this.processingPage );
			// doc helper
			String docHelperType = generalConfigAtts.getProperty( "helper-type" );
			this.getLog().info( "docHelperType    : "+docHelperType );
			if ( docHelperType != null ) {
				try {
					this.docHelper = (DocHelper)ClassHelper.newInstance( docHelperType );
				} catch (Exception e2) {
					this.getLog().info( "failed to create : "+e2 );
					this.docHelper = DocHelper.DEFAULT;
				}
			} else {
				this.docHelper = DocHelper.DEFAULT;
			}
			this.getLog().info( "docHelper    : "+this.docHelper );
			// filtro
			String filterName = generalConfigAtts.getProperty( "filter" );
			this.getLog().info( "filterName    : "+filterName );
			if ( filterName != null ) {
				this.filter = HtmlTagUtils.getFilter( context.getContext() , filterName );
			} else {
				this.filter = new DefaultTextFilter();
			}
			this.getLog().info( "filter    : "+this.filter );
			// itext font
			List itextFontTagList = searchDOM.findAllTags( root , "itext-font" );
			Iterator itextFontTagIt = itextFontTagList.iterator();
			while ( itextFontTagIt.hasNext() ) {
				Element itextFontTag = (Element) itextFontTagIt.next();
				Properties itextFontAtts = DOMUtils.attributesToProperties( itextFontTag );
				String name = itextFontAtts.getProperty( "name" );
				String path = itextFontAtts.getProperty( "path" );
				this.getLog().info( "ITextFont : "+name+" -> "+path );
				ITextDocHandler.registerFont(name, path);
			}
			// type handlers
			try {
				this.addTypeHandlers(searchDOM, DOMIO.loadDOMDoc( DocRequestFacade.class.getResourceAsStream("/org/morozko/java/mod/doc/res/type-handler-default.xml") ).getDocumentElement() );
			} catch (Exception e1) {
				this.getLog().warn( e1 );
			}
			this.addTypeHandlers(searchDOM, root);
			// doc handlers
			List docHandlerTagList = searchDOM.findAllTags( root , "doc-handler" );
			Iterator docHandlerTagIt = docHandlerTagList.iterator();
			while ( docHandlerTagIt.hasNext() ) {
				Element docHandlerTag = (Element) docHandlerTagIt.next();
				Properties atts = DOMUtils.attributesToProperties( docHandlerTag );
				String name = atts.getProperty( "name" );
				String type = atts.getProperty( "type" );
				this.getLog().info( "DocHandler : "+name+" -> "+type );
				DocHandler docHandler = (DocHandler)ClassHelper.newInstance( type );
				docHandler.init( docHandlerTag );
				this.docHandlerMap.put( name , docHandler );
			}
			try {
				Element mimeTag = searchDOM.findTag( root , "mime-map" );
				if ( mimeTag != null ) {
					this.mime = new Properties( defMime );
					this.mime.putAll( DOMUtils.attributesToProperties( mimeTag ) );
					this.getLog().info( "override mime : "+this.mime );
				} else {
					this.mime = defMime;
					this.getLog().info( "default mime : "+this.mime );
				}
			} catch (Exception e1) {
				this.getLog().warn( "error setting mime types", e1 );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ConfigContext context;
	
	public ConfigContext getContext() {
		return context;
	}

	public void setContext(ConfigContext context) {
		this.context = context;
	}

	private HashMap docHandlerMap;
	
	public HashMap getDocHandlerMap() {
		return docHandlerMap;
	}

	public void setDocHandlerMap(HashMap docHandlerMap) {
		this.docHandlerMap = docHandlerMap;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public TextFilter getFilter() {
		return filter;
	}

	public void setFilter(TextFilter filter) {
		this.filter = filter;
	}

	public Properties getMime() {
		return mime;
	}

	public void setMime(Properties mime) {
		this.mime = mime;
	}

	public DocHelper getDocHelper() {
		return docHelper;
	}

	public void setDocHelper(DocHelper docHelper) {
		this.docHelper = docHelper;
	}

	private String jspPath;
	
	private boolean skipFilter;
	
	private boolean debug;
	
	private TextFilter filter;
	
	private Properties mime;
	
	private DocHelper docHelper;
	
	private String outMode;
	
	private String processingPage;

	private HashMap typeHandlerMap;
	
	public String getOutMode() {
		return outMode;
	}

	public void setOutMode(String outMode) {
		this.outMode = outMode;
	}
	
}

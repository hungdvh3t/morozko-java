/*****************************************************************
<copyright>
	Morozko Java Library org.morozko.java.mod.doc 

	Copyright (c) 2006 Morozko

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
 * @(#)DocFacade.java
 *
 * @project    : org.morozko.java.mod.doc
 * @package    : org.morozko.java.mod.doc
 * @creation   : 06/set/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.morozko.java.mod.doc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;

import jxl.Workbook;

import org.morozko.java.core.io.StreamIO;
import org.morozko.java.core.lang.Result;
import org.morozko.java.core.xml.XMLValidator;
import org.morozko.java.core.xml.sax.XMLFactorySAX;
import org.morozko.java.core.xml.sax.XMLValidatorSAX;
import org.morozko.java.core.xml.sax.dh.DefaultHandlerComp;
import org.morozko.java.core.xml.sax.er.ByteArrayEntityResolver;
import org.morozko.java.mod.doc.itext.ITextDocHandler;
import org.morozko.java.mod.doc.itext.ItextPdfDocHandler;
import org.morozko.java.mod.doc.type.XlsTypeHandler;
import org.morozko.java.mod.doc.xml.DocContentHandler;
import org.xml.sax.InputSource;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * <p></p>
 *
 * @author mfranci
 *
 */
public class DocFacade {

	private static void print( PrintStream s, DocContainer docContainer, String indent ) {
		Iterator it = docContainer.docElements();
		while ( it.hasNext() ) {
			DocElement docElement = (DocElement) it.next();
			s.println( indent+docElement );
			if ( docElement instanceof DocContainer ) {
				print( s, (DocContainer)docElement, indent+"  " );
			}
		}
	}
	
	private static void print( PrintStream s, DocContainer docContainer ) {
		print( s, docContainer, "" );
	}
	
	public static void print( PrintStream s, DocBase doc ) {
		print( s, doc.getDocBody() );
	}
	
	private static final String DTD = "/org/morozko/java/mod/doc/res/doc-1-0.dtd";
	
	public final static String SYSTEM_ID = "http://www.morozko.org/data/java/mod/doc/dtd/doc-1-0.dtd";
	
	public static boolean validate( InputStream is ) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamIO.pipeStream( DocFacade.class.getResourceAsStream( DTD  ), baos );
		ByteArrayEntityResolver er = new ByteArrayEntityResolver( baos.toByteArray(), null, SYSTEM_ID );
		XMLValidator validator = XMLValidatorSAX.newInstance( er );
		Result result = validator.validateXML( is );
		//result.printErrorReport( System.out );
		return result.isTotalSuccess();
	}
	
	public static DocBase parse( Reader is, DocHelper docHelper ) throws Exception {
		SAXParser parser = XMLFactorySAX.makeSAXParser( false ,  false );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamIO.pipeStream( DocFacade.class.getResourceAsStream( DTD  ), baos );
		ByteArrayEntityResolver er = new ByteArrayEntityResolver( baos.toByteArray(), null, SYSTEM_ID );
		DocContentHandler dch =  new DocContentHandler( docHelper );
		DefaultHandlerComp dh = new DefaultHandlerComp( dch );
		dh.setWrappedEntityResolver( er );
		parser.parse( new InputSource(is), dh);
		DocBase docBase = dch.getDocBase();
		is.close();
		return docBase;
	}	
	
	public static DocBase parse( InputStream is, DocHelper docHelper ) throws Exception {
		SAXParser parser = XMLFactorySAX.makeSAXParser( true ,  false );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamIO.pipeStream( DocFacade.class.getResourceAsStream( DTD  ), baos );
		ByteArrayEntityResolver er = new ByteArrayEntityResolver( baos.toByteArray(), null, SYSTEM_ID );
		DocContentHandler dch = new DocContentHandler( docHelper );
		DefaultHandlerComp dh = new DefaultHandlerComp( dch );
		dh.setWrappedEntityResolver( er );
		parser.parse( is, dh);
		DocBase docBase = dch.getDocBase();
		is.close();
		return docBase;
	}	
	
	public static DocBase parse( Reader is ) throws Exception {
		return parse( is, DocHelper.DEFAULT );
	}
	
	public static DocBase parse( InputStream is ) throws Exception {
		return parse( is, DocHelper.DEFAULT );
	}
	
	public static void createPDFItext( DocBase docBase, OutputStream outputStream ) throws Exception {
		Document document = new Document( PageSize.A4, 20, 20, 20, 20 );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance( document, baos );
		ItextPdfDocHandler handler = new ItextPdfDocHandler( document, ITextDocHandler.DOC_OUTPUT_PDF );
		handler.handleDoc( docBase );
		baos.writeTo( outputStream );
		baos.close();
		outputStream.close();		
	}
	
	public static void createXLS( DocBase docBase, OutputStream outputStream ) throws Exception {
		String excelTemplate = docBase.getInfo().getProperty( XlsTypeHandler.PROP_XLS_TEMPLATE );
		Workbook templateXls = null;
		if ( excelTemplate != null ) {
			templateXls = Workbook.getWorkbook( new File( excelTemplate ) );
		}			
		XlsTypeHandler.handleDoc( docBase , outputStream, templateXls );
	}	
	
	public static void createPDF( DocBase docBase, OutputStream outputStream ) throws Exception {
		String[] margins = docBase.getInfo().getProperty( "margins", "20;20;20;20" ).split( ";" );
		Document document = new Document( PageSize.A4, Integer.parseInt( margins[0] ),
				Integer.parseInt( margins[1] ),
				Integer.parseInt( margins[2] ), 
				Integer.parseInt( margins[3] ) );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance( document, baos );
		ITextDocHandler handler = new ITextDocHandler( document, pdfWriter );
		handler.handleDoc( docBase );
		baos.writeTo( outputStream );
		baos.close();
		outputStream.close();		
	}		
	
	public static void createRTF( DocBase docBase, OutputStream outputStream ) throws Exception {
		String[] margins = docBase.getInfo().getProperty( "margins", "20;20;20;20" ).split( ";" );
		Document document = new Document( PageSize.A4, Integer.parseInt( margins[0] ),
				Integer.parseInt( margins[1] ),
				Integer.parseInt( margins[2] ), 
				Integer.parseInt( margins[3] ) );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		RtfWriter2 rtfWriter2 = RtfWriter2.getInstance( document, baos );
		ITextDocHandler handler = new ITextDocHandler( document, rtfWriter2 );
		handler.handleDoc( docBase );
		baos.writeTo( outputStream );
		baos.close();
		outputStream.close();		
	}	
	
	public static void createHTML( DocBase docBase, OutputStream outputStream ) throws Exception {
		Document document = new Document( );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HtmlWriter.getInstance( document, baos );
		ITextDocHandler handler = new ITextDocHandler( document, ITextDocHandler.DOC_OUTPUT_HTML );
		handler.handleDoc( docBase );
		baos.writeTo( outputStream );
		baos.close();
		outputStream.close();		
	}		
	
	public static void main( String[] args ) {
		try {
			
			File file = new File( args[0] );
			
			File outputDir = new File( args[1] );
			
			InputStream is = new FileInputStream( file  );
			DocBase docBase = parse( is );
			
			//print( System.out, docBase );
//			
//			FileOutputStream fos1 = new FileOutputStream( new File( outputDir, file.getName()+".html" ) );
//			createHTML( docBase, fos1 );
//			
			
			FileOutputStream fos2 = new FileOutputStream( new File( outputDir, file.getName()+".pdf" ) );
			createPDF( docBase, fos2 );
			
			FileOutputStream fos3 = new FileOutputStream( new File( outputDir, file.getName()+".rtf" ) );
			createRTF( docBase, fos3 );
			
			FileOutputStream fos4 = new FileOutputStream( new File( outputDir, file.getName()+".xls" ) );
			createXLS( docBase, fos4 );
			
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
}

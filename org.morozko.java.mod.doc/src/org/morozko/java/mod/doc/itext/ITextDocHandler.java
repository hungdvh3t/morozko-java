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
 * @(#)ITextDocHandler.java
 *
 * @project    : org.morozko.java.mod.doc
 * @package    : org.morozko.java.mod.doc.itext
 * @creation   : 06/set/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.morozko.java.mod.doc.itext;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.morozko.java.core.log.LogFacade;
import org.morozko.java.core.math.BinaryCalc;
import org.morozko.java.mod.doc.DocBase;
import org.morozko.java.mod.doc.DocBorders;
import org.morozko.java.mod.doc.DocCell;
import org.morozko.java.mod.doc.DocElement;
import org.morozko.java.mod.doc.DocFooter;
import org.morozko.java.mod.doc.DocHandler;
import org.morozko.java.mod.doc.DocHeader;
import org.morozko.java.mod.doc.DocHeaderFooter;
import org.morozko.java.mod.doc.DocImage;
import org.morozko.java.mod.doc.DocInfo;
import org.morozko.java.mod.doc.DocPageBreak;
import org.morozko.java.mod.doc.DocPara;
import org.morozko.java.mod.doc.DocPhrase;
import org.morozko.java.mod.doc.DocRow;
import org.morozko.java.mod.doc.DocStyle;
import org.morozko.java.mod.doc.DocTable;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Header;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.html.HtmlTags;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;

/**
 * <p></p>
 *
 * @author mfranci
 *
 */
public class ITextDocHandler implements DocHandler {
	
	private static HashMap fonts = new HashMap();

	public static void registerFont( String name, String path ) throws Exception {
		registerFont( name, BaseFont.createFont( path, BaseFont.CP1252, true ) );
	}
	
	public static void registerFont( String name, BaseFont font ) {
		fonts.put( name , font );
	}
	
	public static BaseFont findFont( String name ) {
		return (BaseFont)fonts.get( name );
	}
	
	private static void setStyle( DocStyle parent, DocStyle current ) {
		if ( current.getBackColor() == null ) {
			current.setBackColor( parent.getBackColor() );
		}
		if ( current.getForeColor() == null ) {
			current.setForeColor( parent.getForeColor() );
		}
	}
	
	public static Color parseHtmlColor( String c ) {
		int r = (int)BinaryCalc.hexToLong( c.substring( 1, 3 ) );
		int g = (int)BinaryCalc.hexToLong( c.substring( 3, 5 ) );
		int b = (int)BinaryCalc.hexToLong( c.substring( 5, 7 ) );
		return new Color( r, g, b );
	}
	
	private PdfWriter pdfWriter;
	
	private RtfWriter2 rtfWriter2;
	
	private Document document;
	
	private String docType;
	
	public final static String DOC_OUTPUT_HTML = "html";
	
	public final static String DOC_OUTPUT_PDF = "pdf";
	
	public final static String DOC_OUTPUT_RTF = "rtf";
	
	public final static String DOC_DEFAULT_FONT_NAME = "default-font-name";
	public final static String DOC_DEFAULT_FONT_SIZE = "default-font-size";
	public final static String DOC_DEFAULT_FONT_STYLE = "default-font-style";

	public ITextDocHandler( Document document, RtfWriter2 rtfWriter2 ) {
		this( document, DOC_OUTPUT_RTF );
		this.rtfWriter2 = rtfWriter2;
	}	
	
	public ITextDocHandler( Document document, PdfWriter pdfWriter ) {
		this( document, DOC_OUTPUT_PDF );
		this.pdfWriter = pdfWriter;
	}	
	
	public ITextDocHandler( Document document, String docType ) {
		this.document = document;
		this.docType = docType;
	}
	
	private static int getAlign( int align ) {
		int r = Element.ALIGN_LEFT;
		if ( align == DocPara.ALIGN_RIGHT ) {
			r = Element.ALIGN_RIGHT;
		} else if ( align == DocPara.ALIGN_CENTER ) {
			r = Element.ALIGN_CENTER;	
		} else if ( align == DocPara.ALIGN_JUSTIFY ) {
			r = Element.ALIGN_JUSTIFIED_ALL;
		}
		return r;
	}
	
	private static int getValign( int align ) {
		int r = Element.ALIGN_TOP;
		if ( align == DocPara.ALIGN_BOTTOM ) {
			r = Element.ALIGN_BOTTOM;
		} else if ( align == DocPara.ALIGN_MIDDLE ) {
			r = Element.ALIGN_MIDDLE;	
		}
		return r;
	}	
	
	protected static Image createImage( DocImage docImage ) {
		Image image = null;
		String url = docImage.getUrl();
		try {
			image = Image.getInstance( new URL( url )  );
			if ( docImage.getScaling() != null ) {
				image.scalePercent( docImage.getScaling().floatValue() );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}	
	
	protected static Phrase createPhrase( DocPhrase docPhrase ) {
		String text = docPhrase.getText();
		Phrase p = new Phrase( text, new Font( Font.HELVETICA, 10, 0 ) );
		return p;
	}	
	
	protected static Paragraph createPara( DocPara docPara, ITextHelper docHelper ) throws Exception {
		int style = docPara.getStyle();
		String text = docPara.getText();
//		if ( DOC_OUTPUT_HTML.equals( this.docType ) ) {
//			int count = 0;
//			StringBuffer buffer = new StringBuffer();
//			while ( count < text.length() && text.indexOf( " " )==count ) {
//				count++;
//			}
//			buffer.append( text.substring( count ) );
//			text = buffer.toString();
//		}
		String fontName = docPara.getFontName();
		Font f = createFont(fontName, docPara.getSize(), style, docHelper );
		Paragraph p = new Paragraph( new Phrase( text, f ) );
		if ( docPara.getForeColor() != null ) {
			Color c = parseHtmlColor( docPara.getForeColor() );
			Font f1 = new Font( f.getFamily(), f.getSize(), f.getStyle(), c );
			p = new Paragraph( new Phrase( text, f1 ) );
		}
		if ( docPara.getAlign() != DocPara.ALIGN_UNSET ) {
			p.setAlignment( getAlign( docPara.getAlign() ) );
		}
		if ( docPara.getLeading() != null ) {
			p.setLeading(  docPara.getLeading().floatValue() );
		}
		if ( docPara.getSpaceBefore() != null ) {
			p.setSpacingBefore( docPara.getSpaceBefore().floatValue() );
		}
		if ( docPara.getSpaceAfter() != null ) {
			p.setSpacingAfter( docPara.getSpaceAfter().floatValue() );
		}		
		return p;
	}
	
	protected static Table createTable( DocTable docTable, ITextHelper docHelper ) throws Exception {
		boolean startHeader = false;
		Table table = new Table( docTable.getColumns() );
		table.setBorderWidth(0);	
		table.setWidth( docTable.getWidth() );
		table.setBorderColor( Color.black );
		table.setPadding(2);
		table.setSpacing(0);
		table.setCellsFitPage( true );
		int[] cw = docTable.getColWithds();
		if (  cw != null ) {
			float[] w = new float[ cw.length ];
			for ( int k=0; k<w.length; k++ ) {
				w[k] = (float)((float)cw[k]/(float)100);
			}
			table.setWidths( w );
		}
		Iterator itRow = docTable.docElements();
		while ( itRow.hasNext() ) {
			DocRow docRow = (DocRow)itRow.next();
			Iterator itCell = docRow.docElements();
			while ( itCell.hasNext() ) {
				DocCell docCell = (DocCell)itCell.next();
				setStyle( docTable, docCell );
				Cell cell = new Cell();
				if ( docCell.isHeader() ) {
					cell.setHeader( true );
					startHeader = true;
				} else {
					if ( startHeader ) {
						startHeader = false;
						table.endHeaders();
					}
				}
				cell.setColspan( docCell.getCSpan() );
				cell.setRowspan( docCell.getRSpan() );
				DocBorders docBorders = docCell.getDocBorders();
				if ( docBorders != null ) {
					if ( docBorders.getBorderColorBottom() != null ) {
						cell.setBorderColorBottom(  parseHtmlColor( docBorders.getBorderColorBottom() ) );
					}
					if ( docBorders.getBorderColorTop() != null ) {
						cell.setBorderColorTop(  parseHtmlColor( docBorders.getBorderColorTop() ) );
					}
					if ( docBorders.getBorderColorLeft() != null ) {
						cell.setBorderColorLeft(  parseHtmlColor( docBorders.getBorderColorLeft() ) );
					}
					if ( docBorders.getBorderColorRight() != null ) {
						cell.setBorderColorRight(  parseHtmlColor( docBorders.getBorderColorRight() ) );
					}
					if ( docBorders.getBorderWidthBottom() != -1 ) {
						cell.setBorderWidthBottom( docBorders.getBorderWidthBottom() );
					}
					if ( docBorders.getBorderWidthTop() != -1 ) {
						cell.setBorderWidthTop( docBorders.getBorderWidthTop() );
					}
					if ( docBorders.getBorderWidthLeft() != -1 ) {
						cell.setBorderWidthLeft( docBorders.getBorderWidthLeft() );
					}
					if ( docBorders.getBorderWidthRight() != -1 ) {
						cell.setBorderWidthRight( docBorders.getBorderWidthRight() );
					}
				}
				if ( docCell.getBackColor() != null ) {
					cell.setBackgroundColor( parseHtmlColor( docCell.getBackColor() ) );
				}
				if ( docCell.getAlign() != DocPara.ALIGN_UNSET ) {
					cell.setHorizontalAlignment( getAlign( docCell.getAlign() ) );
				}
				if ( docCell.getValign() != DocPara.ALIGN_UNSET ) {
					cell.setVerticalAlignment( getValign( docCell.getValign() ) );
				}				
				CellParent cellParent = new CellParent( cell );
				Iterator itCurrent = docCell.docElements();
				while ( itCurrent.hasNext() ) {
					DocElement docElement = (DocElement) itCurrent.next();
					if ( docElement instanceof DocPara ) {
						DocPara docPara = (DocPara)docElement;
						setStyle( docCell , docPara );
						cellParent.add( createPara( docPara, docHelper ) );
					} else if ( docElement instanceof DocPhrase ) {
						DocPhrase docPhrase = (DocPhrase)docElement;
						//setStyle( docCell , docPara );
						LogFacade.getLog().info( "phrase" );
						cellParent.add( createPhrase( docPhrase ) );						
					} else if ( docElement instanceof DocTable ) {
						LogFacade.getLog().debug( "nested table" );
						table.insertTable( createTable( (DocTable)docElement, docHelper ) );
					} else if ( docElement instanceof DocImage ) {
						LogFacade.getLog().debug( "cell DocImage : "+docElement );
						cellParent.add( createImage( (DocImage)docElement ) );
					}
				}
				table.addCell( cell );
			}
		}
		return table;
	}
	
	private static RtfHeaderFooter createRtfHeaderFooter( DocHeaderFooter docHeaderFooter, Document document, boolean header, ITextHelper docHelper ) throws Exception {
		List list = new ArrayList();
		Iterator itDoc = docHeaderFooter.docElements();
		while ( itDoc.hasNext() ) {
			list.add( itDoc.next() );
		}
		Element[] e = new Element[ list.size() ];
		for ( int k=0; k<list.size(); k++ ) {
			e[k] = (Element)getElement( document, (DocElement)list.get( k ) , false, docHelper );
		}
		RtfHeaderFooter rtfHeaderFooter = new RtfHeaderFooter( e );
		rtfHeaderFooter.setDisplayAt( RtfHeaderFooter.DISPLAY_ALL_PAGES );
		if ( header ) {
			rtfHeaderFooter.setType( RtfHeaderFooter.TYPE_HEADER );	
		} else {
			rtfHeaderFooter.setType( RtfHeaderFooter.TYPE_FOOTER );
		}
		return rtfHeaderFooter;
	}
	
	private static Font createFont( String fontName, int fontSize, int fontStyle, ITextHelper docHelper ) throws Exception {
		return createFont(fontName, fontName, fontSize, fontStyle, docHelper);
	}
	
	private static Font createFont( String fontName, String fontPath, int fontSize, int fontStyle, ITextHelper docHelper ) throws Exception {
		Font font = null;
		int size = fontSize;
		int style = Font.NORMAL;
		if ( size == -1 ) {
			size = Integer.parseInt( docHelper.getDefFontSize() );
		}
		if ( fontStyle == DocPara.STYLE_BOLD ) {
			style = Font.BOLD;
		} else if ( fontStyle == DocPara.STYLE_UNDERLINE ) {
			style = Font.UNDERLINE;
		} else if ( fontStyle == DocPara.STYLE_ITALIC ) {
			style = Font.ITALIC;
		} else if ( fontStyle == DocPara.STYLE_BOLDITALIC ) {
			style = Font.BOLDITALIC;
		}
		if ( fontName == null ) {
			fontName = docHelper.getDefFontName();
		}
		if ( "helvetica".equalsIgnoreCase( fontName ) ) {
			font = new Font( Font.HELVETICA, size, style );
		} else if ( "courier".equalsIgnoreCase( fontName ) ) {
			font = new Font( Font.COURIER, size, style );
		} else if ( "times-roman".equalsIgnoreCase( fontName ) ) {
			font = new Font( Font.TIMES_ROMAN, size, style );
		} else if ( "symbol".equalsIgnoreCase( fontName ) ) {
			font = new Font( Font.SYMBOL, size, style );
		} else {
			BaseFont bf = findFont( fontName );
			if ( bf == null) {
				bf = BaseFont.createFont( fontPath, BaseFont.CP1252, true );
				registerFont( fontName, bf );
			}
			font = new Font( bf, size, style );
		}
		return font;
	}
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.doc.DocHandler#handleDoc(org.morozko.java.mod.doc.DocBase)
	 */
	public void handleDoc(DocBase docBase) throws Exception {
		Properties info = docBase.getInfo();
		
		String defaultFontName = info.getProperty( DOC_DEFAULT_FONT_NAME, "helvetica" );
		String defaultFontSize = info.getProperty( DOC_DEFAULT_FONT_SIZE, "10" );
		String defaultFontStyle = info.getProperty( DOC_DEFAULT_FONT_STYLE, "normal" );
		
		ITextHelper docHelper = new ITextHelper();
		docHelper.setDefFontName( defaultFontName );
		docHelper.setDefFontStyle( defaultFontStyle );
		docHelper.setDefFontSize( defaultFontSize );

		
		// per documenti tipo HTML
		if ( DOC_OUTPUT_HTML.equalsIgnoreCase( this.docType ) ) {
			String cssLink = info.getProperty( DocInfo.INFO_NAME_CSS_LINK );
			if ( cssLink != null ) {
				this.document.add( new Header( HtmlTags.STYLESHEET, cssLink ) );
			}
		}
		
		// per documenti tipo word o pdf
		if ( DOC_OUTPUT_PDF.equalsIgnoreCase( this.docType ) || DOC_OUTPUT_RTF.equalsIgnoreCase( this.docType ) ) {
			Rectangle size = this.document.getPageSize();
			String pageOrient = info.getProperty( DocInfo.INFO_NAME_PAGE_ORIENT );
			if ( pageOrient != null ) {
				if ( "horizontal".equalsIgnoreCase( pageOrient ) ) {
					size = new Rectangle( size.getHeight(), size.getWidth() );
					this.document.setPageSize( size );
				}
			}
		}		
		
		// header / footer section
		PdfHelper pdfHelper = new PdfHelper( docHelper );
		DocHeader docHeader = docBase.getDocHeader();
		if ( docHeader != null && docHeader.isUseHeader() ) {
			if ( docHeader.isBasic() ) {
				HeaderFooter header = this.createHeaderFoter( docHeader, docHeader.getAlign() );
				this.document.setHeader( header );	
			} else {
				if ( DOC_OUTPUT_PDF.equals( this.docType ) ) {
					pdfHelper.setDocHeader( docHeader ); 
				} else if ( DOC_OUTPUT_RTF.equals( this.docType ) ) {
					this.document.setHeader( new RtfHeaderFooter( createRtfHeaderFooter( docHeader , this.document, true, docHelper ) ) );
				}
			}
		}
		DocFooter docFooter = docBase.getDocFooter();
		if ( docFooter != null && docFooter.isUseFooter() ) {
			if ( docFooter.isBasic() ) {
				HeaderFooter footer = this.createHeaderFoter( docFooter, docFooter.getAlign() );
				this.document.setFooter( footer );	
			} else {
				if ( DOC_OUTPUT_PDF.equals( this.docType ) ) {
					pdfHelper.setDocFooter( docFooter ); 
				} else if ( DOC_OUTPUT_RTF.equals( this.docType ) ) {
					this.document.setFooter( new RtfHeaderFooter( createRtfHeaderFooter( docFooter , this.document, false, docHelper ) ) );
				}
			}
			
		}		
		if ( DOC_OUTPUT_PDF.equals( this.docType ) ) {
			this.pdfWriter.setPageEvent( pdfHelper );
		}
		
		this.document.open();
		
		Iterator itDoc = docBase.getDocBody().docElements();
		handleElements(document, itDoc, docHelper);
		
		this.document.close();
	}
	
	protected static void handleElements( Document document, Iterator itDoc, ITextHelper docHelper ) throws Exception {
		while ( itDoc.hasNext() ) {
			DocElement docElement = (DocElement)itDoc.next();
			getElement(document, docElement, true, docHelper );
		}
	}
	
	protected static Element getElement( Document document, DocElement docElement, boolean addElement, ITextHelper docHelper ) throws Exception {
		Element result = null;
		DocumentParent documentParent = new DocumentParent( document );
		if ( docElement instanceof DocPara ) {
			result = createPara( (DocPara)docElement, docHelper );
			if ( addElement ) {
				documentParent.add( result );	
			}
		} else if ( docElement instanceof DocTable ) {
			result = createTable( (DocTable)docElement, docHelper );
			if ( addElement ) {
				document.add( result );	
			}
		} else if ( docElement instanceof DocImage ) {
			result = createImage( (DocImage)docElement );
			if ( addElement ) {
				documentParent.add( result );	
			}
		} else if ( docElement instanceof DocPageBreak ) {
			document.newPage();
		}
		return result;
	}
	
	private HeaderFooter createHeaderFoter( DocHeaderFooter container, int align ) throws Exception {
		Iterator it = container.docElements(); 
		Phrase phrase = new Phrase();
		while ( it.hasNext() ) {
			DocElement docElement = (DocElement)it.next();
			if ( docElement instanceof DocPara ) {
				DocPara docPara = (DocPara) docElement;
				if ( docPara.getLeading() != null ) {
					phrase.setLeading( docPara.getLeading().floatValue() );
				}
				Chunk ck = new Chunk( docPara.getText(), new Font( Font.HELVETICA, docPara.getSize() ) );
				phrase.add( ck );
			} else if ( docElement instanceof DocImage ) {
				DocImage docImage = (DocImage)docElement;
				Image img = createImage( docImage );
				Chunk ck = new Chunk( img, 0, 0, true );
				phrase.add( ck );
			}
		}
		HeaderFooter headerFooter  = new HeaderFooter( phrase, container.isNumbered() );
		if ( align == DocPara.ALIGN_UNSET ) {
			align = DocPara.ALIGN_CENTER;
		}
		headerFooter.setAlignment( getAlign( align ) );
		headerFooter.setBorder( container.getBorderWidth() );
		//headerFooter.setUseVariableBorders( true );
		return headerFooter;
	}
	
}

class ITextHelper {

	
	private String defFontName;
	
	private String defFontSize;
	
	private String defFontStyle;

	public String getDefFontName() {
		return defFontName;
	}

	public void setDefFontName(String defFontName) {
		this.defFontName = defFontName;
	}

	public String getDefFontSize() {
		return defFontSize;
	}

	public void setDefFontSize(String defFontSize) {
		this.defFontSize = defFontSize;
	}

	public String getDefFontStyle() {
		return defFontStyle;
	}

	public void setDefFontStyle(String defFontStyle) {
		this.defFontStyle = defFontStyle;
	}
	
}

interface ParentElement {
	
	public void add( Element element ) throws Exception;
	
}

class PhraseParent implements ParentElement {

	private Phrase phrase;
	
	public PhraseParent( Phrase phrase ) {
		this.phrase = phrase;
	}
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.doc.itext.ParentElement#add(com.lowagie.text.Element)
	 */
	public void add(Element element) throws Exception {
		this.phrase.add( element );
	}
	
}

class DocumentParent implements ParentElement {

	private Document document;
	
	public DocumentParent( Document document) {
		this.document = document;
	}
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.doc.itext.ParentElement#add(com.lowagie.text.Element)
	 */
	public void add(Element element) throws Exception {
		this.document.add( element );
	}
	
}

class CellParent implements ParentElement {
	
	private Cell cell;
	
	public CellParent( Cell cell ) {
		this.cell = cell;
	}

	/* (non-Javadoc)
	 * @see org.morozko.java.mod.doc.itext.ParentElement#add(com.lowagie.text.Element)
	 */
	public void add(Element element) throws Exception {
		this.cell.addElement( element );
	}
	
}

class PdfHelper extends PdfPageEventHelper {
	
	public PdfHelper( ITextHelper docHelper ) {
		this.docHelper = docHelper;
	}
	
	private DocHeader docHeader;
	
	private DocFooter docFooter;

	private ITextHelper docHelper;
	
	public void onEndPage( PdfWriter writer, Document doc ) {
		if ( this.getDocFooter() != null ) {
			try {
				ITextDocHandler.handleElements(doc, this.getDocFooter().docElements(), docHelper);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onStartPage(PdfWriter writer, Document doc ) {
		if ( this.getDocHeader() != null ) {
			try {
				ITextDocHandler.handleElements(doc, this.getDocHeader().docElements(), docHelper );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public DocHeader getDocHeader() {
		return docHeader;
	}

	public void setDocHeader(DocHeader docHeader) {
		this.docHeader = docHeader;
	}

	public DocFooter getDocFooter() {
		return docFooter;
	}

	public void setDocFooter(DocFooter docFooter) {
		this.docFooter = docFooter;
	}
	
}
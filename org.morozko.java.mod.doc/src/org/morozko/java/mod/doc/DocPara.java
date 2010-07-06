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
 * @(#)DocPara.java
 *
 * @project    : org.morozko.java.mod.doc
 * @package    : org.morozko.java.mod.doc
 * @creation   : 06/set/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.morozko.java.mod.doc;

/**
 * <p></p>
 *
 * @author mfranci
 *
 */
public class DocPara extends DocElement implements DocStyle {

	
	private Float spaceBefore;
	
	private Float spaceAfter;	
	
	public Float getSpaceBefore() {
		return spaceBefore;
	}

	public void setSpaceBefore(Float spaceBefore) {
		this.spaceBefore = spaceBefore;
	}

	public Float getSpaceAfter() {
		return spaceAfter;
	}

	public void setSpaceAfter(Float spaceAfter) {
		this.spaceAfter = spaceAfter;
	}

	private Float leading;
	
	public Float getLeading() {
		return leading;
	}

	public void setLeading(Float leading) {
		this.leading = leading;
	}

	public DocPara() {
		this.text = "";
	}
	
	public static int parseStyle( String style ) {
		int result = STYLE_NORMAL;
		if ( "bold".equalsIgnoreCase( style ) ) {
			result = STYLE_BOLD;
		} else if ( "underline".equalsIgnoreCase( style ) ) {
			result = STYLE_UNDERLINE;
		} else if ( "italic".equalsIgnoreCase( style ) ) {
			result = STYLE_ITALIC;
		} else if ( "bolditalic".equalsIgnoreCase( style ) ) {
			result = STYLE_BOLDITALIC;
		}
		return result;
	}
	
	public static final int STYLE_NORMAL = 1;
	public static final int STYLE_BOLD = 2;
	public static final int STYLE_UNDERLINE = 3;
	public static final int STYLE_ITALIC = 4;
	public static final int STYLE_BOLDITALIC = 5;
	
	public static final int ALIGN_UNSET = 0;
	// h align
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_CENTER = 2;
	public static final int ALIGN_RIGHT = 3;
	public static final int ALIGN_JUSTIFY = 9;
	// v align
	public static final int ALIGN_TOP = 4;
	public static final int ALIGN_MIDDLE = 5;
	public static final int ALIGN_BOTTOM = 6;	
	
	private String fontName;
	
	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	private int style;
	
	private int size;
	
	private int align;
	
	private String foreColor;
	
	private String backColor;		
	
	/**
	 * @return the backColor
	 */
	public String getBackColor() {
		return backColor;
	}

	/**
	 * @param backColor the backColor to set
	 */
	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	/**
	 * @return the foreColor
	 */
	public String getForeColor() {
		return foreColor;
	}

	/**
	 * @param foreColor the foreColor to set
	 */
	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}

	/**
	 * @return the align
	 */
	public int getAlign() {
		return align;
	}

	/**
	 * @param align the align to set
	 */
	public void setAlign(int align) {
		this.align = align;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public String toString() {
		return super.toString()+"[text:"+this.getText()+"]";
	}
	
	private String text;

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the style
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(int style) {
		this.style = style;
	}
	
}

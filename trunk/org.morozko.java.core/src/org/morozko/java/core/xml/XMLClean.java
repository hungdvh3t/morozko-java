package org.morozko.java.core.xml;

public class XMLClean {

	public static final String CLEAN_REGEX = "(?<![\\uD800-\\uDBFF])[\\uDC00-\\uDFFF]|[\\uD800-\\uDBFF](?![\\uDC00-\\uDFFF])|[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F-\\x9F\\uFEFF\\uFFFE\\uFFFF]"; 
	
	public static String cleanXML( String s ) {
		return s.replaceAll( CLEAN_REGEX , "" );
	}
	
}

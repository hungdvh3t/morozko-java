package org.morozko.java.core.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class XMLClean {

	public static final String CLEAN_REGEX = "(?<![\\uD800-\\uDBFF])[\\uDC00-\\uDFFF]|[\\uD800-\\uDBFF](?![\\uDC00-\\uDFFF])|[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F-\\x9F\\uFEFF\\uFFFE\\uFFFF]"; 
	
	public static String cleanXML( String s ) {
		return s.replaceAll( CLEAN_REGEX , "" );
	}
	
	public static void cleanStream( Reader r, Writer w ) throws Exception {
		BufferedReader reader = new BufferedReader( r );
		PrintWriter writer = new PrintWriter( w );
		String line = reader.readLine();
		while ( line != null ) {
			writer.println( cleanXML( line ) );
			line = reader.readLine();
		}
		reader.close();
		writer.close();
	}
	
	public static void main( String[] args ) {
		try {
			String in = args[0];
			String out = args[1];
			FileReader fr = new FileReader( new File( in ) );
			FileWriter fw = new FileWriter( new File( out ) );
			cleanStream( fr , fw );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

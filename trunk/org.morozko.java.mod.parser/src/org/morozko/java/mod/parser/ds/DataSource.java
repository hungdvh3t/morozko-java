package org.morozko.java.mod.parser.ds;

import org.w3c.dom.Element;

public interface DataSource {

	public abstract String getId();
	
	public abstract void setId( String id );
	
	public abstract void configure( Element config ) throws ParserFatalException;
	
	public abstract ParserOutput parse( ParserInput input ) throws ParserFatalException;
	
	public abstract ProcessOutput process( ProcessInput input ) throws ParserFatalException;
	
}

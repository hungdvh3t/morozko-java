package org.morozko.java.mod.parser.ds.xml;

import org.morozko.java.mod.parser.ds.ParserFatalException;
import org.morozko.java.mod.parser.ds.ParserInput;
import org.morozko.java.mod.parser.ds.ParserOutput;
import org.morozko.java.mod.parser.ds.ProcessInput;
import org.morozko.java.mod.parser.ds.ProcessOutput;
import org.morozko.java.mod.parser.ds.helpers.AbstractDataSource;
import org.w3c.dom.Element;

public class XmlDataSource extends AbstractDataSource {

	@Override
	public void configure(Element config) throws ParserFatalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ParserOutput parse(ParserInput input) throws ParserFatalException {
		ParserOutput output = null;
		if ( true ) {
			throw new ParserFatalException( "Not implemented" );
		}
		return output;
	}

	@Override
	public ProcessOutput process(ProcessInput input) throws ParserFatalException {
		ProcessOutput output = null;
		return output;
	}


}

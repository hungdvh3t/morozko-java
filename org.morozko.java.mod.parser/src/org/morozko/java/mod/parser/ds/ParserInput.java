package org.morozko.java.mod.parser.ds;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ParserInput {

	public ParserInput(String input) {
		super();
		this.input = input;
		this.parameters = new Properties();
	}

	private String input;
	
	private Properties parameters;

	public Properties getParameters() {
		return parameters;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	public InputStream getInputStream() throws ParserFatalException {
		InputStream is = null;
		try {
			is = new URL( this.getInput() ).openStream();
		} catch (Exception e) {
			throw new ParserFatalException( e );
		}
		return is;
	}
	
}

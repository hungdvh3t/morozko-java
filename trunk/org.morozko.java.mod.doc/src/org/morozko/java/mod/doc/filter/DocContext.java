package org.morozko.java.mod.doc.filter;

import java.io.OutputStream;


public class DocContext {
	
	public String getXmlData() {
		return xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}

	private String name;
	
	private String contentType;
	
	private String encoding;
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String xmlData;
	
	private OutputStream bufferStream;

	public OutputStream getBufferStream() {
		return bufferStream;
	}

	public void setBufferStream(OutputStream bufferStream) {
		this.bufferStream = bufferStream;
	}
	
}

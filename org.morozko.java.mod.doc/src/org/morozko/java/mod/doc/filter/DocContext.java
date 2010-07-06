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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

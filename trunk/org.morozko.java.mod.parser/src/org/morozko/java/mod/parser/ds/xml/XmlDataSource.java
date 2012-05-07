package org.morozko.java.mod.parser.ds.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.morozko.java.mod.parser.ds.ParserFatalException;
import org.morozko.java.mod.parser.ds.ParserInput;
import org.morozko.java.mod.parser.ds.ParserOutput;
import org.morozko.java.mod.parser.ds.ProcessInput;
import org.morozko.java.mod.parser.ds.ProcessOutput;
import org.morozko.java.mod.parser.ds.RecordIterator;
import org.morozko.java.mod.parser.ds.RenderInput;
import org.morozko.java.mod.parser.ds.RenderOutput;
import org.morozko.java.mod.parser.ds.helpers.AbstractDataSource;
import org.morozko.java.mod.parser.model.FieldModel;
import org.morozko.java.mod.parser.model.RecordModel;
import org.w3c.dom.Element;

public class XmlDataSource extends AbstractDataSource {

	@Override
	public void configure(Element config) throws ParserFatalException {
		
	}

	@Override
	public ParserOutput parse(ParserInput input) throws ParserFatalException {
		if ( true ) {
			throw new ParserFatalException( "Not implemented" );
		}
		return null;
	}

	@Override
	public ProcessOutput process(ProcessInput input) throws ParserFatalException {
		ProcessOutput output = null;
		return output;
	}

	private static String prepareTagName( String name ) {
		return name;
	}
	
	@Override
	public RenderOutput render(RenderInput input) throws ParserFatalException {
		RenderOutput output = new RenderOutput();
	
		
		
		try {
		
			OutputStream stream = new FileOutputStream( new File( "C:/test.xml" ) );
			
			
			RecordIterator ri = input.getRecords();
			ri.open();
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware( true );
			
			XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter( stream );
			
			writer.writeStartDocument();
			
			writer.writeStartElement( prepareTagName( "metadata" ) );
			
			if ( ri.getMetadataDescription().getName() != null ) {
				writer.writeAttribute( prepareTagName( "name" ) , ri.getMetadataDescription().getName() );
			}
			
			writer.writeCharacters( ""+'\n' );
			
			while ( ri.hasNext() ) {
				RecordModel record = ri.getNext();
				writer.writeEmptyElement( prepareTagName( record.getRecordDescription().getId() ) );
				Iterator<FieldModel> fields = record.getFields();
				while ( fields.hasNext() ) {
					FieldModel field = fields.next();
					writer.writeAttribute( prepareTagName( field.getName() ) , field.getValue() );
				}
				writer.writeCharacters( ""+'\n' );
			}
			
			
			writer.writeEndElement();

			
			writer.writeEndDocument();
			
			ri.close();
			writer.close();
			stream.close();
			
			
		} catch (Exception e) {
			throw new ParserFatalException( e );
		}
		
		return output;
	}

}


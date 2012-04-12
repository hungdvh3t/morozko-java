package org.morozko.java.mod.parser.ds.pos;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.morozko.java.core.xml.dom.DOMUtils;
import org.morozko.java.mod.parser.ds.ParserFatalException;
import org.morozko.java.mod.parser.ds.ParserInput;
import org.morozko.java.mod.parser.ds.ParserOutput;
import org.morozko.java.mod.parser.ds.ProcessInput;
import org.morozko.java.mod.parser.ds.ProcessOutput;
import org.morozko.java.mod.parser.ds.helpers.AbstractDataSource;
import org.morozko.java.mod.parser.facade.ConfigReader;
import org.w3c.dom.Element;

public class PositionalDataSource extends AbstractDataSource {

	@Override
	public void configure(Element config) throws ParserFatalException {
		this.getLog().info( "config> start" );
		List<Element> metadataTagList = ConfigReader.SEARCH_DOM.findAllTags( config , "metadata" );
		Iterator<Element> metadataTagIt = metadataTagList.iterator();
		while ( metadataTagIt.hasNext() ) {
			Element currentMetadataTag = metadataTagIt.next();
			Properties currentMetadataProps = DOMUtils.attributesToProperties( currentMetadataTag );
			String currentMetadataId = currentMetadataProps.getProperty( "id" );
			this.getLog().info( "metadata : "+currentMetadataId );
			List<Element> recordTagList = ConfigReader.SEARCH_DOM.findAllTags( currentMetadataTag , "record-description" );
			Iterator<Element> recordTagIt = recordTagList.iterator();
			while ( recordTagIt.hasNext() ) {
				Element currentRecordTag = recordTagIt.next();
				Properties currentRecordProps = DOMUtils.attributesToProperties( currentRecordTag );
				String currentRecordId = currentRecordProps.getProperty( "id" );
				this.getLog().info( "record : "+currentRecordId );
				List<Element> fieldTagList = ConfigReader.SEARCH_DOM.findAllTags( currentRecordTag , "field-description" );
				Iterator<Element> fieldTagIt = fieldTagList.iterator();
				while ( fieldTagIt.hasNext() ) {
					Element currentFieldTag = fieldTagIt.next();
					Properties currentFieldProps = DOMUtils.attributesToProperties( currentFieldTag );
					String currentFieldId = currentFieldProps.getProperty( "id" );
					this.getLog().info( "field : "+currentFieldId );
				}
			}
		}
		this.getLog().info( "config> end" );
	}

	@Override
	public ParserOutput parse( ParserInput input ) throws ParserFatalException {
		ParserOutput output = null;
		return output;
	}

	@Override
	public ProcessOutput process(ProcessInput input) throws ParserFatalException {
		
		
		ProcessOutput output = null;
		if ( true ) {
			throw new ParserFatalException( "Not implemented" );
		}
		return output;
	}

}

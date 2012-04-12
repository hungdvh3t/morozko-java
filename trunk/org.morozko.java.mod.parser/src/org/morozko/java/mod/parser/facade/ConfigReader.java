package org.morozko.java.mod.parser.facade;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.morozko.java.core.lang.helpers.ClassHelper;
import org.morozko.java.core.xml.dom.DOMIO;
import org.morozko.java.core.xml.dom.DOMUtils;
import org.morozko.java.core.xml.dom.SearchDOM;
import org.morozko.java.mod.parser.ds.DataSource;
import org.morozko.java.mod.parser.ds.ParserFatalException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConfigReader {

	public static final SearchDOM SEARCH_DOM = SearchDOM.newInstance( true , true );
	
	public static ParserConfig read( InputStream is ) throws ParserFatalException {
		ParserConfig config = new ParserConfig();
		try {
			
			Document doc = DOMIO.loadDOMDoc( is );
			
			Element dataSourceListTag = SEARCH_DOM.findTag( doc.getDocumentElement() , "data-source-list" );
			
			List<Element> dsTagList = SEARCH_DOM.findAllTags( dataSourceListTag , "data-source" );
			for ( int k=0; k<dsTagList.size(); k++ ) {
				Element currentTagDS = dsTagList.get( k );
				Properties dsProps = DOMUtils.attributesToProperties( currentTagDS );
				DataSource currentDS = (DataSource)ClassHelper.newInstance( dsProps.getProperty( "type" ) );
				currentDS.setId( dsProps.getProperty( "id" ) );
				currentDS.configure( currentTagDS );
			}
			
		} catch (Exception e) {
			throw new ParserFatalException( e );
		}
		return config;
	}
	
}

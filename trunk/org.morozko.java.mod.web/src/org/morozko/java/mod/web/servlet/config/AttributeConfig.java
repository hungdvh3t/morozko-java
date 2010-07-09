package org.morozko.java.mod.web.servlet.config;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.morozko.java.core.cfg.ConfigException;
import org.morozko.java.core.xml.dom.DOMUtils;
import org.morozko.java.core.xml.dom.SearchDOM;
import org.w3c.dom.Element;

public class AttributeConfig extends BasicConfig {

	public void configure(Properties props) throws ConfigException {
		throw ( new ConfigException( "Unsupported" ) );
	}

	public void configure(Element tag) throws ConfigException {
		this.getLog().info( "configure start" );
		SearchDOM search = SearchDOM.newInstance( true , true );
		List listContext = search.findAllTags( tag , "context-attribute" );
		Iterator itContext = listContext.iterator();
		while ( itContext.hasNext() ) {
			Element currentContext = (Element)itContext.next();
			Properties props = DOMUtils.attributesToProperties( currentContext );
			String name = props.getProperty( "name" );
			String value = props.getProperty( "value" );
			this.getLog().info( "configure attribute "+name+"="+value );
			this.getConfigContext().getContext().setAttribute( name , value );
		}
		this.getLog().info( "configure end" );
	}

}

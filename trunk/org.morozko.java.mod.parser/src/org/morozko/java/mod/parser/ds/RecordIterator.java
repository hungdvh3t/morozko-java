package org.morozko.java.mod.parser.ds;

import org.morozko.java.mod.parser.model.MetadataDescription;
import org.morozko.java.mod.parser.model.RecordModel;

public interface RecordIterator {

	public abstract boolean open() throws ParserFatalException;
	
	public abstract boolean hasNext()  throws ParserFatalException;
	
	public abstract RecordModel getNext()  throws ParserFatalException;
	
	public abstract boolean close()  throws ParserFatalException;
	
	public MetadataDescription getMetadataDescription();
	
}

package org.morozko.java.mod.parser.ds;

import org.morozko.java.mod.parser.model.RecordModel;

public interface RecordIterator {

	public abstract boolean open();
	
	public abstract boolean hasNext();
	
	public abstract RecordModel getNext();
	
	public abstract boolean close();
	
}

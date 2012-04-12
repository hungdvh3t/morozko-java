package org.morozko.java.mod.parser.ds.pos;

import org.morozko.java.mod.parser.ds.RecordIterator;
import org.morozko.java.mod.parser.model.RecordModel;

public class PositionalRecordIterator implements RecordIterator {

	@Override
	public boolean open() {
		return false;
	}

	@Override
	public boolean close() {
		return false;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public RecordModel getNext() {
		return null;
	}

}

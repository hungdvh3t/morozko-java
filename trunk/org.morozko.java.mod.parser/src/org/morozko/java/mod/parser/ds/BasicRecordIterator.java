package org.morozko.java.mod.parser.ds;

import org.morozko.java.mod.parser.filter.FilterChain;
import org.morozko.java.mod.parser.model.RecordModel;

public abstract class BasicRecordIterator implements RecordIterator {

	public BasicRecordIterator() {
		this.currentRecord = null;
	}
	
	private FilterChain recordFilterChain;

	public FilterChain getRecordFilterChain() {
		return recordFilterChain;
	}

	public void setRecordFilterChain(FilterChain recordFilterChain) {
		this.recordFilterChain = recordFilterChain;
	}
	
	private RecordModel currentRecord;
	
	protected abstract RecordModel findNextWorker() throws ParserFatalException;

	@Override
	public boolean hasNext() throws ParserFatalException {
		RecordModel checkRecord = findNextWorker();
		if ( this.getRecordFilterChain() != null ) {
			while ( checkRecord != null && !this.getRecordFilterChain().accept( checkRecord ) ) {
				checkRecord = findNextWorker();
			}
		}
		this.currentRecord = checkRecord;
		return this.currentRecord != null;
	}

	@Override
	public RecordModel getNext() throws ParserFatalException {
		return this.currentRecord;
	}

	
}


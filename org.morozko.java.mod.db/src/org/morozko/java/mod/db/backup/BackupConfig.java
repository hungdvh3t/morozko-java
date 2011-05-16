/*****************************************************************
<copyright>
	Morozko Java Library org.morozko.java.mod.db 

	Copyright (c) 2006 Morozko

	All rights reserved. This program and the accompanying materials
	are made available under the terms of the Apache License v2.0
	which accompanies this distribution, and is available at
	http://www.apache.org/licenses/
	(txt version : http://www.apache.org/licenses/LICENSE-2.0.txt
	html version : http://www.apache.org/licenses/LICENSE-2.0.html)

   This product includes software developed at
   The Apache Software Foundation (http://www.apache.org/).
</copyright>
*****************************************************************/
/*
 * @(#)BackupConfig.java
 *
 * @project    : org.morozko.java.mod.db
 * @package    : org.morozko.java.mod.db.backup
 * @creation   : 05/set/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.morozko.java.mod.db.backup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.morozko.java.core.cfg.ConfigException;
import org.morozko.java.core.cfg.helpers.XMLConfigurableObject;
import org.morozko.java.core.lang.helpers.ClassHelper;
import org.morozko.java.core.util.CheckUtils;
import org.morozko.java.core.xml.dom.DOMUtils;
import org.morozko.java.core.xml.dom.SearchDOM;
import org.morozko.java.mod.db.connect.ConnectionFactory;
import org.morozko.java.mod.db.connect.ConnectionFactoryImpl;
import org.w3c.dom.Element;

/**
 * <p></p>
 *
 * @author mfranci
 *
 */
public class BackupConfig extends XMLConfigurableObject {

	public static final String PROP_DELETE_FIRST = "delete-first";
	
	public BackupConfig() {
		this.tableList = new ArrayList();
		this.sequenceList = new ArrayList();
	}
	
	private Properties generalProperties;
	
	private TableBackup tableBackup;
	
	private ConnectionFactory factoryFrom;
	
	private ConnectionFactory factoryTo;
	
	private List tableList;
	
	private List sequenceList;

	/* (non-Javadoc)
	 * @see org.morozko.java.core.cfg.ConfigurableObject#configure(org.w3c.dom.Element)
	 */
	public void configure(Element config) throws ConfigException {
		try {
			SearchDOM searchDOM = SearchDOM.newInstance( true , true );
			
			this.tableList.clear();
			
			this.sequenceList.clear();
			
			Element backupConfig = searchDOM.findTag( config , "backup-config" );
			Properties props = DOMUtils.attributesToProperties( backupConfig );
			
			//System.err.println( "backupConfig: "+props );
			
			this.generalProperties = props;
			
			String type = props.getProperty( "type" );
			if ( type != null ) {
				this.tableBackup = (TableBackup)ClassHelper.newInstance( type );	
			} else {
				this.tableBackup = new DefaultTableBackup();
			}
			
			String commitOn = props.getProperty( TableBackup.PROP_COMMIT_ON );
			if ( commitOn != null ) {
				this.tableBackup.setProperty( TableBackup.PROP_COMMIT_ON, commitOn );
			}
			
			String insetMode = props.getProperty( TableBackup.PROP_INSERT_MODE );
			if ( insetMode != null ) {
				this.tableBackup.setProperty( TableBackup.PROP_INSERT_MODE, insetMode );
			}
			
			this.tableBackup.setProperty( TableBackup.PROP_STATEMENT_MODE , props.getProperty( TableBackup.PROP_STATEMENT_MODE, TableBackup.PROP_STATEMENT_MODE_BATCH ) );
			this.tableBackup.setProperty( TableBackup.PROP_ADAPTOR_FROM , props.getProperty( TableBackup.PROP_ADAPTOR_FROM, TableBackup.PROP_ADAPTOR_VALUE_DEFAULT ) );
			this.tableBackup.setProperty( TableBackup.PROP_ADAPTOR_TO , props.getProperty( TableBackup.PROP_ADAPTOR_TO, TableBackup.PROP_ADAPTOR_VALUE_DEFAULT ) );
			this.tableBackup.setProperty( TableBackup.PROP_COLUMN_CHECK_MODE , props.getProperty( TableBackup.PROP_COLUMN_CHECK_MODE, TableBackup.PROP_COLUMN_CHECK_MODE_VALUE_COMPLETE ) );
			
			Element factoryFromTag = searchDOM.findTag( backupConfig , "cf-from-config" );
			this.factoryFrom = ConnectionFactoryImpl.newInstance( DOMUtils.attributesToProperties( factoryFromTag ) );
			
			Element factoryToTag = searchDOM.findTag( backupConfig , "cf-to-config" );
			this.factoryTo = ConnectionFactoryImpl.newInstance( DOMUtils.attributesToProperties( factoryToTag ) );
			
			Element sequenceListTag = searchDOM.findTag( backupConfig , "sequence-list" );
			if ( sequenceListTag != null ) {
				List sequenceTags = searchDOM.findAllTags( sequenceListTag , "sequence" );
				Iterator squenceTagsIt = sequenceTags.iterator();
				while ( squenceTagsIt.hasNext() ) {
					Element sequenceTag = (Element)squenceTagsIt.next();
					String sequenceName = searchDOM.findText( sequenceTag );
					SequenceConfig sequenceConfig = new SequenceConfig();
					sequenceConfig.setSequenceName(sequenceName);
					this.sequenceList.add( sequenceConfig );
				}
			}
			
			Element tableListTag = searchDOM.findTag( backupConfig , "table-list" );
			
			List tableTags = searchDOM.findAllTags( tableListTag , "table" );
			Iterator tableTagsIt = tableTags.iterator();
			while ( tableTagsIt.hasNext() ) {
				//System.out.println( "START ITERATE" );
				Element tableTag = (Element)tableTagsIt.next();
				Properties tableAtts = DOMUtils.attributesToProperties( tableTag );
				String table = searchDOM.findText( tableTag );
				//System.out.println( "E: "+tableTag.getNodeName() );
				//System.out.println( "T: "+table );
				TableConfig tableConfig = new TableConfig();
				tableConfig.setTable(table);
				String select = tableAtts.getProperty( "select" );
//				System.err.println( "TABLE  : "+table );
//				System.err.println( "PROPS  : "+tableAtts );
//				System.err.println( "SELECT : "+select );
				tableConfig.setSelect( select );
				this.tableList.add( tableConfig );
				//System.out.println( "END ITERATE" );
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean deleteFirst() {
		return CheckUtils.isTrue( this.getGeneralProperties().getProperty( PROP_DELETE_FIRST ) );
	}
	
	/**
	 * @return the factoryFrom
	 */
	public ConnectionFactory getFactoryFrom() {
		return factoryFrom;
	}

	/**
	 * @return the factoryTo
	 */
	public ConnectionFactory getFactoryTo() {
		return factoryTo;
	}

	/**
	 * @return the tableBackup
	 */
	public TableBackup getTableBackup() {
		return tableBackup;
	}

	/**
	 * @return the tableList
	 */
	public List getTableList() {
		return tableList;
	}

	/**
	 * @return the generalProperties
	 */
	public Properties getGeneralProperties() {
		return generalProperties;
	}

	/**
	 * <p>
	 *  <jdl:section>
	 * 		<jdl:text lang='it'>Restituisce il valore del campo sequenceList.</jdl:text>
	 * 		<jdl:text lang='en'>Returns the value of sequenceList.</jdl:text>  
	 *  </jdl:section>
	 * </p>
	 *
	 * @return <jdl:section>
	 *         		<jdl:text lang='it'>il valore del campo sequenceList.</jdl:text>
	 *         		<jdl:text lang='en'>the value of sequenceList.</jdl:text> 
	 * 		   </jdl:section>
	 */
	public List getSequenceList() {
		return sequenceList;
	}	
	
}

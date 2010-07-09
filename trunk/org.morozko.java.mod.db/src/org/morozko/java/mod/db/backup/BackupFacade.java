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
 * @(#)BackupFacade.java
 *
 * @project    : org.morozko.java.mod.db
 * @package    : org.morozko.java.mod.db.backup
 * @creation   : 05/set/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.morozko.java.mod.db.backup;

import java.io.File;
import java.sql.Connection;
import java.util.Iterator;

import org.morozko.java.core.log.LogFacade;
import org.morozko.java.core.xml.dom.DOMIO;
import org.morozko.java.mod.db.backup.seq.GenericSequenceReset;
import org.morozko.java.mod.db.sql.ExecFacade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <p></p>
 *
 * @author mfranci
 *
 */
public class BackupFacade {

	public static int backup( Element config ) throws Exception {
		BackupConfig backupConfig = new BackupConfig();
		backupConfig.configure( config );
		return backup( backupConfig );
	}
	
	public static int backup( BackupConfig backupConfig ) throws Exception {
		int result = 0;
		
		if ( backupConfig.deleteFirst() ) {
			int delete = 0;
			for ( int k=backupConfig.getTableList().size()-1; k>=0; k-- ) {
				TableConfig tableConfig = (TableConfig)backupConfig.getTableList().get( k );
				String tableName = tableConfig.getTable();
				String sql = " DELETE FROM "+tableName+" ";
				int currentDelete = ExecFacade.executeUpdate( backupConfig.getFactoryTo() , sql );
				LogFacade.getLog().info( "BackupConfig.backup() Executed delete command : "+sql+" result : "+currentDelete );
				delete+= currentDelete;
			}
			LogFacade.getLog().info( "BackupConfig.backup() Total delete result : "+delete );
		}
		
		Connection from = backupConfig.getFactoryFrom().getConnection();
		Connection to = backupConfig.getFactoryTo().getConnection();		
		
		GenericSequenceReset sequenceReset = new GenericSequenceReset();
		
		Iterator itSequenceList = backupConfig.getSequenceList().iterator();
		while ( itSequenceList.hasNext() ) {
			SequenceConfig sequenceConfig = (SequenceConfig) itSequenceList.next();
			String sequenceName = sequenceConfig.getSequenceName();
			long value = sequenceReset.loadNextValue( from , sequenceName );
			sequenceReset.saveNextValue( to, sequenceName, value );
		}
		
		Iterator itTableList = backupConfig.getTableList().iterator();
		while ( itTableList.hasNext() ) {
			TableConfig tableConfig = (TableConfig)itTableList.next();
			String tableName = tableConfig.getTable();
			String sql = tableConfig.getSelect();
			if ( sql == null ) {
				result+= backupConfig.getTableBackup().backupTable( tableName, from, to);	
			} else {
				result+= backupConfig.getTableBackup().backupTable( tableName, from, to, sql );
			}
		}
		from.close();
		to.close();
		LogFacade.getLog().info( "Backup return code : "+result );
		return result;
	}
	
	public static void main( String[] args ) {
		try {
			String file = args[0];
			
			LogFacade.updateCurrentConfig( "log.level" , "6" );
			
			Document config = DOMIO.loadDOMDoc( new File( file ) );
			
			System.out.println( "Backup result : "+BackupFacade.backup( config.getDocumentElement() ) );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

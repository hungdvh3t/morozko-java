package tool.file;

import java.io.File;

import org.morozko.java.mod.tools.util.args.ArgList;
import org.morozko.java.mod.tools.util.args.ArgUtils;

public class Rename {

	public static void main( String[] args ) {
		try {
			ArgList argList = ArgUtils.parseArgs( args );
			String dir = argList.findArgValue( "dir" );
			String prefix = argList.findArgValue( "prefix" );
			File baseDir = new File( dir );
			String[] files = baseDir.list();
			for ( int k=0; k<files.length; k++ ) {
				String fileName = files[k];
				File currentFile = new File( baseDir, fileName );
				if ( fileName.indexOf( prefix ) != 0 ) {
					File newFile = new File( baseDir, prefix+fileName );
					System.out.println( "rename "+currentFile.getName()+" to "+newFile.getName() );
					currentFile.renameTo( newFile );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

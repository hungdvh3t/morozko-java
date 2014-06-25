package org.morozko.java.mod.tools.mp3.change;

import java.io.File;

import org.farng.mp3.MP3File;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;
import org.morozko.java.mod.tools.util.args.ArgList;
import org.morozko.java.mod.tools.util.args.ArgUtils;

public class ChangeForAndroid {

	public static void main( String[] args ) {
		try {
			ArgList argList = ArgUtils.parseArgs( args );
			File folder = new File( argList.findArgValue( "dir" ) );
			String remove = argList.findArgValue( "remove" );
			boolean removeWhiteSpace = "true".equals( argList.findArgValue( "remove_white_space" ) );
			boolean addTrackNumber = "true".equalsIgnoreCase( argList.findArgValue( "addTrackNumber" ) );
			File[] list = folder.listFiles();
			for ( int k=0; k<list.length; k++ ) {
				File current = list[k];
				if ( current.getName().indexOf( ".mp3" ) != -1 ) {
					System.out.println( "FILE : "+current.getCanonicalPath() );
					// remove from file name
					if ( remove != null && current.getName().indexOf( remove ) != -1 ) {
						String newFileName = current.getName().replace( remove , "" );
						File newFile = new File( folder, newFileName );
						System.out.println( "RENAMING TO : "+newFile.getCanonicalPath() );
						current.renameTo( newFile );
						current = newFilMaggio2014
								
					}
					// remove white spaces
					if ( removeWhiteSpace ) {
						String newFileName = current.getName().replaceAll( " " , "_" );
						File newFile = new File( folder, newFileName );
						System.out.println( "RENAMING TO : "+newFile.getCanonicalPath() );
						current.renameTo( newFile );
						current = newFile;
					}
					// open as mp3
					MP3File mp3 = new MP3File( current );
					
					String use = argList.findArgValue( "use" );
					
					String trackNumber = null;
					String songTitle = null;
					boolean stateChange = false;
					
					if ( "id3v1".equalsIgnoreCase( use ) ) {
						ID3v1 id3v1 = mp3.getID3v1Tag();
						trackNumber = id3v1.getTrackNumberOnAlbum();
						songTitle = id3v1.getSongTitle();
					} else {
						AbstractID3v2 id3v2 = mp3.getID3v2Tag();
						trackNumber = id3v2.getTrackNumberOnAlbum();
						songTitle = id3v2.getSongTitle();
//						// add track number to title
						if ( addTrackNumber ) {
							try {
								trackNumber = trackNumber.trim();
								int tn = Integer.parseInt( trackNumber );
								trackNumber = ("00"+tn);
								trackNumber = trackNumber.substring( trackNumber.length()-2 );
								if ( songTitle.startsWith( trackNumber+" - " ) ) {
									System.out.println( "Song title already starts with track number : "+trackNumber );
								} else {
									String newTitle = trackNumber+" - "+songTitle;
									System.out.println( "adding track number to title : "+newTitle );
									id3v2.setSongTitle( newTitle );
									stateChange = true;	
								}
							} catch (NumberFormatException nfe) {
								System.out.println( "NO VALID track number : "+nfe );
							}						
						}
					}
					
					if ( stateChange ) {
						mp3.save();
					} else {
						System.out.println( "track "+trackNumber+" - "+songTitle );
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

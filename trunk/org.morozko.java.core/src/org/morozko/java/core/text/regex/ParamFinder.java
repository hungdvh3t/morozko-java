package org.morozko.java.core.text.regex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamFinder {

	private static final String PRE = "\\$\\{";
	private static final String POST = "\\}";
	
	public static ParamFinder newFinder() {
		Pattern p = Pattern.compile( PRE+"(.*?)"+POST );
		return new ParamFinder( p );
	}
	
	private Pattern p;
	
	private ParamFinder( Pattern p ) {
		this.p = p;
	}
	
	public Set getParamSet( CharSequence text ) {
		List paramList = this.getParamList( text );
		Set paramSet = new HashSet( paramList );
		return paramSet;
	}
	
	public List getParamList( CharSequence text ) {
		List paramList = new ArrayList();
		Matcher m = p.matcher( text );
		while ( m.find() ) {
			String found = m.group();
			String paramName = found.substring( 2, found.length()-1 );
			paramList.add( paramName );
		}
		return paramList;
	}

	public int count( CharSequence text, String param ) {
		int count = 0;
		List list = this.getParamList( text );
		while ( list.remove( param ) ) {
			count++;
		}
		return count;
	}
	
	public String substitute( CharSequence text, String param, String value ) {
		String result = text.toString(); 
		return result.replaceAll( PRE+param+POST , value );
	}
	
	public String substitute( CharSequence text, Properties params ) {
		String result = text.toString();
		Iterator it = this.getParamList( result ).iterator();
		while ( it.hasNext() ) {
			String current = it.next().toString();
			String sub = params.getProperty( current );
			if ( sub != null ) {
				result = result.replaceAll( PRE+current+POST , sub );	
			}
		}
		return result;
	}
	
}

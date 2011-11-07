package org.morozko.java.mod.pmd.rules.struts;

import java.util.List;

import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.ast.ASTType;
import net.sourceforge.pmd.stat.StatisticalRule;


/**
 * Nome        : PublicAttributesInAction
 * Messaggio   : La action contiene variabili pubbliche 
 * Descrizione : Le action di struts, essendo non thread safe, non dovrebbero contenere variabili locali, in particolare se pubbliche.
 * 
 * @author mttfranci
 *
 */
public class PublicAttributesInAction extends StatisticalRule {

	private boolean isAction( Class c ) {
		boolean ok = false;
		String name = c.getName();
		System.out.println( "CHECK NAME "+name );
		if ( name.equals( "java.lang.Object" ) ) {
			ok = false;
		} else {
			if ( name.equalsIgnoreCase( "org.apache.struts.Action" ) ) {
				ok = true;
			} else {
				isAction( c.getSuperclass() );
			}
		}
		return ok;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Object data) {
		Object result = null;
		Class currentClass = node.getType();
		System.out.println( "CHECK NAME 1 >>> "+currentClass.getName() );
		return null;
	}

//	@Override
//	public Object visit(ASTType node, Object data) {
//		Object result = null;
//		Class currentClass = node.getType();
//		System.out.println( "CHECK NAME>>> "+currentClass.getName() );
////		if ( this.isAction( currentClass ) ) {
////			System.out.println( "IS ACTION! "+currentClass );
////		}
//		return result;
//	}

	public PublicAttributesInAction() {
	}
	
}

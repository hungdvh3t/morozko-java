package org.morozko.java.mod.pmd.rules.struts;

import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.ast.SimpleNode;
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
	
//	private static boolean isAction( Class c ) {
//		boolean ok = false;
//		if ( c != null ) {
//			String name = c.getName();
//			if ( name.equals( "java.lang.Object" ) ) {
//				ok = false;
//			} else {
//				if ( name.equalsIgnoreCase( "org.apache.struts.Action" ) ) {
//					ok = true;
//				} else {
//					isAction( c.getSuperclass() );
//				}
//			}			
//		}
//		return ok;
//	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		try {
			System.out.println( "TEST 1 a "+node );
			System.out.println( "TEST 1 b "+data );
			System.out.println( "TEST 1 c "+node.getType() );
			for ( int k=0; k<node.jjtGetNumChildren(); k++ ) {
				System.out.println( "TEST 1 a "+k+" - "+node.jjtGetChild( k ) );
				try {
					System.out.println( "TEST 2 b "+k+" - "+node.jjtGetChild( k ).getClass().getName() );	
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			try {
				ASTClassOrInterfaceType type = (ASTClassOrInterfaceType) ((SimpleNode) node.jjtGetChild(0)).jjtGetChild(0);
	            Class clazz = type.getType();
	            System.out.println( "TEST c : "+clazz.getName() );
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.visit(node, data);
	}

	public PublicAttributesInAction() {
		System.out.println( "TEST INIT PublicAttributesInAction 2" );
	}
	
}

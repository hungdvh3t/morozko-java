package org.morozko.java.mod.pmd.rules.struts;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.stat.StatisticalRule;

public class AbstractActionRule extends StatisticalRule {

	public static final String ATT_NAME_ISACTION =  "AbstractActionRule.ISACTION";
	
	private static boolean isAction( Class c, int lvl ) {
		boolean ok = false;
		if ( c != null ) {
			String name = c.getName();
			if ( name.equals( "java.lang.Object" ) ) {
				ok = false;
			} else {
				if ( name.equalsIgnoreCase( "org.apache.struts.action.Action" ) ) {
					ok = true;
				} else {
					ok = isAction( c.getSuperclass(), lvl+1 );
				}
			}			
		}
		return ok;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		try {
			Class nodeClass = node.getType();
			if ( isAction( nodeClass, 0 ) ) {
				System.out.println( "IS Action --> "+nodeClass.getName() );
				RuleContext context = (RuleContext)data;
				context.setAttribute( ATT_NAME_ISACTION , "true" );
			} else {
				System.out.println( "NO Action --> "+nodeClass.getName() );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.visit(node, data);
	}

	public boolean checkActionAtt( Object data ) {
		return "true".equalsIgnoreCase( (String)((RuleContext)data).getAttribute( ATT_NAME_ISACTION ));
	}
	
	public AbstractActionRule() {
		System.out.println( "TEST INIT AbstractActionRule 1" );
	}
	
}

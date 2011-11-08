package org.morozko.java.mod.pmd.rules.struts;

import net.sourceforge.pmd.ast.ASTFieldDeclaration;



/**
 * Class       : org.morozko.java.mod.pmd.rules.struts.AttributesInAction
 * Nome        : AttributesInAction
 * Messaggio   : La action contiene variabili pubbliche 
 * Descrizione : Le action di struts, essendo non thread safe, non dovrebbero contenere variabili locali.
 * 
 * @author mttfranci
 *
 */
public class AttributesInAction extends AbstractActionRule {

	@Override
	public Object visit(ASTFieldDeclaration node, Object data) {
		boolean isAction = checkActionAtt( data );
		if ( isAction && !( node.isStatic() && node.isFinal() ) ){
			this.addViolation( data, node );
		}
		return super.visit(node, data);
	}
	
}

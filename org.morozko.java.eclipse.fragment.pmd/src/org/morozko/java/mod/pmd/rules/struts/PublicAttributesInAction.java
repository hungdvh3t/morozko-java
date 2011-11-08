package org.morozko.java.mod.pmd.rules.struts;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTFieldDeclaration;



/**
 * Class       : org.morozko.java.mod.pmd.rules.struts.PublicAttributesInAction
 * Nome        : PublicAttributesInAction
 * Messaggio   : La action contiene variabili pubbliche 
 * Descrizione : Le action di struts, essendo non thread safe, non dovrebbero contenere variabili locali, in particolare se pubbliche.
 * 
 * @author mttfranci
 *
 */
public class PublicAttributesInAction extends AbstractActionRule {
	
	@Override
	public Object visit(ASTFieldDeclaration node, Object data) {
		boolean isAction = checkActionAtt( data );
		if ( isAction && node.isPublic() && !( node.isStatic() && node.isFinal() ) ){
			//RuleContext context = (RuleContext)data;
			this.addViolation( data, node );
		}
		return super.visit(node, data);
	}
	
}

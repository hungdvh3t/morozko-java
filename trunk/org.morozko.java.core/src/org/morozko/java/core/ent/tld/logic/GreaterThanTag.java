/*****************************************************************
<copyright>
	Morozko Java Library org.morozko.java.core.ent 

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
 * @(#)GreaterThanTag.java
 *
 * @project    : org.morozko.java.core.ent
 * @package    : org.morozko.java.core.ent.tld.logic
 * @creation   : 12-mag-2006
 */
package org.morozko.java.core.ent.tld.logic;

import javax.servlet.jsp.JspException;

/**
 * <p>Tag che esegue il codice in esso contenuto solo se di due oggetti che rappresentano un valore numerico
 * il primo � maggiore del secondo.</p>
 *
 *	<p>
 *		Ad esempio il seguente estratto di codice : <br/>
 *		<div class="codejsp"> 
 *			&lt;oilogic:greaterThan name='bean' nameTo='beanTo'&gt;<br/>
 *				TEST
 *			&lt;/oilogic:greaterThan name='bean' nameTo='beanTo'&gt;<br/>
 *		</div>
 *		<br/>
 *		<br/>
 *		E' equivalente a scrivere in una pagina JSP :<br/>
 *		<div class="codejsp">
 *			&lt;%<br/>
 *				&nbsp;&nbsp; Object obj1 = this.pageContext.findAttribute( "bean" );<br/>
 * 				&nbsp;&nbsp; Object obj2 = this.pageContext.findAttribute( "beanTo" );<br/>
 *				&nbsp;&nbsp; if ( Double.valueOf( String.valueOf( obj1 ) ).doubleValue() > Double.valueOf( String.valueOf( obj2 ) ).doubleValue() ) {;<br/>
 *				&nbsp;&nbsp;&nbsp; out.print( "TEST" );<br/>
 *				&nbsp;&nbsp; }<br/>
 *			%&gt;<br/>
 *		</div>
 *	</p>
 *
 * @author Morozko
 * @since JLIB-CORE 1.0
 */
public class GreaterThanTag extends CompareTagHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3154669538849344976L;

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		int result = SKIP_BODY;
		if ( this.compareNumbers()>0 ) {
			result = EVAL_PAGE;
		}
		return result;
	}

}

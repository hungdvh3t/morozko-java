/*****************************************************************
<copyright>
	Morozko Java Library org.morozko.java.mod.message 

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
 * @(#)MessageHandler.java
 *
 * @project    : org.morozko.java.mod.message
 * @package    : org.morozko.java.mod.message
 * @creation   : 5-giu-2006
 */
package org.morozko.java.mod.message;

import java.util.Iterator;

/**
 * <p></p>
 *
 * @author mfranci
 *
 */
public interface MessageHandler {
	
	public String getSubject();

	public MessageAddress getSender();
	
	public MessageAddress getReplyTo();
	
	public MessageAddress[] getToList();
	
	public MessageAddress[] getCcList();
	
	public MessageAddress[] getBccList();
	
	public MessageHeaders getHeaders();
	
	public Iterator getMessageParts();
	
}

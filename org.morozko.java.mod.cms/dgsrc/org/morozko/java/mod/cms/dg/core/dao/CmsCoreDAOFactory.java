/*****************************************************************
<copyright>
	OpenInformatica Java Library org.morozko.java.mod.app.cms 

	Copyright (c) 2006 OpenInformatica

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
 * @(#)CmsCoreDAOFactory.java
 *
 * @project    : OpinfCMS
 * @package    : org.morozko.java.mod.cms.dg.core.dao
 * @creation   : 22/05/2006 11/43/58
 */
package org.morozko.java.mod.cms.dg.core.dao;

import org.morozko.java.mod.cms.dg.CmsDAOFactory;
import org.morozko.java.mod.cms.dg.core.dao.helpers.CmsCoreDAOFactoryHelper;

/**
 * <p>Classe CmsCoreDAOFactory.</p>
 *
 * @author Matteo Franci
 */
public class CmsCoreDAOFactory extends CmsCoreDAOFactoryHelper {

	/**
	 * @param daoFactory
	 */
	public CmsCoreDAOFactory(CmsDAOFactory daoFactory) {
		super(daoFactory);
	}

	private final static long serialVersionUID = 114829103829653L;

}

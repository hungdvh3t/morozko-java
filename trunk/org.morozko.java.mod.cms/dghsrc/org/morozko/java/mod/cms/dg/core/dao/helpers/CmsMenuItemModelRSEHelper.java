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
 * @(#)CmsMenuItemModelRSEHelper.java
 *
 * @project    : OpinfCMS
 * @package    : org.morozko.java.mod.cms.dg.core.dao.helpers
 * @creation   : 25/05/2006 10/55/35
 */
package org.morozko.java.mod.cms.dg.core.dao.helpers;

/**
 * <p>Classe per l' estrazione di oggetti CmsMenuItemModel da un ResultSet.</p>
 *
 * @author Matteo Franci
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morozko.java.mod.cms.dg.core.model.CmsMenuItemModel;

public class CmsMenuItemModelRSEHelper implements org.morozko.java.mod.db.dao.RSExtractor {

	private final static long serialVersionUID = 114854733595362L;

    public Object extractNext(ResultSet rs) throws SQLException {
        CmsMenuItemModel model = new CmsMenuItemModel();
        model.setIdCmsMenuItem( new Long( rs.getLong("id_cms_menu_item" ) ) );
        model.setMenuItemType( new Integer( rs.getInt("menu_item_type") ) );
        model.setIdCmsPage( new Long( rs.getLong("id_cms_page" ) ) );
        model.setDisplay( rs.getString("display") );
        model.setHref( rs.getString("href") );
        model.setTarget( rs.getString("target") );
        model.setIdCmsMenu( new Long( rs.getLong("id_cms_menu" ) ) );
        return model;
    }

}

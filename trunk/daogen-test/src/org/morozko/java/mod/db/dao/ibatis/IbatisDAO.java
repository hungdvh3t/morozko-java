package org.morozko.java.mod.db.dao.ibatis;

import org.morozko.java.mod.db.connect.ConnectionFactory;
import org.morozko.java.mod.db.dao.BasicDAO;
import org.morozko.java.mod.db.dao.BasicDAOFactory;

public class IbatisDAO extends BasicDAO {

	protected IbatisDAO(BasicDAOFactory daoFactory) {
		super(daoFactory);
	}

	public IbatisDAO(ConnectionFactory connectionFactory) {
		super(connectionFactory);
	}
	
}

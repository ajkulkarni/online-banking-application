package org.thothlab.devilsvault.dao.transaction.impl;

import java.util.List;

import javax.sql.DataSource;

import org.thothlab.devilsvault.dao.transaction.TransactionDao;
import org.thothlab.devilsvault.db.model.Transaction;

public class TransactionDaoImpl implements TransactionDao {
	private DataSource dataSource;
	
	public TransactionDaoImpl() {
		// TODO Auto-generated constructor stub
		//Add data source here or make a parameterized constructor and pass it from calling function
		dataSource = null; //TBD
	}

	@Override
	public List<Transaction> getOpenTransactions() {
		// TODO Auto-generated method stub

		return null;
	}
	
	
}

package org.thothlab.devilsvault.dao;

import java.util.List;

import org.thothlab.devilsvault.db.model.Transaction;

public interface TransactionDao {
	public List<Transaction> getOpenTransactions();
}

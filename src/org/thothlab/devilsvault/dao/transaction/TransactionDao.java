package org.thothlab.devilsvault.dao.transaction;

import java.util.List;

import org.thothlab.devilsvault.db.model.Transaction;

public interface TransactionDao {
	public List<Transaction> getOpenTransactions();
}

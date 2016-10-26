package org.thothlab.devilsvault.dao.bankaccount;

import java.util.List;

import javax.sql.DataSource;

import org.thothlab.devilsvault.db.model.BankAccountDB;
import org.thothlab.devilsvault.db.model.CreditAccountDB;

public interface BankAccountDao {
	
	public void setDataSource(DataSource dataSource);
	
	public Boolean save(BankAccountDB bankDetails);
	
	public List<BankAccountDB> getAccountDetailsById(int userId);
}

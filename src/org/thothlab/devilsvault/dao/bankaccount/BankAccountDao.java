package org.thothlab.devilsvault.dao.bankaccount;

import java.util.List;

import javax.sql.DataSource;

import org.thothlab.devilsvault.db.model.BankAccountDB;
import org.thothlab.devilsvault.db.model.Request;

public interface BankAccountDao {
	
	public void setDataSource(DataSource dataSource);
	
	public Boolean save(Request request, String type);
	
	public List<BankAccountDB> getAccountDetailsById(int userId);
}

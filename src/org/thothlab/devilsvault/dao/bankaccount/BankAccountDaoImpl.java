package org.thothlab.devilsvault.dao.bankaccount;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.BankAccountDB;
import org.thothlab.devilsvault.db.model.Request;

@Repository ("bankAccountDao")
public class BankAccountDaoImpl implements BankAccountDao {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
	}

	@Override
	public Boolean save(Request request, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BankAccountDB> getAccountDetailsById(int userId) {
		String query = "SELECT * FROM bank_accounts WHERE external_users_id = " + userId;
		List<BankAccountDB> accountList = jdbcTemplate.query(query, new BeanPropertyRowMapper<BankAccountDB>(BankAccountDB.class));
		return accountList;
	}

}

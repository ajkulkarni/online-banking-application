package org.thothlab.devilsvault.dao.dashboard;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.InternalUser;
import org.thothlab.devilsvault.db.model.PendingRegistration;
import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.Transaction;

@Repository("pendingStatistics")
public class PendingStatisticsDao {
	@SuppressWarnings("unused")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
	}
	
	public List<PendingRegistration> getPendingUserRegistrations() {
		String query = "SELECT * FROM user_pending";
		List<PendingRegistration> user_list = jdbcTemplate.query(query, new BeanPropertyRowMapper(PendingRegistration.class));
		//int  pendingUserRegistrations = userlist.size();
		return user_list;
	}
	
	public List<Request> getPendingInternalRequests() {
		String query = "SELECT * FROM internal_request_pending";
		List<Request> internal_list = jdbcTemplate.query(query, new BeanPropertyRowMapper(Request.class));
		//int  pendingInternalRequests = jdbcTemplate.queryForObject(query, Integer.class);
		return internal_list;
	}
	
	public List<Request> getPendingExternalRequests() {
		String query = "SELECT * FROM external_request_pending";
		List<Request> external_list = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));
		//int  pendingExternalRequests = jdbcTemplate.queryForObject(query, Integer.class);
		return external_list;
	}
	
	public List<Transaction> getPendingTransactions() {
		String query = "SELECT * FROM transaction_pending";
		List<Transaction> transaction_list = jdbcTemplate.query(query, new BeanPropertyRowMapper(Transaction.class));
		//int  pendingTransactions = jdbcTemplate.queryForObject(query, Integer.class);
		return transaction_list;
	}
	
	public HashMap<String, Integer> getPendingStatistics() {
		HashMap<String,Integer> pendingStatisticsMap = new HashMap<String,Integer>();
		pendingStatisticsMap.put("user", getPendingUserRegistrations().size());
		pendingStatisticsMap.put("internal", getPendingInternalRequests().size());
		pendingStatisticsMap.put("external", getPendingExternalRequests().size());
		pendingStatisticsMap.put("transaction", getPendingTransactions().size());
		return pendingStatisticsMap;
	}
	
}

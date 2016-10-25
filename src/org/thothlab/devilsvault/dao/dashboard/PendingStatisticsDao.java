package org.thothlab.devilsvault.dao.dashboard;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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
	
	public List<Request> getPendingInternalRequests(String role) {
		String query = "SELECT * FROM internal_request_pending where role='" + role + "'" ;
		List<Request> internal_list = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));

		return internal_list;
	}
	
	public List<Request> getPendingExternalRequests() {
		String query = "SELECT * FROM external_request_pending where approver='0'";
		List<Request> external_list = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));
		return external_list;
	}
	
	public List<Transaction> getPendingTransactions() {
		String query = "SELECT * FROM transaction_pending";
		List<Transaction> transaction_list = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
		//int  pendingTransactions = jdbcTemplate.queryForObject(query, Integer.class);
		return transaction_list;
	}
	
	public HashMap<String, Integer> getPendingStatistics(String role) {
		HashMap<String,Integer> pendingStatisticsMap = new HashMap<String,Integer>();
		pendingStatisticsMap.put("internal", getPendingInternalRequests(role).size());
		pendingStatisticsMap.put("external", getPendingExternalRequests().size());
		pendingStatisticsMap.put("transaction", getPendingTransactions().size());
		return pendingStatisticsMap;
	}
	
}

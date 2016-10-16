package org.thothlab.devilsvault.dao.log;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Request;

@Repository ("externalLogDao")
public class ExternalLogDaoImpl extends LogDaoImpl {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
	}

	public int createRequest() {
		// TODO Auto-generated method stub
		String query = "insert into external_request_pending (requesterid, request_type, current_value, requested_value, status, approver, description, timestamp_created, timestamp_updated) values (?,?,?,?,?,?,?,?,?)";
		int rowsAffected = jdbcTemplate.update(query, "1", "phone change", "12345", "54321","pending", "ajay", "Chnage phone number","2016-10-10","2016-10-10");
	    return rowsAffected;
	}
	
	public List<Request> getAllPending() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM external_request_pending";
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper(Request.class));
		return requestList;
	}
	
	public List<Request> getAllCompleted() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM external_request_completed";
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper(Request.class));
		return requestList;
	}
	
	@Override
	public Boolean save(Request request, String type) {
		return super.save(request, type);
	}

}

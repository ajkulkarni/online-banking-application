package org.thothlab.devilsvault.dao.request;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Request;

@Repository ("internalRequestDao")
public class InternalRequestDaoImpl extends RequestDaoImpl {
	
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
		String query = "insert into internal_request_pending (requesterid, request_type, current_value, requested_value, status, approver, description, timestamp_created, timestamp_updated) values (?,?,?,?,?,?,?,?,?)";
		int rowsAffected = jdbcTemplate.update(query, "1", "phone change", "12345", "54321","pending", "ajay", "Chnage phone number","2016-10-10","2016-10-10");
	    return rowsAffected;
	}
	
	public List<Request> getAllPending() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM internal_request_pending";
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper(Request.class));
		return requestList;
	}
	
	public List<Request> getAllCompleted() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM internal_request_completed";
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper(Request.class));
		return requestList;
	}
	
	@Override
	public Boolean save(Request request, String type) {
		return super.save(request, type);
	}
	
	@Override
	public Request getById(int id) {
		String query = "SELECT * FROM internal_request_pending WHERE id = "+id;
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper(Request.class));
		if(requestList.size() == 0)
			return null;
		return requestList.get(0);
	}
	
	@Override
	public Request getByUserId(int requesterid) {
		String query = "SELECT * FROM internal_request_pending WHERE requesterid = "+requesterid;
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper(Request.class));
		if(requestList.size() == 0)
			return null;
		return requestList.get(0);
	}	

}

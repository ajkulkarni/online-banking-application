package org.thothlab.devilsvault.dao.request.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.request.ExternalRequestDao;

@Repository ("externalRequestDao")
public class ExternalRequestDaoImpl extends RequestDaoImpl implements ExternalRequestDao {
	
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

	@Override
	public int createRequest() {
		// TODO Auto-generated method stub
		String query = "insert into internal_request (requesterid, request_type, current_value, requested_value, status, description, timestamp_created, timestamp_updated, approver) values (?,?,?,?,?,?,?,?,?)";
		int rowsAffected = jdbcTemplate.update(query, "1", "phone change", "12345", "54321", "pending", "Chnage phone number","2016-10-10","2016-10-10","deepesh");
	    return rowsAffected;
	}

}

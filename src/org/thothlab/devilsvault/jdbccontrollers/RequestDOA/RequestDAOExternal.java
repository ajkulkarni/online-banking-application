package org.thothlab.devilsvault.jdbccontrollers.RequestDOA;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository ("requestDAOExternal")
public class RequestDAOExternal extends RequestDAOImpl{
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void createRequest(){
		String query = "insert into internal_request (requesterid, request_type, current_value, requested_value, status, description, timestamp_created, timestamp_updated, approver) values (?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(query, "1", "phone change", "12345", "54321", "pending", "Chnage phone number","2016-10-10","2016-10-10","deepesh");
	    return;
	}
}

package org.thothlab.devilsvault.jdbccontrollers.RequestDOA;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.jdbccontrollers.model.Request;

public class RequestMapper implements RowMapper<Request> {
	
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
		Request req = new Request();
		req.setId(rs.getInt("id"));
		req.setRequest_type(rs.getString("request_type"));
		req.setCurrent_value(rs.getString("current_value"));
		req.setRequested_value(rs.getString("requested_value"));
		req.setStatus(rs.getString("status"));
		req.setTimestamp_created(rs.getDate("timestamp_created"));
		req.setApprover(rs.getString("approver"));
		return req;
	}
}

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
		
		return req;
	}
}

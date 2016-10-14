package org.thothlab.devilsvault.dao.request.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.thothlab.devilsvault.dao.request.RequestMapperDao;
import org.thothlab.devilsvault.db.model.Request;

public class RequestMapperDaoImpl implements RequestMapperDao{

	@Override
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException{
		// TODO Auto-generated method stub
		Request request = new Request();
		request.setId(rs.getInt("id"));
		request.setRequest_type(rs.getString("request_type"));
		request.setCurrent_value(rs.getString("current_value"));
		request.setRequested_value(rs.getString("requested_value"));
		request.setStatus(rs.getString("status"));
		request.setTimestamp_created(rs.getDate("timestamp_created"));
		request.setApprover(rs.getString("approver"));
		return request;
	}

}

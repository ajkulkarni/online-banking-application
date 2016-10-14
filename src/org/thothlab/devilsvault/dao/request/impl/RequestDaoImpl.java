package org.thothlab.devilsvault.dao.request.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thothlab.devilsvault.dao.request.RequestDao;
import org.thothlab.devilsvault.db.model.Request;

public class RequestDaoImpl implements RequestDao{
	private DataSource dataSource;
	@SuppressWarnings("unused")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub	
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Boolean save(Request request) {
		// TODO Auto-generated method stub
		String query = "insert into Request (requesterid, request_type, current_value, requested_value, status, description, timestamp_created, timestamp_updated) values (?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, request.getRequesterid());
			ps.setString(2, request.getRequest_type());
			ps.setString(3, request.getCurrent_value());
			ps.setString(4, request.getRequested_value());
			ps.setString(5, request.getStatus());
			ps.setString(6, request.getDescription());
			ps.setDate(7, request.getTimestamp_created());
			ps.setDate(8, request.getTimestamp_updated());
			int out = ps.executeUpdate();
			if(out !=0){
				return true;
			}else return false;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	@Override
	public Request getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update(Request employee) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Request> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}

package org.thothlab.devilsvault.dao.request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thothlab.devilsvault.db.model.Request;

public class RequestDaoImpl implements RequestDao{
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub	
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Boolean save(Request request, String type) {
		// TODO Auto-generated method stub
		String query = "insert into "+type+"_request_completed (requesterid, request_type, current_value, requested_value, status, approver, description, timestamp_created, timestamp_updated) values (?,?,?,?,?,?,?,?,?)";
		
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
			ps.setString(6, request.getApprover());
			ps.setString(7, request.getDescription());
			ps.setDate(8, request.getTimestamp_created());
			ps.setDate(9, request.getTimestamp_updated());
			System.out.println(ps);
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
}

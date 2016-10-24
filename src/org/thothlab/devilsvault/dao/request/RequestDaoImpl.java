package org.thothlab.devilsvault.dao.request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
		String query = "insert into "+type+"_request_completed (id,requesterid, request_type, current_value, requested_value, status, approver, description, timestamp_created, timestamp_updated) values (?,?,?,?,?,?,?,?,?,?)";
		
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, request.getId());
			ps.setInt(2, request.getRequesterid());
			ps.setString(3, request.getRequest_type());
			ps.setString(4, request.getCurrent_value());
			ps.setString(5, request.getRequested_value());
			ps.setString(6, request.getStatus());
			ps.setString(7, request.getApprover());
			ps.setString(8, request.getDescription());
			ps.setDate(9, request.getTimestamp_created());
			ps.setDate(10, request.getTimestamp_updated());
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
	public List<Request> getById(int id, String status) {
		// TODO Auto-generated method stub
		return null;
	}
	public void update(String table,String set,String newValue,String where,String oldValue) {
		// TODO Auto-generated method stub
		String sql = "UPDATE " + table +" SET "+ set + " = '" + newValue +
				"' WHERE " + where + " = '" + oldValue + "'"  ;
		jdbcTemplate.update(sql);
	}
	@Override
	public void deleteById(int id, String type) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM "+type+"_request_pending WHERE id ="+id;
		jdbcTemplate.execute(sql);
	}
	@Override
	public List<Request> getByUserId(int requesterid, String status) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void approveRequest(int id, String type, int approver) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM "+type+"_request_pending WHERE id ="+id;
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));
		Request request = requestList.get(0);
		request.setStatus("approved");
		request.setApprover(Integer.toString(approver));
		String table;
		if(type.equalsIgnoreCase("internal"))
			table ="internal_user";
		else
			table = "external_users";
		update(table,request.getRequest_type(),request.getRequested_value(),"id",Integer.toString(request.getRequesterid()));
		save(request, type);
		deleteById(id, type);
	}
	
	@Override
	public void rejectRequest(int id, String type, int approver) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM "+type+"_request_pending WHERE id ="+id;
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));
		Request request = requestList.get(0);
		request.setStatus("rejected");
		request.setApprover(Integer.toString(approver));
		save(request, type);
		deleteById(id, type);
	}
	
	public Boolean saveRaisedRequest(Request request, String type) {
		// TODO Auto-generated method stub
		String query = "insert into "+ type +" (id,requesterid, request_type, current_value, requested_value, status, approver, description, timestamp_created, timestamp_updated,role) values (?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, request.getId());
			ps.setInt(2, request.getRequesterid());
			ps.setString(3, request.getRequest_type());
			ps.setString(4, request.getCurrent_value());
			ps.setString(5, request.getRequested_value());
			ps.setString(6, request.getStatus());
			ps.setString(7, request.getApprover());
			ps.setString(8, request.getDescription());
			ps.setDate(9, request.getTimestamp_created());
			ps.setDate(10, request.getTimestamp_updated());
			ps.setString(11, request.getRole());
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
}

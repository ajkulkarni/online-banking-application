package org.thothlab.devilsvault.jdbccontrollers.RequestDOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thothlab.devilsvault.jdbccontrollers.model.Request;

public class RequestDAOImpl {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Boolean save(Request request) {
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

	public Request getById(int id) {
//		String query = "select name, role from Employee where id = ?";
		Request emp = null;
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try{
//			con = dataSource.getConnection();
//			ps = con.prepareStatement(query);
//			ps.setInt(1, id);
//			rs = ps.executeQuery();
//			if(rs.next()){
//				emp = new Request();
//				System.out.println("Employee Found::"+emp);
//			}else{
//				System.out.println("No Employee found with id="+id);
//			}
//		}catch(SQLException e){
//			e.printStackTrace();
//		}finally{
//			try {
//				rs.close();
//				ps.close();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		return emp;
	}

	public void update(Request employee) {
//		String query = "update Employee set name=?, role=? where id=?";
//		Connection con = null;
//		PreparedStatement ps = null;
//		try{
//			con = dataSource.getConnection();
//			ps = con.prepareStatement(query);
//			ps.setInt(3, employee.getId());
//			int out = ps.executeUpdate();
//			if(out !=0){
//				System.out.println("Employee updated with id="+employee.getId());
//			}else System.out.println("No Employee found with id="+employee.getId());
//		}catch(SQLException e){
//			e.printStackTrace();
//		}finally{
//			try {
//				ps.close();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public void deleteById(int id) {
//		String query = "delete from Employee where id=?";
//		Connection con = null;
//		PreparedStatement ps = null;
//		try{
//			con = dataSource.getConnection();
//			ps = con.prepareStatement(query);
//			ps.setInt(1, id);
//			int out = ps.executeUpdate();
//			if(out !=0){
//				System.out.println("Employee deleted with id="+id);
//			}else System.out.println("No Employee found with id="+id);
//		}catch(SQLException e){
//			e.printStackTrace();
//		}finally{
//			try {
//				ps.close();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public List<Request> getAll() {
		String query = "select * from internal_request";
		List<Request> requestList = jdbcTemplate.query(query, 
                new RequestMapper());
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try{
//			con = dataSource.getConnection();
//			ps = con.prepareStatement(query);
//			rs = ps.executeQuery();
//			while(rs.next()){
//				Request req = new Request();
//				req.setId(rs.getInt("id"));
//				req.setRequest_type(rs.getString("request_type"));
//				req.setCurrent_value(rs.getString("current_value"));
//				req.setRequested_value(rs.getString("requested_value"));
//				req.setStatus(rs.getString("status"));
//				req.setTimestamp_created(rs.getDate("timestamp_created"));
//				req.setApprover(rs.getString("approver"));
//				requestList.add(req);
//			}
//		}catch(SQLException e){
//			e.printStackTrace();
//		}finally{
//			try {
//				rs.close();
//				ps.close();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		return requestList;
	}

}

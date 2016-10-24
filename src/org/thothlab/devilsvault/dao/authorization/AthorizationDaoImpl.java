package org.thothlab.devilsvault.dao.authorization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Authorization;

@Repository ("AuthorizationDao")
public class AthorizationDaoImpl implements AthorizationDao{
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
	public List<Authorization> getAllPendingAuthorization(int id) {
		String query = "SELECT * FROM authorization_pending WHERE internal_userID = "+id+"";
		List<Authorization> authorizationList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		return authorizationList;
	}
	
	public List<Authorization> getAllPendingAuthorizationManager() {
		String query = "SELECT * FROM authorization_pending";
		List<Authorization> authorizationList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		return authorizationList;
	}
	
	public List<Authorization> getAllCompleteAuthorizationManager() {
		String query = "SELECT * FROM authorization_completed";
		List<Authorization> authorizationList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		return authorizationList;
	}

	@Override
	public List<Authorization> getAllCompleteAuthorization(int id) {
		String query = "SELECT * FROM authorization_completed WHERE internal_userID = "+id+"";
		List<Authorization> authorizationList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		return authorizationList;
	}
	@Override
	public boolean isExist(int internal_userID, int external_userID, String auth_Type) {
		// TODO Auto-generated method stub
		String query_completed = "SELECT * FROM authorization_completed WHERE internal_userID = "+internal_userID+" AND external_userID = "+external_userID+" AND auth_Type = '"+auth_Type+"'";
		String query_pending = "SELECT * FROM authorization_pending WHERE internal_userID = "+internal_userID+" AND external_userID = "+external_userID+" AND auth_Type = '"+auth_Type+"'";
		List<Authorization> completeList = jdbcTemplate.query(query_completed, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		List<Authorization> pendingList = jdbcTemplate.query(query_pending, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		if(completeList.size() != 0 || pendingList.size() != 0) return true;
		else return false;
	}
	@Override
	public Boolean save(int internal_userID, int external_userID, String auth_Type) {
		// TODO Auto-generated method stub
		String query = "insert into authorization_pending(internal_userID, external_userID, auth_Type) values (?,?,?)";
		
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, internal_userID);
			ps.setInt(2, external_userID);
			ps.setString(3, auth_Type);
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
	public boolean deleteByID(int auth_id, String table) {
		String query = "DELETE FROM "+table+" WHERE auth_id = "+auth_id+"";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
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
	public boolean addByID(Authorization request) {
		String query = "insert into authorization_completed(internal_userID, external_userID, auth_Type) values (?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, request.getInternal_userID());
			ps.setInt(2, request.getExternal_userID());
			ps.setString(3, request.getAuth_Type());
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
	public Authorization getByID(int auth_id) {
		String query = "SELECT * FROM authorization_pending WHERE auth_id = "+auth_id+"";
		List<Authorization> authorizationList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		return authorizationList.get(0);
	}

	

}
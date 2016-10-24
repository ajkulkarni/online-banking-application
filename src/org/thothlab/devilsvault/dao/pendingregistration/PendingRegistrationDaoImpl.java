package org.thothlab.devilsvault.dao.pendingregistration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.PendingRegistration;

@Repository("PendingRegistrationDao")
public class PendingRegistrationDaoImpl implements PendingRegistrationDao {
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
	public PendingRegistration getById(int id, String table) {
		String query = "SELECT * FROM user_pending id = "+id+"";
		List<PendingRegistration> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper<PendingRegistration>(PendingRegistration.class));
		return requestList.get(0);
	}


	@Override
	public boolean deleteById(int id) {
		String query = "DELETE FROM user_pending WHERE id = "+id+"";
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
	public Boolean save(PendingRegistration user, String type) {
		String query = "insert into user_pending (firstName, lastName, userEmail, userSsn, userPassword, country, city, street, house, state, pincode, userPhonenumber, timestamp_created) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUserEmail());
			ps.setString(4, user.getUserSsn());
			ps.setString(5, user.getUserPassword());
			ps.setString(6, user.getCountry());
			ps.setString(7, user.getCity());
			ps.setString(8, user.getStreet());
			ps.setString(9, user.getHouse());
			ps.setString(10, user.getState());
			ps.setInt(11, user.getPincode());
			ps.setString(12, user.getUserPhonenumber());
			ps.setDate(13, (Date) user.getTimestamp_created());
			
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
	public boolean update(PendingRegistration user, String type) {
		String query = "UPDATE user_pending SET firstName = "+user.getFirstName()+", lastName = "+user.getLastName()+", userEmail = "+user.getUserEmail()+", userSsn = "+user.getUserSsn()+", userPassword = "+user.getUserPassword()+", country = "+user.getCountry()+", city = "+user.getCity()+", street = "+user.getStreet()+", house = "+user.getHouse()+", state = "+user.getState()+", pincode = "+user.getPincode()+", userPhonenumber = "+user.getUserPhonenumber()+", timeStamp_created = "+user.getTimestamp_created()+" WHERE id = ";
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
	public List<PendingRegistration> getAllPending() {
		String query = "SELECT * FROM user_pending";
		List<PendingRegistration> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper<PendingRegistration>(PendingRegistration.class));
		return requestList;
	}

}
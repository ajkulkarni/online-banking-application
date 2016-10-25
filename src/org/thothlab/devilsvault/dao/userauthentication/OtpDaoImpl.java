package org.thothlab.devilsvault.dao.userauthentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.OTP;
import org.thothlab.devilsvault.db.model.UserAuthentication;
@Repository ("OtpDaoImpl")
public class OtpDaoImpl implements OtpDao{
	
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
	public String verifyEmail(String email) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM users WHERE username = '"+email+"' LIMIT 1";
		List<UserAuthentication> userList = jdbcTemplate.query(query, new BeanPropertyRowMapper<UserAuthentication>(UserAuthentication.class));
		if (userList.size() == 0)
			return "Invalid User";
		if(userList.get(0).getAccountNonLocked() == 0)
			return "Account Locked";
		//Make a call to process OTP
		return processOTP(userList.get(0).getUsername());
	}
	
	@Override
	public String processOTP(String email) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM otp_table WHERE userEmail = '"+email+"' LIMIT 1";
		List<OTP> otpList = jdbcTemplate.query(query, new BeanPropertyRowMapper<OTP>(OTP.class));
		String otp = "";
		if(otpList.size() == 0) {
			otp = generateOTP(email);
			String sql = "INSERT INTO otp_table (userEmail,otp,timestamp,attempts) VALUES (?,?,?,?)";
			Connection con = null;
			PreparedStatement ps = null;
			try{
				con = dataSource.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ps.setString(2, otp);
				ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				ps.setInt(4, 1);
				ps.executeUpdate();
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
			return "Email Verified";
		}
		if(otpList.get(0).getAttempts() >= 3) {
			String sql = "UPDATE users SET otpNonLocked = "+0+" WHERE username = '"+email + "'";
			jdbcTemplate.update(sql);
			sql = "DELETE FROM otp_table WHERE userEmail = '"+email + "'";
			jdbcTemplate.execute(sql);
			return "Account Locked";
		}
		otp = generateOTP(email);
		query = "UPDATE otp_table SET attempts = "+(otpList.get(0).getAttempts()+1)+
				", timestamp = '"+(new Timestamp(System.currentTimeMillis()))+"', otp = "+otp+" WHERE userEmail = '"+email  + "'";;
		jdbcTemplate.update(query);
		return "Email Verified";
	}

	@Override
	public String updatePassword(String email, String password) {
		// TODO Auto-generated method stub
		String sql="UPDATE users SET password = "+password+" WHERE username = '" + email  + "'";;
		jdbcTemplate.update(sql);
		return "Password Changed";
	}

	@Override
	public String generateOTP(String email) {
		// TODO Auto-generated method stub
		OtpDelivery otpDelivery = new OtpDelivery();
		return otpDelivery.send(email);
	}

	@Override
	public String verifyOTP(String otp, String email) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM otp_table WHERE userEmail = '"+email+"' LIMIT 1";
		List<OTP> otpList = jdbcTemplate.query(query, new BeanPropertyRowMapper<OTP>(OTP.class));
		if(otpList.size() == 0)
			return "Error in verifying OTP";
		long databaseDate = otpList.get(0).getTimestamp().getTime();
		long currentDate = (new Timestamp(System.currentTimeMillis())).getTime();
		if(otpList.get(0).getOtp().equals(otp) == false)
			return "Incorrect OTP";
		if((currentDate - databaseDate > 180000))
			return "OTP Expired";
		String sql = "DELETE FROM otp_table WHERE userEmail = '"+email + "'";
		jdbcTemplate.execute(sql);
		return "OTP Validated";
	}

}
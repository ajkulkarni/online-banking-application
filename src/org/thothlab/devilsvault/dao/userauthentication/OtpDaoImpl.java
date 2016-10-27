package org.thothlab.devilsvault.dao.userauthentication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.OTP;
import org.thothlab.devilsvault.db.model.UserAuthentication;

@Repository("OtpDaoImpl")
public class OtpDaoImpl implements OtpDao {

	String role;
	int userID;
	String username;

	public void setGlobals(HttpServletRequest request) {
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");

	}

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
		try {
			InternetAddress emailAddress = new InternetAddress(email);
			emailAddress.validate();
		} catch (AddressException ae) {
			return "Invalid User";
		}
		String query = "SELECT * FROM users WHERE username = '" + email + "' LIMIT 1";
		List<UserAuthentication> userList = jdbcTemplate.query(query,
				new BeanPropertyRowMapper<UserAuthentication>(UserAuthentication.class));
		if (userList.size() == 0)
			return "Invalid User";
		if (userList.get(0).getOtpNonLocked() == 0)
			return "Cannot generate more OTP, Contact Support !!";
		// Make a call to process OTP
		return processOTP(userList.get(0).getUsername());
	}

	@Override
	public String processOTP(String email) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM otp_table WHERE userEmail = '" + email + "' LIMIT 1";
		List<OTP> otpList = jdbcTemplate.query(query, new BeanPropertyRowMapper<OTP>(OTP.class));
		String otp = "";
		if (otpList.size() == 0) {
			otp = generateOTP(email);
			String sql = "INSERT INTO otp_table (userEmail,otp,timestamp,attempts) VALUES (?,?,?,?)";
			Connection con = null;
			PreparedStatement ps = null;
			try {
				con = dataSource.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ps.setString(2, otp);
				ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				ps.setInt(4, 1);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return "User Verified";
		}
		if (otpList.get(0).getAttempts() >= 3) {
			String sql = "UPDATE users SET otpNonLocked = " + 0 + " WHERE username = '" + email + "'";
			jdbcTemplate.update(sql);
			sql = "DELETE FROM otp_table WHERE userEmail = '" + email + "'";
			jdbcTemplate.execute(sql);
			return "Account Locked";
		}
		otp = generateOTP(email);
		query = "UPDATE otp_table SET attempts = " + (otpList.get(0).getAttempts() + 1) + ", timestamp = '"
				+ (new Timestamp(System.currentTimeMillis())) + "', otp = " + otp + " WHERE userEmail = '" + email
				+ "'";
		;
		jdbcTemplate.update(query);
		return "User Verified";
	}

	@Override
	public String updatePassword(String email, String password) {
		// TODO Auto-generated method stub
		String sql = "UPDATE users SET password = " + password + " WHERE username = '" + email + "'";
		;
		jdbcTemplate.update(sql);
		return "Password Changed";
	}

	@Override
	public String generateOTP(String email) {
		// TODO Auto-generated method stub
		OTPDelivery otpDelivery = new OTPDelivery();
		return otpDelivery.send(email);
	}

	@Override
	public String verifyOTP(String otp, String email) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM otp_table WHERE userEmail = '" + email + "' LIMIT 1";
		List<OTP> otpList = jdbcTemplate.query(query, new BeanPropertyRowMapper<OTP>(OTP.class));
		if (otpList.size() == 0)
			return "Error in verifying OTP";
		long databaseDate = otpList.get(0).getTimestamp().getTime();
		long currentDate = (new Timestamp(System.currentTimeMillis())).getTime();
		if (otpList.get(0).getOtp().equals(otp) == false)
			return "Incorrect OTP";
		if ((currentDate - databaseDate > 180000))
			return "OTP Expired";
		String sql = "DELETE FROM otp_table WHERE userEmail = '" + email + "'";
		jdbcTemplate.execute(sql);
		return "OTP Validated";
	}

	@Override
	public String getEmailFromPayerID(int payerid) {
		// TODO Auto-generated method stub
		String sql = "SELECT email FROM external_users WHERE id =" + username + " LIMIT 1";
		List<Customer> customerList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Customer>(Customer.class));
		if (customerList.size() == 0)
			return "User Not Found";
		return customerList.get(0).getEmail();
	}

	@Override
	public String getOTPFromEmail(String email) {
		// TODO Auto-generated method stub
		String sql = "SELECT otp FROM otp_table WHERE userEmail ='" + email + "' LIMIT 1";
		List<OTP> otpList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<OTP>(OTP.class));
		if (otpList.size() == 0)
			return "OTP Not Found";
		return otpList.get(0).getUserEmail();
	}

	@Override
	public void sendEmailToUser(String email, String payeeid, BigDecimal amount) {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		try {
			prop.load(OtpDaoImpl.class.getClassLoader().getResourceAsStream("smtp.properties"));	
			
		} catch(FileNotFoundException fne) {
			fne.printStackTrace();
			return;
		} catch(IOException ioe) {
			ioe.printStackTrace();
			return;
		}

		final String username = prop.getProperty("username");
		final String password = prop.getProperty("password");
		prop= new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("securebanking.ss4@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("A critical transaction has been initiated");
			{
				message.setText("Transfer amount to: " + payeeid + "\n\nAmount: " + amount);
			}
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
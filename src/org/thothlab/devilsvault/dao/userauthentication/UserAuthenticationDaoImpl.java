package org.thothlab.devilsvault.dao.userauthentication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.customer.CustomerAccountsDAO;
import org.thothlab.devilsvault.dao.employee.InternalUserDaoImpl;
import org.thothlab.devilsvault.dao.employee.Validator;
import org.thothlab.devilsvault.db.model.UserAuthentication;

@Repository("userAuthenticationDao")
public class UserAuthenticationDaoImpl implements UserAuthenticationDao {

	@SuppressWarnings("unused")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void updatePassword(String newPassword, String username) {
		String sql = "UPDATE users set password ='" + newPassword + "' where username ='" + username + "';";
		jdbcTemplate.update(sql);
	}

	@Override
	public String changePassword(String oldPassword, String newPassword, String confirmPassword, Integer userID,
			String role) {
		String message = "";
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		Validator validate = ctx.getBean("Validator", Validator.class);
		if (newPassword.equals(confirmPassword)) {
			if (validate.validatePassword(newPassword)) {
				CustomerAccountsDAO customeraccDao = ctx.getBean("CustomerAccountsDAO", CustomerAccountsDAO.class);
				InternalUserDaoImpl internaluserdao = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
				String email;
				if (role.equals("ROLE_CUSTOMER") || role.equals("ROLE_MERCHANT")) {
					email = customeraccDao.getEmailID(userID);
				} else {
					email = internaluserdao.getEmailID(userID);
				}
				UserAuthentication userdetails = getUserDetails(email);
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				if (passwordEncoder.matches(oldPassword, userdetails.getPassword())) {
					updatePassword(passwordEncoder.encode(newPassword), email);
					message = "Password changed successfully";
				} else {
					message = "Old password entered is incorrect";
				}
			} else {
				message = "Password not meeting minimum criteria";
			}

		} else {
			message = "confirm password doesn't match with the new password";
		}
		ctx.close();
		return message;
	}

	@Override
	public UserAuthentication getUserDetails(String email) {
		String query = "SELECT * FROM users where username ='" + email + "';";
		List<UserAuthentication> userDetails = jdbcTemplate.query(query,
				new BeanPropertyRowMapper<UserAuthentication>(UserAuthentication.class));
		return userDetails.get(0);
	}

	public Boolean validateuserDetails(String username, BigInteger phone, String table) {
		String queryInternal = "SELECT COUNT(*) FROM " + table + " WHERE email ='" + username + "' OR phone='"
				+ phone.toString() + "';";
		Integer countinternal = jdbcTemplate.queryForObject(queryInternal, Integer.class);
		String queryUser = "SELECT COUNT(*) FROM users WHERE username ='" + username + "';";
		Integer countuser = jdbcTemplate.queryForObject(queryUser, Integer.class);
		if (countinternal > 0 || countuser > 0) {
			return false;
		}
		return true;
	}

	public HashMap<String, String> randomPasswordGenerator() {
		HashMap<String, String> passwords = new HashMap<String, String>();
		Random rand = new Random();
		Long rawPassword = (long) (rand.nextInt(999999 - 100000) + 100000);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(Long.toString(rawPassword));
		passwords.put("rawPassword", Long.toString(rawPassword));
		passwords.put("hashedPassword", hashedPassword);
		return passwords;

	}

	public UserAuthentication setNewUsers(String username, String password, String role) {
		UserAuthentication userdetails = new UserAuthentication();
		userdetails.setUsername(username);
		userdetails.setPassword(password);
		userdetails.setRole(role);
		userdetails.setAccountNonExpired(1);
		userdetails.setAccountNonLocked(1);
		userdetails.setCredentialsNonExpired(1);
		userdetails.setEnabled(1);
		userdetails.setOtpNonLocked(1);
		return userdetails;
	}

	@Override
	public Boolean save(UserAuthentication userdetails) {
		String query = "INSERT INTO users(username,password,enabled,role,accountNonExpired,accountNonLocked,credentialsNonExpired,otpNonLocked)VALUES(?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, userdetails.getUsername());
			ps.setString(2, userdetails.getPassword());
			ps.setInt(3, userdetails.getEnabled());
			ps.setString(4, userdetails.getRole());
			ps.setInt(5, userdetails.getAccountNonExpired());
			ps.setInt(6, userdetails.getAccountNonLocked());
			ps.setInt(7, userdetails.getCredentialsNonExpired());
			ps.setInt(8, userdetails.getOtpNonLocked());
			int out = ps.executeUpdate();
			if (out != 0) {
				return true;
			} else
				return false;
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
		return false;
	}

	public String changeForgotPassword(String newPassword, String confirmPassword,String username) {
        String message ="";
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        Validator validate = ctx.getBean("Validator",Validator.class);
        if(newPassword.equals(confirmPassword))
        {
        	if(validate.validatePassword(newPassword))
        	{
        		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                 updatePassword(passwordEncoder.encode(newPassword), username);
                 message = "Password changed successfully";
        	}
        	else
        	{
        		message = "Password not meeting minimum criteria!!";
        	}
        }
        else
        {
            message = "confirm password doesn't match with the new password";
        }
        ctx.close();
        return message;
    }
	
	public void sendEmailToUser(String email, String pass) {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		try {
			prop.load(OtpDaoImpl.class.getClassLoader().getResourceAsStream("smtp.properties"));

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
			return;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		}

		final String username = prop.getProperty("username");
		final String password = prop.getProperty("password");
		prop = new Properties();
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
			message.setSubject("Welcome to Devils Vault!");
			{
				message.setText("Your Username: " + email + "\n\nPassword : " + pass
						+ "\n\nPlease do not share this password with anyone");
			}
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}

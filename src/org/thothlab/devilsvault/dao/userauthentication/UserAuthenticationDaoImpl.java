package org.thothlab.devilsvault.dao.userauthentication;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.employee.InternalUserDaoImpl;
import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.UserAuthentication;

@Repository ("userAuthenticationDao")
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

	@Override
	public Boolean save(Request request, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	public void updatePassword(String newPassword,String username)
	{
		String sql = "UPDATE users set password ='" 
	+ newPassword + "' where username ='" + username + "';"; 
		jdbcTemplate.update(sql);
	}

	@Override
	public String changePassword(String oldPassword, String newPassword, String confirmPassword, Integer userID) {
		String message ="";
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		if(newPassword.equals(confirmPassword))
		{
			InternalUserDaoImpl internaluserdao = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
			String email = internaluserdao.getEmailID(userID);
			UserAuthentication userdetails = getUserDetails(email);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(passwordEncoder.matches(oldPassword,userdetails.getPassword()))
			{
				updatePassword(passwordEncoder.encode(newPassword), email);
				message = "Password changed successfully";
			}
			else
			{
				message = "Old password entered is incorrect";
			}
		}
		else
		{
			message = "confirm password doesn't match with the new password";
		}
		ctx.close();
		return message;
	}
	
	@Override
	public UserAuthentication getUserDetails(String email) {
		String query = "SELECT * FROM users where username ='" + email + "';";
		List<UserAuthentication> userDetails = jdbcTemplate.query(query, new BeanPropertyRowMapper<UserAuthentication>(UserAuthentication.class));
		return userDetails.get(0);
	}

}

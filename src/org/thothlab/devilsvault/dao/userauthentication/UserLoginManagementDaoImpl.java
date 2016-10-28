package org.thothlab.devilsvault.dao.userauthentication;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.UserAuthentication;

@Repository ("UserLoginManagementDao")
public class UserLoginManagementDaoImpl {
	
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings("unused")
	private DataSource dataSource;
	@Autowired
	public void setdataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<UserAuthentication> getLockedUsers(List <String> roles) {
		String query = "SELECT *from users where (";
        for(int i =0;i < roles.size();i++)
        {
            if(i < roles.size() - 1)
                query += "role = '" + roles.get(i) + "' OR ";
            else
                query += "role = '" + roles.get(i) + "')";
        }
        query += " AND accountNonLocked = 0;";
        List<UserAuthentication> userDetails = jdbcTemplate.query(query, new BeanPropertyRowMapper<UserAuthentication>(UserAuthentication.class));
        return userDetails;
	}
	
	public List<UserAuthentication> getOtpLockedUsers(List <String> roles) {
		String query = "SELECT *from users where (";
        for(int i =0;i < roles.size();i++)
        {
            if(i < roles.size() - 1)
                query += "role = '" + roles.get(i) + "' OR ";
            else
                query += "role = '" + roles.get(i) + "')";
        }
        query += " AND otpNonLocked = 0;";
        List<UserAuthentication> userDetails = jdbcTemplate.query(query, new BeanPropertyRowMapper<UserAuthentication>(UserAuthentication.class));
        return userDetails;
	}
	public Boolean unlockUserAccount(String username,String requestType)
	{
		String query = "UPDATE users set " + requestType.toLowerCase() +"NonLocked = '1' where username='" + username + "';";
		String query1 = "DELETE FROM user_attempts WHERE username='" + username + "'";
		jdbcTemplate.update(query);
		jdbcTemplate.update(query1);
		return null;
	}
	        
}
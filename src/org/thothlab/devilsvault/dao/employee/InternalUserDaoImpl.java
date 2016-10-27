package org.thothlab.devilsvault.dao.employee;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.InternalUser;

@Repository ("EmployeeDAOForInternal")
public class InternalUserDaoImpl implements InternalUserDao {
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings("unused")
	private DataSource dataSource;

	@Autowired
	public void setdataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Integer getUserId(String email) {
        String query = "SELECT id from internal_user where email= '" + email + "'"; 
        Integer id = jdbcTemplate.queryForList(query, Integer.class).get(0);
        return id;
    }
	
	public InternalUser getUserById(int user_id) {
        String query = "SELECT * from internal_user where id= '" + user_id + "'"; 
        InternalUser user = new InternalUser();
        List<InternalUser> user_list = new ArrayList<InternalUser>();
        user_list = jdbcTemplate.query(query, new BeanPropertyRowMapper<InternalUser>(InternalUser.class));
        if(user_list.size()>0){
        	user = user_list.get(0);
        }
        return user;
    }
	
	public String getEmailID(Integer userID)
	{
		 String query = "SELECT email from internal_user where id= '" + userID + "'"; 
	        String email = jdbcTemplate.queryForList(query, String.class).get(0);
	        return email;
	}
	
	public InternalUser setInternalUser(String name,String designation,String address,BigInteger phone,String email, String date_of_birth,String ssn)
    {
        InternalUser userDetails = new InternalUser();
        userDetails.setName(name);
        userDetails.setDesignation(designation);
        userDetails.setAddress(address);
        userDetails.setDate_of_birth(date_of_birth);
        userDetails.setEmail(email);
        userDetails.setPhone(phone);
        userDetails.setSsn(ssn);
        return userDetails;
    }
	
	public Boolean save(InternalUser userdetails)
    {
        String query = "INSERT INTO internal_user ( id , name , designation , address , phone , email , date_of_birth , ssn ) VALUES (?,?,?,?,?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userdetails.getId());
            ps.setString(2, userdetails.getName());
            ps.setString(3, userdetails.getDesignation());
            ps.setString(4, userdetails.getAddress());
            ps.setLong(5, userdetails.getPhone().longValue());
            ps.setString(6, userdetails.getEmail());
            ps.setString(7, userdetails.getDate_of_birth());
            ps.setString(8, userdetails.getSsn());
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

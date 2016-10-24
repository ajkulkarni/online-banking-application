package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.CustomerModel.Customer;


public class CustomerMapper implements RowMapper<Customer>  {

	public Customer mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Customer cust = new Customer();
		cust.setID(rs.getInt("ID"));
		cust.setName(rs.getString("name"));
		cust.setMerchant_secret(rs.getString("merchant_secret"));
		
		return cust;
	}

}

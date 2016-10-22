package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.CustomerModel.Customer;

@Repository ("customerDAO")
public class CustomerDAO {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Customer getCustomer(int id) {
		String query = "select * from external_users WHERE id = 101";//+ id;
		List<Customer> custList = jdbcTemplate.query(query, new CustomerMapper());
		return custList.get(0);
	}

}

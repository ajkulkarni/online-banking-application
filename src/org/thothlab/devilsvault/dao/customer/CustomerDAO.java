package org.thothlab.devilsvault.dao.customer;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Customer;

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
		String query = "select * from external_users WHERE id = "+id+"";
		List<Customer> custList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Customer>(Customer.class));
		return custList.size() == 0 ? null :custList.get(0);
	}

}
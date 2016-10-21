package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thothlab.devilsvault.CustomerModel.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDOAImpl {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
}

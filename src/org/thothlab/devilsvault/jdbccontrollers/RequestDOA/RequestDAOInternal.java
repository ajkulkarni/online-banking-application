package org.thothlab.devilsvault.jdbccontrollers.RequestDOA;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository ("requestDAOInternal")
public class RequestDAOInternal extends RequestDAOImpl{
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	@SuppressWarnings("unused")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}

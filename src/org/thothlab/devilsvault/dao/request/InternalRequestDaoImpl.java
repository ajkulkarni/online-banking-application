package org.thothlab.devilsvault.dao.request;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository ("internalRequestDao")
public class InternalRequestDaoImpl extends RequestDaoImpl {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	@SuppressWarnings("unused")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}

}

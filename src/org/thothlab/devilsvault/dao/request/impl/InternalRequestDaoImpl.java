package org.thothlab.devilsvault.dao.request.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thothlab.devilsvault.dao.request.InternalRequestDao;

public class InternalRequestDaoImpl extends RequestDaoImpl implements InternalRequestDao{
	
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

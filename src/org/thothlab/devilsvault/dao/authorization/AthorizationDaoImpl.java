package org.thothlab.devilsvault.dao.authorization;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Authorization;

@Repository ("AuthorizationDao")
public class AthorizationDaoImpl implements AthorizationDao{
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
	public List<Authorization> getAllPendingAuthorization(int id) {
		String query = "SELECT * FROM authorization_pending WHERE internal_userID = "+id+"";
		List<Authorization> authorizationList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		return authorizationList;
	}

	@Override
	public List<Authorization> getAllCompleteAuthorization(int id) {
		String query = "SELECT * FROM authorization_completed WHERE internal_userID = "+id+"";
		List<Authorization> authorizationList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Authorization>(Authorization.class));
		return authorizationList;
	}

	

}
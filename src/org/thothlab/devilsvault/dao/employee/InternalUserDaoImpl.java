package org.thothlab.devilsvault.dao.employee;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.employee.InternalUserDao;

@Repository ("EmployeeDAOForInternal")
public class InternalUserDaoImpl implements InternalUserDao {
	private JdbcTemplate jdbcTemplate;
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

}

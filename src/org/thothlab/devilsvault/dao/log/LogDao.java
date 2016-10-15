package org.thothlab.devilsvault.dao.log;

import javax.sql.DataSource;

import org.thothlab.devilsvault.db.model.Request;

public interface LogDao {
	
	public void setDataSource(DataSource dataSource);
	
	public Boolean save(Request request, String type);
	
	public Request getById(int id);
	
	public void update(Request employee);
	
	public void deleteById(int id);
	
}

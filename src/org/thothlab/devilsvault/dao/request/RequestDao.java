package org.thothlab.devilsvault.dao.request;

import javax.sql.DataSource;

import org.thothlab.devilsvault.db.model.Request;

public interface RequestDao {
	
	public void setDataSource(DataSource dataSource);
	
	public Boolean save(Request request, String type);
	
	public Request getById(int id);
	
	public void update(Request employee);
	
	public void deleteById(int id);
	
}

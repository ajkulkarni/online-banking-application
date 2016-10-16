package org.thothlab.devilsvault.dao.request;

import java.util.List;

import javax.sql.DataSource;

import org.thothlab.devilsvault.db.model.Request;

public interface RequestDao {
	
	public void setDataSource(DataSource dataSource);
	
	public Boolean save(Request request, String type);
	
	public List<Request> getById(int id, String table);
	
	public void update(Request employee);
	
	public void deleteById(int id);
	
	public List<Request> getByUserId(int requesterid, String status);
	
}

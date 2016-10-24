package org.thothlab.devilsvault.dao.request;

import java.util.List;

import javax.sql.DataSource;

import org.thothlab.devilsvault.db.model.Request;

public interface RequestDao {
	
	public void setDataSource(DataSource dataSource);
	
	public Boolean save(Request request, String type);
	
	public List<Request> getById(int id, String table);
	
	public void update(String table,String set,String newValue,String where,String oldValue);
	
	public void deleteById(int id, String type);
	
	public List<Request> getByUserId(int requesterid, String status);
	
	public void approveRequest(int id, String type, int approver);
	
	public void rejectRequest(int id, String type, int approver);
	
}

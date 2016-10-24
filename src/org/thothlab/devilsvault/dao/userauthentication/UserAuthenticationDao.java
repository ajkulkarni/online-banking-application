package org.thothlab.devilsvault.dao.userauthentication;

import javax.sql.DataSource;

import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.UserAuthentication;

public interface UserAuthenticationDao {
	
	public void setDataSource(DataSource dataSource);
	
	public Boolean save(Request request, String type);
	
	public String changePassword(String oldPassword,String newPassword,String confirmPassword,Integer userID);
	
	public UserAuthentication getUserDetails(String email);
}

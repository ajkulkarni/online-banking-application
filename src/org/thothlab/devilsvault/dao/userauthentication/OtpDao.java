package org.thothlab.devilsvault.dao.userauthentication;

import javax.sql.DataSource;

public interface OtpDao {
	
	public void setDataSource(DataSource dataSource);
	
	public String verifyEmail(String email);
	
	public String generateOTP(String email);
	
	public String processOTP(String email);
	
	public String verifyOTP(String otp, String email);
	
	public String updatePassword(String email, String password);
}
package org.thothlab.devilsvault.logincontrollers;



public interface UserDetailsDao {

	void updateFailAttempts(String username);

	void resetFailAttempts(String username);
	
	UserAttempts getUserAttempts(String username);

}
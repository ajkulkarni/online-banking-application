package org.thothlab.devilsvault.dao.user;

import org.thothlab.devilsvault.db.model.UserAttempts;

public interface UserDetailsDao {

	void updateFailAttempts(String username);

	void resetFailAttempts(String username);
	
	UserAttempts getUserAttempts(String username);

}
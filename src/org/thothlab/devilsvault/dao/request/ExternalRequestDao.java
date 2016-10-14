package org.thothlab.devilsvault.dao.request;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

public interface ExternalRequestDao {
	public void setDataSource(DataSource dataSource);
	public int createRequest();
}

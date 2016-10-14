package org.thothlab.devilsvault.dao.request;

import javax.sql.DataSource;

public interface InternalRequestDao {
	public void setDataSource(DataSource dataSource);
}

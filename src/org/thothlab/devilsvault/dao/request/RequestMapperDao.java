package org.thothlab.devilsvault.dao.request;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.thothlab.devilsvault.db.model.Request;

public interface RequestMapperDao {
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException;
}

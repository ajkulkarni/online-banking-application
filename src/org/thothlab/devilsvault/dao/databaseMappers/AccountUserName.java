package org.thothlab.devilsvault.dao.databaseMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccountUserName implements RowMapper<String>{

	@Override
	public String mapRow(ResultSet arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
		
		   return arg0.getString(1);
}
}
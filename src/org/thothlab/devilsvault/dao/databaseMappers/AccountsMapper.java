package org.thothlab.devilsvault.dao.databaseMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccountsMapper implements RowMapper<Integer>{

	@Override
	public Integer mapRow(ResultSet arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
		return Integer.parseInt(arg0.getString(1));
}
	
	
	
	
}

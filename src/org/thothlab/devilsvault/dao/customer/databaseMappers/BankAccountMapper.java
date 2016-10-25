package org.thothlab.devilsvault.dao.customer.databaseMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.db.model.BankAccountExternal;

public class BankAccountMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		BankAccountExternal ba = new BankAccountExternal();
		ba.setBalance(rs.getBigDecimal("balance"));
		ba.setHold(rs.getBigDecimal("hold"));
		return ba;
	}

}
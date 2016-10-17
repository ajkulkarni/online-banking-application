package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.CustomerModel.BankAccountExternal;

public class BankAccountMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		BankAccountExternal ba = new BankAccountExternal();
		ba.setBalance(rs.getFloat("balance"));
		ba.setHold(rs.getFloat("hold"));
		return ba;
	}

}

package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.jdbccontrollers.model.Request;

public class CreditCardAccMapper implements RowMapper<CreditAccount>  {

	@Override
	public CreditAccount mapRow(ResultSet arg0, int arg1) throws SQLException {
		CreditAccount obj = new CreditAccount();
		//obj.setOwner(rs.get);
		
		
		
		return obj;
	}

}

package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;

public class CreditCardTransMapper implements RowMapper<TransactionModel> {

	@Override
	public TransactionModel mapRow(ResultSet rs, int arg1) throws SQLException {
		TransactionModel obj = new TransactionModel();
		obj.setID(rs.getInt("id"));
		
		obj.setAmount(rs.getInt("amount"));
		
		obj.setTimeStamp(rs.getInt("timeStamp"));
		
		obj.setDescription(rs.getString("Description"));
		// obj.setOwner(rs.get);
		return obj;
	}

}
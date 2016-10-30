package org.thothlab.devilsvault.dao.creditcard;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.db.model.TransactionModel;

public class CreditCardTransMapper implements RowMapper<TransactionModel> {

	@Override
	public TransactionModel mapRow(ResultSet rs, int arg1) throws SQLException {
		TransactionModel obj = new TransactionModel();
		obj.setId(rs.getInt("id"));
		obj.setPayee_id(rs.getInt("payee_id"));
		obj.setPayer_id(rs.getInt("payer_id"));
		obj.setAmount(rs.getInt("amount"));
		obj.setStatus(rs.getString("status"));
		obj.setTimestamp_updated(rs.getDate("timestamp_created"));
		obj.setTimestamp_updated(rs.getDate("timestamp_updated"));
		obj.setIsPending(rs.getInt("isPending"));
		obj.setDescription(rs.getString("Description"));
		// obj.setOwner(rs.get);
		return obj;
	}

}
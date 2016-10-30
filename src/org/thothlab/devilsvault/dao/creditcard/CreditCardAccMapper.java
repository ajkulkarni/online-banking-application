package org.thothlab.devilsvault.dao.creditcard;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.db.model.CreditAccount;


public class CreditCardAccMapper implements RowMapper<CreditAccount>  {

	@Override
	public CreditAccount mapRow(ResultSet rs, int arg1) throws SQLException {
		CreditAccount obj = new CreditAccount();
		obj.setId(rs.getInt("id"));
		obj.setCredit_card_no(new BigInteger(Long.toString(rs.getLong("credit_card_no"))));
		obj.setAvailBalance(rs.getInt("available_balance"));
		obj.setLastBillAmount(rs.getInt("last_bill_amount"));
		obj.setDueDateTimestamp(rs.getDate("due_date"));
		obj.setApr(rs.getFloat("apr"));
		obj.setAccountNumber(rs.getInt("account_number"));
		obj.setCycleDate(rs.getDate("cycle_date"));
		obj.setCurrentDueAmount(rs.getInt("current_due_amt"));
		obj.setCreditLimit(rs.getInt("credit_limit"));
		return obj;
	}

}

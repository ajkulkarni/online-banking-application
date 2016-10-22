package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;

public class CreditCardAccMapper implements RowMapper<CreditAccount>  {

	@Override
	public CreditAccount mapRow(ResultSet rs, int arg1) throws SQLException {
		CreditAccount obj = new CreditAccount();
		obj.setId(rs.getInt("id"));
		obj.setCredit_card_no(rs.getInt("credit_card_no"));
		obj.setAvailBalance(rs.getInt("available_balance"));
		obj.setLastBillAmount(rs.getInt("last_bill_amount"));
		obj.setDueDateTimestamp(rs.getDate("due_date"));
		obj.setApr(rs.getFloat("apr"));
		obj.setBank_accounts_id(rs.getInt("account_number"));
		return obj;
	}

}

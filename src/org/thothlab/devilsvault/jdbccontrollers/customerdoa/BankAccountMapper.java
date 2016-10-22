package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.CustomerModel.BankAccount;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.DebitAccount;
import org.thothlab.devilsvault.CustomerModel.BankAccount.AccountType;


public class BankAccountMapper  implements RowMapper<BankAccount> {

	@Override
	public BankAccount mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BankAccount obj = null;
		String accountType = rs.getString("account_type");
		if (accountType.equals("cc")) {
			 obj = new CreditAccount();
			 
		} else if (accountType.equals("SAVINGS")) {
			obj = new DebitAccount(AccountType.SAVINGS);
			
		} else if (accountType.equals("CHECKINGS")) {
			obj = new DebitAccount(AccountType.CHECKING);
			
		}  
		CustomerDAO custDao = CustomerDAOHelper.customerDAO();
		Customer cust = custDao.getCustomer(rs.getInt("external_users_id"));
		obj.setOwner(cust);
		
		
//		obj.setId(rs.getInt("id"));
//		obj.setCredit_card_no(rs.getInt("credit_card_no"));
//		obj.setAvailBalance(rs.getInt("available_balance"));
//		obj.setLastBillAmount(rs.getInt("last_bill_amount"));
//		obj.setDueDateTimestamp(rs.getDate("due_date"));
//		obj.setApr(rs.getFloat("apr"));
//		obj.setAccountNumber(rs.getInt("account_number"));
		return obj;
	}

}

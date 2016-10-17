package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardAccMapper;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardTransMapper;

@Repository ("creditCardDOA")
public class CreditCardDOA extends CustomerDOAImpl {
		
		@SuppressWarnings("unused")
		private DataSource dataSource;
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		public void setDataSource(DataSource dataSource) {
			
			this.dataSource = dataSource;
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	
	/**
	 * Get the credit account details for the user.
	 * @param customer
	 * @return
	 */
		public CreditAccount getCreditAccount(Customer customer) {
		String query = "select cc.id id, cc.credit_card_no credit_card_no, cc.available_balance available_balance,"
				+ " cc.last_bill_amount last_bill_amount, cc.due_date due_date, cc.apr apr,cc.bank_accounts_id bank_accounts_id "
				+ "from credit_card_account_details cc INNER JOIN bank_accounts bk where bk.id = cc.bank_accounts_id  "
				+ "AND bk.external_users_id= 100";//+customer.getID();
		
		System.out.println(query);
		List<CreditAccount> creditcard_details= jdbcTemplate.query(query,new CreditCardAccMapper());
		return creditcard_details.get(0);
		/*return null;*/
	}
	
	/**
	 * Returns all the credit card transaction for the user.
	 * @return
	 */
	public List<TransactionModel> getAllTransactions(CreditAccount account) {
		
		String query = "select * from transaction_completed WHERE payee_id = 100"
				+" OR payer_id = 101";
		System.out.println("Query - " + query);
		 		List<TransactionModel> transactionList = jdbcTemplate.query(query,new CreditCardTransMapper());
				return transactionList;		
		//return null;
	}
	
	
	

}

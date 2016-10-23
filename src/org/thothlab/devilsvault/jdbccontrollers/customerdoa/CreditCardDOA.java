package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.CustomerModel.BankAccount;
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
				+ " cc.last_bill_amount last_bill_amount, cc.due_date due_date, cc.apr apr,cc.account_number account_number "
				+ "from credit_card_account_details cc INNER JOIN bank_accounts bk where bk.account_number = cc.account_number  "
				+ "AND bk.external_users_id= 100 AND bk.account_type='cc' ";//+customer.getID();
		
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
		
		String query = "select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, isPending from "
				+"(select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated,False isPending from transaction_completed WHERE payee_id = 100"
				+" OR payer_id = 101" 
				+" union "
				+"select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated ,True isPending  from transaction_pending WHERE payee_id = 100"
				+" OR payer_id = 101) as transactiontable limit 10"
				;
		System.out.println("Query - " + query);
		 		List<TransactionModel> transactionList = jdbcTemplate.query(query,new CreditCardTransMapper());
				return transactionList;		
		//return null;
	}
	
	/**
	 * Returns last 1 month credit card transaction for the user.
	 * @return
	 */
	public List<TransactionModel> getLastOneMonTransactions(CreditAccount account) {
		
		String query ="select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated,isPending from  " 
					 +"(select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, False isPending from transaction_completed "
					 +" where (payee_id = "+ "101" +" or payer_id = "+ "101"+")"
					 +" and timestamp_updated >= (CURDATE()-interval 1 month) "
					 +" union "
					 +" select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, True isPending from transaction_completed "
					 +" where (payee_id = "+ "101" +" or payer_id = "+ "101"+")"
					 +" and timestamp_updated >= (CURDATE()-interval 1 month) "
					 +" ) transaction"
					 +" order by timestamp_updated desc ";
		System.out.println("Query - " + query);
		 		List<TransactionModel> transactionList = jdbcTemplate.query(query,new CreditCardTransMapper());
				return transactionList;		
		//return null;
	}
	
	/**
	 * Returns last 3 month credit card transaction for the user.
	 * @return
	 */
	public List<TransactionModel> getLastThreeMonTransactions(CreditAccount account) {
		
		String query = "select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated,isPending from  " 
				 +"(select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, False isPending from transaction_completed "
				 +" where (payee_id = "+ "101" +" or payer_id = "+ "101"+")"
				 +" and timestamp_updated >= (CURDATE()-interval 3 month) "
				 +" union "
				 +" select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, True isPending from transaction_completed "
				 +" where (payee_id = "+ "101" +" or payer_id = "+ "101"+")"
				 +" and timestamp_updated >= (CURDATE()-interval 3 month) "
				 +" ) transaction"
				 +" order by timestamp_updated desc ";
		System.out.println("Query - " + query);
		 		List<TransactionModel> transactionList = jdbcTemplate.query(query,new CreditCardTransMapper());
				return transactionList;		
		//return null;
	}
	
	/**
	 * Returns last 3 month credit card transaction for the user.
	 * @return
	 */
	public List<TransactionModel> getLastSixMonTransactions(CreditAccount account) {
		
		String query = "select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated,isPending from  " 
				 +"(select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, False isPending from transaction_completed "
				 +" where (payee_id = "+ "101" +" or payer_id = "+ "101"+")"
				 +" and timestamp_updated >= (CURDATE()-interval 6 month) "
				 +" union "
				 +" select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, True isPending from transaction_completed "
				 +" where (payee_id = "+ "101" +" or payer_id = "+ "101"+")"
				 +" and timestamp_updated >= (CURDATE()-interval 6 month) "
				 +" ) transaction"
				 +" order by timestamp_updated desc ";
		System.out.println("Query - " + query);
		 		List<TransactionModel> transactionList = jdbcTemplate.query(query,new CreditCardTransMapper());
				return transactionList;		
		//return null;
	}
	
	
	public CreditAccount getAccount(String creditCardNumber, String cvv, String month, String year) {
		String query = "SELECT * FROM credit_card_account_details where credit_card_no = " + creditCardNumber;
		List<CreditAccount> creditcard_details= jdbcTemplate.query(query,new CreditCardAccMapper());
		CreditAccount creditaccount = creditcard_details.get(0);
		
		
		CustomerAccountsDAO customerAccDao = CustomerDAOHelper.customerAccountsDAO();
		BankAccount bankAcc = customerAccDao.getAccount(creditaccount.getAccountNumber());
		
		creditaccount.setAccountNumber(bankAcc.getAccountNumber());
		creditaccount.setOwner(bankAcc.getOwner());
		creditaccount.setAccountType(bankAcc.getAccountType());
		
		return creditaccount;
	}
	
	
	
	

}

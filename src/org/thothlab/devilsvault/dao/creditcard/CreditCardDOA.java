package org.thothlab.devilsvault.dao.creditcard;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.customer.CustomerAccountsDAO;
import org.thothlab.devilsvault.db.model.BankAccount;
import org.thothlab.devilsvault.db.model.CreditAccount;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.TransactionModel;

@Repository ("creditCardDOA")
public class CreditCardDOA {
		
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
				+ " cc.last_bill_amount last_bill_amount, cc.due_date due_date, cc.apr apr,cc.account_number account_number, "
				+ "cc.cycle_date cycle_date, cc.current_due_amt current_due_amt, cc.credit_limit credit_limit "
				+ "from credit_card_account_details cc INNER JOIN bank_accounts bk where bk.account_number = cc.account_number  "
				+ "AND bk.external_users_id= "+customer.getId() +" AND bk.account_type='CREDIT' ";//
		
		
		List<CreditAccount> creditcard_details= jdbcTemplate.query(query,new CreditCardAccMapper());
		return creditcard_details.get(0);
		/*return null;*/
	}
	
		/**
		 * Returns last 1 month credit card transaction for the user.
		 * @return
		 */
		public List<TransactionModel> getTransactionForMonth(CreditAccount account, int month) {
			month += 1;
			String dateFormat = "'2016-" +month+"-15'";
			
			String query ="select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated,isPending from  " 
					+"(select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, False isPending from transaction_completed "
					+" where (payee_id = "+ account.getAccountNumber() +" or payer_id = "+ account.getAccountNumber()+") "
					+" and timestamp_updated between  STR_TO_DATE("+dateFormat+",'%Y-%m-%d') - INTERVAL 1 MONTH AND STR_TO_DATE("+dateFormat+",'%Y-%m-%d') "
					+" union "
					+" select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, True isPending from transaction_pending "
					+" where (payee_id = "+ account.getAccountNumber() +" or payer_id = "+ account.getAccountNumber()+") "
					+" and timestamp_updated between STR_TO_DATE("+dateFormat+",'%Y-%m-%d') - INTERVAL 1 MONTH AND STR_TO_DATE("+dateFormat+",'%Y-%m-%d') "
					+" ) transaction"
					+" order by timestamp_updated desc ";
					
			 		List<TransactionModel> transactionList = jdbcTemplate.query(query,new CreditCardTransMapper());
					return transactionList;		
			//return null;
		}
		
		
		//	/**
//	 * Returns last 1 month credit card transaction for the user.
//	 * @return
//	 */
//	public List<TransactionModel> getTransactionForMonth(CreditAccount account, int month) {
//		month += 1;
//		String dateFormat = "'2016-" +month+"-15'";
//		
//		String query ="select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated,isPending from  " 
//				+"(select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, False isPending from transaction_completed "
//				+" where (payee_id = "+ account.getAccountNumber() +" or payer_id = "+ account.getAccountNumber()+") and (transaction_type in ('CC_FEES', 'CC_PAYMENT' , 'cc','ccpayment','MERCHANT') or description in ('Credit Card payment')) "
//				+" and timestamp_updated between  STR_TO_DATE("+dateFormat+",'%Y-%m-%d') - INTERVAL 1 MONTH AND STR_TO_DATE("+dateFormat+",'%Y-%m-%d') "
//				+" union "
//				+" select id,payee_id,payer_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated, True isPending from transaction_pending "
//				+" where (payee_id = "+ account.getAccountNumber() +" or payer_id = "+ account.getAccountNumber()+") and (transaction_type in ('CC_FEES', 'CC_PAYMENT' , 'cc','ccpayment','MERCHANT') or description in ('Credit Card payment')) "
//				+" and timestamp_updated between STR_TO_DATE("+dateFormat+",'%Y-%m-%d') - INTERVAL 1 MONTH AND STR_TO_DATE("+dateFormat+",'%Y-%m-%d') "
//				+" ) transaction"
//				+" order by timestamp_updated desc ";
//				System.out.println(query);
//		 		List<TransactionModel> transactionList = jdbcTemplate.query(query,new CreditCardTransMapper());
//				return transactionList;		
//		//return null;
//	}

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
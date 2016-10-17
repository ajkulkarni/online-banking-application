package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.CustomerModel.BankAccountExternal;

@Repository ("transferDAO")
public class TransferDAO {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
/*	public List<BankAccount> getPayerAccounts(int payerID) {
		
		String query = "select * from bankaccount WHERE payer_id = " + payerID ;
		List<Integer> bankAccounts = jdbcTemplate.query(query, new AccountsMapper());
		return relatedAccounts;
	} 
*/	
	
	
	
	

	public String getUserNames(int userID){
		
		String query = "Select name from external_users where id=" + userID;
		String user_name = jdbcTemplate.query(query, new AccountUserName()).get(0);
		return user_name;
	}
	
	
	public void getPayerAccounts(int accountNumber,List<String> userAccounts){
		
	//	String query = "select account_number from bank_accounts where external_users_id="+payerID;
		//List<Integer> payerAccounts = jdbcTemplate.query(query, new AccountsMapper());
	
		
		/*List<String> accountNumberType = new ArrayList<>();
		for(String element: userAccounts){
		*/String fetchAccountType = "select account_type from bank_accounts where account_number="+accountNumber;
		String accountType = jdbcTemplate.query(fetchAccountType, new AccountUserName()).get(0);
		
		//	accountNumberType.add(element+":"+accountType);
			userAccounts.add(accountNumber+":"+accountType);
	//		System.out.println("payer:"+element);
		/*}		*/

	}
	
	public List<Integer> getMultipleAccounts(Integer payerID){
	
		String query = "Select account_number from bank_accounts where external_users_id="+payerID;
		List<Integer> userMultipleAccounts = jdbcTemplate.query(query, new AccountsMapper());
		
		return userMultipleAccounts;
	}
	
	
	
	
	/*
	 * Returns payee account (Transfer to)
	 * */
	public void getRelatedAccounts(int payerID,List<String> populatedPayeeAccounts) {
		
		String query = "select payee_id from transaction_pending WHERE payer_id =" +payerID  ;

		System.out.println(query);
		List<Integer> relatedAccounts = jdbcTemplate.query(query, new AccountsMapper());

		Set<Integer> deduplicatedList = new HashSet<>();
		deduplicatedList.addAll(relatedAccounts);
		relatedAccounts.clear();
		relatedAccounts.addAll(deduplicatedList);
		
		List<String> userNameAccountNumber = new ArrayList<>();
		
		for(Integer listAccountElement: relatedAccounts){
			System.out.println(listAccountElement);
		}
		
		
		for(Integer listAccountElement: relatedAccounts){
			
			String userName=getUserNames(listAccountElement);
			System.out.println("-<<"+listAccountElement);
			String fetchAccountName = "Select external_users_id from bank_accounts where account_number="+listAccountElement;	
			
			System.out.println(fetchAccountName);
			
			int external_user_id = jdbcTemplate.query(fetchAccountName, new AccountsMapper()).get(0);
			
			String selectAccountNumbers = "select account_number from bank_accounts where external_users_id="+external_user_id;
			List<Integer> userBankAccounts = jdbcTemplate.query(selectAccountNumbers, new AccountsMapper());		
		
			for(Integer bankAccountElements: userBankAccounts){
				System.out.println("+"+bankAccountElements);
				userNameAccountNumber.add(userName+":"+bankAccountElements);			
				populatedPayeeAccounts.add(userName+":"+bankAccountElements);
			
			}
			System.out.println("--\n");
		}
		
		
	}
	
	public boolean validateAmount(int payerAccountNumber,double amount){
		//String fetchAccountBalance = "Select balance from bank_accounts where account_number="+payerAccountNumber;
		String sql = "Select balance,hold from bank_accounts where account_number = ?";

		BankAccountExternal ba = (BankAccountExternal)jdbcTemplate.queryForObject(
				sql, new Object[] { payerAccountNumber }, new BankAccountMapper());

		if(amount > (ba.getBalance() - ba.getHold())){
			
		
			return false;
		}
		return true;
	}
}

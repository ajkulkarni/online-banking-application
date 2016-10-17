package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;

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
	
	public List<Integer> getPayerAccounts(int payerID){
	
		String query = "select account_number from bank_accounts where external_users_id="+payerID;
		List<Integer> payerAccounts = jdbcTemplate.query(query, new AccountsMapper());
	
		for(Integer element: payerAccounts){
			System.out.println("payer:"+element);
		}
		
		return payerAccounts;
	}
	
	
	/*
	 * Returns payee account (Transfer to)
	 * */
	public List<Integer> getRelatedAccounts(int payerID) {
		
		String query = "select payee_id from transaction_pending WHERE payer_id =" +payerID  ;

		System.out.println(query);
		List<Integer> relatedAccounts = jdbcTemplate.query(query, new AccountsMapper());

		Set<Integer> deduplicatedList = new HashSet<>();
		deduplicatedList.addAll(relatedAccounts);
		relatedAccounts.clear();
		relatedAccounts.addAll(deduplicatedList);
		
		
		for(Integer listAccountElement: relatedAccounts){
		
			System.out.println("-<<"+listAccountElement);
			String fetchAccountName = "Select external_users_id from bank_accounts where account_number="+listAccountElement;	
			int external_user_id = jdbcTemplate.query(fetchAccountName, new AccountsMapper()).get(0);
			
			String selectAccountNumbers = "select account_number from bank_accounts where external_users_id="+external_user_id;
			List<Integer> userBankAccounts = jdbcTemplate.query(selectAccountNumbers, new AccountsMapper());		
		
			for(Integer bankAccountElements: userBankAccounts){
				System.out.println("+"+bankAccountElements);
			}
			System.out.println("--\n");
		}
		return relatedAccounts;	
	}
}

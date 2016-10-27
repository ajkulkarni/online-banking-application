package org.thothlab.devilsvault.dao.transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.BankAccountDB;

import org.thothlab.devilsvault.dao.databaseMappers.*;

@Repository("transferDAO")
public class TransferDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*
	 * public List<BankAccount> getPayerAccounts(int payerID) {
	 *
	 * String query = "select * from bankaccount WHERE payer_id = " + payerID ;
	 * List<Integer> bankAccounts = jdbcTemplate.query(query, new
	 * AccountsMapper()); return relatedAccounts; }
	 */

	public String getUserNames(int userID) {

		String query = "Select name from external_users where id=" + userID;
		String user_name = jdbcTemplate.query(query, new AccountUserName()).get(0);
		return user_name;
	}

	public void getPayerAccounts(int accountNumber, List<String> userAccounts) {

		// String query = "select account_number from bank_accounts where
		// external_users_id="+payerID;
		// List<Integer> payerAccounts = jdbcTemplate.query(query, new
		// AccountsMapper());

		/*
		 * List<String> accountNumberType = new ArrayList<>(); for(String
		 * element: userAccounts){
		 */String fetchAccountType = "select account_type from bank_accounts where account_number=" + accountNumber;
		String accountType = jdbcTemplate.query(fetchAccountType, new AccountUserName()).get(0);

		// accountNumberType.add(element+":"+accountType);
		userAccounts.add(accountNumber + ":" + accountType);
		// System.out.println("payer:"+element);
		/* } */

	}

	public List<Integer> getMultipleAccounts(Integer payerID) {

		String query = "Select account_number from bank_accounts where account_type <> \'CREDIT\' AND external_users_id=" + payerID;
		List<Integer> userMultipleAccounts = jdbcTemplate.query(query, new AccountsMapper());

		return userMultipleAccounts;
	}

	/*
	 * Returns payee account (Transfer to)
	 */
	public void getRelatedAccounts(int payerID, List<String> populatedPayeeAccounts) {

		String query = "select payee_id from transaction_completed WHERE transaction_type = \'externalFundTfr\' AND payer_id ="
				+ payerID;

		System.out.println(query);
		List<Integer> relatedAccounts = jdbcTemplate.query(query, new AccountsMapper());

		Set<Integer> deduplicatedList = new HashSet<>();
		deduplicatedList.addAll(relatedAccounts);
		relatedAccounts.clear();
		relatedAccounts.addAll(deduplicatedList);

		List<String> userNameAccountNumber = new ArrayList<>();

		for (Integer listAccountElement : relatedAccounts) {
			System.out.println(listAccountElement);
		}

		for (Integer listAccountElement : relatedAccounts) {

			System.out.println("-<<" + listAccountElement);
			String fetchAccountName = "Select external_users_id from bank_accounts where account_number="
					+ listAccountElement;

			System.out.println(fetchAccountName);

			int external_user_id = jdbcTemplate.query(fetchAccountName, new AccountsMapper()).get(0);
			System.out.println("Get username for : " + external_user_id);
			String userName = getUserNames(external_user_id);
			String username_account = userName + ":" + listAccountElement;
			if (populatedPayeeAccounts.contains(username_account))
				continue;
			String selectAccountNumbers = "select account_number from bank_accounts where external_users_id="
					+ external_user_id;
			List<Integer> userBankAccounts = jdbcTemplate.query(selectAccountNumbers, new AccountsMapper());

			for (Integer bankAccountElements : userBankAccounts) {
				System.out.println("+" + bankAccountElements);
				userNameAccountNumber.add(userName + ":" + bankAccountElements);
				populatedPayeeAccounts.add(userName + ":" + bankAccountElements);

			}
			System.out.println("--\n");
		}

	}

	public boolean validateAmount(int payerAccountNumber, BigDecimal amount) {
		// String fetchAccountBalance = "Select balance from bank_accounts where
		// account_number="+payerAccountNumber;
		String sql = "Select balance,hold from bank_accounts where account_number = ?";

		BankAccountDB ba = (BankAccountDB) jdbcTemplate.queryForObject(sql,
				new Object[] { payerAccountNumber }, new BankAccountMapper());

		if (amount.compareTo((ba.getBalance().subtract(ba.getHold()))) == 1
				|| amount.compareTo(new BigDecimal("0")) == -1) {
			return false;
		}
		return true;
	}

	public int fetchAccountNumber(String modeOfTransfer, String inputMode) {
		// TODO Auto-generated method stub
		String sql = null;
		boolean email = false;
		if (modeOfTransfer.equalsIgnoreCase("EMAIL")) {
			sql = "select id from external_users where email='" + inputMode + "'";
			email = true;
		} else {
			sql = "select id from external_users where phone='" + inputMode + "'";
		}
		System.out.println("abcbd" + inputMode);
		System.out.println("sql" + sql);

		int account_number = -1;
		try {
			// int external_user_id = (Integer)jdbcTemplate.queryForObject(sql,
			// new Object[]{inputMode},Integer.class);

			String query = null;
			if (email) {
				query = "select COUNT(*) from external_users where email='" + inputMode + "'";
			} else {
				query = "select COUNT(*) from external_users where phone='" + inputMode + "'";
			}

			int counts = jdbcTemplate.query(query, new AccountsMapper()).get(0);
			if (counts != 0) {
				int external_user_id = jdbcTemplate.query(sql, new AccountsMapper()).get(0);

				String account_number_query = "select account_number from bank_accounts where external_users_id="
						+ external_user_id + " " + "and account_type='CHECKING' ";
				account_number = jdbcTemplate.query(account_number_query, new AccountsMapper()).get(0);

			} else {
				return -1;
			}
		} catch (Exception e) {

			System.out.println("exceoption");

			// System.out.println(id+":id");
		}
		return account_number;

	}

	public boolean checkAccountExists(int account_number) {

		try {
			// int external_user_id = (Integer)jdbcTemplate.queryForObject(sql,
			// new Object[]{inputMode},Integer.class);

			String query = "select COUNT(*) from bank_accounts where account_number=" + account_number;
			System.out.println(query);

			int counts = jdbcTemplate.query(query, new AccountsMapper()).get(0);
			if (counts != 0)
				return true;
			else
				return false;

		} catch (Exception e) {

			System.out.println("exceoption");

			// System.out.println(id+":id");
		}
		return false;

	}

	public void updateHold(int payerAccountNumber, BigDecimal amount) {
		String updateStmt = "Update bank_accounts set hold = hold + ? where account_number = ?";
		// int external_user_id = jdbcTemplate.query(updateHoldField, new
		// AccountsMapper()).get(0);
		jdbcTemplate.update(updateStmt, new Object[] { amount, payerAccountNumber });
	}
	
	public List<String> getUserAccounts(int merchantID) throws SQLException {

		Connection con = dataSource.getConnection();
		PreparedStatement ps = null;
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);


		String queryName = "select id, name from external_users where id in (select external_user_id from authorized_merchants_users where merchant_id = " + merchantID + ")";
		System.out.println(queryName);
		List<String> userAccounts = new ArrayList<>();
		ResultSet rs = st.executeQuery(queryName);
		while (rs.next()) {
			userAccounts.add(rs.getString(2) + ":" + rs.getInt(1));
		}

		for (int i = 0; i < userAccounts.size(); i++) {
			System.out.println(userAccounts.get(i));
		}
		return userAccounts;

	}
	
	public HashMap<String,String> processPayment(int merchantID, int sentAmount, int userID) throws SQLException {
	    HashMap<String,String> sendToController = new HashMap<>();
	        String query = "select account_number from bank_accounts where external_users_id = " + userID + " and account_type = \"CREDIT\"";
	        //System.out.println(query+"fhsdkhfksd");
	        Connection con = dataSource.getConnection();
	        PreparedStatement ps = null;
	        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
	        ResultSet rs = st.executeQuery(query);
	        int accountNumber = -1;
	        rs.first();
	        accountNumber = rs.getInt("account_number");
	        //System.out.println(accountNumber);
	        //System.out.println("found acocunt number"+accountNumber);
	        //getting MinimumValidAmount
	        if((accountNumber==-1) ){
	            sendToController.put("userAccount","-1");
	        }
	        else{
	            sendToController.put("userAccount",""+accountNumber);
	        }
	        String getMinimumValidAmount = "select (credit_limit - available_balance) as balance from credit_card_account_details where account_number = " + accountNumber;
	        //System.out.println(getMinimumValidAmount);
	        rs = st.executeQuery(getMinimumValidAmount);
	        int minimumBalance=-1;
	        rs.first();
	        minimumBalance = rs.getInt("balance");
	        if(sentAmount > minimumBalance){
	            sendToController.put("accepted","-1");
	        }else{
	            sendToController.put("accepted","1");
	        }
	        //System.out.println("minimumBalance"+minimumBalance);
	        String getMerchantCheckingsAccount = "select account_number from bank_accounts where external_users_id ="+ merchantID+" and account_type in ( \"CHECKINGS\",\"CHECKING\") ";
	        //System.out.println("getting merchant checking accounts:"+getMerchantCheckingsAccount);
	        rs = st.executeQuery(getMerchantCheckingsAccount);
	        int merchantAccountNumber = -1;
	        rs.next();
	        merchantAccountNumber = rs.getInt("account_number");
	        //System.out.println("merchant Account Number:"+merchantAccountNumber);
	        if((merchantAccountNumber==-1)){
	            sendToController.put("merchantCheckingAccount","-1");
	        }
	        else{
	            sendToController.put("merchantCheckingAccount",""+merchantAccountNumber);
	        }
	        return sendToController;
	    }
	
	public List<String> getNewMerchantAccounts(int userID) throws SQLException {

		Connection con = dataSource.getConnection();
		PreparedStatement ps = null;
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

//		String queryName = "select name,id from external_users where merchant =1 and id not in (select merchant_id from authorized_merchants_users where external_user_id = "+userID+")";

		String queryName = "SELECT external_users.id, external_users.name\n" +
				"FROM external_users\n" +
				"INNER JOIN users\n" +
				"ON external_users.email=users.username and users.role = \"ROLE_MERCHANT\" and (id not in (select merchant_id from authorized_merchants_users where external_user_id = " + userID + "))";
		System.out.println(queryName);
		List<String> merchantAccounts = new ArrayList<>();
		ResultSet rs = st.executeQuery(queryName);
		while (rs.next()) {
			merchantAccounts.add(rs.getString(2) + ":" + rs.getInt(1));
		}

		for (int i = 0; i < merchantAccounts.size(); i++) {
			System.out.println(merchantAccounts.get(i));
		}
		return merchantAccounts;
	}
	
	public boolean addMerchantToUser(int merchantID, int userID) throws SQLException {

		String query = "insert into authorized_merchants_users values(" + merchantID + "," + userID + ")";
		System.out.println(query);
		Connection con = dataSource.getConnection();
		Statement st = con.createStatement();
		int i = st.executeUpdate(query);
		if (i == 1) {
			return true;
		}
		System.out.println(i);
		return false;
	}
	
	public boolean deleteMerchantConnection(int userID, int merchantID) throws SQLException {


		String query = "delete from authorized_merchants_users where merchant_id = " + merchantID + " and external_user_id = " + userID;
		System.out.println(query);
		Connection con = dataSource.getConnection();
		Statement st = con.createStatement();
		int i = st.executeUpdate(query);
		if (i == 1) {
			return true;
		}
		System.out.println("deleted ");
		return false;
	}
	
	public List<String> getExistingMerchantAccounts(int userID) throws SQLException {
		Connection con = dataSource.getConnection();
		PreparedStatement ps = null;
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

//		String queryName = "select merchant_id,NAME from authorized_merchants_users where external_user_id = "+userID;

		String queryName = "(SELECT external_users.id, external_users.name\n" +
				"FROM external_users\n" +
				"INNER JOIN users\n" +
				"ON external_users.email=users.username and users.role = \"ROLE_MERCHANT\" and external_users.id in (select merchant_id from authorized_merchants_users where external_user_id = " + userID + "))";
		System.out.println(queryName);
		List<String> merchantAccounts = new ArrayList<>();
		ResultSet rs = st.executeQuery(queryName);
		while (rs.next()) {
			merchantAccounts.add(rs.getString(2) + ":" + rs.getInt(1));
		}

		for (int i = 0; i < merchantAccounts.size(); i++) {
			System.out.println("Existing merchants" + merchantAccounts.get(i));
		}
		return merchantAccounts;
	}
	
	 public Boolean updateAvailableBalance(BigDecimal amount, int payerAccount) throws SQLException {
		    String query = "update credit_card_account_details set available_balance = available_balance + "+amount+" where account_number = "+payerAccount+"";
		        System.out.println(query);
		        Connection con = dataSource.getConnection();
		        Statement st = con.createStatement();
		        int i = st.executeUpdate(query);
		        System.out.println(i+"what it returns ");
		        if (i == 1) {
		            return true;
		        }
		        return false;
		    }



}
package org.thothlab.devilsvault.dao.customer;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.databaseMappers.BankAccountMapper;
import org.thothlab.devilsvault.db.model.BankAccount;
import org.thothlab.devilsvault.db.model.BankAccountExternal;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.Transaction;

@Repository ("CustomerAccountsDAO")
public class CustomerAccountsDAO{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setdataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<Transaction> getTransactionLines(int account_num,int interval)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Transaction TransactionLine = new Transaction();
		List<Transaction> TransactionLines = new ArrayList<Transaction>(); 
		String sql ="SELECT * FROM transaction_completed WHERE (payer_id="+account_num+" OR payee_id = "+account_num
				+") AND (timestamp_updated between DATE_SUB(NOW(), INTERVAL "+interval+" MONTH) AND NOW()) ORDER BY timestamp_updated DESC";
		System.out.println(sql);
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			//System.out.println(rs.next());
			while(rs.next())
			{	

				TransactionLine = new Transaction();		
				TransactionLine.setDescription(rs.getString("description"));
				TransactionLine.setStatus(rs.getString("status"));
				TransactionLine.setApprover(rs.getString("approver"));
				int payee_id = rs.getInt("payee_id");
				BigDecimal amount = rs.getBigDecimal("amount");
				TransactionLine.setAmount(amount);
				TransactionLine.setPayee_id(payee_id);
				TransactionLine.setTimestamp_updated(rs.getTimestamp("timestamp_updated"));
				TransactionLines.add(TransactionLine);
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		///System.out.println("transaction" + TransactionLines.size());
		return TransactionLines;
     }   
	
	public BankAccount getAccount(int accountNumber) {
		String query = "SELECT * FROM bank_accounts where account_number = " + accountNumber;
		List<Object> accList =  jdbcTemplate.query(query, new BankAccountMapper());
		return (BankAccount) accList.get(0);
	
     }
	public String getEmailID(Integer userID)
	{
		 String query = "SELECT email from external_users where id= '" + userID + "'"; 
	        String email = jdbcTemplate.queryForList(query, String.class).get(0);
	        return email;
	}
	
	public boolean addMoney(Customer user,BankAccountExternal account,Transaction transaction,double amount,String accountType) throws SQLException
	{
		boolean status=false;
		Connection con = null;
		PreparedStatement ps = null;
		java.sql.Timestamp createdDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
		String query_addBalance ="update bank_accounts set balance = balance + "+amount+" WHERE external_users_id="+user.getId()+" and account_type='"+accountType+"'";
		String query_AddToPending = "INSERT INTO transaction_pending (payer_id, payee_id,amount, hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?)";
		String query_AddToCompleted = "INSERT INTO transaction_completed (id,payer_id, payee_id,amount, hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		String query_deleteFromPending = "";
		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(query_AddToPending,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, account.getAccount_number());
			ps.setInt(2, account.getAccount_number());
			ps.setDouble(3, amount);
			ps.setString(4,"hasvalue");
			ps.setString(5,"Ext user add money");
			ps.setString(6,"Deposit money to account");
			ps.setString(7,"approved");
			ps.setString(8,"External user");
			ps.setInt(9, 0);
			ps.setTimestamp(10,createdDateTime);
			ps.setTimestamp(11,createdDateTime);
			int out =ps.executeUpdate();
			System.out.println("first query");
			 if (out != 0) {
	                ResultSet rs = ps.getGeneratedKeys();
	                rs.next();
	                System.out.println("ID----" + rs.getInt(1));
	                transaction.setId(rs.getInt(1));
			 }
			ps = con.prepareStatement(query_AddToCompleted);
			ps.setInt(1, transaction.getId());
			ps.setInt(2, account.getAccount_number());
			ps.setInt(3, account.getAccount_number());
			ps.setDouble(4, amount);
			ps.setString(5,"hasvalue");
			ps.setString(6,"Ext user add money");
			ps.setString(7,"Deposit money to account");
			ps.setString(8,"approved");
			ps.setString(9,"External user");
			ps.setInt(10, 0);
			ps.setTimestamp(11,createdDateTime);
			ps.setTimestamp(12,createdDateTime);
			ps.executeUpdate();
		
			ps = con.prepareStatement(query_addBalance);
			ps.executeUpdate();
			query_deleteFromPending = "delete from transaction_pending where id = "+transaction.getId();
			ps = con.prepareStatement(query_deleteFromPending);
			
			ps.executeUpdate();
			con.commit();
			status =true;
			
		}catch(SQLException e){
			con.rollback();
			status = false;
			e.printStackTrace();
		}finally{
			try {
					ps.close();
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
     }

	public boolean withdrawMoney(Customer user,BankAccountExternal account,Transaction transaction,double amount,String accountType) throws SQLException
	{
		boolean status=false;
		Connection con = null;
		PreparedStatement ps = null;
		java.sql.Timestamp createdDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
		String query_addBalance ="update bank_accounts set balance = balance - "+amount+" WHERE external_users_id="+user.getId()+" and account_type='"+accountType+"'";
		String query_AddToPending = "INSERT INTO transaction_pending (payer_id, payee_id,amount, hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?)";
		String query_AddToCompleted = "INSERT INTO transaction_completed (id,payer_id, payee_id,amount, hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		String query_deleteFromPending = "";
		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(query_AddToPending,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, account.getAccount_number());
			ps.setInt(2, account.getAccount_number());
			ps.setDouble(3, amount);
			ps.setString(4,"hasvalue");
			ps.setString(5,"Ext user add money");
			ps.setString(6,"Deposit money to account");
			ps.setString(7,"approved");
			ps.setString(8,"External user");
			ps.setInt(9, 0);
			ps.setTimestamp(10,createdDateTime);
			ps.setTimestamp(11,createdDateTime);
			int out =ps.executeUpdate();
			System.out.println("first query");
			 if (out != 0) {
	                ResultSet rs = ps.getGeneratedKeys();
	                rs.next();
	                System.out.println("ID----" + rs.getInt(1));
	                transaction.setId(rs.getInt(1));
			 }
			ps = con.prepareStatement(query_AddToCompleted);
			ps.setInt(1, transaction.getId());
			ps.setInt(2, account.getAccount_number());
			ps.setInt(3, account.getAccount_number());
			ps.setDouble(4, amount);
			ps.setString(5,"hasvalue");
			ps.setString(6,"Ext user add money");
			ps.setString(7,"Deposit money to account");
			ps.setString(8,"approved");
			ps.setString(9,"External user");
			ps.setInt(10, 0);
			ps.setTimestamp(11,createdDateTime);
			ps.setTimestamp(12,createdDateTime);
			ps.executeUpdate();
		
			ps = con.prepareStatement(query_addBalance);
			ps.executeUpdate();
			query_deleteFromPending = "delete from transaction_pending where id = "+transaction.getId();
			ps = con.prepareStatement(query_deleteFromPending);
			ps.executeUpdate();
			con.commit();
			status =true;
		}catch(SQLException e){
			con.rollback();
			status = false;
			e.printStackTrace();
		}finally{
			try {
					ps.close();
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
     }
}


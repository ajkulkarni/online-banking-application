package org.thothlab.devilsvault.dao.customer;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.databaseMappers.BankAccountMapper;
import org.thothlab.devilsvault.db.model.BankAccount;
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
	public List<Transaction> getTransactionLines(Customer user,int interval)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Transaction TransactionLine = new Transaction();
		List<Transaction> TransactionLines = new ArrayList<Transaction>(); 
		String sql ="SELECT * FROM  transaction_completed WHERE payer_id="+user.getId()+" OR payee_id = "+user.getId();
				//+"AND "+TransactionLine.getTimestamp_updated()+" between DATE_SUB(NOW(), INTERVAL "+interval+" MONTH) AND NOW()";
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
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
}


package org.thothlab.devilsvault.jdbccontrollers.customerdoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.CustomerModel.BankAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.MerchantPayment;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
@Repository ("CustomerAccountsDAO")
public class CustomerAccountsDAO{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setdataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<TransactionModel> getTransactionLines(Customer user,int interval)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransactionModel TransactionLine = new TransactionModel();
		List<TransactionModel> TransactionLines = new ArrayList<TransactionModel>(); 
		String sql ="SELECT * FROM  transaction_completed WHERE payer_id="+user.getID()+" OR payee_id = "+user.getID();
				//+"AND "+TransactionLine.getTimestamp_updated()+" between DATE_SUB(NOW(), INTERVAL "+interval+" MONTH) AND NOW()";
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				TransactionLine = new TransactionModel();
				
				TransactionLine.setDescription(rs.getString("description"));
				TransactionLine.setStatus(rs.getString("status"));
				TransactionLine.setApprover(rs.getString("approver"));
				int payee_id = rs.getInt("payee_id");
				float amount = rs.getFloat("amount");
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
		List<BankAccount> accList = jdbcTemplate.query(query, new BankAccountMapper());
		return accList.get(0);
	
     }
	public boolean CheckPayment(MerchantPayment merchantpayment) {
		// TODO Auto-generated method stub
		return true;
	}
	public int getMerchantAccountFromSecret(final String secret) {
		// TODO Auto-generated method stub
		String query = "select id from external_users where merchant_secret = ?";
		String query2 = "select account_number from bank_accounts where external_users_id = ? and account_type = ?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		int id = 0;
		int account = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, secret); // set input parameter
			rs = pstmt.executeQuery();
			if(rs.next()){
				id = rs.getInt("id");
			}
			System.out.print("id is " + id);
			pstmt = conn.prepareStatement(query2);
			pstmt.setInt(1, id);
			pstmt.setString(2,  "CHECKINGS");
			rs = pstmt.executeQuery();
			if(rs.next()){
				account = rs.getInt("account_number");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // create a statement
	   
		return account;
	}     

}


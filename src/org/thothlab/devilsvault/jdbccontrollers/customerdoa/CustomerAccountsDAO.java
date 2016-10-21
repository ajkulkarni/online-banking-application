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
import org.springframework.stereotype.Repository;
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
	public boolean CheckPayment(MerchantPayment merchantpayment) {
		// TODO Auto-generated method stub
		return true;
	}     
}


package org.thothlab.devilsvault.CustomerDAO;
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
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.CustomerModel.TransactionModel2;
@Repository ("CustomerAccountsDAO")
public class CustomerAccountsDAO{
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setdataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<TransactionModel2> getTransactionLines(Customer user,int interval)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransactionModel2 TransactionLine = new TransactionModel2();
		List<TransactionModel2> TransactionLines = new ArrayList<TransactionModel2>(); 
		String sql ="SELECT * FROM  transaction_completed WHERE payer_id="+user.getID()
				+"OR payee_id = "+user.getID();
				//+"AND "+TransactionLine.getTimestamp_updated()+" between DATE_SUB(NOW(), INTERVAL "+interval+" MONTH) AND NOW()";
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				TransactionLine = new TransactionModel2();
				
				TransactionLine.setDescription(rs.getString("description"));
				int id = rs.getInt(0);
				//TransactionLine.setID(id);
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
}


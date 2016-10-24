package org.thothlab.devilsvault.dao.customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Customer;
@Repository ("ExtUserDaoImpl")
public class ExtUserDaoImpl{
	@SuppressWarnings("unused")
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setdataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public Double getSavingsBalance(Customer user)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql ="SELECT balance FROM  bank_accounts WHERE external_users_id="+user.getId()+" and account_type='SAVINGS'";
		Double balance = 0.0d;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				balance = rs.getDouble("balance");
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
		return balance;
     }
	public Double getCheckingBalance(Customer user)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql ="SELECT balance FROM  bank_accounts WHERE external_users_id="+user.getId()+" and account_type='CHECKINGS'";
		Double balance = 0.0d;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				balance = rs.getDouble("balance");
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
		return balance;
     }
}

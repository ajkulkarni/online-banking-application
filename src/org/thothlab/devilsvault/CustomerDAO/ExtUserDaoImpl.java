package org.thothlab.devilsvault.CustomerDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.CustomerModel.Customer;
@Repository ("ExtUserDaoImpl")
public class ExtUserDaoImpl{
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
		String sql ="SELECT balance FROM  savings_accounts WHERE id="+user.getID();
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
		String sql ="SELECT balance FROM  checkings_accounts WHERE id="+user.getID();
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

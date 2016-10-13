package org.thothlab.devilsvault.CustomerDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thothlab.devilsvault.CustomerModel.Customer;


public class ExtUserDaoImpl implements ExtUserDao{
	private JdbcTemplate jdbcTemplate;
	public ExtUserDaoImpl(){}
	public ExtUserDaoImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public ArrayList<Float> ViewBalance(Customer user)
	{	
		System.out.println("in viewBalance");
		String sql ="SELECT * FROM  savings_accounts WHERE id=101";//+user.getID();
		ArrayList<Float> balances= new ArrayList<Float>();
		List accounts = jdbcTemplate.queryForList(sql);
		System.out.println("saving account query executed");
		balances.add((Float)accounts.get(0));
		sql ="SELECT * FROM  checkings_accounts WHERE id="+user.getID();
		accounts = jdbcTemplate.queryForList(sql);
		balances.add((Float)accounts.get(0));
		return balances;
     }
}

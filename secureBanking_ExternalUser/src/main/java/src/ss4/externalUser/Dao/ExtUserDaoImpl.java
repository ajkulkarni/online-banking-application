package src.ss4.externalUser.Dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import src.ss4.externalUser.Model.*;
public class ExtUserDaoImpl implements ExtUserDao{
	private JdbcTemplate jdbcTemplate;
	public ExtUserDaoImpl(){}
	public ExtUserDaoImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public ArrayList<Float> ViewBalance(ExtUser user)
	{
		String sql ="SELECT * FROM  savings_accounts WHERE id="+user.getID();
		ArrayList<Float> balances= new ArrayList<Float>();
		List accounts = jdbcTemplate.queryForList(sql);
		balances.add((Float)accounts.get(0));
		sql ="SELECT * FROM  checkings_accounts WHERE id="+user.getID();
		accounts = jdbcTemplate.queryForList(sql);
		balances.add((Float)accounts.get(0));
		return balances;
     }
}

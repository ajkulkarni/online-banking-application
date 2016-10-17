package org.thothlab.devilsvault.dao.transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.transaction.TransactionDao;
import org.thothlab.devilsvault.db.model.Transaction;

@Repository ("TransactionDao")
public class TransactionDaoImpl implements TransactionDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);		
	}

	@Override
	public Boolean save(Transaction transaction, String type) {
		String query = "insert into "+type+"(payer_id,payee_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, transaction.getPayer_id());
			ps.setInt(2, transaction.getPayee_id());
			ps.setBigDecimal(3, transaction.getAmount());
			ps.setString(4, transaction.getHashvalue());
			ps.setString(5, transaction.getTransaction_type());
			ps.setString(6, transaction.getDescription());
			ps.setString(7, transaction.getStatus());
			ps.setString(8, transaction.getApprover());
			ps.setBoolean(9, transaction.isCritical());
			ps.setDate(10, transaction.getTimestamp_created());
			ps.setDate(11, transaction.getTimestamp_updated());
			int out = ps.executeUpdate();
			if(out !=0){
				return true;
			}else return false;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public List<Transaction> getById(int id, String table) {
        String query = "SELECT * FROM "+ table +" WHERE id = "+id;
        List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper(Transaction.class));
        return transactionList;
    }
	
	@Override
	public void update(Transaction employer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteById(int id, String type) {
        Object[] parameters = { id };
        int[] queryType = {Types.INTEGER };
        String query = "DELETE FROM " + type+ " WHERE id = ?";
        int i = jdbcTemplate.update(query, parameters, queryType);
        if(i <= 0)
        {
            return false;
        }
            return true;
    }


	
}

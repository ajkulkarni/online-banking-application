package org.thothlab.devilsvault.dao.transaction.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.transaction.TransactionDao;
import org.thothlab.devilsvault.db.model.Request;
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
	public int createTransaction() {
		String query = "INSERT INTO transaction_pending (payer_id, payee_id,amount, hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?)";
		int rowsAffected = jdbcTemplate.update(query, 1, 1,500, "pending", "Add money","Add 1500USD","sds","sdsds",true,"2016-10-10","2016-10-10");
	    return rowsAffected;
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
			ps.setDouble(3, transaction.getAmount());
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
	public Transaction getById(int id, String table) {
		Transaction transaction = new Transaction();
		String query = "SELECT *FROM " + table + " where id='"+id+"';";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ResultSet out = ps.executeQuery(query);
			 out.next();
			transaction.setId((int)out.getObject(1));
			transaction.setPayer_id((int)out.getObject(2));
			transaction.setPayee_id((int)out.getObject(3));
			transaction.setAmount((double)out.getObject(4));
			transaction.setHashvalue((out.getObject(5)).toString());
			transaction.setTransaction_type((out.getObject(6)).toString());
			transaction.setDescription((out.getObject(7)).toString());
			transaction.setStatus((out.getObject(8)).toString());
			transaction.setApprover((out.getObject(9)).toString());
			transaction.setCritical((boolean)out.getObject(10));
			transaction.setTimestamp_created(out.getDate(11));
			transaction.setTimestamp_updated(out.getDate(12));
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
		return transaction;
	}

	@Override
	public void update(Transaction employer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Transaction> getAllPending() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getAllCompleted() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}

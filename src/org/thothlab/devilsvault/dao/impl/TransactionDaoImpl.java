package org.thothlab.devilsvault.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.thothlab.devilsvault.dao.TransactionDao;
import org.thothlab.devilsvault.db.model.Transaction;

public class TransactionDaoImpl implements TransactionDao {
	private DataSource dataSource;
	
	public TransactionDaoImpl() {
		// TODO Auto-generated constructor stub
		//Add data source here or make a parameterized constructor and pass it from calling function
		dataSource = null; //TBD
	}

	@Override
	public List<Transaction> getOpenTransactions() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM transaction WHERE status = ?";
		Connection conn = null;
		List <Transaction> transactionList = new LinkedList<>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "open");
			Transaction transaction = null;
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				transaction = new Transaction();
				transaction.setStatus("open");
				transaction.setId(rs.getInt("id"));
				transaction.setPayer_id(rs.getInt("payer_id"));
				transaction.setPayee_id(rs.getInt("payee_id"));
				transaction.setAmount(rs.getDouble("amount"));
				transaction.setHashvalue(rs.getString("hashvalue"));
				transaction.setTransaction_type(rs.getString("transaction_type"));
				transaction.setDescription(rs.getString("description"));
				transaction.setApprover(rs.getString("approver"));
				transaction.setCritical(rs.getBoolean("critical"));
				transaction.setTimestamp_created(rs.getDate("timestamp_created"));
				transaction.setTimestamp_created(rs.getDate("timestamp_updated"));
				transactionList.add(transaction);
			}
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
				}
				catch(SQLException e) {}
			}
		}
		return transactionList;
	}
	
	
}

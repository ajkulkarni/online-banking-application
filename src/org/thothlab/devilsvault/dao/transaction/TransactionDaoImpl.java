package org.thothlab.devilsvault.dao.transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Transaction;

import com.mysql.jdbc.Statement;

@Repository("TransactionDao")
public class TransactionDaoImpl implements TransactionDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Transaction createExternalTransaction(int payerAccountNumber, BigDecimal amount, int payeeAccountNumber,
			String description, String transactionType) throws ParseException{
		Transaction extTransfer = new Transaction();

		extTransfer.setPayer_id(payerAccountNumber);
		extTransfer.setPayee_id(payeeAccountNumber);
		extTransfer.setAmount(amount);
		extTransfer.setDescription(description);
		extTransfer.setTransaction_type(transactionType);

		/*
		 * eptpModeOfTransfer eptpinputMode eptpselectPayerAccount
		 * eptpinputAmount
		 */

		extTransfer.setHashvalue("");

		extTransfer.setStatus("pending");
		extTransfer.setApprover("");
		if(amount.compareTo(new BigDecimal("1000")) == 1){
			extTransfer.setCritical(true);			
		}
		else{
			extTransfer.setCritical(false);
		}
		
		/*java.util.Date currentDateTime = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = df.format(currentDateTime);*/
		java.sql.Timestamp createdDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
		extTransfer.setTimestamp_created(createdDateTime);
		extTransfer.setTimestamp_updated(createdDateTime);

		return extTransfer;
	}

	
	
	
	@Override
	public Boolean save(Transaction transaction, String type) {
		String query = "insert into " + type
				+ "(payer_id,payee_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, transaction.getPayer_id());
			ps.setInt(2, transaction.getPayee_id());
			ps.setBigDecimal(3, transaction.getAmount());
			ps.setString(4, transaction.getHashvalue());
			ps.setString(5, transaction.getTransaction_type());
			ps.setString(6, transaction.getDescription());
			ps.setString(7, transaction.getStatus());
			ps.setString(8, transaction.getApprover());
			ps.setBoolean(9, transaction.isCritical());
			ps.setTimestamp(10, transaction.getTimestamp_created());
			ps.setTimestamp(11, transaction.getTimestamp_updated());
			int out = ps.executeUpdate();
			if (out != 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				//System.out.println("ID----" + rs.getInt(1));
				transaction.setId(rs.getInt(1));
				//ps.et
				return true;
			} else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		String query = "SELECT * FROM " + table + " WHERE id = " + id;
		List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
		return transactionList;
	}

	@Override
	public void update(Transaction employer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteById(int id, String type) {
		Object[] parameters = { id };
		int[] queryType = { Types.INTEGER };
		String query = "DELETE FROM " + type + " WHERE id = ?";
		int i = jdbcTemplate.update(query, parameters, queryType);
		if (i <= 0) {
			return false;
		}
		return true;
	}
	
	public Boolean saveToCompleted(Transaction transaction, String type) {
		System.out.println("Completed id : " + transaction.getId());
		String query = "insert into " + type
				+ "(id,payer_id,payee_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, transaction.getId());
			ps.setInt(2, transaction.getPayer_id());
			ps.setInt(3, transaction.getPayee_id());
			ps.setBigDecimal(4, transaction.getAmount());
			ps.setString(5, transaction.getHashvalue());
			ps.setString(6, transaction.getTransaction_type());
			ps.setString(7, transaction.getDescription());
			ps.setString(8, transaction.getStatus());
			ps.setString(9, transaction.getApprover());
			ps.setBoolean(10, transaction.isCritical());
			ps.setTimestamp(11, transaction.getTimestamp_created());
			ps.setTimestamp(12, transaction.getTimestamp_updated());
			int out = ps.executeUpdate();
			if (out != 0) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void updatePayerBalance(int payerAccountNumber, BigDecimal amount){
		String updateStmt = "Update bank_accounts set balance = balance - ? where account_number = ?";
		jdbcTemplate.update(updateStmt, new Object[] {amount, payerAccountNumber});
	}
    
    public void updatePayeeBalance(int payeeAccountNumber, BigDecimal amount){
		String updateStmt = "Update bank_accounts set balance = balance + ? where account_number = ?";
		jdbcTemplate.update(updateStmt, new Object[] {amount, payeeAccountNumber});
	}


}

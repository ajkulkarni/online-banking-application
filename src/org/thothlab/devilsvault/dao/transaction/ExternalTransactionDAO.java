package org.thothlab.devilsvault.dao.transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Transaction;

@Repository("extTransactionDAO")
public class ExternalTransactionDAO extends TransactionDaoImpl {

	private DataSource dataSource;
	@SuppressWarnings("unused")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Transaction createExternalTransaction(int payerAccountNumber, BigDecimal amount, int payeeAccountNumber,
												 String description, String transactionType) {
		
		System.out.println("in createExternalTransaciton");
		
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
		extTransfer.setCritical(true);
		extTransfer.setTimestamp_created(null);
		extTransfer.setTimestamp_updated(null);

		return extTransfer;
	}

	public Boolean save(Transaction transaction, String type) {
		String query = "insert into " + type
				+ "(payer_id,payee_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,NOW(),NOW())";
		Connection con = null;
		PreparedStatement ps = null;
		System.out.println("in trans save");
		try {
			System.out.println("In try con");
			con = dataSource.getConnection();
			System.out.println("In try ps");
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
			// ps.setDate(10, transaction.getTimestamp_created());
			// ps.setDate(11, transaction.getTimestamp_updated());
			System.out.print(ps);
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

}
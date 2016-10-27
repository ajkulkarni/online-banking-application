package org.thothlab.devilsvault.dao.transaction;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Transaction;

@Repository ("TransactionSpecificDao")
public class InternalTransactionDaoImpl extends TransactionDaoImpl {

	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
		super.setDataSource(dataSource);
	}
	public int createTransaction() {
		String query = "INSERT INTO transaction_pending (payer_id, payee_id,amount, hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?)";
		int rowsAffected = jdbcTemplate.update(query, 1, 1,500, "pending", "Add money","Add 1500USD","sds","sdsds",true,"2016-10-10","2016-10-10");
	    return rowsAffected;
	}
	public List<Transaction> getAllPending() {
		
		String query = "SELECT * FROM transaction_pending";
		List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
		return transactionList;
		/*Transaction transaction = new Transaction();
		List <Transaction> transactionList = new ArrayList();
		String query = "SELECT *FROM transaction_pending";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ResultSet out = ps.executeQuery(query);
			while(out.next()) 
			{
			transaction.setId((int)out.getObject(1));
			transaction.setPayer_id((int)out.getObject(2));
			transaction.setPayee_id((int)out.getObject(3));
			transaction.setAmount((BigDecimal)out.getObject(4));
			transaction.setHashvalue((out.getObject(5)).toString());
			transaction.setTransaction_type((out.getObject(6)).toString());
			transaction.setDescription((out.getObject(7)).toString());
			transaction.setStatus((out.getObject(8)).toString());
			transaction.setApprover((out.getObject(9)).toString());
			transaction.setCritical((boolean)out.getObject(10));
			transaction.setTimestamp_created(out.getDate(11));
			transaction.setTimestamp_updated(out.getDate(12));
			transactionList.add(transaction);
			}
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
		return transactionList;*/
	}

	public List<Transaction> getAllCompleted() {
		String query = "SELECT * FROM transaction_completed";
		List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
		return transactionList;
	}
	@Override
	public Boolean save(Transaction transaction, String type) {
		
		return super.save(transaction, type);
	}
	
	public List <Transaction> getByUserId(int id, String table)
    {
        String query = "select *from " + table + " where payer_id = '" + id + "' OR payee_id = '" + id + "';";
        List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
        return transactionList;
    }
	
	public List <Transaction> getTransactionById(int id, String table)
    {
        String query = "select *from " + table + " where id = '" + id + "'";
        List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
        return transactionList;
    }
	
    public void approveTransaction(int id, String type) {
        // TODO Auto-generated method stub
        String query = "SELECT * FROM "+ type +" WHERE id =" + id;
        List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
        Transaction transaction = transactionList.get(0);
        transaction.setStatus("approved");
        save(transaction, "transaction_completed");
        deleteById(id, type);
    }
    
    public void rejectTransaction(int id, String type) {
        // TODO Auto-generated method stub
        String query = "SELECT * FROM "+ type +" WHERE id ="+ id;
        List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
        Transaction transaction = transactionList.get(0);
        transaction.setStatus("rejected");
        save(transaction, "transaction_completed");
        deleteById(id, type);
    }
    
    public List<Transaction> getAllPendingTransactionByAccountNo(List<Integer> accoNos)
    {
        String query = "SELECT *FROM transaction_pending WHERE (";
    for(int i = 0;i < accoNos.size(); i++)
    {
        if(i < accoNos.size() - 1)
            query += "payer_id = '" + accoNos.get(i) + "' OR ";
        else
            query += "payer_id = '" + accoNos.get(i) + "'";
    }
    query += ") AND critical='0'";
        List<Transaction> transactionList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
        return transactionList;
    }
    
    public void updateHold(int payerAccountNumber, BigDecimal amount){
		String updateStmt = "Update bank_accounts set hold = hold - ? where account_number = ?";
		//int external_user_id = jdbcTemplate.query(updateHoldField, new AccountsMapper()).get(0);
		jdbcTemplate.update(updateStmt, new Object[] {amount, payerAccountNumber});
	}
    
    public void updateCC_AvailableBalance(int amount, int account_number){
    	String updateStmt = "Update credit_card_account_details set available_balance = available_balance - ? where account_number = ?";
		//int external_user_id = jdbcTemplate.query(updateHoldField, new AccountsMapper()).get(0);
		jdbcTemplate.update(updateStmt, new Object[] {amount, account_number});
	
    }
    
    public void updateCC_CurrentDueAmt(int amount, int account_number){
    	String updateStmt = "Update credit_card_account_details set current_due_amt = current_due_amt - ? where account_number = ?";
		//int external_user_id = jdbcTemplate.query(updateHoldField, new AccountsMapper()).get(0);
		jdbcTemplate.update(updateStmt, new Object[] {amount, account_number});
	
    }
    
    public void updateCC_Payment(int amount, int account_number){
    	String updateStmt = "Update credit_card_account_details set payment = payment + ? where account_number = ?";
		//int external_user_id = jdbcTemplate.query(updateHoldField, new AccountsMapper()).get(0);
		jdbcTemplate.update(updateStmt, new Object[] {amount, account_number});
	
    }
}

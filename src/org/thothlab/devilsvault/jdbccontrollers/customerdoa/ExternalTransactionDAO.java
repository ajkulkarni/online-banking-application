package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.transaction.TransactionDaoImpl;
import org.thothlab.devilsvault.db.model.Transaction;

@Repository ("extTransactionDAO")
public class ExternalTransactionDAO extends TransactionDaoImpl{
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
    public Boolean save(Transaction transaction, String type) {
        String query = "insert into "+type+"(payer_id,payee_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        System.out.println("in trans save");
        try{
        	System.out.println("In try con");
            con = dataSource.getConnection();
            System.out.println("In try ps");
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
}

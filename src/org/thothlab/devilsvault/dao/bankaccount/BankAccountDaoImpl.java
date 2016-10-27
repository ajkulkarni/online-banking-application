package org.thothlab.devilsvault.dao.bankaccount;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.BankAccountDB;

@Repository ("bankAccountDao")
public class BankAccountDaoImpl implements BankAccountDao {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
	}
	
	public Boolean save(BankAccountDB bankDetails) {
        String query = "INSERT INTO bank_accounts ( external_users_id , account_type , balance,hold,account_number ) VALUES (?,?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, bankDetails.getExternal_user_id());
            ps.setString(2, bankDetails.getAccount_type());
            ps.setBigDecimal(3, bankDetails.getBalance());
            ps.setBigDecimal(4, bankDetails.getHold());
            ps.setInt(5, bankDetails.getAccount_number());
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
	public List<BankAccountDB> getAccountDetailsById(int userId) {
		String query = "SELECT * FROM bank_accounts WHERE external_users_id = " + userId;
		List<BankAccountDB> accountList = jdbcTemplate.query(query, new BeanPropertyRowMapper<BankAccountDB>(BankAccountDB.class));
		return accountList;
	}
	
	public Integer CreateAndGetCreditAccountNo(Integer UserID) {
        String[] type = new String [] {"CHECKING","CREDIT","SAVINGS"};
        BankAccountDB bankDetails;
        for(int i = 0 ;i< type.length;i++ )
        {
        bankDetails = new BankAccountDB();
        bankDetails.setExternal_user_id(UserID);
        bankDetails.setAccount_type(type[i]);
        bankDetails.setBalance(new BigDecimal("0"));
        bankDetails.setHold(new BigDecimal("0"));
        save(bankDetails);
        }
         String query = "SELECT account_number from bank_accounts where external_users_id= '" + UserID + "'" +
        " AND account_type = 'CREDIT'"; 
            Integer id = jdbcTemplate.queryForList(query, Integer.class).get(0);
            return id;
    }

}

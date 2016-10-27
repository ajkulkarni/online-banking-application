package org.thothlab.devilsvault.dao.customer;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.employee.InternalUserDaoImpl;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.InternalUser;

@Repository ("CustomerDAOForInternal")
public class InternalCustomerDAO extends CustomerDAO{
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings("unused")
	private DataSource dataSource;
	@Autowired
	public void setdataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

    public List<Integer> getAccNos(List <Integer> externalUserID)
    {
        String query = "SELECT account_number from bank_accounts where ";
        for(int i =0;i < externalUserID.size();i++)
        {
            if(i < externalUserID.size() - 1)
                query += "external_users_id = '" + externalUserID.get(i) + "' OR ";
            else
                query += "external_users_id = '" + externalUserID.get(i) + "'";
        }
        List<Integer> accNos = (List<Integer>) jdbcTemplate.queryForList(query,Integer.class);
        return accNos;
    }
    public List<Integer> getExternalUserIds(Integer internalUserID, String authType)
    {
        String query = "SELECT external_userID from authorization_completed where internal_userID ='" + internalUserID +"' AND auth_Type='" + authType + "'";
        List<Integer> externalIds = jdbcTemplate.queryForList(query,Integer.class);
        return externalIds;
    }
    
    public Integer getUserId(String email) {
        String query = "SELECT id from external_users where email= '" + email + "'"; 
        Integer id = jdbcTemplate.queryForList(query, Integer.class).get(0);
        return id;
    }
    
    public void deleteInTable(String table,String where, String id)
    {
        String query = "DELETE FROM " + table + " WHERE " + where + " ='" + id + "'";
        jdbcTemplate.update(query);
    }
    public void deleteCustomer(String extUserID)
    {
    	
		List<Integer> extIdList = new ArrayList<Integer>();
		extIdList.add(Integer.parseInt(extUserID));
		List<Integer> accNos = getAccNos(extIdList);
		Customer customer = getCustomer(Integer.parseInt(extUserID));
		deleteInTable("authorization_pending", "external_userID", extUserID);
		deleteInTable("authorization_completed", "external_userID", extUserID);
		deleteInTable("external_log", "userid", extUserID);
		deleteInTable("transaction_pending", "payer_id", extUserID);
		deleteInTable("transaction_pending", "payee_id", extUserID);
		deleteInTable("otp_table", "userEmail", customer.getEmail());
		deleteInTable("user_attempts", "username", customer.getEmail());
		deleteInTable("users", "username", customer.getEmail());
		deleteInTable("external_users", "id", extUserID.toString());
		if(accNos != null)
		{
			for(int i=0; i< accNos.size();i ++)
			{
				deleteInTable("credit_card_account_details","account_number" , accNos.get(i).toString());
			}
		}
		deleteInTable("bank_accounts", "external_users_id", extUserID.toString());
		
    }
    
    public void deleteEmployee(String userID)
    {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
         InternalUserDaoImpl internalCustomer = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
         InternalUser employee = internalCustomer.getUserById(Integer.parseInt(userID));
         deleteInTable("authorization_pending", "internal_userID", userID);
         deleteInTable("authorization_completed", "internal_userID", userID);
         deleteInTable("internal_log", "userid", userID);
         deleteInTable("otp_table", "userEmail", employee.getEmail());
         deleteInTable("user_attempts", "username", employee.getEmail());
         deleteInTable("users", "username", employee.getEmail());
         deleteInTable("internal_user", "id", userID.toString());
         ctx.close();
    }

}

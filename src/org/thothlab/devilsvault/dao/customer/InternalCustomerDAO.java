package org.thothlab.devilsvault.dao.customer;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository ("CustomerDAOForInternal")
public class InternalCustomerDAO {
	private JdbcTemplate jdbcTemplate;
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

}

package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;

public class CreditCardAccMapper implements RowMapper<CreditAccount>  {

	@Override
	public CreditAccount mapRow(ResultSet rs, int arg1) throws SQLException {
		CreditAccount obj = new CreditAccount();
		int external_users_id = rs.getInt("external_users_id");

		CustomerDAO dao = CustomerDAOHelper.customerDAO();
		Customer user = dao.getCustomer(external_users_id);
		obj.setOwner(user);
		
		
		return obj;
	}

}

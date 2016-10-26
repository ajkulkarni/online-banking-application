package org.thothlab.devilsvault.dao.creditcard;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thothlab.devilsvault.dao.customer.CustomerAccountsDAO;
import org.thothlab.devilsvault.dao.customer.CustomerDAO;
import org.thothlab.devilsvault.dao.transaction.TransferDAO;

public class CustomerDAOHelper {
	
	private static ClassPathXmlApplicationContext getApplicationContext() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		return ctx;
	}
	
	public static CreditCardDOA creditCardDAO () {
		CreditCardDOA doa = getApplicationContext().getBean("creditCardDOA", CreditCardDOA.class);
		return doa;
	} 
	
	public static CustomerDAO customerDAO () {
		
		return getApplicationContext().getBean("customerDAO", CustomerDAO.class);
	}
	
	public static CustomerAccountsDAO customerAccountsDAO () {
		
		return getApplicationContext().getBean("CustomerAccountsDAO", CustomerAccountsDAO.class);
	}

	public static TransferDAO transferDAO () {
		return getApplicationContext().getBean("transferDAO", TransferDAO.class);
	}

}

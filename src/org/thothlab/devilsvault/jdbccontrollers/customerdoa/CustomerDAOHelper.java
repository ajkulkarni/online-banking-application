package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerDAOHelper {
	
	private static ClassPathXmlApplicationContext getApplicationContext() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
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

}

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
	
	public static TransferDAO transferDAO () {
		TransferDAO dao = getApplicationContext().getBean("transferDAO", TransferDAO.class);
		return dao;
	}
	
	public static CustomerDAO customerDAO () {
		
		return getApplicationContext().getBean("customerDAO", CustomerDAO.class);
	}

}

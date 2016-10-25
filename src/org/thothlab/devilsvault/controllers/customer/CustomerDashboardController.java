package org.thothlab.devilsvault.controllers.customer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.customer.ExtUserDaoImpl;
import org.thothlab.devilsvault.db.model.BankAccount.AccountType;
import org.thothlab.devilsvault.db.model.CreditAccount;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.DebitAccount;

@Controller
public class CustomerDashboardController {
	
	@RequestMapping("/customer/home")
	public ModelAndView customerHome(){
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		Customer customer = new Customer();
		customer.setId(101);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
		Double SavingsAccBal = CustomerDAO.getSavingsBalance(customer);
		Double CheckingAcctBal = CustomerDAO.getCheckingBalance(customer);
		ctx.close();
		ModelAndView model = new ModelAndView("customerPages/customerDashboard");
		model.addObject("Customer",customer);
		model.addObject("cAccount", checkingAccount );
		model.addObject("sAccount", savingAccount );
		model.addObject("ccAccount", creditAccount );
		model.addObject("SavingsAccBal",SavingsAccBal);
		model.addObject("CheckingAccBal", CheckingAcctBal);
		return model;
	}
}

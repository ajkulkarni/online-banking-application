package org.thothlab.devilsvault.customercontrollers;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.DebitAccount;
import org.thothlab.devilsvault.CustomerDAO.ExtUserDao;
import org.thothlab.devilsvault.CustomerDAO.ExtUserDaoImpl;
import org.thothlab.devilsvault.CustomerModel.BankAccount.AccountType;
@Controller
public class CustomerDashboardController {
	
	@RequestMapping("/customerhome")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/customerDashboard");
		
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		Customer customer = new Customer();
		customer.setID(101);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExtUserDao CustomerDAO = ctx.getBean("requestDAOExternal", ExtUserDaoImpl.class);
		//ExtUserDao CustomerDAO = new ExtUserDaoImpl(Datasource datasource);
		CustomerDAO.ViewBalance(customer);
		model.addObject("Customer",customer);
		model.addObject("cAccount", checkingAccount );
		model.addObject("sAccount", savingAccount );
		model.addObject("ccAccount", creditAccount );
		return model;
	}

	@RequestMapping("/accounts_savings")
	public ModelAndView hellosavings(){
		ModelAndView model = new ModelAndView("customerPages/accountsSavingsPage");
		model.addObject("msg","Hello Accounts");
		return model;
	}

	@RequestMapping("/accounts_checkings")
	public ModelAndView hellocheckings(){
		ModelAndView model = new ModelAndView("customerPages/accountsCheckingsPage");
		model.addObject("msg","Hello Checkings");
		return model;
	}
}

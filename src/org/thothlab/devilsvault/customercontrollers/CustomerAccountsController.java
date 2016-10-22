package org.thothlab.devilsvault.customercontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.DebitAccount;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.CustomerModel.BankAccount.AccountType;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardDOA;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerAccountsDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.ExtUserDaoImpl;

@Controller
public class CustomerAccountsController {
	
	@RequestMapping("/customerSavingsAccount")
	public ModelAndView helloworld(){
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		Customer customer = new Customer();
		customer.setID(101);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
		Double SavingsAccBal = CustomerDAO.getSavingsBalance(customer);
		CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
		List<TransactionModel> TransactionLines = new ArrayList<TransactionModel>();
		TransactionLines = sAccountDAO.getTransactionLines(customer, 3);
		ctx.close();
		ModelAndView model = new ModelAndView("customerPages/accountsSavingsPage");
		model.addObject("Customer",customer);
		model.addObject("cAccount", checkingAccount );
		model.addObject("sAccount", savingAccount );
		model.addObject("ccAccount", creditAccount );
		model.addObject("SavingsAccBal",SavingsAccBal);
		model.addObject("TransactionLines",TransactionLines);
		return model;
	}
	@RequestMapping("/customerCheckingAccount")
	public ModelAndView checkingAccount(){
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		Customer customer = new Customer();
		customer.setID(101);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
		Double CheckingAcctBal = CustomerDAO.getCheckingBalance(customer);
		CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
		List<TransactionModel> TransactionLines = new ArrayList<TransactionModel>();
		TransactionLines = sAccountDAO.getTransactionLines(customer, 1);
		ctx.close();
		ModelAndView model = new ModelAndView("customerPages/accountsCheckingsPage");
		model.addObject("Customer",customer);
		model.addObject("cAccount", checkingAccount );
		model.addObject("sAccount", savingAccount );
		model.addObject("ccAccount", creditAccount );
		model.addObject("CheckingAccBal", CheckingAcctBal);
		model.addObject("TransactionLines",TransactionLines);
		return model;
	}
	
	@RequestMapping(value="getcheckingTransactions", method = RequestMethod.POST) 
	public ModelAndView getSearchResultViaAjax(@RequestParam("monthPicker") String interval) {
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		Customer customer = new Customer();
		customer.setID(101);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
		Double CheckingAcctBal = CustomerDAO.getCheckingBalance(customer);
		CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
		List<TransactionModel> TransactionLines = new ArrayList<TransactionModel>();
		if (interval.equals("Last one month")) {
		TransactionLines = sAccountDAO.getTransactionLines(customer, 1);
		}
		else if (interval.equals("Last 3 months")) {
			TransactionLines = sAccountDAO.getTransactionLines(customer, 3);
		}
		else if (interval.equals("Last 6 months")) {
			TransactionLines = sAccountDAO.getTransactionLines(customer, 6);
		}		
		ctx.close();
		ModelAndView model = new ModelAndView("customerPages/accountsCheckingsPage");
		model.addObject("Customer",customer);
		model.addObject("cAccount", checkingAccount );
		model.addObject("sAccount", savingAccount );
		model.addObject("ccAccount", creditAccount );
		model.addObject("CheckingAccBal", CheckingAcctBal);
		model.addObject("TransactionLines",TransactionLines);
		return model;
		
		
	}
}

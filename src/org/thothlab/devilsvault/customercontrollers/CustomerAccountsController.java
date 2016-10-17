package org.thothlab.devilsvault.customercontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerDAO.*;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.DebitAccount;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.CustomerModel.TransactionModel2;
import org.thothlab.devilsvault.CustomerModel.BankAccount.AccountType;

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
		List<TransactionModel2> TransactionLines = new ArrayList<TransactionModel2>();
		TransactionLines = sAccountDAO.getTransactionLines(customer, 1);
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
		List<TransactionModel2> TransactionLines = new ArrayList<TransactionModel2>();
		TransactionLines = sAccountDAO.getTransactionLines(customer, 1);
		ctx.close();
		ModelAndView model = new ModelAndView("customerPages/accountsCheckingPage");
		model.addObject("Customer",customer);
		model.addObject("cAccount", checkingAccount );
		model.addObject("sAccount", savingAccount );
		model.addObject("ccAccount", creditAccount );
		model.addObject("CheckingAccBal", CheckingAcctBal);
		model.addObject("TransactionLines",TransactionLines);
		return model;
	}
}

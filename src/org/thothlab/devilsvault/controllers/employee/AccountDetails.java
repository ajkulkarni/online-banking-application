package org.thothlab.devilsvault.controllers.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.controllers.security.ExceptionHandlerClass;
import org.thothlab.devilsvault.dao.customer.CustomerAccountsDAO;
import org.thothlab.devilsvault.dao.customer.ExtUserDaoImpl;
import org.thothlab.devilsvault.dao.log.LogDaoImpl;
import org.thothlab.devilsvault.db.model.BankAccountDB;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.DatabaseLog;
import org.thothlab.devilsvault.db.model.Transaction;

@Controller
public class AccountDetails {
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
		
	}
	
	@ExceptionHandler(ExceptionHandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/raiseexception";
    }
	
	@RequestMapping(value="/employee/viewsavingsaccount",method = RequestMethod.POST)
	public ModelAndView SavingAccountInternal(HttpServletRequest request,@RequestParam("savingsPicker") String interval,@RequestParam("extUserID") String extUserID)
	{
		try{
			ClassPathXmlApplicationContext ctx_log = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		       LogDaoImpl logDao = ctx_log.getBean("DatabaseLogDao", LogDaoImpl.class);
		       DatabaseLog dblog = new DatabaseLog();
				setGlobals(request);
				BankAccountDB savingAccount = new BankAccountDB();
				savingAccount.setExternal_user_id(Integer.parseInt(extUserID));
				Customer customer = new Customer();
				customer.setId(Integer.parseInt(extUserID));
				ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
				ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
				savingAccount = CustomerDAO.getAccount(customer, savingAccount,"SAVINGS");
				CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
				List<Transaction> TransactionLines = new ArrayList<Transaction>();
				if (interval.equals("Last month")) 
				{
					TransactionLines = sAccountDAO.getTransactionLines(savingAccount.getAccount_number(), 1);
				}else if (interval.equals("Last 3 months")) 
				{
					TransactionLines = sAccountDAO.getTransactionLines(savingAccount.getAccount_number(), 3);
				}else if (interval.equals("Last 6 months")) 
				{
					TransactionLines = sAccountDAO.getTransactionLines(savingAccount.getAccount_number(), 6);
				}else
				{
					TransactionLines = sAccountDAO.getTransactionLines(savingAccount.getAccount_number(), 1);
				}
				ctx.close();
				ModelAndView model = new ModelAndView("employeePages/InternalaccountsSavingsPage");
				model.addObject("customer",customer);
				model.addObject("savingsAccount", savingAccount );
				model.addObject("TransactionLines",TransactionLines);
			    dblog.setActivity("Savings account" +savingAccount.getAccount_number() );
			    dblog.setDetails("Internal User accessing savings account");
			    dblog.setUserid((int) request.getSession().getAttribute("userID"));
			    logDao.save(dblog, "external_log");
			    ctx_log.close();
				return model;
		}catch (Exception e){
			throw new ExceptionHandlerClass(); 
		}
	   
	}
	
	@RequestMapping(value="/employee/viewcheckingaccount",	method = RequestMethod.POST)
	public ModelAndView CheckingAccountInternal(HttpServletRequest request,@RequestParam("checkingPicker") String interval,@RequestParam("extUserID") String extUserID){
		try{
			ClassPathXmlApplicationContext ctx_log = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		       LogDaoImpl logDao = ctx_log.getBean("DatabaseLogDao", LogDaoImpl.class);
		       DatabaseLog dblog = new DatabaseLog();
			BankAccountDB checkingAccount = new BankAccountDB();
			Customer customer = new Customer();
			customer.setId(Integer.parseInt(extUserID));
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
			checkingAccount = CustomerDAO.getAccount(customer, checkingAccount,"CHECKING");
			CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
			List<Transaction> TransactionLines = new ArrayList<Transaction>();
			if (interval.equals("Last month")) 
			{
				TransactionLines = sAccountDAO.getTransactionLines(checkingAccount.getAccount_number(), 1);
			}else if (interval.equals("Last 3 months")) 
			{
				TransactionLines = sAccountDAO.getTransactionLines(checkingAccount.getAccount_number(), 3);
			}else if (interval.equals("Last 6 months")) 
			{
				TransactionLines = sAccountDAO.getTransactionLines(checkingAccount.getAccount_number(), 6);
			}else
			{
				TransactionLines = sAccountDAO.getTransactionLines(checkingAccount.getAccount_number(), 1);
			}
			ctx.close();
			
			ModelAndView model = new ModelAndView("employeePages/InternalaccountsCheckingPage");
			model.addObject("customer",customer);
			model.addObject("checkingAccount", checkingAccount );
			model.addObject("TransactionLines",TransactionLines);
		    dblog.setActivity("Checking account" +checkingAccount.getAccount_number() );
		    dblog.setDetails("Internal User accessing checking account");
		    dblog.setUserid((int) request.getSession().getAttribute("userID"));
		    logDao.save(dblog, "external_log");
		    ctx_log.close();
			return model;
		}catch (Exception e){
			throw new ExceptionHandlerClass(); 
		}
		   
	}
}

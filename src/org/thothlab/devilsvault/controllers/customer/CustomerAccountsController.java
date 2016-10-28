package org.thothlab.devilsvault.controllers.customer;

import java.math.BigDecimal;
import java.sql.SQLException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thothlab.devilsvault.controllers.security.ExceptionHandlerClass;
import org.thothlab.devilsvault.dao.customer.CustomerAccountsDAO;
import org.thothlab.devilsvault.dao.customer.CustomerDAO;
import org.thothlab.devilsvault.dao.customer.ExtUserDaoImpl;
import org.thothlab.devilsvault.dao.request.ExternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.request.InternalRequestDaoImpl;
import org.thothlab.devilsvault.db.model.BankAccountDB;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.Transaction;

@Controller
public class CustomerAccountsController {
	
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
	
	@RequestMapping("/customer/SavingsAccount")
	public ModelAndView SavingAccount(HttpServletRequest request,@RequestParam("savingsPicker") String interval)
	{
		setGlobals(request);
		try{
			BankAccountDB savingAccount = new BankAccountDB();
			savingAccount.setExternal_user_id(userID);
			Customer customer = new Customer();
			customer.setId(userID);
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
			ModelAndView model = new ModelAndView("customerPages/accountsSavingsPage");
			model.addObject("customer",customer);
			model.addObject("savingsAccount", savingAccount );
			model.addObject("TransactionLines",TransactionLines);
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	
	@RequestMapping("/customer/CheckingAccount")
	public ModelAndView CheckingAccount(HttpServletRequest request,@RequestParam("checkingPicker") String interval){
		setGlobals(request); 
		try{
			BankAccountDB checkingAccount = new BankAccountDB();
			Customer customer = new Customer();
			customer.setId(userID);
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
			
			ModelAndView model = new ModelAndView("customerPages/accountsCheckingsPage");
			model.addObject("Customer",customer);
			model.addObject("checkingAccount", checkingAccount );
			model.addObject("TransactionLines",TransactionLines);
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	
	@RequestMapping(value ="/customer/addMoney")
	public ModelAndView addMoney(HttpServletRequest request,@RequestParam("amount") BigDecimal amount
	,@RequestParam("accountType") String accountType)throws SQLException
	{
		setGlobals(request);
		try{
			BankAccountDB account = new BankAccountDB();
			BankAccountDB savingsAccount = new BankAccountDB();
			BankAccountDB checkingAccount = new BankAccountDB();
			Customer customer = new Customer();
			customer.setId(userID);
			String modelName = "";
			String errorMessage = "";
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
			account = CustomerDAO.getAccount(customer, account,accountType);
			savingsAccount = CustomerDAO.getAccount(customer, savingsAccount,"SAVINGS");
			checkingAccount = CustomerDAO.getAccount(customer, checkingAccount,"CHECKING");
			if(accountType == null || accountType == "" || amount == null || amount.doubleValue() <= 0)
			{
				modelName = "customerPages/add_money_customer";
				errorMessage = "Operation failed. Please choose the correct account type and amount";
			}else
			{
				if(amount.doubleValue() <= 1000d)
				{
					CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
					boolean transactonstatus;
					Transaction transaction = new Transaction();
					transactonstatus = sAccountDAO.addMoney(customer,account,transaction, amount, accountType);
					if(transactonstatus == true)
					{
						modelName = "customerPages/accountssuccesspage";
						errorMessage = "Amount deposited successfully";
					}
					else
					{
						errorMessage = "Operation failed";
						modelName = "customerPages/accountserrorpage";
					}
					
				}else
					{
						errorMessage = "Deposit unsuccessful. Entered amount exceeded deposit limit ($1000)";
						modelName = "customerPages/accountserrorpage";
					}
				ctx.close();
			}
			ModelAndView model = new ModelAndView(modelName);
			model.addObject("customer",customer);
			model.addObject("account", account );
			model.addObject("savingsAccount",savingsAccount);
			model.addObject("checkingAccount",checkingAccount);
			model.addObject("errorMessage", errorMessage );
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	@RequestMapping(value ="/customer/withdrawMoney")
	public ModelAndView withdrawMoney(HttpServletRequest request,@RequestParam("amount") BigDecimal amount
	,@RequestParam("accountType") String accountType) throws SQLException
	{
		setGlobals(request);
		try{
			BankAccountDB account = new BankAccountDB();
			BankAccountDB savingsAccount = new BankAccountDB();
			BankAccountDB checkingAccount = new BankAccountDB();
			Customer customer = new Customer();
			customer.setId(userID);
			String modelName = "";
			String errorMessage = "";
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
			account = CustomerDAO.getAccount(customer, account,accountType);
			savingsAccount = CustomerDAO.getAccount(customer, savingsAccount,"SAVINGS");
			checkingAccount = CustomerDAO.getAccount(customer, checkingAccount,"CHECKING");		if(accountType == null || accountType == "" || amount == null || amount.doubleValue() <= 0)
			{
				modelName = "customerPages/withdraw_money_customer";
				errorMessage = "Operation failed. Please choose the correct account type and amount";
			}else
			{
				if(amount.doubleValue() <= account.getBalance().doubleValue() && amount.doubleValue()<=1000)
				{
					CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
					boolean transactonstatus;
					Transaction transaction = new Transaction();
					transactonstatus = sAccountDAO.withdrawMoney(customer,account,transaction, amount, accountType);
					if(transactonstatus == true)
					{
						modelName = "customerPages/accountssuccesspage";
						errorMessage = "Money withdrawn successfully";
					}else
					{
						errorMessage = "Operation failed";
						modelName = "customerPages/accountserrorpage";
					}
					
				}else
				{	
					if(amount.doubleValue()>1000)
						errorMessage = "Withdraw unsuccessful. Entered amount exceeded withdrawal limit ($1000)";
					else
						errorMessage = "No sufficient balance";
					
				modelName = "customerPages/accountserrorpage";
			}
			ctx.close();
			}
			ModelAndView model = new ModelAndView(modelName);
			model.addObject("customer",customer);
			model.addObject("account", account );
			model.addObject("savingsAccount",savingsAccount);
			model.addObject("checkingAccount",checkingAccount);
			model.addObject("errorMessage", errorMessage );
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	
	@RequestMapping("/customer/addMoneyOption")
	public ModelAndView addMoneyOption(HttpServletRequest request){
		setGlobals(request);
		try{
			BankAccountDB checkingAccount = new BankAccountDB();
			checkingAccount.setExternal_user_id(userID);
			BankAccountDB savingsAccount = new BankAccountDB();
			savingsAccount.setExternal_user_id(userID);
			Customer customer = new Customer();
			customer.setId(userID);
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
			checkingAccount = CustomerDAO.getAccount(customer, checkingAccount, "CHECKING");
			savingsAccount = CustomerDAO.getAccount(customer, savingsAccount, "SAVINGS");
			System.out.println("SA Bal:"+savingsAccount.getBalance());
			ctx.close();
			ModelAndView model = new ModelAndView("customerPages/add_money_customer");
			model.addObject("checkingAccount", checkingAccount );
			model.addObject("savingsAccount", savingsAccount );
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	@RequestMapping("/customer/withdrawMoneyOption")
	public ModelAndView addMoneyPage(HttpServletRequest request){
		setGlobals(request);
		try{
			BankAccountDB checkingAccount = new BankAccountDB();
			checkingAccount.setExternal_user_id(userID);
			BankAccountDB savingsAccount = new BankAccountDB();
			savingsAccount.setExternal_user_id(userID);
			Customer customer = new Customer();
			customer.setId(userID);
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
			checkingAccount = CustomerDAO.getAccount(customer, checkingAccount, "CHECKING");
			savingsAccount = CustomerDAO.getAccount(customer, savingsAccount, "SAVINGS");
			System.out.println("SA Bal:"+savingsAccount.getBalance());
			ctx.close();
			ModelAndView model = new ModelAndView("customerPages/withdraw_money_customer");
			model.addObject("checkingAccount", checkingAccount );
			model.addObject("savingsAccount", savingsAccount );
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	
	@RequestMapping("/customer/accountsBalance")
	public ModelAndView helloworld(HttpServletRequest request){
		setGlobals(request);
		try{
			BankAccountDB checkingAccount = new BankAccountDB();
			checkingAccount.setExternal_user_id(userID);
			BankAccountDB savingsAccount = new BankAccountDB();
			savingsAccount.setExternal_user_id(userID);
			Customer customer = new Customer();
			customer.setId(userID);
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
			checkingAccount = CustomerDAO.getAccount(customer, checkingAccount, "CHECKING");
			savingsAccount = CustomerDAO.getAccount(customer, savingsAccount, "SAVINGS");
			System.out.println("SA Bal:"+savingsAccount.getBalance());
			ctx.close();
			ModelAndView model = new ModelAndView("customerPages/accountsAddWithdrawBalance");
			model.addObject("checkingAccount", checkingAccount );
			model.addObject("savingsAccount", savingsAccount );
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	
	@RequestMapping(value ="/customer/add_Withdraw_Money_home")
	public ModelAndView addMoneyHome(HttpServletRequest request)
	{
		setGlobals(request);
		try{
			BankAccountDB savingAccount = new BankAccountDB();
			BankAccountDB checkingAccount = new BankAccountDB();
			Customer customer = new Customer();
			customer.setId(userID);
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
			savingAccount = CustomerDAO.getAccount(customer, savingAccount,"SAVINGS");
			checkingAccount = CustomerDAO.getAccount(customer, savingAccount,"CHECKING");
			ctx.close();
			ModelAndView model = new ModelAndView("customerPages/accountsAddWithdrawBalance");
			model.addObject("customer",customer);
			model.addObject("savingAccount", savingAccount );
			model.addObject("checkingAccount", checkingAccount );
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	
	@RequestMapping(value="/customer/processrequestexternal", method = RequestMethod.POST)
    public ModelAndView PendingRequestRejectContoller(HttpServletRequest request,RedirectAttributes redir, @RequestParam("requestID") String requestID, @RequestParam("action") String action){
        try{
        	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
            setGlobals(request);
            ModelAndView model = new ModelAndView();
            String msg = "";
            if(action.equals("approve")) {
                ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
                externalRequestDao.approveRequest(Integer.parseInt(requestID), "external", userID);    
                msg = "Request Approved!";
            }
            else {
                ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
                externalRequestDao.rejectRequest(Integer.parseInt(requestID), "external", userID);
                msg="Request Rejected!";
            } 
           ctx.close();
           model.setViewName("redirect:/customer/pendingrequest");
           redir.addFlashAttribute("error_msg",msg);
           return model;
        }catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
    }
    
    @RequestMapping("/customer/pendingrequest")
    public ModelAndView PendingRequestContoller(HttpServletRequest request){
       try{
    	   setGlobals(request);
           ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
          ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
          ModelAndView model = new ModelAndView("customerPages/PendingRequest");
          List<Request> external_list =externalRequestDao.getAllRequestToApprove(userID);
          if(external_list.size() < 1)
          {
              external_list = new ArrayList<Request>();
          }
          model.addObject("external_list",external_list);
          ctx.close();
          return model;
       }catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
    	
       
    }
    
    @RequestMapping(value="/customer/addrequest", method = RequestMethod.POST)
    public ModelAndView modifyDetails(RedirectAttributes redir,@RequestParam("requestType") String requestType, HttpServletRequest request,@RequestParam("newValue") String newValue) {
         try{
        	 ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
             CustomerDAO customerDAO = ctx.getBean("customerDAO", CustomerDAO.class);
             InternalRequestDaoImpl internalrequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
             Customer customer = customerDAO.getCustomer(this.userID);
             setGlobals(request);
             ModelAndView model = new ModelAndView("redirect:/customer/userdetails");
             if(internalrequestDao.validateRequest(userID, requestType, "external_request_pending"))
             {
                 internalrequestDao.raiseInternalRequest(customer, requestType, newValue,this.userID);
                  redir.addFlashAttribute("error_msg","Request raised!!");
             }
             else
             {
                  redir.addFlashAttribute("error_msg","Request denied as already exists!!");
             }
              ctx.close();
              return model;
         }catch(Exception e){
 			throw new ExceptionHandlerClass(); 
 		}
    	 
         }
}

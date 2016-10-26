package org.thothlab.devilsvault.controllers.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thothlab.devilsvault.dao.customer.CustomerAccountsDAO;
import org.thothlab.devilsvault.dao.customer.CustomerDAO;
import org.thothlab.devilsvault.dao.customer.ExtUserDaoImpl;
import org.thothlab.devilsvault.dao.request.ExternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.request.InternalRequestDaoImpl;
import org.thothlab.devilsvault.db.model.BankAccount.AccountType;
import org.thothlab.devilsvault.db.model.CreditAccount;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.DebitAccount;
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
	
	@RequestMapping("/customer/SavingsAccount")
	public ModelAndView helloworld(){
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		Customer customer = new Customer();
		customer.setId(101);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
		Double SavingsAccBal = CustomerDAO.getSavingsBalance(customer);
		CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
		List<Transaction> TransactionLines = new ArrayList<Transaction>();
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
	@RequestMapping("/customer/CheckingAccount")
	public ModelAndView checkingAccount(){
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		Customer customer = new Customer();
		customer.setId(101);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
		Double CheckingAcctBal = CustomerDAO.getCheckingBalance(customer);
		CustomerAccountsDAO sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);
		List<Transaction> TransactionLines = new ArrayList<Transaction>();
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
	
	@RequestMapping(value="/customer/processrequestexternal", method = RequestMethod.POST)
    public ModelAndView PendingRequestRejectContoller(HttpServletRequest request,RedirectAttributes redir, @RequestParam("requestID") String requestID, @RequestParam("action") String action){
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
    }
    
    @RequestMapping("/customer/pendingrequest")
    public ModelAndView PendingRequestContoller(HttpServletRequest request){
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
    }
    
    @RequestMapping(value="/customer/addrequest", method = RequestMethod.POST)
    public ModelAndView modifyDetails(RedirectAttributes redir,@RequestParam("requestType") String requestType, HttpServletRequest request, @RequestParam("newValue") String newValue) {
         ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
         CustomerDAO customerDAO = ctx.getBean("customerDAO", CustomerDAO.class);
         InternalRequestDaoImpl internalrequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
         Customer customer = customerDAO.getCustomer(this.userID);
         setGlobals(request);
         internalrequestDao.raiseInternalRequest(customer, requestType, newValue,this.userID);
          ModelAndView model = new ModelAndView("redirect:/customer/userdetails");
          redir.addFlashAttribute("error_msg","Request Raised !!");
          ctx.close();
          return model;
         }
}

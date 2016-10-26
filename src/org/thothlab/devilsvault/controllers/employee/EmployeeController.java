package org.thothlab.devilsvault.controllers.employee;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thothlab.devilsvault.dao.authorization.AthorizationDaoImpl;
import org.thothlab.devilsvault.dao.bankaccount.BankAccountDaoImpl;
import org.thothlab.devilsvault.dao.customer.CustomerDAO;
import org.thothlab.devilsvault.dao.customer.InternalCustomerDAO;
import org.thothlab.devilsvault.dao.dashboard.PendingStatisticsDao;
import org.thothlab.devilsvault.dao.employee.InternalUserDaoImpl;
import org.thothlab.devilsvault.dao.log.LogDaoImpl;
import org.thothlab.devilsvault.dao.request.ExternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.request.InternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.transaction.InternalTransactionDaoImpl;
import org.thothlab.devilsvault.dao.transaction.TransactionDaoImpl;
import org.thothlab.devilsvault.dao.transaction.TransferDAO;
import org.thothlab.devilsvault.dao.userauthentication.UserAuthenticationDaoImpl;
import org.thothlab.devilsvault.dao.userauthentication.UserLoginManagementDaoImpl;
import org.thothlab.devilsvault.db.model.Authorization;
import org.thothlab.devilsvault.db.model.BankAccountDB;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.DatabaseLog;
import org.thothlab.devilsvault.db.model.InternalUser;
import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.Transaction;
import org.thothlab.devilsvault.db.model.UserAuthentication;

@Controller
public class EmployeeController {
	
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
		
	}
	
	@RequestMapping(value="/employee/home", method=RequestMethod.GET)
	public ModelAndView PendingDashboardContoller(HttpServletRequest request){
		setGlobals(request);	
		if (role.equalsIgnoreCase("ROLE_REGULAR")){
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
			InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
			PendingStatisticsDao pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
			List<Integer> externalIDs = internalCustomerDao.getExternalUserIds(userID, "transaction");
			List<Integer> accountNos = new ArrayList<Integer>();
			List<Transaction> transactionList = new ArrayList<Transaction>();
			if(externalIDs.size() > 0){
				accountNos = internalCustomerDao.getAccNos(externalIDs);
				transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
			}
			int transaction_count = transactionList.size();
			if(transactionList.size() > 5)
				transactionList = transactionList.subList(transactionList.size()-5, transactionList.size());
			List<Request> external_list = pendingStatisticsDao.getPendingExternalRequests();
			int request_count = external_list.size();
			if(external_list.size() > 5)
				external_list = external_list.subList(external_list.size()-5, external_list.size());
			ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
			model.addObject("transaction_count", transaction_count);
			model.addObject("external_count",request_count);
			model.addObject("transaction_list",transactionList);
			model.addObject("external_list",external_list);
			ctx.close();
			return model;
		}else if(role.equalsIgnoreCase("ROLE_MANAGER")){
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			PendingStatisticsDao pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
			HashMap<String,Integer> stats = new HashMap<String,Integer>();
			stats = pendingStatisticsDao.getPendingStatistics("ROLE_REGULAR");
			List<Request> internal_list = pendingStatisticsDao.getPendingInternalRequests("ROLE_REGULAR");
			if(internal_list.size() > 5)
				internal_list = internal_list.subList(internal_list.size()-5, internal_list.size());
			List<Request> external_list = pendingStatisticsDao.getPendingExternalRequests();
			if(external_list.size() > 5)
				external_list = external_list.subList(external_list.size()-5, external_list.size());
			List<Transaction> transaction_list = pendingStatisticsDao.getPendingTransactions();
			if(transaction_list.size() > 5)
				transaction_list = transaction_list.subList(transaction_list.size()-5, transaction_list.size());
			ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
			request.getSession().setAttribute("transaction_count", stats.get("transaction"));
			model.addObject("internal_count",stats.get("internal"));
			model.addObject("external_count",stats.get("external"));
			model.addObject("transaction_list",transaction_list);
			model.addObject("external_list",external_list);
			model.addObject("internal_list",internal_list);
			ctx.close();
			return model;
		}	
		else if(role.equalsIgnoreCase("ROLE_ADMIN")){
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			PendingStatisticsDao pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
			HashMap<String,Integer> stats = new HashMap<String,Integer>();
			stats = pendingStatisticsDao.getPendingStatistics("ROLE_REGULAR' AND role='ROLE_MANAGER");
			List<Request> internal_list = pendingStatisticsDao.getPendingInternalRequests("ROLE_REGULAR' AND role='ROLE_MANAGER");
			if(internal_list.size() > 5)
				internal_list = internal_list.subList(internal_list.size()-5, internal_list.size());
			ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
			model.addObject("internal_count",stats.get("internal"));
			model.addObject("internal_list",internal_list);
			ctx.close();
			return model;
		}else{
			ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
			return model;
		}
	}
	
	@RequestMapping(value="/employee/management", method=RequestMethod.GET)
    public ModelAndView ManagementContoller(HttpServletRequest request) throws UnsupportedEncodingException{
		setGlobals(request);
		if(role.equalsIgnoreCase("ROLE_REGULAR")){
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
            AthorizationDaoImpl authorizationDAO = ctx.getBean("AuthorizationDao", AthorizationDaoImpl.class);
            List<Authorization> pendingList = authorizationDAO.getAllPendingAuthorization(userID);
            ctx.close();
            List<Authorization> completeList = authorizationDAO.getAllCompleteAuthorization(userID);
            ModelAndView model = new ModelAndView("employeePages/UserManagementRegular");
            model.addObject("pendingList",pendingList);
            model.addObject("completeList",completeList);
            return model;
		}else if (role.equalsIgnoreCase("ROLE_MANAGER")){
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	        AthorizationDaoImpl authorizationDAO = ctx.getBean("AuthorizationDao", AthorizationDaoImpl.class);
	        List<Authorization> pendingList = authorizationDAO.getAllPendingAuthorizationManager();
	        ctx.close();
	        List<Authorization> completeList = authorizationDAO.getAllCompleteAuthorizationManager();
	        ModelAndView model = new ModelAndView("employeePages/UserManagementManager");
	        model.addObject("pendingList",pendingList);
	        model.addObject("completeList",completeList);
	        return model;
		}else{
	        ModelAndView model = new ModelAndView("employeePages/UserManagementAdmin");
	        return model;
		}
    }
	
	@RequestMapping(value="/employee/searchexternaluser", method = RequestMethod.POST)
    public ModelAndView SearchExternalUser(RedirectAttributes redir, @RequestParam("customerID") String id) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        CustomerDAO customerdao = ctx.getBean("customerDAO",CustomerDAO.class);
        Customer customer = new Customer(); 
        customer = customerdao.getCustomer(Integer.parseInt(id));
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/employee/management");
        redir.addFlashAttribute("customerObj",customer);
        ctx.close();
        return model;
    }
	
	@RequestMapping(value="/employee/searchinternaluser", method = RequestMethod.POST)
    public ModelAndView SearchInternalUser(@RequestParam("employeeID") String id,RedirectAttributes redir) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalUserDaoImpl internalDao = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
        InternalUser user = new InternalUser();
        user = internalDao.getUserById(Integer.valueOf(id));
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/employee/management");
        redir.addFlashAttribute("employeeObj",user);
        ctx.close();
        return model;
    }

		
	@RequestMapping(value = "/employee/viewtransaction", method = RequestMethod.POST)
	public ModelAndView viewTransactions(@RequestParam("extUserID") String extuserID, HttpServletRequest request) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
		
		
		Integer payerID = Integer.parseInt(extuserID);
		TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
		List<Integer> currentUserAccounts  = transferDAO.getMultipleAccounts(payerID);
		List<String> userAccounts=new ArrayList<>();
		for(Integer currentElements: currentUserAccounts){
			transferDAO.getPayerAccounts(currentElements,userAccounts);
		}
		request.getSession().setAttribute("userAccounts", userAccounts);
			
		List<Integer> extuserIDs = new ArrayList<Integer>();
		extuserIDs.add(Integer.parseInt(extuserID));
		List<Integer> accountNos = new ArrayList<Integer>();
		List<Transaction> transactionList = new ArrayList<Transaction>();
		accountNos = internalCustomerDao.getAccNos(extuserIDs);
		
		InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		if(accountNos.size() > 0){
			transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
		}
		
		ModelAndView model = new ModelAndView("employeePages/AccountTransactions");
		model.addObject("transactionList", transactionList);
		model.addObject("extUserID", extuserID);
		model.addObject("userAccounts", userAccounts);
		
		ctx.close();
		return model;
	}

	
	@RequestMapping(value = "/employee/processAccTransaction", method = RequestMethod.POST)
	public ModelAndView processAccTransactions(@RequestParam("transactionID") String transactionID,
			@RequestParam("requestType") String requestType, @RequestParam("extUserID") String extuserID, HttpServletRequest request) {
		setGlobals(request);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		
		
		//Fetch transaction from transaction_pending
		Transaction transaction = transactionDao.getById(Integer.parseInt(transactionID), "transaction_pending").get(0);
		java.sql.Timestamp createdDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
		transaction.setApprover(""+userID);
		transaction.setStatus(requestType);
		transaction.setTimestamp_created(createdDateTime);
		transaction.setTimestamp_updated(createdDateTime);
		boolean transactionSaved = transactionDao.saveToCompleted(transaction, "transaction_completed");
		
		if(!transactionSaved){
			ModelAndView model = new ModelAndView("employeePages/AccountTransactions");
			model.addObject("error_msg", "Transaction could not be processed!");
			ctx.close();
			return model;
		}
		
		//update amount
		if(requestType.equalsIgnoreCase("Approve")){
			transactionDao.updatePayerBalance(transaction.getPayer_id(), transaction.getAmount());
			transactionDao.updatePayeeBalance(transaction.getPayee_id(), transaction.getAmount());
			transactionDao.updateHold(transaction.getPayer_id(), transaction.getAmount());
		}
		else if(requestType.equalsIgnoreCase("Reject"))
			transactionDao.updateHold(transaction.getPayer_id(), transaction.getAmount());
		
		boolean transactionDeleted = transactionDao.deleteById(Integer.parseInt(transactionID), "transaction_pending");
		
		InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
		List<Integer> extuserIDs = new ArrayList<Integer>();
		extuserIDs.add(Integer.parseInt(extuserID));
		//extuserIDs.add(1);
		List<Integer> accountNos = internalCustomerDao.getAccNos(extuserIDs);
		
		List<Transaction> transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
		ModelAndView model = new ModelAndView("employeePages/AccountTransactions");
		model.addObject("transactionList", transactionList);
		model.addObject("extUserID", extuserID);
		model.addObject("error_msg", "Transaction processed successfully!");
		model.addObject("success", true);
		ctx.close();
		return model;
	}
	
	@RequestMapping(value = "/employee/processtransaction", method = RequestMethod.POST)
	public ModelAndView processTransactions(RedirectAttributes redir, @RequestParam("transactionID") String transactionID,
			@RequestParam("requestType") String requestType, @RequestParam("extUserID") String extuserID, HttpServletRequest request) {
		setGlobals(request);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		
		String msg="";
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/employee/transaction");
		//Fetch transaction from transaction_pending
		Transaction transaction = transactionDao.getById(Integer.parseInt(transactionID), "transaction_pending").get(0);
		java.sql.Timestamp createdDateTime = new java.sql.Timestamp(new java.util.Date().getTime());
		transaction.setApprover(""+userID);
		transaction.setStatus(requestType);
		transaction.setTimestamp_created(createdDateTime);
		transaction.setTimestamp_updated(createdDateTime);
		boolean transactionSaved = transactionDao.saveToCompleted(transaction, "transaction_completed");
		
		if(!transactionSaved){
			
			msg = "Transaction could not be processed!";
			redir.addFlashAttribute("error_msg",msg);
			ctx.close();
			return model;
		}
		
		//update amount
		if(requestType.equalsIgnoreCase("Approve")){
			transactionDao.updatePayerBalance(transaction.getPayer_id(), transaction.getAmount());
			transactionDao.updatePayeeBalance(transaction.getPayee_id(), transaction.getAmount());
			transactionDao.updateHold(transaction.getPayer_id(), transaction.getAmount());
		}
		else if(requestType.equalsIgnoreCase("Reject"))
			transactionDao.updateHold(transaction.getPayer_id(), transaction.getAmount());
		
		boolean transactionDeleted = transactionDao.deleteById(Integer.parseInt(transactionID), "transaction_pending");
		
		redir.addFlashAttribute("error_msg","Transaction processed successfully!");
		redir.addFlashAttribute("success",true);
		ctx.close();
		return model;
	}
	
	@RequestMapping(value = "/employee/newaccTransaction", method = RequestMethod.POST)
	public ModelAndView newAccTransactions(@RequestParam("accountNo") String accountNo,
			@RequestParam("receiverAccount") String receiverAccount, @RequestParam("amount") String amount,
			@RequestParam("extUserID") String extuserID, HttpServletRequest request) throws ParseException {
		setGlobals(request);
		List<String> userAccounts = (List<String>) request.getSession().getAttribute("userAccounts");

		System.out.println("extUSERID"+extuserID);
		
		
		ModelAndView model = new ModelAndView("employeePages/AccountTransactions");
		System.out.println(accountNo+":"+receiverAccount+":"+amount+":"+extuserID);
		BigDecimal amountSent = new BigDecimal(amount.replaceAll(",", ""));
		int payerAccountNumber = Integer.parseInt(accountNo.split(":")[0].trim());
		String payerAccountType = accountNo.split(":")[1].trim();
		int receiverAcountNumber = Integer.parseInt(receiverAccount);
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
		List<Integer> extuserIDs = new ArrayList<Integer>();
		extuserIDs.add(Integer.parseInt(extuserID));
		List<Integer> accountNos = internalCustomerDao.getAccNos(extuserIDs);
		InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		List<Transaction> transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
		model.addObject("transactionList", transactionList);
		model.addObject("extUserID", extuserID);
		
		TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
		boolean receiverAccountExists = transferDAO.checkAccountExists(receiverAcountNumber);
		System.out.println(receiverAccountExists);
		if(!(userAccounts.contains(payerAccountNumber+":"+payerAccountType) )){
			model.addObject("success", false);
			model.addObject("error_msg", "Sorry! Your payment was rejected. Invalid payer account chosen!");
			ctx.close();
			return model;
		}	
		boolean amountValid = transferDAO.validateAmount(payerAccountNumber,amountSent);
		System.out.println(amountValid);

		if(!receiverAccountExists){
			model.addObject("success", false);
			model.addObject("error_msg", "Sorry! Your payment was rejected. Invalid payee account chosen!");
			ctx.close();
			return model;			
		}
		
		if(!(amount.replaceAll(",", "").matches("^(\\d+\\.)?\\d+$")) || amount.isEmpty()){
			model.addObject("success", false);
			model.addObject("error_msg", "Sorry! Your payment was rejected. Invalid Amount!");
			ctx.close();
			return model;
			
		}
		
		if(!amountValid){
			System.out.println("Inadequate balance!");
			model.addObject("success", false);
			model.addObject("error_msg", "Sorry! Your payment was rejected. Insufficient balance!");
			ctx.close();
			return model;
		}
		
		if(amountSent.compareTo(new BigDecimal("1000")) == 1){
			model.addObject("success", false);
			model.addObject("error_msg", "Sorry! Payment should not exceed $1000!");
			ctx.close();	
			return model;
		}
		
		TransactionDaoImpl extTransactionDAO = ctx.getBean("TransactionDao", TransactionDaoImpl.class);

		String description = "Transferred "+amount+"$ from Account:"+payerAccountNumber+" to Account:"+receiverAcountNumber+"";
		
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber,amountSent,receiverAcountNumber, description, "Employee Generated");
		extTransferTrans.setApprover(""+userID);
		
		//save to pending first
		extTransactionDAO.save(extTransferTrans, "transaction_pending");
		System.out.println("Pending id : " + extTransferTrans.getId());
		
		//save to completed now
		boolean transComplete = extTransactionDAO.saveToCompleted(extTransferTrans, "transaction_completed");
		if(transComplete){
			extTransactionDAO.updatePayerBalance(payerAccountNumber, amountSent);
			extTransactionDAO.updatePayeeBalance(receiverAcountNumber, amountSent);
		}
		
		//delete from pending
		extTransactionDAO.deleteById(extTransferTrans.getId(), "transaction_pending");
		
		//transferDAO.updateHold(payerAccountNumber,amountSent);
	
		model.addObject("transactionList", transactionList);
		model.addObject("extUserID", extuserID);
		model.addObject("error_msg", "Your payment was successful!");
		model.addObject("success", true);
		ctx.close();
		return model;
	}
	
		
	@RequestMapping(value="/employee/viewaccountdetails", method = RequestMethod.POST)
	public ModelAndView viewExtAccountDetails(RedirectAttributes redir, HttpServletRequest request,@RequestParam("extUserID") String extuserID, @RequestParam("userType") String userType) {
	setGlobals(request);
	String msg="";
	if(role.equalsIgnoreCase("ROLE_MANAGER") || role.equalsIgnoreCase("ROLE_REGULAR")){
		if (!userType.equalsIgnoreCase("external")){
			msg = "You are only authorized to view customer accounts !!";
			ModelAndView model = new ModelAndView();
			model.setViewName("redirect:/employee/management");
			redir.addFlashAttribute("message",msg);
			return model;
		}
	}
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	ModelAndView model = new ModelAndView("employeePages/ExtAccountDetails");
	if(userType.equalsIgnoreCase("internal"))
	{
		InternalUserDaoImpl internaluserdeoimpl = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
		InternalUser employee = internaluserdeoimpl.getUserById(Integer.parseInt(extuserID));
		if(employee == null)
		{
			employee = new InternalUser();
		}
		model.addObject("extUserObj",employee);		
	}
	else
	{
		CustomerDAO customerdao = ctx.getBean("customerDAO",CustomerDAO.class);
		BankAccountDaoImpl bankaccountdaoimpl = ctx.getBean("bankAccountDao",BankAccountDaoImpl.class);
		List <BankAccountDB> account_list_new = bankaccountdaoimpl.getAccountDetailsById(Integer.parseInt(extuserID));
		if(account_list_new.size() < 1)
		{
			account_list_new = new ArrayList<BankAccountDB>(); 
		}
		Customer customer = customerdao.getCustomer(Integer.parseInt(extuserID));
		model.addObject("extUserObj",customer);
		model.addObject("account_list",account_list_new);
	}
	model.addObject("userType", userType);
	ctx.close();
	return model;
}
	
    @RequestMapping(value="/employee/addrequest", method = RequestMethod.POST)
    public ModelAndView modifyDetails(RedirectAttributes redir,@RequestParam("userID") String userID ,@RequestParam("requestType") String requestType, HttpServletRequest request, @RequestParam("userType") String userType,@RequestParam("newValue") String newValue) {
		ModelAndView model = new ModelAndView();
		if(userType.equalsIgnoreCase("external"))
		{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		CustomerDAO customerDAO = ctx.getBean("customerDAO", CustomerDAO.class);
		InternalRequestDaoImpl internalrequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
		Customer customer = customerDAO.getCustomer(Integer.parseInt(userID));
		setGlobals(request);
		Integer internalUserID= this.userID;
		internalrequestDao.raiseExternalRequest(customer, requestType, newValue,internalUserID);
	    ctx.close();
	    model = new ModelAndView("employeePages/ExtAccountDetails");
        model.addObject("extUserObj",customer);
        model.addObject("userType",userType);
		}
		else if(userType.equalsIgnoreCase("internal"))
		{
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			InternalUserDaoImpl internalDao = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
			InternalRequestDaoImpl internalrequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
			InternalUser internaluser = new InternalUser();
			if(role.equals("ROLE_REGULAR") || role.equals("ROLE_MANAGER"))
			{
				internaluser  = internalDao.getUserById(Integer.parseInt(userID));
				setGlobals(request);
				Integer internalUserID= this.userID;
				internalrequestDao.raiseInternalPersonalRequest(internaluser, requestType, newValue,internalUserID, role);
				model.setViewName("redirect:/employee/userdetails");
    			redir.addFlashAttribute("user",internaluser);
    			redir.addFlashAttribute("userType",userType);
			}
			else
			{
				internalrequestDao.update("internal_user", requestType, newValue, "id", userID);
				internaluser  = internalDao.getUserById(Integer.parseInt(userID));
				model = new ModelAndView("employeePages/ExtAccountDetails");
		        model.addObject("extUserObj",internaluser);
		        model.addObject("userType",userType);

			}
			ctx.close();
		}
        return model;

    }
	
	@RequestMapping(value="/employee/deletecustomer", method = RequestMethod.POST)
    public String deleteCustomer(@RequestParam("extUserID") String extuserID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalCustomerDAO internalCustomer = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
		internalCustomer.deleteCustomer(extuserID);
	    ctx.close();
	    return "redirect:/employee/home";
    }
	
	@RequestMapping(value="/employee/newauthorizationreq", method = RequestMethod.POST)
	 public ModelAndView newAuthorizationRequest(HttpServletRequest request, RedirectAttributes redir, @RequestParam(value = "extUserID", defaultValue="0") String extuserID,@RequestParam("requestType") String requestType) throws UnsupportedEncodingException {
        setGlobals(request);
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
       CustomerDAO customerdao = ctx.getBean("customerDAO",CustomerDAO.class);
       ModelAndView model = new ModelAndView();
       String msg = "";
       AthorizationDaoImpl authorizationDao = ctx.getBean("AuthorizationDao",AthorizationDaoImpl.class);
       ctx.close();
       switch(requestType) {
       case "registration": 
           if(authorizationDao.isExist(userID,Integer.parseInt(extuserID),requestType))
               msg = "Request already exist !!";
           else {
               if(!authorizationDao.save(userID,Integer.parseInt(extuserID),requestType))
            	   msg = "Request insert failed !!";
               else msg = "Request added successfully !!";
           }
       default:
           Customer customer = customerdao.getCustomer(Integer.parseInt(extuserID));
           if(customer == null)  msg = "Customer Id is invalid !!"; 
           else {
               if(authorizationDao.isExist(userID,Integer.parseInt(extuserID),requestType))
                   msg = "Request already exist !!";
               else {
                   if(!authorizationDao.save(userID,Integer.parseInt(extuserID),requestType))
                       msg = "Request insert failed !!";
                   else msg = "Request inserted successfully !!";
               }
           }
       }
       model.setViewName("redirect:/employee/management");
       redir.addFlashAttribute("message",msg);
       return model;
	}
	
	@RequestMapping(value="/employee/processauthorization", method = RequestMethod.POST)
    public String ProcessAuthorization(@RequestParam("requestType") String requestType, @RequestParam("transactionID") String auth_id) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");  
        AthorizationDaoImpl authorizationDao = ctx.getBean("AuthorizationDao",AthorizationDaoImpl.class);
        Authorization request = authorizationDao.getByID(Integer.valueOf(auth_id));
        switch(requestType) {
        case "approve":
            authorizationDao.deleteByID(Integer.valueOf(auth_id),"authorization_pending");
            authorizationDao.addByID(request);
            break;
        case "reject":
            authorizationDao.deleteByID(Integer.valueOf(auth_id),"authorization_pending");
            break;
        default:
            
        }
        ctx.close();
       return "redirect:/employee/management";
    }
    @RequestMapping(value="/employee/revokeauthorization", method = RequestMethod.POST)
    public String RevokeAuthorization(@RequestParam("authID") String auth_id) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");  
        AthorizationDaoImpl authorizationDao = ctx.getBean("AuthorizationDao",AthorizationDaoImpl.class);
        authorizationDao.deleteByID(Integer.valueOf(auth_id),"authorization_completed");
        ctx.close();
       return "redirect:/employee/management";
    }
	
	@RequestMapping(value="/employee/externalRegistration", method = RequestMethod.POST)
    public ModelAndView externalRegistration(@RequestParam("userType") String userType) {
        ModelAndView model = new ModelAndView("employeePages/employeeUserDetails");
	    System.out.println(userType);
	    return model;
    }

	
	@RequestMapping("/employee/userdetails")
	public ModelAndView UserDetailsContoller(HttpServletRequest request){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalUserDaoImpl internalDao = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
		setGlobals(request);
		InternalUser internaluser = internalDao.getUserById(userID);
		InternalRequestDaoImpl internalRequest = ctx.getBean("internalRequestDao",InternalRequestDaoImpl.class);
		List <Request> request_list = internalRequest.getByUserId(userID, "pending");
		request_list.addAll(internalRequest.getById(userID, "completed"));
		ModelAndView model = new ModelAndView("employeePages/employeeUserDetails");
		model.addObject("user",internaluser);
		model.addObject("request_list",request_list);
		ctx.close();
		return model;
	}
	
	@RequestMapping(value="/employee/changepassword", method = RequestMethod.POST)
    public ModelAndView changePasswordInternal(RedirectAttributes redir, @RequestParam("oldpassword") String oldPassword, HttpServletRequest request, @RequestParam("newpassword") String newPassword,@RequestParam("confirmpassword") String confirmPassword) {
		ModelAndView model = new ModelAndView();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		setGlobals(request);
		UserAuthenticationDaoImpl userauthenticationDao = ctx.getBean("userAuthenticationDao", UserAuthenticationDaoImpl.class);
		String message = userauthenticationDao.changePassword(oldPassword, newPassword, confirmPassword, userID,role);
		ctx.close();
		model.setViewName("redirect:/employee/userdetails");
        redir.addFlashAttribute("message",message);
        return model;
		

    }
	
	@RequestMapping("/employee/transaction")
	public ModelAndView TransactionContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
        List <Transaction> complete_list = transactionDAO.getAllCompleted();
        List <Transaction> pending_list = transactionDAO.getAllPending();
        if(complete_list.size() > 10)
            complete_list = complete_list.subList(complete_list.size() - 10, complete_list.size());
        if(pending_list.size() > 10)
            pending_list = pending_list.subList(pending_list.size() - 10, pending_list.size());
        ModelAndView model = new ModelAndView("employeePages/employeeTransaction");
        model.addObject("complete_list",complete_list);
        model.addObject("pending_list",pending_list);
        ctx.close();
        return model;
	}
	
	@RequestMapping(value="/employee/transactionsearch", method = RequestMethod.POST)
    public ModelAndView TransactionSearch(@RequestParam("transactionID") String transactionID) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
       List<Transaction> transactionCompleteList = null;
       List <Transaction> transactionPendingList = null;
       transactionPendingList = transactionDAO.getById(Integer.parseInt(transactionID), "transaction_pending");
       transactionCompleteList = transactionDAO.getTransactionById(Integer.parseInt(transactionID), "transaction_completed");
       ModelAndView model = new ModelAndView("employeePages/employeeTransaction");
       model.addObject("complete_list",transactionCompleteList);
       model.addObject("pending_list",transactionPendingList);
       ctx.close();
       return model;
    }
	
	@RequestMapping(value="/employee/transactionapprove", method = RequestMethod.POST)
    public ModelAndView TransactionApproveContoller(@RequestParam("transactionID") String transactionID){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
        transactionDAO.approveTransaction(Integer.parseInt(transactionID), "transaction_pending");
        ModelAndView model = new ModelAndView("redirect:" + "/employee/transaction");
        model.addObject("error_msg","Transaction Approved!");
       ctx.close();
       return model;
    }
    
    @RequestMapping(value="/employee/transactionreject", method = RequestMethod.POST)
    public ModelAndView TransactionRejectContoller(@RequestParam("transactionID") String transactionID){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
        transactionDAO.rejectTransaction(Integer.parseInt(transactionID), "transaction_pending");
        ModelAndView model = new ModelAndView("redirect:" + "/employee/transaction");
        model.addObject("error_msg","Transaction Rejected!");
       ctx.close();
       return model;
    }
	
	@RequestMapping("/employee/pendingrequest")
	public ModelAndView PendingRequestContoller(HttpServletRequest request){
		setGlobals(request);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = externalRequestDao.getAllPending();
        List<Request> internalRequestList = new ArrayList<Request>();
        if(role.equalsIgnoreCase("ROLE_MANAGER")){
        	internalRequestList = internalRequestDao.getAllPending("ROLE_REGULAR");
        }else if(role.equalsIgnoreCase("ROLE_ADMIN")){
        	internalRequestList = internalRequestDao.getAllPending("ROLE_REGULAR' AND role='ROLE_MANAGER");
        }        
        ModelAndView model = new ModelAndView("employeePages/PendingRequest");
        if(role.equalsIgnoreCase("ROLE_MANAGER") || role.equalsIgnoreCase("ROLE_ADMIN")){
        	model.addObject("internal_list",internalRequestList);
        }
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
	
	@RequestMapping(value="/employee/processrequestinternal", method = RequestMethod.POST)
	public ModelAndView PendingRequestApproveContoller(HttpServletRequest request, RedirectAttributes redir, @RequestParam("requestID") String requestID, @RequestParam("action") String action){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		setGlobals(request);
		ModelAndView model = new ModelAndView();
		String msg = "";
		if(action.equals("approve")) {
	        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
	        internalRequestDao.approveRequest(Integer.parseInt(requestID), "internal",userID);
	        msg = "Request Approved!";
		}
		else
		{
			InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
	        internalRequestDao.rejectRequest(Integer.parseInt(requestID), "internal", userID);
	        msg="Request Rejected!";
		}
        ctx.close();
        model.setViewName("redirect:/employee/pendingrequest");
        redir.addFlashAttribute("error_msg",msg);
        return model;
	}
	
	@RequestMapping(value="/employee/processrequestexternal", method = RequestMethod.POST)
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
        model.setViewName("redirect:/employee/pendingrequest");
        redir.addFlashAttribute("error_msg",msg);
        return model;
	}
	
	@RequestMapping("/employee/completedrequest")
	public ModelAndView CompletedRequestContoller(HttpServletRequest request){
		setGlobals(request);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = new ArrayList<Request>();
        externalRequestList = externalRequestDao.getAllCompleted();
        List<Request> internalRequestList = new ArrayList<Request>();
        if(role.equalsIgnoreCase("ROLE_MANAGER")){
        	internalRequestList = internalRequestDao.getAllCompleted("ROLE_REGULAR");
        }else if(role.equalsIgnoreCase("ROLE_ADMIN")){
        	internalRequestList = internalRequestDao.getAllCompleted("ROLE_REGULAR' AND role='ROLE_MANAGER");
        }
        ModelAndView model = new ModelAndView("employeePages/CompleteRequest");
        model.addObject("internal_list",internalRequestList);
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
	
	@RequestMapping(value="/employee/pendingrequestsearch", method = RequestMethod.POST)
	public ModelAndView PendingRequestSearch(@RequestParam("requestID") String requestID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = null;
        List<Request> internalRequestList = null;
    	externalRequestList = externalRequestDao.getById(Integer.parseInt(requestID),"pending");
    	internalRequestList = internalRequestDao.getById(Integer.parseInt(requestID),"pending");
        ModelAndView model = new ModelAndView("employeePages/PendingRequest");
        model.addObject("internal_list",internalRequestList);
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
	
	
	
	@RequestMapping(value="/employee/completedrequestsearch", method = RequestMethod.POST)
	public ModelAndView CompletedRequestSearch(@RequestParam("requestID") String requestID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = null;
        List<Request> internalRequestList = null;
    	externalRequestList = externalRequestDao.getById(Integer.parseInt(requestID),"completed");
    	internalRequestList = internalRequestDao.getById(Integer.parseInt(requestID),"completed");
        ModelAndView model = new ModelAndView("employeePages/CompleteRequest");
        model.addObject("internal_list",internalRequestList);
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
			
	@RequestMapping("/employee/openrequests")
	public ModelAndView OpenRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		List<Request> requestList = requestDAO.getAllPending();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list",requestList);
		ctx.close();
		return model;
	}
		
	@RequestMapping("/employee/createrequest")
	public ModelAndView CreateRequestContoller(Request request){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		boolean result = requestDAO.save(request, "external");
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		if (result)
			model.addObject("msg","Request Created");
		else
			model.addObject("msg","Request Failed");
		ctx.close();
		return model;
	}
	
	@RequestMapping(value="/employee/systemlogs")
	   public ModelAndView getLogs() {
	        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	        LogDaoImpl logDao = ctx.getBean("DatabaseLogDao", LogDaoImpl.class);
	        List<DatabaseLog> databaseLogInternal = logDao.getLogs("internal");
	        List<DatabaseLog> databaseLogExternal = logDao.getLogs("external");
	        ModelAndView model = new ModelAndView("employeePages/SystemLogs");
	        model.addObject("internal_log",databaseLogInternal);
	        model.addObject("external_log",databaseLogExternal);
	        ctx.close();
	        return model;
	   }
	
	@RequestMapping(value="/employee/searchlogs", method = RequestMethod.POST)
	   public ModelAndView searchLogs(RedirectAttributes redir, @RequestParam("userid") int userid, @RequestParam("type") String type) {
	        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	        LogDaoImpl logDao = ctx.getBean("DatabaseLogDao", LogDaoImpl.class);
	        List<DatabaseLog> databaseLog = logDao.getByUserId(userid, type);
	        ModelAndView model = new ModelAndView();
	        model.setViewName("redirect:/employee/systemlogs");
	        if(type.equals("internal")){
	        	redir.addFlashAttribute("internal_log",databaseLog);
	        }else{
	        	redir.addFlashAttribute("external_log",databaseLog);
	        }
	        ctx.close();
	        return model;
	   }
	
	@RequestMapping(value="/employee/loginmanagement")
	public ModelAndView LoginManagement(HttpServletRequest request) {
		setGlobals(request);
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        UserLoginManagementDaoImpl loginDao = ctx.getBean("UserLoginManagementDao", UserLoginManagementDaoImpl.class);
        List<UserAuthentication> login_list = new ArrayList<UserAuthentication>();
        List<UserAuthentication> otp_list = new ArrayList<UserAuthentication>();
        if(role.equalsIgnoreCase("ROLE_REGULAR")){
        	List<String> roles = new ArrayList<String>();
        	roles.add("ROLE_CUSTOMER");
        	roles.add("ROLE_MERCHANT");
        	login_list = loginDao.getLockedUsers(roles);
        	otp_list = loginDao.getOtpLockedUsers(roles);
        }else if (role.equalsIgnoreCase("ROLE_MANAGER")){
        	List<String> roles = new ArrayList<String>();
        	roles.add("ROLE_CUSTOMER");
        	roles.add("ROLE_MERCHANT");
        	roles.add("ROLE_REGULAR");
        	login_list = loginDao.getLockedUsers(roles);
        	otp_list = loginDao.getOtpLockedUsers(roles);
        }else{
        	List<String> roles = new ArrayList<String>();
        	roles.add("ROLE_CUSTOMER");
        	roles.add("ROLE_MERCHANT");
        	roles.add("ROLE_REGULAR");
        	roles.add("ROLE_MANAGER");
        	otp_list = loginDao.getOtpLockedUsers(roles);
        	login_list = loginDao.getLockedUsers(roles);
        }
        
        ModelAndView model = new ModelAndView("employeePages/employeeLoginManagement");
        model.addObject("login_list",login_list);
        model.addObject("otp_list",otp_list);
        ctx.close();
        return model;
	}
	
	@RequestMapping(value="/employee/unblocklogin", method = RequestMethod.POST)
	   public ModelAndView unBlockLogin(@RequestParam("username") String username){
	      ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	      UserLoginManagementDaoImpl usermanagementDao = ctx.getBean("UserLoginManagementDao", UserLoginManagementDaoImpl.class);
	      usermanagementDao.unlockUserAccount(username, "account");
	      ModelAndView model = new ModelAndView("redirect:/employee/loginmanagement");
	      model.addObject("error_msg","Account Unblocked!!");
	      ctx.close();
	      return model;
	   }
	    
	    @RequestMapping(value="/employee/unblockotp", method = RequestMethod.POST)
	   public ModelAndView unBlockOtp(@RequestParam("username") String username){
	         ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	         UserLoginManagementDaoImpl usermanagementDao = ctx.getBean("UserLoginManagementDao", UserLoginManagementDaoImpl.class);
	         usermanagementDao.unlockUserAccount(username, "otp");
	         ModelAndView model = new ModelAndView("redirect:/employee/loginmanagement");
	        model.addObject("error_msg","Account Unblocked!!");
	         ctx.close();
	         return model;
	   }
}

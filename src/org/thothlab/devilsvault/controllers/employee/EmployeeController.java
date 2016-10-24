package org.thothlab.devilsvault.controllers.employee;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import org.thothlab.devilsvault.dao.authorization.AthorizationDaoImpl;
import org.thothlab.devilsvault.dao.customer.CustomerDAO;
import org.thothlab.devilsvault.dao.customer.InternalCustomerDAO;
import org.thothlab.devilsvault.dao.dashboard.PendingStatisticsDao;
import org.thothlab.devilsvault.dao.employee.InternalUserDaoImpl;
import org.thothlab.devilsvault.dao.request.ExternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.request.InternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.transaction.InternalTransactionDaoImpl;
import org.thothlab.devilsvault.db.model.Authorization;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.InternalUser;
import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.Transaction;

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
		else{
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			PendingStatisticsDao pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
			HashMap<String,Integer> stats = new HashMap<String,Integer>();
			stats = pendingStatisticsDao.getPendingStatistics("ROLE_REGULAR' AND role='ROLE_MANAGER");
			List<Request> internal_list = pendingStatisticsDao.getPendingInternalRequests("ROLE_REGULAR' AND role='ROLE_MANAGER");
			if(internal_list.size() > 5)
				internal_list = internal_list.subList(internal_list.size()-5, internal_list.size());
			List<Request> external_list = pendingStatisticsDao.getPendingExternalRequests();
			if(external_list.size() > 5)
				external_list = external_list.subList(external_list.size()-5, external_list.size());
			List<Transaction> transaction_list = pendingStatisticsDao.getPendingTransactions();
			if(transaction_list.size() > 5)
				transaction_list = transaction_list.subList(transaction_list.size()-5, transaction_list.size());
			ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
			model.addObject("stats",stats);
			model.addObject("internal_list",internal_list);
			model.addObject("external_list",external_list);
			model.addObject("transaction_list",transaction_list);
			ctx.close();
			return model;
		}
	}
	
	@RequestMapping(value="/employee/management", method=RequestMethod.GET)
    public ModelAndView ManagementContoller(HttpServletRequest request,@RequestParam(required=false) String message) throws UnsupportedEncodingException{
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
            if(message != null) message = URLDecoder.decode(message,"UTF-8");
            model.addObject("message",message);
            return model;
		}else if (role.equalsIgnoreCase("ROLE_MANAGER")){
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	        AthorizationDaoImpl authorizationDAO = ctx.getBean("AuthorizationDao", AthorizationDaoImpl.class);
	        List<Authorization> pendingList = authorizationDAO.getAllPendingAuthorizationManager();
	        ctx.close();
	        List<Authorization> completeList = authorizationDAO.getAllCompleteAuthorizationManager();
	        ModelAndView model = new ModelAndView("employeePages/UserManagementManager");
	        model.addObject("pendingList",pendingList);
	        model.addObject("message",message);
	        model.addObject("completeList",completeList);
	        return model;
		}else{
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	        AthorizationDaoImpl authorizationDAO = ctx.getBean("AuthorizationDao", AthorizationDaoImpl.class);
	        List<Authorization> pendingList = authorizationDAO.getAllPendingAuthorization(userID);
	        ctx.close();
	        List<Authorization> completeList = authorizationDAO.getAllCompleteAuthorization(userID);
	        ModelAndView model = new ModelAndView("employeePages/UserManagementRegular");
	        model.addObject("pendingList",pendingList);
	        model.addObject("message",message);
	        model.addObject("completeList",completeList);
	        return model;
		}
    }
	
	@RequestMapping(value="/employee/viewtransaction", method = RequestMethod.POST)
    public ModelAndView viewTransactions(@RequestParam("extUserID") String extuserID) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
        List<Integer> extuserIDs = new ArrayList<Integer>();
        extuserIDs.add(Integer.parseInt(extuserID));
        List<Integer> accountNos = new ArrayList<Integer>();
		List<Transaction> transactionList = new ArrayList<Transaction>();
		InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
		accountNos = internalCustomerDao.getAccNos(extuserIDs);
		if(accountNos.size() > 0){
			transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
		}
        ModelAndView model = new ModelAndView("employeePages/AccountTransactions");
        model.addObject("transactionList",transactionList);
        model.addObject("extUserID",extuserID);
	    ctx.close();
	    return model;
    }
	
	@RequestMapping(value="/employee/processAccTransaction", method = RequestMethod.POST)
    public ModelAndView processAccTransactions(@RequestParam("transactionID") String transactionID,@RequestParam("requestType") String requestType,@RequestParam("extUserID") String extuserID) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
        List<Integer> extuserIDs = new ArrayList<Integer>();
        extuserIDs.add(Integer.parseInt(extuserID));
        System.out.println(transactionID);
        System.out.println(requestType);
        List<Integer> accountNos = internalCustomerDao.getAccNos(extuserIDs);
        InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
		List<Transaction> transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
        ModelAndView model = new ModelAndView("employeePages/AccountTransactions");
        model.addObject("transactionList",transactionList);
        model.addObject("extUserID",extuserID);
	    ctx.close();
	    return model;
    }
	
	@RequestMapping(value="/employee/newaccTransaction", method = RequestMethod.POST)
    public ModelAndView newAccTransactions(@RequestParam("accountNo") String accountNo,@RequestParam("receiverAccount") String receiverAccount,@RequestParam("amount") String amount,@RequestParam("extUserID") String extuserID) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
        List<Integer> extuserIDs = new ArrayList<Integer>();
        extuserIDs.add(Integer.parseInt(extuserID));
        List<Integer> accountNos = internalCustomerDao.getAccNos(extuserIDs);
        InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
		List<Transaction> transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
        ModelAndView model = new ModelAndView("employeePages/AccountTransactions");
        model.addObject("transactionList",transactionList);
        model.addObject("extUserID",extuserID);
	    ctx.close();
	    return model;
    }
		
	@RequestMapping(value="/employee/viewaccountdetails", method = RequestMethod.POST)
    public ModelAndView viewExtAccountDetails(@RequestParam("extUserID") String extuserID) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        CustomerDAO customerdao = ctx.getBean("customerDAO",CustomerDAO.class);
        Customer customer = customerdao.getCustomer(Integer.parseInt(extuserID));
        ModelAndView model = new ModelAndView("employeePages/ExtAccountDetails");
        model.addObject("extUserObj",customer);
	    ctx.close();
	    return model;
    }
	
	@RequestMapping(value="/employee/addrequest", method = RequestMethod.POST)
    public ModelAndView modifyDetails(@RequestParam("userID") String userID ,@RequestParam("requestType") String requestType, HttpServletRequest request, @RequestParam("userType") String userType,@RequestParam("newValue") String newValue) {
		ModelAndView model = null; new ModelAndView("employeePages/ExtAccountDetails");
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
		}
		else if(userType.equalsIgnoreCase("internal"))
		{
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			InternalUserDaoImpl internalDao = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
			InternalRequestDaoImpl internalrequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
			InternalUser internaluser = internalDao.getUserById(Integer.parseInt(userID));
			setGlobals(request);
			Integer internalUserID= this.userID;
			internalrequestDao.raiseInternalPersonalRequest(internaluser, requestType, newValue,internalUserID, role);
		    ctx.close();
		    model = new ModelAndView("employeePages/employeeUserDetails");
	        model.addObject("user",internaluser);
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
	 public String newAuthorizationRequest(HttpServletRequest request, @RequestParam(value = "extUserID", defaultValue="0") String extuserID,@RequestParam("requestType") String requestType) throws UnsupportedEncodingException {
        setGlobals(request);
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
       CustomerDAO customerdao = ctx.getBean("customerDAO",CustomerDAO.class);
       
       AthorizationDaoImpl authorizationDao = ctx.getBean("AuthorizationDao",AthorizationDaoImpl.class);
       ctx.close();
       switch(requestType) {
       case "registration": 
           if(authorizationDao.isExist(userID,Integer.parseInt(extuserID),requestType))
               return "redirect:/employee/management?message="+URLEncoder.encode("Request is already exist","UTF-8");
           else {
               if(!authorizationDao.save(userID,Integer.parseInt(extuserID),requestType))
                   return "redirect:/employee/management?message="+URLEncoder.encode("Request does not processed successfully","UTF-8");
               else return "redirect:/employee/management?message="+URLEncoder.encode("Request inserted successfully","UTF-8");
           }
       default:
           Customer customer = customerdao.getCustomer(Integer.parseInt(extuserID));
           if(customer == null)  return "redirect:/employee/management?message="+ URLEncoder.encode("Customer Id is invalid", "UTF-8"); 
           else {
               if(authorizationDao.isExist(userID,Integer.parseInt(extuserID),requestType))
                   return "redirect:/employee/management?message="+URLEncoder.encode("Request is already exist","UTF-8");
               else {
                   if(!authorizationDao.save(userID,Integer.parseInt(extuserID),requestType))
                       return "redirect:/employee/management?message="+URLEncoder.encode("Request does not processed successfully","UTF-8");
                   else return "redirect:/employee/management?message="+URLEncoder.encode("Request inserted successfully","UTF-8");
               }
           }
       }
       
        
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
		ModelAndView model = new ModelAndView("employeePages/employeeUserDetails");
		model.addObject("user",internaluser);
		ctx.close();
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
    public ModelAndView TransactionSearch(@RequestParam("transactionID") String transactionID, @RequestParam("accNo") String accNo) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
       List<Transaction> transactionCompleteList = null;
       List <Transaction> transactionPendingList = null;
       if(transactionID.length() == 0) {
           transactionPendingList = transactionDAO.getByUserId(Integer.parseInt(accNo), "transaction_pending");
           transactionCompleteList = transactionDAO.getByUserId(Integer.parseInt(accNo), "transaction_completed");

       } else {
           transactionPendingList = transactionDAO.getById(Integer.parseInt(transactionID), "transaction_pending");
           transactionCompleteList = transactionDAO.getByUserId(Integer.parseInt(transactionID), "transaction_completed");

       }
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
	public ModelAndView PendingRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = externalRequestDao.getAllPending();
        List<Request> internalRequestList = new ArrayList<Request>();
        if(externalRequestList.size() > 10)
            externalRequestList = externalRequestList.subList(externalRequestList.size()-10, externalRequestList.size());
        if(role.equalsIgnoreCase("ROLE_MANAGER")){
        	internalRequestList = internalRequestDao.getAllPending("ROLE_REGULAR");
        }else{
        	internalRequestList = internalRequestDao.getAllPending("ROLE_REGULAR' AND role='ROLE_MANAGER");
        }
        if(internalRequestList.size() > 10)
            internalRequestList = internalRequestList.subList(internalRequestList.size()-10, internalRequestList.size());
        ModelAndView model = new ModelAndView("employeePages/PendingRequest");
        model.addObject("internal_list",internalRequestList);
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
	
	@RequestMapping("/employee/pendingrequest/approve")
	public ModelAndView PendingRequestApproveContoller(@RequestParam("requestID") String requestID, @RequestParam("requestType") String requestType){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ModelAndView model = new ModelAndView("employeePages/PendingRequest");
		if(requestType.equals("internal")) {
	        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
	        internalRequestDao.approveRequest(Integer.parseInt(requestID), requestType);
		}
		else {
			ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
			externalRequestDao.approveRequest(Integer.parseInt(requestID), requestType);
		} 
        model.addObject("error_msg","Request Approved!");
        ctx.close();
        return model;
	}
	
	@RequestMapping("/employee/pendingrequest/reject")
	public ModelAndView PendingRequestRejectContoller(@RequestParam("requestID") String requestID, @RequestParam("requestType") String requestType){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ModelAndView model = new ModelAndView("employeePages/PendingRequest");
		if(requestType.equals("internal")) {
	        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
	        internalRequestDao.rejectRequest(Integer.parseInt(requestID), requestType);
		}
		else {
			ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
			externalRequestDao.rejectRequest(Integer.parseInt(requestID), requestType);
		} 
        model.addObject("error_msg","Request Rejected!");
        ctx.close();
        return model;
	}
	
	@RequestMapping("/employee/completedrequest")
	public ModelAndView CompletedRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = externalRequestDao.getAllCompleted();
        if(externalRequestList.size() > 10)
            externalRequestList = externalRequestList.subList(externalRequestList.size()-10, externalRequestList.size());
        List<Request> internalRequestList = internalRequestDao.getAllCompleted();
        if(internalRequestList.size() > 10)
            internalRequestList = internalRequestList.subList(internalRequestList.size()-10, internalRequestList.size());
        ModelAndView model = new ModelAndView("employeePages/CompleteRequest");
        model.addObject("internal_list",internalRequestList);
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
	
	@RequestMapping(value="/employee/pendingrequestsearch", method = RequestMethod.POST)
	public ModelAndView PendingRequestSearch(@RequestParam("requestID") String requestID, @RequestParam("userID") String userID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = null;
        List<Request> internalRequestList = null;
        if(requestID.length() == 0) {
        	externalRequestList = externalRequestDao.getByUserId(Integer.parseInt(userID),"pending");
        	internalRequestList = internalRequestDao.getByUserId(Integer.parseInt(userID),"pending");
        } else {
        	externalRequestList = externalRequestDao.getById(Integer.parseInt(requestID),"pending");
        	internalRequestList = internalRequestDao.getById(Integer.parseInt(requestID),"pending");
        }
        ModelAndView model = new ModelAndView("employeePages/PendingRequest");
        model.addObject("internal_list",internalRequestList);
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
	
	
	
	@RequestMapping(value="/employee/completedrequestsearch", method = RequestMethod.POST)
	public ModelAndView CompletedRequestSearch(@RequestParam("requestID") String requestID, @RequestParam("userID") String userID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = null;
        List<Request> internalRequestList = null;
        if(requestID.length() == 0) {
        	externalRequestList = externalRequestDao.getByUserId(Integer.parseInt(userID),"completed");
        	internalRequestList = internalRequestDao.getByUserId(Integer.parseInt(userID),"completed");
        } else {
        	externalRequestList = externalRequestDao.getById(Integer.parseInt(requestID),"completed");
        	internalRequestList = internalRequestDao.getById(Integer.parseInt(requestID),"completed");
        }
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
}

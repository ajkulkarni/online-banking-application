package org.thothlab.devilsvault.controllers.employee;

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
import org.thothlab.devilsvault.dao.authorization.AthorizationDaoImpl;
import org.thothlab.devilsvault.dao.customer.InternalCustomerDAO;
import org.thothlab.devilsvault.dao.customer.TransferDAO;
import org.thothlab.devilsvault.dao.dashboard.PendingStatisticsDao;
import org.thothlab.devilsvault.dao.pendingregistration.PendingRegistrationDaoImpl;
import org.thothlab.devilsvault.dao.request.ExternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.request.InternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.transaction.InternalTransactionDaoImpl;
import org.thothlab.devilsvault.dao.transaction.TransactionDaoImpl;
import org.thothlab.devilsvault.db.model.Authorization;
import org.thothlab.devilsvault.db.model.PendingRegistration;
import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.Transaction;

@Controller
public class EmployeeController {

	String role;
	int userID;
	String username;

	public void setGlobals(HttpServletRequest request) {
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}

	@RequestMapping(value = "/employee/home", method = RequestMethod.GET)
	public ModelAndView PendingDashboardContoller(HttpServletRequest request) {
		setGlobals(request);
		if (role.equalsIgnoreCase("ROLE_REGULAR")) {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
			InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao",
					InternalTransactionDaoImpl.class);
			PendingStatisticsDao pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
			List<Integer> externalIDs = internalCustomerDao.getExternalUserIds(userID, "transaction");
			List<Integer> accountNos = internalCustomerDao.getAccNos(externalIDs);
			List<Transaction> transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
			int transaction_count = transactionList.size();
			if (transactionList.size() > 5)
				transactionList = transactionList.subList(transactionList.size() - 5, transactionList.size());
			List<Request> external_list = pendingStatisticsDao.getPendingExternalRequests();
			int request_count = external_list.size();
			if (external_list.size() > 5)
				external_list = external_list.subList(external_list.size() - 5, external_list.size());
			ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
			request.getSession().setAttribute("transaction_count", transaction_count);
			model.addObject("request_count", request_count);
			model.addObject("transaction_list", transactionList);
			model.addObject("external_list", external_list);
			ctx.close();
			return model;
		} else {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			PendingStatisticsDao pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
			HashMap<String, Integer> stats = new HashMap<String, Integer>();
			stats = pendingStatisticsDao.getPendingStatistics();
			List<Request> internal_list = pendingStatisticsDao.getPendingInternalRequests();
			if (internal_list.size() > 5)
				internal_list = internal_list.subList(internal_list.size() - 5, internal_list.size());
			List<Request> external_list = pendingStatisticsDao.getPendingExternalRequests();
			if (external_list.size() > 5)
				external_list = external_list.subList(external_list.size() - 5, external_list.size());
			List<PendingRegistration> user_list = pendingStatisticsDao.getPendingUserRegistrations();
			if (user_list.size() > 5)
				user_list = user_list.subList(user_list.size() - 5, user_list.size());
			List<Transaction> transaction_list = pendingStatisticsDao.getPendingTransactions();
			if (transaction_list.size() > 5)
				transaction_list = transaction_list.subList(transaction_list.size() - 5, transaction_list.size());
			ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
			model.addObject("stats", stats);
			model.addObject("internal_list", internal_list);
			model.addObject("external_list", external_list);
			model.addObject("user_list", user_list);
			model.addObject("transaction_list", transaction_list);
			ctx.close();
			return model;
		}
	}

	@RequestMapping(value = "/employee/management", method = RequestMethod.GET)
	public ModelAndView ManagementContoller(HttpServletRequest request) {
		setGlobals(request);
		if (role.equalsIgnoreCase("ROLE_REGULAR")) {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			AthorizationDaoImpl authorizationDAO = ctx.getBean("AuthorizationDao", AthorizationDaoImpl.class);
			List<Authorization> pendingList = authorizationDAO.getAllPendingAuthorization(userID);
			ctx.close();
			List<Authorization> completeList = authorizationDAO.getAllCompleteAuthorization(userID);
			ModelAndView model = new ModelAndView("employeePages/UserManagementRegular");
			model.addObject("pendingList", pendingList);
			model.addObject("completeList", completeList);
			return model;
		} else {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			AthorizationDaoImpl authorizationDAO = ctx.getBean("AuthorizationDao", AthorizationDaoImpl.class);
			List<Authorization> pendingList = authorizationDAO.getAllPendingAuthorization(userID);
			ctx.close();
			List<Authorization> completeList = authorizationDAO.getAllCompleteAuthorization(userID);
			ModelAndView model = new ModelAndView("employeePages/UserManagementRegular");
			model.addObject("pendingList", pendingList);
			model.addObject("completeList", completeList);
			return model;
		}
	}

	@RequestMapping(value = "/employee/viewtransaction", method = RequestMethod.POST)
	public ModelAndView viewTransactions(@RequestParam("extUserID") String extuserID, HttpServletRequest request) {
		
		System.out.println(extuserID+":");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
		
		
		Integer payerID = Integer.parseInt(extuserID);
		//int payerID = 1;
		TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
		List<Integer> currentUserAccounts  = transferDAO.getMultipleAccounts(payerID);
		List<String> userAccounts=new ArrayList<>();
		for(Integer currentElements: currentUserAccounts){
			transferDAO.getPayerAccounts(currentElements,userAccounts);
		}
		
		request.getSession().setAttribute("userAccounts", userAccounts);
			
		List<Integer> extuserIDs = new ArrayList<Integer>();
		//extuserIDs.add(Integer.parseInt(extuserID));
		extuserIDs.add(1);
		List<Integer> accountNos = internalCustomerDao.getAccNos(extuserIDs);
		for(Integer acc: accountNos){
			System.out.println("Account : " + acc);
		}
		InternalTransactionDaoImpl transactionDao = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		List<Transaction> transactionList = transactionDao.getAllPendingTransactionByAccountNo(accountNos);
		for(Transaction acc: transactionList){
			System.out.println("Transaction ID : " + acc.getId());
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
		System.out.println("Transaction : " + transactionSaved + "-" +transaction.getId() + "-" + transaction.getApprover() + "-" + transaction.getStatus() + "-" + transaction.getAmount());
		
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
		System.out.println(" Dletion : " + transactionDeleted);
		
		InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
		List<Integer> extuserIDs = new ArrayList<Integer>();
		//extuserIDs.add(Integer.parseInt(extuserID));
		extuserIDs.add(1);
		System.out.println(transactionID);
		System.out.println(requestType);
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
//			return model;
		}
		
		TransactionDaoImpl extTransactionDAO = ctx.getBean("TransactionDao", TransactionDaoImpl.class);

		String description = "Transferred "+amount+"$ from Account:"+payerAccountNumber+" to Account:"+receiverAcountNumber+"";
		
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber,amountSent,receiverAcountNumber, description, "Employee Generated");
		extTransferTrans.setApprover(""+userID);
		
		//save to pending first
		extTransactionDAO.save(extTransferTrans, "transaction_pending");
		
		//save to completed now
		extTransactionDAO.saveToCompleted(extTransferTrans, "transaction_completed");
		
		//delete from pending
		extTransactionDAO.deleteById(extTransferTrans.getId(), "transaction_pending");
		
		transferDAO.updateHold(payerAccountNumber,amountSent);
	
		model.addObject("transactionList", transactionList);
		model.addObject("extUserID", extuserID);
		model.addObject("error_msg", "Your payment was successful!");
		model.addObject("success", true);
		ctx.close();
		return model;
	}

	@RequestMapping(value = "/employee/viewaccountdetails", method = RequestMethod.POST)
	public ModelAndView viewExtAccountDetails(@RequestParam("extUserID") String extuserID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		System.out.println(userID);
		ModelAndView model = new ModelAndView("employeePages/ExtAccountDetails");
		ctx.close();
		return model;
	}

	@RequestMapping(value = "/employee/newauthorizationreq", method = RequestMethod.POST)
	public ModelAndView newAuthorizationRequest(@RequestParam("extUserID") String extuserID,
			@RequestParam("requestType") String requestType) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		System.out.println(extuserID);
		System.out.println(requestType);
		ModelAndView model = new ModelAndView("employeePages/ExtAccountDetails");
		ctx.close();
		return model;
	}

	@RequestMapping(value = "/employee/externalRegistration", method = RequestMethod.POST)
	public ModelAndView externalRegistration(@RequestParam("userType") String userType) {
		ModelAndView model = new ModelAndView("employeePages/employeeUserDetails");
		System.out.println(userType);
		return model;
	}

	@RequestMapping("/employee/userdetails")
	public ModelAndView UserDetailsContoller() {
		ModelAndView model = new ModelAndView("employeePages/employeeUserDetails");
		model.addObject("request_list", "test message");
		return model;
	}

	@RequestMapping("/employee/transaction")
	public ModelAndView TransactionContoller() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		List<Transaction> complete_list = transactionDAO.getAllCompleted();
		List<Transaction> pending_list = transactionDAO.getAllPending();
		if (complete_list.size() > 10)
			complete_list = complete_list.subList(complete_list.size() - 10, complete_list.size());
		if (pending_list.size() > 10)
			pending_list = pending_list.subList(pending_list.size() - 10, pending_list.size());
		ModelAndView model = new ModelAndView("employeePages/employeeTransaction");
		model.addObject("complete_list", complete_list);
		model.addObject("pending_list", pending_list);
		ctx.close();
		return model;
	}

	@RequestMapping(value = "/employee/transactionsearch", method = RequestMethod.POST)
	public ModelAndView TransactionSearch(@RequestParam("transactionID") String transactionID,
			@RequestParam("accNo") String accNo) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		List<Transaction> transactionCompleteList = null;
		List<Transaction> transactionPendingList = null;
		if (transactionID.length() == 0) {
			transactionPendingList = transactionDAO.getByUserId(Integer.parseInt(accNo), "transaction_pending");
			transactionCompleteList = transactionDAO.getByUserId(Integer.parseInt(accNo), "transaction_completed");

		} else {
			transactionPendingList = transactionDAO.getById(Integer.parseInt(transactionID), "transaction_pending");
			transactionCompleteList = transactionDAO.getByUserId(Integer.parseInt(transactionID),
					"transaction_completed");

		}
		ModelAndView model = new ModelAndView("employeePages/employeeTransaction");
		model.addObject("complete_list", transactionCompleteList);
		model.addObject("pending_list", transactionPendingList);
		ctx.close();
		return model;
	}

	@RequestMapping(value = "/employee/transactionapprove", method = RequestMethod.POST)
	public ModelAndView TransactionApproveContoller(@RequestParam("transactionID") String transactionID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		transactionDAO.approveTransaction(Integer.parseInt(transactionID), "transaction_pending");
		ModelAndView model = new ModelAndView("redirect:" + "/employee/transaction");
		model.addObject("error_msg", "Transaction Approved!");
		ctx.close();
		return model;
	}

	@RequestMapping(value = "/employee/transactionreject", method = RequestMethod.POST)
	public ModelAndView TransactionRejectContoller(@RequestParam("transactionID") String transactionID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao",
				InternalTransactionDaoImpl.class);
		transactionDAO.rejectTransaction(Integer.parseInt(transactionID), "transaction_pending");
		ModelAndView model = new ModelAndView("redirect:" + "/employee/transaction");
		model.addObject("error_msg", "Transaction Rejected!");
		ctx.close();
		return model;
	}

	@RequestMapping("/employee/pendingrequest")
	public ModelAndView PendingRequestContoller() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
		List<Request> externalRequestList = externalRequestDao.getAllPending();
		if (externalRequestList.size() > 10)
			externalRequestList = externalRequestList.subList(externalRequestList.size() - 10,
					externalRequestList.size());
		List<Request> internalRequestList = internalRequestDao.getAllPending();
		if (internalRequestList.size() > 10)
			internalRequestList = internalRequestList.subList(internalRequestList.size() - 10,
					internalRequestList.size());
		ModelAndView model = new ModelAndView("employeePages/PendingRequest");
		model.addObject("internal_list", internalRequestList);
		model.addObject("external_list", externalRequestList);
		ctx.close();
		return model;
	}

	@RequestMapping("/employee/pendingrequest/approve")
	public ModelAndView PendingRequestApproveContoller(@RequestParam("requestID") String requestID,
			@RequestParam("requestType") String requestType) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ModelAndView model = new ModelAndView("employeePages/PendingRequest");
		if (requestType.equals("internal")) {
			InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
			internalRequestDao.approveRequest(Integer.parseInt(requestID), requestType);
		} else {
			ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
			externalRequestDao.approveRequest(Integer.parseInt(requestID), requestType);
		}
		model.addObject("error_msg", "Request Approved!");
		ctx.close();
		return model;
	}

	@RequestMapping("/employee/pendingrequest/reject")
	public ModelAndView PendingRequestRejectContoller(@RequestParam("requestID") String requestID,
			@RequestParam("requestType") String requestType) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ModelAndView model = new ModelAndView("employeePages/PendingRequest");
		if (requestType.equals("internal")) {
			InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
			internalRequestDao.rejectRequest(Integer.parseInt(requestID), requestType);
		} else {
			ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
			externalRequestDao.rejectRequest(Integer.parseInt(requestID), requestType);
		}
		model.addObject("error_msg", "Request Rejected!");
		ctx.close();
		return model;
	}

	@RequestMapping("/employee/completedrequest")
	public ModelAndView CompletedRequestContoller() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
		List<Request> externalRequestList = externalRequestDao.getAllCompleted();
		if (externalRequestList.size() > 10)
			externalRequestList = externalRequestList.subList(externalRequestList.size() - 10,
					externalRequestList.size());
		List<Request> internalRequestList = internalRequestDao.getAllCompleted();
		if (internalRequestList.size() > 10)
			internalRequestList = internalRequestList.subList(internalRequestList.size() - 10,
					internalRequestList.size());
		ModelAndView model = new ModelAndView("employeePages/CompleteRequest");
		model.addObject("internal_list", internalRequestList);
		model.addObject("external_list", externalRequestList);
		ctx.close();
		return model;
	}

	@RequestMapping(value = "/employee/pendingrequestsearch", method = RequestMethod.POST)
	public ModelAndView PendingRequestSearch(@RequestParam("requestID") String requestID,
			@RequestParam("userID") String userID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
		List<Request> externalRequestList = null;
		List<Request> internalRequestList = null;
		if (requestID.length() == 0) {
			externalRequestList = externalRequestDao.getByUserId(Integer.parseInt(userID), "pending");
			internalRequestList = internalRequestDao.getByUserId(Integer.parseInt(userID), "pending");
		} else {
			externalRequestList = externalRequestDao.getById(Integer.parseInt(requestID), "pending");
			internalRequestList = internalRequestDao.getById(Integer.parseInt(requestID), "pending");
		}
		ModelAndView model = new ModelAndView("employeePages/PendingRequest");
		model.addObject("internal_list", internalRequestList);
		model.addObject("external_list", externalRequestList);
		ctx.close();
		return model;
	}

	@RequestMapping(value = "/employee/completedrequestsearch", method = RequestMethod.POST)
	public ModelAndView CompletedRequestSearch(@RequestParam("requestID") String requestID,
			@RequestParam("userID") String userID) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
		List<Request> externalRequestList = null;
		List<Request> internalRequestList = null;
		if (requestID.length() == 0) {
			externalRequestList = externalRequestDao.getByUserId(Integer.parseInt(userID), "completed");
			internalRequestList = internalRequestDao.getByUserId(Integer.parseInt(userID), "completed");
		} else {
			externalRequestList = externalRequestDao.getById(Integer.parseInt(requestID), "completed");
			internalRequestList = internalRequestDao.getById(Integer.parseInt(requestID), "completed");
		}
		ModelAndView model = new ModelAndView("employeePages/CompleteRequest");
		model.addObject("internal_list", internalRequestList);
		model.addObject("external_list", externalRequestList);
		ctx.close();
		return model;
	}

	@RequestMapping("/employee/pendingregistration")
	public ModelAndView PendingRegistrationContoller() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		PendingRegistrationDaoImpl pendingRegistrationDao = ctx.getBean("PendingRegistrationDao",
				PendingRegistrationDaoImpl.class);
		List<PendingRegistration> PendingRegistrationList = pendingRegistrationDao.getAllPending();
		ModelAndView model = new ModelAndView("employeePages/PendingRegistration");
		model.addObject("registration_list", PendingRegistrationList);
		ctx.close();
		return model;
	}

	@RequestMapping("/employee/openrequests")
	public ModelAndView OpenRequestContoller() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		List<Request> requestList = requestDAO.getAllPending();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list", requestList);
		ctx.close();
		return model;
	}

	@RequestMapping("/employee/createrequest")
	public ModelAndView CreateRequestContoller(Request request) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		boolean result = requestDAO.save(request, "external");
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		if (result)
			model.addObject("msg", "Request Created");
		else
			model.addObject("msg", "Request Failed");
		ctx.close();
		return model;
	}
}

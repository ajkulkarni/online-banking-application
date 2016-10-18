package org.thothlab.devilsvault.customercontrollers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.transaction.TransactionDaoImpl;
import org.thothlab.devilsvault.db.model.ExternalUser;
import org.thothlab.devilsvault.db.model.Transaction;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.ExternalTransactionDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.TransferDAO;

@Controller
public class CustomerTransferFundsController {

	@RequestMapping("/customer/transferfunds")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/transferFunds");
		
		ExternalUser user = new ExternalUser(3,"JAY","TEMPE","TEMPE","USA",852,91231288.00);
		TransferDAO transferDAO = CustomerDAOHelper.transferDAO();
		
		
		int payerId=8;
		List<Integer> currentUserAccounts  = transferDAO.getMultipleAccounts(payerId);
		List<String> populatedPayeeAccounts=new ArrayList<>();
		List<String> userAccounts=new ArrayList<>();		
		for(Integer currentElements: currentUserAccounts){
			 transferDAO.getRelatedAccounts(currentElements,populatedPayeeAccounts);
			 transferDAO.getPayerAccounts(currentElements,userAccounts);	
		}
		
		 

	//	System.out.println(account.size());
	
		for(String elem: populatedPayeeAccounts){
			System.out.println(elem);
		}
		
		model.addObject("payeeAccounts",populatedPayeeAccounts);
		model.addObject("userAccounts",userAccounts);
	//	model.addObject("msg","Hello Gaurav");
		return model;
	}
	
	
	
	@RequestMapping(value="/customer/ExternalTransfer", method = RequestMethod.POST)
	public ModelAndView ExternalSubmit(HttpServletRequest request){
		System.out.println("------------");
		TransferDAO transferDAO = CustomerDAOHelper.transferDAO();
		
		System.out.println(request.getParameter("etpdatetimepicker_result"));
		float amount = Float.parseFloat(request.getParameter("etpinputAmount"));
		int payerAccountNumber = Integer.parseInt(request.getParameter("etpselectPayerAccount").split(":")[0]); 
		int payeeAccountNumber = Integer.parseInt(request.getParameter("etpselectPayeeAccount").split(":")[1]);
		String description = request.getParameter("etpTextArea");
		boolean amountValid = transferDAO.validateAmount(payerAccountNumber,amount);
		if(!amountValid){
			System.out.println("Inadequate balance!");
			return null;
		}
		ExternalTransactionDAO extTransactionDAO = CustomerDAOHelper.extTransactionDAO();
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber,amount,payeeAccountNumber, description, "external");
		extTransactionDAO.save(extTransferTrans, "transaction_pending");
		
		return null;
	}
	
	@RequestMapping(value="/customer/InternalTransfer", method = RequestMethod.POST)
	public ModelAndView InternalSubmit(HttpServletRequest request){
		System.out.println("------------");
		TransferDAO transferDAO = CustomerDAOHelper.transferDAO();
		
		System.out.println(request.getParameter("etpdatetimepicker_result"));
		float amount = Float.parseFloat(request.getParameter("etpinputAmount"));
		int payerAccountNumber = Integer.parseInt(request.getParameter("etpselectPayerAccount").split(":")[0]); 
		int payeeAccountNumber = Integer.parseInt(request.getParameter("etpselectPayeeAccount").split(":")[1]);
		String description = request.getParameter("etpTextArea");
		boolean amountValid = transferDAO.validateAmount(payerAccountNumber,amount);
		if(!amountValid){
			System.out.println("Inadequate balance!");
			return null;
		}
		ExternalTransactionDAO extTransactionDAO = CustomerDAOHelper.extTransactionDAO();
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber,amount,payeeAccountNumber, description, "external");
		extTransactionDAO.save(extTransferTrans, "transaction_pending");
		
		return null;
	}

	@RequestMapping(value="/customer/EmailPhoneTransfer", method = RequestMethod.POST)
	public ModelAndView EmailPhoneSubmit(HttpServletRequest request){
		
	
		String modeOfTransfer  =request.getParameter("eptpModeOfTransfer");
		String inputMode = request.getParameter("eptpinputMode");
		System.out.println(request.getParameter("eptpselectPayerAccount")+"here");
		TransferDAO transferDao = CustomerDAOHelper.transferDAO();
		int PayeeAccountNumber = transferDao.fetchAccountNumber(modeOfTransfer,inputMode);
		
		int payerAccountNumber = Integer.parseInt(request.getParameter("eptpselectPayerAccount").split(":")[0]);
		
		
		float amount = Float.parseFloat(request.getParameter("eptpinputAmount")); 
		String description = request.getParameter("etpTextArea");
		
		if(PayeeAccountNumber!=-1){
			boolean amountValid = transferDao.validateAmount(payerAccountNumber,amount);
			if(!amountValid){
				System.out.println("Inadequate balance!");
				return null;
			}
		
			ExternalTransactionDAO extTransactionDAO = CustomerDAOHelper.extTransactionDAO();
			Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber,amount,PayeeAccountNumber, description, "EmailPhone");
			extTransactionDAO.save(extTransferTrans, "transaction_pending");
			
			
		}
		
		System.out.println(PayeeAccountNumber+"exists");
		
		
/*		eptpModeOfTransfer
		eptpinputMode
		eptpselectPayerAccount
		eptpinputAmount
*/		
		return null;
	}
	
}
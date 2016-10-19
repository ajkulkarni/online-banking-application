package org.thothlab.devilsvault.customercontrollers;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
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
		ModelAndView model = new ModelAndView("customerPages/transferConfirmation");
		//System.out.println(request.getParameter("etpdatetimepicker_result"));
		float amount = Float.parseFloat(request.getParameter("etpinputAmount"));
		int payerAccountNumber = Integer.parseInt(request.getParameter("etpselectPayerAccount").split(":")[0]); 
		int payeeAccountNumber = Integer.parseInt(request.getParameter("etpselectPayeeAccount").split(":")[1]);
		String description = request.getParameter("etpTextArea");
		
		boolean amountValid = transferDAO.validateAmount(payerAccountNumber,amount);
		if(!amountValid){
			model.addObject("success", false);
			model.addObject("error_msg", "Insufficient balance!");
			return model;
		}
		ExternalTransactionDAO extTransactionDAO = CustomerDAOHelper.extTransactionDAO();
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber,amount,payeeAccountNumber, description, "external");
		extTransactionDAO.save(extTransferTrans, "transaction_pending");
		transferDAO.updateHold(payerAccountNumber,amount);
		model.addObject("success", true);
		model.addObject("payee_info", payeeAccountNumber);
		model.addObject("Amount", amount);
		return model;
	}
	
	@RequestMapping(value="/customer/InternalTransfer", method = RequestMethod.POST)
	public ModelAndView InternalSubmit(HttpServletRequest request) throws ParseException{
		System.out.println("------------");
		TransferDAO transferDAO = CustomerDAOHelper.transferDAO();
		ModelAndView model = new ModelAndView("customerPages/transferConfirmation");
		
		float amount = Float.parseFloat(request.getParameter("itpinputAmount"));
		int payerAccountNumber = Integer.parseInt(request.getParameter("itpselectPayerAccount").split(":")[0]); 
		int payeeAccountNumber = Integer.parseInt(request.getParameter("itpselectPayeeAccount").split(":")[0]);
		String description = request.getParameter("itpTextArea");
		boolean amountValid = transferDAO.validateAmount(payerAccountNumber,amount);
		if(!amountValid){
			
			System.out.println("Inadequate balance!");
			model.addObject("success", false);
			model.addObject("error_msg", "Insufficient balance!");
			return model;
		}
		ExternalTransactionDAO extTransactionDAO = CustomerDAOHelper.extTransactionDAO();
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber,amount,payeeAccountNumber, description, "external");
		extTransactionDAO.save(extTransferTrans, "transaction_pending");
		transferDAO.updateHold(payerAccountNumber,amount);
		model.addObject("success", true);
		model.addObject("payee_info", payeeAccountNumber);
		model.addObject("Amount", amount);
		return model;
	}

	@RequestMapping(value="/customer/EmailPhoneTransfer", method = RequestMethod.POST)
	public ModelAndView EmailPhoneSubmit(HttpServletRequest request){
		
	
		String modeOfTransfer  =request.getParameter("eptpModeOfTransfer");
		String inputMode = request.getParameter("eptpinputMode");
		System.out.println(request.getParameter("eptpselectPayerAccount")+"here");
		TransferDAO transferDAO = CustomerDAOHelper.transferDAO();
		int PayeeAccountNumber = transferDAO.fetchAccountNumber(modeOfTransfer,inputMode);
		
		int payerAccountNumber = Integer.parseInt(request.getParameter("eptpselectPayerAccount").split(":")[0]);
		
		
		float amount = Float.parseFloat(request.getParameter("eptpinputAmount")); 
		String description = request.getParameter("eptpTextArea");
		ModelAndView model = new ModelAndView("customerPages/transferConfirmation");
		if(PayeeAccountNumber!=-1){
			boolean amountValid = transferDAO.validateAmount(payerAccountNumber,amount);
			if(!amountValid){
				System.out.println("Inadequate balance!");
				model.addObject("success", false);
				model.addObject("error_msg", "Insufficient balance!");
				return model;
			}
		
			ExternalTransactionDAO extTransactionDAO = CustomerDAOHelper.extTransactionDAO();
			Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber,amount,PayeeAccountNumber, description, "EmailPhone");
			extTransactionDAO.save(extTransferTrans, "transaction_pending");
			transferDAO.updateHold(payerAccountNumber,amount);
			model.addObject("success", true);
			model.addObject("payee_info", inputMode);
			model.addObject("Amount", amount);
			return model;
		}
		else{
			model.addObject("success", false);
			model.addObject("error_msg", "Payee account not found for given Email/Phone!");
			return model;
		}
		
		//System.out.println(PayeeAccountNumber+"exists");
		
	}
	
}
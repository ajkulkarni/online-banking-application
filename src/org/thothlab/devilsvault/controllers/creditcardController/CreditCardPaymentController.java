package org.thothlab.devilsvault.controllers.creditcardController;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.creditcard.CreditCardDOA;
import org.thothlab.devilsvault.dao.creditcard.CustomerDAOHelper;
import org.thothlab.devilsvault.dao.customer.CustomerDAO;
import org.thothlab.devilsvault.dao.customer.ExtUserDaoImpl;
import org.thothlab.devilsvault.dao.transaction.ExternalTransactionDAO;
import org.thothlab.devilsvault.dao.transaction.TransferDAO;
import org.thothlab.devilsvault.db.model.BankAccountExternal;
import org.thothlab.devilsvault.db.model.CreditAccount;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.Transaction;



@Controller
public class CreditCardPaymentController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");	
	}
	
	@RequestMapping("/customer/creditPayment")
	public ModelAndView showCreditPaymentPage(HttpServletRequest request){
		setGlobals(request);
		ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
		CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		Customer cust = cust_dao.getCustomer(userID);
		
		CreditAccount account = doa.getCreditAccount(cust);
		
		model.addObject("creditAccount",account);
		return model;
	}
	
	@RequestMapping(value="/customer/makePayement", method = RequestMethod.POST)
	public ModelAndView makePayment(HttpServletRequest request, @RequestParam("inputAmountField") String amount) throws ParseException {
		
		boolean success = false;
		setGlobals(request); 
		
		ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
		BigDecimal amt = new BigDecimal(amount);
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		BankAccountExternal checkingAccount = new BankAccountExternal();
		Customer customer = cust_dao.getCustomer(userID);
		
		CreditAccount account = doa.getCreditAccount(customer);
		ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
		checkingAccount = CustomerDAO.getAccount(customer, checkingAccount,"CHECKING");
		
		TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
		boolean amountValid = transferDAO.validateAmount(checkingAccount.getAccount_number(), amt);

		if (!amountValid) {

			System.out.println("Inadequate balance!");
			model.addObject("paymentResult", "0");
			ctx.close();
			return model;
		}
		ExternalTransactionDAO extTransactionDAO = ctx.getBean("extTransactionDAO",ExternalTransactionDAO.class);
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(checkingAccount.getAccount_number(), amt, account.getAccountNumber(), "Credit Card payment", "external");
		success = extTransactionDAO.save(extTransferTrans, "transaction_pending");
		
		transferDAO.updateHold(checkingAccount.getAccount_number(), amt);
		
		if (success) {
			model.addObject("paymentResult", "1");
		} else {
			model.addObject("paymentResult", "0");
		}
		ctx.close();
		return model;
	}
}

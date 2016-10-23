package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardDOA;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerAccountsDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.ExternalTransactionDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.TransferDAO;
import org.thothlab.devilsvault.customercontrollers.creditcardcontroller.CreditCardDashboardController;
import org.thothlab.devilsvault.db.model.Transaction;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;


@Controller
public class CreditCardPaymentController {

	@RequestMapping("/creditPayment")
	public ModelAndView showCreditPaymentPage(){
		ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
		CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		Customer cust = cust_dao.getCustomer(100);
		
		CreditAccount account = doa.getCreditAccount(cust);
		
		model.addObject("creditAccount",account);
		return model;
	}
	
	@RequestMapping(value="makePayement", method = RequestMethod.POST)
	public ModelAndView makePayment(@RequestParam("inputAmountField") String amount) {
		
		boolean success = false;
		
		ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		TransferDAO transferDAO = ctx.getBean("transferDAO",TransferDAO.class);
		
		boolean valid_payment = transferDAO.validateAmount(1, Double.parseDouble(amount));
		if (valid_payment == false) {
			model.addObject("paymentResult", "0");
			return model;
		}
		
		ExternalTransactionDAO extTransactionDAO = ctx.getBean("extTransactionDAO",ExternalTransactionDAO.class);
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(1, Float.parseFloat(amount), 1, "Credit Card payment", "external");
		success = extTransactionDAO.save(extTransferTrans, "transaction_pending");
		
		if (success) {
			model.addObject("paymentResult", "1");
		} else {
			model.addObject("paymentResult", "0");
		}
		return model;
	}
}

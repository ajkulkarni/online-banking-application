package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardDOA;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.thothlab.devilsvault.customercontrollers.creditcardcontroller.CreditCardDashboardController;

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
}

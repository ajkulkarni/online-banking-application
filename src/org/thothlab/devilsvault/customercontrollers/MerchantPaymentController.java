package org.thothlab.devilsvault.customercontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.DebitAccount;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.CustomerModel.BankAccount.AccountType;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerAccountsDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.ExtUserDaoImpl;

@Controller
public class MerchantPaymentController {
	
	@RequestMapping("/merchantpayment")
	public ModelAndView helloworld(){
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		Customer customer = cust_dao.getCustomer(101);
		ModelAndView model = new ModelAndView("customerPages/merchantMakePayment");
		model.addObject("merchant_secret",customer.getMerchant_secret());
		System.out.print(customer.getMerchant_secret());
		return model;
	}
}

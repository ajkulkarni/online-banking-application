package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.jdbccontrollers.RequestDOA.RequestDAOExternal;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardDOA;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.thothlab.devilsvault.jdbccontrollers.model.Request;

@Controller
public class CreditCardDashboardController {

	@RequestMapping("/credithome")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/creditHomePage");
	
		CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		Customer cust = cust_dao.getCustomer(100);
		
		CreditAccount account = doa.getCreditAccount(cust);
		System.out.println(account.getAccountNumber());
		model.addObject("creditAccount",account);
		
		CreditCardDOA transdao = CustomerDAOHelper.creditCardDAO();
		List<TransactionModel> transactions = transdao.getAllTransactions(account);
		model.addObject("transations", transactions );
		
		return model;
	}
}

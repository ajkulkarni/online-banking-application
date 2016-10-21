package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public ModelAndView showCreditHome(){
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
		
		CreditCardDOA transdao1 = CustomerDAOHelper.creditCardDAO();
		List<TransactionModel> transactions1 = transdao1.getLastOneMonTransactions(account);
		model.addObject("transations_one", transactions1 );
		
		CreditCardDOA transdao3 = CustomerDAOHelper.creditCardDAO();
		List<TransactionModel> transactions3 = transdao3.getLastThreeMonTransactions(account);
		model.addObject("transations_three", transactions3 );
		
		CreditCardDOA transdao6 = CustomerDAOHelper.creditCardDAO();
		List<TransactionModel> transactions6 = transdao6.getLastSixMonTransactions(account);
		model.addObject("transations_six", transactions6 );
		
		
		return model;
	}
	
	public @ResponseBody String byParameter(@RequestParam("interval") String interval) {
	    return "Mapped by path + method + presence of query parameter! (MappingController) - foo = "
	           + interval;
	}
	
	
	
	@RequestMapping(value="getTransactions", method = RequestMethod.POST)
	
	public ModelAndView getSearchResultViaAjax(@RequestParam("monthPicker") String interval) {

		System.out.println("HERE" + interval);
		
		ModelAndView model = new ModelAndView("redirect:"+"credithome");
		
		CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		Customer cust = cust_dao.getCustomer(100);
		
		CreditAccount account = doa.getCreditAccount(cust);
		System.out.println(account.getAccountNumber());
		model.addObject("creditAccount",account);
		
		CreditCardDOA transdao = CustomerDAOHelper.creditCardDAO();
		List<TransactionModel> transactions = transdao.getAllTransactions(account);
		transactions.remove(0);
		model.addObject("transations", transactions );
	
		return model;

	}
	
	

}

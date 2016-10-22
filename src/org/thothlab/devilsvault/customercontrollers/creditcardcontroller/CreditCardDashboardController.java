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
		
		model.addObject("creditAccount",account);
		
		CreditCardDOA transdao = CustomerDAOHelper.creditCardDAO();
		List<TransactionModel> transactions = transdao.getAllTransactions(account);
		model.addObject("transations", transactions );
		
		
		System.out.println("Here");
		
		return model;
	}


	@RequestMapping(value="getTransactions", method = RequestMethod.POST)
	public ModelAndView getSearchResultViaAjax(@RequestParam("monthPicker") String interval) {

	
		ModelAndView model = new ModelAndView("customerPages/creditHomePage");
		
		CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		Customer cust = cust_dao.getCustomer(100);
		
		CreditAccount account = doa.getCreditAccount(cust);
		
		model.addObject("creditAccount",account);
		
		CreditCardDOA transdao1 = CustomerDAOHelper.creditCardDAO();
		
		if (interval.equals("Last one month")) {
			System.out.println("One month");
			List<TransactionModel> transactions1 = transdao1.getLastOneMonTransactions(account);
			model.addObject("transations", transactions1 );
			
		} else if (interval.equals("Last 3 months")) {
			System.out.println("3 month");
			List<TransactionModel> transactions3 = transdao1.getLastThreeMonTransactions(account);
			model.addObject("transations", transactions3 );
			
		} else if (interval.equals("Last 6 months")) {
			System.out.println("6");
			List<TransactionModel> transactions6 = transdao1.getLastSixMonTransactions(account);
			model.addObject("transations", transactions6 );
		}
	
		return model;

	}
	
	

}

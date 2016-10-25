package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardDOA;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;

@Controller
public class CreditCardDashboardController {
	public ModelAndView model_ch = new ModelAndView("customerPages/creditHomePage");

	@RequestMapping("/credithome")
	public ModelAndView showCreditHome(){
		
		
		CreditAccount account = getCreditInfoForUser (100);
		
		model_ch.addObject("creditAccount",account);
		
		CreditCardDOA transdao = CustomerDAOHelper.creditCardDAO();
		List<TransactionModel> transactions = transdao.getAllTransactions(account);
		model_ch.addObject("transations", transactions );
		
		
		int month = new Date().getMonth();
		int year = new Date().getYear() + 1900;
		
		List<String> months = new ArrayList<>();
		for (int i = 0; i <= month; i ++ ) {
			String value = getMonthFromNum(i);
			
			value = value +  " " + year;
			months.add(value);
		}
		String selectedMonth = getMonthFromNum(month) +" " + year;
		model_ch.addObject("selectedMonth",selectedMonth);
		model_ch.addObject("months",months);
		
		return model_ch;
	}


	@RequestMapping(value="getTransactions", method = RequestMethod.POST)
	public ModelAndView getTransactionsBasedOnInterval(@RequestParam("monthPicker") String interval) {

	
		ModelAndView model = new ModelAndView("customerPages/creditHomePage");
		
		CreditAccount account = getCreditInfoForUser (100);
		
		model.addObject("creditAccount",account);
		
		CreditCardDOA transdao1 = CustomerDAOHelper.creditCardDAO();
		int monthNum = getMonthFromString(interval);
		
		
		int month = new Date().getMonth();
		int year = new Date().getYear() + 1900;
		
		List<String> months = new ArrayList<>();
		for (int i = 0; i <= month; i ++ ) {
			String value = getMonthFromNum(i);
			
			value = value +  " " + year;
			months.add(value);
		}
		
		model.addObject("selectedMonth",interval);
		model.addObject("months",months);
		List<TransactionModel> transactions6 = transdao1.getTransactionForMonth(account, monthNum);
		model.addObject("transations", transactions6 );
		
		return model;

	}
	
	private CreditAccount getCreditInfoForUser(int userId) {
		CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		Customer cust = cust_dao.getCustomer(100);
		
		CreditAccount account = doa.getCreditAccount(cust);
		return account;
	}
	
	private int getMonthFromString(String monthStr) {
		int monthNum = 0;
		if (monthStr.contains("Jan")) {
			monthNum = 0;
		} else if  (monthStr.contains("Feb")) {
			monthNum = 1;
		} else if  (monthStr.contains("Mar")) {
			monthNum = 2;
		} else if  (monthStr.contains("April")) {
			monthNum = 3;
		} else if  (monthStr.contains("May")) {
			monthNum = 4;
		} else if  (monthStr.contains("June")) {
			monthNum = 5;
		}  else if  (monthStr.contains("July")) {
			monthNum = 6;
		}  else if  (monthStr.contains("Aug")) {
			monthNum = 7;
		}  else if  (monthStr.contains("Sept")) {
			monthNum = 8;
		}  else if  (monthStr.contains("Oct")) {
			monthNum = 9;
		}  else if  (monthStr.contains("Nov")) {
			monthNum = 10;
		}  else if  (monthStr.contains("Dec")) {
			monthNum = 11;
		} 
		return monthNum;
	}
	
	private String getMonthFromNum(int i) {
		String value = "";
		switch (i) {
		case 0:
			value = "Jan";
			break;
		case 1:
			value = "Feb";
			break;
		case 2:
			value = "Mar";
			break;
		case 3:
			value = "April";
			break;
		case 4:
			value = "May";
			break;
		case 5:
			value = "June";
			break;
		case 6:
			value = "July";
			break;
		case 7:
			value = "Aug";
			break;
		case 8:
			value = "Sept";
			break;
		case 9:
			value = "Oct";
			break;
		case 10:
			value = "Nov";
			break;
		case 11:
			value = "Dec";
			break;
		
		
			
		}
		return value;
	}
	

}

package org.thothlab.devilsvault.controllers.creditcardController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.controllers.security.ExceptionHandlerClass;
import org.thothlab.devilsvault.dao.creditcard.CreditCardDOA;
import org.thothlab.devilsvault.dao.creditcard.CustomerDAOHelper;
import org.thothlab.devilsvault.dao.customer.CustomerDAO;
import org.thothlab.devilsvault.db.model.CreditAccount;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.TransactionModel;

@Controller
public class CreditCardDashboardController {
	public ModelAndView model_ch = new ModelAndView("customerPages/creditHomePage");

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");	
	}
	
	@ExceptionHandler(ExceptionHandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/raiseexception";
    }
	
	@RequestMapping("/customer/credithome")
	public ModelAndView showCreditHome(HttpServletRequest request){
		setGlobals(request);
		
		try{
			ModelAndView model = new ModelAndView("customerPages/creditHomePage");
			
			CreditAccount account = getCreditInfoForUser (userID);
			
			model.addObject("creditAccount",account);
			
			CreditCardDOA transdao1 = CustomerDAOHelper.creditCardDAO();
			
			
			
			int month = new Date().getMonth();
			int year = new Date().getYear() + 1900;
			
			List<String> months = new ArrayList<>();
			for (int i = 0; i <= month + 1; i ++ ) {
				String value = getMonthFromNum(i);
				
				value = value +  " " + year;
				months.add(value);
			}
			
			model.addObject("selectedMonth",month);
			model.addObject("months",months);
			List<TransactionModel> transactions6 = transdao1.getTransactionForMonth(account, month+1);
			model.addObject("transations", transactions6 );
			
			return model;
		}
		catch (Exception e){
			throw new ExceptionHandlerClass(); 
		}
	}

	

	@RequestMapping(value="/customer/getTransactions", method = RequestMethod.POST)
	public ModelAndView getTransactionsBasedOnInterval(HttpServletRequest request,@RequestParam("monthPicker") String interval) {

		setGlobals(request);
		try{
			ModelAndView model = new ModelAndView("customerPages/creditHomePage");
			
			CreditAccount account = getCreditInfoForUser (userID);
			
			model.addObject("creditAccount",account);
			
			CreditCardDOA transdao1 = CustomerDAOHelper.creditCardDAO();
			int monthNum = getMonthFromString(interval);
			
			
			int month = new Date().getMonth();
			int year = new Date().getYear() + 1900;
			
			List<String> months = new ArrayList<>();
			for (int i = 0; i <= month + 1; i ++ ) {
				String value = getMonthFromNum(i);
				
				value = value +  " " + year;
				months.add(value);
			}
			
			model.addObject("selectedMonth",interval);
			model.addObject("months",months);
			List<TransactionModel> transactions6 = transdao1.getTransactionForMonth(account, monthNum);
			model.addObject("transations", transactions6 );
			
			return model;
		}catch (Exception e){
			throw new ExceptionHandlerClass(); 
		}
		

	}
	
	private CreditAccount getCreditInfoForUser(int userId) {
		try{
			CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
			CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
			Customer cust = cust_dao.getCustomer(userId);
			
			CreditAccount account = doa.getCreditAccount(cust);
			return account;
		}catch (Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
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

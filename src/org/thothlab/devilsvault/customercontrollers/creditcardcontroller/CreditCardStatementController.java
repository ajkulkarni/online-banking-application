package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;
import org.thothlab.devilsvault.jdbccontrollers.RequestDOA.RequestDAOExternal;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardDOA;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.thothlab.devilsvault.jdbccontrollers.model.Request;

@Controller
public class CreditCardStatementController {

	@RequestMapping("/statement")
	public ModelAndView creditStatement(){
		ModelAndView model = new ModelAndView("customerPages/creditStatementPage");
	
		CreditCardDOA dao = CustomerDAOHelper.creditCardDAO();
		List<TransactionModel> transactions = dao.getAllTransactions(null);
		model.addObject("transations", transactions );
		return model;
	}
}

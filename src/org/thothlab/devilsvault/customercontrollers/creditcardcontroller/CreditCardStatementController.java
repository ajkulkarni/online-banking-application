package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;

@Controller
public class CreditCardStatementController {

	@RequestMapping("/statement")
	public ModelAndView creditStatement(){
		ModelAndView model = new ModelAndView("customerPages/creditStatementPage");
		
		List<TransactionModel> transactions = new ArrayList<>();
		TransactionModel transModel = new TransactionModel();
		transModel.setAmount(100);
		transModel.setPayee("Payee1");
		transModel.setPaymentType(2);
		transModel.setOwner("Owner 1");
		transModel.setDescription("Payment at walmart");
		transactions.add(transModel);	
		
		transModel = new TransactionModel();
		transModel.setAmount(100);
		transModel.setPayee("Payee1");
		transModel.setPaymentType(2);
		transModel.setOwner("Owner 1");
		transModel.setDescription("Payment at walmart");
		transactions.add(transModel);

		transModel = new TransactionModel();
		transModel.setAmount(110);
		transModel.setPayee("Payee2");
		transModel.setPaymentType(2);
		transModel.setOwner("Owner 1");
		transModel.setDescription("Payment at walmart");
		transactions.add(transModel);
		
		transModel = new TransactionModel();
		transModel.setAmount(22);
		transModel.setPayee("Payee1");
		transModel.setPaymentType(2);
		transModel.setOwner("Owner 1");
		transModel.setDescription("Payment at walmart");
		transactions.add(transModel);
		
		model.addObject("transations", transactions );
		
		return model;
	}
}

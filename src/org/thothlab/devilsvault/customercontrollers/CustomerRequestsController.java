package org.thothlab.devilsvault.customercontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.DebitAccount;
import org.thothlab.devilsvault.CustomerModel.BankAccount.AccountType;

@Controller
public class CustomerRequestsController {

	@RequestMapping("/customerRequests")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/customerRequest");
		
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		
		model.addObject("cAccount", checkingAccount );
		model.addObject("sAccount", savingAccount );
		model.addObject("ccAccount", creditAccount );
	
		return model;
	}
}

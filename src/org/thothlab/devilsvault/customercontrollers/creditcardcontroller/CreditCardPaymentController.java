package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;

@Controller
public class CreditCardPaymentController {

	@RequestMapping("/creditPayment")
	public ModelAndView showCreditPaymentPage(){
		ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
		return model;
	}
	
	@RequestMapping(value="makePayement", method = RequestMethod.POST)
	public ModelAndView makePayment(@RequestParam("inputAmountField") String amount) {
		
		boolean success = false;
		
		ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
		if (success) {
			model.addObject("paymentResult", "1");
		} else {
			model.addObject("paymentResult", "0");
		}
		return model;
	}
}

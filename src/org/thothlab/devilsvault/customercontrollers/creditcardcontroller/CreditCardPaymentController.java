package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreditCardPaymentController {

	@RequestMapping("/creditPayment")
	public ModelAndView showCreditPaymentPage(){
		ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
		return model;
	}
}

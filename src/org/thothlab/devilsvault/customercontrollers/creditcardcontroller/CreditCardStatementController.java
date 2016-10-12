package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreditCardStatementController {

	@RequestMapping("/statement")
	public ModelAndView creditStatement(){
		ModelAndView model = new ModelAndView("customerPages/creditStatementPage");
		model.addObject("min_payment_due","$123");
		return model;
	}
}

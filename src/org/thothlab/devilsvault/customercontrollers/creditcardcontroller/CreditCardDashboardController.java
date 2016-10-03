package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreditCardDashboardController {

	@RequestMapping("/credithome")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/creditHomePage");
		model.addObject("min_payment_due","$123");
		return model;
	}
}

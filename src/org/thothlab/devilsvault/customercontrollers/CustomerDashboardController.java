package org.thothlab.devilsvault.customercontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class CustomerDashboardController {
	
	@RequestMapping("/customerhome")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/customerDashboard");
		model.addObject("msg","Hello Deepesh");
		return model;
	}

	@RequestMapping("/accounts_savings")
	public ModelAndView hellosavings(){
		ModelAndView model = new ModelAndView("customerPages/accountsSavingsPage");
		model.addObject("msg","Hello Accounts");
		return model;
	}

	@RequestMapping("/accounts_checkings")
	public ModelAndView hellosavings(){
		ModelAndView model = new ModelAndView("customerPages/accountsCheckingsPage");
		model.addObject("msg","Hello Checkings");
		return model;
	}
}

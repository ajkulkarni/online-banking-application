package org.thothlab.devilsvault.controllers.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerDashboardController {
	
	@RequestMapping("/customer/home")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/customerDashboard");
		model.addObject("msg","Hello Deepesh");
		return model;
	}
}

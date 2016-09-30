package org.thothlab.devilsvault.employeecontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeDashboardController {
	
	@RequestMapping("/employeehome")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
		model.addObject("msg","Hello Deepesh");
		return model;
	}
}

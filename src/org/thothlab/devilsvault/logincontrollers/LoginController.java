package org.thothlab.devilsvault.logincontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("LoginPage");
		model.addObject("msg","Hello Deepesh");
		return model;
	}
}

package org.thothlab.devilsvault.ExternalUserController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExternalUserController {
	@RequestMapping("/externaluserdashboard")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("/customerPages/customerDashboard");
		model.addObject("msg","Hello Deepesh");
		return model;
	}

}

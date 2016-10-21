package org.thothlab.devilsvault.customercontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountsBalanceController {

	@RequestMapping("/accountsBalance")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/accountsAddWithdrawBalance");
		return model;
	}
}

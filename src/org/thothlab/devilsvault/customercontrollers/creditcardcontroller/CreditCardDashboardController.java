package org.thothlab.devilsvault.customercontrollers.creditcardcontroller;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.jdbccontrollers.RequestDOA.RequestDAOExternal;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardDOA;
import org.thothlab.devilsvault.jdbccontrollers.model.Request;

@Controller
public class CreditCardDashboardController {

	@RequestMapping("/credithome")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("customerPages/creditHomePage");
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CreditCardDOA doa = ctx.getBean("creditCardDOA", CreditCardDOA.class);
		CreditAccount account = doa.getCreditAccount(null);
		model.addObject("creditAccount",account);
		return model;
	}
}

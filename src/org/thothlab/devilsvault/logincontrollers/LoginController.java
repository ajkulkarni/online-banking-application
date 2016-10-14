package org.thothlab.devilsvault.logincontrollers;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.jdbccontrollers.RequestDOA.RequestDAOExternal;
import org.thothlab.devilsvault.jdbccontrollers.model.Request;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public ModelAndView helloworld(){

		
		ModelAndView model = new ModelAndView("LoginPage");
		model.addObject("msg","Hello Deepesh");
		return model;
	}
	
	
	@RequestMapping("/requestview")
	public ModelAndView RequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		RequestDAOExternal requestDAO = ctx.getBean("requestDAOExternal", RequestDAOExternal.class);
		List<Request> reqList = requestDAO.getAll();
		//requestDAO.createRequest();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list",reqList);
		ctx.close();
		return model;
	}
}

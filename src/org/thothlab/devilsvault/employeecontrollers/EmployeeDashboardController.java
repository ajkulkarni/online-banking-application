package org.thothlab.devilsvault.employeecontrollers;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.request.ExternalRequestDao;
import org.thothlab.devilsvault.db.model.Request;

@Controller
public class EmployeeDashboardController {
	
	@RequestMapping("/employeehome")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
		model.addObject("msg","Hello Deepesh");
		return model;
	}
	
	@RequestMapping("/requestview")
	public ModelAndView RequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestDao requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		List<Request> reqList = requestDAO.getAll();
		//requestDAO.createRequest();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list",reqList);
		ctx.close();
		return model;
	}
}

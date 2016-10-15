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
	
	@RequestMapping("/viewopenextrequest")
	public ModelAndView OpenRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestDao requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		List<Request> requestList = requestDAO.getAllPending();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list",requestList);
		ctx.close();
		return model;
	}
	
	@RequestMapping("/viewcompletedextrequest")
	public ModelAndView CompletedRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestDao requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		List<Request> requestList = requestDAO.getAllCompleted();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list",requestList);
		ctx.close();
		return model;
	}
	
	@RequestMapping("/createinternalrequest")
	public ModelAndView CreateRequestContoller(Request request){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestDao requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		boolean result = requestDAO.save(request, "external");
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		if (result)
			model.addObject("msg","Request Created");
		else
			model.addObject("msg","Request Failed");
		ctx.close();
		return model;
	}
}

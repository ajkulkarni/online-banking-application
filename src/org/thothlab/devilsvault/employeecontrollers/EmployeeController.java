package org.thothlab.devilsvault.employeecontrollers;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.request.ExternalRequestDaoImpl;
import org.thothlab.devilsvault.db.model.Request;

@Controller
public class EmployeeController {
	
	@RequestMapping("/employee/home")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
		return model;
	}
	
	@RequestMapping("/employee/userdetails")
	public ModelAndView RequestContoller(){
		ModelAndView model = new ModelAndView("employeePages/employeeUserDetails");
		model.addObject("request_list","test message");
		return model;
	}
	
	@RequestMapping("/employee/transaction")
	public ModelAndView TransactionContoller(){
		ModelAndView model = new ModelAndView("employeePages/employeeTransaction");
		model.addObject("request_list","test message");
		return model;
	}
	
	@RequestMapping("/viewopenextrequest")
	public ModelAndView OpenRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		List<Request> requestList = requestDAO.getAllPending();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list",requestList);
		ctx.close();
		return model;
	}
	
	@RequestMapping("/viewcompletedextrequest")
	public ModelAndView CompletedRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		List<Request> requestList = requestDAO.getAllCompleted();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list",requestList);
		ctx.close();
		return model;
	}
	
	@RequestMapping("/createinternalrequest")
	public ModelAndView CreateRequestContoller(Request request){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
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

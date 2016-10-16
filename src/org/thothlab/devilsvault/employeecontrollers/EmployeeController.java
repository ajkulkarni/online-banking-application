package org.thothlab.devilsvault.employeecontrollers;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.dashboard.PendingStatisticsDao;
import org.thothlab.devilsvault.dao.request.ExternalRequestDaoImpl;
import org.thothlab.devilsvault.dao.request.InternalRequestDaoImpl;
import org.thothlab.devilsvault.db.model.InternalUser;
import org.thothlab.devilsvault.db.model.Request;
import org.thothlab.devilsvault.db.model.Transaction;

@Controller
public class EmployeeController {
	
	@RequestMapping("/employee/userdetails")
	public ModelAndView UserDetailsContoller(){
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
	
	@RequestMapping("/employee/pendingrequest")
	public ModelAndView PendingRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = externalRequestDao.getAllPending();
        if(externalRequestList.size() > 10)
            externalRequestList = externalRequestList.subList(externalRequestList.size()-10, externalRequestList.size());
        List<Request> internalRequestList = internalRequestDao.getAllPending();
        if(internalRequestList.size() > 10)
            internalRequestList = internalRequestList.subList(internalRequestList.size()-10, internalRequestList.size());
        ModelAndView model = new ModelAndView("employeePages/PendingRequest");
        model.addObject("internal_list",internalRequestList);
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
	
	@RequestMapping("/employee/completedrequest")
	public ModelAndView CompletedRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        ExternalRequestDaoImpl externalRequestDao = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
        InternalRequestDaoImpl internalRequestDao = ctx.getBean("internalRequestDao", InternalRequestDaoImpl.class);
        List<Request> externalRequestList = externalRequestDao.getAllCompleted();
        if(externalRequestList.size() > 10)
            externalRequestList = externalRequestList.subList(externalRequestList.size()-10, externalRequestList.size());
        List<Request> internalRequestList = internalRequestDao.getAllCompleted();
        if(internalRequestList.size() > 10)
            internalRequestList = internalRequestList.subList(internalRequestList.size()-10, internalRequestList.size());
        ModelAndView model = new ModelAndView("employeePages/CompleteRequest");
        model.addObject("internal_list",internalRequestList);
        model.addObject("external_list",externalRequestList);
        ctx.close();
        return model;
	}
	
	@RequestMapping("/employee/home")
	public ModelAndView PendingDashboardContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		PendingStatisticsDao pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
		HashMap<String,Integer> stats = new HashMap<String,Integer>();
		stats = pendingStatisticsDao.getPendingStatistics();
		List<Request> internal_list = pendingStatisticsDao.getPendingInternalRequests();
		if(internal_list.size() > 5)
			internal_list = internal_list.subList(internal_list.size()-5, internal_list.size());
		List<Request> external_list = pendingStatisticsDao.getPendingExternalRequests();
		if(external_list.size() > 5)
			external_list = external_list.subList(external_list.size()-5, external_list.size());
		List<InternalUser> user_list = pendingStatisticsDao.getPendingUserRegistrations();
		if(user_list.size() > 5)
			user_list = user_list.subList(user_list.size()-5, user_list.size());
		List<Transaction> transaction_list = pendingStatisticsDao.getPendingTransactions();
		if(transaction_list.size() > 5)
			transaction_list = transaction_list.subList(transaction_list.size()-5, transaction_list.size());
		ModelAndView model = new ModelAndView("employeePages/employeeDashboard");
		model.addObject("stats",stats);
		model.addObject("internal_list",internal_list);
		model.addObject("external_list",external_list);
		model.addObject("user_list",user_list);
		model.addObject("transaction_list",transaction_list);
		ctx.close();
		return model;
	}
	
	@RequestMapping("/employee/openrequests")
	public ModelAndView OpenRequestContoller(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		List<Request> requestList = requestDAO.getAllPending();
		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
		model.addObject("request_list",requestList);
		ctx.close();
		return model;
	}
	
//	@RequestMapping("/employee/completedrequests")
//	public ModelAndView CompletedRequestContoller(){
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
//		ExternalRequestDaoImpl requestDAO = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
//		List<Request> requestList = requestDAO.getAllCompleted();
//		ModelAndView model = new ModelAndView("employeePages/employeeRequest");
//		model.addObject("request_list",requestList);
//		ctx.close();
//		return model;
//	}
	
	@RequestMapping("/employee/createrequest")
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

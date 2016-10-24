package org.thothlab.devilsvault.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thothlab.devilsvault.dao.request.ExternalRequestDaoImpl;
import org.thothlab.devilsvault.db.model.Request;

public class TestRequestDao {
	
	private ExternalRequestDaoImpl externalRequest;
	
	@Test
	public void testCreateRequest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		System.out.println(externalRequest.createRequest());
		ctx.close();
	}
	
	@Test
	public void testGetAllPending() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		List<Request> requestList = externalRequest.getAllPending();
		System.out.println(requestList.size());
		ctx.close();
	}
	
	@Test
	public void testApproveRequest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		//externalRequest.approveRequest(1, "external");
		ctx.close();
	}
	
	@Test
	public void testRejectRequest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		//externalRequest.rejectRequest(4, "external");
		ctx.close();
	}
	
	@Test
	public void testGetAllCompleted() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		List<Request> requestList = externalRequest.getAllCompleted();
		System.out.println(requestList.size());
		ctx.close();
	}
	
//	@Test
//	public void testGetById() {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
//		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
//		Request request = externalRequest.getById(1);
//		System.out.println(request.getStatus());
//		ctx.close();
//	}
//	
//	@Test
//	public void testGetByUserId() {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
//		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
//		Request request = externalRequest.getByUserId(1);
//		System.out.println(request.getStatus());
//		ctx.close();
//	}
	
	@Test
	public void testSave() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDaoImpl.class);
		List<Request> requestList = externalRequest.getAllPending();
		Boolean check = false;
		for(Request req : requestList) {
			if(req.getApprover().equals("ajay"))
				check = externalRequest.save(req, "external");
		}
		System.out.println(check);
		ctx.close();

	}
	
}

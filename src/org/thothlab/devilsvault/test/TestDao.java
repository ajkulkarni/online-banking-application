package org.thothlab.devilsvault.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thothlab.devilsvault.dao.request.ExternalRequestDao;
import org.thothlab.devilsvault.db.model.Request;

public class TestDao {
	
	private ExternalRequestDao externalRequest;
	
	@Test
	public void testCreateRequest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		System.out.println(externalRequest.createRequest());
	}
	
	@Test
	public void testGetAllPending() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		List<Request> requestList = externalRequest.getAllPending();
		System.out.println(requestList.size());
	}
	
	@Test
	public void testGetAllCompleted() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		List<Request> requestList = externalRequest.getAllCompleted();
		System.out.println(requestList.size());
	}
	
	@Test
	public void testSave() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		List<Request> requestList = externalRequest.getAllPending();
		Boolean check = false;
		for(Request req : requestList) {
			if(req.getApprover().equals("ajay"))
				check = externalRequest.save(req, "external");
		}
		System.out.println(check);
	}
	
}

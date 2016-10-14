package org.thothlab.devilsvault.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thothlab.devilsvault.dao.request.ExternalRequestDao;

public class TestDao {
	
	private ExternalRequestDao externalRequest;
	
	@Test
	public void testCreateRequest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		externalRequest = ctx.getBean("externalRequestDao", ExternalRequestDao.class);
		System.out.println(externalRequest.createRequest());
	}
}

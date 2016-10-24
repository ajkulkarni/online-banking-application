package org.thothlab.devilsvault.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thothlab.devilsvault.dao.dashboard.PendingStatisticsDao;

public class TestPendingStatistics {

	private PendingStatisticsDao pendingStatisticsDao;
	
//	@Test
//	public void testGetPendingUserRegistrations() {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
//		pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
//		System.out.println(pendingStatisticsDao.getPendingUserRegistrations());
//		ctx.close();
//	}
	
	@Test
	public void testGetPendingInternalRequests() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
		//System.out.println(pendingStatisticsDao.getPendingInternalRequests());
		ctx.close();
	}
	
	@Test
	public void testGetPendingExternalRequests() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
		System.out.println(pendingStatisticsDao.getPendingExternalRequests());
		ctx.close();
	}
	
	@Test
	public void testGetPendingTransactions() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
		System.out.println(pendingStatisticsDao.getPendingTransactions());
		ctx.close();
	}
	
	@Test
	public void testGetPendingStatistics() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		pendingStatisticsDao = ctx.getBean("pendingStatistics", PendingStatisticsDao.class);
		HashMap<String,Integer> items = new HashMap<String,Integer>();
		//items = pendingStatisticsDao.getPendingStatistics();
		for (Map.Entry<String, Integer> entry : items.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
		}
		ctx.close();
	}
}

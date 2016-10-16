package org.thothlab.devilsvault.test;

import java.util.Date;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thothlab.devilsvault.dao.transaction.TransactionDao;
import org.thothlab.devilsvault.db.model.Transaction;

public class TestTransactions {

	@Test
	public void testCreate()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		TransactionDao transactionDAO = ctx.getBean("TransactionDao", TransactionDao.class);
		transactionDAO.createTransaction();
		ctx.close();
	}
	@Test
	public void testSave()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		TransactionDao transactionDAO = ctx.getBean("TransactionDao", TransactionDao.class);
		String type = "transaction_pending";
		java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
		Transaction transaction = new Transaction();
		transaction.setId(1);
		transaction.setPayee_id(1);
		transaction.setPayer_id(1);
		transaction.setAmount(10000);
		transaction.setHashvalue("Testing");
		transaction.setApprover("");
		transaction.setCritical(false);
		transaction.setDescription("hsgdhashdgdhagsdhgsdhgh");
		transaction.setStatus("approved");
		transaction.setTransaction_type("nothing");
		transaction.setTimestamp_created(sqlDate);
		transaction.setTimestamp_updated(sqlDate);
		transactionDAO.save(transaction, type);
		ctx.close();

	}
	@Test
	public void testgetById()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		TransactionDao transactionDAO = ctx.getBean("TransactionDao", TransactionDao.class);
		Transaction transaction;
		transaction = transactionDAO.getById(1, "transaction_pending");
		System.out.println(transaction);
		ctx.close();

	}
}

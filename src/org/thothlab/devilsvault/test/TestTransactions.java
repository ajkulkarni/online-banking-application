package org.thothlab.devilsvault.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thothlab.devilsvault.dao.transaction.InternalTransactionDaoImpl;
import org.thothlab.devilsvault.db.model.Transaction;

public class TestTransactions {

	@Test
	public void testCreate()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
		transactionDAO.createTransaction();
		ctx.close();
	}
//	@Test
//	public void testSave()
//	{
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
//		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
//		String type = "transaction_pending";
//		java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
//		Transaction transaction = new Transaction();
//		transaction.setId(1);
//		transaction.setPayee_id(1);
//		transaction.setPayer_id(1);
//		transaction.setAmount(new BigDecimal(10000));
//		transaction.setHashvalue("Testing");
//		transaction.setApprover("");
//		transaction.setCritical(false);
//		transaction.setDescription("hsgdhashdgdhagsdhgsdhgh");
//		transaction.setStatus("approved");
//		transaction.setTransaction_type("nothing");
//		transaction.setTimestamp_created(sqlDate);
//		transaction.setTimestamp_updated(sqlDate);
//		transactionDAO.save(transaction, type);
//		ctx.close();
//
//	}
	@Test
	public void testgetById()
	{
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
//		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
//		Transaction transaction;
//		transaction = transactionDAO.getById(1, "transaction_pending");
//		System.out.println(transaction);
//		ctx.close();

	}
	@Test
	public void testgetAllPendingTransactions()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
		List <Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.addAll(transactionDAO.getAllPending());
		System.out.println(transactionList.size());
		ctx.close();
	}
	@Test
	public void testgetAllCompletedTransactions()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
		List <Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.addAll((transactionDAO.getAllCompleted()));
		System.out.println(transactionList.size());
		ctx.close();
	}
	@Test
	public void testdeleteById()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		//InternalTransactionDaoImpl transactionDAO = ctx.getBean("TransactionSpecificDao", InternalTransactionDaoImpl.class);
//		System.out.println(transactionDAO.deleteById(8));
		ctx.close();
	}
}

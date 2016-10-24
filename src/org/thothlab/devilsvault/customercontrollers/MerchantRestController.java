package org.thothlab.devilsvault.customercontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.DebitAccount;
import org.thothlab.devilsvault.CustomerModel.MerchantPayment;
import org.thothlab.devilsvault.db.model.Transaction;
import org.thothlab.devilsvault.CustomerModel.BankAccount;
import org.thothlab.devilsvault.CustomerModel.BankAccount.AccountType;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.ExtUserDaoImpl;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.ExternalTransactionDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CreditCardDOA;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerAccountsDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAO;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.CustomerDAOHelper;
import org.springframework.http.ResponseEntity;
import org.thothlab.devilsvault.jdbccontrollers.customerdoa.TransferDAO;

@RestController
public class MerchantRestController {
	private TransferDAO transferDAO;
	
	
	private CreditCardDOA creditcarddao;
	
	
	private ExternalTransactionDAO extTransactionDAO;
	private CreditAccount bankaccount;
	private CustomerAccountsDAO sAccountDAO;
	CreditCardDOA ccdao = CustomerDAOHelper.creditCardDAO();

	@GetMapping("/merchants")
	public List getCustomers() {
		CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
		Customer cust = cust_dao.getCustomer(100);
		return cust_dao.list();
	}
	
	@PostMapping("/make_payment")
	public ResponseEntity makePayment(@RequestBody MerchantPayment merchantpayment) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		sAccountDAO = ctx.getBean("CustomerAccountsDAO",CustomerAccountsDAO.class);		
		boolean valid_payment = false;
		try 
		{
			System.out.print(merchantpayment.getCard_no());
		bankaccount = ccdao.getAccount(merchantpayment.getCard_no(), "", "", "");
		transferDAO = CustomerDAOHelper.transferDAO();
		if(bankaccount != null){
			System.out.print(bankaccount.getAccountNumber());
			valid_payment = transferDAO.validateAmount(bankaccount.getAccountNumber(), 1);
		}
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		System.out.print(valid_payment);
		if(valid_payment){
			
			
			int merchant_account = sAccountDAO.getMerchantAccountFromSecret(merchantpayment.getMerchant_secret());
//			System.out.printf("%n");
//			System.out.print(bankaccount.getAccountNumber());
//			System.out.printf("%n");
//			System.out.print(merchant_account);
//			System.out.printf("%n");
//			System.out.print(merchantpayment.getAmount());
//			System.out.printf("%n");
//			System.out.print(merchantpayment.getDescription());
//			
			extTransactionDAO = ctx.getBean("extTransactionDAO",ExternalTransactionDAO.class);
			
			Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(bankaccount.getAccountNumber(), merchantpayment.getAmount(), merchant_account, merchantpayment.getDescription(), "external");

			
			extTransactionDAO.save(extTransferTrans, "transaction_pending");
			System.out.print("Sucess");
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
		
	}
}
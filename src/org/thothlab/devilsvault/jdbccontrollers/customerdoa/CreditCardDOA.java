package org.thothlab.devilsvault.jdbccontrollers.customerdoa;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.CustomerModel.CreditAccount;
import org.thothlab.devilsvault.CustomerModel.Customer;
import org.thothlab.devilsvault.CustomerModel.TransactionModel;

@Repository ("creditCardDOA")
public class CreditCardDOA extends CustomerDOAImpl {
	
	/**
	 * Get the credit account details for the user.
	 * @param customer
	 * @return
	 */
	CreditAccount getCreditAccount(Customer customer) {
		return null;
	}
	
	/**
	 * Returns all the credit card transaction for the user.
	 * @return
	 */
	List<TransactionModel> getAllTransactions(CreditAccount account) {
		return null;
	}
	
	
	

}

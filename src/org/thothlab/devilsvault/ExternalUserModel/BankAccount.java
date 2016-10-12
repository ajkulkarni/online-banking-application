package org.thothlab.devilsvault.ExternalUserModel;

import java.util.List;

public class BankAccount {
	
	public enum AccountType {
		SAVINGS, CHECKING, CREDIT
	}
	
	private ExtUser owner;
	private int accountNumber;
	private AccountType accountType;
	private List<TransactionModel> transactionList;

	public ExtUser getOwner() {
		return owner;
	}

	public void setOwner(ExtUser owner) {
		this.owner = owner;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<TransactionModel> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<TransactionModel> transactionList) {
		this.transactionList = transactionList;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	

}

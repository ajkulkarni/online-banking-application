package org.thothlab.devilsvault.CustomerModel;

import java.util.Date;



public class CreditAccount extends BankAccount {
	
	private int Id;
	private int interset;
	private int credit_card_no;
	private int bank_accounts_id;
	private double availBalance;
	private double lastBillAmount;
	private Date dueDateTimestamp;
	private float apr;
	
	public int getInterset() {
		return interset;
	}

	public void setInterset(int interset) {
		this.interset = interset;
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getCredit_card_no() {
		return credit_card_no;
	}

	public void setCredit_card_no(int credit_card_no) {
		this.credit_card_no = credit_card_no;
	}

	public int getBank_accounts_id() {
		return bank_accounts_id;
	}

	public void setBank_accounts_id(int bank_accounts_id) {
		this.bank_accounts_id = bank_accounts_id;
	}
	
	public CreditAccount() {
		this.setAccountType(AccountType.CREDIT);
	}
	
	public double getAvailBalance() {
		return availBalance;
	}
	
	public void setAvailBalance(double availBalance) {
		this.availBalance = availBalance;
	}
	
	public double getLastBillAmount() {
		return lastBillAmount;
	}
	
	public void setLastBillAmount(double lastBillAmount) {
		this.lastBillAmount = lastBillAmount;
	}
	
	public Date getDueDateTimestamp() {
		return dueDateTimestamp;
	}
	
	public void setDueDateTimestamp(Date dueDateTimestamp) {
		this.dueDateTimestamp = dueDateTimestamp;
	}
	
	public float getApr() {
		return apr;
	}
	
	public void setApr(float apr) {
		this.apr = apr;
	}
	
	

}

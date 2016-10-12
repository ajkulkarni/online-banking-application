package org.thothlab.devilsvault.ExternalUserModel;



public class CreditAccount extends BankAccount {
	
	private double availBalance;
	private double lastBillAmount;
	private int dueDateTimestamp;
	private float apr;
	
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
	
	public int getDueDateTimestamp() {
		return dueDateTimestamp;
	}
	
	public void setDueDateTimestamp(int dueDateTimestamp) {
		this.dueDateTimestamp = dueDateTimestamp;
	}
	
	public float getApr() {
		return apr;
	}
	
	public void setApr(float apr) {
		this.apr = apr;
	}
	
	

}

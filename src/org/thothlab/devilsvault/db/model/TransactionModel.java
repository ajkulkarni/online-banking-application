package org.thothlab.devilsvault.db.model;

import java.sql.Date;

public class TransactionModel {
	private int id;
	private int payer_id;
	private int payee_id;
	private double amount;
	private String hashvalue;
	private String transaction_type;
	private String description;
	private String status;
	private String approver;
	private boolean critical;
	private Date timestamp_created;
	private Date timestamp_updated;
	private int isPending;
	
	private String pendingStrg;
	
	public String getPendingStrg() {
		if(this.isPending == 0)
			return "Completed";
		else
			return "Pending";
	}
	public int getIsPending() {
		return isPending;
	}
	public void setIsPending(int isPending) {
		this.isPending = isPending;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPayer_id() {
		return payer_id;
	}
	public void setPayer_id(int payer_id) {
		this.payer_id = payer_id;
	}
	public int getPayee_id() {
		return payee_id;
	}
	public void setPayee_id(int payee_id) {
		this.payee_id = payee_id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getHashvalue() {
		return hashvalue;
	}
	public void setHashvalue(String hashvalue) {
		this.hashvalue = hashvalue;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public boolean isCritical() {
		return critical;
	}
	public void setCritical(boolean critical) {
		this.critical = critical;
	}
	public Date getTimestamp_created() {
		return timestamp_created;
	}
	public void setTimestamp_created(Date timestamp_created) {
		this.timestamp_created = timestamp_created;
	}
	public Date getTimestamp_updated() {
		return timestamp_updated;
	}
	public void setTimestamp_updated(Date timestamp_updated) {
		this.timestamp_updated = timestamp_updated;
	}

}
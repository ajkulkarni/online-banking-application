package org.thothlab.devilsvault.CustomerModel;

public class TransactionModel {

private int ID;
private int owner;
private int payee;

private int amount;
private int timeStamp;

private int paymentType;
private String description;

public int getID() {
return ID;
}

public void setID(int iD) {
ID = iD;
}

public int getOwner() {
return owner;
}

public void setOwner(int owner) {
this.owner = owner;
}

public int getPayee() {
return payee;
}

public void setPayee(int payee) {
this.payee = payee;
}

public int getAmount() {
return amount;
}

public void setAmount(int amount) {
this.amount = amount;
}

public int getTimeStamp() {
return timeStamp;
}

public void setTimeStamp(int timeStamp) {
this.timeStamp = timeStamp;
}

public int getPaymentType() {
return paymentType;
}

public void setPaymentType(int paymentType) {
this.paymentType = paymentType;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}


}
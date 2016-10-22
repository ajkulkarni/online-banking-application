package org.thothlab.devilsvault.db.model;

public class ExternalUser {
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private int pincode;
    private Double phone;

    
    public ExternalUser(int id,String name,String address,String city,String country,int pincode,Double phone){
    	
    	this.id = id;
    	this.name = name;
    	this.address =address;
    	this.city = city;
    	this.state = state;
    	this.country = country;
    	this.pincode = pincode;
    	this.phone = phone;
    	
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getPincode() {
        return pincode;
    }
    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
    public Double getPhone() {
        return phone;
    }
    public void setPhone(Double phone) {
        this.phone = phone;
    }
}
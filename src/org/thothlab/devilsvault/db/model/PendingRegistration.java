package org.thothlab.devilsvault.db.model;
import java.util.Date;

import javax.validation.constraints.Pattern;

public class PendingRegistration {
	@Pattern(regexp="[^0-9]*")
	private String firstName;
	@Pattern(regexp="[^0-9]*")
	private String lastName;
	private String userEmail;
	private String userSsn;
	private String userPassword;
	private String country;
	private String city;
	private String street;
	private String house;
	private String state;
	private int pincode;
	private String userPhonenumber;
	private Date timestamp_created;
	
	
	public String getUserPhonenumber()
	{
		return userPhonenumber;
	}
	public void setUserPhonenumber(String userPhonenumber)
	{
		this.userPhonenumber = userPhonenumber;
	}

	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	
	public void setHouse(String house)
	{
		this.house = house;
	}
	public String getHouse()
	{
		return house;
	}
	
	
	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getStreet()
	{
		return street;
	}
	
	public void setStreet(String street)
	{
		this.street = street;
	}

	public int getPincode()
	{
		return pincode;
	}
	
	public void setPincode(int pincode)
	{
		this.pincode = pincode;
	}
	
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}
	public String getUserEmail()
	{
		return userEmail;
	}
	public void setUserSsn(String userSsn)
	{
		this.userSsn = userSsn;
	}
	public String getUserSsn()
	{
		return userSsn;
	}
	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}
	public String getUserPassword()
	{
		return userPassword;
	}
	public Date getTimestamp_created() {
		return timestamp_created;
	}
	public void setTimestamp_created(Date timestamp_created) {
		this.timestamp_created = timestamp_created;
	}
}
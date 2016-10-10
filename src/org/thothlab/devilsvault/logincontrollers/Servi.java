package org.thothlab.devilsvault.logincontrollers;


public class Servi {

	
	
	public int checko(RegistrationForm register){
		LoginDB so=new LoginDB();
		so.insert_registration(register);
		
		
		
		return 0;
	}
	
}

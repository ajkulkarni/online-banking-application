package org.thothlab.devilsvault.logincontrollers;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class ResetPassword
{

   
  public String generaterandompassword()
  {
	  String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+.";
	  String pwd = RandomStringUtils.random( 8, 0, 0, false, false, characters.toCharArray(), new SecureRandom() );
	  //System.out.println( pwd );
	  return pwd;
  }
}

//http://www.java2s.com/Code/Java/Security/GeneratearandomStringsuitableforuseasatemporarypassword.htm

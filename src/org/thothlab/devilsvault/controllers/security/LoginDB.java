package org.thothlab.devilsvault.controllers.security;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class LoginDB {

	
	public String query(String email)
	 {
	  String dbURL = "jdbc:mysql://ganga.la.asu.edu:3306/CSE545_SS?autoReconnect=true&useSSL=false";
	  String user = "cse545ss_admin";
	  String pass = "cse545ss_admin";
	  ///System.out.println("fff");
	  
	  try {
	   Connection myConn = DriverManager.getConnection(dbURL,user,pass);
	   
	   Statement myStmt = myConn.createStatement();
	   
	   ResultSet myRs = myStmt.executeQuery("select * from users WHERE username = '"+email+"';");
	   while(myRs.next())
	   {
	    
	    String s = myRs.getString("username");
	    int  locked = myRs.getInt("accountNonLocked");
	    if(locked==1)
	    {
	    	if(email.equals(s))
			    {
			     
			        ResultSet myRs1 = myStmt.executeQuery("select * from otp_table");
			        while(myRs1.next())
			        {
				         String s1 = myRs1.getString("userEmail");
				         int attempts = myRs1.getInt("attempt_count");
				         System.out.println("My result 1 "+s1);
				         //System.out.println(email);
				         if(email.equals(s1) && attempts<=3)
				         {
					          long stored_time = myRs1.getLong("timestamp");
					          long currentTime    = (System.currentTimeMillis());
					          long diff = currentTime - stored_time;
					          System.out.println(diff/1000);
					          if (diff/1000>=0 && diff/1000<=30 )
					          {
					        	  return "Email Verified";
					          }
					          else
					          {
						          //String delete_entry = "delete from otp_table where userEmail = '"+myRs1.getString("userEmail")+"';";
						          //myStmt.executeUpdate(delete_entry);
						         
						          attempts++;
						          System.out.println("Attempt "+attempts);
						          if(attempts>3)
						          {
						        	  String so = "update users set otpNonLocked = "+0+" where username = '"+email+"';";
						        	  myStmt.executeUpdate(so);
						        	  String delete_query = "DELETE FROM otp_table WHERE userEmail = '"+email+"';";
						        	  myStmt.executeUpdate(delete_query);
						        	  myConn.close();
						        	  return "OTP blocked";
						          }
						          long currentTime1    = (System.currentTimeMillis());
						          Email E = new Email();
						          String otp = E.send("Your One Time Password is ", s, 1);
						          System.out.println(currentTime1);
						          String so="update otp_table set attempt_count = "+attempts+", timestamp = '"+currentTime1+"', otp = '"+otp+"' where userEmail = '"+email+"';";
						          
						          myStmt.executeUpdate(so);
						          myConn.close();
						          return "Email Verified";
					          }
				         }
				         else {
				        	 return "OTP blocked";
				         }
			         }
			         long currentTime    = (System.currentTimeMillis());
			         System.out.println(currentTime);
			         Email E = new Email();
			         String otp = E.send("Your One Time Password is", s, 1);
			     
			        // int attempt = myRs1.getInt("attempt_count");
			         //attempt++;
			         //System.out.println(attempt);
			         String so="INSERT INTO `otp_table`(`userEmail`,`otp`,`timestamp`,`attempt_count`)VALUES('"+s+"','"+otp+"','"+currentTime+"','1')";
			         myStmt.executeUpdate(so);
			         myConn.close();
			         return "Email Verified";
			    }
	    	}
	    else {
	    	return "user locked";
	    }
	    	
	   	}
	   
	  } catch (SQLException e) {
	   // TODO Auto-generated catch block
		   System.out.println("This is entering exception");
		   e.printStackTrace();
	  	}
	  	return "invalid email/email not present";
	 }
	 
	public String OTPVerification(String OTP, String email) {
		  
		  String dbURL = "jdbc:mysql://ganga.la.asu.edu:3306/CSE545_SS?autoReconnect=true&useSSL=false";
		  String user = "cse545ss_admin";
		  String pass = "cse545ss_admin";
		  ///System.out.println("fff");
		  
		  try {
			   Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			   //System.out.println(myConn);
			   Statement myStmt = myConn.createStatement();
			   ResultSet myRs = myStmt.executeQuery("select * from otp_table;");
			   while(myRs.next()){
			   String s = myRs.getString("userEmail");
			   long stored_time = myRs.getLong("timestamp");
			   int attempts = myRs.getInt("attempt_count");
			   long currentTime    = (System.currentTimeMillis());
			   long diff = currentTime - stored_time;
			   if(email.equals(s) && attempts <=3) {
//		        System.out.println("This is the email in DB" + s + "/n");
//		        System.out.println("This is the email in Session" + email);
				   if ((diff/1000<= 30))
				   {
					   	String OTPDatabase = myRs.getString("otp");
					   	if(OTP.equals(OTPDatabase))
					   	{
					   		String delete_entry = "delete from otp_table where userEmail = '"+myRs.getString("userEmail")+"';";
					   		myStmt.executeUpdate(delete_entry);
					   		return "OTP has been verified successfully!";
					   	}
					   	else {
					   		return "Invalid OTP";
					   	}
				   }
				   else {
					   attempts ++;
					   //Email E = new Email();
				       //String otp = E.send("Your One Time Password is", s, 1);
					   //String update_entry = "UPDATE otp_table SET attempt_count =  " +attempts+ ", timestamp = '"+currentTime+"', otp = '"+otp+"' WHERE userEmail = '"+email+"';";
					  // myStmt.executeUpdate(update_entry);
					   myConn.close();
					   return "OTP expired";
				   }
			   }
			   else if(email.equals(s) && attempts > 3) {
				   String update_entry1 = "UPDATE otp_table SET attempt_count =  " +attempts+ " WHERE userEmail = '"+email+"';";
				   myStmt.executeUpdate(update_entry1);
				   String update_entry = "UPDATE users SET  otpNonLocked = " + 0 + " where userEmail = '"+email+"';";
				   myStmt.executeUpdate(update_entry);
				   myConn.close();
				   return "OTP blocked";
			   }
		     }
		   } 
		   catch (SQLException e) {
			   System.out.println("This is entering exception");
			   e.printStackTrace();
		  }
		  return "OTP blocked";
		}
	 
	 
	 public String updatePassword(String password, String email) {
		  
		  String dbURL = "jdbc:mysql://ganga.la.asu.edu:3306/CSE545_SS?autoReconnect=true&useSSL=false";
		  String user = "cse545ss_admin";
		  String pass = "cse545ss_admin";
		  try {
		   Connection myConn = DriverManager.getConnection(dbURL,user,pass);
		   //System.out.println(myConn);
		   Statement myStmt = myConn.createStatement();
//		   BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		   ResultSet myRs = myStmt.executeQuery("select * from users WHERE username = '"+email+"';");
		   myRs.next();
//		   String p = myRs.getString("password");
//		   System.out.println("The input password is : " + password + " \n");
//		   System.out.println("The password in database is : " + p + " \n");
//		   if (!passwordEncoder.matches(password, p)) {
			   String so="UPDATE users SET password = '"+password+"' WHERE username = '" + email + "';";
		       myStmt.executeUpdate(so);
		       myConn.close();
		       
//		   }
		   
		  } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  return "Password Updated";
//		  return "Same as the old password";
		 }
}
	
	
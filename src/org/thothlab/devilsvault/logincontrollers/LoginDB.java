package org.thothlab.devilsvault.logincontrollers;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class LoginDB {

	
	public String query(String email)
	{
		String dbURL = "jdbc:mysql://ganga.la.asu.edu:3306/CSE545_SS?autoReconnect=true&useSSL=false";
		String user = "cse545ss_admin";
		String pass = "cse545ss_admin";
		System.out.println("fff");
		
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			//System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from external_users");
			while(myRs.next())
			{
				//System.out.println("Stored email:" +myRs.getString("email"));
				String s = myRs.getString("email");
				//System.out.println(email);
				if(email.equals(s))
				{
					
					
					
				    ResultSet myRs1 = myStmt.executeQuery("select * from otp_table");
				    while(myRs1.next())
				    {
				    	String s1 = myRs1.getString("userEmail");
				    	if(email.equals(s1))
				    	{
				    		long stored_time = myRs1.getLong("timestamp");
				    		long currentTime    = (System.currentTimeMillis());
				    		long diff = currentTime - stored_time;
				    		System.out.println(diff/1000);
				    		if (diff/1000>30)
				    		{
				    			int k =0;
								String delete_entry = "delete from otp_table where userEmail = '"+s1+"';";
								myStmt.executeUpdate(delete_entry);
								Email E = new Email();
								String otp = E.send("Your One Time Password is ", s, 1);
								long currentTime1    = (System.currentTimeMillis());
								String so="INSERT INTO `otp_table`(`userEmail`,`otp`,`timestamp`)VALUES('"+s+"','"+otp+"','"+currentTime1+"')";
								myStmt.executeUpdate(so);
								myConn.close();
								return "OTP resent";
				    		}
				    		else
				    		{
				    			return "OTP within 30s";
				    		}
				    	}
				    }
				    long currentTime    = (System.currentTimeMillis());
				    Email E = new Email();
					String otp = E.send("Your One Time Password is", s, 1);
					System.out.println(currentTime);
				    String so="INSERT INTO `otp_table`(`userEmail`,`otp`,`timestamp`)VALUES('"+s+"','"+otp+"','"+currentTime+"')";
				    myStmt.executeUpdate(so);
					myConn.close();
					return "OTP sent";
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "invalid email/email not present";
	}
	public String store(RegistrationForm register)
	{
		String dbURL = "jdbc:mysql://ganga.la.asu.edu:3306/CSE545_SS?autoReconnect=true&useSSL=false";
		String user = "cse545ss_admin";
		String pass = "cse545ss_admin";
		
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			//System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			String se="select * from external_users where email='"+register.getUserEmail()+"'";
		    ResultSet r=myStmt.executeQuery(se);
		    
		    while(r.next())
		    {
		    	return "email already exists";
		    }
		    String so="INSERT INTO `user_pending`(`firstName`,`lastName`,`userEmail`,`userSsn`,`userPassword`,`country`,`city`,`street`,`house`,`userPhonecode`,`userAreacode`,`addresslinetwo`,`state`,`pincode`,`userPhonenumber`)"
		    		+ "VALUES('"+register.getFirstName()+"','"+register.getLastName()+"','"+register.getUserEmail()+"','"+register.getUserSsn()+"','"+register.getUserPassword()+"','"+register.getCountry()+"','"+register.getCity()+"','"+register.getStreet()+"','"+register.getHouse()+"','"+register.getUserPhonecode()+"','"+register.getUserAreacode()+"','"+register.getAddresslineTwo()+"','"+register.getState()+"','"+register.getPincode()+"','"+register.getUserPhonenumber()+"');";
		   // String so="INSERT INTO `user_pending`(`firstName`,`lastName`,`userEmail`,'userSsn','userPassword','country',`city`,`street`,`house`,'userPhonecode','userAreacode','addresslinetwo','state','pincode','userPhonenumber',"+")VALUES('"+register.getUserPhonenumber()+" "+register.getUserAreacode()+"','"+register.getUserPhonecode()+"','"+register.getState()+"','"+register.getHouse()+"','"+register.getAddresslineTwo()+"',"+register.getCountry()+","+register.getCity()+","+register.getStreet()+","+register.getPincode()+","+register.getFirstName()+","+register.getLastName()+","+register.+getUserEmail()","+register.getUserSsn()+","+register.getUserPassword()+");";
		    /*Password P = new Password();
		    String salt = P.generatesalt();
		    String hashpassword = P.signup(register.getUserPassword(), salt);
		    String so="INSERT INTO `user_pending`(`name`,`address`,`city`,`state`,`country`,`email`,"+
		    "`pincode`,`phone`,`salt`, `passhash`)VALUES('"+register.getFirstName()+" "+register.getLastName()+"','"+add+"','"+register.getCity()+"','"+register.getState()+"','"+register.getCountry()+"','"+register.getUserEmail()+"',"+register.getPincode()+","+ph+",'"+salt+"','"+hashpassword+"');";*/
		    myStmt.executeUpdate(so);
		    //add count = 0 and locked = 0 to external_authenicate table
		    //Internal user will create the external_user and external_authenticate tables.
		   /* so="select * from external_user where email='"+register.getUserEmail()+"'";
		    int id=0;
		    r=myStmt.executeQuery(so);
		    while(r.next()){
		     	System.out.println("here ???");
		      	id=r.getInt("id");
		    }
		    Password P = new Password();
		    String salt = P.generatesalt();
		    String hashpassword = P.signup(register.getUserPassword(), salt);
		    System.out.println(hashpassword);
		    so="Insert into  authentic (`email`,`salt`,`passhash`,`count`,`TypeId`,`locked`)VALUES('"+register.getUserEmail()+"','"+salt+"','"+hashpassword+"'"+",0,'E"+id+"',0);";
		     myStmt.executeUpdate(so);*/
		     myConn.close();	    
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return "Insertion Complete";
	}
	
	public boolean authenticate(LoginForm L, String type)
	{
		String dbURL = "jdbc:mysql://ganga.la.asu.edu:3306/CSE545_SS?autoReconnect=true&useSSL=false";
		String user = "cse545ss_admin";
		String pass = "cse545ss_admin";
		String table = "";
		if(type.equals("Internal"))
			table = "internal_authenticate";
		else
			table = "external_authenticate";
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			//System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			String s = "select * from " +table+ " where email='"+L.getUserName()+"'";
			ResultSet r = myStmt.executeQuery(s);
			int count = 0, id = 0;
			String email = "";
			
			while(r.next())
			{
				count++;
				//id = r.getInt("id");
				email = r.getString("email");
			}
			if(count==0)
			{
				System.out.println("email not present");
				return false;
			}
			
			s = "select * from "+table+" where locked=0;";
			r = myStmt.executeQuery(s);
			count = 0;
			
			String salt = "";
			int cc=0;
			
			
			while(r.next())
			{
				count++;
				pass = r.getString("passhash");
				salt = r.getString("salt");
				cc = r.getInt("count");
				
			}
			if(count==0)
			{
				System.out.println("No entry in authenticate table");
				return false;
			}
			
			//Password P = new Password();
			
			//String hashpass = P.login(salt, L.getPassword());
			/*if(hashpass.equals(pass))
			{
				System.out.println("Authenticated");
				//String email = L.getUserName();
				System.out.println("update query here UPDATE " +table+" SET `count` =0 , WHERE email = L.getEmail();");
				s="UPDATE "+table+" SET `count` =0  WHERE email = '"+email+"'";
				myStmt.execute(s);
				System.out.println("Setting count to 0");
				boolean x = irukuba(L.getUserName(), L.getIp());
				System.out.println("Result of irukuba"+x);
				myConn.close();
				return x;
			}
			else
			{
				 System.out.println("update query here UPDATE " +table+" SET `count` ="+(cc+1)+",where email = L.getEmail();");
				 if((cc+1)==4)
				 {
					 s="UPDATE" +table+ " SET `count` =0 ,'locked'=1  WHERE email = '"+email+"'";
					 System.out.println("locking account here");
					 myStmt.execute(s);
					 return false;
				 }
				 s="UPDATE " +table+" SET `count` ="+(cc+1)+" WHERE email = '"+email+"'";
				 
				 myStmt.executeUpdate(s);
				 System.out.println("incremented count here");
				 myConn.close();
				 return false;
				  
				 
					  
			}*/
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean irukuba(String uid, String ip)
	{
		String dbURL = "jdbc:mysql://ganga.la.asu.edu:3306/CSE545_SS?autoReconnect=true&useSSL=false";
		String user = "cse545ss_admin";
		String pass = "cse545ss_admin";
		
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			//System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			
			Statement ss = myConn.createStatement();
			String se = "Select * from users";
			ResultSet r = ss.executeQuery(se);
			int flag = 0;
			while(r.next())
			{
				System.out.println("printing here user:"+r.getString("user")+ "   and the passed val is: "+uid);
		    	System.out.println("IP"+r.getString("IP")+"   and the val is"+ip);
		    	System.out.println("time:"+r.getString("time"));
		    	if(r.getString("IP").trim().equals(ip)&&r.getString("user").trim().equals(uid))
		    	{
		    		myConn.close();
		    		return false;
		    	}
		    	else
		    		System.out.println("Reached the else part");
		    			 
			}
			if(flag==1)
			{
				System.out.println("False");
				myConn.close();
				return false;
			}
			else
			{
				 String in="Insert into users VALUES(\" "+uid+"\",\""+ip+"\",now())";
				 ss.executeUpdate(in);
				 myConn.close();
		    	 return true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
		
	}
}
	
	
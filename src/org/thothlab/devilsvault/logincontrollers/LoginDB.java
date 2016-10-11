package org.thothlab.devilsvault.logincontrollers;
import java.sql.*;
public class LoginDB {

	
	public String query(String email)
	{
		String dbURL = "jdbc:mysql://localhost:3306/login?autoReconnect=true&useSSL=false";
		String user = "root";
		String pass = "Aditya28!%!%";
		
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			//System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from external_user");
			while(myRs.next())
			{
				//System.out.println("Stored email:" +myRs.getString("email"));
				String s = myRs.getString("email");
				//System.out.println(email);
				if(email.equals(s))
				{
					ResetPassword g = new ResetPassword();
					String new_password = g.generaterandompassword();
					
					Email E = new Email();
					E.send("Your new password is ", s,new_password, 0);
					System.out.println("Reached email");
					return "password reset";
				}
				//return "Invalid email";
			}
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "invalid email/email not present";
	}
	public String store(RegistrationForm register)
	{
		String dbURL = "jdbc:mysql://localhost:3306/login?autoReconnect=true&useSSL=false";
		String user = "root";
		String pass = "Aditya28!%!%";
		
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			//System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			String se="select * from external_user where email='"+register.getUserEmail()+"'";
		    ResultSet r=myStmt.executeQuery(se);
		    
		    while(r.next())
		    {
		    	return "email already exists";
		    }
		    String ph = register.getUserPhonecode()+register.getUserPhonecode()+register.getUserPhonenumber();
		    String add = register.getStreet()+" "+register.getHouse();
		    String so="INSERT INTO `external_user`(`name`,`address`,`city`,`state`,`country`,`email`,"+
		    		 "`pincode`,`phone`)VALUES('"+register.getFirstName()+" "+register.getLastName()+"','"+add+"','"+register.getCity()+"','"+register.getState()+"','"+register.getCountry()+"','"+register.getUserEmail()+"',"+register.getPincode()+","+ph+");";
		    myStmt.executeUpdate(so);
		    
		    so="select * from external_user where email='"+register.getUserEmail()+"'";
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
		    so="Insert into  authentic (`salt`,`passhash`,`count`,`TypeId`,`locked`)VALUES('"+salt+"','"+hashpassword+"'"+",0,'E"+id+"',0);";
		     myStmt.executeUpdate(so);
		     myStmt.close();	    
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return "Insertion Complete";
	}
}
	
	
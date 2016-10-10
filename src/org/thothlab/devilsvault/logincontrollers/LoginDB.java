package org.thothlab.devilsvault.logincontrollers;
import java.sql.*;
public class LoginDB {

	
	public void insert_registration(RegistrationForm register)
	{
		String dbURL = "jdbc:mysql://localhost:3306/login?autoReconnect=true&useSSL=false";
		String user = "root";
		String pass = "Aditya28!%!%";
		
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			//System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			
			/*ResultSet myRs = myStmt.executeQuery("select * from userinfo");
			while(myRs.next())
			{
				System.out.println(myRs.getString("firstName"));
			}*/
			String fname = register.getFirstName();
			String email = register.getUserEmail();
			String sql = "insert into userinfo " 
			+ " (firstName, email)"
			+ " values ('"+fname+"', '"+email+"')"; 
			myStmt.executeUpdate(sql);
			System.out.println("insert Complete");
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void query(String email)
	{
		String dbURL = "jdbc:mysql://localhost:3306/login?autoReconnect=true&useSSL=false";
		String user = "root";
		String pass = "Aditya28!%!%";
		
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			//System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from userinfo");
			while(myRs.next())
			{
				System.out.println("Stored email:" +myRs.getString("email"));
				String s = myRs.getString("email");
				System.out.println(email);
				if(email.equals(s))
				{
					Email E = new Email();
					E.send("Your password is ", s);
					System.out.println("Reached email");
					break;
				}
			}
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package org.thothlab.devilsvault.logincontrollers;
import java.sql.*;
public class LoginDB {

	public static void main(String[] args)
	{
		String dbURL = "jdbc:mysql://localhost:3306/login";
		String user = "root";
		String pass = "Aditya28!%!%";
		
		try {
			Connection myConn = DriverManager.getConnection(dbURL,user,pass);
			System.out.println(myConn);
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from customers");
			while(myRs.next())
			{
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

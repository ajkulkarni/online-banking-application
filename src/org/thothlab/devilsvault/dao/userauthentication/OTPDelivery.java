package org.thothlab.devilsvault.dao.userauthentication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OTPDelivery {
	public String send(String email) {

		Properties prop = new Properties();
		try {
			prop.load(OTPDelivery.class.getClassLoader().getResourceAsStream("smtp.properties"));	
			
		} catch(FileNotFoundException fne) {
			return "Failed OTP Generation ";
		} catch(IOException ioe) {
			return "Failed OTP Generation ";
		}
		
		final String username = prop.getProperty("username");
		final String password = prop.getProperty("password");
		String OTP = "";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("securebanking.ss4@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Please do not share this with anyone");
			{
				OTP = OneTimePassword.getOTP();
				message.setText("Your One Time Password is " + OTP);
			}
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return OTP;
	}

}
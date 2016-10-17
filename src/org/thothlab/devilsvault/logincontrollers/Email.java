package org.thothlab.devilsvault.logincontrollers;



import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	public String send(String Subject,String email, int check) {
		
		OneTimePassword otp = new OneTimePassword();
		String OTP="";
		
		final String username = "securebanking.ss4@gmail.com";
		final String password = "ss@12345";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("securebanking.ss4@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
			message.setSubject("Please do not share this with anyone");
			//if(check == 0)
				//message.setText(Subject + new_password);
			//else
			{
				OTP = otp.getotp();
				message.setText(Subject + OTP);
			}
			Transport.send(message);

			System.out.println("Done");
			

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return OTP;
	}
	
}

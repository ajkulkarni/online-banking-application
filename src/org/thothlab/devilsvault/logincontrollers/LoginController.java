package org.thothlab.devilsvault.logincontrollers;

import org.springframework.stereotype.Controller;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.sql.*;
//import net.roseindia.form.LoginForm;

//import delegate.*;

@Controller

public class LoginController {
	
	@RequestMapping(value ="loginform.html", method = RequestMethod.GET)
	public ModelAndView getLoginForm() {
 
		ModelAndView modelandview = new ModelAndView("loginform");
		
		return modelandview;
	}
	@RequestMapping(value = "new.html", method = RequestMethod.POST)
	public ModelAndView submitLoginForm(@Valid LoginForm loginForm, RegistrationForm register, BindingResult result, HttpServletRequest request)
	{
		
		
		String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    
	  	String b = request.getParameter("usertype");
	  	System.out.println(b);
	  		
		//System.out.println(loginForm.getUserType());
		//System.out.println("here lololol ip:"+ip);
	    loginForm.setIp(ip);
	    LoginDB db = new LoginDB();
	   	boolean x = db.authenticate(loginForm, b);
	 
	    
		ModelAndView modelandview = new ModelAndView("loginform");
		if(result.hasErrors()||x==false)
		{		
				return modelandview;
		}
		if(request.getParameterMap().containsKey("g-recaptcha-response")){
			if(request.getParameter("g-recaptcha-response") == "")
			{
				return modelandview;
			}
			else{
				{
					
					ModelAndView modelandview1 = new ModelAndView("next");
					return modelandview1;
				}
			}
		}
		else{
					
			return modelandview;
		}
		
		
	}
	
	@RequestMapping(value ="register.html", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {
 
		ModelAndView modelandview = new ModelAndView("registration");
		
		return modelandview;
	}
	
	@RequestMapping(value ="next.html", method = RequestMethod.POST)
	public ModelAndView submitRegistrationForm(@Valid RegistrationForm register, BindingResult result, HttpServletRequest request) {
 	
		System.out.println(register.getFirstName());
		System.out.println(register.getLastName());
		System.out.println(register.getUserEmail());
		System.out.println(register.getUserPassword());
		System.out.println(register.getUserSsn());
		System.out.println(register.getState());
		System.out.println(register.getCountry());
		System.out.println(register.getPincode());
		System.out.println(register.getStreet());
		System.out.println(register.getHouse());
		System.out.println(register.getUserAreacode());
		System.out.println(register.getUserPhonecode());
		System.out.println(register.getUserPhonenumber());
		
		
		ModelAndView modelandview = new ModelAndView("registration");
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		String email1 = register.getUserEmail();
		Boolean b = email1.matches(EMAIL_REGEX);
		//System.out.println("is e-mail: "+email1+" :Valid = " + b);
		
		if(b==false||result.hasErrors())
				return modelandview;
			
		if(request.getParameterMap().containsKey("g-recaptcha-response"))
		{
			if(request.getParameter("g-recaptcha-response") == "")
			{
				return modelandview;
			}
			else{
				
					System.out.println("here");
					LoginDB d = new LoginDB();
					if(d.store(register).equals("email already exists"))
					{
						ModelAndView modelandview1 = new ModelAndView("loginform");
						modelandview.addObject("error","Email already registered");
						return modelandview1;
					}
					
				}
			
		}
		modelandview = new ModelAndView("loginform");
		System.out.println("Code reaching here");
		return modelandview;

	}
	@RequestMapping(value ="forgot.html", method = RequestMethod.GET)
	public ModelAndView getForgotPasswordForm() {
 
		ModelAndView modelandview = new ModelAndView("forgotpassword");
		
		return modelandview;
	}
	@RequestMapping(value ="backtologin.html", method = RequestMethod.POST)
	public ModelAndView sendEmail(@Valid LoginForm loginForm, BindingResult result, HttpServletRequest request) {
 	
		ModelAndView modelandview = new ModelAndView("forgotpassword");
		System.out.println("Forgotpassword email " + loginForm.getUserName());
	
		/*if(result.hasErrors())
			return modelandview;*/
		
		LoginDB db = new LoginDB();
		String result_email = db.query(loginForm.getUserName());
		if(result_email.equals("invalid email/email not present"))
			System.out.println("invalid email/email not present");
		modelandview = new ModelAndView("loginform");
		return modelandview;
		
	}
}


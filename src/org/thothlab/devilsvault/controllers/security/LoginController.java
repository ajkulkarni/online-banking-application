package org.thothlab.devilsvault.controllers.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) throws IOException {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		
		model.setViewName("LoginPage");

		return model;

	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login";
	}
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public ModelAndView loginFailed (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    ModelAndView model = new ModelAndView("LoginPage");
		model.addObject("msg","Captcha Validation Failed !!");
		return model;
	}
	
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}
	
	@RequestMapping(value ="forgot.html", method = RequestMethod.GET)
	 public ModelAndView getForgotPasswordForm() {
	  
	  ModelAndView modelandview = new ModelAndView("forgotpassword");
	  
	  return modelandview;
	 }
	
	 @RequestMapping(value ="verifyOTP.html", method = RequestMethod.POST)
	 public ModelAndView sendEmail(@Valid LoginForm loginForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
	  
		 ModelAndView modelandview = new ModelAndView("LoginPage");
		 System.out.println("Forgotpassword email " + loginForm.getUserName());
	 	  LoginDB db = new LoginDB();
		  String email = loginForm.getUserName();
		 // System.out.println(email);
		  String result_email = db.query(email);
		 // System.out.println(email);
		  if(result_email.equals("invalid email/email not present"))
		  {
			  System.out.println("invalid email/email not present.");
			  modelandview.addObject("msg", "This is not a registered Email. Please contact the bank for further assistance");
			  return modelandview;
		  }
		  else if(result_email.equals("user locked"))
		  {
			  System.out.println("User has been blocked.");
			  modelandview = new ModelAndView("LoginPage");
			  modelandview.addObject("msg", "This User has been blocked. Please contact the bank to unlock your account.");
			  return modelandview;
		  }
		  else if(result_email.equals("OTP blocked"))
		  {
			   System.out.println("OTP has been blocked");
			   modelandview = new ModelAndView("LoginPage");
			   modelandview.addObject("msg", "This user's OTP services has been temporarily blocked. Please contact the bank for further assistance.");
			   return modelandview;
		  }
		
		  HttpSession session = request.getSession(true); 
		  session.setAttribute("email", email); // I think this part is not working properly for some reason.
		  String encodedURL = response.encodeURL("VerifyOTP");
		  modelandview = new ModelAndView(encodedURL);
		  System.out.println("This is the session attribute email" + session.getAttribute("email"));
		  modelandview.addObject("msg", "Email has been verified! Please enter the OTP.");
		  return modelandview;
	 }
	 
	 @RequestMapping(value ="backtologin.html", method = RequestMethod.POST)
	 public ModelAndView sendOTP(@Valid LoginForm loginForm, BindingResult result,HttpServletRequest request) {
	      
	   ModelAndView modelandview = new ModelAndView("VerifyOTP");
	  /*if(result.hasErrors())
	  {
	   System.out.println("dddd"+result.hasErrors());
	   return modelandview;
	  }*/
	   LoginDB db = new LoginDB();
	   HttpSession session = request.getSession();
	   String email = (String)session.getAttribute("email");
	   System.out.println("This is the email in Session" + email + "/n");
	   String result_OTP = db.OTPVerification(loginForm.getOtp(),email);
	   if(result_OTP.equals("Invalid OTP"))
	   {
		   System.out.println("Invalid OTP. Please enter a valid OTP.");
		   modelandview.addObject("msg", "OTP Invalid. Please enter a valid OTP.");
		   return modelandview;
	   }
	   else if(result_OTP.equals("OTP expired"))
	   {
		   System.out.println("Your OTP session has expired! Please generate a new OTP.");
		   modelandview = new ModelAndView("LoginPage");
		   modelandview.addObject("msg", "Your OTP session has expired. Please generate a new OTP.");
		   return modelandview;
	   }
	   else if(result_OTP.equals("OTP blocked"))
	   {
		   System.out.println("Your OTP service has been temporarily blocked!");
		   modelandview = new ModelAndView("LoginPage");
		   modelandview.addObject("msg", "The OTP service has been blocked for the user. Please contact the bank for further assistance.");
		   return modelandview;
	   }
	   else {
//	    System.out.println(result_OTP);
		   modelandview = new ModelAndView("NewPassword");
		   modelandview.addObject("msg", "OTP verified. Please enter the new password.");
		   return modelandview;
	   }
	   
	 }
	 
	 @RequestMapping(value ="newpassword", method = RequestMethod.POST)
	 public ModelAndView changePassword(@Valid LoginForm loginForm, BindingResult result,HttpServletRequest request) { 
	  
	  ModelAndView modelandview = new ModelAndView("LoginPage");
	  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	  String password = (passwordEncoder.encode(loginForm.getPassword()));
	  LoginDB db = new LoginDB();
	  HttpSession session = request.getSession();
	  String email = (String)session.getAttribute("email");
	  String result_newPassword = db.updatePassword(password, email);
//	  System.out.println("The password here is : " +  + " \n");
//	  if(result_newPassword.equals("Same as the old password"))
//	  {
//		  System.out.println("The password given was previously used! Please enter a different password.");
//		  modelandview = new ModelAndView("NewPassword");
//		  modelandview.addObject("msg", "This password is the same as the existing password! Please enter a different password.");
//		  return modelandview;
//	  }
	  System.out.println(result_newPassword);
	  modelandview.addObject("msg", "Your password has been changed successfully!");
	  return modelandview;
	 }

}

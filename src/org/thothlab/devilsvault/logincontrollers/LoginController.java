package org.thothlab.devilsvault.logincontrollers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.annotation.Bean;
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
	
	@Bean
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
	
	@RequestMapping(value ="register.html", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {
 
		ModelAndView modelandview = new ModelAndView("registration");
		
		return modelandview;
	}
	
	@RequestMapping(value ="/newreg", method = RequestMethod.POST)
    public ModelAndView submitRegistrationForm(@Valid RegistrationForm register, BindingResult result, HttpServletRequest request) throws IOException {
		
     	    	//	System.out.println(s1.getstudentDOB());
			/*String gRecaptchaResponse = request
				.getParameter("g-recaptcha-response");
			System.out.println(gRecaptchaResponse);
			boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
			System.out.println(verify);	*/
    		if(result.hasErrors())//||verify==false)
    		{
    				ModelAndView modelandview = new ModelAndView("registration");
    				return modelandview;
    		}
    		System.out.println(register.getFirstName());
    		System.out.println(register.getLastName());
    		System.out.println(register.getUserEmail());
    		System.out.println(register.getUserPassword());
    		System.out.println(register.getUserSsn());

    		System.out.println(register.getCity());
    		System.out.println(register.getCountry());
    		System.out.println(register.getStreet());
    		System.out.println(register.getPincode());
    		
    		
    		LoginDB dd=new LoginDB();
    		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    		register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
    		System.out.println(register.getUserPassword()); 
    		dd.store(register);
    		
    		ModelAndView modelandview = new ModelAndView("LoginPage");
    	
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
		{
			System.out.println("dddd"+result.hasErrors());
			return modelandview;
		}*/
			
		
		LoginDB db = new LoginDB();
		
		String result_email = db.query(loginForm.getUserName());
		if(result_email.equals("invalid email/email not present"))
		{
			System.out.println("invalid email/email not present");
			modelandview = new ModelAndView("LoginPage");
			return modelandview;
		}
		System.out.println(result_email);
		modelandview = new ModelAndView("VerifyOTP");
		return modelandview;
	}
}

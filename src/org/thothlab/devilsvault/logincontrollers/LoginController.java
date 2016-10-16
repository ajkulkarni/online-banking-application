package org.thothlab.devilsvault.logincontrollers;

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
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

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
	@RequestMapping(value ="register.html", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {
 
		ModelAndView modelandview = new ModelAndView("registration");
		
		return modelandview;
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
	
	@RequestMapping(value ="next.html", method = RequestMethod.POST)
	public ModelAndView submitRegistrationForm(@Valid RegistrationForm register, BindingResult result, HttpServletRequest request) {
 	
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
		System.out.println(register.getUserPassword()); 
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
						System.out.println("Email exits");
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
}

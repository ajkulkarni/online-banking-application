package org.thothlab.devilsvault.logincontrollers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@RequestMapping("/login")
	public ModelAndView helloworld(){
		ModelAndView model = new ModelAndView("login");
		model.addObject("msg","Hello Deepesh");
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

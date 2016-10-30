package org.thothlab.devilsvault.controllers.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExceptionHandlerClass extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	@RequestMapping(value="/raiseexception")
	public String ForgotPassword(RedirectAttributes redir,HttpServletRequest request, HttpServletResponse response) {
        String message = "Something went wrong !! Please try again !!";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
        redir.addFlashAttribute("exception_message",message);
	    return "redirect:/login";
	}
	
}
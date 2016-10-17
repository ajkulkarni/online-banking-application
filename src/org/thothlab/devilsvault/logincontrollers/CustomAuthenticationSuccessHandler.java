package org.thothlab.devilsvault.logincontrollers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.ModelAndView;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub 
		
		String gRecaptchaResponse = arg0.getParameter("g-recaptcha-response");
		System.out.println(gRecaptchaResponse);
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		System.out.println(verify);	
		/*if (verify==false)
		{
			arg1.setStatus(HttpServletResponse.SC_FORBIDDEN);
			arg1.sendRedirect("/CSE545-SS/login");
			return;
		}*/
			
		arg1.setStatus(HttpServletResponse.SC_OK);
		arg1.sendRedirect("/CSE545-SS/employee/home");
		
	}

}

package org.thothlab.devilsvault.controllers.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub 
		
		HttpSession session = arg0.getSession();
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("username", authUser.getUsername());
        String role_raw = arg2.getAuthorities().toString();
        String role = role_raw.substring(1, role_raw.length() -1 );
        session.setAttribute("role", role);
		arg1.setStatus(HttpServletResponse.SC_OK);
		if(role.equalsIgnoreCase("ROLE_REGULAR") || role.equalsIgnoreCase("ROLE_MANAGER") || role.equalsIgnoreCase("ROLE_ADMIN")){
			arg1.sendRedirect("/CSE545-SS/employee/home");
		}else{
			arg1.sendRedirect("/CSE545-SS/customer/home");
		}
		
		
	}

}

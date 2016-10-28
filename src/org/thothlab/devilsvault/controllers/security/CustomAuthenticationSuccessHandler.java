package org.thothlab.devilsvault.controllers.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.thothlab.devilsvault.dao.customer.InternalCustomerDAO;
import org.thothlab.devilsvault.dao.employee.InternalUserDaoImpl;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub 
		String gRecaptchaResponse = arg0.getParameter("g-recaptcha-response");
        @SuppressWarnings("unused")
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
//        if(verify){
        	HttpSession session = arg0.getSession();
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
            
            String role_raw = arg2.getAuthorities().toString();
            String role = role_raw.substring(1, role_raw.length() -1 );
    		
    		if(role.equalsIgnoreCase("ROLE_REGULAR") || role.equalsIgnoreCase("ROLE_MANAGER") || role.equalsIgnoreCase("ROLE_ADMIN")){
    			InternalUserDaoImpl internalEmployeeDao = ctx.getBean("EmployeeDAOForInternal", InternalUserDaoImpl.class);
    			String username = authUser.getUsername();
    			int userID = internalEmployeeDao.getUserId(username);
    			session.setAttribute("username", username);
    			session.setAttribute("userID", userID);
    			session.setAttribute("role", role);
    			arg1.setStatus(HttpServletResponse.SC_OK);
    			ctx.close();
    			arg1.sendRedirect("/CSE545-SS/employee/home");
    		}else{
    			InternalCustomerDAO internalCustomerDao = ctx.getBean("CustomerDAOForInternal", InternalCustomerDAO.class);
    			String username = authUser.getUsername();
    			int userID = internalCustomerDao.getUserId(username);
    			session.setAttribute("username", username);
    			session.setAttribute("userID", userID);
    			session.setAttribute("role", role);
    			arg1.setStatus(HttpServletResponse.SC_OK);
    			ctx.close();
    			arg1.sendRedirect("/CSE545-SS/customer/home");
    		}
//        }else{
//        	arg1.sendRedirect("captchafailed");
//        }
	}

}

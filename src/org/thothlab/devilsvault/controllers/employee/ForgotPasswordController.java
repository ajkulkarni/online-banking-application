package org.thothlab.devilsvault.controllers.employee;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thothlab.devilsvault.controllers.security.ExceptionHandlerClass;
import org.thothlab.devilsvault.dao.log.LogDaoImpl;
import org.thothlab.devilsvault.dao.userauthentication.OtpDaoImpl;
import org.thothlab.devilsvault.dao.userauthentication.UserAuthenticationDaoImpl;
import org.thothlab.devilsvault.db.model.DatabaseLog;

@Controller
public class ForgotPasswordController {
	
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
		
	}
	
	@ExceptionHandler(ExceptionHandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/raiseexception";
    }
		
	@RequestMapping(value="forgotpassword")
	public ModelAndView ForgotPassword() {
		try{
        ModelAndView model = new ModelAndView("ForgotPassword");
        return model;
		}catch (Exception e){
			throw new ExceptionHandlerClass(); 
		}
	}
	
	@RequestMapping(value="verifyemail", method = RequestMethod.POST)
	public ModelAndView VerifyEmail(HttpServletRequest request, @RequestParam("Email") String email) {
		try{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		OtpDaoImpl otpdao = ctx.getBean("OtpDaoImpl",OtpDaoImpl.class);
		String message = otpdao.verifyEmail(email);
		if (message.equalsIgnoreCase("Cannot generate more OTP, Contact Support !!")){
			ModelAndView model = new ModelAndView("ForgotPassword");
			 model.addObject("message", message);
		        ctx.close();
	        return model;
		}else{
	        ModelAndView model = new ModelAndView("ForgotPasswordOTP");
	        request.getSession().removeAttribute("forgotpassemail");
	        request.getSession().setAttribute("forgotpassemail", email);
	        model.addObject("message", message);
	        ctx.close();
	        return model;
		}
		}catch (Exception e){
			throw new ExceptionHandlerClass(); 
		}
	}
	
	@RequestMapping(value="verifyotp", method = RequestMethod.POST)
	public ModelAndView VerifyOTP(HttpServletRequest request, @RequestParam("otp") String otp) {
		try{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		OtpDaoImpl otpdao = ctx.getBean("OtpDaoImpl",OtpDaoImpl.class);
		String email = (String) request.getSession().getAttribute("forgotpassemail");
		String message = otpdao.verifyOTP(otp, email);
		if(message.equalsIgnoreCase("Incorrect OTP") || message.equalsIgnoreCase("Error in verifying OTP")){
			ModelAndView model = new ModelAndView("ForgotPasswordOTP");
			model.addObject("message", message);
			ctx.close();
			return model;
		}else{
			ModelAndView model = new ModelAndView("ChangePassword");
	        model.addObject("message", message);
	        ctx.close();
	        return model;
		}
		}catch (Exception e){
			throw new ExceptionHandlerClass(); 
		}
        
	}
	
	@RequestMapping(value="/changepassword", method = RequestMethod.POST)
	   public ModelAndView changePasswordInternal(RedirectAttributes redir, HttpServletRequest request, @RequestParam("newpassword") String newPassword,@RequestParam("confirmpassword") String confirmPassword) {
	        try{
			String username = (String)request.getSession().getAttribute("forgotpassemail");
	        ModelAndView model = new ModelAndView();
	        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
	        UserAuthenticationDaoImpl userauthenticationDao = ctx.getBean("userAuthenticationDao", UserAuthenticationDaoImpl.class);
	        String message = userauthenticationDao.changeForgotPassword(newPassword, confirmPassword, username);
	        model.setViewName("redirect:/login");
	        LogDaoImpl logDao = ctx.getBean("DatabaseLogDao", LogDaoImpl.class);
            DatabaseLog dblog = new DatabaseLog();

            dblog.setActivity("Forgot password : " + username);
    	                    dblog.setDetails(message);
    	                    dblog.setUserid(userID);
    	                    logDao.save(dblog, "internal_log");
	        redir.addFlashAttribute("exception_message",message);
	        ctx.close();
	        return model;
	        }catch (Exception e){
				throw new ExceptionHandlerClass(); 
			}

	   }
}
package org.thothlab.devilsvault.controllers.customer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thothlab.devilsvault.dao.customer.CustomerDAO;
import org.thothlab.devilsvault.dao.customer.ExtUserDaoImpl;
import org.thothlab.devilsvault.dao.userauthentication.UserAuthenticationDaoImpl;
import org.thothlab.devilsvault.db.model.BankAccount.AccountType;
import org.thothlab.devilsvault.db.model.CreditAccount;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.DebitAccount;

@Controller
public class CustomerDashboardController {
	
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
		
	}
	
	@RequestMapping("/customer/home")
	public ModelAndView customerHome(HttpServletRequest request){
		
		setGlobals(request);
		
		DebitAccount checkingAccount = new DebitAccount(AccountType.CHECKING);
		checkingAccount.setAccountNumber(123);
		DebitAccount savingAccount = new DebitAccount(AccountType.SAVINGS);
		savingAccount.setAccountNumber(100);
		CreditAccount creditAccount = new CreditAccount();
		creditAccount.setAccountNumber(102);
		Customer customer = new Customer();
		customer.setId(101);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
		Double SavingsAccBal = CustomerDAO.getSavingsBalance(customer);
		Double CheckingAcctBal = CustomerDAO.getCheckingBalance(customer);
		ctx.close();
		ModelAndView model = new ModelAndView("customerPages/customerDashboard");
		model.addObject("Customer",customer);
		model.addObject("cAccount", checkingAccount );
		model.addObject("sAccount", savingAccount );
		model.addObject("ccAccount", creditAccount );
		model.addObject("SavingsAccBal",SavingsAccBal);
		model.addObject("CheckingAccBal", CheckingAcctBal);
		return model;
	}
	
	@RequestMapping("/customer/userdetails")
	public ModelAndView UserDetailsContoller(HttpServletRequest request){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		CustomerDAO externalDao = ctx.getBean("customerDAO", CustomerDAO.class);
		setGlobals(request);
		Customer customer = externalDao.getCustomer(userID);
		ModelAndView model = new ModelAndView("customerPages/customerUserDetails");
		model.addObject("user",customer);
		ctx.close();
		return model;
	}
	
	@RequestMapping(value="/customer/changepassword", method = RequestMethod.POST)
    public ModelAndView changePasswordInternal(RedirectAttributes redir, @RequestParam("oldpassword") String oldPassword, HttpServletRequest request, @RequestParam("newpassword") String newPassword,@RequestParam("confirmpassword") String confirmPassword) {
		ModelAndView model = new ModelAndView();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
		setGlobals(request);
		UserAuthenticationDaoImpl userauthenticationDao = ctx.getBean("userAuthenticationDao", UserAuthenticationDaoImpl.class);
		String message = userauthenticationDao.changePassword(oldPassword, newPassword, confirmPassword, userID,role);
		ctx.close();
		model.setViewName("redirect:/customer/userdetails");
        redir.addFlashAttribute("message",message);
        return model;
		

    }
	
	@RequestMapping(value="/customer/transferotp")
    public ModelAndView transfer(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("customerPages/transferOTPPage");
		return model;
		

    }
	
}

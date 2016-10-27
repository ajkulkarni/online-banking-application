package org.thothlab.devilsvault.controllers.employee;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.bankaccount.BankAccountDaoImpl;
import org.thothlab.devilsvault.dao.customer.CreditCardDaoImpl;
import org.thothlab.devilsvault.dao.customer.ExtUserDaoImpl;
import org.thothlab.devilsvault.dao.employee.InternalUserDaoImpl;
import org.thothlab.devilsvault.dao.userauthentication.UserAuthenticationDaoImpl;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.InternalUser;
import org.thothlab.devilsvault.db.model.InternalUserRegister;
import org.thothlab.devilsvault.db.model.UserAuthentication;

@Controller
public class Registration {
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
		
	}
	@RequestMapping(value="/employee/internalregister", method = RequestMethod.POST)
    public ModelAndView registerInternal(@ModelAttribute("user1") InternalUserRegister newuser ) {
        String name = newuser.getName();
		String username = newuser.getEmail();
        String role = newuser.getDesignation();
        String address = newuser.getAddress();
        BigInteger phone = new BigInteger(newuser.getPhone());
        java.sql.Date date_of_birth = newuser.getDate_of_birth();
        String ssn =newuser.getSsn();
        HashMap<String,String> passwords = new HashMap<String,String>();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        UserAuthenticationDaoImpl userauthenticationdaoimpl = ctx.getBean("userAuthenticationDao", UserAuthenticationDaoImpl.class);
        InternalUserDaoImpl internaluserDao = ctx.getBean("EmployeeDAOForInternal",InternalUserDaoImpl.class);
        if(userauthenticationdaoimpl.validateuserDetails(username, phone,"internal_user"))
        {
        	passwords = userauthenticationdaoimpl.randomPasswordGenerator();
        	if(passwords != null)
        	{
        		UserAuthentication userdetails = userauthenticationdaoimpl.setNewUsers(username, passwords.get("hashedPassword"), role);
        		userauthenticationdaoimpl.save(userdetails);
        		InternalUser internaluser = internaluserDao.setInternalUser(name, role, address, phone, username, date_of_birth, ssn);
        		internaluserDao.save(internaluser);
        	}
        	System.out.println("Password : " + passwords.get("rawPassword"));
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/employee/management");
	    ctx.close();
	    return model;
    }
	
	@RequestMapping(value="/employee/externalregister", method = RequestMethod.POST)
    public ModelAndView registerExternal(@ModelAttribute("user1") InternalUserRegister newuser) {
		String name = newuser.getName();
		String username = newuser.getEmail();
        String role = newuser.getDesignation();
        String address = newuser.getAddress();
        BigInteger phone = new BigInteger(newuser.getPhone());
        java.sql.Date date_of_birth = newuser.getDate_of_birth();
        String ssn =newuser.getSsn();
        HashMap<String,String> passwords = new HashMap<String,String>();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        UserAuthenticationDaoImpl userauthenticationdaoimpl = ctx.getBean("userAuthenticationDao", UserAuthenticationDaoImpl.class);
        ExtUserDaoImpl externaluserDao = ctx.getBean("ExtUserDaoImpl",ExtUserDaoImpl.class);
        BankAccountDaoImpl bankaccountDao = ctx.getBean("bankAccountDao",BankAccountDaoImpl.class);
        CreditCardDaoImpl creditcardDao = ctx.getBean("CreditCardDao",CreditCardDaoImpl.class);
        if(userauthenticationdaoimpl.validateuserDetails(username, phone,"external_users"))
        {
        	passwords = userauthenticationdaoimpl.randomPasswordGenerator();
        	System.out.println("Password : " + passwords.get("rawPassword"));
        	if(passwords != null)
        	{
        		UserAuthentication userdetails = userauthenticationdaoimpl.setNewUsers(username, passwords.get("hashedPassword"), role);
        		userauthenticationdaoimpl.save(userdetails);
        		Customer externaluser = externaluserDao.setExternalUser(name, address, phone, username, date_of_birth, ssn);
        		Integer userId = externaluserDao.createUser(externaluser);
        		Integer creditAccNo = bankaccountDao.CreateAndGetCreditAccountNo(userId);
        		creditcardDao.createCreditAccount(creditAccNo);        		
        	}
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/employee/management");
	    ctx.close();
	    return model;
    }
	
	@RequestMapping(value="/employee/externalregistrationform")//, method = RequestMethod.POST)
    public ModelAndView registerExternalForm() {
		ModelAndView model = new ModelAndView("employeePages/registrationExternal");
	    return model;
    }
	
	@RequestMapping(value="/employee/internalregistrationform")//, method = RequestMethod.POST)
    public ModelAndView registerInternalForm() {
		ModelAndView model = new ModelAndView("employeePages/registrationInternal");
	    return model;
    }
}
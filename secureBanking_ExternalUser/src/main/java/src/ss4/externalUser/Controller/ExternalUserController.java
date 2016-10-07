package src.ss4.externalUser.Controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import src.ss4.externalUser.Dao.ExtUserDao;
import src.ss4.externalUser.Dao.ExtUserDaoImpl;
import src.ss4.externalUser.Model.ExtUser;

public class ExternalUserController {
	   @Autowired
	    private ExtUserDao ExternalUserDAO;

	   @RequestMapping(value = "/getExternalUser", method = RequestMethod.GET)
	   public ModelAndView getExternalUser() {
		      return new ModelAndView("getExternalUser", "command", new ExtUser());
		   }
	   @RequestMapping(value = "/viewBalance", method = RequestMethod.GET)
	   public String viewBalance(@ModelAttribute("SpringWeb")ExtUser user, 
			   ModelMap model)
	   {
		   ExternalUserDAO = new ExtUserDaoImpl();
		   ArrayList<Float> balances = ExternalUserDAO.ViewBalance(user);
	       //model.addObject("balances", balances);
	       //model.setViewName("home");
		   model.addAttribute("Savings", balances.get(0));
		   model.addAttribute("Checking", balances.get(1));
	    
	       return "viewBalance";
}
}
package org.thothlab.devilsvault.ExternalUserController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thothlab.devilsvault.ExternalUserDAO.ExtUserDao;
import org.thothlab.devilsvault.ExternalUserDAO.ExtUserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.thothlab.devilsvault.ExternalUserModel.ExtUser;
@Controller
public class ExternalUserController {
	@Autowired ExtUserDao extUserDAO;
	@RequestMapping(value ="/externaluserdashboard",method = RequestMethod.GET)
	public String viewBalances(Model model){
		//Model model = new Model("/customerPages/customerDashboard");
		//user.setID(101);
		//model.addAttribute(user);
		extUserDAO = new ExtUserDaoImpl();
		model.addAttribute("balances",extUserDAO.ViewBalance());
		return "externalUserBalances";
	}

}

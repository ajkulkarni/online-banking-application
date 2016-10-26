
/**
 * Created by jaydatta on 10/24/16.
 */

package org.thothlab.devilsvault.controllers.customer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.transaction.TransferDAO;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthorizeMerchantController {

    String role;
    int userID;
    String username;

    public void setGlobals(HttpServletRequest request) {
        role = (String) request.getSession().getAttribute("role");
        System.out.println(role + "role");
        userID = (int) request.getSession().getAttribute("userID");
        System.out.println("USER ID:" + userID);



        username = (String) request.getSession().getAttribute("username");
    }

    @RequestMapping("/customer/authorizemerchants")
    public ModelAndView addMerchants(HttpServletRequest request) throws SQLException {
        setGlobals(request);


        System.out.println(request.getSession().getAttribute("userID")+"sdfkjsdhfksdhf");


        System.out.println(request.getParameter("checkingPicker")+"checkingPicker");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);

        int payerId = userID;
        ModelAndView model = new ModelAndView("customerPages/authorizeMerchant");

        //TODO
        // Remind to check if merchant account is valid

        //TODO
        // Remind to check

        List<String> merchantAccounts = transferDAO.getNewMerchantAccounts(payerId);


        List<Integer> merchantNames = new ArrayList<>();

        for(int i=0; i < merchantAccounts.size();i++){
           merchantNames.add(Integer.parseInt(merchantAccounts.get(i).split(":")[1].trim()));
        }

        if(request.getParameterMap().containsKey("checkingPicker")){
            int merchantID = Integer.parseInt(request.getParameter("checkingPicker").split(":")[1]);
        if(!merchantNames.contains(merchantID)){
         model.addObject("success", false);
             model.addObject("error_msg", "Merchant not authorized!");
         ctx.close();
            return model;
}


            Boolean result = transferDAO.addMerchantToUser(merchantID,userID);
        }

        if(request.getParameterMap().containsKey("removeMerchant")){
            int merchantID = Integer.parseInt(request.getParameter("removeMerchant").split(":")[1]);
            Boolean deleted = transferDAO.deleteMerchantConnection(payerId,merchantID);
        }



        List<String> existingMerchants = transferDAO.getExistingMerchantAccounts(payerId);
        model.addObject("existingMerchants",existingMerchants);
        model.addObject("merchantAccounts", merchantAccounts);
        request.getSession().setAttribute("merchantAccounts", merchantAccounts);
        return model;
    }
}

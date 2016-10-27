package org.thothlab.devilsvault.controllers.customer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.log.LogDaoImpl;
import org.thothlab.devilsvault.dao.transaction.TransferDAO;
import org.thothlab.devilsvault.db.model.DatabaseLog;

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
        //System.out.println(role + "role");
        userID = (int) request.getSession().getAttribute("userID");
        //System.out.println("USER ID:" + userID);



        username = (String) request.getSession().getAttribute("username");
    }

    @RequestMapping("/customer/authorizemerchants")
    public ModelAndView addMerchants(HttpServletRequest request) throws SQLException {
        setGlobals(request);


        //System.out.println(request.getSession().getAttribute("userID")+"sdfkjsdhfksdhf");
        ClassPathXmlApplicationContext ctx_log = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        LogDaoImpl logDao = ctx_log.getBean("DatabaseLogDao", LogDaoImpl.class);
        DatabaseLog dblog = new DatabaseLog();

        ModelAndView logoutModel = new ModelAndView("redirect:" + "/logout");
           if(request.getSession().getAttribute("role").equals("ROLE_MERCHANT")){
            return logoutModel;
        }

        Boolean result = false;
        Boolean deleted = false;

        //System.out.println(request.getParameter("checkingPicker")+"checkingPicker");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/jdbc/config/DaoDetails.xml");
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

            if(!(request.getParameter("checkingPicker").matches("[a-zA-Z ]+\\w\\s*:\\d+"))){
                model.addObject("success", false);
                model.addObject("error_msg", "Tampering Merchant Accounts!");
                ctx.close();
                return model;
            }

            int merchantID = Integer.parseInt(request.getParameter("checkingPicker").split(":")[1]);
        if(!merchantNames.contains(merchantID)){
            model.addObject("success", false);
            model.addObject("error_msg", "Merchant not authorized!");
            ctx.close();
            return model;
            }


            //System.out.println(merchantID);
            result =  transferDAO.addMerchantToUser(merchantID,userID);

            //System.out.println("result"+result);
            if(result){
                dblog.setActivity("added merchant" + merchantID);
                dblog.setDetails("add merchant page visited by user");
                dblog.setUserid((int) request.getSession().getAttribute("userID"));
                logDao.save(dblog, "external_log");
                }
        }

        if(request.getParameterMap().containsKey("removeMerchant")){
            int merchantID = Integer.parseInt(request.getParameter("removeMerchant").split(":")[1]);
            deleted = transferDAO.deleteMerchantConnection(payerId,merchantID);

            //System.out.println(deleted+"deleted");
            if(deleted){
                dblog.setActivity("removed merchant" + merchantID);
                dblog.setDetails("add merchant page visited by user");
                dblog.setUserid((int) request.getSession().getAttribute("userID"));
                logDao.save(dblog, "external_log");
            }

        }
        
       


        ModelAndView newModel = new ModelAndView("redirect:" + "/customer/authorizemerchants");
        List<String> existingMerchants = transferDAO.getExistingMerchantAccounts(payerId);
        if(result || deleted){
            newModel.addObject("existingMerchants",existingMerchants);
            newModel.addObject("merchantAccounts", merchantAccounts);
            ctx.close();
        return newModel;
        }
        model.addObject("existingMerchants",existingMerchants);
        model.addObject("merchantAccounts", merchantAccounts);
        request.getSession().setAttribute("merchantAccounts", merchantAccounts);
        ctx.close();

        return model;
    }
}

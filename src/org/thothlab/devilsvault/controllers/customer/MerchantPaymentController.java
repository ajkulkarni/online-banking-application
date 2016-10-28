package org.thothlab.devilsvault.controllers.customer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.transaction.TransferDAO;
import org.thothlab.devilsvault.controllers.security.ExceptionHandlerClass;
import org.thothlab.devilsvault.dao.transaction.TransactionDaoImpl;
import org.thothlab.devilsvault.db.model.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@Controller
public class MerchantPaymentController {

    String role;
    int userID;
    String username;

    public void setGlobals(HttpServletRequest request) {
        role = (String) request.getSession().getAttribute("role");
        //System.out.println(role + "role");
        userID = (int) request.getSession().getAttribute("userID");
        username = (String) request.getSession().getAttribute("username");
    }

    @ExceptionHandler(ExceptionHandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/raiseexception";
    }


    @RequestMapping("customer/merchantpayment")
    public ModelAndView merchantPayToUser(HttpServletRequest request) throws SQLException, ParseException {
    	try{
    		ModelAndView model = new ModelAndView("customerPages/merchantMakePayment");
            setGlobals(request);

            Boolean result = false;
            ModelAndView logoutModel = new ModelAndView("redirect:" + "/logout");
            if(request.getSession().getAttribute("role").equals("ROLE_CUSTOMER")){
                return logoutModel;
            }

             //System.out.println(request.getParameter("checkingPicker") + "checkingPicker");
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/jdbc/config/DaoDetails.xml");
            TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
            TransactionDaoImpl extTransactionDAO = ctx.getBean("TransactionDao", TransactionDaoImpl.class);

            int merchantID = Integer.parseInt(request.
                    getSession().getAttribute("userID").toString());

            List<String> userAccounts = transferDAO.getUserAccounts(merchantID);
            model.addObject("userAccounts", userAccounts);

            List<String> authorizedUserAccounts = new ArrayList<>();
            for(int i=0 ; i < userAccounts.size();i++){
                authorizedUserAccounts.add(userAccounts.get(i).split(":")[1].trim());
            }


            System.out.println("authorized user account");
            for(String demo: authorizedUserAccounts){
                System.out.println(demo);
            }


            int sentAmount = 0;
            if (request.getParameterMap().containsKey("etpinputAmount") && request.getParameterMap().containsKey("checkingPicker")) {

                if(!(request.getParameter("checkingPicker").matches("[a-zA-Z ]+\\w\\s*:\\d+"))){
                    model.addObject("success", false);
                    model.addObject("error_msg", "Tampering User Accounts!");
                    ctx.close();
                    return model;
                }

                String AmountSentString = request.getParameter("etpinputAmount").toString().trim();
                if(!(AmountSentString.replaceAll(",", "").matches("^(\\d+\\.)?\\d+$")) || AmountSentString.isEmpty()){
                    model.addObject("success", false);
                    model.addObject("error_msg", "Invalid Amount Co!")  ;
                    return model;
                }
                try {
                    sentAmount = Integer.parseInt(request.getParameter("etpinputAmount").toString().trim());
                }
                catch(Exception e){
                    model.addObject("success", false);
                    model.addObject("error_msg", " Send value not permissible");
                    ctx.close();
                    return model;
                }

                if (!(request.getParameter("checkingPicker") == null)) {
                    String authorizedCustomerID = request.getParameter("checkingPicker").toString().split(":")[1].trim();

                    if(!authorizedUserAccounts.contains(authorizedCustomerID)){
                        model.addObject("success", false);
                        model.addObject("error_msg", " Malicious Merchant Account Inserted");
                        ctx.close();
                        return model;
                    }


                    String userName = request.getParameter("checkingPicker").toString().split(":")[0];
                    HashMap<String,String> returnedHmap = transferDAO.processPayment(merchantID, sentAmount,Integer.parseInt(authorizedCustomerID));

                    int accepted = Integer.parseInt(returnedHmap.get("accepted").trim());

                    Iterator myIterator= returnedHmap.entrySet().iterator();
                    while(myIterator.hasNext()){
                        Map.Entry me = (Map.Entry)myIterator.next();
                        //System.out.println(me.getKey()+"<-->"+me.getValue());
                    }
                    
                    int payerAccount = Integer.parseInt(returnedHmap.get("userAccount").trim());
                    int payeeAccountNumber = Integer.parseInt(returnedHmap.get("merchantCheckingAccount").trim());
                    String description = "Transferred "+sentAmount+"$ from Account:"+payerAccount+" to Account:"+payeeAccountNumber+"";
                    
                    BigDecimal amount = new BigDecimal(sentAmount);
                    if(!(payerAccount==-1) && !(payeeAccountNumber==-1) && !(accepted==-1)){

                        // reject Transaction if balance > 1000
                        if(amount.compareTo(new BigDecimal("1000")) == 1){
                            model.addObject("success", false);
                            model.addObject("error_msg", " Transaction Amount cannot exceed $1000");
                            ctx.close();
                            return model;
                        }
                        else{
                            Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccount, amount, payeeAccountNumber, description, "MERCHANT");
                            extTransactionDAO.save(extTransferTrans, "transaction_pending");
                            result = transferDAO.updateAvailableBalance(amount,payerAccount);
                        }
                    }else{
                        model.addObject("success", false);
                        model.addObject("error_msg", " No sufficient Balance");
                        ctx.close();
                        return model;
                    }

                }
            }

            if(result){
                model.addObject("success",true);
            }
            ctx.close();
            return model;
    	}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
        
    }
}
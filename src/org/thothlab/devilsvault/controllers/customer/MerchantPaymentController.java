package org.thothlab.devilsvault.controllers.customer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thothlab.devilsvault.dao.transaction.TransferDAO;
import org.thothlab.devilsvault.dao.transaction.TransactionDaoImpl;
import org.thothlab.devilsvault.db.model.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Controller
public class MerchantPaymentController {

    String role;
    int userID;
    String username;

    public void setGlobals(HttpServletRequest request) {
        role = (String) request.getSession().getAttribute("role");
        System.out.println(role + "role");
        userID = (int) request.getSession().getAttribute("userID");
        username = (String) request.getSession().getAttribute("username");
    }

    @RequestMapping("/customer/merchantpayment")
    public ModelAndView merchantPayToUser(HttpServletRequest request) throws SQLException, ParseException {
        ModelAndView model = new ModelAndView("customerPages/merchantMakePayment");


        setGlobals(request);

        System.out.println(request.getParameter("checkingPicker") + "checkingPicker");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
        TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
        TransactionDaoImpl extTransactionDAO = ctx.getBean("TransactionDao", TransactionDaoImpl.class);

        int merchantID = Integer.parseInt(request.getSession().getAttribute("userID").toString());

        List<String> userAccounts = transferDAO.getUserAccounts(merchantID);
        model.addObject("userAccounts", userAccounts);


        int sentAmount = 0;
        if (request.getParameterMap().containsKey("etpinputAmount") && request.getParameterMap().containsKey("checkingPicker")) {
            sentAmount = Integer.parseInt(request.getParameter("etpinputAmount").toString().trim());
            if (!(request.getParameter("checkingPicker") == null)) {
                String authorizedCustomerID = request.getParameter("checkingPicker").toString().split(":")[1];
                String userName = request.getParameter("checkingPicker").toString().split(":")[0];
                HashMap<String,String> returnedHmap = transferDAO.processPayment(merchantID, sentAmount,Integer.parseInt(authorizedCustomerID));

                int accepted = Integer.parseInt(returnedHmap.get("accepted").trim());

                System.out.println("succesValue / merchantAccountNumber"+returnedHmap.get("accepted"));
                /*Boolean success = (accepted==-1)? false:true;
                if (!success) {
                    model.addObject("success", false);
                    model.addObject("error_msg", " Transaction Denied / Failed");
                    ctx.close();
                    return model;
                }
*/
                BigDecimal amount = new BigDecimal(sentAmount);
                int payerAccount = Integer.parseInt(returnedHmap.get("userAccount").trim());
                int payeeAccountNumber = Integer.parseInt(returnedHmap.get("merchantCheckingAccount").trim());
                String description = "Transferred "+sentAmount+"$ from Account:"+payerAccount+" to Account:"+payeeAccountNumber+"";

                System.out.println(payerAccount+"sdhfksdhf"+payeeAccountNumber+"fhsdkhsf"+amount);




                if(!(payerAccount==-1) && !(payeeAccountNumber==-1) && !(accepted==-1)){
                    Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccount, amount, payeeAccountNumber, description, "MERCHANT");
                    extTransactionDAO.save(extTransferTrans, "transaction_pending");
                }else{
                    model.addObject("success", false);
                    model.addObject("error_msg", " Transaction Denied / Failed");
                    ctx.close();
                    return model;
                }
    model.addObject("success",true);
            }
        }

        return model;
    }
}

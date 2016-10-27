package org.thothlab.devilsvault.dao.customer;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.dao.bankaccount.CreditCardGenerator;
import org.thothlab.devilsvault.db.model.CreditAccountDB;

@Repository ("CreditCardDao")
public class CreditCardDaoImpl {

	@SuppressWarnings("unused")
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	@Autowired
	public void setdataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public void createCreditAccount(Integer accountNumber)
	{
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    cal.set(year, month, 25);
		java.sql.Date sqldateCycle = new java.sql.Date(cal.getTime().getTime());
	    cal.set(year, month, 15);
		java.sql.Date sqldateDue = new java.sql.Date(cal.getTime().getTime());
		BigInteger credit_card_number;
		credit_card_number = new BigInteger(CreditCardGenerator.generateMasterCardNumber());
		while(true)
		{
			String query = "SELECT COUNT(*) FROM credit_card_account_details WHERE credit_card_no ='" + credit_card_number + "';";
			Integer count = jdbcTemplate.queryForObject(query,Integer.class);
			if(count < 1)
			{
				break;
			}
			credit_card_number = new BigInteger(CreditCardGenerator.generateMasterCardNumber());
		}
		CreditAccountDB creditaccount = new CreditAccountDB();
		creditaccount.setAccount_number(accountNumber);
		creditaccount.setApr((float) 2.2);
		creditaccount.setAvailable_balance(0);
		creditaccount.setCredit_card_number(credit_card_number);
		creditaccount.setCredit_limit(800);
		creditaccount.setCurrent_due_amount(0);
		creditaccount.setCycle_date(sqldateCycle);
		creditaccount.setDue_date(sqldateDue);
		creditaccount.setInterest(5);
		creditaccount.setLast_bill_amount(0);
		creditaccount.setPayment(0);
		save(creditaccount);
		
	}
	public Boolean save(CreditAccountDB creditaccount)
	{
		String query = "INSERT INTO  credit_card_account_details (interest , credit_card_no , available_balance , last_bill_amount , due_date , apr , account_number , cycle_date , current_due_amt , credit_limit , payment ) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1,creditaccount.getInterest());
			ps.setLong(2, creditaccount.getCredit_card_number().longValue());
			ps.setInt(3, creditaccount.getAvailable_balance());
			ps.setInt(4,creditaccount.getLast_bill_amount());
			ps.setInt(7,creditaccount.getAccount_number());
			ps.setInt(9,creditaccount.getCurrent_due_amount());
			ps.setInt(10,creditaccount.getCredit_limit());
			ps.setDate(5,creditaccount.getDue_date());
			ps.setDate(8, creditaccount.getCycle_date());
			ps.setInt(11,creditaccount.getPayment());
			ps.setFloat(6,creditaccount.getApr());
			int out = ps.executeUpdate();
			if(out !=0){
				return true;
			}else return false;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
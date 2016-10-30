package org.thothlab.devilsvault.dao.request;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.Customer;
import org.thothlab.devilsvault.db.model.InternalUser;
import org.thothlab.devilsvault.db.model.Request;

@Repository ("internalRequestDao")
public class InternalRequestDaoImpl extends RequestDaoImpl {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);	
	}

	public int createRequest() {
		// TODO Auto-generated method stub
		String query = "insert into internal_request_pending (requesterid, request_type, current_value, requested_value, status, approver, description, timestamp_created, timestamp_updated) values (?,?,?,?,?,?,?,?,?)";
		int rowsAffected = jdbcTemplate.update(query, "1", "phone change", "12345", "54321","pending", "ajay", "Chnage phone number","2016-10-10","2016-10-10");
	    return rowsAffected;
	}
	
	public List<Request> getAllPending(String role) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM internal_request_pending where role='" + role + "'";
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));
		return requestList;
	}
	
	public List<Request> getAllCompleted(String role) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM internal_request_completed where role='" + role + "'";
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));
		return requestList;
	}
	
	@Override
	public Boolean save(Request request, String type) {
		return super.save(request, type);
	}
	
	@Override
	public List<Request> getById(int id, String status) {
		String query = "SELECT * FROM internal_request_"+status+" WHERE id = "+id;
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));
		return requestList;
	}
	
	@Override
	public List<Request> getByUserId(int requesterid, String status) {
		String query = "SELECT * FROM internal_request_"+status+" WHERE requesterid = "+requesterid;
		List<Request> requestList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Request>(Request.class));
		return requestList;
	}
	
	@Override
	public void approveRequest(int id, String type,int approver) {
		// TODO Auto-generated method stub
		super.approveRequest(id, type, approver);
	}

	@Override
	public void rejectRequest(int id, String type, int approver) {
		// TODO Auto-generated method stub
		super.rejectRequest(id, type, approver);
	}
	
	public void raiseExternalRequest(Customer customer, String requestType, String newValue, Integer internalUserID)
	{
		Request request = new Request();
		request.setRequesterid(internalUserID);
		request.setRequest_type(requestType);
		if(requestType.equalsIgnoreCase("email"))
		{
			request.setCurrent_value(customer.getEmail());
		}
		else if(requestType.equalsIgnoreCase("phone"))
		{
			request.setCurrent_value(customer.getPhone().toString());
		}
		else
		{
			request.setCurrent_value(customer.getAddress());
		}
		request.setRequested_value(newValue);
		request.setStatus("Pending");
		request.setApprover(Integer.toString(customer.getId()));
		request.setDescription("Request to change " + requestType);
		Date utilDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		request.setTimestamp_created(sqlDate);
		request.setTimestamp_updated(sqlDate);
		request.setRole("ROLE_CUSTOMER");
		super.saveRaisedRequest(request, "external_request_pending");
		
	}
	public void raiseInternalPersonalRequest(InternalUser internaluser, String requestType, String newValue, Integer internalUserID, String role)
	{
		Request request = new Request();
		request.setRequesterid(internalUserID);
		request.setRequest_type(requestType);
		if(requestType.equalsIgnoreCase("email"))
		{
			request.setCurrent_value(internaluser.getEmail());
		}
		else if(requestType.equalsIgnoreCase("phone"))
		{
			request.setCurrent_value(internaluser.getPhone().toString());
		}
		else
		{
			request.setCurrent_value(internaluser.getAddress());
		}
		request.setRequested_value(newValue);
		request.setStatus("Pending");
		request.setApprover(Integer.toString(0));
		request.setDescription("Request to change " + requestType);
		Date utilDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		request.setTimestamp_created(sqlDate);
		request.setTimestamp_updated(sqlDate);
		request.setRole(role);
		super.saveRaisedRequest(request, "internal_request_pending");
		
	}
	
	public void raiseInternalRequest(Customer customer, String requestType, String newValue, Integer internalUserID)
    {
        Request request = new Request();
        request.setRequesterid(customer.getId());
        request.setRequest_type(requestType);
        if(requestType.equalsIgnoreCase("email"))
        {
            request.setCurrent_value(customer.getEmail());
        }
        else if(requestType.equalsIgnoreCase("phone"))
        {
            request.setCurrent_value(customer.getPhone().toString());
        }
        else
        {
            request.setCurrent_value(customer.getAddress());
        }
        request.setRequested_value(newValue);
        request.setStatus("Pending");
        request.setApprover(Integer.toString(0));
        request.setDescription("Request to change " + requestType);
        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        request.setTimestamp_created(sqlDate);
        request.setTimestamp_updated(sqlDate);
        request.setRole("ROLE_CUSTOMER");
        super.saveRaisedRequest(request, "external_request_pending");
        
    }
	
	public Boolean validateRequest(Integer userID,String requesttype,String table)
    {
        String query = "SELECT COUNT(*) FROM " + table +" WHERE requesterid ='" + userID + "' AND request_type ='" + requesttype + "';";
       Integer count = jdbcTemplate.queryForObject(query,Integer.class);
       if(count > 0)
       {
           return false;
       }
        return true;
    }

}

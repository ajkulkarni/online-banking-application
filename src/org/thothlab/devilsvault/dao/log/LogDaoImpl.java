package org.thothlab.devilsvault.dao.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thothlab.devilsvault.db.model.DatabaseLog;

@Repository ("DatabaseLogDao")
public class LogDaoImpl implements LogDao{
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub	
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Boolean save(DatabaseLog log, String tablename) {
		// TODO Auto-generated method stub
		String query = "insert into " + tablename + " (activity, userid, details, timestamp) values (?,?,?,?)";
		
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,log.getActivity());
			ps.setInt(2, log.getUserid());
			ps.setString(3, log.getDetails());
			ps.setDate(4, log.getTimestamp());
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

	@Override
    public List<DatabaseLog> getByUserId(int userid, String type) {
        // TODO Auto-generated method stub
        String query = "SELECT * FROM " + type + "_log WHERE userid = " + userid;
        List<DatabaseLog> logList = jdbcTemplate.query(query, new BeanPropertyRowMapper<DatabaseLog>(DatabaseLog.class));
        return logList;
    }
	
	@Override
    public List<DatabaseLog> getLogs(String type) {
        // TODO Auto-generated method stub
        String query = "SELECT * FROM " + type + "_log ORDER BY timestamp DESC LIMIT 50";
        List<DatabaseLog> logList = jdbcTemplate.query(query, new BeanPropertyRowMapper<DatabaseLog>(DatabaseLog.class));
        return logList;
    }
	
}

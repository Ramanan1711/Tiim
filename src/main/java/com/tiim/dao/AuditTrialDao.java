package com.tiim.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.AuditTrial;


@Repository
public class AuditTrialDao {

	@Autowired
	DataSource datasource;
	
	public List<AuditTrial> getAuditTrial(String userId, Date fromDate, Date toDate)
	{
		List<AuditTrial> lstProductHistory = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AuditTrial auditTrial = null;
		try
		{
			con = datasource.getConnection();
			String query = "SELECT a.userId userId,  b.username userName, pageName, description, accesstime FROM transcation_history a left join mst_user b on a.userId = b.userId where username != 'null'";
				
			if(userId != null && fromDate != null && toDate != null && !userId.trim().equals(","))
			{
				query = query + " and a.userId = ? and accesstime between ? and ?";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, userId);
				pstmt.setDate(2, fromDate);
				pstmt.setDate(3, toDate);
			}else
			if(fromDate != null && toDate != null)
			{
				query = query + " and accesstime between ? and ?";
				pstmt = con.prepareStatement(query);
				pstmt.setDate(1, fromDate);
				pstmt.setDate(2, toDate);
			}else if(userId != null && userId.length() > 0)
			{
				query = query + " and a.userId = ?";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, userId);
			}
			System.out.println("query: "+query+" : userId: "+userId);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				auditTrial = new AuditTrial();
				auditTrial.setUserId(rs.getInt("userId"));
				auditTrial.setUserName(rs.getString("userName"));
				auditTrial.setPageName(rs.getString("pageName"));
				auditTrial.setDescription(rs.getString("description"));
				auditTrial.setAccessDate(rs.getString("accesstime"));
				lstProductHistory.add(auditTrial);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in getAuditTrial : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in  getAuditTrial : "+ex.getMessage());
			}
		}
		return lstProductHistory;
	}
}

package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.util.SendMail;

@Repository
public class SendMailToApprover {
	
	@Autowired
	DataSource datasource;
	
	public void sendMail(String screenName, String mailContent, String subject)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		String emailId = "";
		SendMail sendMail = new SendMail();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select emailid from screen_approver_map a, mst_user b where screenname=? and a.username = b.username;");
			pstmt.setString(1, screenName);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				emailId = emailId + rs.getString("emailid") +";";
			}
			sendMail.sendIssueApproved(emailId, mailContent, subject);
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire SendMailToApprover in sendMail : "+ex.getMessage());
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
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)	
			{
				System.out.println("Exception when getting the entire SendMailToApprover in sendMail : "+ex.getMessage());
			}
		}
	}

}

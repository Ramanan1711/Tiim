package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.ApprovalScreen;

@Repository
public class ApprovalConfigDao {

	@Autowired
	DataSource datasource;
	public List<ApprovalScreen> getApprovalScreenConfig()
	{
		List<ApprovalScreen> lstApprovalScreen = new ArrayList<ApprovalScreen>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ApprovalScreen approvalScreen = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select configid,screenname,sessionName,approvalFlag from approval_screen_config");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				approvalScreen = new ApprovalScreen();
				approvalScreen.setConfigId(rs.getInt("configid"));
				approvalScreen.setScreenName(rs.getString("screenname"));
				approvalScreen.setSessionName(rs.getString("sessionName"));
				approvalScreen.setApprovalFlag(rs.getInt("approvalFlag"));
				lstApprovalScreen.add(approvalScreen);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getApprovalScreenConfig in approval_screen_config table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getApprovalScreenConfig in approval_screen_config table : "+ex.getMessage());
			}
		}
		return lstApprovalScreen;	
	}
	
	public void updateApprovalScreenConfig(int approvalFlag[])
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			updateApprovalScreenConfigToZero();
			con = datasource.getConnection();
			for(int i=0; i<approvalFlag.length; i++)
			{
				pstmt = con.prepareStatement("update approval_screen_config set approvalFlag = ? where configid = ?");
				System.out.println(approvalFlag[i]);
				pstmt.setInt(1, 1);
				pstmt.setInt(2, approvalFlag[i]);
				pstmt.executeUpdate();
			}
			
			 
					
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire updateApprovalScreenConfig in approval_screen_config table : "+ex.getMessage());
		}
		finally
		{
			try
			{
			
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
				System.out.println("Exception when closing the connection in entire updateApprovalScreenConfig in approval_screen_config table : "+ex.getMessage());
			}
		}
	}
	
	public void updateApprovalScreenConfigToZero()
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = datasource.getConnection();
			
				pstmt = con.prepareStatement("update approval_screen_config set approvalFlag = ? ");
				pstmt.setInt(1, 0);
				pstmt.executeUpdate();
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire updateApprovalScreenConfigToZero in approval_screen_config table : "+ex.getMessage());
		}
		finally
		{
			try
			{
			
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
				System.out.println("Exception when closing the connection in entire updateApprovalScreenConfig in approval_screen_config table : "+ex.getMessage());
			}
		}
	}

	
}

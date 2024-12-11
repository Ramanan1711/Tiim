package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.util.SendMail;
import com.tiim.util.TiimUtil;

@Repository
public class AddScreenApprovalDao {
	

	@Autowired
	DataSource datasource;
	
	@Autowired
	ToolingIssueDao issueDao;
	
	@Autowired
	ToolingReturnDao returnDao;
	
	SendMail sendMail = new SendMail();
	
	public void addScreenApproval(String[] approval, String userName)
	{
		
		try
		{
			if(approval != null)
			{
				String screenName;
				for(int i=0; i< approval.length;i++)
				{
					String screenApproval = approval[i];				
					int approvalFlag = 0;
					int transactionId = 0;
					int  levelNumber = 0;
					int noOfLevel  = 0;
					String[] screemValue = screenApproval.split(",");
					if(screemValue.length == 1)
					{
						screenName = TiimUtil.ValidateNull(approval[0]).trim();
						transactionId = Integer.parseInt(TiimUtil.ValidateNull(approval[1]).trim());
						levelNumber = Integer.parseInt(TiimUtil.ValidateNull(approval[2]).trim());
						noOfLevel  = Integer.parseInt(TiimUtil.ValidateNull(approval[3]).trim());
						i = 3;
					}else
					{
						screenName = TiimUtil.ValidateNull(screemValue[0]).trim();
						transactionId = Integer.parseInt(TiimUtil.ValidateNull(screemValue[1]).trim());
						levelNumber = Integer.parseInt(TiimUtil.ValidateNull(screemValue[2]).trim());
						noOfLevel  = Integer.parseInt(TiimUtil.ValidateNull(screemValue[3]).trim());
					}
					if(levelNumber == noOfLevel)
					{
						approvalFlag = 1;
					}
					if(screenName.equalsIgnoreCase("Receipt Note"))
					{
						updateNonApprovalReceiptNote(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Receiving Inspection"))
					{
						updateReceivingInspection(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Receiving Inspection Request"))
					{
						updateReceivingInspectionRequest(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Stock Transfer Issue"))
					{
						updateStockTransferIssue(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Stock Transfer Request"))
					{
						updateStockTransferRequest(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Production Return Note"))
					{
						updateProductionReturnNote(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Production Request Note"))
					{
						updateToolingRequest(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Production Issue Note"))
					{
						updateIssueNote(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Periodic Inspection Request"))
					{
						updatePeriodicRequest(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Periodic Inspection Request Report"))
					{
						updatePeriodicInspectionReport(userName, transactionId, levelNumber, approvalFlag);
					}else if(screenName.equalsIgnoreCase("Indent Tool"))
					{
						updateIndentTool(userName, transactionId, levelNumber, approvalFlag);
					}
					
					
				}
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire addScreenApproval : "+ex.getMessage());
		}
			
	}
	
	public List<String> updateNonApprovalReceiptNote(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstReceiptId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_receipt_note set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ?  where toolingreceiptid = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			
			//sendMail.sendIssueApproved("ranikkarasu@gmail.com;ranikkarasu@yahoo.co.in", transactionId+"");
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire updateNonApprovalReceiptNote in tooling_receipt_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire updateNonApprovalReceiptNote in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return lstReceiptId;	
	}

	public List<String> updateReceivingInspection(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstReceiptId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update  tooling_receiving_inspection set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ?  where originalid = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire updateReceivingInspection in tooling_receiving_inspection table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire updateReceivingInspection in tooling_receiving_inspection table : "+ex.getMessage());
			}
		}
		return lstReceiptId;	
	}
	
	public List<String> updateReceivingInspectionRequest(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstReceiptId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_receiving_request set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ?  where originalid = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalReceivingInspectionRequest in tooling_receiving_request table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalReceivingInspectionRequest in tooling_receiving_request table : "+ex.getMessage());
			}
		}
		return lstReceiptId;	
	}
	
	public List<String> updateToolingRequest(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstRequestId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_request_note set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ?  where originalid = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalToolingRequest in tooling_request_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalToolingRequest in tooling_request_note table : "+ex.getMessage());
			}
		}
		return lstRequestId;	
	}
	
	public List<String> updatePeriodicInspectionReport(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstRequestId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update periodic_inspection_report set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ? where originalid = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalPeriodicInspectionReport in periodic_inspection_report table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalPeriodicInspectionReport in periodic_inspection_report table : "+ex.getMessage());
			}
		}
		return lstRequestId;	
	}
	
	public List<String> updateIndentTool(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstRequestId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			System.out.println("intentId: "+transactionId);
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_intent_note set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ? where intentId = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire updateIndentTool in tooling_intent_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire updateIndentTool in tooling_intent_note table : "+ex.getMessage());
			}
		}
		return lstRequestId;	
	}
	
	public List<String> updateStockTransferIssue(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstRequestId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update stock_transfer_issue set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ? where transferIssueId = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalStockTransferIssue in stock_transfer_issue table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalStockTransferIssue in stock_transfer_issue table : "+ex.getMessage());
			}
		}
		return lstRequestId;	
	}

	public List<String> updateStockTransferRequest(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstRequestId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update stock_transfer_request set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ? where stockTransferId = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
					
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalStockTransferRequest in stock_transfer_request table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalStockTransferRequest in stock_transfer_request table : "+ex.getMessage());
			}
		}
		return lstRequestId;	
	}
	
	public List<String> getNonApprovalSupplierReturn()
	{
		List<String> lstRequestId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT supplierreturnid FROM suplier_return where approvalflag = ?");
			pstmt.setInt(1, 0);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lstRequestId.add(rs.getString("supplierreturnid"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalSupplierReturn in suplier_return table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalStockTransferRequest in suplier_return table : "+ex.getMessage());
			}
		}
		return lstRequestId;	
	}
	
	public List<String> updateIssueNote(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstIssueId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_issue_note set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ? where originalid = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			issueDao.getMaxToolIssueId(transactionId);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire updateIssueNote in suplier_return table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalIssueNote in suplier_return table : "+ex.getMessage());
			}
		}
		return lstIssueId;	
	}
	
	public List<String> updatePeriodicRequest(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstRequestno = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_periodical_inspection_request set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ? where originalid = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalPeriodicRequest in tooling_periodical_inspection_request table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalPeriodicRequest in tooling_periodical_inspection_request table : "+ex.getMessage());
			}
		}
		return lstRequestno;	
	}
	
	public List<String> updateProductionReturnNote(String userName, int transactionId, int levelNumber, int approvalFlag)
	{
		List<String> lstReturnNoteId = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_production_return_note set approvalflag = ?, approvedby = ?, approveddate=now(), levelofapproval = ? where originalid = ?");
			pstmt.setInt(1, approvalFlag);
			pstmt.setString(2, userName);
			pstmt.setInt(3, levelNumber);
			pstmt.setInt(4, transactionId);
			pstmt.executeUpdate();
			returnDao.getMaxReturnId(transactionId);
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalProductionReturnNote in tooling_production_return_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalProductionReturnNote in tooling_production_return_note table : "+ex.getMessage());
			}
		}
		return lstReturnNoteId;	
	}
	


}

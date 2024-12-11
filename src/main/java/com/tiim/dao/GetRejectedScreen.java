package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.ScreenUrl;

@Repository
public class GetRejectedScreen {

	@Autowired
	DataSource datasource;
	private final String RECEIVING_INSPECTION_REQUEST_URL = "viewReceivingRequestReport.jsf?toolingRequestId=";
	private final String RECEIVING_INSPECTION_REPORT_URL ="viewToolingReceiveInspectionReport.jsf?toolingInspectionId=";
	private final String STOCK_ISSUE_URL ="";
	private final String STOCK_REQUEST_URL ="";
	private final String PRODUCTION_RETURN_URL ="viewToolingReturnReport.jsf?returnId=";
	private final String PRODUCTION_ISSUE_URL ="viewToolingIssueNoteReport.jsf?issueId=";
	private final String PRODUCTION_REQUEST_URL ="viewToolingRequestReport.jsf?requestId=";
	private final String PERIODIC_REQUEST_URL ="viewPeriodicInspectionRequestReport.jsf?requestNo=";
	private final String PERIODIC_REPORT_URL ="viewPeriodicInspectionReport.jsf?reportNo=";
	private final String INDENT_REPORT_URL = "viewIndentNoteReport.jsf?requestId=";
	private final String RECEIPT_URL ="viewReceiptNoteReport.jsf?requestId=";
	public List<ScreenUrl> getNonApprovalReceiptNote(int leveNumber)
	{
		List<ScreenUrl> lstReceiptId = new ArrayList<ScreenUrl>();
		ScreenUrl url;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT toolingreceiptid FROM tooling_receipt_note where approvalflag = ? and levelofapproval < ?");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("toolingreceiptid"));
				url.setScreenUrl(RECEIPT_URL+rs.getInt("toolingreceiptid"));
				lstReceiptId.add(url);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalReceiptNote in tooling_receipt_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalReceiptNote in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return lstReceiptId;	
	}
	
	public List<ScreenUrl> getNonApprovalReceivingInspection(int leveNumber)
	{
		List<ScreenUrl> lstReceiptId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select toolinginspectionid,originalid from tooling_receiving_inspection where approvalflag =  ?  and levelofapproval < ?"
					+ "  order by toolinginspectionid desc) x group by originalid");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("originalid"));
				url.setScreenUrl(RECEIVING_INSPECTION_REPORT_URL+rs.getInt("toolinginspectionid"));
				lstReceiptId.add(url);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalReceivingInspection in tooling_receiving_inspection table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalReceivingInspection in tooling_receiving_inspection table : "+ex.getMessage());
			}
		}
		return lstReceiptId;	
	}
	
	public List<ScreenUrl> getNonApprovalReceivingInspectionRequest(int leveNumber)
	{
		List<ScreenUrl> lstReceiptId = new ArrayList<ScreenUrl>();
		ScreenUrl url;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select toolingrequestid,originalid from tooling_receiving_request where approvalflag =  ?  and levelofapproval < ? "
					+ " order by inspectionreportno desc) x group by originalid");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("originalid"));
				url.setScreenUrl(RECEIVING_INSPECTION_REQUEST_URL+rs.getInt("toolingrequestid"));
				lstReceiptId.add(url);
			}
			
			
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
	public List<ScreenUrl> getNonApprovalStockTransferIssue(int leveNumber)
	{
		List<ScreenUrl> lstRequestId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT transferIssueId FROM stock_transfer_issue where approvalflag = ? and levelofapproval < ?");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("transferIssueId"));
				url.setScreenUrl(STOCK_ISSUE_URL+rs.getInt("transferIssueId"));
				lstRequestId.add(url);
			}
			
			
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

	public List<ScreenUrl> getNonApprovalStockTransferRequest(int leveNumber)
	{
		List<ScreenUrl> lstRequestId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT stockTransferId FROM stock_transfer_request where approvalflag = ? and levelofapproval < ?");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("stockTransferId"));
				url.setScreenUrl(STOCK_REQUEST_URL+rs.getInt("stockTransferId"));
				lstRequestId.add(url);
			}
			
			
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
	
	public List<ScreenUrl> getNonApprovalSupplierReturn(int leveNumber)
	{
		List<ScreenUrl> lstRequestId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT supplierreturnid FROM suplier_return where approvalflag = ? and levelofapproval < ?");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("supplierreturnid"));
				url.setScreenUrl(RECEIVING_INSPECTION_REQUEST_URL+rs.getInt("supplierreturnid"));
				lstRequestId.add(url);
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
	
	public List<ScreenUrl> getNonApprovalIssueNote(int leveNumber)
	{
		List<ScreenUrl> lstIssueId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select issueId,originalid from tooling_issue_note where approvalflag =  ? and levelofapproval < ? "
					+ " order by issueId desc) x group by originalid");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("originalid"));
				url.setScreenUrl(PRODUCTION_ISSUE_URL+rs.getInt("issueId"));
				lstIssueId.add(url);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalIssueNote in suplier_return table : "+ex.getMessage());
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
	
	public List<ScreenUrl> getNonApprovalPeriodicRequest(int leveNumber)
	{
		List<ScreenUrl> lstRequestno = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url ;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select requestno,originalid from tooling_periodical_inspection_request where approvalflag =  ?  and levelofapproval < ? "
					+ " order by requestno desc) x group by originalid");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("originalid"));
				url.setScreenUrl(PERIODIC_REQUEST_URL+rs.getInt("requestno"));
				lstRequestno.add(url);
			}
			
			
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
	
	public List<ScreenUrl> getNonApprovalProductionReturnNote(int leveNumber)
	{
		List<ScreenUrl> lstReturnNoteId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select returnnoteid,originalid from tooling_production_return_note where approvalflag =  ?  and levelofapproval < ?"
					+ " order by returnnoteid desc) x group by originalid");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("originalid"));
				url.setScreenUrl(PRODUCTION_RETURN_URL+rs.getInt("returnnoteid"));
				lstReturnNoteId.add(url);
			}
			
			
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
	
	public List<ScreenUrl> getNonApprovalIndentTool(int leveNumber)
	{
		List<ScreenUrl> lstRequestId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select intentId from tooling_intent_note where approvalflag =  ?  and levelofapproval < ?"
					+ " order by intentId desc ");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("intentId"));
				url.setScreenUrl(INDENT_REPORT_URL+rs.getInt("intentId"));
				lstRequestId.add(url);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getNonApprovalIndentTool in tooling_intent_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getNonApprovalIndentTool in tooling_intent_note table : "+ex.getMessage());
			}
		}
		return lstRequestId;	
	}
	
	public List<ScreenUrl> getNonApprovalToolingRequest(int leveNumber)
	{
		List<ScreenUrl> lstRequestId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select requestid,originalid from tooling_request_note where approvalflag =  ?  and levelofapproval < ? "
					+ " order by requestid desc) x group by originalid");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("originalid"));
				url.setScreenUrl(PRODUCTION_REQUEST_URL+rs.getInt("requestid"));
				lstRequestId.add(url);
			}
			
			
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
	
	public List<ScreenUrl> getNonApprovalPeriodicInspectionReport(int leveNumber)
	{
		List<ScreenUrl> lstRequestId = new ArrayList<ScreenUrl>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ScreenUrl url;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select reportno,originalid from periodic_inspection_report where approvalflag =  ?  and levelofapproval < ?"
					+ " order by reportno desc) x group by originalid");
			pstmt.setInt(1, 3);
			pstmt.setInt(2, leveNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				url = new ScreenUrl();
				url.setTransactionId(rs.getInt("originalid"));
				url.setScreenUrl(PERIODIC_REPORT_URL+rs.getInt("reportno"));
				lstRequestId.add(url);
			}
			
			
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
	
}

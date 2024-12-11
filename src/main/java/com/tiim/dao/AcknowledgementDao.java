package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.Acknowledgement;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingReturnNote;
import com.tiim.util.TiimUtil;

@Repository
public class AcknowledgementDao {
	@Autowired
	DataSource datasource;
	
	public List<ToolingIssueNote> getUnAcknowledgeToolingIssueNote(String approvalFlag)
	{
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if("1".equalsIgnoreCase(approvalFlag))
			{
			pstmt = con.prepareStatement("Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
					+ " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber from tooling_issue_note a, tooling_issue_note_detail b "
					+ " where isAcknowledge = 0 and a.issueId = b.issueId  ");
			}else
			{
				pstmt = con.prepareStatement("Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
						+ " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber from tooling_issue_note a, tooling_issue_note_detail b "
						+ " where isAcknowledge = 0 and a.issueId = b.issueId  ");
			}
				
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				issueNote.setRequestDate(rs.getString("requestDate"));
				issueNote.setRequestBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setOriginalId(rs.getInt("originalid"));
				issueNote.setProductName1(rs.getString("productname"));
				issueNote.setDrawingNo1(rs.getString("drawingno"));
				issueNote.setTypeOfTooling1(rs.getString("typeoftool"));
				issueNote.setMachineName1(rs.getString("machinename"));
				issueNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
				lstIssueNote.add(issueNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the acknowledgement tooling_issue_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the acknowledgement tooling_issue_note list : "+ex.getMessage());
			}
		}
		return lstIssueNote;	
	}
	
	public void updateAckIssueNote(Acknowledgement issueAck)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		int[] issueId = issueAck.getAcknowledgeId();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_issue_note set isAcknowledge = ? where originalid = ?");
			for(int i = 0; i<issueId.length; i++)
			{
				pstmt.setInt(1, 1);
				pstmt.setInt(2, issueId[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire updateAckIssueNote in suplier_return table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire updateAckIssueNote : "+ex.getMessage());
			}
		}
	}
	
	public void updateAckProductionReturnNote(Acknowledgement returnAck)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		int[] returnId = returnAck.getAcknowledgeId();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_production_return_note set isAcknowledge = ? where originalid = ?");
			for(int i = 0; i<returnId.length; i++)
			{
				pstmt.setInt(1, 1);
				pstmt.setInt(2, returnId[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire updateAckProductionReturnNote in tooling_production_return_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire updateAckProductionReturnNote in tooling_production_return_note table : "+ex.getMessage());
			}
		}
	}
	
	public List<ToolingReturnNote> getUnAcknowledgedToolingReturnNote()
	{
		List<ToolingReturnNote> lstReturnNote = new ArrayList<ToolingReturnNote>();
		ToolingReturnNote returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select a.returnnoteid returnnoteid, returndate, issueNo, issueDate, returnedby, issuedBy, customername, branchname, "
					+ " a.originalid originalid, toolinglotnumber, typeoftool, productname, machinetype, drawingNo "
					+ " from tooling_production_return_note a, tooling_production_return_note_detail b where a.returnnoteid = b.returnnoteid and isAcknowledge = ? "
					// "and a.approvalflag = ?"
					+ " order by returnnoteid desc)  x group by originalid ");
			pstmt.setInt(1, 0);
		//	pstmt.setInt(2, 1);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new ToolingReturnNote();
				returnNote.setIssueId(rs.getInt("issueNo"));
				returnNote.setIssueDate(rs.getString("issueDate"));
				returnNote.setReturnId(rs.getInt("returnnoteid"));
				returnNote.setReturnBy(TiimUtil.ValidateNull(rs.getString("returnedby")).trim());
				returnNote.setIssueBy(TiimUtil.ValidateNull(rs.getString("issuedBy")).trim());
				returnNote.setCustomerName(TiimUtil.ValidateNull(rs.getString("customername")).trim());
				returnNote.setBranchName(TiimUtil.ValidateNull(rs.getString("branchname")).trim());
				returnNote.setOriginalId(rs.getInt("originalid"));
				returnNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
				returnNote.setProductName1(rs.getString("productname"));
				returnNote.setTypeOfTooling1(rs.getString("typeoftool"));
				returnNote.setMachineName1(rs.getString("machinetype"));
				returnNote.setDrawingNo1(rs.getString("drawingNo"));
				lstReturnNote.add(returnNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_issue_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_issue_note list : "+ex.getMessage());
			}
		}
	
		return lstReturnNote;
	}

}

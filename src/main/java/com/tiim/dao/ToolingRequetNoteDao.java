package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.tiim.model.ToolingRequestNote;
import com.tiim.util.TiimUtil;

@Repository
public class ToolingRequetNoteDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	CheckExpiryDateDao expiryDateDao;
	
	@Autowired
	ProductDao productDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	
	public int getIntialValue()
	{
		int returnNoteId = 0;
		returnNoteId = getMaxOriginalId();
		returnNoteId++;
		/*Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(requestid) requestid FROM tooling_request_note ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				returnNoteId = rs.getInt("requestid");
			}
			returnNoteId++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ToolingRequetNoteDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in ToolingRequetNoteDao  getIntialValue : "+ex.getMessage());
			}
		}
		
*/		return returnNoteId;	
	}
	
	public ToolingRequestNote getInitialValue()
	{
		ToolingRequestNote requestNote = new ToolingRequestNote();

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_request_note(requestby, requestDate)"
						+ "values(?,?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				pstmt.setString(1, "");
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(2, date);
				pstmt.executeUpdate();
				
				requestNote.setRequestDate(sdf.format(date));
				
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					requestNote.setRequestId(rs.getInt(1));
				}
				
				msg = "Saved Successfully";
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling request note initial value in getInitialValue table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling request note initial value in getInitialValue : "+ex.getMessage());
			}
		}
		
		return requestNote;
	}
	
	public String addRequestNote(ToolingRequestNote requestNote, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_request_note(requestby, requestDate, branchname, originalid)"
						+ "values(?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				pstmt.setString(1, requestNote.getRequestBy());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			    requestNote.setRequestDate(sdf.format(date));
				pstmt.setDate(2, date);
				pstmt.setString(3, requestNote.getBranchName());
				int originalId = getMaxOriginalId();
				originalId++;
				pstmt.setInt(4,originalId);
				requestNote.setOriginalId(originalId);
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					requestNote.setRequestId(rs.getInt(1));
				}
				
				requestNote.setRevisionNumber(1);
				//updateOriginalId(requestNote);
				msg = addRequestNoteDetails(requestNote);
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("productionRequest.page", null,null));
				history.setDescription(messageSource.getMessage("productionRequest.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling request note initial value in getInitialValue table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling request note initial value in getInitialValue : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String updateRequestNote(ToolingRequestNote requestNote, String dbStatus, int userId)
	{
		System.out.println("updateRequestNote: "+requestNote.getRequestId());
		getRequestVersions(requestNote);
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{

			con = datasource.getConnection();
			pstmt = con.prepareStatement("insert into tooling_request_note(requestby, requestDate, branchname, originalId, versionNumber)"
					+ "values(?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			//,Statement.RETURN_GENERATED_KEYS
			pstmt.setString(1, requestNote.getRequestBy());
			Calendar calendar = Calendar.getInstance();
		    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
		    requestNote.setRequestDate(sdf.format(date));
			pstmt.setDate(2, date);
			pstmt.setString(3, requestNote.getBranchName());
			pstmt.setInt(4, requestNote.getOriginalId());
			pstmt.setInt(5, requestNote.getRevisionNumber());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				requestNote.setRequestId(rs.getInt(1));
			}
		
			msg = addRequestNoteDetails(requestNote);
			msg = "Updated Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("productionRequest.page", null,null));
				history.setDescription(messageSource.getMessage("productionRequest.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling request note detail in tooling_request_note table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling request note detail in tooling_request_note table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String updateRequestNoteOld(ToolingRequestNote requestNote, String dbStatus, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_request_note set requestDate = ?, requestby = ? "
						+ "where requestId = ?");
				
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				requestNote.setRequestDate(sdf.format(date));
				pstmt.setString(2, requestNote.getRequestBy());
				pstmt.setInt(3, requestNote.getRequestId());
				pstmt.executeUpdate();
					
				msg = updateRequestNoteDetails(requestNote);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("productionRequest.page", null,null));
				history.setDescription(messageSource.getMessage("productionRequest.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling request note detail in tooling_request_note table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling request note detail in tooling_request_note table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String addRequestNoteDetails(ToolingRequestNote requestNote)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		String batchQty1;
		String requestQty1;
		String inStock1;
		String underInspection1;
		
		try
		{
				con = datasource.getConnection();
				for(int i = 0; i < requestNote.getProductName().length; i++)
				{
					pstmt = con.prepareStatement("insert into tooling_request_note_detail(requestId, productName, machintype, drawingno, UOM, batchqty, "
							+ " requestqty, instock, underInspection, typeoftool, originalId, revisionNumber,toolingLotNumber,batchnos,batchprod,market"
							+ " ,dustcup,punchSetNo,compForce)"
							+ " values(?,?,?,?,?,?,?,?,?, ?, ?, ?,?,?,?,?,?,?,?)");
	
					pstmt.setInt(1, requestNote.getRequestId());
	
					pstmt.setString(2, TiimUtil.ValidateNull(requestNote.getProductName()[i]).trim());
					pstmt.setString(3, TiimUtil.ValidateNull(requestNote.getMachingType()[i]).trim());
					pstmt.setString(4, TiimUtil.ValidateNull(requestNote.getDrawingNo()[i]).trim());
					pstmt.setString(5, TiimUtil.ValidateNull(requestNote.getUOM()[i]).trim());
					if(requestNote.getBatchQty()[i] != null && !"".equals(requestNote.getBatchQty()[i]))
						{
							batchQty1 = TiimUtil.ValidateNull(requestNote.getBatchQty()[i]).trim();
						}
						else
						{
							batchQty1 = "0";
						}
					pstmt.setString(6, batchQty1);
						if(requestNote.getRequestQty()[i] != null && !"".equals(requestNote.getRequestQty()[i]))
						{
							requestQty1 = TiimUtil.ValidateNull(requestNote.getRequestQty()[i]).trim();
						}
						else
						{
							requestQty1 = "0";
						}
					pstmt.setString(7, requestQty1);
						if(requestNote.getInStock()[i] != null && !"".equals(requestNote.getInStock()[i]))
						{
							inStock1 = TiimUtil.ValidateNull(requestNote.getInStock()[i]).trim();
						}
						else
						{
							inStock1 = "0";
						}
					pstmt.setString(8, inStock1);
						/*if(requestNote.getUnderInspection()[i] != null && !"".equals(requestNote.getUnderInspection()[i]))
						{
							underInspection1 = TiimUtil.ValidateNull(requestNote.getUnderInspection()[i]).trim();
						}
						else
						{
							underInspection1 = "0";
						}*/
						underInspection1 = "0";
					pstmt.setString(9, underInspection1);
					pstmt.setString(10, requestNote.getTypeOfTool()[i]);
					pstmt.setInt(11, requestNote.getOriginalId());
					pstmt.setInt(12, requestNote.getRevisionNumber());
					
					pstmt.setString(13, requestNote.getToolingLotNumber()[i]);
					pstmt.setString(14, requestNote.getBatchnos()[i]);
					
					pstmt.setString(15, requestNote.getBatchProd()[i]);
					if(requestNote.getMarket() != null && requestNote.getMarket().length > 0) {
						pstmt.setString(16, requestNote.getMarket()[i]);
					}
					else {
						pstmt.setString(16, "");
					}
					
					pstmt.setString(17, requestNote.getDustCup());
					pstmt.setString(18, requestNote.getPunchSetNo());
					pstmt.setInt(19, requestNote.getCompForce());
					pstmt.executeUpdate();
				}
				
				msg = "Saved Successfully";
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling request note detail in tooling_request_note table  : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling request note detail in tooling_request_note table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	
	public String updateRequestNoteDetails(ToolingRequestNote requestNote)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		String batchQty1;
		String requestQty1;
		String inStock1;
		String underInspection1;
		try
		{
				con = datasource.getConnection();
				for(int i = 0; i < requestNote.getProductName().length; i++)
				{
					pstmt = con.prepareStatement("update tooling_request_note_detail set requestId = ?, productName = ?, machintype = ?, drawingno = ?, UOM = ?, batchqty = ?, "
							+ " requestqty = ?, instock = ?, underInspection = ?, typeoftool = ? toolingLotNumber = ?, batchnos = ?, batchprod = ?, market = ?, dustcup = ?, "
							+ " punchSetNo = ?, compForce = ? where requestdetailId = ?");

					pstmt.setInt(1, requestNote.getRequestId());
					pstmt.setString(2, requestNote.getProductName()[i]);
					pstmt.setString(3, requestNote.getMachingType()[i]);
					pstmt.setString(4, requestNote.getDrawingNo()[i]);
					pstmt.setString(5, requestNote.getUOM()[i]);
					if(requestNote.getBatchQty()[i] != null && !"".equals(requestNote.getBatchQty()[i]))
						{
							batchQty1 = TiimUtil.ValidateNull(requestNote.getBatchQty()[i]).trim();
						}
						else
						{
							batchQty1 = "0";
						}
					pstmt.setString(6, batchQty1);
						if(requestNote.getRequestQty()[i] != null && !"".equals(requestNote.getRequestQty()[i]))
						{
							requestQty1 = TiimUtil.ValidateNull(requestNote.getRequestQty()[i]).trim();
						}
						else
						{
							requestQty1 = "0";
						}
					pstmt.setString(7, requestQty1);
						if(requestNote.getInStock()[i] != null && !"".equals(requestNote.getInStock()[i]))
						{
							inStock1 = TiimUtil.ValidateNull(requestNote.getInStock()[i]).trim();
						}
						else
						{
							inStock1 = "0";
						}
					pstmt.setString(8, inStock1);
						/*if(requestNote.getUnderInspection()[i] != null && !"".equals(requestNote.getUnderInspection()[i]))
						{
							underInspection1 = TiimUtil.ValidateNull(requestNote.getUnderInspection()[i]).trim();
						}
						else
						{
							underInspection1 = "0";
						}*/
						underInspection1 = "0";
					pstmt.setString(9, underInspection1);
					pstmt.setString(10, requestNote.getTypeOfTool()[i]);
					pstmt.setString(11, requestNote.getToolingLotNumber()[i]);
					pstmt.setString(12, requestNote.getBatchnos()[i]);
					pstmt.setString(13, requestNote.getBatchProd()[i]);
					pstmt.setString(14, "");
					pstmt.setString(15, requestNote.getDustCup());
					pstmt.setString(16, requestNote.getPunchSetNo());
					pstmt.setInt(17, requestNote.getCompForce());
					pstmt.setString(18, requestNote.getRequestDetailId()[i]);
					
					//pstmt.addBatch();
					pstmt.executeUpdate();
				}
				//pstmt.executeBatch();
				//con.commit();
				
				msg = "Updated Successfully";
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling request note detail in tooling_request_note_detail table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling request note detail in tooling_request_note_detail table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String changeToolingRequestNoteStatus(int requestId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from tooling_request_note where requestId = ?");
				pstmt.setInt(1, requestId);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					isActive = rs.getInt("isActive");
					if(isActive == 1)
					{
						isActive=0;
						msg = "Made InActive Successfully";
					}
					else
					{
						isActive=1;
						msg = "Made Active Successfully";	
					}
				}
				
				pstmt = con.prepareStatement("Update tooling_request_note set isActive = ?  where requestId = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, requestId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of request note in tooling_request_note table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connectin in changing the status of tooling request in tooling_request_note table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<ToolingRequestNote> getToolingRequestNote(String requestBy)
	{
		List<ToolingRequestNote> lstRequestNote = new ArrayList<ToolingRequestNote>();

		ToolingRequestNote requestNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(requestBy == null || "".equals(requestBy))
			{
				pstmt = con.prepareStatement("select * from (Select requestId,isreport, requestby, requestDate, originalid from tooling_request_note order by requestId desc)  x group by originalid");
			}else
			{
				pstmt = con.prepareStatement("select * from (Select requestId,isreport, requestby, requestDate, originalid from tooling_request_note where requestby  like '%"+requestBy+"%'  order by requestId desc)  x group by originalid");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				requestNote = new ToolingRequestNote();
				requestNote.setRequestId(rs.getInt("requestId"));
				requestNote.setReportStatus(rs.getInt("isreport"));
				requestNote.setRequestBy(TiimUtil.ValidateNull(rs.getString("requestby")).trim());
				requestNote.setRequestDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("requestDate")))));
				requestNote.setOriginalId(rs.getInt("originalid"));
				lstRequestNote.add(requestNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_request_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_request_note list : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	}
	
	public ToolingRequestNote getToolingRequestNote(int requestId)
	{
		ToolingRequestNote requestNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select requestId, requestby, requestDate,originalid from tooling_request_note where requestId = ?");
			pstmt.setInt(1, requestId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				requestNote = new ToolingRequestNote();
				requestNote.setOriginalId(rs.getInt("originalid"));
				requestNote.setRequestId(rs.getInt("requestId"));
				requestNote.setRequestBy(TiimUtil.ValidateNull(rs.getString("requestby")).trim());
				requestNote.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestDate"))));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_request_note values : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_request_note values : "+ex.getMessage());
			}
		}
		return requestNote;	
	
	}
	
	public List<ToolingRequestNote> getToolingRequestNoteDetails(int requestId)
	{
		List<ToolingRequestNote> lstRequestNote = new ArrayList<ToolingRequestNote>();
		ToolingRequestNote requestNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		String batchQty1;
		String requestQty1;
		String inStock1;
		String underInspection1;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select originalid, requestdetailId, requestId, productName, machintype, drawingno, UOM, batchqty, requestqty, instock, underInspection, typeoftool,toolingLotNumber,"
					+ " batchnos,batchprod,market,dustcup, punchSetNo, compForce "
					+ "  from tooling_request_note_detail"
					+ " where requestId = ?");
			pstmt.setInt(1, requestId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				requestNote = new ToolingRequestNote();
				
				requestNote.setRequestDetailId1(rs.getInt("requestdetailId"));
				requestNote.setRequestId(rs.getInt("originalid"));
				//requestNote.setRequestId(requestId);
				requestNote.setProductName1(TiimUtil.ValidateNull(rs.getString("productName")).trim());
				requestNote.setMachingType1(TiimUtil.ValidateNull(rs.getString("machintype")).trim());
				requestNote.setDrawingNo1(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				requestNote.setUOM1(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				requestNote.setToolingLotNumber1(TiimUtil.ValidateNull(rs.getString("toolingLotNumber")).trim());
				
				requestNote.setBatchnos1(TiimUtil.ValidateNull(rs.getString("batchnos")).trim());
				requestNote.setBatchProd1(TiimUtil.ValidateNull(rs.getString("batchprod")).trim());
				
				requestNote.setMarket1(TiimUtil.ValidateNull(rs.getString("market")).trim());
				requestNote.setDustCup(TiimUtil.ValidateNull(rs.getString("dustCup")).trim());
				requestNote.setPunchSetNo(rs.getString("punchSetNo"));
				requestNote.setCompForce(rs.getInt("compForce"));
				
					if(rs.getInt("batchqty") == 0)
					{
						batchQty1 = "";
					}
					else
					{
						batchQty1 = rs.getInt("batchqty")+"";
					}
				requestNote.setBatchQty1(batchQty1);
					if(rs.getInt("requestqty") == 0)
					{
						requestQty1 = "";
					}
					else
					{
						requestQty1 = rs.getInt("requestqty")+"";
					}
				requestNote.setRequestQty1(requestQty1);
					if(rs.getInt("instock") == 0)
					{
						inStock1 = "";
					}
					else
					{
						inStock1 = rs.getInt("instock")+"";
					}
				requestNote.setInStock1(inStock1);
					if(rs.getInt("underInspection") == 0)
					{
						underInspection1 = "";
					}
					else
					{
						underInspection1 = rs.getInt("underInspection")+"";
					}
				requestNote.setUnderInspection1(underInspection1);
				requestNote.setTypeOfTool1(rs.getString("typeoftool"));
				
				requestNote.setLastInspectionDate1(stockDao.getLastInspectionDate(requestNote.getProductName1(), requestNote.getMachingType1(), requestNote.getDrawingNo1(), requestNote.getTypeOfTool1()));
				lstRequestNote.add(requestNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_request_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_request_note list : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	
	}
	
	public String deleteRequestNote(int originalId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from tooling_request_note_detail where originalId = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from tooling_request_note where originalId = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("productionRequest.page", null,null));
			history.setDescription(messageSource.getMessage("productionRequest.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the detail in tooling_request_note and tooling_request_note_detail table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connectin in delete the detail in tooling_request_note and tooling_request_note_detail table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<ToolingRequestNote> getAutoToolingRequestNote(String requestId)
	{
		List<ToolingRequestNote> lstRequestNote = new ArrayList<ToolingRequestNote>();

		ToolingRequestNote requestNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(requestId != null && !requestId.equals(""))
			{
				pstmt = con.prepareStatement("select * from (Select requestId, requestby, requestDate, originalid from tooling_request_note where originalid like '"+requestId+"%' and requestId not in(SELECT requestNo FROM tooling_issue_note))  x group by originalid");
			}else
			{
				pstmt = con.prepareStatement("select * from (Select requestId, requestby, requestDate, originalid from tooling_request_note where originalid not in(SELECT requestNo FROM tooling_issue_note))  x group by originalid");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				requestNote = new ToolingRequestNote();
				requestNote.setRequestId(rs.getInt("originalid"));
				requestNote.setRequestBy(TiimUtil.ValidateNull(rs.getString("requestby")).trim());
				requestNote.setRequestDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("requestDate")))));
				lstRequestNote.add(requestNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the auto complete tooling_request_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the auto complete tooling_request_note list : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	}
	
	public List<ToolingRequestNote> getAutoToolingRequestNoteId(String approvalFlag)
	{
		List<ToolingRequestNote> lstRequestNote = new ArrayList<ToolingRequestNote>();

		ToolingRequestNote requestNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if("1".equalsIgnoreCase(approvalFlag))
			{
				pstmt = con.prepareStatement("select * from (Select a.requestId requestId, b.productName productName, drawingno, requestby, requestDate, b.machintype machintype, b.typeoftool  typeoftool, a.originalid originalid,"
						+ " punchSetNo, compForce, dustcup from tooling_request_note a, tooling_request_note_detail b "
						+ " where  a.originalid not in(SELECT requestNo FROM tooling_issue_note) and a.requestId = b.requestId order by a.requestId desc)  x group by originalid");
				//pstmt.setInt(1, 1);
			}else
			{
				pstmt = con.prepareStatement("select * from (Select a.requestId requestId, b.productName productName, drawingno, requestby, requestDate, b.machintype machintype, b.typeoftool  typeoftool, a.originalid originalid,"
						+ " punchSetNo, compForce,dustcup from tooling_request_note a, tooling_request_note_detail b "
						+ " where  a.originalid not in(SELECT requestNo FROM tooling_issue_note) and a.requestId = b.requestId order by a.requestId desc)  x group by originalid");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				requestNote = new ToolingRequestNote();
				requestNote.setOriginalId(rs.getInt("originalid"));
				requestNote.setRequestId(rs.getInt("requestId"));
				requestNote.setProductName1(rs.getString("productName"));
				requestNote.setDrawingNo1(rs.getString("drawingno"));
				requestNote.setRequestBy(TiimUtil.ValidateNull(rs.getString("requestby")).trim());
				requestNote.setRequestDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("requestDate")))));
				requestNote.setMachingType1(TiimUtil.ValidateNull(rs.getString("machintype")).trim());
				requestNote.setTypeOfTool1(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				requestNote.setPunchSetNo(rs.getString("punchSetNo"));
				requestNote.setCompForce(rs.getInt("compForce"));
				requestNote.setDustCup(rs.getString("dustcup"));
				/*boolean status = expiryDateDao.isProductExpired(rs.getString("productName"), rs.getString("drawingno"), 
						TiimUtil.ValidateNull(rs.getString("machintype")).trim(), TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());*/
				requestNote.setUploadPath(productDao.getProductUploadedPath(rs.getString("productName"), rs.getString("drawingno"), rs.getString("machintype"), rs.getString("typeoftool")));
				lstRequestNote.add(requestNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the auto complete tooling_request_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the auto complete tooling_request_note list : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	}
	/**
	 * get pending request records
	 * @return
	 */
	public List<ToolingRequestNote> getPendingToolingRequestNote()
	{
		List<ToolingRequestNote> lstRequestNote = new ArrayList<ToolingRequestNote>();

		ToolingRequestNote requestNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select requestId, requestby, requestDate, branchname, originalid from "
					+ " tooling_request_note where approvalflag = 1 and originalid not in(SELECT requestNo FROM tooling_issue_note) order by requestId desc )  x group by originalid");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				requestNote = new ToolingRequestNote();
				requestNote.setRequestId(rs.getInt("requestId"));
				requestNote.setRequestBy(TiimUtil.ValidateNull(rs.getString("requestby")).trim());
				requestNote.setRequestDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("requestDate")))));
				requestNote.setOriginalId(rs.getInt("originalid"));
				lstRequestNote.add(requestNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPendingToolingRequestNote list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getPendingToolingRequestNote : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	}
	
	public void updateOriginalId(ToolingRequestNote requestNote)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_request_note set originalid = ? "
						+ " where requestId = ?");

				pstmt.setInt(1, requestNote.getOriginalId());
				pstmt.setInt(2, requestNote.getRequestId());
				pstmt.executeUpdate();
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingInspection updateOriginalId : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in updating detail in ToolingInspection updateOriginalId : "+ex.getMessage());
			}
		}
	}
	
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private void getRequestVersions(ToolingRequestNote requestNote)
	{
		System.out.println("requestNote.getRequestId(): "+requestNote.getRequestId());
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select originalId, versionNumber from tooling_request_note "
						+ " where requestId = ?");

				pstmt.setInt(1, requestNote.getRequestId());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					requestNote.setOriginalId(rs.getInt("originalId"));
					int revisionNumber = rs.getInt("versionNumber");
					revisionNumber++;
					requestNote.setRevisionNumber(revisionNumber);
				}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingRequestNote updateOriginalId : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in updating detail in ToolingRequestNote updateOriginalId : "+ex.getMessage());
			}
		}
	
	}
	
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private int getMaxOriginalId()
	{
		int originalId = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select max(originalid) originalid from tooling_request_note");
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					originalId = rs.getInt("originalid");
				}
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in tooling_request_note getMaxOriginalId : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in updating detail in tooling_request_note getMaxOriginalId : "+ex.getMessage());
			}
		}
	
		return originalId;
	}
}

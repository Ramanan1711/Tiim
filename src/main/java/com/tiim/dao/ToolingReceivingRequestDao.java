package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.tiim.model.ToolingRequest;
import com.tiim.util.TiimUtil;

@Repository
public class ToolingReceivingRequestDao {

	@Autowired
	DataSource datasource;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	java.util.Date cDate=new java.util.Date();
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ProductDao productDao;
	
	public int getIntialValue()
	{
		int toolingrequestid = 0;
		toolingrequestid = getMaxOriginalId();
		toolingrequestid++;
		/*Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(toolingrequestid) toolingrequestid FROM tooling_receiving_request");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				toolingrequestid = rs.getInt("toolingrequestid");
			}
			toolingrequestid++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ToolingReceivingRequestDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in ToolingReceivingRequestDao  getIntialValue : "+ex.getMessage());
			}
		}*/
		
		return toolingrequestid;	
	}
	
	public String addToolingReceivingInspection(ToolingRequest toolingInspection, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		int toolingrequestid = 0;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_receiving_request(inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid, branchname, originalId)"
						+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				pstmt.setString(1, toolingInspection.getInspectionReportNo());
				java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				pstmt.setTimestamp(2, date);
				pstmt.setInt(3, toolingInspection.getGrnNo());
				pstmt.setString(4, sdfDB.format(sdf.parse(toolingInspection.getGrnDate())));
				pstmt.setString(5, TiimUtil.ValidateNull(toolingInspection.getSupplierCode()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolingInspection.getSupplierName()).trim());
				pstmt.setInt(7, toolingInspection.getToolingReceiptId());
				pstmt.setString(8, TiimUtil.ValidateNull(toolingInspection.getBranchName()).trim());
				int originalId = getMaxOriginalId();
				originalId++;
				toolingInspection.setOriginalId(originalId);
				pstmt.setInt(9, originalId);
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					toolingrequestid = rs.getInt(1);
				}
				toolingInspection.setToolingRequestId(toolingrequestid);
				
				//updateOriginalId(toolingInspection);
				toolingInspection.setRevisionNumber(1);
				addToolingReceivingInspectionDetail(toolingInspection);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("inspectionRequest.page", null,null));
				history.setDescription(messageSource.getMessage("inspectionRequest.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				
				msg = "Saved Successfully";
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receiving_request table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_request table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public void updateOriginalId(ToolingRequest toolingInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receiving_request set originalId = ? "
						+ " where toolingrequestid = ?");

				pstmt.setInt(1, toolingInspection.getOriginalId());
				pstmt.setInt(2, toolingInspection.getToolingRequestId());
				pstmt.executeUpdate();
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in updateOriginalId : "+ex.getMessage());
			}
		}
	
	}
	
	@SuppressWarnings("resource")
	public String addToolingReceivingInspectionDetail(ToolingRequest toolingInspection)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< toolingInspection.getToolingProductId().length;i++)
			{
				pstmt1 = con.prepareStatement("select a.toolingreceiptid, productname, drawingno,  receivedquantity, toolinglotnumber,"
						+ "typeoftool, machinetype, b.po po, b.grndate grndate, uom, punchSetNo, compForce "
											+ "from tooling_receipt_product a, tooling_receipt_note b "
											+" where a.toolingProductId = ? and a.toolingreceiptid = b.toolingreceiptid;");
				pstmt1.setInt(1, toolingInspection.getToolingProductId()[i]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("insert into tooling_receiving_request_details(toolingname, ponumber, podate, drawingno, lotnumber, receivedquantity, UOM, remarks, "
								+ " toolingrequestid, toolingProductId, typeoftool, machinetype, originalId, revisionnumber, punchSetNo, compForce)"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("po")));
						pstmt.setDate(3, rs.getDate("grndate"));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(5, rs.getString("toolinglotnumber"));
						pstmt.setLong(6, rs.getLong("receivedquantity"));
						pstmt.setString(7, TiimUtil.ValidateNull(rs.getString("UOM")));
						
						toolingInspection.setToolingname(rs.getString("productname"));
						toolingInspection.setDrawingNo(rs.getString("drawingno"));
						toolingInspection.setLotNumber(rs.getString("toolinglotnumber"));
						toolingInspection.setMachineType(rs.getString("machinetype"));
						//pstmt.setString(8, TiimUtil.ValidateNull(toolingInspection.getInspectionStatus()[i]));
						
						if(toolingInspection.getRemarks() != null && toolingInspection.getRemarks().length > 0 && toolingInspection.getRemarks().length == (i+1)  )
								pstmt.setString(8, TiimUtil.ValidateNull(toolingInspection.getRemarks()[i]));
						else
							pstmt.setString(8, "");
						pstmt.setInt(9, toolingInspection.getToolingRequestId());
						pstmt.setInt(10, toolingInspection.getToolingProductId()[i]);
						pstmt.setString(11, rs.getString("typeoftool"));
						pstmt.setString(12, rs.getString("machinetype"));
						pstmt.setInt(13, toolingInspection.getOriginalId());
						pstmt.setInt(14, toolingInspection.getRevisionNumber());
						pstmt.setString(15, rs.getString("punchSetNo"));
						pstmt.setInt(16, rs.getInt("compForce"));
						pstmt.executeUpdate();
						/*if(toolingInspection.getInspectionStatus()[i].equalsIgnoreCase("Accepted"))
						{
							storeTransaction(toolingInspection, i);
						}*/
					}catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing the data to tooling_receiving_request_details: "+ex.getMessage());
					}finally
					{
						try
						{
							if(pstmt != null)
							{
								pstmt.close();
							}
						}catch(Exception ex)
						{
							System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_request_details table : "+ex.getMessage());
						}
					}
				}
				
				msg = "Saved Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receiving_request_details table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt1 != null)
				{
					pstmt1.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_request_details table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String updateToolingInspectionOld(ToolingRequest toolingInspection, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receiving_request set inspectionreportno = ?, inspectionreportdate = ?, grnno = ?, grndate = ?, suppliercode = ?,"
						+ " suppliername = ?, toolingreceiptid = ? "
						+ " where toolingrequestid = ?");

				pstmt.setString(1, toolingInspection.getInspectionReportNo());
				
				java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				pstmt.setTimestamp(2, date);
				
				pstmt.setInt(3, toolingInspection.getGrnNo());
				pstmt.setString(4, sdfDB.format(sdf.parse(toolingInspection.getGrnDate())));
				pstmt.setString(5, TiimUtil.ValidateNull(toolingInspection.getSupplierCode()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolingInspection.getSupplierName()).trim());
				pstmt.setInt(7, toolingInspection.getToolingReceiptId());
				pstmt.setInt(8, toolingInspection.getToolingRequestId());
				pstmt.executeUpdate();
				
				updateToolingInspectionDetail(toolingInspection);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("inspectionRequest.page", null,null));
				history.setDescription(messageSource.getMessage("inspectionRequest.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				msg = "Updated Successfully";
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in tooling_receiving_request table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in tooling_receiving_request table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	public String updateToolingInspection(ToolingRequest toolingInspection, int userId)
	{
		getRequestVersions(toolingInspection);
		
		//toolingInspection.setOriginalId(originalId);
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		int toolingrequestid = 0;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("insert into tooling_receiving_request(inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid, branchname, originalId, revisionnumber)"
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?,?)",Statement.RETURN_GENERATED_KEYS);
			//,Statement.RETURN_GENERATED_KEYS
			int reportNo = Integer.parseInt(toolingInspection.getInspectionReportNo());
			reportNo++;
			pstmt.setString(1, reportNo+"");
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			pstmt.setTimestamp(2, date);
			pstmt.setInt(3, toolingInspection.getGrnNo());
			pstmt.setString(4, sdfDB.format(sdf.parse(toolingInspection.getGrnDate())));
			pstmt.setString(5, TiimUtil.ValidateNull(toolingInspection.getSupplierCode()).trim());
			pstmt.setString(6, TiimUtil.ValidateNull(toolingInspection.getSupplierName()).trim());
			pstmt.setInt(7, toolingInspection.getToolingReceiptId());
			pstmt.setString(8, TiimUtil.ValidateNull(toolingInspection.getBranchName()).trim());
			pstmt.setInt(9, toolingInspection.getOriginalId());
			pstmt.setInt(10, toolingInspection.getRevisionNumber());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				toolingrequestid = rs.getInt(1);
			}
			toolingInspection.setToolingRequestId(toolingrequestid);
			
			addToolingReceivingInspectionDetail(toolingInspection);
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("inspectionRequest.page", null,null));
			history.setDescription(messageSource.getMessage("inspectionRequest.update", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
			msg = "Saved Successfully";
		
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in tooling_receiving_request table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in tooling_receiving_request table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	public String updateToolingInspectionDetail(ToolingRequest toolingInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< toolingInspection.getToolingProductId().length;i++)
			{
				pstmt1 = con.prepareStatement("select a.toolingreceiptid, productname, drawingno,  receivedquantity, toolinglotnumber, b.po po, b.grndate grndate,"
						+ " typeoftool, machinetype, uom, punchSetNo, compForce  "
											+ " from tooling_receipt_product a, tooling_receipt_note b "
											+" where a.toolingProductId = ? and a.toolingreceiptid = b.toolingreceiptid;");
				pstmt1.setInt(1, toolingInspection.getToolingProductId()[i]);
				ResultSet rs = pstmt1.executeQuery();
				if(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("update tooling_receiving_request_details set toolingname = ?, ponumber = ?, podate = ?, drawingno = ?, "
								+ " lotnumber = ?, receivedquantity = ?, UOM = ?, remarks = ?, toolingrequestid = ?, typeoftool = ?, machinetype  = ?, punchSetNo=?, compForce=? "
								+ " where toolingRequestdetailid = ?");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("po")));
						pstmt.setDate(3, rs.getDate("grndate"));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(5, rs.getString("toolinglotnumber"));
						pstmt.setLong(6, rs.getLong("receivedquantity"));
						pstmt.setString(7, TiimUtil.ValidateNull(rs.getString("uom")));
						//pstmt.setString(8, TiimUtil.ValidateNull(toolingInspection.getInspectionStatus()[i]));
						System.out.println( (toolingInspection.getRemarks().length+1) +","+ i );
						if(toolingInspection.getRemarks() != null && toolingInspection.getRemarks().length > 0 && toolingInspection.getRemarks().length == (i+1) )
							pstmt.setString(8, TiimUtil.ValidateNull(toolingInspection.getRemarks()[i]));
						else
							pstmt.setString(8, "");
						pstmt.setInt(9, toolingInspection.getToolingRequestId());
						pstmt.setString(10, rs.getString("typeoftool"));
						pstmt.setString(11, rs.getString("machinetype"));
						pstmt.setInt(12, toolingInspection.getToolingRequestDetailId()[i]);
						pstmt.setString(13, rs.getString("punchSetNo"));
						pstmt.setInt(14,rs.getInt("compForce"));
						pstmt.executeUpdate();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when updating the data to tooling_receiving_request_details: "+ex.getMessage());
					}finally
					{
						try
						{
							if(pstmt != null)
							{
								pstmt.close();
							}
						}catch(Exception ex)
						{
							System.out.println("Exception when closing the connection in updating detail in tooling_receiving_request_details table : "+ex.getMessage());
						}
					}
				}
				msg = "Updated Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receiving_request_details table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt1 != null)
				{
					pstmt1.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_request_details table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String deleteReceiptInspection(int toolingrequestid, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from tooling_receiving_request_details where originalId = ?");
			
			pstmt.setInt(1, toolingrequestid);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from tooling_receiving_request where originalId = ?");
			pstmt.setInt(1, toolingrequestid);
			pstmt.executeUpdate();
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("inspectionRequest.page", null,null));
			history.setDescription(messageSource.getMessage("inspectionRequest.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
			msg = "Deleted Successfully";
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the detail in tooling_receipt_product and tooling_receipt_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the detail in tooling_receipt_product and tooling_receipt_note table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<ToolingRequest> getToolingInspection(String searchInspection, String approvalFlag)
	{
		List<ToolingRequest> lstToolingInspection = new ArrayList<ToolingRequest>();

		ToolingRequest objInspection = new ToolingRequest();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(searchInspection != null && !"".equals(searchInspection))
			{
			    pstmt = con.prepareStatement("Select toolingrequestid,isreport, inspectionreportno, inspectionreportdate, originalid, grnno, grndate, suppliercode, suppliername, toolingreceiptid, approvalflag "
			    		+ " from tooling_receiving_request Where inspectionreportno like '%"+searchInspection+"%' order by inspectionreportno desc");
			}
			else
			{
				pstmt = con.prepareStatement("select * from (Select toolingrequestid, isreport, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, "
						+ "suppliername, toolingreceiptid,originalid, approvalflag from tooling_receiving_request order by inspectionreportno desc)"
						+ " x group by originalid");
			}
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingRequest();
				objInspection.setToolingRequestId(rs.getInt("toolingrequestid"));
				objInspection.setInspectionReportNo(rs.getString("toolingrequestid"));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setGrnNo(rs.getInt("grnno"));
				objInspection.setGrnDate( sdf.format(sdfDB.parse(rs.getString("grndate"))));
				objInspection.setSupplierCode(rs.getString("suppliercode"));
				objInspection.setSupplierName(rs.getString("suppliername"));
				objInspection.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				objInspection.setOriginalId(rs.getInt("originalid"));
				objInspection.setReportStatus(rs.getInt("isreport"));
				if("1".equals(approvalFlag))
				{
					if(rs.getInt("approvalflag") == 1)
					{
						objInspection.setRemarks1("Approved");
					}else if(rs.getInt("approvalflag") == 3)
					{
						objInspection.setRemarks1("Rejected");
					}else
					{
						objInspection.setRemarks1("Yet to Approve");
					}
				}else
				{
					objInspection.setRemarks1("NILL");
				}
				lstToolingInspection.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getToolingInspection : "+ex.getMessage());
			}
		}
	
		return lstToolingInspection;
	}
	
	public ToolingRequest editToolingInspection(int toolingrequestid)
	{
		ToolingRequest objInspection = new ToolingRequest();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid from tooling_receiving_request where toolingrequestid = ?");
			pstmt.setInt(1, toolingrequestid);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingRequest();
				objInspection.setToolingRequestId(rs.getInt("toolingrequestid"));
				objInspection.setInspectionReportNo(TiimUtil.ValidateNull(rs.getString("inspectionreportno")));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setGrnNo(rs.getInt("grnno"));
				objInspection.setGrnDate(TiimUtil.ValidateNull( sdf.format(sdfDB.parse(rs.getString("grndate")))));
				objInspection.setSupplierCode(TiimUtil.ValidateNull(rs.getString("suppliercode")));
				objInspection.setSupplierName(TiimUtil.ValidateNull(rs.getString("suppliername")));
				objInspection.setToolingReceiptId(rs.getInt("toolingreceiptid"));
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when getting the editToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  editToolingInspection : "+ex.getMessage());
			}
		}
	
		return objInspection;
	}
	
	public List<ToolingRequest> getToolingInspectionDetails(int toolingrequestid)
	{
		List<ToolingRequest> lstToolingInspection = new ArrayList<ToolingRequest>();

		ToolingRequest objInspection = new ToolingRequest();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingname, ponumber, podate, drawingno, lotnumber, receivedquantity, UOM, remarks, "
					+ " toolingrequestid, toolingRequestdetailid, toolingProductId, typeoftool, machinetype, punchSetNo, compForce from tooling_receiving_request_details where toolingrequestid = ?");
			pstmt.setInt(1, toolingrequestid);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingRequest();
				objInspection.setToolingname(rs.getString("toolingname"));
				objInspection.setPoNumber(rs.getString("ponumber"));
				objInspection.setPoDate(sdf.format(sdfDB.parse(rs.getString("podate"))));
				objInspection.setDrawingNo(rs.getString("drawingno"));
				objInspection.setLotNumber(rs.getString("lotnumber"));				
				objInspection.setReceivedQuantity(rs.getLong("receivedquantity"));
				objInspection.setUom(rs.getString("UOM"));
				//objInspection.setInspectionStatus1(rs.getString("inspectionstatus"));
				objInspection.setRemarks1(rs.getString("remarks"));
				objInspection.setToolingRequestId(rs.getInt("toolingrequestid"));
				objInspection.setToolingRequestDetailId1(rs.getInt("toolingRequestdetailid"));
				objInspection.setToolingProductId1(rs.getInt("toolingProductId"));
				objInspection.setTypeOfTool(rs.getString("typeoftool"));
				objInspection.setMachineType(rs.getString("machinetype"));
				objInspection.setPunchSetNo(rs.getString("punchSetNo"));
				objInspection.setCompForce(rs.getInt("compForce"));
				lstToolingInspection.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getToolingInspection : "+ex.getMessage());
			}
		}
	
		return lstToolingInspection;
	}
	
	public List<ToolingRequest> autoToolingReceivingRequest(String toolingrequestid, String approvalFlag)
	{
		List<ToolingRequest> lstRequest = new ArrayList<ToolingRequest>();
		ToolingRequest objInspection = new ToolingRequest();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if("1".equalsIgnoreCase(approvalFlag))
			{
				pstmt = con.prepareStatement("Select toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid from tooling_receiving_request where toolingrequestid like '"+toolingrequestid+"%'"
						+ " and approvalflag = ? and toolingrequestid not in (select requestId from tooling_receiving_inspection )");
				pstmt.setInt(1, 1);
			}else
			{
				pstmt = con.prepareStatement("Select toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid from tooling_receiving_request where toolingrequestid like '"+toolingrequestid+"%'"
						+ "  and toolingrequestid not in (select requestId from tooling_receiving_inspection )");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingRequest();
				objInspection.setToolingRequestId(rs.getInt("toolingrequestid"));
				objInspection.setInspectionReportNo(TiimUtil.ValidateNull(rs.getString("inspectionreportno")));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setGrnNo(rs.getInt("grnno"));
				objInspection.setGrnDate(TiimUtil.ValidateNull( sdf.format(sdfDB.parse(rs.getString("grndate")))));
				objInspection.setSupplierCode(TiimUtil.ValidateNull(rs.getString("suppliercode")));
				objInspection.setSupplierName(TiimUtil.ValidateNull(rs.getString("suppliername")));
				objInspection.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				lstRequest.add(objInspection);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the autoToolingReceivingRequest : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  autoToolingReceivingRequest : "+ex.getMessage());
			}
		}
	
		return lstRequest;
	}	
	
	public List<ToolingRequest> autoToolingReceivingRequestDrawingNo(String drawingNo)
	{
		List<ToolingRequest> lstRequest = new ArrayList<ToolingRequest>();
		ToolingRequest objInspection = new ToolingRequest();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select a.toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid, drawingno, punchSetNo, compForce"
					+ " from tooling_receiving_request a, tooling_receiving_request_details b  where drawingno like '"+drawingNo+"%' and "
							+ " a.toolingrequestid = b.toolingrequestid  and a.toolingrequestid not in (select requestId from tooling_receiving_inspection )");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingRequest();
				objInspection.setToolingRequestId(rs.getInt("toolingrequestid"));
				objInspection.setInspectionReportNo(TiimUtil.ValidateNull(rs.getString("inspectionreportno")));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setGrnNo(rs.getInt("grnno"));
				objInspection.setGrnDate(TiimUtil.ValidateNull( sdf.format(sdfDB.parse(rs.getString("grndate")))));
				objInspection.setSupplierCode(TiimUtil.ValidateNull(rs.getString("suppliercode")));
				objInspection.setSupplierName(TiimUtil.ValidateNull(rs.getString("suppliername")));
				objInspection.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				objInspection.setDrawingNo(rs.getString("drawingno"));
				objInspection.setPunchSetNo(rs.getString("punchSetNo"));
				objInspection.setCompForce(rs.getInt("compForce"));
				lstRequest.add(objInspection);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the autoToolingReceivingRequest : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  autoToolingReceivingRequest : "+ex.getMessage());
			}
		}
	
		return lstRequest;
	}	
	
	public List<ToolingRequest> pendingToolingReceivingRequest(String approvalFlag)
	{
		List<ToolingRequest> lstRequest = new ArrayList<ToolingRequest>();
		ToolingRequest objInspection = new ToolingRequest();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			/*pstmt = con.prepareStatement("Select toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid from "
					+ " tooling_receiving_request where approvalflag = ? and toolingrequestid not in (select requestId from tooling_receiving_inspection ) order by inspectionreportno desc");*/
			if("1".equalsIgnoreCase(approvalFlag))
			{
				pstmt = con.prepareStatement("select * from (Select a.toolingrequestid toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid,"
				 		+ " b.toolingname toolingname, b.drawingno drawingno, b.machinetype machinetype, a.originalId originalId, b.typeoftool typeoftool, punchSetNo, compForce"
				 		+ " from tooling_receiving_request a, tooling_receiving_request_details b "
						+ " where a.toolingrequestid = b.toolingrequestid and a.approvalflag = ? and a.originalId not in (select requestId from tooling_receiving_inspection)"
						+ " order by a.toolingrequestid desc ) x group by originalId ");
				pstmt.setInt(1, 1);
			}else
			{
				pstmt = con.prepareStatement("select * from (Select a.toolingrequestid toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid,"
				 		+ " b.toolingname toolingname, b.drawingno drawingno, b.machinetype machinetype, a.originalId originalId, b.typeoftool typeoftool, punchSetNo, compForce from tooling_receiving_request a, tooling_receiving_request_details b "
						+ " where a.toolingrequestid = b.toolingrequestid and a.originalId not in (select requestId from tooling_receiving_inspection)"
						+ " order by a.toolingrequestid desc ) x group by originalId ");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingRequest();
				objInspection.setOriginalId(rs.getInt("originalId"));
				objInspection.setToolingRequestId(rs.getInt("toolingrequestid"));
				objInspection.setInspectionReportNo(TiimUtil.ValidateNull(rs.getString("inspectionreportno")));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setGrnNo(rs.getInt("grnno"));
				objInspection.setPunchSetNo(rs.getString("punchSetNo"));
				objInspection.setCompForce(rs.getInt("compForce"));
				objInspection.setGrnDate(TiimUtil.ValidateNull( sdf.format(sdfDB.parse(rs.getString("grndate")))));
				objInspection.setSupplierCode(TiimUtil.ValidateNull(rs.getString("suppliercode")));
				objInspection.setSupplierName(TiimUtil.ValidateNull(rs.getString("suppliername")));
				objInspection.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				lstRequest.add(objInspection);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the autoToolingReceivingRequest : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  autoToolingReceivingRequest : "+ex.getMessage());
			}
		}
	
		return lstRequest;
	}
	
	/*private void storeTransaction(ToolingRequest toolingInspection, int index)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			{
				pstmt1 = con.prepareStatement("select a.toolingreceiptid, productname, drawingno,  receivedquantity, toolinglotnumber, b.po po, b.grndate grndate, uom "
											+ "from tooling_receipt_product a, tooling_receipt_note b "
											+" where a.toolingProductId = ? and a.toolingreceiptid = b.toolingreceiptid;");
				pstmt1.setInt(1, toolingInspection.getToolingProductId()[index]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("insert into transaction(productname, drawingno, toolinglotnumber, UOM, stockqty, "
								+ " transactiondate )"
								+ "values(?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setLong(3, rs.getLong("toolinglotnumber"));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("uom")));
						pstmt.setInt(5, rs.getInt("receivedquantity"));
					
						pstmt.setString(6, sdf.format(cDate));
						
						pstmt.executeUpdate();
					}catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing the data to transaction: "+ex.getMessage());
					}finally
					{
						try
						{
							if(pstmt != null)
							{
								pstmt.close();
							}
						}catch(Exception ex)
						{
							System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_request_details table : "+ex.getMessage());
						}
					}
				}
				
				msg = "Saved Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receiving_request_details table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt1 != null)
				{
					pstmt1.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_request_details table : "+ex.getMessage());
			}
		}
	}*/
	
	public List<ToolingRequest> toolingReceivingRequestDetail(String drawingNumber, String approvalFlag)
	{
		List<ToolingRequest> lstRequest = new ArrayList<ToolingRequest>();
		ToolingRequest objInspection = new ToolingRequest();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if("1".equalsIgnoreCase(approvalFlag))
			{
				if(drawingNumber == null || "".equals(drawingNumber))
				{
				  pstmt = con.prepareStatement("select * from (Select a.toolingrequestid toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid,"
				 		+ " b.toolingname toolingname, b.drawingno drawingno, b.machinetype machinetype, a.originalId originalId, b.typeoftool typeoftool, punchSetNo, compForce"
				 		+ " from tooling_receiving_request a, tooling_receiving_request_details b "
						+ " where a.toolingrequestid = b.toolingrequestid and a.approvalflag = ? and a.originalId not in (select requestId from tooling_receiving_inspection)"
						+ " order by a.toolingrequestid desc ) x group by originalId ");
				  pstmt.setInt(1, 1);
				}else
				{
					pstmt = con.prepareStatement("select * from (Select a.toolingrequestid toolingrequestid , inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid,"
					 		+ " b.toolingname toolingname, b.drawingno drawingno, b.machinetype machinetype,a.originalId originalId, b.typeoftool typeoftool, punchSetNo, compForce"
					 		+ " from tooling_receiving_request a, tooling_receiving_request_details b "
							+ " where a.toolingrequestid = b.toolingrequestid and drawingno = ? and a.approvalflag = ? and a.originalId not in (select requestId from tooling_receiving_inspection)"
							+ " order by a.toolingrequestid desc ) x group by originalId ");
					pstmt.setString(1, drawingNumber);
					pstmt.setInt(2, 1);
				}
			}else
			{
				if(drawingNumber == null || "".equals(drawingNumber))
				{
				  pstmt = con.prepareStatement("select * from (Select a.toolingrequestid toolingrequestid, inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid,"
				 		+ " b.toolingname toolingname, b.drawingno drawingno, b.machinetype machinetype, a.originalId originalId, b.typeoftool typeoftool, punchSetNo, compForce from tooling_receiving_request a, tooling_receiving_request_details b "
						+ " where a.toolingrequestid = b.toolingrequestid and a.originalId not in (select requestId from tooling_receiving_inspection)"
						+ " order by a.toolingrequestid desc ) x group by originalId ");
				 
				}else
				{
					pstmt = con.prepareStatement("select * from (Select a.toolingrequestid toolingrequestid , inspectionreportno, inspectionreportdate, grnno, grndate, suppliercode, suppliername, toolingreceiptid,"
					 		+ " b.toolingname toolingname, b.drawingno drawingno, b.machinetype machinetype,a.originalId originalId, b.typeoftool typeoftool, punchSetNo, compForce from tooling_receiving_request a, tooling_receiving_request_details b "
							+ " where a.toolingrequestid = b.toolingrequestid and drawingno = ? and a.originalId not in (select requestId from tooling_receiving_inspection)"
							+ " order by a.toolingrequestid desc ) x group by originalId ");
					pstmt.setString(1, drawingNumber);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingRequest();
				objInspection.setToolingRequestId(rs.getInt("toolingrequestid"));
				objInspection.setOriginalId(rs.getInt("originalId"));
				objInspection.setInspectionReportNo(TiimUtil.ValidateNull(rs.getString("inspectionreportno")));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setGrnNo(rs.getInt("grnno"));
				objInspection.setGrnDate(TiimUtil.ValidateNull( sdf.format(sdfDB.parse(rs.getString("grndate")))));
				objInspection.setSupplierCode(TiimUtil.ValidateNull(rs.getString("suppliercode")));
				objInspection.setSupplierName(TiimUtil.ValidateNull(rs.getString("suppliername")));
				objInspection.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				objInspection.setToolingname(rs.getString("toolingname"));
				objInspection.setDrawingNo(rs.getString("drawingno"));
				objInspection.setMachineType(rs.getString("machinetype"));
				objInspection.setTypeOfTool(rs.getString("typeoftool"));
				objInspection.setPunchSetNo(rs.getString("punchSetNo"));
				objInspection.setCompForce(rs.getInt("compForce"));
				objInspection.setUploadPath(productDao.getProductUploadedPath(rs.getString("toolingname"), rs.getString("drawingno"), rs.getString("machinetype"), rs.getString("typeoftool")));
				lstRequest.add(objInspection);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the toolingReceivingRequestDetail : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  autoToolingReceivingRequest : "+ex.getMessage());
			}
		}
	
		return lstRequest;
	}	
	
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private void getRequestVersions(ToolingRequest toolingRequest)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select originalId, revisionnumber from tooling_receiving_request "
						+ " where toolingrequestid = ?");

				pstmt.setInt(1, toolingRequest.getToolingRequestId());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					toolingRequest.setOriginalId(rs.getInt("originalId"));
					int revisionNumber = rs.getInt("revisionnumber");
					revisionNumber++;
					toolingRequest.setRevisionNumber(revisionNumber);
				}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in updateOriginalId : "+ex.getMessage());
			}
		}
	
	}
	
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private int getRevisionNumber(int requestId)
	{
		int revisionNumber = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select revisionnumber from tooling_receiving_request"
						+ " where toolingrequestid = ?");

				pstmt.setInt(1, requestId);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					revisionNumber = rs.getInt("originalId");
				}
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in getRevisionNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in getRevisionNumber : "+ex.getMessage());
			}
		}
	
		return revisionNumber;
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
				pstmt = con.prepareStatement("select max(originalid) originalid from tooling_receiving_request");
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					originalId = rs.getInt("originalid");
				}
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in getMaxOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in getMaxOriginalId : "+ex.getMessage());
			}
		}
	
		return originalId;
	}
}

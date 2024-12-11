package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.tiim.model.StockTransfer;
import com.tiim.model.ToolingRequestNote;
import com.tiim.util.TiimUtil;

@Repository
public class StockTransferDao {
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	
	public int getIntialValue()
	{
		int returnNoteId = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(stockTransferId) stockTransferId FROM stock_transfer_request ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				returnNoteId = rs.getInt("stockTransferId");
			}
			returnNoteId++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in StockTransferDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in StockTransferDao  getIntialValue : "+ex.getMessage());
			}
		}
		
		return returnNoteId;	
	}
	
	public String addstockTransfer(StockTransfer stockTransfer, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into stock_transfer_request(stocktransferdate, transferFrom, transferTo) "
						+ "values(?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			    stockTransfer.setStockDate(sdf.format(date));
				pstmt.setDate(1, date);
				pstmt.setString(2, stockTransfer.getFromBranch());
				pstmt.setString(3, stockTransfer.getToBranch());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					stockTransfer.setStockTransferId(rs.getInt(1));
				}
				msg = addStockTransferDetail(stockTransfer);
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("stockTransferRequest.page", null,null));
				history.setDescription(messageSource.getMessage("stockTransferRequest.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the addstockTransfer table : "+ex.getMessage());
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

	private String addStockTransferDetail(StockTransfer stockTransfer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< stockTransfer.getToolinglotnumber().length;i++)
			{
				pstmt1 = con.prepareStatement("select toolinglotnumber, typeoftool, productname, drawingno, UOM, machinetype, stockqty from stock where toolinglotnumber = ?");
				pstmt1.setString(1, stockTransfer.getToolinglotnumber()[i]);
				ResultSet rs = pstmt1.executeQuery();
				if(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("insert into stock_transfer_request_detail (toolinglotnumber, productname, drawingno, machinetype, typeoftool, uom, stockqty,transferqty,stockTransferId) "
								+ " values(?,?,?,?,?,?,?,?,?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, rs.getString("toolinglotnumber"));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("machinetype")));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setString(6, TiimUtil.ValidateNull(rs.getString("UOM")));
						pstmt.setLong(7, rs.getLong("stockqty"));
						pstmt.setLong(8, stockTransfer.getTransferQty()[i]);
						pstmt.setInt(9, stockTransfer.getStockTransferId());
						
						pstmt.executeUpdate();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when updating the data to stock_transfer_request_detail: "+ex.getMessage());
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
							System.out.println("Exception when closing the connection in updating detail in stock_transfer_request_detail table : "+ex.getMessage());
						}
					}
				}
				msg = "Updated Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in stock_transfer_request_detail table : "+ex.getMessage());
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
	
	public String updateStockTransfer(StockTransfer stockTransfer, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update stock_transfer_request set stocktransferdate = ?, transferFrom = ?, transferTo = ? where stockTransferId = ?");
				
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			    stockTransfer.setStockDate(sdf.format(date));
				pstmt.setDate(1, date);
				pstmt.setString(2, stockTransfer.getFromBranch());
				pstmt.setString(3, stockTransfer.getToBranch());
				pstmt.setInt(4, stockTransfer.getStockTransferId());
				pstmt.executeUpdate();
				
				msg = updateStockTransferDetail(stockTransfer);
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("stockTransferRequest.page", null,null));
				history.setDescription(messageSource.getMessage("stockTransferRequest.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the updateStockTransfer table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling request note initial value in updateStockTransfer : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	private String updateStockTransferDetail(StockTransfer stockTransfer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< stockTransfer.getToolinglotnumber().length;i++)
			{
				pstmt1 = con.prepareStatement("select toolinglotnumber, typeoftool, productname, drawingno, UOM, machinetype, stockqty from stock where toolinglotnumber = ?");
				pstmt1.setString(1, stockTransfer.getToolinglotnumber()[i]);
				ResultSet rs = pstmt1.executeQuery();
				if(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("update stock_transfer_request_detail set toolinglotnumber = ?, productname = ?, drawingno = ?, machinetype = ?, typeoftool = ?, "
								+ " uom = ?, stockqty = ?, transferqty = ? where stockTransferDetailId = ?");
								
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, rs.getString("toolinglotnumber"));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("machinetype")));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setString(6, TiimUtil.ValidateNull(rs.getString("UOM")));
						pstmt.setLong(7, rs.getLong("stockqty"));
						pstmt.setLong(8, stockTransfer.getTransferQty()[i]);
						pstmt.setInt(9, stockTransfer.getStockTransferDetailId()[i]);
						
						pstmt.executeUpdate();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when updateStockTransferDetail the data to stock_transfer_request_detail: "+ex.getMessage());
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
							System.out.println("Exception when closing the connection in updateStockTransferDetail detail in stock_transfer_request_detail table : "+ex.getMessage());
						}
					}
				}
				msg = "Updated Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note updateStockTransferDetail in stock_transfer_request_detail table : "+ex.getMessage());
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
	
	public List<StockTransfer> getStockTransfer(String transferId)
	{
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();

		StockTransfer stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(transferId == null || "".equals(transferId))
			{
				pstmt = con.prepareStatement("select stockTransferId, stocktransferdate, transferFrom, transferTo from stock_transfer_request order by stockTransferId desc");
			}else
			{
				pstmt = con.prepareStatement("select stockTransferId, stocktransferdate, transferFrom, transferTo from stock_transfer_request where stockTransferId  like '%"+transferId+"%'  order by stockTransferId desc");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransfer();
				stockTransfer.setStockTransferId(rs.getInt("stockTransferId"));
				stockTransfer.setFromBranch(TiimUtil.ValidateNull(rs.getString("transferFrom")).trim());
				stockTransfer.setToBranch(TiimUtil.ValidateNull(rs.getString("transferTo")).trim());
				stockTransfer.setStockDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("stocktransferdate")))));
				lstStockTransfer.add(stockTransfer);
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
		return lstStockTransfer;	
	}
	
	public StockTransfer getStockTransfer(int transferId)
	{
		StockTransfer stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT stocktransferdate, transferFrom, transferTo FROM stock_transfer_request where stockTransferId = ?");
			pstmt.setInt(1, transferId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransfer();
				stockTransfer.setStockTransferId(transferId);
				stockTransfer.setStockDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("stocktransferdate")))));
				stockTransfer.setFromBranch(TiimUtil.ValidateNull(rs.getString("transferFrom")).trim());
				stockTransfer.setToBranch(rs.getString("transferTo"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the stock_transfer_request values : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the stock_transfer_request values : "+ex.getMessage());
			}
		}
		return stockTransfer;	
	
	}
	
	public List<StockTransfer> getAutoStockTransfer(String transferId)
	{
		List<StockTransfer> lstStockTransfers = new ArrayList<StockTransfer>();

		StockTransfer stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select toolinglotnumber, productname, drawingno, machinetype, typeoftool, uom, stockqty,transferqty,stockTransferId, stockTransferDetailId from stock_transfer_request_detail where stockTransferId  = ?");
			pstmt.setString(1, transferId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransfer();
				stockTransfer.setStockTransferId(rs.getInt("stockTransferId"));
				stockTransfer.setToolinglotnumber1(rs.getString("toolinglotnumber"));
				stockTransfer.setProductName(rs.getString("productname"));
				stockTransfer.setDrawingNo(rs.getString("drawingno"));
				stockTransfer.setMachineType(rs.getString("machinetype"));
				stockTransfer.setTypeOfTool(rs.getString("typeoftool"));
				stockTransfer.setUom(rs.getString("uom"));
				stockTransfer.setStockQty(rs.getLong("stockqty"));
				stockTransfer.setTransferQty1(rs.getLong("transferqty"));
				stockTransfer.setStockTransferDetailId1(rs.getInt("stockTransferDetailId"));
				lstStockTransfers.add(stockTransfer);
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
		return lstStockTransfers;	
	}
	
	public List<StockTransfer> getAutoStockTransferDetail(String transferId)
	{
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();

		StockTransfer stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(transferId == null || "".equals(transferId))
			{
				pstmt = con.prepareStatement("select stockTransferId, stocktransferdate, transferFrom, transferTo from stock_transfer_request order by stockTransferId desc");
			}else
			{
				pstmt = con.prepareStatement("select stockTransferId, stocktransferdate, transferFrom, transferTo from "
						+ " stock_transfer_request where stockTransferId  like '"+transferId+"%' and stockTransferId not in(SELECT stockTransferId FROM stock_transfer_issue)");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransfer();
				stockTransfer.setStockTransferId(rs.getInt("stockTransferId"));
				stockTransfer.setFromBranch(TiimUtil.ValidateNull(rs.getString("transferFrom")).trim());
				stockTransfer.setToBranch(TiimUtil.ValidateNull(rs.getString("transferTo")).trim());
				stockTransfer.setStockDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("stocktransferdate")))));
				lstStockTransfer.add(stockTransfer);
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
		return lstStockTransfer;	
	}
}

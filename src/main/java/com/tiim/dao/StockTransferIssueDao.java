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
import com.tiim.model.Stock;
import com.tiim.model.StockTransferIssue;
import com.tiim.util.TiimUtil;

@Repository
public class StockTransferIssueDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	StockDao stockDao;
	
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
			pstmt = con.prepareStatement("SELECT max(transferIssueId) transferIssueId FROM stock_transfer_issue ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				returnNoteId = rs.getInt("transferIssueId");
			}
			returnNoteId++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in StockTransferIssueDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in StockTransferIssueDao  getIntialValue : "+ex.getMessage());
			}
		}
		
		return returnNoteId;	
	}
	
	public String addstockTransfer(StockTransferIssue stockTransfer, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into stock_transfer_issue(transferIssueDate, stockTransferId, transferFrom, transferTo) "
						+ "values(?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			    stockTransfer.setStockDate(sdf.format(date));
				pstmt.setDate(1, date);
				pstmt.setInt(2, stockTransfer.getStockTransferId());
				pstmt.setString(3, stockTransfer.getFromBranch());
				pstmt.setString(4, stockTransfer.getToBranch());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					stockTransfer.setStockTransferIssueId(rs.getInt(1));
				}
				msg = addStockTransferIssueDetail(stockTransfer);
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("stockTransfer.page", null,null));
				history.setDescription(messageSource.getMessage("stockTransfer.insert", null,null));
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
	
	private String addStockTransferIssueDetail(StockTransferIssue stockTransfer)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< stockTransfer.getStockTransferDetailId().length;i++)
			{
				pstmt1 = con.prepareStatement("select stockTransferDetailId, toolinglotnumber, typeoftool, productname, drawingno, uom, machinetype, stockqty, transferqty from stock_transfer_request_detail where stockTransferDetailId = ?");
				pstmt1.setLong(1, stockTransfer.getStockTransferDetailId()[i]);
				ResultSet rs = pstmt1.executeQuery();
				if(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("insert into stock_transfer_issue_detail (toolinglotnumber, productname, drawingno, machinetype, typeoftool, uom, stockqty,transferqty,issueQty,stockTransferDetailId,transferIssueId) "
								+ " values(?,?,?,?,?,?,?,?,?,?,?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, rs.getString("toolinglotnumber"));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("machinetype")));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setString(6, TiimUtil.ValidateNull(rs.getString("UOM")));
						pstmt.setLong(7, rs.getLong("stockqty"));
						pstmt.setLong(8, rs.getLong("transferqty"));
						pstmt.setLong(9, stockTransfer.getStockIssueQty()[i]);
						pstmt.setInt(10, rs.getInt("stockTransferDetailId"));
						pstmt.setInt(11, stockTransfer.getStockTransferIssueId());
						
						stockTransfer.setToolinglotnumber(rs.getString("toolinglotnumber"));
						stockTransfer.setProductName(rs.getString("productname"));
						stockTransfer.setDrawingNo(rs.getString("drawingno"));
						stockTransfer.setMachineType(rs.getString("machinetype"));
						stockTransfer.setTypeOfTool(rs.getString("typeoftool"));
						stockTransfer.setUom(rs.getString("UOM"));
						stockTransfer.setTransferQty(rs.getLong("transferqty"));
												
						pstmt.executeUpdate();
						
						storeStock(stockTransfer, i);
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
	
	public String updateStockTransferIssueDetail(StockTransferIssue stockTransfer, int userId)
	{
		StockTransferIssue transferIssue = getStockTransferissueDetailById(stockTransfer.getStockTransferIssueId());
		StockTransferIssue transferIssue1 = getStockTransferIssue(stockTransfer.getStockTransferIssueId());
		transferIssue.setToBranch(transferIssue1.getToBranch());
		transferIssue.setFromBranch(transferIssue1.getFromBranch());
		storeIssueStock(transferIssue);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< stockTransfer.getStockTransferIssueDetailId().length;i++)
			{
				
					try
					{
						pstmt = con.prepareStatement("update stock_transfer_issue_detail set issueQty = ? where transferIssueDetailId = ?");
								
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setLong(1, stockTransfer.getStockIssueQty()[i]);
						pstmt.setInt(2, stockTransfer.getStockTransferIssueDetailId()[i]);
						
						pstmt.executeUpdate();
						stockTransfer.setToolinglotnumber(transferIssue.getToolinglotnumber());
						storeStock(stockTransfer,i);
						TransactionHistory history = new TransactionHistory();
						history.setPageName(messageSource.getMessage("stockTransfer.page", null,null));
						history.setDescription(messageSource.getMessage("stockTransfer.update", null,null));
						history.setUserId(userId);
						historyDao.addHistory(history);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when updateStockTransferIssueDetail the data to stock_transfer_issue_detail: "+ex.getMessage());
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
							System.out.println("Exception when closing the connection in updateStockTransferIssueDetail detail in stock_transfer_issue_detail table : "+ex.getMessage());
						}
					}
				}
				msg = "Updated Successfully";
			
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
				if(con != null)
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
	
	public String deleteStockTransferIssue(int stockTransferId, int userId)
	{
		StockTransferIssue transferIssue = getStockTransferissueDetailById(stockTransferId);
		StockTransferIssue transferIssue1 = getStockTransferIssue(stockTransferId);
		transferIssue.setToBranch(transferIssue1.getToBranch());
		transferIssue.setFromBranch(transferIssue1.getFromBranch());
		storeIssueStock(transferIssue);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			{
					try
					{			
						pstmt = con.prepareStatement("delete from stock_transfer_issue_detail where transferIssueId = ?");
						pstmt.setInt(1, stockTransferId);
						pstmt.executeUpdate();
						
						pstmt = con.prepareStatement("delete from stock_transfer_issue where transferIssueId = ?");
						pstmt.setInt(1, stockTransferId);
						pstmt.executeUpdate();		
						
						TransactionHistory history = new TransactionHistory();
						history.setPageName(messageSource.getMessage("stockTransfer.page", null,null));
						history.setDescription(messageSource.getMessage("stockTransfer.update", null,null));
						history.setUserId(userId);
						historyDao.addHistory(history);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when deleteStockTransferIssue the data to stock_transfer_issue_detail: "+ex.getMessage());
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
							System.out.println("Exception when closing the connection in deleteStockTransferIssue detail in stock_transfer_issue_detail table : "+ex.getMessage());
						}
					}
				}
				msg = "Deleted Successfully";
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note deleteStockTransferIssue : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in deleteStockTransferIssue : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public List<StockTransferIssue> getStockTransferIssue(String transferIssueId)
	{
		List<StockTransferIssue> lstStockTransfer = new ArrayList<StockTransferIssue>();

		StockTransferIssue stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(transferIssueId == null || "".equals(transferIssueId))
			{
				pstmt = con.prepareStatement("select transferIssueId, transferIssueDate, transferFrom, transferTo, stockTransferId from stock_transfer_issue order by transferIssueId desc");
			}else
			{
				pstmt = con.prepareStatement("select transferIssueId, transferIssueDate, transferFrom, transferTo, stockTransferId from stock_transfer_issue where transferIssueId  like '%"+transferIssueId+"%'  order by transferIssueId desc");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransferIssue();
				stockTransfer.setStockTransferIssueId(rs.getInt("transferIssueId"));
				stockTransfer.setStockTransferId(rs.getInt("stockTransferId"));
				stockTransfer.setFromBranch(TiimUtil.ValidateNull(rs.getString("transferFrom")).trim());
				stockTransfer.setToBranch(TiimUtil.ValidateNull(rs.getString("transferTo")).trim());
				stockTransfer.setStockIssueDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("transferIssueDate")))));
				lstStockTransfer.add(stockTransfer);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the stock_transfer_issue list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the stock_transfer_issue list : "+ex.getMessage());
			}
		}
		return lstStockTransfer;	
	}
	
	public List<StockTransferIssue> getStockTransferIssueSearch(String transferIssueId)
	{
		List<StockTransferIssue> lstStockTransfer = new ArrayList<StockTransferIssue>();

		StockTransferIssue stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(transferIssueId == null || "".equals(transferIssueId))
			{
				pstmt = con.prepareStatement("select transferIssueId, transferIssueDate, transferFrom, transferTo, stockTransferId from stock_transfer_issue order by transferIssueId desc");
			}else
			{
				pstmt = con.prepareStatement("select transferIssueId, transferIssueDate, transferFrom, transferTo, stockTransferId from stock_transfer_issue where transferIssueId  like '%"+transferIssueId+"%'  order by transferIssueId desc");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransferIssue();
				stockTransfer.setStockTransferIssueId(rs.getInt("transferIssueId"));
				stockTransfer.setStockTransferId(rs.getInt("stockTransferId"));
				stockTransfer.setFromBranch(TiimUtil.ValidateNull(rs.getString("transferFrom")).trim());
				stockTransfer.setToBranch(TiimUtil.ValidateNull(rs.getString("transferTo")).trim());
				stockTransfer.setStockIssueDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("transferIssueDate")))));
				lstStockTransfer.add(stockTransfer);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the stock_transfer_issue list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the stock_transfer_issue list : "+ex.getMessage());
			}
		}
		return lstStockTransfer;	
	}
	
	public StockTransferIssue getStockTransferIssue(int transferIssueId)
	{
		StockTransferIssue stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select transferIssueId, transferIssueDate, transferFrom, transferTo, stockTransferId from stock_transfer_issue where transferIssueId = ?");
			pstmt.setInt(1, transferIssueId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransferIssue();
				stockTransfer.setStockTransferIssueId(rs.getInt("transferIssueId"));
				stockTransfer.setStockTransferId(rs.getInt("stockTransferId"));
				stockTransfer.setFromBranch(TiimUtil.ValidateNull(rs.getString("transferFrom")).trim());
				stockTransfer.setToBranch(TiimUtil.ValidateNull(rs.getString("transferTo")).trim());
				stockTransfer.setStockIssueDate(TiimUtil.ValidateNull(sdf.format(sdfDB.parse(rs.getString("transferIssueDate")))));
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
	
	public StockTransferIssue getStockTransferissueDetailById(int transferIssueDetailId)
	{

		StockTransferIssue stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select toolinglotnumber, productname, drawingno, machinetype, typeoftool, uom, stockqty,"
					+ " transferqty,issueQty,stockTransferDetailId, transferIssueId,transferIssueDetailId from stock_transfer_issue_detail where transferIssueId = ?");
			pstmt.setInt(1, transferIssueDetailId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransferIssue();
				stockTransfer.setToolinglotnumber(rs.getString("toolinglotnumber"));
				stockTransfer.setProductName(rs.getString("productname"));
				stockTransfer.setDrawingNo(rs.getString("drawingno"));
				stockTransfer.setMachineType(rs.getString("machinetype"));
				stockTransfer.setTypeOfTool(rs.getString("typeoftool"));
				stockTransfer.setUom(rs.getString("uom"));
				stockTransfer.setStockQty(rs.getLong("stockqty"));
				stockTransfer.setTransferQty(rs.getLong("transferqty"));
				stockTransfer.setStockIssueQty1(rs.getLong("issueQty"));
				stockTransfer.setStockTransferDetailId1(rs.getInt("stockTransferDetailId"));
				stockTransfer.setStockTransferIssueId(rs.getInt("transferIssueId"));
				stockTransfer.setStockTransferIssueDetailsId1(rs.getInt("transferIssueDetailId"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getStockTransferIssueDetail values : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the getStockTransferIssueDetail values : "+ex.getMessage());
			}
		}
		return stockTransfer;	
	}
	
	public List<StockTransferIssue> getStockTransferIssueDetail(int transferIssueId)
	{
		StockTransferIssue stockTransfer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		List<StockTransferIssue> lstStockTransfer = new ArrayList<StockTransferIssue>();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select toolinglotnumber, productname, drawingno, machinetype, typeoftool, uom, stockqty,"
					+ " transferqty,issueQty,stockTransferDetailId, transferIssueId,transferIssueDetailId from stock_transfer_issue_detail where transferIssueId = ?");
			pstmt.setInt(1, transferIssueId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockTransfer = new StockTransferIssue();
				stockTransfer.setToolinglotnumber(rs.getString("toolinglotnumber"));
				stockTransfer.setProductName(rs.getString("productname"));
				stockTransfer.setDrawingNo(rs.getString("drawingno"));
				stockTransfer.setMachineType(rs.getString("machinetype"));
				stockTransfer.setTypeOfTool(rs.getString("typeoftool"));
				stockTransfer.setUom(rs.getString("uom"));
				stockTransfer.setStockQty(rs.getLong("stockqty"));
				stockTransfer.setTransferQty(rs.getLong("transferqty"));
				stockTransfer.setStockIssueQty1(rs.getLong("issueQty"));
				stockTransfer.setStockTransferDetailId1(rs.getInt("stockTransferDetailId"));
				stockTransfer.setStockTransferIssueId(rs.getInt("transferIssueId"));
				stockTransfer.setStockTransferIssueDetailsId1(rs.getInt("transferIssueDetailId"));
				lstStockTransfer.add(stockTransfer);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getStockTransferIssueDetail values : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the getStockTransferIssueDetail values : "+ex.getMessage());
			}
		}
		return lstStockTransfer;	
	
	}
	
	private void storeStock(StockTransferIssue stockTransfer, int index)
	{
		
		Stock stock = new Stock();
		System.out.println(stockTransfer.getToolinglotnumber()+","+ stockTransfer.getFromBranch());
		stock = stockDao.getStock(stockTransfer.getToolinglotnumber(), stockTransfer.getFromBranch());
		long issueQty = stockTransfer.getStockIssueQty()[index];
		stock.setBranch(stockTransfer.getFromBranch());
		stock.setToolingLotNumber(stockTransfer.getToolinglotnumber());
		stock.setTypeOfTool(stockTransfer.getTypeOfTool());
		stock.setDrawingNo(stockTransfer.getDrawingNo());
		stock.setMachineName(stockTransfer.getMachineType());
		stock.setUom(stockTransfer.getUom());
		stock.setProductName(stockTransfer.getProductName());
		
		 //Transfer from branch. Minus the stock qty
		//stockDao.getStockId(stock);
		long stockQty = stock.getStockQty() - issueQty;
		stock.setStockQty(stockQty);
		System.out.println("stockQty: "+stockQty+", "+stock.getStockId());
		stockDao.updateStock(stock);
		
		stock.setStockQty(issueQty);
		stock.setBranch(stockTransfer.getToBranch());
		stock.setStockQty(stockTransfer.getStockIssueQty()[index]);
		Stock existStock = stockDao.getStock(stockTransfer.getToolinglotnumber(), stockTransfer.getToBranch());
		if(existStock != null)
		{
			stockQty =  existStock.getStockQty() +  stockTransfer.getStockIssueQty()[index];
			existStock.setStockQty(stockQty);
			stockDao.updateStock(existStock);
		}
		else
		{
			//Transfered to new branch
			stockDao.storeStock(stock);
		}
	}
	
	private void storeIssueStock(StockTransferIssue stockTransfer)
	{
		
		Stock stock = new Stock();
		stock = stockDao.getStock(stockTransfer.getToolinglotnumber(), stockTransfer.getFromBranch());
		long issueQty = stockTransfer.getStockIssueQty1();
		stock.setBranch(stockTransfer.getFromBranch());
		stock.setToolingLotNumber(stockTransfer.getToolinglotnumber());
		stock.setTypeOfTool(stockTransfer.getTypeOfTool());
		stock.setDrawingNo(stockTransfer.getDrawingNo());
		stock.setMachineName(stockTransfer.getMachineType());
		stock.setUom(stockTransfer.getUom());
		stock.setProductName(stockTransfer.getProductName());
		
		 //Transfer from branch. Minus the stock qty
		//stockDao.getStockId(stock);
		long stockQty = stock.getStockQty() + issueQty;
		stock.setStockQty(stockQty);
		stockDao.updateStock(stock);
		
		//stock.setStockQty(issueQty);
		stock = stockDao.getStock(stockTransfer.getToolinglotnumber(), stockTransfer.getToBranch());
		stock.setBranch(stockTransfer.getToBranch());
		//stockDao.getStockId(stock);
		stock.setStockQty(stock.getStockQty() - issueQty);
		//Transfered to new branch
		stockDao.updateStock(stock);
		
	}
	
	
}

package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.Stock;
import com.tiim.util.TiimUtil;

@Repository
public class StockDao {

	@Autowired
	DataSource datasource;
	
	@Autowired 
	CheckExpiryDateDao expiryDao;
	
	@Autowired
	ProductDao productDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	
	public List<Stock> getAutoStock(String lotNo, String branchName)
	{
		
		List<Stock> lstStock = new ArrayList<Stock>();
		Stock stock ;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(lotNo != null && !("").equals(lotNo))
			{
				pstmt = con.prepareStatement("Select stockId, toolinglotnumber, typeoftool, productname, drawingno, UOM, machinetype, stockqty from stock Where branch = ? and toolinglotnumber like '"+lotNo+"%'");
			}else
			{
				pstmt = con.prepareStatement("Select stockId, toolinglotnumber, typeoftool, productname, drawingno, UOM, machinetype, stockqty from stock Where branch = ? ");
			}
			pstmt.setString(1, branchName.trim());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stock = new Stock();
				stock.setStockId(rs.getInt("stockId"));
				stock.setToolingLotNumber(rs.getString("toolinglotnumber"));
				stock.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				stock.setProductName(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				stock.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				stock.setUom(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				stock.setMachineName(rs.getString("machinetype"));
				stock.setStockQty(rs.getInt("stockqty"));
				stock.setUploadPath(productDao.getProductUploadedPath(rs.getString("productname"), rs.getString("drawingno"), rs.getString("machinetype"), rs.getString("typeoftool")));
				//boolean status = expiryDao.isProductExpired(rs.getString("toolinglotnumber"));
				boolean isRejected = expiryDao.isProductRejected(rs.getString("toolinglotnumber"));
				if(!isRejected)
				{
					lstStock.add(stock);
				}
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular stock details in stock table by using lotno : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in particular stock details in stock table by using lotno : "+ex.getMessage());
			}
		}
		return lstStock;	
	}
	
	public Stock getIndividualStock(int stockId)
	{
		Stock stock = new Stock();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select stockId, toolinglotnumber, typeoftool, productname, drawingno, UOM, machinetype, stockqty from stock Where stockId = ?");
			pstmt.setInt(1, stockId);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				stock.setStockId(rs.getInt("stockId"));
				stock.setToolingLotNumber(rs.getString("toolinglotnumber"));
				stock.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				stock.setProductName(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				stock.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				stock.setUom(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				stock.setMachineName(rs.getString("machinetype"));
				stock.setStockQty(rs.getInt("stockqty"));
				getTotalProdQty(stock);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the individual stock details in stock table by using stock id : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in individual stock details in stock table by using stock id : "+ex.getMessage());
			}
		}
		return stock;	
	}
	
	public void storeStock(Stock stock)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into stock(toolinglotnumber, typeoftool, productname, drawingno, machinetype, stockqty, branch, UOM )"
						+ " values(?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, stock.getToolingLotNumber());
				pstmt.setString(2, stock.getTypeOfTool());
				pstmt.setString(3, stock.getProductName());
				pstmt.setString(4, stock.getDrawingNo());
				pstmt.setString(5, stock.getMachineName());
				pstmt.setLong(6, stock.getStockQty());
				pstmt.setString(7, stock.getBranch());
				pstmt.setString(8, stock.getUom());
				pstmt.executeUpdate();
							
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the storeStock stockdao : "+ex.getMessage());
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
				System.out.println("Exception when adding the storeStock stockdao : "+ex.getMessage());
			}
		}
	
	}
	
	public void updateStock(Stock stock)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update stock set stockqty =  ?, approvedQty = ?, rejectedQty = ?"
						+ " where stockId = ?");
				
				pstmt.setLong(1, stock.getStockQty());
				pstmt.setInt(2, stock.getGoodQty());
				pstmt.setLong(3, stock.getDamagedQty());
				pstmt.setInt(4, stock.getStockId());
				pstmt.executeUpdate();
							
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the updateStock stockdao : "+ex.getMessage());
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
				System.out.println("Exception when adding the storeStock stockdao : "+ex.getMessage());
			}
		}	
	}
	
	public int getStockQty(String productName, String machineType, String drawingNo, String typeOfTool)
	{
		int stockQty = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select stockId, toolinglotnumber, stockqty from stock "
					+ " Where typeoftool = ? and productname = ? and drawingno = ? and machinetype = ? ");
			pstmt.setString(1, typeOfTool);
			pstmt.setString(2, productName);
			pstmt.setString(3, drawingNo);
			pstmt.setString(4, machineType);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockQty = stockQty + rs.getInt("stockqty");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getStockQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getStockQty : "+ex.getMessage());
			}
		}
		return stockQty;
	}
	
	public Stock getStock(String lotNumber, String branch)
	{
		Stock stock = new Stock();
		int stockQty = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select stockId,stockqty from stock where branch=? and toolinglotnumber = ?");
			pstmt.setString(1, branch);
			pstmt.setString(2, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stock.setStockId(rs.getInt("stockId"));
				stock.setStockQty(rs.getInt("stockqty"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getStockQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getStockQty : "+ex.getMessage());
			}
		}
		return stock;
	
	}
	
	public int getStockLotQty(String productName, String machineType, String drawingNo, String typeOfTool)
	{
		int stockQty = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select stockId, toolinglotnumber, stockqty from stock "
					+ " Where typeoftool = ? and productname = ? and drawingno = ? and machinetype = ? ");
			pstmt.setString(1, typeOfTool);
			pstmt.setString(2, productName);
			pstmt.setString(3, drawingNo);
			pstmt.setString(4, machineType);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockQty = stockQty + rs.getInt("stockqty");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getStockQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getStockQty : "+ex.getMessage());
			}
		}
		return stockQty;
	}
	
	
	public int getUnderInspectionQty(String productName, String machineType, String drawingNo, String typeOfTool)
	{
		int receivedQuantity = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT receivedquantity FROM tooling_receiving_request_details"
					+ " Where typeoftool = ? and toolingname = ? and drawingno = ? and machinetype = ? ");
			pstmt.setString(1, typeOfTool);
			pstmt.setString(2, productName);
			pstmt.setString(3, drawingNo);
			pstmt.setString(4, machineType);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				receivedQuantity = receivedQuantity + rs.getInt("receivedquantity");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getStockQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getStockQty : "+ex.getMessage());
			}
		}
		return receivedQuantity;
	}
	
	
	public List<String> getToolingLotNo(String productName, String machineType, String drawingNo, String typeOfTool, String branch)
	{
		
		List<String>  lstLotNumber = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select stockId, toolinglotnumber from stock "
					+ " Where typeoftool = ? and productname = ? and drawingno = ? and machinetype = ? and branch = ?");
			pstmt.setString(1, typeOfTool.trim());
			pstmt.setString(2, productName.trim());
			pstmt.setString(3, drawingNo.trim());
			pstmt.setString(4, machineType.trim());
			pstmt.setString(5, branch);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				//boolean isExpired = expiryDao.isProductExpired(rs.getString("toolinglotnumber"));
				boolean isRejected = expiryDao.isProductRejected(rs.getString("toolinglotnumber"));
				if(!isRejected)
				{
					lstLotNumber.add(rs.getString("toolinglotnumber"));
				}
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getStockQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getStockQty : "+ex.getMessage());
			}
		}
		return lstLotNumber;
	}
	
	public String getLastInspectionDate(String productName, String machineType, String drawingNo, String typeOfTool)
	{

		String lastInspectionDate = new String();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT b.inspectionreportdate inspectionreportdate FROM tooling_receiving_inspection_details a, tooling_receiving_inspection b "
					+ " Where typeoftool = ? and toolingname = ? and drawingno = ? and machinetype = ? and a.toolingInspectionid = b.toolingInspectionid "
					+ " order by b.toolingInspectionid desc");
			pstmt.setString(1, typeOfTool);
			pstmt.setString(2, productName);
			pstmt.setString(3, drawingNo);
			pstmt.setString(4, machineType);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				lastInspectionDate = sdf.format(sdfDB.parse(rs.getString("inspectionreportdate")));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getStockQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getStockQty : "+ex.getMessage());
			}
		}
		return lastInspectionDate;
	
	}
	
	public void getStockId(Stock stock)
	{
		int stockId = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select stockId, toolinglotnumber, stockqty from stock "
					+ " Where typeoftool = ? and productname = ? and drawingno = ? and machinetype = ? and toolinglotnumber = ?");
			pstmt.setString(1, stock.getTypeOfTool());
			pstmt.setString(2, stock.getProductName());
			pstmt.setString(3, stock.getDrawingNo());
			pstmt.setString(4, stock.getMachineName());
			pstmt.setString(5, stock.getToolingLotNumber());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stock.setStockId(rs.getInt("stockId"));
				stock.setStockQty(rs.getLong("stockqty"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getStockId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getStockId : "+ex.getMessage());
			}
		}
		
	}
	
	private void getTotalProdQty(Stock stock) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select sum(batchQty) producedqty from mst_clearance_closing where lotNumber = ?");
					
			pstmt.setString(1, stock.getToolingLotNumber());
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				stock.setTabletProducedQty(rs.getInt("producedqty"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getSumOfProdQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getSumOfProdQty  : "+ex.getMessage());
			}
		}
		
	
	}
	
	public void getSumOfProdQty(Stock stock)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select "
					+ " sum(b.producedqty) producedqty "
					+ " from  tooling_production_return_note_detail b"
					+ " where  b.productname = ? and b.drawingno  = ? and b.machinetype = ? and b.typeoftool = ?"
					+ " group by toolinglotnumber");
			pstmt.setString(1, stock.getProductName());
			pstmt.setString(2, stock.getDrawingNo());
			pstmt.setString(3, stock.getMachineName());
			pstmt.setString(4, stock.getTypeOfTool());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				stock.setTabletProducedQty(rs.getInt("producedqty"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getSumOfProdQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getSumOfProdQty  : "+ex.getMessage());
			}
		}
		
	}
}


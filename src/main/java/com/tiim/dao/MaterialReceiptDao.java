package com.tiim.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.MaterialReceipt;
import com.tiim.util.TiimUtil;

@Repository
public class MaterialReceiptDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	
	public String addMaterialReceipt(MaterialReceipt material, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
		//	if(isMaterialExists(material.getMaterialCode()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into material_receipt_note(materialgrnno,materialgrndate,dcNo,dcdate,suppliercode,suppliername,billNo,billDate"
						+ ",materialcode,lotNumber,materialName,materialQty,uom,mgfdate,receivedby,remark) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				pstmt.setInt(1, material.getMaterialGrnNo());
				pstmt.setDate(2, getDbFormatDate(material.getMaterialGrnDate()));
				pstmt.setString(3, TiimUtil.ValidateNull(material.getDcNo()).trim());
				pstmt.setDate(4, getDbFormatDate(material.getDcDate()));
				pstmt.setString(5, TiimUtil.ValidateNull(material.getSupplierCode()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(material.getSupplierName()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(material.getBillNo()).trim());
				pstmt.setDate(8, getDbFormatDate(material.getBillDate()));
				pstmt.setInt(9, material.getMaterialCode());
				pstmt.setString(10, TiimUtil.ValidateNull(material.getLotNumber()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(material.getMaterialName()).trim());
				pstmt.setInt(12, material.getMaterialQty());
				pstmt.setString(13, TiimUtil.ValidateNull(material.getUom()).trim());
				pstmt.setDate(14, getDbFormatDate(material.getMgfDate()));
				pstmt.setString(15, TiimUtil.ValidateNull(material.getReceivedBy()).trim());
				pstmt.setString(16, TiimUtil.ValidateNull(material.getRemark()).trim());

				pstmt.executeUpdate();
				//msg = "Saved Successfully";
				msg = addToolingReceiptProduct(material);
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("materialreceipt.page", null,null));
				history.setDescription(messageSource.getMessage("materialreceipt.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			/*else
			{
				msg = "Already Exists";
			}*/
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the material detail in mst_material table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in material master detail in mst_material table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String addToolingReceiptProduct(MaterialReceipt materialReceipt)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		try
		{
			int stockQty = getStockQty(materialReceipt.getLotNumber());
			con = datasource.getConnection();
			if (stockQty == 0) {
				pstmt = con.prepareStatement("insert into material_stock(materialgrnno, toolinglotnumber, materialcode, UOM, stockqty, branch)"
						+ "values(?, ?, ?, ?, ?, ?)");

				pstmt.setInt(1, materialReceipt.getMaterialGrnNo());
				pstmt.setString(2, materialReceipt.getLotNumber());
				pstmt.setInt(3, materialReceipt.getMaterialCode());
				pstmt.setString(4, TiimUtil.ValidateNull(materialReceipt.getUom()).trim());
				pstmt.setInt(5, materialReceipt.getMaterialQty());
				pstmt.setString(6, TiimUtil.ValidateNull(materialReceipt.getBranch()).trim());
				
				pstmt.executeUpdate();
				msg = "Saved Successfully</>";
			} else {
				pstmt = con.prepareStatement("update material_stock set stockqty = ? where toolinglotnumber =?");
				pstmt.setInt(1, materialReceipt.getMaterialQty()+stockQty);
				pstmt.setString(2, materialReceipt.getLotNumber());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling_receipt_product detail in tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling product detail in tooling_receipt_product table : "+ex.getMessage());
			}
		}
		return msg;
	}
	public String updateMaterial(MaterialReceipt material, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update material_receipt_note set materialgrnno=?,materialgrndate=?,dcNo=?,dcdate=?,suppliercode=?,suppliername=?,"
						+ "billNo=?,billDate=?,materialcode=?,lotNumber=?,materialName=?,materialQty=?,uom=?,mgfdate=?,receivedby=?,remark=? where materialgrnno=?");
				pstmt.setInt(1, material.getMaterialGrnNo());
				pstmt.setDate(2, getDbFormatDate(material.getMaterialGrnDate()));
				pstmt.setString(3, TiimUtil.ValidateNull(material.getDcNo()).trim());
				pstmt.setDate(4, getDbFormatDate(material.getDcDate()));
				pstmt.setString(5, TiimUtil.ValidateNull(material.getSupplierCode()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(material.getSupplierName()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(material.getBillNo()).trim());
				pstmt.setDate(8, getDbFormatDate(material.getBillDate()));
				pstmt.setInt(9, material.getMaterialCode());
				pstmt.setString(10, TiimUtil.ValidateNull(material.getLotNumber()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(material.getMaterialName()).trim());
				pstmt.setInt(12, material.getMaterialQty());
				pstmt.setString(13, TiimUtil.ValidateNull(material.getUom()).trim());
				pstmt.setDate(14, getDbFormatDate(material.getMgfDate()));
				pstmt.setString(15, TiimUtil.ValidateNull(material.getReceivedBy()).trim());
				pstmt.setString(16, TiimUtil.ValidateNull(material.getRemark()).trim());
				pstmt.setInt(17, material.getMaterialGrnNo());

				pstmt.executeUpdate();
				msg = addToolingReceiptProduct(material);
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("materialreceipt.page", null,null));
				history.setDescription(messageSource.getMessage("materialreceipt.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the material detail in mst_material table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in material detail in mst_material table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteMaterial(int materialgrnno, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from material_receipt_note where materialgrnno = ?");
			pstmt.setInt(1, materialgrnno);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("materialreceipt.page", null,null));
			history.setDescription(messageSource.getMessage("materialreceipt.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the material detail in mst_material table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the material detail in mst_material table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<MaterialReceipt> getMaterialDetails(int materialGrnNo)
	{
		List<MaterialReceipt> lstMaterialReceipt = new ArrayList<MaterialReceipt>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialReceipt material;
		try
		{
			con = datasource.getConnection();
			if(materialGrnNo != 0)
			{
			    pstmt = con.prepareStatement("Select materialgrnno,materialgrndate,dcNo,dcdate,suppliercode,suppliername,"
			    		+ "billNo,billDate,materialcode,lotNumber,materialName,materialQty,uom,mgfdate,receivedby,remark "
			    		+ "from material_receipt_note Where materialgrnno like '%"+materialGrnNo+"%' order by materialgrnno");
			}
			else
			{
				pstmt = con.prepareStatement("Select materialgrnno,materialgrndate,dcNo,dcdate,suppliercode,suppliername,"
			    		+ "billNo,billDate,materialcode,lotNumber,materialName,materialQty,uom,mgfdate,receivedby,remark "
			    		+ "from material_receipt_note order by materialgrnno");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				material = new MaterialReceipt();
				material.setMaterialGrnNo(rs.getInt("materialgrnno"));
				material.setMaterialGrnDate(TiimUtil.ValidateNull(rs.getString("materialgrndate")).trim());
				material.setDcNo(TiimUtil.ValidateNull(rs.getString("dcNo")).trim());
				material.setDcDate(TiimUtil.ValidateNull(rs.getString("dcdate")).trim());
				material.setSupplierCode(TiimUtil.ValidateNull(rs.getString("suppliercode")).trim());
				material.setSupplierName(TiimUtil.ValidateNull(rs.getString("suppliername")).trim());
				material.setBillNo(TiimUtil.ValidateNull(rs.getString("billNo")).trim());
				material.setBillDate(TiimUtil.ValidateNull(rs.getString("billDate")).trim());
				material.setMaterialCode(rs.getInt("materialcode"));
				material.setLotNumber(TiimUtil.ValidateNull(rs.getString("lotNumber")).trim());
				material.setMaterialName(TiimUtil.ValidateNull(rs.getString("materialName")).trim());
				material.setMaterialQty(rs.getInt("materialQty"));
				material.setUom(TiimUtil.ValidateNull(rs.getString("uom")).trim());
				material.setMgfDate(TiimUtil.ValidateNull(rs.getString("mgfdate")).trim());
				material.setReceivedBy(TiimUtil.ValidateNull(rs.getString("receivedby")).trim());
				material.setRemark(TiimUtil.ValidateNull(rs.getString("remark")).trim());

				lstMaterialReceipt.add(material);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire material in mst_material table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire material details in mst_material table : "+ex.getMessage());
			}
		}
		return lstMaterialReceipt;	
	}
	
	
	private boolean isMaterialExists(int sopId)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM material_receipt_note WHERE materialgrnno = ?");
			 pstmt.setInt(1, sopId);
			 rs = pstmt.executeQuery();
			 if(rs.next())
			 {
				 count = rs.getInt("Is_Exists");
			 }
			 
			 if(count > 0)
			 {
				 isExists = false;
			 }
		}
		catch(Exception e)
		{
			System.out.println("Exception while checking the isBranchExists : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in isBranchExists : "+ex.getMessage());
			}
		}
		return isExists;
	}
	public Map<Integer,String> getMaterialType()
	{
		Map<Integer, String> materialTypes = new HashMap<Integer, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select materialTypeId, materialTypeName from material_type order by materialTypeId");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				materialTypes.put(rs.getInt("materialTypeId"), TiimUtil.ValidateNull(rs.getString("materialTypeName")).trim());
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire material in material_type table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire material_type details in material_type table : "+ex.getMessage());
			}
		}
		return materialTypes;	
	}
	
	public int getIntialValue()
	{
		int materialgrnno = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(materialgrnno) materialgrnno FROM material_receipt_note ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				materialgrnno = rs.getInt("materialgrnno");
			}
			materialgrnno++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in mst_material  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in mst_material : "+ex.getMessage());
			}
		}
		
		return materialgrnno;	
	}
	public Date getDbFormatDate(String date) {
		Date dbDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("date: "+date);
		
		java.util.Date dtDob = new java.util.Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		
		try {
			java.util.Date utilDate  = formatter.parse(sdf.format(dtDob));
			dbDate = new Date(utilDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		return dbDate;
	}
	
	public List<MaterialReceipt> getMaterialValues(int materialcCode)
	{
		List<MaterialReceipt> lstSop = new ArrayList<MaterialReceipt>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialReceipt material;
		try
		{
			con = datasource.getConnection();
			if(materialcCode != 0)
			{
			    pstmt = con.prepareStatement("Select materialcCode, materialName, uom from mst_material "
			    		+ "Where materialcCode like '%"+materialcCode+"%' order by materialcCode");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				material = new MaterialReceipt();
				material.setMaterialCode(rs.getInt("materialcCode"));
				material.setMaterialName(TiimUtil.ValidateNull(rs.getString("materialName")).trim());
				material.setUom(TiimUtil.ValidateNull(rs.getString("uom")).trim());
				lstSop.add(material);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire material in mst_material table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire material details in mst_material table : "+ex.getMessage());
			}
		}
		return lstSop;	
	}
	
	public int getStockQty(String lotNumber)
	{
		int stockQty = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select stockqty from material_stock where toolinglotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stockQty = rs.getInt("stockqty");
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
}

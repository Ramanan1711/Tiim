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

import com.tiim.model.MaterialIssue;
import com.tiim.model.MaterialReturn;
import com.tiim.util.TiimUtil;

@Repository
public class MaterialReturnDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	MaterialReceiptDao materialReceiptDao;
	
	public String addMaterialReturn(MaterialReturn material, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isMaterialReturnExists(material.getReturnNo()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into material_return(returnno,returndate,materialcode,lotnumber,materialname,materialqty,"
						+ "uom,materialtype,receivedby,remark,toolissueno,returnedby) "+ "values(?,?,?,?,?,?,?,?,?,?,?,?)");

				pstmt.setInt(1, material.getReturnNo());
				pstmt.setDate(2, getDbFormatDate(material.getReturnDate()));
				pstmt.setInt(3, material.getMaterialCode());
				pstmt.setString(4, TiimUtil.ValidateNull(material.getLotNumber()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(material.getMaterialName()).trim());
				pstmt.setInt(6, material.getMaterialQty());
				pstmt.setString(7, TiimUtil.ValidateNull(material.getUom()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(material.getMaterialType()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(material.getReceivedBy()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(material.getRemark()).trim());
				pstmt.setInt(11, material.getToolIssueNo());
				pstmt.setString(12, material.getReturnBy());

				pstmt.executeUpdate();
				addToolingReceiptProduct(material, "Save");
				updateMaterialIssue(material);
				msg = "Saved Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("materialreturn.page", null,null));
				history.setDescription(messageSource.getMessage("materialreturn.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			else
			{
				msg = "Already Exists";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the material detail in material_return table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in material master detail in material_return table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateMaterialReturn(MaterialReturn material, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update material_return set returnno=?,returndate=?,materialcode=?,lotnumber=?,materialname=?,materialqty=?,"
						+ "uom=?,materialtype=?,receivedby=?,remark=?,toolissueno=?,returnedby=? where returnno=?");
				pstmt.setInt(1, material.getReturnNo());
				pstmt.setDate(2, getDbFormatDate(material.getReturnDate()));
				pstmt.setInt(3, material.getMaterialCode());
				pstmt.setString(4, TiimUtil.ValidateNull(material.getLotNumber()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(material.getMaterialName()).trim());
				pstmt.setInt(6, material.getMaterialQty());
				pstmt.setString(7, TiimUtil.ValidateNull(material.getUom()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(material.getMaterialType()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(material.getReceivedBy()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(material.getRemark()).trim());
				pstmt.setInt(11, material.getToolIssueNo());
				pstmt.setString(12, TiimUtil.ValidateNull(material.getReturnBy()).trim());
				pstmt.setInt(13, material.getReturnNo());

				pstmt.executeUpdate();
				msg = "Updated Successfully";
				addToolingReceiptProduct(material, "Update");
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("materialreturn.page", null,null));
				history.setDescription(messageSource.getMessage("materialreturn.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the material detail in material_return table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in material detail in material_return table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteMaterialReturn(int returnno, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			MaterialReturn matReturn=getMaterialReturnDetails(returnno).get(0);
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from material_return where returnno = ?");
			pstmt.setInt(1, returnno);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			addToolingReceiptProduct(matReturn, "Delete");
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("materialreturn.page", null,null));
			history.setDescription(messageSource.getMessage("materialreturn.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the material detail in material_return table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the material detail in material_return table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<MaterialReturn> getMaterialReturnDetails(int returnno)
	{
		List<MaterialReturn> lstMaterialReturn = new ArrayList<MaterialReturn>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialReturn material;
		try
		{
			con = datasource.getConnection();
			if(returnno != 0)
			{
			    pstmt = con.prepareStatement("Select returnno,returndate,materialcode,lotnumber,materialname,materialqty,uom,materialtype,"
			    		+ "receivedby,remark,toolissueno,returnedby from material_return Where returnno like '%"+returnno+"%' order by returnno");
			}
			else
			{
				pstmt = con.prepareStatement("Select returnno,returndate,materialcode,lotnumber,materialname,materialqty,uom,materialtype,"
						+ "receivedby,remark,toolissueno,returnedby from material_return order by returnno");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				material = new MaterialReturn();
				material.setReturnNo(rs.getInt("returnno"));
				material.setReturnDate(TiimUtil.ValidateNull(rs.getString("returndate")).trim());
				material.setMaterialCode(rs.getInt("materialcode"));
				material.setLotNumber(TiimUtil.ValidateNull(rs.getString("lotNumber")).trim());
				material.setMaterialName(TiimUtil.ValidateNull(rs.getString("materialName")).trim());
				material.setMaterialQty(rs.getInt("materialQty"));
				material.setUom(TiimUtil.ValidateNull(rs.getString("uom")).trim());
				material.setMaterialType(TiimUtil.ValidateNull(rs.getString("materialtype")).trim());
				material.setReceivedBy(TiimUtil.ValidateNull(rs.getString("receivedby")).trim());
				material.setRemark(TiimUtil.ValidateNull(rs.getString("remark")).trim());
				material.setToolIssueNo(rs.getInt("toolissueno"));
				material.setReturnBy(TiimUtil.ValidateNull(rs.getString("returnedby")));

				lstMaterialReturn.add(material);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire material in material_return table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire material details in material_return table : "+ex.getMessage());
			}
		}
		return lstMaterialReturn;	
	}
	
	
	private boolean isMaterialReturnExists(int returnno)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM material_return WHERE returnno = ?");
			 pstmt.setInt(1, returnno);
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
		int returnno = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(returnno) returnno FROM material_return ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				returnno = rs.getInt("returnno");
			}
			returnno++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in material_return  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in material_return : "+ex.getMessage());
			}
		}
		
		return returnno;	
	}
	public String addToolingReceiptProduct(MaterialReturn materialReturn, String action)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		try
		{
			int stockQty = materialReceiptDao.getStockQty(materialReturn.getLotNumber());
			con = datasource.getConnection();
			if ("Delete".equalsIgnoreCase(action)) {
				pstmt = con.prepareStatement("update material_stock set stockqty = ? where toolinglotnumber =?");
				pstmt.setInt(1, stockQty-materialReturn.getMaterialQty());
				pstmt.setString(2, materialReturn.getLotNumber());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
			} else if ("Update".equalsIgnoreCase(action))
				{
				stockQty = stockQty-materialReturn.getIssuedQty();
				pstmt = con.prepareStatement("update material_stock set stockqty = ? where toolinglotnumber =?");
				pstmt.setInt(1, stockQty+materialReturn.getMaterialQty());
				pstmt.setString(2, materialReturn.getLotNumber());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				}else {
				pstmt = con.prepareStatement("update material_stock set stockqty = ? where toolinglotnumber =?");
				pstmt.setInt(1, stockQty+materialReturn.getMaterialQty());
				pstmt.setString(2, materialReturn.getLotNumber());
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

	public String updateMaterialIssue(MaterialReturn materialReturn)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
				pstmt = con.prepareStatement("update material_issue set isreturned = ? where issueno =?");
				pstmt.setInt(1, 1);
				pstmt.setInt(2, materialReturn.getToolIssueNo());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
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
}

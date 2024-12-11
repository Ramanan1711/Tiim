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
import com.tiim.model.MaterialReceipt;
import com.tiim.util.TiimUtil;

@Repository
public class MaterialIssueDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	MaterialReceiptDao materialReceiptDao;
	
	public String addMaterialIssue(MaterialIssue material, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isIssueExists(material.getIssueNo()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into material_issue(issueno,issuedate,materialcode,lotnumber,materialname,materialqty,"
						+ "uom,materialtype,issuedby,remark,toolrequestno,receivedby) "+ "values(?,?,?,?,?,?,?,?,?,?,?,?)");

				pstmt.setInt(1, material.getIssueNo());
				pstmt.setDate(2, getDbFormatDate(material.getIssueDate()));
				pstmt.setInt(3, material.getMaterialCode());
				pstmt.setString(4, TiimUtil.ValidateNull(material.getLotNumber()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(material.getMaterialName()).trim());
				pstmt.setInt(6, material.getMaterialQty());
				pstmt.setString(7, TiimUtil.ValidateNull(material.getUom()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(material.getMaterialType()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(material.getIssuedBy()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(material.getRemark()).trim());
				pstmt.setInt(11, material.getToolRequestNo());
				pstmt.setString(12, TiimUtil.ValidateNull(material.getReceivedBy()).trim());

				pstmt.executeUpdate();
				addToolingReceiptProduct(material, "Save");
				msg = "Saved Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("materialissue.page", null,null));
				history.setDescription(messageSource.getMessage("materialissue.insert", null,null));
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
			System.out.println("Exception when adding the material detail in material_issue table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in material master detail in material_issue table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateMaterialIssue(MaterialIssue material, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update material_issue set issueno=?,issuedate=?,materialcode=?,lotnumber=?,materialname=?,materialqty=?,"
						+ "uom=?,materialtype=?,issuedby=?,remark=?,toolrequestno=?,receivedby=? where issueno=?");
				pstmt.setInt(1, material.getIssueNo());
				pstmt.setDate(2, getDbFormatDate(material.getIssueDate()));
				pstmt.setInt(3, material.getMaterialCode());
				pstmt.setString(4, TiimUtil.ValidateNull(material.getLotNumber()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(material.getMaterialName()).trim());
				pstmt.setInt(6, material.getMaterialQty());
				pstmt.setString(7, TiimUtil.ValidateNull(material.getUom()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(material.getMaterialType()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(material.getIssuedBy()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(material.getRemark()).trim());
				pstmt.setInt(11, material.getToolRequestNo());
				pstmt.setString(12, TiimUtil.ValidateNull(material.getReceivedBy()).trim());
				pstmt.setInt(13, material.getIssueNo());
				pstmt.executeUpdate();
				msg = addToolingReceiptProduct(material, "Update");
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("materialissue.page", null,null));
				history.setDescription(messageSource.getMessage("materialissue.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the material detail in material_issue table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in material detail in material_issue table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteMaterialIssue(int issueno, int userId, MaterialIssue materialIssue)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from material_issue where issueno = ?");
			pstmt.setInt(1, issueno);
			pstmt.executeUpdate();
			addToolingReceiptProduct(materialIssue, "Delete");
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("materialissue.page", null,null));
			history.setDescription(messageSource.getMessage("materialissue.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the material detail in material_issue table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the material detail in material_issue table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<MaterialIssue> getMaterialIssueDetails(int issueno)
	{
		List<MaterialIssue> lstMaterialIssue = new ArrayList<MaterialIssue>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialIssue material;
		try
		{
			con = datasource.getConnection();
			if(issueno != 0)
			{
			    pstmt = con.prepareStatement("Select issueno,issuedate,materialcode,lotnumber,materialname,materialqty,uom,materialtype,"
			    		+ "issuedby,remark,toolrequestno,receivedby,isreturned from material_issue Where issueno like '%"+issueno+"%' order by issueno");
			}
			else
			{
				pstmt = con.prepareStatement("Select issueno,issuedate,materialcode,lotnumber,materialname,materialqty,uom,materialtype,"
						+ "issuedby,remark,toolrequestno,receivedby,isreturned from material_issue order by issueno");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				material = new MaterialIssue();
				material.setIssueNo(rs.getInt("issueno"));
				material.setIssueDate(TiimUtil.ValidateNull(rs.getString("issuedate")).trim());
				material.setMaterialCode(rs.getInt("materialcode"));
				material.setLotNumber(TiimUtil.ValidateNull(rs.getString("lotNumber")).trim());
				material.setMaterialName(TiimUtil.ValidateNull(rs.getString("materialName")).trim());
				material.setMaterialQty(rs.getInt("materialQty"));
				material.setUom(TiimUtil.ValidateNull(rs.getString("uom")).trim());
				material.setMaterialType(TiimUtil.ValidateNull(rs.getString("materialtype")).trim());
				material.setIssuedBy(TiimUtil.ValidateNull(rs.getString("issuedby")).trim());
				material.setRemark(TiimUtil.ValidateNull(rs.getString("remark")).trim());
				material.setToolRequestNo(rs.getInt("toolrequestno"));
				material.setReceivedBy(TiimUtil.ValidateNull(rs.getString("receivedby")).trim());
				material.setIsReturned(rs.getInt("isreturned"));

				lstMaterialIssue.add(material);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire material in material_issue table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire material details in material_issue table : "+ex.getMessage());
			}
		}
		return lstMaterialIssue;	
	}
	
	
	private boolean isIssueExists(int issueno)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM material_issue WHERE issueno = ?");
			 pstmt.setInt(1, issueno);
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
		int issueno = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(issueno) issueno FROM material_issue ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				issueno = rs.getInt("issueno");
			}
			issueno++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in material_issue  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in material_issue : "+ex.getMessage());
			}
		}
		
		return issueno;	
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
	
	public List<MaterialReceipt> getMaterialDetails(int lotNo)
	{
		List<MaterialReceipt> lstMaterialReceipt = new ArrayList<MaterialReceipt>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialReceipt material;
		try
		{
			con = datasource.getConnection();
			if(lotNo != 0)
			{
			    pstmt = con.prepareStatement("Select materialgrnno,materialgrndate,dcNo,dcdate,suppliercode,suppliername,"
			    		+ "billNo,billDate,materialcode,lotNumber,materialName,materialQty,uom,mgfdate,receivedby,remark "
			    		+ "from material_receipt_note Where lotNumber like '%"+lotNo+"%' order by lotNumber");
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

	public MaterialIssue getMaterialDetailsByLotNo(int materialGrnNo)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialIssue material = null;
		try
		{
			con = datasource.getConnection();
			if(materialGrnNo != 0)
			{
			    pstmt = con.prepareStatement("Select materialgrnno,materialcode,lotNumber,materialName,materialQty,uom,receivedby "
			    		+ "from material_receipt_note Where materialgrnno = "+materialGrnNo);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				material = new MaterialIssue();
				material.setToolRequestNo(rs.getInt("materialgrnno"));
				material.setMaterialCode(rs.getInt("materialcode"));
				material.setLotNumber(TiimUtil.ValidateNull(rs.getString("lotNumber")).trim());
				material.setMaterialName(TiimUtil.ValidateNull(rs.getString("materialName")).trim());
				material.setMaterialQty(rs.getInt("materialQty"));
				material.setUom(TiimUtil.ValidateNull(rs.getString("uom")).trim());
				material.setReceivedBy(TiimUtil.ValidateNull(rs.getString("receivedby")).trim());
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
		return material;	
	}

	public String addToolingReceiptProduct(MaterialIssue materialIssue, String action)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		try
		{
			int stockQty = materialReceiptDao.getStockQty(materialIssue.getLotNumber());
			con = datasource.getConnection();
			if ("Delete".equalsIgnoreCase(action)) {
				pstmt = con.prepareStatement("update material_stock set stockqty = ? where toolinglotnumber =?");
				pstmt.setInt(1, stockQty+materialIssue.getMaterialQty());
				pstmt.setString(2, materialIssue.getLotNumber());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
			}else {
				pstmt = con.prepareStatement("update material_stock set stockqty = ? where toolinglotnumber =?");
				pstmt.setInt(1, stockQty+materialIssue.getIssuedQty()-materialIssue.getMaterialQty());
				pstmt.setString(2, materialIssue.getLotNumber());
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
}

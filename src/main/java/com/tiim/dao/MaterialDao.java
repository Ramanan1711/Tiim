package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.Material;
import com.tiim.util.TiimUtil;

@Repository
public class MaterialDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	
	public String addMaterial(Material material, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isMaterialExists(material.getMaterialCode()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_material(materialcCode,materialType,materialQty,materialName,uom,minStockLevel,reorderLevel,materialDate) "
						+ "values(?,?,?,?,?,?,?,?)");
				pstmt.setInt(1, material.getMaterialCode());
				pstmt.setString(2, TiimUtil.ValidateNull(material.getMaterialType()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(material.getMaterialQty()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(material.getMaterialName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(material.getUom()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(material.getMinStockLevel()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(material.getReorderLevel()).trim());

				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(8, date);

				pstmt.executeUpdate();
				msg = "Saved Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("material.page", null,null));
				history.setDescription(messageSource.getMessage("material.insert", null,null));
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
	
	public String updateMaterial(Material material, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update mst_material set materialcCode= ?,materialType= ?,materialQty= ?,materialName= ?,uom= ?,minStockLevel=?,reorderLevel=?"
						+ " where materialcCode = ?");
				pstmt.setInt(1, material.getMaterialCode());
				pstmt.setString(2, TiimUtil.ValidateNull(material.getMaterialType()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(material.getMaterialQty()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(material.getMaterialName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(material.getUom()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(material.getMinStockLevel()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(material.getReorderLevel()).trim());
				pstmt.setInt(8, material.getMaterialCode());

				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("material.page", null,null));
				history.setDescription(messageSource.getMessage("material.update", null,null));
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
	
	public String deleteMaterial(int materialcCode, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_material where materialcCode = ?");
			pstmt.setInt(1, materialcCode);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("material.page", null,null));
			history.setDescription(messageSource.getMessage("material.delete", null,null));
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
	
	
	public List<Material> getMaterialDetails(int materialcCode)
	{
		List<Material> lstSop = new ArrayList<Material>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Material material;
		try
		{
			con = datasource.getConnection();
			if(materialcCode != 0)
			{
			    pstmt = con.prepareStatement("Select materialcCode, materialType, materialQty,materialName, uom,minStockLevel,reorderLevel from mst_material Where materialcCode like '%"+materialcCode+"%' order by materialcCode");
			}
			else
			{
				pstmt = con.prepareStatement("Select materialcCode, materialType, materialQty,materialName,uom,minStockLevel,reorderLevel from mst_material order by materialcCode");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				material = new Material();
				material.setMaterialCode(rs.getInt("materialcCode"));
				material.setMaterialType(TiimUtil.ValidateNull(rs.getString("materialType")).trim());
				material.setMaterialQty(TiimUtil.ValidateNull(rs.getString("materialQty")).trim());
				material.setMaterialName(TiimUtil.ValidateNull(rs.getString("materialName")).trim());
				material.setUom(TiimUtil.ValidateNull(rs.getString("uom")).trim());
				material.setMinStockLevel(TiimUtil.ValidateNull(rs.getString("minStockLevel")).trim());
				material.setReorderLevel(TiimUtil.ValidateNull(rs.getString("reorderLevel")).trim());

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
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_material WHERE materialcCode = ?");
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
		int materialcCode = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(materialcCode) materialcCode FROM mst_material ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				materialcCode = rs.getInt("materialcCode");
			}
			materialcCode++;
			
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
		
		return materialcCode;	
	}
}

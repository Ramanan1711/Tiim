package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.MaterialType;
import com.tiim.util.TiimUtil;

@Repository
public class MaterialTypeDao {
	


	@Autowired
	DataSource datasource;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	public String addMaterialType(MaterialType materialType, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isMaterialTypeExists(materialType.getMaterialTypeName()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into material_type(materialTypename, isactive)"
						+ "values(?, ?)");
				pstmt.setString(1, TiimUtil.ValidateNull(materialType.getMaterialTypeName()).trim());
				pstmt.setInt(2, 1);
				
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("MaterialType.page", null,null));
				history.setDescription(messageSource.getMessage("MaterialType.insert", null,null));
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
			System.out.println("Exception when adding the material type master detail in materialtype table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in material type master detail in materialtype table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateMaterialType(MaterialType materialType, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update material_type set materialTypename = ?, isactive = ?  where materialTypeid = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(materialType.getMaterialTypeName()).trim());
				pstmt.setInt(2, 1);
				pstmt.setInt(3, materialType.getMaterialTypeId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("materialType.page", null,null));
				history.setDescription(messageSource.getMessage("materialType.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the materialType detail in materialType table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in materialType detail in materialType table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<MaterialType> getMaterialTypeDetails(String materialTypeName)
	{
		List<MaterialType> materialTypes = new ArrayList<MaterialType>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialType materialType;
		try
		{
			con = datasource.getConnection();
			if(materialTypeName != null && !"".equals(materialTypeName))
			{
			    pstmt = con.prepareStatement("select materialTypeid, materialTypename, isactive from material_type Where materialTypename like '%"+materialTypeName+"%' order by id");
			}
			else
			{
				pstmt = con.prepareStatement("select materialTypeid, materialTypename, isactive from material_type order by materialTypename");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				materialType = new MaterialType();
				materialType.setMaterialTypeId(rs.getInt("materialTypeid"));
				materialType.setMaterialTypeName(rs.getString("materialTypename"));
				materialType.setIsActive(rs.getInt("isactive"));
				materialTypes.add(materialType);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire materialType details in materialType table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire Department details in mst_department table : "+ex.getMessage());
			}
		}
		return materialTypes;	
	}
	
	
	public List<MaterialType> getMaterialTypeDetails()
	{
		List<MaterialType> lstMaterialType = new ArrayList<MaterialType>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialType materialType;
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("Select materialTypeid, materialTypename from material_type where isactive = 1");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				materialType = new MaterialType();
				materialType.setMaterialTypeId(rs.getInt("materialTypeid"));
				materialType.setMaterialTypeName(rs.getString("materialTypename"));
				
				lstMaterialType.add(materialType);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the MaterialTypeDetails in materialType table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire MaterialTypeDetails in materialType table : "+ex.getMessage());
			}
		}
		return lstMaterialType;	
	}
	
	public String deleteMaterialType(int materialTypeId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from material_type where materialTypeId = ?");
			pstmt.setInt(1, materialTypeId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("materialType.page", null,null));
			history.setDescription(messageSource.getMessage("materialType.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the materialType detail in materialType table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the materialType detail in materialType table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public MaterialType getMaterialType(int materialTypeId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		MaterialType materialType = new MaterialType();
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("Select materialTypeid, materialTypename from material_type where materialTypeId = ?");
		    pstmt.setInt(1, materialTypeId);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				materialType.setMaterialTypeId(rs.getInt("materialTypeid"));
				materialType.setMaterialTypeName(rs.getString("materialTypename"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the MaterialType in materialType table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire MaterialType in materialType table : "+ex.getMessage());
			}
		}
		return materialType;	
	
	}
	
	public String changeMaterialTypeStatus( int materialTypeId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from material_type where materialTypeId = ?");
				pstmt.setInt(1, materialTypeId);
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
				
				pstmt = con.prepareStatement("Update materialType set isActive = ?  where materialTypeId = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, materialTypeId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of MaterialType in materialType table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in changing the status of MaterialType in materialType table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	private boolean isMaterialTypeExists(String materialTypeName)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM material_type WHERE materialTypename = ?");
			 pstmt.setString(1, TiimUtil.ValidateNull(materialTypeName).trim());
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
			System.out.println("Exception while checking the materialType name exists in materialType table when adding : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in materialType master detail in materialType table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	}
	


}

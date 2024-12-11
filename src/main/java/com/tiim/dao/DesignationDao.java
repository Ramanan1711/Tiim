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

import com.tiim.model.Designation;
import com.tiim.util.TiimUtil;

@Repository
public class DesignationDao {
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	public String addDepartment(Designation designation, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isDesignationExists(designation.getDesigcode()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into designation(desigcode, designationname)"
						+ "values(?, ?)");
				pstmt.setString(1, TiimUtil.ValidateNull(designation.getDesigcode()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(designation.getDesignationname()).trim());
				
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("department.page", null,null));
				history.setDescription(messageSource.getMessage("department.insert", null,null));
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
			System.out.println("Exception when adding the designation master detail in designation table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in designation master detail in designation table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateDesignation(Designation designation, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isDesignationExistsUpdate(designation.getDesignationId(), designation.getDesigcode()))
			{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update designation set desigcode = ?, designationname = ?  where designationId = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(designation.getDesigcode()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(designation.getDesignationname()).trim());
				pstmt.setInt(3, designation.getDesignationId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("department.page", null,null));
				history.setDescription(messageSource.getMessage("department.update", null,null));
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
			System.out.println("Exception when updating the designation detail in designation table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in designation detail in designation table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public String deleteDesignation(int designationId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from designation where designationId = ?");
			pstmt.setInt(1, designationId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("department.page", null,null));
			history.setDescription(messageSource.getMessage("department.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the designation detail in designation table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the designation detail in designation table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<Designation> getDesignationDetails(String searchDesignation)
	{
		List<Designation> lstDesignation = new ArrayList<Designation>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Designation designation;
		try
		{
			con = datasource.getConnection();
			if(searchDesignation != null && !"".equals(searchDesignation))
			{
			    pstmt = con.prepareStatement("select designationId, designationname, desigcode, isactive from designation Where designationname like '%"+searchDesignation+"%' order by desigcode");
			}
			else
			{
				pstmt = con.prepareStatement("select designationId, designationname, desigcode, isactive from designation order by designationname");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				designation = new Designation();
				designation.setDesignationId(rs.getInt("designationId"));
				designation.setDesignationname(rs.getString("designationname"));
				designation.setDesigcode(rs.getString("desigcode"));
				designation.setIsActive(rs.getInt("isactive"));
				lstDesignation.add(designation);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getDesignationDetails in designation table : "+ex.getMessage());
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
		return lstDesignation;	
	}
	
	public Designation getDesignationDetail(int designationId)
	{
		Designation designation = new Designation();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select designationId, designationname, desigcode, isActive from designation where designationId = ? order by designationname ");
			pstmt.setInt(1, designationId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				designation.setDesignationId(rs.getInt("designationId"));
				designation.setDesignationname(rs.getString("designationname"));
				designation.setDesigcode(rs.getString("desigcode"));
				designation.setIsActive(rs.getInt("isActive"));
				designation.setAction("");
				designation.setSearchDesignation("");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular designation details in designation table by using departmentid : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  particular designation details in designation table by using departmentid : "+ex.getMessage());
			}
		}
		return designation;	
	}
	
	
	private boolean isDesignationExists(String desigcode)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM designation WHERE desigcode = ?");
			 pstmt.setString(1, TiimUtil.ValidateNull(desigcode).trim());
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
			System.out.println("Exception while checking the desigcode exists in designation table when adding : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in desigcode master detail in designation table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	}
	
	private boolean isDesignationExistsUpdate(int designationId, String desigCode)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM designation WHERE desigcode = ?  and designationId <> ?  ");
			 pstmt.setString(1, TiimUtil.ValidateNull(desigCode).trim());
			 pstmt.setInt(2, designationId);
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
			System.out.println("Exception while checking the departmentcode exists in mst_department table when updating : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in departmentcode master detail in mst_department table when updating : "+ex.getMessage());
			}
		}
		return isExists;
	}

	public String changeDesignationStatus( int designationId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from designation where designationId = ?");
				pstmt.setInt(1, designationId);
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
				
				pstmt = con.prepareStatement("Update designation set isActive = ?  where designationId = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, designationId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of Department in mst_department table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in changing the status of Department in mst_department table : "+ex.getMessage());
			}
		}
		
		return msg;
	}

}

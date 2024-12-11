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

import com.tiim.model.Department;
import com.tiim.util.TiimUtil;

@Repository
public class DepartmentDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	public String addDepartment(Department department, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isDepartmentExists(department.getDepartmentCode()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_department(departmentcode, departmentname, description,isactive)"
						+ "values(?, ?, ?, ?)");
				pstmt.setString(1, TiimUtil.ValidateNull(department.getDepartmentCode()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(department.getDepartmentName()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(department.getDescription()).trim());
				pstmt.setInt(4, 1);
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
			System.out.println("Exception when adding the department master detail in mst_department table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in department master detail in mst_department table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateDepartment(Department department, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isDepartmentExistsUpdate(department.getDepartmentId(), department.getDepartmentCode()))
			{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update mst_department set departmentcode = ?,departmentname = ?,description=?,isactive=1  where departmentid = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(department.getDepartmentCode()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(department.getDepartmentName()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(department.getDescription()).trim());
				pstmt.setInt(4, department.getDepartmentId());
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
			System.out.println("Exception when updating the Department detail in mst_department table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in Department detail in mst_department table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteDepartment(int departmentId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_department where departmentid = ?");
			pstmt.setInt(1, departmentId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("department.page", null,null));
			history.setDescription(messageSource.getMessage("department.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the Department detail in mst_department table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the Department detail in mst_department table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<Department> getDepartmentDetails(String searchDepartment)
	{
		List<Department> lstDepartment = new ArrayList<Department>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Department department;
		try
		{
			con = datasource.getConnection();
			if(searchDepartment != null && !"".equals(searchDepartment))
			{
			    pstmt = con.prepareStatement("Select departmentid, departmentcode,departmentname,description isactive from mst_department Where departmentid like '%"+searchDepartment+"%' order by departmentid");
			}
			else
			{
				pstmt = con.prepareStatement("Select departmentid, departmentcode,departmentname,description, isactive from mst_department order by departmentid");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				department = new Department();
				department.setDepartmentId(rs.getInt("departmentid"));
				department.setDepartmentCode(TiimUtil.ValidateNull(rs.getString("departmentcode")).trim());
				department.setDepartmentName(TiimUtil.ValidateNull(rs.getString("departmentname")).trim());
				department.setDescription(TiimUtil.ValidateNull(rs.getString("description")).trim());
				department.setIsActive(rs.getInt("isactive"));
				
				lstDepartment.add(department);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getDepartmentDetails in mst_department table : "+ex.getMessage());
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
		return lstDepartment;	
	}
	
	public Department getDepartmentDetail(int departmentId)
	{
		Department department = new Department();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select departmentid, departmentcode,departmentname,description, isactive from mst_department Where departmentid = ?");
			pstmt.setInt(1, departmentId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				department.setDepartmentId(rs.getInt("departmentid"));
				department.setDepartmentCode(TiimUtil.ValidateNull(rs.getString("departmentcode")).trim());
				department.setDepartmentName(TiimUtil.ValidateNull(rs.getString("departmentname")).trim());
				department.setDescription(TiimUtil.ValidateNull(rs.getString("description")).trim());
				department.setIsActive(rs.getInt("isactive"));
				department.setAction("");
				department.setSearchDepartment("");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular department details in mst_department table by using departmentid : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  particular department details in mst_department table by using departmentid : "+ex.getMessage());
			}
		}
		return department;	
	}
	
	
	private boolean isDepartmentExists(String departmentCode)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_department WHERE departmentcode = ?");
			 pstmt.setString(1, TiimUtil.ValidateNull(departmentCode).trim());
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
			System.out.println("Exception while checking the departmentcode exists in mst_department table when adding : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in departmentcode master detail in mst_department table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	}
	 
	private boolean isDepartmentExistsUpdate(int departmentId, String departmentCode)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_department WHERE  departmentid <> ? and  departmentcode = ?");
			 pstmt.setInt(1, departmentId);
			 pstmt.setString(2, TiimUtil.ValidateNull(departmentCode).trim());
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

	public String changeDepartmentStatus( int departmentId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from mst_department where departmentid = ?");
				pstmt.setInt(1, departmentId);
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
				
				pstmt = con.prepareStatement("Update mst_department set isActive = ?  where departmentid = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, departmentId);
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

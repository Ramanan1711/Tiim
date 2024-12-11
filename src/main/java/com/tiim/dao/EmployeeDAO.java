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
import com.tiim.model.Employee;

@Repository
public class EmployeeDAO {

	@Autowired
	DataSource datasource;
	
	@Autowired 
	MessageSource messageSource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	private boolean isEmployeeExists(String empCode)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_employee WHERE empCode = ?");
			 pstmt.setString(1, ValidateNull(empCode).trim());
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
			System.out.println("Exception while checking the empCode exists in mst_employee table when adding : "+e.getMessage());
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
				System.out.println("Exception when closing the connectin in Employee master detail in mst_employee table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	}
	
	public String addEmployeeDB(Employee emp, int  userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isEmployeeExists(emp.getEmpCode()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_employee(empCode, empName, department, isActive, Designation)"
						+ "values(?, ?, ?, ?, ?)");
				pstmt.setString(1, ValidateNull(emp.getEmpCode()).trim());
				pstmt.setString(2, ValidateNull(emp.getEmpName()).trim());
				pstmt.setString(3, ValidateNull(emp.getDepartment()).trim());
				pstmt.setInt(4, 1);
				pstmt.setString(5, emp.getDesignation());
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("employee.page", null,null));
				history.setDescription(messageSource.getMessage("employee.insert", null,null));
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
			System.out.println("Exception when adding the Employee master detail in mst_employee table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in Employee master detail in mst_employee table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String getParticularEmployeeDetails(int empId)
	{
		String empDetails = new String();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select empId, empCode, empName, department, isActive, Designation from mst_employee Where empId = ?");
			pstmt.setInt(1, empId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				empDetails = rs.getInt("empId")+"</>";
				empDetails = empDetails + ValidateNull(rs.getString("empCode").trim())+"</>";
				empDetails = empDetails + ValidateNull(rs.getString("empName").trim())+"</>"; 
				empDetails = empDetails + ValidateNull(rs.getString("department").trim())+"</>";
				empDetails = empDetails + rs.getInt("isActive")+"</>";
				empDetails = empDetails + ValidateNull(rs.getString("Designation").trim())+"</>";
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular employee details in mst_employee table by using empId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  particular employee details in mst_employee table by using empId : "+ex.getMessage());
			}
		}
		return empDetails;	
	}
	
	private boolean isEmployeeExistsUpdate(int empId, String empCode)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_employee WHERE  empId <> ? and  empCode = ?");
			 pstmt.setInt(1, empId);
			 pstmt.setString(2, ValidateNull(empCode).trim());
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
			System.out.println("Exception while checking the empCode exists in mst_employee table when updating : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in Employee master detail in mst_employee table when updating : "+ex.getMessage());
			}
		}
		return isExists;
	}
	
	public String updateEmployee(Employee emp, int  userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isEmployeeExistsUpdate(emp.getEmpId(), emp.getEmpCode()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("Update mst_employee set empCode = ?, empName = ?, department = ?  where empId = ?");
				pstmt.setString(1, ValidateNull(emp.getEmpCode()).trim());
				pstmt.setString(2, ValidateNull(emp.getEmpName()).trim());
				pstmt.setString(3, ValidateNull(emp.getDepartment()).trim());
				pstmt.setInt(4, emp.getEmpId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("employee.page", null,null));
				history.setDescription(messageSource.getMessage("employee.update", null,null));
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
			System.out.println("Exception when updating the Employee detail in mst_employee table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in Employee detail in mst_employee table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String changeEmployeeStatus( int empId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from mst_employee where empId = ?");
				pstmt.setInt(1, empId);
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
				
				pstmt = con.prepareStatement("Update mst_employee set isActive = ?  where empId = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, empId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of Employee in mst_employee table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in changing the status of Employee in mst_employee table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteEmployee(int empId, int  userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_employee where empId = ?");
			pstmt.setInt(1, empId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("employee.page", null,null));
			history.setDescription(messageSource.getMessage("employee.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the Employee detail in mst_employee table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the Employee detail in mst_employee table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<Employee> getEmployeeDetails(String searchEmployee)
	{
		List<Employee> lstEmployee = new ArrayList<Employee>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Employee emp;
		try
		{
			con = datasource.getConnection();
			if(searchEmployee != null && !"".equals(searchEmployee))
			{
			    pstmt = con.prepareStatement("Select empId, empCode, empName, department, isActive from mst_employee Where empName like '%"+searchEmployee+"%' order by empCode");
			}
			else
			{
				pstmt = con.prepareStatement("Select empId, empCode, empName, department, isActive from mst_employee order by empCode");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				emp = new Employee();
				emp.setEmpId(rs.getInt("empId"));
				emp.setEmpCode(ValidateNull(rs.getString("empCode")).trim());
				emp.setEmpName(ValidateNull(rs.getString("empName")).trim());
				emp.setDepartment(ValidateNull(rs.getString("department")).trim());
				emp.setIsActive(rs.getInt("isActive"));
				emp.setDelStatus(0);
				lstEmployee.add(emp);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire employee details in mst_employee table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire employee details in mst_employee table : "+ex.getMessage());
			}
		}
		return lstEmployee;	
	}
	
	public List<String> getDesignation()
	{
		List<String> lstDesignation = new ArrayList<String>();

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("select designationname from designation");
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 lstDesignation.add(rs.getString("designationname"));
			 }
			 
		}
		catch(Exception e)
		{
			System.out.println("Exception while getDesignation: "+e.getMessage());
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
				System.out.println("Exception when closing the connection in getDesignation : "+ex.getMessage());
			}
		}
	
		return lstDesignation;
	}
	
	public List<Department> getDepartment()
	{
		List<Department> lstDepartment = new ArrayList<Department>();

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Department department;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("select departmentcode, departmentname from mst_department");
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 department = new Department();
				 department.setDepartmentCode(rs.getString("departmentcode"));
				 department.setDepartmentName(rs.getString("departmentname"));
				 lstDepartment.add(department);
			 }
			 
		}
		catch(Exception e)
		{
			System.out.println("Exception while getDepartment: "+e.getMessage());
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
				System.out.println("Exception when closing the connection in getDepartment : "+ex.getMessage());
			}
		}
	
		return lstDepartment;
	}
	
	public List<Employee> getAutoEmployeeName(String searchEmployee)
	{
		List<Employee> lstEmployee = new ArrayList<Employee>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Employee emp;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select empId, empCode, empName, department, isActive from mst_employee Where empName like '"+searchEmployee+"%' order by empName");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				emp = new Employee();
				emp.setEmpId(rs.getInt("empId"));
				emp.setEmpCode(ValidateNull(rs.getString("empCode")).trim());
				emp.setEmpName(ValidateNull(rs.getString("empName")).trim());
				emp.setDepartment(ValidateNull(rs.getString("department")).trim());
				emp.setIsActive(rs.getInt("isActive"));
				emp.setDelStatus(0);
				
				lstEmployee.add(emp);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire employee name using autofill in mst_employee table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire employee name using autofill in mst_employee table : "+ex.getMessage());
			}
		}
		return lstEmployee;	
	}
	
	private String ValidateNull(String str)
	{
		if(str == null)
		{
			str = "";
		}
		return str;
	}
}

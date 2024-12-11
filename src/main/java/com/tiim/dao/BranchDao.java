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

import com.tiim.model.Branch;
import com.tiim.model.Department;
import com.tiim.util.TiimUtil;

@Repository
public class BranchDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	public List<Branch> getBranchName(String branchName)
	{

		List<Branch> lstBranch = new ArrayList<Branch>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Branch branch;
		try
		{
			con = datasource.getConnection();
			if(branchName != null && !"".equals(branchName))
			{
				pstmt = con.prepareStatement("select branchId,branchname, branchcode, isActive from branch where branchName like '"+branchName+"%'");
			}else
			{
				pstmt = con.prepareStatement("select branchId, branchname, branchcode, isActive from branch");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				branch = new  Branch();
				branch.setBranchId(rs.getInt("branchId"));
				branch.setBranchName(rs.getString("branchname"));
				branch.setBranchCode(rs.getString("branchcode"));
				branch.setIsActive(rs.getInt("isActive"));
				lstBranch.add(branch);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getbranch in branch table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getbranch in branch table : "+ex.getMessage());
			}
		}
		return lstBranch;	
	
	}
	
	public String addBranch(Branch branch, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isBranchExists(branch.getBranchName()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into branch(branchname,branchcode) "
						+ "values(?, ?)");
				pstmt.setString(1, TiimUtil.ValidateNull(branch.getBranchName()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(branch.getBranchCode()).trim());
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("branch.page", null,null));
				history.setDescription(messageSource.getMessage("branch.insert", null,null));
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
	
	public String updateBranch(Branch branch, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isBranchExistsUpdate(branch.getBranchId(), branch.getBranchName()))
			{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update branch set branchcode = ?, branchname = ?  where branchId = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(branch.getBranchCode()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(branch.getBranchName()).trim());
				pstmt.setInt(3, branch.getBranchId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("branch.page", null,null));
				history.setDescription(messageSource.getMessage("branch.update", null,null));
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
			System.out.println("Exception when updating the Branch detail in Branch table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in Branch detail in Branch table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteBranch(int branchId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from branch where branchId = ?");
			pstmt.setInt(1, branchId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("branch.page", null,null));
			history.setDescription(messageSource.getMessage("branch.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the branch detail in branch table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the branch detail in branch table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String changeBranchStatus( int branchId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from branch where branchId = ?");
				pstmt.setInt(1, branchId);
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
				
				pstmt = con.prepareStatement("Update branch set isActive = ?  where branchId = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, branchId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of branch in branch table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in changing the status of branch in branch table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<Branch> getBranchDetails(String searchBranch)
	{
		List<Branch> lstBranch = new ArrayList<Branch>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Branch branch;
		try
		{
			con = datasource.getConnection();
			if(searchBranch != null && !"".equals(searchBranch))
			{
			    pstmt = con.prepareStatement("Select branchId, branchname, branchcode,  isActive from branch Where branchname like '%"+searchBranch+"%' order by branchname");
			}
			else
			{
				pstmt = con.prepareStatement("Select branchId, branchname, branchcode,  isActive from branch order by branchname");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				branch = new Branch();
				branch.setBranchId(rs.getInt("branchId"));
				branch.setBranchCode(TiimUtil.ValidateNull(rs.getString("branchcode")).trim());
				branch.setBranchName(TiimUtil.ValidateNull(rs.getString("branchname")).trim());
				branch.setIsActive(rs.getInt("isactive"));
				
				lstBranch.add(branch);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getBranchDetails in branch table : "+ex.getMessage());
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
		return lstBranch;	
	}
	
	public Branch getBranchDetail(int branchId)
	{
		Branch branch = new Branch();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select branchId, branchname, branchcode,  isActive from branch Where branchId = ?");
			pstmt.setInt(1, branchId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				branch.setBranchId(rs.getInt("branchId"));
				branch.setBranchCode(TiimUtil.ValidateNull(rs.getString("branchcode")).trim());
				branch.setBranchName(TiimUtil.ValidateNull(rs.getString("branchname")).trim());
				branch.setIsActive(rs.getInt("isactive"));
				
				branch.setAction("");
				branch.setSearchBranch("");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular branch details in branch table by using departmentid : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  particular branch details in branch table by using departmentid : "+ex.getMessage());
			}
		}
		return branch;	
	}
	
	private boolean isBranchExists(String branchName)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM branch WHERE branchname = ?");
			 pstmt.setString(1, TiimUtil.ValidateNull(branchName).trim());
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
	
	private boolean isBranchExistsUpdate(int branchId, String branchName)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM branch WHERE  branchId <> ? and  branchname = ?");
			 pstmt.setInt(1, branchId);
			 pstmt.setString(2, TiimUtil.ValidateNull(branchName).trim());
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
			System.out.println("Exception while checking the branchname exists in branch table when updating : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in branchname master detail in branch table when updating : "+ex.getMessage());
			}
		}
		return isExists;
	}
}

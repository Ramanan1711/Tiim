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

import com.tiim.model.Role;
import com.tiim.util.TiimUtil;;

@Repository
public class RoleDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	public String addRole(Role role, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isRoleExists(role.getRoleName()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into role(rolename, isactive)"
						+ "values(?, ?)");
				pstmt.setString(1, TiimUtil.ValidateNull(role.getRoleName()).trim());
				pstmt.setInt(2, 1);
				
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("Role.page", null,null));
				history.setDescription(messageSource.getMessage("Role.insert", null,null));
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
	
	public String updateRole(Role role, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update role set rolename = ?, isactive = ?  where roleid = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(role.getRoleName()).trim());
				pstmt.setInt(2, 1);
				pstmt.setInt(3, role.getRoleId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("role.page", null,null));
				history.setDescription(messageSource.getMessage("role.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the role detail in role table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in role detail in role table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<Role> getRoleDetails(String roleName)
	{
		List<Role> roles = new ArrayList<Role>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Role role;
		try
		{
			con = datasource.getConnection();
			if(roleName != null && !"".equals(roleName))
			{
			    pstmt = con.prepareStatement("select roleid, rolename, isactive from role Where rolename like '%"+roleName+"%' order by id");
			}
			else
			{
				pstmt = con.prepareStatement("select roleid, rolename, isactive from role order by rolename");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				role = new Role();
				role.setRoleId(rs.getInt("roleid"));
				role.setRoleName(rs.getString("rolename"));
				role.setIsActive(rs.getInt("isactive"));
				roles.add(role);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire role details in role table : "+ex.getMessage());
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
		return roles;	
	}
	
	
	public List<Role> getRoleDetails()
	{
		List<Role> lstRole = new ArrayList<Role>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Role role;
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("Select roleid, rolename from role where isactive = 1");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				role = new Role();
				role.setRoleId(rs.getInt("roleid"));
				role.setRoleName(rs.getString("rolename"));
				
				lstRole.add(role);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the RoleDetails in role table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire RoleDetails in role table : "+ex.getMessage());
			}
		}
		return lstRole;	
	}
	
	public String deleteRole(int roleId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from role where roleid = ?");
			pstmt.setInt(1, roleId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("role.page", null,null));
			history.setDescription(messageSource.getMessage("role.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the role detail in role table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the role detail in role table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public Role getRole(int roleId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Role role = new Role();
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("Select roleid, rolename from role where roleId = ?");
		    pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				role.setRoleId(rs.getInt("roleid"));
				role.setRoleName(rs.getString("rolename"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the Role in role table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire Role in role table : "+ex.getMessage());
			}
		}
		return role;	
	
	}
	
	public String changeRoleStatus( int roleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from role where roleId = ?");
				pstmt.setInt(1, roleId);
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
				
				pstmt = con.prepareStatement("Update role set isActive = ?  where roleId = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, roleId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of Role in role table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in changing the status of Role in role table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	private boolean isRoleExists(String roleName)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM role WHERE rolename = ?");
			 pstmt.setString(1, TiimUtil.ValidateNull(roleName).trim());
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
			System.out.println("Exception while checking the role name exists in role table when adding : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in role master detail in role table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	}
	
}

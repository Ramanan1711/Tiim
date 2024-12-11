package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.Modules;
import com.tiim.model.RoleVsUser;
import com.tiim.util.TiimUtil;

@Repository
public class RoleModuleMapDao {

	@Autowired
	DataSource datasource;

	public String addRoleModule(RoleVsUser roleUser)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		
		try
		{
			System.out.println("Add new Role Map Module:::: "+ roleUser.getModuleName().length);
			//if(isMappingExists(roleUser.getModuleName(), roleUser.getRoleName()))
			for(int i = 0; i < roleUser.getModuleName().length; i++)
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into rolemapmodule(modulename, rolename, addAccess, editAccess, viewaccess, deleteaccess, screenname, accesscontrol)"
						+ "values(?, ?, ?, ?, ?, ?, ?, ?)");
				//,Statement.RETURN_GENERATED_KEYS
				
				pstmt.setString(1, TiimUtil.ValidateNull(roleUser.getModuleName()[i]).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(roleUser.getRoleName()[i]).trim());
				pstmt.setString(3, roleUser.getAddAccess()[i]);
				pstmt.setString(4, roleUser.getEditAccess()[i]);
				pstmt.setString(5, roleUser.getViewAccess()[i]);
				pstmt.setString(6, roleUser.getDeleteAccess()[i]);
				pstmt.setString(7, TiimUtil.ValidateNull(roleUser.getScreenName()[i]).trim());
				pstmt.setString(8, roleUser.getAccessControl()[i]);
				pstmt.executeUpdate();
			
				msg = "Saved Successfully";
			}
			/*else
			{
				msg = "Already Exists";
			}*/
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the rolemapmodule master detail in rolemapmodule table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in rolemapmodule master detail in rolemapmodule table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateRoleModule(RoleVsUser roleUser, HttpSession session)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		String role = "";
		try
		{
				con = datasource.getConnection();
				for(int i = 0; i < roleUser.getModuleName().length; i++)
				{
					pstmt = con.prepareStatement("update rolemapmodule set modulename = ?, rolename = ?, addAccess = ?, editAccess = ?, viewaccess = ?, deleteaccess = ?, screenname = ?, accesscontrol = ?"
							+ " where rolemoduleid = ?");
					
					//,Statement.RETURN_GENERATED_KEYS
					role = TiimUtil.ValidateNull(roleUser.getRoleName()[i]).trim();
					
					pstmt.setString(1, TiimUtil.ValidateNull(roleUser.getModuleName()[i]).trim());
					pstmt.setString(2, role);
					pstmt.setString(3, roleUser.getAddAccess()[i]);
					pstmt.setString(4, roleUser.getEditAccess()[i]);
					pstmt.setString(5, roleUser.getViewAccess()[i]);
					pstmt.setString(6, roleUser.getDeleteAccess()[i]);
					pstmt.setString(7, TiimUtil.ValidateNull(roleUser.getScreenName()[i]).trim());
					pstmt.setString(8, roleUser.getAccessControl()[i]);
					pstmt.setInt(9, roleUser.getRoleModuleId()[i]);
					pstmt.executeUpdate();
					if(pstmt.getUpdateCount() == 0 && !TiimUtil.ValidateNull(roleUser.getScreenName()[i]).isEmpty()) {
						
						RoleVsUser roleUserAdd = new RoleVsUser();

						String[] roleName = new String[1];
						String[] moduleName = new String[1] ;
						String[] addAccess = new String[1];
						String[] editAccess = new String[1];
						String[] viewAccess = new String[1];
						String[] deleteAccess = new String[1];
						String[] screenName = new String[1];
						String[] accessControl = new String[1];
						
						roleName[0] = TiimUtil.ValidateNull(roleUser.getRoleName()[i]).trim();
						moduleName[0] = TiimUtil.ValidateNull(roleUser.getModuleName()[i]).trim();
						addAccess[0] = roleUser.getAddAccess()[i];
						editAccess[0] = roleUser.getEditAccess()[i];
						viewAccess[0] = roleUser.getViewAccess()[i];
						deleteAccess[0] = roleUser.getDeleteAccess()[i];
						screenName[0] =  TiimUtil.ValidateNull(roleUser.getScreenName()[i]).trim();
						accessControl[0] = TiimUtil.ValidateNull(roleUser.getScreenName()[i]);
						roleUserAdd.setRoleName(roleName);
						roleUserAdd.setModuleName(moduleName);
						roleUserAdd.setAddAccess(addAccess);
						roleUserAdd.setEditAccess(editAccess);
						roleUserAdd.setViewAccess(viewAccess);
						roleUserAdd.setDeleteAccess(deleteAccess);
						roleUserAdd.setScreenName(screenName);
						roleUserAdd.setAccessControl(accessControl);
						addRoleModule(roleUser);
					}
					
				}
				String selectedRole = TiimUtil.ValidateNull((String)session.getAttribute("role"));
				if(selectedRole.equals(role))
				{
					userRoleMapping( session, role);
				}
				msg = "Updated Successfully";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the rolemapmodule master detail in rolemapmodule table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in rolemapmodule master detail in rolemapmodule table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	private void userRoleMapping(HttpSession session, String role)
	{
		/***********SET Necessary Values to Session*************/
		String module;
		String screen;
		String access;
		
		boolean sesMstProduct = false;
		boolean sesMstDepartment = true;
		boolean sesMstEmployee = true;
		boolean sesMstMachine = true;
		boolean sesMstSupplier = false;
		boolean sesMstUserDetail = true;
		boolean sesMstUserMapping = true;
		
		boolean sesSTReceiptNote = false;
		boolean sesSTReceivingRequest = false;
		boolean sesSTReceivingInspection = false;
		boolean sesSTPeriodicInspectionRequest = true;
		boolean sesSTPeriodicInspectionRequestReport = true;
		
		boolean sesProductionRequestNote  = false;
		boolean sesProductionIssueNote  = false;
		boolean sesProductionReturnNote  = true;
		
		boolean sesChangePassword = true;
		
		HashMap<String, RoleVsUser> hmRoleVsUser = getIndividualRoleModuleMap(role);
		session.setAttribute("RoleVsUser", hmRoleVsUser);
		/*Iterator<RoleVsUser> itr = lstRoleVsUser.iterator();
		while(itr.hasNext())
		{
			RoleVsUser obj = itr.next();
			
			module = TiimUtil.ValidateNull(obj.getModuleName1()).trim();
			screen = TiimUtil.ValidateNull(obj.getScreenName1()).trim();
			access = TiimUtil.ValidateNull(obj.getAccessControl1()).trim();
			
			if("Master".equals(module))
			{
				if("Product".equals(screen) && "1".equals(access))
				{
					sesMstProduct = true;
				}
				else if("Supplier".equals(screen) && "1".equals(access))
				{
					sesMstSupplier = true;
				}
				else if("Department".equals(screen) && "1".equals(access))
				{
					sesMstDepartment = true;
				}
				else if("Employee".equals(screen) && "1".equals(access))
				{
					sesMstEmployee = true;
				}
				else if("Machine".equals(screen) && "1".equals(access))
				{
					sesMstMachine = true;
				}
				else if("User Detail".equals(screen) && "1".equals(access))
				{
					sesMstUserDetail = true;
				}
				else if("User vs role mapping".equals(screen) && "1".equals(access))
				{
					sesMstUserMapping = true;
				}
			}
			else if("Stores".equals(module))
			{
				if("Receipt Note".equals(screen) && "1".equals(access))
				{
					sesSTReceiptNote = true;
				}
				else if("Receiving Request".equals(screen) && "1".equals(access))
				{ 
					sesSTReceivingRequest = true;
				}
				else if("Receiving Inspection".equals(screen) && "1".equals(access))
				{ 
					sesSTReceivingInspection = true;
				}
			}
			else if("Production".equals(module))
			{
				if("Production Request Note".equals(screen) && "1".equals(access))
				{
					sesProductionRequestNote  = true;
				}
				else if("Production Issue Note".equals(screen) && "1".equals(access))
				{
					sesProductionIssueNote = true;
				}
			}
		}*/
		
		session.setAttribute("sesMstProduct", sesMstProduct);
		session.setAttribute("sesMstDepartment", sesMstDepartment);
		session.setAttribute("sesMstEmployee", sesMstEmployee);
		session.setAttribute("sesMstMachine", sesMstMachine);
		session.setAttribute("sesMstSupplier", sesMstSupplier);
		session.setAttribute("sesMstUserDetail", sesMstUserDetail);
		session.setAttribute("sesMstUserMapping", sesMstUserMapping);
		
		session.setAttribute("sesSTReceiptNote", sesSTReceiptNote);
		session.setAttribute("sesSTReceivingRequest", sesSTReceivingRequest);
		session.setAttribute("sesSTReceivingInspection", sesSTReceivingInspection);
		session.setAttribute("sesSTPeriodicInspectionRequest", sesSTPeriodicInspectionRequest);
		session.setAttribute("sesSTPeriodicInspectionRequestReport", sesSTPeriodicInspectionRequestReport);
		
		session.setAttribute("sesProductionRequestNote", sesProductionRequestNote);
		session.setAttribute("sesProductionIssueNote", sesProductionIssueNote);
		session.setAttribute("sesProductionReturnNote", sesProductionReturnNote);
		
		session.setAttribute("sesChangePassword", sesChangePassword);
		/*******************************************************/
	}
	
	public boolean isMappingExists( String roleName)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM rolemapmodule WHERE rolename = ?");
			 //pstmt.setString(1, TiimUtil.ValidateNull(moduleName).trim());
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
			System.out.println("Exception while checking the modulename exists in rolemapmodule table when adding : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in modulename master detail in rolemapmodule table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	
	}
	
	public List<RoleVsUser> getRoleModuleMap(String searchRole)
	{

		List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		RoleVsUser roleVsUser;
		try
		{
			con = datasource.getConnection();
			if(searchRole != null && !"".equals(searchRole))
			{
			    //pstmt = con.prepareStatement("Select rolemoduleid, modulename, rolename, addAccess, editAccess, viewaccess, deleteaccess, screenname, accesscontrol from rolemapmodule Where rolename like '%"+searchRole+"%' order by rolename");
				pstmt = con.prepareStatement("Select distinct rolename from rolemapmodule Where rolename like '%"+searchRole+"%' order by rolename");
			}
			else
			{
				//pstmt = con.prepareStatement("Select rolemoduleid, modulename, rolename, addAccess, editAccess, viewaccess, deleteaccess, screenname, accesscontrol from rolemapmodule order by rolename");
				pstmt = con.prepareStatement("Select distinct rolename from rolemapmodule order by rolename");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				roleVsUser = new RoleVsUser();
				/*roleVsUser.setRoleModuleId1(rs.getInt("rolemoduleid"));
				roleVsUser.setModuleName1(rs.getString("modulename"));*/
				roleVsUser.setRoleName1(rs.getString("rolename"));
				/*roleVsUser.setAddAccess1(rs.getString("addAccess"));
				roleVsUser.setEditAccess1(rs.getString("editAccess"));
				roleVsUser.setViewAccess1(rs.getString("viewaccess"));
				roleVsUser.setDeleteAccess1(rs.getString("deleteaccess"));
				roleVsUser.setScreenName1(rs.getString("screenname"));
				roleVsUser.setAccessControl1(rs.getString("accesscontrol"));*/
				lstRoleVsUser.add(roleVsUser);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire rolemapmodule in rolemapmodule table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire rolemap details in rolemapmodule table : "+ex.getMessage());
			}
		}
		return lstRoleVsUser;	
	
	}
	
	public HashMap<String, RoleVsUser> getIndividualRoleModuleMap(String roleName)
	{
		HashMap<String, RoleVsUser> hmRoleMap = new HashMap<String, RoleVsUser>();
		List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		RoleVsUser roleVsUser;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select rolemoduleid, modulename, rolename, addAccess, editAccess, viewaccess, deleteaccess, screenname, accesscontrol from rolemapmodule Where rolename = ? order by rolename");
			pstmt.setString(1, roleName);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				roleVsUser = new RoleVsUser();
				roleVsUser.setRoleModuleId1(rs.getInt("rolemoduleid"));
				roleVsUser.setModuleName1(rs.getString("modulename"));
				roleVsUser.setRoleName1(rs.getString("rolename"));
				roleVsUser.setAddAccess1(rs.getString("addAccess"));
				roleVsUser.setEditAccess1(rs.getString("editAccess"));
				roleVsUser.setViewAccess1(rs.getString("viewaccess"));
				roleVsUser.setDeleteAccess1(rs.getString("deleteaccess"));
				roleVsUser.setScreenName1(rs.getString("screenname"));
				roleVsUser.setAccessControl1(rs.getString("accesscontrol"));
				hmRoleMap.put(rs.getString("screenname"), roleVsUser);
				lstRoleVsUser.add(roleVsUser);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the individual rolemapmodule in rolemapmodule table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in individual rolemap details in rolemapmodule table : "+ex.getMessage());
			}
		}
		return hmRoleMap;	
	
	}
	
	public List<RoleVsUser> getIndividualRoleModuleList(String roleName)
	{
		List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		RoleVsUser roleVsUser;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select rolemoduleid, modulename, rolename, addAccess, editAccess, viewaccess, deleteaccess, screenname, accesscontrol from rolemapmodule Where rolename = ? order by modulename");
			pstmt.setString(1, roleName);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				roleVsUser = new RoleVsUser();
				roleVsUser.setRoleModuleId1(rs.getInt("rolemoduleid"));
				roleVsUser.setModuleName1(rs.getString("modulename"));
				roleVsUser.setRoleName1(rs.getString("rolename"));
				roleVsUser.setAddAccess1(rs.getString("addAccess"));
				roleVsUser.setEditAccess1(rs.getString("editAccess"));
				roleVsUser.setViewAccess1(rs.getString("viewaccess"));
				roleVsUser.setDeleteAccess1(rs.getString("deleteaccess"));
				roleVsUser.setScreenName1(rs.getString("screenname"));
				roleVsUser.setAccessControl1(rs.getString("accesscontrol"));
				lstRoleVsUser.add(roleVsUser);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the individual rolemapmodule in rolemapmodule table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in individual rolemap details in rolemapmodule table : "+ex.getMessage());
			}
		}
		return lstRoleVsUser;	
	
	}
	
	public List<RoleVsUser> getRestrictedRoleModuleList(String roleName)
	{
		List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		RoleVsUser roleVsUser;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT modulename, screenname FROM modules where screenname not in(select screenname from rolemapmodule Where rolename = ?)");
			pstmt.setString(1, roleName);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				roleVsUser = new RoleVsUser();
				
				roleVsUser.setModuleName1(rs.getString("modulename"));
				roleVsUser.setRoleName1(roleName);
				roleVsUser.setAddAccess1("0");
				roleVsUser.setEditAccess1("0");
				roleVsUser.setViewAccess1("0");
				roleVsUser.setDeleteAccess1("0");
				roleVsUser.setScreenName1(rs.getString("screenname"));
				roleVsUser.setAccessControl1("0");
				lstRoleVsUser.add(roleVsUser);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the individual rolemapmodule in rolemapmodule table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in individual getRestrictedRoleModuleList details in rolemapmodule table : "+ex.getMessage());
			}
		}
		return lstRoleVsUser;	
	
	}
	
	public String deleteRoleModule(String rolename)
	{
		System.out.println("Delete the rolename : "+rolename);
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from rolemapmodule where rolename = ?");
			pstmt.setString(1, rolename);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			System.out.println("Deleted the rolename : "+rolename);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the detail rolemapmodule table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the detail rolemapmodule table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<String> getRole()
	{
		List<String> lstRole = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select rolename from role");
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lstRole.add(rs.getString("rolename"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getRole in getRole table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire rolemap details in getRole table : "+ex.getMessage());
			}
		}
		return lstRole;	
	}
	
	public List<Modules> getModuleList()
	{
		List<Modules> lstModule = new ArrayList<Modules>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		Modules module = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select moduleId, modulename, screenname from modules");
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				module = new Modules();
				module.setModuleId(rs.getInt("moduleId"));
				module.setModuleName(rs.getString("modulename"));
				module.setScreenName(rs.getString("screenname"));
				lstModule.add(module);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getModuleList in getModuleList table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getModuleList details in getModuleList table : "+ex.getMessage());
			}
		}
		return lstModule;	
	
	}
	
	public List<String> getModules(){

		List<String> lstModule = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select  modulename from modules ");
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lstModule.add(rs.getString("modulename"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getModules in getModules table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getModules details in getModules table : "+ex.getMessage());
			}
		}
		return lstModule;	
	
	}
	
	public List<Modules> getScreenName(String moduleName)
	{

		List<Modules> lstModule = new ArrayList<Modules>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		Modules module = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select moduleId, modulename, screenname from modules where screenname = ?");
			pstmt.setString(1, moduleName);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				module = new Modules();
				module.setModuleId(rs.getInt("moduleId"));
				module.setModuleName(rs.getString("modulename"));
				module.setScreenName(rs.getString("screenname"));
				lstModule.add(module);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getModuleList in getModuleList table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getModuleList details in getModuleList table : "+ex.getMessage());
			}
		}
		return lstModule;	
	
	
	}
}

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

import com.tiim.model.UserScreenMap;
import com.tiim.util.TiimUtil;

@Repository
public class UserScreenMapDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	
	public String addScreenApprover(UserScreenMap userScreen, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
				if(isScreenAlreadyExists(userScreen.getScreenName()))
				{
					msg = "Screen Name already exists";
				}
				else
				{
					String userLevel = userScreen.getUserName();
					String userLevelInput[] = userLevel.split(",");
					for(int i=0;i<userLevelInput.length;i++)
					{
						String levelDetails = userLevelInput[i];
						String user[] = levelDetails.split("#");
					
						pstmt = con.prepareStatement("insert into screen_approver_map(screenname,username,levelnumber,numberoflevel) "
								+ "values(?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(userScreen.getScreenName()).trim());
						pstmt.setString(2, TiimUtil.ValidateNull(user[0]).trim());
						pstmt.setInt(3, Integer.parseInt(user[1])+1);
						pstmt.setInt(4, userScreen.getNoOfLevels());
						pstmt.executeUpdate();
					}
					
					TransactionHistory history = new TransactionHistory();
					history.setPageName(messageSource.getMessage("machine.page", null,null));
					history.setDescription(messageSource.getMessage("machine.insert", null,null));
					history.setUserId(userId);
					historyDao.addHistory(history);
					msg = "Saved Successfully";
				}
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the user screen map : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in machine master detail in mst_machine table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String updateUserMap(UserScreenMap userScreen, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update screen_approver_map set screenname = ?,username = ?,levelnumber = ?,numberoflevel = ? where screenid = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(userScreen.getScreenName()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(userScreen.getUserName()).trim());
				pstmt.setInt(3, userScreen.getLevel());
				pstmt.setInt(4, userScreen.getNoOfLevels());
				pstmt.setInt(5, userScreen.getScreenId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("machine.page", null,null));
				history.setDescription(messageSource.getMessage("machine.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the user screen map  : "+ex.getMessage());
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
	
	public String deleteUserMap(int screenId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from screen_approver_map where screenid = ?");
			pstmt.setInt(1, screenId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("machine.page", null,null));
			history.setDescription(messageSource.getMessage("machine.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the deleteUserMap : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the deleteUserMap : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteUserMap(String screenName, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from screen_approver_map where screenname = ?");
			pstmt.setString(1, screenName);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("machine.page", null,null));
			history.setDescription(messageSource.getMessage("machine.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the deleteUserMap : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the deleteUserMap : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<UserScreenMap> getUserMapDetails(String searchScreenName)
	{
		List<UserScreenMap> lstScreenMap = new ArrayList<UserScreenMap>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		UserScreenMap screenMap;
		try
		{
			con = datasource.getConnection();
			if(searchScreenName != null && !"".equals(searchScreenName))
			{
			    pstmt = con.prepareStatement("select max(screenid) screenid, screenname,username,levelnumber,numberoflevel from screen_approver_map where screenname like '%"+searchScreenName+"%' group by screenname order by screenid");
			}
			else
			{
				pstmt = con.prepareStatement("select max(screenid) screenid, screenname,username,levelnumber,numberoflevel from screen_approver_map group by screenname order by screenname");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				screenMap = new UserScreenMap();
				screenMap.setScreenId(rs.getInt("screenid"));
				screenMap.setScreenName(TiimUtil.ValidateNull(rs.getString("screenname")).trim());
				screenMap.setUserName(TiimUtil.ValidateNull(rs.getString("username")).trim());
				screenMap.setLevel(rs.getInt("levelnumber"));
				screenMap.setNoOfLevels(rs.getInt("numberoflevel"));
				lstScreenMap.add(screenMap);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getUserMapDetails in screen_approver_map table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getUserMapDetails in screen_approver_map table : "+ex.getMessage());
			}
		}
		return lstScreenMap;	
	}
	
	public UserScreenMap getUserScreenMap(int screenId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		UserScreenMap screenMap = new UserScreenMap();;
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("select screenid, screenname,username,levelnumber,numberoflevel from screen_approver_map where screenid = ?");
		    pstmt.setInt(1, screenId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				screenMap.setScreenId(rs.getInt("screenid"));
				screenMap.setScreenName(TiimUtil.ValidateNull(rs.getString("screenname")).trim());
				screenMap.setUserName(TiimUtil.ValidateNull(rs.getString("username")).trim());
				screenMap.setLevel(rs.getInt("levelnumber"));
				screenMap.setNoOfLevels(rs.getInt("numberoflevel"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getUserMapDetails in screen_approver_map table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getUserMapDetails in screen_approver_map table : "+ex.getMessage());
			}
		}
		return screenMap;	
	}
	
	public List<UserScreenMap>  getUserScreenMap(int screenId, String screenName)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		UserScreenMap screenMap = new UserScreenMap();
		List<UserScreenMap> lstScreenMap = new ArrayList<UserScreenMap>();
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("select screenid, screenname,username,levelnumber,numberoflevel from screen_approver_map where screenname = ?");
		    pstmt.setString(1, screenName);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				screenMap = new UserScreenMap();
				screenMap.setScreenId(rs.getInt("screenid"));
				screenMap.setScreenName(TiimUtil.ValidateNull(rs.getString("screenname")).trim());
				screenMap.setUserName(TiimUtil.ValidateNull(rs.getString("username")).trim());
				screenMap.setLevel(rs.getInt("levelnumber"));
				screenMap.setNoOfLevels(rs.getInt("numberoflevel"));
				lstScreenMap.add(screenMap);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getUserMapDetails in screen_approver_map table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getUserMapDetails in screen_approver_map table : "+ex.getMessage());
			}
		}
		return lstScreenMap;	
	}
	
	public List<String> getScreenName()
	{
		List<String> lstScreenName = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			
				pstmt = con.prepareStatement("SELECT distinct screenname FROM modules order by screenname");
		
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lstScreenName.add(rs.getString("screenname"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getScreenName : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getScreenName : "+ex.getMessage());
			}
		}
		return lstScreenName;	
	}
	
	public List<String> getUserName()
	{
		List<String> lstUserName = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			
				pstmt = con.prepareStatement("SELECT username FROM mst_user");
		
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lstUserName.add(rs.getString("username"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getUserName : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getUserName : "+ex.getMessage());
			}
		}
		return lstUserName;	
	}
	
	public boolean isScreenAlreadyExists(String screenName)
	{
		boolean returnValue = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select screenname from screen_approver_map where screenname = ?");
			pstmt.setString(1, screenName);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				returnValue = true;
			}
			
			
		}catch(Exception ex)
		{
			returnValue = false;
			System.out.println("Exception when getting the isScreenAlreadyExists : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in isScreenAlreadyExists : "+ex.getMessage());
			}
		}
		
		return returnValue;
		
	}
	
}

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

import com.tiim.model.Plating;
import com.tiim.util.TiimUtil;

@Repository
public class PlatingDao {



	@Autowired
	DataSource datasource;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	public String addPlating(Plating plating, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isPlatingExists(plating.getPlatingName()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_plating(plating, active)"
						+ "values(?, ?)");
				pstmt.setString(1, TiimUtil.ValidateNull(plating.getPlatingName()).trim());
				pstmt.setInt(2, 1);
				
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				
				/*
				 * TransactionHistory history = new TransactionHistory();
				 * history.setPageName(messageSource.getMessage("Role.page", null,null));
				 * history.setDescription(messageSource.getMessage("Role.insert", null,null));
				 * history.setUserId(userId); historyDao.addHistory(history);
				 */
			}
			else
			{
				msg = "Already Exists";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the plating master detail in plating table : "+ex.getMessage());
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
				System.out.println("Exception when adding the plating master detail in plating table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updatePlating(Plating plating, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("update mst_plating set plating = ?, active = ?  where id = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(plating.getPlatingName()).trim());
				pstmt.setInt(2, 1);
				pstmt.setInt(3, plating.getId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				
				/*
				 * TransactionHistory history = new TransactionHistory();
				 * history.setPageName(messageSource.getMessage("role.page", null,null));
				 * history.setDescription(messageSource.getMessage("role.update", null,null));
				 * history.setUserId(userId); historyDao.addHistory(history);
				 */
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the plating detail in plating table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in plating detail in plating table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<Plating> getPlatingDetails(String platingName)
	{
		List<Plating> platings = new ArrayList<Plating>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Plating plating;
		try
		{
			con = datasource.getConnection();
			if(platingName != null && !"".equals(platingName))
			{
			    pstmt = con.prepareStatement("select id, plating, active from mst_plating Where plating like '%"+platingName+"%' order by id");
			}
			else
			{
				pstmt = con.prepareStatement("select id, plating, active from mst_plating order by plating");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				plating = new Plating();
				plating.setId(rs.getInt("id"));
				plating.setPlatingName(rs.getString("plating"));
				plating.setActive(rs.getInt("active"));
				platings.add(plating);
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
		return platings;	
	}
	
	
	public List<Plating> getPlatingDetails()
	{
		List<Plating> lstPlatings = new ArrayList<Plating>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Plating plating;
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("select id, plating, active from mst_plating where active = 1 order by plating");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				plating = new Plating();
				plating.setPlatingName(rs.getString("plating"));
				plating.setId(rs.getInt("id"));
				plating.setActive(rs.getInt("active"));
				
				lstPlatings.add(plating);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the PlatingDetails in plating table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire PlatingDetails in plating table : "+ex.getMessage());
			}
		}
		return lstPlatings;	
	}
	
	public String deletePlating(int plateId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_plating where id = ?");
			pstmt.setInt(1, plateId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			/*
			 * TransactionHistory history = new TransactionHistory();
			 * history.setPageName(messageSource.getMessage("role.page", null,null));
			 * history.setDescription(messageSource.getMessage("role.delete", null,null));
			 * history.setUserId(userId); historyDao.addHistory(history);
			 */
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the plating detail in plating table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the plating detail in plating table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public Plating getPlating(int plateId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Plating plating = new Plating();
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("select id, plating, active from mst_plating where id = ?");
		    pstmt.setInt(1, plateId);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				plating.setActive(rs.getInt("active"));
				plating.setPlatingName(rs.getString("plating"));
				plating.setId(rs.getInt("id"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the mst_plating in mst_plating table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire Plating in Plating table : "+ex.getMessage());
			}
		}
		return plating;	
	
	}
	
	public String changePlatingStatus( int platingId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select active from mst_plating where id = ?");
				pstmt.setInt(1, platingId);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					isActive = rs.getInt("active");
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
				
				pstmt = con.prepareStatement("Update mst_plating set active = ?  where id = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, platingId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of Plating in plate table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in changing the status of Plating in Plate table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	private boolean isPlatingExists(String platingName)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_plating WHERE plating = ?");
			 pstmt.setString(1, TiimUtil.ValidateNull(platingName).trim());
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
			System.out.println("Exception while checking the plating name exists in mst_plating table when adding : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in plating master detail in plating table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	}
	

}

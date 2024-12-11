package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.Modules;

@Repository
public class ModuleDao {

	@Autowired
	DataSource datasource;
	
	public List<Modules> getModuleDetails()
	{
		List<Modules> lstModules = new ArrayList<Modules>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Modules mod;
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("Select moduleid, modulename, screenname from modules where isactive = 1 order by modulename");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				mod = new Modules();
				mod.setModuleId(rs.getInt("moduleid"));
				mod.setModuleName(rs.getString("modulename"));
				mod.setScreenName(rs.getString("screenname"));
				lstModules.add(mod);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the ModuleDetails in module table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire ModuleDetails in module table : "+ex.getMessage());
			}
		}
		return lstModules;	
	}
	
}

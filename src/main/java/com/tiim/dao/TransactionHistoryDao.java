package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.util.TiimUtil;

@Repository
public class TransactionHistoryDao {

	@Autowired
	DataSource datasource;
	
	public void addHistory(TransactionHistory history)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into transcation_history(userId, pagename, description, accesstime) "
						+ "values(?, ?, ?, now())");
				//,Statement.RETURN_GENERATED_KEYS
				pstmt.setInt(1, history.getUserId());
				pstmt.setString(2, TiimUtil.ValidateNull(history.getPageName()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(history.getDescription()).trim());

				pstmt.executeUpdate();
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the addHistory : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection inaddHistory : "+ex.getMessage());
			}
		}	
	}
}

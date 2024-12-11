package com.tiim.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.DestructionSerialId;
import com.tiim.model.ToolDestructionNote;
import com.tiim.model.ToolSerialNumber;

@Repository
public class ToolSerialNumberDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	public void addSerialNumber(ToolSerialNumber toolSerialNumber) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("insert into tool_serial_number(lotnumber, serialNumber, module, rejectQty, acceptQty) values(?, ?, ?, ?, ?)");
			pstmt.setString(1, toolSerialNumber.getLotNumber());
			if(toolSerialNumber.getSerialNumber() != null && (toolSerialNumber.getSerialNumber().equalsIgnoreCase("na") || (toolSerialNumber.getSerialNumber().equalsIgnoreCase("0")))) {
				pstmt.setString(2, "");
			}else {
				pstmt.setString(2, toolSerialNumber.getSerialNumber());
			}			
			pstmt.setString(3, toolSerialNumber.getModule());
			pstmt.setInt(4, toolSerialNumber.getRejectQty());
			pstmt.setInt(5, toolSerialNumber.getAcceptQty());
			pstmt.executeUpdate();	
			
		}catch(Exception ex)
		{
			System.out.println("Exception when add the serial number : "+ex.getMessage());
			ex.printStackTrace();
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
				System.out.println("Exception when closing the connection in add the serial number : "+ex.getMessage());
			}
		}	
	}
	
	public List<ToolSerialNumber> getSerialNumber(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		List<ToolSerialNumber> lstSerialNumber = new ArrayList<ToolSerialNumber>();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select serialId, lotnumber, serialNumber, module, rejectQty, acceptQty from tool_serial_number");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ToolSerialNumber serialNumber = new ToolSerialNumber();
				serialNumber.setSerialId(rs.getLong("serialId"));
				serialNumber.setLotNumber(rs.getString("lotnumber"));
				serialNumber.setSerialNumber(rs.getString("serialNumber"));
				serialNumber.setModule(rs.getString("module"));
				serialNumber.setRejectQty(rs.getInt("rejectQty"));
				serialNumber.setAcceptQty(rs.getInt("acceptQty"));
				lstSerialNumber.add(serialNumber);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the serial number : "+ex.getMessage());
			ex.printStackTrace();
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
				System.out.println("Exception when closing the connection in delete the serial number : "+ex.getMessage());
			}
		}	
	
		return lstSerialNumber;
	}
	
	public List<ToolSerialNumber> getSerialNumbersByLotNumber(String lotNumber){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		int total = 0;
		List<ToolSerialNumber> lstSerialNumber = new ArrayList<ToolSerialNumber>();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select serialId, lotnumber, serialNumber, module, rejectQty, acceptQty from tool_serial_number where lotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ToolSerialNumber serialNumber = new ToolSerialNumber();
				serialNumber.setSerialId(rs.getLong("serialId"));
				serialNumber.setLotNumber(rs.getString("lotnumber"));
				serialNumber.setSerialNumber(rs.getString("serialNumber"));
				serialNumber.setModule(rs.getString("module"));
				serialNumber.setRejectQty(rs.getInt("rejectQty"));
				serialNumber.setAcceptQty(rs.getInt("acceptQty"));
				total = total + rs.getInt("rejectQty");
				serialNumber.setTotalRejectedQty(total);
				lstSerialNumber.add(serialNumber);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the serial number : "+ex.getMessage());
			ex.printStackTrace();
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
				System.out.println("Exception when closing the connection in delete the serial number : "+ex.getMessage());
			}
		}	
	
		return lstSerialNumber;
	}
	
	
	public void deleteSerialNumber(String lotNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from tool_serial_number where lotnumber = ?");
			pstmt.setString(1, lotNumber);
			
			pstmt.executeUpdate();	
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the serial number : "+ex.getMessage());
			ex.printStackTrace();
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
				System.out.println("Exception when closing the connection in delete the serial number : "+ex.getMessage());
			}
		}	
	
	}
	
	public List<ToolDestructionNote> approveSerialNoForDestruction(DestructionSerialId destructionSerialId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ToolDestructionNote> toolDestructionNotes = new ArrayList<ToolDestructionNote>();
		int[] serialIds = destructionSerialId.getSerialId();
		try {
			con = datasource.getConnection();
			if (serialIds != null) {
				for (int i = 0; i < serialIds.length; i++) {
					System.out.println("serialIds[i]: "+serialIds[i]);
					pstmt = con.prepareStatement(
							"update tool_serial_number set approvelForDestruction = 1  where serialId = ? ");
					pstmt.setInt(1, serialIds[i]);
					pstmt.executeUpdate();
					
				}
			}

		} catch (Exception ex) {
			System.out.println("Exception when delete the serial number : " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in delete the serial number : " + ex.getMessage());
			}
		}

		return toolDestructionNotes;

	}
	
}

package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.SerialNumber;
import com.tiim.model.ToolingInspection;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingPeriodicInspection;
import com.tiim.model.ToolingPeriodicInspectionReport;
import com.tiim.model.ToolingReturnNote;

@Repository
public class AddSerialNumberDao {
	@Autowired
	DataSource datasource;
	
	
	public void addSerialNumber(ToolingInspection serialNumber)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("insert into serial_number_details(lotnumber, serialNumber, approved) values(?, ?, ?)");
			int[] serNumber = serialNumber.getSerialNumber1();
			int[] approveFlag = serialNumber.getApprovedQty();
			for(int i = 0; i < serNumber.length; i++)
			{
				pstmt.setString(1, serialNumber.getLotNumber());
				pstmt.setInt(2, serNumber[i]);
				pstmt.setInt(3, approveFlag[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
			
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
	
	public void updateSerialNumber(ToolingInspection serialNumber)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			int[] serNumber = serialNumber.getSerialNumber1();
			int[] approveFlag = serialNumber.getApprovedQty();
			int[] serialId = serialNumber.getSerialId();
			for(int i = 0; i < serNumber.length; i++)
			{
				try
				{
				pstmt = con.prepareStatement("update serial_number_details set lotnumber = ?, serialNumber = ?, approved = ? where serialId = ?");
				pstmt.setString(1, serialNumber.getLotNumber());
				pstmt.setInt(2, serNumber[i]);
				pstmt.setInt(3, approveFlag[i]);
				pstmt.setInt(4, serialId[i]);
				pstmt.executeUpdate();
				}finally
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
						
					}catch(Exception ex)
					{
						System.out.println("Exception when closing the connection in add the serial number : "+ex.getMessage());
					}
				}
			
			}
		
		}catch(Exception ex)
		{
			System.out.println("Exception when update the serial number : "+ex.getMessage());
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
	
	public void updateSerialNumber(ToolingIssueNote issueNote)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			int[] approveFlag = issueNote.getApprovedQty();
			
			for(int i = 0; i < approveFlag.length; i++)
			{
				try
				{
				pstmt = con.prepareStatement("update serial_number_details set stockflag = ? where serialNumber = ? and lotnumber = ?");
				pstmt.setInt(1, 1);
				pstmt.setInt(2, approveFlag[i]);
				pstmt.setString(3, issueNote.getToolingLotNumber()[0]);
				
				pstmt.executeUpdate();
				}finally
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
						
					}catch(Exception ex)
					{
						System.out.println("Exception when closing the connection in add the serial number : "+ex.getMessage());
					}
				}
			
			}
		
		}catch(Exception ex)
		{
			System.out.println("Exception when update the serial number : "+ex.getMessage());
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
	
	public void updateSerialNumber(ToolingPeriodicInspection periodicInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			int[] approveFlag = periodicInspection.getApprovedQty();
			
			for(int i = 0; i < approveFlag.length; i++)
			{
				try
				{
				pstmt = con.prepareStatement("update serial_number_details set stockflag = ? where serialNumber = ? and lotnumber = ?");
				pstmt.setInt(1, 3);
				pstmt.setInt(2, approveFlag[i]);
				pstmt.setString(3, periodicInspection.getToolingLotNo()[0]);
				
				pstmt.executeUpdate();
				}finally
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
						
					}catch(Exception ex)
					{
						System.out.println("Exception when closing the connection in add the serial number : "+ex.getMessage());
					}
				}
			
			}
		
		}catch(Exception ex)
		{
			System.out.println("Exception when update the serial number : "+ex.getMessage());
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
	
	public void updateReturnSerialNumber(ToolingReturnNote toolingReturnNote)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			int[] approveFlag = toolingReturnNote.getApprovedQty();
			int[] serialId = toolingReturnNote.getSerialId();
			for(int i = 0; i < approveFlag.length; i++)
			{
				try
				{
					pstmt = con.prepareStatement("update serial_number_details set lotnumber = ?,  approved = ?, stockflag = ? where serialId = ?");
					pstmt.setString(1, toolingReturnNote.getToolingLotNumber1());
					pstmt.setInt(2, approveFlag[i]);
					if(approveFlag[i] == 1)
					{
						pstmt.setInt(3, 2);
					}
					else
					{
						pstmt.setInt(3, 1);
					}
					pstmt.setInt(4, serialId[i]);
					pstmt.executeUpdate();
				}finally
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
						
					}catch(Exception ex)
					{
						System.out.println("Exception when closing the connection in add the serial number : "+ex.getMessage());
					}
				}
			
			}
		
		}catch(Exception ex)
		{
			System.out.println("Exception when update the serial number : "+ex.getMessage());
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
	
	public void updatePeriodicReportSerialNumber(ToolingPeriodicInspectionReport periodicInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			int[] approveFlag = periodicInspection.getApprovedQty();
			int[] serialId = periodicInspection.getSerialId();
			for(int i = 0; i < approveFlag.length; i++)
			{
				try
				{
					pstmt = con.prepareStatement("update serial_number_details set lotnumber = ?,  approved = ?, stockflag = ? where serialId = ?");
					pstmt.setString(1, periodicInspection.getToolingLotNumber());
					pstmt.setInt(2, approveFlag[i]);
					if(approveFlag[i] == 1)
					{
						pstmt.setInt(3, 4);
					}
					else
					{
						pstmt.setInt(3, 3);
					}
					pstmt.setInt(4, serialId[i]);
					pstmt.executeUpdate();
				}finally
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
						
					}catch(Exception ex)
					{
						System.out.println("Exception when closing the connection in add the serial number : "+ex.getMessage());
					}
				}
			
			}
		
		}catch(Exception ex)
		{
			System.out.println("Exception when update the serial number : "+ex.getMessage());
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
	
	
	public List<SerialNumber> getSerialNumber(String lotNumber)
	{
		List<SerialNumber> lstSerialNumber = new ArrayList<>();
		SerialNumber serial;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select lotnumber, serialNumber, serialId, approved, stockflag from serial_number_details where lotnumber=? order by serialId");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				serial = new SerialNumber();
				serial.setLotNumber(rs.getString("lotnumber"));
				serial.setSerialNumber(rs.getString("serialNumber"));
				serial.setSerialId(rs.getInt("serialId"));
				serial.setApprovedFlag(rs.getInt("approved"));
				serial.setStockFlag(rs.getInt("stockflag"));
				lstSerialNumber.add(serial);
			}			
			
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
		return lstSerialNumber;
	
	}
	
	public List<SerialNumber> getIssueSerialNumber(String lotNumber)
	{
		List<SerialNumber> lstSerialNumber = new ArrayList<>();
		SerialNumber serial;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select lotnumber, serialNumber, serialId, approved, stockflag from serial_number_details where lotnumber = ? and approved = ? order by serialId");
			pstmt.setString(1, lotNumber);
			pstmt.setInt(2, 1);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				serial = new SerialNumber();
				serial.setLotNumber(rs.getString("lotnumber"));
				serial.setSerialNumber(rs.getString("serialNumber"));
				serial.setSerialId(rs.getInt("serialId"));
				serial.setApprovedFlag(rs.getInt("approved"));
				serial.setStockFlag(rs.getInt("stockflag"));
				lstSerialNumber.add(serial);
			}			
			
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
		return lstSerialNumber;
	
	}
	
	public List<SerialNumber> getPeriodicSerialNumber(String lotNumber)
	{
		List<SerialNumber> lstSerialNumber = new ArrayList<>();
		SerialNumber serial;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select lotnumber, serialNumber, serialId, approved, stockflag from serial_number_details where lotnumber = ? and stockflag = ? order by serialId");
			pstmt.setString(1, lotNumber);
			pstmt.setInt(2, 2);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				serial = new SerialNumber();
				serial.setLotNumber(rs.getString("lotnumber"));
				serial.setSerialNumber(rs.getString("serialNumber"));
				serial.setSerialId(rs.getInt("serialId"));
				serial.setApprovedFlag(rs.getInt("approved"));
				serial.setStockFlag(rs.getInt("stockflag"));
				lstSerialNumber.add(serial);
			}			
			
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
		return lstSerialNumber;
	
	}
	
	public List<SerialNumber> getReturnSerialNumber(String lotNumber)
	{
		List<SerialNumber> lstSerialNumber = new ArrayList<>();
		SerialNumber serial;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select lotnumber, serialNumber, serialId, approved, stockflag from serial_number_details where lotnumber = ? and stockflag > ? order by serialId");
			pstmt.setString(1, lotNumber);
			pstmt.setInt(2, 0);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				serial = new SerialNumber();
				serial.setLotNumber(rs.getString("lotnumber"));
				serial.setSerialNumber(rs.getString("serialNumber"));
				serial.setSerialId(rs.getInt("serialId"));
				serial.setApprovedFlag(rs.getInt("approved"));
				serial.setStockFlag(rs.getInt("stockflag"));
				lstSerialNumber.add(serial);
			}			
			
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
		return lstSerialNumber;
	
	}
	public List<SerialNumber> getPeriodicReportSerialNumber(String lotNumber)
	{
		List<SerialNumber> lstSerialNumber = new ArrayList<>();
		SerialNumber serial;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select lotnumber, serialNumber, serialId, approved, stockflag from serial_number_details where lotnumber = ? and stockflag > ? order by serialId");
			pstmt.setString(1, lotNumber);
			pstmt.setInt(2, 2);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				serial = new SerialNumber();
				serial.setLotNumber(rs.getString("lotnumber"));
				serial.setSerialNumber(rs.getString("serialNumber"));
				serial.setSerialId(rs.getInt("serialId"));
				serial.setApprovedFlag(rs.getInt("approved"));
				serial.setStockFlag(rs.getInt("stockflag"));
				lstSerialNumber.add(serial);
			}			
			
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
		return lstSerialNumber;
	
	}
}

package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.tiim.model.AutoSerialNumber;
import com.tiim.model.AutoSerialNumberList;

@Repository
public class AutoSerialNumberDao {

	@Autowired
	DataSource datasource;
	private final String RECEIVE_SERIAL = "INITIAL INSPECTION REPORT";
	private final String RETURN_SERIAL = "RE INSPECTION";
	private final String PERIODIC_SERIAL = "PERIODIC INSPECTION";
		
	@Scheduled(fixedRate = 5000)
	public List<AutoSerialNumber> editToolingInspection()
	{
		List<AutoSerialNumber> lstSerialNumber = new ArrayList<>();
		List<String> lstAcceptedQty = new ArrayList<>(); 
		List<String> lstRejectedQty = new ArrayList<>();
		Map<String, AutoSerialNumberList> hmSerialNumber = new HashMap<String, AutoSerialNumberList>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select ins_type, ins_no, lot_no, acc_qty, rej_qty, miss_qty, tot_qty from test_data");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				AutoSerialNumberList autoSerialNumber = new AutoSerialNumberList();
				autoSerialNumber.setInsType(rs.getString("ins_type"));
				autoSerialNumber.setInsNo(rs.getString("ins_no"));
				autoSerialNumber.setLotNumber(rs.getString("lot_no"));
				
				String serialNumber = rs.getString("acc_qty");
				String rejected = rs.getString("rej_qty");
				String missed = rs.getString("miss_qty");
				String acceptedSerial[] = serialNumber.split(",");
				String rejectedSerial[] = rejected.split(",");
				String missedSerial[] = missed.split(",");
				
				if(lstAcceptedQty != null && lstAcceptedQty.size() == 0)
				{
					for(int i=0; i<acceptedSerial.length; i++)
					{
						lstAcceptedQty.add(acceptedSerial[i]);
					}
				}
				
					for(int i=0; i<rejectedSerial.length; i++)
					{
						if(!lstRejectedQty.contains(rejectedSerial[i]))
						{
							lstRejectedQty.add(rejectedSerial[i]);
						}
						if(lstAcceptedQty.contains(rejectedSerial[i]))
						{
							lstAcceptedQty.remove(rejectedSerial[i]);
						}
					}
					
					for(int i=0; i<missedSerial.length; i++)
					{
						if(!lstRejectedQty.contains(missedSerial[i]))
						{
							lstRejectedQty.add(missedSerial[i]);
						}
						if(lstAcceptedQty.contains(missedSerial[i]))
						{
							lstAcceptedQty.remove(missedSerial[i]);
						}
					}
				autoSerialNumber.setAcceptedQty(lstAcceptedQty);
				autoSerialNumber.setRejectedQty(lstRejectedQty);
				autoSerialNumber.setTotalQty(rs.getInt("tot_qty"));
				/*if(RECEIVE_SERIAL.equalsIgnoreCase(autoSerialNumber.getInsType()))
				{
					getToolingReceivingSerialNumber(autoSerialNumber);
				}else if(RETURN_SERIAL.equalsIgnoreCase(autoSerialNumber.getInsType()))
				{
					getToolingReceivingSerialNumber(autoSerialNumber);
				}else if(PERIODIC_SERIAL.equalsIgnoreCase(autoSerialNumber.getInsType()))
				{
					getToolingReceivingSerialNumber(autoSerialNumber);
				}*/
				hmSerialNumber.put(rs.getString("lot_no"), autoSerialNumber);
				//lstSerialNumber.add(autoSerialNumber);
				
			}
			
			for (Map.Entry<String, AutoSerialNumberList> entry : hmSerialNumber.entrySet()) {
				AutoSerialNumberList autoSerialNumberList = entry.getValue();
				AutoSerialNumber autoSerialNumber = new AutoSerialNumber();
				autoSerialNumber.setLotNumber(autoSerialNumberList.getLotNumber());
				autoSerialNumber.setInsNo(autoSerialNumberList.getInsNo());
				autoSerialNumber.setInsType(autoSerialNumberList.getInsType());
				List<String> lstAccepted = autoSerialNumberList.getAcceptedQty();
				List<String> lstRejected = autoSerialNumberList.getRejectedQty();
				autoSerialNumber.setTotalQty(autoSerialNumberList.getTotalQty());
				String acceptedQty = new String();
				String rejectedQty = new String();
				for(int i = 0; i<lstAccepted.size(); i++)
				{
					if(i == lstAccepted.size() - 1)
					{
						acceptedQty = acceptedQty + lstAccepted.get(i);
					}else
					{
						acceptedQty = acceptedQty + lstAccepted.get(i) + ",";
					}
				}
				for(int i = 0; i<lstRejected.size(); i++)
				{
					rejectedQty = rejectedQty + lstRejected.get(i) + ",";
					/*if(i == lstAccepted.size() - 1)
					{
						rejectedQty = rejectedQty + lstRejected.get(i);
					}else
					{
						rejectedQty = rejectedQty + lstRejected.get(i) + ",";
					}*/
				}
				autoSerialNumber.setAcceptedQty(acceptedQty);
				autoSerialNumber.setRejectedQty(rejectedQty);
				System.out.println(acceptedQty+", "+rejectedQty);
				if(RECEIVE_SERIAL.equalsIgnoreCase(autoSerialNumber.getInsType()))
				{
					getToolingReceivingSerialNumber(autoSerialNumber);
				}else if(RETURN_SERIAL.equalsIgnoreCase(autoSerialNumber.getInsType()))
				{
					getToolingReceivingSerialNumber(autoSerialNumber);
				}else if(PERIODIC_SERIAL.equalsIgnoreCase(autoSerialNumber.getInsType()))
				{
					getToolingReceivingSerialNumber(autoSerialNumber);
				}
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when getting the editToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  editToolingInspection : "+ex.getMessage());
			}
		}
	
		return lstSerialNumber;
	}
	
	public void getToolingReceivingSerialNumber(AutoSerialNumber serialNumber)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select toolinginspectiondetailid FROM tooling_receiving_inspection_details where lotnumber = ? order by revisionnumber desc");
			pstmt.setString(1, serialNumber.getLotNumber());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				updateToolingReceivingSerialNumber(serialNumber, rs.getInt("toolinginspectiondetailid"));	
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when getting the updateToolingReceivingSerialNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  updateToolingReceivingSerialNumber : "+ex.getMessage());
			}
		}
	
	}
	
	public void updateToolingReceivingSerialNumber(AutoSerialNumber serialNumber, int toolingInspectionDetailId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			int acceptedQty = 0;
			int rejectedQty = 0;
			String acceptedSerial[] = serialNumber.getAcceptedQty().split(",");
			if(acceptedSerial != null)
			{
				acceptedQty = acceptedSerial.length;
				rejectedQty = serialNumber.getTotalQty() - acceptedQty;
			}
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_receiving_inspection_details set acceptedQty = ?, rejectedqty = ?, rejectedserialnumber = ? where toolinginspectiondetailid = ?");
			
			pstmt.setInt(1, acceptedQty);
			pstmt.setInt(2, rejectedQty);
			pstmt.setString(3, serialNumber.getRejectedQty());
			pstmt.setInt(4, toolingInspectionDetailId);
			pstmt.executeUpdate();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when getting the updateToolingReceivingSerialNumber : "+ex.getMessage());
		}
		finally
		{
			try
			{
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
				System.out.println("Exception when closing the connection in  updateToolingReceivingSerialNumber : "+ex.getMessage());
			}
		}
	
	}
	
	public void getReturnSerialNumber(AutoSerialNumber serialNumber)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT returnnotedetailid FROM tooling_production_return_note_detail where toolinglotnumber = ? order by returnnotedetailid desc");
			pstmt.setString(1, serialNumber.getLotNumber());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				updateReturnSerialNumber(serialNumber, rs.getInt("returnnotedetailid"));	
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when getting the getReturnSerialNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getReturnSerialNumber : "+ex.getMessage());
			}
		}
	
	}
	
	public void updateReturnSerialNumber(AutoSerialNumber serialNumber, int returnnoteDetailId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			int acceptedQty = 0;
			int rejectedQty = 0;
			String acceptedSerial[] = serialNumber.getAcceptedQty().split(",");
			if(acceptedSerial != null)
			{
				acceptedQty = acceptedSerial.length;
				rejectedQty = serialNumber.getTotalQty() - acceptedQty;
			}
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_production_return_note_detail set goodqty = ?, damagedqty = ?, damagedserialnumber = ? where returnnotedetailid = ?");
			
			pstmt.setInt(1, acceptedQty);
			pstmt.setInt(2, rejectedQty);
			pstmt.setString(3, serialNumber.getRejectedQty() );
			pstmt.setInt(4, returnnoteDetailId);
			pstmt.executeUpdate();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when getting the updateReturnSerialNumber : "+ex.getMessage());
		}
		finally
		{
			try
			{
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
				System.out.println("Exception when closing the connection in  updateReturnSerialNumber : "+ex.getMessage());
			}
		}
	
	}
	
	public void getPeriodicSerialNumber(AutoSerialNumber serialNumber)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT reportdetailno FROM periodic_inspection_report_detail where toolinglotnumber = ? order by reportdetailno desc");
			pstmt.setString(1, serialNumber.getLotNumber());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				updateReturnSerialNumber(serialNumber, rs.getInt("reportdetailno"));	
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when getting the getPeriodicSerialNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicSerialNumber : "+ex.getMessage());
			}
		}
	
	}
	
	public void updatePeriodicSerialNumber(AutoSerialNumber serialNumber, int reportDetailNo)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			int acceptedQty = 0;
			int rejectedQty = 0;
			String acceptedSerial[] = serialNumber.getAcceptedQty().split(",");
			if(acceptedSerial != null)
			{
				acceptedQty = acceptedSerial.length;
				rejectedQty = serialNumber.getTotalQty() - acceptedQty;
			}
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update periodic_inspection_report_detail set goodqty = ?, damagedqty = ?, damagedserialnumber = ? where reportdetailno = ?");
			
			pstmt.setInt(1, acceptedQty);
			pstmt.setInt(2, rejectedQty);
			pstmt.setString(3, serialNumber.getRejectedQty() +","+serialNumber.getMissedQty());
			pstmt.setInt(4, reportDetailNo);
			pstmt.executeUpdate();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when getting the updatePeriodicSerialNumber : "+ex.getMessage());
		}
		finally
		{
			try
			{
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
				System.out.println("Exception when closing the connection in  updatePeriodicSerialNumber : "+ex.getMessage());
			}
		}
	
	}
}

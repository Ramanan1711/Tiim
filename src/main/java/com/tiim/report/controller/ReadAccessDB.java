package com.tiim.report.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tiim.model.OriginalSerialNumber;

@Component
public class ReadAccessDB {

	@Autowired
	DataSource datasource;
	
	public void readAccessDB()
	{
		System.out.println("Read External Database...");
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = datasource.getConnection();
			List<OriginalSerialNumber> lstSerialNumber = getSerialNumber();
			//pstmt = con.prepareStatement("Select ins_type, ins_no, lot_no, acc_qty, rej_qty, miss_qty, tot_qty, mean, max, min from test_data");
			//rs = pstmt.executeQuery();
			for(OriginalSerialNumber obj:lstSerialNumber)
			{
				pstmt = con.prepareStatement("insert into test_data(ins_type, ins_no, lot_no, acc_qty, rej_qty, miss_qty, tot_qty, mean, max, min) values "
						+ "(?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1, obj.getInsType());
				pstmt.setString(2, obj.getInsNo());
				pstmt.setString(3, obj.getLotNumber());
				pstmt.setString(4, obj.getAcceptedQty());
				pstmt.setString(5, obj.getRejectedQty());
				pstmt.setString(6, obj.getMissQty());
				pstmt.setString(7, obj.getTotalQty());
				pstmt.setString(8, obj.getMean());
				pstmt.setString(9, obj.getMax());
				pstmt.setString(10, obj.getMin());
				pstmt.executeUpdate();
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when insert the auto serial number to PAT db: "+ex.getMessage());
		}finally
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
				System.out.println("Exception when closing the connection in  readAccessDB : "+ex.getMessage());
			}
		}
	}
	
	private List<OriginalSerialNumber> getSerialNumber()
	{
		List<OriginalSerialNumber> lstSerialNumber = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		try
		{
			 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			 String msAccessDBName = "C:/Users/sriha/Documents/example.accdb";
			 String dbURL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + msAccessDBName + ";DriverID=22;READONLY=true";

			// Step 2.A: Create and get connection using DriverManager class
			con = DriverManager.getConnection(dbURL); 

			pstmt = con.prepareStatement("Select ins_type, ins_no, lot_no, acc_qty, rej_qty, miss_qty, tot_qty, mean, max, min from test_data");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				OriginalSerialNumber obj = new OriginalSerialNumber();
				obj.setInsType(rs.getString("ins_type"));
				obj.setInsNo(rs.getString("ins_no"));
				obj.setLotNumber(rs.getString("lot_no"));
				obj.setAcceptedQty(rs.getString("acc_qty"));
				obj.setRejectedQty(rs.getString("rej_qty"));
				obj.setMissQty(rs.getString("miss_qty"));
				obj.setTotalQty(rs.getString("tot_qty"));
				obj.setMean(rs.getString("mean"));
				obj.setMax(rs.getString("max"));
				obj.setMin(rs.getString("min"));
				lstSerialNumber.add(obj);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when get the data from access db: "+ex.getMessage());
		}finally
		{
			try
			{
				if(rs!=null)
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
				System.out.println("Exception when closing the connection in  get serial number from access db : "+ex.getMessage());
			}
		}
		return lstSerialNumber;
	}
	
	
}

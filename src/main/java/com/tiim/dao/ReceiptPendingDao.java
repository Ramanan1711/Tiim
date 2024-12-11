package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.ToolingReceiptNote;

@Repository
public class ReceiptPendingDao {

	@Autowired
	DataSource datasource;
	
	public List<ToolingReceiptNote> getPendingReceipt(String approvalFlag)
	{
		List<ToolingReceiptNote> grnList = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		try
		{
			con = datasource.getConnection();
			
			//pstmt = con.prepareStatement("Select toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno from tooling_receipt_note where grnno LIKE '"+grn+"%' order by grnno");
			if("1".equalsIgnoreCase(approvalFlag))
			{
				pstmt = con.prepareStatement("SELECT toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, branchname FROM "
						+ " tooling_receipt_note where grnno not in (select grnno from tooling_receiving_request) and isactive = ? and approvalflag = ?");
				pstmt.setInt(1, 1);
				pstmt.setInt(2, 1);
			}else
			{
				pstmt = con.prepareStatement("SELECT toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, branchname FROM "
						+ " tooling_receipt_note where grnno not in (select grnno from tooling_receiving_request) and isactive = ? ");
				pstmt.setInt(1, 1);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setGrnNo(rs.getInt("grnno"));
				toolingReceipt.setGrnDate(rs.getString("grndate"));
				toolingReceipt.setPo(rs.getString("po"));
				toolingReceipt.setSupplierCode(rs.getString("suppliercode"));
				toolingReceipt.setSupplierName(rs.getString("suppliername"));
				toolingReceipt.setProductSerialNo(rs.getString("productserno"));
				toolingReceipt.setIsActive(0);
				grnList.add(toolingReceipt);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the GRN No Auto Complete List : "+e.getMessage());
		}
		return grnList;
	}
}

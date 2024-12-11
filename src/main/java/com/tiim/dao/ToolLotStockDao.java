package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.ProductReport;
import com.tiim.model.ToolingLotStock;

@Repository
public class ToolLotStockDao {

	@Autowired
	DataSource datasource;

		
	public List<ToolingLotStock> getToolLotStock(ProductReport productReport) {
		List<ToolingLotStock> lotStocks = new ArrayList<ToolingLotStock>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
						
			String query = "SELECT intent.productname, intent.drawingno, intent.machinetype, intent.typeoftool, intent.toolinglotnumber,"
					+ " intent.punchSetNo, intent.compForce, "
					+ " intent.poQuantity, s.stockqty,rec.rejectedserialnumber, prod.damagedserialnumber,"
					+ " sum(clear.batchqty) producedQty,period.intervalqty,prod.returnedqty, req.requestqty, issue.issuedqty"
					+ "  FROM tooling_intent_note intent "
					+ " left join stock s on intent.toolinglotnumber = s.toolinglotnumber "
					+ " left join tooling_receiving_inspection_details rec on intent.toolinglotnumber = rec.lotnumber "
					+ " left join mst_clearance_closing clear on intent.toolinglotnumber = clear.lotnumber "
					+ " left join tooling_production_return_note_detail prod on intent.toolinglotnumber = prod.toolinglotnumber "
					+ " left join periodic_inspection_report_detail period on intent.toolinglotnumber = period.toolinglotnumber  "
					+ " left join tooling_request_note_detail req on intent.toolinglotnumber = req.toolinglotnumber "
					+ " left join tooling_issue_note_detail issue on intent.toolinglotnumber = issue.toolinglotnumber "					
					+ " where intentid > 0 ";
			
			if(productReport.getProductName()!= null && !productReport.getProductName().equals(""))
			{
				query = query + " and intent.productname = '"+productReport.getProductName()+"' ";
			}
			if(productReport.getDrawingNumber()!= null && !productReport.getDrawingNumber().equals(""))
			{
				query = query + " and intent.drawingno = '"+productReport.getDrawingNumber()+"' ";
			}
			if(productReport.getMachine()!= null && !productReport.getMachine().equals(""))
			{
				query = query + " and intent.machinetype = '"+productReport.getMachine()+"' ";
			}
			if(productReport.getTypeOfTool()!= null && !productReport.getTypeOfTool().equals(""))
			{
				query = query + " and intent.typeoftool = '"+productReport.getTypeOfTool()+"' ";
			}
			if(productReport.getLotNumber()!= null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and intent.toolinglotnumber = '"+productReport.getLotNumber()+"' ";
			}
			System.out.println("Lot stock query: "+query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ToolingLotStock toolingLotStock = new ToolingLotStock();
				toolingLotStock.setProduct(rs.getString("productname"));
				toolingLotStock.setToolType(rs.getString("typeoftool"));
				toolingLotStock.setDrawingNo(rs.getString("drawingno"));
				toolingLotStock.setMachineType(rs.getString("machinetype"));
				toolingLotStock.setLotNumber(rs.getString("toolinglotnumber"));
				toolingLotStock.setOrderQty(rs.getInt("poQuantity"));
				toolingLotStock.setAvailableQty(rs.getInt("stockqty"));
				toolingLotStock.setTabletProducedQty(rs.getInt("producedQty"));
				toolingLotStock.setIntervalQty(rs.getInt("intervalqty"));
				toolingLotStock.setMissingSINo(rs.getString("rejectedserialnumber"));
				toolingLotStock.setPunchSetNo(rs.getString("punchSetNo"));
				toolingLotStock.setCompForce(rs.getInt("compForce"));
				if(rs.getString("rejectedserialnumber") != null) {
					toolingLotStock.setMissingSINo(rs.getString("rejectedserialnumber"));
				}
				if(rs.getString("damagedserialnumber") != null) {
					toolingLotStock.setMissingSINo(toolingLotStock.getMissingSINo()+","+rs.getString("damagedserialnumber"));
				}
				
				if(toolingLotStock.getTabletProducedQty() > 0) {
					toolingLotStock.setLocation("Store: Clearance Closing");
					toolingLotStock.setLotStatus("Live");
				}else if(toolingLotStock.getIntervalQty() > 0){
					toolingLotStock.setLocation("User: Periodic Inspection");
					toolingLotStock.setLotStatus("Inspection Due");
				}else if(rs.getString("returnedqty") != null) {
					toolingLotStock.setLocation("Store: Tool Return");
					toolingLotStock.setLotStatus("Live");
				}else if(rs.getInt("requestqty") > 0) {
					toolingLotStock.setLocation("Store: Tool Request");
					toolingLotStock.setLotStatus("Live");
				}else if(rs.getInt("issuedqty") > 0) {
					toolingLotStock.setLocation("User: Tool Issue");
					toolingLotStock.setLotStatus("Live");
				}
				else
				{
					toolingLotStock.setLocation("User: Receiving Inspection");
					toolingLotStock.setLotStatus("Live");
				}
				
				lotStocks.add(toolingLotStock);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolLotStock for getting history report : "+ex.getMessage());
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
				System.out.println("Exception when getting the entire getToolLotStock for getting history report : "+ex.getMessage());
			}
		}
		return lotStocks;
	}
	
	public List<ToolingLotStock> getClearanceDetails(ProductReport productReport) {
		List<ToolingLotStock> lotStocks = new ArrayList<ToolingLotStock>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
						
			String query = " SELECT intent.productname, intent.drawingno, intent.machinetype, intent.typeoftool, intent.toolinglotnumber,"
					+ " s.stockqty,rec.rejectedqty, prod.damagedqty prodDamagedQty,sum(clear.batchqty) producedQty,period.intervalqty,"
					+ " prod.returnedqty, issue.issuedqty, period.damagedqty periodDamaged, clr.clearanceNo, clr.clearanceDate,clear.closingNo,"
					+ " clear.batchNumber,issueNote.issueId, issueBy,visual_check_by, retNote.returnNoteId, retNote.returnedBy, retNote.cleaned_By "
					+ " FROM tooling_intent_note intent "
					+ " left join stock s on intent.toolinglotnumber = s.toolinglotnumber "
					+ " left join tooling_receiving_inspection_details rec on intent.toolinglotnumber = rec.lotnumber "
					+ " left join mst_clearance_closing clear on intent.toolinglotnumber = clear.lotnumber "
					+ " left join tooling_production_return_note_detail prod on intent.toolinglotnumber = prod.toolinglotnumber  "
					+ " left join periodic_inspection_report_detail period on intent.toolinglotnumber = period.toolinglotnumber "
					+ " left join tooling_production_return_note_detail retDetail on intent.toolinglotnumber = retDetail.toolinglotnumber "
					+ " left join tooling_production_return_note retNote on retDetail.returnNoteId = retNote.returnNoteId "
					+ " left join tooling_issue_note_detail issue on intent.toolinglotnumber = issue.toolinglotnumber "
					+ " left join tooling_issue_note issueNote on issue.issueId = issueNote.issueId"
					+ " left join mst_clearance_no clr on intent.toolinglotnumber = clr.lotNumber where clear.closingNo > 0";
			
			if(productReport.getProductName()!= null && !productReport.getProductName().equals(""))
			{
				query = query + " and clear.productname = '"+productReport.getProductName()+"' ";
			}
			
			if(productReport.getLotNumber()!= null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and clear.lotNumber = '"+productReport.getLotNumber()+"' ";
			}
			if(productReport.getBatchNumber()!= null && !productReport.getBatchNumber().equals(""))
			{
				query = query + " and clear.batchnumber = '"+productReport.getBatchNumber()+"' ";
			}
			System.out.println("Lot Batch query: "+query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ToolingLotStock toolingLotStock = new ToolingLotStock();
				toolingLotStock.setBatchNumber(rs.getString("batchNumber"));
				toolingLotStock.setProduct(rs.getString("productname"));
				toolingLotStock.setLotNumber(rs.getString("toolinglotnumber"));
				toolingLotStock.setClearanceNumber(rs.getInt("clearanceNo"));
				toolingLotStock.setClosingNumber(rs.getInt("closingNo"));
				toolingLotStock.setTabletProducedQty(rs.getInt("producedQty"));
				toolingLotStock.setIntervalQty(rs.getInt("intervalqty"));
				toolingLotStock.setIssueQty(rs.getInt("issuedqty"));
				toolingLotStock.setReturnQty(rs.getInt("returnedqty"));				
				toolingLotStock.setDamagedQty(rs.getInt("rejectedqty") + rs.getInt("periodDamaged") + rs.getInt("prodDamagedQty"));
				toolingLotStock.setIssueBy(rs.getString("issueBy"));
				toolingLotStock.setIssueId(rs.getInt("issueId"));
				toolingLotStock.setVisualCheckBy(rs.getString("visual_check_by"));
				toolingLotStock.setReturnId(rs.getLong("returnNoteId"));
				toolingLotStock.setReturnBy(rs.getString("returnedBy"));
				toolingLotStock.setCleanedBy(rs.getString("cleaned_By"));
				lotStocks.add(toolingLotStock);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolLotStock for getting history report : "+ex.getMessage());
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
				System.out.println("Exception when getting the entire getToolLotStock for getting history report : "+ex.getMessage());
			}
		}
		return lotStocks;
	
	}

}

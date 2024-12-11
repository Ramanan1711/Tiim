package com.tiim.transaction.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.StockTransferDao;
import com.tiim.dao.SupplierReturnNoteDao;
import com.tiim.dao.ToolingPeriodicInspectionReportDao;
import com.tiim.dao.ToolingReceiptNoteDao;
import com.tiim.dao.ToolingReceivingInspectionDao;
import com.tiim.model.StockTransfer;
import com.tiim.model.SupplierReturn;
import com.tiim.model.ToolingInspection;
import com.tiim.model.ToolingPeriodicInspectionReport;
import com.tiim.model.ToolingReceiptNote;

@Controller
public class SupplierReturnNoteController {


	@Autowired
	StockTransferDao stockTransferDao;
	@Autowired
	SupplierReturnNoteDao supplierReturnDao;
	
	@Autowired
	ToolingReceivingInspectionDao toolingInpsectionDao;
	
	@Autowired
	ToolingPeriodicInspectionReportDao periodicInpspectionDao;
	
	@Autowired
	ToolingReceiptNoteDao receiptDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();

	@RequestMapping(value = "/showSupplierReturnNoteList", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSupplierReturnNoteList( HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("supplierReturnList");
		List<SupplierReturn> lstSupplierReturn = new ArrayList<SupplierReturn>();
		lstSupplierReturn = supplierReturnDao.getSupplierReturn(0);
		modelView.addObject("lstSupplierReturn", lstSupplierReturn);

		return modelView;
	}
	
	@RequestMapping(value = "/searchSupplierReturnNoteList", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchSupplierReturnNoteList( HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("supplierReturnList");
		List<SupplierReturn> lstSupplierReturn = new ArrayList<SupplierReturn>();
		lstSupplierReturn = supplierReturnDao.getSupplierReturn(Integer.parseInt(request.getParameter("searchReturntNo")));
		modelView.addObject("lstSupplierReturn", lstSupplierReturn);

		return modelView;
	}
	
	@RequestMapping(value = "/deleteSupplierReturnNote", method={RequestMethod.GET, RequestMethod.POST})
	public String deleteSupplierReturnNote( HttpServletRequest request)
	{
		//ModelAndView modelView = new ModelAndView("supplierReturnList");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		int requestNo = Integer.parseInt(request.getParameter("requestNo"));
		supplierReturnDao.deleteSupplierReturn(requestNo, userId);
		
		return "redirect:showSupplierReturnNoteList.jsf";
	}
	
	
	@RequestMapping(value = "/showSupplierReturnNote", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSupplierReturnNote( HttpServletRequest request)
	{
		//@ModelAttribute PurchaseIndentConfirmation purchase,
		ModelAndView modelView = new ModelAndView("supplierToolingReturnNote");
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();
		modelView.addObject("returnNoteDate", sdf.format(cDate));
		lstStockTransfer = stockTransferDao.getStockTransfer("");
		modelView.addObject("lstStockTransfer", lstStockTransfer);
		modelView.addObject("isActive", 0);
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		return modelView;
	}
	
	
	
	@RequestMapping(value = "/getStockReturnByReceipt", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getStockReturnByReceipt(@RequestParam("grnNo") int grnNo, HttpServletRequest request)
	{
		System.out.println("grnNo: "+grnNo);
		ModelAndView modelView = new ModelAndView("supplierToolingReturnNote");
		List<SupplierReturn> lstSupplierReturn = supplierReturnDao.getReceiptProductDetails(grnNo);
		modelView.addObject("lstSupplierReturn",lstSupplierReturn);
		modelView.addObject("grnNo", grnNo);
		/*modelView.addObject("isActive", 0);
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");*/
		return modelView;
	}
	
	@RequestMapping(value = "/getStockReturnByReceivedId", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getStockReturnByReceivedId(@RequestParam("requestNo") int requestNo, HttpServletRequest request)
	{
		System.out.println("periodicRequestNo: "+requestNo);
		ModelAndView modelView = new ModelAndView("supplierToolingReturnNote");
		List<SupplierReturn> lstSupplierReturn = supplierReturnDao.getToolingInspectionDetails(requestNo);
		modelView.addObject("lstSupplierReturn",lstSupplierReturn);
		modelView.addObject("requestNo", requestNo);
		/*modelView.addObject("isActive", 0);
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");*/
		return modelView;
	}
	
	@RequestMapping(value = "/getStockReturnByPeriodicId", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getStockReturnByPeriodicId(@RequestParam("periodicRequestNo") int periodicRequestNo, HttpServletRequest request)
	{
		System.out.println("periodicRequestNo: "+periodicRequestNo);
		ModelAndView modelView = new ModelAndView("supplierToolingReturnNote");
		List<SupplierReturn> lstSupplierReturn = supplierReturnDao.getPeriodicInspectionDetail(periodicRequestNo);
		System.out.println("lstSupplierReturn: "+lstSupplierReturn);
		modelView.addObject("lstSupplierReturn",lstSupplierReturn);
		modelView.addObject("periodicRequestNo", periodicRequestNo);
		/*modelView.addObject("isActive", 0);
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");*/
		return modelView;
	}
	
	@RequestMapping(value = "/addSupplierReturn", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView addSupplierReturn(@ModelAttribute SupplierReturn supplierReturn, HttpServletRequest request)
	{ 

		ModelAndView modelView = new ModelAndView("supplierToolingReturnNote");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		System.out.println("supplierReturn: "+supplierReturn+", "+session.getAttribute("sesBranchName").toString()+", "+supplierReturn.getBranch());
		String branchName = session.getAttribute("sesBranchName").toString();
		//supplierReturn.setBranch(branchName);
		String message = supplierReturnDao.addSupplierReturn(supplierReturn, userId);
		
		modelView.addObject("returnNoteDate", sdf.format(cDate));
		
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);
		modelView.addObject("count", 0);
		modelView.addObject("gridOrgCount", 0);
		return modelView;
	
	}
	
	@RequestMapping(value = "/editSupplierReturn", method={RequestMethod.POST, RequestMethod.POST})
	public ModelAndView editperiodicInspection(@RequestParam int returnId, HttpServletRequest request)
	{
		
		ModelAndView modelView = new ModelAndView("supplierToolingReturnNote");
		List<SupplierReturn> lstSupplierReturn = new ArrayList<SupplierReturn>();
		
		SupplierReturn obj = supplierReturnDao.getSupplierReturnById(returnId);
		SupplierReturn supplierReturn = supplierReturnDao.getSupplierReturnDetail(returnId);
				
		modelView.addObject("returnNoteDate", obj.getReturnDate());
			
		modelView.addObject("returnNoteId", obj.getReturnId());
		modelView.addObject("requestId", obj.getRequestId());	
		modelView.addObject("returnNoteId", obj.getReturnId());	
		modelView.addObject("branch", obj.getBranch());	
		modelView.addObject("action", "exist");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("supplierReturn", supplierReturn);
		modelView.addObject("message", "");
		
		if(supplierReturn != null)
		{
			modelView.addObject("count", 1);
			modelView.addObject("gridOrgCount", 1);
		}
		
		return modelView;
	}
	
	@RequestMapping(value = "/updateSupplierReturn", method=RequestMethod.POST)
	public ModelAndView updateSupplierReturn(@ModelAttribute SupplierReturn supplierReturn, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		supplierReturn.setBranch(branchName);
		ModelAndView modelView = new ModelAndView("supplierToolingReturnNote");
		String message = supplierReturnDao.updateSupplierReturn(supplierReturn, userId);
		
		modelView.addObject("returnNoteDate", sdf.format(cDate));
		
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);
		modelView.addObject("count", 0);
		modelView.addObject("gridOrgCount", 0);
		
		return modelView;
	}
	/*@RequestMapping(value = "/searchStockTransferIssue", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchStockTransferIssue(@ModelAttribute StockTransfer tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssueList");
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();
		lstStockTransfer = stockTransferDao.getStockTransfer(tool.getStockTransferId()+"");
		modelView.addObject("lstStockTransfer", lstStockTransfer);
		modelView.addObject("searchRequestBy", tool.getStockTransferId());
		modelView.addObject("isActive", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/addIntialStockTransferIssue", method=RequestMethod.POST)
	public ModelAndView addIntialStockTransferIssue(@ModelAttribute StockTransfer stockTransfer, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		int StockTransferId = stockTransferDao.getIntialValue();
		modelView.addObject("transferId", StockTransferId);
		modelView.addObject("transferDate",  sdf.format(cDate));
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		return modelView;
	}
	
	@RequestMapping(value = "/addStockTransferIssue", method=RequestMethod.POST)
	public ModelAndView addStockTransferIssue(@ModelAttribute StockTransfer stockTransfer, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		HttpSession session = request.getSession();
		String branchName = session.getAttribute("sesBranchName").toString();
		String message = stockTransferDao.addstockTransfer(stockTransfer);
		//modelView.addObject("StockTransfer", StockTransfer);
		modelView.addObject("message", message);
		
		*//****************Reset Value*****************//*
		int StockTransferId = stockTransferDao.getIntialValue();
		modelView.addObject("transferId", StockTransferId);
		modelView.addObject("transferDate",  sdf.format(cDate));
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		*//********************************************//*
		
		return modelView;
	}
	
	@RequestMapping(value = "/addToolingRequestNoteDetail", method=RequestMethod.POST)
	public ModelAndView addToolingRequestNoteDetail(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("addToolingRequestNote");
		String message = toolingRequestDao.addRequestNoteDetails(toolingRequestNote);
		modelView.addObject("toolingRequestNote", toolingRequestNote);
		modelView.addObject("message", message);
		return modelView;
	}
	
	@RequestMapping(value = "/updateStockTransferIssue", method=RequestMethod.POST)
	public ModelAndView updateStockTransferIssue(@ModelAttribute StockTransfer stockTransfer, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		String message = stockTransferDao.updateStockTransfer(stockTransfer);
		//modelView.addObject("toolingRequestNote", toolingRequestNote);
		modelView.addObject("message", message);
		
		*//****************Reset Value*****************//*
		int StockTransferId = stockTransferDao.getIntialValue();
		modelView.addObject("transferId", StockTransferId);
		modelView.addObject("transferDate",  sdf.format(cDate));
		modelView.addObject("requestBy", "");
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		*//********************************************//*
		
		return modelView;
	}
	
	
	@RequestMapping(value = "/updateToolingRequestNoteDetail", method=RequestMethod.POST)
	public @ResponseBody String updateToolingRequestNoteDetail(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		String message = toolingRequestDao.updateRequestNoteDetails(toolingRequestNote);

		return message;
	}
	
	@RequestMapping(value = "/editStockTransferIssue", method=RequestMethod.POST)
	public ModelAndView editStockTransferIssue(@RequestParam int transferId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();
		
		StockTransfer stockTransfer = stockTransferDao.getStockTransfer(transferId);
		lstStockTransfer = stockTransferDao.getAutoStockTransfer(transferId+"");
		
		modelView.addObject("transferId", stockTransfer.getStockTransferId());
		modelView.addObject("requestDate", stockTransfer.getStockDate());
		//modelView.addObject("requestBy", toolingRequestNote.getRequestBy());
		modelView.addObject("lstStockTransfer", lstStockTransfer);
		modelView.addObject("action", "exist");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
	
	@RequestMapping(value = "/changeRequestStatus", method=RequestMethod.POST)
	public ModelAndView changeRequestStatus(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNote");
		String message = toolingRequestDao.changeToolingRequestNoteStatus(requestId);
		modelView.addObject("message", message);
		return modelView;
	}
	
	@RequestMapping(value = "/deleteStockTransferIssue", method=RequestMethod.POST)
	public ModelAndView deleteStockTransferIssue(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		String message = toolingRequestDao.deleteRequestNote(requestId);
		List<ToolingRequestNote> lstToolingRequestNote = new ArrayList<ToolingRequestNote>();
		lstToolingRequestNote = toolingRequestDao.getToolingRequestNote("");
		modelView.addObject("lstToolingRequestNote", lstToolingRequestNote);
		modelView.addObject("message", message);
		return modelView;
	}

*/
	@RequestMapping(value = "/getRejectPODetail", method=RequestMethod.POST)
	public ModelAndView getRejectPODetail(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listRejectPo");
		List<ToolingReceiptNote> lstToolingReceiptNote = new ArrayList<ToolingReceiptNote>();
		String drawingNo = request.getParameter("drawingNumber");
		lstToolingReceiptNote = receiptDao.getToolingRejectReceiptProductDetails(drawingNo);
		modelView.addObject("lstToolingReceiptNote", lstToolingReceiptNote);
		return modelView;

	}
	
	@RequestMapping(value = "/getRejectReceivingProductDetail", method=RequestMethod.POST)
	public ModelAndView getReceivingProductDetail(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listRejectProductReceivingReport");
		List<ToolingInspection> lstToolingReceivingRequest = new ArrayList<ToolingInspection>();
		String drawingNo = request.getParameter("drawingNumber");
		lstToolingReceivingRequest = toolingInpsectionDao.getRejectInspectionDetails(drawingNo);
		modelView.addObject("lstToolingReceivingRequest", lstToolingReceivingRequest);
		return modelView;

	}
	
	
	@RequestMapping(value = "/getRejectPeriodicInspectionReportDetail", method=RequestMethod.POST)
	public ModelAndView getRejectPeriodicInspectionReportDetail(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listRejectPeriodicReqestDetail");
		List<ToolingPeriodicInspectionReport> lstToolingPeriodicInspection = new ArrayList<ToolingPeriodicInspectionReport>();
		String drawingNo = request.getParameter("drawingNumber");
		lstToolingPeriodicInspection = periodicInpspectionDao.getRejectPeriodicInspectionReportDetail(drawingNo);
		modelView.addObject("lstToolingPeriodicInspection", lstToolingPeriodicInspection);
		return modelView;

	}
}

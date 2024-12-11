package com.tiim.transaction.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.StockTransferDao;
import com.tiim.model.StockTransfer;

@Controller
public class PurchaseIndentController {



	@Autowired
	StockTransferDao stockTransferDao;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();

	@RequestMapping(value = "/showPurchaseIndent", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showPurchaseIndent( HttpServletRequest request)
	{
		//@ModelAttribute PurchaseIndentConfirmation purchase,
		ModelAndView modelView = new ModelAndView("purchaseIndentConfirmation");
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();
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

}

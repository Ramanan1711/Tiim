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

import com.tiim.dao.StockTransferIssueDao;
import com.tiim.model.StockTransfer;
import com.tiim.model.StockTransferIssue;

@Controller
public class StockTransferIssueController {

	@Autowired
	StockTransferIssueDao stockTransferDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();

	@RequestMapping(value = "/showStockTransferIssue", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showStockTransferIssue(@ModelAttribute StockTransfer tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssueList");
		List<StockTransferIssue> lstStockTransfer = new ArrayList<StockTransferIssue>();
		lstStockTransfer = stockTransferDao.getStockTransferIssue("");
		modelView.addObject("lstStockTransfer", lstStockTransfer);
		modelView.addObject("isActive", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/searchStockTransferIssue", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchStockTransferIssue(@ModelAttribute StockTransferIssue tool, HttpServletRequest request)
	{
		System.out.println("searchStockTransferIssue...");
		ModelAndView modelView = new ModelAndView("stockTransferIssueList");
		List<StockTransferIssue> lstStockTransfer = new ArrayList<StockTransferIssue>();
		lstStockTransfer = stockTransferDao.getStockTransferIssue(tool.getSearchTransferIssueId()+"");
		modelView.addObject("lstStockTransfer", lstStockTransfer);
		modelView.addObject("searchRequestBy", tool.getStockTransferId());
		modelView.addObject("isActive", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/addIntialStockTransferIssue", method=RequestMethod.POST)
	public ModelAndView addIntialStockTransferIssue(@ModelAttribute StockTransferIssue stockTransfer, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		int StockTransferId = stockTransferDao.getIntialValue();
		modelView.addObject("stockTransferIssueId", StockTransferId);
		modelView.addObject("stockIssueDate",  sdf.format(cDate));
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		return modelView;
	}
	
	@RequestMapping(value = "/addStockTransferIssue", method=RequestMethod.POST)
	public String addStockTransferIssue(@ModelAttribute StockTransferIssue stockTransfer, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		String message = stockTransferDao.addstockTransfer(stockTransfer, userId);
		//modelView.addObject("StockTransfer", StockTransfer);
		modelView.addObject("message", message);
		
		/****************Reset Value*****************/
		int StockTransferId = stockTransferDao.getIntialValue();
		modelView.addObject("transferId", StockTransferId);
		modelView.addObject("transferDate",  sdf.format(cDate));
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		/********************************************/
		
		return "redirect:/showStockTransferIssue.jsf";
	}
	
	/*@RequestMapping(value = "/addToolingRequestNoteDetail", method=RequestMethod.POST)
	public ModelAndView addToolingRequestNoteDetail(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("addToolingRequestNote");
		String message = toolingRequestDao.addRequestNoteDetails(toolingRequestNote);
		modelView.addObject("toolingRequestNote", toolingRequestNote);
		modelView.addObject("message", message);
		return modelView;
	}*/
	
	@RequestMapping(value = "/updateStockTransferIssue", method=RequestMethod.POST)
	public String updateStockTransferIssue(@ModelAttribute StockTransferIssue stockTransfer, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		String message = stockTransferDao.updateStockTransferIssueDetail(stockTransfer, userId);
		//modelView.addObject("toolingRequestNote", toolingRequestNote);
		modelView.addObject("message", message);
		
		/****************Reset Value*****************/
		int StockTransferId = stockTransferDao.getIntialValue();
		modelView.addObject("transferId", StockTransferId);
		modelView.addObject("transferDate",  sdf.format(cDate));
		modelView.addObject("requestBy", "");
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		/********************************************/
		
		return "redirect:/showStockTransferIssue.jsf";
	}
	
	
	/*@RequestMapping(value = "/updateToolingRequestNoteDetail", method=RequestMethod.POST)
	public @ResponseBody String updateToolingRequestNoteDetail(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		String message = toolingRequestDao.updateRequestNoteDetails(toolingRequestNote);

		return message;
	}*/
	
	@RequestMapping(value = "/editStockTransferIssue", method=RequestMethod.POST)
	public ModelAndView editStockTransferIssue(@RequestParam int transferId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssue");
		List<StockTransferIssue> lstStockTransfer = new ArrayList<StockTransferIssue>();
		
		StockTransferIssue stockTransfer = stockTransferDao.getStockTransferIssue(transferId);
		lstStockTransfer = stockTransferDao.getStockTransferIssueDetail(transferId);
		
		modelView.addObject("stockTransferIssueId", stockTransfer.getStockTransferId());
		modelView.addObject("stockIssueDate", stockTransfer.getStockIssueDate());
		modelView.addObject("stockTransferId", stockTransfer.getStockTransferId());
		modelView.addObject("stockDate", stockTransfer.getStockDate());
		modelView.addObject("fromBranch", stockTransfer.getFromBranch());
		modelView.addObject("toBranch", stockTransfer.getToBranch());
		//modelView.addObject("requestBy", toolingRequestNote.getRequestBy());
		modelView.addObject("lstStockTransfer", lstStockTransfer);
		modelView.addObject("action", "exist");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
	
	/*@RequestMapping(value = "/changeRequestStatus", method=RequestMethod.POST)
	public ModelAndView changeRequestStatus(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNote");
		String message = toolingRequestDao.changeToolingRequestNoteStatus(requestId);
		modelView.addObject("message", message);
		return modelView;
	}*/
	
	@RequestMapping(value = "/deleteStockTransferIssue", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView deleteStockTransferIssue(@RequestParam int transferId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferIssueList");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		stockTransferDao.deleteStockTransferIssue(transferId, userId);
	/*	String message = toolingRequestDao.deleteRequestNote(requestId);
		List<ToolingRequestNote> lstToolingRequestNote = new ArrayList<ToolingRequestNote>();
		lstToolingRequestNote = toolingRequestDao.getToolingRequestNote("");
		modelView.addObject("lstToolingRequestNote", lstToolingRequestNote);
		modelView.addObject("message", message);*/
		return modelView;
	}


}

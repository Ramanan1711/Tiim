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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tiim.dao.StockTransferDao;
import com.tiim.model.StockTransfer;

@Controller
public class StockTransferRequestController {

	@Autowired
	StockTransferDao stockTransferDao;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();

	@RequestMapping(value = "/showStockTransfer", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showStockTransfer(@ModelAttribute StockTransfer tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferList");
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();
		lstStockTransfer = stockTransferDao.getStockTransfer("");
		modelView.addObject("lstStockTransfer", lstStockTransfer);
		modelView.addObject("isActive", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/searchStockTransfer", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchStockTransfer(@ModelAttribute StockTransfer tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferList");
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();
		lstStockTransfer = stockTransferDao.getStockTransfer(tool.getSearchTransferId());
		modelView.addObject("lstStockTransfer", lstStockTransfer);
		modelView.addObject("searchTransferId", tool.getSearchTransferId());
		modelView.addObject("isActive", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/addIntialStockTransfer", method=RequestMethod.POST)
	public ModelAndView addIntialStockTransfer(@ModelAttribute StockTransfer stockTransfer, HttpServletRequest request)
	{
		String branchName = request.getSession().getAttribute("sesBranchName").toString();
		ModelAndView modelView = new ModelAndView("stockTransferRequest");
		int StockTransferId = stockTransferDao.getIntialValue();
		modelView.addObject("stockTransferId", StockTransferId);
		modelView.addObject("fromBranch",branchName);
		modelView.addObject("stockDate",  sdf.format(cDate));
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		return modelView;
	}
	
	@RequestMapping(value = "/addStockTransfer", method=RequestMethod.POST)
	public String addStockTransfer(@ModelAttribute StockTransfer stockTransfer, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferRequest");
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
		
		return  "redirect:/showStockTransfer.jsf";
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
	
	@RequestMapping(value = "/updateStockTransfer", method=RequestMethod.POST)
	public String updateStockTransfer(@ModelAttribute StockTransfer stockTransfer, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferRequest");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String message = stockTransferDao.updateStockTransfer(stockTransfer, userId);
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
		
		return "redirect:/showStockTransfer.jsf";
	}
	
	
	/*@RequestMapping(value = "/updateToolingRequestNoteDetail", method=RequestMethod.POST)
	public @ResponseBody String updateToolingRequestNoteDetail(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		String message = toolingRequestDao.updateRequestNoteDetails(toolingRequestNote);

		return message;
	}*/
	
	@RequestMapping(value = "/editStockTransfer", method=RequestMethod.POST)
	public ModelAndView editToolingRequest(@RequestParam int transferId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferRequest");
		List<StockTransfer> lstStockTransfer = new ArrayList<StockTransfer>();
		
		StockTransfer stockTransfer = stockTransferDao.getStockTransfer(transferId);
		lstStockTransfer = stockTransferDao.getAutoStockTransfer(transferId+"");
		
		modelView.addObject("stockTransferId", stockTransfer.getStockTransferId());
		modelView.addObject("stockDate", stockTransfer.getStockDate());
		modelView.addObject("fromBranch", stockTransfer.getFromBranch());
		modelView.addObject("toBranch", stockTransfer.getToBranch());
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
	
	@RequestMapping(value = "/deleteStockTransfer", method=RequestMethod.POST)
	public ModelAndView deleteReceiptNote(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("stockTransferRequest");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
	/*	String message = toolingRequestDao.deleteRequestNote(requestId);
		List<ToolingRequestNote> lstToolingRequestNote = new ArrayList<ToolingRequestNote>();
		lstToolingRequestNote = toolingRequestDao.getToolingRequestNote("");
		modelView.addObject("lstToolingRequestNote", lstToolingRequestNote);
		modelView.addObject("message", message);*/
		return modelView;
	}
	
	@RequestMapping(value = "/getStockTransferDetail", method=RequestMethod.POST)
	public @ResponseBody String getStockTransferDetail(@RequestParam int transferId, HttpServletRequest request)
	{
		StockTransfer stockTransfer = stockTransferDao.getStockTransfer(transferId);
		List<StockTransfer> lstStockTransfer = stockTransferDao.getAutoStockTransfer(transferId+"");
		Gson gson = new Gson();
		return gson.toJson(lstStockTransfer);
		
	}
	
	@RequestMapping(value = "/getStockTransfer", method=RequestMethod.POST)
	public @ResponseBody StockTransfer getStockTransfer(@RequestParam int transferId, HttpServletRequest request)
	{
		StockTransfer stockTransfer = stockTransferDao.getStockTransfer(transferId);
		return stockTransfer;
		
	}
	

}

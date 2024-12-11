package com.tiim.master.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.tiim.dao.ClearanceNoDao;
import com.tiim.dao.ToolingIssueDao;
import com.tiim.model.ClearanceNo;
import com.tiim.model.Param;
import com.tiim.model.ToolingIssueNote;
import com.tiim.util.TiimConstant;

@Controller
public class ClearanceNoController {
	@Autowired
	ClearanceNoDao clearnaceDao;
	@Autowired
	ToolingIssueDao issueDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	java.util.Date cDate=new java.util.Date();
		
	@RequestMapping(value = "/showClearance", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showClearance(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("clearanceNo");
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	@RequestMapping(value = "/showClearanceList", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showClearanceList(@ModelAttribute ClearanceNo clearnace, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("clearanceNoList");
		List<ClearanceNo> lstClearanceNoDetail = new ArrayList<ClearanceNo>();
		lstClearanceNoDetail = clearnaceDao.getClearanceDetails(clearnace.getClearanceNo());
		modelView.addObject("lstClearanceNoDetail", lstClearanceNoDetail);
		return modelView;
	}

	@RequestMapping(value = "/addClearance", method=RequestMethod.POST)
	public ModelAndView addClearance(@ModelAttribute ClearanceNo clearnace, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("clearanceNo");
		String message = clearnaceDao.addClearance(clearnace, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	@RequestMapping(value = "/editClearance", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editClearance(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("clearanceNo");
		List<ClearanceNo> clearance = clearnaceDao.getClearanceDetails(requestId);
		for (Iterator iterator = clearance.iterator(); iterator.hasNext();) {
			ClearanceNo clearanceDtl = (ClearanceNo) iterator.next();
			modelView.addObject("clearanceNo", clearanceDtl.getClearanceNo());
			modelView.addObject("lotNumber", clearanceDtl.getLotNumber());
			modelView.addObject("clearanceDate", sdf.format(clearanceDtl.getClearanceDate()));
			modelView.addObject("productName", clearanceDtl.getProductName());
			modelView.addObject("batchNumber", clearanceDtl.getBatchNumber());
			modelView.addObject("batchQty", clearanceDtl.getBatchQty());
		}
		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
	@RequestMapping(value = "/updateClearance", method=RequestMethod.POST)
	public ModelAndView updateClearance(@ModelAttribute ClearanceNo clearance, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("clearanceNo");
		String message = clearnaceDao.updateClearance(clearance, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");

		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteClearance", method=RequestMethod.POST)
	public ModelAndView deleteClearance(@RequestParam int requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("clearanceNoList");
		String message = clearnaceDao.deleteClearance(requestId, userId);
		modelView.addObject("message", message);
		List<ClearanceNo> lstClearanceNoDetail = new ArrayList<ClearanceNo>();
		lstClearanceNoDetail = clearnaceDao.getClearanceDetails(0);
		modelView.addObject("lstClearanceNoDetail", lstClearanceNoDetail);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	 @RequestMapping(value = "/viewClearance", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView viewClearance(@RequestParam int requestId, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("viewClearance");
			List<ClearanceNo> lstClearanceNoDetail = new ArrayList<ClearanceNo>();
			lstClearanceNoDetail = clearnaceDao.getClearanceDetails(requestId);
			modelView.addObject("lstClearanceNoDetail", lstClearanceNoDetail);
			return modelView;
		} 
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		int clearanceNo = clearnaceDao.getIntialValue();
		modelView.addObject("clearanceNo", clearanceNo);
		modelView.addObject("action", action);
		modelView.addObject("clearanceDate", sdf.format(cDate));
	}
	
	@RequestMapping(value = "/getClearanceToolingLotNo", method=RequestMethod.GET)
	public @ResponseBody String getClearanceToolingLotNo(@RequestParam String productName, @RequestParam String lotNumber, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		String branch = (String)session.getAttribute("sesBranchName");
		List<String> lstLotNumber = clearnaceDao.getToolingLotNo(productName,lotNumber);
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		Iterator<String> itr = lstLotNumber.iterator();
		while(itr.hasNext())
		{
			String obj = itr.next();
			Param p = new Param(obj ,obj, obj);
	        data.add(p);	
		}
		return gson.toJson(data);
	}

	@RequestMapping(value = "/getPendingIssueLotNumber", method=RequestMethod.POST)
	public ModelAndView getPendingIssueLotNumber(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_ISSUE_SCREEN).toString();
		ModelAndView modelView = new ModelAndView("listPendingIssueLot");
		List<ToolingIssueNote> lstToolingIssue = new ArrayList<ToolingIssueNote>();
		lstToolingIssue = issueDao.getToolingClosingIssueNoteDetail(approvalFlag);
		modelView.addObject("lstToolingIssue", lstToolingIssue);
		return modelView;

	}
	
	@RequestMapping(value = "/getClearacnceCount", method=RequestMethod.POST)
	public @ResponseBody ClearanceNo getClearacnceCount(@RequestParam String lotNumber,@RequestParam Long issueId, HttpServletRequest request)
	{
		ClearanceNo clearance = clearnaceDao.getClearanceNumberCount(lotNumber,issueId);
		return clearance;
	}
}

package com.tiim.master.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.BranchDao;
import com.tiim.dao.CleaningSopDao;
import com.tiim.model.Branch;
import com.tiim.model.CleaningSop;
import com.tiim.model.ToolIndent;

@Controller
public class SOPController {
	
	@Autowired
	CleaningSopDao sopDao;
	
		
	@RequestMapping(value = "/showSOP", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showAddSop(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("addSop");
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "display");
		return modelView;
	}
	@RequestMapping(value = "/mstCleaningSop", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMstCleaningSop(@ModelAttribute CleaningSop sop, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstCleaningSop");
		List<CleaningSop> lstSopDetail = new ArrayList<CleaningSop>();
		lstSopDetail = sopDao.getSopListDetails(sop.getSerialNo());
		modelView.addObject("lstSopDetail", lstSopDetail);
		return modelView;
	}
	@RequestMapping(value = "/addSop", method=RequestMethod.POST)
	public ModelAndView addSop(@ModelAttribute CleaningSop sop, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("addSop");
		String message = sopDao.addsop(sop, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	@RequestMapping(value = "/editSop", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editToolIndent(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("addSop");
		List<CleaningSop> cleaningSop = sopDao.getSopDetails(requestId);
		for (Iterator iterator = cleaningSop.iterator(); iterator.hasNext();) {
			CleaningSop sop = (CleaningSop) iterator.next();
			modelView.addObject("serialNo", sop.getSerialNo());
			modelView.addObject("cleaningtype", sop.getCleaningtype());
			modelView.addObject("description", sop.getDescription());
			modelView.addObject("cleaningId", sop.getCleaningId());
			modelView.addObject("cleaningprocess", sop.getCleaningprocess());

		}
		modelView.addObject("lstSopDetail", cleaningSop);
		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
	@RequestMapping(value = "/updateSop", method=RequestMethod.POST)
	public ModelAndView updateSop(@ModelAttribute CleaningSop sop, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("addSop");
		String message = sopDao.updateSop(sop, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");

		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteSop", method=RequestMethod.POST)
	public ModelAndView deleteSop(@RequestParam int requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstCleaningSop");
		String message = sopDao.deleteSop(requestId, userId);
		modelView.addObject("message", message);
		List<CleaningSop> lstSopDetail = new ArrayList<CleaningSop>();
		lstSopDetail = sopDao.getSopDetails(0);
		modelView.addObject("lstSopDetail", lstSopDetail);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteProcess", method=RequestMethod.POST)
	public ModelAndView deleteProcess(@RequestParam int requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstCleaningSop");
		String message = sopDao.deleteProcess(requestId, userId);
		modelView.addObject("message", message);
		List<CleaningSop> lstSopDetail = new ArrayList<CleaningSop>();
		lstSopDetail = sopDao.getSopDetails(0);
		modelView.addObject("lstSopDetail", lstSopDetail);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	 @RequestMapping(value = "/getSopDetails", method={RequestMethod.GET, RequestMethod.POST})
	 public ModelAndView getSopDetails(@ModelAttribute CleaningSop sop, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("mstCleaningSop");
			List<CleaningSop> lstIntentDetail = new ArrayList<CleaningSop>();
			lstIntentDetail = sopDao.getSopDetails(sop.getSerialNo());
			modelView.addObject("lstIntentDetail", lstIntentDetail);
			return modelView;
	}
	 @RequestMapping(value = "/viewCleaningSop", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView viewtCleaningSop(@RequestParam int requestId, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("viewCleaningSop");
			List<CleaningSop> lstSopDetail = new ArrayList<CleaningSop>();
			lstSopDetail = sopDao.getSopDetails(requestId);
			modelView.addObject("lstSopDetail", lstSopDetail);
			return modelView;
		} 
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("isActive", 1);
		int serialNo = sopDao.getIntialValue();
		modelView.addObject("serialNo", serialNo);
		modelView.addObject("action", action);
	}
}

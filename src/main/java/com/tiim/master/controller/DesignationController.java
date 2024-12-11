package com.tiim.master.controller;

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

import com.tiim.dao.DesignationDao;
import com.tiim.model.Designation;

@Controller
public class DesignationController {

	@Autowired
	DesignationDao designationDao;
	
	@RequestMapping(value = "/showDesignation", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showDesignation(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstDesignation");
		List<Designation> lstDesignationDetail = new ArrayList<Designation>();
		lstDesignationDetail = designationDao.getDesignationDetails("");
		modelView.addObject("lstDesignationDetail", lstDesignationDetail);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchDesignation", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchDesignation(@ModelAttribute Designation designation, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstDesignation");
		List<Designation> lstDesignationDetail = new ArrayList<Designation>();
		lstDesignationDetail = designationDao.getDesignationDetails(designation.getSearchDesignation());
		modelView.addObject("lstDesignationDetail", lstDesignationDetail);
		modelView.addObject("searchDesignation", designation.getSearchDesignation());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addDesignation", method=RequestMethod.POST)
	public ModelAndView addDesignation(@ModelAttribute Designation designation, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstDesignation");
		String message = designationDao.addDepartment(designation, userId);
		List<Designation> lstDesignationDetail = new ArrayList<Designation>();
		lstDesignationDetail = designationDao.getDesignationDetails("");
		modelView.addObject("lstDesignationDetail", lstDesignationDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateDesignation", method=RequestMethod.POST)
	public ModelAndView updateDesignation(@ModelAttribute Designation designation, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstDesignation");
		String message = designationDao.updateDesignation(designation, userId);
		List<Designation> lstDesignationDetail = new ArrayList<Designation>();
		lstDesignationDetail = designationDao.getDesignationDetails("");
		modelView.addObject("lstDesignationDetail", lstDesignationDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteDesignation", method=RequestMethod.POST)
	public ModelAndView deleteDesignation(@RequestParam int designationId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstDesignation");
		String message = designationDao.deleteDesignation(designationId, userId);
		List<Designation> lstDesignationDetail = new ArrayList<Designation>();
		lstDesignationDetail = designationDao.getDesignationDetails("");
		modelView.addObject("lstDesignationDetail", lstDesignationDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getDesignation", method=RequestMethod.POST)
	public @ResponseBody Designation getDesignation(@RequestParam int designationId, HttpServletRequest request)
	{
		Designation designation = designationDao.getDesignationDetail(designationId);
		return designation;
	}
	
	@RequestMapping(value = "/updateDesignationStatus", method=RequestMethod.POST)
	public @ResponseBody String updateDesignationStatus(@RequestParam int designationId, HttpServletRequest request)
	{
		String message = designationDao.changeDesignationStatus(designationId);
		return message;
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("designationId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}
}

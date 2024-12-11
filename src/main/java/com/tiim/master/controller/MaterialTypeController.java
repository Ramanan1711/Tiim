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

import com.tiim.dao.MaterialTypeDao;
import com.tiim.model.MaterialType;

@Controller
public class MaterialTypeController {

	@Autowired
	MaterialTypeDao materialTypeDao;
	
	@RequestMapping(value = "/showMaterialType", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMaterialType(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstMaterialType");
		List<MaterialType> lstMaterialType = new ArrayList<MaterialType>();
		lstMaterialType = materialTypeDao.getMaterialTypeDetails("");
		modelView.addObject("lstMaterialType", lstMaterialType);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchMaterialType", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchMaterialType(@ModelAttribute MaterialType materialType, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstMaterialType");
		List<MaterialType> lstMaterialType = new ArrayList<MaterialType>();
		lstMaterialType = materialTypeDao.getMaterialTypeDetails(materialType.getSearchMaterialType());
		modelView.addObject("lstMaterialType", lstMaterialType);
		modelView.addObject("searchMaterialType",materialType.getSearchMaterialType());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addMaterialType", method=RequestMethod.POST)
	public ModelAndView addMaterialType(@ModelAttribute MaterialType materialType, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstMaterialType");
		String message = materialTypeDao.addMaterialType(materialType, userId);
		List<MaterialType> lstMaterialType = new ArrayList<MaterialType>();
		lstMaterialType = materialTypeDao.getMaterialTypeDetails("");
		modelView.addObject("lstMaterialType", lstMaterialType);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateMaterialType", method=RequestMethod.POST)
	public ModelAndView updateMaterialType(@ModelAttribute MaterialType materialType, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstMaterialType");
		String message = materialTypeDao.updateMaterialType(materialType, userId);
		List<MaterialType> lstMaterialType = new ArrayList<MaterialType>();
		lstMaterialType = materialTypeDao.getMaterialTypeDetails("");
		modelView.addObject("lstMaterialType", lstMaterialType);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteMaterialTypeName", method=RequestMethod.POST)
	public ModelAndView deleteMaterialType(@RequestParam int materialTypeId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstMaterialType");
		String message = materialTypeDao.deleteMaterialType(materialTypeId, userId);
		List<MaterialType> lstMaterialType = new ArrayList<MaterialType>();
		lstMaterialType = materialTypeDao.getMaterialTypeDetails("");
		modelView.addObject("lstMaterialType", lstMaterialType);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getMaterialType", method=RequestMethod.POST)
	public @ResponseBody MaterialType getMaterialType(@RequestParam int materialTypeId, HttpServletRequest request)
	{
		MaterialType materialType = materialTypeDao.getMaterialType(materialTypeId);
		return materialType;
	}
	
	
	  @RequestMapping(value = "/changeMaterialTypeStatus", method=RequestMethod.POST)
	  public @ResponseBody String changeMaterialTypeStatus(@RequestParam int materialTypeId,
	  HttpServletRequest request) {
		  String message = materialTypeDao.changeMaterialTypeStatus(materialTypeId);
		  return message;
	  }
	 
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("materialTypeId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}
	



}

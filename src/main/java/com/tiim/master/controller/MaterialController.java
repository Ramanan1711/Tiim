package com.tiim.master.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.MaterialDao;
import com.tiim.model.Material;

@Controller
public class MaterialController {
	
	@Autowired
	MaterialDao materialDao;
	
		
	@RequestMapping(value = "/showMaterial", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMaterial(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("addMaterial");
		
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Display");
		return modelView;
	}
	@RequestMapping(value = "/mstMaterial", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMstMaterial(@ModelAttribute Material material, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstMaterial");
		List<Material> lstMaterialDetail = new ArrayList<Material>();
		lstMaterialDetail = materialDao.getMaterialDetails(material.getMaterialCode());
		modelView.addObject("lstMaterialDetail", lstMaterialDetail);
		return modelView;
	}
	@RequestMapping(value = "/addMaterial", method=RequestMethod.POST)
	public ModelAndView addMaterial(@ModelAttribute Material material, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("addMaterial");
		String message = materialDao.addMaterial(material, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	@RequestMapping(value = "/editMaterial", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editToolIndent(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("addMaterial");
		List<Material> materialDtl = materialDao.getMaterialDetails(requestId);
		for (Iterator iterator = materialDtl.iterator(); iterator.hasNext();) {
			Material material = (Material) iterator.next();
			modelView.addObject("materialCode", material.getMaterialCode());
			modelView.addObject("materialType", material.getMaterialType());
			modelView.addObject("materialQty", material.getMaterialQty());
			modelView.addObject("materialName", material.getMaterialName());
			modelView.addObject("minStockLevel", material.getMinStockLevel());
			modelView.addObject("reorderLevel", material.getReorderLevel());
			modelView.addObject("uom", material.getUom());
		}
		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstMaterialTypes", materialDao.getMaterialType());
		
		return modelView;
	}
	@RequestMapping(value = "/updateMaterial", method=RequestMethod.POST)
	public ModelAndView updateMaterial(@ModelAttribute Material material, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("addMaterial");
		String message = materialDao.updateMaterial(material, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");

		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteMaterial", method=RequestMethod.POST)
	public ModelAndView deleteMaterial(@RequestParam int requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstMaterial");
		String message = materialDao.deleteMaterial(requestId, userId);
		modelView.addObject("message", message);
		List<Material> lstMaterialDetail = new ArrayList<Material>();
		lstMaterialDetail = materialDao.getMaterialDetails(0);
		modelView.addObject("lstMaterialDetail", lstMaterialDetail);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	 @RequestMapping(value = "/getMaterialDetails", method={RequestMethod.GET, RequestMethod.POST})
	 public ModelAndView getMaterialDetails(@ModelAttribute Material material, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("mstMaterial");
			List<Material> lstMaterialDetail = new ArrayList<Material>();
			lstMaterialDetail = materialDao.getMaterialDetails(material.getMaterialCode());
			modelView.addObject("lstMaterialDetail", lstMaterialDetail);
			return modelView;
	}
	 @RequestMapping(value = "/viewMaterial", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView viewtMaterial(@RequestParam int requestId, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("viewMaterial");
			List<Material> lstMaterialDetail = new ArrayList<Material>();
			lstMaterialDetail = materialDao.getMaterialDetails(requestId);
			modelView.addObject("lstMaterialDetail", lstMaterialDetail);
			return modelView;
		} 
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		int materialCode = materialDao.getIntialValue();
		modelView.addObject("materialCode", materialCode);
		modelView.addObject("lstMaterialTypes", materialDao.getMaterialType());

	}
}

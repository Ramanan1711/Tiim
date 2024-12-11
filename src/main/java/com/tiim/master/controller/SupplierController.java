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

import com.tiim.model.Supplier;
import com.tiim.service.SupplierService;

@Controller
public class SupplierController {
	
	@Autowired
	SupplierService supplierService;
	
	@RequestMapping(value = "/showSupplier", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMachine(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstSupplier");
		List<Supplier> lstSupplierDetail = new ArrayList<Supplier>();
		lstSupplierDetail = supplierService.getSupplierDetails("");
		modelView.addObject("lstSupplierDetail", lstSupplierDetail);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchSupplier", method= RequestMethod.POST)
	public ModelAndView showSearchSupplier(@RequestParam String searchSupplier, HttpServletRequest request)
	{
		Supplier supplier = new Supplier();
		supplier.setSearchSupplier(searchSupplier);
		System.out.println("search supplier...........");
		ModelAndView modelView = new ModelAndView("mstSupplier");
		List<Supplier> lstSupplierDetail = new ArrayList<Supplier>();
		System.out.println("search supplier...........");
		lstSupplierDetail = supplierService.getSupplierDetails(supplier.getSearchSupplier());
		modelView.addObject("lstSupplierDetail", lstSupplierDetail);
		modelView.addObject("searchSupplier", supplier.getSearchSupplier());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addSupplier", method=RequestMethod.POST)
	public ModelAndView addSupplier(@ModelAttribute Supplier supplier, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstSupplier");
		String message = supplierService.addSupplier(supplier, userId);
		List<Supplier> lstSupplierDetail = new ArrayList<Supplier>();
		lstSupplierDetail = supplierService.getSupplierDetails("");
		modelView.addObject("lstSupplierDetail", lstSupplierDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateSupplier", method=RequestMethod.POST)
	public ModelAndView updateSupplier(@ModelAttribute Supplier supplier, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstSupplier");
		String message = supplierService.updateSupplier(supplier, userId);
		List<Supplier> lstSupplierDetail = new ArrayList<Supplier>();
		lstSupplierDetail = supplierService.getSupplierDetails("");
		modelView.addObject("lstSupplierDetail", lstSupplierDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteSupplier", method=RequestMethod.POST)
	public ModelAndView deleteSupplier(@RequestParam int supplierId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstSupplier");
		String message = supplierService.deleteSupplier(supplierId, userId);
		List<Supplier> lstSupplierDetail = new ArrayList<Supplier>();
		lstSupplierDetail = supplierService.getSupplierDetails("");
		modelView.addObject("lstSupplierDetail", lstSupplierDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getSupplier", method=RequestMethod.POST)
	public @ResponseBody Supplier getSupplier(@RequestParam int supplierId, HttpServletRequest request)
	{
		Supplier supplier = supplierService.getSupplier(supplierId);
		return supplier;
	}
	
	@RequestMapping(value = "/updateSupplierStatus", method=RequestMethod.POST)
	public @ResponseBody String changeSupplierStatus(@RequestParam int supplierId, HttpServletRequest request)
	{
		String message = supplierService.changeSupplierStatus(supplierId);
		return message;
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("supplierId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}
}

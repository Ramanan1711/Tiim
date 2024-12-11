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

import com.tiim.model.Department;
import com.tiim.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	
	@RequestMapping(value = "/showDepartment", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showDepartment(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstDepartment");
		List<Department> lstDepartmentDetail = new ArrayList<Department>();
		lstDepartmentDetail = departmentService.getDepartmentDetails("");
		modelView.addObject("lstDepartmentDetail", lstDepartmentDetail);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchDepartment", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchDepartment(@ModelAttribute Department department, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstDepartment");
		List<Department> lstDepartmentDetail = new ArrayList<Department>();
		lstDepartmentDetail = departmentService.getDepartmentDetails(department.getSearchDepartment());
		modelView.addObject("lstDepartmentDetail", lstDepartmentDetail);
		modelView.addObject("searchDepartment", department.getSearchDepartment());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addDepartment", method=RequestMethod.POST)
	public ModelAndView addDepartment(@ModelAttribute Department department, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstDepartment");
		String message = departmentService.addDepartment(department, userId);
		List<Department> lstDepartmentDetail = new ArrayList<Department>();
		lstDepartmentDetail = departmentService.getDepartmentDetails("");
		modelView.addObject("lstDepartmentDetail", lstDepartmentDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateDepartment", method=RequestMethod.POST)
	public ModelAndView updateDepartment(@ModelAttribute Department department, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstDepartment");
		String message = departmentService.updateDepartment(department, userId);
		List<Department> lstDepartmentDetail = new ArrayList<Department>();
		lstDepartmentDetail = departmentService.getDepartmentDetails("");
		modelView.addObject("lstDepartmentDetail", lstDepartmentDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteDepartment", method=RequestMethod.POST)
	public ModelAndView deleteDepartment(@RequestParam int departmentId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstDepartment");
		String message = departmentService.deleteDepartment(departmentId, userId);
		List<Department> lstDepartmentDetail = new ArrayList<Department>();
		lstDepartmentDetail = departmentService.getDepartmentDetails("");
		modelView.addObject("lstDepartmentDetail", lstDepartmentDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getDepartment", method=RequestMethod.POST)
	public @ResponseBody Department getDepartment(@RequestParam int departmentId, HttpServletRequest request)
	{
		Department department = departmentService.getDepartment(departmentId);
		return department;
	}
	
	@RequestMapping(value = "/updateDepartmentStatus", method=RequestMethod.POST)
	public @ResponseBody String changeDepartmentsStatus(@RequestParam int departmentId, HttpServletRequest request)
	{
		String message = departmentService.changeDepartmentStatus(departmentId);
		return message;
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("departmentId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}

}

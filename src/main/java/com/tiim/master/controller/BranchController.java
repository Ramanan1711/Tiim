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

import com.tiim.dao.BranchDao;
import com.tiim.model.Branch;

@Controller
public class BranchController {

	@Autowired
	BranchDao branchDao;
	
	@RequestMapping(value = "/showBranch", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showDepartment(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstBranch");
		List<Branch> lstBranch = new ArrayList<Branch>();
		lstBranch = branchDao.getBranchName("");
		modelView.addObject("lstBranch", lstBranch);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchBranch", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchDepartment(@ModelAttribute Branch branch, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstBranch");
		List<Branch> lstBranch = new ArrayList<Branch>();
		lstBranch = branchDao.getBranchName(branch.getSearchBranch());
		modelView.addObject("lstBranch", lstBranch);
		modelView.addObject("searchBranch",branch.getSearchBranch());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addBranch", method=RequestMethod.POST)
	public ModelAndView addDepartment(@ModelAttribute Branch branch, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstBranch");
		String message = branchDao.addBranch(branch, userId);
		List<Branch> lstBranch = new ArrayList<Branch>();
		lstBranch = branchDao.getBranchDetails("");
		modelView.addObject("lstBranch", lstBranch);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateBranch", method=RequestMethod.POST)
	public ModelAndView updateDepartment(@ModelAttribute Branch branch, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstBranch");
		String message = branchDao.updateBranch(branch, userId);
		List<Branch> lstBranch = new ArrayList<Branch>();
		lstBranch = branchDao.getBranchDetails("");
		modelView.addObject("lstBranch", lstBranch);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteBranch", method=RequestMethod.POST)
	public ModelAndView deleteDepartment(@RequestParam int branchId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstBranch");
		String message = branchDao.deleteBranch(branchId, userId);
		List<Branch> lstBranch = new ArrayList<Branch>();
		lstBranch = branchDao.getBranchDetails("");
		modelView.addObject("lstBranch", lstBranch);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getBranch", method=RequestMethod.POST)
	public @ResponseBody Branch getBranch(@RequestParam int branchId, HttpServletRequest request)
	{
		Branch branch = branchDao.getBranchDetail(branchId);
		return branch;
	}
	
	@RequestMapping(value = "/changeBranchStatus", method=RequestMethod.POST)
	public @ResponseBody String changeBranchStatus(@RequestParam int branchId, HttpServletRequest request)
	{
		String message = branchDao.changeBranchStatus(branchId);
		return message;
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("branchId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}
	
}

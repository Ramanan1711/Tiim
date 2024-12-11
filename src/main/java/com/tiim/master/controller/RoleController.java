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

import com.tiim.dao.RoleDao;
import com.tiim.model.Role;

@Controller
public class RoleController {



	@Autowired
	RoleDao roleDao;
	
	@RequestMapping(value = "/showRole", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showRole(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRole");
		List<Role> lstRole = new ArrayList<Role>();
		lstRole = roleDao.getRoleDetails("");
		modelView.addObject("lstRole", lstRole);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchRole", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchRole(@ModelAttribute Role role, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRole");
		List<Role> lstRole = new ArrayList<Role>();
		lstRole = roleDao.getRoleDetails(role.getSearchRole());
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("searchRole",role.getSearchRole());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addRole", method=RequestMethod.POST)
	public ModelAndView addRole(@ModelAttribute Role role, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstRole");
		String message = roleDao.addRole(role, userId);
		List<Role> lstRole = new ArrayList<Role>();
		lstRole = roleDao.getRoleDetails("");
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateRole", method=RequestMethod.POST)
	public ModelAndView updateRole(@ModelAttribute Role role, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstRole");
		String message = roleDao.updateRole(role, userId);
		List<Role> lstRole = new ArrayList<Role>();
		lstRole = roleDao.getRoleDetails("");
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteRoleName", method=RequestMethod.POST)
	public ModelAndView deleteRole(@RequestParam int roleId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstRole");
		String message = roleDao.deleteRole(roleId, userId);
		List<Role> lstRole = new ArrayList<Role>();
		lstRole = roleDao.getRoleDetails("");
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getRole", method=RequestMethod.POST)
	public @ResponseBody Role getRole(@RequestParam int roleId, HttpServletRequest request)
	{
		Role role = roleDao.getRole(roleId);
		return role;
	}
	
	
	  @RequestMapping(value = "/changeRoleStatus", method=RequestMethod.POST)
	  public @ResponseBody String changeRoleStatus(@RequestParam int roleId,
	  HttpServletRequest request) {
		  String message = roleDao.changeRoleStatus(roleId);
		  return message;
	  }
	 
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("roleId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}
	

}

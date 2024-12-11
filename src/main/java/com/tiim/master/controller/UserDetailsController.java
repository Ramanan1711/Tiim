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

import com.tiim.dao.RoleModuleMapDao;
import com.tiim.model.UserDetails;
import com.tiim.service.UserDetailsService;

@Controller
public class UserDetailsController {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	RoleModuleMapDao roleDao;
	
	@RequestMapping(value = "/showUserDetail", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showUserDetails(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView modelView = new ModelAndView("mstUserDetail");
		List<UserDetails> lstUserDetails = new ArrayList<UserDetails>();
		lstUserDetails = userDetailsService.getUserDetails("");
		modelView.addObject("lstUserDetails", lstUserDetails);
		
		session.setAttribute("roleList", roleDao.getRole());
		session.setAttribute("branchList", userDetailsService.getBranchList());
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchUserDetails", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchUserDetails(@ModelAttribute UserDetails userDetail, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstUserDetail");
		List<UserDetails> lstUserDetailsDetail = new ArrayList<UserDetails>();
		lstUserDetailsDetail = userDetailsService.getUserDetails(userDetail.getSearchUserDetail());
		modelView.addObject("lstUserDetails", lstUserDetailsDetail);
		modelView.addObject("searchUserDetail", userDetail.getSearchUserDetail());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addUserDetails", method=RequestMethod.POST)
	public ModelAndView addUserDetails(@ModelAttribute UserDetails userDetail, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstUserDetail");
		String message = userDetailsService.addUserDetails(userDetail, userId);
		List<UserDetails> lstUserDetails = new ArrayList<UserDetails>();
		lstUserDetails = userDetailsService.getUserDetails("");
		modelView.addObject("lstUserDetails", lstUserDetails);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateUserDetails", method=RequestMethod.POST)
	public ModelAndView updateUserDetails(@ModelAttribute UserDetails userDetail, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstUserDetail");
		String message = userDetailsService.updateUserDetails(userDetail, userId);
		List<UserDetails> lstUserDetails = new ArrayList<UserDetails>();
		lstUserDetails = userDetailsService.getUserDetails("");
		modelView.addObject("lstUserDetails", lstUserDetails);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteUserDetails", method=RequestMethod.POST)
	public ModelAndView deleteUserDetails(@RequestParam int userId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int sesuserId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstUserDetail");
		String message = userDetailsService.deleteUserDetails(userId, sesuserId);
		List<UserDetails> lstUserDetails = new ArrayList<UserDetails>();
		lstUserDetails = userDetailsService.getUserDetails("");
		modelView.addObject("lstUserDetails", lstUserDetails);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getUserDetails", method=RequestMethod.POST)
	public @ResponseBody UserDetails getUserDetails(@RequestParam int userId, HttpServletRequest request)
	{
		UserDetails userDetail = userDetailsService.getUserDetails(userId);
		return userDetail;
	}
	
	@RequestMapping(value = "/updateUserDetailsStatus", method=RequestMethod.POST)
	public @ResponseBody String changeUserDetailsStatus(@RequestParam int userId, HttpServletRequest request)
	{
		String message = userDetailsService.changeUserDetailsStatus(userId);
		return message;
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("userId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}


}

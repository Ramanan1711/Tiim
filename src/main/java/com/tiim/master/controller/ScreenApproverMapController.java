package com.tiim.master.controller;

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

import com.tiim.dao.UserScreenMapDao;
import com.tiim.model.Machine;
import com.tiim.model.Product;
import com.tiim.model.UserScreenMap;

@Controller
public class ScreenApproverMapController {

	@Autowired
	UserScreenMapDao userScreenMap;
	
	@RequestMapping(value = "/showUserApproverMap", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showUserApproverMap(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listScreenApprover");
		List<UserScreenMap> lstUserScreen = userScreenMap.getUserMapDetails("");
		modelView.addObject("lstUserScreen", lstUserScreen);
		List<String>  lstScreenName = userScreenMap.getScreenName();
		List<String> lstUserName = userScreenMap.getUserName();
		modelView.addObject("lstUserName",lstUserName);
		modelView.addObject("lstScreenName",lstScreenName);
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/addScreenApprovalMap", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView addUserApprover(@ModelAttribute UserScreenMap userScreen, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listScreenApprover");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String message = userScreenMap.addScreenApprover(userScreen, userId);
		List<UserScreenMap> lstUserScreen = userScreenMap.getUserMapDetails("");
		modelView.addObject("lstUserScreen", lstUserScreen);
		List<String>  lstScreenName = userScreenMap.getScreenName();
		List<String> lstUserName = userScreenMap.getUserName();
		modelView.addObject("lstUserName",lstUserName);
		modelView.addObject("lstScreenName",lstScreenName);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	

	@RequestMapping(value = "/updateScreenApprovalMap", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView updateScreenApprovalMap(@ModelAttribute UserScreenMap userScreen, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listScreenApprover");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		userScreenMap.deleteUserMap(userScreen.getScreenName(), userId);
		String message = userScreenMap.addScreenApprover(userScreen, userId);
	//	String message = userScreenMap.updateUserMap(userScreen, userId);
		List<UserScreenMap> lstUserScreen = userScreenMap.getUserMapDetails("");
		modelView.addObject("lstUserScreen", lstUserScreen);
		List<String>  lstScreenName = userScreenMap.getScreenName();
		List<String> lstUserName = userScreenMap.getUserName();
		modelView.addObject("lstUserName",lstUserName);
		modelView.addObject("lstScreenName",lstScreenName);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteScreenApprovalMap", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView deleteScreenApprovalMap( HttpServletRequest request)
	{
		int screenId = Integer.parseInt(request.getParameter("screenId"));
		ModelAndView modelView = new ModelAndView("listScreenApprover");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String message = userScreenMap.deleteUserMap(screenId, userId);
		List<UserScreenMap> lstUserScreen = userScreenMap.getUserMapDetails("");
		modelView.addObject("lstUserScreen", lstUserScreen);
		List<String>  lstScreenName = userScreenMap.getScreenName();
		List<String> lstUserName = userScreenMap.getUserName();
		modelView.addObject("lstUserName",lstUserName);
		modelView.addObject("lstScreenName",lstScreenName);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getUserScreenMap", method=RequestMethod.POST)
	public @ResponseBody List<UserScreenMap>  getUserScreenMap(@RequestParam int screenId,@RequestParam String screenName, HttpServletRequest request)
	{
		List<UserScreenMap>  userScreen = userScreenMap.getUserScreenMap(screenId,screenName);
		return userScreen;
	}
	
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("machineId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}
}

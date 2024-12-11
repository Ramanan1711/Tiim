package com.tiim.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ApprovalConfigDao;
import com.tiim.model.ApprovalScreen;
import com.tiim.model.ApprovalScreenFlag;

@Controller
public class ApprovalScreenConfigController {
	
	@Autowired
	ApprovalConfigDao approvalDao;

	 @RequestMapping(value = "/showApprovalScreen", method={RequestMethod.GET, RequestMethod.POST})
	 public ModelAndView showToolingRequest(HttpServletRequest request)
	{
			ModelAndView modelView = new ModelAndView("approvalScreenConfig");
			List<ApprovalScreen> lstApprovalScreen = new ArrayList<>();
			lstApprovalScreen = approvalDao.getApprovalScreenConfig();
			modelView.addObject("lstApprovalScreen", lstApprovalScreen);
			return modelView;
	}
	 
	 @RequestMapping(value = "/updateApprovalScreen", method={RequestMethod.GET, RequestMethod.POST})
	 public String updateApprovalScreen(@ModelAttribute ApprovalScreenFlag approvalScreen, HttpServletRequest request)
	{
			
		 	approvalDao.updateApprovalScreenConfig(approvalScreen.getApprovalFlag());
			return "redirect:/showApprovalScreen.jsf";
	}
}

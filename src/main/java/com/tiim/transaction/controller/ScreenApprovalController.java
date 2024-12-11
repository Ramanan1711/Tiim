package com.tiim.transaction.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.AddScreenApprovalDao;
import com.tiim.dao.RejectScreenApprovalDao;
import com.tiim.dao.ScreenApprovalDao;
import com.tiim.model.ScreenApproval;
import com.tiim.model.ScreenApproved;

@Controller
public class ScreenApprovalController {

	@Autowired
	ScreenApprovalDao approvalDao;
	
	@Autowired
	AddScreenApprovalDao addScreenDao;
	
	@Autowired
	RejectScreenApprovalDao rejectScreenDao;
	
	@RequestMapping(value = "/screenApproval", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView screenApproval(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("screenApproval");
		HttpSession session = request.getSession();
		String userName = session.getAttribute("username").toString();
		String screenName = request.getParameter("searchScreenName");
		List<ScreenApproval> lstScreenApproval = approvalDao.getScreenApproval(userName,screenName);
		modelView.addObject("lstScreenApproval",lstScreenApproval);
		
		return modelView;
	}
	
	@RequestMapping(value = "/addScreenApproval", method={RequestMethod.GET, RequestMethod.POST})
	public String addScreenApproval(@ModelAttribute ScreenApproved screenApproved, HttpServletRequest request)
	{
		//ModelAndView modelView = new ModelAndView("screenApproval");
	//	System.out.println(screenApproved.getApproval());
		HttpSession session = request.getSession();
		String userName = session.getAttribute("username").toString();
		addScreenDao.addScreenApproval(screenApproved.getApproval(), userName);
		
		/*List<ScreenApproval> lstScreenApproval = approvalDao.getScreenApproval(userName);
		modelView.addObject("lstScreenApproval",lstScreenApproval);*/
		
		return "redirect:/screenApproval.jsf";
	}
	
	@RequestMapping(value = "/rejectScreenApproval", method= RequestMethod.POST)
	public String rejectScreenApproval(@ModelAttribute ScreenApproved screenApproved, HttpServletRequest request)
	{		
		HttpSession session = request.getSession();
		String userName = session.getAttribute("username").toString();
		rejectScreenDao.rejectScreenApproval(screenApproved.getReject(), userName);

		return "redirect:/screenApproval.jsf";
	}
	
	@RequestMapping(value = "/getRejectedScreen", method= RequestMethod.POST)
	public ModelAndView getRejectedScreen( HttpServletRequest request)
	{		
		ModelAndView modelView = new ModelAndView("screenApproval");
		HttpSession session = request.getSession();
		String userName = session.getAttribute("username").toString();
		List<ScreenApproval> lstScreenApproval = approvalDao.getRejectedScreenApproval(userName);
		modelView.addObject("lstScreenApproval",lstScreenApproval);
		
		return modelView;
	}
	@RequestMapping(value = "/reSubmitApproval", method= RequestMethod.POST)
	public ModelAndView reSubmitApproval( HttpServletRequest request)
	{		
		ModelAndView modelView = new ModelAndView("screenApproval");
		HttpSession session = request.getSession();
		String userName = session.getAttribute("username").toString();
		List<ScreenApproval> lstScreenApproval = approvalDao.getRejectedScreenApproval(userName);
		modelView.addObject("lstScreenApproval",lstScreenApproval);
		
		return modelView;
	}
	
}

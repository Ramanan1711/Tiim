package com.tiim.master.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.AcknowledgementDao;
import com.tiim.model.Acknowledgement;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingReturnNote;
import com.tiim.util.TiimConstant;

@Controller
public class IssueAcknowledgeController {

	@Autowired
	AcknowledgementDao acknowledgeDao;
	
	@RequestMapping(value = "/issueAcknowledge", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView issueAcknowledge(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView modelView = new ModelAndView("issueAckList");
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_ISSUE_SCREEN).toString();
		System.out.println("SES_PRODUCTION_ISSUE_SCREEN: "+approvalFlag);
		List<ToolingIssueNote> lstIssue = acknowledgeDao.getUnAcknowledgeToolingIssueNote(approvalFlag);
		modelView.addObject("lstIssue", lstIssue);
		return modelView;
	}
	
	@RequestMapping(value = "/addIssueAcknowledged", method={RequestMethod.GET, RequestMethod.POST})
	public String addIssueAcknowledged(@ModelAttribute Acknowledgement issueAck,HttpServletRequest request)
	{
		acknowledgeDao.updateAckIssueNote(issueAck);
		return "redirect:/issueAcknowledge.jsf";
	}
	
	@RequestMapping(value = "/returnAcknowledge", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView returnAcknowledge(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("returnAckList");
		List<ToolingReturnNote> lstReturn = acknowledgeDao.getUnAcknowledgedToolingReturnNote();
		modelView.addObject("lstReturn", lstReturn);
		return modelView;
	}
	
	@RequestMapping(value = "/addReturnAcknowledged", method={RequestMethod.GET, RequestMethod.POST})
	public String addReturnAcknowledged(@ModelAttribute Acknowledgement returnAck,HttpServletRequest request)
	{
		acknowledgeDao.updateAckProductionReturnNote(returnAck);
		return "redirect:/returnAcknowledge.jsf";
	}
		
}

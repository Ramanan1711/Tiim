package com.tiim.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ToolingIssueDao;
import com.tiim.dao.ToolingRequetNoteDao;
import com.tiim.model.ToolingIssueNote;

@Controller
public class PendingProductionIssueController {

	@Autowired
	ToolingIssueDao toolingIssueDao;
	
	@Autowired
	ToolingRequetNoteDao RequetNoteDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();
	/***************************************************************/

	@RequestMapping(value = "/showPendingToolingIssueNote", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolingRequest(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNoteList");
		List<ToolingIssueNote> lstToolingIssueNote = new ArrayList<ToolingIssueNote>();
		lstToolingIssueNote = toolingIssueDao.getPendingToolingIssueNote();
		modelView.addObject("lstToolingIssueNote", lstToolingIssueNote);
		return modelView;
	}
}

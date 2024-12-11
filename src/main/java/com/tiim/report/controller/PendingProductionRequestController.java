package com.tiim.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ToolingRequetNoteDao;
import com.tiim.model.ToolingRequestNote;

@Controller
public class PendingProductionRequestController {

	@Autowired
	ToolingRequetNoteDao toolingRequestDao;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();

	@RequestMapping(value = "/showPendingToolingRequest", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolingRequest(@ModelAttribute ToolingRequestNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNoteList");
		List<ToolingRequestNote> lstToolingRequestNote = new ArrayList<ToolingRequestNote>();
		lstToolingRequestNote = toolingRequestDao.getPendingToolingRequestNote();
		modelView.addObject("lstToolingRequestNote", lstToolingRequestNote);
		modelView.addObject("isActive", 0);
		return modelView;
	}
}

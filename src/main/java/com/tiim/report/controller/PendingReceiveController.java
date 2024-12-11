package com.tiim.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ToolingReceiptNoteDao;
import com.tiim.dao.ToolingReceivingRequestDao;
import com.tiim.model.ToolingRequest;
import com.tiim.util.TiimConstant;

@Controller
public class PendingReceiveController {
	
	@Autowired
	ToolingReceivingRequestDao toolingRequestDao;
	
	@Autowired
	ToolingReceiptNoteDao toolingReceiptDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();
	/***************************************************************/
	
	@RequestMapping(value = "/showPendingReceivingRequest", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showReceivingRequest(@ModelAttribute ToolingRequest tool, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIVING_SCREEN).toString();
		ModelAndView modelView = new ModelAndView("toolingReceivingRequestList");
		
		List<ToolingRequest> lstToolinginsInspections = new ArrayList<ToolingRequest>();
		lstToolinginsInspections = toolingRequestDao.pendingToolingReceivingRequest(approvalFlag);
		modelView.addObject("lstToolinginsInspections", lstToolinginsInspections);
		return modelView;
	}

}

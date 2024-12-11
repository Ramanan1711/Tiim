package com.tiim.report.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.AuditTrialDao;
import com.tiim.dao.UserDetailsDao;
import com.tiim.model.AuditTrial;
import com.tiim.model.UserDetails;

@Controller
public class AuditTrialReportController {
	
	@Autowired
	AuditTrialDao auditTrialDao;
	
	@Autowired
	UserDetailsDao userDetailDao;
	
	@RequestMapping(value = "/showAuditTrialReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showAuditTrialReport( HttpServletRequest request)
	{
		List<UserDetails> lstUser = userDetailDao.getUserDetails("");
		ModelAndView modelView = new ModelAndView("viewAuditTrialReport");
		modelView.addObject("lstUser",lstUser);
		
		return modelView;
	}

	@RequestMapping(value = "/viewAuditTrial", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewAuditTrial(@RequestParam  String userId, @RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request)
	{		
		String dateFormat = "dd/MM/yyyy";
		ModelAndView modelView = new ModelAndView("pdfViewAuditTrialReport");
		java.util.Date date1 = null;
		Date sqlFromDate = null;
		if(fromDate != null && !fromDate.equals(""));
		{
			try {
				date1 = new SimpleDateFormat(dateFormat).parse(fromDate);
				sqlFromDate = new Date(date1.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception ex)
			{
				
			}
		}
		java.util.Date date2 = null;
		Date sqlToDate = null;
		if(toDate != null)
		{
			try {
				date2 = new SimpleDateFormat(dateFormat).parse(toDate);
				sqlToDate = new Date(date2.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception ex)
			{
				
			}
		}
		List<AuditTrial> lstAuditTrial = auditTrialDao.getAuditTrial(userId, sqlFromDate, sqlToDate);
		
		modelView.addObject("lstAuditTrial", lstAuditTrial);
		return modelView;
	}
}

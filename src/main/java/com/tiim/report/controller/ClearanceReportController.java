package com.tiim.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ClearanceNoDao;
import com.tiim.model.ClearanceReport;

@Controller
public class ClearanceReportController {

	@Autowired
	ClearanceNoDao clearanceDao;
	
	@RequestMapping(value = "/createClearanceReport", method={RequestMethod.GET, RequestMethod.POST})
	public String  createClearanceReport( HttpServletRequest request)
	{
		return "viewClearanceReport";
	}
	
	@RequestMapping(value = "/viewClearanceReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewClearanceReport(@RequestParam String lotNumber, HttpServletRequest request)
	{		
		
		ModelAndView modelView = new ModelAndView("pdfViewClearanceReport");
		List<ClearanceReport> lstClearance = clearanceDao.getClearanceReport(lotNumber);
		modelView.addObject("lstClearance", lstClearance);
		return modelView;
	}
}

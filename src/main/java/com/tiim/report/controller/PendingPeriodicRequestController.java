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

import com.tiim.dao.StockDao;
import com.tiim.dao.ToolingPeriodicalInpsectionDao;
import com.tiim.model.ToolingPeriodicInspection;

@Controller
public class PendingPeriodicRequestController {

	@Autowired
	ToolingPeriodicalInpsectionDao periodicInspectionDao;
	
	@Autowired
	StockDao stkDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();
	/***************************************************************/

	@RequestMapping(value = "/showPendingPeriodicInspection", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showPendingPeriodicInspection(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionList");
		List<ToolingPeriodicInspection> lstToolingPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();
		lstToolingPeriodicInspection = periodicInspectionDao.getPendingPeriodicalRequestNote();
		modelView.addObject("lstToolingPeriodicInspection", lstToolingPeriodicInspection);
		return modelView;
	}
	
}

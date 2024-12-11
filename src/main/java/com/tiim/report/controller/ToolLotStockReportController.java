package com.tiim.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ToolLotStockDao;
import com.tiim.model.ProductReport;
import com.tiim.model.ToolingLotStock;

@Controller
public class ToolLotStockReportController {

	@Autowired
	private ToolLotStockDao toolLotStock;
	
	
	@RequestMapping(value = "/toolLotStockReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String toolLotStockReport(HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolLotStockReport");

		return "toolLotStockReport";
	}

	@RequestMapping(value = "/showToolLotStock", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showToolLotStock(@ModelAttribute ProductReport productReport, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("showToolLotStock");
		List<ToolingLotStock> toolingLotStocks = toolLotStock.getToolLotStock(productReport);
		modelView.addObject("toolingLotStocks", toolingLotStocks);
		return modelView;
	}
	
	@RequestMapping(value = "/toolLotBatchNo", method = { RequestMethod.GET, RequestMethod.POST })
	public String toolLotBatchNo(HttpServletRequest request) {
		return "toolLotBatchReport";
	}

	@RequestMapping(value = "/showToolLotBatchNo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showToolLotBatchNo(@ModelAttribute ProductReport productReport, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("showToolLotBatchNo");
		List<ToolingLotStock> toolingLotStocks = toolLotStock.getClearanceDetails(productReport);
		modelView.addObject("toolingLotStocks", toolingLotStocks);
		return modelView;
	}

}

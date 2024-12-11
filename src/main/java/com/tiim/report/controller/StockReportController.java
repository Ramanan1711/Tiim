package com.tiim.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.StockReportDao;
import com.tiim.model.Stock;

@Controller
public class StockReportController {
	
	@Autowired
	private StockReportDao stockDao;

	@RequestMapping(value = "/createStockReport", method={RequestMethod.GET, RequestMethod.POST})
	public String  createStockReport( HttpServletRequest request)
	{
		return "viewStockReport";
	}
	
	@RequestMapping(value = "/getStockReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView  getStockReport(@ModelAttribute Stock stock, HttpServletRequest request)
	{
		List<Stock> lstStock= stockDao.getStockReport(stock);
		ModelAndView modelView = new ModelAndView("viewStockReportDetails");
		modelView.addObject("lstStock", lstStock);
		return modelView;

	}
	
	@RequestMapping(value = "/createStockStatus", method={RequestMethod.GET, RequestMethod.POST})
	public String  createStockStatus( HttpServletRequest request)
	{
		return "viewStockStatusReport";
	}
	
	@RequestMapping(value = "/getStockStatusReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView  getStockStatusReport(HttpServletRequest request)
	{
		String toolingLotNumber = request.getParameter("toolingLotNumber");
		HttpSession session = request.getSession();
		String branchName = session.getAttribute("sesBranchName").toString();
		Stock stock = stockDao.getStockQuantity(toolingLotNumber, branchName);
		ModelAndView modelView = new ModelAndView("viewStockStatusReport");
		int inspectionQty = stockDao.inspectionQuantity(toolingLotNumber);
		int issueQty = stockDao.productIssueQuantity(toolingLotNumber);
		int periodicQty = stockDao.periodicRequestQuantity(toolingLotNumber);
		modelView.addObject("stock", stock);
		modelView.addObject("inspectionQty", inspectionQty);
		modelView.addObject("issueQty", issueQty);
		modelView.addObject("periodicQty", periodicQty);
		return modelView;

	}
	
	@RequestMapping(value = "/getAvailableStockReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView  getAvailableStockReport(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		String branchName = session.getAttribute("sesBranchName").toString();
		List<Stock> lstStock= stockDao.getAvailableStock(branchName);
		ModelAndView modelView = new ModelAndView("pdfViewAvailableStock");
		modelView.addObject("lstStock", lstStock);
		return modelView;

	}
}

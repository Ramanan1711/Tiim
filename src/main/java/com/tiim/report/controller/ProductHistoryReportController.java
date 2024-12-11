package com.tiim.report.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tiim.dao.ProductHistoryDao;
import com.tiim.fileutil.CreateHistoryExcelReport;
import com.tiim.model.Param;
import com.tiim.model.ProductHistory;
import com.tiim.model.ProductHistoryInput;

@Controller
public class ProductHistoryReportController {

	@Autowired
	ProductHistoryDao productHistoryDao;
	
	@RequestMapping(value = "/showTransactionHistoryReport", method={RequestMethod.GET})
	public String CreateProductReport( HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewTransactionHistoryReport");
	
		return "viewTransactionHistoryReport";
	}
	
	
	@RequestMapping(value = "/getProductLotNumber", method = RequestMethod.GET)
	public @ResponseBody String getProductLotNumber(@RequestParam("lotNumber") String lotNumber)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(lotNumber != null && !"".equals(lotNumber))
		{
			List<String> arlList = productHistoryDao.getLotNumber(lotNumber);
			Iterator<String> itr = arlList.iterator();
			while(itr.hasNext())
			{
				String lot = itr.next();
				Param p = new Param(lot ,lot,lot);
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/getProductHistory1", method = RequestMethod.POST)
	public @ResponseBody String  getProductHistory(@RequestParam("lotNumber") String lotNumber, @RequestParam("allTransaction") String allTransaction, @RequestParam("receivedTransaction") String receivedTransaction,
			@RequestParam("productionTransaction") String productionTransaction, @RequestParam("periodicTransaction")String periodicTransaction, @RequestParam("fromDate") String fromDate, 
			@RequestParam("toDate") String toDate, HttpServletRequest request)
	{
		String dateFormat = "dd/MM/yyyy";
		ProductHistoryInput input = new ProductHistoryInput();
		input.setAllTransaction(allTransaction);
		Date date1 = null;
		/*
		 * if(fromDate != null) { try { date1 = new
		 * SimpleDateFormat(dateFormat).parse(fromDate); input.setFromDate(new
		 * java.sql.Date(date1.getTime())); } catch (ParseException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }catch(Exception ex) {
		 * 
		 * } }
		 */
		
		input.setLotNumber(lotNumber);
		input.setPeriodicTransaction(periodicTransaction);
		input.setProductionTransaction(productionTransaction);
		input.setReceivedTransaction(receivedTransaction);
		/*
		 * if(toDate != null) { try { date1 = new
		 * SimpleDateFormat(dateFormat).parse(toDate); input.setToDate(new
		 * java.sql.Date(date1.getTime())); } catch (ParseException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */
		
		System.out.println(allTransaction+", "+receivedTransaction+", "+toDate);
		List<ProductHistory> lstHistory = new ArrayList<ProductHistory>();
		if("all".equalsIgnoreCase(input.getAllTransaction()) || "received".equalsIgnoreCase(input.getReceivedTransaction()))
		{
			List<ProductHistory> receivingRequestHistory = productHistoryDao.getProductHistoryFromReceivingRequest(input);
			if(receivingRequestHistory != null && receivingRequestHistory.size() > 0)
			{
				lstHistory.addAll(receivingRequestHistory);
			}
			
			List<ProductHistory> receivingHistory = productHistoryDao.getProductHistoryFromReceivingInspection(input);
			if(receivingHistory != null && receivingHistory.size() > 0)
			{
				lstHistory.addAll(receivingHistory);
			}
		}
		if("all".equalsIgnoreCase(input.getAllTransaction()) || "production".equalsIgnoreCase(input.getProductionTransaction()))
		{
			List<ProductHistory> issueHistory = productHistoryDao.getProductHistoryFromProductionIssue(input);
			if(issueHistory != null && issueHistory.size() > 0)
			{
				lstHistory.addAll(issueHistory);
			}
		
			List<ProductHistory> returnHistory = productHistoryDao.getProductHistoryFromProductionReturn(input);
			if(returnHistory != null && returnHistory.size() > 0)
			{
				lstHistory.addAll(returnHistory);
			}
		}
		if("all".equalsIgnoreCase(input.getAllTransaction()) || "periodic".equalsIgnoreCase(input.getPeriodicTransaction()))
		{
			List<ProductHistory> periodicHistory = productHistoryDao.getProductHistoryFromPeriodicInspection(input);
			if(periodicHistory != null && periodicHistory.size() > 0)
			{
				lstHistory.addAll(periodicHistory);
			}
			List<ProductHistory> periodicReportHistory = productHistoryDao.getProductHistoryFromPeriodicReport(input);
			if(periodicReportHistory != null && periodicReportHistory.size() > 0)
			{
				lstHistory.addAll(periodicReportHistory);
			}
		}
		String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		String fileName = "/productreport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreateHistoryExcelReport createExcel = new CreateHistoryExcelReport();
		createExcel.writeProductExcel(lstHistory, imageFolder,"");
		return "./ReportFiles/"+fileName;
	}
	
	@RequestMapping(value = "/getProductHistory", method = RequestMethod.POST)
	public ModelAndView  getProductHistoryForLot(@ModelAttribute ProductHistoryInput input,  HttpServletRequest request)
	{
		System.out.println("lotnumber: "+request.getParameter("lotNumber"));
		ModelAndView modelView = new ModelAndView("pdfViewProductHistoryReport");
			
		String dateFormat = "dd/MM/yyyy";
			
		Date date1 = null;
		if(input.getFromDate() != null)
		{
			try {
				date1 = new SimpleDateFormat(dateFormat).parse(input.getFromDate());
				input.setFromDate1(new java.sql.Date(date1.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception ex)
			{
				
			}
		}
		
		
		if(input.getToDate() != null)
		{
			try {
				date1 = new SimpleDateFormat(dateFormat).parse(input.getToDate());
				input.setToDate1(new java.sql.Date(date1.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("getAllTransaction: "+input.getAllTransaction());
		List<ProductHistory> lstHistory = new ArrayList<ProductHistory>();
		if("all".equalsIgnoreCase(input.getAllTransaction()) || "received".equalsIgnoreCase(input.getReceivedTransaction()))
		{
			List<ProductHistory> receivingRequestHistory = productHistoryDao.getProductHistoryFromReceivingRequest(input);
			if(receivingRequestHistory != null && receivingRequestHistory.size() > 0)
			{
				lstHistory.addAll(receivingRequestHistory);
			}
			
			List<ProductHistory> receivingHistory = productHistoryDao.getProductHistoryFromReceivingInspection(input);
			if(receivingHistory != null && receivingHistory.size() > 0)
			{
				lstHistory.addAll(receivingHistory);
			}
		}
		if("all".equalsIgnoreCase(input.getAllTransaction()) || "production".equalsIgnoreCase(input.getProductionTransaction()))
		{
			List<ProductHistory> issueHistory = productHistoryDao.getProductHistoryFromProductionIssue(input);
			if(issueHistory != null && issueHistory.size() > 0)
			{
				lstHistory.addAll(issueHistory);
			}
		
			List<ProductHistory> returnHistory = productHistoryDao.getProductHistoryFromProductionReturn(input);
			if(returnHistory != null && returnHistory.size() > 0)
			{
				lstHistory.addAll(returnHistory);
			}
		}
		if("all".equalsIgnoreCase(input.getAllTransaction()) || "periodic".equalsIgnoreCase(input.getPeriodicTransaction()))
		{
			List<ProductHistory> periodicHistory = productHistoryDao.getProductHistoryFromPeriodicInspection(input);
			if(periodicHistory != null && periodicHistory.size() > 0)
			{
				lstHistory.addAll(periodicHistory);
			}
			List<ProductHistory> periodicReportHistory = productHistoryDao.getProductHistoryFromPeriodicReport(input);
			if(periodicReportHistory != null && periodicReportHistory.size() > 0)
			{
				lstHistory.addAll(periodicReportHistory);
			}
		}
		modelView.addObject("lstHistory", lstHistory);
		return modelView;
	}
	
	
}

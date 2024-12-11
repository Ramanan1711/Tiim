package com.tiim.transaction.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tiim.dao.StockDao;
import com.tiim.model.Branch;
import com.tiim.model.Param;

@Controller
public class StockController {
	
	@Autowired
	StockDao stockDao;
	
	@RequestMapping(value = "/getStockQty", method=RequestMethod.POST)
	public @ResponseBody int getStockQty(@RequestParam String productName, @RequestParam String machineType, @RequestParam String drawingNo, @RequestParam String typeOfTool, HttpServletRequest request)
	{
		int stockQty = stockDao.getStockQty(productName, machineType, drawingNo, typeOfTool);
		return stockQty;
	}
	
	@RequestMapping(value = "/getStockLotQty", method=RequestMethod.POST)
	public @ResponseBody int getStockLotQty(@RequestParam String productName, @RequestParam String machineType, @RequestParam String drawingNo, @RequestParam String typeOfTool, HttpServletRequest request)
	{
		int stockQty = stockDao.getStockLotQty(productName, machineType, drawingNo, typeOfTool);
		return stockQty;
	}
	
	
	@RequestMapping(value = "/getUnderInspectionQty", method=RequestMethod.POST)
	public @ResponseBody int getUnderInspectionQty(@RequestParam String productName, @RequestParam String machineType, @RequestParam String drawingNo, @RequestParam String typeOfTool, HttpServletRequest request)
	{
		int stockQty = stockDao.getUnderInspectionQty(productName, machineType, drawingNo, typeOfTool);
		return stockQty;
	}
	
	@RequestMapping(value = "/getToolingLotNo", method=RequestMethod.GET)
	public @ResponseBody String getToolingLotNo(@RequestParam String productName, @RequestParam String machineType, @RequestParam String drawingNo, @RequestParam String typeOfTool, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		String branch = (String)session.getAttribute("sesBranchName");
		List<String> lstLotNumber = stockDao.getToolingLotNo(productName, machineType, drawingNo, typeOfTool, branch);
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		Iterator<String> itr = lstLotNumber.iterator();
		while(itr.hasNext())
		{
			String obj = itr.next();
			Param p = new Param(obj ,obj, obj);
	        data.add(p);	
		}
		return gson.toJson(data);
	}

}

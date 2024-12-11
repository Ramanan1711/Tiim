package com.tiim.report.controller;

import java.util.ArrayList;
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
import com.tiim.dao.MaterialStockReportDao;
import com.tiim.model.MaterialStock;
import com.tiim.model.Param;

@Controller
public class MaterialStockReportController {
	
	@Autowired
	private MaterialStockReportDao stockDao;

	@RequestMapping(value = "/createMaterialStockReport", method={RequestMethod.GET, RequestMethod.POST})
	public String  createStockReport( HttpServletRequest request)
	{
		return "viewMaterialStockReport";
	}
	
	@RequestMapping(value = "/getMaterialStockReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView  getStockReport(@ModelAttribute MaterialStock stock, HttpServletRequest request)
	{
		List<MaterialStock> lstStock= stockDao.getStockReport(stock);
		ModelAndView modelView = new ModelAndView("viewMaterialStockReportDetails");
		modelView.addObject("lstStock", lstStock);
		return modelView;

	}
	
	@RequestMapping(value = "/createMaterialStockStatus", method={RequestMethod.GET, RequestMethod.POST})
	public String  createStockStatus( HttpServletRequest request)
	{
		return "viewMaterialStockStatusReport";
	}
	
	@RequestMapping(value = "/autoMaterialToolLotNumber", method = RequestMethod.GET)
	public @ResponseBody String autoMaterialToolLotNumber(@RequestParam("lotNumber") String lotNumber)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(lotNumber != null && !"".equals(lotNumber))
		{
			List<String> arlList = stockDao.getToolLotNumber(lotNumber);
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
}

package com.tiim.auto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tiim.dao.ProductDao;
import com.tiim.model.Param;
import com.tiim.model.Product;

@Controller
public class AutoProductName {
	
	@Autowired
	ProductDao objDao;

	@RequestMapping(value = "/autoProductName", method = RequestMethod.GET)
	public @ResponseBody String autoProductName(@RequestParam("productName") String productName)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(productName != null && !"".equals(productName))
		{
			List<Product> arlList = objDao.getAutoProductDetails(productName);
			Iterator<Product> itr = arlList.iterator();
			while(itr.hasNext())
			{
				Product obj = (Product)itr.next();
				Param p = new Param(obj.getProductName() ,obj.getProductName(),obj.getProductId()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/getRequestByDrawingNo", method=RequestMethod.POST)
	public ModelAndView getRequestByDrawingNo(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listRequestByDrawingNo");
		List<Product> lstProduct = new ArrayList<Product>();
		String drawingNo = request.getParameter("drawingNumber");
		
		lstProduct = objDao.getProductDetailsByDrawing(drawingNo);
		modelView.addObject("lstProduct", lstProduct);
		return modelView;
		
	}
	@RequestMapping(value = "/getProductDetailsByLot", method=RequestMethod.POST)
	public ModelAndView getProductDetailsByLot(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listRequestByDrawingNo");
		List<Product> lstProduct = new ArrayList<Product>();
		String toolinglotnumber = request.getParameter("toolinglotnumber");
		
		lstProduct = objDao.getProductDetailsByLot(toolinglotnumber);
		modelView.addObject("lstProduct", lstProduct);
		return modelView;

	}
}

package com.tiim.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.model.Product;
import com.tiim.service.ProductService;

@Controller
public class ProductMasterReportController {

	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/viewProductDetails", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewProductDetails(HttpServletRequest request)
	{		
		
		ModelAndView modelView = new ModelAndView("pdfViewProductDetails");
		List<Product> lstProductDetail = new ArrayList<Product>();
		lstProductDetail = productService.getProductDetails("");
		modelView.addObject("lstProductDetail", lstProductDetail);
		return modelView;
	}
}

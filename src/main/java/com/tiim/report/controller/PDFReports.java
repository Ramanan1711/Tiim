package com.tiim.report.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PDFReports {

	@RequestMapping(value = "/openPDFReport", method={RequestMethod.GET, RequestMethod.POST})
	public String  createStockReport( HttpServletRequest request)
	{
		return "openPDF";
	}
}

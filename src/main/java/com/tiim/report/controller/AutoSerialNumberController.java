package com.tiim.report.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiim.dao.AutoSerialNumberDao;

@Controller
public class AutoSerialNumberController {

	@Autowired
	AutoSerialNumberDao serialNumberDao;
	
	@Autowired
	ReadAccessDB accessDb;
	
	@RequestMapping(value = "/updateAutoSerialNumber", method={RequestMethod.GET, RequestMethod.POST})
	public String updateAutoSerialNumber(HttpServletRequest request)
	{
		accessDb.readAccessDB();
		serialNumberDao.editToolingInspection();
		return "redirect:/home.jsf";
	}
}

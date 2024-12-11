package com.tiim.master.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CleaningSopController {

		
	@RequestMapping(value = "/mstCleaningSop1", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showDepartment(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstCleaningSop");
		return modelView;
	}
	
}

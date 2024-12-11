package com.tiim.transaction.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.model.ToolingIssueNote;

@Controller
public class IndentController {
	
	@RequestMapping(value = "/showIndent1", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolingRequest(@ModelAttribute ToolingIssueNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolIndentList1");
		return modelView;
	}

}

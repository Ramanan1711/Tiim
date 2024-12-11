package com.tiim.master.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.SecurityQuestionDao;
import com.tiim.model.SecurityQuestion;

@Controller
public class SecurityQuestionController {

	@Autowired
	SecurityQuestionDao questionDao;
	
	@RequestMapping(value = "/securityQuestion", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView securityQuestion()
	{
		ModelAndView modalView = new ModelAndView("securityQuestion");
		return modalView;
	}
	
	@RequestMapping(value = "/submitSecurityQuestion", method={RequestMethod.GET, RequestMethod.POST})
	public String submitSecurityQuestion(@ModelAttribute SecurityQuestion question, HttpServletRequest request)
	{
		questionDao.addSecurityQuestion(question);
		return "redirect:/home.jsf";
	}
	
	@RequestMapping(value = "/verifySecurityQuestion", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView verifySecurityQuestion(@ModelAttribute SecurityQuestion question, HttpServletRequest request)
	{
		ModelAndView modalView = new ModelAndView("showTempPassword");
		String userName = request.getParameter("userName");
		System.out.println("usernae: "+userName);
		String password = questionDao.verifySecurityQuestion(userName, question);
		System.out.println("after reset...");
		modalView.addObject("password1", password);
		return modalView;
	}
	
}

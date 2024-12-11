package com.tiim.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Login {

	@RequestMapping(value = "/login-authentication", method=RequestMethod.GET)
	public String showGrid(HttpServletRequest request)
	{
		return "grid";
	}
}

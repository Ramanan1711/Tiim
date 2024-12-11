package com.tiim.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.PlatingDao;
import com.tiim.model.Plating;

@Controller
public class PlatingController {




	@Autowired
	PlatingDao platingDao;
	
	@RequestMapping(value = "/showPlating", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showPlating(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstPlating1");
		List<Plating> latPlating = new ArrayList<Plating>();
		latPlating = platingDao.getPlatingDetails("");
		modelView.addObject("latPlating", latPlating);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchPlating", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchPlating(@ModelAttribute Plating plating, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstPlating1");
		List<Plating> latPlating = new ArrayList<Plating>();
		latPlating = platingDao.getPlatingDetails(plating.getSearchPlating());
		modelView.addObject("latPlating", latPlating);
		modelView.addObject("searchPlating",plating.getSearchPlating());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addPlating", method=RequestMethod.POST)
	public ModelAndView addPlating(@ModelAttribute Plating plating, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstPlating1");
		String message = platingDao.addPlating(plating, userId);
		List<Plating> latPlating = new ArrayList<Plating>();
		latPlating = platingDao.getPlatingDetails("");
		modelView.addObject("latPlating", latPlating);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updatePlating", method=RequestMethod.POST)
	public ModelAndView updatePlating(@ModelAttribute Plating plating, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstPlating1");
		String message = platingDao.updatePlating(plating, userId);
		List<Plating> latPlating = new ArrayList<Plating>();
		latPlating = platingDao.getPlatingDetails("");
		modelView.addObject("latPlating", latPlating);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deletePlatingName", method=RequestMethod.POST)
	public ModelAndView deletePlating(@RequestParam int id, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstPlating1");
		String message = platingDao.deletePlating(id, userId);
		List<Plating> latPlating = new ArrayList<Plating>();
		latPlating = platingDao.getPlatingDetails("");
		modelView.addObject("latPlating", latPlating);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getPlating", method=RequestMethod.POST)
	public @ResponseBody Plating getPlating(@RequestParam int id, HttpServletRequest request)
	{
		Plating plating = platingDao.getPlating(id);
		return plating;
	}
	
	
	  @RequestMapping(value = "/changePlatingStatus", method=RequestMethod.POST)
	  public @ResponseBody String changePlatingStatus(@RequestParam int id,
	  HttpServletRequest request) {
		  String message = platingDao.changePlatingStatus(id);
		  return message;
	  }
	 
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("platingId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}
	


}

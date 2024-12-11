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

import com.tiim.model.Machine;
import com.tiim.service.MachineService;

@Controller
public class MachineController {

	@Autowired
	MachineService machineService;
	
	@RequestMapping(value = "/showMachine", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMachine(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstMachine");
		List<Machine> lstMachineDetail = new ArrayList<Machine>();
		lstMachineDetail = machineService.getMachineDetails("");
		modelView.addObject("lstMachineDetail", lstMachineDetail);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchMachine", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchMachine(@ModelAttribute Machine machine, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstMachine");
		List<Machine> lstMachineDetail = new ArrayList<Machine>();
		lstMachineDetail = machineService.getMachineDetails(machine.getSearchMachine());
		modelView.addObject("lstMachineDetail", lstMachineDetail);
		modelView.addObject("searchMachine", machine.getSearchMachine());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addMachine", method=RequestMethod.POST)
	public ModelAndView addMachine(@ModelAttribute Machine machine, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstMachine");
		String message = machineService.addMachine(machine, userId);
		List<Machine> lstMachineDetail = new ArrayList<Machine>();
		lstMachineDetail = machineService.getMachineDetails("");
		modelView.addObject("lstMachineDetail", lstMachineDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateMachine", method=RequestMethod.POST)
	public ModelAndView updateMachine(@ModelAttribute Machine machine, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstMachine");
		String message = machineService.updateMachine(machine, userId);
		List<Machine> lstMachineDetail = new ArrayList<Machine>();
		lstMachineDetail = machineService.getMachineDetails("");
		modelView.addObject("lstMachineDetail", lstMachineDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteMachine", method=RequestMethod.POST)
	public ModelAndView deleteMachine(@RequestParam int machineId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("mstMachine");
		String message = machineService.deleteMachine(machineId, userId);
		List<Machine> lstMachineDetail = new ArrayList<Machine>();
		lstMachineDetail = machineService.getMachineDetails("");
		modelView.addObject("lstMachineDetail", lstMachineDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getMachine", method=RequestMethod.POST)
	public @ResponseBody Machine getMachine(@RequestParam int machineId, HttpServletRequest request)
	{
		Machine machine = machineService.getMachine(machineId);
		return machine;
	}
	
	@RequestMapping(value = "/updateMachineStatus", method=RequestMethod.POST)
	public @ResponseBody String updateMachineStatus(@RequestParam int machineId, HttpServletRequest request)
	{
		String message = machineService.changeMachineStatus(machineId);
		return message;
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("machineId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
	}
}

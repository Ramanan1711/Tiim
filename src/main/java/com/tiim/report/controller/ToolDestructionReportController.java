package com.tiim.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ToolDestructionDao;
import com.tiim.model.DestructionSerialId;
import com.tiim.model.ToolDestructionNote;
import com.tiim.model.ToolSerialNumber;

@Controller
public class ToolDestructionReportController {
	
	@Autowired
	ToolDestructionDao toolDestructionDao;

	@RequestMapping(value = "/addDestructionNoteDetail", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showDestructionDetail(@ModelAttribute DestructionSerialId destructionSerialId, HttpServletRequest request)
	{		
		ModelAndView modelView = new ModelAndView("showDestructionNoteDetail");
		toolDestructionDao.addDestructionNote(destructionSerialId);
		List<ToolDestructionNote> toolDestructionNotes = toolDestructionDao.getToolDestructionNote();
		modelView.addObject("toolDestructionNotes",toolDestructionNotes);
		return modelView;
	}
	
	@RequestMapping(value = "/addDestructionNoteDetail1", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView approveDestructionDetail(@ModelAttribute DestructionSerialId destructionSerialId, HttpServletRequest request)
	{		
		ModelAndView modelView = new ModelAndView("showDestructionNoteDetail");
		toolDestructionDao.addDestructionNote(destructionSerialId);
		List<ToolDestructionNote> toolDestructionNotes = toolDestructionDao.getToolDestructionNote();
		modelView.addObject("toolDestructionNotes",toolDestructionNotes);
		return modelView;
	}

	@RequestMapping(value = "/viewDestructionNoteReport", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView viewDestructionNoteReport() {
		ModelAndView modelView = new ModelAndView("viewDestructionNoteReport");
		List<ToolSerialNumber> toolSerialNumbers = new ArrayList<ToolSerialNumber>();
		toolSerialNumbers = toolDestructionDao.getSerialNumberForDestruction();
		modelView.addObject("toolSerialNumbers",
				toolSerialNumbers);
		return modelView;
	}
	
	@RequestMapping(value = "/showDestructionNoteDetail", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getSerialNumberForDestruction() {
		ModelAndView modelView = new ModelAndView("showDestructionNoteDetail");
		List<ToolDestructionNote> toolDestructionNotes = toolDestructionDao.getToolDestructionNote();
		modelView.addObject("toolDestructionNotes",toolDestructionNotes);
		return modelView;
	}
}

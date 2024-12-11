package com.tiim.master.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.LotDestructionNoteDao;
import com.tiim.model.LotToolDestruction;

@Controller
public class LotToolDestructionController {

	@Autowired
	LotDestructionNoteDao lotDestructionDao;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	java.util.Date cDate = new java.util.Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

	@RequestMapping(value = "/lotToolDestructionList", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView lotToolDestructionList(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("lotToolDestructionList");
		List<LotToolDestruction> lstLotToolDestructionDetail = new ArrayList<LotToolDestruction>();
		lstLotToolDestructionDetail = lotDestructionDao.getLotToolDestructionDetails(0);
		modelView.addObject("lstLotToolDestructionDetail",
				lstLotToolDestructionDetail);
		return modelView;
	}
	@RequestMapping(value = "/lotToolDestruction", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView lotToolDestruction(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("lotToolDestruction");
		fillDefaultValue(modelView, "Display");
		modelView.addObject("btnStatusVal", "Save");
		return modelView;
	}	
	@RequestMapping(value = "/addLotToolDestruction", method = RequestMethod.POST)
	public ModelAndView addLotToolDestruction(
			@ModelAttribute LotToolDestruction destructionNote,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelView = new ModelAndView("lotToolDestruction");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");

		String message = lotDestructionDao.addLotToolDestruction(destructionNote,
				userId);
		List<LotToolDestruction> lstLotToolDestructionDetail = new ArrayList<LotToolDestruction>();
		lstLotToolDestructionDetail = lotDestructionDao.getLotToolDestructionDetails(0);
		modelView.addObject("lstLotToolDestructionDetail",
				lstLotToolDestructionDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Save");
		return modelView;
	}

	@RequestMapping(value = "/updateLotToolDestruction", method = RequestMethod.POST)
	public ModelAndView updateLotToolDestruction(
			@ModelAttribute LotToolDestruction destructionNote,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelView = new ModelAndView("lotToolDestruction");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");
		String message = lotDestructionDao.updateLotToolDestruction(destructionNote,
				userId);
		List<LotToolDestruction> lstLotToolDestructionDetail = new ArrayList<LotToolDestruction>();
		lstLotToolDestructionDetail = lotDestructionDao.getLotToolDestructionDetails(0);
		modelView.addObject("lstLotToolDestructionDetail",
				lstLotToolDestructionDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}

	@RequestMapping(value = "/editLotToolDestruction", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView editLotToolDestruction(@RequestParam int lotDestruction,
			HttpServletRequest request) throws ParseException {
		ModelAndView modelView = new ModelAndView("lotToolDestruction");
		List<LotToolDestruction> lstLotToolDestructionDetail = new ArrayList<LotToolDestruction>();
		lstLotToolDestructionDetail = lotDestructionDao
				.getLotToolDestructionDetails(lotDestruction);
		for (Iterator iterator = lstLotToolDestructionDetail.iterator(); iterator
				.hasNext();) {
			LotToolDestruction destructionNote = (LotToolDestruction) iterator.next();
			modelView.addObject("destructionNo",
					destructionNote.getDestructionNo());
			modelView.addObject("lotDestructionDate",
					sdf.format(formatter.parse(destructionNote.getLotDestructionDate())));
			modelView.addObject("lotDestruction",
					destructionNote.getLotDestruction());
			modelView.addObject("lotNo",
					destructionNote.getLotNo());
			modelView.addObject("punch",
					destructionNote.getPunch());
			modelView.addObject("serailNo",
					destructionNote.getSerailNo());
			modelView.addObject("rejectedAt",
					destructionNote.getRejectedAt());
			modelView.addObject("destroyedBy",
					destructionNote.getDestroyedBy());
		}
		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		return modelView;
	}

	@RequestMapping(value = "/deleteLotToolDestruction", method = RequestMethod.POST)
	public ModelAndView deleteLotToolDestruction(@RequestParam int lotDestruction,
			HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("lotToolDestructionList");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");
		String message = lotDestructionDao.deleteLotToolDestruction(lotDestruction,
				userId);
		List<LotToolDestruction> lstLotToolDestructionDetail = new ArrayList<LotToolDestruction>();
		lstLotToolDestructionDetail = lotDestructionDao.getLotToolDestructionDetails(0);
		modelView.addObject("lstLotToolDestructionDetail",
				lstLotToolDestructionDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}

	@RequestMapping(value = "/viewLotToolDestruction", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView viewLotToolDestruction(@RequestParam int lotDestruction,
			HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("viewLotDestruction");
		List<LotToolDestruction> lstLotToolDestructionDetail = new ArrayList<LotToolDestruction>();
		lstLotToolDestructionDetail = lotDestructionDao.getLotToolDestructionDetails(lotDestruction);
		modelView.addObject("lstLotToolDestructionDetail",
				lstLotToolDestructionDetail);
		return modelView;
	}

	private void fillDefaultValue(ModelAndView modelView, String action) {
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		int destructionNo = lotDestructionDao.getIntialValue();
		modelView.addObject("lotDestruction", destructionNo);
		modelView.addObject("lotDestructionDate", sdf.format(cDate));
	}
}

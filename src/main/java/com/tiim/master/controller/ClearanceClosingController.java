package com.tiim.master.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.google.gson.Gson;
import com.tiim.dao.CleaningSopDao;
import com.tiim.dao.ClearanceClosingDao;
import com.tiim.dao.ClearanceNoDao;
import com.tiim.model.CleaningSop;
import com.tiim.model.ClearanceClosing;
import com.tiim.model.ClearanceNo;
import com.tiim.model.Param;
import com.tiim.model.Stock;

@Controller
public class ClearanceClosingController {

	@Autowired
	ClearanceClosingDao clearnaceDao;

	@Autowired
	ClearanceNoDao clearanceNoDao;

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	java.util.Date cDate = new java.util.Date();

	@RequestMapping(value = "/clearanceClosingList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView clearanceClosingList(@ModelAttribute ClearanceClosing clearnace, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("clearanceClosingList");
		List<ClearanceClosing> lstClearanceDetail = new ArrayList<ClearanceClosing>();
		lstClearanceDetail = clearnaceDao.getClearanceClosingDetails(clearnace.getClearanceNo());
		modelView.addObject("lstClearanceDetail", lstClearanceDetail);
		return modelView;
	}

	@RequestMapping(value = "/clearanceClosing", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView clearanceClosing(HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("clearanceClosing");
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");

		return modelView;
	}

	@RequestMapping(value = "/addClearanceClosing", method = RequestMethod.POST)
	public ModelAndView addClearanceClosing(@ModelAttribute ClearanceClosing clearnace, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("clearanceClosing");
		String message = clearnaceDao.addClearanceClosing(clearnace, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");
		return modelView;
	}

	@RequestMapping(value = "/editClearanceClosing", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView editClearanceClosing(@RequestParam int requestId, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("clearanceClosing");
		ClearanceClosing clearanceDtl = clearnaceDao.getClearanceClosing(requestId);

		modelView.addObject("clearanceNo", clearanceDtl.getClearanceNo());
		modelView.addObject("lotNumber", clearanceDtl.getLotNumber());
		modelView.addObject("clearanceDate", sdf.format(clearanceDtl.getClearanceDate()));
		modelView.addObject("productName", clearanceDtl.getProductName());
		modelView.addObject("batchNumber", clearanceDtl.getBatchNumber());
		modelView.addObject("batchQty", clearanceDtl.getBatchQty());
		modelView.addObject("closingNo", clearanceDtl.getClosingNo());

		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");

		return modelView;
	}

	@RequestMapping(value = "/updateClearanceClosing", method = RequestMethod.POST)
	public ModelAndView updateClearanceClosing(@ModelAttribute ClearanceClosing clearance, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("clearanceClosing");
		String message = clearnaceDao.updateClearanceClosing(clearance, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");

		fillDefaultValue(modelView, "Update");
		return modelView;
	}

	@RequestMapping(value = "/deleteClearanceClosing", method = RequestMethod.POST)
	public ModelAndView deleteClearanceClosing(@RequestParam int requestId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("clearanceClosingList");
		String message = clearnaceDao.deleteClearanceClosing(requestId, userId);
		modelView.addObject("message", message);
		List<ClearanceClosing> lstClearanceDetail = new ArrayList<ClearanceClosing>();
		lstClearanceDetail = clearnaceDao.getClearanceClosingDetails(0);
		modelView.addObject("lstClearanceDetail", lstClearanceDetail);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}

	@RequestMapping(value = "/viewClearanceClosing", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView viewClearanceClosing(@RequestParam int requestId, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("viewClearanceClosing");
		List<ClearanceClosing> lstClearanceDetail = new ArrayList<ClearanceClosing>();
		lstClearanceDetail = clearnaceDao.getClearanceClosingDetails(requestId);
		modelView.addObject("lstClearanceDetail", lstClearanceDetail);
		return modelView;
	}

	private void fillDefaultValue(ModelAndView modelView, String action) {
		int closingNo = clearnaceDao.getIntialValue();
		modelView.addObject("closingNo", closingNo);
		modelView.addObject("action", action);
		modelView.addObject("clearanceDate", sdf.format(cDate));
	}

	@RequestMapping(value = "/getClearanceNo", method = RequestMethod.POST)
	public @ResponseBody ClearanceNo getClearanceNo(@RequestParam int clearanceNo, HttpServletRequest request) {
		ClearanceNo clearanceNoDtl = null;
		List<ClearanceNo> lstClearanceNoDetail = new ArrayList<ClearanceNo>();
		lstClearanceNoDetail = clearanceNoDao.getClearanceDetails(clearanceNo);
		for (ClearanceNo clearanceVal : lstClearanceNoDetail) {
			clearanceNoDtl = clearanceVal;
		}
		return clearanceNoDtl;
	}

	@RequestMapping(value = "/autoClearanceNo", method = RequestMethod.GET)
	public @ResponseBody String autoClearanceNo(@RequestParam(required = false) int clearanceNo,
			HttpServletRequest request) {
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();

		List<ClearanceNo> lstClearanceNoDetail = new ArrayList<ClearanceNo>();
		lstClearanceNoDetail = clearanceNoDao.getAutoClearanceDetails(clearanceNo);
		Iterator<ClearanceNo> itr = lstClearanceNoDetail.iterator();
		while (itr.hasNext()) {
			ClearanceNo obj = itr.next();
			Param p = new Param(obj.getClearanceNo() + "", obj.getClearanceNo() + "", obj.getClearanceNo() + "");

			data.add(p);
		}

		return gson.toJson(data);
	}

	@RequestMapping(value = "/getTotalBatchQty", method = RequestMethod.POST)
	public @ResponseBody ClearanceClosing getTotalBatchQty(@RequestParam String lotNumber, HttpServletRequest request) {
		ClearanceClosing closing = clearnaceDao.getTotalBatchQnty(lotNumber);
		return closing;
	}
}

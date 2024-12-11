package com.tiim.master.controller;

import java.text.ParseException;
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
import com.tiim.dao.MaterialIssueDao;
import com.tiim.dao.MaterialReceiptDao;
import com.tiim.model.MaterialIssue;
import com.tiim.model.MaterialReceipt;
import com.tiim.model.Param;

@Controller
public class MaterialIssueController {
	
	@Autowired
	MaterialIssueDao materialDao;
	
	@Autowired
	MaterialReceiptDao materialReceiptDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	java.util.Date cDate=new java.util.Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		
	@RequestMapping(value = "/showMaterialIssue", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMaterialIssue(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("materialIssue");
		
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Display");
		HttpSession session = request.getSession();

		String userName = session.getAttribute("username").toString();
		modelView.addObject("issuedBy", userName);
		return modelView;
	}
	@RequestMapping(value = "/showMaterialIssueLst", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMaterialIssueLst(@ModelAttribute MaterialIssue material, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("materialIssueList");
		List<MaterialIssue> lstMaterialIssueDetail = new ArrayList<MaterialIssue>();
		lstMaterialIssueDetail = materialDao.getMaterialIssueDetails(material.getIssueNo());
		modelView.addObject("lstMaterialIssueDetail", lstMaterialIssueDetail);
		return modelView;
	}
	@RequestMapping(value = "/addMaterialIssue", method=RequestMethod.POST)
	public ModelAndView addMaterialIssue(@ModelAttribute MaterialIssue material, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialIssue");
		
		int stockQty = materialReceiptDao.getStockQty(material.getLotNumber());
		String message;
		if(stockQty >= material.getMaterialQty())
		{
		 message = materialDao.addMaterialIssue(material, userId);
		} else {
		message = "Issue Quantity should not be greater than Stock Quantity";
		}
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	@RequestMapping(value = "/editMaterialIssue", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editMaterialIssue(@RequestParam int requestId, HttpServletRequest request) throws ParseException
	{
		ModelAndView modelView = new ModelAndView("materialIssue");
		List<MaterialIssue> materialDtl = materialDao.getMaterialIssueDetails(requestId);
		for (Iterator iterator = materialDtl.iterator(); iterator.hasNext();) {
			MaterialIssue material = (MaterialIssue) iterator.next();
			modelView.addObject("issueNo", material.getIssueNo());
			modelView.addObject("issueDate", sdf.format(formatter.parse(material.getIssueDate())));
			modelView.addObject("materialCode", material.getMaterialCode());
			modelView.addObject("lotNumber", material.getLotNumber());
			modelView.addObject("materialName", material.getMaterialName());
			modelView.addObject("materialQty", material.getMaterialQty());
			modelView.addObject("issuedQty", material.getMaterialQty());
			modelView.addObject("uom", material.getUom());
			modelView.addObject("issuedBy", material.getIssuedBy());
			modelView.addObject("receivedBy", material.getReceivedBy());
			modelView.addObject("remark", material.getRemark());
			modelView.addObject("materialType", material.getMaterialType());
			modelView.addObject("toolRequestNo", material.getToolRequestNo());
			int stockQty = materialReceiptDao.getStockQty(material.getLotNumber());
			modelView.addObject("stockQty", stockQty);
		}

		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
	@RequestMapping(value = "/updateMaterialIssue", method=RequestMethod.POST)
	public ModelAndView updateMaterialIssue(@ModelAttribute MaterialIssue material, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialIssue");
		String message = materialDao.updateMaterialIssue(material, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");

		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteMaterialIssue", method=RequestMethod.POST)
	public ModelAndView deleteMaterialIssue(@RequestParam int requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialIssueList");
		MaterialIssue materialDtl = materialDao.getMaterialIssueDetails(requestId).get(0);
		String message = materialDao.deleteMaterialIssue(requestId, userId, materialDtl);
		modelView.addObject("message", message);
		List<MaterialIssue> lstMaterialIssueDetail = new ArrayList<MaterialIssue>();
		lstMaterialIssueDetail = materialDao.getMaterialIssueDetails(0);
		modelView.addObject("lstMaterialIssueDetail", lstMaterialIssueDetail);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	 @RequestMapping(value = "/viewMaterialIssue", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView viewMaterialIssue(@RequestParam int requestId, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("viewMaterialIssue");
			List<MaterialIssue> lstMaterialIssueDetail = new ArrayList<MaterialIssue>();
			lstMaterialIssueDetail = materialDao.getMaterialIssueDetails(requestId);
			modelView.addObject("lstMaterialIssueDetail", lstMaterialIssueDetail);
			return modelView;
		} 
	 
	 @RequestMapping(value = "/getMaterialReceipt", method=RequestMethod.POST)
	 public @ResponseBody MaterialIssue getMaterialReceipt(@RequestParam int materialGrnNo, HttpServletRequest request)
	 {
		 MaterialIssue materialIssue = materialDao.getMaterialDetailsByLotNo(materialGrnNo);
		 return materialIssue;
	 }
	 
	 @RequestMapping(value = "/autoLotNo", method = RequestMethod.GET)
		public @ResponseBody String autoLotNo(@RequestParam("lotNo") int lotNo,HttpServletRequest request)
		{
			Gson gson = new Gson();
			List<Param> data = new ArrayList<Param>();	
				List<MaterialReceipt> arlList = materialDao.getMaterialDetails(lotNo);
				Iterator<MaterialReceipt> itr = arlList.iterator();
				while(itr.hasNext())
				{
					MaterialReceipt obj = itr.next();
					Param p = new Param(obj.getLotNumber()+"" ,obj.getLotNumber()+"",obj.getMaterialGrnNo()+"");
					
			        data.add(p);	
				}
			return gson.toJson(data);
		}
	 
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		int materialGrnNo = materialDao.getIntialValue();
		modelView.addObject("issueNo", materialGrnNo);
		modelView.addObject("issueDate", sdf.format(cDate));
	}
}

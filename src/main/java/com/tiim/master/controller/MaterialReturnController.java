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
import com.tiim.dao.MaterialReturnDao;
import com.tiim.model.MaterialIssue;
import com.tiim.model.MaterialReceipt;
import com.tiim.model.MaterialReturn;
import com.tiim.model.Param;

@Controller
public class MaterialReturnController {
	
	@Autowired
	MaterialReturnDao materialDao;
	
	@Autowired
	MaterialIssueDao materialIssueDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	java.util.Date cDate=new java.util.Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		
	@RequestMapping(value = "/showMaterialReturn", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMaterialReturn(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("materialReturn");
		
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Display");
		HttpSession session = request.getSession();

		String userName = session.getAttribute("username").toString();
		modelView.addObject("returnBy", userName);
		return modelView;
	}
	@RequestMapping(value = "/showMaterialReturnLst", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMaterialReturnLst(@ModelAttribute MaterialReturn material, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("materialReturnList");
		List<MaterialReturn> lstMaterialReturnDetail = new ArrayList<MaterialReturn>();
		lstMaterialReturnDetail = materialDao.getMaterialReturnDetails(material.getReturnNo());
		modelView.addObject("lstMaterialReturnDetail", lstMaterialReturnDetail);
		return modelView;
	}
	@RequestMapping(value = "/addMaterialReturn", method=RequestMethod.POST)
	public ModelAndView addMaterialReturn(@ModelAttribute MaterialReturn material, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialReturn");
		String message = materialDao.addMaterialReturn(material, userId);
	    modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	@RequestMapping(value = "/editMaterialReturn", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editMaterialReturn(@RequestParam int requestId, HttpServletRequest request) throws ParseException
	{
		ModelAndView modelView = new ModelAndView("materialReturn");
		List<MaterialReturn> materialDtl = materialDao.getMaterialReturnDetails(requestId);
		for (Iterator iterator = materialDtl.iterator(); iterator.hasNext();) {
			MaterialReturn material = (MaterialReturn) iterator.next();
			modelView.addObject("returnNo", material.getReturnNo());
			modelView.addObject("returnDate", sdf.format(formatter.parse(material.getReturnDate())));
			modelView.addObject("materialCode", material.getMaterialCode());
			modelView.addObject("lotNumber", material.getLotNumber());
			modelView.addObject("materialName", material.getMaterialName());
			modelView.addObject("materialQty", material.getMaterialQty());
			modelView.addObject("uom", material.getUom());
			modelView.addObject("receivedBy", material.getReceivedBy());
			modelView.addObject("returnBy", material.getReturnBy());
			modelView.addObject("remark", material.getRemark());
			modelView.addObject("materialType", material.getMaterialType());
			modelView.addObject("toolIssueNo", material.getToolIssueNo());
			modelView.addObject("issuedQty", material.getMaterialQty());

		}
		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
	@RequestMapping(value = "/updateMaterialReturn", method=RequestMethod.POST)
	public ModelAndView updateMaterialReturn(@ModelAttribute MaterialReturn material, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialReturn");
		String message = materialDao.updateMaterialReturn(material, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");

		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteMaterialReturn", method=RequestMethod.POST)
	public ModelAndView deleteMaterialReturn(@RequestParam int requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialReturnList");
		String message = materialDao.deleteMaterialReturn(requestId, userId);
		modelView.addObject("message", message);
		List<MaterialReturn> lstMaterialReturnDetail = new ArrayList<MaterialReturn>();
		lstMaterialReturnDetail = materialDao.getMaterialReturnDetails(0);
		modelView.addObject("lstMaterialReturnDetail", lstMaterialReturnDetail);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	 @RequestMapping(value = "/viewMaterialReturn", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView viewMaterialReturn(@RequestParam int requestId, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("viewMaterialReturn");
			List<MaterialReturn> lstMaterialReturnDetail = new ArrayList<MaterialReturn>();
			lstMaterialReturnDetail = materialDao.getMaterialReturnDetails(requestId);
			modelView.addObject("lstMaterialReturnDetail", lstMaterialReturnDetail);
			return modelView;
		} 
	 @RequestMapping(value = "/autoIssueNo", method = RequestMethod.GET)
		public @ResponseBody String autoIssueNo(@RequestParam("toolIssueNo") int toolIssueNo,HttpServletRequest request)
		{
			Gson gson = new Gson();
			List<Param> data = new ArrayList<Param>();	
				List<MaterialIssue> arlList = materialIssueDao.getMaterialIssueDetails(toolIssueNo);
				Iterator<MaterialIssue> itr = arlList.iterator();
				while(itr.hasNext())
				{
					MaterialIssue obj = itr.next();
					Param p = new Param(obj.getIssueNo()+"" ,obj.getIssueNo()+"",obj.getIssueNo()+"");
					
			        data.add(p);	
				}
			return gson.toJson(data);
		}
	@RequestMapping(value = "/getMaterialIssue", method=RequestMethod.POST)
	 public @ResponseBody MaterialReturn getMaterialIssue(@RequestParam int issueNo, HttpServletRequest request)
	 {
		 MaterialReturn returnValue = new MaterialReturn();
		 List<MaterialIssue> materialIssue = materialIssueDao.getMaterialIssueDetails(issueNo);
		 Iterator<MaterialIssue> itr = materialIssue.iterator();
			while(itr.hasNext())
			{
				MaterialIssue issue = itr.next();
				returnValue.setToolIssueNo(issue.getIssueNo());
				returnValue.setLotNumber(issue.getLotNumber());
				returnValue.setMaterialCode(issue.getMaterialCode());
				returnValue.setMaterialName(issue.getMaterialName());
				returnValue.setMaterialQty(issue.getMaterialQty());
				returnValue.setUom(issue.getUom());
				returnValue.setReceivedBy(issue.getReceivedBy());
				returnValue.setRemark(issue.getRemark());
				returnValue.setIssuedQty(issue.getMaterialQty());
			}
			return returnValue;		
	}
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		int materialGrnNo = materialDao.getIntialValue();
		modelView.addObject("returnNo", materialGrnNo);
		modelView.addObject("returnDate", sdf.format(cDate));
	}
}

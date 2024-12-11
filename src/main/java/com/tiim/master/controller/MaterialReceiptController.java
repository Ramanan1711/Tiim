package com.tiim.master.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.tiim.dao.MaterialDao;
import com.tiim.dao.MaterialReceiptDao;
import com.tiim.model.ClearanceNo;
import com.tiim.model.Material;
import com.tiim.model.MaterialReceipt;
import com.tiim.model.Param;

@Controller
public class MaterialReceiptController {
	
	@Autowired
	MaterialReceiptDao materialDao;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	java.util.Date cDate=new java.util.Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		
	@RequestMapping(value = "/showMaterialReceipt", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMaterialReceipt(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("materialReceiptNote");
		HttpSession session = request.getSession();
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Display");
		String userName = session.getAttribute("username").toString();
		modelView.addObject("receivedBy", userName);
		return modelView;
	}
	@RequestMapping(value = "/showMaterialReceiptLst", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMaterialReceiptLst(@ModelAttribute MaterialReceipt material, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("materialReceiptNoteList");
		List<MaterialReceipt> lstMaterialRcptDetail = new ArrayList<MaterialReceipt>();
		lstMaterialRcptDetail = materialDao.getMaterialDetails(material.getMaterialGrnNo());
		modelView.addObject("lstMaterialRcptDetail", lstMaterialRcptDetail);
		return modelView;
	}
	@RequestMapping(value = "/addMaterialReceipt", method=RequestMethod.POST)
	public ModelAndView addMaterialReceipt(@ModelAttribute MaterialReceipt material, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialReceiptNote");
		String branchName = session.getAttribute("sesBranchName").toString();
		material.setBranch(branchName);
		String message = materialDao.addMaterialReceipt(material, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	@RequestMapping(value = "/editMaterialReceipt", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editMaterialReceipt(@RequestParam int requestId, HttpServletRequest request) throws ParseException
	{
		ModelAndView modelView = new ModelAndView("materialReceiptNote");
		List<MaterialReceipt> materialDtl = materialDao.getMaterialDetails(requestId);
		for (Iterator iterator = materialDtl.iterator(); iterator.hasNext();) {
			MaterialReceipt material = (MaterialReceipt) iterator.next();
			modelView.addObject("materialGrnNo", material.getMaterialGrnNo());
			modelView.addObject("materialGrnDate", sdf.format(formatter.parse(material.getMaterialGrnDate())));
			modelView.addObject("dcNo", material.getDcNo());
			modelView.addObject("dcDate", sdf.format(formatter.parse(material.getDcDate())));
			modelView.addObject("supplierCode", material.getSupplierCode());
			modelView.addObject("supplierName", material.getSupplierName());
			modelView.addObject("billNo", material.getBillNo());
			modelView.addObject("billDate", sdf.format(formatter.parse(material.getBillDate())));
			modelView.addObject("materialCode", material.getMaterialCode());
			modelView.addObject("lotNumber", material.getLotNumber());
			modelView.addObject("materialName", material.getMaterialName());
			modelView.addObject("materialQty", material.getMaterialQty());
			modelView.addObject("uom", material.getUom());
			modelView.addObject("mgfDate", sdf.format(formatter.parse(material.getMgfDate())));
			modelView.addObject("receivedBy", material.getReceivedBy());
			modelView.addObject("remark", material.getRemark());
		}
		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
	@RequestMapping(value = "/updateMaterialReceipt", method=RequestMethod.POST)
	public ModelAndView updateMaterialReceipt(@ModelAttribute MaterialReceipt material, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialReceiptNote");
		String message = materialDao.updateMaterial(material, userId);
		modelView.addObject("message", message);
		modelView.addObject("btnStatusVal", "Save");

		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteMaterialReceipt", method=RequestMethod.POST)
	public ModelAndView deleteMaterialReceipt(@RequestParam int requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("materialReceiptNoteList");
		String message = materialDao.deleteMaterial(requestId, userId);
		modelView.addObject("message", message);
		List<MaterialReceipt> lstMaterialRcptDetail = new ArrayList<MaterialReceipt>();
		lstMaterialRcptDetail = materialDao.getMaterialDetails(0);
		modelView.addObject("lstMaterialRcptDetail", lstMaterialRcptDetail);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
/*	 @RequestMapping(value = "/getMaterialDetails", method={RequestMethod.GET, RequestMethod.POST})
	 public ModelAndView getMaterialDetails(@ModelAttribute Material material, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("mstMaterial");
			List<Material> lstMaterialRcptDetail = new ArrayList<Material>();
			lstMaterialRcptDetail = materialDao.getMaterialDetails(material.getMaterialCode());
			modelView.addObject("lstMaterialRcptDetail", lstMaterialRcptDetail);
			return modelView;
	}
	*/
	 @RequestMapping(value = "/viewMaterialReceipt", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView viewMaterialReceipt(@RequestParam int requestId, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("viewMaterialReceipt");
			List<MaterialReceipt> lstMaterialRcptDetail = new ArrayList<MaterialReceipt>();
			lstMaterialRcptDetail = materialDao.getMaterialDetails(requestId);
			modelView.addObject("lstMaterialRcptDetail", lstMaterialRcptDetail);
			return modelView;
		} 
	 
	 @RequestMapping(value = "/autoMaterialCode", method = RequestMethod.GET)
		public @ResponseBody String autoMaterialCode(@RequestParam("materialCode") int materialCode,HttpServletRequest request)
		{
			Gson gson = new Gson();
			List<Param> data = new ArrayList<Param>();	
			
			if(materialCode != 0 )
			{
				List<MaterialReceipt> lstMaterialReceiptDetail = new ArrayList<MaterialReceipt>();
				lstMaterialReceiptDetail = materialDao.getMaterialValues(materialCode);
				Iterator<MaterialReceipt> itr = lstMaterialReceiptDetail.iterator();
				while(itr.hasNext())
				{
					MaterialReceipt obj = itr.next();
					Param p = new Param(obj.getMaterialCode()+"" ,obj.getMaterialName(),obj.getUom());
					
			        data.add(p);	
				}
			}
			
			return gson.toJson(data);
		}
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		int materialGrnNo = materialDao.getIntialValue();
		modelView.addObject("materialGrnNo", materialGrnNo);
		modelView.addObject("materialGrnDate", sdf.format(cDate));
		modelView.addObject("dcDate", sdf.format(cDate));
		modelView.addObject("billDate", sdf.format(cDate));
		modelView.addObject("mgfDate", sdf.format(cDate));

		String autoToolingLotNumber = materialGrnNo +"";
		if(autoToolingLotNumber.length() == 1)
		{
			autoToolingLotNumber = "00000"+autoToolingLotNumber;
		}else if(autoToolingLotNumber.length() == 2)
		{
			autoToolingLotNumber = "0000"+autoToolingLotNumber;
		}else if(autoToolingLotNumber.length() == 3)
		{
			autoToolingLotNumber = "000"+autoToolingLotNumber;
		}else if(autoToolingLotNumber.length() == 4)
		{
			autoToolingLotNumber = "00"+autoToolingLotNumber;
		}else if(autoToolingLotNumber.length() == 5)
		{
			autoToolingLotNumber = "0"+autoToolingLotNumber;
		}
		modelView.addObject("lotNumber", autoToolingLotNumber);

	}
}

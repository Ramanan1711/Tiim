package com.tiim.master.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.PlatingDao;
import com.tiim.dao.SendMailToApprover;
import com.tiim.dao.ToolIntentDao;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.ToolIndent;
import com.tiim.service.MachineService;
import com.tiim.util.TiimConstant;

@Controller
public class IndentToolController {
	@Autowired
	ToolIntentDao toolIntentDao;
	
	@Autowired
	MachineService machineService;
	
	@Autowired
	PlatingDao platingDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SendMailToApprover sendMail;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();
	/***************************************************************/	
	  
	 @RequestMapping(value = "/showIndent", method={RequestMethod.GET, RequestMethod.POST})
	 public ModelAndView showToolingRequest(@ModelAttribute ToolIndent tool, HttpServletRequest request)
		{
			ModelAndView modelView = new ModelAndView("toolIndentList1");
			List<ToolIndent> lstIntentDetail = new ArrayList<ToolIndent>();
			lstIntentDetail = toolIntentDao.getlstIntentDetail(tool.getSearchRequestBy());
			modelView.addObject("lstIntentDetail", lstIntentDetail);
			return modelView;
	}
	 
	 @RequestMapping(value = "/showPendingIndent", method={RequestMethod.GET, RequestMethod.POST})
	 public ModelAndView showPendingIndent(@ModelAttribute ToolIndent tool, HttpServletRequest request)
	{
		 HttpSession session = request.getSession();
			ModelAndView modelView = new ModelAndView("toolIndentList1");
			List<ToolIndent> lstIntentDetail = new ArrayList<ToolIndent>();
			String approvalFlag = session.getAttribute(TiimConstant.SES_INDENT_SCREEN).toString();
			lstIntentDetail = toolIntentDao.getPendingIndent(approvalFlag);
			modelView.addObject("lstIntentDetail", lstIntentDetail);
			return modelView;
	}

	@RequestMapping(value = "/showToolIndent", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolIndent(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolIndent");
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "display");
		return modelView;
	}
	
	@RequestMapping(value = "/addToolIntent", method=RequestMethod.POST)
	public ModelAndView addToolIndent(@ModelAttribute ToolIndent toolIndent, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView modelView = new ModelAndView("toolIndent");
		int userId = (Integer)session.getAttribute("userid");
		String message = toolIntentDao.addToolingIntent(toolIndent, userId);
		
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_INDENT_SCREEN).toString();
		//*********Send email*****************************//	
		if("1".equalsIgnoreCase(approvalFlag))
		{
			ReadContent emailContent = new ReadContent();
			Map<String, String> input = new HashMap<String, String>();
			input.put(TiimConstant.TRANSACTION_ID, toolIndent.getIndentNo()+"");
			input.put(TiimConstant.LOT_NUMBER, toolIndent.getToolingLotNumberPO());
			input.put(TiimConstant.PRODUCT_NAME, toolIndent.getProductNamePO());
			input.put(TiimConstant.DRAWING_NUMBER, toolIndent.getDrawingNo());
			input.put(TiimConstant.MACHINE_TYPE, toolIndent.getMachineTypePO());
			
			String filePath = messageSource.getMessage("emailTemplate", null, null);
			String subject = messageSource.getMessage("subject", null, null)+" "+TiimConstant.INDENT_SCREEN+" Screen";
			String content = emailContent.getEmailContent(filePath, input);
			sendMail.sendMail(TiimConstant.INDENT_SCREEN, content, subject);
		}
		
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Save");
		return modelView;
	}
	
	@RequestMapping(value = "/editToolIndent", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editToolIndent(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolIndent");
		ToolIndent toolIndent = toolIntentDao.getToolIndent(requestId);
		modelView.addObject("indentNo", toolIndent.getIndentNo());
		modelView.addObject("indentDate", toolIndent.getIndentDate());
		modelView.addObject("po", toolIndent.getPo());
		modelView.addObject("supplierCode", toolIndent.getSupplierCode());
		modelView.addObject("supplierName", toolIndent.getSupplierName());
		modelView.addObject("productSerialNo", toolIndent.getProductSerialNo());
		modelView.addObject("drawingNo", toolIndent.getDrawingNo());
		modelView.addObject("machineTypePO", toolIndent.getMachineTypePO());
		modelView.addObject("mocPunches", toolIndent.getMocPunches());
		modelView.addObject("mocDies", toolIndent.getMocDies());
		modelView.addObject("dimensionsPO", toolIndent.getDimensionsPO());
		modelView.addObject("poQuantity", toolIndent.getPoQuantity());
		modelView.addObject("toolingCodeNumberPO", toolIndent.getToolingCodeNumberPO());
		modelView.addObject("toolingLotNumberPO", toolIndent.getToolingLotNumberPO());
		modelView.addObject("dustCapGroove", toolIndent.getDustCapGroove());
		modelView.addObject("typeOfTool", toolIndent.getTypeOfTool());
		modelView.addObject("hardCromePlating", toolIndent.getHardCromePlating());
		modelView.addObject("embosingLower", toolIndent.getEmbosingLower());
		modelView.addObject("embosingUpper", toolIndent.getEmbosingUpper());
		modelView.addObject("shape", toolIndent.getShape());
		modelView.addObject("uomPO", toolIndent.getUomPO());
		modelView.addObject("productNamePO", toolIndent.getProductNamePO());
		modelView.addObject("expiryDates", toolIndent.getExpiryDates());
		modelView.addObject("minQuantity", toolIndent.getMinQuantity());
		modelView.addObject("punchSetNo",toolIndent.getPunchSetNo());
		modelView.addObject("compForce",toolIndent.getCompForce());

		modelView.addObject("action", "exist");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstMachineType", machineService.getMachineType());
		return modelView;
	}
	@RequestMapping(value = "/updateToolIndent", method=RequestMethod.POST)
	public @ResponseBody ModelAndView updateToolIndent(@ModelAttribute ToolIndent toolIndent, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("toolIndent");
		String message = toolIntentDao.updateToolIndent(toolIndent, userId);		
		modelView.addObject("message", message);
		
		
		modelView.addObject("btnStatusVal", "Save");
		fillDefaultValue(modelView, "Update");

		return modelView;
	}
	
	@RequestMapping(value = "/deleteIndent", method=RequestMethod.POST)
	public ModelAndView deleteIndent(@RequestParam int requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("toolIndentList1");
		String message = toolIntentDao.deleteIndentNote(requestId, userId);
		List<ToolIndent> lstIntentDetail = new ArrayList<ToolIndent>();
		lstIntentDetail = toolIntentDao.getlstIntentDetail("");
		modelView.addObject("lstIntentDetail", lstIntentDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/viewIndentNoteReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewIndentNoteReport(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewIndentNote");

		ToolIndent toolIndent = toolIntentDao.getToolIndent(requestId);
		List<ToolIndent> toolIndentList = new ArrayList<ToolIndent>();

		modelView.addObject("indentNo", toolIndent.getIndentNo());
		modelView.addObject("indentDate", toolIndent.getIndentDate());
		modelView.addObject("supplierCode", toolIndent.getSupplierCode());
		modelView.addObject("supplierName", toolIndent.getSupplierName());
		
		toolIndentList.add(toolIndent);
		modelView.addObject("toolIndentList", toolIndentList);

		modelView.addObject("msg", "");
		
		return modelView;
	}
	/*@RequestMapping(value = "/viewIndentNote", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewIndentNote(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewIndentNote");

		ToolIndent toolIndent = toolIntentDao.getToolIndent(requestId);
		List<ToolIndent> toolIndentList = new ArrayList<ToolIndent>();

		modelView.addObject("indentNo", toolIndent.getIndentNo());
		modelView.addObject("indentDate", toolIndent.getIndentDate());
		modelView.addObject("supplierCode", toolIndent.getSupplierCode());
		modelView.addObject("supplierName", toolIndent.getSupplierName());
		
		toolIndentList.add(toolIndent);
		modelView.addObject("toolIndentList", toolIndentList);

		modelView.addObject("msg", "");
		
		return modelView;
	}*/
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		int grnNo = toolIntentDao.getIntialValue();
		modelView.addObject("indentNo", grnNo);
		
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		modelView.addObject("lstPlating",platingDao.getPlatingDetails());
		modelView.addObject("lstMachineType", machineService.getMachineType());
		modelView.addObject("indentDate", sdf.format(cDate));
	}
	
	@RequestMapping(value = "/getToolingLotNoByProductName", method=RequestMethod.POST)
	public @ResponseBody String getToolingLotNoByProductName(@RequestParam String productNamePO,@RequestParam String drawingNo, HttpServletRequest request)
	{
		String autoToolingLotNumber = toolIntentDao.getIntialLotNumberValue(productNamePO,drawingNo);
		return autoToolingLotNumber;
	}
	
	@RequestMapping(value = "/searchPendingIndent", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView searchPendingIndent( HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView modelView = new ModelAndView("listIndentDetils");
		List<ToolIndent> lstPendingIndent = new ArrayList<ToolIndent>();
		String approvalFlag = session.getAttribute(TiimConstant.SES_INDENT_SCREEN).toString();
		lstPendingIndent = toolIntentDao.getPendingIndent(approvalFlag);
		modelView.addObject("lstPendingIndent", lstPendingIndent);
		return modelView;
	}
	
	@RequestMapping(value = "/getIndentById", method=RequestMethod.POST)
	public @ResponseBody ToolIndent getIndentById(@RequestParam int indentId, HttpServletRequest request)
	{
		ToolIndent indent = toolIntentDao.getToolIndent(indentId);
		return indent;
		
	}
}

package com.tiim.transaction.controller;

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

import com.tiim.dao.AddSerialNumberDao;
import com.tiim.dao.ProductReportDao;
import com.tiim.dao.SendMailToApprover;
import com.tiim.dao.ToolingIssueDao;
import com.tiim.dao.ToolingRequetNoteDao;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.ProductReport;
import com.tiim.model.SerialNumber;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingRequestNote;
import com.tiim.util.TiimConstant;
import com.tiim.util.TiimUtil;

@Controller
public class ToolingIssueController {
	
	@Autowired
	ToolingIssueDao toolingIssueDao;
	
	@Autowired
	ToolingRequetNoteDao RequetNoteDao;
	
	@Autowired
	ProductReportDao productReportDao;
	
	@Autowired
	AddSerialNumberDao serialNumberDao;
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SendMailToApprover sendMail;

	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();
	/***************************************************************/

	@RequestMapping(value = "/showToolingIssueNote", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolingRequest(@ModelAttribute ToolingIssueNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNoteList");
		List<ToolingIssueNote> lstToolingIssueNote = new ArrayList<ToolingIssueNote>();
		lstToolingIssueNote = toolingIssueDao.getToolingIssueNote("");
		modelView.addObject("lstToolingIssueNote", lstToolingIssueNote);
		return modelView;
	}
	
	@RequestMapping(value = "/searchToolingIssueNote", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchToolingRequest(@ModelAttribute ToolingIssueNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNoteList");
		List<ToolingIssueNote> lstToolingIssueNote = new ArrayList<ToolingIssueNote>();
		lstToolingIssueNote = toolingIssueDao.getToolingIssueNote(tool.getSearchRequestBy());
		modelView.addObject("lstToolingIssueNote", lstToolingIssueNote);
		modelView.addObject("searchRequestBy", tool.getSearchRequestBy());
		return modelView;
	}
	
	@RequestMapping(value = "/addIntialToolingIssueNote", method=RequestMethod.POST)
	public ModelAndView addIntialToolingIssueNote(@ModelAttribute ToolingIssueNote toolingIssueNote, HttpServletRequest request)
	{

		ModelAndView modelView = new ModelAndView("toolingIssueNote");
		int toolingIssueNoteId = toolingIssueDao.getIntialValue();
		modelView.addObject("issueId", toolingIssueNoteId);
		modelView.addObject("issueDate", sdf.format(cDate));
		
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", "");
		return modelView;
	}
	
	@RequestMapping(value = "/addToolingIssueNote", method=RequestMethod.POST)
	public String addToolingIssueNote(@ModelAttribute ToolingIssueNote toolingIssueNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNote");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingIssueNote.setBranchName(branchName);
		String message = toolingIssueDao.addToolingIssueNote(toolingIssueNote, userId);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.updateSerialNumber(toolingIssueNote);
		}
		//*********Send email*****************************//		
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_ISSUE_SCREEN).toString();
		if("1".equalsIgnoreCase(approvalFlag))
		{
				ReadContent emailContent = new ReadContent();
				Map<String, String> input = new HashMap<String, String>();
				input.put(TiimConstant.TRANSACTION_ID, toolingIssueNote.getOriginalId()+"");
				input.put(TiimConstant.LOT_NUMBER, TiimUtil.ValidateNull(toolingIssueNote.getToolingLotNumber1()));
				input.put(TiimConstant.PRODUCT_NAME, TiimUtil.ValidateNull(toolingIssueNote.getProductName1()));
				input.put(TiimConstant.DRAWING_NUMBER, TiimUtil.ValidateNull(toolingIssueNote.getDrawingNo1()));
				input.put(TiimConstant.MACHINE_TYPE, TiimUtil.ValidateNull(toolingIssueNote.getMachineName1()));
				
				/*
				 * String filePath = messageSource.getMessage("emailTemplate", null, null);
				 * String subject = messageSource.getMessage("subject", null,
				 * null)+" "+TiimConstant.PRODUCTION_ISSUE_SCREEN+" Screen"; String content =
				 * emailContent.getEmailContent(filePath, input);
				 * sendMail.sendMail(TiimConstant.PRODUCTION_ISSUE_SCREEN, content, subject);
				 */
		}
		/*ToolingInspection inspection = new ToolingInspection();
		inspection.setLotNumber(toolingIssueNote.getToolingLotNumber()[0]);
		inspection.setSerialId(toolingIssueNote.getSerialId());
		inspection.setSerialNumber1(toolingIssueNote.getSerialNumber1());
		inspection.setApprovedQty(toolingIssueNote.getApprovedQty());
		inspection.setStockFlag(toolingIssueNote.getStockFlag());
		serialNumberDao.updateSerialNumber(inspection);*/
		
		
		int toolingIssueNoteId = toolingIssueDao.getIntialValue() - 1;
		modelView.addObject("issueId", toolingIssueNoteId);
		modelView.addObject("issueDate", sdf.format(cDate));
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);
		return "redirect:/showToolingIssueNote.jsf";
	}
	
	/*@RequestMapping(value = "/addToolingIssueNoteDetail", method=RequestMethod.POST)
	public ModelAndView addToolingIssueNoteDetail(@ModelAttribute ToolingIssueNote toolingIssueNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNote");
		String message = toolingIssueDao.addIssueNoteDetails(toolingIssueNote);
		modelView.addObject("toolingIssueNote", toolingIssueNote);
		modelView.addObject("message", message);
		return modelView;
	}*/
	
	@RequestMapping(value = "/updateToolingIssueNote", method=RequestMethod.POST)
	public ModelAndView updateToolingIssueNote(@ModelAttribute ToolingIssueNote toolingIssueNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNote");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingIssueNote.setBranchName(branchName);
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_ISSUE_SCREEN).toString();
		String message = toolingIssueDao.updateToolingIssueNote(toolingIssueNote, "update", userId,approvalFlag);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.updateSerialNumber(toolingIssueNote);
		}
		/*ToolingInspection inspection = new ToolingInspection();
		inspection.setLotNumber(toolingIssueNote.getToolingLotNumber()[0]);
		inspection.setSerialId(toolingIssueNote.getSerialId());
		inspection.setSerialNumber1(toolingIssueNote.getSerialNumber1());
		inspection.setApprovedQty(toolingIssueNote.getApprovedQty());
		inspection.setStockFlag(toolingIssueNote.getStockFlag());
		serialNumberDao.updateSerialNumber(inspection);*/
		int toolingIssueNoteId = toolingIssueDao.getIntialValue();
		modelView.addObject("issueId", toolingIssueNoteId);
		modelView.addObject("issueDate", sdf.format(cDate));
		
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		
		modelView.addObject("message", message);
		return modelView;
	}
	
	
	/*@RequestMapping(value = "/updateToolingIssueNoteDetail", method=RequestMethod.POST)
	public @ResponseBody String updateToolingIssueNoteDetail(@ModelAttribute ToolingIssueNote toolingIssueNote, HttpServletRequest request)
	{
		String message = toolingIssueDao.updateToolingIssueNote(toolingIssueNote);

		return message;
	}*/
	
	/*@RequestMapping(value = "/changeRequestStatus", method=RequestMethod.POST)
	public ModelAndView changeRequestStatus(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNote");
		String message = toolingIssueDao.changeToolingIssueNoteStatus(requestId);
		modelView.addObject("message", message);
		return modelView;
	}*/
	
	@RequestMapping(value = "/fetchToolingIssue", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView fetchToolingReceiveInspection(@ModelAttribute ToolingIssueNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNote");

		List<ToolingRequestNote> lstToolingIssue = new ArrayList<ToolingRequestNote>();
		
		ToolingRequestNote obj = RequetNoteDao.getToolingRequestNote(tool.getOriginalId());
		lstToolingIssue = RequetNoteDao.getToolingRequestNoteDetails(tool.getOriginalId());
		
		modelView.addObject("issueId", tool.getIssueId());
		modelView.addObject("issueDate", sdf.format(cDate));
		modelView.addObject("requestNo", obj.getOriginalId());
		modelView.addObject("requestDate", obj.getRequestDate());
		modelView.addObject("requestBy", obj.getRequestBy());
		
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("lstToolingIssue", lstToolingIssue);
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/editToolingIssueNote", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView editToolingIssueNote(@RequestParam int issueId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNote");

		List<ToolingIssueNote> lstToolingIssue = new ArrayList<ToolingIssueNote>();
		
		ToolingIssueNote obj = toolingIssueDao.editToolingIssueNote(issueId);
		lstToolingIssue = toolingIssueDao.getToolingIssueNoteDetail1(issueId);
		
		modelView.addObject("issueId", obj.getIssueId());
		modelView.addObject("issueDate", obj.getIssueDate());
		modelView.addObject("requestNo", obj.getRequestNo());
		modelView.addObject("requestDate", obj.getRequestDate());
		modelView.addObject("requestBy", obj.getRequestBy());
		modelView.addObject("issueBy", obj.getIssueBy());
		modelView.addObject("checkedBy", obj.getCheckedBy());
		
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstToolingIssue", lstToolingIssue);
		modelView.addObject("message", "");
		
		return modelView;
	}

	@RequestMapping(value = "/deleteToolingIssue", method=RequestMethod.POST)
	public ModelAndView deleteToolingIssue(@RequestParam int issueId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingIssueNoteList");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String message = "";
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_ISSUE_SCREEN).toString();
		if(!toolingIssueDao.isIntegratedWithProductionIssue(issueId))
		{
			message = toolingIssueDao.deleteToolingIssue(issueId, userId, approvalFlag);
		}else
		{
			message = "Issue moved to report. Cannot delete";
		}
		List<ToolingIssueNote> lstToolingIssueNote = new ArrayList<ToolingIssueNote>();
		lstToolingIssueNote = toolingIssueDao.getToolingIssueNote("");
		modelView.addObject("lstToolingIssueNote", lstToolingIssueNote);
		modelView.addObject("message", message);
		
		return modelView;
	}
	
	@RequestMapping(value = "/viewToolingIssueNoteReport", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView viewToolingIssueNoteReport(@RequestParam int issueId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewToolingIssueNoteReport");

		List<ToolingIssueNote> lstToolingIssue = new ArrayList<ToolingIssueNote>();
		
		ToolingIssueNote obj = toolingIssueDao.editToolingIssueNote(issueId);
		lstToolingIssue = toolingIssueDao.getToolingIssueNoteDetail1(issueId);
		
		modelView.addObject("issueId", obj.getIssueId());
		modelView.addObject("issueDate", obj.getIssueDate());
		modelView.addObject("requestNo", obj.getRequestNo());
		modelView.addObject("requestDate", obj.getRequestDate());
		modelView.addObject("requestBy", obj.getRequestBy());
		modelView.addObject("issueBy", obj.getIssueBy());
		modelView.addObject("serialNumber", obj.getSerialNumber());
		
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstToolingIssue", lstToolingIssue);
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/getIssueRequestNo", method=RequestMethod.POST)
	public ModelAndView getIssueRequestNo(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_REQUEST_SCREEN).toString();
		ModelAndView modelView = new ModelAndView("listProductIssueNote");
		List<ToolingRequestNote> lstToolingRequestNote = new ArrayList<ToolingRequestNote>();
		lstToolingRequestNote = RequetNoteDao.getAutoToolingRequestNoteId(approvalFlag);
		modelView.addObject("lstToolingRequestNote", lstToolingRequestNote);
		return modelView;

	}
	
	@RequestMapping(value = "/getNextDueQty", method=RequestMethod.POST)
	public @ResponseBody ProductReport getNextDueQty(@RequestParam String lotNumber, String productName, String drawingNumber, String machineName, String typeOfTool, HttpServletRequest request)
	{
		/*long nextDueQty = toolingIssueDao.getNextQty(lotNumber, productName, drawingNumber, machineName, typeOfTool);
		if(nextDueQty == 0)
		{
			nextDueQty = toolingIssueDao.getNextQtyFromProduct(productName, drawingNumber, machineName, typeOfTool);
		}*/
		
		ProductReport obj = toolingIssueDao.getNextQty(lotNumber, productName, drawingNumber, machineName, typeOfTool);
		if(obj.getNextDueQty() == 0)
		{
			obj = toolingIssueDao.getNextQtyFromReceipt(productName, drawingNumber, machineName, typeOfTool, lotNumber);
		}
		
		//long stockQty = toolingIssueDao.getStockQty(lotNumber);
		return obj;
	}
	
	@RequestMapping(value = "/getStockQuantity", method=RequestMethod.POST)
	public @ResponseBody long getStockQuantity(@RequestParam String lotNumber)
	{
				
		long stockQty = toolingIssueDao.getStockQty(lotNumber);
		return stockQty;
	}
	
	@RequestMapping(value = "/getIntervalBalance", method=RequestMethod.POST)
	public @ResponseBody long getIntervalBalance(@RequestParam String lotNumber, String productName, String drawingNumber, String machineName, String typeOfTool, HttpServletRequest request)
	{
		long balance = toolingIssueDao.getIntervalQuantity(productName, drawingNumber, machineName, typeOfTool, lotNumber);
		if(balance == 0)
		{
			balance = toolingIssueDao.getNextQtyFromProduct(productName, drawingNumber, machineName, typeOfTool);
		}
		return balance;
	}
	
	@RequestMapping(value = "/getStorageLocation", method=RequestMethod.POST)
	public @ResponseBody String getStorageLocation(@RequestParam String lotNumber)
	{
				
		String storageLocation = toolingIssueDao.getStorageLocation(lotNumber);
		return storageLocation;
	}
	
	@RequestMapping(value = "/getIssueSerialNumbers", method=RequestMethod.POST)
	public ModelAndView getIssueSerialNumbers(@RequestParam("lotNumber") String lotNumber, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listIssueSerialNumbers");
		List<SerialNumber> lstSerialNumber = serialNumberDao.getIssueSerialNumber(lotNumber);
		modelView.addObject("lstSerialNumber", lstSerialNumber);
		return modelView;
	}
	
}

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
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.SendMailToApprover;
import com.tiim.dao.ToolingIssueDao;
import com.tiim.dao.ToolingRequetNoteDao;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.ToolingRequestNote;
import com.tiim.util.TiimConstant;
import com.tiim.util.TiimUtil;

@Controller
public class ToolingProductionRequestController {

	@Autowired
	ToolingRequetNoteDao toolingRequestDao;
	
	@Autowired
	ToolingIssueDao issueDao;
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SendMailToApprover sendMail;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();

	@RequestMapping(value = "/showToolingRequest", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolingRequest(@ModelAttribute ToolingRequestNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNoteList");
		List<ToolingRequestNote> lstToolingRequestNote = new ArrayList<ToolingRequestNote>();
		lstToolingRequestNote = toolingRequestDao.getToolingRequestNote("");
		modelView.addObject("lstToolingRequestNote", lstToolingRequestNote);
		modelView.addObject("isActive", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/searchToolingRequest", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchToolingRequest(@ModelAttribute ToolingRequestNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNoteList");
		List<ToolingRequestNote> lstToolingRequestNote = new ArrayList<ToolingRequestNote>();
		lstToolingRequestNote = toolingRequestDao.getToolingRequestNote(tool.getSearchRequestBy());
		modelView.addObject("lstToolingRequestNote", lstToolingRequestNote);
		modelView.addObject("searchRequestBy", tool.getSearchRequestBy());
		modelView.addObject("isActive", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/addIntialToolingRequestNote", method=RequestMethod.POST)
	public ModelAndView addIntialToolingRequestNote(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNote");
		int toolingRequestNoteId = toolingRequestDao.getIntialValue();
		modelView.addObject("requestId", toolingRequestNoteId);
		modelView.addObject("originalId", toolingRequestNoteId);
		modelView.addObject("requestDate",  sdf.format(cDate));
		modelView.addObject("requestBy", "");
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		return modelView;
	}
	
	@RequestMapping(value = "/addToolingRequestNote", method=RequestMethod.POST)
	public String addToolingRequestNote(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNote");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingRequestNote.setBranchName(branchName);
		String message = toolingRequestDao.addRequestNote(toolingRequestNote, userId);
		//modelView.addObject("toolingRequestNote", toolingRequestNote);
		modelView.addObject("message", message);

		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_REQUEST_SCREEN).toString();
		if("1".equalsIgnoreCase(approvalFlag))
		{
			//*********Send email*****************************//		
			ReadContent emailContent = new ReadContent();
			Map<String, String> input = new HashMap<String, String>();
			input.put(TiimConstant.TRANSACTION_ID, toolingRequestNote.getOriginalId()+"");
			input.put(TiimConstant.LOT_NUMBER, TiimUtil.ValidateNull(""));
			input.put(TiimConstant.PRODUCT_NAME, TiimUtil.ValidateNull(toolingRequestNote.getProductName1()));
			input.put(TiimConstant.DRAWING_NUMBER, TiimUtil.ValidateNull(toolingRequestNote.getDrawingNo1()));
			input.put(TiimConstant.MACHINE_TYPE, TiimUtil.ValidateNull(toolingRequestNote.getMachingType1()));
			
			String filePath = messageSource.getMessage("emailTemplate", null, null);
			String subject = messageSource.getMessage("subject", null, null)+" "+TiimConstant.PRODUCTION_REQUEST_SCREEN+" Screen";
			String content = emailContent.getEmailContent(filePath, input);
			sendMail.sendMail(TiimConstant.PRODUCTION_REQUEST_SCREEN, content, subject);	
		}
		/****************Reset Value*****************/
		//toolingRequestNote = toolingRequestDao.getInitialValue();
		modelView.addObject("requestId", toolingRequestNote.getRequestId());
		modelView.addObject("requestDate", toolingRequestNote.getRequestDate());
		modelView.addObject("requestBy", "");
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		/********************************************/
		
		return "redirect:/showToolingRequest.jsf";
	}
	
	/*@RequestMapping(value = "/addToolingRequestNoteDetail", method=RequestMethod.POST)
	public ModelAndView addToolingRequestNoteDetail(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("addToolingRequestNote");
		String message = toolingRequestDao.addRequestNoteDetails(toolingRequestNote);
		modelView.addObject("toolingRequestNote", toolingRequestNote);
		modelView.addObject("message", message);
		return modelView;
	}*/
	
	@RequestMapping(value = "/updateToolingRequestNote", method=RequestMethod.POST)
	public ModelAndView updateToolingRequestNote(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNote");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
	
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingRequestNote.setBranchName(branchName);
		String message = toolingRequestDao.updateRequestNote(toolingRequestNote, "update", userId);
		//modelView.addObject("toolingRequestNote", toolingRequestNote);
		modelView.addObject("message", message);
		
		/****************Reset Value*****************/
		//toolingRequestNote = toolingRequestDao.getInitialValue();
		modelView.addObject("requestId", toolingRequestNote.getRequestId());
		modelView.addObject("requestDate", toolingRequestNote.getRequestDate());
		modelView.addObject("requestBy", "");
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		/********************************************/
		
		return modelView;
	}
	
	
	/*@RequestMapping(value = "/updateToolingRequestNoteDetail", method=RequestMethod.POST)
	public @ResponseBody String updateToolingRequestNoteDetail(@ModelAttribute ToolingRequestNote toolingRequestNote, HttpServletRequest request)
	{
		String message = toolingRequestDao.updateRequestNoteDetails(toolingRequestNote);

		return message;
	}*/
	
	@RequestMapping(value = "/editToolingRequest", method=RequestMethod.POST)
	public ModelAndView editToolingRequest(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNote");
		List<ToolingRequestNote> lstToolingRequestNoteDetail = new ArrayList<ToolingRequestNote>();
		
		ToolingRequestNote toolingRequestNote = toolingRequestDao.getToolingRequestNote(requestId);
		lstToolingRequestNoteDetail = toolingRequestDao.getToolingRequestNoteDetails(requestId);
		System.out.println("lstToolingRequestNoteDetail: "+lstToolingRequestNoteDetail.size());
		modelView.addObject("originalId", toolingRequestNote.getOriginalId());
		modelView.addObject("requestId", toolingRequestNote.getRequestId());
		modelView.addObject("requestDate", toolingRequestNote.getRequestDate());
		modelView.addObject("requestBy", toolingRequestNote.getRequestBy());
		modelView.addObject("lstToolingRequestNoteDetail", lstToolingRequestNoteDetail);
		modelView.addObject("action", "exist");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
	
	/*@RequestMapping(value = "/changeRequestStatus", method=RequestMethod.POST)
	public ModelAndView changeRequestStatus(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNote");
		String message = toolingRequestDao.changeToolingRequestNoteStatus(requestId);
		modelView.addObject("message", message);
		return modelView;
	}*/
	
	@RequestMapping(value = "/deleteRequestNote", method=RequestMethod.POST)
	public ModelAndView deleteReceiptNote(@RequestParam int originalId, @RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingRequestNoteList");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String message = "";
		if(!issueDao.isIntegratedWithProductionRequest(originalId))
		{
			message = toolingRequestDao.deleteRequestNote(originalId, userId);
		}else
		{
			message = "Request Moved to Issue. Cannot delete"; 
		}
		List<ToolingRequestNote> lstToolingRequestNote = new ArrayList<ToolingRequestNote>();
		lstToolingRequestNote = toolingRequestDao.getToolingRequestNote("");
		modelView.addObject("lstToolingRequestNote", lstToolingRequestNote);
		modelView.addObject("message", message);
		return modelView;
	}
	
	@RequestMapping(value = "/viewToolingRequestReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewToolingRequestReport(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewToolingRequestReport");
		List<ToolingRequestNote> lstToolingRequestNoteDetail = new ArrayList<ToolingRequestNote>();
		
		ToolingRequestNote toolingRequestNote = toolingRequestDao.getToolingRequestNote(requestId);
		lstToolingRequestNoteDetail = toolingRequestDao.getToolingRequestNoteDetails(requestId);
		
		modelView.addObject("requestId", toolingRequestNote.getRequestId());
		modelView.addObject("requestDate", toolingRequestNote.getRequestDate());
		modelView.addObject("requestBy", toolingRequestNote.getRequestBy());
		modelView.addObject("lstToolingRequestNoteDetail", lstToolingRequestNoteDetail);
		modelView.addObject("action", "exist");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		
		return modelView;
	}
}

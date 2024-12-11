package com.tiim.transaction.controller;

import java.text.ParseException;
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

import com.tiim.dao.AddSerialNumberDao;
import com.tiim.dao.CleaningSopDao;
import com.tiim.dao.SendMailToApprover;
import com.tiim.dao.ToolingIssueDao;
import com.tiim.dao.ToolingReturnDao;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.SerialNumber;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingReturnNote;
import com.tiim.util.TiimConstant;
import com.tiim.util.TiimUtil;

@Controller
public class ToolingReturnController {

	@Autowired
	ToolingReturnDao toolingReturnDao;
	
	@Autowired
	ToolingIssueDao issueDao;

	@Autowired
	AddSerialNumberDao serialNumberDao;
	
	@Autowired
	CleaningSopDao sopDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SendMailToApprover sendMail;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();
	/***************************************************************/
	  
	@RequestMapping(value = "/showToolingReturn", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolingReturn(@ModelAttribute ToolingReturnNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReturnNoteList");
		List<ToolingReturnNote> lstToolingReturnNote = new ArrayList<ToolingReturnNote>();
		lstToolingReturnNote = toolingReturnDao.getToolingReturnNote("");
		//modelView.addObject("lstSOP", sopDao.getSop("recevingcleaning"));
		HttpSession session = request.getSession();
		session.setAttribute("lstSOP", sopDao.getSop("returncleaning"));
		modelView.addObject("lstToolingReturnNote", lstToolingReturnNote);
		return modelView;
	}
	
	@RequestMapping(value = "/searchToolingReturn", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchToolingReturn(@ModelAttribute ToolingReturnNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReturnNoteList");
		List<ToolingReturnNote> lstToolingReturnNote = new ArrayList<ToolingReturnNote>();
		lstToolingReturnNote = toolingReturnDao.getToolingReturnNote(tool.getSearchIssuedBy());
		modelView.addObject("lstToolingReturnNote", lstToolingReturnNote);
		modelView.addObject("searchIssuedBy", tool.getSearchIssuedBy());
		return modelView;
	}
	
	@RequestMapping(value = "/addIntialToolingReturnNote", method=RequestMethod.POST)
	public ModelAndView addIntialToolingReturnNote(@ModelAttribute ToolingReturnNote toolingReturnNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReturnNote");
		int toolingReturnNoteId = toolingReturnDao.getIntialValue();
		modelView.addObject("returnId", toolingReturnNoteId);
		modelView.addObject("returnDate", sdf.format(cDate));
		
		modelView.addObject("action", "new");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		return modelView;
	}
	
	@RequestMapping(value = "/addToolingReturnNote", method=RequestMethod.POST)
	public String addToolingReturnNote(@ModelAttribute ToolingReturnNote toolingReturnNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReturnNote");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingReturnNote.setBranchName(branchName);
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_RETURN_SCREEN).toString();
		String message = toolingReturnDao.addToolingReturnNote(toolingReturnNote, userId, approvalFlag);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{	
			serialNumberDao.updateReturnSerialNumber(toolingReturnNote);
		}
		//*********Send email*****************************//	

		if("1".equalsIgnoreCase(approvalFlag))
		{
			ReadContent emailContent = new ReadContent();
			Map<String, String> input = new HashMap<String, String>();
			input.put(TiimConstant.TRANSACTION_ID, toolingReturnNote.getOriginalId()+"");
			input.put(TiimConstant.LOT_NUMBER, TiimUtil.ValidateNull(toolingReturnNote.getToolingLotNumber1()));
			input.put(TiimConstant.PRODUCT_NAME, TiimUtil.ValidateNull(toolingReturnNote.getProductName1()));
			input.put(TiimConstant.DRAWING_NUMBER, TiimUtil.ValidateNull(toolingReturnNote.getDrawingNo1()));
			input.put(TiimConstant.MACHINE_TYPE, TiimUtil.ValidateNull(toolingReturnNote.getMachineName1()));
			
			String filePath = messageSource.getMessage("emailTemplate", null, null);
			String subject = messageSource.getMessage("subject", null, null)+" "+TiimConstant.PRODUCTION_RETURN_SCREEN+" Screen";
			String content = emailContent.getEmailContent(filePath, input);
			sendMail.sendMail(TiimConstant.PRODUCTION_RETURN_SCREEN, content, subject);	
		}
		/*ToolingInspection inspection = new ToolingInspection();
		inspection.setLotNumber(toolingReturnNote.getToolingLotNumber1());
		inspection.setSerialId(toolingReturnNote.getSerialId());
		inspection.setSerialNumber1(toolingReturnNote.getSerialNumber1());
		inspection.setApprovedQty(toolingReturnNote.getApprovedQty());
		inspection.setStockFlag(toolingReturnNote.getStockFlag());
		serialNumberDao.updateSerialNumber(inspection);
		*/
		
		int toolingReturnNoteId = toolingReturnDao.getIntialValue() - 1;
		modelView.addObject("returnId", toolingReturnNoteId);
		modelView.addObject("returnDate", toolingReturnNote.getReturnDate());
		
		modelView.addObject("message", message);
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		return "redirect:/showToolingReturn.jsf";
	}
	
	/*@RequestMapping(value = "/addToolingReturnNoteDetail", method=RequestMethod.POST)
	public ModelAndView addToolingReturnNoteDetail(@ModelAttribute ToolingReturnNote toolingReturnNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReturnNote");
		String message = toolingReturnDao.addReturnDetail(toolingReturnNote);
		modelView.addObject("toolingReturnNote", toolingReturnNote);
		modelView.addObject("message", message);
		return modelView;
	}*/
	
	@RequestMapping(value = "/updateToolingReturnNote", method=RequestMethod.POST)
	public ModelAndView updateToolingReturnNote(@ModelAttribute ToolingReturnNote toolingReturnNote, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingReturnNote.setBranchName(branchName);
		ModelAndView modelView = new ModelAndView("toolingReturnNote");
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_RETURN_SCREEN).toString();
		String message = toolingReturnDao.updateToolingReturnNote(toolingReturnNote, "update", userId, approvalFlag);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.updateReturnSerialNumber(toolingReturnNote);
		}
		/*ToolingInspection inspection = new ToolingInspection();
		inspection.setLotNumber(toolingReturnNote.getToolingLotNumber1());
		inspection.setSerialId(toolingReturnNote.getSerialId());
		inspection.setSerialNumber1(toolingReturnNote.getSerialNumber1());
		inspection.setApprovedQty(toolingReturnNote.getApprovedQty());
		inspection.setStockFlag(toolingReturnNote.getStockFlag());
		serialNumberDao.updateSerialNumber(inspection);*/
		
		int toolingReturnNoteId = toolingReturnDao.getIntialValue() - 1;
		modelView.addObject("returnId", toolingReturnNoteId);
		modelView.addObject("returnDate", toolingReturnNote.getReturnDate());
		
		modelView.addObject("message", message);
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		
		return modelView;
	}
	
	
	/*@RequestMapping(value = "/updateToolingReturnNoteDetail", method=RequestMethod.POST)
	public @ResponseBody String updateToolingReturnNoteDetail(@ModelAttribute ToolingReturnNote toolingReturnNote, HttpServletRequest request)
	{
		String message = toolingReturnDao.updateToolingReturnDetail(toolingReturnNote);

		return message;
	}*/
	
	/*@RequestMapping(value = "/changeRequestStatus", method=RequestMethod.POST)
	public ModelAndView changeRequestStatus(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("ToolingReturnNote");
		String message = toolingReturnDao.changeToolingReturnNoteStatus(requestId);
		modelView.addObject("message", message);
		return modelView;
	}
*/
	@RequestMapping(value = "/fetchAllIssue", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView fetchAllIssueDetails(@ModelAttribute ToolingReturnNote toolingReturnNote)
	{
		ModelAndView modelView = new ModelAndView("toolingReturnNote");
		System.out.println("toolingReturnNote.getOriginalId(): "+toolingReturnNote.getOriginalId());
		ToolingIssueNote toolingissueNote = issueDao.getIssueNote(toolingReturnNote.getOriginalId());
		List<ToolingIssueNote> lstIssueNote = issueDao.getToolingIssueNoteDetail1(toolingReturnNote.getOriginalId());
		
		modelView.addObject("returnId", toolingReturnNote.getReturnId());
		modelView.addObject("returnDate", sdf.format(cDate));
		modelView.addObject("issueId", toolingissueNote.getIssueId());
		modelView.addObject("originalId", toolingissueNote.getOriginalId());
		try 
		{
			if(toolingissueNote.getIssueDate() != null && !"".equals(toolingissueNote.getIssueDate()))
				modelView.addObject("issueDate", sdf.format(sdfDB.parse(toolingissueNote.getIssueDate())));
			else
				modelView.addObject("issueDate", sdf.format(cDate));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		modelView.addObject("issueBy", toolingissueNote.getIssueBy());
		modelView.addObject("lstIssueNote", lstIssueNote);
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "Save");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		
		return modelView;
	}
	
	@RequestMapping(value = "/editToolingReturnNote", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView editReturnNote(@RequestParam int returnId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReturnNote");

		List<ToolingReturnNote> lstIssueNote  = new ArrayList<ToolingReturnNote>();
		
		ToolingReturnNote obj = toolingReturnDao.editToolingReturnNote(returnId);
		lstIssueNote = toolingReturnDao.getToolingReturnNoteDetails(returnId);
		List<ToolingIssueNote> lstIssue = issueDao.getToolingIssueNoteDetail(obj.getIssueId());
		
		ToolingIssueNote issueNote = lstIssue.get(0);
		modelView.addObject("issueDetailId", issueNote.getIssueDetailId1());
		modelView.addObject("returnId", obj.getReturnId());
		modelView.addObject("returnDate", obj.getReturnDate());
		modelView.addObject("returnBy", obj.getReturnBy());
		modelView.addObject("issueId", obj.getIssueId());
		modelView.addObject("issueDate", obj.getIssueDate());
		modelView.addObject("issueBy", obj.getIssueBy());
		modelView.addObject("customerName", obj.getCustomerName());
		modelView.addObject("branch", obj.getBranchName());
		
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstIssueNote", lstIssueNote);
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/viewToolingReturnReport", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView viewToolingReturnReport(@RequestParam int returnId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewToolingReturnReport");

		List<ToolingReturnNote> lstIssueNote  = new ArrayList<ToolingReturnNote>();
		
		ToolingReturnNote obj = toolingReturnDao.editToolingReturnNote(returnId);
		lstIssueNote = toolingReturnDao.getToolingReturnNoteDetails(returnId);
		
		modelView.addObject("returnId", obj.getReturnId());
		modelView.addObject("returnDate", obj.getReturnDate());
		modelView.addObject("returnBy", obj.getReturnBy());
		modelView.addObject("issueId", obj.getIssueId());
		modelView.addObject("issueDate", obj.getIssueDate());
		modelView.addObject("issueBy", obj.getIssueBy());
		modelView.addObject("customerName", obj.getCustomerName());
		modelView.addObject("branch", obj.getBranchName());
		
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstIssueNote", lstIssueNote);
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/deleteReturnNote", method=RequestMethod.POST)
	public ModelAndView deleteToolingIssue(@RequestParam int originalId, @RequestParam int returnId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReturnNoteList");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		String message = toolingReturnDao.deleteReturnNote(originalId, returnId, userId, branchName);
		
		List<ToolingReturnNote> lstToolingReturnNote = new ArrayList<ToolingReturnNote>();
		lstToolingReturnNote = toolingReturnDao.getToolingReturnNote("");
		modelView.addObject("lstToolingReturnNote", lstToolingReturnNote);
		modelView.addObject("message", message);
		
		return modelView;
	}
	
	@RequestMapping(value = "/getToolingIssueNoteDetail", method=RequestMethod.POST)
	public ModelAndView getToolingIssueNoteDetail(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listProductIssueReturn");
		List<ToolingIssueNote> lstToolingIssue = new ArrayList<ToolingIssueNote>();
		lstToolingIssue = issueDao.getToolingIssueNoteDetail();
		modelView.addObject("lstToolingIssue", lstToolingIssue);
		return modelView;

	}

	@RequestMapping(value = "/getReturnSerialNumbers", method=RequestMethod.POST)
	public ModelAndView getReturnSerialNumbers(@RequestParam("lotNumber") String lotNumber, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listSerialNumber");
		List<SerialNumber> lstSerialNumber = serialNumberDao.getReturnSerialNumber(lotNumber);
		modelView.addObject("lstSerialNumber", lstSerialNumber);
		return modelView;
	}
}

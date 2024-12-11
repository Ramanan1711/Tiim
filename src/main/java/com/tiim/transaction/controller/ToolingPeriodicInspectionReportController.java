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
import com.tiim.dao.SendMailToApprover;
import com.tiim.dao.ToolingPeriodicInspectionReportDao;
import com.tiim.dao.ToolingPeriodicalInpsectionDao;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.SerialNumber;
import com.tiim.model.ToolingPeriodicInspection;
import com.tiim.model.ToolingPeriodicInspectionReport;
import com.tiim.util.TiimConstant;
import com.tiim.util.TiimUtil;

@Controller
public class ToolingPeriodicInspectionReportController {

	@Autowired
	ToolingPeriodicInspectionReportDao periodicReportDao;
	
	@Autowired
	ToolingPeriodicalInpsectionDao periodicDao;
	
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

	@RequestMapping(value = "/showToolingPeriodicInspectionReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolingPeriodicInspectionReport(@ModelAttribute ToolingPeriodicInspectionReport tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionReportList");
		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();
		lstPeriodicInspectionReport = periodicReportDao.getPeriodicInspectionReportSearch(tool.getSearchReportNo());
		modelView.addObject("lstPeriodicInspectionReport", lstPeriodicInspectionReport);
		return modelView;
	}
	
	@RequestMapping(value = "/searchToolingPeriodicInspectionReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchToolingPeriodicInspectionReport(@ModelAttribute ToolingPeriodicInspectionReport tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionReportList");
		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();
		lstPeriodicInspectionReport = periodicReportDao.getPeriodicInspectionReportSearch(tool.getSearchReportNo());
		modelView.addObject("lstPeriodicInspectionReport", lstPeriodicInspectionReport);
		modelView.addObject("searchReportNo", tool.getSearchReportNo());
		return modelView;
	}
	
	@RequestMapping(value = "/addIntialPeriodicInspectionReport", method=RequestMethod.POST)
	public ModelAndView addIntialToolingIssueNote( HttpServletRequest request)
	{
		int reportNo = periodicReportDao.getIntialValue();
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionReport");
		modelView.addObject("reportDate", sdf.format(cDate));
		modelView.addObject("reportNo", reportNo);
		
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", "");
		return modelView;
	}
	
	@RequestMapping(value = "/addPeriodicInspectionReport", method=RequestMethod.POST)
	public ModelAndView addPeriodicInspectionReport(@ModelAttribute ToolingPeriodicInspectionReport periodicInspection, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionReport");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		periodicInspection.setBranchName(branchName);
		String message = periodicReportDao.addPeriodicInspectionReport(periodicInspection, userId);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.updatePeriodicReportSerialNumber(periodicInspection);
		}
		//*********Send email*****************************//	
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PERIODIC_REPORT_SCREEN).toString();
		if("1".equalsIgnoreCase(approvalFlag))
		{
			ReadContent emailContent = new ReadContent();
			Map<String, String> input = new HashMap<String, String>();
			input.put(TiimConstant.TRANSACTION_ID, periodicInspection.getOriginalId()+"");
			input.put(TiimConstant.LOT_NUMBER, TiimUtil.ValidateNull(periodicInspection.getLotNumber()));
			input.put(TiimConstant.PRODUCT_NAME, TiimUtil.ValidateNull(periodicInspection.getProductName()));
			input.put(TiimConstant.DRAWING_NUMBER, TiimUtil.ValidateNull(periodicInspection.getDrawingNo()));
			input.put(TiimConstant.MACHINE_TYPE, TiimUtil.ValidateNull(periodicInspection.getMachineType()));
			
			String filePath = messageSource.getMessage("emailTemplate", null, null);
			String subject = messageSource.getMessage("subject", null, null)+" "+TiimConstant.PERIODIC_REPORT_SCREEN+" Screen";
			String content = emailContent.getEmailContent(filePath, input);
			sendMail.sendMail(TiimConstant.PERIODIC_REPORT_SCREEN, content, subject);	
		}
		int reportNo = periodicReportDao.getIntialValue();
		modelView.addObject("reportNo", reportNo);
		modelView.addObject("reportDate", sdf.format(cDate));
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);
		return modelView;
	}
	
	@RequestMapping(value = "/updatePeriodicInspectionReport", method=RequestMethod.POST)
	public ModelAndView updatePeriodicInspectionReport(@ModelAttribute ToolingPeriodicInspectionReport periodicInspection, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionReport");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		periodicInspection.setBranchName(branchName);
		
		String message = periodicReportDao.updatePeriodicInspectionReport(periodicInspection, userId);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.updatePeriodicReportSerialNumber(periodicInspection);
		}
		modelView.addObject("reportDate", sdf.format(cDate));
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);
		return modelView;
	}
	
	@RequestMapping(value = "/fetchToolingPeriodicReport", method=RequestMethod.POST)
	public ModelAndView fetchToolingReceiveInspection(@ModelAttribute ToolingPeriodicInspectionReport tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionReport");
		HttpSession session = request.getSession();
		List<ToolingPeriodicInspection> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspection>();
		String branchName = session.getAttribute("sesBranchName").toString();
		ToolingPeriodicInspection obj = periodicDao.editPeriodicInspectionSearch(tool.getOriginalId());
		lstPeriodicInspectionReport = periodicDao.getPeriodicInspectionDetail(tool.getOriginalId(), branchName);
		
		modelView.addObject("reportNo", tool.getReportNo());
		modelView.addObject("reportDate", sdf.format(cDate));
		
		modelView.addObject("requestNo", obj.getRequestNo());
		try 
		{
			modelView.addObject("requestDate", sdf.format(sdfDB.parse(obj.getRequestDate())));
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		modelView.addObject("requestedBy", obj.getRequestedBy());
		modelView.addObject("reportedBy", TiimUtil.ValidateNull(tool.getReportedBy()));
		
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("lstPeriodicInspectionReport", lstPeriodicInspectionReport);
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/editPeriodicInspectionReport", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView editPeriodicInspectionReport(@RequestParam int reportNo, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionReport");

		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();
		
		ToolingPeriodicInspectionReport obj = periodicReportDao.editPeriodicInspectionReport(reportNo);
		lstPeriodicInspectionReport = periodicReportDao.getPeriodicInspectionReportDetail(reportNo);
		System.out.println("lstPeriodicInspectionReport: "+lstPeriodicInspectionReport);
		modelView.addObject("reportNo", obj.getReportNo());
		modelView.addObject("requestNo", obj.getRequestNo());
		modelView.addObject("reportDate", obj.getReportDate());
		modelView.addObject("requestDate", obj.getRequestDate());
		modelView.addObject("requestedBy", obj.getRequestedBy());
		modelView.addObject("reportedBy", TiimUtil.ValidateNull(obj.getReportedBy()));
				
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstPeriodicInspectionReport", lstPeriodicInspectionReport);
		modelView.addObject("message", "");
		
		return modelView;
	}

	@RequestMapping(value = "/viewPeriodicInspectionReport", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView viewPeriodicInspectionReport(@RequestParam int reportNo, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewPeriodicInspectionReport");

		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();
		
		ToolingPeriodicInspectionReport obj = periodicReportDao.editPeriodicInspectionReport(reportNo);
		lstPeriodicInspectionReport = periodicReportDao.getPeriodicInspectionReportDetail(reportNo);
		modelView.addObject("reportNo", obj.getReportNo());
		modelView.addObject("requestNo", obj.getRequestNo());
		modelView.addObject("reportDate", obj.getReportDate());
		modelView.addObject("requestDate", obj.getRequestDate());
		modelView.addObject("requestedBy", obj.getRequestedBy());
		modelView.addObject("reportedBy", TiimUtil.ValidateNull(obj.getReportedBy()));
				
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstPeriodicInspectionReport", lstPeriodicInspectionReport);
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/deletePeriodicInspectionReport", method=RequestMethod.POST)
	public ModelAndView deletePeriodicInspectionReport(@RequestParam int originalId, @RequestParam int reportNo, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionReportList");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		
		String message = periodicReportDao.deletePeriodicInspection(originalId, reportNo, branchName, userId);
		
		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();
		lstPeriodicInspectionReport = periodicReportDao.getPeriodicInspectionReport("");
		modelView.addObject("lstPeriodicInspectionReport", lstPeriodicInspectionReport);
		modelView.addObject("message", message);
		
		return modelView;
	}

	@RequestMapping(value = "/getToolingPeriodicalRequestDetail", method=RequestMethod.POST)
	public ModelAndView getToolingPeriodicalRequestDetail(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PERIODIC_REQUEST_SCREEN).toString();
		ModelAndView modelView = new ModelAndView("listPeriodicReqestDetail");
		List<ToolingPeriodicInspection> lstToolingPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();
		lstToolingPeriodicInspection = periodicDao.getToolingPeriodicalRequestDetail(approvalFlag);
		modelView.addObject("lstToolingPeriodicInspection", lstToolingPeriodicInspection);
		return modelView;

	}

	@RequestMapping(value = "/getPeriodiReportcSerialNumber", method=RequestMethod.POST)
	public ModelAndView getPeriodiReportcSerialNumber(@RequestParam("lotNumber") String lotNumber, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listSerialNumber");
		List<SerialNumber> lstSerialNumber = serialNumberDao.getPeriodicReportSerialNumber(lotNumber);
		modelView.addObject("lstSerialNumber", lstSerialNumber);
		return modelView;
	}
}

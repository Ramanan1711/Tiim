package com.tiim.transaction.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.google.gson.Gson;
import com.tiim.dao.AddSerialNumberDao;
import com.tiim.dao.SendMailToApprover;
import com.tiim.dao.ToolingReceiptNoteDao;
import com.tiim.dao.ToolingReceivingInspectionDao;
import com.tiim.dao.ToolingReceivingRequestDao;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.Param;
import com.tiim.model.SerialNumber;
import com.tiim.model.ToolingInspection;
import com.tiim.model.ToolingRequest;
import com.tiim.util.TiimConstant;
import com.tiim.util.TiimUtil;

@Controller
public class ToolingReceivingInspectionController {

	@Autowired
	ToolingReceivingInspectionDao toolingInpsectionDao;
	
	@Autowired
	ToolingReceivingRequestDao toolingDao;
	
	@Autowired
	ToolingReceiptNoteDao receiptDao;
	
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
	
	@RequestMapping(value = "/showToolingReceiveInspection", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchProduct(@ModelAttribute ToolingInspection tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceivingInspectionList");
		
		List<ToolingInspection> lstToolinginsInspections = new ArrayList<ToolingInspection>();
		lstToolinginsInspections = toolingInpsectionDao.getToolingInspection("");
		modelView.addObject("lstToolinginsInspections", lstToolinginsInspections);
		return modelView;
	}
	
	@RequestMapping(value = "/searchToolingReceiveInspection", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView SearchProduct(@ModelAttribute ToolingInspection tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceivingInspectionList");
		
		List<ToolingInspection> lstToolinginsInspections = new ArrayList<ToolingInspection>();
		lstToolinginsInspections = toolingInpsectionDao.getToolingInspectionSearch(tool.getToolingSearch());
		modelView.addObject("lstToolinginsInspections", lstToolinginsInspections);
		return modelView;
	}
	
	@RequestMapping(value = "/showAddToolingReceiveInspection", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showAddToolingReceiveInspection(@ModelAttribute ToolingInspection tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceivingInspection");
		int inspectionId = toolingInpsectionDao.getIntialValue();
		modelView.addObject("inspectionReportNo", inspectionId);
		modelView.addObject("inspectionReportDate", sdf.format(cDate));
		modelView.addObject("count", 0);
		modelView.addObject("gridOrgCount", 0);
		modelView.addObject("toolingReceiptId", 0);
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("msg", "");
		return modelView;
	}
	
	@RequestMapping(value = "/fetchToolingReceiveInspection", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView fetchToolingReceiveInspection(@ModelAttribute ToolingInspection tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceivingInspection");

		List<ToolingRequest> lstToolingProduct = new ArrayList<ToolingRequest>();
		//System.out.println("requestId: "+tool.getRequestId()+", "+tool.getOriginalId());
		lstToolingProduct = toolingDao.getToolingInspectionDetails(tool.getOriginalId());
		
		modelView.addObject("inspectionReportNo", tool.getInspectionReportNo());
		modelView.addObject("inspectionReportDate", sdf.format(cDate));
		modelView.addObject("requestId", tool.getRequestId());
		modelView.addObject("requestDate", sdf.format(cDate));
		
		modelView.addObject("count", lstToolingProduct.size());
		modelView.addObject("gridOrgCount", lstToolingProduct.size());
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("lstToolingProduct", lstToolingProduct);
		modelView.addObject("msg", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/editToolingReceiveInspection", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editToolingReceiveInspection(@ModelAttribute ToolingInspection tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceivingInspection");

		List<ToolingInspection> lstToolingProduct = new ArrayList<ToolingInspection>();
	//	System.out.println("tool.getToolingInspectionId(): "+tool.getToolingInspectionId());
		ToolingInspection obj = toolingInpsectionDao.editToolingInspection(tool.getToolingInspectionId());
		lstToolingProduct = toolingInpsectionDao.getToolingInspectionDetails(tool.getToolingInspectionId());
		
		modelView.addObject("inspectionReportNo", obj.getToolingInspectionId());
		modelView.addObject("inspectionReportDate", obj.getInspectionReportDate());
		modelView.addObject("requestId", obj.getRequestId());
		try 
		{
			modelView.addObject("requestDate", obj.getRequestDate());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		for(ToolingInspection tooling: lstToolingProduct) {
			System.out.print("accepted: "+tooling.getAcceptedQty()+", "+tooling.getRejectedQty());
		}
		
		modelView.addObject("count", lstToolingProduct.size());
		modelView.addObject("gridOrgCount", lstToolingProduct.size());
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstToolingProduct", lstToolingProduct);
		modelView.addObject("toolingInspectionId", tool.getToolingInspectionId());
		modelView.addObject("msg", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/addToolingReceiveInspection", method=RequestMethod.POST)
	public ModelAndView addToolingReceiptNote(@ModelAttribute ToolingInspection toolingInspection, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceivingInspection");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingInspection.setBranchName(branchName);
		String message = toolingInpsectionDao.addToolingReceivingInspection(toolingInspection, userId);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.addSerialNumber(toolingInspection);
		}
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIVING_REPORT_SCREEN).toString();
		//*********Send email*****************************//	
		if("1".equalsIgnoreCase(approvalFlag))
		{
			/*
			 * ReadContent emailContent = new ReadContent(); Map<String, String> input = new
			 * HashMap<String, String>(); input.put(TiimConstant.TRANSACTION_ID,
			 * toolingInspection.getOriginalId()+""); input.put(TiimConstant.LOT_NUMBER,
			 * TiimUtil.ValidateNull(toolingInspection.getLotNumber()));
			 * input.put(TiimConstant.PRODUCT_NAME,
			 * TiimUtil.ValidateNull(toolingInspection.getToolingname()));
			 * input.put(TiimConstant.DRAWING_NUMBER,
			 * TiimUtil.ValidateNull(toolingInspection.getDrawingNo()));
			 * input.put(TiimConstant.MACHINE_TYPE,
			 * TiimUtil.ValidateNull(toolingInspection.getMachineType()));
			 * 
			 * String filePath = messageSource.getMessage("emailTemplate", null, null);
			 * String subject = messageSource.getMessage("subject", null,
			 * null)+" "+TiimConstant.RECEIVING_REPORT_SCREEN+" Screen"; String content =
			 * emailContent.getEmailContent(filePath, input);
			 * sendMail.sendMail(TiimConstant.RECEIVING_REPORT_SCREEN, content, subject);
			 */
		}
		int inspectionId = toolingInpsectionDao.getIntialValue();
		modelView.addObject("inspectionReportNo", inspectionId);
		modelView.addObject("inspectionReportDate", sdf.format(cDate));
		modelView.addObject("count", 0);
		modelView.addObject("gridOrgCount", 0);
		modelView.addObject("toolingReceiptId", 0);
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("msg", message);

		return modelView;
	}
	
	@RequestMapping(value = "/updateToolingInspection", method=RequestMethod.POST)
	public String updateToolingInspection(@ModelAttribute ToolingInspection toolingInspection, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceivingInspection");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingInspection.setBranchName(branchName);
		String message = toolingInpsectionDao.updateToolingInspection(toolingInspection, userId);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.updateSerialNumber(toolingInspection);
		}
		//int inspectionId = toolingInpsectionDao.getIntialValue();
		//modelView.addObject("inspectionReportNo", inspectionId);
		modelView.addObject("inspectionReportDate", sdf.format(cDate));
		modelView.addObject("count", 0);
		modelView.addObject("gridOrgCount", 0);
		modelView.addObject("toolingReceiptId", 0);
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("msg", message);

		return "redirect:/showToolingReceiveInspection.jsf";
	}
	
	@RequestMapping(value = "/deleteToolingInspection", method=RequestMethod.POST)
	public ModelAndView deleteToolingInspection(@ModelAttribute ToolingInspection toolingInspection, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("toolingReceivingInspectionList");
		List<ToolingInspection> lstToolinginsInspections = new ArrayList<ToolingInspection>();
		//System.out.println(toolingInspection.getOriginalId()+", "+toolingInspection.getToolingInspectionId());
		String branchName = session.getAttribute("sesBranchName").toString();
		String message = toolingInpsectionDao.deleteReceiptInspection(toolingInspection.getOriginalId(),toolingInspection.getToolingInspectionId(), userId,branchName);
		
		lstToolinginsInspections = toolingInpsectionDao.getToolingInspection("");
		
		modelView.addObject("lstToolinginsInspections", lstToolinginsInspections);
		modelView.addObject("msg", message);
		
		return modelView;
	}
	
	@RequestMapping(value = "/autoToolingReceivingInspection", method = RequestMethod.GET)
	public @ResponseBody String autoRequestNo(@RequestParam("inpectionId") String insectionId)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(insectionId != null && !"".equals(insectionId))
		{
			List<Integer> arlList = toolingInpsectionDao.getReceivingInspection(insectionId);
			Iterator<Integer> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				int id = itr.next();
				Param p = new Param(id+"" ,id+"",id+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/viewToolingReceiveInspectionReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewToolingReceiveInspectionReport(@ModelAttribute ToolingInspection tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewReceivingInspection");

		List<ToolingInspection> lstToolingProduct = new ArrayList<ToolingInspection>();
		
		ToolingInspection obj = toolingInpsectionDao.editToolingInspection(tool.getToolingInspectionId());
		lstToolingProduct = toolingInpsectionDao.getToolingInspectionDetails(tool.getToolingInspectionId());
		
		modelView.addObject("inspectionReportNo", obj.getToolingInspectionId());
		modelView.addObject("inspectionReportDate", obj.getInspectionReportDate());
		modelView.addObject("requestId", obj.getRequestId());
		try 
		{
			modelView.addObject("requestDate", obj.getRequestDate());
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		modelView.addObject("count", lstToolingProduct.size());
		modelView.addObject("gridOrgCount", lstToolingProduct.size());
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstToolingProduct", lstToolingProduct);
		modelView.addObject("toolingInspectionId", tool.getToolingInspectionId());
		modelView.addObject("msg", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/getReceivingProductDetail", method=RequestMethod.POST)
	public ModelAndView getReceivingProductDetail(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIVING_SCREEN).toString();
		ModelAndView modelView = new ModelAndView("listProductReceivingReport");
		List<ToolingRequest> lstToolingReceivingRequest = new ArrayList<ToolingRequest>();
		String drawingNo = request.getParameter("drawingNumber");
		lstToolingReceivingRequest = toolingDao.toolingReceivingRequestDetail(drawingNo, approvalFlag);
		modelView.addObject("lstToolingReceivingRequest", lstToolingReceivingRequest);
		return modelView;

	}
	
	@RequestMapping(value = "/getSerialNumberDetails", method=RequestMethod.POST)
	public ModelAndView getSerialNumberDetails(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listReceivedQuantity");
		int receivedQty = Integer.parseInt(request.getParameter("receivedQty"));
		modelView.addObject("receivedQty", receivedQty);
		return modelView;
	}
	
	@RequestMapping(value = "/getUpdatedSerialNumberDetails", method=RequestMethod.POST)
	public ModelAndView getUpdatedSerialNumberDetails(@RequestParam("lotNumber") String lotNumber, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listSerialNumber");
		List<SerialNumber> lstSerialNumber = serialNumberDao.getSerialNumber(lotNumber);
		modelView.addObject("lstSerialNumber", lstSerialNumber);
		return modelView;
	}
}

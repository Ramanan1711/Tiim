package com.tiim.transaction.controller;

import java.text.ParseException;
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
import com.tiim.dao.StockDao;
import com.tiim.dao.ToolingPeriodicInspectionReportDao;
import com.tiim.dao.ToolingPeriodicalInpsectionDao;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.Param;
import com.tiim.model.SerialNumber;
import com.tiim.model.Stock;
import com.tiim.model.ToolingPeriodicInspection;
import com.tiim.util.TiimConstant;
import com.tiim.util.TiimUtil;

@Controller
public class ToolingPeriodicInspectionController {

	@Autowired
	ToolingPeriodicalInpsectionDao periodicInspectionDao;
	
	@Autowired
	StockDao stkDao;
	
	@Autowired
	ToolingPeriodicInspectionReportDao inspectionReportDao;
	
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

	@RequestMapping(value = "/showToolingPeriodicInspection", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showToolingPeriodicInspection( HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionList");
		List<ToolingPeriodicInspection> lstToolingPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();
		lstToolingPeriodicInspection = periodicInspectionDao.getPeriodicInspection("");
		modelView.addObject("lstToolingPeriodicInspection", lstToolingPeriodicInspection);
		return modelView;
	}
	
	@RequestMapping(value = "/searchToolingPeriodicInspection", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchToolingPeriodicInspection(@ModelAttribute ToolingPeriodicInspection tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionList");
		List<ToolingPeriodicInspection> lstToolingPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();
		lstToolingPeriodicInspection = periodicInspectionDao.getPeriodicInspection(tool.getSearchRequestNo());
		modelView.addObject("lstToolingPeriodicInspection", lstToolingPeriodicInspection);
		modelView.addObject("searchRequestNo", tool.getSearchRequestNo());
		return modelView;
	}
	
	@RequestMapping(value = "/showPeriodicInspection", method=RequestMethod.POST)
	public ModelAndView showPeriodicInspection(@ModelAttribute ToolingPeriodicInspection periodicInspection, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspection");
		int requestNo = periodicInspectionDao.getIntialValue();
		modelView.addObject("requestNo", requestNo);
		modelView.addObject("requestDate", sdf.format(cDate));
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", "");
		modelView.addObject("count", 0);
		modelView.addObject("gridOrgCount", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/getIndividualStock", method=RequestMethod.POST)
	public @ResponseBody Stock getIndividualStock(@RequestParam int stockId, HttpServletRequest request)
	{
		Stock stock = stkDao.getIndividualStock(stockId);
		return stock;
	}
	
	@RequestMapping(value = "/addPeriodicInspection", method=RequestMethod.POST)
	public ModelAndView addPeriodicInspection(@ModelAttribute ToolingPeriodicInspection periodicInspection, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspection");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		periodicInspection.setBranchName(branchName);
		String message = periodicInspectionDao.addPeriodicalInspection(periodicInspection, userId);
		
		//*********Send email*****************************//		
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PERIODIC_REQUEST_SCREEN).toString();
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
				String subject = messageSource.getMessage("subject", null, null)+" "+TiimConstant.PERIODIC_REQUEST_SCREEN+" Screen";
				String content = emailContent.getEmailContent(filePath, input);
				sendMail.sendMail(TiimConstant.PERIODIC_REQUEST_SCREEN, content, subject);	
		}	
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.updateSerialNumber(periodicInspection);
		}
		modelView.addObject("requestDate", sdf.format(cDate));
		int requestNo = periodicInspectionDao.getIntialValue();
		modelView.addObject("requestNo", requestNo);
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);
		modelView.addObject("count", 0);
		modelView.addObject("gridOrgCount", 0);
		return modelView;
	}
	
	@RequestMapping(value = "/updatePeriodicInspection", method=RequestMethod.POST)
	public ModelAndView updatePeriodicInspection(@ModelAttribute ToolingPeriodicInspection periodicInspection, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		periodicInspection.setBranchName(branchName);
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspection");
		String message = periodicInspectionDao.updatePeriodicInspection(periodicInspection, userId);
		String serialFlag = session.getAttribute("serilaFlag").toString();
		if("1".equalsIgnoreCase(serialFlag))
		{
			serialNumberDao.updateSerialNumber(periodicInspection);
		}
		modelView.addObject("requestDate", sdf.format(cDate));
		
		modelView.addObject("action", "new");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);
		modelView.addObject("count", 0);
		modelView.addObject("gridOrgCount", 0);
		
		return modelView;
	}
	
	@RequestMapping(value = "/editPeriodicInspection", method={RequestMethod.POST, RequestMethod.POST})
	public ModelAndView editperiodicInspection(@RequestParam int requestNo, HttpServletRequest request)
	{
		
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspection");
		List<ToolingPeriodicInspection> lstPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();
		HttpSession session = request.getSession();
		String branchName = session.getAttribute("sesBranchName").toString();
		ToolingPeriodicInspection obj = periodicInspectionDao.editPeriodicInspection(requestNo);
				lstPeriodicInspection = periodicInspectionDao.getPeriodicInspectionDetail(requestNo,branchName);
				
		modelView.addObject("requestNo", obj.getRequestNo());
		try 
		{
			modelView.addObject("requestDate", sdf.format(sdfDB.parse(obj.getRequestDate())));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}	
		modelView.addObject("requestedBy", obj.getRequestedBy());	
				
		modelView.addObject("action", "exist");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstPeriodicInspection", lstPeriodicInspection);
		modelView.addObject("message", "");
		
		if(lstPeriodicInspection.size() == 0)
		{
			modelView.addObject("count", 1);
			modelView.addObject("gridOrgCount", 1);
		}
		else
		{
			modelView.addObject("count", lstPeriodicInspection.size());
			modelView.addObject("gridOrgCount", lstPeriodicInspection.size());
		}
		
		return modelView;
	}
	
	@RequestMapping(value = "/viewPeriodicInspectionRequestReport", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView viewPeriodicInspectionRequestReport(@RequestParam int requestNo, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewPeriodicInspectionRequestReport");
		List<ToolingPeriodicInspection> lstPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();
		HttpSession session = request.getSession();
		String branchName = session.getAttribute("sesBranchName").toString();
		ToolingPeriodicInspection obj = periodicInspectionDao.editPeriodicInspection(requestNo);
				lstPeriodicInspection = periodicInspectionDao.getPeriodicInspectionDetail(requestNo, branchName);
				
		modelView.addObject("requestNo", obj.getRequestNo());
		try 
		{
			modelView.addObject("requestDate", sdf.format(sdfDB.parse(obj.getRequestDate())));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}	
		modelView.addObject("requestedBy", obj.getRequestedBy());	
				
		modelView.addObject("action", "exist");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstPeriodicInspection", lstPeriodicInspection);
		modelView.addObject("message", "");
		
		if(lstPeriodicInspection.size() == 0)
		{
			modelView.addObject("count", 1);
			modelView.addObject("gridOrgCount", 1);
		}
		else
		{
			modelView.addObject("count", lstPeriodicInspection.size());
			modelView.addObject("gridOrgCount", lstPeriodicInspection.size());
		}
		
		return modelView;
	}

	@RequestMapping(value = "/deletePeriodicInspection", method=RequestMethod.POST)
	public ModelAndView deletePeriodicInspection(@RequestParam int originalId, @RequestParam int requestNo, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingPeriodicInspectionList");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		String message = "";
		if(!inspectionReportDao.isIntegratedWithPeriodicRequest(originalId))
		{
			message = periodicInspectionDao.deletePeriodicInspection(originalId, requestNo, userId,branchName);
		}else
		{
			message = "Request moved to report. Cannot delete";
		}
		
		List<ToolingPeriodicInspection> lstToolingPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();
		lstToolingPeriodicInspection = periodicInspectionDao.getPeriodicInspection("");
		modelView.addObject("lstToolingPeriodicInspection", lstToolingPeriodicInspection);
		modelView.addObject("message", message);
		
		return modelView;
	}
	
	@RequestMapping(value = "/getAutoPeriodicInspection", method = RequestMethod.GET)
	public @ResponseBody String getAutoPeriodicInspection(@RequestParam("inpectionId") String inpectionId)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(inpectionId != null && !"".equals(inpectionId))
		{
			List<Integer> arlList = periodicInspectionDao.getAutoPeriodicInspection(inpectionId);
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
	
	@RequestMapping(value = "/getLotNoDetails", method=RequestMethod.POST)
	public ModelAndView getLotNoDetails(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listLotNoStockDetails");
		List<Stock> lstStock = new ArrayList<Stock>();
		lstStock = stkDao.getAutoStock("","");
		modelView.addObject("lstStock", lstStock);
		return modelView;

	}
	
	@RequestMapping(value = "/getPeriodicSerialNumber", method=RequestMethod.POST)
	public ModelAndView getPeriodicSerialNumber(@RequestParam("lotNumber") String lotNumber, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listPeriodicSerialNumber");
		List<SerialNumber> lstSerialNumber = serialNumberDao.getPeriodicSerialNumber(lotNumber);
		modelView.addObject("lstSerialNumber", lstSerialNumber);
		return modelView;
	}
}

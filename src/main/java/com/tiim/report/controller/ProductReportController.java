package com.tiim.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.tiim.dao.ProductReportDao;
import com.tiim.dao.RejectedProductDao;
import com.tiim.dao.ToolingIssueDao;
import com.tiim.dao.ToolingReceiptNoteDao;
import com.tiim.fileutil.CreateExcelReport;
import com.tiim.fileutil.CreateExpirtyProductReport;
import com.tiim.fileutil.CreatePendingReturnReport;
import com.tiim.fileutil.CreateRejectedProductReport;
import com.tiim.model.Param;
import com.tiim.model.ProductReport;
import com.tiim.model.RejectedProduct;
import com.tiim.model.ReportFiles;
import com.tiim.model.ToolTracker;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.model.ToolingReturnNote;
import com.tiim.service.GetReportBasedOnProductDetails;
import com.tiim.util.TiimConstant;

@Controller
public class ProductReportController {

	@Autowired
	ProductReportDao reportDao;
	
	@Autowired
	ProductReportDao productReportDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	GetReportBasedOnProductDetails getReportBasedOnProductDetails;
	
	@Autowired
	ToolingIssueDao issueDao;
	
	@Autowired
	ToolingReceiptNoteDao receiptDao;
	
	@Autowired
	RejectedProductDao rejectDao;
	
	@RequestMapping(value = "/createProductReport", method={RequestMethod.GET})
	public String CreateProductReport( HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewProductReport");
	
		return "viewProductReport";
	}
	
	@RequestMapping(value = "/viewProductReport", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String viewProductReport(@ModelAttribute ProductReport productReport, HttpServletRequest request)
	{		
		//ProductReport productReport = new ProductReport();
		//productReport.setProductName(productName);

		String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		ModelAndView modelView = new ModelAndView("pdfViewProductReport");
		List<ProductReport> lstProductReport = reportDao.getProductReport(productReport);
		String fileName = "/productreport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreateExcelReport createExcel = new CreateExcelReport();
		createExcel.writeProductExcel(lstProductReport, imageFolder,"");
		return "./ReportFiles/"+fileName;
		//modelView.addObject("lstProductReport", lstProductReport);
	}
	
	@RequestMapping(value = "/viewProductReport1", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewProductReport1(@ModelAttribute ProductReport productReport, HttpServletRequest request)
	{		
		//ProductReport productReport = new ProductReport();
		//productReport.setProductName(productName);

		//String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		ModelAndView modelView = new ModelAndView("pdfViewProductReport");
		List<ProductReport> lstProductReport = reportDao.getProductReport(productReport);
		/*String fileName = "/productreport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreateExcelReport createExcel = new CreateExcelReport();
		//createExcel.writeProductExcel(lstProductReport, imageFolder,"");
		//return "./ReportFiles/"+fileName;
*/		modelView.addObject("lstProductReport", lstProductReport);
		return modelView;
	}
	
	@RequestMapping(value = "/viewProductReturnPendingReport", method={RequestMethod.GET})
	public ModelAndView viewProductReturnPendingReport( HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView modelView = new ModelAndView("viewProductReturnPendingReport");
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_ISSUE_SCREEN).toString();
		List<ToolingIssueNote> lstProductReport = issueDao.getToolingIssueNoteDetail(approvalFlag);
		modelView.addObject("lstProductReport", lstProductReport);
		return modelView;
	}
	
	
	@RequestMapping(value = "/showProductReturnPendingReport", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String showProductReturnPendingReport(HttpServletRequest request)
	{		
		//ProductReport productReport = new ProductReport();
		//productReport.setProductName(productName);
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_ISSUE_SCREEN).toString();
		String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		//ModelAndView modelView = new ModelAndView("viewProductReport");
		List<ToolingIssueNote> lstToolingIssue = issueDao.getToolingIssueNoteDetail(approvalFlag);
		String fileName = "/pendingReturnReport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreatePendingReturnReport createExcel = new CreatePendingReturnReport();
		createExcel.writeProductExcel(lstToolingIssue, imageFolder);
		return "./ReportFiles/"+fileName;
	}
	
	@RequestMapping(value = "/viewExpiryProductReport", method={RequestMethod.GET})
	public ModelAndView viewExpiryProductReport( HttpServletRequest request)
	{
		List<ToolingReceiptNote> lstProductReport = receiptDao.getExpiryProductDetail();
		ModelAndView modelView = new ModelAndView("viewExpiryProductReport");
		modelView.addObject("lstProductReport", lstProductReport);
		return modelView;
	}
	
	
	@RequestMapping(value = "/showExpiryProductReport", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String showExpiryProductReport(HttpServletRequest request)
	{		
		//ProductReport productReport = new ProductReport();
		//productReport.setProductName(productName);

		String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		//ModelAndView modelView = new ModelAndView("viewProductReport");
		List<ToolingReceiptNote> lstReceiptNote = receiptDao.getExpiryProductDetail();
		String fileName = "/expirtyProductReport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreateExpirtyProductReport productReport = new CreateExpirtyProductReport();
		productReport.writeProductExcel(lstReceiptNote, imageFolder);
		return "./ReportFiles/"+fileName;
	}
	
	@RequestMapping(value = "/viewRejectedProductReport", method={RequestMethod.GET})
	public ModelAndView viewRejectedProductReport( HttpServletRequest request)
	{
		List<RejectedProduct> lstProductReport = rejectDao.getRejectedProductDetail();
		ModelAndView modelView = new ModelAndView("viewRejectedProductReport");
		modelView.addObject("lstProductReport", lstProductReport);
		return modelView;
	}
	
	@RequestMapping(value = "/showRejectedProductReport", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String showRejectedProductReport(HttpServletRequest request)
	{		
		//ProductReport productReport = new ProductReport();
		//productReport.setProductName(productName);

		String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		//ModelAndView modelView = new ModelAndView("viewProductReport");
		List<RejectedProduct> lstRejectedProduct = rejectDao.getRejectedProductDetail();
		String fileName = "/expirtyProductReport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreateRejectedProductReport productReport = new CreateRejectedProductReport();
		productReport.writeProductExcel(lstRejectedProduct, imageFolder);
		return "./ReportFiles/"+fileName;
	}
	
	@RequestMapping(value = "/createProductIntervalReport", method={RequestMethod.GET})
	public String createProductIntervalReport( HttpServletRequest request)
	{	
		return "viewProductInervalReport";
	}
	
	@RequestMapping(value = "/viewProductIntervalReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewProductIntervalReport(
			@ModelAttribute ProductReport productReport ,HttpServletRequest request)
	{		
		//ProductReport productReport = new ProductReport();
		//String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
	//	ModelAndView modelView = new ModelAndView("pdfToolingInspectionDueReport");
		//List<ProductReport> lstProductReport = reportDao.getProductIntervalReport(productReport, productReport.getPercentage());
		//modelView.addObject("lstProductReport",lstProductReport);
		/*String fileName = "/productreport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreateExcelReport createExcel = new CreateExcelReport();
		createExcel.writeProductExcel(lstProductReport, imageFolder, "Interval");
		//return "./ReportFiles/"+fileName;*/
		ModelAndView modelView = new ModelAndView("pdfToolingInspectionDueReport");
		ToolTracker toolTracker = reportDao.getToolTracker(productReport);
		modelView.addObject("toolTracker",toolTracker);
		return modelView;
	}
	
	@RequestMapping(value = "/createProductLifeReport", method={RequestMethod.GET})
	public String createProductLifeReport( HttpServletRequest request)
	{	
		return "viewProductLifeReport";
	}
	
	@RequestMapping(value = "/viewProductLifeReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewProductLifeReport(@ModelAttribute ProductReport productReport , HttpServletRequest request)
	{		
		//ProductReport productReport = new ProductReport();
		//String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		List<ProductReport> lstProductReport = reportDao.getProductLifeReport(productReport, productReport.getPercentage());
		ModelAndView modelView = new ModelAndView("pdfProductLifeReport");
		modelView.addObject("lstProductReport",lstProductReport);
		/*String fileName = "/productreport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreateExcelReport createExcel = new CreateExcelReport();
		createExcel.writeProductExcel(lstProductReport, imageFolder, "Life");
		return "./ReportFiles/"+fileName;*/
		return modelView;
	}
	
	@RequestMapping(value = "/viewAllProductReportFiles", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String viewAllProductReportFiles(@RequestParam("toolingLotNumber") String toolingLotNumber, 
			@RequestParam("requestId") String requestId, HttpServletRequest request)
	{		
		Gson gson = new Gson();
		String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		System.out.println("imageFolder: "+imageFolder);
		String srcFile = messageSource.getMessage("src.directory", null,null);
		String productName = "report";
		List<ReportFiles> lstFiles = getReportBasedOnProductDetails.getProductFiles(srcFile,imageFolder, productName, toolingLotNumber, requestId);
		
		return gson.toJson(lstFiles);
	}
	
	@RequestMapping(value = "/viewProductReportFiles", method={RequestMethod.GET, RequestMethod.POST})
	public String viewProductReportFiles(HttpServletRequest request)
	{		
		//System.out.println("src file: "+messageSource.getMessage("src.directory", null,null));
		return "GetReportFiles";
	}
	
	@RequestMapping(value = "/autoToolLotNumber", method = RequestMethod.GET)
	public @ResponseBody String autoLotNumber(@RequestParam("lotNumber") String lotNumber)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(lotNumber != null && !"".equals(lotNumber))
		{
			List<String> arlList = productReportDao.getToolLotNumber(lotNumber);
			Iterator<String> itr = arlList.iterator();
			while(itr.hasNext())
			{
				String lot = itr.next();
				Param p = new Param(lot ,lot,lot);
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/createClearingSOPReport", method={RequestMethod.GET})
	public String createClearingSOPReport( HttpServletRequest request)
	{	
		return "createClearingSOPReport";
	}
	
	
	@RequestMapping(value = "/viewClearingSOPReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewClearingSOPReport(HttpServletRequest request)
	{		
		//ProductReport productReport = new ProductReport();
		//String imageFolder = request.getSession().getServletContext().getRealPath("ReportFiles");
		
		String lotNumber = request.getParameter("lotNumber");
		System.out.println(lotNumber);
		List<ToolingReturnNote> lstReturnNote = reportDao.getClearingSOPReport(lotNumber);
		ModelAndView modelView = new ModelAndView("pdfCleaningSOPReport");
		modelView.addObject("lstReturnNote",lstReturnNote);
		/*String fileName = "/productreport_"+ new SimpleDateFormat("yyyyMMddhhmm'.xlsx'").format(new Date());
		imageFolder = imageFolder+fileName;
		CreateExcelReport createExcel = new CreateExcelReport();
		createExcel.writeProductExcel(lstProductReport, imageFolder, "Life");
		return "./ReportFiles/"+fileName;*/
		return modelView;
	}
	
}

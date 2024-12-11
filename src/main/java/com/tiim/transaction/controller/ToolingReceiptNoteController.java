package com.tiim.transaction.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tiim.dao.CleaningSopDao;
import com.tiim.dao.PlatingDao;
import com.tiim.dao.SendMailToApprover;
import com.tiim.dao.ToolingReceiptNoteDao;
import com.tiim.fileutil.FileMeta;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.Product;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.service.MachineService;
import com.tiim.service.ProductService;
import com.tiim.util.TiimConstant;

@Controller
public class ToolingReceiptNoteController {

	@Autowired
	ToolingReceiptNoteDao toolingReceiptDao;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	MachineService machineService;
	
	@Autowired
	PlatingDao platingDao;
	
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
	
	@RequestMapping(value = "/showToolingReceiptNote", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showProduct(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceiptNote");
		List<ToolingReceiptNote> lstProductDetail = new ArrayList<ToolingReceiptNote>();
		lstProductDetail = toolingReceiptDao.getToolingReceiptNoteDetails("");
		modelView.addObject("lstProductDetail", lstProductDetail);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchToolingReceiptNote", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchProduct( HttpServletRequest request)
	{
		//@ModelAttribute ToolingReceiptNote tool,
		ModelAndView modelView = new ModelAndView("toolingReceiptNote");
	/*	System.out.println("searchToolingReceiptNote: "+request.getParameter("searchToolingReceiptNote"));
		System.out.println("tool.getSearchProduct(): "+request.getParameter("searchProduct"));*/
		List<ToolingReceiptNote> lstProductDetail = new ArrayList<ToolingReceiptNote>();
		lstProductDetail = toolingReceiptDao.getToolingReceiptNoteDetails(request.getParameter("searchToolingReceiptNote"));
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("searchProduct", request.getParameter("searchToolingReceiptNote"));
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/listReceiptNote", method={RequestMethod.POST, RequestMethod.POST})
	public ModelAndView listReceiptNote(@ModelAttribute ToolingReceiptNote tool, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listReceiptNote");
		List<ToolingReceiptNote> lstProductDetail = new ArrayList<ToolingReceiptNote>();
		lstProductDetail = toolingReceiptDao.getToolingReceiptNoteDetails(tool.getSearchProduct());
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("searchProduct", tool.getSearchProduct());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/searchProductNameOnly", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView showSearchProduct(@RequestParam String searchProductName, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listReceiptNote");
		List<Product> lstProductDetail = new ArrayList<Product>();
		lstProductDetail = productService.getProductDetails(searchProductName);
		modelView.addObject("lstProductDetail", lstProductDetail);
		return modelView;
	}
	
	
	@RequestMapping(value = "/getProductDetailsByDrawingNo", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView getProductDetailsByDrawingNo(@RequestParam String searchProductName, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listReceiptNote");
		List<Product> lstProductDetail = new ArrayList<Product>();
		lstProductDetail = productService.getProductDetailsByDrawingNo(searchProductName);
		modelView.addObject("lstProductDetail", lstProductDetail);
		return modelView;
	}
	
	@RequestMapping(value = "/addToolingReceiptNote", method=RequestMethod.POST)
	public String addToolingReceiptNote(@ModelAttribute ToolingReceiptNote toolingReceiptNote, MultipartHttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingReceiptNote.setBranchName(branchName);
		//*************************************************************/
		
		 String folderName = "receipt";
		   FileMeta fileMeta = new FileMeta();
			String imageFolder = request.getSession().getServletContext().getRealPath("uploaddocument");
			//System.out.println("path: "+request.getSession().getServletContext().getRealPath("uploaddocument"));
			if(folderName!=null)
				imageFolder = imageFolder +"/"+ folderName+"/";
			File imageFile = new File(imageFolder);
			if(!imageFile.exists())
			{
				imageFile.mkdir();
			}
			 Iterator<String> itr =  request.getFileNames();
			 MultipartFile mpf = null;
			 int i = 0;
			 //2. get each file
			 while(itr.hasNext()){
			//	System.out.println("i: "+i);
				 String documentName = "";
				 //2.1 get next MultipartFile
				 mpf = request.getFile(itr.next()); 
				 String originalFileName = mpf.getOriginalFilename();
				 if(originalFileName != null && !originalFileName.equals(""))
				 {
					 originalFileName = originalFileName.replaceAll(" ", "_");
				 System.out.println(originalFileName +" uploaded! ");
				 if(i==0)
				 {
				  documentName = "MOC_"+toolingReceiptNote.getProductNamePO()+"_"+toolingReceiptNote.getDrawingNoPO()+"_"+originalFileName;
				  toolingReceiptNote.setMocUploadImage("./uploaddocument/"+folderName+"/"+documentName);
				 }else if(i==1){
					 documentName = "DQ_"+toolingReceiptNote.getProductNamePO()+"_"+toolingReceiptNote.getDrawingNoPO()+"_"+originalFileName;
					 toolingReceiptNote.setDqUploadImage("./uploaddocument/"+folderName+"/"+documentName);
				 }else if(i==2){
					 documentName = "Insp_"+toolingReceiptNote.getProductNamePO()+"_"+toolingReceiptNote.getDrawingNoPO()+"_"+originalFileName;
					 toolingReceiptNote.setInspectionUploadImage("./uploaddocument/"+folderName+"/"+documentName);
				 }
				 //2.3 create new fileMeta
				 fileMeta = new FileMeta();
				 fileMeta.setFileName(documentName);
				 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
				 fileMeta.setFileType(mpf.getContentType());
				 
				 try {
					fileMeta.setBytes(mpf.getBytes());
					// copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
					FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(imageFolder+documentName));			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Exception when copy the file: "+e.getMessage());
					e.printStackTrace();
				}
				 //2.4 add to files
				// files.add(fileMeta);
				 }
				 i++;
			 }
		
		//**********************************************************//
		
		String message = toolingReceiptDao.addToolingReceiptNote(toolingReceiptNote, userId);		
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIPT_SCREEN).toString();
		//*********Send email*****************************//	
		/*
		 * if("1".equalsIgnoreCase(approvalFlag)) { ReadContent emailContent = new
		 * ReadContent(); Map<String, String> input = new HashMap<String, String>();
		 * input.put(TiimConstant.TRANSACTION_ID, toolingReceiptNote.getGrnNo()+"");
		 * input.put(TiimConstant.LOT_NUMBER,
		 * toolingReceiptNote.getToolingLotNumberPO());
		 * input.put(TiimConstant.PRODUCT_NAME, toolingReceiptNote.getProductNamePO());
		 * input.put(TiimConstant.DRAWING_NUMBER, toolingReceiptNote.getDrawingNoPO());
		 * input.put(TiimConstant.MACHINE_TYPE, toolingReceiptNote.getMachineTypePO());
		 * 
		 * String filePath = messageSource.getMessage("emailTemplate", null, null);
		 * String subject = messageSource.getMessage("subject", null,
		 * null)+" "+TiimConstant.RECEIPT_SCREEN+" Screen"; String content =
		 * emailContent.getEmailContent(filePath, input);
		 * sendMail.sendMail(TiimConstant.RECEIPT_SCREEN, content, subject); }
		 */
		return "redirect:/showToolingReceiptNote.jsf";
	}
	
	
	@RequestMapping(value = "/listReceiptProduct", method={RequestMethod.POST, RequestMethod.POST})
	public ModelAndView listReceiptProduct(@RequestParam int toolingReceiptId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("listProductName");
		List<ToolingReceiptNote> lstProductDetail = new ArrayList<ToolingReceiptNote>();
		List<ToolingReceiptNote> lstProductDetail1 = new ArrayList<ToolingReceiptNote>();
		lstProductDetail = toolingReceiptDao.getReceiptProductDetails(toolingReceiptId);
		lstProductDetail1 = toolingReceiptDao.getReceiptProductDetails1(toolingReceiptId);
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("lstProductDetail1", lstProductDetail1);
		return modelView;
	}
	
	/*@RequestMapping(value = "/addToolingReceiptNote", method=RequestMethod.POST)
	public ModelAndView addToolingReceiptNote(@ModelAttribute ToolingReceiptNote toolingReceiptNote, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("toolingReceiptNote");
		String message = toolingReceiptDao.addToolingReceiptNote(toolingReceiptNote);		
		modelView.addObject("message", message);

		return modelView;
	}*/
	
	@RequestMapping(value = "/updateToolingReceiptNote", method=RequestMethod.POST)
	public @ResponseBody ModelAndView updateToolingReceiptNote(@ModelAttribute ToolingReceiptNote toolingReceiptNote, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("toolingReceiptNote");
		String message = toolingReceiptDao.updateToolingReceiptNote(toolingReceiptNote, userId);		
		modelView.addObject("message", message);

		return modelView;
	}
	
	@RequestMapping(value = "/deleteReceiptNote", method=RequestMethod.POST)
	public ModelAndView deleteReceiptNote(@RequestParam int toolingreceiptid, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("toolingReceiptNote");
		String message = toolingReceiptDao.deleteReceiptNote(toolingreceiptid, userId);
		List<ToolingReceiptNote> lstProductDetail = new ArrayList<ToolingReceiptNote>();
		lstProductDetail = toolingReceiptDao.getToolingReceiptNoteDetails("");
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getToolingReceiptNote", method=RequestMethod.POST)
	public @ResponseBody ToolingReceiptNote getToolingReceiptNote(@RequestParam int toolingreceiptid, HttpServletRequest request)
	{
		ToolingReceiptNote obj = toolingReceiptDao.getToolingReceiptNote(toolingreceiptid);
		return obj;
	}
	
	@RequestMapping(value = "/viewToolingReceiptNote", method=RequestMethod.POST)
	public @ResponseBody ToolingReceiptNote viewToolingReceiptNote(@RequestParam int toolingreceiptid, HttpServletRequest request)
	{
		ToolingReceiptNote obj = toolingReceiptDao.getToolingReceiptNote(toolingreceiptid);
		return obj;
	}
	
	@RequestMapping(value = "/getToolingReceiptProduct", method={RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String getToolingReceiptProduct(@RequestParam("toolingProductid") int toolingProductid, HttpServletRequest request)
	{
		Gson gson = new Gson();
		ToolingReceiptNote obj = toolingReceiptDao.getToolingReceiptProduct(toolingProductid);
		return gson.toJson(obj);
	}
	
	@RequestMapping(value = "/viewReceiptNoteReport", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView viewIndentNoteReport(@RequestParam int requestId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("viewReceiptNote");

		ToolingReceiptNote receiptNote = toolingReceiptDao.getToolingReceiptNote(requestId);
		List<ToolingReceiptNote> lstReceiptNote = toolingReceiptDao.getReceiptProductDetails(requestId);

		modelView.addObject("receiptNote", receiptNote);
		modelView.addObject("lstReceiptNote", lstReceiptNote);
		modelView.addObject("msg", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/getToolingReceiptProduct1", method=RequestMethod.POST)
	public @ResponseBody String getToolingReceiptProduct1(@RequestParam int toolingProductid, HttpServletRequest request)
	{
		Gson gson = new Gson();
		ToolingReceiptNote obj = toolingReceiptDao.getToolingReceiptProduct1(toolingProductid);
		
		return gson.toJson(obj);
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		int grnNo = toolingReceiptDao.getIntialValue();
		int autoToolingLotNumber = toolingReceiptDao.getIntialLotNumberValue();
		modelView.addObject("toolingReceiptId", 0);
		modelView.addObject("toolingProductId", 0);
		modelView.addObject("grnNo", grnNo);
		String lotNumber = autoToolingLotNumber +"";
		if(lotNumber.length() == 1)
		{
			lotNumber = "00000"+lotNumber;
		}else if(lotNumber.length() == 2)
		{
			lotNumber = "0000"+lotNumber;
		}else if(lotNumber.length() == 3)
		{
			lotNumber = "000"+lotNumber;
		}else if(lotNumber.length() == 4)
		{
			lotNumber = "00"+lotNumber;
		}else if(lotNumber.length() == 5)
		{
			lotNumber = "0"+lotNumber;
		}
		modelView.addObject("lstSOP", sopDao.getSop("recevingcleaning"));
		modelView.addObject("autoToolingLotNumber", lotNumber);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		modelView.addObject("lstMachineType", machineService.getMachineType());
		modelView.addObject("lstPlating",platingDao.getPlatingDetails());
		modelView.addObject("grnDate", sdf.format(cDate));
	}
}

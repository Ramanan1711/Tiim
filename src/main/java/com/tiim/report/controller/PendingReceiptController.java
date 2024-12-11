package com.tiim.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ReceiptPendingDao;
import com.tiim.dao.ToolingReceiptNoteDao;
import com.tiim.model.Stock;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.service.MachineService;
import com.tiim.util.TiimConstant;

@Controller
public class PendingReceiptController {
	
	@Autowired
	ReceiptPendingDao receiptPendingDao;
	
	@Autowired
	MachineService machineService;
	
	@Autowired
	ToolingReceiptNoteDao toolingReceiptDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/**********Set Current Date for refreshing the lab data********/
	  java.util.Date cDate=new java.util.Date();
	/***************************************************************/
	
	@RequestMapping(value = "/getReceiptPendingReport", method={RequestMethod.GET})
	public ModelAndView  getReceiptPendingReport(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIPT_SCREEN).toString();
		ModelAndView modelView = new ModelAndView("toolingReceiptNote");
		List<ToolingReceiptNote> lstProductDetail = new ArrayList<ToolingReceiptNote>();
		lstProductDetail = receiptPendingDao.getPendingReceipt(approvalFlag);
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("searchProduct", "");
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		int grnNo = toolingReceiptDao.getIntialValue();
		modelView.addObject("toolingReceiptId", 0);
		modelView.addObject("toolingProductId", 0);
		modelView.addObject("grnNo", grnNo);
		
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		modelView.addObject("lstMachineType", machineService.getMachineType());
		modelView.addObject("grnDate", sdf.format(cDate));
	}
}

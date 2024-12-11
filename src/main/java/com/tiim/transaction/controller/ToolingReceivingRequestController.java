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
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.SendMailToApprover;
import com.tiim.dao.ToolingReceiptNoteDao;
import com.tiim.dao.ToolingReceivingInspectionDao;
import com.tiim.dao.ToolingReceivingRequestDao;
import com.tiim.fileutil.ReadContent;
import com.tiim.model.ToolingRequest;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.util.TiimConstant;
import com.tiim.util.TiimUtil;

@Controller
public class ToolingReceivingRequestController {

	@Autowired
	ToolingReceivingRequestDao toolingRequestDao;

	@Autowired
	ToolingReceiptNoteDao toolingReceiptDao;

	@Autowired
	ToolingReceiptNoteDao receiptDao;

	@Autowired
	ToolingReceivingInspectionDao toolingInpsectionDao;
	@Autowired
	MessageSource messageSource;

	@Autowired
	SendMailToApprover sendMail;

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	/********** Set Current Date for refreshing the lab data ********/
	java.util.Date cDate = new java.util.Date();

	/***************************************************************/

	@RequestMapping(value = "/showReceivingRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showReceivingRequest(@ModelAttribute ToolingRequest tool, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolingReceivingRequestList");
		HttpSession session = request.getSession();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIVING_SCREEN).toString();
		List<ToolingRequest> lstToolinginsInspections = new ArrayList<ToolingRequest>();
		lstToolinginsInspections = toolingRequestDao.getToolingInspection("", approvalFlag);
		modelView.addObject("lstToolinginsInspections", lstToolinginsInspections);
		return modelView;
	}

	@RequestMapping(value = "/searchReceivingRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView SearchProduct(@ModelAttribute ToolingRequest tool, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolingReceivingRequestList");
		List<ToolingRequest> lstToolinginsInspections = new ArrayList<ToolingRequest>();
		HttpSession session = request.getSession();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIVING_SCREEN).toString();
		lstToolinginsInspections = toolingRequestDao.getToolingInspection(tool.getToolingSearch(), approvalFlag);
		modelView.addObject("lstToolinginsInspections", lstToolinginsInspections);
		return modelView;
	}

	@RequestMapping(value = "/viewReceivingRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showAddToolingReceiveInspection(@ModelAttribute ToolingRequest tool,
			HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolingReceivingRequest");
		int inspectionId = toolingRequestDao.getIntialValue();
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

	@RequestMapping(value = "/showAddReceivingRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView fetchToolingReceiveInspection(@ModelAttribute ToolingRequest tool, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolingReceivingRequest");

		List<ToolingReceiptNote> lstToolingProduct = new ArrayList<ToolingReceiptNote>();

		ToolingReceiptNote obj = toolingReceiptDao.getToolingReceiptNote(tool.getToolingReceiptId());
		lstToolingProduct = toolingReceiptDao.getReceiptProductDetails(tool.getToolingReceiptId());
		Iterator<ToolingReceiptNote> itr = lstToolingProduct.iterator();
		while (itr.hasNext()) {
			ToolingReceiptNote obj1 = itr.next();
		}

		modelView.addObject("inspectionReportNo", tool.getInspectionReportNo());
		modelView.addObject("inspectionReportDate", sdf.format(cDate));
		modelView.addObject("grnNo", obj.getGrnNo());
		try {
			modelView.addObject("grnDate", sdf.format(sdfDB.parse(obj.getGrnDate())));

			modelView.addObject("poNumber", obj.getPo());
			modelView.addObject("poDate", sdf.format(sdfDB.parse(obj.getGrnDate())));
			modelView.addObject("minQuantity", obj.getMinAcceptedQty());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		modelView.addObject("supplierCode", obj.getSupplierCode());
		modelView.addObject("supplierName", obj.getSupplierName());

		modelView.addObject("count", lstToolingProduct.size());
		modelView.addObject("gridOrgCount", lstToolingProduct.size());
		modelView.addObject("toolingReceiptId", tool.getToolingReceiptId());
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("lstToolingProduct", lstToolingProduct);
		modelView.addObject("msg", "");

		return modelView;
	}

	@RequestMapping(value = "/editReceivingRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView editToolingReceiveInspection(@ModelAttribute ToolingRequest tool, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolingReceivingRequest");

		List<ToolingRequest> lstToolingProduct = new ArrayList<ToolingRequest>();

		ToolingRequest obj = toolingRequestDao.editToolingInspection(tool.getToolingRequestId());
		lstToolingProduct = toolingRequestDao.getToolingInspectionDetails(tool.getToolingRequestId());

		modelView.addObject("inspectionReportNo", obj.getInspectionReportNo());
		modelView.addObject("inspectionReportDate", obj.getInspectionReportDate());
		modelView.addObject("grnNo", obj.getGrnNo());
		try {
			modelView.addObject("grnDate", obj.getGrnDate());

			if (lstToolingProduct.size() > 0) {
				modelView.addObject("poNumber", lstToolingProduct.get(0).getPoNumber());
				modelView.addObject("poDate", lstToolingProduct.get(0).getPoDate());
			} else {
				modelView.addObject("poNumber", "");
				modelView.addObject("poDate", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelView.addObject("supplierCode", obj.getSupplierCode());
		modelView.addObject("supplierName", obj.getSupplierName());

		modelView.addObject("count", lstToolingProduct.size());
		modelView.addObject("gridOrgCount", lstToolingProduct.size());
		modelView.addObject("toolingReceiptId", obj.getToolingReceiptId());
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstToolingProduct", lstToolingProduct);
		modelView.addObject("toolingRequestId", tool.getToolingRequestId());
		modelView.addObject("msg", "");

		return modelView;
	}

	@RequestMapping(value = "/addReceivingRequest", method = RequestMethod.POST)
	public ModelAndView addToolingReceiptNote(@ModelAttribute ToolingRequest toolingInspection,
			HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolingReceivingRequest");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingInspection.setBranchName(branchName);
		String message = toolingRequestDao.addToolingReceivingInspection(toolingInspection, userId);
		// *********Send email*****************************//

		// String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIVING_SCREEN).toString();
		// *********Send email*****************************//
		if ("1".equalsIgnoreCase(approvalFlag)) {
			ReadContent emailContent = new ReadContent();
			Map<String, String> input = new HashMap<String, String>();
			input.put(TiimConstant.TRANSACTION_ID, toolingInspection.getOriginalId() + "");
			input.put(TiimConstant.LOT_NUMBER, TiimUtil.ValidateNull(toolingInspection.getLotNumber()));
			input.put(TiimConstant.PRODUCT_NAME, TiimUtil.ValidateNull(toolingInspection.getToolingname()));
			input.put(TiimConstant.DRAWING_NUMBER, TiimUtil.ValidateNull(toolingInspection.getDrawingNo()));
			input.put(TiimConstant.MACHINE_TYPE, TiimUtil.ValidateNull(toolingInspection.getMachineType()));

			String filePath = messageSource.getMessage("emailTemplate", null, null);
			String subject = messageSource.getMessage("subject", null, null) + " " + TiimConstant.RECEIVING_SCREEN
					+ " Screen";
			String content = emailContent.getEmailContent(filePath, input);
			sendMail.sendMail(TiimConstant.RECEIVING_SCREEN, content, subject);
		}

		int inspectionId = toolingRequestDao.getIntialValue();
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

	@RequestMapping(value = "/updateReceivingRequest", method = RequestMethod.POST)
	public ModelAndView updateToolingRequest(@ModelAttribute ToolingRequest toolingInspection,
			HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolingReceivingRequest");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");
		String branchName = session.getAttribute("sesBranchName").toString();
		toolingInspection.setBranchName(branchName);
		String message = toolingRequestDao.updateToolingInspection(toolingInspection, userId);

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

	@RequestMapping(value = "/deleteReceivingRequest", method = RequestMethod.POST)
	public ModelAndView deleteToolingRequest(@ModelAttribute ToolingRequest toolingInspection,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String message = "";
		int userId = (Integer) session.getAttribute("userid");
		ModelAndView modelView = new ModelAndView("toolingReceivingRequestList");
		List<ToolingRequest> lstToolinginsInspections = new ArrayList<ToolingRequest>();
		System.out.println(toolingInspection.getToolingRequestId());
		if (!toolingInpsectionDao.isIntegratedWithRequest(toolingInspection.getToolingRequestId())) {
			message = toolingRequestDao.deleteReceiptInspection(toolingInspection.getToolingRequestId(), userId);
		} else {
			message = "This request moved to report. Cannot delete";
		}
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIVING_SCREEN).toString();
		lstToolinginsInspections = toolingRequestDao.getToolingInspection("", approvalFlag);

		modelView.addObject("lstToolinginsInspections", lstToolinginsInspections);
		modelView.addObject("message", message);

		return modelView;
	}

	@RequestMapping(value = "/viewReceivingRequestReport", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView viewReceivingRequestReport(@ModelAttribute ToolingRequest tool, HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("viewReceivingRequest");

		List<ToolingRequest> lstToolingProduct = new ArrayList<ToolingRequest>();

		ToolingRequest obj = toolingRequestDao.editToolingInspection(tool.getToolingRequestId());
		lstToolingProduct = toolingRequestDao.getToolingInspectionDetails(tool.getToolingRequestId());

		modelView.addObject("inspectionReportNo", obj.getInspectionReportNo());
		modelView.addObject("inspectionReportDate", obj.getInspectionReportDate());
		modelView.addObject("grnNo", obj.getGrnNo());
		try {
			modelView.addObject("grnDate", obj.getGrnDate());

			if (lstToolingProduct.size() > 0) {
				modelView.addObject("poNumber", lstToolingProduct.get(0).getPoNumber());
				modelView.addObject("poDate", lstToolingProduct.get(0).getPoDate());
			} else {
				modelView.addObject("poNumber", "");
				modelView.addObject("poDate", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelView.addObject("supplierCode", obj.getSupplierCode());
		modelView.addObject("supplierName", obj.getSupplierName());

		modelView.addObject("count", lstToolingProduct.size());
		modelView.addObject("gridOrgCount", lstToolingProduct.size());
		modelView.addObject("toolingReceiptId", obj.getToolingReceiptId());
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("lstToolingProduct", lstToolingProduct);
		modelView.addObject("toolingRequestId", tool.getToolingRequestId());
		modelView.addObject("msg", "");

		return modelView;
	}

	@RequestMapping(value = "/getGRNProductDetail", method = RequestMethod.POST)
	public ModelAndView getGRNProductDetail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIPT_SCREEN).toString();
		ModelAndView modelView = new ModelAndView("listProductReceivingRequest");
		List<ToolingReceiptNote> lstToolingReceiptNote = new ArrayList<ToolingReceiptNote>();
		String drawingNumber = request.getParameter("drawingNumber");
		lstToolingReceiptNote = receiptDao.getGRNProductDetail(drawingNumber, approvalFlag);
		modelView.addObject("lstToolingReceiptNote", lstToolingReceiptNote);
		return modelView;

	}
}

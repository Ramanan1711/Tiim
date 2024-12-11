package com.tiim.master.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.DestructionNoDao;
import com.tiim.dao.ToolSerialNumberDao;
import com.tiim.fileutil.FileMeta;
import com.tiim.model.DestructionNote;
import com.tiim.model.Material;
import com.tiim.model.Product;
import com.tiim.model.ToolSerialNumber;
import com.tiim.service.ProductService;

@Controller
public class DestructionNoteController {

	@Autowired
	DestructionNoDao destructionDao;
	@Autowired
	ToolSerialNumberDao toolSerialNumberDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	java.util.Date cDate = new java.util.Date();

	@RequestMapping(value = "/toolDestructionNoteList", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView toolDestructionNoteList(HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolDestructionNoteList");
		List<DestructionNote> lstDestructionNoteDetail = new ArrayList<DestructionNote>();
		lstDestructionNoteDetail = destructionDao.getDestructionNoteDetails(0);
		modelView.addObject("lstDestructionNoteDetail",
				lstDestructionNoteDetail);
		return modelView;
	}

	@RequestMapping(value = "/toolDestructionNote", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView toolDestructionNote(HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolDestructionNote");
		fillDefaultValue(modelView, "Display");
		modelView.addObject("btnStatusVal", "Save");

		return modelView;
	}

	@RequestMapping(value = "/addDestructionNote", method = RequestMethod.POST)
	public ModelAndView addDestructionNote(
			@ModelAttribute DestructionNote destructionNote,
			MultipartHttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelView = new ModelAndView("toolDestructionNote");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");

		String folderName = "destructionNote";
		FileMeta fileMeta = new FileMeta();
		String imageFolder = request.getSession().getServletContext()
				.getRealPath("uploaddocument");
		System.out.println("path: "
				+ request.getSession().getServletContext()
						.getRealPath("uploaddocument"));
		if (folderName != null)
			imageFolder = imageFolder + "/" + folderName + "/";
		File imageFile = new File(imageFolder);
		if (!imageFile.exists()) {
			imageFile.mkdir();
		}
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		int i = 0;
		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());

			if (mpf.getOriginalFilename() != null
					&& !mpf.getOriginalFilename().equals("")) {
				System.out.println(mpf.getOriginalFilename() + " uploaded! ");
				String documentName = destructionNote.getDestructionNo() + "_"
						+ mpf.getOriginalFilename();
				// 2.3 create new fileMeta
				fileMeta = new FileMeta();
				fileMeta.setFileName(documentName);
				fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
				fileMeta.setFileType(mpf.getContentType());
				destructionNote.setUploadedPath("./uploaddocument/"
						+ folderName + "/" + documentName);
				try {
					fileMeta.setBytes(mpf.getBytes());
					// copy file to local disk (make sure the path
					// "e.g. D:/temp/files" exists)
					FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
							imageFolder + documentName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Exception when copy the file: "
							+ e.getMessage());
					e.printStackTrace();
				}
				// 2.4 add to files
				// files.add(fileMeta);
			}
		}

		String message = destructionDao.addDestructionNo(destructionNote,
				userId);
		List<DestructionNote> lstDestructionNoteDetail = new ArrayList<DestructionNote>();
		lstDestructionNoteDetail = destructionDao.getDestructionNoteDetails(0);
		modelView.addObject("lstDestructionNoteDetail",
				lstDestructionNoteDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Save");
		return modelView;
	}

	@RequestMapping(value = "/updateDestructionNote", method = RequestMethod.POST)
	public ModelAndView updateDestructionNote(
			@ModelAttribute DestructionNote destructionNote,
			MultipartHttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelView = new ModelAndView("toolDestructionNote");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");

		String folderName = "destructionNote";
		FileMeta fileMeta = new FileMeta();
		String imageFolder = request.getSession().getServletContext()
				.getRealPath("uploaddocument");
		System.out.println("path: "
				+ request.getSession().getServletContext()
						.getRealPath("uploaddocument"));
		if (folderName != null)
			imageFolder = imageFolder + "/" + folderName + "/";
		File imageFile = new File(imageFolder);
		if (!imageFile.exists()) {
			imageFile.mkdir();
		}
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		int i = 0;
		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());

			if (mpf.getOriginalFilename() != null
					&& !mpf.getOriginalFilename().equals("")) {
				System.out.println(mpf.getOriginalFilename() + " uploaded! ");
				String documentName = destructionNote.getDestructionNo() + "_"
						+ mpf.getOriginalFilename();
				// 2.3 create new fileMeta
				fileMeta = new FileMeta();
				fileMeta.setFileName(documentName);
				fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
				fileMeta.setFileType(mpf.getContentType());
				destructionNote.setUploadedPath("./uploaddocument/"
						+ folderName + "/" + documentName);
				try {
					fileMeta.setBytes(mpf.getBytes());
					// copy file to local disk (make sure the path
					// "e.g. D:/temp/files" exists)
					FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
							imageFolder + documentName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Exception when copy the file: "
							+ e.getMessage());
					e.printStackTrace();
				}
				// 2.4 add to files
				// files.add(fileMeta);
			}
		}

		String message = destructionDao.updateDestructionNote(destructionNote,
				userId);
		List<DestructionNote> lstDestructionNoteDetail = new ArrayList<DestructionNote>();
		lstDestructionNoteDetail = destructionDao.getDestructionNoteDetails(0);
		modelView.addObject("lstDestructionNoteDetail",
				lstDestructionNoteDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}

	@RequestMapping(value = "/editDestructionNote", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView editDestructionNote(@RequestParam int destructionNo,
			HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolDestructionNote");
		List<DestructionNote> lstDestructionNoteDetail = new ArrayList<DestructionNote>();
		lstDestructionNoteDetail = destructionDao
				.getDestructionNoteDetails(destructionNo);
		for (Iterator iterator = lstDestructionNoteDetail.iterator(); iterator
				.hasNext();) {
			DestructionNote destructionNote = (DestructionNote) iterator.next();
			modelView.addObject("destructionNo",
					destructionNote.getDestructionNo());
			modelView.addObject("destructionDate",
					destructionNote.getDestructionDate());
			modelView.addObject("uploadedPath",
					destructionNote.getUploadedPath());

		}
		modelView.addObject("action", "edit");
		modelView.addObject("message", "");
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		return modelView;
	}

	@RequestMapping(value = "/deleteDestructionNote", method = RequestMethod.POST)
	public ModelAndView deleteDestructionNote(@RequestParam int destructionNo,
			HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("toolDestructionNoteList");
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("userid");
		String message = destructionDao.deleteDestructionNote(destructionNo,
				userId);
		List<DestructionNote> lstDestructionNoteDetail = new ArrayList<DestructionNote>();
		lstDestructionNoteDetail = destructionDao.getDestructionNoteDetails(0);
		modelView.addObject("lstDestructionNoteDetail",
				lstDestructionNoteDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}

	@RequestMapping(value = "/viewDestructionNote", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView viewDestructionNote(@RequestParam int destructionNo,
			HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("viewDestructionNote");
		List<DestructionNote> lstDestructionNoteDetail = new ArrayList<DestructionNote>();
		lstDestructionNoteDetail = destructionDao.getDestructionNoteDetails(destructionNo);
		modelView.addObject("lstDestructionNoteDetail",
				lstDestructionNoteDetail);
		return modelView;
	}

	private void fillDefaultValue(ModelAndView modelView, String action) {
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		int destructionNo = destructionDao.getIntialValue();
		modelView.addObject("destructionNo", destructionNo);
		modelView.addObject("destructionDate", sdf.format(cDate));
	}
	
	
	@RequestMapping(value = "/viewDestructionNoteReportByLotNumber", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView viewDestructionNoteReportByLotNumber(@RequestParam String lotNumber) {
		ModelAndView modelView = new ModelAndView("viewDestructionNoteReport");
		List<ToolSerialNumber> toolSerialNumbers = new ArrayList<ToolSerialNumber>();
		toolSerialNumbers = toolSerialNumberDao.getSerialNumbersByLotNumber(lotNumber);
		modelView.addObject("toolSerialNumbers",
				toolSerialNumbers);
		return modelView;
	}
}

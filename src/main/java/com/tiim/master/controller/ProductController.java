package com.tiim.master.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.PlatingDao;
import com.tiim.fileutil.FileMeta;
import com.tiim.model.Product;
import com.tiim.service.MachineService;
import com.tiim.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	MachineService machineService;
	
	@Autowired
	PlatingDao platingDao;
	
	@RequestMapping(value = "/showProduct", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showProduct(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstProduct");
		List<Product> lstProductDetail = new ArrayList<Product>();
		lstProductDetail = productService.getProductDetails("");
		modelView.addObject("lstProductDetail", lstProductDetail);
		
		fillDefaultValue(modelView, "listdata");
		return modelView;
	}
	
	@RequestMapping(value = "/searchProduct", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showSearchProduct(@ModelAttribute Product product, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstProduct");
		List<Product> lstProductDetail = new ArrayList<Product>();
		lstProductDetail = productService.getProductDetails(product.getSearchProduct());
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("searchProduct", product.getSearchProduct());
		fillDefaultValue(modelView, "list");
		return modelView;
	}
	
	@RequestMapping(value = "/addProduct", method=RequestMethod.POST)
	public ModelAndView addProduct(@ModelAttribute Product product, MultipartHttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelView = new ModelAndView("mstProduct");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		
		 String folderName = "product";
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
				 //2.1 get next MultipartFile
				 mpf = request.getFile(itr.next()); 

				 if(mpf.getOriginalFilename() != null && !mpf.getOriginalFilename().equals(""))
				 {
				// System.out.println(mpf.getOriginalFilename() +" uploaded! ");
				 String documentName = product.getProductName()+"_"+product.getDrawingNo()+"_"+mpf.getOriginalFilename();
				 //2.3 create new fileMeta
				 fileMeta = new FileMeta();
				 fileMeta.setFileName(documentName);
				 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
				 fileMeta.setFileType(mpf.getContentType());
				 product.setUploadedPath("./uploaddocument/"+folderName+"/"+documentName);
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
			 }
		
		String message = productService.addProduct(product, userId);
		List<Product> lstProductDetail = new ArrayList<Product>();
		lstProductDetail = productService.getProductDetails("");
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Add");
		return modelView;
	}
	
	@RequestMapping(value = "/updateProduct", method=RequestMethod.POST)
	public ModelAndView updateProduct(@ModelAttribute Product product, MultipartHttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelView = new ModelAndView("mstProduct");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		
		String folderName = "product";
		   FileMeta fileMeta = new FileMeta();
			String imageFolder = request.getSession().getServletContext().getRealPath("uploaddocument");
			System.out.println("path: "+request.getSession().getServletContext().getRealPath("uploaddocument"));
			if(folderName!=null)
				imageFolder = imageFolder +"/"+ folderName+"/";
			File imageFile = new File(imageFolder);
			if(!imageFile.exists())
			{
				imageFile.mkdir();
			}
			 Iterator<String> itr =  request.getFileNames();
			 MultipartFile mpf = null;
			 //2. get each file
			 while(itr.hasNext()){
				 //2.1 get next MultipartFile
				 mpf = request.getFile(itr.next()); 

				 if(mpf.getOriginalFilename() != null && !mpf.getOriginalFilename().equals(""))
				 {
				 System.out.println(mpf.getOriginalFilename() +" uploaded! ");
				 String documentName = product.getProductName()+"_"+product.getDrawingNo()+"_"+mpf.getOriginalFilename();
				 //2.3 create new fileMeta
				 fileMeta = new FileMeta();
				 fileMeta.setFileName(documentName);
				 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
				 fileMeta.setFileType(mpf.getContentType());
				 product.setUploadedPath("./uploaddocument/"+folderName+"/"+documentName);
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
			 }
		
		String message = productService.updateProduct(product, userId);
		List<Product> lstProductDetail = new ArrayList<Product>();
		lstProductDetail = productService.getProductDetails("");
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Update");
		return modelView;
	}
	
	@RequestMapping(value = "/deleteProduct", method=RequestMethod.POST)
	public ModelAndView deleteProduct(@RequestParam int productId, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstProduct");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String message = productService.deleteProduct(productId, userId);
		List<Product> lstProductDetail = new ArrayList<Product>();
		lstProductDetail = productService.getProductDetails("");
		modelView.addObject("lstProductDetail", lstProductDetail);
		modelView.addObject("message", message);
		fillDefaultValue(modelView, "Delete");
		return modelView;
	}
	
	@RequestMapping(value = "/getProduct", method=RequestMethod.POST)
	public @ResponseBody Product getProduct(@RequestParam int productId, HttpServletRequest request)
	{
		Product product = productService.getProduct(productId);
		return product;
	}
	
	@RequestMapping(value = "/updateProductStatus", method=RequestMethod.POST)
	public @ResponseBody String updateProductStatus(@RequestParam int productId, HttpServletRequest request)
	{
		String message = productService.changeProductStatus(productId);
		return message;
	}
	
	private void fillDefaultValue(ModelAndView modelView, String action)
	{
		modelView.addObject("productId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("action", action);
		modelView.addObject("lstMachineType", machineService.getMachineType());
		modelView.addObject("lstPlating",platingDao.getPlatingDetails());
	}
}

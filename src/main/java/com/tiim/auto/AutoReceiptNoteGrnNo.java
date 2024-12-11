package com.tiim.auto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tiim.dao.ToolingReceiptNoteDao;
import com.tiim.model.Param;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.util.TiimConstant;

@Controller
public class AutoReceiptNoteGrnNo {

	@Autowired
	ToolingReceiptNoteDao toolingReceiptDao;
	
	@RequestMapping(value = "/autoReceiptNoteGrnNo", method = RequestMethod.GET)
	public @ResponseBody String getGrnNo(@RequestParam("grnNo") String grnNo, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIPT_SCREEN).toString();
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(grnNo != null && !"".equals(grnNo))
		{
			List<ToolingReceiptNote> arlList = toolingReceiptDao.getGRNNo(grnNo, 1, approvalFlag);
			Iterator<ToolingReceiptNote> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				ToolingReceiptNote obj = (ToolingReceiptNote)itr.next();
				//System.out.println("obj.getGrnNo(): "+obj.getGrnNo());
				Param p = new Param(obj.getGrnNo()+"" ,obj.getGrnNo()+"",obj.getToolingReceiptId()+"");
				
		        data.add(p);	
			}
		}
		
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/autoDrawingNo", method = RequestMethod.GET)
	public @ResponseBody String autoDrawingNo(@RequestParam("drawingNo") String drawingNo)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(drawingNo != null && !"".equals(drawingNo))
		{
			List<ToolingReceiptNote> arlList = toolingReceiptDao.getDrawingNo(drawingNo, 1);
			Iterator<ToolingReceiptNote> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				ToolingReceiptNote obj = (ToolingReceiptNote)itr.next();
				System.out.println("obj.getGrnNo(): "+obj.getDrawingNo());
				Param p = new Param(obj.getDrawingNo()+"" ,obj.getDrawingNo()+"",obj.getDrawingNo()+"");
				
		        data.add(p);	
			}
		}
		
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/autoReceiptNoteGrnNoReject", method = RequestMethod.GET)
	public @ResponseBody String getGrnNoReject(@RequestParam("grnNo") String grnNo, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIPT_SCREEN).toString();
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(grnNo != null && !"".equals(grnNo))
		{
			List<ToolingReceiptNote> arlList = toolingReceiptDao.getGRNNo(grnNo, 0, approvalFlag);
			Iterator<ToolingReceiptNote> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				ToolingReceiptNote obj = (ToolingReceiptNote)itr.next();
				System.out.println("obj.getGrnNo(): "+obj.getGrnNo());
				Param p = new Param(obj.getGrnNo()+"" ,obj.getGrnNo()+"",obj.getToolingReceiptId()+"");
				
		        data.add(p);	
			}
		}
		
		return gson.toJson(data);
	}
}

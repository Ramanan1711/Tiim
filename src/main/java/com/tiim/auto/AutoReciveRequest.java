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
import com.tiim.dao.ToolingReceivingRequestDao;
import com.tiim.model.Param;
import com.tiim.model.ToolingRequest;
import com.tiim.util.TiimConstant;

@Controller
public class AutoReciveRequest {
	
	@Autowired
	ToolingReceivingRequestDao RequetDao;
	
	@RequestMapping(value = "/autoReciveRequest", method = RequestMethod.GET)
	public @ResponseBody String autoRequestNo(@RequestParam("requestId") String requestId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_RECEIVING_SCREEN).toString();
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(requestId != null && !"".equals(requestId))
		{
			List<ToolingRequest> arlList = RequetDao.autoToolingReceivingRequest(requestId, approvalFlag);
			Iterator<ToolingRequest> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				ToolingRequest obj = (ToolingRequest)itr.next();
				Param p = new Param(obj.getToolingRequestId()+"" ,obj.getToolingRequestId()+"",obj.getToolingRequestId()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/autoReciveRequestDrawingNo", method = RequestMethod.GET)
	public @ResponseBody String autoReciveRequestDrawingNo(@RequestParam("drawingNo") String drawingNo)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(drawingNo != null && !"".equals(drawingNo))
		{
			List<ToolingRequest> arlList = RequetDao.autoToolingReceivingRequestDrawingNo(drawingNo);
			Iterator<ToolingRequest> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				ToolingRequest obj = (ToolingRequest)itr.next();
				Param p = new Param(obj.getDrawingNo()+"" ,obj.getDrawingNo()+"",obj.getDrawingNo()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}

}

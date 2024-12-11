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
import com.tiim.dao.ToolingPeriodicalInpsectionDao;
import com.tiim.model.Param;
import com.tiim.model.ToolingPeriodicInspection;
import com.tiim.util.TiimConstant;

@Controller
public class AutoPeriodicInspectionRequest {
	
	@Autowired
	ToolingPeriodicalInpsectionDao RequetNoteDao;
	
	@RequestMapping(value = "/autoPeriodicRequestNo", method = RequestMethod.GET)
	public @ResponseBody String autoRequestNo(@RequestParam("requestNo") String requestNo, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PERIODIC_REQUEST_SCREEN).toString();
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(requestNo != null && !"".equals(requestNo))
		{
			List<ToolingPeriodicInspection> arlList = RequetNoteDao.getAutoToolingPeriodicalRequestNote(requestNo, approvalFlag);
			Iterator<ToolingPeriodicInspection> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				ToolingPeriodicInspection obj = (ToolingPeriodicInspection)itr.next();
				Param p = new Param(obj.getRequestNo()+"" ,obj.getRequestNo()+"",obj.getRequestNo()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
}

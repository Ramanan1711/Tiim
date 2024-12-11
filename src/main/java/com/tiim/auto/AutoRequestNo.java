package com.tiim.auto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tiim.dao.ToolingRequetNoteDao;
import com.tiim.model.Param;
import com.tiim.model.ToolingRequestNote;

@Controller
public class AutoRequestNo {

	@Autowired
	ToolingRequetNoteDao RequetNoteDao;
	
	@RequestMapping(value = "/autoRequestNo", method = RequestMethod.GET)
	public @ResponseBody String autoRequestNo(@RequestParam("requestNo") String requestNo)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(requestNo != null && !"".equals(requestNo))
		{
			List<ToolingRequestNote> arlList = RequetNoteDao.getAutoToolingRequestNote(requestNo);
			Iterator<ToolingRequestNote> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				ToolingRequestNote obj = (ToolingRequestNote)itr.next();
				Param p = new Param(obj.getRequestId()+"" ,obj.getRequestId()+"",obj.getRequestId()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	/*@RequestMapping(value = "/getProductRequestNo", method = RequestMethod.GET)
	public @ResponseBody String getProductRequestNo()
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(requestNo != null && !"".equals(requestNo))
		{
			List<ToolingRequestNote> arlList = RequetNoteDao.getAutoToolingRequestNote(requestNo);
			Iterator<ToolingRequestNote> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				ToolingRequestNote obj = (ToolingRequestNote)itr.next();
				Param p = new Param(obj.getRequestId()+"" ,obj.getRequestId()+"",obj.getRequestId()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}*/
}

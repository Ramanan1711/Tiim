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
import com.tiim.dao.ToolingIssueDao;
import com.tiim.model.Param;
import com.tiim.model.ToolingIssueNote;
import com.tiim.util.TiimConstant;

@Controller
public class AutoIssueId {
	@Autowired
	ToolingIssueDao issueDao;
	
	@RequestMapping(value = "/autoReturnIssueId", method = RequestMethod.GET)
	public @ResponseBody String getGrnNo(@RequestParam("issueId") String issueId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//String approvalFlag = session.getAttribute("approval").toString();
		String approvalFlag = session.getAttribute(TiimConstant.SES_PRODUCTION_ISSUE_SCREEN).toString();
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(issueId != null)
		{
			List<ToolingIssueNote> arlList = issueDao.getAutoToolingIssueNote(issueId, approvalFlag);
			Iterator<ToolingIssueNote> itr = arlList.iterator();
			while(itr.hasNext())
			{
				ToolingIssueNote obj = itr.next();
				Param p = new Param(obj.getIssueId()+"" ,obj.getIssueId()+"",obj.getIssueId()+"");
				
		        data.add(p);	
			}
		}
		
		return gson.toJson(data);
	}
}

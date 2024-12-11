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
import com.tiim.dao.BranchDao;
import com.tiim.model.Branch;
import com.tiim.model.Param;

@Controller
public class AutoBranchName {

	@Autowired
	BranchDao objDao;

	@RequestMapping(value = "/autoBranchName", method = RequestMethod.GET)
	public @ResponseBody String autoProductName(@RequestParam("branchName") String branchName)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(branchName != null && !"".equals(branchName))
		{
			List<Branch> arlList = objDao.getBranchName(branchName);
			Iterator<Branch> itr = arlList.iterator();
			while(itr.hasNext())
			{
				Branch obj = itr.next();
				Param p = new Param(obj.getBranchName() ,obj.getBranchName(),obj.getBranchId()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
}

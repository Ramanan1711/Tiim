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
import com.tiim.dao.StockDao;
import com.tiim.model.Param;
import com.tiim.model.Stock;

@Controller
public class AutoStockDetails {

	@Autowired
	StockDao stkDao;
	
	@RequestMapping(value = "/autoStock", method = RequestMethod.GET)
	public @ResponseBody String autoStock(@RequestParam("lotNo") String lotNo,HttpServletRequest request)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		HttpSession session = request.getSession();
		String branchName = (String)session.getAttribute("sesBranchName");	
		
		if(lotNo != null)
		{
			List<Stock> arlList = stkDao.getAutoStock(lotNo, branchName);
			Iterator<Stock> itr = arlList.iterator();
			while(itr.hasNext())
			{
				Stock obj = itr.next();
				Param p = new Param(obj.getToolingLotNumber()+"" ,obj.getToolingLotNumber()+"",obj.getStockId()+"");
				
		        data.add(p);	
			}
		}
		
		return gson.toJson(data);
	}
	
}

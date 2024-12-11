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
import com.tiim.dao.StockTransferDao;
import com.tiim.model.Param;
import com.tiim.model.StockTransfer;

@Controller
public class AutoStockTransfer {

	@Autowired
	StockTransferDao stockTransferDao;
	
	@RequestMapping(value = "/autoStockTansfer", method = RequestMethod.GET)
	public @ResponseBody String autoStock(@RequestParam("stockTransferId") String stockTransferId)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(stockTransferId != null)
		{
			List<StockTransfer> arlList = stockTransferDao.getAutoStockTransferDetail(stockTransferId);
			Iterator<StockTransfer> itr = arlList.iterator();
			while(itr.hasNext())
			{
				StockTransfer obj = itr.next();
				Param p = new Param(obj.getStockTransferId()+"",obj.getStockTransferId()+"" ,obj.getStockTransferId()+"");
				
		        data.add(p);	
			}
		}
		
		return gson.toJson(data);
	}
	
}

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
import com.tiim.dao.SupplierDao;
import com.tiim.model.Param;
import com.tiim.model.Supplier;

@Controller
public class AutoSupplierName {

	@Autowired
	SupplierDao supplierDao;
	
	@RequestMapping(value = "/autoSupplierName", method = RequestMethod.GET)
	public @ResponseBody String autoProductName(@RequestParam("supplierName") String supplierName)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(supplierName != null && !"".equals(supplierName))
		{
			List<Supplier> arlList = supplierDao.getSupplierDetails(supplierName);
			Iterator<Supplier> itr = arlList.iterator();
			while(itr.hasNext())
			{
				Supplier obj = itr.next();
				Param p = new Param(obj.getSupplierName() ,obj.getSupplierName(),obj.getSupplierId()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}

}

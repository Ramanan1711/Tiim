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
import com.tiim.dao.EmployeeDAO;
import com.tiim.dao.ProductDao;
import com.tiim.model.Employee;
import com.tiim.model.Param;
import com.tiim.model.Product;

@Controller
public class AutoRequestBy {

	@Autowired
	EmployeeDAO empDAO;
	
	@Autowired
	ProductDao productDao;
	
	@RequestMapping(value = "/autoRequestBy", method = RequestMethod.GET)
	public @ResponseBody String autoRequestBy(@RequestParam("requestBy") String requestBy)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(requestBy != null && !"".equals(requestBy))
		{
			List<Employee> arlList = empDAO.getAutoEmployeeName(requestBy);
			Iterator<Employee> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				Employee obj = (Employee)itr.next();
				Param p = new Param(obj.getEmpName()+"" ,obj.getEmpName()+"",obj.getEmpId()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/autoRequestByDrawingNo", method = RequestMethod.GET)
	public @ResponseBody String autoRequestByDrawingNo(@RequestParam("drawingNumber") String drawingNumber)
	{
		System.out.println("drawingNumber: "+drawingNumber);
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		
		if(drawingNumber != null && !"".equals(drawingNumber))
		{
			List<Product> arlList = productDao.getAutoDrawingNo(drawingNumber);
			Iterator<Product> itr = arlList.iterator();
			
			while(itr.hasNext())
			{
				Product obj = (Product)itr.next();
				Param p = new Param(obj.getDrawingNo()+"" ,obj.getDrawingNo()+"",obj.getDrawingNo()+"");
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
}

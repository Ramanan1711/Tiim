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
import com.tiim.dao.ProductReportDao;
import com.tiim.model.Param;

@Controller
public class AutoMachineType {
	
	@Autowired
	ProductReportDao productReportDao;
	
	@RequestMapping(value = "/autoMachineType", method = RequestMethod.GET)
	public @ResponseBody String autoMachineType(@RequestParam("machineType") String machineType)
	{
		System.out.println("auto machine type...");
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(machineType != null && !"".equals(machineType))
		{
			List<String> arlList = productReportDao.getMachineType(machineType);
			Iterator<String> itr = arlList.iterator();
			while(itr.hasNext())
			{
				String machine = itr.next();
				Param p = new Param(machine ,machine,machine);
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/autoMachineName", method = RequestMethod.GET)
	public @ResponseBody String autoMachineName(@RequestParam("machineName") String machineName)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(machineName != null && !"".equals(machineName))
		{
			List<String> arlList = productReportDao.getMachineName(machineName);
			Iterator<String> itr = arlList.iterator();
			while(itr.hasNext())
			{
				String machine = itr.next();
				Param p = new Param(machine ,machine,machine);
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/autoLotNumber", method = RequestMethod.GET)
	public @ResponseBody String autoLotNumber(@RequestParam("lotNumber") String lotNumber)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(lotNumber != null && !"".equals(lotNumber))
		{
			List<String> arlList = productReportDao.getLotNumber(lotNumber);
			Iterator<String> itr = arlList.iterator();
			while(itr.hasNext())
			{
				String lot = itr.next();
				Param p = new Param(lot ,lot,lot);
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/autoTypeOfTool", method = RequestMethod.GET)
	public @ResponseBody String autoTypeOfTool(@RequestParam("typeOfTool") String typeOfTool)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(typeOfTool != null && !"".equals(typeOfTool))
		{
			List<String> arlList = productReportDao.getTypeOfTool(typeOfTool);
			Iterator<String> itr = arlList.iterator();
			while(itr.hasNext())
			{
				String tool = itr.next();
				Param p = new Param(tool ,tool,tool);
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	@RequestMapping(value = "/autoDrawingNumber", method = RequestMethod.GET)
	public @ResponseBody String autoDrawingNumber(@RequestParam("drawingNumber") String drawingNumber)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(drawingNumber != null && !"".equals(drawingNumber))
		{
			List<String> arlList = productReportDao.getDrawingNumber(drawingNumber);
			Iterator<String> itr = arlList.iterator();
			while(itr.hasNext())
			{
				String tool = itr.next();
				Param p = new Param(tool ,tool,tool);
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}
	
	
	@RequestMapping(value = "/autoGetTypeOfTool", method = RequestMethod.GET)
	public @ResponseBody String autoGetTypeOfTool(@RequestParam("typeOfTool") String typeOfTool)
	{
		Gson gson = new Gson();
		List<Param> data = new ArrayList<Param>();	
		if(typeOfTool != null && !"".equals(typeOfTool))
		{
			List<String> arlList = productReportDao.getTypeOfTool(typeOfTool);
			Iterator<String> itr = arlList.iterator();
			while(itr.hasNext())
			{
				String tool = itr.next();
				Param p = new Param(tool ,tool,tool);
				
		        data.add(p);	
			}
		}
		return gson.toJson(data);
	}

}

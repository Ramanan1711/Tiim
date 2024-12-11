package com.tiim.master.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ToolSerialNumberDao;
import com.tiim.model.ToolSerialNumber;

@Controller
public class SerialNumberController {

	@Autowired
	ToolSerialNumberDao toolSerialNumberDao;
	
	@RequestMapping(value = "/getSerialNumberDetailByLotNumber", method = RequestMethod.POST)
	public ModelAndView getSerialNumberByLotNumber(@RequestParam String lotNumber){
		final List<ToolSerialNumber> lstSerialNumber = toolSerialNumberDao.getSerialNumbersByLotNumber(lotNumber);
		ModelAndView modelView = new ModelAndView("showSerialNumberDetails");
		modelView.addObject("lstSerialNumber",
				lstSerialNumber);
		int totalRejectedQty = 0;
		if(lstSerialNumber != null && !lstSerialNumber.isEmpty())
		{
			totalRejectedQty = lstSerialNumber.get(lstSerialNumber.size()-1).getTotalRejectedQty();
		}
		modelView.addObject("totalRejectedQty",
				totalRejectedQty);
		return modelView;
	}
}

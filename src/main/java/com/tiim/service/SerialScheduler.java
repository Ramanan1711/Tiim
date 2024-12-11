package com.tiim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tiim.dao.AutoSerialNumberDao;
import com.tiim.report.controller.ReadAccessDB;

@Component
public class SerialScheduler {

	@Autowired
	AutoSerialNumberDao serialNumberDao;
	
	@Autowired
	ReadAccessDB accessDb;
	
	public void performService()
	{
		accessDb.readAccessDB();
		serialNumberDao.editToolingInspection();
	}
}

package com.tiim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiim.dao.MachineDao;
import com.tiim.model.Machine;

@Service
public class MachineService {

	@Autowired
	MachineDao machineDao; 
	
	public String addMachine(Machine machine, int userId)
	{
		return machineDao.addMachine(machine, userId);
	}
	
	public String updateMachine(Machine machine, int userId)
	{
		return machineDao.updateMachine(machine, userId);
	}
	
	public String deleteMachine(int machineId, int userId)
	{
		return machineDao.deleteMachine(machineId, userId);
	}
	
	public List<Machine> getMachineDetails(String searchMachine)
	{
		return machineDao.getMachineDetails(searchMachine);
	}
	
	public Machine getMachine(int machineId)
	{
		return machineDao.getMachineDetail(machineId);
	}
	
	public String changeMachineStatus(int machineId)
	{
		return machineDao.changeMachineStatus(machineId);
	}
	
	public List<String> getMachineType()
	{
		List<String> lstMachineType = machineDao.getMachineType();
		return lstMachineType;
	}
}

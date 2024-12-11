package com.tiim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiim.dao.DepartmentDao;
import com.tiim.model.Department;

@Service
public class DepartmentService {
	
	@Autowired
	DepartmentDao departmentDao; 
	
	public String addDepartment(Department department, int userId)
	{
		return departmentDao.addDepartment(department, userId);
	}
	
	public String updateDepartment(Department department, int userId)
	{
		return departmentDao.updateDepartment(department, userId);
	}
	
	public String deleteDepartment(int departmentId, int userId)
	{
		return departmentDao.deleteDepartment(departmentId, userId);
	}
	
	public List<Department> getDepartmentDetails(String searchDepartment)
	{
		return departmentDao.getDepartmentDetails(searchDepartment);
	}
	
	public Department getDepartment(int departmentId)
	{
		return departmentDao.getDepartmentDetail(departmentId);
	}
	
	public String changeDepartmentStatus(int departmentId)
	{
		return departmentDao.changeDepartmentStatus(departmentId);
	}
}

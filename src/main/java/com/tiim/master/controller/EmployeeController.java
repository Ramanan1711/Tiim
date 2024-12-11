package com.tiim.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.EmployeeDAO;
import com.tiim.model.Department;
import com.tiim.model.Employee;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeDAO empDAO;
	
	@RequestMapping(value = "/manageEmployee", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showGrid(@ModelAttribute Employee employee, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstEmployee");
		List<Employee> arlEmployeeList = new ArrayList<Employee>();
		String message = "";
		List<String> lstDesignation = null;
		List<Department> lstDepartment = null;
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		try
		{
			lstDesignation = empDAO.getDesignation();
			lstDepartment = empDAO.getDepartment();
			
			if(employee.getAction() != null)
			{
				if("Save".equals(employee.getAction()))
				{
					message = empDAO.addEmployeeDB(employee, userId);
				}
				else if("Update".equals(employee.getAction()))
				{
					message = empDAO.updateEmployee(employee, userId);
				}
				else if("Delete".equals(employee.getAction()))
				{
					message = empDAO.deleteEmployee(employee.getEmpId(), userId);
				}
				else if("list".equals(employee.getAction()))
				{
					System.out.println("employee.getSearchEmployee(): "+employee.getSearchEmployee());
					arlEmployeeList = empDAO.getEmployeeDetails(employee.getSearchEmployee());	
				}
				else if("listdata".equals(employee.getAction()))
				{
					arlEmployeeList = empDAO.getEmployeeDetails("");					
				}
			}
		}
		catch(Exception ec)
		{
			ec.printStackTrace();
			System.out.println("Exception while managing the employee details in mst_employee table : "+ec.getMessage());
		}
		
		if(!"list".equals(employee.getAction()) && !"listdata".equals(employee.getAction()))
		{
		   arlEmployeeList = empDAO.getEmployeeDetails("");
		}
		
		modelView.addObject("arlEmployeeList", arlEmployeeList);  // For Listing the Employee
		modelView.addObject("message", message);
		modelView.addObject("action", validateNull(employee.getAction()));
		modelView.addObject("empId", 1);
		modelView.addObject("isActive", 1);
		modelView.addObject("designationList",lstDesignation);
		modelView.addObject("departmentList",lstDepartment);
		modelView.addObject("searchEmployee", validateNull(employee.getSearchEmployee()));

		return modelView;
	}
	
	@RequestMapping(value = "/getEmployeeDetails", method = RequestMethod.POST)
	public @ResponseBody String getEmployeeDetails(@RequestParam("empId") int empId)
	{
		String employeeDetail = new String();
		try
		{
			employeeDetail = empDAO.getParticularEmployeeDetails(empId);
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the particular employee details in EmployeeController : "+e.getMessage());
		}
		return employeeDetail;
	}
	
	@RequestMapping(value = "/updateEmployeeStatus", method = RequestMethod.POST)
	public @ResponseBody String updateEmployeeStatus(@RequestParam("empId") int empId)
	{
		String employeeDetail = new String();
		try
		{
			employeeDetail = empDAO.changeEmployeeStatus(empId);
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the particular employee status in EmployeeController : "+e.getMessage());
		}
		return employeeDetail;
	}
	
	private String validateNull(String str)
	{
		if(str == null)
		{
			str = "";
		}
		return str;
	}
	
}

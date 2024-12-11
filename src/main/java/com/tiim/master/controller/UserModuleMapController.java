package com.tiim.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ModuleDao;
import com.tiim.dao.RoleDao;
import com.tiim.dao.RoleModuleMapDao;
import com.tiim.model.Modules;
import com.tiim.model.Role;
import com.tiim.model.RoleVsUser;

@Controller
public class UserModuleMapController {

	@Autowired
	RoleModuleMapDao roleModuleMapDao;
	
	@Autowired
	ModuleDao moduleDao;
	
	@Autowired
	RoleDao roleDao;
	
	@RequestMapping(value = "/showRoleModule", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMachine(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRoleModuleList");
		List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		lstRoleVsUser = roleModuleMapDao.getRoleModuleMap("");
		modelView.addObject("lstRoleVsUser", lstRoleVsUser);
		
		return modelView;
	}
	
	@RequestMapping(value = "/searchRoleModule", method=RequestMethod.POST)
	public ModelAndView showSearchMachine(@ModelAttribute RoleVsUser roleVsUser, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRoleModuleList");
		List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		lstRoleVsUser = roleModuleMapDao.getRoleModuleMap(roleVsUser.getSearchRole());
		modelView.addObject("lstRoleVsUser", lstRoleVsUser);
		modelView.addObject("searchRole", roleVsUser.getSearchRole());

		return modelView;
	}
	
	@RequestMapping(value = "/viewRoleModule", method= RequestMethod.POST)
	public ModelAndView viewRoleModule(HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRoleModule");
		List<Role> lstRole = new ArrayList<Role>();
		List<Modules> lstModule = new ArrayList<Modules>();
		
		lstRole = roleDao.getRoleDetails();
		lstModule = moduleDao.getModuleDetails();
		
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("lstModule", lstModule);
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/fetchRoleModule", method=RequestMethod.POST)
	public ModelAndView fetchRoleModule(@ModelAttribute RoleVsUser roleVsUser,HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRoleModule");
		List<Role> lstRole = new ArrayList<Role>();
		List<Modules> lstModule = new ArrayList<Modules>();
		
		lstRole = roleDao.getRoleDetails();
		lstModule = moduleDao.getModuleDetails();
		
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("lstModule", lstModule);
		
		if(!"select".equals(roleVsUser.getRoleName1()) && roleModuleMapDao.isMappingExists(roleVsUser.getRoleName1()))
		{
			if("select".equals(roleVsUser.getRoleName1()))
			{
				modelView.addObject("btnStatus", "disabled");
				modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
				modelView.addObject("btnStatusVal", "Save");
				modelView.addObject("selectedRole", "");
			}
			else
			{
				modelView.addObject("btnStatus", "");
				modelView.addObject("btnSatusStyle", "btn btnImportant");
				modelView.addObject("btnStatusVal", "Save");
				modelView.addObject("selectedRole", roleVsUser.getRoleName1());
			}
		}
		else
		{
			modelView.addObject("btnStatus", "");
			modelView.addObject("btnSatusStyle", "btn btnImportant");
			modelView.addObject("btnStatusVal", "Update");
			modelView.addObject("selectedRole", roleVsUser.getRoleName1());
			
			List<RoleVsUser> lstRoleVsUser = roleModuleMapDao.getIndividualRoleModuleList(roleVsUser.getRoleName1());
			List<RoleVsUser> lstRestrictedRoleVsUser = roleModuleMapDao.getRestrictedRoleModuleList(roleVsUser.getRoleName1());
			lstRoleVsUser.addAll(lstRestrictedRoleVsUser);
			modelView.addObject("lstRoleVsUser", lstRoleVsUser);
		}
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/addRoleModule", method=RequestMethod.POST)
	public ModelAndView addMachine(@ModelAttribute RoleVsUser roleVsUser, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRoleModule");
		String message = roleModuleMapDao.addRoleModule(roleVsUser);
		//List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		//lstRoleVsUser = roleModuleMapDao.getRoleModuleMap("");
		//modelView.addObject("lstRoleVsUser", lstRoleVsUser);
		
		List<Role> lstRole = new ArrayList<Role>();
		List<Modules> lstModule = new ArrayList<Modules>();
		
		lstRole = roleDao.getRoleDetails();
		lstModule = moduleDao.getModuleDetails();
		
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("lstModule", lstModule);
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);

		return modelView;
	}
	
	@RequestMapping(value = "/updateRoleModule", method=RequestMethod.POST)
	public ModelAndView updateMachine(@ModelAttribute RoleVsUser roleVsUser, HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRoleModule");
		String message = roleModuleMapDao.updateRoleModule(roleVsUser,request.getSession());
		//List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		//lstRoleVsUser = roleModuleMapDao.getRoleModuleMap("");
		//modelView.addObject("lstRoleVsUser", lstRoleVsUser);
		
		List<Role> lstRole = new ArrayList<Role>();
		List<Modules> lstModule = new ArrayList<Modules>();
		
		lstRole = roleDao.getRoleDetails();
		lstModule = moduleDao.getModuleDetails();
		
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("lstModule", lstModule);
		modelView.addObject("btnStatus", "disabled");
		modelView.addObject("btnSatusStyle", "btn btnImportantDisable");
		modelView.addObject("btnStatusVal", "Save");
		modelView.addObject("message", message);

		return modelView;
	}
	
	@RequestMapping(value = "/editRoleModule", method=RequestMethod.POST)
	public ModelAndView editRoleModule(@ModelAttribute RoleVsUser roleVsUser,HttpServletRequest request)
	{
		ModelAndView modelView = new ModelAndView("mstRoleModule");
		List<Role> lstRole = new ArrayList<Role>();
		List<Modules> lstModule = new ArrayList<Modules>();
		
		lstRole = roleDao.getRoleDetails();
		lstModule = moduleDao.getModuleDetails();
		
		modelView.addObject("lstRole", lstRole);
		modelView.addObject("lstModule", lstModule);
		
		modelView.addObject("btnStatus", "");
		modelView.addObject("btnSatusStyle", "btn btnImportant");
		modelView.addObject("btnStatusVal", "Update");
		modelView.addObject("selectedRole", roleVsUser.getRoleName1());
		
		List<RoleVsUser> lstRoleVsUser = roleModuleMapDao.getIndividualRoleModuleList(roleVsUser.getRoleName1());
		List<RoleVsUser> lstRestrictedRoleVsUser = roleModuleMapDao.getRestrictedRoleModuleList(roleVsUser.getRoleName1());
		lstRoleVsUser.addAll(lstRestrictedRoleVsUser);
		System.out.println("add: "+lstRoleVsUser.size());
		modelView.addObject("lstRoleVsUser", lstRoleVsUser);
		//modelView.addObject("lstRestrictedRoleVsUser", lstRoleVsUser);
		modelView.addObject("message", "");
		
		return modelView;
	}
	
	@RequestMapping(value = "/deleteRole", method= RequestMethod.POST)
	public ModelAndView deleteRole(@RequestParam("roleName")  String roleName, HttpServletRequest request)
	{
		System.out.println("roleName: "+roleName);
		ModelAndView modelView = new ModelAndView("mstRoleModuleList");
		//List<Modules> lstScreens = new ArrayList<Modules>();
		//lstScreens = roleModuleMapDao.getScreenName(module.getModuleName());
		String message = roleModuleMapDao.deleteRoleModule(roleName);
		List<RoleVsUser> lstRoleVsUser = new ArrayList<RoleVsUser>();
		lstRoleVsUser = roleModuleMapDao.getRoleModuleMap("");
		modelView.addObject("lstRoleVsUser", lstRoleVsUser);
		modelView.addObject("message", message);
		return modelView;
	}
}

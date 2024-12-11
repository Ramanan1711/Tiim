package com.tiim.master.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tiim.dao.ApprovalConfigDao;
import com.tiim.dao.RoleModuleMapDao;
import com.tiim.dao.ToolingIssueDao;
import com.tiim.fileutil.AppendToFile;
import com.tiim.model.ApprovalScreen;
import com.tiim.model.Login;
import com.tiim.model.RoleVsUser;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.UserDetails;
import com.tiim.service.LoginService;
import com.tiim.service.UserDetailsService;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	RoleModuleMapDao roleModuleMapDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ToolingIssueDao issueDao;
	
	@Autowired
	ApprovalConfigDao approvalDao;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ModelAndView validateUser(@ModelAttribute (value="LOGIN") Login login, BindingResult result, HttpServletRequest request)
	{
		int passwordLock = 0;
		ModelAndView modalView = new ModelAndView("login");
		boolean isValidUser = false;
		isValidUser = loginService.checkLoginCredential(login);
		ObjectError error = new ObjectError("invalid", "Invalid username or password");
		if(login.getPasswordLock() == 1)
		{
			modalView.addObject("errorMessage", "User account got locked");
			return modalView;
		}
		
		Calendar calendar = Calendar.getInstance();
		int passwordExpiryDay = Integer.parseInt(messageSource.getMessage("password.expirydays", null, null));
		calendar.add(Calendar.DATE, -passwordExpiryDay);
	    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    String dateText = df.format(date);
	    HttpSession session = request.getSession();
		if(isValidUser)
		{
			String folderName = request.getSession().getServletContext().getRealPath("uploaddocument");
			folderName = folderName +"/config/";
			AppendToFile.appendFile(folderName,"companyname:pat");
			AppendToFile.appendFile(folderName,"license:32e34rr");
			modalView = new ModelAndView("home");
			session.setAttribute("passwordlock", "0");
			session.setAttribute("username", login.getUsername());
			session.setAttribute("userid", login.getUserId());
			session.setAttribute("password", login.getPassword());
			session.setAttribute("role", login.getRole());
			session.setAttribute("approval", messageSource.getMessage("approvalflag", null, null));
			session.setAttribute("clearance", messageSource.getMessage("clearanceflag", null, null));
			session.setAttribute("indent", messageSource.getMessage("indentFlag", null, null));
			session.setAttribute("serilaFlag", messageSource.getMessage("serilaFlag", null, null));
			
			List<ApprovalScreen> lstApproval = approvalDao.getApprovalScreenConfig();
			Iterator<ApprovalScreen> itr = lstApproval.iterator();
			while(itr.hasNext())
			{
				ApprovalScreen screen = itr.next();
				session.setAttribute(screen.getSessionName(), screen.getApprovalFlag());
			}
			
			session.setAttribute("sesFirstName", login.getFirstName());
			session.setAttribute("sesLastName", login.getLastName());
			session.setAttribute("sesBranchName", login.getBranchName());	
			String originalDate = df.format(login.getPasswordDate());
			List<ToolingIssueNote> lstToolingIssue = issueDao.getToolingIssueNoteDetail(messageSource.getMessage("approvalflag", null, null));
			modalView.addObject("lstToolingIssue", lstToolingIssue);
			if(dateText.equals(originalDate))
			{
				modalView = new ModelAndView("changePassword");
				session = request.getSession();
				String user = (String)session.getAttribute("username");
				modalView.addObject("userName", user);
			}
			if(login.getLoginFirstTime() == 0)
			{
				modalView = new ModelAndView("securityQuestion");
				modalView.addObject("question1", messageSource.getMessage("security.question1", null, null));
				modalView.addObject("question2", messageSource.getMessage("security.question2", null, null));
			}
			userRoleMapping(session, login.getRole());
		}
		else
		{
			if(session.getAttribute("passwordlock")!=null)
			{
				passwordLock = Integer.parseInt(session.getAttribute("passwordlock").toString());
			}
			passwordLock++;
			if(passwordLock == 5)
			{
				loginService.lockPassword(login);
			}
			session.setAttribute("passwordlock", passwordLock);
			result.addError(error);
			modalView.addObject("errorMessage", "Invalid username or password");
		}
		return modalView;
	}
	
	@RequestMapping(value = "/home", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView goHome()
	{
		ModelAndView modalView = new ModelAndView("home");
		List<ToolingIssueNote> lstToolingIssue = issueDao.getToolingIssueNoteDetail(messageSource.getMessage("approvalflag", null, null));
		modalView.addObject("lstToolingIssue", lstToolingIssue);
		return modalView;
	}
	
	@RequestMapping(value = "/loginForm", method=RequestMethod.GET)
	public ModelAndView categoroyForm()
	{
		return new ModelAndView("login","LOGIN",new Login());
	}
	
	@RequestMapping(value = "/changePasswordForm", method=RequestMethod.GET)
	public ModelAndView changePasswordFrom(HttpServletRequest request)
	{
		ModelAndView modalView = new ModelAndView("changePassword");
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute("username");
		modalView.addObject("userName", user);
		
		return modalView;
	}
	
	@RequestMapping(value = "/unLockAccountForm", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView unLockAccountForm(HttpServletRequest request)
	{
		int userId = Integer.parseInt(request.getParameter("userId"));
		UserDetails userDetail = userDetailsService.getUserDetails(userId);
		ModelAndView modalView = new ModelAndView("unLockAccount");
		/*
		 * HttpSession session = request.getSession(); String user =
		 * (String)session.getAttribute("username");
		 */
		modalView.addObject("userName", userDetail.getUserName());
		modalView.addObject("userId",userId);
		
		return modalView;
	}
	
	@RequestMapping(value = "/unLockAccount", method=RequestMethod.POST)
	public ModelAndView unLockAccount(@ModelAttribute Login login,  HttpServletRequest request)
	{
		ModelAndView modalView = new ModelAndView("home");
		String message = loginService.unlockAccount(login);
		return modalView;
	}
	
	@RequestMapping(value = "/changePassword", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView changePassword(@ModelAttribute Login login,  HttpServletRequest request)
	{
		ModelAndView modalView = new ModelAndView("changePassword");
		HttpSession session = request.getSession();
		int userId = (Integer)session.getAttribute("userid");
		String user = (String)session.getAttribute("username");
		
		login.setUserId(userId);
		String message = loginService.changePassword(login);
		modalView.addObject("errorMessage", message);
		modalView.addObject("userName", user);
		//session.invalidate();
		session.setAttribute("password", login.getConfirmNewPassword());
		
		return modalView;
	}
	
	@RequestMapping(value = "/forgotPasswordForm", method=RequestMethod.POST)
	public ModelAndView forgetPasswordFrom()
	{
		ModelAndView modalView = new ModelAndView("forgotPassword");
		modalView.addObject("question1", messageSource.getMessage("security.question1", null, null));
		modalView.addObject("question2", messageSource.getMessage("security.question2", null, null));
		return modalView;
	}
	
	@RequestMapping(value = "/forgotPassword", method=RequestMethod.POST)
	public ModelAndView forgetPassword(@ModelAttribute (value="LOGIN") Login login,  HttpServletRequest request)
	{
		
		String password = loginService.getPassword(login);
		ModelAndView modalView = new ModelAndView("changePassword");
		modalView.addObject("password",password);
		return modalView;
	}
	
	@RequestMapping(value = "/sessionExpire", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView sessionExpire()
	{
		ModelAndView modalView = new ModelAndView("sessionExpire");
		
		return modalView;
	}
	
	private void userRoleMapping(HttpSession session, String role)
	{
		/***********SET Necessary Values to Session*************/
		String module;
		String screen;
		String access;
		
		
		boolean sesMstProduct = false;
		boolean sesMstDepartment = true;
		boolean sesMstEmployee = true;
		boolean sesMstMachine = true;
		boolean sesMstSupplier = false;
		boolean sesMstUserDetail = true;
		boolean sesMstUserMapping = true;
		
		boolean sesSTReceiptNote = false;
		boolean sesSTReceivingRequest = true;
		boolean sesSTReceivingInspection = false;
		boolean sesSTPeriodicInspectionRequest = true;
		boolean sesSTPeriodicInspectionRequestReport = true;
		
		boolean sesProductionRequestNote  = false;
		boolean sesProductionIssueNote  = false;
		boolean sesProductionReturnNote  = true;
		
		boolean sesChangePassword = true;
		
		HashMap<String, RoleVsUser> hmRoleVsUser = roleModuleMapDao.getIndividualRoleModuleMap(role);
		session.setAttribute("RoleVsUser", hmRoleVsUser);
		/*Iterator<RoleVsUser> itr = lstRoleVsUser.iterator();
		while(itr.hasNext())
		{
			RoleVsUser obj = itr.next();
			
			module = TiimUtil.ValidateNull(obj.getModuleName1()).trim();
			screen = TiimUtil.ValidateNull(obj.getScreenName1()).trim();
			access = TiimUtil.ValidateNull(obj.getAccessControl1()).trim();
			
			if("Master".equals(module))
			{
				if("Product".equals(screen) && "1".equals(access))
				{
					sesMstProduct = true;
				}
				else if("Supplier".equals(screen) && "1".equals(access))
				{
					sesMstSupplier = true;
				}
				else if("Department".equals(screen) && "1".equals(access))
				{
					sesMstDepartment = true;
				}
				else if("Employee".equals(screen) && "1".equals(access))
				{
					sesMstEmployee = true;
				}
				else if("Machine".equals(screen) && "1".equals(access))
				{
					sesMstMachine = true;
				}
				else if("User Detail".equals(screen) && "1".equals(access))
				{
					sesMstUserDetail = true;
				}
				else if("User vs role mapping".equals(screen) && "1".equals(access))
				{
					sesMstUserMapping = true;
				}
			}
			else if("Stores".equals(module))
			{
				if("Receipt Note".equals(screen) && "1".equals(access))
				{
					sesSTReceiptNote = true;
				}
				else if("Receiving Request".equals(screen) && "1".equals(access))
				{ 
					sesSTReceivingRequest = true;
				}
				else if("Receiving Inspection".equals(screen) && "1".equals(access))
				{ 
					sesSTReceivingInspection = true;
				}
			}
			else if("Production".equals(module))
			{
				if("Production Request Note".equals(screen) && "1".equals(access))
				{
					sesProductionRequestNote  = true;
				}
				else if("Production Issue Note".equals(screen) && "1".equals(access))
				{
					sesProductionIssueNote = true;
				}
			}
		}*/
		
		session.setAttribute("sesMstProduct", sesMstProduct);
		session.setAttribute("sesMstDepartment", sesMstDepartment);
		session.setAttribute("sesMstEmployee", sesMstEmployee);
		session.setAttribute("sesMstMachine", sesMstMachine);
		session.setAttribute("sesMstSupplier", sesMstSupplier);
		session.setAttribute("sesMstUserDetail", sesMstUserDetail);
		session.setAttribute("sesMstUserMapping", sesMstUserMapping);
		
		session.setAttribute("sesSTReceiptNote", sesSTReceiptNote);
		session.setAttribute("sesSTReceivingRequest", sesSTReceivingRequest);
		session.setAttribute("sesSTReceivingInspection", sesSTReceivingInspection);
		session.setAttribute("sesSTPeriodicInspectionRequest", sesSTPeriodicInspectionRequest);
		session.setAttribute("sesSTPeriodicInspectionRequestReport", sesSTPeriodicInspectionRequestReport);
		
		session.setAttribute("sesProductionRequestNote", sesProductionRequestNote);
		session.setAttribute("sesProductionIssueNote", sesProductionIssueNote);
		session.setAttribute("sesProductionReturnNote", sesProductionReturnNote);
		
		session.setAttribute("sesChangePassword", sesChangePassword);
		/*******************************************************/
	}
}

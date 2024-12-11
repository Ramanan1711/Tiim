<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
session.removeAttribute("username");
session.removeAttribute("password");
session.removeAttribute("role");
session.removeAttribute("sesFirstName");
session.removeAttribute("sesLastName");

session.removeAttribute("sesMstProduct");
session.removeAttribute("sesMstDepartment");
session.removeAttribute("sesMstEmployee");
session.removeAttribute("sesMstMachine");
session.removeAttribute("sesMstSupplier");
session.removeAttribute("sesMstUserDetail");
session.removeAttribute("sesMstUserMapping");

session.removeAttribute("sesSTReceiptNote");
session.removeAttribute("sesSTReceivingInspection");
session.removeAttribute("sesSTPeriodicInspectionRequest");
session.removeAttribute("sesSTPeriodicInspectionRequestReport");

session.removeAttribute("sesProductionRequestNote");
session.removeAttribute("sesProductionIssueNote");
session.removeAttribute("sesProductionReturnNote");

session.removeAttribute("sesChangePassword");

try
{
	session.invalidate();
}
catch(Exception e)
{
	System.out.print("Exception in validating the all sessions in Tiim : "+e.getMessage());
}
response.sendRedirect("../loginForm.jsf");
%>
<%@page import="java.util.*, com.tiim.model.RoleVsUser "%>
<%@ page autoFlush="true" buffer="1094kb"%>
<html>
<head>
<%
String username = (String)session.getAttribute("username");
String password = (String)session.getAttribute("password");
String role = (String)session.getAttribute("role");
String firstName = (String)session.getAttribute("sesFirstName");
String lastName = (String)session.getAttribute("sesLastName");
String clearance = (String)session.getAttribute("clearance");
String indent = (String)session.getAttribute("indent");

if(username == null && password == null && role == null)
{
%>
<jsp:forward page="../sessionExpire.jsf" />
<% 
}
boolean sesMstProduct = (Boolean)session.getAttribute("sesMstProduct");
boolean sesMstDepartment = (Boolean)session.getAttribute("sesMstDepartment");
boolean sesMstEmployee = (Boolean)session.getAttribute("sesMstEmployee");
boolean sesMstMachine = (Boolean)session.getAttribute("sesMstMachine");
boolean sesMstSupplier = (Boolean)session.getAttribute("sesMstSupplier");
boolean sesMstUserDetail = (Boolean)session.getAttribute("sesMstUserDetail");
boolean sesMstUserMapping = (Boolean)session.getAttribute("sesMstUserMapping");

boolean sesSTReceiptNote = (Boolean)session.getAttribute("sesSTReceiptNote");
boolean sesSTReceivingInspection = (Boolean)session.getAttribute("sesSTReceivingInspection");
boolean sesSTPeriodicInspectionRequest = (Boolean)session.getAttribute("sesSTPeriodicInspectionRequest");
boolean sesSTPeriodicInspectionRequestReport = (Boolean)session.getAttribute("sesSTPeriodicInspectionRequestReport");

boolean sesProductionRequestNote = (Boolean)session.getAttribute("sesProductionRequestNote");
boolean sesProductionIssueNote = (Boolean)session.getAttribute("sesProductionIssueNote");
boolean sesProductionReturnNote = (Boolean)session.getAttribute("sesProductionReturnNote");

boolean sesChangePassword = (Boolean)session.getAttribute("sesChangePassword");

HashMap<String, RoleVsUser> hmRoleVsUser = (HashMap<String, RoleVsUser>)session.getAttribute("RoleVsUser");
RoleVsUser roleVsUser = hmRoleVsUser.get("Product");

String showMstProduct = "display:none;";
String showMstDepartment = "display:none;";
String showMstEmployee = "display:none;";
String showMstMachine = "display:none;";
String showMstSupplier = "display:none;";
String showMstPlating = "display:none;";
String showMstUserDetail = "display:none;";
String showUnlockAccount = "display:none;";
String showMstUserMapping = "display:none;";
String showMstBranch = "display:none;";
String showMstRole = "display:none;";
String showMstMaterialType = "display:none;";
String showMstDesignation = "display:none;";

String showSTReceiptNote = "display:none;";
String showSTReceivingInspection = "display:none;";
String showSTPeriodicInspectionRequest = "display:none;";
String showSTPeriodicInspectionRequestReport = "display:none;";

String showProductionRequestNote = "display:none;";
String showProductionIssueNote = "display:none;";
String showProductionReturnNote = "display:none;";

String showChangePassword = "display:none;";

String showProductReport = "display:none;";
String showAuditReport = "display:none;";
String showServiceIntervalReport = "display:none;";
String showProductLifeReport = "display:none;";

String showStockTransferRequest = "display:none;";
String showStockTransferIssue = "display:none;";
String showCleaningSOP = "display:none;";
String showApprovalFlag = "display:none;";
String showLotDestruction = "display:none;";
String showExpiryProduct = "display:none;";
String showRejectedProduct = "display:none;";
String showClearanceReport = "display:none;";
String showAvailStock = "display:none;";
String showStockReport = "display:none;";
String showStockStatus = "display:none;";
String showMaterialStock = "display:none;";
String showToolDestruction = "display:none;";
String showPendingReport = "display:none;";
String showMaterial = "display:none;";
String showIndent = "display:none;";
String showReturnAcknowledge = "display:none;";
String showSupplierReturn = "display:none;";
String showIssueAcknowledge = "display:none;";
String showClearanceNumber = "display:none;";
String showClearanceClose = "display:none;";
String showMaterialReceipt = "display:none;";
String showMaterialIssue = "display:none;";
String showMaterialReturn = "display:none;";


String isShowMaster = "";
String isShowStore = "";
String isShowProduction = "";

Iterator it = hmRoleVsUser.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry pair = (Map.Entry)it.next();
    String key = pair.getKey().toString();
    roleVsUser = (RoleVsUser)pair.getValue(); 
      
    if("Product".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstProduct = "";
    }else if("Supplier".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstSupplier = "";
    }else if("Plating".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstPlating = "";
    }else if("Receipt Note".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showSTReceiptNote= "";
    }else if("Receiving Inspection".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showSTReceivingInspection = "";
    }else if("Production Issue Note".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showProductionIssueNote = "";
    }else if("Department".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstDepartment = "";
    }else if("Employee".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstEmployee = "";
    }else if("Machine".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstMachine = "";
    }else if("User Detail".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstUserDetail = "";
    }
    else if("Unlock Account".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showUnlockAccount = "";
    }
    else if("Designation".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstDesignation = "";
    }
    else if("Branch".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstBranch = "";
    	showMstRole = "";
    	showMstMaterialType = "";
    }
    else if("User Role Map".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMstUserMapping = "";
    }else if("Production Request Note".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showProductionRequestNote = "";
    }else if("Production Return Note".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showProductionReturnNote = "";
    }else if("Receiving Inspection Request".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	
    }else if("Periodic Inspection Request".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showSTPeriodicInspectionRequest = "";
    }else if("Periodic Inspection Request Report".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showSTPeriodicInspectionRequestReport = "";
    }else if("Service Interval Report".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showServiceIntervalReport = "";
    }else if("Product Report".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showProductReport = "";
    }else if("Product Life Report".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showProductLifeReport = "";
    }else if("Audit Trial".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showAuditReport = "";
    }else if("Stock Transfer Request".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showStockTransferRequest = "";
    }else if("Stock Transfer Issue".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showStockTransferIssue = "";
    }else if("Cleaning SOP".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showCleaningSOP = "";
    }else if("Approval Flag".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showApprovalFlag = "";
    }else if("Lot Tool Desctruction".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showLotDestruction = "";
    }else if("Expiry Product".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showExpiryProduct = "";
    }else if("Rejected Product".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showRejectedProduct = "";
    }else if("Clearance Report".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showClearanceReport = "";
    }else if("Available Stock".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showAvailStock = "";
    }else if("Stock Report".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showStockReport = "";
    }else if("Stock Status".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showStockStatus = "";
    }else if("Material Stock".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMaterialStock = "";
    }else if("Tool Desctruction".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showToolDestruction = "";
    }else if("Pending Report".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showPendingReport = "";
    }else if("Material Master".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMaterial = "";
    }else if("Indent Tool".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showIndent = "";
    }else if("Return Acknowledge".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showReturnAcknowledge = "";
    }else if("Supplier Return Note".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showSupplierReturn = "";
    }else if("Issue Acknowledge".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showIssueAcknowledge = "";
    }else if("Clearance Number".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showClearanceNumber = "";
    }else if("Clearance Closing".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showClearanceClose = "";
    }else if("Material Receipt Note".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMaterialReceipt = "";
    }else if("Material Issue".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMaterialIssue = "";
    }
    else if("Material Return".equalsIgnoreCase(key) && "1".equals(roleVsUser.getAccessControl1()))
    {
    	showMaterialReturn = "";
    }
    
    
}

if(sesChangePassword)showChangePassword = "";


%>
<style type="text/css">
.crpmenus
{
	position:fixed;
	top:0;
	left:0;
	width:100%;
	font:13px/27px Arial,sans-serif;
	color:#3366cc;
	height:30px;
	background:#000059;
	z-index:1;
}

.crpmenus a:hover
{
	background-color:#676767;
	color:#CCCCCC;
	font-weight:bold;
}
.crpmenus a
{
	text-decoration:none;
	padding:6px 9px 7px;
	color:#FFFFFF;
	font-weight:bold;
	outline:none;
}
.crpmenus ul
{
	list-style:none;
	margin:0;
	padding:0 0 0 10px;
}
.crpmenus ul li
{
	padding:0;
	float:left;
}
.crpmenus ul li ul li
{
	padding:0;
	float:none;
	margin:0 0 0 0px;
	width:100%;
}
.crpmenus ul li ul
{
	position:absolute;
	border:1px solid #C3D1EC;
	/*box-shadow*/
	-webkit-box-shadow:0 1px 5px #CCCCCC;
	   -moz-box-shadow:0 1px 5px #CCCCCC;
	        box-shadow:0 1px 5px #CCCCCC;
	margin-top:-1px;
	display:none;
	padding:0px 16px 0px 0;
	
}
.active ul
{
	display:block !important;
}
.single ul
{
	display:block !important;
}
.active a
{
	background-color:white;
	border:1px solid #C3D1EC;
	border-bottom:0;
	/*box-shadow*/
	-webkit-box-shadow:0 -1px 5px #CCCCCC;
	   -moz-box-shadow:0 -1px 5px #CCCCCC;
	        box-shadow:0 -1px 5px #CCCCCC;
	display:block;
	height:26px;
	padding:0 8px 0 8px;
	position:relative;
	z-index:1;
	color:#3366CC;
	text-align: left;
}
/*Styling for the link of the current page*/
.current a
{
	background-color:#2D2D2D;
	border-top:2px solid #DD4B39;/*red ribbon at top*/
	border-bottom:0;
	display:block;
	height:25px;
	padding:0 8px 0 8px;
	position:relative;
	z-index:1;
	color:#FFFFFF;
	font-weight:bold;
}
.active a:hover
{
	background-color:white;
	color:#3366CC;
}
.active ul a:hover
{
	background-color:#e4ebf8;
}
.active ul a
{
	border:0 !important;
	/*box-shadow*/
	-webkit-box-shadow:0 0 0 #CCCCCC;
	   -moz-box-shadow:0 0 0 #CCCCCC;
	        box-shadow:0 0 0 #CCCCCC;
	border:0;
	width:100%;
}
.crparrow
{	
	border-color:#C0C0C0 transparent white;
	border-style:solid dashed dashed;
	margin-left:5px;
	position:relative;
	top: -1px;
	display: inline-block;
	border-width: 3px 3px 0;
    padding-top: 1px;
    height: 0;
    line-height: 0;
    width: 0;
}
.mid-line
{
	background-color:#FFF;
	border-top:1px solid #e5e5e5;
	font-size:0;
}

.ui-widget {
    font-family: Verdana,Arial,sans-serif;
    font-size: 12px;
}

</style>
<script>
        $('document').ready(function(){
            $('.crpmenus').fixedcrpmenus();
            
            $(".crpmenus").click(function(e) {
            	  e.stopPropagation(); //stops click event from reaching document
            });
            
            $(document).click(function() {
            	
           		if($('.crpmenus').find('ul li > a').parent().hasClass('active'))
               	{
                	$('.crpmenus').find('ul li > a').parent().removeClass('active');
               	}
            });
            
        });
        
        (function ($) {
        	  $.fn.fixedcrpmenus = function () {
        	    return this.each(function () {
        	      var crpmenus = $(this);
        	      crpmenus.find('ul li > a').bind('click', function () {
        	        //check whether the particular link has a dropdown
        	        if (!$(this).parent().hasClass('single-link') && !$(this).parent().hasClass('current')) {
        	          //hiding drop down crpmenus when it is clicked again
        	          if ($(this).parent().hasClass('active')) {
        	            $(this).parent().removeClass('active');
        	          }
        	          else {
        	            //displaying the drop down crpmenus
        	            $(this).parent().parent().find('.active').removeClass('active');
        	            $(this).parent().addClass('active');
        	          }
        	        }
        	        else {
        	          //hiding the drop down crpmenus when some other link is clicked
        	          $(this).parent().parent().find('.active').removeClass('active');

        	        }
        	      });
        	    });
        	  }
        	})(jQuery);
</script>
<script>
</script>
</head>
<body>
<table  id="CRP" class="crpmenus">
 <tr>
    <td width="82%">
	<ul>
	  <li style="<%=isShowMaster%>">
		<a href="#">Masters<span class="crparrow"></span></a>
			<ul>
				<li style="<%=showMstProduct%>"><a href="./showProduct.jsf">Product</a></li>
				<li style="<%=showMstDepartment%>"><a href="./showDepartment.jsf">Department</a></li>
				<li style="<%=showMstEmployee%>"><a href="./manageEmployee.jsf">Employee</a></li>
				<li style="<%=showMstMachine%>"><a href="./showMachine.jsf">Machine</a></li>
				<li style="<%=showMstBranch%>"><a href="./showBranch.jsf">Branch</a></li>
				<li style="<%=showMstBranch%>"><a href="./showRole.jsf">Role</a></li>
				<li style="<%=showMstDesignation%>"><a href="./showDesignation.jsf">Designation</a></li>
				<li style="<%=showMstSupplier%>"><a href="./showSupplier.jsf">Supplier</a></li>
				<li style="<%=showMstPlating%>"><a href="./showPlating.jsf">Plating</a></li>
				<li style="<%=showMstUserDetail%>"><a href="./showUserDetail.jsf">UserDetail</a></li>
				<li style="<%=showMstProduct%>"><a href="./changePassword.jsf">Change Password</a></li>
				<li style="<%=showMstUserMapping%>"><a href="./showRoleModule.jsf">User vs role mapping</a></li>
				<li style="<%=showApprovalFlag%>"><a href="./showApprovalScreen.jsf">Approval Flag Screen </a></li>
				
				<!--<li style="<%=showCleaningSOP%>"><a href="./mstCleaningSop.jsf">Cleaning SOP</a></li>-->
				<%
					if("1".equalsIgnoreCase(indent))
					{
				%>
				
				<%
					}
				%>
				
				
				<%
					if("1".equalsIgnoreCase(clearance))
					{
				%>
				
				<%
					}				
				%>
				

				<li style="<%=showMstUserMapping%>"><a href="showUserApproverMap.jsf">Screen Approver Map</a></li> 
				<li style="<%=showMaterial%>"><a href="showMaterialType.jsf">Material Type Master</a></li>
				<li style="<%=showMaterial%>"><a href="mstMaterial.jsf">Material Master</a></li>
				</ul>				
	  </li>
	  <li style="<%=isShowStore%>">
		<a href="#">Stores<span class="crparrow"></span></a>
			<ul>
				<li style="<%=showIndent%>"><a href="./showIndent.jsf">Tooling Information</a></li>
				<li style="<%=showSTReceiptNote%>"><a href="./showToolingReceiptNote.jsf">Receipt Note</a></li>
				<li style="<%=showSTReceivingInspection%>"><a href="./showReceivingRequest.jsf">New Tool Inspection Request</a></li>
				<li style="<%=showSTReceivingInspection%>"><a href="./showToolingReceiveInspection.jsf">New Tool Inspection Report</a></li>
				<li style="<%=showProductionIssueNote%>"><a href="./showToolingIssueNote.jsf">User Tool Issue</a></li>
				<li style="<%=showReturnAcknowledge%>"><a href="./returnAcknowledge.jsf">Return Acknowledge</a></li>
				<li style="<%=showSTPeriodicInspectionRequest%>"><a href="./showToolingPeriodicInspection.jsf">Periodic Inspection Request</a></li>
				<li style="<%=showSTPeriodicInspectionRequestReport%>"><a href="./showToolingPeriodicInspectionReport.jsf">Periodic Inspection Report</a></li>
				<%-- <li style="<%=showStockTransferRequest%>"><a href="./showStockTransfer.jsf">Stock Transfer Request</a></li>
				<li style="<%=showStockTransferIssue%>"><a href="./showStockTransferIssue.jsf">Stock Transfer Issue</a></li> --%>
				<!-- <li style=""><a href="./showPurchaseIndent.jsf">Purchase Indent Confirmation</a></li> -->
				<li style="<%=showSupplierReturn%>"><a href="./showSupplierReturnNoteList.jsf">Supplier Return Note</a></li>
			</ul>		  
	  </li>
	  <li style="<%=isShowProduction%>">
		 <a href="#">User<span class="crparrow"></span></a>

	 <ul>
		 		<li style="<%=showProductionRequestNote%>"><a href="./showToolingRequest.jsf">User Tool Request</a></li>
				<li style="<%=showIssueAcknowledge%>"><a href="./issueAcknowledge.jsf">Issue Acknowledge</a></li>
				
				<li style="<%=showProductionReturnNote%>"><a href="./showToolingReturn.jsf">User Tool Return</a></li>
				<li style="<%=showClearanceNumber%>"><a href="./showClearanceList.jsf">Clearance Number</a></li>
				<li style="<%=showClearanceClose%>"><a href="./clearanceClosingList.jsf">Clearance Closing</a></li>
		 </ul>
	  </li>
	 <%--  <li style="<%=isShowStore%>">
		<a href="#">Tool Stores<span class="crparrow"></span></a>
			<ul>
				
				<li style="<%=showMaterialReceipt%>"><a href="./showMaterialReceiptLst.jsf">Material Receipt Note</a></li>
				<li style="<%=showMaterialIssue%>"><a href="./showMaterialIssueLst.jsf">Material Issue</a></li>
			   <li style="<%=showMaterialReturn%>"><a href="./showMaterialReturnLst.jsf">Material Return</a></li>
			</ul>		  
	  </li> --%>
	  <!-- <li class="single-link">
		 <a href="#">User</a>
	  </li> -->
	  <li >
		 <a href="#">Reports<span class="crparrow"></span></a>
		 <ul>
		 		<%-- <li style="<%=showProductReport%>"><a href="./createProductReport.jsf">Product Report</a></li> --%>
		 		<li style="<%=showProductReport%>"><a href="./viewProductDetails.jsf">Product Report (Master)</a></li>
		 		<li style="<%=showAuditReport%>"><a href="./showAuditTrialReport.jsf">Audit trial Report</a></li>
		 		<li style="<%=showAuditReport%>"><a href="./showTransactionHistoryReport.jsf">Transaction History Report</a></li>
		 		<li style="<%=showServiceIntervalReport%>"><a href="toolLotStockReport.jsf">Tooling Lot Stock</a></li>
		 		<li style="<%=showServiceIntervalReport%>"><a href="toolLotBatchNo.jsf">Tooling Batch No</a></li>
		 		<li style="<%=showServiceIntervalReport%>"><a href="./createProductIntervalReport.jsf">Inspection Due Report</a></li>
		 		<%-- <li style="<%=showServiceIntervalReport%>"><a href="./toolLotStockReport.jsf">Tooling Lot Tracker</a></li> --%>
		 		<%-- <li style="<%=showProductLifeReport%>"><a href="./createProductLifeReport.jsf">Tooling Life Status Report</a></li> --%>
		 		<%-- <li style="<%=showProductLifeReport%>"><a href="./viewProductReportFiles.jsf">Inspection data Report</a></li> --%>
		 		<li style="<%=showClearanceReport%>"><a href="./createClearanceReport.jsf">Clearance Report</a></li>
		 		
		 		<%-- <li style="<%=showAvailStock%>"><a href="./getAvailableStockReport.jsf">Available Stock Report</a></li> --%>
		 		<%-- <li style="<%=showStockReport%>"><a href="./createStockReport.jsf">Stock Report</a></li> --%>
		 		<%-- <li style="<%=showStockStatus%>"><a href="./createStockStatus.jsf">Stock Status Report</a></li> --%>
		 		
		 		<%-- <li style="<%=showExpiryProduct%>"><a href="./viewExpiryProductReport.jsf">Expiry Product Report</a></li>
 --%>		 		<%-- <li style="<%=showRejectedProduct%>"><a href="./viewRejectedProductReport.jsf">Rejected Product Report</a></li>
		 		<li style="<%=showCleaningSOP%>"><a href="./createClearingSOPReport.jsf">Cleaning SOP Report</a></li>
		 		 --%>
		 		
		 		<%-- <li style="<%=showMaterialStock%>"><a href="./createMaterialStockReport.jsf">Material Stock Report</a></li> --%>
				<li style="<%=showToolDestruction%>"><a href="./viewDestructionNoteReport.jsf">Tool Destruction Note</a></li>
				<li style="<%=showToolDestruction%>"><a href="./showDestructionNoteDetail.jsf">Show Tool Destruction Note</a></li>
				<li style="<%=showLotDestruction%>"><a href="./lotToolDestructionList.jsf">Lot Tool Destruction</a></li>
				<%-- <li style="<%=showMstUserMapping%>"><a href="./updateAutoSerialNumber.jsf">Auto Serial Number</a></li> --%>
		 </ul>
	  </li>
	 <%--  <li>
		 <a href="#">Pending Reports<span class="crparrow"></span></a>
		<ul>
				<li style="<%=showPendingReport%>"><a href="screenApproval.jsf">Approvals</a></li>
				<li style="<%=showPendingReport%>"><a href="./showPendingIndent.jsf">Indent Pending Report</a></li>
				<li style="<%=showPendingReport%>"><a href="./viewProductReturnPendingReport.jsf">Tooling Product Pending Return Report</a></li>
				<li style="<%=showPendingReport%>"><a href="./getReceiptPendingReport.jsf">Inspection Request Pending Report</a></li>
		 		<li style="<%=showPendingReport%>"><a href="./showPendingReceivingRequest.jsf">Pending Inspection Report</a></li>
		 		<li style="<%=showPendingReport%>"><a href="./showPendingToolingRequest.jsf">Pending Indents from Production Report</a></li>
		 		<li style="<%=showPendingReport%>"><a href="./showPendingToolingIssueNote.jsf">Pending Returns from Production Report</a></li>
		 		<li style="<%=showPendingReport%>"><a href="./showPendingPeriodicInspection.jsf">Pending Periodic Inspection Request</a></li>	




		</ul>
	  </li> --%>
	  <li class="single-link">
		 <a href="#">Help</a>
	  </li>
	  <!-- <li class="single-link">
		 <a href="./home.jsf">Home</a>
	  </li> 
	  <li style="showChangePassword" class="single-link">
		 <a href="./changePasswordForm.jsf">Change Password</a>
	  </li>
	  <li class="single-link">
		 <a href="./pages/logout.jsp">Log Out</a>
	  </li>-->
	  <li class="single-link" style="width:13%">&nbsp;
	  </li>
	  <li class="single-link">
		 <a href="./home.jsf">TIIM 100 -  &nbsp;${sesBranchName}</a>
	  </li>
	</ul>
	</td> 
	<td width="18%" align="right" style="text-align:right;">	  
		 <ul><li>
			 <a href="#"><%=firstName%>&nbsp;&nbsp;&nbsp;<span class="crparrow"></span></a><!-- &nbsp;&nbsp;&nbsp;&nbsp; -->	
			 <ul>
		 		 <li style="<%=showChangePassword%>" class="single-link"><a href="./changePasswordForm.jsf">Change Password</a></li>
				 <li><a href="./pages/logout.jsp">Log Out</a></li>
			 </ul>
		 </li>	</ul>  
	</td>

   </tr>
</table>	
<table cellspacing="0"width="100%"  cellpadding="5" border="0" align="center" >		      
		<tr height="20px">  
			<td></td> 		            
			<td></td>
		</tr>
</table> 
</body>
</html>
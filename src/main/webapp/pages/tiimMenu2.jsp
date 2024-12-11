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
String approvalFlag = (String)session.getAttribute("approval");
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
String showMstUserDetail = "display:none;";
String showUnlockAccount = "display:none;";
String showMstUserMapping = "display:none;";
String showMstBranch = "display:none;";
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
				<li style="<%=showMstDesignation%>"><a href="./showDesignation.jsf">Designation</a></li>
				<li style="<%=showMstSupplier%>"><a href="./showSupplier.jsf">Supplier</a></li>
				<li style="<%=showMstUserDetail%>"><a href="./showUserDetail.jsf">UserDetail</a></li>
				<li style="<%=showUnlockAccount%>"><a href="./unLockAccountForm.jsf">Unlock Account</a></li>
				<li style="<%=showMstUserMapping%>"><a href="./showRoleModule.jsf">User vs role mapping</a></li>
				<%
					if("1".equalsIgnoreCase(approvalFlag))
					{
				%>
				 	<li style="<%=showMstUserMapping%>"><a href="showUserApproverMap.jsf">Screen Approver Map</a></li> 
					<li style="<%=showMstUserMapping%>"><a href="screenApproval.jsf">Approvals</a></li>
				<%
					}
				%>
				</ul>				
	  </li>
	  <li style="<%=isShowStore%>">
		<a href="#">Tool Stores<span class="crparrow"></span></a>
			<ul>
				<li style="<%=showSTReceiptNote%>"><a href="./showToolingReceiptNote.jsf">Receipt Note</a></li>
				<li style="<%=showSTReceivingInspection%>"><a href="./showReceivingRequest.jsf">New Tool Inspection Request</a></li>
				<li style="<%=showSTReceivingInspection%>"><a href="./showToolingReceiveInspection.jsf">New Tool Inspection Report</a></li>
				<li style="<%=showProductionIssueNote%>"><a href="./showToolingIssueNote.jsf">Production Tool Issue</a></li>
				<li style="<%=showMstUserMapping%>"><a href="./issueAcknowledge.jsf">Tool Return Acknowledgement</a></li>
				<li style="<%=showSTPeriodicInspectionRequest%>"><a href="./showToolingPeriodicInspection.jsf">Periodic Inspection Request</a></li>
				<li style="<%=showSTPeriodicInspectionRequestReport%>"><a href="./showToolingPeriodicInspectionReport.jsf">Periodic Inspection Report</a></li>
				<li style="<%=showStockTransferRequest%>"><a href="./showStockTransfer.jsf">Stock Transfer Request</a></li>
				<li style="<%=showStockTransferIssue%>"><a href="./showStockTransferIssue.jsf">Stock Transfer Issue</a></li>
				<!-- <li style=""><a href="./showPurchaseIndent.jsf">Purchase Indent Confirmation</a></li> -->
				<li style=""><a href="./showSupplierReturnNoteList.jsf">Supplier Return Note</a></li>
			</ul>		  
	  </li>
	  <li style="<%=isShowProduction%>">
		 <a href="#">User<span class="crparrow"></span></a>
		 <ul>
		 		<li style="<%=showProductionRequestNote%>"><a href="./showToolingRequest.jsf">Production Tool Request</a></li>
				
				<%-- <li style="<%=showMstUserMapping%>"><a href="./issueAcknowledge.jsf">Tool Issue Acknowledgement</a></li> --%>
				<li style="<%=showProductionReturnNote%>"><a href="./showToolingReturn.jsf">Production Tool Return</a></li>

				<%-- <li style="<%=showMstUserMapping%>"><a href="./toolDestructionNoteList.jsf">Tool Destruction Note</a></li>
				<li style="<%=showMstUserMapping%>"><a href="./lotToolDestructionList.jsf">Lot Wise Tool Destruction</a></li> --%>
                    		 </ul>
	  </li>
	  <!-- <li class="single-link">
		 <a href="#">User</a>
	  </li> -->
	  <li >
		 <a href="#">Reports<span class="crparrow"></span></a>
		 <ul>
		 		<li style="<%=showProductReport%>"><a href="./createProductReport.jsf">Product Report</a></li>
		 		<li style="<%=showAuditReport%>"><a href="./showTransactionHistoryReport.jsf">Audit trial Report</a></li>
		 		<li style="<%=showServiceIntervalReport%>"><a href="./createProductIntervalReport.jsf">Tooling Inspection due Report</a></li>
		 		<li style="<%=showProductLifeReport%>"><a href="./createProductLifeReport.jsf">Tooling Life Status Report</a></li>
		 		<li style="<%=showProductLifeReport%>"><a href="./viewProductReportFiles.jsf">Inspection data Report</a></li>
		 		<li style="<%=showProductLifeReport%>"><a href="./createStockReport.jsf">Stock Report</a></li>
		 		<li style="<%=showProductLifeReport%>"><a href="./createStockStatus.jsf">Stock Status Report</a></li>
		 		
		 		<li style="<%=showProductLifeReport%>"><a href="./viewExpiryProductReport.jsf">Expirty Product Report</a></li>
		 		<li style="<%=showProductLifeReport%>"><a href="./viewRejectedProductReport.jsf">Rejected Product Report</a></li>
		 		
				
		 		
		 </ul>
	  </li>
	  <li >
		 <a href="#">Pending Reports<span class="crparrow"></span></a>
		 <ul>
				<li style="<%=showProductLifeReport%>"><a href="./getReceiptPendingReport.jsf">Inspection Request Pending Report</a></li>
				<li style="<%=showProductLifeReport%>"><a href="./viewProductReturnPendingReport.jsf">Tooling Product Pending Return Report</a></li>
		 		<li style="<%=showProductLifeReport%>"><a href="./showPendingReceivingRequest.jsf">Pending Inspection Report</a></li>
		 		<li style="<%=showProductLifeReport%>"><a href="./showPendingToolingIssueNote.jsf">Pending Returns from Production Report</a></li>
		 		<li style="<%=showProductLifeReport%>"><a href="./showPendingPeriodicInspection.jsf">Pending Periodic Inspection Request</a></li>		
		 </ul>
	  </li>
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
	  <li class="single-link" style="width:25%">&nbsp;
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
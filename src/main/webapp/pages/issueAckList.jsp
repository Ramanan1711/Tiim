<%@page import="java.util.HashMap, com.tiim.model.RoleVsUser "%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String addAccess = "";
String editAccess = "";
String deleteAccess = "";
String viewAccess = "";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<title>Tiim</title>
<meta http-equiv="X-UA-Compatible" content="IE=9"/> 

<script type="text/javascript" src="./auto/jquery-1.7.2.js"></script>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="./js/jquery.fixheadertable.js"></script>
<link rel="stylesheet" type="text/css" href="./css/base.css" />
<link rel="stylesheet" type="text/css" href="./css/jquery-ui-1.8.4.custom.css" />

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>
<style>
.pagination 
{  
  display:none;
}
</style>
<script>
$("#techAlert").empty();
$("#empCode").val("");  

   $(document).ready(function ()
    {	  	
		   $('.tableHeading').fixheadertable({
	       	   caption     : '', 
	           colratio    : [100, 230, 230, 160, 155, 100], 
	           height      : 350, 
	           width       : 970,             
	           sortable    : true, 
	           sortedColId : 0,    
	           unSortColumn: ['Request No'],     
	           resizeCol   : true,            
	           minColWidth : 50,
	           whiteSpace	 : 'nowrap',
	           pager		 : true,
			   rowsPerPage	 : 10,
	           sortType    : ['integer', 'string', 'string', 'string','String']          
	      });  
		   $("body .ui-widget-content").css("overflow-x","hidden"); 
	                 
	
   });

   function showDeleteDialog()
   {
      $("#overlay").show();
      $("#delete").fadeIn(300);    
      $("#overlay").unbind("click");           
   }

   function hideDeleteDialog()
   {
      $("#overlay").hide();
      $("#delete").fadeOut(300);
   } 
   
   function showmsg()
   {	   
	   $("#msg").fadeIn(300);	   
   }

  
    
   /* function fundelete(originalId, delid)          
   
   function viewReport(id)          
   {   
	   $("#requestNo").val(id); 
	   window.open('viewPeriodicInspectionRequestReport.jsf?requestNo='+id, 'window', 'width=700,height=750'); 
   } */
   

   function removemsg()                    
   {
	    $("#dynmsg").empty();
		$("#msg").fadeOut(300);
		$("#listMsg").fadeOut(300);
   }

   function trim(stringTrim)
   {
	  	return stringTrim.replace(/^\s+|\s+$/g,"");
   }

   function init()
   {
	  $("#loadoverlay").fadeOut();  
	  $("#initialloading").fadeOut(); 
	 
	  var msg = $("#message").val();
		if(msg != "")  
		{    
			$("#msg").fadeIn(300);	   
	    }
   }
   function showAllListDialog()
   {
      $("#overlay").show();
      $("#allListConfirm").fadeIn(300);  
      $("#btnProcessyes").focus();
      $("#overlay").unbind("click");           
   }

   function hideAllListDialog()
   {
      $("#overlay").hide();
      $("#allListConfirm").fadeOut(300);
   } 
   
   function funAllList()
   {	
	   var searchVal = trim($("#searchScreenName").val());   
	   if(searchVal == "")
  	   {   	
		   showAllListDialog();
  	   }
	   else
 	   {   
   	       $("#action").val("list");  	
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();	 			                    	                  
 	 	   $("#frnIssueAcknowledge").attr("action","issueAcknowledge.jsf");   	                
 	       $("#frnIssueAcknowledge").submit();
  	   }	  
   }
   function addAcknowledged()
   {  		    	
	        
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();   	 			                    	                  
 	 	   $("#frnIssueAcknowledge").attr("action","addIssueAcknowledged.jsf");   	                
 	       $("#frnIssueAcknowledge").submit();	      	 	
   }   	
  
   function checkApproval()
   {
	  // $('.myCheckbox').prop('checked', true);
	  $('input:checkbox[id*=acknowledge]').attr('checked', true);
   }
   
   function redirectPage(screenUrl)
   {
	   window.open(screenUrl, 'window', 'width=700,height=750');
   }
   
</script>

</head>
<body class="body" onload="init();">
<form name="frnIssueAcknowledge" id="frnIssueAcknowledge" method="post" autocomplete="off">
 <%@ include file="tiimMenu.jsp"%>
 <%
	HashMap<String, RoleVsUser> hmRoleVsUser1 = (HashMap<String, RoleVsUser>)session.getAttribute("RoleVsUser");
	RoleVsUser roleVsUser1 = hmRoleVsUser1.get("Periodic Inspection Request");
	if(roleVsUser1 != null)
	{
	 addAccess = roleVsUser1.getAddAccess1();
	 editAccess = roleVsUser1.getEditAccess1();
	 deleteAccess = roleVsUser1.getDeleteAccess1();
	 viewAccess = roleVsUser1.getViewAccess1();
	}
%>
 <div id="loadoverlay" class="loading_overlay" ></div>
 <div id="initialloading" style=" position: absolute; z-index: 700;top:105px; left: 405px; width: 450px; height: 50px; background-color:#ffffff;">
          <img  src="./images/loadmain.gif"></img>
 </div>
 <div id="processloading" style=" position: absolute; z-index: 700;top:105px; left: 405px;width: 450px; height: 50px; background-color:#ffffff;display:none;">
          <img  src="./images/Processing.gif"></img>
 </div>
 
  <div id="overlay" class="web_dialog_overlay"></div>
    
     <div id="delete" class="web_dialog_delete">    
     <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="80%" align="left" class="popuptoptitlelarge">Delete</td>			        			       
				        <td width="18%" align="right" class="popuptoptitle"><a href="#" title="Click to Close" class="popupanchor" id="btndelclose">X</a>&nbsp;</td>
				     </tr>	
				     <tr><td colspan="2"></td></tr>
			    </table>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">				     
				     <tr style="height:20px"></tr>				     
				     <tr><td  class="popuplabel" align="center">Are you sure to Delete?</td></tr>	
				     <tr style="height:20px"></tr>		     			
				     <tr>					               
				        <td class="formlabelblack" align="center" >				       
				        <input  type="button" class="btn btnSMNormal" id="btndelyes" value="Yes" />&nbsp;
				        <input type="button" id="btndelno"  class="btn btnSMNormal"  value="No" />
				        </td>	        			       	       
				     </tr>					     			     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div>
	
	<div id="allListConfirm" class="web_dialog_delete">    
     <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="80%" align="left" class="popuptoptitlelarge">Confirm</td>			        			       
				        <td width="18%" align="right" class="popuptoptitle"><a href="#" title="Click to Close" class="popupanchor" id="btnProcessclose">X</a>&nbsp;</td>
				     </tr>	
				     <tr><td colspan="2"></td></tr>
			    </table>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">				     
				     <tr style="height:20px"><td></td></tr>				     
				     <tr><td  class="popuplabel" align="center">Are you sure to list all data?</td></tr>	
				     <tr style="height:20px"><td></td></tr>		     			
				     <tr>					               
				        <td class="formlabelblack" align="center" >				       
				        <input  type="button" class="btn btnSMNormal" id="btnProcessyes" value="Yes" />&nbsp;
				        <input type="button" id="btnProcessno"  class="btn btnSMNormal"  value="No" />
				        </td>	        			       	       
				     </tr>					     			     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div>
	
	<table cellspacing="0" width="100%"  cellpadding="5" border="0" align="center"  style="height:10px">		      
		<tr >  
			<td></td> 		            
			<td></td>
		</tr>
	</table>
	 <table width="1000px" cellspacing="0" cellpadding="5" border="0" align="center"  style="height:25px">		      
	     <tr>  
	          <td width="35%" class="heading" align="left">&nbsp;Issue Acknowledgement</td> 
	          <td width="50%" class="submenutitlesmall" align="left">
	               <table cellspacing="1" cellpadding="2" width="50%" align="left" style="display:none" id="msg">	
				     	<tr>  
					    	<td class="confirmationmessage" align="center">&nbsp;<span id="dynmsg">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="popupanchor" id="msgclose">X</a></td>
					    </tr>
			       </table>
	          </td>
	          <td align="right" width="15%" class="anchorlabel">
			  &nbsp;
			 
			  &nbsp;			  
			  </td>                             
	     </tr>
	 </table>
       
	<center>           
		<div style=" width: 1000px; padding:0px; margin: 0px;height:425;min-height:420px;">       

		 <c:choose>
			<c:when test="${fn:length(lstIssue) gt 0}">
				<table cellpadding="0" cellspacing ="0" border="0" align="right"  width="100%" >
					 <tr>
						 <td height="5px" align="right">&nbsp;</td>		
					 </tr>
			    </table>
		        <table cellpadding="0" cellspacing ="0" border="0" align="right"  width="100%" >
					 <tr>
						 <td height="5px" align="right">&nbsp;<input type="button" onclick="addAcknowledged()" value="Approve" class="btn btnSMImportant"/>&nbsp;&nbsp;&nbsp;
						 <input type="button" onclick="checkApproval()" value="Select All" class="btn btnSMImportant"/>&nbsp;&nbsp;&nbsp;</td>		
					 </tr>
					 <tr>
						 <td height="5px" align="right">&nbsp;</td>		
					 </tr>
			    </table>
		                                
			<table id="lstFieldTech" name="lstFieldTech" class="tableHeading" align="center">
			      
			     <thead>
			          <tr>
				          <th width="10%">Id</th>
				          <th width="20%">Product Name</th>
				          <th width="20">Machine Type</th>
				          <th width="30%">Drawing No</th>
				          <th width="15%">Lot Number</th>
				          <th width="15%">Acknowledge</th>
			          </tr>  
			     </thead> 
			     <tbody>
			          <c:forEach items="${lstIssue}" var="lstIssue" varStatus="row">
			         	  
				          <tr>
				             <td>${lstIssue.originalId}</td> 
				             <td>${lstIssue.productName1}</td>   
				             <td>${lstIssue.machineName1}</td>
				             <td>${lstIssue.drawingNo1}</td>
				             <td>${lstIssue.toolingLotNumber1}</td>
				             <td align="center" style="text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				             	<input type="checkbox" name="acknowledgeId" id="acknowledge${row.index}" value="${lstIssue.originalId}" />
				             		             					
				             </td>
				         </tr>
				       
				         </c:forEach>

			     </tbody>       
			</table>  
			
			 </c:when>
			<c:otherwise>
					<center>
					    <br>
					    <br>
	                    <div id="listMsg" class="searchListMsg">Data not found</div>
	                </center>
			</c:otherwise>
			
		 </c:choose>       
		</div>
		<table  cellspacing="0" cellpadding="5" style=" width: 1000px;" border="0" align="center" class="legendtable">		      	      
			<tr>
			    <td align="right" class="legend">
				<img border="0" style="" src="./images/pencil.png"/>&nbsp;-&nbsp;Edit&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon.gif"/>&nbsp;-&nbsp;Delete&nbsp;</td>     
			</tr>                         	       
	   </table> 
	</center>    
   

<input type="hidden" name="action" id="action" value="${action}"/>
<input type="hidden" name="requestNo" id="requestNo" value="${requestNo}"/>
<input type="hidden" name="isActive" id="isActive" value="${isActive}"/>
<input type="hidden" name="message" id="message" value="${message}"/>
<input type="hidden" name="originalId" id="originalId" value="0"/>

<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />

</form>
</body>
</html>

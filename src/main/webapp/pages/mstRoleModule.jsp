<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<script type="text/javascript" src="./grid/RequestNote.js"></script> 
<style>
</style>
<script>
$(document).ready(function(){ 
	
	$("#requestBy").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoRequestBy.jsf", {
	           // term: extractLast(request.term)
	        	requestBy: $("#requestBy").val()
	        }, response);
	    },
	    search: function () {  // custom minLength
	       	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 2,
	    select: function (event, ui) {
	    	setTimeout(function()
   		    {
	    		$("#serialNo1").focus();
		    	return false;
   		    },250);
	    }
   });  
	
   $("#msgclose").click(function()            
   {				    	
  	  $("#msg").fadeOut(300);		  	         
   }); 
   
 });
 
function funFillProductName(evt,obj)      
{
	var cnt = $("#count").val();
	$("#productName"+cnt).autocomplete({
		source: function (request, response) {
		$.getJSON("${pageContext. request. contextPath}/autoProductName.jsf", {
			productName: $("#productName"+cnt).val()
		}, response);
	},
	search: function () {  // custom minLength
		//console.log($(this).val());
	},
	focus: function () {
		// prevent value inserted on focus
		return false;
	},
	autoFocus: true,
	minLength: 2,
	select: function (event, ui) {
		funLoadProductDetails(ui.item.id);
	}
	});  
}

  function funLoadProductDetails(productId)
 {
	$.ajax({
		dataType : 'json',
        url : "${pageContext.request.contextPath}/getProduct.jsf?productId="+parseInt(productId),
        type : "POST",
        success : function(result) 
        {
        	var cnt = $("#count").val();
        	if(result != "")
	    	{			
        		 $("#txtToolName"+cnt).text(result.productName);
        		 $("#toolName"+cnt).val(result.productName); 
        		 $("#txtProductName"+cnt).text(result.productName);
	  	         $("#productName"+cnt).val(result.productName); 
	  	         $("#txtDrawingNo"+cnt).text(result.drawingNo);
	  	  	     $("#drawingNo"+cnt).val(result.drawingNo);	   
	  	  	     $("#txtmachingType"+cnt).text(result.machineType);
	  			 $("#machingType"+cnt).val(result.machineType);
	  			 setTimeout(function()
	  		   	 {
	  				$("#batchQty"+cnt).focus();
	  				return false;
	  		   	 },200);
	    	}
        }
     });
 }
  
 function funChooseRole()
 {
	 var roleName =  $('#roleName :selected').val();	
	
	 $("#frmRoles").attr("action","fetchRoleModule.jsf");
   	 $("#frmRoles").submit();
 }

 function funProvideAccess(no)
 {
	 var access = $("#accessControl"+no+":checked").val();
	 if(access == undefined)
	 {
		 $('#viewAccess'+no).prop('checked', false);
		 $('#addAccess'+no).prop('checked', false);
		 $('#editAccess'+no).prop('checked', false);
		 $('#deleteAccess'+no).prop('checked', false);
	 }
 }
 
 function Init()
 {	
	$("#loadoverlay").hide();
	$("#ajaxLoading").hide();
	var msg = $("#message").val();
	if(msg != "")  
	{    
		$("#msg").fadeIn(300);	   
    }
 }

 function trim(stringTrim)
 {
 	return stringTrim.replace(/^\s+|\s+$/g,"");
 }
 
 function showimg()
 {
 	$("#ajaxLoading").show();  
 }
 
 function processRequestNote(val)
 {
	 var roleName =  $('#roleName :selected').val();	
	 if(roleName == "select")
     {        	
		    jAlert('Please Select Roles', 'Roles  is not Selected', function(){	
		    	//$("#requestId").focus();						  			    	 
	        });	
		   return false;
     }
     else
     {
		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();   	 
		 //var chkAddAccess = $("input:checkbox[name=chkAddAccess]").length;	
		 $("input:checkbox[name=chkAddAccess]").each(function(i){	
			 
			    if($("#accessControl"+i).is(":checked")== true)
				{
			    	$("#actAccessControl"+i).val("1");
				}
				else
				{
					$("#actAccessControl"+i).val("0");
				}
			  
				if($("#addAccess"+i).is(":checked")== true)
				{
					$("#actAddAccess"+i).val("1");
				}
				else
				{
					$("#actAddAccess"+i).val("0");
				}
				if($("#viewAccess"+i).is(":checked")== true)
				{
					$("#actViewAccess"+i).val("1");
				}
				else
				{
					$("#actViewAccess"+i).val("0");
				}
				if($("#editAccess"+i).is(":checked")== true)
				{
					$("#actEditAccess"+i).val("1");
				}
				else
				{
					$("#actEditAccess"+i).val("0");
				}
				
				if($("#deleteAccess"+i).is(":checked")== true)
				{
					$("#actDeleteAccess"+i).val("1");
				}
				else
				{
					$("#actDeleteAccess"+i).val("0");
				}
		 });	
		 
		 if(val == "Save")
		 {
			 $("#frmRoles").attr("action","addRoleModule.jsf");   	      
		 }
		 else
		 {
			$("#frmRoles").attr("action","updateRoleModule.jsf");
		 }
	     $("#frmRoles").submit();
     }
 }
 
 function funRequestNoteList()
 {
	 $("#requestId").val("0");
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmRoles").attr("action","showRoleModule.jsf");   	                
     $("#frmRoles").submit();
 }
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmRoles" method="post"  id="frmRoles" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>

<div id="loadoverlay" class="loading_overlay" ></div>
<img id="ajaxLoading" src="./images/ultraloading.jpg" class="loadingPosition"></img>
<div id="overlay" class="web_dialog_overlay" ></div>

   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="10px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Manage Role</td> 
           <td width="45%" class="submenutitlesmall" align="center">
               <table cellspacing="1" cellpadding="2" width="57%" height="20px" align="left" style="display:none" id="msg">	
			     	<tr>  
				        <td class="confirmationmessage" align="center">&nbsp;&nbsp;&nbsp;<span id="dynmsg" style="align:center;">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="msganchor" id="msgclose">X</a></td>
				    </tr>
			   </table>
           </td> 
           <td width="25%" class="submenutitlesmall" align="right">        
           		        
           </td>                                      
       </tr>
   </table>
   <center>   
         <DIV STYLE="width: 99%; padding:0px; margin: 0px;height:auto;min-height:auto;" class="content">
         
         
             <!-- FIELDS -->
             <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
					<tr height="20px">
					<td></td>
					</tr>
				</table>
				<table style="width:70%" align="left" cellpadding="3" cellspacing="0" border="0">		
					<tr>
						<td width="3%" class="tabpopuplabel" align="left"></td>
						<td class="formlabelblack" align="left">Roles&nbsp;<span class="formlabel">*</span></td>
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left">
								<select name="roleName1" id="roleName" onchange="funChooseRole();">
								    <option value="select" id="select">--Select--</option>
									<c:forEach items="${lstRole}" var="lstRole" varStatus="row">  	
									    <c:choose>
											<c:when test="${lstRole.roleName eq selectedRole}">
									  			<option value="${lstRole.roleName}" id="${lstRole.roleName}${row.index}" selected>${lstRole.roleName}</option>
									  		</c:when>
									  		<c:otherwise>
									  			<option value="${lstRole.roleName}" id="${lstRole.roleName}${row.index}">${lstRole.roleName}</option>
									  		</c:otherwise>
								  		</c:choose>	
								  	</c:forEach>
								</select>
						</td>
					</tr>		     			
					<tr>
					    <td class="popuplabel" colspan="2" align="left"></td>
					</tr>
				</table>
             <!-- END -->
         
				<div style="width:1300px;height:auto;min-height:175px;overflow:auto;">      
		        <table class="tablesorter" style="width:98%" align="center" id="requestNoteData">
			         <tr>
			              <th align="center" width="17%">Module&nbsp;Name</th>
			              <th align="center" width="17%">Role&nbsp;Name</th>
			              <th align="center" width="16%">Screen&nbsp;Name</th>
			              <th align="center" width="10%">Acess&nbsp;Control</th>
						  <th align="center" width="10%">view&nbsp;Access</th>
						  <th align="center" width="10%">Add&nbsp;Access</th>
			              <th align="center" width="10%">Edit&nbsp;Access</th>
			              <th align="center" width="10%">Delete&nbsp;Access</th>
			         </tr>
			         <c:choose>
							<c:when test="${fn:length(lstRoleVsUser) gt 0}">
								   <c:forEach items="${lstRoleVsUser}" var="lstModule" varStatus="row">
									 <tr id="rowid-0" >
							          <td align="left">&nbsp;${lstModule.moduleName1}<input type="hidden" name="moduleName" value="${lstModule.moduleName1}"/></td>
							          <td align="left">&nbsp;${lstModule.roleName1}<input type="hidden" name="roleName" value="${lstModule.roleName1}"/></td>
							          <td align="left">&nbsp;${lstModule.screenName1}<input type="hidden" name="screenName" value="${lstModule.screenName1}"/></td>		          
									  <td align="center">&nbsp;
									  		<c:choose>
											<c:when test="${lstModule.accessControl1 eq '1'}">
									  			<input type="checkbox" name="chkAccessControl" id="accessControl${row.index}" value="${lstModule.accessControl1}" checked/>
									  		</c:when>
									  		<c:otherwise>
									  			<input type="checkbox" name="chkAccessControl" id="accessControl${row.index}" value="${lstModule.accessControl1}"/>
									  		</c:otherwise>
									  		</c:choose>
									  		<input type="hidden" name="accessControl" id="actAccessControl${row.index}" value="${lstModule.accessControl1}"/>
									  </td>
									  <td align="center">&nbsp;
									  		<c:choose>
											<c:when test="${lstModule.viewAccess1 eq '1'}">
										  		<input type="checkbox" name="chkViewAccess" id="viewAccess${row.index}" value="${lstModule.viewAccess1}" checked/>
										  	</c:when>
									  		<c:otherwise>
									  			<input type="checkbox" name="chkViewAccess" id="viewAccess${row.index}" value="${lstModule.viewAccess1}"/>
									  		</c:otherwise>
									  		</c:choose>
										  	<input type="hidden" name="viewAccess" id="actViewAccess${row.index}" value="${lstModule.viewAccess1}"/>
									  </td>				  
									  <td align="center">&nbsp;
									  		<c:choose>
											<c:when test="${lstModule.addAccess1 eq '1'}">
											 	<input type="checkbox" name="chkAddAccess" id="addAccess${row.index}" value="${lstModule.addAccess1}" checked/>
											</c:when>
									  		<c:otherwise>
									  			<input type="checkbox" name="chkAddAccess" id="addAccess${row.index}" value="${lstModule.addAccess1}"/>
									  		</c:otherwise>
									  		</c:choose>
											 <input type="hidden" name="addAccess" id="actAddAccess${row.index}" value="${lstModule.addAccess1}"/>
									  </td>				  
									  <td align="center">&nbsp;
									  		 <c:choose>
											 <c:when test="${lstModule.editAccess1 eq '1'}"> 
									  		 		<input type="checkbox" name="chkeditAccess" id="editAccess${row.index}" value="${lstModule.editAccess1}" checked/>
									  		 </c:when>
									  		 <c:otherwise>
									  		 		<input type="checkbox" name="chkeditAccess" id="editAccess${row.index}" value="${lstModule.editAccess1}"/>
									  		 </c:otherwise>
									  		 </c:choose>
									  		 <input type="hidden" name="editAccess" id="actEditAccess${row.index}" value="${lstModule.editAccess1}"/>
									  </td>
									  <td align="center">&nbsp;
									  		 <c:choose>
											 <c:when test="${lstModule.deleteAccess1 eq '1'}"> 
									  		 	  <input type="checkbox" name="chkDeleteAccess" id="deleteAccess${row.index}" value="${lstModule.deleteAccess1}" checked/>
									  		 </c:when>
									  		 <c:otherwise>	
									  		 	  <input type="checkbox" name="chkDeleteAccess" id="deleteAccess${row.index}" value="${lstModule.deleteAccess1}"/>
									  		 </c:otherwise>
									  		 </c:choose> 
									  		 <input type="hidden" name="deleteAccess" id="actDeleteAccess${row.index}" value="${lstModule.deleteAccess1}"/>
									  		 <input type="hidden" name="roleModuleId" value="${lstModule.roleModuleId1}"/>
									  </td>
						            </tr> 
						         </c:forEach>	
							</c:when>
							<c:otherwise>
								  <c:forEach items="${lstModule}" var="lstModule" varStatus="row">
									 <tr id="rowid-${row.index}" >
							          <td align="left">&nbsp;${lstModule.moduleName}<input type="hidden" name="moduleName" value="${lstModule.moduleName}"/></td>
							          <td align="left">&nbsp;${selectedRole}<input type="hidden" name="roleName" value="${selectedRole}"/></td>
							          <td align="left">&nbsp;${lstModule.screenName}<input type="hidden" name="screenName" value="${lstModule.screenName}"/></td>
									  <td align="center">&nbsp;<input type="checkbox" name="chkAccessControl" id="accessControl${row.index}" onclick="javascript:funProvideAccess('${row.index}');" value="1"/><input type="hidden" name="accessControl" id="actAccessControl${row.index}" value="0"/></td>
									  <td align="center">&nbsp;<input type="checkbox" name="chkViewAccess" id="viewAccess${row.index}" value="1"/><input type="hidden" name="viewAccess" id="actViewAccess${row.index}" value="0"/></td>				  
									  <td align="center">&nbsp;<input type="checkbox" name="chkAddAccess" id="addAccess${row.index}" value="1"/><input type="hidden" name="addAccess" id="actAddAccess${row.index}" value="0"/></td>				  
									  <td align="center">&nbsp;<input type="checkbox" name="chkeditAccess" id="editAccess${row.index}" value="1"/><input type="hidden" name="editAccess" id="actEditAccess${row.index}" value="0"/></td>
									  <td align="center">&nbsp;<input type="checkbox" name="chkDeleteAccess" id="deleteAccess${row.index}" value="1"/><input type="hidden" name="deleteAccess" id="actDeleteAccess${row.index}" value="0"/></td>
						            </tr> 
						         </c:forEach>
							</c:otherwise>
				     </c:choose>
			         
		       </table> 
		       </div> 
			 <table  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="50px">
		           <td height="50px" align="center">&nbsp;</td>
	        </table> 
     </DIV>
     </center>    
     
     <table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
	    <tr>
           <td height="50px" align="left">
		     <input  type="button" class="${btnSatusStyle}" value="${btnStatusVal}"  onclick="javascript:processRequestNote('${btnStatusVal}');" ${btnStatus}/>&nbsp;&nbsp;
		     <input type="button" class="btn btnNormal"  onclick="javascript:funRequestNoteList();" value="List" /></td>
	    </tr>
	 </table>       
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 <input type="hidden" name="message" id="message" value="${message}" />
</form>
</body>
</html>
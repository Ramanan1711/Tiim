<%@page import="java.util.HashMap, com.tiim.model.RoleVsUser "%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String addAccess = "";
String editAccess = "";
String deleteAccess = "";
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
<style>
.pagination 
{  
  display:none;
}
</style>
<script>
$("#techAlert").empty();

   $(document).ready(function ()
    {	  	
		   $('.tableHeading').fixheadertable({
	       	   caption     : '', 
	           colratio    : [150, 350, 350], 
	           height      : 350, 
	           width       : 970,             
	           sortable    : true, 
	           sortedColId : 0,    
	           unSortColumn: ['Actions'],     
	           resizeCol   : true,            
	           minColWidth : 50,
	           whiteSpace	 : 'nowrap',
	           sortType    : ['string', 'string', 'string']          
	      });  
		   $("body .ui-widget-content").css("overflow-x","hidden"); 
	                 
	      $("#btnpop").click(function ()              
	      {			      	    	  
	    	  if($("#dynmsg").text() != "")
			  {
			    removemsg();
			  } 	    	
	    	 $("#techAlert").empty(); 
	    	 $("#roleName").val(""); 	
	         ShowDialog(); 
	         $("#btnprocess").val("Save");	
	      });
	      
	      $("#btnpopup").click(function ()        
 	      {		    	
	    	 if($("#dynmsg").text() != "")
			 {
			    removemsg();
			 }  		    	
	    	 $("#techAlert").empty();  	
	    	 $("#roleName").val("");	   
 	         ShowDialog();	
 	         $("#btnprocess").val("Save");
 	         $("#roleName").focus();                 
 	      });
	     	      
	      $("#msgclose").click(function()            
		  {				    	
		  	  $("#msg").fadeOut(300);		  	         
		  }); 
  	       
	     	    	                     	      
	      $("#btnstatclose").click(function ()             
   	      {		    	   	  	    			    		    	
	    	  hideStatusDialog();
   	      });  

	      $("#btnstatno").click(function ()                 
   	      {		    	   	    	  	    			    		    	
	    	  hideStatusDialog();
   	      });  		  
   	      
      	  $("#btndelclose").click(function ()      
   	      {		    	   	    	  	    			    		    	
      		  hideDeleteDialog();
   	      });  

	      $("#btndelno").click(function ()      
   	      {		    	   	    	  	    			    		    	
	    	  hideDeleteDialog();
   	      });  		  

   	      $("#btndelyes").click(function ()         
   	      {		    	   	    	  	    			    		    		    	  
	    	  $("#action").val("Delete");                    	                  
	    	  hideDeleteDialog();	 

	    	  $("#loadoverlay").fadeIn();  
	   	      $("#processloading").fadeIn();
	   	   
	    	  $("#frmRole").attr("action","deleteRoleName.jsf");   	                
	          $("#frmRole").submit(); 
   	      });  	        
   	      
   	   $("#btnProcessyes").click(function ()      
  	   	      {		  
  					hideAllListDialog();	
  					funAllProcessList();
  	   	      });
   	   $("#btnProcessclose").click(function ()  
      	      {		    	   	    	  	    			    		    	
  					hideAllListDialog();
  					$("#searchRole").focus();
      	      });  
   	    $("#btnProcessno").click(function ()      
      	      {		    	   	    	  	    			    		    	
  	    			hideAllListDialog();	
  	    			$("#searchRole").focus();
      	      });   	
   	   $("#searchRole").keypress(function(e){
 	 	  	  if(e.which == 13)
 	 	  	  {	 	  		
 	 	  		 return funAllList();
 	 	  	  }     	  	 
 	         });
   		$("#btnSearch").click(function (){  	  		
 			 	return funAllList(); 	           
 		     }); 
   });

  function mstEmpCancelClose()             
  {	    	  
	var process = $("#btnprocess").val(); 
	$("#techAlert").empty(); 
	$("#roleName").val("");
	if(process == "Save")
    {
 		$("#btnprocess").val("Save");
    }  
    else if(process == "Update")
    {
     	 $("#btnprocess").val("Update");
    } 
	if($("#dynmsg").text() != "")
	{
	 removemsg();
	}
	HideDialog();	         
  }
  
  function funTypeRoleName(evt,obj)
  {
	  $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Role Name is required";
		   var roleName =  trim($("#roleName").val());
		   if(roleName == "")
		   {
			   alertmsg = "Role Name is required";
			   $("#techAlert").text(alertmsg);
			   $("#roleName").focus();
			   return false;
		   }
		  
	   }
  }
		     
  function proccessRole()                   
  {  	    	  	       	
	 var alertmsg = "Role Name is required";
	 var process = $("#btnprocess").val(); 
	 var roleName = trim($("#roleName").val());

	 
	 $("#action").val("");  	 	       	        	    
     if(roleName == "")
     {
   	  	alertmsg = "Role Name is required";
      	$("#techAlert").text(alertmsg);
    	$("#roleName").focus();
    	return false;
     }
     
     else
     {
    	 $("#techAlert").empty();  
     }                
     HideDialog();

     $("#loadoverlay").fadeIn();  
     $("#processloading").fadeIn();
    	
     if(process == "Save")
     {            	
         $("#action").val("Save");      
         $("#frmRole").attr("action","addRole.jsf");               
     }  
     else if(process == "Update")
     {                           
    	 $("#action").val("Update"); 
    	 $("#frmRole").attr("action","updateRole.jsf");               
     }       
     $("#frmRole").submit(); 	    	        
  }	
           
   function ShowDialog()
   {	   
      $("#overlay").show();
      $("#departmentDialog").fadeIn(300);     
      $("#overlay").unbind("click");                       
   }

   function HideDialog()
   {
      $("#overlay").hide();
      $("#departmentDialog").fadeOut(300);
   } 

   function showStatusDialog()
   {
      $("#overlay").show();
      $("#status").fadeIn(300);    
      $("#overlay").unbind("click");           
   }

   function hideStatusDialog()
   {
      $("#overlay").hide();
      $("#status").fadeOut(300);
   } 

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

   function funedit(id,rowid,stat)  
   {		 
	   $.ajax({
			dataType : 'json',
	        url : "${pageContext.request.contextPath}/getRole.jsf?roleId="+id,
	        type : "POST",
	        success : function(result) 
	        {
	  		   if(result != "")
	    	   {				
	  	    	  if($("#dynmsg").text() != "")
	  	    	  {
	  	    		 removemsg();
	  	    	  }       	 
	  	          $("#techAlert").empty(); 

	  	          $("#roleName").val(trim(result.roleName)); 	
	  	         
	  	          $("#roleId").val(result.roleId);     
	  	          $("#isActive").val(1); 	
	  	          
	  	          ShowDialog(); 
	  	          $("#btnprocess").val("Update");	
	  	          $("#roleName").focus();
	    	   }
	        }
	    }); 
   }
    
   function fundelete(delid)          
   {   
	   if($("#dynmsg").text() != "")
	   {
		 removemsg();
	   } 
	   $("#roleId").val(delid); 
	   showDeleteDialog();
   }

   function funstatus(statid,rowid,delStatus)   
   {			      
	   if($("#dynmsg").text() != "")
	   {
		 removemsg();
	   } 	   
	   $("#deleteStatus").val(delStatus);     
	   $("#rowId").val(rowid);   
	   $("#autoId").val(statid); 

	   setChangeStatus("disable",statid);  
   }

   function setChangeStatus(val,id) 
   {	 
       var url = "${pageContext.request.contextPath}/changeRoleStatus.jsf?roleId="+id;   	  	   
       // Perform the AJAX request using a non-IE browser.
       if (window.XMLHttpRequest) 
       {		 
         request = new XMLHttpRequest();	   
         // Register callback function that will be called when
         // the response is generated from the server.
         request.onreadystatechange = updateChangeStatus;	      
         try 
         {
             request.open("POST", url, true);
         } 
         catch (e) 
         {
            //alert("Unable to connect to server to retrieve count.");
         }
      
         request.send(null);
       // Perform the AJAX request using an IE browser.
       } 
       else if (window.ActiveXObject) 
       {
   	    
         request = new ActiveXObject("Microsoft.XMLHTTP");
         if (request) 
         {
           // Register callback function that will be called when
           // the response is generated from the server.
           request.onreadystatechange = updateChangeStatus;
      
           request.open("GET", url, true);
           request.send();
         }	     
        }
   }
   
   function updateChangeStatus() 
   {		 
   	 if (request.readyState == 4) 
   	 {
		  if (request.status == 200) 
		  {
		  	   var status = request.responseText;	
		       if(status != "")
			   {			   
				    if(trim(status) == "Session Expire")
				    {
				    	$("#frmRole").attr("action","pages/SessionExpire.jsp");   	                
					    $("#frmRole").submit();
				    }
				    else
				    {
				       $("#dynmsg").text(status);
				       $("#msg").fadeIn(100);
				     	
			           var rowid = $("#rowId").val();  
					   if(status == "Made Active Successfully")
					   {
						   $("#delete"+rowid).attr('src',"./images/deleteicon_black.gif"); 
						   $("#changeStatus"+rowid).attr('src',"./images/activate.gif"); 
					
						   $("#deleteAnchor"+rowid).attr('title' , "Inactive entry can only be deleted"); 
						   $("#statusAnchor"+rowid).attr('title' , "Click to make this Inactive"); 
					
						   $("#deleteAnchor"+rowid).removeAttr("href");
						   $("#editAnchor"+rowid).attr("href", "javascript:funedit('"+$("#autoId").val()+"','"+rowid+"','1');");
					   }		  	  
					   else
					   {
						  /*  if($("#deleteStatus").val() == "0")
						   {
							   $("#delete"+rowid).attr('src',"./images/deleteicon.gif");
							   $("#deleteAnchor"+rowid).attr('title' , "Click to Delete"); 
					
							   $("#deleteAnchor"+rowid).attr("href", "javascript:fundelete("+$("#autoId").val()+");");			  		
						   }
						   else
						   {
							   $("#delete"+rowid).attr('src',"./images/deleteicon_black.gif"); 
							   $("#deleteAnchor"+rowid).attr('title' , "Cannot delete, Department in use"); 
					
							   $("#deleteAnchor"+rowid).removeAttr("href");
						   } */
						   
						   $("#delete"+rowid).attr('src',"./images/deleteicon.gif");
						   $("#deleteAnchor"+rowid).attr('title' , "Click to Delete"); 
				
						   $("#deleteAnchor"+rowid).attr("href", "javascript:fundelete("+$("#autoId").val()+");");		
						    
						   $("#changeStatus"+rowid).attr('src',"./images/deactivate.gif");		  		   
						   $("#statusAnchor"+rowid).attr('title' , "Click to make this Active");
						   $("#editAnchor"+rowid).attr("href", "javascript:funedit('"+$("#autoId").val()+"','"+rowid+"','0');");
					   }
			        }
			     }   		      
		    }   	  
       } 
   }

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
	 
	  if($("#action").val() != "" && $("#action").val() != "list" && $("#action").val() != "listdata")  
	  {    
			var dmsg = $("#dynmsg").text();	
		    if(dmsg.indexOf("exists") > -1)
		    {
		      $("#dynmsg").text('');
		      $("#msg").fadeOut(100);  
		 		   	    		    	
		      ShowDialog(); 		    
			  if($("#action").val() == "Save")   
			  {		    
			     $("#btnprocess").val("Save");		         
			  }
			  else if($("#action").val() == "edit")
			  {			  
		        $("#btnprocess").val("Update");			      
			  }
			  $("#roleName").focus(); 
		    }
		    else
		    {
		    	showmsg();
		    	$("#searchRole").focus();
		    }
	  }
	  else 
	  {
		  $("#searchRole").focus();
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
	   var searchVal = ($("#searchRole").val());   
	   if(searchVal == "")
  		{   	
		   showAllListDialog();
  		}
	   else
 		{   		    
	   	       $("#action").val("list");  	
	 		   $("#loadoverlay").fadeIn();  
	 		   $("#processloading").fadeIn();	 			                    	                  
	 	 	   $("#frmRole").attr("action","searchRole.jsf");   	                
	 	       $("#frmRole").submit();
  		 }	  
   }
   function funAllProcessList()
   {  		    	
	       $("#action").val("listdata");  
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();   	 			                    	                  
 	 	   $("#frmRole").attr("action","showRole.jsf");   	                
 	       $("#frmRole").submit();	      	 	
   }   	  
</script>

</head>
<body class="body" onload="init();">
<form name="frmRole" id="frmRole" method="post" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>
<%
	HashMap<String, RoleVsUser> hmRoleVsUser1 = (HashMap<String, RoleVsUser>)session.getAttribute("RoleVsUser");
	RoleVsUser roleVsUser1 = hmRoleVsUser1.get("Department");
	if(roleVsUser1 != null)
	{
	 addAccess = roleVsUser1.getAddAccess1();
	 editAccess = roleVsUser1.getEditAccess1();
	 deleteAccess = roleVsUser1.getDeleteAccess1();
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
	
	<div id="departmentDialog" class="web_dialog" style="">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	       <td>
			  <table style="width:98%" align="center" cellpadding="4" cellspacing="0" border="0">	     
			     <tr>
			        <td align="left" width="50%" class="popuptoptitle">Role Info</td>
			        <td width="48" class="popuptoptitle" align="right"><a href="javascript:mstEmpCancelClose();" title="Click to Close" class="popupanchor" id="btnclose">X</a>&nbsp;</td>
			     </tr>			     			     			    
			     <tr>
			        <td colspan="2" align="center" class="popuptopalert">&nbsp;<span id="techAlert">${message}</span></td>
			     </tr>		
			    
			     <tr>
			        <td align="left" class="formlabelblack">Role Name&nbsp;<span class="formlabel">*</span></td>
			        <td align="left">&nbsp;<input type="text" class="textsmall" name="roleName" id="roleName" onkeypress="javascript:funTyperoleName(event,this);" value="" autocomplete="off" maxlength="50"/></td>
			     </tr> 
			       
			     <tr>
			       <td colspan="2">&nbsp;</td>			       
		         </tr>       			     
			     <tr>
			        <td>&nbsp;</td>
			        <td align="left"><input  type="button" class="btn btnImportant" id="btnprocess" value="Save" onclick="javascript:proccessRole();"/>&nbsp;&nbsp;<input type="button" id="btncancel"  class="btn btnCancel"  onclick="javascript:mstEmpCancelClose();" value="Cancel" /></td>
			     </tr>	
			     <tr>
			       <td colspan="2">&nbsp;</td>				       
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
	          <td width="25%" class="heading" align="left">&nbsp;Role</td> 
	          <td width="50%" class="submenutitlesmall" align="center">
	               <table cellspacing="1" cellpadding="2" width="50%" align="center" style="display:none" id="msg">	
				     	<tr>  
					    	<td class="confirmationmessage" align="center">&nbsp;<span id="dynmsg">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="popupanchor" id="msgclose">X</a></td>
					    </tr>
			       </table>
	          </td>
	          <td align="right" width="25%" class="anchorlabel">
			  &nbsp;
			  <%
			  if("1".equals(addAccess)){
			  %>
			  <input type="button" title="Click to Add" class='btn btnSMNormal' id="btnpop" value="Add"/>
			  <%
			  }else{
			  %>
			  <input type="button" title="Access denied" class='btn btnSMNormalDisable' value="Add" disabled="disabled"/>
			  <%}%>
			  &nbsp;			  
			  </td>                             
	     </tr>
	 </table>
       
	<center>           				
		<div style=" width: 1000px; padding:0px; margin: 0px;height:425;min-height:420px;">       
		
		 <div style="width: 980px;background-color:white; border: 1px solid #E0E0E0;;">
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>   
		    <table cellspacing="0" class="searchList" cellpadding="5" border="0" align="center">
		       <tr>
		          <td class="searchListLabel" align="left">Search&nbsp;&nbsp;<input type="text" name="searchRole" id="searchRole" placeholder="Enter Role Name" value="${searchRole}" class="textmediumlarge" maxlength="50"/>&nbsp;&nbsp;<input type="button" name="btnSearch" id="btnSearch" value="Go" class="btn btnSMImportant"></td>  
		       </tr>
		    </table>  
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>  
		  </div>     
		             
		  <c:choose>
			<c:when test="${fn:length(lstRole) gt 0}">
		        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
					 <tr>
						 <td height="5px"></td>		
					 </tr>
			    </table>
		                                
			<table id="lstRole" name="lstRole" class="tableHeading">
			      
			     <thead>
			          <tr>
				          <th width="20%">Role Id</th>
				          <th width="30%">Role Name</th>
				          <th width="15%">Action</th>
			          </tr>  
			     </thead> 
			     <tbody>
			         <c:forEach items="${lstRole}" var="lstRole" varStatus="row">  
				          <tr>
				             <td>&nbsp;&nbsp;&nbsp;&nbsp;${lstRole.roleId}  </td>
				             <td>&nbsp;&nbsp;&nbsp;&nbsp;${lstRole.roleName}  </td>
				             
				             <td align="center" style="text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;
				             	<%
								  if("1".equals(editAccess)){
								%>
				             	<a title="Click to Edit" id="editAnchor${row.index}" href="javascript:funedit('${lstRole.roleId}','${row.index}','${lstRole.isActive}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
				             	<%
				             	}else{
				             	%>
				             	<a title="Access denied" id="editAnchor${row.index}" href="#"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
				             	<%}%>    		             					
								<c:choose>
									<c:when test="${lstRole.isActive == 1}">
										<a title="Inactive entry can only be deleted" id="deleteAnchor${row.index}"><img border="0" id="delete${row.index}" src="./images/deleteicon_black.gif"/></a>&nbsp;&nbsp;<a title="Click to make this Inactive" href="javascript:funstatus('${lstRole.roleId}','${row.index}','${lstRole.isActive}');" id="statusAnchor${row.index}" ><img border="0" id="changeStatus${row.index}" style="" src="./images/activate.gif"/></a>
									</c:when>
									<c:when test="${lstRole.isActive == 0}">
										<%
										  if("1".equals(deleteAccess)){
										%>
										<a title="Click to Delete" href="javascript:fundelete('${lstRole.roleId}');" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
										<%
										  }else{
						             	%>  
						             	<a title="Access denied" href="#" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
						             	<%}%>
										&nbsp;<a title="Click to make this Active" href="javascript:funstatus('${lstRole.roleId}','${row.index}','${lstRole.isActive}');" id="statusAnchor${row.index}" ><img border="0" id="changeStatus${row.index}" style="" src="./images/deactivate.gif"/></a>
									</c:when>
									<c:otherwise>
									    <a title="Cannot delete, Role in use" id="deleteAnchor${row.index}"><img border="0" id="delete${row.index}" src="./images/deleteicon_black.gif"/></a>&nbsp;<a title="Click to make this Active" href="javascript:funstatus('${lstRole.roleId}','${row.index}','${lstRole.isActive}');" id="statusAnchor${row.index}" ><img border="0" style="" id="changeStatus${row.index}" src="./images/deactivate.gif"/></a>
									</c:otherwise>
								</c:choose>
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
				<img border="0" style="" src="./images/pencil.png"/>&nbsp;-&nbsp;Edit&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon.gif"/>&nbsp;-&nbsp;Delete&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon_black.gif"/>&nbsp;-&nbsp;Data in use&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/activate.gif"/>&nbsp;-&nbsp;Active&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deactivate.gif"/>&nbsp;-&nbsp;Inactive&nbsp;</td>     
			</tr>                         	       
	   </table> 
	</center>    
   

<input type="hidden" name="action" id="action" value="${action}"/>
<input type="hidden" name="roleId" id="roleId" value="${roleId}"/>
<input type="hidden" name="isActive" id="isActive" value="${isActive}"/>

<input type="hidden" name="deleteStatus" id="deleteStatus" />
<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />

</form>
</body>
</html>

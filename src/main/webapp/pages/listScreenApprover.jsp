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
$("#productName").val("");  

   $(document).ready(function ()
    {	  	
		   $('.tableHeading').fixheadertable({
	       	   caption     : '', 
	           colratio    : [350, 350, 270], 
	           height      : 350, 
	           width       : 970,             
	           sortable    : true, 
	           sortedColId : 0,    
	           unSortColumn: ['Actions'],     
	           resizeCol   : true,            
	           minColWidth : 50,
	           whiteSpace	 : 'nowrap',
	           sortType    : ['integer', 'string', 'string']          
	      });  
		   $("body .ui-widget-content").css("overflow-x","hidden"); 
	                 
	      $("#btnpop").click(function ()              
	      {			      	    	  
	    	  if($("#dynmsg").text() != "")
			  {
			    removemsg();
			  } 	    	
	    	 $("#productName").val(""); 
	    	 $("#drawingNo").val("");	   
	    	 
			 //$("#machineType").val("Required");
			 $("#typeOfTool").val("");
			 $("#mocPunches").val("");
			 $("#mocDies").val("");
			 $("#shape").val("");
			 $("#dimensions").val("");
			 //$("#breaklineUpper").val("Required");
			 //$("#breaklineLower").val("Required");
			 $("#embosingUpper").val("");
			 $("#embosingLower").val("");
			// $("#laserMaking").val("Required");
			 $("#hardChromePlating").val("");
			 $("#searchScreenName").val("");
			// $("#dustCapGroove").val("");
			 //$("#inspectionReport").val("Required");
			// $("#dqDocument").val("");
			// $("#mocCertificate").val("Required");
			 $("#ductCapMOCCertificate").val("");
			 $("#toolingLife").val("");
			 $("#serviceIntervalQty").val("");
			 $("#uom").val("");
	         ShowDialog(); 
	         $("#btnprocess").val("Save");	
	         $("#productName").focus();  	           
	      });
	      
	      $("#noOfLevels").keypress(function(evt) {
			  if(evt.which != 8 && evt.which != 0 && ((evt.which < 48) || (evt.which > 57)))
			  {
			      $("#noOfLevels").val("");
			   	  $("#noOfLevels").focus();
				   return false;   
			  }
			 // $("#userName0").nextAll().remove();
			 
			  
	      });
	      
	      /*******UserName Drop Down***********/
	      
	      $("#noOfLevels").keyup(function(evt)             
	      {   		    	
	    	  $("#userName0").nextAll().remove();
				 
			  var i = 0;
			  var j = 0;
			  for (i = 1; i <=$("#noOfLevels").val(); i++) {

				  $("#example").append($("#userName"+j).clone());
				 // $("#userName"+j).after($("#userName"+j).clone());
				  $("#userName"+j).attr('name', 'userName'+i);
				  $("#userName"+j).attr('id', 'userName'+i);
				  j++;
				}
			  $("#userName"+$("#noOfLevels").val()).remove();
	      }) ;
	      
	      $("#btnpopup").click(function ()        
 	      {		    	
	    	 if($("#dynmsg").text() != "")
			 {
			    removemsg();
			 }  		    	
	    	 $("#techAlert").empty();  	
	    	 
	    	 $("#productName").val(""); 
	    	 $("#drawingNo").val("");	   
	    	
			 //$("#machineType").val("Required");
			 $("#typeOfTool").val("");
			 $("#mocPunches").val("");
			 $("#mocDies").val("");
			 $("#shape").val("");
			 $("#dimensions").val("");
			/*  $("#breaklineUpper").val("Required");
			 $("#breaklineLower").val("Required"); */
			 $("#embosingUpper").val("");
			 $("#embosingLower").val("");
			// $("#laserMaking").val("Required");
			 $("#hardChromePlating").val("");
			 $("#searchScreenName").val("");
			// $("#dustCapGroove").val("");
			// $("#inspectionReport").val("Required");
			// $("#dqDocument").val("");
			// $("#mocCertificate").val("Required");
			 $("#ductCapMOCCertificate").val("");
			 $("#toolingLife").val("");
			 $("#serviceIntervalQty").val("");
			 $("#uom").val("");
 	         ShowDialog();	
 	         $("#btnprocess").val("Save");
 	         $("#productName").focus();                 
 	      });
	     	      
	      $("#msgclose").click(function()            
		  {				    	
		  	  $("#msg").fadeOut(300);		  	         
		  }); 
  	       
	      $("#productName").keyup(function()           
	      {
		    	if(trim($("#productName").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	      }) ;
	      
	      $("#drawingNo").keyup(function()             
   	      {   		    	
   		    	if(trim($("#drawingNo").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      }) ;

	      
		  
	    	                     	      
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
	   	   
	    	  $("#frmProduct").attr("action","deleteScreenApprovalMap.jsf");   	                
	          $("#frmProduct").submit(); 
   	      });  	        
   	      
   	   $("#btnProcessyes").click(function ()      
  	   	      {		  
  					hideAllListDialog();	
  					funAllProcessList();
  	   	      });
   	   $("#btnProcessclose").click(function ()  
      	      {		    	   	    	  	    			    		    	
  					hideAllListDialog();
  					$("#searchScreenName").focus();
      	      });  
   	    $("#btnProcessno").click(function ()      
      	      {		    	   	    	  	    			    		    	
  	    			hideAllListDialog();	
  	    			$("#searchScreenName").focus();
      	      });   	
   	   $("#searchScreenName").keypress(function(e){
 	 	  	  if(e.which == 13)
 	 	  	  {	 	  		
 	 	  		 return funAllList();
 	 	  	  }     	  	 
 	         });
   		$("#btnSearch").click(function (){  	  		
 			 	return funAllList(); 	           
 		     }); 
   });

  function mstProductCancelClose()             
  {	    	  
	var process = $("#btnprocess").val(); 
	$("#techAlert").empty(); 
	$("#productName").val(""); 
	$("#drawingNo").val("");
	
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
		      
  function funTypeProductName(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else
		   {
			   $("#drawingNo").focus();
		   }
	   }
  }
  
  function funTypeDrawingNo(evt,obj)
  {
	  $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		  
	   }
  }

  
 
  
  function processProduct()                   
  {  	    	  	       	
	  $("#techAlert").empty();
	  var process = $("#btnprocess").val(); 
	   var alertmsg = "Screen Name is required";
	  var screenName =  trim($("#screenName").val());
	   var noOfLevels =  trim($("#noOfLevels").val());
	  // var userName = trim($("#userName").val());
	  	var i = 0;
	  	var userName = "";
	   for (i = 0; i < noOfLevels; i++) {
		   if(i == 0)
		   {
			   userName =  $("#userName"+i).val()+"#"+i;
			}else
			{
				userName = userName + ","+ $("#userName"+i).val()+"#"+i;
			}
		   
		   if($("#userName"+i).val() == "")
			   {
			 	  alertmsg = "UserName is required";
				   $("#techAlert").text(alertmsg);
		      	   $("#userName0").focus();
		      	   return false;
			   }
		}
	   
	   ($("#userName").val(userName));
	   
	    
	   if(screenName == "")
       {                                        	 	         
           $("#techAlert").text(alertmsg);
      	   $("#screenName").focus();
      	   return false;
       }
	   else if(noOfLevels == "")
	   {
		   alertmsg = "Number of levels is required";
		   $("#techAlert").text(alertmsg);
		   $("#noOfLevels").focus();
		   return false;
	   }
	  
	   else if(userName == "")
	   {
		   alertmsg = "UserName is required";
		   $("#techAlert").text(alertmsg);
		   $("#userName").focus();
		   return false;
	   }
	  
	   else
	   {
		     $("#techAlert").empty();  
		     HideDialog();

		     $("#loadoverlay").fadeIn();  
		     $("#processloading").fadeIn();
		     if(process == "Save")
		     {            	
		    	 $("#screenId").val("0");
		         $("#action").val("Save");      
		         $("#frmProduct").attr("action","addScreenApprovalMap.jsf");               
		     }  
		     else if(process == "Update")
		     {                           
		    	 $("#screenId").val("0");
		    	 $("#action").val("Update"); 
		    	 $("#frmProduct").attr("action","updateScreenApprovalMap.jsf");               
		     }       
		     $("#frmProduct").submit(); 
	   }
  }	
           
   function ShowDialog()
   {	   
      $("#overlay").show();
      $("#productDialog").fadeIn(300);     
      $("#overlay").unbind("click");                       
   }

   function HideDialog()
   {
      $("#overlay").hide();
      $("#productDialog").fadeOut(300);
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
	        url : "${pageContext.request.contextPath}/getUserScreenMap.jsf?screenId="+id+"&screenName="+stat,
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
	  	          var i = 1;
	  	        $("#userName0").nextAll().remove();
	  	        $.each(result, function(j, screenMap) {
	  	        	$("#screenName").val(screenMap.screenName); 
		  	  	     $("#noOfLevels").val((screenMap.noOfLevels));	
			  	  	 $("#example").append($("#userName"+j).clone());
					 // $("#userName"+j).after($("#userName"+j).clone());
					  $("#userName"+j).attr('name', 'userName'+i);
					  $("#userName"+j).attr('id', 'userName'+i);
					  i++;
	  	        });
				var noOfLevels = $("#noOfLevels").val();
	  	     	 $("#userName"+$("#noOfLevels").val()).remove();
	  	    	for (i = 0; i < noOfLevels; i++) {
	  	    		$("#userName"+i).val(result[i].userName);
	  	    	}
	  	  	  
	  			/*  $("#userName").val((result.userName)); */
	  			
	  	         /* $("#screenId").val(result.screenId);      */    
	  	         $("#isActive").val("0"); 
	  	         $("#delStatus").val("0");
	  	          
	  	          ShowDialog(); 
	  	          $("#btnprocess").val("Update");	
	  	          $("#productName").focus();
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
	   $("#screenId").val(delid); 
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
       var url = "${pageContext.request.contextPath}/updateProductStatus.jsf?productId="+id;   	   
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
				    	$("#frmProduct").attr("action","pages/SessionExpire.jsp");   	                
					    $("#frmProduct").submit();
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
						   if($("#deleteStatus").val() == "0")
						   {
							   $("#delete"+rowid).attr('src',"./images/deleteicon.gif");
							   $("#deleteAnchor"+rowid).attr('title' , "Click to Delete"); 
					
							   $("#deleteAnchor"+rowid).attr("href", "javascript:fundelete("+$("#autoId").val()+");");			  		
						   }
						   else
						   {
							   $("#delete"+rowid).attr('src',"./images/deleteicon_black.gif"); 
							   $("#deleteAnchor"+rowid).attr('title' , "Cannot delete, Product in use"); 
					
							   $("#deleteAnchor"+rowid).removeAttr("href");
						   }
						    
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
			  $("#productName").focus(); 
		    }
		    else
		    {
		    	showmsg();
		    	$("#searchScreenName").focus();
		    }
	  }
	  else 
	  {
		  $("#searchScreenName").focus();
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
 	 	   $("#frmProduct").attr("action","screenApproval.jsf");   	                
 	       $("#frmProduct").submit();
 	   }	  
   }
   function funAllProcessList()
   {  		    	
	       $("#action").val("listdata");  
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();   	 			                    	                  
 	 	   $("#frmProduct").attr("action","screenApproval.jsf");   	                
 	       $("#frmProduct").submit();	      	 	
   }   	  
   
   function funClearProducts()
   {
   }
</script>

</head>
<body class="body" onload="init();">
<form name="frmProduct" id="frmProduct" method="post" autocomplete="off" enctype="multipart/form-data">
<%@ include file="tiimMenu.jsp"%>
<%
	HashMap<String, RoleVsUser> hmRoleVsUser1 = (HashMap<String, RoleVsUser>)session.getAttribute("RoleVsUser");
	RoleVsUser roleVsUser1 = hmRoleVsUser1.get("Product");
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
	
	<div id="productDialog" style="height:auto" class="max_dialog_Contact web_dialog"> 
       <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
		   <tr><td></td></tr>	     
		   <tr>
		   		<td>
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">	     
		              <tr>
				          <td width="20%" align="left" class="popuptoptitlelarge">Screen Level</td>	
				          <td width="60%" align="center" class="popuptopalert">&nbsp;&nbsp;<span id="techAlert">${message}</span></td>	        
				          <td width="20%" align="right" class="popuptoptitle"><a href="javascript:mstProductCancelClose();" title="Click to Close" class="popupanchor" >X</a>&nbsp;</td>
				      </tr>	 		     
				      <tr><td colspan="2"></td></tr>				      
				   </table>   
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
				      <tr>
				          <td class="formlabelblack" width="24%" align="left"></td>		
			              <td class="formlabelblack" width="28%" align="left"></td>
			              <td class="formlabelblack" width="22%" align="left"></td>
			              <td class="formlabelblack" width="26%" align="left"></td>
				     </tr>
				      <tr>
			             <td class="formlabelblack" align="left">Screen Name&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left" colspan="3">
			             <select name="screenName" id="screenName">
			             		<option value="">Select</option>
			             		<c:forEach items="${lstScreenName}" var="screenName" varStatus="row">    
			                       <option value="${screenName}">${screenName}</option>
			                    </c:forEach>
		                	</select>
			             </td>
			             				             			                 
			         </tr>       				        			         
			         <tr height="2px"></tr>	     			          					 
			         <tr>
			              <td class="formlabelblack" align="left">No. Of Levels&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="noOfLevels" id="noOfLevels" class="textsmall" maxlength="1"/></td>
			            	              
					 </tr>
					 <tr height="2px"></tr>
					 <tr>
					     <td class="formlabelblack" align="left">User Name&nbsp;</td>
			             <td class="formlabelblack" align="left"><div id='example'>
			             	<select name="userName0" id="userName0">
			             		<option value="">Select</option>
			             		<c:forEach items="${lstUserName}" var="userName" varStatus="row">    
			                       <option value="${userName}">${userName}</option>
			                    </c:forEach>
		                	</select>
		                	</div>
			             </td>	
			             
			         </tr>			                
			         <tr height="2px"></tr>			        			        
			         
			         
			         <tr height="2px"></tr>	
			         <tr>
			             <td class="formlabelblack" align="left"><span class="formlabel">*</span>&nbsp;- required fields</td>
			             <td class="formlabelblack" align="left" colspan="3"><input  type="button" class="btn btnImportant" id="btnprocess" value="Save" onclick="javascript:processProduct();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btnNormal" id="btnClear" value="Clear" onclick="javascript:funClearProducts();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="btncancel"  class="btn btnCancel"  value="Close" onclick="javascript:mstProductCancelClose();" /></td>
			         </tr>	
			         <tr height="2px"><td>&nbsp;</td></tr>        
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
	          <td width="25%" class="heading" align="left">&nbsp;Screen Approver Map</td> 
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
		          <td class="searchListLabel" align="left">Search&nbsp;&nbsp;<input type="text" name="searchScreenName" id="searchScreenName" placeholder="Enter Screen Name" value="${searchScreenName}" class="textmediumlarge" maxlength="50"/>&nbsp;&nbsp;<input type="button" name="btnSearch" id="btnSearch" value="Go" class="btn btnSMImportant"></td>  
		       </tr>
		    </table>  
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>  
		  </div>     
		             
		  <c:choose>
			<c:when test="${fn:length(lstUserScreen) gt 0}">
		        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
					 <tr>
						 <td height="5px"></td>		
					 </tr>
			    </table>
		                                
			<table id="lstProduct" name="lstProduct" class="tableHeading">			     
			     <thead>
			          <tr>
				          <th width="30%">Screen Name</th>
				          
				          <th width="30%">Level Number</th>
				          <th width="15%">Action</th>
			          </tr>  
			     </thead> 
			     <tbody>
			         <c:forEach items="${lstUserScreen}" var="lstUserScreen" varStatus="row">  
				          <tr>
				             <td>${lstUserScreen.screenName}</td>   
				             <%-- <td>${lstUserScreen.userName}</td> --%>
				             <td>${lstUserScreen.noOfLevels}</td>
				             <td >&nbsp;&nbsp;&nbsp;&nbsp;
				             	<%
								  if("1".equals(editAccess)){
								%>
				             	<a title="Click to Edit" id="editAnchor${row.index}" href="javascript:funedit('${lstUserScreen.screenId}','${row.index}','${lstUserScreen.screenName}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
				             	<%
				             	}else{
				             	%>
				             	<a title="Access denied" id="editAnchor${row.index}" href="#"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
				             	<%}%>   		             					
								
									
									
										<%
										  if("1".equals(deleteAccess)){
										%>
										<a title="Click to Delete" href="javascript:fundelete('${lstUserScreen.screenId}');" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
										<%
										  }else{
						             	%>  
						             	<a title="Access denied" href="#" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
						             	<%}%>
										
									
									
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
<input type="hidden" name="screenId" id="screenId" value="${screenId}"/>
<input type="hidden" name="isActive" id="isActive" value="${isActive}"/>
<input type="hidden" name="deleteStatus" id="deleteStatus" />
<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />
<input type="hidden" name="userName" id="userName" />
</form>
</body>
</html>

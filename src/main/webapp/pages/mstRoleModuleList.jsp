<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
$("#departmentCode").val("");  

   $(document).ready(function ()
    {	  	
		   $('.tableHeading').fixheadertable({
	       	   caption     : '', 
	           colratio    : [850, 120], 
	           height      : 350, 
	           width       : 970,             
	           sortable    : true, 
	           sortedColId : 0,    
	           unSortColumn: ['Actions'],     
	           resizeCol   : true,            
	           minColWidth : 50,
	           whiteSpace	 : 'nowrap',
	           sortType    : ['string', 'string', 'string', 'string']          
	      });  
		   $("body .ui-widget-content").css("overflow-x","hidden"); 
	                 
	      $("#btnpop").click(function ()              
	      {			      	    	  
	    	  if($("#dynmsg").text() != "")
			  {
			    removemsg();
			  } 	    	
	    	  $("#frmRole").attr("action","viewRoleModule.jsf");   	                
	          $("#frmRole").submit(); 	           
	      });
	      
	      $("#btnpopup").click(function ()        
 	      {		    	
	    	 if($("#dynmsg").text() != "")
			 {
			    removemsg();
			 }  		    	
	    	 $("#frmRole").attr("action","viewRoleModule.jsf");   	                
	         $("#frmRole").submit();               
 	      });
	     	      
	      $("#msgclose").click(function()            
		  {				    	
		  	  $("#msg").fadeOut(300);		  	         
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
	   	      var roleName = $("#roleName").val();
	    	  $("#frmRole").attr("action","deleteRole.jsf");   	                
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

   function funedit(id)  
   {		 
	   $("#roleName").val(id); 
	   $("#frmRole").attr("action","editRoleModule.jsf");    
	   $("#frmRole").submit(); 	    
   }
    
   function fundelete(delid)          
   {   
	   if($("#dynmsg").text() != "")
	   {
		 removemsg();
	   } 
	   $("#roleName").val(delid); 
	   showDeleteDialog();
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
	   var searchVal = trim($("#searchRole").val());   
	   if(searchVal == "")
  		{   	
		   showAllListDialog();
  		}
	   else
 		{   		    
	   	       $("#action").val("list");  	
	 		   $("#loadoverlay").fadeIn();  
	 		   $("#processloading").fadeIn();	 			                    	                  
	 	 	   $("#frmRole").attr("action","searchRoleModule.jsf");   	                
	 	       $("#frmRole").submit();
  		 }	  
   }
   function funAllProcessList()
   {  		    	
	       $("#action").val("listdata");  
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();   	 			                    	                  
 	 	   $("#frmRole").attr("action","showRoleModule.jsf");   	                
 	       $("#frmRole").submit();	      	 	
   }   	  
</script>

</head>
<body class="body" onload="init();">
<form name="frmRole" id="frmRole" method="post" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>
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
	          <td width="25%" class="heading" align="left">&nbsp;Roles</td> 
	          <td width="50%" class="submenutitlesmall" align="center">
	               <table cellspacing="1" cellpadding="2" width="50%" align="center" style="display:none" id="msg">	
				     	<tr>  
					    	<td class="confirmationmessage" align="center">&nbsp;<span id="dynmsg">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="popupanchor" id="msgclose">X</a></td>
					    </tr>
			       </table>
	          </td>
	          <td align="right" width="25%" class="anchorlabel">
			  &nbsp;<input type="button" title="Click to Add" class='btn btnSMNormal' id="btnpop" value="Add"/>&nbsp;			  
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
		          <td class="searchListLabel" align="left">Search&nbsp;&nbsp;<input type="text" name="searchRole" id="searchRole" placeholder="Enter Role Name" value="${searchRole}" class="textmediumlarge" maxlength="50" autofocus/>&nbsp;&nbsp;<input type="button" name="btnSearch" id="btnSearch" value="Go" class="btn btnSMImportant"></td>  
		       </tr>
		    </table>  
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>  
		  </div>     
		             
		  <c:choose>
			<c:when test="${fn:length(lstRoleVsUser) gt 0}">
		        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
					 <tr>
						 <td height="5px"></td>		
					 </tr>
			    </table>
		                                
			<table id="lstRoleVsUser" name="lstRoleVsUser" class="tableHeading">
			      
			     <thead>
			          <tr>
				          <!-- <th width="20%">Module Name</th> -->
				          <th width="85%">Role Name</th>
				          <!-- <th width="35%">Screen Name</th> -->
				          <th width="15%">Action</th>
			          </tr>  
			     </thead> 
			     <tbody>
			         <c:forEach items="${lstRoleVsUser}" var="lstRoleVsUser" varStatus="row">  
				          <tr>
				             <td>${lstRoleVsUser.roleName1}</td>   
				            <%-- <td>${lstRoleVsUser.moduleName1}</td>
				             <td>${lstRoleVsUser.screenName1}</td> --%>
				             <td align="center" style="text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;
				             	<a title="Click to Edit" id="editAnchor${row.index}" href="javascript:funedit('${lstRoleVsUser.roleName1}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;   		             					
								<a title="Click to Delete" href="javascript:fundelete('${lstRoleVsUser.roleName1}');" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
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
<input type="hidden" name="roleModuleId" id="roleModuleId" value="0"/>
<input type="hidden" name="roleName" id="roleName" value=""/>
<input type="hidden" name="message" id="message" value="${message}"/>
</form>
</body>
</html>

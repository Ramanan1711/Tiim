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
	           colratio    : [150, 350, 200,150, 120], 
	           height      : 350, 
	           width       : 970,             
	           sortable    : true, 
	           sortedColId : 0,    
	           unSortColumn: ['Inspection Report No'],     
	           resizeCol   : true,            
	           minColWidth : 50,
	           whiteSpace	 : 'nowrap',
	           pager		 : true,
			   rowsPerPage	 : 10,
	           sortType    : ['integer', 'string', 'string', 'string', 'string']          
	      });  
		   $("body .ui-widget-content").css("overflow-x","hidden"); 
	                 
	      $("#btnpop").click(function ()              
	      {			      	    	  
	    	  if($("#dynmsg").text() != "")
			  {
			    removemsg();
			  } 	 
	    	  $("#toolingRequestId").val("0"); 
	    	  $("#frnToolRecvInsp").attr("action","viewReceivingRequest.jsf");   	                
	          $("#frnToolRecvInsp").submit(); 
	      });
	      
	      $("#btnpopup").click(function ()        
 	      {		    	
	    	 if($("#dynmsg").text() != "")
			 {
			    removemsg();
			 }  	
	    	 $("#toolingRequestId").val("0"); 
	    	 $("#frnToolRecvInsp").attr("action","viewReceivingRequest.jsf");   	                
	         $("#frnToolRecvInsp").submit();
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
	   	   
	    	  $("#frnToolRecvInsp").attr("action","deleteReceivingRequest.jsf");   	                
	          $("#frnToolRecvInsp").submit(); 
   	      });  	        
   	      
   	   $("#btnProcessyes").click(function ()      
  	   	      {		  
  					hideAllListDialog();	
  					funAllProcessList();
  	   	      });
   	   $("#btnProcessclose").click(function ()  
      	      {		    	   	    	  	    			    		    	
  					hideAllListDialog();
  					$("#toolingSearch").focus();
      	      });  
   	    $("#btnProcessno").click(function ()      
      	      {		    	   	    	  	    			    		    	
  	    			hideAllListDialog();	
  	    			$("#toolingSearch").focus();
      	      });   	
   	   $("#toolingSearch").keypress(function(e){
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
	   $("#toolingRequestId").val(id); 
	   $("#frnToolRecvInsp").attr("action","editReceivingRequest.jsf");   	                
	   $("#frnToolRecvInsp").submit();
   }
  /*  var specialElementHandlers = {
		    '#editor': function (element, renderer) {
		        return true;
		    }
		};
   var doc = new jsPDF(); */
   function viewReport(requestId)
   {
	  // demoFromHTML();
	  /*  alert($('#content').html());
	   doc.fromHTML($('#content').html(), 15, 15, {
	        'width': 170,
	            'elementHandlers': specialElementHandlers
	    });
	    doc.save('sample-file.pdf'); */
	   $("#toolingRequestId").val(requestId); 
	   window.open('viewReceivingRequestReport.jsf?toolingRequestId='+requestId, 'window', 'width=700,height=750'); 
	 /*   $("#frnToolRecvInsp").attr("action","editReceivingRequest.jsf");   	                
	   $("#frnToolRecvInsp").submit(); */ 
   }
   
/*    function demoFromHTML() {
       var pdf = new jsPDF('p', 'pt', 'letter');
       alert(pdf);
       // source can be HTML-formatted string, or a reference
       // to an actual DOM element from which the text will be scraped.
       source = $('#content')[0];

       // we support special element handlers. Register them with jQuery-style 
       // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
       // There is no support for any other type of selectors 
       // (class, of compound) at this time.
       specialElementHandlers = {
           // element with id of "bypass" - jQuery style selector
           '#editor': function (element, renderer) {
               // true = "handled elsewhere, bypass text extraction"
               return true
           }
       };
       margins = {
           top: 80,
           bottom: 60,
           left: 40,
           width: 522
       };
       // all coords and widths are in jsPDF instance's declared units
       // 'inches' in this case
       pdf.fromHTML(
       source, // HTML string or DOM elem ref.
       margins.left, // x coord
       margins.top, { // y coord
           'width': margins.width, // max width of content on PDF
           'elementHandlers': specialElementHandlers
       },

       function (dispose) {
           // dispose: object with X, Y of the last line add to the PDF 
           //          this allow the insertion of new lines after html
           pdf.save('Test.pdf');
       }, margins);
   }
     */
   function fundelete(delid)          
   {   
	   if($("#dynmsg").text() != "")
	   {
		 removemsg();
	   } 
	   $("#toolingRequestId").val(delid); 
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
	   var searchVal = trim($("#toolingSearch").val());   
	   if(searchVal == "")
  	   {   	
		   $("#toolingRequestId").val("0"); 
		   showAllListDialog();
  	   }
	   else
 	   {   
		   $("#toolingRequestId").val("0"); 
   	       $("#action").val("list");  	
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();	 			                    	                  
 	 	   $("#frnToolRecvInsp").attr("action","searchReceivingRequest.jsf");   	                
 	       $("#frnToolRecvInsp").submit();
  	   }	  
   }
   function funAllProcessList()
   {  		    	
       $("#toolingRequestId").val("0"); 
       $("#action").val("listdata");  
	   $("#loadoverlay").fadeIn();  
	   $("#processloading").fadeIn();   	 			                    	                  
 	   $("#frnToolRecvInsp").attr("action","showReceivingRequest.jsf");   	                
       $("#frnToolRecvInsp").submit();	      	 	
   }   	  
</script>

</head>
<body class="body" onload="init();">
<form name="frnToolRecvInsp" id="frnToolRecvInsp" method="post" autocomplete="off">
 <%@ include file="tiimMenu.jsp"%>
 <%
	HashMap<String, RoleVsUser> hmRoleVsUser1 = (HashMap<String, RoleVsUser>)session.getAttribute("RoleVsUser");
	RoleVsUser roleVsUser1 = hmRoleVsUser1.get("Receiving Inspection Request");
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
	          <td width="25%" class="heading" align="left">&nbsp;New Tool Inspection Request</td> 
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
			  <input type="button" title="Access denied" class='btn btnSMNormalDisable' id="btnpop" value="Add" disabled="disabled"/>
			  <%}%>
			  &nbsp;			  
			  </td>                             
	     </tr>
	 </table>
       
	<center>           
		<div id="content" style=" width: 1000px; padding:0px; margin: 0px;height:425;min-height:420px;">       
		
		 <div style="width: 980px;background-color:white; border: 1px solid #E0E0E0;;">
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>   
		    <table cellspacing="0" class="searchList" cellpadding="5" border="0" align="center">
		       <tr>
		          <td class="searchListLabel" align="left">Search&nbsp;&nbsp;<input type="text" name="toolingSearch" id="toolingSearch" placeholder="Enter Inspection Request No" value="${toolingSearch}" class="textmediumlarge" autofocus maxlength="50"/>&nbsp;&nbsp;<input type="button" name="btnSearch" id="btnSearch" value="Go" class="btn btnSMImportant"></td>  
		       </tr>
		    </table>  
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>  
		  </div>     
		             
		  <c:choose>
			<c:when test="${fn:length(lstToolinginsInspections) gt 0}">
		        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
					 <tr>
						 <td height="5px"></td>		
					 </tr>
			    </table>
		                                
			<table id="lstFieldTech" name="lstFieldTech" class="tableHeading">
			      
			     <thead>
			          <tr>
				          <th width="20%">Inspection Request No</th>
				          <th width="30%">Inspection Request Date</th>
				          <th width="20%">Supplier Name</th>
				          <th width="15%">Approval Status</th>
				          <th width="15%">Action</th>
			          </tr>  
			     </thead> 
			     <tbody>
			         <c:forEach items="${lstToolinginsInspections}" var="arlToolInspect" varStatus="row">  
				          <tr>
				             <td>${arlToolInspect.originalId}</td>   
				             <td>${arlToolInspect.inspectionReportDate}</td>
				             <td>${arlToolInspect.supplierName}</td>
				             <td>${arlToolInspect.remarks1}</td>
				             <td align="center" style="text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;
				             	<%
								 if("1".equals(editAccess)){
								%> 
								<c:choose>
									<c:when test="${arlToolInspect.reportStatus eq 0}">
									      	<a title="Click to Edit" id="editAnchor${row.index}" href="javascript:funedit('${arlToolInspect.toolingRequestId}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
									 </c:when>
									 <c:otherwise>
									 	<a title="Access denied" id="editAnchor${row.index}" href="#"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
									 </c:otherwise>
								</c:choose>
				             	<%
								 }else{
				             	%>
				             	<a title="Access denied" id="editAnchor${row.index}" href="#"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
				             	<%}%>
				             	<%
								if("1".equals(deleteAccess)){
								%>
				             	<a title="Click to Delete" href="javascript:fundelete('${arlToolInspect.originalId}');" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
				             	<%
								}else{
				             	%>   
				             	<a title="Access denied" href="#" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
				             	<%}%> 		
				             	<%
								if("1".equals(viewAccess)){
								%>
				             	<a title="Print" href="javascript:viewReport('${arlToolInspect.toolingRequestId}');" ><img border="0"  id="viewReportImg${row.index}" style="" src="./images/view_report-icon.png"/></a>
				             	<%
								 }else{
				             	%>
				             	<a title="Access denied" href="#" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/view_report-icon.png"/></a>
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
				<img border="0" style="" src="./images/pencil.png"/>&nbsp;-&nbsp;Edit&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon.gif"/>&nbsp;-&nbsp;Delete&nbsp;</td>     
			</tr>                         	       
	   </table> 
	   <div id="editor"></div>
	</center>    
   

<input type="hidden" name="action" id="action" value="${action}"/>
<input type="hidden" name="toolingRequestId" id="toolingRequestId" value="${toolingRequestId}"/>
<input type="hidden" name="isActive" id="isActive" value="${isActive}"/>
<input type="hidden" name="message" id="message" value="${message}"/>

<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />

</form>
</body>
</html>

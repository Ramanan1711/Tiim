<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cleaning SOP</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>
<style>
td {
    padding: 5px;
	}
</style>
<script>
$(document).ready(function(){ 
	
	$("#grnNo").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoReceiptNoteGrnNo.jsf", {
	           // term: extractLast(request.term)
	        	grnNo: $("#grnNo").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	 $("#autoStatus").html("Data not Available");
                }
             });
	    },
	    search: function () {  // custom minLength
	       	
	    	$("#grnSatus").hide();
	    	//$("#grnNo").val("");
	    	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	 $("#autoStatus").html("");
	    	$("#toolingReceiptId").val(ui.item.id);
	    	funLoadInspectionDetails() ;
	    }
	 });  
	
	$("#srchMinProductName").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoDrawingNo.jsf", {
	           // term: extractLast(request.term)
	        	drawingNo: $("#srchMinProductName").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	 $("#autoStatus").html("Data not Available");
                }
             });
	    },
	    search: function () {  // custom minLength
	       	
	    	$("#grnSatus").hide();
	    	//$("#grnNo").val("");
	    	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	 $("#autoStatus").html("");
	    	$("#srchMinProductName").val(ui.item.id);
	    	funSearchMinProductName(ui.item.id);
	    }
	 }); 
	
});

function addRow(tableID) {

	var table = document.getElementById(tableID);

	var rowCount = table.rows.length;
	var count=0;
	var row = table.insertRow(rowCount);
	var cell1 = row.insertCell(0);
	var element1 = document.createElement("input");
	element1.type = "text";
	element1.name = "cleaningprocess";
	cell1.appendChild(element1);
	
	var cell2 = row.insertCell(1);
	var element2 = document.createElement("input");
	element2.type = "button";
	element2.id = ++count;
	element2.value = "Delete Process";
	element2.className  = "btn btnImportant";
	element2.onclick = function() {deleteRow('dataTable',this)};
	cell2.appendChild(element2);
}

function deleteRow(tableID,rowId) {
		var table = document.getElementById(tableID);
	    var i=rowId.parentNode.parentNode.rowIndex;
		table.deleteRow(i);
	}
function deleteProcess(requestId, rowId)
{
	 $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/deleteProcess.jsf?requestId="+requestId,
	        type : "POST",
	        success : function(result) 
	        {
	  		   if(result != "")
	    	   {
	  				var table = document.getElementById("dataTable");
	  			    var i=rowId.parentNode.parentNode.rowIndex;
	  				table.deleteRow(i);
	    	   }
	        }
	    });
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

 function funLoadInspectionDetails()
 { 
	 $("#toolingRequestId").val("0");
	 $("#frmInspection").attr("action","showAddReceivingRequest.jsf");                 
	 $("#frmInspection").submit();
 }

 function trim(stringTrim)
 {
 	return stringTrim.replace(/^\s+|\s+$/g,"");
 }
 
 function showimg()
 {
 	$("#ajaxLoading").show();  
 }
 
 function processInspection(val)
 {
	 var serialNo = $("#serialNo").val();
	 if(serialNo == "")
     {        	
		    jAlert('Please Enter Serial No', 'Serial No is Empty', function(){	
		    	$("#serialNo").focus();						  			    	 
	        });	
		   return false;
     }
     else if($("#cleaningtype").val() == "")
     {
    	 jAlert('Please Enter the Cleaning Type.', 'cleaning type is Empty');
    	 $("#cleaningtype").focus();		    		
		 return false;		
     }
     else
     {
		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();  
		 
		 if(val == "Save")
		 {
			 $("#toolingRequestId").val("0");
		 	$("#frmInspection").attr("action","addSop.jsf");   
		 }
		 else
		 {
			$("#frmInspection").attr("action","updateSop.jsf");
		 }
	     $("#frmInspection").submit();
     }
 }
 
 function funToolingList()
 {
     $("#serialNo").val(0);
     $("#cleaningId").val(0);
     $("#alertWeeks").val(0);
	 $("#frmInspection").attr("action","mstCleaningSop.jsf");   	                
     $("#frmInspection").submit();
 }
 

 
 function funClear()
 {
	 $("#toolingRequestId").val("0"); 
	 $("#frmInspection").attr("action","viewReceivingRequest.jsf");   	                
     $("#frmInspection").submit(); 
 }
 

 
 

 

</script>

</head>
<body class="body" onload="Init();">
<form name="frmInspection" method="post"  id="frmInspection" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>

<div id="loadoverlay" class="loading_overlay" ></div>
<img id="ajaxLoading" src="./images/ultraloading.jpg" class="loadingPosition"></img>
<div id="overlay" class="web_dialog_overlay" ></div>

    <div id="dialogRule" class="Vertical_web_dialog">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="80%" align="left" class="popuptoptitlelarge">Quick Reference</td>			        			       
				        <td width="18%" align="right" class="popuptoptitle"><a title="Click to Close" class="popupanchor" id="ruleClose">X</a>&nbsp;</td>
				     </tr>
				     <tr>
				         <td colspan="2" height="5px"></td>
				     </tr>	
			    </table>
			     <table style="width:99%" align="center" class="tablesorter" cellpadding="4" cellspacing="0" border="0">		
			     	 <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;"><img src="./images/Mouse-Pointer-icon.png"/></td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Double click on particular row to edit values</td>
				     </tr>		     					     		     			
				     
				      <tr>
				     	 <td align="left"><img src="./images/right_arrow.png"/></td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press right arrow to move forward</td>
				     </tr>
				     <tr>
				     	 <td align="left"><img src="./images/left_arrow.png"/></td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press left arrow to move backward</td>
				     </tr>				    
				     <tr>
				     	 <td align="left"><img src="./images/up_arrow.png"/></td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press up arrow to move upward</td>
				     </tr>
				      <tr>
				     	 <td align="left"><img src="./images/down_arrow.png"/></td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press down arrow to move downward</td>
				     </tr>
				     <tr>
				     	 <td align="left"><img src="./images/enter_key.png"/></td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press enter key to move to next cell</td>
				     </tr>
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Home</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press Home key to move to S.no</td>
				     </tr>
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">End</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press End key to move to Remarks</td>
				     </tr>			     			     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div>
	
   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="10px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Cleaning SOP</td> 
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
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">SERIAL No&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="serialNo" id="serialNo" class="textmin" value="${serialNo}"  maxlength="7"  autofocus autoComplete="off" readonly="readonly"/></td>		
				   </tr>
							     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">TYPE&nbsp;</td>	
						<td class="formlabelblack" align="left">
						<select name="cleaningtype" id="cleaningtype" tabindex="10">
			             		<option value="">Select</option>
			                <c:choose>
					  		    <c:when test="${cleaningtype eq 'recevingcleaning'}">
					  				<option value="recevingcleaning" SELECTED>Receving cleaning</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="recevingcleaning">Receving cleaning</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
				  				<c:when test="${cleaningtype eq 'returncleaning'}">
					  				<option value="returncleaning" SELECTED>Production return cleaning</option>
					  			</c:when>
					  			<c:otherwise>
			                    <option value="returncleaning">Production return cleaning</option>
					  			</c:otherwise>
					  		</c:choose>	
			                </select>	
			          </td>			        
					</tr>	
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Description&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="description" id="description" class="textavg" value="${description}"   maxlength="150"  autofocus autoComplete="off"/></td>
								
				   </tr>
				   
						<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						
						<td class="formlabelblack" align="left">Alert (In Weeks)&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="alertWeeks" id="alertWeeks" class="textavg" value="${alertWeeks}"   maxlength="150"  autofocus autoComplete="off"/></td>
						</tr>				     		    
					<tr>
						<td colspan="3"></td>
					</tr>	
				</table>
				<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0" width="99%" align="center">
					<tr height="15px">
						<td><hr class="style-six"></td>
					</tr>
				</table>
             <!-- END -->
         
             
			  
			    
				 <%
					int index = 1;
				 %>
				
		       
		       <input type="hidden" name="delValue" id="delValue" value="" />
		       <input type="hidden" name="action" id="action" value="${action}"/>
		       <input type="hidden" name="count" id="count" value="${count}" />
		       <input type="hidden" name="message" id="message" value="${message}" />
		
		       <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="99%">&nbsp;</td> 		            				
					</tr>
			 <INPUT type="button" value="Add Process" class="btn btnImportant" onclick="addRow('dataTable')" />
			 </table> 
			     
		<TABLE id="dataTable" width="350px" border="1">
        <c:forEach items="${lstSopDetail}" var="lstSopDetail" varStatus="row">
      		<input type="hidden" name="cleaningId" id="cleaningId" value="${lstSopDetail.cleaningId[row.index]}"/>
       		<c:forEach items="${lstSopDetail.cleaningprocess[row.index]}" var="clean" varStatus="cleanRow">
			<TR>
				<TD> <INPUT type="text" name="cleaningprocess" id="cleaningprocess" value="${clean}"/> </TD>
				<c:choose>
					<c:when test="${action eq 'edit'}">
					  <td><INPUT type="button" value="Delete Process" class="btn btnImportant" id ="${cleanRow.index}" onclick="deleteProcess(${lstSopDetail.cleaningId[row.index]},this)" /></td>
					</c:when>
					<c:otherwise>
					 <td><INPUT type="button" value="Delete Process" class="btn btnImportant" onclick="deleteRow('dataTable')" /></td>
					</c:otherwise>
				</c:choose>
			</TR>
	  		</c:forEach>	
		</c:forEach>	
		</TABLE>
	  		   
			 <table  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="10px">
		           <td height="10px" align="center">&nbsp;</td>
			   </tr>
	        </table> 
     </DIV>
     </center>    
     
     <table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
	    <tr>
           <td height="50px" align="left">
		     <input  type="button" class="btn btnImportant" value="${btnStatusVal}" onclick="javascript:processInspection('${btnStatusVal}');" ${btnStatus} />&nbsp;&nbsp;

		     <input type="button" class="btn btnNormal"  onclick="javascript:funToolingList();" value="List" /></td>
	    </tr>
	 </table>   
	     
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
	 <input type="hidden" name="approvalflag" id="approvalflag" value="1"/>
	 <input type="hidden" name="approvedby" id="approvedby"/>
</form>
</body>
</html>
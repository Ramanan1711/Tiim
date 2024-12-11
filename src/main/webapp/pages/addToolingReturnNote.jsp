<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tooling Receiving Inspection</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<!-- <script type="text/javascript" src="./js/SampleGrid.js"></script>  -->

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<script>
$(document).ready(function(){ 
	
	$("#issueId").autocomplete({
	    source: function (request, response) {
	        $.getJSON("autoReturnIssueId.jsf", {
	           // term: extractLast(request.term)
	        	issueId: $("#issueId").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                    $("#issueStatus").html("Data not Available");
                }
             });
	    },
	    search: function () {  // custom minLength
	       	
	    	$("#grnSatus").hide();
	    	//$("#issueId").val("");
	    	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	alert();
	    	$("#issueStatus").html("");
	    	$("#issueId").val(ui.item.id);
	    	funLoadInspectionDetails() ;
	    }
	 });  
	
});

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
	// $("#toolingInspectionId").val("0");
	 $("#frmInspection").attr("action","fetchToolingReceiveInspection.jsf");                 
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
	 var inspectionReportNo = $("#inspectionReportNo").val();
	 var issueId = $("#issueId").val();
	 if(inspectionReportNo == "")
     {        	
		    jAlert('Please Enter the Report No', 'Inspection Report No is Empty', function(){	
		    	$("#inspectionReportNo").focus();						  			    	 
	        });	
		   return false;
     }
     else if(issueId == "")
     {
    	 jAlert('Please Enter the GRN No.', 'GRN No is Empty');
    	 $("#issueId").focus();		    		
		 return false;		
     }
     else
     {
		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();  
		 
		 if(val == "Save")
		 {
			 $("#toolingInspectionId").val("0");
		 	$("#frmInspection").attr("action","addToolingReceiveInspection.jsf");   
		 }
		 else
		 {
			$("#frmInspection").attr("action","updateToolingInspection.jsf");
		 }
	     $("#frmInspection").submit();
     }
 }
 
 function funToolingList()
 {
	 $("#toolingInspectionId").val("0");
	 $("#inspectionReportNo").val("0");
	 $("#issueId").val("0");
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmInspection").attr("action","showToolingReceiveInspection.jsf");   	                
     $("#frmInspection").submit();
 }
 
 function funTypeissueId(evt,obj)
 {
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode != 8 && charCode != 0 && ((charCode < 48) || (charCode > 57)))
	   {
		   return false;   
	   }
	   else
	   {
		   return true;
	   }
 }
 
 function funTypeReportNo(evt,obj)
 {
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode != 8 && charCode != 0 && ((charCode < 48) || (charCode > 57)))
	   {
		   return false;   
	   }
	   else
	   {
		   return true;
	   }
}
 
 function funClear()
 {
	 $("#frmInspection").attr("action","showAddToolingReceiveInspection.jsf");   	                
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
           <td width="35%" class="heading" align="left">&nbsp;Tooling Receiving Inspection</td> 
           <td width="45%" class="submenutitlesmall" align="center">
               <table cellspacing="1" cellpadding="2" width="57%" height="20px" align="left" style="display:none" id="msg">	
			     	<tr>  
				        <td class="confirmationmessage" align="center">&nbsp;&nbsp;&nbsp;<span id="dynmsg" style="align:center;">${msg}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="msganchor" id="msgclose">X</a></td>
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
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;Return Note</td>
						<td class="formlabelblack" align="left">Return Note&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Return Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="returnId" id="returnId" class="textmin" value="${toolingReturnNote.returnId}"  maxlength="7"  onkeypress="return funTypeReportNo(event,this);"autofocus autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="returnDate" type="text" name="returnDate" class="textmin" value="${toolingReturnNote.returnDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Issue No&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<span id="grnSatus" class="labelAlert">Invalid</span></td>						
						<td width="50%" class="formlabelblack" align="left">&nbsp;Issue Date&nbsp;</td>												
					</tr>	
					<tr>
					    <td><span id="issueStatus" class="popuptopalert">jgkjgkjgkj</span></td>
					    <td class="popuplabel" align="left">
					    	<c:choose>
					            <c:when test="${btnStatusVal eq 'Update'}">
				    				<input id="issueId" type="text" name="issueId" class="textmin" value="${toolingReturnNote.issueId}" maxlength="5"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly  autoComplete="off"/>
				    			</c:when>
				    			<c:otherwise>
				    				<input id="issueId" type="text" name="issueId" class="textmin" value="${toolingReturnNote.issueId}" maxlength="5"  onkeypress="return funTypeissueId(event,this);" autoComplete="off"/>
				    			</c:otherwise>
				    		</c:choose>	
				    	</td>
					    <td class="popuplabel" align="left"><input id="issueDate" type="text" name="issueDate" class="textmin" value="${toolingReturnNote.issueDate}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					</tr>					     		    
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Returned by</td>						
						<td class="formlabelblack" align="left">&nbsp;Issued by&nbsp;</td>						
					</tr>
					<tr>
					    <td></td> 
					    <td class="popuplabel" align="left"><input id="returnBy" type="text" name="returnBy" class="textmin" value="${toolingReturnNote.returnBy}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					    <td class="popuplabel" align="left"><input id="issueBy" type="text" name="issueBy" class="textmin" value="${toolingReturnNote.issueBy}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					</tr>
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Customer Name</td>						
						<td class="formlabelblack" align="left">&nbsp;Branch&nbsp;</td>						
					</tr>
					<tr>
					    <td></td> 
					    <td class="popuplabel" align="left"><input id="customerName" type="customerName" name="returnBy" class="textmin" value="${toolingReturnNote.customerName}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					    <td class="popuplabel" align="left"><input id="branch" type="text" name="branch" class="textmin" value="${toolingReturnNote.branch}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					</tr>
					<tr>
					    <td class="popuplabel" colspan="3" align="left"></td>
					</tr>
					<tr>
						<td></td>							
						<td class="popuplabel"  align="left"><input id="clear" type="button" name="clear" value="Clear All" class="btn btnSMNormal" onclick="javascript:funClear();"/></td>
						<td class="popuplabel"  align="left"></td>									        	        			       	      
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
         
             <table cellspacing="0" width="99%" cellpadding="5" border="0" align="center" height="30px">		      
					<tr>  			 		           
						<td align="right" width="100%" class="labelanchor">
							<img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;"></img>&nbsp;
							<!-- <input type="button" id="btnAddRow" onclick="javascript:funAddNewRow();" value="Add New Row" class="btn btnSMNormal"/> -->&nbsp;
					    </td>
					</tr>
			  </table>    
				<div style="width:1300px;height:auto;min-height:175px;overflow:auto;">  
				 <%
					int index = 1;
				 %>
				<c:choose>
					<c:when test="${fn:length(lstToolingProduct) gt 0}">
					        <table class="tablesorter" style="width:98%" align="center" id="cylinderFieldData">
					         <tr>
					              <th align="center" width="10%">S&nbsp;No.</th>
					              <th align="center" width="10%">Lot&nbsp;Number&nbsp;</th>
					              <th align="center" width="10%">Tooling&nbsp;Name</th>
					              <th align="center" width="10%">Product&nbsp;Name&nbsp;</th>
					              <th align="center" width="10%">Machine&nbsp;Type&nbsp;</th>
								  <th align="center" width="10%">Drawing&nbsp;No.</th>
								  <th align="center" width="10%">Batch&nbsp;Qty&nbsp;</th>
					              <th align="center" width="10%">Produced&nbsp;Qty</th>
					              <th align="center" width="10%">Issued&nbsp;Qty</th>
					              <th align="center" width="10%">Returned&nbsp;Qty</th>
					              <th align="center" width="10%">UOM</th>
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>             
					              <th align="center" width="10%">Tooling&nbsp;Status</th>        
					              <th align="center" width="10%">Tooling&nbsp;Serial&nbsp;Numbers</th>
					         </tr>
					         <c:choose>
					         <c:when test="${btnStatusVal eq 'Update'}">
					         		<c:forEach items="${lstToolingProduct}" var="lstToolingProduct" varStatus="row">  
						                 <tr id="rowid-<%=index%>">
									          <td align="center"><%=index%></td>
									          <td align="center"></td>
									          <td align="center">${lstToolingProduct.toolingname}</td>
									          <td align="center">${lstToolingProduct.poNumber}</td>		          
											  <td align="center">${lstToolingProduct.poDate}</td>
											  <td align="center">${lstToolingProduct.drawingNo}</td>				  
											  <td align="center">${lstToolingProduct.lotNumber}</td>				  
											  <td align="center">${lstToolingProduct.receivedQuantity}</td>
											  <td align="center"><input type="text" name="uom" id="uom<%=index%>" class="txtleastavg"  value="${lstToolingProduct.uom}" maxlength="25" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="fldtoolSerialNo" id="toolSerialNo<%=index%>" class="txtleastavg"  value="" autocomplete="off"/></td>
											  <td align="center">
											  		<select name="inspectionStatus" id="inspectionStatus<%=index%>">
											  		    <c:choose>
												  		    <c:when test="${lstToolingProduct.inspectionStatus1 eq 'Accepted'}">
												  				<option SELECTED>Accepted</option>
												  			</c:when>
												  			<c:otherwise>
												  				<option>Accepted</option>
												  			</c:otherwise>
											  			</c:choose>
											  			<c:choose>
											  				<c:when test="${lstToolingProduct.inspectionStatus1 eq 'Rejected'}">
												  				<option SELECTED>Rejected</option>
												  			</c:when>
												  			<c:otherwise>
												  				<option>Rejected</option>
												  			</c:otherwise>
												  		</c:choose>	
											  			<c:choose>
											  				<c:when test="${lstToolingProduct.inspectionStatus1 eq 'Hold'}">
												  				<option SELECTED>Hold</option>
												  			</c:when>
												  			<c:otherwise>
												  				<option>Hold</option>
												  			</c:otherwise>
												  		</c:choose>
											  		</select>
											  <td align="center">
											  		<input type="text" name="remarks" id="remark<%=index%>" class="textverysmall"  value="${lstToolingProduct.remarks1}" maxlenght="250" autocomplete="off"/>
											        <input type="hidden" name="toolingInspectionDetailId" value="${lstToolingProduct.toolingInspectionDetailId1}"/>
											        <input type="hidden" name="toolingProductId" value="${lstToolingProduct.toolingProductId1}"/>
											  </td>
							           </tr> 
							           <%
							           index++;
							           %>
							           </c:forEach>
					         </c:when>
					         <c:otherwise>
					         		<c:forEach items="${lstToolingProduct}" var="lstToolingProduct" varStatus="row">  
						                 <tr id="rowid-<%=index%>">
									          <td align="center"><%=index%></td>
									          <td align="center"></td>
									          <td align="center">${lstToolingProduct.productName}</td>
									          <td align="center">${poNumber}</td>		          
											  <td align="center">${poDate}</td>
											  <td align="center">${lstToolingProduct.drawingNo}</td>				  
											  <td align="center">${lstToolingProduct.toolingLotNumber}</td>				  
											  <td align="center">${lstToolingProduct.receivedQuantity}</td>
											  <td align="center"><input type="text" name="uom" id="uom<%=index%>" class="txtleastavg"  value="" maxlength="25" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="fldtoolSerialNo" id="toolSerialNo<%=index%>" class="txtleastavg"  value="" autocomplete="off"/></td>
											  <td align="center">
											  		<select name="inspectionStatus" id="inspectionStatus<%=index%>">
											  			<option>Accepted</option>
											  			<option>Rejected</option>
											  			<option>Hold</option>
											  		</select>
											  <td align="center">
											  		<input type="text" name="remarks" id="remark<%=index%>" class="textverysmall"  value="" maxlenght="250" autocomplete="off"/>
											        <input type="hidden" name="toolingProductId" value="${lstToolingProduct.toolingProductId}"/>
											  </td>
							           </tr> 
							           <%
							           index++;
							           %>
							           </c:forEach>
					         </c:otherwise>
					         </c:choose>
					       </table> 	
				    </c:when>
				    <c:otherwise>
				             <table class="tablesorter" style="width:98%" align="center" id="cylinderFieldData">
					         <tr>
					              <th align="center" width="10%">S&nbsp;No.</th>
					              <th align="center" width="10%">Lot&nbsp;Number&nbsp;</th>
					              <th align="center" width="10%">Tooling&nbsp;Name</th>
					              <th align="center" width="10%">Product&nbsp;Name&nbsp;</th>
					              <th align="center" width="10%">Machine&nbsp;Type&nbsp;</th>
								  <th align="center" width="10%">Drawing&nbsp;No.</th>
								  <th align="center" width="10%">Batch&nbsp;Qty&nbsp;</th>
					              <th align="center" width="10%">Produced&nbsp;Qty</th>
					              <th align="center" width="10%">Issued&nbsp;Qty</th>
					              <th align="center" width="10%">Returned&nbsp;Qty</th>
					              <th align="center" width="10%">UOM</th>
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>             
					              <th align="center" width="10%">Tooling&nbsp;Status</th>        
					              <th align="center" width="10%">Tooling&nbsp;Serial&nbsp;Numbers</th>        
					         </tr>
			                 <tr id="rowid-<%=index%>">
						          <td align="center">&nbsp;</td>
						          <td align="center">&nbsp;</td>
						          <td align="center">&nbsp;</td>
						          <td align="center">&nbsp;</td>		          
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>				  
								  <td align="center">&nbsp;</td>				  
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
				           </tr> 		        		      
					       </table>        
				    </c:otherwise>
				 </c:choose>
		       
		       <input type="hidden" name="delValue" id="delValue" value="" />
		       <input type="hidden" name="action" id="action" />
		       <input type="hidden" name="count" id="count" value="${count}" />
		       <input type="hidden" name="gridOrgCount" id="gridOrgCount" value="${gridOrgCount}" />
		       <input type="hidden" name="toolingReceiptId" id="toolingReceiptId" value="${toolingReceiptId}" />
		       <input type="hidden" name="message" id="message" value="${msg}" />
		       <input type="hidden" name="toolingInspectionId" id="toolingInspectionId" value="${toolingInspectionId}" />
		       </div> 
		       <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="99%"><img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp;&nbsp;</td> 		            				
					</tr>
			 </table>       
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
		     <input  type="button" class="${btnSatusStyle}" value="${btnStatusVal}" onclick="javascript:processInspection('${btnStatusVal}');" ${btnStatus} />&nbsp;&nbsp;
		     <input type="button" class="btn btnNormal"  onclick="javascript:funToolingList();" value="List" /></td>
	    </tr>
	 </table>       
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
	 <input type="hidden" name="issueId" id="issueId" value="0"/>
	        
</form>
</body>
</html>
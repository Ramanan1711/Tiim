<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tooling Periodic Inspection Report</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<script>
$(document).ready(function(){ 
	
/////////////////////////////Validation/////////////////////////////
    $("#requestNo").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoPeriodicRequestNo.jsf", {
	           // term: extractLast(request.term)
	        	requestNo: $("#requestNo").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	 $("#autoStatus").html("Data not Available");
                }
             });
	    },
	    search: function () {  // custom minLength
	       	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	$("#autoStatus").html("");
	    	funLoadPeriodicReqDetails(ui.item.id);
	    }
   });
	
    $("#reportedBy").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoRequestBy.jsf", {
	           // term: extractLast(request.term)
	        	requestBy: $("#reportedBy").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	 $("#autoStatus").html("Data not Available");
                }
             });
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
	    	$("#autoStatus").html("");
	    	 setTimeout(function()
   		    {
	    		$("#intervalQty1").focus();
		    	return false;
   		    },250); 
	    }
   });  
	
	$('#rule').click(function(event) {
		 $("#overlay").show();
		 $("#dialogRule").show();	
	});
	  
	$('#ruleClose').click(function(event) {   
		 $("#dialogRule").hide();
		 $("#overlay").hide();
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

 function funLoadPeriodicReqDetails(id)
 { 
	 $("#originalId").val(id);
	 $("#frmPeriodicReport").attr("action","fetchToolingPeriodicReport.jsf");                 
	 $("#frmPeriodicReport").submit();
 }

 function trim(stringTrim)
 {
 	return stringTrim.replace(/^\s+|\s+$/g,"");
 }
 
 function showimg()
 {
 	$("#ajaxLoading").show();  
 }
 
 function processReturn(val)
 {
	 var reportNo = $("#reportNo").val();
	 var requestNo = $("#requestNo").val();
	 var reportedBy = $("#reportedBy").val();
	 var requestQty = $("#requestQty").val();
	 var intervalQty = $("#intervalQty1").val();
	 var goodIntervalQty = $("#goodIntervalQty1").val();
	 var damagedIntervalQty = $("#damagedIntervalQty1").val();
	 if(reportNo == "")
     {        	
		    jAlert('Please Enter the Report No.', 'Report No. is Empty', function(){	
		    	$("#reportNo").focus();						  			    	 
	        });	
		   return false;
     }
     else if(requestNo == "")
     {
    	 jAlert('Please Enter the Request No.', 'Request No. is Empty', function(){	
    	 	$("#requestNo").focus();	
    	 });
		 return false;		
     }
   	 else if(reportedBy == "")
     {
       	  jAlert('Please Enter the Report by', 'Report by is Empty', function(){
	       	 $("#reportedBy").focus();
	        });
   		 return false;		
     }else if(goodIntervalQty == "")
     {
    	 jAlert('Please Enter the Good Qty', 'Good Qty is Empty', function(){
	       	 $("#reportedBy").focus();
	        });
   		 return false;
     }
   	 else if(parseInt(requestQty) < parseInt(intervalQty))
   	 {
   		jAlert('Issue Quantity should be less than Request Quantity', 'Issue Quantity error', function(){
	       	 $("#intervalQty1").focus();
	        });
  		 return false;	
   	 }else if(parseInt(intervalQty) < parseInt(goodIntervalQty))
   	 {
    		jAlert('Inspection Quantity should be greater than or equal to Good Quantity', 'Good Quantity error', function(){
 	       	 $("#goodIntervalQty1").focus();
 	        });
   		 return false;	
    }
   	 else
     {
   		 damagedIntervalQty = parseInt(intervalQty) - parseInt(goodIntervalQty);
   		$("#damagedIntervalQty1").val(damagedIntervalQty);
   		if(parseInt(damagedIntervalQty)>0)
   		{
	   		 var damagedSerialNumber = $("#damagedSerialNumber").val();
			 if(damagedSerialNumber == null || damagedSerialNumber == "")
			 {
				// $("#inspectionStatusAfterInspection1").val("Damaged");
				 jAlert('Please enter Damaged serial number', 'Damaged Serial Number is empty!', function(){	
				    	$("#damagedSerialNumber").focus();						  			    	 
			        });	
				   return false;
			  }
   		} 
		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();  
		 $("#originalId").val(0);
		 if(($("#serilaFlag").val() == 1) && ($("#approvedQty1").val() == null))
		 {
			 jAlert('Please Selecte serial number for Accepted/Rejected', 'Serial Numbers are empty!', function(){	
				 getSerialNumber();		  			    	 
		        });	
			   return false;
		 }else
		 {
			 damagedIntervalQty = parseInt(intervalQty) - parseInt(goodIntervalQty);
		   		$("#damagedIntervalQty1").val(damagedIntervalQty);
		   		if(parseInt(damagedIntervalQty)>0)
		   		{
			   		 var damagedSerialNumber = $("#damagedSerialNumber").val();
					 if(damagedSerialNumber == null || damagedSerialNumber == "")
					 {
						 $("#inspectionStatusAfterInspection1").val("Damaged");
						 jAlert('Please enter Damaged serial number', 'Damaged Serial Number is empty!', function(){	
						    	$("#damagedSerialNumber").focus();						  			    	 
					        });	
						   return false;
					  }
		   		}
		 }
		 if(val == "Save")
		 {
		 	$("#frmPeriodicReport").attr("action","addPeriodicInspectionReport.jsf");   
		 }
		 else
		 {
			$("#frmPeriodicReport").attr("action","updatePeriodicInspectionReport.jsf");
		 }
	     $("#frmPeriodicReport").submit();
     }
 }
 
 function funToolingList()
 {
	 $("#requestNo").val("0");
	 $("#reportNo").val("0");
	 $("#originalId").val(0);
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmPeriodicReport").attr("action","showToolingPeriodicInspectionReport.jsf");   	                
     $("#frmPeriodicReport").submit();
 }
 
 function funTypeIntervalQty(evt,obj)
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
 function funTypeGoodIntervalQty(evt,obj)
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
 function funTypeDamageIntervalQty(evt,obj)
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
	 $("#requestNo").val("0");
	 $("#originalId").val('0');
	 $("#frmPeriodicReport").attr("action","addIntialPeriodicInspectionReport.jsf");   	                
     $("#frmPeriodicReport").submit();
 }
 
 function funSearchRequestNo()
 {
	 $("#productNameListDialog").fadeIn(300);  
	    // $("#srchMinProductName").focus();
	     $("#overlay").unbind("click");  
	     $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getToolingPeriodicalRequestDetail.jsf",
		        type : "POST",
		        success : function(result) 
		        {
		  		   if(result != "")
		    	   {	
		  			  $("#listProjectNames").html(result); 
		  			//  $("#waitloadingProductName").hide();
		    	   }
		        }
		    });
 }
 
 function hideProductNameListDialog()
 {
    $("#productNameListDialog").fadeOut(300);

 }
 
 function funRequestNo(requestNo, originalId)
 {
	 $("#requestNo").val(originalId);
	 $("#originalId").val(requestNo);
	 hideProductNameListDialog();
	 $("#frmPeriodicReport").attr("action","fetchToolingPeriodicReport.jsf");                 
	 $("#frmPeriodicReport").submit();
 }
 
 function getSerialNumber()
 {
	 var lotNumber = $("#toolingLotNumber").val();
	 
		 $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getPeriodiReportcSerialNumber.jsf?lotNumber="+lotNumber,
		        type : "POST",
		        success : function(result) 
		        {
		  		   if(result != "")
		    	   {	
		  			 $("#ajaxLoading").hide();
		  		     $("#serialNumberDialog").fadeIn(300);  
		  			  $("#listSerialNumber").html(result); 
		  			//  $("#waitloadingProductName").hide();
		    	   }
		        }
		    });
 }
 
 function hideSerialNumberDialog()
 {
	 var goodQty = $('.approvedQty:checkbox:checked').length;
	 var intervalQty = $("#intervalQty1").val();
	 $("#goodIntervalQty1").val(goodQty);
	 var damagedIntervalQty = parseInt(intervalQty) - parseInt(goodQty);
	 $("#damagedIntervalQty1").val(damagedIntervalQty);
		
	 $("#serialNumberDialog").fadeOut(300); 
 }
 function changeAttr(value)
 {
	 $("#" + value).attr('checked', false);
 }
</script>

</head>
<body class="body" onload="Init();">
<form name="frmPeriodicReport" method="post"  id="frmPeriodicReport" autocomplete="off">
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
           <td width="35%" class="heading" align="left">&nbsp;Tooling Periodic Inspection Report</td> 
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
        <div id="productNameListDialog" class="max_dialog">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			   <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="18%" align="left" class="popuptoptitlelarge">Product Name</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideProductNameListDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
				     <tr>
				        <td class="formlabelblack" align="center" colspan="2">
				             Search Product Name&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<input  type="text" name="srchMinProductName" id="srchMinProductName" maxlength="150" value="" class="textmedium" />
	                         &nbsp;<input type="button" value="Go" class="btn btnSMImportant" onclick="funSearchMinProductName();"/>
	                    </td>		        			       	       
				     </tr>
				     <tr>
				         <td colspan="2" align="center">				           
					        <img id="waitloadingProductName" style="display:none;" src="./images/ultraloading.jpg" class="loadingPosition"></img>
					     </td></tr>
			   </table>   
	   		</td>
	   </tr>
	   </table>   
       <div id="listProjectNames" style="height:300px;overflow:auto;"></div>                                       
   </div>
   
    <div id="serialNumberDialog" class="max_dialog">
    <br>
    		<table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="18%" align="left" class="popuptoptitlelarge">Periodic Serial Number</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideSerialNumberDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
			 </table>
    	<div id="listSerialNumber" style="height:300px;overflow:auto;"></div>   
    	<table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
		    <tr>
	           <td height="50px" align="left">
			     <input type="button" class="btn btnNormal"  onclick="" value="Select All" />
			     <input type="button" class="btn btnNormal"  onclick="hideSerialNumberDialog();" value="Submit" />
			     </td>
		    </tr>
		</table>       
    </div>
    
   <center>   
         <DIV STYLE="width: 99%; padding:0px; margin: 0px;height:auto;min-height:auto;" class="content">
         
         
             <!-- FIELDS -->
             <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
					<tr height="20px">
					<td></td>
					</tr>
				</table>
				<table style="width:70%" align="left" cellpadding="3" cellspacing="0" border="0" id="returnNoteData">		
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;Report Note</td>
						<td class="formlabelblack" align="left">Report No.&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Report Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="reportNo" id="reportNo" class="textmin" value="${reportNo}"  maxlength="7"  onkeypress="return funTypeReportNo(event,this);" autofocus autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="reportDate" type="text" name="reportDate" class="textmin" value="${reportDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Request No.&nbsp;<span class="formlabel">*</span></td>						
						<td width="50%" class="formlabelblack" align="left">Request Date&nbsp;</td>												
					</tr>	
					<tr>
					    <td></td>
					    <td class="popuplabel" align="left">
					    	<c:choose>
					            <c:when test="${btnStatusVal eq 'Update'}">
				    				<input id="requestNo" type="text" name="requestNo" class="textmin" value="${requestNo}" maxlength="5"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly  autoComplete="off"/>
				    			</c:when>
				    			<c:otherwise>
				    				<input id="requestNo" type="text" name="requestNo" class="textmin" value="${requestNo}" maxlength="5"  autoComplete="off"/>
				    			</c:otherwise>
				    		</c:choose>	&nbsp;<input type="button" value="" onclick="javascript:funSearchRequestNo();" class="btnselect"/>
				    	</td>
					    <td class="popuplabel" align="left"><input id="requestDate" type="text" name="requestDate" class="textmin" value="${requestDate}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					</tr>					     		    
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Requested by</td>						
						<td class="formlabelblack" align="left">Report by&nbsp;<span class="formlabel">*</span></td>						
					</tr>
					<tr>
					    <td></td> 
					    <td class="popuplabel" align="left"><input id="requestedBy" type="text" name="requestedBy" class="textmin" value="${requestedBy}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					    <td class="popuplabel" align="left"><input id="reportedBy" type="text" name="reportedBy" class="textmin" value="${username}" autoComplete="off" maxlength="50" /></td>
					</tr>
					
					<tr>
					    <td class="popuplabel" colspan="3" align="left"><span id="autoStatus" class="popuptopalert"><br></span></td>
					</tr>
					<!-- <tr>
						<td></td>							
						<td class="popuplabel"  align="left"><input id="clear" type="button" name="clear" value="Clear All" class="btn btnSMNormal" onclick="javascript:funClear();"/></td>
						<td class="popuplabel"  align="left"></td>									        	        			       	      
					</tr> 	 -->				
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
							<!-- <img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;"></img>&nbsp;
							<input type="button" id="btnAddRow" onclick="javascript:funAddNewRow();" value="Add New Row" class="btn btnSMNormal"/> -->&nbsp;
					    </td>
					</tr>
			  </table>    
				      
				 <%
					int index = 1;
				 %>
				<c:choose>
					<c:when test="${fn:length(lstPeriodicInspectionReport) gt 0}">
					<div class="table-responsive">
						<table class="table table-bordered" style="width:96%" align="center">
					         <tr>
					              <th align="center" width="10%">S&nbsp;No.</th>
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Machine&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tooling&nbsp;Produced&nbsp;Qty</th>
					              <th align="center" width="10%">Tablet&nbsp;Req&nbsp;Qty</th>
					              <th align="center" width="10%">UOM</th>
					              <th align="center" width="10%">Tooling&nbsp;Status</th>
					              <th align="center" width="10%">Inspection&nbsp;Qty</th>
								  <th align="center" width="10%">Inspection&nbsp;Qty (Good)</th>
								  <th align="center" width="10%">Inspection&nbsp;Qty (Damaged)</th>
					              <th align="center" width="10%">Tooling&nbsp;Status&nbsp;(After Inspection)</th>
					              <th align="center" width="10%">Condition</th>
					              <th align="center" width="10%">Damaged&nbsp;SNo</th>
					              <th align="center" width="10%">Remark</th>
					                   
					         </tr>
					         <c:choose>
					         <c:when test="${btnStatusVal eq 'Update'}">
					         		<c:forEach items="${lstPeriodicInspectionReport}" var="periodicReport" varStatus="row">  
						                 <tr id="rowid-<%=index%>">
									          <td align="center"><%=index%></td>
									          <td align="center">${periodicReport.lotNumber}<input type="hidden" id="toolingLotNumber" name="toolingLotNumber"  value="${periodicReport.lotNumber}"/></td>
									          <td align="center">${periodicReport.productName}</td>
									          <td align="center">${periodicReport.typeOfTool}</td>		          
											  <td align="center">${periodicReport.drawingNo}</td>
											  <td align="center">${periodicReport.machineType}</td>				  
											  <td align="center">${periodicReport.producedQuantity}</td>
											  <td align="center">${periodicReport.requestQuantity}<input type="hidden" name="requestQty" id="requestQty" value="${periodicReport.requestQuantity}"/></td>
											  <td align="center">${periodicReport.uom}</td>
											  <td align="center">${periodicReport.toolingStatus}</td>
											  <td align="center"><input type="text" name="intervalQty" id="intervalQty<%=index%>" class="txtleastavg"  value="${periodicReport.intervalQty1}" maxlength="7" onkeypress="return funTypeIntervalQty(event,this);" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="goodIntervalQty" id="goodIntervalQty<%=index%>" class="txtleastavg"  value="${periodicReport.goodIntervalQty1}" maxlength="7" onkeypress="return funTypeGoodIntervalQty(event,this);" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="damagedIntervalQty" id="damagedIntervalQty<%=index%>" class="txtleastavg"  value="${periodicReport.damagedIntervalQty1}" maxlength="7" onkeypress="return funTypeDamageIntervalQty(event,this);" autocomplete="off"/></td>
											  <td align="center"><select name="inspectionStatusAfterInspection" id="inspectionStatusAfterInspection<%=index%>">
											  	<c:if test="${periodicReport.inspectionStatusAfterInspection1 ne NULL && periodicReport.inspectionStatusAfterInspection1 ne ''}">
											  		<option value="${periodicReport.inspectionStatusAfterInspection1}" selected>${periodicReport.inspectionStatusAfterInspection1}</option>
											 	 </c:if>
											  	<option value="good">Good</option>
  												<option value="Damaged">Damaged</option>
  												<option value="service">Service</option>
											  </select>
											  </td>
											  <td align="center"><input type="text" name="condition" id="condition<%=index%>" class="txtleastavg"  value="${periodicReport.condition1}" maxlength="100" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="damagedSerialNumber" id="damagedSerialNumber" class="txtleastavg"  value="${periodicReport.damagedSerialNumber}" maxlength="250" autocomplete="off"/></td>
											  <td align="center">
										  			<input type="text" name="remark" id="remark<%=index%>" class="txtleastavg"  value="${periodicReport.remark1}" maxlength="100" autocomplete="off"/>
											  		<input type="hidden" name="toolingDetailId" value="${periodicReport.requestDetailId}"/>
											  		<input type="hidden" name="reportDetailNo" value="${periodicReport.reportDetailNo1}"/>
											  </td>
											 
							           </tr> 
							           <%
							           index++;
							           %>
							           </c:forEach>
					         </c:when>
					         <c:otherwise>
					         		<c:forEach items="${lstPeriodicInspectionReport}" var="periodicReport" varStatus="row">  
						                 <tr id="rowid-<%=index%>">
									          <td align="center"><%=index%></td>
									          <td align="center">${periodicReport.lotNumber}<input type="hidden" id="toolingLotNumber" name="toolingLotNumber"  value="${periodicReport.lotNumber}"/></td>
									          <td align="center">${periodicReport.productName}</td>
									          <td align="center">${periodicReport.typeOfTool}</td>		          
											  <td align="center">${periodicReport.drawingNo}</td>
											  <td align="center">${periodicReport.machineType}</td>				  
											  <td align="center">${periodicReport.tabProducedQty1}</td>
											   <td align="center">${periodicReport.requestQuantity}
											   <input type="hidden" name="requestQty" id="requestQty" value="${periodicReport.requestQuantity}"/></td>
											  <td align="center">${periodicReport.uom}</td>
											  <td align="center">${periodicReport.toolingStatus1}</td>
											  <td align="center"><input type="text" name="intervalQty" id="intervalQty<%=index%>" class="txtleastavg"  value="${periodicReport.requestQuantity}" maxlength="7" onkeypress="return funTypeIntervalQty(event,this);" autocomplete="off"/></td>
											   <td align="center"><input type="text" name="goodIntervalQty" id="goodIntervalQty<%=index%>" class="txtleastavg"  value="" maxlength="7" onkeypress="return funTypeGoodIntervalQty(event,this);" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="damagedIntervalQty" id="damagedIntervalQty<%=index%>" class="txtleastavg"  value="" maxlength="7" onkeypress="return funTypeDamageIntervalQty(event,this);" autocomplete="off"/></td>
											  <td align="center">
											  	<select name="inspectionStatusAfterInspection" id="inspectionStatusAfterInspection<%=index%>">
												  	<option value="good">Good</option>
	  												<option value="Damaged">Damaged</option>
	  												<option value="service">Service</option>
											  	</select>
											  </td>
											  <td align="center"><input type="text" name="condition" id="condition<%=index%>" class="txtleastavg"  value="" maxlength="100" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="damagedSerialNumber" id="damagedSerialNumber" class="txtleastavg"  maxlength="250" autocomplete="off"/></td>
											  <td align="center">
											  		<input type="text" name="remark" id="remark<%=index%>" class="txtleastavg"  value="" maxlength="100" autocomplete="off"/>
											  		<input type="hidden" name="toolingDetailId" value="${periodicReport.toolingDetailId1}"/>
											  </td>
											 
							           </tr> 
							           <%
							           index++;
							           %>
							           </c:forEach>
					         </c:otherwise>
					         </c:choose>
					       </table> 	
					     </div>
				    </c:when>
				    <c:otherwise>
				            <div class="table-responsive">
								<table class="table table-bordered" style="width:96%" align="center" id="cylinderFieldData">
					         <tr>
					              
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Machine&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tablet&nbsp;Produced&nbsp;Qty</th>
					              <th align="center" width="10%">UOM</th>
					              <th align="center" width="10%">Tooling&nbsp;Status</th>
					              <th align="center" width="10%">Inspection&nbsp;Qty</th>
								  <th align="center" width="10%">Inspection&nbsp;Qty (Good)</th>
								  <th align="center" width="10%">Inspection&nbsp;Qty (Damaged)</th>
					              <th align="center" width="10%">Tooling&nbsp;Status&nbsp;(After Inspection)</th>
					              <th align="center" width="10%">Condition</th>
					              <th align="center" width="10%">Damaged&nbsp;SNo</th>
					              <th align="center" width="10%">Remark</th>   
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
								  <td align="center">&nbsp;</td>
				           </tr> 		        		      
					       </table>    
					       </div>    
				    </c:otherwise>
				 </c:choose>

		       <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="99%"><!-- <img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp; -->&nbsp;</td> 		            				
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
		     <input  type="button" class="${btnSatusStyle}" value="${btnStatusVal}" onclick="javascript:processReturn('${btnStatusVal}');" ${btnStatus} />&nbsp;&nbsp;
		     <input type="button" class="btn btnNormal"  onclick="javascript:funToolingList();" value="List" />
		     <c:if test="${serilaFlag == 1}">
		  	   <input type="button" class="btn btnNormal"  onclick="getSerialNumber();" value="Serial" />
		     </c:if>
		     <input type="hidden" name="originalId" id="originalId"/></td>
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
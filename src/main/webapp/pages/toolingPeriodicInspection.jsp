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
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<script type="text/javascript" src="./grid/PeriodicalInspection.js"></script>

<script>
$(document).ready(function(){ 
	
	 $("#requestedBy").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoRequestBy.jsf", {
	           // term: extractLast(request.term)
	        	requestBy: $("#requestedBy").val()
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
	    		$("#serialNo1").focus();
		    	return false;
   		    },250);
	    }
   });   
});

  function funFillStockDetails(evt,obj)      
  {
	  var cnt = $("#count").val();
	  $("#toolingLotNo"+cnt).autocomplete({
		    source: function (request, response) {
		        $.getJSON("${pageContext. request. contextPath}/autoStock.jsf", {
		           // term: extractLast(request.term)
		        	lotNo: $("#toolingLotNo"+cnt).val()
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
		    minLength: 0,
		    select: function (event, ui) {
		    	$("#autoStatus").html("");
		    	funLoadStockDetails(ui.item.id) ;
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

 function funLoadStockDetails(stockId)
 { 
	 $.ajax({
		dataType : 'json',
        url : "${pageContext.request.contextPath}/getIndividualStock.jsf?stockId="+parseInt(stockId),
        type : "POST",
        success : function(result) 
        {
        	var cnt = $("#count").val();
        	if(result != "")
	    	{		
        		 $("#stockId"+cnt).val("");
	       		 $("#toolingLotNo"+cnt).val("");
	       		 $("#txtToolingLotNo"+cnt).text(""); 
	       		 $("#txtMachineType"+cnt).text("");
	  	         $("#txtProductName"+cnt).text(""); 
	  	         $("#txtDrawingNo"+cnt).text("");
	  	         $("#txtTypeOfTool"+cnt).text("");
	  	       
	  			 $("#tabProducedQty"+cnt).text("");
	  			 $("#txtUom"+cnt).text("");
        		
        		 $("#stockId"+cnt).val(result.stockId);
        		 $("#toolingLotNo"+cnt).val(result.toolingLotNumber);
        		 $("#txtToolingLotNo"+cnt).text(result.toolingLotNumber); 
        		 $("#txtMachineType"+cnt).text(result.machineName);
	  	         $("#txtProductName"+cnt).text(result.productName); 
	  	         $("#txtDrawingNo"+cnt).text(result.drawingNo);
	  	         $("#txtTypeOfTool"+cnt).text(result.typeOfTool);
	  			 $("#tabProducedQty"+cnt).text("");
	  			 $("#txtUom"+cnt).text(result.uom);
	  			 $("#txtInStock").text(result.stockQty);
	  			 $("#stockQty").val(result.stockQty);	  			
	  			 $("#tabProducedQty"+cnt).val(result.tabletProducedQty);
	  			 	  			  			 
	  			 setTimeout(function()
	  		   	 {
	  				$("#toolingSerialNo"+cnt).focus();
	  				getNextDueDate(result.toolingLotNumber,result.productName,result.machineName,result.drawingNo,result.typeOfTool);
	  				return false;
	  		   	 },200);
	    	}
        }
     });
 }
 
 function getNextDueDate(lotNumber, productName, machineName, drawingNumber, typeOfTool)
	{
		/*var productName = $("#productName1").val();
		var machineName = $("#machingType1").val();
		var drawingNumber = $("#drawingNo1").val();
		var typeOfTool = $("#typeOfTool1").val();
		*/
		$.ajax({
			dataType : 'json',
			async: "false",
	        url : "${pageContext.request.contextPath}/getNextDueQty.jsf?lotNumber="+lotNumber+"&productName="+productName+"&drawingNumber="+drawingNumber+"&machineName="+machineName+"&typeOfTool="+typeOfTool,
	        type : "POST",
	        success : function(data) 
	        {
	        	if(data != "")
		    	{
	        		$("#toolingDueQty1").val(data.nextDueQty);	        		
		    	}
	        }
	     });
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
	 
	 var requestNo = $("#requestNo").val();
	 var requestedBy = $("#requestedBy").val();
	 var toolingLotNo1 = $("#toolingLotNo1").val();
	 var toolingSerialNo1 = $("#toolingSerialNo1").val();
	 var tabProducedQty1 = $("#tabProducedQty1").val();
	 var toolingStatus1 = $("#toolingStatus1").val();
	 var toolingHistory1 = $("#toolingHistory1").val();
	 var  inspectionDueQty1 = $("#toolingDueQty1").val();
	 var requestQty = $('#requestQty1').val();
	 var stockId = $('#stockQty').val();
	 if(requestNo == "")
     {        	
		    jAlert('Please Enter the Request No.', 'Request No. is Empty', function(){	
		    	$("#requestNo").focus();						  			    	 
	        });	
		   return false;
     }
     else if(requestedBy == "")
     {
    	 jAlert('Please Enter the Requested By', 'Requested By is Empty');
    	 $("#requestedBy").focus();		    		
		 return false;		
     }
     else if(toolingLotNo1 == "")
     {
    	 jAlert('Please Enter the Tooling lot number', 'Requested By is Empty');
    	 $("#toolingLotNo1").focus();		    		
		 return false;		
     }
     else if(toolingSerialNo1 == "")
     {
    	 jAlert('Please Enter the Tooling serial number', 'Requested By is Empty');
    	 $("#toolingSerialNo1").focus();		    		
		 return false;		
     }
     else if(tabProducedQty1 == "")
	 {
    	 jAlert('Please Enter the Tooling produced quantity', 'Requested By is Empty');
    	 $("#tabProducedQty1").focus();		    		
		 return false;		
     }
     else if(toolingStatus1 == "")
	 {
    	 jAlert('Please Enter the Tooling status', 'Requested By is Empty');
    	 $("#toolingStatus1").focus();		    		
		 return false;		
     }
     else if(requestQty == "")
     {
    	 jAlert('Please Enter the Request Qty', 'Requested By is Empty');
    	 $("#requestQty1").focus();		    		
		 return false;	
     }	 
     else if(toolingHistory1 == "")
	 {
    	 jAlert('Please Enter the Tooling History', 'Requested By is Empty');
    	 $("#toolingHistory1").focus();		    		
		 return false;		
     }
     else if(inspectionDueQty1 == "")
	 {
    	 jAlert('Please Enter the Inspection due quantitiy', 'Requested By is Empty');
    	 $("#toolingDueQty").focus();		    		
		 return false;		
     }
    /*  else if(parseInt(tabProducedQty1) < parseInt(inspectionDueQty1))
     {
    	 jAlert('Production quantity should be greater than Due quantity', 'Requested By is Empty');
    	 $("#tabProducedQty1").focus();		    		
		 return false;
     } */
     else if(parseInt(requestQty) > parseInt(stockId))
     {
    	 jAlert('Request quantity should be less than Stock quantity', 'Invalid Request Quantity');
    	 $("#tabProducedQty1").focus();		    		
		 return false;
     }
     else
     {
		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();  
		 if(($("#serilaFlag").val() == 1) && ($("#approvedQty1").val() == null))
		 {
			 jAlert('Please Selecte serial number for issue', 'Serial Numbers are empty!', function(){	
				 getSerialNumber();		  			    	 
		        });	
			   return false;
		 }
		 if(val == "Save")
		 {
		 	$("#frmPeriod").attr("action","addPeriodicInspection.jsf");   
		 }
		 else
		 {
			$("#frmPeriod").attr("action","updatePeriodicInspection.jsf");
		 }
	     $("#frmPeriod").submit();
     }
 }
 
 function funToolingList()
 {
	 $("#requestNo").val("0");
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmPeriod").attr("action","showToolingPeriodicInspection.jsf");   	                
     $("#frmPeriod").submit();
 }
  
 function funTypeRequestNo(evt,obj)
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
 
 function funToolingRequestQty(evt,obj)
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
	 $("#frmPeriod").attr("action","showPeriodicInspection.jsf");   	                
     $("#frmPeriod").submit();
 }
 
 function funSearchLotNo()
 {
     $("#productNameListDialog").fadeIn(300);  
    // $("#srchMinProductName").focus();
     $("#overlay").unbind("click");  
     $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getLotNoDetails.jsf",
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
 
 function funLotNo(stockId)
 {
	 $("#productNameListDialog").fadeOut(300);
	 funLoadStockDetails(stockId) ;
 }
 
 function getSerialNumber()
 {
	 var lotNumber = $("#toolingLotNo1").val();
	 
		 $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getPeriodicSerialNumber.jsf?lotNumber="+lotNumber,
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
	// var issueQnty = $("input:checkbox:checked").length;
	// $("#issuedQty1").val(issueQnty);
	 $("#serialNumberDialog").fadeOut(300); 
 }
 function setAllCheck()
 {
	 $('.approvedQty').prop('checked', true);
 }
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmPeriod" method="post"  id="frmPeriod" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>

<div id="loadoverlay" class="loading_overlay" ></div>
<img id="ajaxLoading" src="./images/ultraloading.jpg" class="loadingPosition"></img>
<div id="overlay" class="web_dialog_overlay" ></div>

    <div id="confirmDeleteDialog" class="web_dialog_delete">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="80%" align="left" class="popuptoptitlelarge">Delete</td>			        			       
				        <td width="18%" align="right" class="popuptoptitle"><a href="javascript:hidedeleteDialog();" title="Click to Close" class="popupanchor" id="confrmDelClose">X</a>&nbsp;</td>
				     </tr>	
				     <tr><td colspan="2"></td></tr>
			    </table>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">				     
				     <tr height="20px"></tr>				     
				     <tr><td  class="popuplabel" align="center">Are you sure to Delete?</td></tr>	
				     <tr height="20px"></tr>		     			
				     <tr>					               
				        <td class="formlabelblack" align="center" >
				        <input type="button" value="Yes" class="btn btnSMNormal" onclick="javascript:funConfirmDelete();"/>&nbsp;				            
				        <input type="button" value="No" class="btn btnSMNormal" onclick="javascript:hidedeleteDialog();"/>
				        </td>	        			       	       
				     </tr>					     			     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div> 
	
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
           <td width="35%" class="heading" align="left">&nbsp;Tooling Periodical Inspection Request</td> 
           <td width="55%" class="submenutitlesmall" align="center">
        
               <table cellspacing="1" cellpadding="2" width="57%" height="20px" align="left" style="display:none" id="msg">	
			     	<tr>  
				        <td class="confirmationmessage" align="center">&nbsp;&nbsp;&nbsp;<span id="dynmsg" style="align:center;">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="msganchor" id="msgclose">X</a>
				        
				        </td>
				    </tr>
			   </table>
           </td> 
           <td width="15%" class="submenutitlesmall" align="right">        
           		        
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
				        <td width="18%" align="left" class="popuptoptitlelarge">Periodical Request Serial Number</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideSerialNumberDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
			 </table>
    	<div id="listSerialNumber" style="height:300px;overflow:auto;"></div>   
    	<table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
		    <tr>
	           <td height="50px" align="left">
			     <input type="button" class="btn btnNormal"  onclick="setAllCheck()" value="Select All" />
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
				<table style="width:70%" align="left" cellpadding="3" cellspacing="0" border="0">		
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;Inspection</td>
						<td class="formlabelblack" align="left">Request No.&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Request Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="requestNo" id="requestNo" class="textmin" value="${requestNo}"  maxlength="7"  onkeypress="return funTypeRequestNo(event,this);" autofocus autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="requestDate" type="text" name="requestDate" class="textmin" value="${requestDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Requested By&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<span id="grnSatus" class="labelAlert">Invalid</span></td>						
						<td width="50%" class="formlabelblack" align="left"></td>												
					</tr>	
					<tr>
					    <td></td>
					    <td class="popuplabel" align="left">
					    	<c:choose>
					            <c:when test="${btnStatusVal eq 'Update'}">
				    				<input id="requestedBy" type="text" name="requestedBy" class="textmin" value="${username}" maxlength="50"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly  autoComplete="off"/>
				    			</c:when>
				    			<c:otherwise>
				    				<input id="requestedBy" type="text" name="requestedBy" class="textmin" value="${username}" maxlength="50"  autoComplete="off"/>
				    			</c:otherwise>
				    		</c:choose>	
				    	</td>
					    <td class="popuplabel" align="left"> <span id="autoStatus" class="popuptopalert"></span></td>
					</tr>					     		    
					<tr>
					    <td class="popuplabel" colspan="3" align="left"><br></td>
					</tr>
					<!-- <tr>
						<td></td>							
						<td class="popuplabel"  align="left"><input id="clear" type="button" name="clear" value="Clear All" class="btn btnSMNormal" onclick="javascript:funClear();"/></td>
						<td class="popuplabel"  align="left"></td>									        	        			       	      
					</tr>  -->					
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
         <!-- 
             <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center" height="30px">		      
					<tr>  			 		           
						<td align="right" width="100%" class="labelanchor">
							<img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;"></img>&nbsp;
							<input type="button" id="btnAddRow" onclick="javascript:funAddNewRow();" value="Add New Row" class="btn btnSMNormal"/>&nbsp;&nbsp;
					    </td>
					</tr>
			  </table>     -->

				 <%
					int index = 1;
				%>      
		        <div class="table-responsive">
				<table class="table table-bordered" style="width:96%" align="center" id="periodicData">
		         <tr>
		              
		              <th align="center" width="15%">Tooling&nbsp;Lot&nbsp;Number</th>
		              <th align="center" width="10%">Product&nbsp;Name</th>
		              <th align="center" width="10%">Machine&nbsp;Name</th>
					  <th align="center" width="10%">Drawing&nbsp;No.</th>
					  <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
		              <th align="center" width="10%">Tab&nbsp;Prod&nbsp;Qty</th>
		              <th align="center" width="10%">UOM</th>
		              <th align="center" width="10%">Tooling&nbsp;Status</th>
		              <th align="center" width="10%">Inspection&nbsp;Req&nbsp;Qty</th>   
		              <th align="center" width="10%">In&nbsp;Stock</th>   
		              <th align="center" width="10%">Inspection&nbsp;Due&nbsp;Qty</th>                 
		              <!-- <th align="center" width="10%">Actions</th>  -->
		         </tr>
		         <c:choose>
				    <c:when test="${action eq 'new'}">       
				       <tr id="rowid-1">
					      
					      <td align="center"><input type="text" name="toolingLotNo" id="toolingLotNo1" class="textverysmall"  value="" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo1" ></span>&nbsp;<!-- <input type="button" value="" onclick="javascript:funSearchLotNo();" class="btnselect"/> --></td>
				          <td align="center"><font id="txtProductName1" ></font></td>
				          <td align="center"><font id="txtMachineType1" ></font></td>		          
						  <td align="center"><font id="txtDrawingNo1" ></font></td>				  
						  <td align="center"><font id="txtTypeOfTool1" ></font></td>				  
						  <td align="center"><input type="text" name="tabProducedQty" id="tabProducedQty1" class="textverysmall"  value="" onkeyup="javascript:funToolingProducedQty(event,this);" autocomplete="off"/><span style="display:none;" id="tabProducedQty1"></span></td>
						  <td align="center"><font id="txtUom1"></font></td>
						  <td align="center">
						  		<select name="toolingStatus" id="toolingStatus1">
							  			<option>Good</option>
							  			<option>Damaged</option>
							  			<option>Service</option>
							  	</select>
						  </td>
						  <td align="center"><input type="text" name="requestQuantity" id="requestQty1" class="textverysmall"  value="" onkeydown="javascript:funToolingRequestQty(event,this);" autofocus autocomplete="off"/><span style="display:none;" id="requestQty1"></span></td>
						  <td align="center"><font id="txtInStock" ></font><input type="hidden" name="stockQty1" id="stockQty"/></td>
						  <td align="center">
						  	<input type="text" name="toolingDueQty" id="toolingDueQty1" class="textverysmall"  value="" onkeyup="javascript:funToolingDueQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolingDueQty1"></span>
						  	<input type="hidden" name="stockId" id="stockId1" value=""/>
						  	</td>
						 
				         <!--  <td align="center"><a title="Click to Add Row in Between" style="visibility:hidden;"  href="#"><img border="0" name="midRow" id="newMidRow1" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('1');"><img border="0" src="./images/deleteicon.gif"/></a></td> -->		        
					   </tr>       
			        </c:when>
			        <c:otherwise>
			        		<c:choose>
								<c:when test="${fn:length(lstPeriodicInspection) gt 0}">
								
									<c:forEach items="${lstPeriodicInspection}" var="lstPeriodicDetail" varStatus="row">  	
									    <tr id="rowid-<%=index%>" >
								          
								          <td align="center"><input type="text" name="toolingLotNo" id="toolingLotNo<%=index%>" class="textverysmall"  value="${lstPeriodicDetail.lotNumber}" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo<%=index%>" >${lstPeriodicDetail.lotNumber}</span>&nbsp;<!-- <input type="button" value="" onclick="javascript:funSearchLotNo();" class="btnselect"/> --></td>
								          <td align="center"><font id="txtProductName<%=index%>" >${lstPeriodicDetail.productName}</font></td>
								          <td align="center"><font id="txtMachineType<%=index%>" >${lstPeriodicDetail.machineType}</font></td>
								          		          
										  <td align="center"><font id="txtDrawingNo<%=index%>" >${lstPeriodicDetail.drawingNo}</font></td>				  
										  <td align="center"><font id="txtTypeOfTool<%=index%>" >${lstPeriodicDetail.typeOfTool}</font></td>				  
										  <td align="center"><input type="text" name="tabProducedQty" id="tabProducedQty<%=index%>" class="textverysmall"  value="${lstPeriodicDetail.tabProducedQty1}" onkeyup="javascript:funToolingProducedQty(event,this);" autocomplete="off"/><span style="display:none;" id="tabProducedQty<%=index%>">${lstPeriodicDetail.tabProducedQty1}</span></td>
										  <td align="center"><font id="txtUom<%=index%>" ></font>${lstPeriodicDetail.uom}</td>
										  <td align="center">
										  		<select name="toolingStatus" id="toolingStatus<%=index%>">
											  			<c:if test="${lstPeriodicDetail.toolingStatus1 ne NULL && lstPeriodicDetail.toolingStatus1 ne ''}">
													  		<option value="${lstPeriodicDetail.toolingStatus1}" selected>${lstPeriodicDetail.toolingStatus1}</option>
													 	</c:if>
													  	<option value="Good">Good</option>
		  												<option value="Damaged">Damaged</option>
		  												<option value="Service">Service</option>
											  	</select>
										  </td>
										  <td align="center"><font id="txtUom<%=index%>" ></font><input type="text" name="requestQuantity" id="requestQty1" class="textverysmall"  value="${lstPeriodicDetail.requestQuantity}" onkeydown="javascript:funToolingRequestQty(event,this);" autofocus autocomplete="off"/><span style="display:none;" id="requestQty1"></span></td>
										  <td align="center"><font id="txtStockQty<%=index%>" ></font>${lstPeriodicDetail.stockQty}<input type="hidden" name="stockQty1" id="stockQty" value="${lstPeriodicDetail.stockQty}"/></td>
										  <td align="center">
										  		<input type="text" name="toolingDueQty" id="toolingDueQty<%=index%>" class="textverysmall"  value="${lstPeriodicDetail.inspectionDueQty1}" onkeyup="javascript:funToolingDueQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolingDueQty<%=index%>">${lstPeriodicDetail.inspectionDueQty1}</span>
										  		<input type="hidden" name="stockId" id="stockId<%=index%>" value="${lstPeriodicDetail.stockId1}"/>
										  </td>
										  
								         <%--  <%
								           if(index == 1)
								           {
								          %> 
								          <td align="center"><a title="Click to Add Row in Between" style="visibility:hidden;"  href="#"><img border="0" name="midRow" id="newMidRow1" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('<%=index%>');"><img border="0" src="./images/deleteicon.gif"/></a></td>
								          <% 
								           }
								           else
								           {
								          %>
								          <td align="center"><a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow<%=index%>" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('<%=index%>');"><img border="0" src="./images/deleteicon.gif"/></a></td>
								          <%
								           }
								          %> --%>
							           </tr> 
							           <% index++;%>
							        </c:forEach> 
							           
								</c:when>
								<c:otherwise>
										<tr id="rowid-1" >
									       <td align="center"><input type="text" name="toolingLotNo" id="toolingLotNo1" class="textverysmall"  value="" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo1" ></span>&nbsp;<!-- <input type="button" value="" onclick="javascript:funSearchLotNo();" class="btnselect"/> --></td>
								           <td align="center"><font id="txtProductName1" ></font></td>	
								           <td align="center"><font id="txtMachineType1" ></font></td>
								           	          
										   <td align="center"><font id="txtDrawingNo1" ></font></td>				  
										   <td align="center"><font id="txtTypeOfTool1" ></font></td>				  
										   <td align="center"><input type="text" name="tabProducedQty" id="tabProducedQty1" class="textverysmall"  value="" onkeyup="javascript:funToolingProducedQty(event,this);"  autocomplete="off"/><span style="display:none;" id="tabProducedQty1"></span></td>
										   <td align="center"><font id="txtUom1"></font></td>
										   <td align="center">
										  		<select name="toolingStatus" id="toolingStatus1">
											  			<option>Good</option>
											  			<option>Damaged</option>
											  			<option>Service</option>
											  	</select>
										   </td>
										   <td align="center"><input type="text" name="requestQuantity" id="requestQty1" class="textverysmall"  value="" onkeydown="javascript:funToolingRequestQty(event,this);" autofocus autocomplete="off"/><span style="display:none;" id="requestQty1"></span></td>
						  					<td align="center"><font id="inStock" ></font><input type="hidden" name="stockQty1" id="stockQty"/></td>
										   <td align="center"><input type="text" name="toolingDueQty" id="toolingDueQty1" class="textverysmall"  value="" onkeyup="javascript:funToolingDueQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolingDueQty1"></span></td>
										   <input type="hidden" name="stockId" id="stockId1" value=""/>
										   
								          <%--  <td align="center"><a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow<%=index%>" src="./images/up-arrow.png"/></a>&nbsp;<img border="0" src="./images/deleteicon.gif"/></td> --%>
							           </tr> 
								</c:otherwise>
							</c:choose>
		          </c:otherwise>
		       </c:choose>
		       </table> 
		       </div>
		       <input type="hidden" name="delValue" id="delValue" value="" />
		       <input type="hidden" name="action" id="action" />
		       <c:choose>
				  <c:when test="${action eq 'new'}">
			            <input type="hidden" name="count" id="count" value="1" />
			            <input type="hidden" name="gridOrgCount" id="gridOrgCount" value="1" />
		          </c:when>
		          <c:otherwise>
			            <input type="hidden" name="count" id="count" value="${count}" />
			            <input type="hidden" name="gridOrgCount" id="gridOrgCount" value="${gridOrgCount}" />
		          </c:otherwise> 
		       </c:choose>	
		       
		       <input type="hidden" name="delValue" id="delValue" value="" />
		       <input type="hidden" name="message" id="message" value="${message}" />
		      
		       <!-- <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="99%"><img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp;&nbsp;</td> 		            				
					</tr>
			 </table>     -->   
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
		     <input type="button" class="btn btnNormal"  onclick="javascript:funToolingList();" value="List" />
		     <c:if test="${serilaFlag == 1}">
		  	   <input type="button" class="btn btnNormal"  onclick="getSerialNumber();" value="Serial" />
		     </c:if>		     
	    </tr>
	 </table>       
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
	        
</form>
</body>
</html>
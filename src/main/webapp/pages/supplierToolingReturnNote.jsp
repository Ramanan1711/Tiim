<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
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
$(document).ready(function(){ 
	 $("#requestDiv").hide();
	 $("#periodictDiv").hide();
/* 	 $('.tableHeading').fixheadertable({
     	   caption     : '', 
     	  caption     : '', 
          colratio    : [150, 120, 120, 180, 120, 180, 100, 120], 
          height      : 350, 
          width       : 970,             
          sortable    : true, 
          sortedColId : 0,    
          unSortColumn: [],     
          resizeCol   : true,            
          minColWidth : 50,
          whiteSpace	 : 'nowrap',
          pager		 : true,
		   rowsPerPage	 : 10,
          sortType    : ['string', 'string', 'string', 'string', 'string', 'string', 'string', 'string']            
    });   */
	
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
		    minLength: 2,
		    select: function (event, ui) {
		    	$("#autoStatus").html("");
		    	funLoadStockDetails(ui.item.id) ;
		    }
	   });  
  }	
function funTypeGrnNo(evt,obj)
{
	 $("#grnNo").autocomplete({
		    source: function (request, response) {
		        $.getJSON("${pageContext. request. contextPath}/autoReceiptNoteGrnNoReject.jsf", {
		           // term: extractLast(request.term)
		        	grnNo: $("#grnNo").val()
		        }, response).success(function(data) {
	                if(data.length == 0)
	                {
	                	// $("#autoStatus").html("Data not Available");
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
		    	 //$("#autoStatus").html("");
		    	 $("#grnNo").val(ui.item.id);
		    	 getReceiptDetails();
		    	/* setTimeout(function()
	   		    {
		    		$("#serialNo1").focus();
			    	return false;
	   		    },250); */
		    }
	   });   
}

function funRequestNo()
{
	$("#requestNo").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoToolingReceivingInspection.jsf", {
	           // term: extractLast(request.term)
	        	inpectionId: $("#requestNo").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	// $("#autoStatus").html("Data not Available");
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
	    	 //$("#autoStatus").html("");
	    	 $("#requestNo").val(ui.item.id);
	    	 getToolingReceivingInspectionDetail();
	    	/* setTimeout(function()
   		    {
	    		$("#serialNo1").focus();
		    	return false;
   		    },250); */
	    }
   });   
}

function funPeriodicRequestNo()
{
	$("#periodicRequestNo").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/getAutoPeriodicInspection.jsf", {
	           // term: extractLast(request.term)
	        	inpectionId: $("#periodicRequestNo").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	// $("#autoStatus").html("Data not Available");
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
	    	 //$("#autoStatus").html("");
	    	 $("#periodicRequestNo").val(ui.item.id);
	    	 getPeriodicDetails();
	    	/* setTimeout(function()
   		    {
	    		$("#serialNo1").focus();
		    	return false;
   		    },250); */
	    }
   });   
}

function getReceiptDetails()
{
	 $("#frmSupplierReturn").attr("action","getStockReturnByReceipt.jsf");                 
	 $("#frmSupplierReturn").submit();
}

function getToolingReceivingInspectionDetail()
{
	 
	 $("#frmSupplierReturn").attr("action","getStockReturnByReceivedId.jsf");                 
	 $("#frmSupplierReturn").submit();
}

function getPeriodicDetails()
{
	 $("#frmSupplierReturn").attr("action","getStockReturnByPeriodicId.jsf");                 
	 $("#frmSupplierReturn").submit();
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
	  			 
	  			 setTimeout(function()
	  		   	 {
	  				$("#toolingSerialNo"+cnt).focus();
	  				return false;
	  		   	 },200);
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
	 var  inspectionDueQty1 = $("#inspectionDueQty1").val();
	 
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
     else if(toolingHistory1 == "")
	 {
    	 jAlert('Please Enter the Tooling History', 'Requested By is Empty');
    	 $("#toolingHistory1").focus();		    		
		 return false;		
     }
     else if(inspectionDueQty1 == "")
	 {
    	 jAlert('Please Enter the Inspection due quantitiy', 'Requested By is Empty');
    	 $("#inspectionDueQty1").focus();		    		
		 return false;		
     }
     else
     {
		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();  
		 
		 if(val == "Save")
		 {
		 	$("#frmSupplierReturn").attr("action","addSupplierReturn.jsf");   
		 }
		 else
		 {
			$("#frmSupplierReturn").attr("action","updatePeriodicInspection.jsf");
		 }
	     $("#frmSupplierReturn").submit();
     }
 }
 
 function funSearchRequestId()
 {
     $("#productNameListDialog").fadeIn(300);  
    // $("#srchMinProductName").focus();
     $("#overlay").unbind("click");  
     $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getRejectReceivingProductDetail.jsf",
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
 
 function funSearchReceiptGrn()
 {
	 
	 $("#productNameListDialog").fadeIn(300);  
	    // $("#srchMinProductName").focus();
	     $("#overlay").unbind("click");  
	     $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getRejectPODetail.jsf",
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
 
 
 function funSearchPeriodicRequestId()
 {
     $("#productNameListDialog").fadeIn(300);  
    // $("#srchMinProductName").focus();
     $("#overlay").unbind("click");  
     $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getRejectPeriodicInspectionReportDetail.jsf",
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
 function changeValue()
 {
	 if($("#returnType").val() == "production")
	 {
		 $("#periodictDiv").show();
		 $("#requestDiv").hide();
		 $("#receiptDiv").hide();
	 }
	 else if($("#returnType").val() == "receipt")
	 {
		 $("#requestDiv").show();
		 $("#receiptDiv").hide();
		 $("#periodictDiv").hide();
	 }
	 else if($("#returnType").val() == "po")
	 {
		 $("#receiptDiv").show();
		 $("#requestDiv").hide();
		 $("#periodictDiv").hide();
	 }

 }
 
 function funShowRequestNo(toolingInspectionDetailId1,inspectionReportDate,drawingNo,toolingname,machineType,typeOfTooling,uom,receivedQuantity,lotNumber, requestNo, requestDate, branchName)
 {
	 //$("#txtProductName1").val(toolingname);
	 $("#txtProductName1").text(toolingname);
	 $("#txtToolinglotnumber1").text(lotNumber);
	 $("#txtMachineType1").text(machineType);
	 $("#txtDrawingNo1").text(drawingNo);
	 $("#txtTypeOfTool1").text(typeOfTooling);
	 $("#txtUom1").text(uom);
	 $("#txtReceivedQty1").text(receivedQuantity);

	 $("#productName").val(toolingname);
	 $("#toolingLotNumber").val(lotNumber);
	 $("#machineType").val(machineType);
	 $("#drawingNo").val(drawingNo);
	 $("#typeOfTool").val(typeOfTooling);
	 $("#uom").val(uom);
	 $("#receivedQuantity").val(receivedQuantity);

	 $("#requestNo").val(requestNo);
	 $("#requestNoteDate").val(requestDate);
	 $("#requestId").val(requestNo);
	 $("#requestDate").val(requestDate);
	 $("#branch").val(branchName);

	 hideProductNameListDialog();
 }
 
 function funToolingList()
 {
	 $("#returnNoteId").val("0");
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmSupplierReturn").attr("action","showSupplierReturnNoteList.jsf");   	                
     $("#frmSupplierReturn").submit();
 }
  
 function callMethod()
 {

	 if($("#returnType").val() == "production")
	 {
		funSearchPeriodicRequestId();
	 }
	 else if($("#returnType").val() == "receipt")
	 {
		 funSearchRequestId();
	 }
	 else if($("#returnType").val() == "po")
	 {
		 funSearchReceiptGrn();
	 }
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
 
 function funClear()
 {
	/*  $("#frmSupplierReturn").attr("action","showStockTransfer.jsf");   	                
     $("#frmSupplierReturn").submit(); */
 }
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmSupplierReturn" method="post"  id="frmSupplierReturn" autocomplete="off">
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
           <td width="35%" class="heading" align="left">&nbsp;Tooling Return Note</td> 
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
				             Search Drawing Number&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<input  type="text" name="srchMinProductName" id="srchMinProductName" maxlength="150" value="" class="textmedium" />
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
						<td width="30%" class="tabpopuplabel" align="left">&nbsp;Tooling Return Note</td>
						<td class="formlabelblack" align="left">Return Note No.&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Return Note Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="returnNoteId" id=returnNoteId class="textmin" value="${returnNoteId}"   onkeypress="return funTypeRequestNo(event,this);"autofocus autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="returnNoteDate" type="text" name="returnNoteDate" class="textmin" value="${returnNoteDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>	
					
					<tr>
						<td width="30%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left" colspan="2">Return Type&nbsp;<span class="formlabel">*</span></td>        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left">
							<select id="returnType" name="returnType" onchange="changeValue();">
								<option value="po">PO Validation Failed</option>
								<option value="receipt">Rejection at Receipt</option>
								<option value="production">Rejection at Production</option>
							</select>
						</td>
						
					</tr>	
					
						<tr>
							<td width="30%" class="tabpopuplabel" align="left">&nbsp;</td>
							
							<td class="formlabelblack" align="left"><div id="receiptDiv" >GRN No.&nbsp;<span class="formlabel">*</span></div>
							<div id="requestDiv" >Request No.&nbsp;<span class="formlabel">*</span></div>
							<div id="periodictDiv" >Periodic Request No.&nbsp;<span class="formlabel">*</span></div></td>
							<td class="formlabelblack" align="left">
							<div id="requestDateDiv" >Request Date&nbsp;<span class="formlabel">*</span></div></td>			        	        			       	       
						</tr>
					
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="requestNo" id="requestNo" class="textmin" value="${requestId}"   onkeypress="return funTypeGrnNo(event,this);" autoComplete="off"/>&nbsp;<input type="button" value="" onclick="javascript:callMethod();" class="btnselect"/></td>
						<td class="popuplabel" align="left"><input id="returnNoteDate" type="text" name="returnNoteDate" class="textmin" value="${returnNoteDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>	
					
					<%-- <tr>
						<td width="30%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Request Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="requestNo" id="requestNo" class="textmin" value=""   onkeypress="return funRequestNo(event,this);" autoComplete="off"/>&nbsp;<input type="button" value="" onclick="javascript:funSearchRequestId();" class="btnselect"/></td>
						<td class="popuplabel" align="left"><input id="returnNoteDate" type="text" name="returnNoteDate" class="textmin" value="${returnNoteDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>	
					
					<tr>
						<td width="30%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Request Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="periodicRequestNo" id="periodicRequestNo" class="textmin" value=""   onkeypress="return funPeriodicRequestNo(event,this);" autoComplete="off"/>&nbsp;<input type="button" value="" onclick="javascript:funSearchPeriodicRequestId();" class="btnselect"/></td>
						<td class="popuplabel" align="left"><input id="requestNoteDate" type="text" name="requestNoteDate" class="textmin" value="${returnNoteDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>	 --%>
					
					<%-- <tr>
						<td width="30%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Supplier Code&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Supplier Name&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="supplierCode" id=supplierCode class="textmin" value="${supplierCode}"   onkeypress="return funTypeRequestNo(event,this);"autofocus autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="supplierName" type="text" name="supplierName" class="textmin" value="${returnNoteDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr> --%>
					
					<tr>
						<td width="30%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Branch&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left"></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="branch" id=branch class="textmin" value="${branch}"   onkeypress="return funTypeRequestNo(event,this);"autofocus autoComplete="off"/></td>
						<td class="popuplabel" align="left"></td>
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
		        <div class="table-responsive">
				<table class="tableHeading" style="width:96%" align="center" id="periodicData">
		         <tr>
		              
		              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
		              <th align="center" width="10%">Product&nbsp;Name</th>
		              <th align="center" width="10%">Machine&nbsp;Name</th>
					  <th align="center" width="10%">Drawing&nbsp;No.</th>
					  <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					  <th align="center" width="10%">UOM</th>
		             <!--  <th align="center" width="10%">Customer&nbsp;PO&nbsp;No</th>
		              <th align="center" width="10%">Customer&nbsp;PO&nbsp;Date</th>           
		              <th align="center" width="10%">Inspection Report No</th>
		              <th align="center" width="10%">Supplier Invoice No</th>
		              <th align="center" width="10%">Supplier Invoice Date</th> -->
		              <th align="center" width="10%">Received&nbsp;Qty</th>
		              
		         </tr>
		         <c:choose>
				    <c:when test="${action eq 'new'}">       
				       <tr id="rowid-1">
					      
					      <td align="center"><font id="txtToolinglotnumber1" ></font><input type="hidden" name="toolingLotNumber" id="toolingLotNumber"/></td>
				          <td align="center"><font id="txtProductName1" ></font><input type="hidden" name="productName" id="productName"/></td>
				          <td align="center"><font id="txtMachineType1" ></font><input type="hidden" name="machineType" id="machineType"/></td>		          
						  <td align="center"><font id="txtDrawingNo1" ></font><input type="hidden" name="drawingNo" id="drawingNo"/></td>				  
						  <td align="center"><font id="txtTypeOfTool1" ></font><input type="hidden" name="typeOfTool" id="typeOfTool"/></td>
						  <td align="center"><font id="txtUom1"></font><input type="hidden" name="uom" id="uom"/></td>				  
						 <!--  <td align="center"><font id="txtCustomerPoNo1"></font></td>
						 <td align="center"><font id="txtCustomerPoDate1"></font></td>
						  <td align="center"><font id="txtReportNo"></font></td>
						  <td align="center"><font id="txtInvoiceNo1"></font></td>
						  <td align="center"><font id="txtInvoiceDate1"></font></td> -->
						  <td align="center"><font id="txtReceivedQty1"></font><input type="hidden" name="receivedQuantity" id="receivedQuantity"/></td>
				          	        
					   </tr>       
			        </c:when>
			        <c:otherwise>
			        		<c:choose>
								<c:when test="${count eq '1'}">
								
									 <td align="center"><font id="txtToolinglotnumber1" >${supplierReturn.toolingLotNumber}</font><input type="hidden" name="toolingLotNumber" id="toolingLotNumber" value="${supplierReturn.toolingLotNumber}"/></td>
							          <td align="center"><font id="txtProductName1" >${supplierReturn.productName}</font><input type="hidden" name="productName" id="productName" value="${supplierReturn.productName}"/></td>
							          <td align="center"><font id="txtMachineType1" >${supplierReturn.machineType}</font><input type="hidden" name="machineType" id="machineType" value="${supplierReturn.machineType}"/></td>		          
									  <td align="center"><font id="txtDrawingNo1" >${supplierReturn.drawingNo}</font><input type="hidden" name="drawingNo" id="drawingNo" value="${supplierReturn.drawingNo}"/></td>				  
									  <td align="center"><font id="txtTypeOfTool1" >${supplierReturn.typeOfTool}</font><input type="hidden" name="typeOfTool" id="typeOfTool" value="${supplierReturn.typeOfTool}"/></td>
									  <td align="center"><font id="txtUom1">${supplierReturn.uom}</font><input type="hidden" name="uom" id="uom" value="${supplierReturn.uom}"/></td>				  
									  <td align="center"><font id="txtReceivedQty1">${supplierReturn.receivedQuantity}</font><input type="hidden" name="receivedQuantity" id="receivedQuantity" value="${supplierReturn.receivedQuantity}"/></td>
							         	           
								</c:when>
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
		       <input type="hidden" name="requestId" id="requestId" value="${requestId}" />
		       <input type="hidden" name="requestDate" id="requestDate" value = "${requestDate}" />
		       <input type="hidden" name="message" id="message" value="${message}" />
		      
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
	 
	        
</form>
</body>
</html>
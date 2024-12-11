<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="./css/bootstrap.min.css">
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
	
	$("#srchMinProductName").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/getProductDetailsByLot.jsf", {
	           // term: extractLast(request.term)
	        	toolinglotnumber: $("#srchMinProductName").val()
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
	    	$("#srchMinProductName").val(ui.item.id);
	    	funSearchMinProductName(ui.item.id);
	    }
   });  
   
 });
 

 function prodQty() {
             var batchnos1 = document.getElementById('batchnos1').value;
             var batchProd1 = document.getElementById('batchProd1').value;
 			var result = parseInt(batchnos1) * parseInt(batchProd1);
             if (!isNaN(result)) {
                 document.getElementById('batchQty1').value = result;
             }
         }
 
 
function funFillProductName(evt,obj)      
{
	var cnt = $("#count").val();
	$("#inStock1").val("");
	$("#inStock1").text("");
	$("#productName"+cnt).autocomplete({
		source: function (request, response) {
		$.getJSON("${pageContext. request. contextPath}/autoProductName.jsf", {
			productName: $("#productName"+cnt).val()
		}, response).success(function(data) {
            if(data.length == 0)
            {
            	 $("#autoStatus").html("Data not Available");
            }
         });
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
		$("#autoStatus").html("");
		funLoadProductDetails(ui.item.id);
	}
	});  
}

function funFillMachineName(evt,obj)      
{
	var cnt = $("#count").val();
	$("#machingType"+cnt).autocomplete({
		source: function (request, response) {
		$.getJSON("${pageContext. request. contextPath}/autoMachineName.jsf", {
			machineName: $("#machingType"+cnt).val()
		}, response).success(function(data) {
            if(data.length == 0)
            {
            	 $("#autoStatus").html("Data not Available");
            }
         });
	},
	search: function () {  // custom minLength
		//console.log($(this).val());
	},
	focus: function () {
		// prevent value inserted on focus
		return false;
	},
	autoFocus: true,
	minLength: 1,
	select: function (event, ui) {
		$("#autoStatus").html("");
		$("#machingType"+cnt).val(ui.item.id);
		setTimeout(function()
	  	{
			$("#inStock1").val("");
  			$("#inStock1").text("");
			getStockQty( $("#productName"+cnt).val(), $("#machingType"+cnt).val(), $("#drawingNo"+cnt).val(), $("#typeOfTool"+cnt).val());
	  		return false;
	  	 },200);
		//funLoadProductDetails(ui.item.id);
	}
	});  
}

	function loadStockQuantity()
	{
		$("#inStock1").val("");
		$("#inStock1").text("");
		getStockQty( $("#productName1").val(), $("#machingType1").val(), $("#drawingNo1").val(), $("#typeOfTool1").val());
	}

/* function funFillTypeOfTool(evt,obj)      
{
	var cnt = $("#count").val();
	$("#typeOfTool"+cnt).autocomplete({
		source: function (request, response) {
		$.getJSON("${pageContext. request. contextPath}/autoGetTypeOfTool.jsf", {
			typeOfTool: $("#typeOfTool"+cnt).val()
		}, response).success(function(data) {
            if(data.length == 0)
            {
            	 $("#autoStatus").html("Data not Available");
            }
         });
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
		$("#autoStatus").html("");
		$("#typeOfTool"+cnt).val(ui.item.id);
		//funLoadProductDetails(ui.item.id);
	}
	});  
}
 */
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
        		 $("#txtTypeOfTool"+cnt).text(result.typeOfTool);
        		
        		// $("#typeOfTool"+cnt).val(result.typeOfTool); 
        		 $("#txtProductName"+cnt).text(result.productName);
	  	         $("#productName"+cnt).val(result.productName); 
	  	         $("#txtDrawingNo"+cnt).text(result.drawingNo);
	  	  	     $("#drawingNo"+cnt).val(result.drawingNo);	   
	  	  	     $("#txtMachingType"+cnt).text(result.machineType);
	  			// $("#machingType"+cnt).val(result.machineType);
	  			$("#uom"+cnt).val(result.uom);
	  			$("#txtUom"+cnt).text(result.uom);
	  			 setTimeout(function()
	  		   	 {
	  				getStockQty(result.productName, result.machineType, result.drawingNo, result.typeOfTool);
	  				//$("#batchQty"+cnt).focus();
	  				return false;
	  		   	 },200);
	    	}
        }
     });
 }
  
  function getStockQty(productName, machineType, drawingNo, typeOfTool)
  {
	  $.ajax({
			dataType : 'json',
	        url : "${pageContext.request.contextPath}/getStockLotQty.jsf?productName="+productName+"&machineType="+machineType+"&drawingNo="+drawingNo+"&typeOfTool="+typeOfTool,
	        type : "POST",
	        success : function(result) 
	        {
	        	var cnt = $("#count").val();
	        	if(result != "")
		    	{			
	        		 
		  			$("#inStock1").val(result);
		  			$("#inStock1").text(result);
		  			 setTimeout(function()
		  		   	 {
		  				getUnderInspectionQty(productName, machineType, drawingNo, typeOfTool);
		  				//return false;
		  		   	 },200);
		    	}
	        }
	     });
  }
  
  function getUnderInspectionQty(productName, machineType, drawingNo, typeOfTool)
  {
	  $.ajax({
			dataType : 'json',
	        url : "${pageContext.request.contextPath}/getUnderInspectionQty.jsf?productName="+productName+"&machineType="+machineType+"&drawingNo="+drawingNo+"&typeOfTool="+typeOfTool,
	        type : "POST",
	        success : function(result) 
	        {
	        	var cnt = $("#count").val();
	        	if(result != "")
		    	{			
	        		 
		  			$("#underInspection"+cnt).val(result);
		  			 setTimeout(function()
		  		   	 {
		  				$("#batchQty"+cnt).focus();
		  				return false;
		  		   	 },200);
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

 function trim(stringTrim)
 {
 	return stringTrim.replace(/^\s+|\s+$/g,"");
 }
 
 function showimg()
 {
 	$("#ajaxLoading").show();  
 }
 
 function funSearchMinProductName(toolinglotnumber)
 {
	 $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getProductDetailsByLot.jsf?toolinglotnumber="+toolinglotnumber,
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
 
 function funSearchRequestNo()
 {
	 $("#machingType1").val("");
	 $("#inStock1").val("");
	 $("#typeOfTool1").val("Select")
     $("#productNameListDialog").fadeIn(300);  
    // $("#srchMinProductName").focus();
     $("#overlay").unbind("click");  
     $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getProductDetailsByLot.jsf",
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
 
 function funShowDrawingNo(productName, drawingNo, toolinglotnumber, punchSetNo, compForce, dustCup)
 {
	 hideProductNameListDialog();
	// var cnt = $("#count").val();
	 var cnt = 1;
 			 $("#txtProductName"+cnt).text(productName);
	         $("#productName"+cnt).val(productName); 
	         $("#txtDrawingNo"+cnt).text(drawingNo);
	  	     $("#drawingNo"+cnt).val(drawingNo);	
	  	     $("#punchSetNo").val(punchSetNo);	
	  		 $("#compForce").val(compForce);	
	  		$("#dustCup").val(dustCup);	
	  	     $("#txtToolingLotNumber"+cnt).text(toolinglotnumber);
	  	     $("#toolingLotNumber"+cnt).val(toolinglotnumber);
	  	     
 }
 
 
 function hideProductNameListDialog()
 {
    $("#productNameListDialog").fadeOut(300);

 } 
 
 function processRequestNote(val)
 {
	 var requestId = trim($("#requestId").val());
	 var requestBy = trim($("#requestBy").val());
	 var productName = trim($("#productName1").val());
	 var drawingNo = trim($("#drawingNo1").val());
	 var uom = trim($("#uom1").val());
	 var stock = trim($("#inStock1").val());
	// var dustCup = trim($("#dustCup").val());
	 var requestQty = trim ($("#receivedQty1").val());
	 if(requestId == "")
     {        	
		    jAlert('Please Select Request No', 'Request No is Empty', function(){	
		    	$("#requestId").focus();						  			    	 
	        });	
		   return false;
     }
     else if(requestBy == "")
     {
    	 jAlert('Please Enter the Requested By', 'Requested By is Empty');
    	 $("#requestBy").focus();		    		
		 return false;		
     }
     
     else if(drawingNo == "")
     {
    	 jAlert('Please Enter the Drawing number', 'DrawingNo By is Empty');
    	 $("#productName1").focus();		    		
		 return false;		
     }else if(productName == "")
     {
    	 jAlert('Please Enter the ProductName', 'ProductName By is Empty');
    	 $("#drawingNo1").focus();		    		
		 return false;		
     }else if(uom == "")
     {
    	 jAlert('Please Enter the UOM', 'UOM is Empty');
    	 $("#drawingNo1").focus();		    		
		 return false;		
     }else if(stock == "")
     {
    	 jAlert('Please Enter the stock', 'Stock is Empty');
    	 $("#drawingNo1").focus();		    		
		 return false;		
     }
    /*  else if(dustCup == "")
     {
    	jAlert('Please Select the DustCup','DustCup is Empty'); 
    	$("#dustCup").focus();
    	return false;
     } */
     else if(requestQty == ""){
		 jAlert('Please enter the request quantity','RequestQnty is Empty'); 
	    	$("#receivedQty1").focus();
	    	return false;
	 }
     else if(parseInt(stock) < parseInt(requestQty)){
    	 jAlert('Request Quantity should not be exceeded Stock Quantity','Invalid Request Quantity'); 
	    	$("#receivedQty1").focus();
	    	return false;
     }
     else
     {
		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();   	 			                    	                  
		 
		 if(val == "Save")
		 {
			 $("#frmRequestNote").attr("action","addToolingRequestNote.jsf");   	      
		 }
		 else
		 {
			$("#frmRequestNote").attr("action","updateToolingRequestNote.jsf");
		 }
	     $("#frmRequestNote").submit();
     }
 }
 
 function funRequestNoteList()
 {
	 $("#requestId").val("0");
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmRequestNote").attr("action","showToolingRequest.jsf");   	                
     $("#frmRequestNote").submit();
 }
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmRequestNote" method="post"  id="frmRequestNote" autocomplete="off">
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
           <td width="35%" class="heading" align="left">&nbsp;Production Tool Request</td> 
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
				        <td width="18%" align="left" class="popuptoptitlelarge">Product Details</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideProductNameListDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
				     <tr>
				        <td class="formlabelblack" align="center" colspan="2">
				             Search Att Number&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<input  type="text" name="srchMinProductName" id="srchMinProductName" maxlength="150" value="" class="textmedium" />
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
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Request No&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Request Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left">
						<input type="hidden" name="requestId" id="requestId" class="textmin" value="${requestId}" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/>
						<input type="text" value="${originalId}" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/>
						</td>
						<td class="popuplabel" align="left"><input type="text" id="requestDate"  name="requestDate" class="textmin" value="${requestDate}"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Requested By&nbsp;<span class="formlabel">*</span></td>						
						<td width="50%" class="formlabelblack" align="left"></td>												
					</tr>	
					<tr>
					    <td></td>

					    <td class="popuplabel" align="left"><input id="requestBy" type="text" name="requestBy" class="txtreal" value="${username}" maxlength="50" autofocus/></td>
					    <td class="popuplabel" align="left"></td>
					</tr>					     		    
					<tr>
					    <td class="popuplabel" colspan="3" align="left"></td>
					</tr>
				</table>
				<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0" width="99%" align="center">
					<tr height="15px">
						<td><hr class="style-six"><span id="autoStatus" class="popuptopalert"></span></td>
					</tr>
				</table>
             <!-- END -->
         
             <!-- <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center" height="30px">		      
					<tr>  			 		           
						<td align="right" width="100%" class="labelanchor">
							<img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;"></img>&nbsp;
							<input type="button" id="btnAddRow" onclick="javascript:funAddNewRow();" value="Add New Row" class="btn btnSMNormal"/>&nbsp;&nbsp;
					    </td>
					</tr>
			  </table> -->    
				
				<%
					int index = 1;
				%>      
		        <div class="table-responsive">
				<table class="table table-bordered" style="width:98%" align="center" id="requestNoteData">
		         <tr>
		         <th align="center" width="13%">Lot&nbsp;Number</th>
		         <th align="center" width="10%">Punch Set&nbsp;No.</th>
		              <th align="center" width="10%">Drawing&nbsp;No.</th>
		              <th align="center" width="10%">Max Comp Force Limit</th>
		              <th align="center" width="10%">DustCup</th>
		              <th align="center" width="10%">Product&nbsp;Name</th>
		              <th align="center" width="10%">Machine&nbsp;Type</th>
		              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					  <th align="center" width="10%">No&nbsp;of&nbsp;Batch</th>
					  <th align="center" width="10%">Batch&nbsp;Qty&nbsp;</th>
					  <th align="center" width="10%">Production&nbsp;Qty&nbsp;</th>
		              <th align="center" width="10%">Tooling&nbsp;Req&nbsp;Qty</th>
		              <th align="center" width="10%">UOM</th>
		              <th align="center" width="10%">In&nbsp;Stock</th>
		             <!--  <th align="center" width="10%">Market</th>
		              <th align="center" width="10%">DustCup</th> -->
		              <!-- <th align="center" width="10%">Under&nbsp;Inspection</th>      -->        
		         </tr>
		         <c:choose>
				    <c:when test="${action eq 'new'}">       
				       <tr id="rowid-1">
				          <td align="center"><input type="text" name="toolingLotNumber" id="toolingLotNumber1" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/>&nbsp;<input type="button" value="" onclick="javascript:funSearchRequestNo();" class="btnselect" readonly/><span style="display:none;" id="txtToolingLotNumber1" ></span></td>				       
				          <td align="center"><input type="text" name="punchSetNo" id="punchSetNo" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/>&nbsp;<span style="display:none;" id="txtDrawingNo1" ></span></td>
				          <td align="center"><input type="text" name="drawingNo" id="drawingNo1" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/>&nbsp;<span style="display:none;" id="txtDrawingNo1" ></span></td>
					     <td align="center"><input type="text" name="compForce" id="compForce" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/>&nbsp;<span style="display:none;" id="txtDrawingNo1" ></span></td>
					      <td align="center"><input type="text" name="dustCup" id="dustCup" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/>&nbsp;<span style="display:none;" id="txtDrawingNo1" ></span></td>
					      <td align="center"><input type="text" name="productName" id="productName1" class="textsmall"  value="" onkeydown="javascript:funProductName(event,this);" onkeyup="javascript:funFillProductName(event,this);" autocomplete="off"readonly/><span style="display:none;" id="txtProductName1" ></span></td>		          
						 				  
						  <td align="center"><input type="text" name="machingType" id="machingType1" class="textverysmall"  value="" onkeyup="javascript:funFillMachineName(event,this);" autocomplete="off"/><span style="display:none;" id="txtMachingType1" ></span></td>
						 <td align="center">
						 	<select name="typeOfTool" id="typeOfTool1" onchange="loadStockQuantity();">
			             		<option value="">Select</option>
			                    <option value="D/D">D/D</option>
			                    <option value="B/B">B/B</option>
			                    <option value="D/B"> D/B</option>
			                    <option value="B/BB">B/BB</option>
			                    <option value="B/BBS">B/BBS</option>
			                </select>
						 <!-- <input type="text" name="typeOfTool" id="typeOfTool1" class="textverysmall"  value="" onkeyup="javascript:funFillTypeOfTool(event,this);" autocomplete="off"/> -->
						 <span style="display:none;" id="txtTypeOfTool1" ></span></td>
						   <td align="center"><input type="text" name="batchnos" id="batchnos1" class="textverysmall"  value="" onkeyup="javascript:funBatchQty(event,this);" maxlength="150" autocomplete="off"/><span style="display:none;" id="txtBatchnos1"></span></td>
						    <td align="center"><input type="text" name="batchProd" id="batchProd1" class="textverysmall"  value="" onkeyup="javascript:funBatchQty(event,this);" maxlength="150" autocomplete="off"/><span style="display:none;" id="txtBatchProd1"></span></td>
						  <td align="center"><input type="text" name="batchQty" id="batchQty1" class="textverysmall"  value="" onclick="javascript:prodQty(event, this);" onkeyup="javascript:prodQty(event, this); " maxlength="150" autocomplete="off" readonly/><span style="display:none;" id="txtBatchQty1"></span></td>				  
						  <td align="center"><input type="text" name="requestQty" id="receivedQty1" class="textverysmall"  value="" onkeyup="javascript:funReceivedQty(event,this);" maxlength="150" autocomplete="off"/><span style="display:none;" id="txtReceivedQty1" ></span></td>
						  <td align="center"><input type="text" name="UOM" id="uom1" class="textverysmall"  value="SETS" onkeyup="javascript:funUOM(event,this);" autocomplete="off" readonly /><span style="display:none;" id="txtUom1" ></span></td>
						  <td align="center"><input type="text" name="inStock" id="inStock1" class="txtleast"  value="" onkeyup="javascript:funInStock(event,this);" maxlength="150" autocomplete="off" readonly/><span style="display:none;" id="txtInStock1" ></span></td>
						  <!-- <td align="center"><input type="text" name="market" id="market1" class="txtleast"  value=""  maxlength="150" autocomplete="off"/><span style="display:none;" id="txtMarket1"  ></span></td> -->
						 <!--  <td align="center">
						  
						 <select name="dustCup" id="dustCup" >
						  <option value="">Select</option>
						  <option value="Required">Required</option>
						  <option value="Not Required">Not Required</option>
						  </select >
						  </td> -->
						  			  
						  <!-- <td align="center"><input type="text" name="underInspection" id="underInspection1" class="textverysmall"  value="" onkeyup="javascript:funUnderInspection(event,this);" maxlength="150" autocomplete="off"/><span style="display:none;" id="txtUnderInspection1"></span></td> -->
				          <!-- <td align="center"><a title="Click to Add Row in Between" style="visibility:hidden;"  href="#"><img border="0" name="midRow" id="newMidRow1" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('1');"><img border="0" src="./images/deleteicon.gif"/></a></td> -->		        
					   </tr>       
			        </c:when>
			        <c:otherwise>
			        		
			        		<c:choose>
								<c:when test="${fn:length(lstToolingRequestNoteDetail) gt 0}">
								
									<c:forEach items="${lstToolingRequestNoteDetail}" var="lstRequestDetail" varStatus="row">  	
									    <tr id="rowid-<%=index%>" >
									     <td align="center"><input type="text" name="toolingLotNumber" id="toolingLotNumber<%=index%>" class="textsmall"  value="${lstRequestDetail.toolingLotNumber1}" onkeydown="javascript:funProductName(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolingLotNumber<%=index%>" ></span></td>	
									     <td align="center"><input type="text" name="punchSetNo" id="punchSetNo" class="textverysmall"  value="${lstRequestDetail.punchSetNo}"  autocomplete="off"/>
								          <td align="center"><input type="text" name="drawingNo" id="drawingNo<%=index%>" class="textverysmall"  value="${lstRequestDetail.drawingNo1}" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/>&nbsp;<input type="button" value="" onclick="javascript:funSearchRequestNo();" class="btnselect"/><span style="display:none;" id="txtDrawingNo1" ></span><span style="display:none;" id="txtDrawingNo<%=index%>" ></span></td>
								          <td align="center"><input type="text" name="compForce" id="compForce" class="textverysmall"  value="${lstRequestDetail.compForce}"  autocomplete="off"/>
								          <td align="center"><input type="text" name="dustCup" id="dustCup" class="textverysmall"  value="${lstRequestDetail.dustCup}"  autocomplete="off"/>
								          <td align="center"><input type="text" name="productName" id="productName<%=index%>" class="textsmall"  value="${lstRequestDetail.productName1}" onkeydown="javascript:funProductName(event,this);" autocomplete="off"/><span style="display:none;" id="txtProductName<%=index%>" ></span></td>		          
										  				  
										  <td align="center"><input type="text" name="machingType" id="machingType<%=index%>" class="textverysmall"  value="${lstRequestDetail.machingType1}" onkeyup="javascript:funFillMachineName(event,this);" autocomplete="off"/><span style="display:none;" id="txtMachingType<%=index%>" ></span></td>
										  <td align="center">
										  <select name="typeOfTool" id="typeOfTool1">
							             		<option value="">Select</option>
							             		<option value="${lstRequestDetail.typeOfTool1}" selected>${lstRequestDetail.typeOfTool1}</option>
							                    <option value="D/D">D/D</option>
							                    <option value="B/B">B/B</option>
							                    <option value="D/B"> D/B</option>
							                    <option value="B/BB">B/BB</option>
							                    <option value="B/BBS">B/BBS</option>
							                </select>
							                </td>
							                
										 <%--  <input type="text" name="typeOfTool" id="typeOfTool<%=index%>" class="textverysmall"  value="${lstRequestDetail.typeOfTool1}" onkeyup="javascript:funFillTypeOfTool(event,this);" autocomplete="off"/> --%><span style="display:none;" id="txtTypeOfTool<%=index%>" ></span></td>
										  <td align="center"><input type="text" name="batchnos" id="batchnos<%=index%>" class="textverysmall"  value="${lstRequestDetail.batchnos1}"   autocomplete="off"/><span style="display:none;" id="txtBatchnos<%=index%>"></span></td>	
										
										  <td align="center"><input type="text" name="batchProd" id="batchProd<%=index%>" class="textverysmall"  value="${lstRequestDetail.batchProd1}"   autocomplete="off"/><span style="display:none;" id="txtBatchProd<%=index%>"></span></td>	
										  <td align="center"><input type="text" name="batchQty" id="batchQty<%=index%>" class="textverysmall"  value="${lstRequestDetail.batchQty1}" onkeyup="javascript:funBatchQty(event,this);"  autocomplete="off"/><span style="display:none;" id="txtBatchQty<%=index%>"></span></td>				  
										  <td align="center"><input type="text" name="requestQty" id="receivedQty<%=index%>" class="textverysmall"  value="${lstRequestDetail.requestQty1}" onkeyup="javascript:funReceivedQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtReceivedQty<%=index%>" ></span></td>
										  <td align="center"><input type="text" name="UOM" id="uom<%=index%>" class="textverysmall"  value="${lstRequestDetail.UOM1}" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUom<%=index%>" ></span></td>
										  <td align="center"> <input type="text" name="inStock" id="inStock<%=index%>" class="txtleast"  value="${lstRequestDetail.inStock1}" onkeyup="javascript:funInStock(event,this);"  autocomplete="off"/><span style="display:none;" id="txtInStock<%=index%>"></span></td>
										 
										  <%-- <td align="center"> <input type="text" name="market" id="market<%=index%>" class="txtleast"  value="${lstRequestDetail.market1}"   autocomplete="off"/><span style="display:none;" id="txtMarket<%=index%>"></span></td>
										   <td align="center">
						  
						 <select name="dustCup" id="dustCup1" >
						  <option value="">Select</option>
						  <option value="${lstRequestDetail.dustCup1}" selected>${lstRequestDetail.dustCup1}</option>
						  <option value="Required">Required</option>
						  <option value="Not Required">Not Required</option>
						  </select >
						  </td> --%>
					
										
							           </tr> 
							           <% index++;%>
							        </c:forEach> 
							           
								</c:when>
								<c:otherwise>
										<tr id="rowid-0" >
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
										 <!--  <td align="center">&nbsp;</td>
										  <td align="center">&nbsp;</td> -->
								          <%-- <td align="center"><a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow<%=index%>" src="./images/up-arrow.png"/></a>&nbsp;<img border="0" src="./images/deleteicon.gif"/></td> --%>
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
		        
		        
		       <!-- <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="100%%"><img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/up-arrow.png">&nbsp;-&nbsp;Insert New Row Above&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon.gif">&nbsp;-&nbsp;Delete&nbsp;&nbsp;&nbsp;</td> 		            				
					</tr>
			 </table> -->       
			 <table  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="50px">
		           <td height="50px" align="center">&nbsp;</td>
			   </tr>
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
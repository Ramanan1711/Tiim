<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clearance Number</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 
<link rel="stylesheet" href="./css/bootstrap.min.css">
<!-- <script type="text/javascript" src="./js/SampleGrid.js"></script>  -->

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">
 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
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

 function trim(stringTrim)
 {
 	return stringTrim.replace(/^\s+|\s+$/g,"");
 }
 
 function showimg()
 {
 	$("#ajaxLoading").show();  
 }
 function funSearchIssueNo()
 {
	 $("#productNameListDialog").fadeIn(300);  
	    // $("#srchMinProductName").focus();
	     $("#overlay").unbind("click");  
	     $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getPendingIssueLotNumber.jsf",
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
 
 function processInspection(val)
 {
	 var clearanceNo = $("#clearanceNo").val();
	 var lotNumber = $("#lotNumber").val();
	 var clearanceDate = $("#clearanceDate").val();
	 var productName = $("#productName").val();
	 var batchNumber = $("#batchNumber").val();
	 var batchQty = $("#batchQty").val();
	 var totalQty = $("#totalQty").val();
	 var nextDueQty = $("#nextDueQty").val();
	// var toolingLife = $("#toolingLife").val();
	 var totalBatchQty = $("#totalBatchQty").val();
	 totalQty = parseInt(totalQty) + parseInt(batchQty);
	 if(clearanceNo == "")
     {        	
		    jAlert('Please Enter the Clearance Number', 'Clearance Number is Empty', function(){	
		    	$("#clearanceNo").focus();						  			    	 
	        });	
		   return false;
     }
	 else if(lotNumber == "")
	 {
		 jAlert('Please Enter the Lot Number', 'Lot Number is Empty', function(){	
	    	 $("#lotNumber").focus();		    		
	        });	
		   return false;
	 } else if(clearanceDate == "")
     {
    	 jAlert('Please Enter the Date', 'Date is Empty');
    	 $("#clearanceDate").focus();		    		
		 return false;		
     } else if(productName == "")
     {
    	 jAlert('Please Enter the Product Name', 'Product Name is Empty');
    	 $("#productName").focus();		    		
		 return false;		
     } else if(batchNumber == "")
     {
    	 jAlert('Please Enter the Batch Number', 'Batch Number is Empty');
    	 $("#batchNumber").focus();		    		
		 return false;		
     } else if(batchQty == "")
     {
    	 jAlert('Please Enter the Batch Quantity', 'Batch Quantity is Empty');
    	 $("#batchQty").focus();		    		
		 return false;		
     }
     else if(parseInt(batchQty) > parseInt(totalBatchQty))
     {
    	 jAlert('Batch Quantity should be less than specified batch quantity', 'Batch Quantity is Wrong');
     }
	 else if(parseInt(batchQty) > parseInt(nextDueQty))
     {
    	 jAlert('Batch Quantity should be less than next due Qty', 'Batch Quantity is Wrong');
     }
    /*  else if(parseInt(batchQty) > parseInt(toolingLife))
     {
    	 jAlert('Batch Quantity should be less than life Qty', 'Batch Quantity is Wrong');
     } */
   /*   else if(parseInt(totalQty) > parseInt(toolingLife))
     {
    	 jAlert('Total Batch Quantity should be less than life Qty', 'Batch Quantity is Wrong');
     }
     else if(parseInt(totalQty) > parseInt(nextDueQty))
     {
    	 jAlert('Total Batch Quantity should be less than next due Qty', 'Batch Quantity is Wrong');
     } */
     else
       	 {
    		 $("#loadoverlay").fadeIn();  
    		 $("#ajaxLoading").fadeIn();  
    		 
    		 if(val == "Save")
    		 {
    		 	$("#frmClearance").attr("action","addClearance.jsf");   
    		 }
    		 else
    		 {
    			$("#frmClearance").attr("action","updateClearance.jsf");
    		 }
    	     $("#frmClearance").submit();
    		 
    	}
		 
     }
 
 function funToolingList()
 {
     $("#clearanceNo").val(0); 
     $("#lotNumber").val(0); 
     $("#batchQty").val(0); 
     $("#issueId").val(0);	
     $("#nextDueQty").val(0);	
	 $("#frmClearance").attr("action","showClearanceList.jsf");   	                
     $("#frmClearance").submit();
 }
 
 function hideProductNameListDialog()
 {
    $("#productNameListDialog").fadeOut(300);

 } 
 
 function funFillStockDetails(evt,obj)      
 {
	  $("#lotNumber").autocomplete({
		    source: function (request, response) {
		        $.getJSON("${pageContext. request. contextPath}/autoStock.jsf", {
		           // term: extractLast(request.term)
		        	lotNo: $("#lotNumber").val()
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
 
 function funLoadStockDetails(stockId)
 { 
	 
	 $.ajax({
		dataType : 'json',
		async: "false",
        url : "${pageContext.request.contextPath}/getIndividualStock.jsf?stockId="+parseInt(stockId),
        type : "POST",
        success : function(result) 
        {
        	if(result != "")
	    	{		
	       		 $("#lotNumber").val("");
	  	         $("#productName").text(""); 
	  	         $("#batchQty").text(""); 

        		 $("#lotNumber").val(result.toolingLotNumber);
	  	         $("#productName").val(result.productName); 
	  	         $("#machineType").val(result.machineName); 
	  	       	 $("#drawingNumber").val(result.drawingNo); 
	  	  		 $("#typeOfTool").val(result.typeOfTool); 
	  	        // $("#batchQty").val(result.tabletProducedQty); 
	  	      	 getNextDueDate(result.toolingLotNumber);
	    	}
        }
     });
 }
 
 function loadIssueDetails(lotNumber, drawingNumber, productName, machineType, typeOfTool, requestQty, batchQty, issueId)
 {
	 $("#lotNumber").val(lotNumber);
     $("#productName").val(productName); 
     $("#machineType").val(machineType); 
     $("#drawingNumber").val(drawingNumber); 
	 $("#typeOfTool").val(typeOfTool);
	 $("#totalBatchQty").val(batchQty);	
	 $("#issueId").val(issueId);	
	 
   //  getNextDueDate(lotNumber);
   	getClearanceCounter(lotNumber, requestQty, batchQty,issueId);
     $("#productNameListDialog").fadeOut(300);
 }
 
 function getClearanceCounter(lotNumber, requestQty, batchQty, issueId)
 {
	 
	 $.ajax({
			dataType : 'json',
			async: "false",
	        url : "${pageContext.request.contextPath}/getClearacnceCount.jsf?lotNumber="+lotNumber+"&issueId="+issueId,
	        type : "POST",
	        success : function(data) 
	        {
	        	if(data != "")
		    	{
	        		var batch = parseInt(batchQty);
	        		var batchNumber = parseInt(data.batchNumber);
	        		var totalQty = parseInt(data.batchQty);
	        		//alert(batch+', '+totalQty);
	        		//if(batch > batchNumber && parseInt(requestQty) > totalQty)
					
	        		if(parseInt(batch) > totalQty)
	        		{
	        			$("#totalQty").val(totalQty);
		        		getNextDueDate(lotNumber, totalQty);
	        		}else
	        		{
	        			 jAlert('Batch Quantity is exceeded, It is already fullfiled', 'Batch Quantity is Exceeded');
	        		}
	        		
		    	}
	        }
	     });
 }
 
 function getNextDueDate(lotNumber, totalQty)
	{
		var productName = $("#productName").val();
		var machineName = $("#machineType").val();
		var drawingNumber = $("#drawingNumber").val();
		var typeOfTool = $("#typeOfTool").val();

		$.ajax({
			dataType : 'json',
			async: "false",
	        url : "${pageContext.request.contextPath}/getNextDueQty.jsf?lotNumber="+lotNumber+"&productName="+productName+"&drawingNumber="+drawingNumber+"&machineName="+machineName+"&typeOfTool="+typeOfTool,
	        type : "POST",
	        success : function(data) 
	        {

	        	if(data != "")
		    	{
/* 					alert($("#serilaFlag").val()+", "+$("#issuedQty1").val()); */
 
	        /* 		alert($(#nextDueQty).val());
	 */        		var nextDue = data.nextDueQty;
					//alert(nextDue+","+totalQty); 

	        		if(parseInt(nextDue) >= parseInt(totalQty))
	        		{
	        			$("#nextDueQty").val(data.nextDueQty);	 
		        		//$("#toolingLife").val(data.toolingLife);
	        		}else
	        		{
	        			jAlert('Total batch quantity is exceeded', 'Batch Quantity is Exceeded');
	        		}
	        		
		    	}
	        }
	     });
	}
 
 function funToolingLotNo(evt,obj)
 { 
	 $("#lotNumber").text($("#lotNumber").val()); 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {   
    	 $("#lotNumber").text(trim($("#lotNumber").val()));
     }
 }
</script>

</head>
<body class="body" onload="Init();">
<form name="frmClearance" method="post"  id="frmClearance" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>

<div id="loadoverlay" class="loading_overlay" ></div>
<img id="ajaxLoading" src="./images/ultraloading.jpg" class="loadingPosition"></img>
<div id="overlay" class="web_dialog_overlay" ></div>

    
	
   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="10px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Tool Life Cycle Management</td> 
           <td width="45%" class="submenutitlesmall" align="center">
           </td> 
           <td width="25%" class="submenutitlesmall" align="right">        
           		        
           </td>                                      
       </tr>
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Clearance Number</td> 
            
           <td width="25%" class="submenutitlesmall" align="right">        
           		 <table cellspacing="1" cellpadding="2" width="57%" height="20px" align="left" style="display:none" id="msg">	
			     	<tr>  
				        <td class="confirmationmessage" align="center">&nbsp;&nbsp;&nbsp;<span id="dynmsg" style="align:center;">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="msganchor" id="msgclose">X</a></td>
				    </tr>
			   </table>        
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
						<td class="formlabelblack" align="left">Clearance Number&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="clearanceNo" id="clearanceNo" class="textmedium" value ="${clearanceNo}" maxlength="11"  autofocus readonly="readonly" /></td>
					</tr>		
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Lot Number&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="lotNumber" id="lotNumber" class="textmedium" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" value ="${lotNumber}" maxlength="11"  autofocus />&nbsp;<input type="button" value="" onclick="javascript:funSearchIssueNo();" class="btnselect"/></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Date&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="clearanceDate" id="clearanceDate" class="textmedium" value ="${clearanceDate}" maxlength="10"  readonly="readonly" /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Product Name&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="productName" id="productName" class="textmedium" value ="${productName}"  maxlength="50"  autofocus /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Machine Type&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="machineType" id="machineType" class="textmedium" value ="${machineType}"  maxlength="50"  autofocus /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Drawing Number&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="drawingNumber" id="drawingNumber" class="textmedium" value ="${drawingNumber}"  maxlength="50"  autofocus /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Type Of Tool&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="typeOfTool" id="typeOfTool" class="textmedium" value ="${typeOfTool}"  maxlength="50"  autofocus /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Next Due Qnty&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="nextDueQty" id="nextDueQty" class="textmedium" value ="${nextDueQty}"  maxlength="150"  autofocus readonly/><span id="nextDueQty"></span></td>
					</tr>
					<%-- <tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Tooling life Quantity&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="toolingLife" id="toolingLife" class="textmedium" value ="${batchNumber}"  maxlength="150"  autofocus readonly/></td>
					</tr> --%>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Batch Number&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="batchNumber" id="batchNumber" class="textmedium" value ="${batchNumber}"  maxlength="150"  autofocus /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Batch Quantity&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="number" name="batchQty" id="batchQty" class="textmedium" value ="${batchQty}"  maxlength="11"  autofocus /></td>
					</tr>
					<tr>
					    <td></td>
					    					
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
         
              <table cellspacing="0" width="19%" cellpadding="5" border="0" align="center" height="30px">		      
					 <tr>
           <td height="50px" align="left">
		     <input  type="button" class="btn btnNormal" value="${btnStatusVal}" onclick="javascript:processInspection('${btnStatusVal}');" ${btnStatus} />&nbsp;&nbsp;
		     <input type="button" class="btn btnNormal"  onclick="javascript:funToolingList();" value="List" /></td>
	    </tr>
			  </table>    
			
				 <%
					int index = 1;
				 %>
		      
		  	<input type="hidden" name="action" id="action" value="${action}"/>
			<input type="hidden" name="message" id="message" value="${message}" />	
			<input type="hidden" name="totalQty" id="totalQty" />
       		<input type="hidden" name="totalBatchQty" id="totalBatchQty" />
       		<input type="hidden" name="issueId" id="issueId" />
			
     </DIV>
     </center>    
     
     <table  border="0" cellspacing="0" cellpadding="0"  width="80%" align="center">
	   
	 </table>       
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
</form>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 
<link rel="stylesheet" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>
<style>
</style>
<script>
$(document).ready(function(){ 
	
    /////////////////////////////Validation/////////////////////////////
    $('#issueData input[type=text]').live("keypress", function(event){
    	
	    	var pattern = /[a-zA-Z]+/g;
	   		var findFocusId =  $(this).attr('id');	
	   		findFocusId = findFocusId.match(pattern);
	   		switch($.trim(findFocusId))
	   		{		   		   
	   			
   				case "nextDueQty":
			   		
	   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
	   			break;
   				case "issuedQty":
	
					return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
				break;
   				case "toolingSerialNo":
   					
					return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
				break;
				case "lastInspectionDate":
   					
					return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
				break;
	   			
	   		}
    });
    //////////////////////////////////////////////////////////////////// 
	
	$("#issueBy").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoRequestBy.jsf", {
	           // term: extractLast(request.term)
	        	requestBy: $("#issueBy").val()
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
	    }
   }); 
	 $("#serialNumber").keyup(function(e)              
	 {	
		 calculateSerialNo();
		 return false;
	  });
	/* $("#requestNo").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoRequestNo.jsf", {
	           // term: extractLast(request.term)
	        	requestNo: $("#requestNo").val()
	        }, response);
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
	    	$("#requestNo").val(ui.item.id);
	    	$("#originalId").val("0");
	    	funLoadRequestNoteDetails();
	    }
   });  */
	
	$("#toolingLotNumber1").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/getToolingLotNo.jsf", {
	           // term: extractLast(request.term)
	        	productName: $("#productName1").val(),
	        	machineType: $("#machingType1").val(),
	        	drawingNo: $("#drawingNo1").val(),
	        	typeOfTool: $("#typeOfTool1").val()
	        	
	        }, response);
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
	    	$("#toolingLotNumber1").val(ui.item.id);
	    	getNextDueDate($("#toolingLotNumber1").val());
	    }
   }); 
   

	
	function getNextDueDate(lotNumber)
	{
		var productName = $("#productName1").val();
		var machineName = $("#machingType1").val();
		var drawingNumber = $("#drawingNo1").val();
		var typeOfTool = $("#typeOfTool1").val();

		$.ajax({
			dataType : 'json',
			async: "false",
	        url : "${pageContext.request.contextPath}/getNextDueQty.jsf?lotNumber="+lotNumber+"&productName="+productName+"&drawingNumber="+drawingNumber+"&machineName="+machineName+"&typeOfTool="+typeOfTool,
	        type : "POST",
	        success : function(data) 
	        {

	        	if(data != "")
		    	{
	        		$("#nextDueQty1").val(data.nextDueQty);	 
	        		$("#balanceInterval").val(data.balanceProducedQty);
	        		//$("#spanToolingLife").html(data.toolingLife);
	        		$("#spanProducedQty").html(data.producedQty);
	        		getStockQty(lotNumber);
		    	}
	        }
	     });
	}
	
	
	
	
	$('#rule').click(function(event) {
		 $("#overlay").show();
		 $("#dialogRule").show();	
	});
	  
	$('#ruleClose').click(function(event) {   
		 $("#dialogRule").hide();
		 $("#overlay").hide();
	});
	
	
   
 });
 
function funSearchMinProductName(drawingNo)
{
	 $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getRequestByDrawingNo.jsf?drawingNumber="+drawingNo,
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

  function funLoadRequestNoteDetails()
  {
	  $("#noOfDays").val("0");
	//  $("#originalId").val("0");
	  $("#frmIssue").attr("action","fetchToolingIssue.jsf");
      $("#frmIssue").submit(); 
 }

  function getSerialNumber()
  {
 	 var lotNumber = $("#toolingLotNumber1").val();
 	 
 		 $.ajax({
 				dataType : 'html',
 		        url : "${pageContext.request.contextPath}/getIssueSerialNumbers.jsf?lotNumber="+lotNumber,
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
  
 function Init()
 {	
	$("#loadoverlay").hide();
	$("#ajaxLoading").hide();
	var msg = $("#message").val();
	if(msg != "")  
	{    
		$("#msg").fadeIn(300);	   
    }
	getLotNumber();
 }
 
 function getLotNumber(){
	   var productName = $("#productName1").val();
		var machineName = $("#machingType1").val();
		var drawingNumber = $("#drawingNo1").val();
		var typeOfTool = $("#typeOfTool1").val();
		if(productName != null){
			$.ajax({
				dataType : 'json',
				async: "false",
		        url : "${pageContext.request.contextPath}/getToolingLotNo.jsf?productName="+productName+"&machineType="+machineName+"&drawingNo="+drawingNumber+"&typeOfTool="+typeOfTool,
		        type : "GET",
		        success : function(data) 
		        {
		        	 $("#toolingLotNumber1").empty();
		        	var len = data.length;
		        	$("#toolingLotNumber1").append("<option value='Select'>Select</option>");
		        	 for( var i = 0; i<len; i++){
		                    var id = data[i]['id'];
		                    $("#toolingLotNumber1").append("<option value='"+id+"'>"+id+"</option>");

		                }
		        }
		     });
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
 
 function funGetNextDue(){
	 var lotNumber = $("#toolingLotNumber1").val();
	 var productName = $("#productName1").val();
		var machineName = $("#machingType1").val();
		var drawingNumber = $("#drawingNo1").val();
		var typeOfTool = $("#typeOfTool1").val();

		$.ajax({
			dataType : 'json',
			async: "false",
	        url : "${pageContext.request.contextPath}/getNextDueQty.jsf?lotNumber="+lotNumber+"&productName="+productName+"&drawingNumber="+drawingNumber+"&machineName="+machineName+"&typeOfTool="+typeOfTool,
	        type : "POST",
	        success : function(data) 
	        {

	        	if(data != "")
		    	{
	        		$("#nextDueQty1").val(data.nextDueQty);	 
	        		$("#balanceInterval").val(data.balanceProducedQty);
	        		//$("#spanToolingLife").html(data.toolingLife);
	        		$("#spanProducedQty").html(data.producedQty);
	        		getStockQty(lotNumber);
		    	}
	        }
	     });
 }
 
 function getStockQty(lotNumber)
	{
		
		$.ajax({
			dataType : 'json',
			async: "false",
	        url : "${pageContext.request.contextPath}/getStockQuantity.jsf?lotNumber="+lotNumber,
	        type : "POST",
	        success : function(data) 
	        {
	        	if(data != "")
		    	{
	        		$("#inStock1").val(data);	
	        		$("#spanStcokQty").html(data);
	        		getIntervalBalance(lotNumber);
		    	}
	        }
	     });
	}
	
	function getIntervalBalance(lotNumber)
	{
		var productName = $("#productName1").val();
		var machineName = $("#machingType1").val();
		var drawingNumber = $("#drawingNo1").val();
		var typeOfTool = $("#typeOfTool1").val();

		$.ajax({
			dataType : 'json',
			async: "false",
	        url : "${pageContext.request.contextPath}/getIntervalBalance.jsf?lotNumber="+lotNumber+"&productName="+productName+"&drawingNumber="+drawingNumber+"&machineName="+machineName+"&typeOfTool="+typeOfTool,
	        type : "POST",
	        success : function(data) 
	        {
	        	if(data != "")
		    	{
	        		$("#balanceInterval").val(data);
		    	}
     		getStorageLocation(lotNumber);
	        }
	     });
	}
	
	function getStorageLocation(lotNumber)
	{
		$.ajax({
			dataType : 'text',
			async: "false",
	        url : "${pageContext.request.contextPath}/getStorageLocation.jsf?lotNumber="+lotNumber,
	        type : "POST",
	        success : function(data) 
	        {
	        	if(data != "")
		    	{
	        		$("#storageLocation").html(data);
		    	}
	        	getSerialNumberDetails(lotNumber);
	        }
	     });
	}
	
 function processIssue(val)
 {
	 var count = $("#hiddenCount").val();
	 var requestNo = trim($("#requestNo").val());
	 var issueBy = trim($("#issueBy").val());
	 var checkedBy = trim($("#checkedBy").val());
	 var issuedQty = trim($("#issuedQty1").val());
	 var stockQty = trim($("#inStock1").val());
	 var requestQty = trim($("#requestQty1").val());
	 var nextDueQty = trim($("#nextDueQty1").val());
	 var batchQty = trim($("#batchQty1").val());
	 var intervalQty = trim($("#balanceInterval").val());
	 var lotNumber = trim($("#toolingLotNumber1").val());
//	 var noOfDays = trim($("#noOfDays").val());
	 if(requestNo == "")
     {        	
		    jAlert('Please Select Request No', 'Request No is Empty', function(){	
		    	$("#requestNo").focus();						  			    	 
	        });	
		   return false;
     }
     else if(checkedBy == "")
     {
    	 jAlert('Please Enter the Visual Checked By', 'Visual Checked By is Empty');
    	 $("#checkedBy").focus();		    		
		 return false;		
     } else if(issueBy == "")
     {
    	 jAlert('Please Enter the Issue By', 'Issue By is Empty');
    	 $("#issueBy").focus();		    		
		 return false;		
     }
     else if(lotNumber == ""){
    	 jAlert('Please enter lot Number','Lot Number is Empty');
    	 $("#toolingLotNumber").focus();		    		
		 return false;		
     }
     else if(issuedQty == "")
     {
    	 jAlert('Please Enter the Issue Quantity', 'Issue Quantity is Empty');
    	 $("#issuedQty1").focus();		    		
		 return false;		
     }
      else if(parseInt(batchQty) > parseInt(nextDueQty))
     {
    	 jAlert('Batch Quantity should be less than next due Qty', 'Batch Quantity is Wrong');
     } 
     else if(parseInt(issuedQty) > parseInt(stockQty))
     {
    	 jAlert('Issue Quantity should be less than Stock Qty', 'Issue Quantity is Wrong');
     }/* else if(noOfDays == "")
     {
    	 jAlert('Please Enter the Number of days before return', 'Number of Days is Empty');
    	 $("#noOfDays").focus();		    		
		 return false;	
     } */
      /* else if(parseInt(batchQty) >= parseInt(intervalQty+1) )
     {
    	 var value = "Batch quanty should be less than "+intervalQty+ " (interval quantity)";
    	 jAlert(value, 'Batch Quantity is Wrong');
     }   */
     else
     {
    	 var isValid = true;
    	 for(i=1;i<=count;i++) {
             var issuedQty =$("#issuedQty"+i).val();
             var requestQty =$("#requestQty"+i).val();
             if(parseInt(issuedQty) > parseInt(requestQty))
             {
            	 isValid = false;
            	 alert("Issue Quantity should be less than Request Quantity");
            	 return false;
             }
   		 }
		// $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();   	 			                    	                  
		 if(isValid)
		 {
			 $("#originalId").val(0);
			//	alert($("#serilaFlag").val()+", "+$("#issuedQty1").val());
			  if(($("#serilaFlag").val() == 1) && ($("#issuedQty1").val() == null))
			 {
				 jAlert('Please Selecte serial number for Accepted/Rejected', 'Serial Numbers are empty!', function(){	
					 getSerialNumber();		  			    	 
 		        });	
 			   return false;
			 } 
			 if(val == "Save")
			 {
					 $("#loadoverlay").fadeIn();
					 $("#frmIssue").attr("action","addToolingIssueNote.jsf");
					 $("#frmIssue").submit();				   	      
			 }
			 else
			 {
				$("#loadoverlay").fadeIn();
				$("#frmIssue").attr("action","updateToolingIssueNote.jsf");
				$("#frmIssue").submit();
			 }
		     
		 }
		 
     }
 }
 
 function funRequestNoteList()
 {
	 $("#issueId").val("0");
	 $("#requestNo").val("0");
	 $("#noOfDays").val("0");
	 $("#originalId").val(0);
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmIssue").attr("action","showToolingIssueNote.jsf");   	                
     $("#frmIssue").submit();
 }
 
 function funSearchRequestNo()
 {
     $("#productNameListDialog").fadeIn(300);  
    // $("#srchMinProductName").focus();
     $("#overlay").unbind("click");  
     $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getIssueRequestNo.jsf",
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
 
 function getSerialNumberDetails(lotNumber)
 {
	 //var lotNumber = $("#toolingLotNumber1").val();
 	 
		 $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getSerialNumberDetailByLotNumber.jsf?lotNumber="+lotNumber,
		        type : "POST",
		        success : function(result) 
		        {
		  		   if(result != "")
		    	   {	
		  			// $("#ajaxLoading").hide();
		  		     //$("#serialNumberDialog").fadeIn(300);  
		  			  $("#listSerialNumberDetails").html(result); 
		  			//  $("#waitloadingProductName").hide();
		    	   }
		        }
		    });
 }
 
 function funShowRequestNo(requestNo, originalId)
 {
	 $("#requestNo").val(originalId);
	 $("#originalId").val(requestNo);
	 funLoadRequestNoteDetails();
	// hideProductNameListDialog();
 }
 
 function hideProductNameListDialog()
 {
    $("#productNameListDialog").fadeOut(300);

 }
 
 function submitSerialNumber()
 {
 	
 	hideSerialNumberDialog();
 }
 function funInspectionDate(evt,obj)
 {
	 var keyCode = (evt.which) ? evt.which : evt.keyCode ;
	 var txt = obj;
	 var seperator = "/";
	 if(String.fromCharCode(evt.which) == "'" || String.fromCharCode(evt.which) == "`")
	 {
		  return false;
	 }
	 else if((keyCode == 37 || keyCode == 39) && (String.fromCharCode(evt.which) != "%"))
			return true; 
	 else if(keyCode == 8 || (keyCode == 46 && (String.fromCharCode(evt.which) != ".")))
		 return true; 
	 else if(((keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105)))
     {
       if ((txt.value.length == 2 || txt.value.length==5) && keyCode != 8)
       {
           txt.value += seperator;
       }
       return true;
     }
     else
     {
       return false;
     }
 }

 function funInspectionDateEnter(evt,obj, cnt)
 {
	  var keyCode = evt; 
	  var txt = obj;	  
	  var val= trim(txt.value);
	 
	  if(keyCode == 13 || keyCode == 9)
      {  
		  /* if(val.length == 0)
		  {
			  jAlert('Please enter the Last Inspection Date. (MMddYYYY)', 'Alert', function(){	
				  setTimeout(function() {
			    	$("#lastInspectionDate"+cnt).focus();	
				  },300);
			  });        	 
	    	  return false;
		  } */
		  //else if(val.length > 0 && val.length < 10)
		  if(val.length > 0 && val.length < 10)
          {           
	       	   jAlert('Please enter the valid date. (MMddYYYY)', 'Alert', function(){	
	       		   setTimeout(function() {
	       			    $("#lastInspectionDate"+cnt).val("");
			    	    $("#lastInspectionDate"+cnt).focus();	
	       		   },300);
			   });        	 
	       	   return false;
       	
          }
		  else if(val.length == 10)
	      {
	        var splits = val.split("/");
	        var dt = new Date(splits[0] + "/" + splits[1] + "/" + splits[2]);
	        if(dt.getDate()==splits[1] && dt.getMonth()+1==splits[0]  && dt.getFullYear()==splits[2])
	        {
		    	$("#nextDueQty"+cnt).focus();
	        }
	        else
	        { 
	        	jAlert('Please enter the valid date. (MMddYYYY)', 'Alert', function(){	
	        		   setTimeout(function() {
	        			$("#lastInspectionDate"+cnt).val("");
	 		    	    $("#lastInspectionDate"+cnt).focus();	
	        		   },300);
		 	    });        	 
       	    return false;
	        }   
	      }      
     }
 }
 function selectAllIssue()
 {
	 $('input:checkbox[id*=approvedQty]').attr('checked', true);
 }
 
 function hideSerialNumberDialog()
 {
	 var issueQnty = $("input:checkbox:checked").length;
	 $("#issuedQty1").val(issueQnty);
	 $("#serialNumberDialog").fadeOut(300); 	 
	 
	 var slvals = [];
	 $('input:checkbox[name=approvedQty]:checked').each(function() {
	 slvals.push($(this).val());
	 
	 })
		 
	$("#serialNumber").val(slvals); 
	
 }
 
 function calculateSerialNo(){
	 
	 var requestQty = $("#requestQty1").val();
	 var serialNumber = $("#serialNumber").val();
	 var totalRejectedQty = $("#totalRejectedQty").val();

	 if(serialNumber != "" && parseInt(serialNumber) > 0 && parseInt(serialNumber) < 10)
	 {
		 var lastSerialNumber = parseInt(serialNumber) + parseInt(requestQty) + parseInt(totalRejectedQty) - 1
		 $("#serialNumber").val("");
		 var value =  parseInt(serialNumber) + "-" + lastSerialNumber;
		 $("#serialNumber").val(value);
		 $("#btnList").focus();
		 return false;
		// $("#btnList").focus();
	 }
	 
 }
 function getNextQty(evt,obj)
 {
	 
 }
</script>

</head>
<body class="body" onload="Init();">
<form name="frmIssue" method="post"  id="frmIssue" autocomplete="off">
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
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Last&nbsp;Inspection&nbsp;Date</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Enter (MMHHYYYY) to show as (MM/HH/YYYY)</td>
				     </tr>		     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div>
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
    <div id="serialNumberDialog" class="max_dialog">
    <br>
    		<table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="18%" align="left" class="popuptoptitlelarge">Issued Serial Number</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideSerialNumberDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
			 </table>
    	<div id="listSerialNumber" style="height:300px;overflow:auto;"></div>   
    	<table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
		    <tr>
	           <td height="50px" align="left">
			     <input type="button" class="btn btnNormal"  onclick="selectAllIssue()" value="Select All" />
			     <input type="button" class="btn btnNormal"  onclick="submitSerialNumber();" value="Submit" />
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
           <td width="35%" class="heading" align="left">&nbsp;Production Tool Issue</td> 
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
				<table style="width:70%" align="left" cellpadding="4" cellspacing="0" border="0">		
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Issue No&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Issue Date&nbsp;<span class="formlabel">*</span></td>		
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input type="text" name="issueId" id="issueId" class="textmin" value="${issueId}" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
						<td class="popuplabel" align="left"><input type="text" id="issueDate"  name="issueDate" class="textmin" value="${issueDate}"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Request No.&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Request Date&nbsp;<span class="formlabel">*</span></td>		
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left">
							<c:choose>
					         	<c:when test="${btnStatusVal eq 'Update'}">
									<input type="text" name="requestNo" id="requestNo" class="textmin" value="${requestNo}"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/>
								</c:when>
							    <c:otherwise>
							        <input type="text" name="requestNo" id="requestNo" class="textmin" value="${requestNo}" />
							    </c:otherwise> 	
							</c:choose>
							&nbsp;<input type="button" value="" onclick="javascript:funSearchRequestNo();" class="btnselect"/>
					    </td>
						<td class="popuplabel" align="left"><input type="text" id="requestDate"  name="requestDate" class="textmin" value="${requestDate}"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Requested By&nbsp;<span class="formlabel">*</span></td>						
						<td width="50%" class="formlabelblack" align="left">Issued By&nbsp;<span class="formlabel">*</span></td>		
					</tr>	
					<tr>
					    <td></td>
					    <td class="popuplabel" align="left"><input id="requestBy" type="text" name="requestBy" class="txtreal" value="${requestBy}" maxlength="50" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					    <td class="popuplabel" align="left"><input id="issueBy" type="text" name="issueBy" class="txtreal" value="${username}" maxlength="50" autofocus/></td>
					</tr>	
					<tr>
						<td></td>
						<td width="50%" class="formlabelblack" align="left">Visual Checked By&nbsp;<span class="formlabel">*</span></td>	
						<td></td>									
					</tr>	
					<tr>
					    <td></td>
					     <td class="popuplabel" align="left"><input id="checkedBy" type="text" name="checkedBy" class="txtreal" maxlength="70" autofocus value="${checkedBy}"/></td>
						<td></td>
					</tr>					     		    
					<tr>
					    <td class="popuplabel" colspan="3" align="center"><span id="autoStatus" class="popuptopalert"></span></td>
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
							<img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;display:none;"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    </td>
					</tr>
			  </table>    

				<%
					int index = 1;
				 %>
				<c:choose>
					<c:when test="${fn:length(lstToolingIssue) gt 0}">
					
					    <div class="table-responsive">
							<table class="table table-bordered" style="width:98%" id="issueData" align="center">
					         <tr>
					              <th align="center" width="10%">S&nbsp;No.</th>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Machine&nbsp;No/Name</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					               <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
								  <th align="center" width="10%">Batch&nbsp;Qty</th>
								  <th align="center" width="10%">Tooling&nbsp;Req&nbsp;Qty</th>
					             
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>
					              <th align="center" width="10%">In&nbsp;Stock</th>
<!-- 					              <th align="center" width="10%">Tooling&nbsp;Life</th>
 -->					              <th align="center" width="10%">Tablet&nbsp;Produced&nbsp;Qty</th> 
					              <th align="center" width="10%">Tooling&nbsp;Issue&nbsp;Qty</th>             
					              <th align="center" width="10%">UOM</th>  
					              <th align="center" width="10%">Serial&nbsp;Number</th>
					        <!--   <th align="center" width="10%">No Of&nbsp;Days</th>  -->
					              <th align="center" width="10%">Storage location</th>
					              <!-- <th align="center" width="10%">DustCup</th> -->
					         </tr>
					         <c:choose>
					         <c:when test="${btnStatusVal eq 'Update'}">
					         		<c:forEach items="${lstToolingIssue}" var="lstToolingIssue" varStatus="row">  
						                 <tr id="rowid-<%=index%>">
									          <td align="center"><%=index%><input type="hidden" name="hiddenCount" id="hiddenCount" value="<%=index%>"/></td>
									          
									          <td align="center">${lstToolingIssue.productName1}<input type="hidden" name="productName1" id="productName1" value="${lstToolingIssue.productName1}"/></td>
									          <td align="center">${lstToolingIssue.drawingNo1}<input type="hidden" name="drawingNo1" id="drawingNo1" value="${lstToolingIssue.drawingNo1}"/></td>
									          <td align="center">${lstToolingIssue.machineName1}<input type="hidden" name="machineName1" id="machingType1" value="${lstToolingIssue.machineName1}"/></td>		
									          <td align="center">${lstToolingIssue.typeOfTooling1}<input type="hidden" name="typeOfTooling1" id="typeOfTool1" value="${lstToolingIssue.typeOfTooling1}"/></td>          
											  <td align="center">${lstToolingIssue.toolingLotNumber1}<input type="hidden" name="toolingLotNumber" id="toolingLotNumber1" class="txtleastavg"  value="${lstToolingIssue.toolingLotNumber1}"  autocomplete="off"/></td>
											  <td align="center">${lstToolingIssue.batchQty1}<input type="hidden" name="batchQty" id="batchQty1" value="${lstToolingIssue.batchQty1}"/></td>				  
											  <td align="center">${lstToolingIssue.requestQty1}<input type="hidden" name="requestQty" id="requestQty<%=index%>" value="${lstToolingIssue.requestQty1}"/><input type="hidden" name="inStock" id="inStock1"/>
											  <input type="hidden" name="balanceInterval" id="balanceInterval"/></td>				  
											  
											  <td align="center">${lstToolingIssue.lastInspectionDate1}<input type="hidden" name="lastInspectionDate" id="lastInspectionDate<%=index%>" class="textverysmall"  value="${lstToolingIssue.lastInspectionDate1}" maxlength="10" onkeypress="return funInspectionDate(event,this);" onkeyup="javascript:funInspectionDateEnter(event.keyCode,this,'<%=index%>');" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="nextDueQty" id="nextDueQty<%=index%>" class="txtleastavg"  value="${lstToolingIssue.nextDueQty1}"  autocomplete="off" readonly/></td>
											  <td align="center"></td>
											  <td align="center"></td>
											  <td align="center"></td>
											  <td align="center"><input type="text" name="issuedQty" id="issuedQty<%=index%>" class="txtleastavg"  value="${lstToolingIssue.issuedQty1}"  autocomplete="off" /></td>
											  <td align="center">${lstToolingIssue.UOM1}<input type="hidden" name="issueDetailId" value="${lstToolingIssue.issueDetailId1}"/><input type="hidden" name="requestDetailId" value="${lstToolingIssue.requestDetailId1}"/></td>
											  <td align="center"><input type="text" name="serialNumber" id="serialNumber" class="txtleastavg"  value="${lstToolingIssue.serialNumber}"  autocomplete="off"/></td>
											  <td align="center"><input type="hidden" name="storageLocation" id="storageLocation" class="txtleastavg"  value=""  autocomplete="off"/></td>
											<%--   <td align="center">${lstToolingIssue.dustCup}<input type="hidden" name="dustCup" id="dustCup" class="txtleastavg"  value="${lstToolingIssue.dustCup}"  autocomplete="off"/></td> --%>
										<%--  <td align="center"><input type="text" name="noOfDays" id="noOfDays" class="txtleastavg"  value="${lstToolingIssue.noOfDays}"  autocomplete="off"/></td> --%>
											 
							           </tr> 
							           <%
							           index++;
							           %>
							           </c:forEach>
					         </c:when>
					         <c:otherwise>
					         		<c:forEach items="${lstToolingIssue}" var="lstToolingIssue" varStatus="row">  
						                 <tr id="rowid-<%=index%>">
									          <td align="center"><%=index%><input type="hidden" name="hiddenCount" id="hiddenCount" value="<%=index%>"/></td>
									          
									          <td align="center">${lstToolingIssue.productName1}<input type="hidden" name="productName1" id="productName1" value="${lstToolingIssue.productName1}"/></td>
									          <td align="center">${lstToolingIssue.drawingNo1}<input type="hidden" name="drawingNo1" id="drawingNo1" value="${lstToolingIssue.drawingNo1}"/></td>
									          <td align="center">${lstToolingIssue.machingType1}<input type="hidden" name="machingType1" id="machingType1" value="${lstToolingIssue.machingType1}"/></td>		
									          <td align="center">${lstToolingIssue.typeOfTool1}<input type="hidden" name="typeOfTool1" id="typeOfTool1" value="${lstToolingIssue.typeOfTool1}"/></td>          
											  <td align="center">
											  <select name="toolingLotNumber1" id="toolingLotNumber1" onchange="funGetNextDue();"><option>Lot Number</option></select>
											  <%-- <input type="text" name="toolingLotNumber" id="toolingLotNumber<%=index%>" class="txtleastavg"  value=""  autocomplete="off"/> --%></td>
											  <td align="center">${lstToolingIssue.batchQty1}<input type="hidden" name="batchQty" id="batchQty1" value="${lstToolingIssue.batchQty1}"/></td>				  
											  <td align="center">${lstToolingIssue.requestQty1}<input type="hidden" name="requestQty" id="requestQty<%=index%>" value="${lstToolingIssue.requestQty1}"/>
											  <input type="hidden" name="inStock" id="inStock1"/><input type="hidden" name="balanceInterval" id="balanceInterval"/></td>	
											  			  
											  
											  <td align="center"><input type="text" name="lastInspectionDate" id="lastInspectionDate<%=index%>" class="textverysmall"  value="${lstToolingIssue.lastInspectionDate1}"  onkeypress="return funInspectionDate(event,this);" onkeyup="javascript:funInspectionDateEnter(event.keyCode,this,'<%=index%>');" autocomplete="off"/></td>
											  <td align="center"><input type="text" name="nextDueQty" id="nextDueQty<%=index%>" class="txtleastavg"  value="" autocomplete="off" onkeyup="javascript:getNextQty(event,this);"readonly/></td>
											  <td align="center"><span id="spanStcokQty"></span></td>
											 <!--  <td align="center"><span id="spanToolingLife"></span></td> -->
											  <td align="center"><span id="spanProducedQty"></span></td>
											  <td align="center"><input type="text" name="issuedQty" id="issuedQty<%=index%>" class="txtleastavg"  value="" autocomplete="off" /></td>
											  <td align="center">${lstToolingIssue.UOM1}<input type="hidden" name="requestDetailId" value="${lstToolingIssue.requestDetailId1}"/></td>
											  <td align="center"><input type="text" name="serialNumber" id="serialNumber" class="txtleastavg"  value="" onchange="calculateSerialNo()" autocomplete="off"/></td>
											  
											 <!--  <td align="center"><input type="text" name="noOfDays" id="noOfDays" class="txtleastavg"  value=""  autocomplete="off"/></td> -->
											  <td align="center"><span id="storageLocation"></span></td>
											  <%-- <td align="center">${lstToolingIssue.dustCup1}<input type="hidden" name="dustCup1" id="dustCup" class="txtleastavg"  value="${lstToolingIssue.dustCup1}"  autocomplete="off"/></td> --%>
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
							<table class="table table-bordered" style="width:98%" align="center" id="cylinderFieldData">
					         <tr>
					              <th align="center" width="10%">S&nbsp;No.</th>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Machine&nbsp;No/Name</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
								  <th align="center" width="10%">Batch&nbsp;Qty</th>
								  <th align="center" width="10%">Tooling&nbsp;Req&nbsp;Qty</th>
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>
					               <th align="center" width="10%">In&nbsp;Stock</th>
					              <th align="center" width="10%">Tooling&nbsp;Issued&nbsp;Qty</th>             
					              <th align="center" width="10%">UOM</th>  
					              <th align="center" width="10%">Serial&nbsp;Number</th>
					             <!--  <th align="center" width="10%">No Of&nbsp;Days</th> -->
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
								<!--   <td align="center">&nbsp;</td> -->
				           </tr> 		        		      
					       </table>     
					       </div>    
				    </c:otherwise>
				 </c:choose>
		      
		       <!-- <table cellspacing="0" width="99%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="100%%"><img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/up-arrow.png">&nbsp;-&nbsp;Insert New Row Above&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon.gif">&nbsp;-&nbsp;Delete&nbsp;</td> 		            				
					</tr>
			 </table>  -->      
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
		     <input  type="button" class="${btnSatusStyle}" value="${btnStatusVal}"  onclick="javascript:processIssue('${btnStatusVal}');" ${btnStatus}/>&nbsp;&nbsp;
		     <input type="button" class="btn btnNormal"  onclick="javascript:funRequestNoteList();" value="List" name="btnList" id="btnList"/>
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
	 
	  <div id="listSerialNumberDetails" style="height:300px;overflow:auto;"></div>
	 
	 <input type="hidden" name="message" id="message" value="${message}" />
	 <input type="hidden" name="serilaFlag" id="serilaFlag" value="${serilaFlag}" />
</form>
</body>
</html>
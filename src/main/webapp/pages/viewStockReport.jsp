<%@page import="java.util.HashMap, com.tiim.model.RoleVsUser "%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String addAccess = "";
String editAccess = "";
String deleteAccess = "";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<title>Tiim</title>
<meta http-equiv="X-UA-Compatible" content="IE=9"/> 

<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<style>
.pagination 
{  
  display:none;
}
</style>
<script>

   $(document).ready(function ()
    {	  	
	      $("#productName").autocomplete({
	  	    source: function (request, response) {
	  	        $.getJSON("autoProductName.jsf", {
	  	           // term: extractLast(request.term)
	  	        	productName: $("#productName").val()
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
	  	    	// $("#autoStatus").html("");
	  	    	$("#productName").val(ui.item.id);
	  	    }
	  	 });  
	      
	      $("#machineName").autocomplete({
		  	    source: function (request, response) {
		  	        $.getJSON("autoMachineType.jsf", {
		  	           // term: extractLast(request.term)
		  	        	machineType: $("#machineName").val()
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
		  	    	// $("#autoStatus").html("");
		  	    	$("#machineName").val(ui.item.id);
		  	    }
		  	 });  
	      $("#drawingNumber").autocomplete({
		  	    source: function (request, response) {
		  	        $.getJSON("autoDrawingNumber.jsf", {
		  	           // term: extractLast(request.term)
		  	        	drawingNumber: $("#drawingNumber").val()
		  	        }, response).success(function(data) {
		                  if(data.length == 0)
		                  {
		                  	 $("#drawingNumber").html("Data not Available");
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
		  	    	// $("#autoStatus").html("");
		  	    	$("#drawingNumber").val(ui.item.id);
		  	    }
		  	 });  
	      
	      $("#typeOfTool").autocomplete({
		  	    source: function (request, response) {
		  	        $.getJSON("autoTypeOfTool.jsf", {
		  	           // term: extractLast(request.term)
		  	        	typeOfTool: $("#typeOfTool").val()
		  	        }, response).success(function(data) {
		                  if(data.length == 0)
		                  {
		                  	 $("#typeOfTool").html("Data not Available");
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
		  	    	// $("#autoStatus").html("");
		  	    	$("#typeOfTool").val(ui.item.id);
		  	    }
		  	 });  
	      
	      $("#toolingLotNumber").autocomplete({
		  	    source: function (request, response) {
		  	        $.getJSON("autoLotNumber.jsf", {
		  	           // term: extractLast(request.term)
		  	        	lotNumber: $("#toolingLotNumber").val()
		  	        }, response).success(function(data) {
		                  if(data.length == 0)
		                  {
		                  	 $("#toolingLotNumber").html("Data not Available");
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
		  	    	// $("#autoStatus").html("");
		  	    	$("#toolingLotNumber").val(ui.item.id);
		  	    }
		  	 });  
   });

 
   function viewReport()  
   {		 
	   	var productName = $("#productName").val();
	   	var machineName = $("#machineName").val();
	   	var toolingLotNumber = $("#toolingLotNumber").val();
	   	var typeOfTool = $("#typeOfTool").val();
	   	var drawingNo = $("#drawingNo").val();
	   	if(productName == '')
	   	{
	   		jAlert('Please Enter the Product name', 'Product Name is Empty', function(){	
		    	$("#productName").focus();						  			    	 
	        });	
		   return false;	   		
	   	}
	   	else
	   	{
	   		 $.ajax({
	   			 dataType: 'text',
		         type: "POST", 
		         processData: false,
		         url: "${pageContext.request.contextPath}/getStockReport.jsf?productName="+productName+"&machineName="+machineName+"&toolingLotNumber="+toolingLotNumber+"&typeOfTool="+typeOfTool+"&drawingNo="+drawingNo, 
		         data: {productName: productName, machineName: machineName},
		         success: function(data) {
		        	 $("#overlay").show();
		        	 $("#stockReport").html(data); 
		             $("#stockReport").fadeIn(300);     
		             $("#overlay").unbind("click");    
		        	
		         },
		         error: function(error)
		         {
		        	 alert(error);
		         }
	       });  
	       //window.open('getStockReport.jsf', 'window', 'width=700,height=750'); 
	   	/*  $("#frmStockReport").attr("action","getStockReport.jsf");   	                
	     $("#frmStockReport").submit(); */
	   	}
		 
  }
   
   function closeStatement()
   {

	   $("#stockReport").fadeOut(300);
	   $("#frmStockReport").attr("action","createStockReport.jsf");   	                
       $("#frmStockReport").submit(); 
	 //  $("#overlay").hide();
   }
   
    
   
</script>

</head>
<body class="body" >
<form name="frmStockReport" id="frmStockReport" method="post" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>
 
 <div id="processloading" style=" position: absolute; z-index: 700;top:105px; left: 405px;width: 450px; height: 50px; background-color:#ffffff;display:none;">
          <img  src="./images/Processing.gif"></img>
 </div>
 
  <div id="overlay" class="web_dialog_overlay"></div>

	
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	       <td>
			  <table style="width:98%" align="center" cellpadding="4" cellspacing="0" border="0">	     
			     <tr>
			        <td align="left" width="50%" class="popuptoptitle">Stock Report</td>
			        <td width="48" class="popuptoptitle" align="right"></td>
			     </tr>			     			     			    
			    <tr>
			        <td align="left" class="formlabelblack">Drawing Number&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<input  type="text" class="textsmall" name="drawingNumber" id="drawingNumber" /></td>
			     </tr>			      	   			    
			     <tr>
			        <td align="left" class="formlabelblack">Product Name&nbsp;&nbsp;<span class="formlabel">*</span></td>
			        <td align="left" class="formlabelblack">&nbsp;<input  type="text" class="textsmall" name="productName" id="productName" /></td>
			     </tr> 
			     <tr>
			        <td align="left" class="formlabelblack">Machine Type&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<input type="text" class="textsmall" name="machineName" id="machineName" value="" autocomplete="off" maxlength="200"/></td>
			     </tr> 
			      <tr>
			        <td align="left" class="formlabelblack">Type of Tool&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<input type="text" class="textsmall" name="typeOfTool" id="typeOfTool"  value="" autocomplete="off" maxlength="200"/></td>
			     </tr>
			     <tr>
			        <td align="left" class="formlabelblack">Tooling Lot Number&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<input type="text" class="textsmall" name="toolingLotNumber" id="toolingLotNumber" autocomplete="off" maxlength="200"/></td>
			     </tr> 
			         
			     <tr>
			       <td colspan="2">&nbsp;</td>			       
		         </tr>       			     
			     <tr>
			        <td>&nbsp;</td>
			        <td align="left"><input  type="button" class="btn btnImportant" id="btnprocess" value="View" onclick="javascript:viewReport();"/>&nbsp;&nbsp;<input type="button" id="btncancel"  class="btn btnCancel"  onclick="javascript:mstEmpCancelClose();" value="Cancel" /></td>
			     </tr>	
			     <tr>
			       <td colspan="2">&nbsp;</td>				       
		         </tr>	
			  </table>
	        </td>
	      </tr>	       
	  </table>

	
	<table cellspacing="0" width="100%"  cellpadding="5" border="0" align="center"  style="height:10px">		      
		<tr >  
			<td><div id="stockReport"  style="height:auto;margin-top:-300px;" class="max_dialog_Contact"></div></td> 		            
			<td></td>
		</tr>
	</table>
	
</form>
</body>
</html>

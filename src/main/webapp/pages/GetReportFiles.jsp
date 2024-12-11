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
	      
	      
	      
	      $("#lotNumber").autocomplete({
		  	    source: function (request, response) {
		  	        $.getJSON("autoToolLotNumber.jsf", {
		  	           // term: extractLast(request.term)
		  	        	lotNumber: $("#lotNumber").val()
		  	        }, response).success(function(data) {
		                  if(data.length == 0)
		                  {
		                  	 $("#autoToolLotNumber").html("Data not Available");
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
		  	    	$("#lotNumber").val(ui.item.id);
		  	    }
		  	 });  
   });

 
   function viewReport()  
   {		 
	   $("#fileList").html("");
	   $("#caption").html('');
	   //	var productName = $("#productName").val();
	   	var lotNumber = $("#lotNumber").val();
	   	var requestId = $("#requestId").val();
	   /* 	if(productName == "")
	   	{
	   		alert("Please enter Product Name");
	   		$("#productName").focus();
	   	}
	   	else */ if(lotNumber == "")
	   	{
	   		alert("Please enter Lot number");
	   		$("#lotNumber").focus();
	   	}
	   	else
	   	{
	   		
			 $.ajax({
				 dataType : 'json',
		         type: "POST", 
		         url: "${pageContext.request.contextPath}/viewAllProductReportFiles.jsf", 
		         data: { toolingLotNumber: lotNumber, requestId: requestId},
		         success: function(data) {
		        	//window.open(data);
		        	if(data.length > 0)
		        	{
		        		$("#caption").append('FileName');
		        	}
		        	
		        	$.each(data, function(idx,result) 
		        	 {
		        		  var methodName = "openFile(\'"+result.fileNamePath+"\')";
		        		  $("#fileList").append('<a href="#" onclick="'+methodName+'">'+result.fileName+"</a><br>");
		        		});
		         }
	       });  
	   	}
		 
  }
    
   function openFile(fileName)
   {
	   window.open(fileName); 
   }
   
   
</script>

</head>
<body class="body" >
<form name="frmProductReport" id="frmProductReport" method="post" autocomplete="off">
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
			        <td align="left" width="50%" class="popuptoptitle">Inspection Data Report</td>
			        <td width="48" class="popuptoptitle" align="right"></td>
			     </tr>			     			     			    
			    			      	   			    
			    <!--  <tr>
			        <td align="left" class="formlabelblack">Product Name&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<input  type="text" class="textsmall" name="productName" id="productName" /></td>
			     </tr> --> 
			    
			     <tr>
			        <td align="left" class="formlabelblack">Tooling Lot Number&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<input type="text" class="textsmall" name="lotNumber" id="lotNumber"  value="" autocomplete="off" maxlength="200"/></td>
			     </tr> 
			     
			      <tr>
			        <td align="left" class="formlabelblack">Request Id&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<input type="text" class="textsmall" name="requestId" id="requestId"  value="" autocomplete="off" maxlength="200"/></td>
			     </tr>
			      <tr>
			        <td align="left" class="formlabelblack"><div id="caption"></div>&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<div id="fileList"></div></td>
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
			<td></td> 		            
			<td></td>
		</tr>
	</table>
	
</form>
</body>
</html>

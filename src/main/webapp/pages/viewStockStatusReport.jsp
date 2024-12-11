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
	   	var toolingLotNumber = $("#toolingLotNumber").val();
	   	if(toolingLotNumber == '')
	   	{
	   		jAlert('Please Enter the Lot Number', 'Lot Number is Empty', function(){	
		    	$("#toolingLotNumber").focus();						  			    	 
	        });	
		   return false;	   		
	   	}
	   	else
	   	{
	   		$("#frmStockReport").attr("action","getStockStatusReport.jsf");  
	   	 	$("#frmStockReport").submit(); 
	   		/*  $.ajax({
	   			 dataType: 'text',
		         type: "POST", 
		         processData: false,
		         url: "${pageContext.request.contextPath}/getStockStatusReport.jsf", 
		         data: {toolingLotNumber: toolingLotNumber},
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
	       });   */
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
			        <td align="left" width="50%" class="popuptoptitle">Stock Status Report</td>
			        <td width="48" class="popuptoptitle" align="right"></td>
			     </tr>			     			     			    
			    
			     <tr>
			        <td align="left" class="formlabelblack">Tooling Lot Number&nbsp;</td>
			        <td align="left" class="formlabelblack">&nbsp;<input type="text" class="textsmall" name="toolingLotNumber" id="toolingLotNumber"  value="" autocomplete="off" maxlength="200"/></td>
			     </tr> 
			         
			     <tr>
			       <td colspan="2">&nbsp;</td>			       
		         </tr>       			     
			     <tr>
			        <td>&nbsp;</td>
			        <td align="left"><input  type="button" class="btn btnImportant" id="btnprocess" value="View" onclick="javascript:viewReport();"/></td>
			     </tr>	
			     <tr>
			       <td colspan="2">&nbsp;</td>				       
		         </tr>	
			  </table>
	        </td>
	      </tr>	       
	  </table>

	<c:if test ="${not empty stock}">
		 <table class="tablesorter" style="width:98%" align="center" id="cylinderFieldData">
					         <tr>
				             
					              <th align="center" width="20%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Machine&nbsp;No/Name</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
					              <th align="center" width="5%">Receiving&nbsp;Qty</th>
								  <th align="center" width="10%">Available&nbsp;Qty</th>
								  <th align="center" width="5%">Under Production&nbsp;Qty</th>
					              <th align="center" width="5%">Periodic&nbsp;Inspection&nbsp;Qty</th>
					              
					         </tr>
			                 <tr >
						          <td align="center">${stock.productName}</td>
						          <td align="center">${stock.drawingNo}</td>
						          <td align="center">${stock.machineName}</td>
						          <td align="center">${stock.typeOfTool}</td>
						          <td align="center">${stock.toolingLotNumber}</td>	
						          <td align="center">${inspectionQty}</td>	
						          <td align="center">${stock.stockQty}</td>		          
								  <td align="center">${issueQty}</td>
								  <td align="center">${periodicQty}</td>				  
							</tr> 		        		      
					       </table>    
	</c:if>
</form>
</body>
</html>

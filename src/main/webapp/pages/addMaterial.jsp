<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Material Master</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

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
	
	$("#grnNo").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoReceiptNoteGrnNo.jsf", {
	           // term: extractLast(request.term)
	        	grnNo: $("#grnNo").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	 $("#autoStatus").html("Data not Available");
                }
             });
	    },
	    search: function () {  // custom minLength
	       	
	    	$("#grnSatus").hide();
	    	//$("#grnNo").val("");
	    	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	 $("#autoStatus").html("");
	    	$("#toolingReceiptId").val(ui.item.id);
	    	funLoadInspectionDetails() ;
	    }
	 });  
	
	$("#srchMinProductName").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoDrawingNo.jsf", {
	           // term: extractLast(request.term)
	        	drawingNo: $("#srchMinProductName").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	 $("#autoStatus").html("Data not Available");
                }
             });
	    },
	    search: function () {  // custom minLength
	       	
	    	$("#grnSatus").hide();
	    	//$("#grnNo").val("");
	    	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	 $("#autoStatus").html("");
	    	$("#srchMinProductName").val(ui.item.id);
	    	funSearchMinProductName(ui.item.id);
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
	 $("#toolingRequestId").val("0");
	 $("#frmMaterial").attr("action","showAddReceivingRequest.jsf");                 
	 $("#frmMaterial").submit();
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
	 var materialType = $("#materialType").val();
	 if(materialType == "")
     {        	
		    jAlert('Please Select Material Type', 'Material Type is Empty', function(){	
		    	$("#materialType").focus();						  			    	 
	        });	
		   return false;
     }
     else if($("#materialCode").val() == "")
     {
    	 jAlert('Please Enter Material code.', 'Material code is Empty');
    	 $("#materialCode").focus();		    		
		 return false;		
     }else if($("#materialName").val() == "")
     {
    	 jAlert('Please Enter materialName.', 'materialName is Empty');
    	 $("#materialName").focus();		    		
		 return false;		
     }
     /* else if($("#materialQty").val() == "")
     {
    	 jAlert('Please Enter materialQty.', 'materialQty is Empty');
    	 $("#materialQty").focus();		    		
		 return false;		
     } */
     else if($("#uom").val() == "")
     {
    	 jAlert('Please Enter Uom.', 'Uom is Empty');
    	 $("#uom").focus();		    		
		 return false;		
     }else if($("#minStockLevel").val() == "")
     {
    	 jAlert('Please Enter Minimum Stock Level.', 'minStockLevel code is Empty');
    	 $("#minStockLevel").focus();		    		
		 return false;		
     }else if($("#reorderLevel").val() == "")
     {
    	 jAlert('Please Enter RE Order Level.', 'reorderLevel is Empty');
    	 $("#reorderLevel").focus();		    		
		 return false;		
     }
     else
     {
		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();  
		 
		 if(val == "Save")
		 {
		 	$("#frmMaterial").attr("action","addMaterial.jsf");   
		 }
		 else
		 {
			$("#frmMaterial").attr("action","updateMaterial.jsf");
		 }
	     $("#frmMaterial").submit();
     }
 }
 
 function funToolingList()
 {
     $("#materialCode").val(0); 
	 $("#frmMaterial").attr("action","mstMaterial.jsf");   	                
     $("#frmMaterial").submit();
 }
 

 
 function funClear()
 {
	 $("#materialCode").val("0"); 
	 $("#frmMaterial").attr("action","viewMaterial.jsf");   	                
     $("#frmMaterial").submit(); 
 }
 

 
 

 

</script>

</head>
<body class="body" onload="Init();">
<form name="frmMaterial" method="post"  id="frmMaterial" autocomplete="off">
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
           <td width="35%" class="heading" align="left">&nbsp;Material Master</td> 
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
				<table style="width:70%" align="left" cellpadding="3" cellspacing="0" border="0">		
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Material Type&nbsp;<span class="formlabel">*</span></td>
					    <td class="formlabelblack" align="left">
						<select name="materialType" id="materialType" class="textmedium">
			             		<option value="">Select</option>
			             	    <c:forEach items="${lstMaterialTypes}" var="materialtype" varStatus="row"> 
			             	    <c:set var="selected" scope="request" value="${''}"/>   
			             	    <c:if test="${materialtype.key ne NULL && materialtype.key eq materialType}">
									<c:set var="selected" scope="request" value="${'SELECTED'}"/>  
			  				 	 </c:if>
			                       <option value="${materialtype.key}" ${selected} >${materialtype.value}</option>
			                    </c:forEach>
		                	</select>	
		                </td>	
				   </tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Material code&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="materialCode" id="materialCode" class="textmedium" value="${materialCode}"   maxlength="7"  autofocus autoComplete="off" readonly="readonly"/></td>		
				   </tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Material Name&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="materialName" id="materialName" class="textmedium" value="${materialName}"   maxlength="150"  autofocus autoComplete="off"/></td>		
				   </tr>
				  <%--  <tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Minimum Quantity&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="materialQty" id="materialQty" class="textmedium" value="${materialQty}"   maxlength="150"  autofocus autoComplete="off"/></td>		
				   </tr> --%>
				   <tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">UOM&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="uom" id="uom" class="textmedium" value="${uom}"   maxlength="150"  autofocus autoComplete="off"/></td>		
				   </tr>	
				   <tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Minimum Stock Level&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="minStockLevel" id="minStockLevel" class="textmedium" value="${minStockLevel}"   maxlength="150"  autofocus autoComplete="off"/></td>		
				   </tr>	
				   <tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">RE Order Level&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="reorderLevel" id="reorderLevel" class="textmedium" value="${reorderLevel}"   maxlength="150"  autofocus autoComplete="off"/></td>		
				   </tr>					     		    
					<tr>
						<td colspan="3"></td>
					</tr>	
				</table>
				<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0" width="99%" align="center">
					<tr height="15px">
					</tr>
				</table>
             <!-- END -->
         
             
			  
			    
				 <%
					int index = 1;
				 %>
				
		       
		       <input type="hidden" name="delValue" id="delValue" value="" />
		       <input type="hidden" name="action" id="action" value="${action}"/>
		       <input type="hidden" name="count" id="count" value="${count}" />
		       <input type="hidden" name="message" id="message" value="${message}" />
		       			     
	    </DIV>
     </center>    
     
     <table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
	    <tr>
	    <td></td>
           <td height="50px" align="left">
		     <input  type="button" class="btn btnImportant" value="${btnStatusVal}" onclick="javascript:processInspection('${btnStatusVal}');" ${btnStatus} />&nbsp;&nbsp;

		     <input type="button" class="btn btnNormal"  onclick="javascript:funToolingList();" value="List" /></td>
	    </tr>
	 </table>   
	     
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
	 <input type="hidden" name="approvalflag" id="approvalflag" value="1"/>
	 <input type="hidden" name="approvedby" id="approvedby"/>
</form>
</body>
</html>
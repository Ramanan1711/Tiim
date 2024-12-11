<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String addAccess = "";
String editAccess = "";
String deleteAccess = "";
String viewAccess = "";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Material Receipt Note</title>
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
	
	$("#supplierName").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoSupplierName.jsf", {
	           // term: extractLast(request.term)
	        	supplierName: $("#supplierName").val()
	        }, response).success(function(data) {
                   if(data.length == 0)
                   {
                       $("#supplierStatus").html("Data not Available");
                   }
                });
	            
	    },
	    search: function (data ) {  // custom minLength
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 2,
	    select: function (event, ui) {
	    	 $("#supplierStatus").html("");
	    	 $("#supplierCode").val(ui.item.id);
	         $("#supplierName").val(ui.item.value);
	    }
   });
	
	
	$("#materialCode").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoMaterialCode.jsf", {
	           // term: extractLast(request.term)
	        	materialCode: $("#materialCode").val()
	        }, response).success(function(data) {
                   if(data.length == 0)
                   {
                       $("#materialCodeStatus").html("Data not Available");
                   }
                });
	            
	    },
	    search: function (data ) {  // custom minLength
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 2,
	    select: function (event, ui) {
	    	 $("#materialCodeStatus").html("");
	    	 $("#materialCode").text(ui.item.id);
	         $("#materialName").val(ui.item.label);
	         $("#uom").val(ui.item.id);

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
	 var materialGrnNo = $("#materialGrnNo").val();
	 var materialGrnDate = $("#materialGrnDate").val();
	 var dcNo = $("#dcNo").val();
	 var dcDate = $("#dcDate").val();
	 var supplierCode = $("#supplierCode").val();
	 var supplierName = $("#supplierName").val();
	 var billNo = $("#billNo").val();
	 var billDate = $("#billDate").val();
	 var materialCode = $("#materialCode").val();
	 var lotNumber = $("#lotNumber").val();
	 var materialName = $("#materialName").val();
	 var materialQty = $("#materialQty").val();
	 var uom = $("#uom").val();
	 var mgfDate = $("#mgfDate").val();
	 var receivedBy = $("#receivedBy").val();

	 if(materialGrnNo == "")
    {        	
		    jAlert('Please Enter the Grn Number', 'Grn Number is Empty', function(){	
		    	$("#materialGrnNo").focus();						  			    	 
	        });	
		   return false;
    }
	 else if(materialGrnDate == "")
	 {
		 jAlert('Please Enter the Grn date', 'Grn date is Empty', function(){	
	    	 $("#materialGrnDate").focus();		    		
	        });	
		   return false;
	 } else if(dcNo == "")
    {
   	 jAlert('Please Enter the dcNo', 'dcNo is Empty');
   	 $("#dcNo").focus();		    		
		 return false;		
    } else if(dcDate == "")
    {
   	 jAlert('Please Enter the dcDate', 'dcDate is Empty');
   	 $("#dcDate").focus();		    		
		 return false;		
    } else if(supplierCode == "")
    {
   	 jAlert('Please Enter the Supplier Code', 'Supplier Code is Empty');
   	 $("#supplierCode").focus();		    		
		 return false;		
    } else if(supplierName == "")
    {
   	 jAlert('Please Enter the Supplier Name', 'Supplier Name is Empty');
   	 $("#supplierName").focus();		    		
		 return false;		
    } else if(billNo == "")
    {
      	 jAlert('Please Enter the Bill No', 'Bill No is Empty');
      	 $("#billNo").focus();		    		
   		 return false;		
    } else if(billDate == "")
    {
      	 jAlert('Please Enter the Bill Date', 'Bill Date is Empty');
      	 $("#billDate").focus();		    		
   		 return false;		
    }else if(materialCode == "")
    {
     	 jAlert('Please Enter the Material Code', 'Material Code is Empty');
     	 $("#materialCode").focus();		    		
  		 return false;		
   }else if(lotNumber == "")
   {
    	 jAlert('Please Enter the Lot Number', 'Lot Number is Empty');
    	 $("#lotNumber").focus();		    		
 		 return false;		
  }else if(materialName == "")
  {
   	 jAlert('Please Enter the Material Name', 'Material Name is Empty');
   	 $("#materialName").focus();		    		
		 return false;		
 }else if(materialQty == "")
 {
  	 jAlert('Please Enter the Material Quantity', 'Material Quantity is Empty');
  	 $("#materialQty").focus();		    		
		 return false;		
}else if(uom == "")
{
 	 jAlert('Please Enter the UOM', 'UOM is Empty');
 	 $("#uom").focus();		    		
		 return false;		
}else if(mgfDate == "")
{
 	 jAlert('Please Enter the mgfDate', 'mgfDate is Empty');
 	 $("#mgfDate").focus();		    		
		 return false;		
}else if(receivedBy == "")
{
 	 jAlert('Please Enter the Received By', 'Received By is Empty');
 	 $("#receivedBy").focus();		    		
		 return false;		
} else
      	 {
   		 $("#loadoverlay").fadeIn();  
   		 $("#ajaxLoading").fadeIn();  
   		 
   		 if(val == "Save")
   		 {
   		 	$("#frnReceiptNote").attr("action","addMaterialReceipt.jsf");   
   		 }
   		 else
   		 {
   			$("#frnReceiptNote").attr("action","updateMaterialReceipt.jsf");
   		 }
   	     $("#frnReceiptNote").submit();
   		 
   	}
		 
    }

function funToolingList()
{
    $("#materialGrnNo").val(0); 
    $("#materialCode").val(0); 
    $("#materialQty").val(0); 
	 $("#frnReceiptNote").attr("action","showMaterialReceiptLst.jsf");   	                
    $("#frnReceiptNote").submit();
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
	  	         $("#batchQty").val(result.tabletProducedQty); 

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
<form name="frnReceiptNote" id="frnReceiptNote" method="post" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>
<%
	HashMap<String, RoleVsUser> hmRoleVsUser1 = (HashMap<String, RoleVsUser>)session.getAttribute("RoleVsUser");
	RoleVsUser roleVsUser1 = hmRoleVsUser1.get("Receipt Note");
	if(roleVsUser1 != null)
	{
	 addAccess = roleVsUser1.getAddAccess1();
	 editAccess = roleVsUser1.getEditAccess1();
	 deleteAccess = roleVsUser1.getDeleteAccess1();
	 viewAccess = roleVsUser1.getViewAccess1();
	}
 %>
 <div id="loadoverlay" class="loading_overlay" ></div>
 <div id="ajaxLoading" style=" position: absolute; z-index: 700;top:105px; left: 405px; width: 450px; height: 50px; background-color:#ffffff;">
          <img  src="./images/loadmain.gif"></img>
 </div>
 <div id="processloading" style=" position: absolute; z-index: 700;top:105px; left: 405px;width: 450px; height: 50px; background-color:#ffffff;display:none;">
          <img  src="./images/Processing.gif"></img>
 </div>
 
       <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
		   <tr><td></td></tr>	     
		   <tr>
		   		<td>
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">	     
		              <tr>
				          <td width="25%" align="left" class="heading">Material Receipt Note</td>	
				           <td width="25%" class="submenutitlesmall" align="right">        
           		     <table cellspacing="1" cellpadding="2" width="57%" height="20px" align="left" style="display:none" id="msg">	
			        	<tr>  
				        <td class="confirmationmessage" align="center">&nbsp;&nbsp;&nbsp;<span id="dynmsg" style="align:left;">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="msganchor" id="msgclose">X</a></td>
				       </tr>
			          </table>        
                      </td>                                    
				      </tr>	 		     
				      <tr><td colspan="2"></td></tr>				      
				   </table>   
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
				      <tr>
				          <td class="formlabelblack" align="left">GRN No&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="materialGrnNo" id="materialGrnNo" class="textmin" value="${materialGrnNo}" autoComplete="off" readonly="readonly"/></td>
			              <td class="formlabelblack" align="left">GRN Date&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" align="left"><input type="text" name="materialGrnDate" id="materialGrnDate" class="textmin" value="${materialGrnDate}" maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
			              <td class="formlabelblack" align="left">Dc No&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="dcNo" id="dcNo" class="textmin" value="${dcNo}" maxlength="50" autoComplete="off"/></td>
			              <td class="formlabelblack" align="left">DC Date&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" align="left"><input type="text" name="dcDate" id="dcDate" class="textmin" value="${dcDate}" maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
			             
				     </tr>
				     <tr>
				     	  <td class="formlabelblack" align="left">Supplier Name&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" align="left"><input type="text" name="supplierName" id="supplierName" class="textmin" value="${supplierName}" maxlength="50" autoComplete="off" /></td>
				          <td class="formlabelblack" align="left">Supplier Code&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="supplierCode" id="supplierCode" class="textmin" value="${supplierCode}" maxlength="50" autoComplete="off" /></td>
			              
			              <td class="formlabelblack" align="left">Bill No&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="billNo" id="billNo" class="textmin" value="${billNo}" maxlength="50"  autoComplete="off"/></td>
			              <td class="formlabelblack" align="left">Bill Date&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" align="left"><input type="text" name="billDate" id="billDate" class="textmin" value="${billDate}" maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
			             
				     </tr>
				     <tr>
						<td colspan="8" class="formlabelblack"><span id="supplierStatus" class="popuptopalert"></span><hr class="style-six"></td>
					</tr>
				   </table>
				   <table style="width:100%" align="center" cellpadding="2" cellspacing="0" border="0">
				    <tr>
				    	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Lot Number&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="lotNumber" id="lotNumber" value="${lotNumber}" class="textsmall" readonly="readonly"/></td>
			         </tr>   	         
			          <tr>
			          	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Material Code&nbsp;</td>		
		             <td class="formlabelblack" align="left"><input type="text" name="materialCode" id="materialCode" value="${materialCode}"class="textsmall" />&nbsp;<span id="materialCodeStatus" class="popuptopalert"></span></td>
			         </tr> 		        	   				        			         
			          <tr>
			          	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Material Name&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="materialName" id="materialName" value="${materialName}" class="textsmall" maxlength="100" readonly="readonly"/></td>
			         </tr>  
				           				        			         
			           <tr>
			           	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Quantity&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="materialQty" id="materialQty" value="${materialQty}" class="textsmall" maxlength="150"/></td>
			         </tr>
			         <tr>
			         	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">UOM&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="uom" id="uom" value="${uom}" class="textsmall" maxlength="150" readonly="readonly"/></td>
			         </tr>
			         <tr>
			         	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Mgf date&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="mgfDate" id="mgfDate" value="${mgfDate}" class="textsmall" maxlength="150"/></td>
			         </tr>
			         <tr>
			         	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Received by&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="receivedBy" id="receivedBy" value="${username}" class="textsmall" maxlength="150" readonly="readonly"/></td>
			         </tr>
			          <tr>
			             <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Remark&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="remark" id="remark" value="${remark}" class="textsmall" maxlength="150"/></td>
			         </tr>
			       </table>
			       <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
			          <tr height="15px">
						<td colspan="2" class="formlabelblack"><hr class="style-six"></td>
					  </tr>
			         <tr>
			             <td width="25%" class="formlabelblack" align="left"><span class="formlabel">*</span>&nbsp;- required fields</td>
			             <td width="75%" class="formlabelblack" align="left"><input  type="button" class="btn btnImportant" id="btnprocess" value="${btnStatusVal}" onclick="javascript:processInspection('${btnStatusVal}');"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btnNormal" id="btnClear" value="Close" onclick="javascript:funToolingList();"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			         </tr>	
			         <tr height="2px"><td colspan="2">&nbsp;</td></tr> 
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
			<input type="hidden" name="action" id="action" value="${action}"/>
			<input type="hidden" name="message" id="message" value="${message}" />	
			<input type="hidden" name="isActive" id="isActive" value="${isActive}"/>


<input type="hidden" name="deleteStatus" id="deleteStatus" />
<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />

<input type="hidden" name="type" id="type" />

</form>
</body>
</html>

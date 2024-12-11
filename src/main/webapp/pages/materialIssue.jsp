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
<title>Material Issue</title>
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
	 var issueNo = $("#issueNo").val();
	 var issueDate = $("#issueDate").val();
	 var materialCode = $("#materialCode").val();
	 var lotNumber = $("#lotNumber").val();
	 var materialName = $("#materialName").val();
	 var materialQty = $("#materialQty").val();
	 var uom = $("#uom").val();
	 var materialType = $("#materialType").val();
	 var issuedBy = $("#issuedBy").val();

	 if(issueNo == "")
    {        	
		    jAlert('Please Enter the Issue Number', 'Issue Number is Empty', function(){	
		    	$("#issueNo").focus();						  			    	 
	        });	
		   return false;
    }
	 else if(issueDate == "")
	 {
		 jAlert('Please Enter the Issue date', 'Issue date is Empty', function(){	
	    	 $("#issueDate").focus();		    		
	        });	
		   return false;
	 } else if(materialType == "")
    {
   	 jAlert('Please Enter the materialType', 'materialType is Empty');
   	 $("#materialType").focus();		    		
		 return false;		
    } else if(issuedBy == "")
    {
   	 jAlert('Please Enter the Issued By', 'Issued By is Empty');
   	 $("#issuedBy").focus();		    		
		 return false;		
    } else if(lotNumber == "")
   {
    	 jAlert('Please Enter the Lot Number', 'Lot Number is Empty');
    	 $("#lotNumber").focus();		    		
 		 return false;		
  } else if(materialCode == "")
  {
  	 jAlert('Please Enter the Material Code', 'Material Code is Empty');
  	 $("#materialCode").focus();		    		
		 return false;		
  } else if(materialName == "")
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
} else
      	 {
   		 $("#loadoverlay").fadeIn();  
   		 $("#ajaxLoading").fadeIn();  
   		 
   		 if(val == "Save")
   		 {
   			$("#stockQty").val(0); 
   		    $("#issuedQty").val(0);
   		 	$("#frnMaterialIssue").attr("action","addMaterialIssue.jsf");   
   		 }
   		 else
   		 {
   			 var stockQty = $("#stockQty").val();
   			 var issuedQty = $("#issuedQty").val();

   			if((stockQty+issuedQty)-materialQty < 0)
   			{
   			 jAlert('Issue Quantity should not be greater than Stock Quantity', 'materialQty is Empty');
   			 $("#materialQty").focus();		    		
   			 return false;	
   			}
   			$("#frnMaterialIssue").attr("action","updateMaterialIssue.jsf");
   		 }
   	     $("#frnMaterialIssue").submit();
   		 
   	}
		 
    }

function funToolingList()
{
    $("#issueNo").val(0); 
    $("#toolRequestNo").val(0); 
    $("#materialCode").val(0); 
    $("#materialQty").val(0); 
    $("#stockQty").val(0); 
	$("#issuedQty").val(0);
	 $("#frnMaterialIssue").attr("action","showMaterialIssueLst.jsf");   	                
    $("#frnMaterialIssue").submit();
}

function funFillStockDetails(evt,obj)      
{
	  $("#lotNumber").autocomplete({
		    source: function (request, response) {
		        $.getJSON("${pageContext. request. contextPath}/autoLotNo.jsf", {
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

function funLoadStockDetails(materialGrnNo)
{ 
	 $.ajax({
		dataType : 'json',
       url : "${pageContext.request.contextPath}/getMaterialReceipt.jsf?materialGrnNo="+parseInt(materialGrnNo),
       type : "POST",
       success : function(result) 
       {
       	if(result != "")
	    	{		
  			     $("#toolRequestNo").val(result.toolRequestNo);
       			 $("#lotNumber").val(result.lotNumber);
	  	         $("#materialCode").val(result.materialCode); 
	  	         $("#materialName").val(result.materialName); 
	  	         $("#materialQty").val(result.materialQty); 
	  	         $("#uom").val(result.uom); 
	  	         $("#receivedBy").val(result.receivedBy); 

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
<form name="frnMaterialIssue" id="frnMaterialIssue" method="post" autocomplete="off">
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
				          <td width="25%" align="left" class="heading">Material Issue</td>	
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
				          <td class="formlabelblack" align="left">&nbsp;&nbsp;&nbsp;Issue No&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="issueNo" id="issueNo" class="textsmall" value="${issueNo}" autoComplete="off" readonly="readonly"/></td>
			              <td class="formlabelblack" align="left">Issue Date&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" align="left"><input type="text" name="issueDate" id="issueDate" class="textsmall" value="${issueDate}" maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
			             
				     </tr>
				     <tr>
				     	  <td class="formlabelblack" align="left">&nbsp;&nbsp;&nbsp;Issued By&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" align="left"><input type="text" name="issuedBy" id="issuedBy" class="textsmall" value="${issuedBy}" maxlength="50" autoComplete="off" readonly="readonly"/></td>
				          <td class="formlabelblack" align="left">Material Type&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="materialType" id="materialType" class="textsmall" value="${materialType}" maxlength="50" autoComplete="off" /></td>
				     </tr>
				     <tr>
						<td colspan="4" class="formlabelblack"><span id="supplierStatus" class="popuptopalert"></span><hr class="style-six"></td>
					</tr>
				   </table>
				   <table style="width:100%" align="center" cellpadding="2" cellspacing="0" border="0">
				    <tr>
				    	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Lot Number&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="lotNumber" id="lotNumber" value="${lotNumber}" class="textsmall" onkeydown="javascript:funFillStockDetails(event,this);"/></td>
			         </tr>
			         <tr>
			         	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Tool Request No&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="toolRequestNo" id="toolRequestNo" value="${toolRequestNo}" class="textsmall" maxlength="150" readonly="readonly"/></td>
			         </tr>   	         
			          <tr>
			          	 <td width="10%" class="formlabelblack" align="left">&nbsp;</td>
			             <td class="formlabelblack" align="left">Material Code&nbsp;</td>		
		             <td class="formlabelblack" align="left"><input type="text" name="materialCode" id="materialCode" value="${materialCode}"class="textsmall" readonly="readonly"/></td>
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
			             <td class="formlabelblack" align="left">Received by&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="receivedBy" id="receivedBy" value="${receivedBy}" class="textsmall" maxlength="150"/></td>
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
			<input type="hidden" name="issuedQty" id="issuedQty" value="${issuedQty}" />	
			<input type="hidden" name="stockQty" id="stockQty" value="${stockQty}" />	

<input type="hidden" name="deleteStatus" id="deleteStatus" />
<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />

<input type="hidden" name="type" id="type" />

</form>
</body>
</html>

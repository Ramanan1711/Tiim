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
td {
    padding: 15px;
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
	
$("#indentNo").keyup(function()           
		   {
		    	if(trim($("#indentNo").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	       });
	       
});


 function Init()
 {	
	var msg = $("#message").val();
	if(msg != "")  
	{    
		$("#msg").fadeIn(300);	   
    }
 }

 function trim(stringTrim)
 {
	if(stringTrim != undefined) 
	{
 		return stringTrim.replace(/^\s+|\s+$/g,"");
	}
 }
 
 function showimg()
 {
 	$("#ajaxLoading").show();  
 }
 
 function processIntent(val)
 {
 		 $("#loadoverlay").fadeIn();  
		 $("#ajaxLoading").fadeIn();   	 			                    	                  
		   var alertmsg = "";
		   var indentNo = trim($("#indentNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var toolingCodeNumber = trim($("#toolingCodeNumberPO").val());
		   var toolingLotNumber= trim($("#toolingLotNumberPO").val());
		   var productName =  trim($("#productNamePO").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   var shape = trim($("#shape").val());
		   var dimensions = trim($("#dimensionsPO").val());
		   var dustCapGroove = trim($("#dustCapGroove").val());
		   var poQuantity = trim($("#poQuantity").val());
		   var minQuantity = trim($("#minQuantity").val());
		   if(indentNo == "")
	       {        
			   alertmsg = "Indent No is required"	   
	           $("#techAlert").text(alertmsg);
	      	   $("#indentNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#toolingLotNumberPO").focus();
	      	   return false;
	       }
	       else if(toolingCodeNumber == "")
	       {
	    	   alertmsg = "Tooling code number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#toolingCodeNumberPO").focus();
	      	   return false;
	    	}
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		   else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productNamePO").focus();
	      	   return false;
	       }
		   else if(dimensions == "")
		   {
			   alertmsg = "Dimensions of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#dimensionsPO").focus();
			   return false;
		   }
		   else if(shape == "")
		   {
			   alertmsg = "Shape of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#shape").focus();
			   return false;
		   }
		   else if($("#embosingUpper").val() == "")
		   {
			   alertmsg = "Embossing Upper is required";
			   $("#techAlert").text(alertmsg);
			   $("#embosingUpper").focus();
			   return false;
			 
		   }
		   else if($("#embosingLower").val() == "")
		   {
			   alertmsg = "Embossing Lower is required";
			   $("#techAlert").text(alertmsg);
			   $("#embosingLower").focus();
			   return false;
				
		   }
		   else if($("#hardCromePlating").val() == "")
		   {
			   alertmsg = "Plating is required";
			   $("#techAlert").text(alertmsg);
			   $("#hardCromePlating").focus();
			   return false;
		   }
		   else if($("#machineTypePO").val() == "")
		   {
			   alertmsg = "Machine Type  is required";
			   $("#techAlert").text(alertmsg);
			   $("#machineTypePO").focus();
			   return false;
		   }
		   else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   }
		   else if(mocPunches == "")
		   {
			   alertmsg = "MOC - Punches is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocPunches").focus();
			   return false;
		   }
		   else if(mocDies == "")
		   {
			   alertmsg = "MOC - Dies is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocDies").focus();
			   return false;
		   }
		   else if(dustCapGroove == "")
		   {
			   alertmsg = "Dustcup is required";
			   $("#techAlert").text(alertmsg);
			   $("#dustCapGroove").focus();
			   return false;
		   }
		   else if($("#uomPO").val() == "")
		   {
			   alertmsg = "UOM is required";
			   $("#techAlert").text(alertmsg);
			   $("#uomPO").focus();
			   return false;
		   }
		   else if(poQuantity == "")
		   {
			   alertmsg = "PO Qty is required";
			   $("#techAlert").text(alertmsg);
			   $("#poQuantity").focus();
			   return false;
		   } 
		   else if(minQuantity == ""){
			   alertmsg = "Min Qty is required";
			   $("#techAlert").text(alertmsg);
			   $("#minQuantity").focus();
			   return false;
		   }
		   else {		
			if(parseInt(poQuantity) < parseInt(minQuantity)){
				 alertmsg = "Order quantity should be greater than Min Qty";
				 $("#techAlert").text(alertmsg);
				 $("#poQuantity").focus();
				 return false;
			}else
		 if(val == "Save")
		 {
			 
			 $("#frmIntent").attr("action","addToolIntent.jsf");   	      
		 }
		 else
		 {
			$("#frmIntent").attr("action","updateToolIndent.jsf");
		 }
	     $("#frmIntent").submit();
	}
 }
 
 function funSearchMinProduct(type)
 {
	   $("#type").val(type);
	   showProductNameListDialog();
 }
 
 function showProductNameListDialog()
 {
    $("#productDialog").hide();
    $("#productNameAlert").text("");
    $("#productNameListDialog").fadeIn(300);  
    $("#srchMinProductName").focus();
    $("#overlay").unbind("click");    
    $("#listProjectNames").html(""); 
    $("#srchMinProductName").val("");
    funSearchMinProductName();
 }

 function hideProductNameListDialog()
 {
    $("#productNameListDialog").fadeOut(300);
    $("#productDialog").show();
    $("#type").val("");
 } 
 
 function funSearchMinProductName()
 {
	   var srchMinProductName = $.trim($("#srchMinProductName").val());
	   {
		   $("#productNameAlert").text("");
		   $("#waitloadingProductName").show();

		   $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getProductDetailsByDrawingNo.jsf?searchProductName="+srchMinProductName,
		        type : "POST",
		        success : function(result) 
		        {
		  		   if(result != "")
		    	   {	
		  			  $("#listProjectNames").html(result); 
		  			  $("#waitloadingProductName").hide();
		    	   }
		        }
		    });
	   }
 }
 function funClearProducts()
 {
	     $("#indentNo").val("");
	     $("#po").val("");
	     $("#supplierCode").val("");
	     $("#supplierName").val("");
	     $("#productSerialNo").val("");
	     $("#toolingLotNumberPO").val(""); 
	     $("#drawingNo").val("");	   
		 $("#typeOfTool").val("");
		 $("#mocPunches").val("");
		 $("#mocDies").val("");
		 $("#shape").val("");
		 $("#dimensionsPO").val("");
		 $("#embosingUpper").val("");
		 $("#embosingLower").val("");
		 $("#hardChromePlating").val("Required");
		 $("#dustCapGroove").val("");
		 $("#poQuantity").val("");
		 $("#minQuantity").val("")
		 $("#machineTypePO").val("");
         $("#productNamePO").val(""); 
	     $("#uomPO").val("");	   
		 $("#indentNo").focus();
 }

 function funToolingList()
 {
     $("#indentNo").val(0); 
     $("#poQuantity").val(0);
     $("#minQuantity").val(0);
     $("#expiryDates").val(0);
 	 $("#frmIntent").attr("action","showIndent.jsf"); 
     $("#frmIntent").submit();
 }
 
 function funTollLotNo(lotPrefix,drawingNo)
 {
     var productNamePO = $.trim($("#productNamePO").val());
		   $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getToolingLotNoByProductName.jsf?productNamePO="+productNamePO+"&drawingNo="+drawingNo,
		        type : "POST",
		        success : function(data) 
		        {
		  		   if(data != "")
		    	   {	
		  			  $("#toolingLotNumberPO").val(lotPrefix+''+data); 
		    	   }
		        }
		    });
 }
 
 function funSltProductName(productId)
 {
   funLoadProductDetails(productId);
 }
 
 function funLoadProductDetails(productId)
 {
	   $.ajax({
			dataType : 'json',
	        url : "${pageContext.request.contextPath}/getProduct.jsf?productId="+productId,
	        type : "POST",
	        success : function(result) 
	        {
	  		   if(result != "")
	    	   {				
	  	    	  if($("#dynmsg").text() != "")
	  	    	  {
	  	    		 removemsg();
	  	    	  }       	 
	  	          $("#techAlert").empty(); 

	  	         $("#productNamePO").val(trim(result.productName)); 
	  	  	     $("#drawingNo").val(trim(result.drawingNo));	   
	  			 $("#machineTypePO").val(trim(result.machineType));
	  			 $("#typeOfTool").val(result.typeOfTool);
	  			 $("#mocPunches").val(trim(result.mocPunches));
	  			 $("#mocDies").val(trim(result.mocDies));
	  			 $("#shape").val(trim(result.shape));
	  			 $("#dimensionsPO").val(trim(result.dimensions));
	  			 $("#embosingUpper").val(trim(result.embosingUpper));
	  			 $("#embosingLower").val(trim(result.embosingLower));
	  			 $("#hardCromePlating").val(trim(result.hardChromePlating));
	  			 $("#dustCapGroove").val(trim(result.dustCapGroove));
	  			 $("#uomPO").val(trim(result.uom));
	  			 $("#expiryDates").val(result.expiryPeriod);
	  			 $("#punchSetNo").val(result.punchSetNo);
	  			 $("#compForce").val(result.compForce);
	  			/* var CurrentDate = new Date();
	  			CurrentDate.setMonth(CurrentDate.getMonth() + result.expiryPeriod);
	  			$("#expiryDates").val( CurrentDate.getDate( )  + '/' +(CurrentDate.getMonth( ) + 1 )+ '/' + CurrentDate.getFullYear( )); */
	  			
	  	         $("#productName").focus();
	  	       funTollLotNo(result.punchSetNo,(trim(result.drawingNo)));
	    	   }
	        }
	    });
	   hideProductNameListDialog();
 }
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmIndent" method="post"  id="frmIntent" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>

   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="10px">
	         <td></td>
	    </tr>
   </table>
		 
   <div id="productNameListDialog" class="max_dialog">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
	    <tr>
	        <td>
			   <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="18%" align="left" class="popuptoptitlelarge">Product Name</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideProductNameListDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr>
				        <td class="formlabelblack" align="center" colspan="2">
				             Search Drawing Number&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<input  type="text" name="srchMinProductName" id="srchMinProductName" maxlength="150" value="" class="textmedium" />
	                         &nbsp;<input type="button" value="Go" class="btn btnSMImportant" onclick="funSearchMinProductName();"/>
	                    </td>		        			       	       
				     </tr>
				    
			   </table>   
	   		</td>
	   </tr>
	   </table>   
       <div id="listProjectNames" style="height:300px;overflow:auto;"></div>                                       
   </div>

		 <table style="width:80%" align="center" cellpadding="2" cellspacing="0" border="0">	     
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;TOOL INDENT</td> 
		   <td width="55%" align="left" class="submenutitlesmall"><span class="popuptopalert" id="techAlert">${message}</span></td>	        
           <td width="20%" class="submenutitlesmall" align="right"></td>                                      
       </tr>
   </table>
   
   
   <center>   
<div id="toolIndent" > 
       
				   <table style="width:80%" align="center" cellpadding="1" cellspacing="0" border="0">
				      <tr>
				          <td class="formlabelblack" width="15%" align="left">Indent No&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" width="15%" align="left"><input type="text" name="indentNo" id="indentNo" class="textmin" value="${indentNo}" autoComplete="off" readonly/></td>
			              <td class="formlabelblack" width="15%" align="left">Indent Date&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" width="15%" align="left"><input type="text" name="indentDate" id="indentDate" class="textmin" value="${indentDate}" maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
<!-- 			              <td class="formlabelblack" width="15%" align="left">PO No&nbsp;<span class="formlabel">*</span></td>		 -->
			              <%-- <td class="formlabelblack" width="15%" align="left"><input type="text" name="po" id="po" class="textmin" value="${po}" maxlength="50" autoComplete="off"/></td> --%>
				   <td class="formlabelblack" align="left"></td>
			              <td class="formlabelblack" align="left"></td>
				     </tr>
				     <tr>
				     	  <td class="formlabelblack" align="left">Supplier Name&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" align="left"><input type="text" name="supplierName" id="supplierName" class="textmin" value="${supplierName}" maxlength="50" autoComplete="off" /></td>
				         <%--  <td class="formlabelblack" align="left">Supplier Code&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="supplierCode" id="supplierCode" class="textmin" value="${supplierCode}" maxlength="50" autoComplete="off"/></td> --%>
			              <td class="formlabelblack" align="left"></td>
			              <td class="formlabelblack" align="left"></td>
			                <td class="formlabelblack" align="left"></td>
			              <td class="formlabelblack" align="left"></td>
				     </tr>
				     <tr height="15px">
						<td colspan="6"><span id="supplierStatus" class="popuptopalert"></span><hr class="style-six"></td>
					</tr>
				     <tr>
			             <td class="formlabelblack" width="20%" align="left">Tooling Lot Number&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" width="15%" align="left"><input type="text" name="toolingLotNumberPO" id="toolingLotNumberPO" value="${toolingLotNumberPO}" class="textmin" readonly="readonly"/></td>
			             <td class="formlabelblack" align="left">Product Name&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="productNamePO" id="productNamePO" value="${productNamePO}" class="textmin" maxlength="150"readonly/></td>
			             <td class="formlabelblack" align="left">Drawing No.&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="drawingNo" id="drawingNo" value="${drawingNo}" class="textmin" maxlength="100"readonly/>&nbsp;<input type="button" value="" onclick="javascript:funSearchMinProduct('PO');" class="btnselect" /></td>
			         </tr>  
			         <tr>
			             <td class="formlabelblack" width="20%" align="left">Punch Set Number&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" width="15%" align="left"><input type="text" name="punchSetNo" id="punchSetNo" value="${punchSetNo}" class="textmin" readonly="readonly"/></td>
			             <td class="formlabelblack" align="left">Max Comp Force Limit&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="compForce" id="compForce" value="${compForce}" class="textmin" maxlength="150"readonly/></td>
			             <td class="formlabelblack" align="left">&nbsp;</td>		
			             <td class="formlabelblack" align="left">&nbsp;</td>
			         </tr>  
			           <tr>
			              <td class="formlabelblack" align="left">Dimensions of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="dimensionsPO" id="dimensionsPO" value="${dimensionsPO}" class="textmin" maxlength="50"readonly/></td>				                  
			             <td class="formlabelblack" align="left">Shape of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left">
			             	<select name="shape" id="shape" tabindex="8">
			             		<option value="">Select</option>
			             	<c:choose>
					  		    <c:when test="${shape eq 'Round'}">
					  				<option value="Round" SELECTED>Round</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Round">Round</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Capsule'}">
					  				<option value="Capsule" SELECTED>Capsule</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Capsule">Capsule</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Oval'}">
					  				<option value="Oval" SELECTED>Oval</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Oval">Oval</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Modified Capsule'}">
					  				<option value="Modified Capsule" SELECTED>Modified Capsule</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Modified Capsule">Modified Capsule</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Square'}">
					  				<option value="Square" SELECTED>Square</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Square">Square</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Triangle'}">
					  				<option value="Triangle" SELECTED>Triangle</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Triangle">Triangle</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Hex'}">
					  				<option value="Hex" SELECTED>Hex</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Hex">Hex</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Modified Capsule'}">
					  				<option value="Modified Capsule" SELECTED>Modified Capsule</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Modified Capsule">Modified Capsule</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Diamond'}">
					  				<option value="Diamond" SELECTED>Diamond</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Diamond">Diamond</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Heart'}">
					  				<option value="Heart" SELECTED>Heart</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Heart">Heart</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Rectangle'}">
					  				<option value="Rectangle" SELECTED>Rectangle</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Rectangle">Rectangle</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Barrel'}">
					  				<option value="Barrel" SELECTED>Barrel</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Barrel">Barrel</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${shape eq 'Pentagon'}">
					  				<option value="Pentagon" SELECTED>Pentagon</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Pentagon">Pentagon</option>
					  			</c:otherwise>
				  			</c:choose>
			                 <c:choose>
					  		    <c:when test="${shape eq 'Others'}">
					  				<option value="Others" SELECTED>Others</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Others">Others</option>
					  			</c:otherwise>
				  			</c:choose>
			                </select>
 							<td class="formlabelblack" align="left">Embossing Upper&nbsp;<span class="formlabel">*</span></td>
 			
 			               <td class="formlabelblack" align="left"><input type="text" name="embosingUpper" id="embosingUpper" value="${embosingUpper}" class="textmin" maxlength="50"/></td>			        
			         </tr>    
			         <tr>
			             <td class="formlabelblack" align="left">Embossing Lower&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="embosingLower" id="embosingLower" value="${embosingLower}" class="textmin" maxlength="50"/></td>			                 
			             <td class="formlabelblack" align="left">Plating&nbsp;<span class="formlabel">*</span></td>
			             
			             <td class="formlabelblack" align="left">
			               <select name="hardCromePlating" id="hardCromePlating" tabindex="12">
			             		<option value="">Select</option>
			                    <c:forEach items="${lstPlating}" var="plating" varStatus="row"> 
				             	    <c:set var="selected" scope="request" value="${''}"/>   
				             	    <c:if test="${plating ne NULL && plating.platingName eq hardCromePlating}">
										<c:set var="selected" scope="request" value="${'SELECTED'}"/>  
				  				 	 </c:if>
				                     <option value="${plating.platingName}" ${selected} >${plating.platingName}</option>
			                    </c:forEach>
		                	</select>
			                
			             </td>
			          <td class="formlabelblack" align="left">Machine Type&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             	<select name="machineTypePO" id="machineTypePO">
			             		<option value="">Select</option>
			                    <c:forEach items="${lstMachineType}" var="machineType" varStatus="row"> 
			             	    <c:set var="selected" scope="request" value="${''}"/>   
			             	    <c:if test="${machineType ne NULL && machineType eq machineTypePO}">
									<c:set var="selected" scope="request" value="${'SELECTED'}"/>  
			  				 	 </c:if>
			                       <option value="${machineType}" ${selected} >${machineType}</option>
			                    </c:forEach>
		                	</select>
			             </td>			                 
			         </tr>
					 <tr>
			             <td class="formlabelblack" align="left">Type of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left">
			             	<select name="typeOfTool" id="typeOfTool" tabindex="5">
			             		<option value="">Select</option>
			             	<c:choose>
					  		    <c:when test="${typeOfTool eq 'D/D'}">
					  				<option value="D/D" SELECTED>D/D</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="D/D">D/D</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${typeOfTool eq 'B/B'}">
					  				<option value="B/B" SELECTED>B/B</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="B/B">B/B</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${typeOfTool eq 'D/B'}">
					  				<option value="D/B" SELECTED>D/B</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="D/B">D/B</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${typeOfTool eq 'B/BB'}">
					  				<option value="B/BB" SELECTED>B/BB</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="B/BB">B/BB</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${typeOfTool eq 'B/BBS'}">
					  				<option value="B/BBS" SELECTED>B/BBS</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="B/BBS">B/BBS</option>
					  			</c:otherwise>
				  			</c:choose>
			                </select>
			             </td>
			             <td class="formlabelblack" align="left">MOC - Punches&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             <select name="mocPunches" id="mocPunches" tabindex="6">
			             		<option value="">Select</option>
			             	<c:choose>
					  		    <c:when test="${mocPunches eq 'O1'}">
					  				<option value="O1" SELECTED>O1</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="O1">O1</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${mocPunches eq 'S7'}">
					  				<option value="S7" SELECTED>S7</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="S7">S7</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${D2 eq 'D2'}">
					  				<option value="D2" SELECTED>D2</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="D2">D2</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${mocPunches eq 'D3'}">
					  				<option value="D3" SELECTED>D3</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="D3">D3</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${mocPunches eq 'S1'}">
					  				<option value="S1" SELECTED>S1</option>
					  			</c:when>
					  			<c:otherwise>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${mocPunches eq '440C'}">
					  				<option value="440C" SELECTED>440C</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="440C">440C</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${mocPunches eq 'Others'}">
					  				<option value="Others" SELECTED>Others</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Others">Others</option>
					  			</c:otherwise>
				  			</c:choose>
			                </select>
			             </td>
			             <td class="formlabelblack" align="left">MOC - Dies&nbsp;<span class="formlabel">*</span></td>		 
			             <td class="formlabelblack" align="left">
			             	<select name="mocDies" id="mocDies" onkeypress="javascript:funTypeMOCDies(event,this);" tabindex="7">
			             		<option value="">Select</option>
			             	<c:choose>
					  		    <c:when test="${mocDies eq 'D3'}">
					  				<option value="D3" SELECTED>D3</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="D3">D3</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${mocDies eq 'D2'}">
					  				<option value="D2" SELECTED>D2</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="D2">D2</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${mocDies eq 'S7'}">
					  				<option value="S7" SELECTED>S7</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="S7">S7</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${mocDies eq 'TC'}">
					  				<option value="TC" SELECTED>TC</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="TC">TC</option>
					  			</c:otherwise>
				  			</c:choose>
			                </select>
			             </td>
			             </tr>
			         <tr> 
			         	 <td class="formlabelblack" align="left">Dustcup&nbsp;<span class="formlabel">*</span></td>
			             
			             <td class="formlabelblack" align="left">
			             	<select name="dustCapGroove" id="dustCapGroove" tabindex="13">
				           	<option value="">Select</option>
				            <c:choose>
					  		    <c:when test="${dustCapGroove eq 'Std'}">
					  				<option value="Std" SELECTED>Std</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Std">Std</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${dustCapGroove eq 'Bellow'}">
					  				<option value="Bellow" SELECTED>Bellow</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Bellow">Bellow</option>
					  			</c:otherwise>
				  			</c:choose>
				  			<c:choose>
					  		    <c:when test="${dustCapGroove eq 'Others'}">
					  				<option value="Others" SELECTED>Others</option>
					  			</c:when>
					  			<c:otherwise>
									<option value="Others">Others</option>
					  			</c:otherwise>
				  			</c:choose>
      		                </select>  
			             </td>
			         	 <td class="formlabelblack" align="left">UOM<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="uomPO" id="uomPO" value="${uomPO}" class="textmin" maxlength="45" readonly/></td>
			             <td class="formlabelblack"></td><td class="formlabelblack"></td>
			              </tr>
			              <tr colspan="2">
			              	<td class="formlabelblack" align="left">Order Qty&nbsp;<span class="formlabel">*</span></td>
			             	<td class="formlabelblack" align="left"><input type="text" name="poQuantity" id="poQuantity" value="${poQuantity}" class="txtleast" tabindex="18"/></td>
			              	<td class="formlabelblack" align="left">Min Qty&nbsp;<span class="formlabel">*</span></td>
			             	<td class="formlabelblack" align="left"><input type="text" name="minQuantity" id="minQuantity" value="${minQuantity}" class="txtleast" tabindex="19"/></td>
			              	<td class="formlabelblack"></td><td class="formlabelblack"></td>              	
			              </tr>
			       </table>
			       <table style="width:80%" align="center" cellpadding="4" cellspacing="0" border="0">
			          <tr>
					<td class="formlabelblack" colspan="2"><hr class="style-six"></td>
					  </tr>
			         <tr>
			             <td width="25%" class="formlabelblack" align="left"><span class="formlabel">*</span>&nbsp;- required fields</td>
			             <td width="75%" class="formlabelblack" align="left"><input  type="button" class="btn btnImportant" id="btnprocess" value="${btnStatusVal}" onclick="javascript:processIntent(this.value);"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btnNormal" id="btnClear" value="Clear" onclick="javascript:funClearProducts();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="btncancel"  class="btn btnNormal"  value="Close" onclick="javascript:funToolingList();" /></td>
			         </tr>	
			         <tr height="2px"><td colspan="2">&nbsp;</td></tr> 
			        </table>
	            </td>
	       </tr>
	   </table> 
</div>
     </center>    
     
           
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 <input type="hidden" name="message" id="message" value="${message}" />
     <input type="hidden" name="action" id="action" value="${action}"/>
     <input type="hidden" name="expiryDates" id="expiryDates" value="${expiryDates}"/>
</form>
</body>
</html>
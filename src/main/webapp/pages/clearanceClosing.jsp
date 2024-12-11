<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Closing Clearance</title>
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
	
	$("#clearanceNo").change(function(){
		funLoadClearanceNoDetails($("#clearanceNo").val()) ;
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
	funFillStockDetails();
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
	 var clearanceNo = $("#clearanceNo").val();
	 var lotNumber = $("#lotNumber").val();
	 var clearanceDate = $("#clearanceDate").val();
	 var productName = $("#productName").val();
	 var batchNumber = $("#batchNumber").val();
	 var batchQty = $("#batchQty").val();
	 var currentBatchQty =  $("#currentBatchQty").val();
	 var closingNo = $("#closingNo").val();
	// var closingBatchQty = $("#closingBatchQty").val();

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
     } else if(closingNo == "")
     {
    	 jAlert('Please Enter the Closing Number', 'Closing Number is Empty');
    	 $("#closingNo").focus();		    		
		 return false;		
     } if(parseInt(batchQty) > parseInt(currentBatchQty)){
    	 jAlert('Please Enter the valid Batch Quantity', 'Batch Quantity should not be greater than clossing number quantity');
    	 $("#batchQty").focus();		    		
		 return false;		
     }/* else if(parseInt(closingBatchQty) > parseInt(currentBatchQty)){
    	 jAlert('Please Enter the valid Closing Batch Quantity', 'Batch Quantity should not be greater than clossing number quantity');
    	 $("#batchQty").focus();		    		
		 return false;		
     } */
     
     else
       	 {
    		 $("#loadoverlay").fadeIn();  
    		 $("#ajaxLoading").fadeIn();  
    		 
    		 if(val == "Save")
    		 {
    		 	$("#frmClearance").attr("action","addClearanceClosing.jsf");   
    		 }
    		 else
    		 {
    			$("#frmClearance").attr("action","updateClearanceClosing.jsf");
    		 }
    	     $("#frmClearance").submit();
    		 
    	}
		 
     }
 
 function funToolingList()
 {
	 $("#clearanceNo").empty();
	 $("#clearanceNo").append("<option value='0'>0</option>");
     $("#closingNo").val(0); 
     $("#lotNumber").val(0); 
     $("#currentBatchQty").val(0); 
     $("#closingBatchQty").val(0);
     
	 $("#frmClearance").attr("action","clearanceClosingList.jsf");   	                
     $("#frmClearance").submit();
 }
 
 function funFillStockDetails()      
 {
	 /*  $("#clearanceNo").autocomplete({
		    source: function (request, response) {
		        $.getJSON("${pageContext. request. contextPath}/autoClearanceNo.jsf", {
		        	clearanceNo: $("#clearanceNo").val()
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
		    	funLoadClearanceNoDetails(ui.item.id) ;
		    }
	   });   */
	  var clearanceNo = 0;
	  
	  $.ajax({
			dataType : 'json',
			async: "false",
	        url : "${pageContext.request.contextPath}/autoClearanceNo.jsf?clearanceNo="+clearanceNo,
	        type : "GET",
	        success : function(data) 
	        {
	        	 $("#clearanceNo").empty();
	        	 $("#clearanceNo").append("<option value='0'>-- Select --</option>");
	        	var len = data.length;
	        	 for( var i = 0; i<len; i++){
	                    var id = data[i]['id'];
	                    $("#clearanceNo").append("<option value='"+id+"'>"+id+"</option>");

	                }
	        }
	     });
	  
 }
 
 function funLoadClearanceNoDetails(clearanceNo)
 { 
	 $.ajax({
		dataType : 'json',
        url : "${pageContext.request.contextPath}/getClearanceNo.jsf?clearanceNo="+parseInt(clearanceNo),
        type : "POST",
        success : function(result) 
        {
        	if(result != "")
	    	{		
	       		 $("#lotNumber").val("");
	  	         $("#productName").text(""); 
	  	         $("#batchQty").text(""); 
	  	         $("#batchNumber").text(""); 
	  	         $("#currentBatchQty").text(""); 

        		 $("#lotNumber").val(result.lotNumber);
	  	         $("#productName").val(result.productName); 
	  	         $("#batchQty").val(result.batchQty);
	  	         $("#batchQty").val(result.batchQty); 
	  	         $("#batchNumber").val(result.batchNumber); 
	  	         $("#currentBatchQty").val(result.batchQty);
	  	         $("#closingBatchQty").val(result.batchQty);
	  	       
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
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="1px">		      
       <tr height="1px">
	           <td height="1px" align="center">&nbsp;</td>
		   </tr>
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Tool Life Cycle Management</td> 
           <td width="45%" class="submenutitlesmall" align="center">
           </td> 
           <td width="25%" class="submenutitlesmall" align="right">        
           </td>                                      
       </tr>
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Closing Clearance</td> 
            
           <td width="25%" class="submenutitlesmall" align="right">        
           		 <table cellspacing="1" cellpadding="2" width="57%" height="20px" align="left" style="display:none" id="msg">	
			     	<tr>  
				        <td class="confirmationmessage" align="center">&nbsp;&nbsp;&nbsp;<span id="dynmsg" style="align:center;">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="msganchor" id="msgclose">X</a></td>
				    </tr>
			   </table>        
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
						<td class="formlabelblack" align="left">Closing Number&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="closingNo" id="closingNo" class="textmedium" value ="${closingNo}" readonly="readonly" autofocus /></td>
					</tr>	
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Clearance Number&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left">
						<select name="clearanceNo" id="clearanceNo"><option>Select</option></select>
						<%-- <input  type="text" name="clearanceNo" id="clearanceNo" class="textmedium" value ="${clearanceNo}" maxlength="11" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" autofocus /> --%>
						</td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Lot Number&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="lotNumber" id="lotNumber" class="textmedium" value ="${lotNumber}" readonly="readonly" autofocus /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Date&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="clearanceDate" id="clearanceDate" class="textmedium"  value ="${clearanceDate}"  maxlength="11"  autofocus readonly="readonly" /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Product Name&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="productName" id="productName" class="textmedium" value ="${productName}" readonly="readonly"  autofocus /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Batch Number&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="batchNumber" id="batchNumber" class="textmedium" value ="${batchNumber}" readonly="readonly" autofocus /></td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Batch Quantity&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="closingBatchQty" id="closingBatchQty" class="textmedium" value ="${batchQty}" autofocus />
						</td>
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Closing Batch Quantity&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="batchQty" id="batchQty" class="textmedium" value ="${batchQty}" autofocus />
						<input  type="hidden" name="currentBatchQty" id="currentBatchQty" value ="${batchQty}" /></td>
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
				
		      
		          
			
	       
     </DIV>
     </center>    
     
     <table  border="0" cellspacing="0" cellpadding="0"  width="80%" align="center">
	   
	 </table>       
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
	<input type="hidden" name="action" id="action" value="${action}"/>
	<input type="hidden" name="message" id="message" value="${message}" />	        
</form>
</body>
</html>
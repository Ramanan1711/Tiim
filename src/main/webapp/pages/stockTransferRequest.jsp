<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<script type="text/javascript" src="./grid/PeriodicalInspection.js"></script>

<script>
$(document).ready(function(){ 
	
	 $("#requestedBy").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoRequestBy.jsf", {
	           // term: extractLast(request.term)
	        	requestBy: $("#requestedBy").val()
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
	    	setTimeout(function()
   		    {
	    		$("#serialNo1").focus();
		    	return false;
   		    },250);
	    }
   });   
	 
	 $("#fromBranch").autocomplete({
		    source: function (request, response) {
		        $.getJSON("${pageContext. request. contextPath}/autoBranchName.jsf", {
		           // term: extractLast(request.term)
		        	branchName: $("#fromBranch").val()
		        }, response).success(function(data) {
		            if(data.length == 0)
		            {
		            	// $("#autoStatus").html("Data not Available");
		            }
		         });
		    },
		    search: function () {  // custom minLength
		       	
		    	//$("#grnSatus").hide();
		    	//$("#grnNo").val("");
		    	
		    },
		    focus: function () {
		        // prevent value inserted on focus
		        return false;
		    },
		    autoFocus: true,
		    minLength: 1,
		    select: function (event, ui) {
		    	// $("#autoStatus").html("");
		    	$("#fromBranch").val(ui.item.id);
		    }
		 });  
	 
	 $("#toBranch").autocomplete({
		    source: function (request, response) {
		        $.getJSON("${pageContext. request. contextPath}/autoBranchName.jsf", {
		           // term: extractLast(request.term)
		        	branchName: $("#toBranch").val()
		        }, response).success(function(data) {
		            if(data.length == 0)
		            {
		            	// $("#autoStatus").html("Data not Available");
		            }
		         });
		    },
		    search: function () {  // custom minLength
		       	
		    	//$("#grnSatus").hide();
		    	//$("#grnNo").val("");
		    	
		    },
		    focus: function () {
		        // prevent value inserted on focus
		        return false;
		    },
		    autoFocus: true,
		    minLength: 1,
		    select: function (event, ui) {
		    	// $("#autoStatus").html("");
		    	$("#toBranch").val(ui.item.id);
		    }
		 });  
	 
	 $("#transferQty1").keypress(function(evt) {
		 
		   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
		   if(charCode != 8 && charCode != 0 && ((charCode < 48) || (charCode > 57)))
		   {
			   $("#transferQty1").val("");
			   return false;   
		   }
		   else
		   {
			   return true;
		   }
	});
	 
});



  function funFillStockDetails(evt,obj)      
  {
	  var cnt = $("#count").val();
	  $("#toolingLotNo1").autocomplete({
		    source: function (request, response) {
		        $.getJSON("${pageContext. request. contextPath}/autoStock.jsf", {
		           // term: extractLast(request.term)
		        	lotNo: $("#toolingLotNo1").val()
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
		    	$("#autoStatus").html("");
		    	funLoadStockDetails(ui.item.id) ;
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
 }

 function funLoadStockDetails(stockId)
 { 
	 $.ajax({
		dataType : 'json',
        url : "${pageContext.request.contextPath}/getIndividualStock.jsf?stockId="+parseInt(stockId),
        type : "POST",
        success : function(result) 
        {
        	var cnt = $("#count").val();
        	if(cnt == '')
        	{
        		cnt = 1;	
        	}
        	if(result != "")
	    	{		
        		 $("#stockId"+cnt).val("");
	       		 $("#toolingLotNo"+cnt).val("");
	       		 $("#txtToolingLotNo"+cnt).text(""); 
	       		 $("#txtMachineType"+cnt).text("");
	  	         $("#txtProductName"+cnt).text(""); 
	  	         $("#txtDrawingNo"+cnt).text("");
	  	         $("#txtTypeOfTool"+cnt).text("");
	  	       
	  			 $("#txtStockQty"+cnt).text("");
	  			 $("#txtUom"+cnt).text("");
        		
        		 $("#stockId"+cnt).val(result.stockId);
        		 $("#toolingLotNo"+cnt).val(result.toolingLotNumber);
        		 $("#txtToolingLotNo"+cnt).text(result.toolingLotNumber); 
        		 $("#txtMachineType"+cnt).text(result.machineName);
	  	         $("#txtProductName"+cnt).text(result.productName); 
	  	         $("#txtDrawingNo"+cnt).text(result.drawingNo);
	  	         $("#txtTypeOfTool"+cnt).text(result.typeOfTool);
	  			 $("#txtStockQty"+cnt).text(result.stockQty);
	  			 $("#stockQty1").val(result.stockQty);
	  			 $("#txtUom"+cnt).text(result.uom);
	  			 $("#transferQty"+cnt).focus();
	  			
	  			 
	  			 setTimeout(function()
	  		   	 {
	  				$("#toolingSerialNo"+cnt).focus();
	  				return false;
	  		   	 },200);
	    	}
        }
     });
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
	 var stockTransferId = $("#stockTransferId").val();
	 var fromBranch = $("#fromBranch").val();
	 var toolingLotNo1 = $("#toolingLotNo1").val();
	 var toBranch = $("#toBranch").val();
	 var transferQty1 = $("#transferQty1").val();
	
	 
	 if(stockTransferId == "")
     {        	
		    jAlert('Please Enter the Strock transfer id', 'Request No. is Empty', function(){	
		    	$("#stockTransferId").focus();						  			    	 
	        });	
		   return false;
     }
     else if(fromBranch == "")
     {
    	 jAlert('Please Enter the Requested By', 'Requested By is Empty');
    	 $("#fromBranch").focus();		    		
		 return false;		
     }
     else if(toBranch == "")
     {
    	 jAlert('Please Enter the To stock branch', 'Requested By is Empty');
    	 $("#toBranch").focus();		    		
		 return false;		
     }
     else if(toolingLotNo1 == "")
     {
    	 jAlert('Please Enter the Tooling lot number', 'Requested By is Empty');
    	 $("#toolingLotNo1").focus();		    		
		 return false;		
     }
     
     else if(transferQty1 == "")
	 {
    	 jAlert('Please Enter the transfer quantity', 'Requested By is Empty');
    	 $("#transferQty1").focus();		    		
		 return false;		
     }
     else
     {
    	 
    	 var isValid = true;
    	// for(i=1;i<=count;i++) {
             var txtStockQty =$("#stockQty1").val();
             var transferQty =$("#transferQty1").val();
             if(parseInt(transferQty) > parseInt(txtStockQty))
             {
            	 isValid = false;
            	 alert("Transfer Quantity should be less than Stock Quantity");
            	 $("#transferQty1").focus();
            	// return false;
             }
   		 //}
		 if(isValid)
		 {
			 $("#loadoverlay").fadeIn();  
			 $("#ajaxLoading").fadeIn();  
			 
			 if(val == "Save")
			 {
			 	$("#frmStockIssue").attr("action","addStockTransfer.jsf");   
			 }
			 else
			 {
				$("#frmStockIssue").attr("action","updateStockTransfer.jsf");
			 }
		     $("#frmStockIssue").submit();
		 }
     }
 }
 
 function funToolingList()
 {
	 $("#stockTransferId").val("0");
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmStockIssue").attr("action","showStockTransfer.jsf");   	                
     $("#frmStockIssue").submit();
 }
  
 function funTypeRequestNo(evt,obj)
 {
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode != 8 && charCode != 0 && ((charCode < 48) || (charCode > 57)))
	   {
		   return false;   
	   }
	   else
	   {
		   return true;
	   }
}
 

 
 function funClear()
 {
	 $("#frmStockIssue").attr("action","showStockTransfer.jsf");   	                
     $("#frmStockIssue").submit();
 }
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmStockIssue" method="post"  id="frmStockIssue" autocomplete="off">
<%@ include file="tiimMenu.jsp"%>

<div id="loadoverlay" class="loading_overlay" ></div>
<img id="ajaxLoading" src="./images/ultraloading.jpg" class="loadingPosition"></img>
<div id="overlay" class="web_dialog_overlay" ></div>

    <div id="confirmDeleteDialog" class="web_dialog_delete">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="80%" align="left" class="popuptoptitlelarge">Delete</td>			        			       
				        <td width="18%" align="right" class="popuptoptitle"><a href="javascript:hidedeleteDialog();" title="Click to Close" class="popupanchor" id="confrmDelClose">X</a>&nbsp;</td>
				     </tr>	
				     <tr><td colspan="2"></td></tr>
			    </table>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">				     
				     <tr height="20px"></tr>				     
				     <tr><td  class="popuplabel" align="center">Are you sure to Delete?</td></tr>	
				     <tr height="20px"></tr>		     			
				     <tr>					               
				        <td class="formlabelblack" align="center" >
				        <input type="button" value="Yes" class="btn btnSMNormal" onclick="javascript:funConfirmDelete();"/>&nbsp;				            
				        <input type="button" value="No" class="btn btnSMNormal" onclick="javascript:hidedeleteDialog();"/>
				        </td>	        			       	       
				     </tr>					     			     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div> 
	
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
           <td width="35%" class="heading" align="left">&nbsp;Stock Transfer Request</td> 
           <td width="55%" class="submenutitlesmall" align="center">
        
               <table cellspacing="1" cellpadding="2" width="57%" height="20px" align="left" style="display:none" id="msg">	
			     	<tr>  
				        <td class="confirmationmessage" align="center">&nbsp;&nbsp;&nbsp;<span id="dynmsg" style="align:center;">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="msganchor" id="msgclose">X</a>
				        
				        </td>
				    </tr>
			   </table>
           </td> 
           <td width="15%" class="submenutitlesmall" align="right">        
           		        
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
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;Stock Transfer Request</td>
						<td class="formlabelblack" align="left">Stock Transfer Request No.&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Stock Transfer Request Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="stockTransferId" id="stockTransferId" class="textmin" value="${stockTransferId}"   onkeypress="return funTypeRequestNo(event,this);"autofocus autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="stockDate" type="text" name="stockDate" class="textmin" value="${stockDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Transfer From&nbsp;<span class="formlabel">*</span></td>						
						<td width="10%" class="formlabelblack" align="left">Transfer To&nbsp;<span class="formlabel">*</span></td>											
					</tr>	
					<tr>
					    <td></td>
					    <td class="popuplabel" align="left">
					    	<c:choose>
					            <c:when test="${btnStatusVal eq 'Update'}">
				    				<input id="fromBranch" type="text" name="fromBranch" class="textmin" value="${fromBranch}" maxlength="50"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly  autoComplete="off"/>
				    			</c:when>
				    			<c:otherwise>
				    				<input id="fromBranch" type="text" name="fromBranch" class="textmin" value="${fromBranch}" maxlength="50"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly  autoComplete="off"/>
				    			</c:otherwise>
				    		</c:choose>	
				    	</td>
					   <td class="popuplabel" align="left">
					    	<c:choose>
					            <c:when test="${btnStatusVal eq 'Update'}">
				    				<input id="toBranch" type="text" name="toBranch" class="textmin" value="${toBranch}" maxlength="50"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly  autoComplete="off"/>
				    			</c:when>
				    			<c:otherwise>
				    				<input id="toBranch" type="text" name="toBranch" class="textmin" value="${toBranch}" maxlength="50"  autoComplete="off"/>
				    			</c:otherwise>
				    		</c:choose>	
				    	</td>
					</tr>					     		    
					<tr>
					    <td class="popuplabel" colspan="3" align="left"></td>
					</tr><tr>
					    <td class="popuplabel" colspan="3" align="left"><br></td>
					</tr>
					<!-- <tr>
						<td></td>							
						<td class="popuplabel"  align="left"><input id="clear" type="button" name="clear" value="Clear All" class="btn btnSMNormal" onclick="javascript:funClear();"/></td>
						<td class="popuplabel"  align="left"></td>									        	        			       	      
					</tr> 	 -->				
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
         
           <!--  <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center" height="30px">		      
					<tr>  			 		           
						<td align="right" width="100%" class="labelanchor">
							<img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;"></img>&nbsp;
							<input type="button" id="btnAddRow" onclick="javascript:funAddNewRow();" value="Add New Row" class="btn btnSMNormal"/>&nbsp;&nbsp;
					    </td>
					</tr>
			  </table>     --> 

				 <%
					int index = 1;
				%>      
		        <div class="table-responsive">
				<table class="table table-bordered" style="width:96%" align="center" id="periodicData">
		         <tr>
		              
		              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
		              <th align="center" width="10%">Product&nbsp;Name</th>
		              <th align="center" width="10%">Machine&nbsp;Name</th>
					  <th align="center" width="10%">Drawing&nbsp;No.</th>
					  <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					  <th align="center" width="10%">UOM</th>
		              <th align="center" width="10%">Stock&nbsp;Qty</th>
		              <th align="center" width="10%">Transfer&nbsp;Qty</th>           
		              <th align="center" width="10%">Actions</th> 
		         </tr>
		         <c:choose>
				    <c:when test="${action eq 'new'}">       
				       <tr id="rowid-1">
					      
					      <td align="center"><input type="text" name="toolinglotnumber" id="toolingLotNo1" class="textverysmall"  value=""  onkeydown="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo1" ></span></td>
				          <td align="center"><font id="txtProductName1" ></font></td>
				          <td align="center"><font id="txtMachineType1" ></font></td>		          
						  <td align="center"><font id="txtDrawingNo1" ></font></td>				  
						  <td align="center"><font id="txtTypeOfTool1" ></font></td>
						  <td align="center"><font id="txtUom1"></font></td>				  
						  <td align="center"><font id="txtStockQty1"></font><input type="hidden" name="txtStockQty" id="stockQty1"/></td>
						 
						  <td align="center"><input type="text" name="transferQty" id="transferQty1" class="textverysmall"  value=""  autocomplete="off"/><span style="display:none;" id="txtTransferQty1"></span></td>
						  
				          <td align="center"><a title="Click to Add Row in Between" style="visibility:hidden;"  href="#"><img border="0" name="midRow" id="newMidRow1" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('1');"><img border="0" src="./images/deleteicon.gif"/></a></td>		        
					   </tr>       
			        </c:when>
			        <c:otherwise>
			        		<c:choose>
								<c:when test="${fn:length(lstStockTransfer) gt 0}">
								
									<c:forEach items="${lstStockTransfer}" var="stockTransfer" varStatus="row">  	
									    <tr id="rowid-<%=index%>" >
								          
								          <td align="center"><input type="text" name="toolinglotnumber" id="toolingLotNo1" class="textverysmall"  value="${stockTransfer.toolinglotnumber1}"  onkeydown="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo<%=index%>" >${stockTransfer.toolinglotnumber1}</span></td>
								          <td align="center"><font id="txtProductName<%=index%>" >${stockTransfer.productName}</font></td>
								          <td align="center"><font id="txtMachineType<%=index%>" >${stockTransfer.machineType}</font></td>
								          		          
										  <td align="center"><font id="txtDrawingNo<%=index%>" >${stockTransfer.drawingNo}</font></td>				  
										  <td align="center"><font id="txtTypeOfTool<%=index%>" >${stockTransfer.typeOfTool}</font></td>	
										  <td align="center"><font id="txtUom<%=index%>" ></font>${stockTransfer.uom}</td>			  
										  <td align="center"><font id="txtStockQty<%=index%>" ></font>${stockTransfer.stockQty}<input type="hidden" name="txtStockQty" id="stockQty1" value="${stockTransfer.stockQty}"/></td>
										  
										  <td align="center">
										  		<input type="text" name="transferQty" id="transferQty1" class="textverysmall"  value="${stockTransfer.transferQty1}" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtTransferQty<%=index%>">${stockTransfer.transferQty1}</span>
										  		<input type="hidden" name="stockTransferDetailId" id="stockTransferDetailId1" class="textverysmall"  value="${stockTransfer.stockTransferDetailId1}" autocomplete="off"/><span style="display:none;" id="txtStockTransferDetailId<%=index%>">${stockTransfer.stockTransferDetailId1}</span>
										  		<input type="hidden" name="stockId" id="stockId1" class="textverysmall" />
										  </td>
										  
								          <%
								           if(index == 1)
								           {
								          %> 
								          <td align="center"><a title="Click to Add Row in Between" style="visibility:hidden;"  href="#"><img border="0" name="midRow" id="newMidRow1" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('<%=index%>');"><img border="0" src="./images/deleteicon.gif"/></a></td>
								          <% 
								           }
								           else
								           {
								          %>
								          <td align="center"><a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow<%=index%>" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('<%=index%>');"><img border="0" src="./images/deleteicon.gif"/></a></td>
								          <%
								           }
								          %>
							           </tr> 
							           <% index++;%>
							        </c:forEach> 
							           
								</c:when>
								<c:otherwise>
										<tr id="rowid-1" >
										  
									      <td align="center"><input type="text" name="toolinglotnumber" id="toolingLotNo1" class="textverysmall"  value=""  onkeydown="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo1" ></span></td>
								          <td align="center"><font id="txtProductName1" ></font></td>
								          <td align="center"><font id="txtMachineType1" ></font></td>		          
										  <td align="center"><font id="txtDrawingNo1" ></font></td>				  
										  <td align="center"><font id="txtTypeOfTool1" ></font></td>
										  <td align="center"><font id="txtUom1"></font></td>				  
										  <td align="center"><font id="txtStockQty1"></font></td>
										 
										  <td align="center"><input type="text" name="transferQty" id="transferQty1" class="textverysmall"  value=""  autocomplete="off"/><span style="display:none;" id="txtTransferQty1"></span></td>
										 <td align="center">
										 <input type="hidden" name="stockId" id="stockId1" class="textverysmall"  value="" />
										 <a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow<%=index%>" src="./images/up-arrow.png"/></a>&nbsp;<img border="0" src="./images/deleteicon.gif"/></td>
							           </tr> 
								</c:otherwise>
							</c:choose>
		          </c:otherwise>
		       </c:choose>
		       </table> 
		       </div>
		       <input type="hidden" name="delValue" id="delValue" value="" />
		       <input type="hidden" name="action" id="action" />
		       <c:choose>
				  <c:when test="${action eq 'new'}">
			            <input type="hidden" name="count" id="count" value="1" />
			            <input type="hidden" name="gridOrgCount" id="gridOrgCount" value="1" />
		          </c:when>
		          <c:otherwise>
			            <input type="hidden" name="count" id="count" value="${count}" />
			            <input type="hidden" name="gridOrgCount" id="gridOrgCount" value="${gridOrgCount}" />
		          </c:otherwise> 
		       </c:choose>	
		       
		       <input type="hidden" name="delValue" id="delValue" value="" />
		       <input type="hidden" name="message" id="message" value="${message}" />
		      
		      <!-- <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="99%"><img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp;&nbsp;</td> 		            				
					</tr>
			 </table>   -->      
			 <table  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="10px">
		           <td height="10px" align="center">&nbsp;</td>
			   </tr>
	        </table> 
     </DIV>
     </center>    
     
     <table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
	    <tr>
           <td height="50px" align="left">
		     <input  type="button" class="${btnSatusStyle}" value="${btnStatusVal}" onclick="javascript:processInspection('${btnStatusVal}');" ${btnStatus} />&nbsp;&nbsp;
		     <input type="button" class="btn btnNormal"  onclick="javascript:funToolingList();" value="List" /></td>
	    </tr>
	 </table>       
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
	        
</form>
</body>
</html>
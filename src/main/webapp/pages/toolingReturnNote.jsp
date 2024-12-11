<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tooling Production Return</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<script>
$(document).ready(function(){ 
	
/////////////////////////////Validation/////////////////////////////
    $('#returnNoteData input[type=text]').live("keypress", function(event){
    	
	    	var pattern = /[a-zA-Z]+/g;
	   		var findFocusId =  $(this).attr('id');	
	   		findFocusId = findFocusId.match(pattern);
	   		switch($.trim(findFocusId))
	   		{		   		   
	   			case "producedQty":
			   		
	   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
	   			break;
   				case "returnQty":
			   		
	   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
	   			break;
				case "goodQty":
			   		
	   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
	   			break;
				case "damagedQty":
		
					return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
				break;
	   		}
    });
	
	$("#issueId").autocomplete({
	    source: function (request, response) {
	        $.getJSON("autoReturnIssueId.jsf", {
	           // term: extractLast(request.term)
	        	issueId: $("#issueId").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	 $("#autoStatus").html("Data not Available");
                }
             });
	    },
	    search: function () {  // custom minLength
	       	
	    	$("#grnSatus").hide();
	    	//$("#issueId").val("");
	    	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	 $("#autoStatus").html("");
	    	$("#issueId").val(ui.item.id);
	    	funLoadInspectionDetails() ;
	    }
	 });  
	
	$('#rule').click(function(event) {
		 $("#overlay").show();
		 $("#dialogRule").show();	
	});
	  
	$('#ruleClose').click(function(event) {   
		 $("#dialogRule").hide();
		 $("#overlay").hide();
	});
	getSerialNumberDetails();
	
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

	var arrayValues = $("#cleaningSOP").split(',');
	$.each(arrayValues, function(i, val){
		   $("input[value='" + val + "']").prop('checked', true);
		});
	
 }

 function funLoadInspectionDetails()
 { 
	 $("#goodQty").val(0);
	 $("#damagedQty").val(0);
	 $("#frmToolReturns").attr("action","fetchAllIssue.jsf");                 
	 $("#frmToolReturns").submit();
 }

 function trim(stringTrim)
 {
 	return stringTrim.replace(/^\s+|\s+$/g,"");
 }
 
 function showimg()
 {
 	$("#ajaxLoading").show();  
 }
 
 function processReturn(val)
 {
	 var count = $("#hiddenCount").val();
	 var returnNote = $("#returnId").val();
	 var issueNo = $("#issueId").val();
	 var returnBy = $("#returnBy").val();
	 var cleanedBy = $("#cleanedBy").val();
	 var customerName = $("#customerName").val();
	 var branch = $("#branchName").val();
	 
	 var goodQty = $("#goodQty").val();
	 var damagedQty = $("#damagedQty").val();
	 var returnQty = $("#returnQty").val();
	 
	 if(returnNote == "")
     {        	
		    jAlert('Please Enter the Return Note', 'Return Note is Empty', function(){	
		    	$("#returnId").focus();						  			    	 
	        });	
		   return false;
     }
     else if(issueNo == "")
     {
    	 jAlert('Please Enter the Issue No', 'Issue No is Empty', function(){	
    	 	$("#issueId").focus();	
    	 });
		 return false;		
     }
     else if(returnBy == "")
     {
    	 jAlert('Please Enter the Returned by', 'Returned by is Empty', function(){
    		 $("#returnBy").focus();	
         });
		 return false;		
     }
     else if(cleanedBy == "")
     {
    	 jAlert('Please Enter the Cleaned by', 'Cleaned by is Empty', function(){
    		 $("#cleanedBy").focus();	
         });
		 return false;		
     }
     else if(customerName == "")
     {
    	 jAlert('Please Enter the Customer Name', 'Customer Name is Empty', function(){
	    	 $("#customerName").focus();		    
	     });
		 return false;		
     }
   	 else if(branch == "")
     {
       	  jAlert('Please Enter the Branch', 'Branch is Empty', function(){
	       	 $("#branchName").focus();
	        });
   		 return false;		
     }
	  else if(goodQty == "")
	    {
	      	  jAlert('Please Enter the Good Qty', 'Good Qty is Empty', function(){
		       	 $("#goodQty").focus();
		        });
	  		 return false;		
	    }
   	 else
     {
		
		 var isValid = true;
    	 for(i=1;i<=count;i++) {
    		 
    		 if($("#batchNumber"+i).val() == "")
    		 {
    			 jAlert('Please Enter the batch number', 'Batch number is Empty', function(){
    		       	 $("#batchNumber"+i).focus();
    		        });
    	   		 return false;		
    		 }
    	 	  else if($("#producedQty"+i).val() == "")
    		 {
    			 jAlert('Please Enter the Product qty', 'Product qty is Empty', function(){
    		       	 $("#producedQty"+i).focus();
    		        });
    	   		 return false;		
    		 }
    		 else if($("#returnQty"+i).val() == "")
    		 {
    			 jAlert('Please Enter the Return qty', 'Return qty is Empty', function(){
    		       	 $("#returnQty"+i).focus();
    		        });
    	   		 return false;		
    		 }
    		
             var issuedQty =$("#issuedQty1").val();
             var returnQty =$("#returnQty"+i).val();

             if(parseInt(returnQty) > parseInt(issuedQty))
             {
            	 isValid = false;
            	 alert("Return Quantity should be less than or equal to Issue Quantity");
            	 $("#returnQty"+i).focus();
            	 return false;
             }
             
             if(parseInt(returnQty) < parseInt(goodQty))
             {
            	 isValid = false;
            	 alert("Return Quantity should be greater than or equal to Good Quantity");
            	 $("#goodQty").focus();
            	 return false;
             }
   		 }
		 if(isValid)
		 {
			 $("#originalId").val(0);
			 /* var returnQty =$("#returnQty1").val();
			 damagedQty = parseInt(returnQty) - parseInt(goodQty);
			 $("#damagedQty").val(damagedQty);
			 if(parseInt(damagedQty) > 0)
			 {
				 var damagedSerialNumber = $("#damagedSerialNumber").val();
				 if(damagedSerialNumber == null || damagedSerialNumber == "")
				 {
					 $("#toolingStatus1").val("Damaged");
					 jAlert('Please enter Damaged serial number', 'Damaged Serial Number is empty!', function(){	
					    	$("#damagedSerialNumber").focus();						  			    	 
				        });	
					   return false;
				  }
			 } */
			 //$("#loadoverlay").fadeIn();  
			

			 if(($("#serilaFlag").val() == 1) && ($("#approvedQty1").val() == null))
			 {
				 jAlert('Please Selecte serial number for Accepted/Rejected', 'Serial Numbers are empty!', function(){	
					 getSerialNumber();		  			    	 
 		        });	
 			   return false;
			 }else
			 {
				 var returnQty =$("#returnQty1").val();
				 damagedQty = parseInt(returnQty) - parseInt(goodQty);
				 $("#damagedQty").val(damagedQty);
				 if(parseInt(damagedQty) > 0)
				 {
					 var damagedSerialNumber = $("#damagedSerialNumber").val();
					 if(damagedSerialNumber == null || damagedSerialNumber == "")
					 {
						// $("#toolingStatus1").val("Damaged");
						 jAlert('Please enter Damaged serial number', 'Damaged Serial Number is empty!', function(){	
						    	$("#damagedSerialNumber").focus();						  			    	 
					        });	
						   return false;
					  }
				 } 
			 }
			 $("#ajaxLoading").fadeIn();  
			 var sop = "";
		    	$('input[name="sop"]:checked').each(function() {
					sop = sop + this.value +",";
				});
		    	$("#cleaningSOP").val(sop);
			 if(val == "Save")
			 {
					$("#loadoverlay").fadeIn();  
				    $("#frmToolReturns").attr("action","addToolingReturnNote.jsf");   
					$("#frmToolReturns").submit(); 
			 }
			 else
			 {
				 $("#loadoverlay").fadeIn();  
				$("#frmToolReturns").attr("action","updateToolingReturnNote.jsf");
				$("#frmToolReturns").submit(); 
			 }
		     
		 } 
		 
     }
 }
 
 function funToolingList()
 {
	 $("#issueId").val("0");
	 $("#issueId").val("0");
	  $("#goodQty").val("0");
	 $("#damagedQty").val("0");
	 $("#returnQty").val("0");
	 $("#originalId").val(0);
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmToolReturns").attr("action","showToolingReturn.jsf");   	                
     $("#frmToolReturns").submit();
 }
 
 function funTypeissueId(evt,obj)
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
 function hideSerialNumberDialog()
 {
	 var goodQty = $('.approvedQty:checkbox:checked').length;
	 $("#goodQty").val(goodQty);
	 var returnQty = $("#returnQty1").val();
	 damagedQty = parseInt(returnQty) - parseInt(goodQty);
	 $("#damagedQty").val(damagedQty);
	 
	 var slvals = [];
	 $('input:checkbox[name=rejectQty]:checked').each(function() {
	 slvals.push($(this).val());
	 
	 })
		 
	$("#damagedSerialNumber").val(slvals); 
	 
	
	 
	 $("#serialNumberDialog").fadeOut(300); 
 }
 function funTypeReportNo(evt,obj)
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
	 $("#issueId").val(0);
	 $("#goodQty").val(0);
	 $("#damagedQty").val(0);
	 $("#frmToolReturns").attr("action","addIntialToolingReturnNote.jsf");   	                
     $("#frmToolReturns").submit();
 }
 
 function funSearchIssueNo()
 {
	 $("#productNameListDialog").fadeIn(300);  
	    // $("#srchMinProductName").focus();
	     $("#overlay").unbind("click");  
	     $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getToolingIssueNoteDetail.jsf",
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
 
 function getSerialNumber()
 {
	 var lotNumber = $("#toolingLotNumber").val();
	 
		 $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getReturnSerialNumbers.jsf?lotNumber="+lotNumber,
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
 
 function hideProductNameListDialog()
 {
    $("#productNameListDialog").fadeOut(300);

 } 
 
 function funIssueNo(issueDate, issueBy, issueId)
 {
	 $("#issueId").val(issueId);
	 hideProductNameListDialog();
 	funLoadInspectionDetails() ;
 }
 
 function loadIssueDetails(issueId, originalId)
 {
		$("#issueId").val(originalId);
		$("#originalId").val(issueId);
  	    funLoadInspectionDetails() ;
 }
 
 function changeAttr(value)
 {
	 $("#" + value).attr('checked', false);
 }
 
 
 function getSerialNumberDetails()
 {
	 var lotNumber = $("#toolingLotNumber").val();
	// var lotNumber = '';
	// alert(lotNumber);
 	 
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
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmToolReturns" method="post"  id="frmToolReturns" autocomplete="off">
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
           <td width="35%" class="heading" align="left">&nbsp;Tooling Production Return</td> 
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
				             Search Product Name&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<input  type="text" name="srchMinProductName" id="srchMinProductName" maxlength="150" value="" class="textmedium" />
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
				        <td width="18%" align="left" class="popuptoptitlelarge">Return Serial Number</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideSerialNumberDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
			 </table>
    	<div id="listSerialNumber" style="height:300px;overflow:auto;"></div>   
    	<table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
		    <tr>
	           <td height="50px" align="left">
			     <input type="button" class="btn btnNormal"  onclick="" value="Select All" />
			     <input type="button" class="btn btnNormal"  onclick="hideSerialNumberDialog();" value="Submit" />
			     </td>
		    </tr>
		</table>       
    </div>
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
						<td class="formlabelblack" align="left">Return Note&nbsp;<span class="formlabel">*</span></td>
						<td class="formlabelblack" align="left">Return Date&nbsp;<span class="formlabel">*</span></td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="returnId" id="returnId" class="textmin" value="${returnId}"  maxlength="7"  onkeypress="return funTypeReportNo(event,this);" autofocus autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="returnDate" type="text" name="returnDate" class="textmin" value="${returnDate}"  maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly/></td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Issue No&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<span id="grnSatus" class="labelAlert">Invalid</span></td>						
						<td width="50%" class="formlabelblack" align="left">&nbsp;Issue Date&nbsp;</td>												
					</tr>	
					<tr>
					    <td></td>
					    <td class="popuplabel" align="left">
					    	<c:choose>
					            <c:when test="${btnStatusVal eq 'Update'}">
				    				<input id="issueId" type="text" name="issueId" class="textmin" value="${issueId}" maxlength="5"  style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly  autoComplete="off"/>
				    			</c:when>
				    			<c:otherwise>
				    				<input type="text"  class="textmin" value="${originalId}" maxlength="5"  onkeypress="return funTypeissueId(event,this);" autoComplete="off"/>
				    				<input id="issueId" type="hidden" name="issueId" class="textmin" value="${originalId}"/>
				    			</c:otherwise>
				    		</c:choose>	&nbsp;<input type="button" value="" onclick="javascript:funSearchIssueNo();" class="btnselect"/>
				    	</td>
					    <td class="popuplabel" align="left"><input id="issueDate" type="text" name="issueDate" class="textmin" value="${issueDate}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					</tr>					     		    
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Returned by&nbsp;<span class="formlabel">*</span></td>						
						<td class="formlabelblack" align="left">&nbsp;Issued by&nbsp;</td>						
					</tr>
					<tr>
					    <td></td> 
					    <td class="popuplabel" align="left"><input id="returnBy" type="text" name="returnBy" class="textmin" value="${username}" autoComplete="off" maxlength="50"/></td>
					    <td class="popuplabel" align="left"><input id="issueBy" type="text" name="issueBy" class="textmin" value="${issueBy}" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;" readonly /></td>
					</tr>
					
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Cleaned by&nbsp;<span class="formlabel">*</span></td>						
						<td class="formlabelblack" align="left"></td>						
					</tr>
					<tr>
					    <td></td> 
					    <td class="popuplabel" align="left"><input id="cleanedBy" type="text" name="cleanedBy" class="textmin" value="${cleanedBy}" autoComplete="off" maxlength="50"/></td>
					    <td class="popuplabel" align="left"></td>
					</tr>
					
					  <c:if test="${fn:length(lstSOP) gt 0}">
				           	
				          	<tr>
				          		<td></td>
					         	 <td class="formlabelblack" align="left">Cleaning Done</td>
					           
					             <td class="formlabelblack" align="left" >
					             	<c:forEach items="${lstSOP}" var="lstSOP" varStatus="row">
					             		<input type="checkbox" name="sop" id="sop" value="${lstSOP}">${lstSOP}&nbsp;&nbsp;&nbsp;
					             	 </c:forEach> 
					             </td>
					             
				        	 </tr>	
			       		 </c:if>
					<tr>
					    <td class="popuplabel" colspan="3" align="left"><span id="autoStatus" class="popuptopalert"></span></td>
					</tr>
					<!-- <tr>
						<td></td>							
						<td class="popuplabel"  align="left"><input id="clear" type="button" name="clear" value="Clear All" class="btn btnSMNormal" onclick="javascript:funClear();"/></td>
						<td class="popuplabel"  align="left"></td>									        	        			       	      
					</tr>  -->					
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
         
             <table cellspacing="0" width="99%" cellpadding="5" border="0" align="center" height="30px">		      
					<tr>  			 		           
						<td align="right" width="100%" class="labelanchor">
							<!--<img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;"></img>&nbsp;
							 <input type="button" id="btnAddRow" onclick="javascript:funAddNewRow();" value="Add New Row" class="btn btnSMNormal"/> -->&nbsp;
					    </td>
					</tr>
			  </table>    
				<div style="max-width:98%;min-width:1000px;height:auto;min-height:175px;overflow:auto;">      
				 <%
					int index = 1;
				 %>
				<c:choose>
					<c:when test="${fn:length(lstIssueNote) gt 0}">
					        <table class="tablesorter" style="width:98%" id="returnNoteData" align="center">
					         <tr>
					              <th align="center" width="10%">S&nbsp;No.</th>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Machine&nbsp;No/Name</th>
					              <th align="center" width="10%">Type&nbsp;Of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
								  <th align="center" width="10%">Batch&nbsp;Qty</th>
								  <!-- <th align="center" width="10%">Batch&nbsp;Number</th> -->
								  <th align="center" width="10%">Tablet&nbsp;Prod&nbsp;Qty</th>
					              
					              <th align="center" width="10%">Tooling&nbsp;Return&nbsp;Qty</th>
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Issued&nbsp;Qty</th>             
					              <th align="center" width="10%">UOM</th>  
					              <th align="center" width="10%">Good Qty</th>
					              <th align="center" width="10%">Damaged Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Status</th>
					              <th align="center" width="10%">Damaged&nbsp;SNo</th>    
					        <!--       <th align="center" width="10%">Rejected&nbsp;SNo</th>         -->
					         </tr>
					         <c:choose>
					         <c:when test="${btnStatusVal eq 'Update'}">
					         		<c:forEach items="${lstIssueNote}" var="lstIssueNote" varStatus="row">  
						                 <tr id="rowid-<%=index%>">
									          <td align="center"><%=index%><input type="hidden" name="hiddenCount" id="hiddenCount" value="<%=index%>"/></td>
									          <td align="center">${lstIssueNote.productName1}</td>
									           <td align="center">${lstIssueNote.drawingNo1}</td>
									          <td align="center">${lstIssueNote.machineName1}</td>		
									          <td align="center">${lstIssueNote.typeOfTooling1}</td>          
											  <td align="center">${lstIssueNote.toolingLotNumber1}<input type="hidden" name="toolingLotNumber1" id="toolingLotNumber" class="txtleastavg"  value="${lstIssueNote.toolingLotNumber1}"  autocomplete="off"/></td>
											  <td align="center">${lstIssueNote.batchQty1}<input type="hidden" name="batchNumber" id="batchNumber<%=index%>" class="txtleastavg"  value="${lstIssueNote.batchNumber1}" maxlength="45" autocomplete="off"/></td>
											  <%-- <td align="center">${lstIssueNote.batchNumber1}</td>			 --%>		  
											  <td align="center">${lstIssueNote.producedQty1}<input type="hidden" name="producedQty" id="producedQty<%=index%>" class="txtleastavg"  value="${lstIssueNote.producedQty1}" maxlength="7" autocomplete="off"/></td>
											  
											  <td align="center">${lstIssueNote.requestQty1}<input type="hidden" name="returnQty" id="returnQty<%=index%>" class="txtleastavg"  value="${lstIssueNote.requestQty1}" maxlength="7" autocomplete="off"/></td>
											  <td align="center">${lstIssueNote.lastInspectionDate1}</td>
											  <td align="center">${lstIssueNote.nextDueQty1}</td>
											  <td align="center">${lstIssueNote.issuedQty1}<input type="hidden" id="issuedQty1" value="${lstIssueNote.issuedQty1}"/></td>
											  <td align="center">${lstIssueNote.UOM1}</td>
											  <td align="center"><input type="text" name="goodQty" id="goodQty"  value="${lstIssueNote.goodQty}" size="8"/></td>
											  <td align="center"><input type="text" name="damagedQty" id="damagedQty" value="${lstIssueNote.damagedQty}"  size="8"/></td>
											  <td align="center"><select name="toolingStatus" id="toolingStatus<%=index%>" class="txtleastavg">
												 <c:if test="${lstPeriodicDetail.toolingStatus1 ne NULL && lstPeriodicDetail.toolingStatus1 ne ''}">
											  		<option value="${lstIssueNote.toolingStatus1}">${lstIssueNote.toolingStatus1}</option>
											 	 </c:if>
											  	<option value="good">Good</option>
  												<option value="Damaged">Damaged</option>
  												<option value="service">Service</option>
											  </select>
											  <input type="hidden" name="returnDetailId" value="${lstIssueNote.returnDetailId1}"/>
											  <input type="hidden" name="toolingIssueDetailId" value="${issueDetailId}"/>
											  </td>
											   <td align="center"><input type="text" name="damagedSerialNumber" id="damagedSerialNumber" value="${lstIssueNote.damagedSerialNumber}"/></td>
											<%--   <td align="center"><input type="text" name="rejectedSerialNumber" id="rejectedSerialNumber" value="${lstIssueNote.rejectedSerialNumber}"/></td> --%>
							           </tr> 
							           <%
							           index++;
							           %>
							           </c:forEach>
					         </c:when>
					         <c:otherwise>
					         		<c:forEach items="${lstIssueNote}" var="lstIssueNote" varStatus="row">  
						                 <tr id="rowid-<%=index%>">
									          <td align="center"><%=index%><input type="hidden" name="hiddenCount" id="hiddenCount" value="<%=index%>"/></td>
									          
									          <td align="center">${lstIssueNote.productName1}</td>
									          <td align="center">${lstIssueNote.drawingNo1}</td>
									          <td align="center">${lstIssueNote.machineName1}</td>		
									          <td align="center">${lstIssueNote.typeOfTooling1}</td>          
											  <td align="center">${lstIssueNote.toolingLotNumber1}<input type="hidden" name="toolingLotNumber1" id="toolingLotNumber" class="txtleastavg"  value="${lstIssueNote.toolingLotNumber1}"  autocomplete="off"/></td>
											  <td align="center">${lstIssueNote.batchQty1}<input type="hidden" name="batchNumber" id="batchNumber<%=index%>" class="txtleastavg"  value="${lstIssueNote.batchNumber}" maxlength="7" autocomplete="off"/></td>		
											<%--   <td align="center">${lstIssueNote.batchNumber}</td>			 --%>  
											  <td align="center">${lstIssueNote.totalBatchQty}<input type="hidden" name="producedQty" id="producedQty<%=index%>" class="txtleastavg"  value="${lstIssueNote.totalBatchQty}" maxlength="7" autocomplete="off"/></td>				  
											  
											  <td align="center">${lstIssueNote.requestQty1}<input type="hidden" name="returnQty" id="returnQty<%=index%>" class="txtleastavg"  value="${lstIssueNote.issuedQty1}" maxlength="7" autocomplete="off"/></td>
											  <td align="center">${lstIssueNote.lastInspectionDate1}</td>
											  <td align="center">${lstIssueNote.nextDueQty1}</td>
											  <td align="center">${lstIssueNote.issuedQty1}<input type="hidden" id="issuedQty1" value="${lstIssueNote.issuedQty1}"/></td>
											  <td align="center">${lstIssueNote.UOM1}</td>
											  <td align="center"><input type="text" name="goodQty" id="goodQty" size="8"/></td>
											  <td align="center"><input type="text" name="damagedQty" id="damagedQty" size="8"/></td>
											  <td align="center"><select name="toolingStatus" id="toolingStatus<%=index%>" class="txtleastavg">
											  <c:if test="${lstPeriodicDetail.toolingStatus1 ne NULL && lstPeriodicDetail.toolingStatus1 ne ''}">
											  	<option value="${lstIssueNote.toolingStatus1}">${lstIssueNote.toolingStatus1}</option>
											  </c:if>
											  	<option value="good">Good</option>
  												<option value="Damaged">Damaged</option>
  												<option value="service">Service</option>
											  </select>
											  <input type="hidden" name="toolingIssueDetailId" value="${lstIssueNote.issueDetailId1}"/>
											  </td>
											  <td align="center"><input type="text" name="damagedSerialNumber" id="damagedSerialNumber" /></td>
											<!--   <td align="center"><input type="text" name="rejectedSerialNumber" id="rejectedSerialNumber" /></td> -->
											 
							           </tr> 
							           <%
							           index++;
							           %>
							           </c:forEach>
					         </c:otherwise>
					         </c:choose>
					       </table> 	
				    </c:when>
				    <c:otherwise>
				             <table class="tablesorter" style="width:98%" align="center" id="cylinderFieldData">
					         <tr>
					              <th align="center" width="10%">S&nbsp;No.</th>
					             
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Machine&nbsp;No/Name</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
								  <th align="center" width="10%">Batch&nbsp;Qty</th>
								  <!-- <th align="center" width="10%">Batch&nbsp;Number</th> -->
								  <th align="center" width="10%">Tooling&nbsp;Prod&nbsp;Qty</th>
					              
					              <th align="center" width="10%">Tooling&nbsp;Return&nbsp;Qty</th>
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Issued&nbsp;Qty</th>             
					              <th align="center" width="10%">UOM</th>  
					              <th align="center" width="10%">Good Qty</th>
					              <th align="center" width="10%">Damaged Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Status</th>  
					              <th align="center" width="10%">Damaged&nbsp;SNo</th>    
					           <!--    <th align="center" width="10%">Rejected&nbsp;SNo</th>   -->
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
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
								  <td align="center">&nbsp;</td>
								 <!--  <td align="center">&nbsp;</td> -->
								<!--   <td align="center">&nbsp;</td> -->
				           </tr> 		        		      
					       </table>        
				    </c:otherwise>
				 </c:choose>
		       </div> 
		       <table cellspacing="0" width="98%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="99%"><!-- <img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp; -->&nbsp;</td> 		            				
					</tr>
			 </table>       
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
		     <input  type="button" class="${btnSatusStyle}" value="${btnStatusVal}" onclick="javascript:processReturn('${btnStatusVal}');" ${btnStatus} />&nbsp;&nbsp;
		     <input type="button" class="btn btnNormal"  onclick="javascript:funToolingList();" value="List" />
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
	 <input type="hidden" name="cleaningSOP" id="cleaningSOP" value="${cleaningSOP}"/>
	        
</form>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TIIM</title>
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
	
		 var path =  $("#uploadedPath").val();
		 $("#uploadedUrl").html("<a href='"+path+"'>"+path.substring(path.lastIndexOf("/")+1)+"</a>");
		 
	 $('#receivingInspection input[type=text]').live("keypress", function(event){
	    	
	    	var pattern = /[a-zA-Z]+/g;
	   		var findFocusId =  $(this).attr('id');	
	   		findFocusId = findFocusId.match(pattern);
	   		switch($.trim(findFocusId))
	   		{		   		   
				case "acceptedQty":
			   		
	   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
	   			break;
				case "rejectedQty":
	
					return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
				break;
				
	   		}
   });
	 
	$("#requestId").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoReciveRequest.jsf", {
	           // term: extractLast(request.term)
	        	requestId: $("#requestId").val()
	        }, response).success(function(data) {
                if(data.length == 0)
                {
                	 $("#autoStatus").html("Data not Available");
                }
             });
	    },
	    search: function () {  // custom minLength
	       	
	    	$("#grnSatus").hide();
	    	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	 $("#autoStatus").html("");
	    	funLoadInspectionDetails() ;
	    }
	 });  
	
	$("#srchMinProductName").autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext. request. contextPath}/autoReciveRequestDrawingNo.jsf", {
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
	    	
	    },
	    focus: function () {
	        // prevent value inserted on focus
	        return false;
	    },
	    autoFocus: true,
	    minLength: 1,
	    select: function (event, ui) {
	    	// $("#autoStatus").html("");
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
	 $("#toolingInspectionId").val("0");
	 $("#acceptedQty").val("0");
	 $("#rejectedQty").val("0");
	 $("#frmtoolDestructionNoteList").attr("action","fetchToolingReceiveInspection.jsf");                 
	 $("#frmtoolDestructionNoteList").submit();
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
	 var destructionNo = $("#destructionNo").val();
	 var destructionDate = $("#destructionDate").val();
	 if(destructionNo == "")
     {        	
		    jAlert('Please Enter the Report No', 'Inspection Report No is Empty', function(){	
		    	$("#inspectionReportNo").focus();						  			    	 
	        });	
		   return false;
     }
	 else if(destructionDate == "")
	 {
		 jAlert('Please Enter the Accepted Qty', 'Accepted Qty is Empty', function(){	
		    	$("#acceptedQty").focus();						  			    	 
	        });	
		   return false;
	 }
	  else
    	 {
    		 $("#loadoverlay").fadeIn();  
    		 $("#ajaxLoading").fadeIn();  
    		 
    		 if(val == "Save")
    		 {
    		 	$("#frmtoolDestructionNoteList").attr("action","addDestructionNote.jsf");   
    		 }
    		 else
    		 {
    			$("#frmtoolDestructionNoteList").attr("action","updateToolingInspection.jsf");
    		 }
    	     $("#frmtoolDestructionNoteList").submit();
    		 
    	}
		 
 }
 
 function funToolingList()
 {
	 $("#destructionNo").val("0");
	 $("#loadoverlay").fadeIn();  
	 $("#ajaxLoading").fadeIn();   	 			                    	                  
	 $("#frmtoolDestructionNoteList").attr("action","toolDestructionNoteList.jsf");   	                
     $("#frmtoolDestructionNoteList").submit();
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
	 $("#toolingInspectionId").val("0");
	 $("#inspectionReportNo").val("0");
	 $("#requestId").val("0");
	 $("#frmtoolDestructionNoteList").attr("action","showAddToolingReceiveInspection.jsf");   	                
     $("#frmtoolDestructionNoteList").submit();
 }
 
 function funSearchMinProductName(drawingNumber)
 {
	 $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getReceivingProductDetail.jsf?drawingNumber="+drawingNumber,
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
 
 
 function funSearchRequestId()
 {
     $("#productNameListDialog").fadeIn(300);  
    // $("#srchMinProductName").focus();
     $("#overlay").unbind("click");  
     $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/getReceivingProductDetail.jsf",
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
 
 function hideProductNameListDialog()
 {
    $("#productNameListDialog").fadeOut(300);

 } 
 
 function funShowRequestNo(toolingRequestId,inspectionReportDate, originalId)
 {
	 $("#requestId").val(originalId);
	 $("#originalId").val(toolingRequestId);
	 $("#requestDate").val(inspectionReportDate);
	 hideProductNameListDialog();
	 funLoadInspectionDetails() ;
 }
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmtoolDestructionNoteList" method="post"  id="frmtoolDestructionNoteList" autocomplete="off"  enctype="multipart/form-data">
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
           <td width="35%" class="heading" align="left">&nbsp;Tool Destruction Note</td> 
            
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
						<td class="formlabelblack" align="left">Destruction Note No&nbsp;<span class="formlabel">*</span></td>
						<td class="popuplabel" align="left"><input  type="text" name="destructionNo" id="destructionNo" class="textmin" value="${destructionNo}" maxlength="7"  autofocus /></td>
					    <td class="formlabelblack" align="left">Date&nbsp;<span class="formlabel">*</span></td>
					    <td class="popuplabel" align="left"><input  type="text" name="destructionDate" id="destructionDate" class="textmin" value="${destructionDate}" maxlength="7"  autofocus /></td>
					    
					</tr>
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td class="formlabelblack" align="left">Upload Image&nbsp;<span class="formlabel">*</span></td>
 						<td class="formlabelblack" align="left"><input id="profileFile" name="profileFile" type="file" class="file-loading"/>
			             <span id="uploadedUrl"></span>	
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
		     <input  type="button" class="btn btnNormal" value="Save" onclick="javascript:processInspection('${btnStatusVal}');" ${btnStatus} />&nbsp;&nbsp;
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
	 
<input type="hidden" name="uploadedPath" id="uploadedPath" value ="${uploadedPath}"/>
<input type="hidden" name="action" id="action" value="${action}"/>	        
</form>
</body>
</html>

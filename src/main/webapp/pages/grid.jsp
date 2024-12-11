<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.Vector,java.util.Enumeration"%>

<%

 Vector vectBreakSheet = new Vector();
 
 //////Jsp Var/////////
 String action;
 String msg = "";
 /////////////////////
 
 action =  request.getParameter("action"); 
 
 /************Save Break Sheets***********/
     if(action != null)
     {
	     if("saveBreakSheet".equals(action))
	     {
	    	 //msg = sheet.insertBreakSheet(count, selecteDB);
	     }
     }
 /****************************************/

 /************load breaksheet*************/
	// vectBreakSheet = new Vector();
     System.out.println("vectBreakSheet >> "+vectBreakSheet.size());
 /****************************************/
  
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sample</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/SampleGrid.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<%@ include file="toastmessage.jsp"%>
<style>
</style>
<script>
$(document).ready(function(){ 
	
});

 function Init()
 {	
	$("#loadoverlay").hide();
	$("#ajaxLoading").hide();

	var action = '<%=action%>';
	if(trim(action) != "")
	{
		var msg = '<%=msg%>';
		if(trim(msg) != "")
		{
		  funShowSuccessMsg(msg);
		}
	}	
	
	 $("#serialNo1").focus();
 }

 function funSaveBreakSheet()
 { 

	 $("#labDashboard").attr("action","labcylinder.jsp");                 
	// $("#labDashboard").submit();
 }

 function funShowAlertMsg(info)
 {
 	var msgVisible = $("div .toast-item-wrapper").is(":visible");
 	if(!msgVisible)
 	{
 		showAlert(info,""); 
 	}    
 }
 
 function funShowSuccessMsg(info)
 {
 	var msgVisible = $("div .toast-item-wrapper").is(":visible");
 	if(!msgVisible)
 	{
 		showStickySuccessToast(info,"");
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
 
 
</script>

</head>
<body class="body" onload="Init();">
<form name="frmGrid" method="post"  id="grid" autocomplete="off">
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
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Double click on particular row to edit the Cylinder values</td>
				     </tr>		     					     		     			
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Mix ID, Set ID, Set, #</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Read only columns</td>
				     </tr>
				      <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Hold, Date Made, Age</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Read only columns</td>
				     </tr>	
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Dia1 / Dia2</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Enter Dia1 as (0.00)</td>
				     </tr>
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Length1 / Length2</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Enter Length1 as (0.00)</td>
				     </tr>				    	
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Weight</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Enter Weight like (00.00)</td>
				     </tr>				     				     
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Break Type</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press Enter to Open & Esc to close the Break Type Popup</td>
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
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press Home key to move to Mix ID</td>
				     </tr>
				     <tr>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">End</td>
				     	 <td align="left" style="font-family: Verdana;font-size:10pt;color:#000000;">Press End key to move to Notes</td>
				     </tr>			     			     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div>
	
   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="25px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;GRN</td> 
           <td width="45%" class="submenutitlesmall" align="center">
               <table cellspacing="1" cellpadding="2" width="57%" height="20px" align="left" style="display:none" id="showmsg">	
			     	<tr>  
				        <td class="confirmationmessage" align="center">&nbsp;&nbsp;&nbsp;<span id="msgtext" style="align:center;"><%=msg%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="msganchor" id="msgclose">X</a></td>
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
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;Data Entry</td>
						<td width="10%" class="formlabelblack" align="left">GRN No</td>						
						<td width="50%" class="formlabelblack" align="left">&nbsp;GRN Date&nbsp;</td>												
					</tr>	
					<tr>
					    <td></td>
					    <td class="popuplabel" align="left"><input id="mixId" type="text" name="mixId" class="txtreal" value="" style="width:200px;" maxlength="25" autoComplete="off"/></td>
					    <td class="popuplabel" align="left"><input id="mixId" type="text" name="mixId" class="txtreal" value="" style="width:200px;" maxlength="25" autoComplete="off"/></td>
					</tr>					     		    
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Supplier Code&nbsp;&nbsp;&nbsp;<span id="mixIdSatus" class="labelAlert">Invalid</span></td>						
						<td class="formlabelblack" align="left">&nbsp;Supplier Name&nbsp;</td>						
					</tr>
					<tr>
					    <td></td> 
					    <td class="popuplabel" align="left"><input id="mixId" type="text" name="mixId" class="txtreal" value="" style="width:200px;" maxlength="25" autoComplete="off"/></td>
					    <td class="popuplabel" align="left"><input id="mixId" type="text" name="mixId" class="txtreal" value="" style="width:200px;" maxlength="25" autoComplete="off"/></td>
					</tr>
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Customer Name</td>
						<td class="formlabelblack" align="left">Branch</td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="setNo" id="setNo" class="txtreal" value="" style="width:200px;"  maxlength="25" autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="mixId" type="text" name="mixId" class="txtreal" value="" style="width:200px;" maxlength="25" autoComplete="off"/></td>
					</tr>
					<tr>
					    <td class="popuplabel" colspan="3" align="left"></td>
					</tr>
					<tr>
						<td></td>							
						<td class="popuplabel"  align="left"><input id="clear" type="button" name="clear" value="Clear All" class="btn btnSMNormal" onclick="javascript:funClear();"/></td>
						<td class="popuplabel"  align="left"></td>									        	        			       	      
					</tr> 					
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
         
	         <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="15px">
		           <td align="center"></td>
			   </tr>
		     </table>
             <table cellspacing="0" width="99%" cellpadding="5" border="0" align="center" height="30px">		      
					<tr>  			 		           
						<td align="right" width="100%" class="labelanchor">
							<img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;"></img>&nbsp;
							<input type="button" id="btnAddRow" onclick="javascript:funAddNewRow();" value="Add New Row" class="btn btnSMNormal"/>&nbsp;
					    </td>
					</tr>
			  </table>    
				 <div style="width:1300px;height:auto;min-height:175px;overflow:auto;">      
				<%
					int index = 0;
				%>      
		        <table class="tablesorter" style="width:98%" align="center" id="cylinderFieldData">
		         <tr>
		              <th align="center" width="10%">S&nbsp;No.</th>
		              <th align="center" width="10%">Tool&nbsp;No.</th>
		              <th align="center" width="10%">Tool&nbsp;Name</th>
		              <th align="center" width="10%">Customer&nbsp;PO&nbsp;No.</th>
		              <th align="center" width="10%">Customer&nbsp;PO&nbsp;Date</th>
					  <th align="center" width="10%">Drawing&nbsp;No.</th>
					  <th align="center" width="10%">Inspection&nbsp;Report&nbsp;No./Date</th>
		              <th align="center" width="10%">DQ&nbsp;details</th>
		              <th align="center" width="10%">MOC&nbsp;Certificate</th>
		              <th align="center" width="10%">Supplier&nbsp;Invoice&nbsp;No.</th>
		              <th align="center" width="10%">Supplier&nbsp;Invoice&nbsp;Date</th>             
		              <th align="center" width="10%">Received&nbsp;Qty</th>
		              <th align="center" width="10%">UOM</th>
		              <th align="center" width="10%">Validation&nbsp;Status</th>             
		              <th align="center" width="10%">Tool&nbsp;Serial&nbsp;No.</th>	  
		              <th align="center" width="10%">Actions</th>         
		         </tr>
		         <%
					if(vectBreakSheet.size() == 0)
					{
		         %>       
				       <tr id="rowid-1">
					      <td align="center"><input type="text" name="fldSerialNo" id="serialNo1" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSerialNo1" ></span></td>
				          <td align="center"><input type="text" name="fldToolNo" id="toolNo1" class="txtleastavg"  value="" onkeyup="javascript:funToolNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolNo1" ></span></td>
				          <td align="center"><input type="text" name="fldToolName" id="toolName1" class="txtleastavg"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolName1" ></span></td>
				          <td align="center"><input type="text" name="fldCustomerPoNo" id="customerPoNo1" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoNo1" ></span></td>		          
						  <td align="center"><input type="text" name="fldCustomerPoDate" id="customerPoDate1" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoDate1" ></span></td>
						  <td align="center"><input type="text" name="fldDrawingNo" id="drawingNo1" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo1" ></span></td>				  
						  <td align="center"><input type="text" name="fldInspReportNo" id="inspReportNo1" class="textverysmall"  value="" onkeyup="javascript:funInspReportNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtInspReportNo1"></span></td>				  
						  <td align="center"><input type="text" name="fldDqDetails" id="dqDetails1" class="textsmall"  value="" onkeyup="javascript:funDqDetails(event,this);" autocomplete="off"/><span style="display:none;" id="txtDqDetails1" ></span></td>
						  <td align="center"><input type="text" name="fldMOC" id="moc1" class="textverysmall"  value="" onkeyup="javascript:funMOC(event,this);" autocomplete="off"/><span style="display:none;" id="txtMOC1" ></span></td>
						  <td align="center"><input type="text" name="fldSupplierInvoiceNo" id="supplierInvoiceNo1" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceNo1" ></span></td>
						  <td align="center"><input type="text" name="fldSupplierInvoiceDate" id="supplierInvoiceDate1" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate1" ></span></td>				  
						  <td align="center"><input type="text" name="fldReceivedQty" id="receivedQty1" class="txtleastavg"  value="" onkeyup="javascript:funReceivedQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate1" ></span></td>
						  <td align="center"><input type="text" name="fldUOM" id="uom1" class="txtleast"  value="" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUOM1" ></span></td>
						  <td align="center"><input type="text" name="fldValidationStatus" id="validationStatus1" class="txtleast"  value="" onkeyup="javascript:funValidationStatus(event,this);" autocomplete="off"/><span style="display:none;" id="txtValidationStatus1" ></span></td>				  				  
				          <td align="center"><input type="text" name="fldToolSerialNo" id="toolSerialNo1" class="txtleastmin"  value="" onkeyup="javascript:funToolSerialNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolSerialNo1" ></span></td>				  	
				          <td align="center"><a title="Click to Add Row in Between" style="visibility:hidden;"  href="#"><img border="0" name="midRow" id="newMidRow1" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('1');"><img border="0" src="./images/deleteicon.gif"/></a></td>		        
					   </tr>       
			       <%
					}
					else
					{
			       %>      <%
			                 Enumeration eBS = vectBreakSheet.elements();
				             while(eBS.hasMoreElements())
				             {  
					           index++;
					       %>                   	
			               <tr id="rowid-<%=index%>" >
					          <td align="center"><input type="text" name="fldSerialNo" id="serialNo<%=index%>" class="txtleast"  value='${i.ticketNo}' onkeyup="javascript:funSerialNo(event,this);" style="display:none;" maxlength="50" autocomplete="off"/><span id="txtSerialNo<%=index%>" >${i.ticketNo}</span></td>
					          <td align="center"><input type="text" name="fldToolNo" id="toolNo<%=index%>" class="txtleastavg"  value='${i.timeBatched}' onkeyup="javascript:funToolNo(event,this);" style="display:none;" autocomplete="off"/><span id="txtToolNo<%=index%>" >${i.timeBatched}</span></td>
					          <td align="center"><input type="text" name="fldToolName" id="toolName<%=index%>" class="txtleastavg"  value='${i.timePlaced}' onkeyup="javascript:funToolName(event,this);" style="display:none;" autocomplete="off"/><span id="txtToolName<%=index%>" >${i.timePlaced}</span></td>
					          <td align="center"><input type="text" name="fldCustomerPoNo" id="customerPoNo<%=index%>" class="textverysmall"  value='${i.yard}' onkeyup="javascript:funCustomerPoNo(event,this);" style="display:none;" autocomplete="off"/><span id="txtCustomerPoNo<%=index%>" >${i.yard}</span></td>			    			          
					          <td align="center"><input type="text" name="fldCustomerPoDate" id="customerPoDate<%=index%>" class="textverysmall"  value="${i.air1}" onkeyup="javascript:funCustomerPoDate(event,this);" style="display:none;" autocomplete="off"/><span id="txtCustomerPoDate<%=index%>" >${i.air1}</span></td>
						      <td align="center"><input type="text" name="fldDrawingNo" id="drawingNo<%=index%>" class="textverysmall"  value="${i.air2}" onkeyup="javascript:funDrawingNo(event,this);" style="display:none;" autocomplete="off"/><span id="txtDrawingNo<%=index%>" >${i.air2}</span></td>				      
						      <td align="center"><input type="text" name="fldInspReportNo" id="inspReportNo<%=index%>" class="textverysmall"  value="${i.air3}" onkeyup="javascript:funInspReportNo(event,this);" style="display:none;" autocomplete="off"/><span id="txtInspReportNo<%=index%>">${i.air3}</span></td>				     
						      <td align="center"><input type="text" name="fldDqDetails" id="dqDetails<%=index%>" class="textsmall"  value="${i.slump1}" onkeyup="javascript:funDqDetails(event,this);" style="display:none;" autocomplete="off"/><span id="txtDqDetails<%=index%>" >${i.slump1}</span></td>
						      <td align="center"><input type="text" name="fldMOC" id="moc<%=index%>" class="textverysmall"  value="${i.slump2}" onkeyup="javascript:funMOC(event,this);" style="display:none;" autocomplete="off"/><span id="txtMOC<%=index%>" >${i.slump2}</span></td>
						      <td align="center"><input type="text" name="fldSupplierInvoiceNo" id="supplierInvoiceNo<%=index%>" class="textverysmall"  value="${i.slump3}" onkeyup="javascript:funSupplierInvoiceNo(event,this);" style="display:none;" autocomplete="off"/><span id="txtSupplierInvoiceNo<%=index%>" >${i.slump3}</span></td>
						      <td align="center"><input type="text" name="fldSupplierInvoiceDate" id="supplierInvoiceDate<%=index%>" class="textverysmall"  value="${i.slump4}" onkeyup="javascript:funSupplierInvoiceDate(event,this);" style="display:none;" autocomplete="off"/><span id="txtSupplierInvoiceDate<%=index%>" >${i.slump4}</span></td>				      
						      <td align="center"><input type="text" name="fldReceivedQty" id="receivedQty<%=index%>" class="txtleastavg"  value="${i.density}" onkeyup="javascript:funReceivedQty(event,this);" style="display:none;" autocomplete="off"/><span id="txtSupplierInvoiceDate<%=index%>" >${i.density}</span></td>
						      <td align="center"><input type="text" name="fldUOM" id="uom<%=index%>" class="txtleast"  value="${i.batchWt}" onkeyup="javascript:funUOM(event,this);" style="display:none;" autocomplete="off"/><span id="txtUOM<%=index%>" >${i.batchWt}</span></td>				      
				              <td align="center"><input type="text" name="fldValidationStatus" id="validationStatus<%=index%>" class="txtleast"  value="${i.yield}" onkeyup="javascript:funValidationStatus(event,this);" style="display:none;" autocomplete="off"/><span id="txtValidationStatus<%=index%>" >${i.yield}</span></td>
				              <td align="center"><input type="text" name="fldToolSerialNo" id="toolSerialNo<%=index%>" class="txtleastmin"  value="${i.temperature}" onkeyup="javascript:funToolSerialNo(event,this);" style="display:none;" autocomplete="off"/><span id="txtToolSerialNo<%=index%>" >${i.temperature}</span></td>				      			          			          
					          <%
					              if(index == 1)
					              {
					          %> 
					          <td align="center"><a title="Click to Add Row in Between"  style="visibility:hidden;" href="#"><img border="0" name="midRow" id="newMidRow1" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('<%=index%>');"><img border="0" src="./images/deleteicon.gif"/></a></td>
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
		              <%} %>                 		                     
		      <%} %>
		       </table> 
		       <input type="hidden" name="delValue" id="delValue" value="" />
		       <input type="hidden" name="action" id="action" />
		         <%
					if(vectBreakSheet.size() == 0)
					{
		         %>
		            <input type="hidden" name="count" id="count" value="1" />
		            <input type="hidden" name="gridOrgCount" id="gridOrgCount" value="1" />
		         <%
					}
					else
					{
			     %> 
		            <input type="hidden" name="count" id="count" value="<%=vectBreakSheet.size()%>" />
		            <input type="hidden" name="gridOrgCount" id="gridOrgCount" value="<%=vectBreakSheet.size()%>" />
		        <%  } %>            
		        
		       </div> 
		       <table cellspacing="0" width="99%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="100%%"><img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/up-arrow.png">&nbsp;-&nbsp;Insert New Row Above&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon.gif">&nbsp;-&nbsp;Delete&nbsp;</td> 		            				
					</tr>
			 </table>       
			 <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="50px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table> 
                 </DIV>
     </center>          
     <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="50px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table> 
</form>
</body>

</html>
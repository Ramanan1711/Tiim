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
<title>Tooling Production Request Note</title>
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

 function funClear()
 {
	 
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
	  	<tr height="25px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Tooling Production Request Note</td> 
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
						<td class="formlabelblack" align="left">Request No</td>
						<td class="formlabelblack" align="left">Request Date</td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="popuplabel" align="left"><input  type="text" name="requestNo" id="reportNo" class="txtreal" value="" style="width:200px;"  maxlength="25" autoComplete="off"/></td>
						<td class="popuplabel" align="left"><input id="requestDate" type="text" name="reportDate" class="txtreal" value="" style="width:200px;" maxlength="25" autoComplete="off"/></td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Requested by</td>						
						<td></td>											
					</tr>	
					<tr>
					    <td></td>
					    <td  class="popuplabel" align="left"><input id="requestedBy" type="text" name="grnNo" class="txtreal" value="" style="width:200px;" maxlength="25" autoComplete="off"/></td>
					    <td></td>
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
             <!-- <table cellspacing="0" width="99%" cellpadding="5" border="0" align="center" height="30px">		      
					<tr>  			 		           
						<td align="right" width="100%" class="labelanchor">
							<img src="./images/rules.png" border="0" title="Click to view Quick Reference" id="rule" style="vertical-align: middle;cursor:pointer;"></img>&nbsp;
							<input type="button" id="btnAddRow" onclick="javascript:funAddNewRow();" value="Add New Row" class="btn btnSMNormal"/>&nbsp;
					    </td>
					</tr>
			  </table>     -->
				 <div style="width:1300px;height:auto;min-height:175px;overflow:auto;">      
				<%
					int index = 0;
				%>      
		        <table class="tablesorter" style="width:98%" align="center" id="cylinderFieldData">
		         <tr>
		              <th align="center" width="10%">S&nbsp;No.</th>
		              <th align="center" width="10%">Toolint&nbsp;No.</th>
		              <th align="center" width="10%">Product&nbsp;Name</th>
		              <th align="center" width="10%">Machine&nbsp;No/Name</th>
		              <th align="center" width="10%">Drawing&nbsp;No.</th>
					  <th align="center" width="10%">Batch&nbsp;Qty&nbsp;</th>
		              <th align="center" width="10%">Tooling&nbsp;Request&nbsp;Qty</th>
		              <th align="center" width="10%">UOM</th>
		              <th align="center" width="10%">In&nbsp;Stock</th>
		              <th align="center" width="10%">Under&nbsp;Inspection</th>   
		              <th align="center" width="10%">Actions</th>          
		         </tr>
		         <%
					if(vectBreakSheet.size() == 0)
					{
		         %>       
				       <tr id="rowid-1">
					      <td align="center"><input type="text" name="fldSerialNo" id="serialNo1" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSerialNo1" ></span></td>
				          <td align="center"><input type="text" name="fldToolNo" id="toolNo1" class="txtleastavg"  value="" onkeyup="javascript:funToolNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolNo1" ></span></td>
				          <td align="center"><input type="text" name="fldProductName" id="productName1" class="txtleastavg"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolName1" ></span></td>
				          <td align="center"><input type="text" name="fldMachineNo" id="machineNo1" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoNo1" ></span></td>		          
						  <td align="center"><input type="text" name="fldDrawingNo" id="drawingNo1" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoDate1" ></span></td>
						  <td align="center"><input type="text" name="fldBatchQty" id="batchQty1" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo1" ></span></td>				  
						  <td align="center"><input type="text" name="fldToolingRequestQty" id="toolingRequestQty1" class="textverysmall"  value="" onkeyup="javascript:funInspReportNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtInspReportNo1"></span></td>				  
						  <td align="center"><input type="text" name="flduom" id="uom1" class="textsmall"  value="" onkeyup="javascript:funDqDetails(event,this);" autocomplete="off"/><span style="display:none;" id="txtDqDetails1" ></span></td>
						  <td align="center"><input type="text" name="fldInStock" id="inStock1" class="textverysmall"  value="" onkeyup="javascript:funMOC(event,this);" autocomplete="off"/><span style="display:none;" id="txtMOC1" ></span></td>
						  <td align="center"><input type="text" name="fldUnderInpection1" id="underInpection1" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceNo1" ></span></td>
						  
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
					         <td align="center"><input type="text" name="fldSerialNo" id="serialNo1" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSerialNo1" ></span></td>
					          <td align="center"><input type="text" name="fldToolNo" id="toolNo1" class="txtleastavg"  value="" onkeyup="javascript:funToolNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolNo1" ></span></td>
					          <td align="center"><input type="text" name="fldProductName" id="productName1" class="txtleastavg"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolName1" ></span></td>
					          <td align="center"><input type="text" name="fldMachineNo" id="machineNo1" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoNo1" ></span></td>		          
							  <td align="center"><input type="text" name="fldDrawingNo" id="drawingNo1" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoDate1" ></span></td>
							  <td align="center"><input type="text" name="fldBatchQty" id="batchQty1" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo1" ></span></td>				  
							  <td align="center"><input type="text" name="fldToolingRequestQty" id="toolingRequestQty1" class="textverysmall"  value="" onkeyup="javascript:funInspReportNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtInspReportNo1"></span></td>				  
							  <td align="center"><input type="text" name="flduom" id="uom1" class="textsmall"  value="" onkeyup="javascript:funDqDetails(event,this);" autocomplete="off"/><span style="display:none;" id="txtDqDetails1" ></span></td>
							  <td align="center"><input type="text" name="fldInStock" id="inStock1" class="textverysmall"  value="" onkeyup="javascript:funMOC(event,this);" autocomplete="off"/><span style="display:none;" id="txtMOC1" ></span></td>
							  <td align="center"><input type="text" name="fldUnderInpection1" id="underInpection1" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceNo1" ></span></td>
						
							  <td align="center"><input type="text" name="fldremark" id="remark1" class="txtleastavg"  value="" onkeyup="javascript:funReceivedQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate1" ></span></td>
							  				      			          			          
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
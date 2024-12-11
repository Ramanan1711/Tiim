<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tooling Periodic Inspection Report</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<script>
function printStatement()
{
	var divContents = $("#content").html();
    var printWindow = window.open('', '', 'height=400,width=800');
    printWindow.document.write('<html>');
    printWindow.document.write('</head><body >');
    printWindow.document.write(divContents);
    printWindow.document.write('</body></html>');
    printWindow.document.close();
    printWindow.print();
}
 
</script>

</head>
<body class="body">
<form name="frmPeriodicReport" method="post"  id="frmPeriodicReport" autocomplete="off">
<div id="content">
   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="10px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Tooling Periodic Inspection Report</td> 
           <td width="35%" class="heading" align="right">&nbsp;TIIM 100 -  &nbsp;${sesBranchName}</td>                                      
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
				<table style="width:70%" align="left" cellpadding="3" cellspacing="0" border="0" id="returnNoteData">		
					<tr>
						<td width="3">&nbsp;&nbsp;</td>
						<td width="25%" class="formlabelblack" align="left">Report No&nbsp;</td>
						<td align="left">${reportNo} </td>		
						<td width="35%" class="formlabelblack" align="left">Report Date</td>
						<td class="popuplabel" align="left">${reportDate} </td>	        	        			       	       
					</tr>
					<tr>
						<td width="3">&nbsp;&nbsp;</td>
						<td width="25%" class="formlabelblack" align="left">Request No&nbsp;</td>
						<td align="left">${requestNo} </td>		
						<td width="35%" class="formlabelblack" align="left">Request Date</td>
						<td class="popuplabel" align="left">${requestDate} </td>	        	        			       	       
					</tr>
					
					<tr>
						<td width="3">&nbsp;&nbsp;</td>
						<td width="25%" class="formlabelblack" align="left">Requested by&nbsp;</td>
						<td align="left">${requestedBy} </td>		
						<td width="35%" class="formlabelblack" align="left">Report by</td>
						<td class="popuplabel" align="left">${reportedBy} </td>	        	        			       	       
					</tr>
					
					<tr>
					    <td class="popuplabel" colspan="3" align="left"><span id="autoStatus" class="popuptopalert"><br></span></td>
					</tr>
					
				</table>
				<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0" width="99%" align="center">
					<tr height="15px">
						<td><hr class="style-six"></td>
					</tr>
				</table>
             <!-- END -->
         
				<c:choose>
					<c:when test="${fn:length(lstPeriodicInspectionReport) gt 0}">
					<div class="table-responsive">
						<table class="table table-bordered" style="width:96%" align="center">
					         <tr>
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Machine&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tablet&nbsp;Produced&nbsp;Qty</th>
					              <th align="center" width="10%">UOM</th>
					              <th align="center" width="10%">Tooling&nbsp;Status</th>
								  <th align="center" width="10%">Inspection&nbsp;Interval&nbsp;Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Status&nbsp;(After Inspection)</th>
					              <th align="center" width="10%">Condition</th>
					              <th align="center" width="10%">Remark</th>
					                   
					         </tr>
					         <c:forEach items="${lstPeriodicInspectionReport}" var="periodicReport" varStatus="row">  
						                 <tr>
									          <td align="center">${periodicReport.lotNumber}</td>
									          <td align="center">${periodicReport.productName}</td>
									          <td align="center">${periodicReport.typeOfTool}</td>		          
											  <td align="center">${periodicReport.drawingNo}</td>
											  <td align="center">${periodicReport.machineType}</td>				  
											  <td align="center">${periodicReport.producedQuantity}</td>
											  <td align="center">${periodicReport.uom}</td>
											  <td align="center">${periodicReport.toolingStatus}</td>
											  <td align="center">${periodicReport.intervalQty1}</td>
											  <td align="center">${periodicReport.inspectionStatusAfterInspection1} </td>
											  <td align="center">${periodicReport.condition1}</td>
											  <td align="center">${periodicReport.remark1}</td>
											 
							           </tr> 
							           </c:forEach>
					       </table> 	
					     </div>
				    </c:when>
				 </c:choose>
      
			 <table  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="10px">
		           <td height="10px" align="center">&nbsp;</td>
			   </tr>
	        </table> 
     </DIV>
     </center>    
     </div>
     <table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
	    <tr>
           <td height="50px" align="left">
		     <input type="button" class="btn btnNormal"  onclick="javascript:printStatement();" value="Print" /></td>
	    </tr>
	 </table>       
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
	 <input type="hidden" name="message" id="message" value="${message}" />
	        
</form>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 
<link rel="stylesheet" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>
<style>
</style>
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
<form name="frmIssue" method="post"  id="frmIssue" autocomplete="off">
  <div id="content">
   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="10px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Production Tool Issue</td> 
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
				<table style="width:70%" align="left" cellpadding="3" cellspacing="0" border="0">		
					<tr>
						<td width="10%" class="tabpopuplabel" align="left">&nbsp;</td>
						<td width="35%" class="formlabelblack" align="left">Issue No&nbsp;</td>
						<td class="popuplabel" align="left">${issueId}</td>			        	        			       	       
					</tr>
					<tr>
					    <td></td>
					    <td class="formlabelblack" align="left">Issue Date&nbsp;</span></td>
						<td class="popuplabel" align="left">${issueDate}</td>
					</tr>
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Request No.&nbsp;</span></td>
						<td class="popuplabel" align="left">${requestNo}</td>
							        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="formlabelblack" align="left">Request Date&nbsp;</span></td>		
						<td class="popuplabel" align="left">${requestDate}</td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Requested By&nbsp;</td>			
						<td class="popuplabel" align="left">${requestBy}</td>			
																		
					</tr>	
					<tr>
					    <td></td>
					    <td class="formlabelblack" align="left">Issued By&nbsp;</td>
					    <td class="popuplabel" align="left">${issueBy}</td>
					</tr>					     		    
					<tr>
					    <td class="popuplabel" colspan="3" align="center"><span id="autoStatus" class="popuptopalert"></span></td>
					</tr>
				</table>
				<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0" width="99%" align="center">
					<tr height="15px">
						<td><hr class="style-six"></td>
					</tr>
				</table>
             <!-- END -->
         
				<c:choose>
					<c:when test="${fn:length(lstToolingIssue) gt 0}">
					
					    <div class="table-responsive">
							<table class="table table-bordered" style="width:98%" id="issueData" align="center">
					         <tr>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Machine&nbsp;No/Name</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					               <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
								  <th align="center" width="10%">Batch&nbsp;Qty</th>
								  <th align="center" width="10%">Tooling&nbsp;Req&nbsp;Qty</th>
					             
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Issued&nbsp;Qty</th>             
					              <th align="center" width="10%">UOM</th>
					              <th align="center" width="10%">Serial&nbsp;Number</th>    
					         </tr>
					        
					         		<c:forEach items="${lstToolingIssue}" var="lstToolingIssue" varStatus="row">  
						                      
									          <td align="center">${lstToolingIssue.productName1}</td>
									          <td align="center">${lstToolingIssue.drawingNo1}</td>
									          <td align="center">${lstToolingIssue.machineName1}</td>		
									          <td align="center">${lstToolingIssue.typeOfTooling1}</td>          
											  <td align="center">${lstToolingIssue.toolingLotNumber1}</td>
											  <td align="center">${lstToolingIssue.batchQty1}</td>				  
											  <td align="center">${lstToolingIssue.requestQty1}</td>				  
											  
											  <td align="center">${lstToolingIssue.lastInspectionDate1}</td>
											  <td align="center">${lstToolingIssue.nextDueQty1}</td>
											  <td align="center">${lstToolingIssue.issuedQty1}</td>
											  <td align="center">${lstToolingIssue.UOM1}</td>
											  <td align="center">${lstToolingIssue.serialNumber}</td>
											  
											 
							           </tr> 
							         
							           </c:forEach>
					         
					       </table> 	
					       </div>
				    </c:when>
				    <c:otherwise>
				          <div class="table-responsive">
							<table class="table table-bordered" style="width:98%" align="center" id="cylinderFieldData">
					         <tr>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Machine&nbsp;No/Name</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
								  <th align="center" width="10%">Batch&nbsp;Qty</th>
								  <th align="center" width="10%">Tooling&nbsp;Req&nbsp;Qty</th>
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Issued&nbsp;Qty</th>             
					              <th align="center" width="10%">UOM</th>  
					         </tr>
			                 <tr>
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
				           </tr> 		        		      
					       </table>     
					       </div>    
				    </c:otherwise>
				 </c:choose>
		      
		       <!-- <table cellspacing="0" width="99%" cellpadding="5" border="0" align="center">		
		            <tr><td></td></tr>      
					<tr>  
						<td align="right" class="legend" width="100%%"><img border="0"  src="./images/rules.png">&nbsp;-&nbsp;Quick Reference&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/up-arrow.png">&nbsp;-&nbsp;Insert New Row Above&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon.gif">&nbsp;-&nbsp;Delete&nbsp;</td> 		            				
					</tr>
			 </table>  -->      
			 <table  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="50px">
		           <td height="50px" align="center">&nbsp;</td>
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

</form>
</body>
</html>
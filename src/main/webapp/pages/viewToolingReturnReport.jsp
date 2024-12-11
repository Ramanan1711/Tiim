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
<form name="frmToolReturns" method="post"  id="frmToolReturns" autocomplete="off">

<div id="content">
   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="10px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Tooling Production Return</td> 
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
						<td width="3">&nbsp;&nbsp;</td>
						<td width="25%" class="formlabelblack" align="left">Return Note No&nbsp;</td>
						<td align="left">${returnId} </td>		
						<td width="35%" class="formlabelblack" align="left">Return Date</td>
						<td class="popuplabel" align="left">${returnDate} </td>	        	        			       	       
					</tr>	
					<tr>
						<td width="3">&nbsp;&nbsp;</td>
						<td width="25%" class="formlabelblack" align="left">Issue No&nbsp;</td>
						<td align="left">${issueId} </td>		
						<td width="35%" class="formlabelblack" align="left">Issue Date</td>
						<td class="popuplabel" align="left">${issueDate} </td>	        	        			       	       
					</tr>	
						
					<tr>
						<td width="3">&nbsp;&nbsp;</td>
						<td width="25%" class="formlabelblack" align="left">Returned by&nbsp;</td>
						<td align="left">${returnBy} </td>		
						<td width="35%" class="formlabelblack" align="left">Issued by</td>
						<td class="popuplabel" align="left">${issueBy} </td>	        	        			       	       
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
				
				<c:choose>
					<c:when test="${fn:length(lstIssueNote) gt 0}">
					        <table class="tablesorter" style="width:98%" id="returnNoteData" align="center">
					         <tr>
					              <th align="center" width="10%">Product&nbsp;Name</th>
					              <th align="center" width="10%">Drawing&nbsp;No.</th>
					              <th align="center" width="10%">Machine&nbsp;No/Name</th>
					              <th align="center" width="10%">Type&nbsp;Of&nbsp;Tooling</th>
					              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
								  <th align="center" width="10%">Batch&nbsp;Qty</th>
								  <th align="center" width="10%">Batch&nbsp;Number</th>
								  <th align="center" width="10%">Tooling&nbsp;Prod&nbsp;Qty</th>
					              
					              <th align="center" width="10%">Tooling&nbsp;Return&nbsp;Qty</th>
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Issued&nbsp;Qty</th>             
					              <th align="center" width="10%">UOM</th>  
					              <th align="center" width="10%">Tooling&nbsp;Status</th>    
					         </tr>
					         <c:forEach items="${lstIssueNote}" var="lstIssueNote" varStatus="row">  
						                 <tr>
									          <td align="center">${lstIssueNote.productName1}</td>
									           <td align="center">${lstIssueNote.drawingNo1}</td>
									          <td align="center">${lstIssueNote.machineName1}</td>		
									          <td align="center">${lstIssueNote.typeOfTooling1}</td>          
											  <td align="center">${lstIssueNote.toolingLotNumber1}</td>
											  <td align="center">${lstIssueNote.batchQty1}</td>
											  <td align="center">${lstIssueNote.batchNumber1}</td>					  
											  <td align="center">${lstIssueNote.producedQty1}</td>
											  
											  <td align="center">${lstIssueNote.requestQty1}</td>
											  <td align="center">${lstIssueNote.lastInspectionDate1}</td>
											  <td align="center">${lstIssueNote.nextDueQty1}</td>
											  <td align="center">${lstIssueNote.issuedQty1}</td>
											  <td align="center">${lstIssueNote.UOM1}</td>
											  <td align="center">${lstIssueNote.toolingStatus1} </td>
											  
							           </tr> 
							   </c:forEach>
					         
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
								  <th align="center" width="10%">Batch&nbsp;Number</th>
								  <th align="center" width="10%">Tooling&nbsp;Prod&nbsp;Qty</th>
					              
					              <th align="center" width="10%">Tooling&nbsp;Return&nbsp;Qty</th>
					              <th align="center" width="10%">Last&nbsp;Inspection&nbsp;Date</th>
					              <th align="center" width="10%">Next&nbsp;Due&nbsp;Qty</th>
					              <th align="center" width="10%">Tooling&nbsp;Issued&nbsp;Qty</th>             
					              <th align="center" width="10%">UOM</th>  
					              <th align="center" width="10%">Tooling&nbsp;Status</th>  
					         </tr>
			                 <tr >
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
				           </tr> 		        		      
					       </table>        
				    </c:otherwise>
				 </c:choose>
		       </div> 
		      
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
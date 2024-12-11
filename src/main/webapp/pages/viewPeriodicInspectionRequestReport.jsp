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
<form name="frmPeriod" method="post"  id="frmPeriod" autocomplete="off">
<div id="content">
 <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="10px">
	           <td height="10px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Tooling Periodical Inspection Request</td> 
           <td width="35%" class="heading" align="right">&nbsp;TIIM 100 -  &nbsp;${sesBranchName}</td>           
       </tr>
   </table>
   <center>   
         <DIV STYLE="width: 99%; padding:0px; margin: 0px;height:auto;min-height:auto;" class="content">
         
         
				<table style="width:99%" align="left" cellpadding="3" cellspacing="0" border="0">	
					<tr>
						<td width="3">&nbsp;&nbsp;</td>
						<td width="25%" class="formlabelblack" align="left">Request No.&nbsp;</td>
						<td align="left">${requestNo} </td>		
						<td width="35%" class="formlabelblack" align="left">Request Date</td>
						<td class="popuplabel" align="left">${requestDate} </td>	        	        			       	       
					</tr>	
						     			
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Requested By&nbsp;</td>						
						<td class="popuplabel" align="left">${requestedBy}</td>												
					</tr>	
				</table>
				<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0" width="99%" align="center">
					<tr height="15px">
						<td><hr class="style-six"></td>
					</tr>
				</table>
             <!-- END -->
   				   
		        <div class="table-responsive">
				<table class="table table-bordered" style="width:96%" align="center" id="periodicData">
		         <tr>
		              
		              <th align="center" width="10%">Tooling&nbsp;Lot&nbsp;No</th>
		              <th align="center" width="10%">Product&nbsp;Name</th>
		              <th align="center" width="10%">Machine&nbsp;Name</th>
					  <th align="center" width="10%">Drawing&nbsp;No.</th>
					  <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
		              <th align="center" width="10%">Tab&nbsp;Prod&nbsp;Qty</th>
		              <th align="center" width="10%">UOM</th>
		              <th align="center" width="10%">Tooling&nbsp;Status</th>
		              <th align="center" width="10%">Inspection&nbsp;Due&nbsp;Qty</th>            
		         </tr>
		        		<c:choose>
								<c:when test="${fn:length(lstPeriodicInspection) gt 0}">
								
									<c:forEach items="${lstPeriodicInspection}" var="lstPeriodicDetail" varStatus="row">  	
									    <tr >
								          
								          <td align="center">${lstPeriodicDetail.lotNumber}</td>
								          <td align="center">${lstPeriodicDetail.productName}</td>
								          <td align="center">${lstPeriodicDetail.machineType}</td>
								          		          
										  <td align="center">${lstPeriodicDetail.drawingNo}</td>				  
										  <td align="center">${lstPeriodicDetail.typeOfTool}</td>				  
										  <td align="center">${lstPeriodicDetail.tabProducedQty1}</td>
										  <td align="center">${lstPeriodicDetail.uom}</td>
										  <td align="center">${lstPeriodicDetail.toolingStatus1}</td>
										  <td align="center">${lstPeriodicDetail.inspectionDueQty1} </td>
										</tr> 
							        </c:forEach> 
							           
								</c:when>
								
							</c:choose>

		       </table> 
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
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
	        
</form>
</body>
</html>
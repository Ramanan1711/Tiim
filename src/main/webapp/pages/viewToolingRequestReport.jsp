<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

<script type="text/javascript" src="./grid/RequestNote.js"></script> 

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
<body class="body" >
<form name="frmRequestNote" method="post"  id="frmRequestNote" autocomplete="off">
<div id="content">
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Production Tool Request</td> 
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
						<td width="35%" class="formlabelblack" align="left">Request No&nbsp;</span></td>
						<td class="popuplabel" align="left">${requestId}</td>
									        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<td class="formlabelblack" align="left">Request Date&nbsp;</td>
						<td class="popuplabel" align="left">${requestDate}</td>
					</tr>		     			
					<tr>
						<td></td>
						<td width="10%" class="formlabelblack" align="left">Requested By&nbsp;</td>						
						<td width="50%" class="popuplabel" align="left">${requestBy}</td>												
					</tr>	
										     		    
					<tr>
					    <td class="popuplabel" colspan="3" align="left"></td>
					</tr>
				</table>
				
             <!-- END -->
         		<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
					<tr height="20px">
					<td></td>
					</tr>
				</table>
		        <div class="table-responsive">
				<table class="table table-bordered" style="width:98%" align="center" id="requestNoteData">
		         <tr>
		              
		              <th align="center" width="10%">Product&nbsp;Name</th>
		              <th align="center" width="10%">Drawing&nbsp;No.</th>
		              <th align="center" width="10%">Machine&nbsp;Name</th>
		              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					  
					  <th align="center" width="10%">Batch&nbsp;Qty&nbsp;</th>
		              <th align="center" width="10%">Tooling&nbsp;Req&nbsp;Qty</th>
		              <th align="center" width="10%">UOM</th>
		              <th align="center" width="10%">In&nbsp;Stock</th>
		          <!--     <th align="center" width="10%">Under&nbsp;Inspection</th>             
		              <th align="center" width="10%">Actions</th>  -->
		         </tr>
		       
			        		
			        		<c:choose>
								<c:when test="${fn:length(lstToolingRequestNoteDetail) gt 0}">
								
									<c:forEach items="${lstToolingRequestNoteDetail}" var="lstRequestDetail" varStatus="row">  	
									    <tr id="rowid" >
								          
								          <td align="center">${lstRequestDetail.productName1}</td>		          
										  <td align="center">${lstRequestDetail.drawingNo1}</td>				  
										  <td align="center">${lstRequestDetail.machingType1}</td>
										  <td align="center">${lstRequestDetail.typeOfTool1}</td>
										  
										  <td align="center">${lstRequestDetail.batchQty1}</td>				  
										  <td align="center">${lstRequestDetail.requestQty1}</td>
										  <td align="center">${lstRequestDetail.UOM1}</td>
										  <td align="center">${lstRequestDetail.inStock1}</td>
										<%--   <td align="center">${lstRequestDetail.underInspection1}</td> --%>
							           </tr> 
							        </c:forEach> 
							           
								</c:when>
								<c:otherwise>
										<tr id="rowid-0" >
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
								</c:otherwise>
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
     
</form>
</body>
</html>
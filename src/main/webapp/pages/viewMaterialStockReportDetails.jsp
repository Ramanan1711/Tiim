<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Material Stock Status Report</title>
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

</head>
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
<body class="body" >
<form name="frmStockReportDetail" method="post"  id=""frmStockReportDetail"" autocomplete="off">
<div id="content">
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;Material Stock Status Report</td> 
            <td width="35%" class="heading" align="right">&nbsp;TIIM 100 -  &nbsp;${sesBranchName}</td>                        
       </tr>
   </table>
   <center>   
         <DIV STYLE="width: 99%; padding:0px; margin: 0px;height:auto;min-height:auto;" class="content">

				<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0" width="99%" align="center">
					<tr height="15px">
						<td><hr class="style-six"></td>
					</tr>
				</table>
             <!-- END -->
   			
				<c:choose>
					<c:when test="${fn:length(lstStock) gt 0}">
					<div class="table-responsive">
					        <table class="table table-bordered" style="max-width:98%;" align="center" id="cylinderFieldData">
					         <tr>
					     		  <th align="center" width="10%">Material Name</th>
								  <th align="center" width="10%">Lot&nbsp;Number&nbsp;</th>
					              <th align="center" width="10%">Available&nbsp;Qty</th>
					         </tr>
					         
					         		<c:forEach items="${lstStock}" var="lstToolingProduct" varStatus="row">  
						                 <tr>
									          
									          <td align="center">${lstToolingProduct.materialName}</td>
											  <td align="center">${lstToolingProduct.toolingLotNumber}</td>
											  <td align="center">${lstToolingProduct.stockQty}  </td>
							           </tr> 
							           
							           </c:forEach>
					      
					       </table> 
					       </div>	
				    </c:when>
				    <c:otherwise>
				    <div class="table-responsive">
					        <table class="table table-bordered"  style="max-width:98%;" align="center" id="cylinderFieldData">
					         <tr>
					              <th align="center" width="10%">Material Name</th>
								  <th align="center" width="10%">Lot&nbsp;Number&nbsp;</th>
					              <th align="center" width="10%">Available&nbsp;Qty</th>      
					         </tr>
			                 <tr>
						         
						          <td align="center" colspan='8'>No Stocks are available</td>
						         
				           </tr> 		        		      
					       </table>  
					       </div>      
				    </c:otherwise>
				 </c:choose>
		      
     </DIV>
     </center> 
    </div>   
     
     <table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
	    <tr>
           <td height="50px" align="left">
		     <input type="button" class="btn btnNormal"  onclick="javascript:printStatement();" value="Print" />
		     &nbsp;&nbsp;<input type="button" class="btn btnNormal"  onclick="javascript:closeStatement();" value="Close" /></td>
	    </tr>
	 </table>       
      
</form>
</body>
</html>
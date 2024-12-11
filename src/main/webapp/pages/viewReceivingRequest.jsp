<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tooling Receiving Inspection</title>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>
 <script type="text/javascript" src="./js/jspdf.min.js"></script> 
<script type="text/javascript" src="./editor/editor.js"></script>
<script>
function openPDF() {
    var pdf = new jsPDF('p', 'pt', 'letter');
    alert(pdf);
    // source can be HTML-formatted string, or a reference
    // to an actual DOM element from which the text will be scraped.
    source = $('#content')[0];

    // we support special element handlers. Register them with jQuery-style 
    // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
    // There is no support for any other type of selectors 
    // (class, of compound) at this time.
    specialElementHandlers = {
        // element with id of "bypass" - jQuery style selector
        '#editor': function (element, renderer) {
            // true = "handled elsewhere, bypass text extraction"
            return true
        }
    };
    margins = {
        top: 80,
        bottom: 60,
        left: 40,
        width: 522
    };
    // all coords and widths are in jsPDF instance's declared units
    // 'inches' in this case
    pdf.fromHTML(
    source, // HTML string or DOM elem ref.
    margins.left, // x coord
    margins.top, { // y coord
        'width': margins.width, // max width of content on PDF
        'elementHandlers': specialElementHandlers
    },

    function (dispose) {
        // dispose: object with X, Y of the last line add to the PDF 
        //          this allow the insertion of new lines after html
        pdf.save('Test.pdf');
    }, margins);
}

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
<form name="frmInspection" method="post"  id="frmInspection" autocomplete="off">
<div id="content">
   <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
	  	<tr height="10px">
	         <td></td>
	    </tr>
   </table>
   <table width="100%" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="35%" class="heading" align="left">&nbsp;New Tool Inspection Request</td> 
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
				<table style="width:80%" align="left" cellpadding="3" cellspacing="0" border="0">		
					<tr>
						<td width="3">&nbsp;&nbsp;</td>
						<td width="25%" class="formlabelblack" align="left">Inspection Request No&nbsp;</td>
						<td align="left">${inspectionReportNo} </td>		
						<td width="35%" class="formlabelblack" align="left">&nbsp;&nbsp;&nbsp;Inspection Request Date</td>
						<td class="popuplabel" align="left">${inspectionReportDate} </td>	        	        			       	       
					</tr>
					<tr>
					    <td></td>
						<%-- <td class="formlabelblack" align="left">Inspection Request Date&nbsp;</td>
						<td class="popuplabel" align="left">${inspectionReportDate} </td> --%>
					</tr>		     			
					<tr>
						<td></td>
						<td  class="formlabelblack" align="left">GRN No&nbsp;</td>						
						<td   align="left">${grnNo}</td>		
						<td class="formlabelblack" align="left">&nbsp;&nbsp;&nbsp;GRN Date</td>
					    <td class="popuplabel" align="left">${grnDate}</td>										
					</tr>	
					<tr>
					    <td></td>
					   <%--  <td class="formlabelblack" align="left">GRN Date&nbsp;</td>
					    <td class="popuplabel" align="left">${grnDate}</td> --%>
					</tr>					     		    
					<tr>
						<td></td>
						<td class="formlabelblack" align="left">Supplier Code</td>						
						<td align="left">${supplierCode}</td>				
						<td class="formlabelblack" align="left">&nbsp;&nbsp;&nbsp;Supplier Name</td>
					    <td class="popuplabel" align="left">${supplierName}</td>		
					</tr>
					<tr>
					    <td></td> 
					    <%-- <td class="formlabelblack" align="left">Supplier Name&nbsp;</td>
					    <td class="popuplabel" align="left">${supplierName}</td> --%>
					</tr>
					<tr>
					    <td class="popuplabel" colspan="3" align="center"><span id="autoStatus" class="popuptopalert"></span><br></td>
					</tr>
										
					<tr>
						<td colspan="3"></td>
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
			  
				<c:choose>
					<c:when test="${fn:length(lstToolingProduct) gt 0}">
					<div class="table-responsive">
					        <table class="table table-bordered" style="width:98%" align="center" id="cylinderFieldData">
					         <tr>
					              
					              <th align="center" width="8%">Product&nbsp;Name</th>
					              <th align="center" width="8%">Drawing&nbsp;No.</th>
					              <th align="center" width="8%">Machine&nbsp;Name</th>
					              <th align="center" width="8%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="8%">Lot&nbsp;Number&nbsp;</th>
					              <th align="center" width="8%">Customer&nbsp;PO&nbsp;No.</th>
					              <th align="center" width="8%">Customer&nbsp;PO&nbsp;Date</th>
								  
					              <th align="center" width="8%">Received&nbsp;Qty</th>
					              <th align="center" width="8%">UOM</th>   
					              <th align="center" width="8%">Remark</th>        
					         </tr>
					         <c:forEach items="${lstToolingProduct}" var="lstToolingProduct" varStatus="row">  
						                 <tr id="rowid">
									          
									          <!-- <td align="center"></td> -->
									          <td align="center">${lstToolingProduct.toolingname}</td>
									          <td align="center">${lstToolingProduct.drawingNo}</td>
									          <td align="center">${lstToolingProduct.machineType}</td>
									          <td align="center">${lstToolingProduct.typeOfTool}</td>
									          <td align="center">${lstToolingProduct.lotNumber}</td>
									          <td align="center">${lstToolingProduct.poNumber}</td>		          
											  <td align="center">${lstToolingProduct.poDate}</td>
											  <td align="center">${lstToolingProduct.receivedQuantity}</td>
											  <td align="center">${lstToolingProduct.uom}</td>											  
											  <td align="center">${lstToolingProduct.remarks1} </td>
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
					              <th align="center" width="10%">Machine&nbsp;Name</th>
					              <th align="center" width="10%">Type&nbsp;of&nbsp;Tooling</th>
					              <th align="center" width="10%">Lot&nbsp;Number&nbsp;</th>
					              <th align="center" width="10%">Customer&nbsp;PO&nbsp;No.</th>
					              <th align="center" width="10%">Customer&nbsp;PO&nbsp;Date</th>
								  
					              <th align="center" width="10%">Received&nbsp;Qty</th>
					              <th align="center" width="10%">UOM</th>
   
					              <th align="center" width="10%">Remark</th>        
					         </tr>
			                 <tr >
						          
						          <!-- <td align="center">&nbsp;</td> -->
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
		      
     </DIV>
     </center>    
     
     <table  border="0" cellspacing="0" cellpadding="0"  width="99%" align="center">
	    <tr>
           <td height="50px" align="left">
		     <input type="button" class="btn btnNormal"  id="btnPrint" onclick="printStatement();" value="Print" /></td>
	    </tr>
	 </table>       
     <table  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="30px">
	           <td height="50px" align="center">&nbsp;</td>
		   </tr>
	 </table>
	 
</div>
	       <div id="editor"></div> 
</form>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
	<meta http-equiv="X-UA-Compatible" content="IE=9"/> 
	<title>Available Stock Report</title>
	<link rel="stylesheet" type="text/css" href="./css/semantic.css">
	
<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>
<style>
.pagination 
{  
  display:none;
}
</style>
</head>
<body>
<%@ include file="tiimMenu.jsp"%>
	<div class="ui page grid">
		<div class="wide column">
			
			<div class="ui divider hidden"></div>
			<div class="ui segment">
				
				<div class="ui divider"></div>
				<form class="ui form">
				
					<h4 class="ui dividing header">Available Stock Report</h4>
					<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
					<tr height="20px">
					<td></td>
					</tr>
				</table>
				<table style="width:70%" align="left" cellpadding="3" cellspacing="0" border="1">	
				<c:choose>
					<c:when test="${fn:length(lstStock) gt 0}">
					<div class="table-responsive">
					        <table class="table table-bordered" style="width:98%" align="center" id="cylinderFieldData" border="1">
					         <tr>
					              
					              <th align="center" width="20">Lot Number</th>
						          <th align="center" width="20">Product Name</th>
    						      <th align="center" width="25%">Drawing Number</th>
    						      <th align="center" width="15%">Machine Type</th>
    						      <th align="center" width="25%">Type Of Tooling</th>
    						      <th align="center" width="15%">Quantity</th>
    						       						          
					         </tr>
					         <c:forEach items="${lstStock}" var="lstStock" varStatus="row">  
						       <tr id="rowid">
								    <td align="center" style="text-align:center;">${lstStock.toolingLotNumber}</td>
						             <td align="center" style="text-align:center;">${lstStock.productName}</td>
						             <td align="center" style="text-align:center;">${lstStock.drawingNo}</td>
						             <td align="center" style="text-align:center;">${lstStock.machineName}</td>
									 <td align="center" style="text-align:center;">${lstStock.typeOfTool}</td>
						             <td align="center" style="text-align:center;">${lstStock.stockQty}</td>
							   </tr> 
							 </c:forEach>
							 
					       </table> 	
					     </div>
				    </c:when>
				    <c:otherwise>
				          <div class="table-responsive">
					        
					       </div>
				    </c:otherwise>
				 </c:choose>
				</table>
				</form>
			</div>
		</div>
	</div>
	
	<div style="display:none;" class="table-responsive" id="showReport">
<table id="basic-table">
<c:choose>
					<c:when test="${fn:length(lstStock) gt 0}">
					
					         <tr>
					              
					              <th align="center" width="20">Lot Number</th>
						          <th align="center" width="20">Product Name</th>
    						      <th align="center" width="25%">Drawing Number</th>
    						      <th align="center" width="15%">Machine Type</th>
    						      <th align="center" width="25%">Type Of Tooling</th>
    						      <th align="center" width="15%">Quantity</th>
    						       						          
					         </tr>
					         <c:forEach items="${lstStock}" var="lstStock" varStatus="row">  
						       <tr id="rowid">
								    <td align="center" style="text-align:center;">${lstStock.toolingLotNumber}</td>
						             <td align="center" style="text-align:center;">${lstStock.productName}</td>
						             <td align="center" style="text-align:center;">${lstStock.drawingNo}</td>
						             <td align="center" style="text-align:center;">${lstStock.machineName}</td>
									 <td align="center" style="text-align:center;">${lstStock.typeOfTool}</td>
						             <td align="center" style="text-align:center;">${lstStock.stockQty}</td>
							   </tr> 
							 </c:forEach>
						
				    </c:when>
				    <c:otherwise>
				          <div class="table-responsive">
					        
					       </div>
				    </c:otherwise>
				 </c:choose>
</table>
</div>
	<!-- scripts -->

	<script type="text/javascript" src="./js/html2canvas.js"></script>
	<!-- <script type="text/javascript" src="./js/jspdf.js"></script>
	<script type="text/javascript" src="./js/jspdf.plugin.autotable.js"></script> -->
	<!--  <script type="text/javascript" src="./js/basic.js"></script> -->
	<!--   <script type="text/javascript" src="./js/jspdf.debug.js"></script> --> 
	 <script src='https://cdn.rawgit.com/simonbengtsson/jsPDF/requirejs-fix-dist/dist/jspdf.debug.js'></script> 
	<script src='https://unpkg.com/jspdf-autotable@2.3.2'></script> 

</body>

<div class="ui divider hidden"></div>
	<table>
	
	<td width=55%></td>
	<td><input type="button" title="Click to Download" class="btn btnSMNormal" id="btnpop" value="Download PDF" onclick="generate();"></td>
	</table>
	
	<div class="ui divider hidden"></div>
<script>
var 
form = $('.form'),
cache_width = form.width(),
a4  =[ 595.28,  841.89];  // for a4 size paper width and height
$(document).ready(function(){

//	generate();
});


function generate() {
	 l = {
		     orientation: 'l',
		     unit: 'mm',
		     format: 'a3',
		     compress: true,
		     fontSize: 8,
		     lineHeight: 1,
		     autoSize: false,
		     printHeaders: true
		 };
	  var doc = new jsPDF('l', 'pt');

	  var res = doc.autoTableHtmlToJson(document.getElementById("basic-table"));
	 // doc.addImage("./images/add", 'PNG', res.settings.margin.left, 20, 50, 50);
	 // doc.autoTable(res.columns, res.data, {margins: {right: 10, left: 10, top: 40, bottom: 40}, startY: 70});

	  var header = function(data) {
	    doc.setFontSize(10);
	    doc.setTextColor(40);
	    doc.setFontStyle('normal');
	   // doc.addImage("./images/header-bg.jpeg", 'JPEG', data.settings.margin.left, 20, 50, 50);
	   // doc.text("Testing Report", data.settings.margin.left, 50);
	  };

	  var options = {
	    beforePageContent: header,
	    margin: {
	      top: 80
	    },
	    startY: doc.autoTableEndPosY() + 20
	  };

	  doc.autoTable(res.columns, res.data, options);

	  doc.save("AvailableLot.pdf");  
	}
</script>

</html>
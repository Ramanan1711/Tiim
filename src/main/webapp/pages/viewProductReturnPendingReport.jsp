<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
	<meta http-equiv="X-UA-Compatible" content="IE=9"/> 
	<title>Product Report</title>
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
				
					<h4 class="ui dividing header">Product Report</h4>
					<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
					<tr height="20px">
					<td></td>
					</tr>
				</table>
				<table style="width:70%" align="left" cellpadding="3" cellspacing="0" border="1">	
				<c:choose>
					<c:when test="${fn:length(lstProductReport) gt 0}">
					<div class="table-responsive">
					        <table class="table table-bordered" style="width:98%" align="center" id="cylinderFieldData" border="1">
					         <tr>
					              
					              <th width="20%">Issue Number</th>
					              <th width="10%">ProductName</th>
						          <th width="10%">Machine Type</th>
    						      <th width="15%">Type of Tool</th>
    						      <th width="15%">Drawing Number</th>
    						      
					         </tr>
					         <c:forEach items="${lstProductReport}" var="lstProductReport" varStatus="row">  
						       <tr id="rowid">
								    <td align="center" style="text-align:center;">${lstProductReport.issueId}</td>
						             <td align="center" style="text-align:center;">${lstProductReport.productName1}</td>
						             <td align="center" style="text-align:center;">${lstProductReport.machineName1}</td>
						             <td align="center" style="text-align:center;">${lstProductReport.typeOfTooling1}</td>
						   		     <td align="center" style="text-align:center;">${lstProductReport.drawingNo1}</td>						
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
	<!-- scripts -->

	<script type="text/javascript" src="./js/html2canvas.js"></script>
	<script type="text/javascript" src="./js/jspdf.js"></script>
	<script type="text/javascript" src="./js/app.js"></script>
</body>
<script>
var 
form = $('.form'),
cache_width = form.width(),
a4  =[ 595.28,  841.89];  // for a4 size paper width and height
$(document).ready(function(){
	createPDF();

//create pdf
function createPDF(){
	getCanvas().then(function(canvas){
		var 
		img = canvas.toDataURL("image/png"),
		doc = new jsPDF({
          unit:'px', 
          format:'a4'
        });     
        doc.addImage(img, 'JPEG', 20, 20);
        doc.save('productReport.pdf');
        form.width(cache_width);
	});
} 

// create canvas object
function getCanvas(){
	form.width((a4[0]*1.33333) -80).css('max-width','none');
	return html2canvas(form,{
    	imageTimeout:2000,
    	removeContainer:true
    });	
}
});
</script>

</html>
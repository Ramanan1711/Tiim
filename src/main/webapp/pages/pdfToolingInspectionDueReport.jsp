<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>Product Report</title>
<link rel="stylesheet" type="text/css" href="./css/semantic.css">

<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script>

<script type="text/javascript" src="./js/jquery.alerts.js"></script>
<link href="./css/jquery.alerts.css" rel="stylesheet" type="text/css">

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet"
	type="text/css" />
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>
<style>
.pagination {
	display: none;
}

th {
	display: table-cell;
	vertical-align: inherit;
	font-weight: bold;
	text-align: center;
	background-color: lightblue
}
</style>
</head>
<body>
	<%@ include file="tiimMenu.jsp"%>
	<div class="ui page grid">
		<div class="wide column">

			<div class="ui divider hidden"></div>
			<div class="ui segment">
				<form class="form">
					<table style="width: 98%">
						<tr>
							<td align="right"><input type="button"
								title="Click to Download" class="btn btnSMNormal"
								id="download_Btn" " value="Download PDF"></td>
						</tr>
					</table>
					<div id="reportbox">
						<table style="width: 98%">
							<tr>
								<td align="left" width="50%"><h2>Inspection Due Report</h2></td>
								<td align="right" width="50%"><img src="./images/logo.jpeg"
									style="object-fit: contain; width: 200px; height: 50px;" /></td>
							</tr>
						</table>

						<table style="width: 70%" align="left" cellpadding="3"
							cellspacing="0" border="0">
							<c:choose>
								<c:when
									test="${fn:length(toolTracker.toolingReceiptNotes) gt 0}">
									<div class="table-responsive">
										<tr>
											<td><B>Receipt Note</B></td>
										</tr>
										<table class="table table-bordered" style="width: 98%"
											align="center" id="cylinderFieldData" border="1">
											<tr>

												<th width="10%">ProductName</th>
												<th width="10%">Machine Type</th>
												<th width="15%">Type of Tool</th>
												<th width="15%">Lot Number</th>
												<th width="15%">Tooling Life</th>
												<th width="15%">Received Quantity</th>
												<th width="15%">Upper Qnty</th>
												<th width="15%">Lower Qty</th>
												<th width="15%">Service Interval Qnty</th>
												<th width="20%">Min Accepted Qty</th>


											</tr>
											<c:forEach items="${toolTracker.toolingReceiptNotes}"
												var="lstProductReport" varStatus="row">
												<tr id="rowid">
													<td align="center" style="text-align: center;">${lstProductReport.productName}</td>
													<td align="center" style="text-align: center;">${lstProductReport.machineType}</td>
													<td align="center" style="text-align: center;">${lstProductReport.typeOfTool}</td>
													<td align="center" style="text-align: center;">${lstProductReport.toolingLotNumber}</td>
													<td align="center" style="text-align: center;">${lstProductReport.toolingLife}</td>
													<td align="center" style="text-align: center;">${lstProductReport.receivedQuantity}</td>
													<td align="center" style="text-align: center;">${lstProductReport.upperQnty}</td>
													<td align="center" style="text-align: center;">${lstProductReport.lowerQnty}</td>
													<td align="center" style="text-align: center;">${lstProductReport.serviceIntervalQnty}</td>
													<td align="center" style="text-align: center;">${lstProductReport.minAcceptedQty}</td>
												</tr>
											</c:forEach>

										</table>
										<br>
										<c:choose>
											<c:when test="${fn:length(toolTracker.toolingRequests) gt 0}">
												<tr>
													<td><B>New Tool Inspection Request</B></td>
												</tr>
												<table class="table table-bordered" style="width: 98%"
													align="center" border="1">
													<tr>

														<th width="15%">ToolingName</th>
														<th width="15%">Machine Type</th>
														<th width="15%">Type of Tool</th>
														<th width="15%">Lot Number</th>
														<th width="15%">Received Qnty</th>
														<th width="15%">Drawing No</th>
														<th width="15%">PO Number</th>
														<th width="15%">PO Date</th>

													</tr>
													<c:forEach items="${toolTracker.toolingRequests}"
														var="lstProductReport" varStatus="row">
														<tr id="rowid">
															<td align="center" style="text-align: center;">${lstProductReport.toolingname}</td>
															<td align="center" style="text-align: center;">${lstProductReport.machineType}</td>
															<td align="center" style="text-align: center;">${lstProductReport.typeOfTool}</td>
															<td align="center" style="text-align: center;">${lstProductReport.lotNumber}</td>
															<td align="center" style="text-align: center;">${lstProductReport.receivedQuantity}</td>
															<td align="center" style="text-align: center;">${lstProductReport.drawingNo}</td>
															<td align="center" style="text-align: center;">${lstProductReport.poNumber}</td>
															<td align="center" style="text-align: center;">${lstProductReport.poDate}</td>
														</tr>
													</c:forEach>

												</table>
											</c:when>
										</c:choose>


										<br>
										<c:choose>
											<c:when
												test="${fn:length(toolTracker.toolingInspections) gt 0}">
												<tr>
													<td><B>New Tool Inspection Report</B></td>
												</tr>
												<table class="table table-bordered" style="width: 98%"
													align="center" border="1">
													<tr>

														<th width="15%">ToolingName</th>
														<th width="15%">Machine Type</th>
														<th width="15%">Type of Tool</th>
														<th width="15%">Lot Number</th>
														<th width="15%">Received Qnty</th>
														<th width="15%">Accepted Qty</th>
														<th width="15%">Rejected Qty</th>
														<th width="15%">Rejected S.No</th>

													</tr>
													<c:forEach items="${toolTracker.toolingInspections}"
														var="lstProductReport" varStatus="row">
														<tr id="rowid">
															<td align="center" style="text-align: center;">${lstProductReport.toolingname}</td>
															<td align="center" style="text-align: center;">${lstProductReport.machineType}</td>
															<td align="center" style="text-align: center;">${lstProductReport.typeOfTooling}</td>
															<td align="center" style="text-align: center;">${lstProductReport.lotNumber}</td>
															<td align="center" style="text-align: center;">${lstProductReport.receivedQuantity}</td>
															<td align="center" style="text-align: center;">${lstProductReport.acceptedQty}</td>
															<td align="center" style="text-align: center;">${lstProductReport.rejectedQty}</td>
															<td align="center" style="text-align: center;">${lstProductReport.rejectedSerialNumber}</td>
														</tr>
													</c:forEach>

												</table>
											</c:when>
										</c:choose>
										<br>
										<c:choose>
											<c:when
												test="${fn:length(toolTracker.toolingRequestNotes) gt 0}">
												<tr>
													<td><B>User Tool Request</B></td>
												</tr>
												<table class="table table-bordered" style="width: 98%"
													align="center" border="1">
													<tr>

														<th width="15%">ProductName</th>
														<th width="15%">Machine Type</th>
														<th width="15%">Type of Tool</th>
														<th width="15%">Lot Number</th>
														<th width="15%">Batch Nos</th>
														<th width="15%">Production Qty</th>
														<th width="15%">Batch Qty</th>
														<th width="15%">Request Qty</th>
														<th width="15%">In Stock</th>

													</tr>
													<c:forEach items="${toolTracker.toolingRequestNotes}"
														var="lstProductReport" varStatus="row">
														<tr id="rowid">
															<td align="center" style="text-align: center;">${lstProductReport.productName1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.machingType1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.typeOfTool1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.toolingLotNumber1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.batchnos1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.batchProd1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.batchQty1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.requestQty1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.inStock1}</td>
														</tr>
													</c:forEach>

												</table>
											</c:when>
										</c:choose>
										<br>
										<c:choose>
											<c:when
												test="${fn:length(toolTracker.toolingIssueNotes) gt 0}">
												<tr>
													<td><B>User Tool Issue</B></td>
												</tr>
												<table class="table table-bordered" style="width: 98%"
													align="center" border="1">
													<tr>

														<th width="15%">ProductName</th>
														<th width="15%">Machine Type</th>
														<th width="15%">Type of Tool</th>
														<th width="15%">Lot Number</th>
														<th width="15%">Batch Qty</th>
														<th width="15%">Request Qty</th>
														<th width="15%">Next Due Qty</th>
														<th width="15%">Issue Qty</th>
														<th width="15%">Serial No</th>

													</tr>
													<c:forEach items="${toolTracker.toolingIssueNotes}"
														var="lstProductReport" varStatus="row">
														<tr id="rowid">
															<td align="center" style="text-align: center;">${lstProductReport.productName1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.machineName1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.typeOfTooling1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.toolingLotNumber1}</td>

															<td align="center" style="text-align: center;">${lstProductReport.batchQty1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.requestQty1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.nextDueQty1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.issuedQty1}</td>
															<td align="center" style="text-align: center;">${lstProductReport.serialNumber}</td>
														</tr>
													</c:forEach>

												</table>
											</c:when>
										</c:choose>
									</div>
								</c:when>
							</c:choose>
							<br>

							<br>


						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.min.js">
</script>
<script>
$(document).ready(function () {  

});
const download_button =
document.getElementById('download_Btn');
const content =
document.getElementById('reportbox');

download_button.addEventListener
('click', async function () {
    const filename = 'inspection_report.pdf';

    try {
        const opt = {
            margin: 1,
            filename: filename,
            image: { type: 'jpeg', quality: 0.98 },
            html2canvas: { scale: 2 },
            jsPDF: {
                unit: 'in', format: 'letter',
                orientation: 'portrait'
            }
        };
        await html2pdf().set(opt).
            from(content).save();
    } catch (error) {
        console.error('Error:', error.message);
    }
});
</script>


</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>Product History Report</title>
<link rel="stylesheet" type="text/css" href="./css/semantic.css">
<link rel="stylesheet" type="text/css"
	href="css/smoothness/jquery-ui-1.8.17.custom.css">
<link rel="stylesheet" type="text/css" href="css/main.css">

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

				<div class="ui divider"></div>
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
								<td align="left" width="50%"><h2>Tooling Lot Batch
										Number Report</h2></td>
								<td align="right" width="50%"><img src="./images/logo.jpeg"
									style="object-fit: contain; width: 200px; height: 50px;" /></td>
							</tr>
						</table>
						<table class="spacerwht" border="0" cellspacing="0"
							cellpadding="0" align="center">
							<tr height="20px">
								<td></td>
							</tr>
							<tr>
								<td>SOP&nbsp;</td>
								<td><input type="text" class="textsmall" name="sop"
									id="sop" /></td>
							</tr>
							<tr>
								<td>Format Number&nbsp;</td>
								<td><input type="text" class="textsmall"
									name="formatNumber" id="formatNumber" /></td>
							</tr>
							<tr height="20px">
								<td></td>
							</tr>
						</table>
						<table class="spacerwht" border="0" cellspacing="0"
							cellpadding="0" align="center">
							<tr height="20px">
								<td></td>
							</tr>
						</table>
						<c:choose>
							<c:when test="${fn:length(toolingLotStocks) gt 0}">
								<div class="table-responsive">
									<font size="1">
										<table class="table table-bordered" style="width: 98%"
											align="center" id="cylinderFieldData" border="1">
											<tr>
												<th align="center" width="5">Batch No</th>
												<th align="center" width="15%">Product</th>
												<th align="center" width="13%">Lot No</th>
												<th align="center" width="10%">Clearance No</th>
												<th align="center" width="10%">Closing No</th>
												<th align="center" width="10%">Tablet Produced Qty</th>
												<th align="center" width="13%">Issue No</th>
												<th align="center" width="10%">Issued By</th>
												<th align="center" width="10%">Visual Checked By</th>
												<th align="center" width="10%">Tool Return No</th>
												<th align="center" width="10%">Tool Return by</th>
												<th align="center" width="10%">Tool Clean by</th>
												<!-- <th align="center" width="10%">Issue Qty</th>
												<th align="center" width="10%">Return Qty</th>
												<th align="center" width="10">Damaged Qty</th>
 -->
											</tr>
											<c:forEach items="${toolingLotStocks}" var="lstHistory"
												varStatus="row">
												<tr id="rowid">
													<td align="center" style="text-align: center;">${lstHistory.batchNumber}</td>
													<td align="center" style="text-align: center;">${lstHistory.product}</td>
													<td align="center" style="text-align: center;">${lstHistory.lotNumber}</td>
													<td align="center" style="text-align: center;">${lstHistory.clearanceNumber}</td>
													<td align="center" style="text-align: center;">${lstHistory.closingNumber}</td>
													<td align="center" style="text-align: center;">${lstHistory.tabletProducedQty}</td>
													<td align="center" style="text-align: center;">${lstHistory.issueId}</td>
													<td align="center" style="text-align: center;">${lstHistory.issueBy}</td>
													<td align="center" style="text-align: center;">${lstHistory.visualCheckBy}</td>
													<td align="center" style="text-align: center;">${lstHistory.returnId}</td>
													<td align="center" style="text-align: center;">${lstHistory.returnBy}</td>
													<td align="center" style="text-align: center;">${lstHistory.cleanedBy}</td>
													<%-- <td align="center" style="text-align: center;">${lstHistory.issueQty}</td>
													<td align="center" style="text-align: center;">${lstHistory.returnQty}</td>
													<td align="center" style="text-align: center;">${lstHistory.damagedQty}</td> --%>
												</tr>
											</c:forEach>

										</table>
									</font>
								</div>
							</c:when>
							<c:otherwise>
								<div class="table-responsive"></div>
							</c:otherwise>
						</c:choose>
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
        const filename = 'tooling_lot_batch_report.pdf';

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
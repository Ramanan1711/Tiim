
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>Product Master Report</title>
<link rel="stylesheet" type="text/css" href="./css/semantic.css">
<link rel="stylesheet" type="text/css"
	href="css/smoothness/jquery-ui-1.8.17.custom.css">
<link rel="stylesheet" type="text/css" href="css/main.css">

<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script>

<script type="text/javascript" src="./auto/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>
<style>
.pagination {
	display: none;
}
</style>
</head>
<body>
	<%@ include file="tiimMenu.jsp"%>
	<div id='html-content' class="ui page grid">
		<div class="wide column">

			<div id="printreport" class="ui divider hidden"></div>
			<div class="ui segment">

				<form class="form">
					<table style="width: 98%">
						<tr>
							<td align="right"><input type="button" title="Click to Download"
								class="btn btnSMNormal" id="download_Btn" " value="Download PDF">
							</td>
						</tr>
					</table>
					<div id="reportbox">
						<table style="width: 98%">
							<tr>
								<td align="left" width="50%"><h2>Product Master Report</h2></td>
								<td align="right" width="50%"><img src="./images/logo.jpeg"
									style="object-fit: contain; width: 200px; height: 50px;" /></td>
							</tr>
						</table>

						<table style="width: 70%" align="left" cellpadding="3"
							cellspacing="0" border="0">
							<c:choose>
								<c:when test="${fn:length(lstProductDetail) gt 0}">
									<div class="table-responsive">
										<table class="table table-bordered" style="width: 98%"
											align="center" id="cylinderFieldData" border="1">
											<tr>

												<th align="center" width="15%">Product Name</th>
												<th align="center" width="15%">Drawing Number</th>
												<th align="center" width="15%">Punch Set No</th>
												<th align="center" width="15%">Max Comp Force Limit</th>
												<th align="center" width="10%">Shape</th>
												<th align="center" width="10%">Dimensions</th>
												<th align="center" width="10%">Embossing Upper</th>
												<th align="center" width="15%">Embossing Lower</th>

											</tr>
											<c:forEach items="${lstProductDetail}" var="lstProductDetail"
												varStatus="row">
												<tr id="rowid">
													<td align="center" style="text-align: center;">${lstProductDetail.productName}</td>
													<td align="center" style="text-align: center;">${lstProductDetail.drawingNo}</td>
													<td align="center" style="text-align: center;">${lstProductDetail.punchSetNo}</td>
													<td align="center" style="text-align: center;">${lstProductDetail.compForce}</td>
													<td align="center" style="text-align: center;">${lstProductDetail.shape}</td>
													<td align="center" style="text-align: center;">${lstProductDetail.dimensions}</td>
													<td align="center" style="text-align: center;">${lstProductDetail.embosingUpper}</td>

													<td align="center" style="text-align: center;">${lstProductDetail.embosingLower}</td>
												</tr>
											</c:forEach>

										</table>
									</div>
								</c:when>
								<c:otherwise>
									<div class="table-responsive"></div>
								</c:otherwise>
							</c:choose>

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
        const filename = 'product_report.pdf';

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


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<center>
	<c:choose>
		<c:when test="${fn:length(lstToolingIssue) gt 0}">
			<table cellpadding="0" cellspacing="0" border="0" align="center"
				width="100%">
				<tr>
					<td height="5px"></td>
				</tr>
			</table>

			<table id="lstProduct" name="lstProduct" class="tableHeading">
				<thead>
					<tr>
						<th width="10%">Lot Number</th>
						<th width="25%">Drawing Number</th>
						<th width="30%">Product Name</th>

						<th width="30%">Machine Name</th>
						<th width="25%">Type of Tooling</th>
						<th width="10%">IssueId</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${lstToolingIssue}" var="lstToolingIssue"
						varStatus="row">
						<tr>
							<td><a href="#"
								onclick="loadIssueDetails('${lstToolingIssue.toolingLotNumber1}','${lstToolingIssue.drawingNo1}','${lstToolingIssue.productName1}','${lstToolingIssue.machineName1}','${lstToolingIssue.typeOfTooling1}','${lstToolingIssue.requestQty1}','${lstToolingIssue.batchQty1}','${lstToolingIssue.issueId}')">${lstToolingIssue.toolingLotNumber1}</a></td>
							<td>${lstToolingIssue.drawingNo1}<a
								href='${lstToolingIssue.uploadPath}'>${lstToolingIssue.uploadPath}</a></td>
							<td>${lstToolingIssue.productName1}</td>
							<td>${lstToolingIssue.machineName1}</td>
							<td>${lstToolingIssue.typeOfTooling1}</td>
							<td>${lstToolingIssue.issueId}</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<center>
				<br> <br>
				<div id="listMsg" class="searchListMsg"></div>
			</center>
		</c:otherwise>
	</c:choose>
</center>
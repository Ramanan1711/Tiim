<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstToolingReceivingRequest) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstProduct" name="lstProduct" class="tableHeading">			     
		     <thead>
		          <tr>
		          	<th width="10%">Request Id</th>
		          	<th width="30%">Drawing Number</th>
			          <th width="30%">Product Name</th>
			          
			         <th width="30%">Machine Name</th>
			         <th width="30%">Type of Tooling</th>
		          </tr>  
		     </thead> 
		     <tbody>
		         <c:forEach items="${lstToolingReceivingRequest}" var="lstToolingReceivingRequest" varStatus="row">  
			          <tr>
			              <td><a title="Click to Select Request No" href="javascript:funShowRequestNo('${lstToolingReceivingRequest.toolingInspectionDetailId1}','${lstToolingReceivingRequest.inspectionReportDate}','${lstToolingReceivingRequest.drawingNo}','${lstToolingReceivingRequest.toolingname}','${lstToolingReceivingRequest.machineType}','${lstToolingReceivingRequest.typeOfTooling}','${lstToolingReceivingRequest.uom}','${lstToolingReceivingRequest.receivedQuantity}','${lstToolingReceivingRequest.lotNumber}','${lstToolingReceivingRequest.toolingInspectionId}','${lstToolingReceivingRequest.requestDate}','${lstToolingReceivingRequest.branchName}');" >${lstToolingReceivingRequest.toolingInspectionDetailId1}</a></td>
			             <td>${lstToolingReceivingRequest.drawingNo}</td>
			             <td>${lstToolingReceivingRequest.toolingname}</td>   
			             <td>${lstToolingReceivingRequest.machineType}</td><td>${lstToolingReceivingRequest.typeOfTooling}</td>
			             
			             
			         </tr>
		         </c:forEach>
		     </tbody>       
		</table>  
		</c:when>
		<c:otherwise>
				<center>
				    <br>
				    <br>
                    <div id="listMsg" class="searchListMsg">Data not found</div>
                </center>
		</c:otherwise>
</c:choose>     
</center>
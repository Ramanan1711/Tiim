<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstToolingPeriodicInspection) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstProduct" name="lstProduct" class="tableHeading">			     
		     <thead>
		          <tr>
		          	<th width="10%">Request No</th>
		          	<th width="30%">Drawing Number</th>
			          <th width="30%">Product Name</th>
			          
			         <th width="30%">Machine Name</th>
			         <th width="30%">Type of Tooling</th>
		          </tr>  
		     </thead> 
		     <tbody>
		         <c:forEach items="${lstToolingPeriodicInspection}" var="lstToolingPeriodicInspection" varStatus="row">  
			          <tr>
			              <td><a title="Click to Select Request No" href="javascript:funShowRequestNo('${lstToolingPeriodicInspection.requestDetailId}','${lstToolingPeriodicInspection.requestDate}','${lstToolingPeriodicInspection.drawingNo}','${lstToolingPeriodicInspection.productName}','${lstToolingPeriodicInspection.machineType}','${lstToolingPeriodicInspection.typeOfTool}','${lstToolingPeriodicInspection.uom}','${lstToolingPeriodicInspection.producedQuantity}','${lstToolingPeriodicInspection.lotNumber}','${lstToolingPeriodicInspection.reportNo}','${lstToolingPeriodicInspection.requestDate}','${lstToolingPeriodicInspection.branchName}');" >${lstToolingPeriodicInspection.reportNo}</a></td>
			              											
			             <td>${lstToolingPeriodicInspection.drawingNo}</td>
			             <td>${lstToolingPeriodicInspection.productName}</td>   
			             <td>${lstToolingPeriodicInspection.machineType}</td><td>${lstToolingPeriodicInspection.typeOfTool}</td>
			             
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstProduct) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstProduct" name="lstProduct" class="tableHeading">			     
		     <thead>
		          <tr>
		          	<th width="30%"> Lot Number </th>
		          	<th width="30%"> Punch Set No </th>
		          	<th width="30%">Drawing Number</th>
		          	<th width="30%">Max Comp Force Limit</th>
		          	<th width="30%">dustCup</th>
			          <th width="30%">Product Name</th>
			          
			         <th width="30%">Machine Type</th>
			         <th width="30%">Type of Tooling</th>
		          </tr>  
		     </thead> 
		     <tbody>
		         <c:forEach items="${lstProduct}" var="lstProduct" varStatus="row">  
			          <tr>
			           <td>${lstProduct.toolinglotnumber}</td>
			           <td>${lstProduct.punchSetNo}</td>
			             <td><a title="Click to Select Product Name" 
			              href="javascript:funShowDrawingNo('${lstProduct.productName}','${lstProduct.drawingNo}',
			              '${lstProduct.toolinglotnumber}','${lstProduct.punchSetNo}','${lstProduct.compForce}','${lstProduct.dustCapGroove}');" style="">${lstProduct.drawingNo}</a>  <a href='${lstProduct.uploadedPath}'>${lstProduct.uploadedPath}</a></td>
			             <td>${lstProduct.compForce}</td>
			             <td>${lstProduct.dustCapGroove}</td>
			             <td>${lstProduct.productName}</td>   
			             <td>${lstProduct.machineType}</td><td>${lstProduct.typeOfTool}</td>
			             
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
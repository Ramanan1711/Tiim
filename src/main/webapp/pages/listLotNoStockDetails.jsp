<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstStock) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstProduct" name="lstProduct" class="tableHeading">			     
		     <thead>
		          <tr>
		          	<th width="10%">Lot Number</th>
		          	<th width="30%">Drawing Number</th>
			          <th width="30%">Product Name</th>
			          
			         <th width="30%">Machine Name</th>
			         <th width="30%">Type of Tooling</th>
		          </tr>  
		     </thead> 
		     <tbody>
		         <c:forEach items="${lstStock}" var="lstStock" varStatus="row">  
			          <tr>
			              <td><a title="Click to Select Request No" href="javascript:funLotNo('${lstStock.stockId}');" >${lstStock.toolingLotNumber}</a></td>
			             <td>${lstStock.drawingNo} <a href='${lstStock.uploadPath}'>${lstStock.uploadPath}</a></td>
			             <td>${lstStock.productName}</td>   
			             <td>${lstStock.machineName}</td><td>${lstStock.typeOfTool}</td>
			             
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
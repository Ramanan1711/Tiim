<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
 <input type="hidden" id="totalRejectedQty"  name="totalRejectedQty" class="textmin" value="${totalRejectedQty}" />
<c:choose>
		<c:when test="${fn:length(lstSerialNumber) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstSerialNumber" name="lstSerialNumber" class="tableHeading">			     
		     <thead>
		          <tr>
		          	<th width="30%"> Lot Number </th>
		            <th width="30%">Action</th>
			      	<th width="30%">Rejected Serial Number</th>    
			         <th width="30%">Accepted Qty</th>
			         <th width="30%">Rejected Qty</th>
		          </tr>  
		     </thead> 
		     <tbody>
		         <c:forEach items="${lstSerialNumber}" var="serialNumber" varStatus="row">  
			          <tr>
			           <td>${serialNumber.lotNumber}</td>
			             <td>${serialNumber.module}</td>
			             <td>${serialNumber.serialNumber}</td>   
			             <td>${serialNumber.acceptQty}</td>
			             <td>${serialNumber.rejectQty}</td>
			         </tr>
		         </c:forEach>
		     </tbody>       
		</table>  
		</c:when>
		
</c:choose>     

</center>
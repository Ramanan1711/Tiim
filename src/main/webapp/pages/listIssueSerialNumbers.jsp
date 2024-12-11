<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstSerialNumber) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstProduct" name="lstProduct" class="tableHeading">			     
		     <thead>
		          <tr>
		          	<th width="10%">Serial Number</th>
		          	<th width="30%">Issue</th>
		          </tr>  
		     </thead> 
		     <tbody>
		     	<c:forEach items="${lstSerialNumber}" var="lstSerialNumber" varStatus="i">  
		     		<tr>
			             <td>${lstSerialNumber.serialNumber}<input type="hidden" value="${lstSerialNumber.serialNumber}" name="serialNumber1"/>
			             <input type="hidden" value="${lstSerialNumber.serialId}" name="serialId"/>
			             </td>
			             <td>
			             <c:choose>
				             <c:when test="${lstSerialNumber.stockFlag == 1}">
				            	 <input type="checkbox" name="approvedQty" id="approvedQty${i.index}" value="${lstSerialNumber.serialNumber}" checked> Issue
				             </c:when>
				             <c:otherwise>
				             	<input type="checkbox" name="approvedQty" id="approvedQty${i.index}" value="${lstSerialNumber.serialNumber}"> Issue
				             </c:otherwise>
				          </c:choose>

			             </td>
			             
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
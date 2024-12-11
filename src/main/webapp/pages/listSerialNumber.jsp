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
		          	<th width="30%">Approved</th>
			          <th width="30%">Rejected</th>
			         
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
				             <c:when test="${lstSerialNumber.approvedFlag == 1}">
				            	 <input type="checkbox" name="approvedQty" id="rejectQty${i.index}" class="approvedQty${i.index} approvedQty"  value="1" checked onclick="changeAttr('approvedQty${i.index}')"> Accept
				             </c:when>
				             <c:otherwise>
				             	<input type="checkbox" name="approvedQty" id="rejectQty${i.index}" class="approvedQty${i.index}"  value="1" onclick="changeAttr('approvedQty${i.index}')"> Accept
				             </c:otherwise>
				          </c:choose>
			             </td>
			             <td>
				            <c:choose>
				             <c:when test="${lstSerialNumber.approvedFlag == 2}">
				            	 <input type="checkbox" name="rejectQty" id="approvedQty${i.index}" class="rejectQty${i.index}" value="${lstSerialNumber.serialNumber}" onclick="changeAttr('rejectQty${i.index}')" checked> Reject
				             </c:when>
				             <c:otherwise>
				             	<input type="checkbox" name="rejectQty" id="approvedQty${i.index}" class="rejectQty${i.index}"  onclick="changeAttr('rejectQty${i.index}')" value="${lstSerialNumber.serialNumber}"> Reject
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
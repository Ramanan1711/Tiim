<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstToolingReceiptNote) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstProduct" name="lstProduct" class="tableHeading">			     
		     <thead>
		          <tr>
		          	<th width="10%">Grn No</th>
		          	<th width="30%">Drawing Number</th>
			          <th width="30%">Product Name</th>
			          
			         <th width="30%">Machine Name</th>
			         <th width="30%">Type of Tooling</th>
		          </tr>  
		     </thead> 
		     <tbody>
		         <c:forEach items="${lstToolingReceiptNote}" var="lstToolingReceiptNote" varStatus="row">  
			          <tr>
			              <td><a title="Click to Select GRN No" href="javascript:funShowRequestNo('${lstToolingReceiptNote.grnNo}','${lstToolingReceiptNote.grnDate}','${lstToolingReceiptNote.supplierCode}','${lstToolingReceiptNote.supplierName}','${lstToolingReceiptNote.toolingReceiptId}');" >${lstToolingReceiptNote.grnNo}</a></td>
			             <td>${lstToolingReceiptNote.drawingNo} <a href='${lstToolingReceiptNote.uploadPath}'>${lstToolingReceiptNote.uploadPath}</a></td>
			             <td>${lstToolingReceiptNote.productName}</td>   
			             <td>${lstToolingReceiptNote.machineType}</td><td>${lstToolingReceiptNote.typeOfTool}</td>
			             
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
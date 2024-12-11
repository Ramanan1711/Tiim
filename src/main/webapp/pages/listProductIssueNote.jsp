<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstToolingRequestNote) gt 0}">
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
		         <c:forEach items="${lstToolingRequestNote}" var="lstToolingRequestNote" varStatus="row">  
			          <tr>
			             <td><a title="Click to Select Product Name" href="javascript:funShowRequestNo('${lstToolingRequestNote.requestId}','${lstToolingRequestNote.originalId}');" style="">${lstToolingRequestNote.originalId}</a></td>
			             <td>${lstToolingRequestNote.drawingNo1} <a href='${lstToolingRequestNote.uploadPath}'>${lstToolingRequestNote.uploadPath}</a></td>
			             <td>${lstToolingRequestNote.productName1}</td>   
			             <td>${lstToolingRequestNote.machingType1}</td><td>${lstToolingRequestNote.typeOfTool1}</td>
			             
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
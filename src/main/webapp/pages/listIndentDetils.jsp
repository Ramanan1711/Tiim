<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstPendingIndent) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstProduct" name="lstProduct" class="tableHeading">			     
		     <thead>
		          <tr>
		          	  <th width="5%">Indent Id</th>
			          <th width="30%">Product Name</th>
			          <th width="30%">Drawing No.</th>
			          <th width="30%">Machine Name</th>
			          <th width="25%">Type of Tooling</th>
		          </tr>  
		     </thead> 
		     <tbody>
		         <c:forEach items="${lstPendingIndent}" var="lstPendingIndent" varStatus="row">  
			          <tr>
			             <td><a title="Click to Select Indent" href="javascript:funIndentId('${lstPendingIndent.indentNo}');" style="">${lstPendingIndent.indentNo}</a></td>   
			             <td>${lstPendingIndent.productName}</td><td>${lstPendingIndent.drawingNo}</td><td>${lstPendingIndent.machineTypePO}</td><td>${lstPendingIndent.typeOfTool}</td>
			             
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
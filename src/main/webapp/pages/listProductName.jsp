<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center>
<c:choose>
		<c:when test="${fn:length(lstProductDetail) gt 0}">
	        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
				 <tr>
					 <td height="5px"></td>		
				 </tr>
		    </table>
	                                
		<table id="lstProduct" name="lstProduct" class="tableHeading">			     
		     <thead>
		          <tr>
			          <th width="40%">Product Name</th>
			          <th width="30%">Drawing No.</th>
			          <th width="10%">Actions</th>
		          </tr>  
		     </thead> 
		     <tbody>
		         <c:forEach items="${lstProductDetail}" var="lstProductDetail" varStatus="row">  
			          <tr>
			             <td>${lstProductDetail.productName}</td>   
			             <td>${lstProductDetail.drawingNo}</td>
			             <td align="center">
			             	<a title="Click to Edit" href="javascript:funEditProduct('${lstProductDetail.toolingProductId}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
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
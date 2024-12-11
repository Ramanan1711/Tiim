<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>
<script type="text/javascript" src="./auto/jquery-1.7.2.js"></script>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"/>
</head>
<script>
$(document).ready(function(){		

});

function Init()
{
	setTimeout(function()
    {
		$("#loadoverlay").fadeOut();  
		$("#ajaxLoading").fadeOut();
    },350);
}
</script>
<body class="body" onload="Init()">
<form name="frmmenulist" id="frmHome" method="post">
<%@ include file="tiimMenu.jsp"%>
 <div id="loadoverlay" class="loading_overlay" ></div>
 <div id="overlay" class="web_dialog_overlay"></div>
 <img id="ajaxLoading" src="./images/ultraloading.jpg" class="loadingPosition"></img>
 
<table cellspacing="0" width="1000px"  cellpadding="5" border="0" align="center" height="5px">		      
		<tr>  
			<td></td> 		            
		</tr>
</table>
<!-- <table width="1000px" cellspacing="0" cellpadding="5" border="0" align="center">		      
	     <tr>  
	          <td width="25%" class="heading" align="left">&nbsp;Home</td> 
	          <td width="50%" class="submenutitlesmall" align="center">	             
	          </td>
	          <td align="right" width="25%" class="label"></td>                             
	     </tr>
</table> -->

<!-- <center>           
		<DIV STYLE="width: 980px;padding:0px; margin:0px;left:20px;height:350px" class="content">    
			      
				<table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
					<tr height="100px">
					<td>&nbsp;</td>
					</tr>
				</table>	
				<table style="width:96%" align="center" cellpadding="3" cellspacing="0" border="0">				     			
					<tr>
					    <td width="10%" class="popuplabel" align="left"></td>
					    <td width="25%" class="popuplabel" align="left"><input type="button" name="one" id="one" value="Masters" onclick="javascript:funMaster();" class="btn btnSTDNormal" /></td>
						<td width="3%" class="tabpopuplabel" align="left"></td>
						<td width="25%" class="popuplabel" align="left"><input type="button" name="two" id="two" value="Stores" onclick="javascript:funStores();" class="btn btnSTDNormal"/></td>
						<td width="3%" align="left"></td>
						<td width="25%" class="popuplabel" align="left"><input type="button" name="three" id="three" value="Production" class="btn btnSTDNormal" onclick="javascript:funProduction();"/></td>
						<td width="5%" align="left"></td>				        			        	        			       	      
					</tr>   
					<tr height="30px">
						<td colspan="7">&nbsp;</td>
					</tr>
					<tr>
					    <td class="popuplabel" align="left"></td>			        
						<td class="popuplabel" align="left"><input type="button" name="four" id="four" value="User" onclick="javascript:funUser();" class="btn btnSTDNormal" disabled/></td>	
						<td class="popuplabel" align="left"></td>			        
						<td class="popuplabel" align="left"><input type="button" name="five" id="five" value="Reports" onclick="javascript:funReports();" class="btn btnSTDNormal"/></td>
						<td class="popuplabel" align="left"></td>		
						<td class="popuplabel" align="left"><input type="button" name="six" id="six" value="Help" onclick="javascript:funHelp();" class="btn btnSTDNormal"/></td>
						<td class="popuplabel" align="left"></td>			        		        			       	      
					</tr> 									    
				</table>									
			</DIV>		      				     		     
		</center> -->
		
</form>
</body>
<script>
	function redirectReturn()
	{
		$("#frmHome").attr("action","addIntialToolingReturnNote.jsf");
	    $("#frmHome").submit();
	}
</script>
</html>
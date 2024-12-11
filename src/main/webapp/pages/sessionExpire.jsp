<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>
<meta http-equiv="X-UA-Compatible" content="IE=9"/> 
<link href="./css/tiim.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="./auto/jquery-1.7.2.js"></script>
</head>
<body class="body">
<form name="frmError" method="post"  id="session">
   
      <table width="1000px" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
       <tr>  
           <td width="25%" class="heading" align="left">&nbsp;Session Expired</td> 
           <td width="50%" class="submenutitlesmall" align="center"></td>                                      
       </tr>
     </table>
     <center>   
         <DIV STYLE=" overflow:auto;width: 980px; padding:0px; margin: 0px;height:425px;min-height:420px;" class="content">
         
	         <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="100px">
		           <td align="center"></td>
			   </tr>
		     </table>
	         <table width="70%" cellpadding="2" cellspacing="3" align="center">
	              <tr>
	                 <td align="center" class="formlabel">Sorry ! Session Expired, Please Login.</td>
	             </tr>	            
	             <tr><td align="center"></td></tr>
	             <tr><td align="center"></td></tr>
	             <tr><td align="center"></td></tr>
	             <tr>
	                 <td align="center"><a href="./pages/logout.jsp" class="erroranchorcontent">Click here to Login</a></td>
	             </tr>
	         </table>
	         <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="25px">
		           <td align="center"></td>
			   </tr>
		     </table>
         
         </DIV>
     </center>    
     <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="20px">
	           <td align="center"></td>
		   </tr>
	 </table> 

</form>
</body>
</html>
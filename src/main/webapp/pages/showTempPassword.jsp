<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>

<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

</head>
<body class="body">
<form name="frmForgotPassWord" method="post"  id="frmForgotPassWord">


 
    <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
		 <tr height="5px">
			<td></td>
		 </tr>
	 </table>	     
     <table width="1000px" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
	     <tr>  
	          <td width="25%" class="heading" align="left">&nbsp;Temporary Password</td> 
	          <td width="50%" class="submenutitlesmall" align="center">
	               
	          </td>
	          <td align="right" width="25%" class="anchorlabel"></td>                             
	     </tr>
	 </table>
     <center>   
         <DIV STYLE=" overflow:auto;width: 980px; padding:0px; margin: 0px;height:425px;min-height:420px;" class="content">
 				 <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="40px">
		           <td align="center"></td>
			   </tr>
		     </table>
		     
		     <table width="100%" cellpadding="4" cellspacing="2" align="center" border="0">
			    <tr>
			       <td width="55%" align="left">
			             
			            <table border="0" cellspacing="0" width="80%" cellpadding="0" id="initial" align="center">
						   <tr>
						       <td width="55%" align="left" valign="top" class="infoheader">Temporary passowrd - ${password1}</td>						      
					       </tr>
					       <tr height="50px;">
					          <td width="55%" align="left" class="screenlabel">To reset your password, <br> <br>please change your temporary password.</td>					         
					       </tr>	
					       <tr height="10px;">
					           <td></td>
					       </tr>				      
					      
					       <tr>
					          <td align="left" class="screenlabel">Click to&nbsp;&nbsp;<a title="Click to Login" class="homeLink" href="loginForm.jsf">Login</a>&nbsp;&nbsp;here.</td>
					       </tr>
					       <tr height="50px;">
					           <td></td>
					       </tr>
					    </table>	
			       </td>
			     </tr>
			</table>     
			     
         </DIV>
     </center>
     
     <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
		    <tr height="20px">
	           <td></td>
		   </tr>
	 </table> 
 
 </form>
 
</body>
</html>
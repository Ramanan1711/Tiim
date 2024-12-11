<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%
response.addHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
response.setDateHeader("Expires",0);
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>

<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<link rel="stylesheet" href="./css/passwordstrength.css" type="text/css" media="screen" />

<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 
<script type="text/javascript" src="./js/passwordstrength.js"></script>


<script>

 $(document).ready(function ()
 {  
	 $(document).keydown(function(e){    			   		 
   		if (e.keyCode === 8) { ///This prevent back page  
	        var element = e.target.nodeName.toLowerCase();
	        if ((element != 'input' && element != 'textarea') || $(e.target).attr("readonly")) {
	            return false;
	        }
	     }
   	 });
   	 
	 $("#username").keyup(function(e){   
		 removemsg();
	 });

	 $("#crpnewuserpassword").keyup(function(e){    		 
        var newPassword = trim($("#crpnewuserpassword").val()); 			 
		 if(newPassword == "")
	     {             
			 $('#result').empty(); 
	     }	 
		 removemsg();
	 });
	
	 $("#crpnewuserpassword").keydown(function(e){    	
		
	 });

	 $("#confirmNewPassword").keyup(function(e){  
		// $("#result").empty();  
		 removemsg();
	 });

	 
	 $("#cancel").click(function(event){               
		 removemsg();
		 $("#frmChangePassWord").attr("action","./home.jsf");                 
		 $("#frmChangePassWord").submit();
	 });
	 
	 $("#changePassword").click(function(event)                  
	 {	
		 changePasswordProcess();		 
	 });

	
	  $("#confirmNewPassword").keyup(function(e)              
	  {	
		  var keycode =  e.keyCode ? e.keyCode : e.which;
		  if(keycode == 13)
	      {	
			  changePasswordProcess();
	      }
	  });

	  $("#msgclose").click(function()                        // Close the Message
	  {				    	
		 $("#showMsg").fadeOut(300);
		 $("#spMsg").empty();
		 $("#username").focus();		  	         
	  }); 
	  
 });


 function changePasswordProcess()
 {
	 var alertCrntPwd = "Username is required";
	 var invalidPwd = "Current Password is wrong";
     var alertNewPwd  = "New Password is required";
     var alertConPwd  = "Confirm New Password  is required";

  	// var userName = $("#hidUsername").val();
     var userName = trim($("#username").val());
     var hidPwd = trim($("#hidPassword").val()); 
     var newPassword = trim($("#crpnewuserpassword").val()); 
     var conPassword = trim($("#confirmNewPassword").val()); 		 	    	  
     $("#pswd_info").removeClass("block");
	 $("#pswd_info").addClass("none");
     if(userName == "")
      {                
         if($("#msg").text() == "")
          {
         	 $("#msg").append(alertCrntPwd);
          }
     	 $("#username").focus();
     	 return false; 
      }	
         
     if(newPassword == "")
      {                
         if($("#msg").text() == "")
          {
         	 $("#msg").append(alertNewPwd);
          }
     	 $("#crpnewuserpassword").focus();
     	 return false; 
      }	  
     if(conPassword == "")
      {                
         if($("#msg").text() == "")
          {
         	 $("#msg").append(alertConPwd);
          }
     	 $("#confirmNewPassword").focus();
     	 return false; 
      }	
     if(newPassword != conPassword)
      {                
         if($("#msg").text() == "")
          {
         	 $("#msg").append("Password do not Match");
          }
     	 $("#confirmNewPassword").focus();
     	 return false; 
      }	
     else if(newPassword.toLowerCase().indexOf(userName) >= 0)
     {
    	 if($("#msg").text() == "")
         {
        	 $("#msg").append("Password should not have Username");
         }
     }
     else if($('#result').text() == 'Strong'){
    	 $("#frmChangePassWord").attr("action","unLockAccount.jsf");                 
    	 $("#frmChangePassWord").submit();
     }
     else
     {
    	 $("#pswd_info").removeClass("none");
    	 $("#pswd_info").addClass("block");
     }
 }
 
 function init()
 {
	  $("#loadoverlay").fadeOut();  
	  $("#initialloading").fadeOut(); 
	  $("#result").empty(); 

	  if($("#spMsg").text() != "")                       //to show the popup while data already exists
	  {     
			showmsg();			    
	  }
	  $("#username").val(""); 
	  $("#username").focus(); 
 }

 function showmsg()
 {	   
	 $("#showMsg").fadeIn(300);	   
 }
 
 function removemsg()                          //common function for remove message
 {	    
	 $("#msg").empty();		

	 $("#showMsg").fadeOut(300);
	 $("#spMsg").empty();			
 }

 function trim(stringTrim)
 {
	 return stringTrim.replace(/^\s+|\s+$/g,"");
 }

 
</script>

</head>
<body class="body" onload="init();">
<form name="frmChangePassWord" method="post"  id="frmChangePassWord">
<%@ include file="tiimMenu.jsp"%>

 <div id="loadoverlay" class="loading_overlay" ></div>
 <div id="initialloading" style=" position: absolute; z-index: 700;top:105px; left: 405px; width: 450px; height: 50px; background-color:#ffffff;">
          <img  src="./images/loadmain.gif"></img>
 </div>
 
      <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
		 <tr height="15px">
			<td></td>
		 </tr>
	  </table>	     
     <table width="1000px" cellspacing="0" cellpadding="5" border="0" align="center"  height="30px">		      
	     <tr>  
	          <td width="25%" class="heading" align="left">&nbsp;Unlock User Account</td> 
	          <td width="50%" class="submenutitlesmall" align="center">
	               <table cellspacing="1" cellpadding="2" width="50%" align="center" style="display:none;" id="showMsg">	
			     	<tr>  
				    	<td class="confirmationmessage" align="center">&nbsp;<span id="spMsg">${errorMessage}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="popupanchor" id="msgclose">X</a></td>
				    </tr>
			    </table>
	          </td>
	          <td align="right" width="25%" class="anchorlabel"></td>                             
	     </tr>
	 </table>
     <center>   
         <DIV STYLE=" width: 980px; padding:0px; margin: 0px;height:435px;min-height:435px;" class="content">
         
	         <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="40px">
		           <td align="center"></td>
			   </tr>
		     </table>
		     
		     <table width="100%" cellpadding="4" cellspacing="2" align="center" border="0">
			    <tr>
			       <td width="55%" align="left">
			             
			            <table border="0" cellspacing="0" width="80%" cellpadding="0"  align="center">
						   <tr>
						       <td width="55%" align="left" valign="top" class="infoheader">Change Your Password to unlock</td>						      
					       </tr>
					       <tr height="90px;">
					          <td width="55%" align="left" class="screenlabel">&nbsp;Enter a new password for&nbsp;-&nbsp;<span style="color:#C20100;" >${userName}</span>.</td>					         
					       </tr>
					       <tr height="20px;">
					           <td>
					           <div class="none" id="pswd_info"  style="color:#C20100;">
								    <h4>Password must meet the following requirements:</h4>
								    <ul>
								        <li id="letter" class="invalid">At least <strong>one letter</strong></li>
								        <li id="capital" class="invalid">At least <strong>one capital letter</strong></li>
								        <li id="number" class="invalid">At least <strong>one number</strong></li>
								        <li id="length" class="invalid">Be at least <strong>8 characters</strong></li>
								        <li id="length" class="invalid">At least <strong>one special characters</strong></li>
								    </ul>
								</div>
					           </td>
					       </tr>
					       <tr height="20px;">
					           <td></td>
					       </tr>
					       <tr height="90px;">
					           <td></td>
					       </tr>
					    </table>
			       
			       </td>
			       <td width="45%" align="left">	     
					     <table border="0" cellspacing="0" width="85%" cellpadding="0"  align="center" style="background-color:#F6F6F6; border: 1px solid #DDD;">
						   <tr height="20px;">
						       <td width="10%" align="center" valign="top" class="tabpopuplabel"></td>
						       <td width="75%" align="left">
					       </tr>
					       <tr>
					           <td align="center" valign="top" class="tabpopuplabel"></td>
						       <td align="left">
						           <table width="100%" cellpadding="4" cellspacing="2" border="0" align="center" style="background-color:#F6F6F6;" >
						               <tr height="30px">
						                  <td width="100%" align="center" style="background-color:#F6F6F6;" class="popuptopalert"><span id="msg"></span></td>	              
						              </tr>
						              <tr>
						                  <td  align="left" class="grayBgLabel">User Name&nbsp;<span class="grayBgLabelMandatory">*</span></td>	              
						              </tr>	   
						              <tr>
						                 <td align="left" class="popuplabel"><input type="text" name="username" class="textavg" id="username" value="${userName}" maxlength="50"/></td>
						              </tr> 
						              <tr height="10px">
						                 <td align="left" class="popuplabel"></td>
						              </tr>
						              <tr>
						                  <td align="left" class="grayBgLabel">New Password&nbsp;<span class="grayBgLabelMandatory">*</span></td>	              
						              </tr>	
						              <tr>
						                 <td align="left" class="popuplabel"><input type="password" name="crpnewuserpassword" class="textavg" id="crpnewuserpassword" value="" maxlength="20"/><span id="result"></span></td>
						              </tr> 
						              <tr height="10px">
						                 <td align="left" class="popuplabel"></td>
						              </tr>
						              <tr>
						                  <td align="left" class="grayBgLabel">Confirm New Password&nbsp;<span class="grayBgLabelMandatory">*</span></td>	              
						              </tr>    
						              <tr>
						                 <td align="left" class="popuplabel"><input type="password" name="confirmNewPassword" class="textavg" id="confirmNewPassword" value="" maxlength="20"/></td>
						              </tr> 
						              <tr height="10px">
						                 <td align="left" class="popuplabel"></td>
						              </tr>  						               
						         </table>							          
				              </td>
						       <td width="15%" align="left"></td>
						   </tr>
						   <tr height="20px;">
						       <td align="left" class="popuplabel"></td>
						       <td align="left" class="popuplabel"></td>
						   </tr>
						</table>
						<table width="88%" cellpadding="1" cellspacing="2" align="center" border="0">
						<tr height="15px">
						<td width="88%">&nbsp;</td>
						</tr>
				              <tr>
				                 <td align="right" class="popuplabel"><input type="button" name="changePassword" class="btn btnImportant" id="changePassword" value="Change Password" />&nbsp;&nbsp;<input type="button" name="cancel" class="btn btnCancel" id="cancel" value="Cancel"/></td>
				              </tr>					         
						</table>
					</td>	
	     		   </tr>      
	     	</table>	         
	     		         
	     		         
	         <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
			    <tr height="40px">
		           <td align="center"></td>
			   </tr>
		     </table>
         		<input type="hidden" name="hidPassword"  id="hidPassword" value="${password}"/>
         		<input type="hidden" name="hidUsername"  id="hidUsername" value="${userName}"/>
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
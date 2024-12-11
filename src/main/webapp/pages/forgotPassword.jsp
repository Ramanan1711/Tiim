<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tiim</title>

<link href="./css/tiim.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script> 

<script>

	$(document).ready(function ()
	{	
  		
		$("#btnForgot").click(function()                     
	    {	 
			var question1 = $.trim($('#question1').val());
			var question2 = $.trim($('#question2').val());
			
			if(question1 == '')
			{
				$("#question1").attr("placeholder","Please answer this question");
				alert("Please enter answer1");
				$("#question1").focus();
				return false;
			}
			else if(question2 == "")
			{
				$("#question2").attr("placeholder","Please answer this question");
				alert("Please enter answer1");
				$("#question2").focus();
				return false;
			}
			else
			{
				$("#frmForgotPassWord").attr("action","verifySecurityQuestion.jsf");               
			    $("#frmForgotPassWord").submit();
			}
	    });

		/* $("#emailAddress").keyup(function(event)                         
	    {
			var keycode = event.keyCode ? event.keyCode : event.which;		   
			removeMsg();		    		    	    
		    if(keycode == 13){ // Enter
		    	funEmailValidation();
		    }
		     
	    });
 */

		$("#msgclose").click(function()                        // Close the Message
		  {				    	
			 $("#showMsg").fadeOut(300);
			 $("#spMsg").empty();
			 $("#emailAddress").focus();		  	         
		  });
		  
	});


function init()
{
  $("#loadoverlay").fadeOut();  
  $("#initialloading").fadeOut(); 

  var msg = trim($("#spMsg").text());
  
  if(msg != "")                       //to show the popup while data already exists
  {     
     if(msg == "Mail Sent Successfully")
     {
    	 showmsg();
    	 $("#success").show();    
    	 $("#initial").hide(); 
     }
     else
     {	  
    	 showmsg();
     }			    
  }
  
  $("#emailAddress").focus();
}

function funEmailValidation()
{
	var emailId = trim($("#emailAddress").val());
	var alertmsg = "Enter a Email Id";
	if(emailId == "")
	{
		$("#alert").text(alertmsg);
        $("#emailAddress").focus();
        return false;
	}
	
	if(emailId != "")
    {
          //check weather entered email id is correct or not//
          var re = /^[\w-]+((\_)?)[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;	         
          if(trim(emailId)!="" && !trim(emailId).match(re))
          {	        	  	        	  
        	var alertmsg = "Enter a valid Email Id";	        	                   
            $("#alert").text(alertmsg);
            $("#emailAddress").focus();
            return false;
          } 
    }
    
	$("#frmForgotPassWord").attr("action","forgotPassword.jsf");               
    $("#frmForgotPassWord").submit();
}

function showmsg()
{	   
	 $("#showMsg").fadeIn(300);	   
}


function removeMsg()
{
	$("#alert").empty();
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
<form name="frmForgotPassWord" method="post"  id="frmForgotPassWord">

 <div id="loadoverlay" class="loading_overlay" ></div>
 <div id="initialloading" style=" position: absolute; z-index: 700;top:105px; left: 405px; width: 450px; height: 50px; background-color:#ffffff;">
          <img  src="./images/loadmain.gif"></img>
 </div>
 
    <table class="spacerwht"  border="0" cellspacing="0" cellpadding="0"  align="center">
		 <tr height="5px">
			<td></td>
		 </tr>
	 </table>	     
     <table width="1000px" cellspacing="0" cellpadding="5" border="0" align="center"  height="25px">		      
	     <tr>  
	          <td width="25%" class="heading" align="left">&nbsp;Forgot Password</td> 
	          <td width="50%" class="submenutitlesmall" align="center">
	               <table cellspacing="1" cellpadding="2" width="auto" align="center" style="display:none;" id="showMsg">	
			     	<tr>  
				    	<td class="confirmationmessage" align="center">&nbsp;<span id="spMsg">&nbsp;&nbsp;&nbsp;${dynMsg}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="popupanchor" id="msgclose">X</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    </tr>
			    </table>
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
						       <td width="55%" align="left" valign="top" class="infoheader">Forgot your password?</td>						      
					       </tr>
					       <tr height="50px;">
					          <td width="55%" align="left" class="screenlabel">To reset your password, <br> <br>please answer the following questions.</td>					         
					       </tr>	
					       <tr height="10px;">
					           <td></td>
					       </tr>				      
					        <tr height="15px;">
					           <td align="left" class="formlabelblack">User Name</td>
					       </tr>
					       <tr height="10px;">
					           <td></td>
					       </tr>					      
			               <tr>
			                 <td align="left" class="popuplabel"><input type="text" name="userName" class="textavg" id="userName"  maxlength="100"/>&nbsp;&nbsp;<span class="popuptopalert" id="alert"></span></td>
			                
			               </tr>
			               <tr height="10px;">
					           <td></td>
					       </tr>
					       <tr height="15px;">
					           <td align="left" class="formlabelblack">${question1}</td>
					       </tr>
					       <tr height="10px;">
					           <td></td>
					       </tr>					      
			               <tr>
			                 <td align="left" class="popuplabel"><input type="text" name="question1" class="textavg" id="question1"  maxlength="100"/>&nbsp;&nbsp;<span class="popuptopalert" id="alert"></span></td>
			                
			               </tr>
			               <tr height="10px;">
					           <td></td>
					       </tr>
					        <tr height="15px;">
					           <td align="left" class="formlabelblack">${question2}</td>
					       </tr>
					       <tr height="10px;">
					           <td></td>
					       </tr>					      
			               <tr>
			                 <td align="left" class="popuplabel"><input type="text" name="question2" class="textavg" id="question2"  maxlength="100"/>&nbsp;&nbsp;<span class="popuptopalert" id="alert"></span></td>
			                
			               </tr>
			               <tr height="10px;">
					           <td></td>
					       </tr>
			                <tr height="20px;">
					           <td align="left" class="popuplabel"><input type="button" name="btnForgot" id="btnForgot" value="Submit" class="orangesmall"/></td>
					       </tr>
					       <tr height="10px;">
					           <td></td>
					       </tr>
					       <tr>
					          <td align="left" class="screenlabel">If you remember your password, Click to&nbsp;&nbsp;<a title="Click to Login" class="homeLink" href="loginForm.jsf">Login</a>&nbsp;&nbsp;here.</td>
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
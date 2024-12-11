<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<style>
#body {
background-color: #f8f9fa;
color: #5a5656;
font-family: 'Open Sans', Arial, Helvetica, sans-serif;
font-size: 16px;
line-height: 1.5em;
}
a { text-decoration: none; }
h1 { font-size: 1em; }
h1, p {
margin-bottom: 10px;
}
strong {
font-weight: bold;
}
.uppercase { text-transform: uppercase; }
#login {
margin: 50px auto;
width: 300px;
font-family: "Lato","Helvetica Neue","Helvetica","Roboto","Arial",sans-serif;
backgroung-color:#FFFFFF;
}



#header {
    background-color:#f3c859;
    color:white;
    text-align:left;
    padding:5px;
    height: 60px;
    margin: -8px;
    font-family: "Lato","Helvetica Neue","Helvetica","Roboto","Arial",sans-serif;
}
#header2 {
    background-color:#E9EAEA;
    height:120px;
    margin:-8px;
    margin-button:-8px;
	font-family: "Lato","Helvetica Neue","Helvetica","Roboto","Arial",sans-serif;
}

#footer {
    background-color: #E9EAEA;
    color: #9498a1;
    text-align:center;
    padding:5px;
    font-size:12px;
    height: 110px;
    margin: -8px;
    font-family: "Lato","Helvetica Neue","Helvetica","Roboto","Arial",sans-serif;	 
}

form fieldset input[type="text"], input[type="password"] {
background-color: #e5e5e5;
border: none;
border-radius: 3px;
-moz-border-radius: 3px;
-webkit-border-radius: 3px;
color: #5a5656;
font-family: 'Open Sans', Arial, Helvetica, sans-serif;
font-size: 14px;
height: 50px;
outline: none;
padding: 0px 10px;
width: 280px;
-webkit-appearance:none;
}

form fieldset input[type="submit"], input[type="button"]{
background-color: #008dde;
border: none;
border-radius: 3px;
-moz-border-radius: 3px;
-webkit-border-radius: 3px;
color: #f4f4f4;
cursor: pointer;
font-family: 'Open Sans', Arial, Helvetica, sans-serif;
height: 50px;
width: 300px;
-webkit-appearance:none;
}

lable{
background-color: #008dde;
border: none;
border-radius: 3px;
-moz-border-radius: 3px;
-webkit-border-radius: 3px;
color: #f4f4f4;
cursor: pointer;
font-family: 'Open Sans', Arial, Helvetica, sans-serif;
height: 50px;
text-transform: uppercase;
width: 300px;
-webkit-appearance:none;
}

form fieldset a {
color: #5a5656;
font-size: 10px;
}

form fieldset a:hover { text-decoration: underline; }
.btn-round {
background-color: #FFFFFF;
border-radius: 50%;
-moz-border-radius: 50%;
-webkit-border-radius: 50%;
color: #f4f4f4;
display: block;
font-size: 12px;
height: 50px;
line-height: 50px;
margin: 30px 125px;
text-align: center;
text-transform: uppercase;
width: 50px;
}

#dis
{
text-align:center;
height: 25px;
width: 250px;
background-color:#46DAFF;
color:#000;
}
</style>
</head>
<script type="text/javascript" src="./js/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function()
{
	var passwordLock = $("#passwordLock").val();
	
	$('#submitButton').click(function() 
	{
		$("#message").hide();
		var user = $.trim($('#username').val());
		var pass = $.trim($('#password').val());
		
		if(user == '')
		{
			$("#username").attr("placeholder","Username Required");
			$("#message").val("Enter Username");
			$("#message").show();
			$("#username").focus();
			return false;
		}
		else if(pass == "")
		{
			$("#password").attr("placeholder","Password Required");
			$("#message").val("Enter Password");
			$("#message").show();
			$("#password").focus();
			return false;
		}else if(passwordLock != null && parseInt(passwordLock) == 5)
		{
			alert('Your account got locked');
		}
		else
		{
			$("#login").attr("action","./login.jsf");
			$("#login").submit();
		}
	}); 
	
	$('#username').keypress(function(event) {
		$("#message").hide();
		if(event.which == 13)
		{
		  event.preventDefault();
		}
	 });
	
	$('#password').keypress(function(event) {
		 $("#message").hide();
		 if(event.which == 13)
			{
			  event.preventDefault();
			}
	 });
	
	 $('#username').keyup(function() {
		 if(event.which == 13)
		 {
			    $("#message").hide();
				var user = $.trim($('#username').val());
				var pass = $.trim($('#password').val());
				
				if(user == '')
				{
					$("#username").attr("placeholder","Username Required");
					$("#message").val("Enter Username");
					$("#message").show();
					$("#username").focus();
					return false;
				}
				else if(pass == "")
				{
					$("#password").focus();
					return false;
				}
				
		 }
	 });
	 
	 $('#password').keyup(function(event) {
		 var passwordLock = $("#passwordLock").val();
			
		 if(event.which == 13)
		 {
			 $("#message").hide();
				var user = $.trim($('#username').val());
				var pass = $.trim($('#password').val());
				
				if(user == '')
				{
					$("#username").attr("placeholder","Username Required");
					$("#message").val("Enter Username");
					$("#message").show();
					$("#username").focus();
					return false;
				}
				else if(pass == "")
				{
					$("#password").attr("placeholder","Password Required");
					$("#message").val("Enter Password");
					$("#message").show();
					$("#password").focus();
					return false;
				}else if(passwordLock != null && parseInt(passwordLock) == 5)
				{
					alert('Your account got locked');
				}
				else
				{
					$("#login").attr("action","./login.jsf");
					$("#login").submit();
				}
		 }
	 });
}); 

function funForgotPassword()
{
	$("#login").attr("action","./forgotPasswordForm.jsf");
	$("#login").submit();
}

/* function funChangePassword()
{
	$("#login").attr("action","./changePasswordForm.jsf");
	$("#login").submit();
} */

function Init()
{
	var passwordLock = $("#passwordLock").val();
	if(passwordLock != null)
	{
		if(parseInt(passwordLock) == 3)
		{
			alert('Continuously 5 times enter wrong password, it will lock');
		}
	}
	var msg = '${errorMessage}';
	if((msg == "Invalid username or password") || (msg == "User account got locked"))
	{
		$("#message").show();	
	}
}
</script>
<body onload="Init();">

<header id="header">
&nbsp;
</header>

<div id="header2">
	<table style="broder:1;width:100%">
		<tr>
			<td width="10%">&nbsp;</td>
			<td width="40%">&nbsp;</td>
			<td width="40%">&nbsp;</td>
			<td width="10%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><img src="./images/key-icon.png" style="width:48px;height:48px;"></td>
			<td>LOGIN TO TIIM 100</td>
			<td align="right" style="color: #6B3636;font-weight:bold;font-size:22px"></td>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>

<form method="post" id="login" autocomplet="off">
	<div id="login">
		<h1><strong>Login</strong></h1>
		<fieldset>
			<p><input type="text" name="username" id="username" placeholder="Username" autofocus></p>
			<p><input type="password" name="password" id="password" placeholder="password"></p>
			<p><a href="javascript:funForgotPassword();" style="text-decoration:none;">Forgot Password?</a><!-- <a href="javascript:funChangePassword();" style="float:right;color:#008dde;text-decoration:none;">Change Password</a> --></p>
			<p><input type="button" value="LOGIN" name="submitButton" id="submitButton"></p>
			<p><input type="button" id="message" name="message" value="${errorMessage}" style="background:red; display:none">
			<input type="hidden" id="passwordLock" value="${passwordlock}"/>
			</p>
		</fieldset>
	</div>
</form>

<footer id="footer"><br><br><br><br>Copyright &copy; 2021</footer>
</body>
</html>
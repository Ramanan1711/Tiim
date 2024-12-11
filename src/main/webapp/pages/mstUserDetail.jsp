<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<title>Tiim</title>
<meta http-equiv="X-UA-Compatible" content="IE=9"/> 

<script type="text/javascript" src="./auto/jquery-1.7.2.js"></script>
<link href="./css/tiim.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="./js/jquery.fixheadertable.js"></script>
<link rel="stylesheet" type="text/css" href="./css/base.css" />
<link rel="stylesheet" type="text/css" href="./css/jquery-ui-1.8.4.custom.css" />
<script type="text/javascript" src="./js/passwordstrength.js"></script>
<link rel="stylesheet" href="./css/passwordstrength.css" type="text/css" media="screen" />
<style>
.pagination 
{  
  display:none;
}
</style>
<script>
$("#techAlert").empty();
$("#userName").val("");  

   $(document).ready(function ()
    {	  	
		   $('.tableHeading').fixheadertable({
	       	   caption     : '', 
	           colratio    : [150, 350, 350, 120], 
	           height      : 350, 
	           width       : 970,             
	           sortable    : true, 
	           sortedColId : 0,    
	           unSortColumn: ['Actions'],     
	           resizeCol   : true,            
	           minColWidth : 50,
	           whiteSpace	 : 'nowrap',
	           sortType    : ['string', 'string', 'string', 'string']          
	      });  
		   $("body .ui-widget-content").css("overflow-x","hidden"); 
	                 
	      $("#btnpop").click(function ()              
	      {			      	    	  
	    	  if($("#dynmsg").text() != "")
			  {
			    removemsg();
			  } 	    	
	    	 $("#techAlert").empty(); 
	    	 $("#userName").val(""); 
	    	 $("#password").val(""); 	
	    	 $("#role").val("");
	    	 $("#firstName").val("");
	    	 $("#lastName").val("");
	         ShowDialog(); 
	         $("#btnprocess").val("Save");	
	         $("#userName").focus();  	           
	      });
	      
	      $("#btnpopup").click(function ()        
 	      {		    	
	    	 if($("#dynmsg").text() != "")
			 {
			    removemsg();
			 }  		    	
	    	 $("#techAlert").empty();  	
	    	 $("#userName").val(""); 
	    	 $("#password").val("");	   
	    	 $("#role").val("");
	    	 $("#firstName").val("");
	    	 $("#lastName").val("");
 	         ShowDialog();	
 	         $("#btnprocess").val("Save");
 	         $("#userName").focus();                 
 	      });
	     	      
	      $("#msgclose").click(function()            
		  {				    	
		  	  $("#msg").fadeOut(300);		  	         
		  }); 
  	       
	      $("#userName").keyup(function()           
	      {
		    	if(trim($("#userName").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	      }) ;
	      
	      $("#userName").keyup(function()             
   	      {   		    	
   		    	if(trim($("#userName").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      }) ;

	      $("#role").keyup(function()             
   	      {   		    	
   		    	if(trim($("#role").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      }) ;

	      $("#role").keypress(function(event) {        
	    	  if(event.which == 13 ) 
		       {	    		  
	    		 var alertmsg = "Username is required";
	 	       	 var fldtech =  trim($("#userName").val());
	 	       	 var password = trim($("#password").val());
	 	       	 var role = trim($("#role").val());
	 	       	 var process = $("#btnprocess").val();        	        	    
	              if(fldtech == "")
	              {                                        	 
	                  if($("#techAlert").text() == "")
	                  {
	                 	 $("#techAlert").append(alertmsg);
	                  }
	             	  $("#userName").focus();
	             	  return false;
	              }	
	              else if(password == "")
	              {
	            	  alertmsg = "Password is required";
	            	  $("#techAlert").text(alertmsg);
	                  $("#password").focus();
	             	  return false;
	              }
	              else if(role == "")
	              {
	            	  alertmsg = "role is required";
	            	  $("#techAlert").text(alertmsg);
	                  $("#role").focus();
	             	  return false;
	              }
	              else
	              {
	             	 $("#techAlert").empty();  
	              }    	             
	              if(process == "Save")
	              {	             	 
	                  $("#action").val("Save"); 
	              }  
	              else if(process == "Update")
	              {
	             	 $("#action").val("Update"); 
	              }            	                  
	    	      HideDialog();	

	    	      $("#loadoverlay").fadeIn();  
	    	  	  $("#processloading").fadeIn();
	    	  	       	    
	    	      $("#firstName").focus();            
		    	  
	    	     event.preventDefault();
	    	   }	    	       	   	    
	    	});

	    	                     	      
	      $("#btnstatclose").click(function ()             
   	      {		    	   	  	    			    		    	
	    	  hideStatusDialog();
   	      });  

	      $("#btnstatno").click(function ()                 
   	      {		    	   	    	  	    			    		    	
	    	  hideStatusDialog();
   	      });  		  
   	      
      	  $("#btndelclose").click(function ()      
   	      {		    	   	    	  	    			    		    	
      		  hideDeleteDialog();
   	      });  

	      $("#btndelno").click(function ()      
   	      {		    	   	    	  	    			    		    	
	    	  hideDeleteDialog();
   	      });  		  

   	      $("#btndelyes").click(function ()         
   	      {		    	   	    	  	    			    		    		    	  
	    	  $("#action").val("Delete");                    	                  
	    	  hideDeleteDialog();	 

	    	  $("#loadoverlay").fadeIn();  
	   	      $("#processloading").fadeIn();
	   	   
	    	  $("#frmUserDetail").attr("action","deleteUserDetails.jsf");   	                
	          $("#frmUserDetail").submit(); 
   	      });  	        
   	      
   	   $("#btnProcessyes").click(function ()      
  	   	      {		  
  					hideAllListDialog();	
  					funAllProcessList();
  	   	      });
   	   $("#btnProcessclose").click(function ()  
      	      {		    	   	    	  	    			    		    	
  					hideAllListDialog();
  					$("#searchUserDetail").focus();
      	      });  
   	    $("#btnProcessno").click(function ()      
      	      {		    	   	    	  	    			    		    	
  	    			hideAllListDialog();	
  	    			$("#searchUserDetail").focus();
      	      });   	
   	   $("#searchUserDetail").keypress(function(e){
 	 	  	  if(e.which == 13)
 	 	  	  {	 	  		
 	 	  		 return funAllList();
 	 	  	  }     	  	 
 	         });
   		$("#btnSearch").click(function (){  	  		
 			 	return funAllList(); 	           
 		     }); 
   });

  function mstEmpCancelClose()             
  {	    	  
	var process = $("#btnprocess").val(); 
	$("#techAlert").empty(); 
	$("#userName").val(""); 
	$("#password").val("");
	$("#role").val("");
	if(process == "Save")
    {
 		$("#btnprocess").val("Save");
    }  
    else if(process == "Update")
    {
     	 $("#btnprocess").val("Update");
    } 
	if($("#dynmsg").text() != "")
	{
	 removemsg();
	}
	HideDialog();	         
  }
		      
  function funTypeuserName(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "User Name is required";
		   var userName =  trim($("#userName").val());
		   if(userName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#userName").focus();
	      	   return false;
	       }
		   else
		   {
			   $("#password").focus();
		   }
	   }
  }
  
  function funTypepassword(evt,obj)
  {
	 
	  $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	  
	   if(charCode == 13)
	   {
		   $("#role").focus().select();
		/*    var alertmsg = "UserName is required";
		   
		   var userName =  trim($("#userName").val());
		   var password1 = trim($("#password").val());
		   
		 //  var role =  trim($("#role").val());
		   if(userName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#userName").focus();
	      	   return false;
	       }
		   else if(password1 == "")
		   {
			   alertmsg = "Password is required";
			   $("#techAlert").text(alertmsg);
			   $("#password").focus();
			   return false;
		   }
		   else
		   {
			   
			   $("#role").focus().select();
		   } */
	   }
  }
	
  function funTyperole(evt,obj)
  {
	 
	  $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	  
	   if(charCode == 13)
	   {
		   $("#firstName").focus();
	   }
  }
	
  function funTypeFirstName(evt,obj)
  {
	 
	  $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	  
	   if(charCode == 13)
	   {
		   $("#lastName").focus();
	   }
  }
  
  function funTypeLastName(evt,obj)
  {
	 
	  $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	  
	   if(charCode == 13)
	   {
		   $("#branchName").focus();
	   }
  }
  function processUserDetail()                   
  {  	    	  	       	
	 var alertmsg = "User Name is required";
	 var userName =  trim($("#userName").val());
	 var password = trim($("#password").val());
	 var role = trim($("#role").val());	 
	 var firstName = trim($("#firstName").val());	 
	 var lastName = trim($("#lastName").val());
	 var branchName = trim($("#branchName").val());
	 var process = $("#btnprocess").val();
	 var email = $("#email").val();

	 $("#action").val("");  	 	       	        	    
     if(userName == "")
     {                                        	 
         if($("#techAlert").text() == "")
         {
        	 $("#techAlert").append(alertmsg);
         }
    	 $("#userName").focus();
    	 return false;
     }	else if (!userName.match(/([a-z])|([A-Z])/)) 
    {
    	 alertmsg = "UserName should have ateleast one character";
       	$("#techAlert").text(alertmsg);
    	 $("#userName").focus();
    	 return false;
     }
     else if(password == "")
     {
   	  	alertmsg = "Password is required";
      	$("#techAlert").text(alertmsg);
    	$("#password").focus();
    	return false;
     }
     else if(role == "")
     {
   	    alertmsg = "role is required";
   	    $("#techAlert").text(alertmsg);
        $("#role").focus();
    	return false;
     }else if(firstName == "")
     {
    	 alertmsg = "First Name is required";
    	 $("#techAlert").text(alertmsg);
         $("#firstName").focus();
     	return false;
      }else if(lastName == "")
      {
     	 alertmsg = "Last Name is required";
     	 $("#techAlert").text(alertmsg);
          $("#lastName").focus();
      	return false;
       }else if(branchName == "")
       {
       	 alertmsg = "Branch Name is required";
       	 $("#techAlert").text(alertmsg);
            $("#branchName").focus();
        	return false;
       }else if(email == "")
       {
         	 alertmsg = "Email is required";
         	 $("#techAlert").text(alertmsg);
              $("#email").focus();
          	return false;
         }
       else if(password.toLowerCase().indexOf(userName) >= 0)
       {
    	  alertmsg = "Password should not have Username";
          $("#techAlert").text(alertmsg);
          return false;
       }else if($('#result').text() != 'Strong')
    	   {
	    	  $("#pswd_info").removeClass("none");
	      	 $("#pswd_info").addClass("block");
	      	 return false;
    	   }
     else
     {
    	 $("#techAlert").empty();  
     }                
     HideDialog();

     $("#loadoverlay").fadeIn();  
     $("#processloading").fadeIn();
    
    	 if(process == "Save")
         {            	
             $("#action").val("Save");       
             $("#frmUserDetail").attr("action","addUserDetails.jsf");                 
             $("#frmUserDetail").submit(); 	
         }  
         else if(process == "Update")
         {                   
        	 
        	 $("#action").val("Update");  
        	 $("#frmUserDetail").attr("action","updateUserDetails.jsf");                 
             $("#frmUserDetail").submit(); 	 
         } 
     
       	        
  }	
           
   function ShowDialog()
   {	   
      $("#overlay").show();
      $("#userDetailDialog").fadeIn(300);     
      $("#overlay").unbind("click");                       
   }

   function HideDialog()
   {
      $("#overlay").hide();
      $("#userDetailDialog").fadeOut(300);
   } 

   function showStatusDialog()
   {
      $("#overlay").show();
      $("#status").fadeIn(300);    
      $("#overlay").unbind("click");           
   }

   function hideStatusDialog()
   {
      $("#overlay").hide();
      $("#status").fadeOut(300);
   } 

   function showDeleteDialog()
   {
      $("#overlay").show();
      $("#delete").fadeIn(300);    
      $("#overlay").unbind("click");           
   }

   function hideDeleteDialog()
   {
      $("#overlay").hide();
      $("#delete").fadeOut(300);
   } 
   
   function showmsg()
   {	   
	   $("#msg").fadeIn(300);	   
   }

   function funedit(id,rowid,stat)  
   {		 
	   
	   $.ajax({
		    
		    dataType : 'json',
			url : "${pageContext.request.contextPath}/getUserDetails.jsf?userId="+id,
	        type : "POST",
	        success : function(result) 
	        {
	  		   if(result != "")
	    	   {				
	  			 
	  	    	  if($("#dynmsg").text() != "")
	  	    	  {
	  	    		 removemsg();
	  	    	  }       	 
	  	          $("#techAlert").empty(); 

	  	          $("#userName").val(trim(result.userName)); 	
	  	          $("#password").val(trim(result.password)); 	
	  	         $("#userId").val((result.userId));
	  	        
	  	        //  $("#role").val(trim(dept)); 	
	  	          $("#role option:contains(" + trim(result.role) + ")").attr('selected', 'selected');
	  	          $("#firstName").val(result.firstName);          
	  	          $("#lastName").val(result.lastName); 			
	  	          $("#email").val(result.email); 
	  	          ShowDialog(); 
	  	          $("#btnprocess").val("Update");	
	  	          $("#userName").focus();
	    	   }
	        }
	    }); 
   }
    
   function funUnlock(id,rowid,stat)  
   {		 
	   
	   $("#frmUserDetail").attr("action","unLockAccountForm.jsf?userId="+id);   	                
	   $("#frmUserDetail").submit();
   }

   
   function fundelete(delid)          
   {   
	   if($("#dynmsg").text() != "")
	   {
		 removemsg();
	   } 
	   $("#userId").val(delid); 
	   showDeleteDialog();
   }

   function funstatus(statid,rowid,delStatus)   
   {			      
	   if($("#dynmsg").text() != "")
	   {
		 removemsg();
	   } 	   
	   $("#deleteStatus").val(delStatus);     
	   $("#rowId").val(rowid);   
	   $("#autoId").val(statid); 

	   setChangeStatus("disable",statid);  
   }

   function setChangeStatus(val,id) 
   {	 
       var url = "${pageContext.request.contextPath}/updateUserDetailsStatus.jsf?userId="+id;   	   
       // Perform the AJAX request using a non-IE browser.
       if (window.XMLHttpRequest) 
       {		 
         request = new XMLHttpRequest();	   
         // Register callback function that will be called when
         // the response is generated from the server.
         request.onreadystatechange = updateChangeStatus;	      
         try 
         {
             request.open("POST", url, true);
         } 
         catch (e) 
         {
            //alert("Unable to connect to server to retrieve count.");
         }
      
         request.send(null);
       // Perform the AJAX request using an IE browser.
       } 
       else if (window.ActiveXObject) 
       {
   	    
         request = new ActiveXObject("Microsoft.XMLHTTP");
         if (request) 
         {
           // Register callback function that will be called when
           // the response is generated from the server.
           request.onreadystatechange = updateChangeStatus;
      
           request.open("GET", url, true);
           request.send();
         }	     
        }
   }

   function updateChangeStatus() 
   {		 
   	 if (request.readyState == 4) 
   	 {
		  if (request.status == 200) 
		  {
		  	   var status = request.responseText;	
		       if(status != "")
			   {			   
				    if(trim(status) == "Session Expire")
				    {
				    	$("#frmUserDetail").attr("action","pages/SessionExpire.jsp");   	                
					    $("#frmUserDetail").submit();
				    }
				    else
				    {
				       $("#dynmsg").text(status);
				       $("#msg").fadeIn(100);
				     	
			           var rowid = $("#rowId").val();   
					   if(status == "Made Active Successfully")
					   {
						   $("#delete"+rowid).attr('src',"./images/deleteicon_black.gif"); 
						   $("#changeStatus"+rowid).attr('src',"./images/activate.gif"); 
					
						   $("#deleteAnchor"+rowid).attr('title' , "Inactive entry can only be deleted"); 
						   $("#statusAnchor"+rowid).attr('title' , "Click to make this Inactive"); 
					
						   $("#deleteAnchor"+rowid).removeAttr("href");
						   $("#editAnchor"+rowid).attr("href", "javascript:funedit('"+$("#autoId").val()+"','"+rowid+"','1');");
					   }		  	  
					   else
					   {
						   if($("#deleteStatus").val() == "0")
						   {
							   $("#delete"+rowid).attr('src',"./images/deleteicon.gif");
							   $("#deleteAnchor"+rowid).attr('title' , "Click to Delete"); 
					
							   $("#deleteAnchor"+rowid).attr("href", "javascript:fundelete("+$("#autoId").val()+");");			  		
						   }
						   else
						   {
							   $("#delete"+rowid).attr('src',"./images/deleteicon_black.gif"); 
							   $("#deleteAnchor"+rowid).attr('title' , "Cannot delete, User in use"); 
					
							   $("#deleteAnchor"+rowid).removeAttr("href");
						   }
						    
						   $("#changeStatus"+rowid).attr('src',"./images/deactivate.gif");		  		   
						   $("#statusAnchor"+rowid).attr('title' , "Click to make this Active");
						   $("#editAnchor"+rowid).attr("href", "javascript:funedit('"+$("#autoId").val()+"','"+rowid+"','0');");
					   }
			        }
			     }   		      
		    }   	  
       } 
   }

   function removemsg()                    
   {
	    $("#dynmsg").empty();
		$("#msg").fadeOut(300);
		$("#listMsg").fadeOut(300);
   }

   function trim(stringTrim)
   {
	  	return stringTrim.replace(/^\s+|\s+$/g,"");
   }

   function init()
   {
	  $("#loadoverlay").fadeOut();  
	  $("#initialloading").fadeOut(); 
	 
	  if($("#action").val() != "" && $("#action").val() != "list" && $("#action").val() != "listdata")  
	  {    
			var dmsg = $("#dynmsg").text();	
		    if(dmsg.indexOf("exists") > -1)
		    {
		      $("#dynmsg").text('');
		      $("#msg").fadeOut(100);  
		 		   	    		    	
		      ShowDialog(); 		    
			  if($("#action").val() == "Save")   
			  {		    
			     $("#btnprocess").val("Save");		         
			  }
			  else if($("#action").val() == "edit")
			  {			  
		        $("#btnprocess").val("Update");			      
			  }
			  $("#userName").focus(); 
		    }
		    else
		    {
		    	showmsg();
		    	$("#searchUserDetail").focus();
		    }
	  }
	  else 
	  {
		  $("#searchUserDetail").focus();
	  }
   }
   function showAllListDialog()
   {
      $("#overlay").show();
      $("#allListConfirm").fadeIn(300);  
      $("#btnProcessyes").focus();
      $("#overlay").unbind("click");           
   }

   function hideAllListDialog()
   {
      $("#overlay").hide();
      $("#allListConfirm").fadeOut(300);
   } 
   function funAllList()
   {	
	   var searchVal = trim($("#searchUserDetail").val());   
	   if(searchVal == "")
  		{   	
		   showAllListDialog();
  		}
	   else
 		{   		    
	   	       $("#action").val("list");  	
	 		   $("#loadoverlay").fadeIn();  
	 		   $("#processloading").fadeIn();	 			                    	                  
	 	 	   $("#frmUserDetail").attr("action","searchUserDetails.jsf");   	                
	 	       $("#frmUserDetail").submit();
  		 }	  
   }
   function funAllProcessList()
   {  		    	
	       $("#action").val("listdata");  
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();   	 			                    	                  
 	 	   $("#frmUserDetail").attr("action","showUserDetail.jsf");   	                
 	       $("#frmUserDetail").submit();	      	 	
   }   	  
</script>

</head>
<body class="body" onload="init();">
<form name="frmUserDetail" id="frmUserDetail" method="post" autocomplete="off">
 <%@ include file="tiimMenu.jsp"%>
 
 <div id="loadoverlay" class="loading_overlay" ></div>
 <div id="initialloading" style=" position: absolute; z-index: 700;top:105px; left: 405px; width: 450px; height: 50px; background-color:#ffffff;">
          <img  src="./images/loadmain.gif"></img>
 </div>
 <div id="processloading" style=" position: absolute; z-index: 700;top:105px; left: 405px;width: 450px; height: 50px; background-color:#ffffff;display:none;">
          <img  src="./images/Processing.gif"></img>
 </div>
 
  <div id="overlay" class="web_dialog_overlay"></div>
    
     <div id="delete" class="web_dialog_delete">    
     <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="80%" align="left" class="popuptoptitlelarge">Delete</td>			        			       
				        <td width="18%" align="right" class="popuptoptitle"><a href="#" title="Click to Close" class="popupanchor" id="btndelclose">X</a>&nbsp;</td>
				     </tr>	
				     <tr><td colspan="2"></td></tr>
			    </table>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">				     
				     <tr style="height:20px"></tr>				     
				     <tr><td  class="popuplabel" align="center">Are you sure to Delete?</td></tr>	
				     <tr style="height:20px"></tr>		     			
				     <tr>					               
				        <td class="formlabelblack" align="center" >				       
				        <input  type="button" class="btn btnSMNormal" id="btndelyes" value="Yes" />&nbsp;
				        <input type="button" id="btndelno"  class="btn btnSMNormal"  value="No" />
				        </td>	        			       	       
				     </tr>					     			     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div>
	
	<div id="userDetailDialog" class="web_dialog" style="height:480px">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	       <td>
			  <table style="width:98%" align="center" cellpadding="4" cellspacing="0" border="0">	     
			     <tr>
			        <td align="left" width="50%" class="popuptoptitle">User Info</td>
			        <td width="48" class="popuptoptitle" align="right"><a href="javascript:mstEmpCancelClose();" title="Click to Close" class="popupanchor" id="btnclose">X</a>&nbsp;</td>
			     </tr>			     			     			    
			     <tr>
			        <td colspan="2" align="center" class="popuptopalert">&nbsp;<span id="techAlert">${message}</span></td>
			     </tr>			      	   			    
			     <tr>
			        <td align="left" class="formlabelblack">User Name&nbsp;<span class="formlabel">*</span></td>
			        <td align="left">&nbsp;<input  type="text" class="textsmall" name="userName" id="userName" value="" onkeypress="javascript:funTypeuserName(event,this);" autocomplete="off" maxlength="20"/></td>
			     </tr> 
			     <tr>
			        <td align="left" class="formlabelblack">Password&nbsp;<span class="formlabel">*</span></td>
			        <td align="left">&nbsp;<input type="password" class="textsmall" name="password" id="password" onkeypress="javascript:funTypepassword(event,this);" value="" autocomplete="off" maxlength="20"/><span id="result"></span></td>
			     </tr> 
			     <tr>
			        <td align="left" class="formlabelblack">Role&nbsp;<span class="formlabel">*</span></td>
			        <td align="left">&nbsp;
			        <select id="role" name="role" class="textsmall" maxlength="200" onkeypress="javascript:funTyperole(event,this);">
							        <c:forEach var="roleObj" items="${roleList}">
											<option value="${roleObj}">${roleObj}</option>
									</c:forEach>
					 </select> 
			       <!--  <input type="text" class="textsmall" name="role" id="role" onkeypress="javascript:funTyperole(event,this);" value="" autocomplete="off" maxlength="200"/></td> -->
			     </tr>
			      <tr>
			        <td align="left" class="formlabelblack">First Name&nbsp;<span class="formlabel">*</span></td>
			          <td align="left">&nbsp;<input type="text" class="textsmall" name="firstName" id="firstName" onkeypress="javascript:funTypeFirstName(event,this);" value="" autocomplete="off" maxlength="200"/></td>
			     </tr> 
			     
			      <tr>
			        <td align="left" class="formlabelblack">Last Name&nbsp;<span class="formlabel">*</span></td>
			          <td align="left">&nbsp;<input type="text" class="textsmall" name="lastName" id="lastName" onkeypress="javascript:funTypeLastName(event,this);" value="" autocomplete="off" maxlength="200"/></td>
			     </tr>    
			     <tr>
			        <td align="left" class="formlabelblack">Email&nbsp;<span class="formlabel">*</span></td>
			          <td align="left">&nbsp;<input type="text" class="textsmall" name="email" id="email" value="" autocomplete="off" maxlength="200"/></td>
			     </tr>    
			     <tr>
			       <td colspan="2">&nbsp;</td>			       
		         </tr>  
		          <tr>
			        <td align="left" class="formlabelblack">Branch Name&nbsp;<span class="formlabel">*</span></td>
			       <td> <select id="branchName" name="branchName" class="textsmall" maxlength="200">
							        <c:forEach var="branch" items="${branchList}">
											<option value="${branch}">${branch}</option>
									</c:forEach>
					 </select> </td>
			       </tr>    
			     <tr>
			       <td colspan="2">&nbsp;</td>			       
		         </tr>       			     
			     <tr>
			        <td>&nbsp;</td>
			        <td align="left">
			        	<button type="button" class="btn btnImportant" id="btnprocess" onclick="javascript:processUserDetail();">Save</button>
			        &nbsp;&nbsp;<input type="button" id="btncancel"  class="btn btnCancel"  onclick="javascript:mstEmpCancelClose();" value="Cancel" /></td>
			     </tr>	
			     <tr>
			       <td colspan="2">&nbsp;<div class="none" id="pswd_info"  style="color:#C20100;">
								    <h4>Password must meet the following requirements:</h4>
								    <ul>
								        <li id="letter" class="invalid">At least <strong>one letter</strong></li>
								        <li id="capital" class="invalid">At least <strong>one capital letter</strong></li>
								        <li id="number" class="invalid">At least <strong>one number</strong></li>
								        <li id="length" class="invalid">Be at least <strong>8 characters</strong></li>
								        <li id="length" class="invalid">At least <strong>one special characters</strong></li>
								    </ul>
								</div></td>				       
		         </tr>	
		         
			  </table>
	        </td>
	      </tr>	       
	  </table>
	</div>
	
	<div id="allListConfirm" class="web_dialog_delete">    
     <table style="width:98%" align="center" cellpadding="2" cellspacing="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="80%" align="left" class="popuptoptitlelarge">Confirm</td>			        			       
				        <td width="18%" align="right" class="popuptoptitle"><a href="#" title="Click to Close" class="popupanchor" id="btnProcessclose">X</a>&nbsp;</td>
				     </tr>	
				     <tr><td colspan="2"></td></tr>
			    </table>
			     <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">				     
				     <tr style="height:20px"><td></td></tr>				     
				     <tr><td  class="popuplabel" align="center">Are you sure to list all data?</td></tr>	
				     <tr style="height:20px"><td></td></tr>		     			
				     <tr>					               
				        <td class="formlabelblack" align="center" >				       
				        <input  type="button" class="btn btnSMNormal" id="btnProcessyes" value="Yes" />&nbsp;
				        <input type="button" id="btnProcessno"  class="btn btnSMNormal"  value="No" />
				        </td>	        			       	       
				     </tr>					     			     			     			    			     		      	   			   			       
			     </table>	 
	        </td>
	      </tr>	       
	  </table>
	</div>
	
	<table cellspacing="0" width="100%"  cellpadding="5" border="0" align="center"  style="height:10px">		      
		<tr >  
			<td></td> 		            
			<td></td>
		</tr>
	</table>
	 <table width="1000px" cellspacing="0" cellpadding="5" border="0" align="center"  style="height:25px">		      
	     <tr>  
	          <td width="25%" class="heading" align="left">&nbsp;User Detail</td> 
	          <td width="50%" class="submenutitlesmall" align="center">
	               <table cellspacing="1" cellpadding="2" width="50%" align="center" style="display:none" id="msg">	
				     	<tr>  
					    	<td class="confirmationmessage" align="center">&nbsp;<span id="dynmsg">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="popupanchor" id="msgclose">X</a></td>
					    </tr>
			       </table>
	          </td>
	          <td align="right" width="25%" class="anchorlabel">
			  &nbsp;<input type="button" title="Click to Add" class='btn btnSMNormal' id="btnpop" value="Add"/>&nbsp;			  
			  </td>                             
	     </tr>
	 </table>
       
	<center>           
		<div style=" width: 1000px; padding:0px; margin: 0px;height:425;min-height:420px;">       
		
		 <div style="width: 980px;background-color:white; border: 1px solid #E0E0E0;;">
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>   
		    <table cellspacing="0" class="searchList" cellpadding="5" border="0" align="center">
		       <tr>
		          <td class="searchListLabel" align="left">Search&nbsp;&nbsp;<input type="text" name="searchUserDetail" id="searchUserDetail" placeholder="Enter User Name" value="${searchUserDetail}" class="textmediumlarge" maxlength="50"/>&nbsp;&nbsp;<input type="button" name="btnSearch" id="btnSearch" value="Go" class="btn btnSMImportant"></td>  
		       </tr>
		    </table>  
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>  
		  </div>     
		             
		  <c:choose>
			<c:when test="${fn:length(lstUserDetails) gt 0}">
		        <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
					 <tr>
						 <td height="5px"></td>		
					 </tr>
			    </table>
		                                
			<table id="lstFieldTech" name="lstFieldTech" class="tableHeading">
			      
			     <thead>
			          <tr>
				          <th width="20%">User Name</th>
				          <th width="20%">First Name</th>
				          <th width="20%">role</th>
				          <th width="40%">Action</th>
			          </tr>  
			     </thead> 
			     <tbody>
			         <c:forEach items="${lstUserDetails}" var="lstUserDetails" varStatus="row">  
				          <tr>
				             <td>${lstUserDetails.userName}</td>   
				             <td>${lstUserDetails.firstName}</td>
				             <td>${lstUserDetails.role}</td>
				             <td align="center" style="text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;
				             	<a title="Click to Edit" id="editAnchor${row.index}" href="javascript:funedit('${lstUserDetails.userId}','${row.index}','${lstUserDetails.isActive}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;   		             		
				             	<a title="Click to unlock" id="unlock${row.index}" href="javascript:funUnlock('${lstUserDetails.userId}','${row.index}','${lstUserDetails.isActive}');"><img border="0" id="showedit" src="./images/unlock.png"/></a>&nbsp;			
								<c:choose>
									<c:when test="${lstUserDetails.isActive == 1}">
										<a title="Inactive entry can only be deleted" id="deleteAnchor${row.index}"><img border="0" id="delete${row.index}" src="./images/deleteicon_black.gif"/></a>&nbsp;&nbsp;<a title="Click to make this Inactive" href="javascript:funstatus('${lstUserDetails.userId}','${row.index}','${lstUserDetails.delStatus}');" id="statusAnchor${row.index}" ><img border="0" id="changeStatus${row.index}" style="" src="./images/activate.gif"/></a>
									</c:when>
									<c:when test="${lstUserDetails.delStatus == 0}">
										<a title="Click to Delete" href="javascript:fundelete('${lstUserDetails.userId}');" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>&nbsp;<a title="Click to make this Active" href="javascript:funstatus('${lstUserDetails.userId}','${row.index}','${lstUserDetails.delStatus}');" id="statusAnchor${row.index}" ><img border="0" id="changeStatus${row.index}" style="" src="./images/deactivate.gif"/></a>
									</c:when>
									<c:otherwise>
									    <a title="Cannot delete, Inspector in use" id="deleteAnchor${row.index}"><img border="0" id="delete${row.index}" src="./images/deleteicon_black.gif"/></a>&nbsp;<a title="Click to make this Active" href="javascript:funstatus('${lstUserDetails.userId}','${row.index}','${lstUserDetails.delStatus}');" id="statusAnchor${row.index}" ><img border="0" style="" id="changeStatus${row.index}" src="./images/deactivate.gif"/></a>
									</c:otherwise>
								</c:choose>
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
		</div>
		<table  cellspacing="0" cellpadding="5" style=" width: 1000px;" border="0" align="center" class="legendtable">		      	      
			<tr>
			    <td align="right" class="legend">
				<img border="0" style="" src="./images/pencil.png"/>&nbsp;-&nbsp;Edit&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon.gif"/>&nbsp;-&nbsp;Delete&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deleteicon_black.gif"/>&nbsp;-&nbsp;Data in use&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/activate.gif"/>&nbsp;-&nbsp;Active&nbsp;&nbsp;&nbsp;<img border="0" style="" src="./images/deactivate.gif"/>&nbsp;-&nbsp;Inactive&nbsp;</td>     
			</tr>                         	       
	   </table> 
	</center>    
   

<input type="hidden" name="action" id="action" value="${action}"/>
<input type="hidden" name="userId" id="userId" value="${userId}"/>
<input type="hidden" name="isActive" id="isActive" value="${isActive}"/>

<input type="hidden" name="deleteStatus" id="deleteStatus" />
<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />

</form>
</body>
</html>

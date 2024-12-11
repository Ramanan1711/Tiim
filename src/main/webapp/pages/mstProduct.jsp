<%@page import="java.util.HashMap, com.tiim.model.RoleVsUser "%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String addAccess = "";
String editAccess = "";
String deleteAccess = "";
%>
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
<style>
.pagination 
{  
  display:none;
}
</style>
<script>
$("#techAlert").empty();
$("#productName").val("");  

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
	    	 $("#productName").val(""); 
	    	 $("#drawingNo").val("");	   
	    	 
			 //$("#machineType").val("Required");
			 $("#typeOfTool").val("");
			 $("#mocPunches").val("");
			 $("#mocDies").val("");
			 $("#shape").val("");
			 $("#dimensions").val("");
			 //$("#breaklineUpper").val("Required");
			 //$("#breaklineLower").val("Required");
			 $("#embosingUpper").val("");
			 $("#embosingLower").val("");
			// $("#laserMaking").val("Required");
			 $("#hardChromePlating").val("");
			 $("#searchProduct").val("");
			// $("#dustCapGroove").val("");
			 //$("#inspectionReport").val("Required");
			// $("#dqDocument").val("");
			// $("#mocCertificate").val("Required");
			// $("#ductCapMOCCertificate").val("");
			// $("#toolingLife").val("");
			 $("#serviceIntervalQty").val("");
			 $("#uom").val("");
	         ShowDialog(); 
	         $("#btnprocess").val("Save");	
	         $("#productName").focus();  	           
	      });
	      
	      $("#btnpopup").click(function ()        
 	      {		    	
	    	 if($("#dynmsg").text() != "")
			 {
			    removemsg();
			 }  		    	
	    	 $("#techAlert").empty();  	
	    	 
	    	 $("#productName").val(""); 
	    	 $("#drawingNo").val("");	   
	    	
			 //$("#machineType").val("Required");
			 $("#typeOfTool").val("");
			 $("#mocPunches").val("");
			 $("#mocDies").val("");
			 $("#shape").val("");
			 $("#dimensions").val("");
			/*  $("#breaklineUpper").val("Required");
			 $("#breaklineLower").val("Required"); */
			 $("#embosingUpper").val("");
			 $("#embosingLower").val("");
			// $("#laserMaking").val("Required");
			 $("#hardChromePlating").val("");
			 $("#searchProduct").val("");
			// $("#dustCapGroove").val("");
			// $("#inspectionReport").val("Required");
			// $("#dqDocument").val("");
			// $("#mocCertificate").val("Required");
		//	 $("#ductCapMOCCertificate").val("");
			// $("#toolingLife").val("");
			 $("#serviceIntervalQty").val("");
			 $("#uom").val("");
 	         ShowDialog();	
 	         $("#btnprocess").val("Save");
 	         $("#productName").focus();                 
 	      });
	     	      
	      $("#msgclose").click(function()            
		  {				    	
		  	  $("#msg").fadeOut(300);		  	         
		  }); 
  	       
	      $("#productName").keyup(function()           
	      {
		    	if(trim($("#productName").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	      }) ;
	      
	      $("#drawingNo").keyup(function()             
   	      {   		    	
   		    	if(trim($("#drawingNo").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      }) ;

	      
		 /*  $("#machineType").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#machineType").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#typeOfTool").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ; */

		  $("#typeOfTool").keyup(function()             
   	      {   		    	
   		    	if(trim($("#typeOfTool").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      }) ;
		  
		  $("#mocPunches").keyup(function()             
   	      {   		    	
   		    	if(trim($("#mocPunches").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      });
		  
		  $("#mocDies").keyup(function()             
   	      {   		    	
   		    	if(trim($("#mocDies").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      });
		  
		  $("#shape").keyup(function()             
   	      {   		    	
   		    	if(trim($("#shape").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      });
		  
		  $("#dimensions").keyup(function()             
   	      {   		    	
   		    	if(trim($("#dimensions").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      });
		  
		/*   $("#breaklineUpper").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#breaklineUpper").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#breaklineLower").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ;
		  
		  $("#breaklineLower").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#breaklineLower").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#embosingUpper").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ; */
		  
		  $("#embosingUpper").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#embosingUpper").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#embosingLower").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ;
		  
		  $("#embosingLower").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#embosingLower").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#hardChromePlating").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ;
		  
		 /*  $("#laserMaking").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#laserMaking").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#hardChromePlating").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ; */
		  
		  $("#hardChromePlating").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#hardChromePlating").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		// 	$("#dustCapGroove").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ;
		  
		/*   $("#dustCapGroove").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#dustCapGroove").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      }) ; */
		  
		 /*  $("#inspectionReport").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#inspectionReport").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#dqDocument").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ; */
		  
		 /*  $("#dqDocument").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#dqDocument").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#ductCapMOCCertificate").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ; */
		  
		  /*  $("#dustCapGroove").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#dustCapGroove").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#dqDocument").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ;  */
		/*   
		  $("#ductCapMOCCertificate").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#ductCapMOCCertificate").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   		    	var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
   	    	    if(charCode == 13)
   	    	    { 
   		    		setTimeout(function() {
   			 		 	$("#toolingLife").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ; */
		  
		  
		  
		  $("#serviceIntervalQty").keydown(function(evt)             
		  {   		    	
			  /* alert(evt.which);
					  if(evt.which != 8 && evt.which != 0 && ((evt.which < 48) || (evt.which > 57)))
					  {
					      $("#serviceIntervalQty").val("");
					   	  $("#serviceIntervalQty").focus();
						   return false;   
					  } */
		  		    	if(trim($("#serviceIntervalQty").val()) != "")
		  		    	{
		  		    		$("#techAlert").empty();
		  		    	}
		  		    	if($("#dynmsg").text() != "")
		  		    	{
		  		    		removemsg();
		  		    	}
		  }) ;

		  $("#uom").keypress(function(event) { 
		        
	    	  if(event.which == 13 ) 
		       {	    		  
	    		  $("#techAlert").empty();
	    		   var alertmsg = "UOM is required";
	    		   var uom =  trim($("#uom").val());
	    		   var productName =  trim($("#productName").val());
	    		   var drawingNo =  trim($("#drawingNo").val());
	    		   var embosingUpper = trim($("#embosingUpper").val());
	    		   var embosingLower = trim($("#embosingLower").val());
	    		   var typeOfTool = trim($("#typeOfTool").val());
	    		   var mocPunches = trim($("#mocPunches").val());
	    		   var mocDies = trim($("#mocDies").val());
	    		   var shape = trim($("#shape").val());
	    		   var dimensions = trim($("#dimensions").val());
	    		 //  var dustCapGroove = trim($("#dustCapGroove").val());
	    		  // var toolingLife = trim($("#toolingLife").val());
	    		   var serviceIntervalQty = trim($("#serviceIntervalQty").val());
	    		   var process = $("#btnprocess").val(); 
	    		   var hardChromePlating = trim($("#hardChromePlating").val());
	    		   //var dqDocument = trim($("#dqDocument").val());
	    		  // var ductCapMOCCertificate = trim($("#ductCapMOCCertificate").val());
	    		    
	    		  /*  if(uom == "")
	    			{
	    			   $("#techAlert").text(alertmsg);
	    	      	   $("#uom").focus();
	    	      	   return false;
	    			}	    		   
	    		   else */ 
	    		   if(productName == "")
	    	       {      
	    			   alertmsg = "Product name is required";
	    	           $("#techAlert").text(alertmsg);
	    	      	   $("#productName").focus();
	    	      	   return false;
	    	       }
	    		   else if(drawingNo == "")
	    		   {
	    			   alertmsg = "Drawing No. is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#drawingNo").focus();
	    			   return false;
	    		   }
	    		  
	    		  /*  else if(typeOfTool == "")
	    		   {
	    			   alertmsg = "Type of Tooling is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#typeOfTool").focus();
	    			   return false;
	    		   } */
	    		  /*  else if(mocPunches == "")
	    		   {
	    			   alertmsg = "MOC - Punches is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#mocPunches").focus();
	    			   return false;
	    		   }
	    		   else if(mocDies == "")
	    		   {
	    			   alertmsg = "MOC - Dies is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#mocDies").focus();
	    			   return false;
	    		   } */
	    		   else if(shape == "")
	    		   {
	    			   alertmsg = "Shape of Tooling is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#shape").focus();
	    			   return false;
	    		   }
	    		   else if(dimensions == "")
	    		   {
	    			   alertmsg = "Dimensions of Tooling is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#dimensions").focus();
	    			   return false;
	    		   }
	    		   else if(embosingUpper == "")
	    		   {
	    			   alertmsg = "Embosing Upper is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#embosingUpper").focus();
	    			   return false;
	    		   } 
	    		   else if(embosingLower == "")
	    		   {
	    			   alertmsg = "Embosing Lower is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#embosingLower").focus();
	    			   return false;
	    		   }	
	    		   
	    		  /*  else if(hardChromePlating == "")
	    		   {
	    			   alertmsg = "Platting is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#hardChromePlating").focus();
	    			   return false;
	    		   } */	
	    		  /*  else if(dustCapGroove == "")
	    		   {
	    			   alertmsg = "Dust Gap is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#dustCapGroove").focus();
	    			   return false;
	    		   } */
	    		  /*  else if(dqDocument == "")
	    		   {
	    			   alertmsg = "DQ document is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#dqDocument").focus();
	    			   return false;
	    		   } */
	    		  /*  else if(ductCapMOCCertificate == "")
	    		   {
	    			   alertmsg = "MOC Certificate is required";
	    			   $("#techAlert").text(alertmsg);
	    			  // $("#dustCapGroove").focus();
	    			   return false;
	    		   } */
	    		  /*  else if(toolingLife == "")
	    		   {
	    			   alertmsg = "Tooling Life is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#toolingLife").focus();
	    			   return false;
	    		   } */
	    		   else if(serviceIntervalQty == "")
	    		   {
	    			   alertmsg = "Tooling Service Interval Qty is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#serviceIntervalQty").focus();
	    			   return false;
	    		   }
	    		   
   			     $("#techAlert").empty();  
   			     HideDialog();

   			     $("#loadoverlay").fadeIn();  
   			     $("#processloading").fadeIn();
   			    	
   			     if(process == "Save")
   			     {            	
   			         $("#action").val("Save");      
   			         $("#frmProduct").attr("action","addProduct.jsf");               
   			     }  
   			     else if(process == "Update")
   			     {                           
   			    	 $("#action").val("Update"); 
   			    	 $("#frmProduct").attr("action","updateProduct.jsf");               
   			     }       
   			     $("#frmProduct").submit(); 
	    	     event.preventDefault();
	    	   }	
		  });
		  
	      $("#serviceIntervalQty").keypress(function(event) {       
	    	  if(event.which != 8 && event.which != 0 && ((event.which < 48) || (event.which > 57)))
			  {
			      $("#serviceIntervalQty").val("");
			   	  $("#serviceIntervalQty").focus();
				   return false;   
			  }else
	    	  if(event.which == 13 ) 
		       {	    		  
	    		  $("#techAlert").empty();
	    		   var alertmsg = "Product Name is required";
	    		   var productName =  trim($("#productName").val());
	    		   var drawingNo =  trim($("#drawingNo").val());
	    		   
	    		   var typeOfTool = trim($("#typeOfTool").val());
	    		   var mocPunches = trim($("#mocPunches").val());
	    		   var mocDies = trim($("#mocDies").val());
	    		   var shape = trim($("#shape").val());
	    		   var dimensions = trim($("#dimensions").val());
	    		  // var dustCapGroove = trim($("#dustCapGroove").val());
	    		  // var toolingLife = trim($("#toolingLife").val());
	    		   var serviceIntervalQty = trim($("#serviceIntervalQty").val());
	    		   var process = $("#btnprocess").val(); 
	    		    
	    		   if(productName == "")
	    	       {                                        	 	         
	    	           $("#techAlert").text(alertmsg);
	    	      	   $("#productName").focus();
	    	      	   return false;
	    	       }
	    		   else if(drawingNo == "")
	    		   {
	    			   alertmsg = "Drawing No. is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#drawingNo").focus();
	    			   return false;
	    		   }
	    		   
	    		  /*  else if(typeOfTool == "")
	    		   {
	    			   alertmsg = "Type of Tooling is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#typeOfTool").focus();
	    			   return false;
	    		   } */
	    		  /*  else if(mocPunches == "")
	    		   {
	    			   alertmsg = "MOC - Punches is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#mocPunches").focus();
	    			   return false;
	    		   }
	    		   else if(mocDies == "")
	    		   {
	    			   alertmsg = "MOC - Dies is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#mocDies").focus();
	    			   return false;
	    		   } */
	    		   else if(shape == "")
	    		   {
	    			   alertmsg = "Shape of Tooling is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#shape").focus();
	    			   return false;
	    		   }
	    		   else if(dimensions == "")
	    		   {
	    			   alertmsg = "Dimensions of Tooling is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#dimensions").focus();
	    			   return false;
	    		   }
	    		  /*  else if(dustCapGroove == "")
	    		   {
	    			   alertmsg = "Dust Gap & Groove is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#dustCapGroove").focus();
	    			   return false;
	    		   } */
	    		   /* else if(toolingLife == "")
	    		   {
	    			   alertmsg = "Tooling Life is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#toolingLife").focus();
	    			   return false;
	    		   } */
	    		   else if(serviceIntervalQty == "")
	    		   {
	    			   alertmsg = "Tooling Service Interval Qty is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#serviceIntervalQty").focus();
	    			   return false;
	    		   }
	    		   else if (attNo == "")
				   {
				   alertmsg = "ATT No. is required";
				   $("$techAlert").text(alertmsg);
				   $("#attNo").focus();
				   return false;
				   }
	    		   else
	    		   {
	    			   $("#uom").focus();
	    		   }
   			    
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
	   	   
	    	  $("#frmProduct").attr("action","deleteProduct.jsf");   	                
	          $("#frmProduct").submit(); 
   	      });  	        
   	      
   	   $("#btnProcessyes").click(function ()      
  	   	      {		  
  					hideAllListDialog();	
  					funAllProcessList();
  	   	      });
   	   $("#btnProcessclose").click(function ()  
      	      {		    	   	    	  	    			    		    	
  					hideAllListDialog();
  					$("#searchProduct").focus();
      	      });  
   	    $("#btnProcessno").click(function ()      
      	      {		    	   	    	  	    			    		    	
  	    			hideAllListDialog();	
  	    			$("#searchProduct").focus();
      	      });   	
   	   $("#searchProduct").keypress(function(e){
 	 	  	  if(e.which == 13)
 	 	  	  {	 	  		
 	 	  		 return funAllList();
 	 	  	  }     	  	 
 	         });
   		$("#btnSearch").click(function (){  	  		
 			 	return funAllList(); 	           
 		     }); 
   });

  function mstProductCancelClose()             
  {	    	  
	var process = $("#btnprocess").val(); 
	$("#techAlert").empty(); 
	$("#productName").val(""); 
	$("#drawingNo").val("");
	
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
		      
  function funTypeProductName(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else
		   {
			   $("#drawingNo").focus();
		   }
	   }
  }
  
  function funTypeDrawingNo(evt,obj)
  {
	  $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		 
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		   else if (attNo == "")
			   {
			   alertmsg = "ATT No. is required";
			   $("$techAlert").text(alertmsg);
			   $("#attNo").focus();
			   return false;
			   }
		   else
		   {
			   $("#typeOfTool").focus();
		   }
	   }
  }

  /* function funTypeStrength(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   var strength =  trim($("#strength").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		   else if(strength == "")
		   {
			  alertmsg = "Strength is required";
			  $("#techAlert").text(alertmsg);
			  $("#strength").focus();
			  return false;
		   }
		   else
		   {
			   $("#machineType").focus();
		   }
	   }
  }*/
  
  function funTypeTool(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		  
		  /*  else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   } */
		   else
		   {
			   $("#mocPunches").focus();
		   }
	   }
  } 
  
  function funTypeMOCPunches(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		 
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		   
		   /* else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   } */
		  /*  else if(mocPunches == "")
		   {
			   alertmsg = "MOC - Punches is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocPunches").focus();
			   return false;
		   } */
		   else
		   {
			   $("#mocDies").focus();
		   }
	   }
  }
  
  function funTypeMOCDies(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		  
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		  
		  /*  else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   } */
		   /* else if(mocPunches == "")
		   {
			   alertmsg = "MOC - Punches is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocPunches").focus();
			   return false;
		   }
		   else if(mocDies == "")
		   {
			   alertmsg = "MOC - Dies is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocDies").focus();
			   return false;
		   } */
		   else
		   {
			   $("#shape").focus();
		   }
	   }
  }
  
  function funTypeShape(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		  
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   var shape = trim($("#shape").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		  
		   /* else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   } */
		 /*   else if(mocPunches == "")
		   {
			   alertmsg = "MOC - Punches is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocPunches").focus();
			   return false;
		   }
		   else if(mocDies == "")
		   {
			   alertmsg = "MOC - Dies is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocDies").focus();
			   return false;
		   } */
		   else if(shape == "")
		   {
			   alertmsg = "Shape of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#shape").focus();
			   return false;
		   }
		   else
		   {
			   $("#dimensions").focus();
		   }
	   }
 }
  function funTypeDimension(evt,obj)
  {
	  $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   var shape = trim($("#shape").val());
		   var dimensions = trim($("#dimensions").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		   
		   /* else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   } */
		  /*  else if(mocPunches == "")
		   {
			   alertmsg = "MOC - Punches is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocPunches").focus();
			   return false;
		   }
		   else if(mocDies == "")
		   {
			   alertmsg = "MOC - Dies is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocDies").focus();
			   return false;
		   } */
		   else if(shape == "")
		   {
			   alertmsg = "Shape of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#shape").focus();
			   return false;
		   }
		   else if(dimensions == "")
		   {
			   alertmsg = "Dimensions of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#dimensions").focus();
			   return false;
		   }
		   else
		   {
			   $("#embosingUpper").focus();
		   }
	   }
 }
  
  function funTypeDustGap(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "Product Name is required";
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		  
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   var shape = trim($("#shape").val());
		   var dimensions = trim($("#dimensions").val());
		  // var dustCapGroove = trim($("#dustCapGroove").val());
		   if(productName == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
		   else if(drawingNo == "")
		   {
			   alertmsg = "Drawing No. is required";
			   $("#techAlert").text(alertmsg);
			   $("#drawingNo").focus();
			   return false;
		   }
		  /*  else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   } */
		  /*  else if(mocPunches == "")
		   {
			   alertmsg = "MOC - Punches is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocPunches").focus();
			   return false;
		   }
		   else if(mocDies == "")
		   {
			   alertmsg = "MOC - Dies is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocDies").focus();
			   return false;
		   } */
		   else if(shape == "")
		   {
			   alertmsg = "Shape of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#shape").focus();
			   return false;
		   }
		   else if(dimensions == "")
		   {
			   alertmsg = "Dimensions of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#dimensions").focus();
			   return false;
		   }
		  /*  else if(dustCapGroove == "")
		   {
			   alertmsg = "Dust Gap is required";
			   $("#techAlert").text(alertmsg);
			   $("#dustCapGroove").focus();
			   return false;
		   } */
		   else
		   {
			 //  $("#ductCapMOCCertificate").focus();
		   }
	   }
  }
  
 
  
  function processProduct()                   
  {  	    	  	       	
	  $("#techAlert").empty();
	   var alertmsg = "Product Name is required";
	   var productName =  trim($("#productName").val());
	   var drawingNo =  trim($("#drawingNo").val());
	   var compForce = trim($("#compForce").val())
	   var punchSetNo = trim($("#punchSetNo").val())
	   var typeOfTool = trim($("#typeOfTool").val());
	   var mocPunches = trim($("#mocPunches").val());
	   var mocDies = trim($("#mocDies").val());
	   var shape = trim($("#shape").val());
	   var dimensions = trim($("#dimensions").val());
	 //  var dustCapGroove = trim($("#dustCapGroove").val());
	  // var dqDocument = trim($("#dqDocument").val());
	  // var ductCapMOCCertificate = trim($("#ductCapMOCCertificate").val());
	  // var toolingLife = trim($("#toolingLife").val());
	   var serviceIntervalQty = trim($("#serviceIntervalQty").val());
	   var uom = trim($("#uom").val());
	   var process = $("#btnprocess").val(); 
	   var embosingLower = $("#embosingLower").val();
	   var embosingUpper = $("#embosingUpper").val();
	   var hardChromePlating = $("#hardChromePlating").val();

	   //var expiryPeriod = $("#expiryPeriod").val();
	   var acceptedQty = $("#acceptedQty").val();
	    
	   if(productName == "")
       {                                        	 	         
           $("#techAlert").text(alertmsg);
      	   $("#productName").focus();
      	   return false;
       }
	   else if(drawingNo == "")
	   {
		   alertmsg = "Drawing No. is required";
		   $("#techAlert").text(alertmsg);
		   $("#drawingNo").focus();
		   return false;
	   }
	   
	   else if(compForce == "")
	   {
		   alertmsg = "Max compression force limit is required";
		   $("#techAlert").text(alertmsg);
		   $("#compForce").focus();
		   return false;
	   }
	   else if(punchSetNo == "")
	   {
		   alertmsg = "Punch Set Number is required";
		   $("#techAlert").text(alertmsg);
		   $("#punchSetNo").focus();
		   return false;
	   }
	  
	   /* else if(typeOfTool == "")
	   {
		   alertmsg = "Type of Tooling is required";
		   $("#techAlert").text(alertmsg);
		   $("#typeOfTool").focus();
		   return false;
	   } */
	  /*  else if(mocPunches == "")
	   {
		   alertmsg = "MOC - Punches is required";
		   $("#techAlert").text(alertmsg);
		   $("#mocPunches").focus();
		   return false;
	   }
	   else if(mocDies == "")
	   {
		   alertmsg = "MOC - Dies is required";
		   $("#techAlert").text(alertmsg);
		   $("#mocDies").focus();
		   return false;
	   } */
	   else if(shape == "")
	   {
		   alertmsg = "Shape of Tooling is required";
		   $("#techAlert").text(alertmsg);
		   $("#shape").focus();
		   return false;
	   }
	   else if(dimensions == "")
	   {
		   alertmsg = "Dimensions of Tooling is required";
		   $("#techAlert").text(alertmsg);
		   $("#dimensions").focus();
		   return false;
	   }
	   else if(embosingUpper == "")
	   {
		   alertmsg = "Embosing Upper is required";
		   $("#techAlert").text(alertmsg);
		   $("#embosingUpper").focus();
		   return false;
	   }
	   else if(embosingLower == "")
	   {
		   alertmsg = "Embosing Lower is required";
		   $("#techAlert").text(alertmsg);
		   $("#embosingLower").focus();
		   return false;
	   }	
	 /*   else if(hardChromePlating == "")
	   {
		   alertmsg = "Platting is required";
		   $("#techAlert").text(alertmsg);
		   $("#hardChromePlating").focus();
		   return false;
	   } */
	   
	   /* else if(dustCapGroove == "")
	   {
		   alertmsg = "Dust Gap is required";
		   $("#techAlert").text(alertmsg);
		   $("#dustCapGroove").focus();
		   return false;
	   } */
	  /*  else if(dqDocument == "")
	   {
		   alertmsg = "DQ document is required";
		   $("#techAlert").text(alertmsg);
		   $("#dqDocument").focus();
		   return false;
	   } */
	  /*  else if(ductCapMOCCertificate == "")
	   {
		   alertmsg = "MOC Certificate is required";
		   $("#techAlert").text(alertmsg);
		  // $("#dustCapGroove").focus();
		   return false;
	   } */
	   
	   
	  /*  else if(toolingLife == "")
	   {
		   alertmsg = "Tooling Life is required";
		   $("#techAlert").text(alertmsg);
		   $("#toolingLife").focus();
		   return false;
	   } */
	   else if(serviceIntervalQty == "")
	   {
		   alertmsg = "Tooling Service Interval Qty is required";
		   $("#techAlert").text(alertmsg);
		   $("#serviceIntervalQty").focus();
		   return false;
	   }
	   
	   else
	   {
		     $("#techAlert").empty();  
		     HideDialog();

		     $("#loadoverlay").fadeIn();  
		     $("#processloading").fadeIn();
		    	
		     if(process == "Save")
		     {            	
		         $("#action").val("Save");      
		         $("#frmProduct").attr("action","addProduct.jsf");               
		     }  
		     else if(process == "Update")
		     {                           
		    	 $("#action").val("Update"); 
		    	 $("#frmProduct").attr("action","updateProduct.jsf");               
		     }       
		     $("#frmProduct").submit(); 
	   }
  }	
           
   function ShowDialog()
   {	   
      $("#overlay").show();
      $("#productDialog").fadeIn(300);     
      $("#overlay").unbind("click");                       
   }

   function HideDialog()
   {
      $("#overlay").hide();
      $("#productDialog").fadeOut(300);
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
	        url : "${pageContext.request.contextPath}/getProduct.jsf?productId="+id,
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

	  	         $("#productName").val(trim(result.productName)); 
	  	  	     $("#drawingNo").val(trim(result.drawingNo));
	  	  	 	 $("#compForce").val(trim(result.compForce));
	  	  		 $("#punchSetNo").val(trim(result.punchSetNo));
	  	  	     $("#attNo").val(trim(result.attNo));
	  		//	 $("#machineType").val(trim(result.machineType));
	  			 $("#typeOfTool").val(result.typeOfTool);
	  			 $("#mocPunches").val(trim(result.mocPunches));
	  			 $("#mocDies").val(trim(result.mocDies));
	  			 $("#shape").val(trim(result.shape));
	  			 $("#dimensions").val(trim(result.dimensions));
	  			/*  $("#breaklineUpper").val(trim(result.breaklineUpper));
	  			 $("#breaklineLower").val(trim(result.breaklineLower)); */
	  			 $("#embosingUpper").val(trim(result.embosingUpper));
	  			 $("#embosingLower").val(trim(result.embosingLower));
	  			// $("#laserMaking").val(trim(result.laserMaking));
	  			 $("#hardChromePlating").val(trim(result.hardChromePlating));
	  			// $("#dustCapGroove").val(trim(result.dustCapGroove));
	  			// $("#inspectionReport").val(trim(result.inspectionReport));
	  			// $("#dqDocument").val(trim(result.dqDocument));
	  			// $("#mocCertificate").val(trim(result.mocCertificate));
	  			// $("#ductCapMOCCertificate").val(trim(result.ductCapMOCCertificate));
	  			// $("#toolingLife").val(trim(result.toolingLife));
	  			 $("#serviceIntervalQty").val(trim(result.serviceIntervalQty));
	  			 $("#uom").val(trim(result.uom));
	  			 $("#acceptedQty").val(result.acceptedQty);
	  			// $("#expiryPeriod").val(result.expiryPeriod);
	  			 var path = result.uploadedPath;
	  			 if(path != null)
	  			 {
	  				$("#uploadedUrl").html("<a href='"+path+"'>"+path.substring(path.lastIndexOf("/")+1)+"</a>");
		  			$("#uploadedPath").val(path);
	  			}
	  			 
	  	         $("#productName").focus();
	  	         
	  	         $("#productId").val(result.productId);          
	  	         $("#isActive").val(result.isActive);
	  	         $("#delStatus").val("0");
	  	          
	  	          ShowDialog(); 
	  	          $("#btnprocess").val("Update");	
	  	          $("#productName").focus();
	    	   }
	        }
	    }); 
   }
    
   function fundelete(delid)          
   {   
	   if($("#dynmsg").text() != "")
	   {
		 removemsg();
	   } 
	   $("#productId").val(delid); 
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
       var url = "${pageContext.request.contextPath}/updateProductStatus.jsf?productId="+id;   	   
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
				    	$("#frmProduct").attr("action","pages/SessionExpire.jsp");   	                
					    $("#frmProduct").submit();
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
							   $("#deleteAnchor"+rowid).attr('title' , "Cannot delete, Product in use"); 
					
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
	   return stringTrim;
	  	//return stringTrim.replace(/^\s+|\s+$/g,"");
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
			  $("#productName").focus(); 
		    }
		    else
		    {
		    	showmsg();
		    	$("#searchProduct").focus();
		    }
	  }
	  else 
	  {
		  $("#searchProduct").focus();
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
	   var searchVal = trim($("#searchProduct").val());   
	   if(searchVal == "")
  	   {   	
		   showAllListDialog();
  	   }
	   else
 	   {   		    
		   $("#acceptedQty").val(0);
		 //  $("#expiryPeriod").val(0);
		   
   	       $("#action").val("list");  	
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();	 			                    	                  
 	 	   $("#frmProduct").attr("action","searchProduct.jsf");   	                
 	       $("#frmProduct").submit();
 	   }	  
   }
   function funAllProcessList()
   {  		    	
	       $("#action").val("listdata");  
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();   	 			                    	                  
 	 	   $("#frmProduct").attr("action","showProduct.jsf");   	                
 	       $("#frmProduct").submit();	      	 	
   }   	  
   
   function funClearProducts()
   {
	     $("#productName").val(""); 
  	     $("#drawingNo").val("");	   
  	   
		 //$("#machineType").val("Required");
		 $("#typeOfTool").val("");
		 $("#mocPunches").val("");
		 $("#mocDies").val("");
		 $("#shape").val("");
		 $("#dimensions").val("");
		 /* $("#breaklineUpper").val("Required");
		 $("#breaklineLower").val("Required"); */
		 $("#embosingUpper").val("");
		 $("#embosingLower").val("");
		// $("#laserMaking").val("Required");
		 $("#hardChromePlating").val("");
		 $("#searchProduct").val("");
		// $("#dustCapGroove").val("");
		// $("#inspectionReport").val("Required");
		// $("#dqDocument").val("");
		// $("#mocCertificate").val("Required");
		// $("#ductCapMOCCertificate").val("");
		 //$("#toolingLife").val("");
		 $("#serviceIntervalQty").val("");
		 $("#uom").val("");
         $("#productName").focus();
   }
</script>

</head>
<body class="body" onload="init();">
<form name="frmProduct" id="frmProduct" method="post" autocomplete="off" enctype="multipart/form-data">
<%@ include file="tiimMenu.jsp"%>
<%
	HashMap<String, RoleVsUser> hmRoleVsUser1 = (HashMap<String, RoleVsUser>)session.getAttribute("RoleVsUser");
	RoleVsUser roleVsUser1 = hmRoleVsUser1.get("Product");
	if(roleVsUser1 != null)
	{
	 addAccess = roleVsUser1.getAddAccess1();
	 editAccess = roleVsUser1.getEditAccess1();
	 deleteAccess = roleVsUser1.getDeleteAccess1();
	}
%>
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
	
	<div id="productDialog" style="height:auto" class="max_dialog_Contact web_dialog"> 
       <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
		   <tr><td></td></tr>	     
		   <tr>
		   		<td>
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">	     
		              <tr>
				          <td width="20%" align="left" class="popuptoptitlelarge">Product Info</td>	
				          <td width="60%" align="center" class="popuptopalert">&nbsp;&nbsp;<span id="techAlert">${message}</span></td>	        
				          <td width="20%" align="right" class="popuptoptitle"><a href="javascript:mstProductCancelClose();" title="Click to Close" class="popupanchor" >X</a>&nbsp;</td>
				      </tr>	 		     
				      <tr><td colspan="2"></td></tr>				      
				   </table>   
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
				      <tr>
				          <td class="formlabelblack" width="24%" align="left"></td>		
			              <td class="formlabelblack" width="28%" align="left"></td>
			              <td class="formlabelblack" width="22%" align="left"></td>
			              <td class="formlabelblack" width="26%" align="left"></td>
				     </tr>
				      <tr>
			             <td class="formlabelblack" align="left">Product Name&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left" colspan="3"><input type="text" name="productName" id="productName" value="" onkeypress="javascript:funTypeProductName(event,this);" size="60" maxlength="250"/></td>
			             <!-- <td class="formlabelblack" align="left" colspan="2"></td>	 -->				             			                 
			         </tr>       				        			         
			         <tr height="2px"></tr>	     			          					 
			         <tr>
			              <td class="formlabelblack" align="left">Drawing No.&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="drawingNo" id="drawingNo" value=""  onkeypress="javascript:funTypeDrawingNo(event,this);" value="" class="textsmall" maxlength="100"/></td>
			              <td class="formlabelblack" align="left">Max Comp Force Limit&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="compForce" id="compForce" value=""  onkeypress="javascript:funTypeDrawingNo(event,this);" value="" class="textsmall" maxlength="100"/></td>
			              
			              <!-- <td class="formlabelblack" align="left">Strength&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left" colspan="3">			              			             
				              <input type="text" name="strength" id="strength" value="" onkeypress="javascript:funTypeStrength(event,this);" class="textsmall" maxlength="50"/>		     
			              </td>	 -->		              
					 </tr>
					 <tr height="2px"></tr>
					 <tr>
					   <%--   <td class="formlabelblack" align="left">Machine Type&nbsp;</td>
			             <td class="formlabelblack" align="left">
			             	<select name="machineType" id="machineType">
			             		<option value="">Select</option>
			             		<c:forEach items="${lstMachineType}" var="machineType" varStatus="row">    
			                       <option value="${machineType}">${machineType}</option>
			                    </c:forEach>
		                	</select>
			             </td>	 --%>
			             <td class="formlabelblack" align="left">Type of Tooling&nbsp;</td>		
			             <td class="formlabelblack" align="left">
			            <!--  <input type="text" name="typeOfTool" id="typeOfTool" value="" onkeypress="javascript:funTypeTool(event,this);" class="textsmall" maxlength="150"/> -->
			             <select name="typeOfTool" id="typeOfTool">
			             		<option value="">Select</option>
			                    <option value="D/D">D/D</option>
			                    <option value="B/B">B/B</option>
			                    <option value="D/B"> D/B</option>
			                    <option value="B/BB">B/BB</option>
			                    <option value="B/BBS">B/BBS</option>
			                </select>
			             </td>		
			             <td class="formlabelblack" align="left">Punch Set NO.&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="punchSetNo" id="punchSetNo" value=""  onkeypress="javascript:funTypeDrawingNo(event,this);" value="" class="textsmall" maxlength="100"/></td>
			              		     
			         </tr>			                
			         <tr height="2px"></tr>			        			        
			         <tr>
			             <td class="formlabelblack" align="left">MOC - Punches&nbsp;</td>
			             <td class="formlabelblack" align="left">
			             <!-- <input type="text" name="mocPunches" id="mocPunches" value="" class="textsmall" onkeypress="javascript:funTypeMOCPunches(event,this);" maxlength="50"/> -->
			             <select name="mocPunches" id="mocPunches">
			             		<option value="">Select</option>
			                    <option value="O1">O1</option>
			                    <option value="S7">S7</option>
			                    <option value="D2">D2</option>
			                    <option value="D3">D3</option>
			                    <option value="S1">S1</option>
			                    <option value="440C">440C</option>
			                    <option value="Others">Others</option>
			                </select>
			             </td>
			             <td class="formlabelblack" align="left">MOC - Dies&nbsp;</td>		 
			             <td class="formlabelblack" align="left">
			             <!-- <input type="text" name="mocDies" id="mocDies" value="" class="textsmall" onkeypress="javascript:funTypeMOCDies(event,this);" maxlength="50"/> -->
			             	<select name="mocDies" id="mocDies">
			             		<option value="">Select</option>
			                    <option value="D3">D3</option>
			                    <option value="D2">D2</option>
			                    <option value="S7">S7</option>
			                    <option value="TC">TC</option>
			                </select>
			             </td>
			         </tr>
			         <tr height="2px"></tr>			         
			         <tr>
			             <td class="formlabelblack" align="left">Shape of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left">
			             <!-- <input type="text" name="shape" id="shape" value="" class="textsmall" onkeypress="javascript:funTypeShape(event,this);" maxlength="50"/> -->
			             <select name="shape" id="shape">
			             		<option value="">Select</option>
			                    <option value="Round">Round</option>
			                    <option value="Capsule">Capsule</option>
			                    <option value="Oval">Oval</option>
			                    <option value="Modified Capsule">Modified Capsule</option>
			                    <option value="Square">Square</option>
			                    <option value="Triangle">Triangle</option>
			                    <option value="Hex">Hex</option>
			                    <option value="Modified Capsule">Modified Capsule</option>
			                    <option value="Diamond">Diamond</option>
			                    <option value="Heart">Heart</option>
			                    <option value="Rectangle">Rectangle</option>
			                     <option value="Barrel">Barrel</option>
			                    <option value="Pentagon">Pentagon</option>
			                    <option value="Others">Others</option>
			                </select>
			             </td>
			             <td class="formlabelblack" align="left">Dimensions of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="dimensions" id="dimensions" value="" class="textsmall" onkeypress="javascript:funTypeDimension(event,this);" maxlength="50"/></td>				                  
			         </tr>	
			         <tr height="2px"></tr>			         
			        <!--  <tr>			             	
			             <td class="formlabelblack" align="left">Breakline Upper&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			                <select name="breaklineUpper" id="breaklineUpper">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select>
			             </td>	
			             <td class="formlabelblack" align="left">Breakline Lower&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             	<select name="breaklineLower" id="breaklineLower">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select>
			             </td>	                 
			         </tr>	
			         <tr height="2px"></tr> -->
			         <tr>
			         	 <td class="formlabelblack" align="left">Embossing Upper&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             <input type="text" name="embosingUpper" id="embosingUpper" value="" class="textsmall" maxlength="50"/>
			             	<!-- <select name="embosingUpper" id="embosingUpper">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select> -->
			             <td class="formlabelblack" align="left">Embossing Lower&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             <input type="text" name="embosingLower" id="embosingLower" value="" class="textsmall" maxlength="50"/>
			             	<!-- <select name="embosingLower" id="embosingLower">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select> -->
			             </td>			                 
			         </tr>		         
			         <tr height="2px"></tr>
			         <tr>
			         	<!--  <td class="formlabelblack" align="left">Laser Making&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
		             		<select name="laserMaking" id="laserMaking">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select>
			             </td> -->
			             <td class="formlabelblack" align="left">Plating&nbsp;</td>
			             <td class="formlabelblack" align="left">
			             
			            	<select name="hardChromePlating" id="hardChromePlating">
			             		<option value="">Select</option>
			                    <c:forEach items="${lstPlating}" var="plating" varStatus="row"> 
				             	    <c:set var="selected" scope="request" value="${''}"/>   
				             	    <c:if test="${plating ne NULL && plating.platingName eq hardChromePlating}">
										<c:set var="selected" scope="request" value="${'SELECTED'}"/>  
				  				 	 </c:if>
				                     <option value="${plating.platingName}" ${selected} >${plating.platingName}</option>
			                    </c:forEach>
		                	</select>
	
			             </td>			                 
			         </tr>
			       <!--  <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">DustCup&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             <!-- <input type="text" name="dustCapGroove" id="dustCapGroove" value="" class="textsmall" onkeypress="javascript:funTypeDustGap(event, this);" maxlength="45" /> 
			               <select name="dustCapGroove" id="dustCapGroove">
			               		<option value="">Select</option>
			                    <option value="Std">Std</option>
			                    <option value="Bellow">Bellow</option>
			                    <option value="Others">Others</option>
			                </select>  
			             
			             </td>
			              <td class="formlabelblack" align="left">Inspection Report&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			                <select name="inspectionReport" id="inspectionReport">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select>
			             </td> 		                 
			         </tr>-->	
			        <!-- <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">DQ Document&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             	<select name="dqDocument" id="dqDocument">
			             		<option value="">Select</option>
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select>
			             </td>
			              <td class="formlabelblack" align="left">MOC Certificate&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
		             		<select name="mocCertificate" id="mocCertificate">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
		                	</select>
			             </td>	 		                 
			         </tr>-->
			         <tr height="2px"></tr>
			        <!--  <tr>
			             <td class="formlabelblack" align="left">Tooling Life&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="toolingLife" id="toolingLife" value="" class="textsmall"  maxlength="10" /></td>			                 
			         </tr> -->
			         <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Tooling Service Interval Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="serviceIntervalQty" id="serviceIntervalQty" value="" class="textsmall"  maxlength="100"/></td>
			             <td class="formlabelblack" align="left">UOM&nbsp;</td>
			             <td class="formlabelblack" align="left"><input type="text" name="uom" id="uom" value="" class="textsmall"  maxlength="100"/></td>                
			         </tr>
			         <tr height="2px"></tr>	
			        <!--  <tr>
			         	 <td class="formlabelblack" align="left">Expiry&nbsp;Period in Month<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="expiryPeriod" id="expiryPeriod" value="" class="textsmall"  maxlength="100"/></td>
			          </tr> -->
			         <tr height="2px"></tr>	
			         <tr>
			         	 <td class="formlabelblack" align="left">Upload File<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input id="profileFile" name="profileFile" type="file" class="file-loading"/>
			             <span id="uploadedUrl"></span>
			             </td>
			                            
			         </tr>
			         
			         <tr height="2px"></tr>	
			         <tr>
			             <td class="formlabelblack" align="left"><span class="formlabel">*</span>&nbsp;- required fields</td>
			             <td class="formlabelblack" align="left" colspan="3"><input  type="button" class="btn btnImportant" id="btnprocess" value="Save" onclick="javascript:processProduct();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btnNormal" id="btnClear" value="Clear" onclick="javascript:funClearProducts();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="btncancel"  class="btn btnCancel"  value="Close" onclick="javascript:mstProductCancelClose();" /></td>
			         </tr>	
			         <tr height="2px"><td>&nbsp;</td></tr>        
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
	          <td width="25%" class="heading" align="left">&nbsp;Product</td> 
	          <td width="50%" class="submenutitlesmall" align="center">
	               <table cellspacing="1" cellpadding="2" width="50%" align="center" style="display:none" id="msg">	
				     	<tr>  
					    	<td class="confirmationmessage" align="center">&nbsp;<span id="dynmsg">${message}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="popupanchor" id="msgclose">X</a></td>
					    </tr>
			       </table>
	          </td>
	          <td align="right" width="25%" class="anchorlabel">
			  &nbsp;
			  <%
			  if("1".equals(addAccess)){
			  %>
			  <input type="button" title="Click to Add" class='btn btnSMNormal' id="btnpop" value="Add"/>
			  <%
			  }else{
			  %>
			  <input type="button" title="Access denied" class='btn btnSMNormalDisable' value="Add" disabled="disabled"/>
			  <%}%>
			  &nbsp;			  
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
		          <td class="searchListLabel" align="left">Search&nbsp;&nbsp;<input type="text" name="searchProduct" id="searchProduct" placeholder="Enter Product Name" value="${searchProduct}" class="textmediumlarge" maxlength="50"/>&nbsp;&nbsp;<input type="button" name="btnSearch" id="btnSearch" value="Go" class="btn btnSMImportant"></td>  
		       </tr>
		    </table>  
		    <table cellpadding="0" cellspacing ="0" border="0" align="center"  width="100%" >
			 <tr>
				 <td height="10px"></td>		
			 </tr>
		    </table>  
		  </div>     
		             
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
				          <th width="20%">Product Name</th>
				          <th width="30%">Drawing No.</th>
				          <th width="35%">Type of Tooling</th>
				          <th width="15%">Action</th>
			          </tr>  
			     </thead> 
			     <tbody>
			         <c:forEach items="${lstProductDetail}" var="lstProductDetail" varStatus="row">  
				          <tr>
				             <td>${lstProductDetail.productName}</td>   
				             <td>${lstProductDetail.drawingNo}</td>
				             <td>${lstProductDetail.typeOfTool}</td>
				             <td align="center" style="text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;
				             <c:choose>
				             	<c:when test="${lstProductDetail.isEdit == 0}">
										<a title="Product is in progress. cann't edit" id="editAnchor${row.index}" href="#"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
										<a title="Product is in progress. Access denied" href="#" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>&nbsp;
										<a title="Product is in progress." href="#' id="statusAnchor${row.index}" ><img border="0" src="./images/deactivate.gif"/></a>
								</c:when>
				             	<c:otherwise>
									   <%
										  if("1".equals(editAccess)){
										%>
						             	<a title="Click to Edit" id="editAnchor${row.index}" href="javascript:funedit('${lstProductDetail.productId}','${row.index}','${lstProductDetail.isActive}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
						             	<%
						             	}else{
						             	%>
						             	<a title="Access denied" id="editAnchor${row.index}" href="#"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
						             	<%}%>  
									
				             	 		             					
								<c:choose>
									<c:when test="${lstProductDetail.isActive == 1}">
										<a title="Inactive entry can only be deleted" id="deleteAnchor${row.index}"><img border="0" id="delete${row.index}" src="./images/deleteicon_black.gif"/></a>&nbsp;&nbsp;<a title="Click to make this Inactive" href="javascript:funstatus('${lstProductDetail.productId}','${row.index}','${lstProductDetail.delStatus}');" id="statusAnchor${row.index}" ><img border="0" id="changeStatus${row.index}" style="" src="./images/activate.gif"/></a>
									</c:when>
									<c:when test="${lstProductDetail.delStatus == 0}">
										<%
										  if("1".equals(deleteAccess)){
										%>
										<a title="Click to Delete" href="javascript:fundelete('${lstProductDetail.productId}');" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
										<%
										  }else{
						             	%>  
						             	<a title="Access denied" href="#" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
						             	<%}%>
										&nbsp;<a title="Click to make this Active" href="javascript:funstatus('${lstProductDetail.productId}','${row.index}','${lstProductDetail.delStatus}');" id="statusAnchor${row.index}" ><img border="0" id="changeStatus${row.index}" style="" src="./images/deactivate.gif"/></a>
									</c:when>
									<c:otherwise>
									    <a title="Cannot delete, Inspector in use" id="deleteAnchor${row.index}"><img border="0" id="delete${row.index}" src="./images/deleteicon_black.gif"/></a>&nbsp;<a title="Click to make this Active" href="javascript:funstatus('${lstProductDetail.productId}','${row.index}','${lstProductDetail.delStatus}');" id="statusAnchor${row.index}" ><img border="0" style="" id="changeStatus${row.index}" src="./images/deactivate.gif"/></a>
									</c:otherwise>
								</c:choose>
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
<input type="hidden" name="productId" id="productId" value="${productId}"/>
<input type="hidden" name="isActive" id="isActive" value="${isActive}"/>
<input type="hidden" name="uploadedPath" id="uploadedPath"/>
<input type="hidden" name="deleteStatus" id="deleteStatus" />
<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />

</form>
</body>
</html>

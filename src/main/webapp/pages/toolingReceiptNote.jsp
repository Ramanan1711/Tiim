<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String addAccess = "";
String editAccess = "";
String deleteAccess = "";
String viewAccess = "";

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

<link href="./auto/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/> 
<link href="./auto/themes/base/jquery-ui-1.8.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="./auto/jquery-ui-1.8.18.min.js"></script>

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
	           colratio    : [100, 150, 150 , 150, 300, 120], 
	           height      : 350, 
	           width       : 970,             
	           sortable    : true, 
	           sortedColId : 0,    
	           unSortColumn: ['GRN No'],     
	           resizeCol   : true,            
	           minColWidth : 50,
	           pager		 : true,
			   rowsPerPage	 : 10,
	           whiteSpace	 : 'nowrap',
	           sortType    : ['integer', 'date', 'string', 'string', 'string', 'string']          
	      });  
		   $("body .ui-widget-content").css("overflow-x","hidden"); 
	                 
		   
		   $("#supplierName").autocomplete({
			    source: function (request, response) {
			        $.getJSON("${pageContext. request. contextPath}/autoSupplierName.jsf", {
			           // term: extractLast(request.term)
			        	supplierName: $("#supplierName").val()
			        }, response).success(function(data) {
	                       if(data.length == 0)
	                       {
	                           $("#supplierStatus").html("Data not Available");
	                       }
	                    });
			            
			    },
			    search: function (data ) {  // custom minLength
			    },
			    focus: function () {
			        // prevent value inserted on focus
			        return false;
			    },
			    autoFocus: true,
			    minLength: 2,
			    select: function (event, ui) {
			    	 $("#supplierStatus").html("");
			    	 $("#supplierCode").val(ui.item.id);
			         $("#supplierName").val(ui.item.value);
			    }
		   }); 
		   
		  
		   
	      $("#btnpop").click(function ()              
	      {			      	    	  
	    	  if($("#dynmsg").text() != "")
			  {
			    removemsg();
			  } 	    	
	    	  
	    	// $("#grnNo").val("");
	         $("#po").val("");
	         $("#supplierCode").val("");
	         $("#supplierName").val("");
	         $("#productSerialNo").val(""); 
	        // $("#toolingLotNumber").val(""); 
	    	 $("#productName").val(""); 
	    	 $("#drawingNo").val("");	   
	    	// $("#strength").val("");
			 //$("#machineType").val("Required");
			 $("#typeOfTool").val("");
			 $("#mocPunches").val("");
			 $("#mocDies").val("");
			 $("#shape").val("");
			 $("#dimensions").val("");
			 $("#breakLineUpper").val("Required");
			 $("#breakLineLower").val("Required");
			 $("#embosingUpper").val("");
			 $("#embosingLower").val("");
			// $("#laserMarking").val("Required");
			 $("#hardChromePlating").val("Required");
			 $("#searchToolingReceiptNote").val("");
			 $("#dustCapGroove").val("");
			 $("#poQuantity").val("");
			 $("#receivedQuantity").val("");
			 
			 $("#productNamePO").val(""); 
	  	     $("#drawingNoPO").val("");	   
	  	   //  $("#strengthPO").val("");
			 $("#typeOfToolPO").val("");
			 $("#mocPunchesPO").val("");
			 $("#mocDiesPO").val("");
			 $("#shapePO").val("");
			 $("#dimensionsPO").val("");
			// $("#breaklineUpperPO").val("Required");
			// $("#breaklineLowerPO").val("Required");
			// $("#embosingUpperPO").val("");
			// $("#embosingLowerPO").val("");
			 $("#laserMakingPO").val("Required");
			 $("#hardChromePlatingPO").val("Required");
			 $("#searchProductPO").val("");
			 $("#dustCapGroovePO").val("");
			 $("#uomPO").val("");
			 $("#dqDocument").val("");
			 $("#mocNumber").val("");
			// $("#btnList").hide();
	         ShowDialog(); 
	         $("#btnprocess").val("Save");	
	         $("#toolingLotNumber").focus();
	         $("#grnNo").focus();  	           
	      });
	      
	      $("#btnpopup").click(function ()        
 	      {		    	
	    	 if($("#dynmsg").text() != "")
			 {
			    removemsg();
			 }  		    	
	    	 $("#techAlert").empty();  	
	    	 
	    	// $("#grnNo").val("");
	         $("#po").val("");
	         $("#supplierCode").val("");
	         $("#supplierName").val("");
	         $("#productSerialNo").val(""); 
	        // $("#toolingLotNumber").val(""); 
	    	 $("#productName").val(""); 
	    	 $("#drawingNo").val("");	   
	    	 //$("#strength").val("");
			 //$("#machineType").val("Required");
			 $("#typeOfTool").val("");
			 $("#mocPunches").val("");
			 $("#mocDies").val("");
			 $("#shape").val("");
			 $("#dimensions").val("");
			// $("#breakLineUpper").val("Required");
			// $("#breakLineLower").val("Required");
			 $("#embosingUpper").val("");
			 $("#embosingLower").val("");
			// $("#laserMarking").val("Required");
			 $("#hardChromePlating").val("Required");
			 $("#searchToolingReceiptNote").val("");
			 $("#dustCapGroove").val("");
			 $("#poQuantity").val("");
			 $("#receivedQuantity").val("");
			 
			 $("#productNamePO").val(""); 
	  	     $("#drawingNoPO").val("");	   
	  	    // $("#strengthPO").val("");
			 $("#typeOfToolPO").val("");
			 $("#mocPunchesPO").val("");
			 $("#mocDiesPO").val("");
			 $("#shapePO").val("");
			 $("#dimensionsPO").val("");
			/*  $("#breaklineUpperPO").val("Required");
			 $("#breaklineLowerPO").val("Required"); */
			// $("#embosingUpperPO").val("");
			// $("#embosingLowerPO").val("");
			 $("#laserMakingPO").val("Required");
			 $("#hardChromePlatingPO").val("Required");
			 $("#searchProductPO").val("");
			 $("#dustCapGroovePO").val("");
			 $("#uomPO").val("");
			 $("#dqDocument").val("");
			 $("#mocNumber").val("");
			 $("#dqDocumentPO").val("");
			 $("#mocNumberPO").val("");
			// $("#btnList").hide();
 	         ShowDialog();	
 	         $("#btnprocess").val("Save");
 	         $("#toolingLotNumber").focus();
 	         $("#grnNo").focus();                 
 	      });
	     	      
	      $("#msgclose").click(function()            
		  {				    	
		  	  $("#msg").fadeOut(300);		  	         
		  }); 
  	       
		   $("#grnNo").keyup(function()           
		   {
		    	if(trim($("#grnNo").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	       });
		   
		   $("#po").keyup(function()           
				   {
		    	if(trim($("#po").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	       });
		   
		   $("#supplierCode").keyup(function()           
		   {
		    	if(trim($("#supplierCode").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	       });
		   
		   $("#supplierName").keyup(function()           
		   {
		    	if(trim($("#supplierName").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	       });
		   
		   $("#productSerialNo").keyup(function()           
		   {
		    	if(trim($("#productSerialNo").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	       });
		   
		  $("#toolingLotNumber").keyup(function()           
	      {
		    	if(trim($("#toolingLotNumber").val()) != "")
		    	{
		    		$("#techAlert").empty();
		    	}		    	
		    	if($("#dynmsg").text() != "")
		    	{
		    		removemsg();
		    	}
	      }) ; 
	      
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

	   

		  $("#machineType").keydown(function(evt)             
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
   	      }) ;

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
		  
		  $("#breakLineUpper").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#breakLineUpper").val()) != "")
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
   			 		 	$("#breakLineLower").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ;
		  
		  $("#breakLineLower").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#breakLineLower").val()) != "")
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
   	      }) ;
		  
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
   			 		 	$("#dustCapGroove").focus();
   			 		 	return false;
   		    		},300);
   	    	    }
   	      }) ;
		  
		  $("#dustCapGroove").keydown(function(evt)             
   	      {   		    	
   		    	if(trim($("#dustCapGroove").val()) != "")
   		    	{
   		    		$("#techAlert").empty();
   		    	}
   		    	if($("#dynmsg").text() != "")
   		    	{
   		    		removemsg();
   		    	}
   	      }) ;
		  
		  $("#poQuantity").keypress(function(evt)             
  	      {   		    	
  		    	if(trim($("#poQuantity").val()) != "")
  		    	{
  		    		$("#techAlert").empty();
  		    	}
  		    	if($("#dynmsg").text() != "")
  		    	{
  		    		removemsg();
  		    	}
  	      }) ;

	      $("#receivedQuantity").keypress(function(event) {        
	    	  if(event.which == 13 ) 
		      {	    		  
	    		   $("#techAlert").empty();
	    		   var alertmsg = "Product Name is required";
	    		   var productName =  trim($("#productName").val());
	    		   var drawingNo =  trim($("#drawingNo").val());
	    		  /// var strength =  trim($("#strength").val());
	    		   var typeOfTool = trim($("#typeOfTool").val());
	    		   var mocPunches = trim($("#mocPunches").val());
	    		   var mocDies = trim($("#mocDies").val());
	    		   var shape = trim($("#shape").val());
	    		   var dimensions = trim($("#dimensions").val());
	    		   var dustCapGroove = trim($("#dustCapGroove").val());
	    		   var poQuantity = trim($("#poQuantity").val());
	    		   var receivedQuantity = trim($("#receivedQuantity").val());
	    		   var process = $("#btnprocess").val(); 
	    		   /* if(productName == "")
	    	       {                                        	 	         
	    	           $("#techAlert").text(alertmsg);
	    	      	   $("#productName").focus();
	    	      	   return false;
	    	       }
	    		   else */ 
	    		   if(drawingNo == "")
	    		   {
	    			   alertmsg = "Drawing No. is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#drawingNo").focus();
	    			   return false;
	    		   }
	    		  
	    		   else if(typeOfTool == "")
	    		   {
	    			   alertmsg = "Type of Tooling is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#typeOfTool").focus();
	    			   return false;
	    		   }
	    		   else if(mocPunches == "")
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
	    		   }
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
	    		   else if(dustCapGroove == "")
	    		   {
	    			   alertmsg = "Dustcup & Groove is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#dustCapGroove").focus();
	    			   return false;
	    		   }
	    		   else if(poQuantity == "")
	    		   {
	    			   alertmsg = "PO Qty is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#poQuantity").focus();
	    			   return false;
	    		   }
	    		  /*  else if($("#dqDocument").val() == "")
	    		   {
	    			   alertmsg = "DQ document number is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#dqDocument").focus();
	    			   return false;
	    			 
	    		   }
	    		   else if($("#mocNumber").val() == "")
	    		   {
	    			   alertmsg = "MOC Certificate number is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#mocNumber").focus();
	    			   return false;
	  	  			
	    		   }
	    		   else if($("#dqDocumentPO").val() == "")
	    		   {
	    			   alertmsg = "DQ document number PO is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#dqDocumentPO").focus();
	    			   return false;
	    		   }
	    		   else if($("#mocNumberPO").val() == "")
	    		   {
	    			   alertmsg = "MOC Certificate number PO is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#mocNumberPO").focus();
	    			   return false;
	    		   } */
	    		   else if(receivedQuantity == "")
	    		   {
	    			   alertmsg = "Received Qty is required";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#receivedQuantity").focus();
	    			   return false;
	    		   }else if(parseInt(receivedQuantity) <  parseInt($("#minAcceptedQty").val()))
	    		   {
	    			   alertmsg = "Received Qty should be greather than Min quantity";
	    			   $("#techAlert").text(alertmsg);
	    			   $("#receivedQuantity").focus();
	    		   }
	    		   else
    			   {
	    			   
	    			     $("#techAlert").empty();  
	    			     if(process == "Save")
	    			     {    
	    			    	// $("#btnprocess").prop("disabled", true);
	    			    	 $("#frnReceiptNote").attr("action","addToolingReceiptNote.jsf");   
	    					 $("#frnReceiptNote").submit(); 
	    						
	    			        /*  var datastring = $("form#frnReceiptNote").serialize();
	    			         $.ajax({
	    						dataType : 'text',
	    				        url : "${pageContext.request.contextPath}/addToolingReceiptNote.jsf",
	    				        data : datastring,
	    				        type : "POST",
	    				        success : function(result) 
	    				        {
	    				           var detail = result.split("</>");
	    				  		   if(detail != "")
	    				    	   {
	    				  			   var alertmsg = trim(detail[0]); ;
	    				  			   var toolingReceiptId = trim(detail[1]);
	    				  			   
	    				  			   $("#techAlert").text(alertmsg);
	    				  			   $("#toolingReceiptId").val(toolingReceiptId);
	    				  			   
	    				  			   //$("#btnList").show();
	    				  			   funClearAfterProcess();
	    				    	   }
	    				        }
	    			         }); */
	    			     }  
	    			     else if(process == "Update")
	    			     {           
	    			    	 $("#btnprocess").prop("disabled", true);
	    			    	 var datastring = $("form#frnReceiptNote").serialize();
		    			         $.ajax({
		    						dataType : 'text',
		    				        url : "${pageContext.request.contextPath}/updateToolingReceiptNote.jsf",
		    				        data : datastring,
		    				        type : "POST",
		    				        success : function(result) 
		    				        {
		    				           var detail = result.split("</>");
		    				  		   if(detail != "")
		    				    	   {
		    				  			   var alertmsg = trim(detail[0]); ;
		    				  			   var toolingReceiptId = trim(detail[1]);
		    				  			   
		    				  			   $("#techAlert").text(alertmsg);
		    				  			   $("#toolingReceiptId").val(toolingReceiptId);
		    				  			   
		    				  			  // $("#btnList").show();
		    				  			   funClearAfterProcess();
		    				    	   }
		    				        }
		    			         });         
	    			     } 
    			   }
	    	  }
	    	  else
    		  {
	    		  if(event.which != 8 && event.which != 0 && ((event.which < 48) || (event.which > 57)))
		   		  {
		   			   return false;   
		   		  }
		   		  else
		   		  {
		   			   return true;
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
	   	   	  var toolingReceiptId = trim($("#productId").val());
	    	  $("#frnReceiptNote").attr("action","deleteReceiptNote.jsf?toolingreceiptid="+toolingReceiptId);   	                
	          $("#frnReceiptNote").submit(); 
   	      });  	        
   	      
   	   $("#btnProcessyes").click(function ()      
  	   	      {		  
  					hideAllListDialog();	
  					funAllProcessList();
  	   	      });
   	   $("#btnProcessclose").click(function ()  
      	      {		    	   	    	  	    			    		    	
  					hideAllListDialog();
  					$("#searchToolingReceiptNote").focus();
      	      });  
   	    $("#btnProcessno").click(function ()      
      	      {		    	   	    	  	    			    		    	
  	    			hideAllListDialog();	
  	    			$("#searchToolingReceiptNote").focus();
      	      });   	
   	   $("#searchToolingReceiptNote").keypress(function(e){
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
	
	// $("#grnNo").val("");
     $("#po").val("");
     $("#supplierCode").val("");
     $("#supplierName").val("");
     $("#productSerialNo").val(""); 
     $("#toolingLotNumber").val(""); 
	 $("#productName").val(""); 
	 $("#drawingNo").val("");	   
	// $("#strength").val("");
	 //$("#machineType").val("Required");
	 $("#typeOfTool").val("");
	 $("#mocPunches").val("");
	 $("#mocDies").val("");
	 $("#shape").val("");
	 $("#dimensions").val("");
	 $("#breakLineUpper").val("Required");
	 $("#breakLineLower").val("Required");
	 $("#embosingUpper").val("");
	 $("#embosingLower").val("");
	// $("#laserMarking").val("Required");
	 $("#hardChromePlating").val("Required");
	 $("#searchToolingReceiptNote").val("");
	 $("#dustCapGroove").val("");
	 $("#poQuantity").val("");
	 $("#receivedQuantity").val("");
	// $("#btnList").hide();
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
	 $("#frnReceiptNote").attr("action","./showToolingReceiptNote.jsf");
	 $("#frnReceiptNote").submit();
  }
  
  function funTypeGRNNo(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else
		   {
			   $("#po").focus();
		   }
	   }
	   else
	   {
		   if(charCode != 8 && charCode != 0 && ((charCode < 48) || (charCode > 57)))
		   {
			   return false;   
		   }
		   else
		   {
			   return true;
		   }
	   }
  }
  
  function funTypePO(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else
		   {
			   $("#supplierCode").focus();
		   }
	   }
  }
  
  function funTypeSupplierCode(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
		   else
		   {
			   $("#supplierName").focus();
		   }
	   }
  }
  function funTypeSupplierName(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
		   else
		   {
			   $("#productSerialNo").focus();
		   }
	   }
  }
  function funTypeProductSNo(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
		   else
		   {
			   $("#toolingLotNumber").focus();
		   }
	   }
  }
  
  function funTypeProductToolingLotNumber(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	      
		   else
		   {
			   $("#productName").focus();
		   }
	   }
	   else
	   {
		   return true;
		   /* if(charCode != 8 && charCode != 0 && ((charCode < 48) || (charCode > 57)))
		   {
			   return false;   
		   }
		   else
		   {
			   return true;
		   } */
	   }
  }
  
function funDatePick(evt,obj)
		  {
		  $("#manDate").datepicker(
		  {
		  showOn:"both",
		  buttonImage:"./images/calendarIcon.png",
		  dateFormat:"mm/dd/yy",
		  buttonImageOnly:true,
	  //  minDate:+0, //you do not want to show previous date.
		  maxDate:+0   // you do not want to show next day.
		  });
		  };
	/* function  funCalExpiryDate(evt,obj)
		  {
		/* exphidden= $("#exphidden").val();
		alert(exphidden);
	
		
			 var manDate=new Date($("#manDate").val());

			var month =  manDate.getMonth() +  parseInt($("#exphidden").val());
			 manDate.setMonth(month);
			 $("#expiryDate").val( (manDate.getMonth() + 1 )+'/'+manDate.getDate()+'/'+manDate.getFullYear());
			 	
	
		  } */
  function funTypeProductName(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productName").focus();
	      	   return false;
	       }
	       else if (manDates == "")
	      {
	    	   alertmsg = "Manufacture Date is required";
	    	   $("#techAlert").text(alertmsg);
	    	   $("#manDates").focus();
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
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		   else
		   {
			   $("#strength").focus();
		   }
	   }
  }

  function funTypeStrength(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		  // var strength =  trim($("#strength").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		   
		   else
		   {
			   $("#machineType").focus();
		   }
	   }
  }
  
  function funTypeTool(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   //var strength =  trim($("#strength").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		   else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   }
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
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		 //  var strength =  trim($("#strength").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		  
		   else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   }
		   else if(mocPunches == "")
		   {
			   alertmsg = "MOC - Punches is required";
			   $("#techAlert").text(alertmsg);
			   $("#mocPunches").focus();
			   return false;
		   }
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
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		 //  var strength =  trim($("#strength").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		  
		   else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   }
		   else if(mocPunches == "")
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
		   }
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
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   //var strength =  trim($("#strength").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   var shape = trim($("#shape").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		  
		   else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   }
		   else if(mocPunches == "")
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
		   }
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
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		   //var strength =  trim($("#strength").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   var shape = trim($("#shape").val());
		   var dimensions = trim($("#dimensions").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		  
		   else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   }
		   else if(mocPunches == "")
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
		   }
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
			   $("#breakLineUpper").focus();
		   }
	   }
 }
  
  function funTypeDustGap(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;  
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		  // var strength =  trim($("#strength").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   var shape = trim($("#shape").val());
		   var dimensions = trim($("#dimensions").val());
		   var dustCapGroove = trim($("#dustCapGroove").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		   
		   else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   }
		   else if(mocPunches == "")
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
		   }
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
		   else if(dustCapGroove == "")
		   {
			   alertmsg = "Dust Gap & Groove is required";
			   $("#techAlert").text(alertmsg);
			   $("#dustCapGroove").focus();
			   return false;
		   }
		   else
		   {
			   $("#poQuantity").focus();
		   }
	   }
  }
  
  function funTypePOQty(evt,obj)
  {
	   $("#techAlert").empty();
	   var charCode = (evt.which) ? evt.which : evt.keyCode ;
	   if(charCode == 13)
	   {
		   var alertmsg = "GRN No is required";
		   var grnNo = trim($("#grnNo").val());
		   var po = trim($("#po").val());
		   var supplierCode = trim($("#supplierCode").val());
		   var supplierName = trim($("#supplierName").val());
		   var productSerialNo = trim($("#productSerialNo").val());
		   var toolingLotNumber = trim($("#toolingLotNumber").val());
		   var productName =  trim($("#productName").val());
		   var drawingNo =  trim($("#drawingNo").val());
		  // var strength =  trim($("#strength").val());
		   var typeOfTool = trim($("#typeOfTool").val());
		   var mocPunches = trim($("#mocPunches").val());
		   var mocDies = trim($("#mocDies").val());
		   var shape = trim($("#shape").val());
		   var dimensions = trim($("#dimensions").val());
		   var dustCapGroove = trim($("#dustCapGroove").val());
		   var poQuantity = trim($("#poQuantity").val());
		   if(grnNo == "")
	       {                                        	 	         
	           $("#techAlert").text(alertmsg);
	      	   $("#grnNo").focus();
	      	   return false;
	       }
		   else if(po == "")
	       {       
			   alertmsg = "PO No is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#po").focus();
	      	   return false;
	       }
		   else if(supplierCode == "")
	       {       
			   alertmsg = "Supplier Code is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierCode").focus();
	      	   return false;
	       }
	       else if(supplierName == "")
	       {       
			   alertmsg = "Supplier Name is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#supplierName").focus();
	      	   return false;
	       }
	       else if(productSerialNo == "")
	       {       
			   alertmsg = "Product S.No as Per PO is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(toolingLotNumber == "")
	       {       
			   alertmsg = "Tooling Lot Number is required";
	           $("#techAlert").text(alertmsg);
	      	   $("#productSerialNo").focus();
	      	   return false;
	       }
	       else if(productName == "")
	       {     
	    	   alertmsg = "Product Name is required";
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
		   
		   else if(typeOfTool == "")
		   {
			   alertmsg = "Type of Tooling is required";
			   $("#techAlert").text(alertmsg);
			   $("#typeOfTool").focus();
			   return false;
		   }
		   else if(mocPunches == "")
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
		   }
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
		   else if(dustCapGroove == "")
		   {
			   alertmsg = "Dust Gap & Groove is required";
			   $("#techAlert").text(alertmsg);
			   $("#dustCapGroove").focus();
			   return false;
		   }
		   else if(poQuantity == "")
		   {
			   alertmsg = "PO Qty is required";
			   $("#techAlert").text(alertmsg);
			   $("#poQuantity").focus();
			   return false;
		   }
		   else
		   {
			   $("#receivedQuantity").focus();
		   }
	   }
	   else
	   {
		   if(charCode != 8 && charCode != 0 && ((charCode < 48) || (charCode > 57)))
		   {
			   return false;   
		   }
		   else
		   {
			   return true;
		   }
	  }
  }
  
  function processReceiptNote()                   
  {  	    	  	       	
	   $("#techAlert").empty();
	    
	   var alertmsg = "GRN No is required";
	   var grnNo = trim($("#grnNo").val());
	   var po = trim($("#po").val());
	   var supplierCode = trim($("#supplierCode").val());
	   var supplierName = trim($("#supplierName").val());
	   var productSerialNo = trim($("#productSerialNo").val());
	   var toolingCodeNumber = trim($("#toolingCodeNumber").val());
	   var toolingLotNumber = trim($("#toolingLotNumber").val());
	   var productName =  trim($("#productName").val());
	   var drawingNo =  trim($("#drawingNo").val());
	  // var strength =  trim($("#strength").val());
	   var typeOfTool = trim($("#typeOfTool").val());
	   var mocPunches = trim($("#mocPunches").val());
	   var mocDies = trim($("#mocDies").val());
	   var shape = trim($("#shape").val());
	   var dimensions = trim($("#dimensions").val());
	   var dustCapGroove = trim($("#dustCapGroove").val());
	   var poQuantity = trim($("#poQuantity").val());
	   var receivedQuantity = trim($("#receivedQuantity").val());
	   var inspectionReportNumber = trim($("#inspectionReportNumber").val());
	   var process = $("#btnprocess").val();
	   
	   var uomPO = $("#uomPO").val();
	   var storageLocation = $("#storageLocation").val();
	   var upperQnty = $("#upperQnty").val();
	   var lowerQnty = $("#lowerQnty").val();
	   var dieQnty = $("#dieQnty").val();
	   var manDate = $("#manDate").val();
	   //var expiryDate = $("#expiryDate").val();
	   
	   if(grnNo == "")
       {                                        	 	         
           $("#techAlert").text(alertmsg);
      	   $("#grnNo").focus();
      	   return false;
       }
	   else if(po == "")
       {       
		   alertmsg = "PO No is required";
           $("#techAlert").text(alertmsg);
      	   $("#po").focus();
      	   return false;
       }
	   else if(supplierCode == "")
       {       
		   alertmsg = "Supplier Code is required";
           $("#techAlert").text(alertmsg);
      	   $("#supplierCode").focus();
      	   return false;
       }
       else if(supplierName == "")
       {       
		   alertmsg = "Supplier Name is required";
           $("#techAlert").text(alertmsg);
      	   $("#supplierName").focus();
      	   return false;
       }
       else if(productSerialNo == "")
       {       
		   alertmsg = "Product S.No as Per PO is required";
           $("#techAlert").text(alertmsg);
      	   $("#productSerialNo").focus();
      	   return false;
       }
      /*  else if(toolingCodeNumber == "")
       {
    	   alertmsg = "Tooling code number is required";
           $("#techAlert").text(alertmsg);
      	   $("#toolingCodeNumber").focus();
      	   return false;
    	} */
       else if(toolingLotNumber == "")
       {       
		   alertmsg = "Tooling Lot Number is required";
           $("#techAlert").text(alertmsg);
      	   $("#productSerialNo").focus();
      	   return false;
       }
       /* else if(productName == "")
       {     
    	   alertmsg = "Product Name is required";
           $("#techAlert").text(alertmsg);
      	   $("#productName").focus();
      	   return false;
       } */
	   else if(drawingNo == "")
	   {
		   alertmsg = "Drawing No. is required";
		   $("#techAlert").text(alertmsg);
		   $("#drawingNo").focus();
		   return false;
	   }
	  
	   else if(typeOfTool == "")
	   {
		   alertmsg = "Type of Tooling is required";
		   $("#techAlert").text(alertmsg);
		   $("#typeOfTool").focus();
		   return false;
	   }
	   else if(mocPunches == "")
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
	   }
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
	   else if(dustCapGroove == "")
	   {
		   alertmsg = "Dustcup is required";
		   $("#techAlert").text(alertmsg);
		   $("#dustCapGroove").focus();
		   return false;
	   }
	   else if(poQuantity == "")
	   {
		   alertmsg = "PO Qty is required";
		   $("#techAlert").text(alertmsg);
		   $("#poQuantity").focus();
		   return false;
	   }
	   else if($("#dqDocument").val() == "")
	   {
		   alertmsg = "DQ document number is required";
		   $("#techAlert").text(alertmsg);
		   $("#dqDocument").focus();
		   return false;
		 
	   }
	  /*  else if($("#mocNumber").val() == "")
	   {
		   alertmsg = "MOC Certificate number is required";
		   $("#techAlert").text(alertmsg);
		   $("#mocNumber").focus();
		   return false;
			
	   } */
	 /*   else if($("#dqDocumentPO").val() == "")
	   {
		   alertmsg = "DQ document number PO is required";
		   $("#techAlert").text(alertmsg);
		   $("#dqDocumentPO").focus();
		   return false;
	   }
	   else if($("#mocNumberPO").val() == "")
	   {
		   alertmsg = "MOC Certificate number PO is required";
		   $("#techAlert").text(alertmsg);
		   $("#mocNumberPO").focus();
		   return false;
	   }
	   else if($("#inspectionReportNumberPO").val() == "")
	   {
		   alertmsg = "Inspection Report number PO is required";
		   $("#techAlert").text(alertmsg);
		   $("#inspectionReportNumberPO").focus();
		   return false;
	   } */
	   else if($("#inspectionReportNumber").val() == "")
	   {
		   alertmsg = "Inspection Report number is required";
		   $("#techAlert").text(alertmsg);
		   $("#inspectionReportNumber").focus();
		   return false;
	   }
	   else if(uomPO == ""){
		   alertmsg = "UOM is required";
		   $("#techAlert").text(alertmsg);
		   $("#uomPO").focus();
	   }
	   else if(uomPO == ""){
		   alertmsg = "UOM is required";
		   $("#techAlert").text(alertmsg);
		   $("#uomPO").focus();
	   }
	   else if(storageLocation == ""){
		   alertmsg = "Storage Location is required";
		   $("#techAlert").text(alertmsg);
		   $("#storageLocation").focus();
	   }
	   else if(upperQnty == ""){
		   alertmsg = "Upper Qnty is required";
		   $("#techAlert").text(alertmsg);
		   $("#upperQnty").focus();
	   }
	   else if(lowerQnty == ""){
		   alertmsg = "Lower Qnty is required";
		   $("#techAlert").text(alertmsg);
		   $("#lowerQnty").focus();
	   }
	   else if(dieQnty == ""){
		   alertmsg = "Die Qnty is required";
		   $("#techAlert").text(alertmsg);
		   $("#dieQnty").focus();
	   }
	   else if(manDate == ""){
		   alertmsg = "Manufacture Date is required";
		   $("#techAlert").text(alertmsg);
		   $("#manDate").focus();
	   }
	   
	   
	  /*  else if(expiryDate == ""){
		   alertmsg = "Expirty Date is required";
		   $("#techAlert").text(alertmsg);
		   $("#expiryDate").focus();
	   } */
	   
	   else if(receivedQuantity == "")
	   {
		   alertmsg = "Received Qty is required";
		   $("#techAlert").text(alertmsg);
		   $("#receivedQuantity").focus();
		   return false;
	   }else if(parseInt(receivedQuantity) <  parseInt($("#minAcceptedQty").val()))
	   {
		   alertmsg = "Received Qty should be greather than Min quantity";
		   $("#techAlert").text(alertmsg);
		   $("#receivedQuantity").focus();
	   }else if(parseInt(poQuantity) <  parseInt(receivedQuantity))
	   {
		   alertmsg = "Received Qty should be less than or equal than Order quantity";
		   $("#techAlert").text(alertmsg);
		   $("#receivedQuantity").focus();
	   }
	   else
	   {
		     $("#techAlert").empty();  
		     //HideDialog();

		     //$("#loadoverlay").fadeIn();  
		     //$("#processloading").fadeIn();
		     var sop = "";
		    	$('input[name="sop"]:checked').each(function() {
   					sop = sop + this.value +",";
				});
		    	$("#cleaningSOP").val(sop);
		     if(process == "Save")
		     {            	
		    	 $("#btnprocess").prop("disabled", true);


			    	// $("#btnprocess").prop("disabled", true);
			    	 $("#frnReceiptNote").attr("action","addToolingReceiptNote.jsf");   
					 $("#frnReceiptNote").submit(); 
		        /*  var datastring = $("form#frnReceiptNote").serialize();
		         $.ajax({
					dataType : 'text',
			        url : "${pageContext.request.contextPath}/addToolingReceiptNote.jsf",
			        data : datastring,
			        type : "POST",
			        success : function(result) 
			        {
			           var detail = result.split("</>");
			  		   if(detail != "")
			    	   {
			  			   var alertmsg = trim(detail[0]); ;
			  			   var toolingReceiptId = trim(detail[1]);
			  			   
			  			   $("#techAlert").text(alertmsg);
			  			   $("#toolingReceiptId").val(toolingReceiptId);
			  			   
			  			  // $("#btnList").show();
			  			  // funClearAfterProcess();
			  			 	mstProductCancelClose();
			    	   }
			        }
		         }); */
		     }  
		     else if(process == "Update")
		     {                           
		    	 $("#btnprocess").prop("disabled", true);
		    	 var datastring = $("form#frnReceiptNote").serialize();
		         $.ajax({
					dataType : 'text',
			        url : "${pageContext.request.contextPath}/updateToolingReceiptNote.jsf",
			        data : datastring,
			        type : "POST",
			        success : function(result) 
			        {
			           var detail = result.split("</>");
			  		   if(detail != "")
			    	   {
			  			  // var alertmsg = trim(detail[0]); ;
			  			   //var toolingReceiptId = trim(detail[1]);
			  			   
			  			  // $("#techAlert").text(alertmsg);
			  			  // $("#toolingReceiptId").val(toolingReceiptId);
			  			   
			  			  // $("#btnList").show();
			  			  // funClearAfterProcess();
			  			 mstProductCancelClose();
			    	   }
			        }
		         });       
		     }       
		     //$("#frnReceiptNote").submit(); 
	   }
  }	
           
   function ShowDialog()
   {	   
      $("#overlay").show();
      $("#productDialog").fadeIn(300);     
      $("#overlay").unbind("click");                       
   }
	
   function viewDialog()
   {
	   $("#overlay").show();
	   $("#viewProductDialog").fadeIn(300);     
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
	        url : "${pageContext.request.contextPath}/getToolingReceiptNote.jsf?toolingreceiptid="+id,
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
	  	          $("#grnNo").val(result.grnNo);
	  		      $("#po").val(trim(result.po));
	  		      $("#supplierCode").val(trim(result.supplierCode));
	  		      $("#supplierName").val(trim(result.supplierName));
	  		      $("#productSerialNo").val(trim(result.productSerialNo));
	  		    
	  	          $("#toolingReceiptId").val(result.toolingReceiptId);          
	  	          $("#isActive").val("0");
	  	        $("#productName").val(trim(result.productName)); 
	  	  	     $("#drawingNo").val(trim(result.drawingNo));	   
	  	  	   //  $("#strength").val(trim(result.strength));
	  			 $("#machineType").val(trim(result.machineType));
	  			 $("#typeOfTool").val(result.typeOfTool);
	  			 $("#mocPunches").val(trim(result.mocPunches));
	  			 $("#mocDies").val(trim(result.mocDies));
	  			 $("#shape").val(trim(result.shape));
	  			 $("#dimensions").val(trim(result.dimensions));
	  			 $("#breakLineUpper").val(trim(result.breakLineUpper));
	  			 $("#breakLineLower").val(trim(result.breakLineLower));
	  			 $("#embosingUpper").val(trim(result.embosingUpper));
	  			 $("#embosingLower").val(trim(result.embosingLower));
	  			// $("#laserMarking").val(trim(result.laserMarking));
	  			 $("#hardChromePlating").val(trim(result.hardCromePlating));
	  			 $("#dustCapGroove").val(trim(result.dustCapGroove));
	  			 $("#poQuantity").val(result.poQuantity);
	  			 $("#receivedQuantity").val(result.receivedQuantity);
	  			 $("#toolingLotNumber").val(result.toolingLotNumber);
	  			$("#toolingCodeNumber").val(result.toolingCodeNumber);
	  			$("#dqDocument").val(result.dqDocument);
	  			$("#mocNumber").val(result.mocNumber);
	  			$("#inspectionReportNumber").val(result.inspectionReportNumber);
	  			$("#uomPO").val(result.uom);
	  	         $("#productName").focus();
	  	        $("#toolingCodeNumberPO").val(result.toolingCodeNumber);
	  	        $("#punchSetNo").val(result.punchSetNo);
	  	        $("#compForce").val(result.compForce);
	  	       $("#productNamePO").val(trim(result.productName)); 
	  	  	     $("#drawingNoPO").val(trim(result.drawingNo));	   
	  	  	   //  $("#strength").val(trim(result.strength));
	  			 $("#machineTypePO").val(trim(result.machineType));
	  			 $("#typeOfToolPO").val(result.typeOfTool);
	  			 $("#mocPunchesPO").val(trim(result.mocPunches));
	  			 $("#mocDiesPO").val(trim(result.mocDies));
	  			 $("#shapePO").val(trim(result.shape));
	  			 $("#dimensionsPO").val(trim(result.dimensions));
	  			 $("#breakLineUpperPO").val(trim(result.breakLineUpper));
	  			 $("#breakLineLowerPO").val(trim(result.breakLineLower));
	  			 $("#embosingUpperPO").val(trim(result.embosingUpper));
	  			 $("#embosingLowerPO").val(trim(result.embosingLower));
	  			// $("#laserMarking").val(trim(result.laserMarking));
	  			 $("#hardChromePlatingPO").val(trim(result.hardCromePlating));
	  			 $("#dustCapGroovePO").val(trim(result.dustCapGroove));
	  			 $("#poQuantity").val(result.poQuantity);
	  			 $("#receivedQuantity").val(result.receivedQuantity);
	  			 $("#toolingLotNumberPO").val(result.toolingLotNumber);
	  			$("#dqDocumentPO").val(result.dqDocument);
	  			$("#mocNumberPO").val(result.mocNumber);
	  			$("#inspectionReportNumberPO").val(result.inspectionReportNumber);
	  			$("#uomPO").val(result.uom);
	  			$("#productId").val(result.toolingProductId);
	  		//	$("#expiryDate").val(result.expiryDates);
	  			$("#manDate").val(result.manDates);
	  	         //$("#delStatus").val("0");
	  	         $("#storageLocation").val(result.storageLocation);
	  			$("#upperQnty").val(result.upperQnty);
	  			$("#lowerQnty").val(result.lowerQnty);
	  			$("#dieQnty").val(result.dieQnty);
	  			$("#minAcceptedQty").val(result.minAcceptedQty);
	  			var arrayValues = result.cleaningSOP.split(',');
	  			$.each(arrayValues, function(i, val){

	  			   $("input[value='" + val + "']").prop('checked', true);

	  			});
	  			/* $("#mocLocation").val(result.mocUploadImage);
	  			$("#dqLocation").val(result.dqUploadImage);
	  			$("#inspLocation").val(result.inspectionUploadImage); */
	  			$("#mocLocation").html("<a href="+result.mocUploadImage+" target='_blank'>"+result.mocUploadImage.substring(result.mocUploadImage.lastIndexOf('/')+1)+"</a>");
	  			$("#dqLocation").html("<a href="+result.dqUploadImage+" target='_blank'>"+result.dqUploadImage.substring(result.dqUploadImage.lastIndexOf('/')+1)+"</a>");
	  			$("#inspLocation").html("<a href="+result.inspectionUploadImage+" target='_blank'>"+result.inspectionUploadImage.substring(result.inspectionUploadImage.lastIndexOf('/')+1)+"</a>");
	  			
	  	          ShowDialog(); 
	  	         // $("#btnList").show();
	  	          $("#btnprocess").val("Update");
	  	          $("#toolingLotNumber").focus();
	  	          $("#grnNo").focus();
	    	   }
	        }
	    }); 
   }
   
   function viewReport(id,rowid,stat)  
   {		 
	   $.ajax({
			dataType : 'json',
			async :false,
	        url : "${pageContext.request.contextPath}/viewToolingReceiptNote.jsf?toolingreceiptid="+id,
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

	  	          $("#viewgrnNo").html(result.grnNo);
	  		      $("#viewpo").html(trim(result.po));
	  		      $("#viewsupplierCode").html(trim(result.supplierCode));
	  		      $("#viewsupplierName").html(trim(result.supplierName));
	  		      $("#viewproductSerialNo").html(trim(result.productSerialNo));
	  		    
	  	          $("#viewtoolingReceiptId").html(result.toolingReceiptId);          
	  	          $("#isActive").val("0");
	  	         //$("#delStatus").val("0");
	  	          
	  	          viewDialog(); 
	  	          funViewListReceiptProduct(id);
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
				    	$("#frnReceiptNote").attr("action","pages/SessionExpire.jsp");   	                
					    $("#frnReceiptNote").submit();
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
	  	//return stringTrim.replace(/^\s+|\s+$/g,"");
	   return stringTrim;
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
			  $("#grnNo").focus(); 
		    }
		    else
		    {
		    	showmsg();
		    	$("#searchToolingReceiptNote").focus();
		    }
	  }
	  else 
	  {
		  $("#searchToolingReceiptNote").focus();
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
	   var searchVal = trim($("#searchToolingReceiptNote").val());   
	   if(searchVal == "")
  	   {   	
		   showAllListDialog();
  	   }
	   else
 	   {   		    
   	       $("#action").val("list");  	
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();	 			                    	                  
 	 	   $("#frnReceiptNote").attr("action","searchToolingReceiptNote.jsf");   	                
 	       $("#frnReceiptNote").submit();
 	   }	  
   }
   function funAllProcessList()
   {  		    	
	       $("#action").val("listdata");  
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();   	 			                    	                  
 	 	   $("#frnReceiptNote").attr("action","showToolingReceiptNote.jsf");   	                
 	       $("#frnReceiptNote").submit();	      	 	
   }   	  
   
   function funClearProducts()
   {
	     $("#indentNo").val("");
	     $("#po").val("");
	     $("#supplierCode").val("");
	     $("#supplierName").val("");
	     $("#productSerialNo").val("");
	     $("#toolingLotNumber").val(""); 
	     $("#productName").val(""); 
  	     $("#drawingNo").val("");	   
  	    // $("#strength").val("");
		 $("#typeOfTool").val("");
		 $("#mocPunches").val("");
		 $("#mocDies").val("");
		 $("#shape").val("");
		 $("#dimensions").val("");
		 $("#uomPO").val("");
		 $("#breakLineUpper").val("Required");
		 $("#breakLineLower").val("Required");
		 $("#embosingUpper").val("");
		 $("#embosingLower").val("");
		// $("#laserMarking").val("Required");
		 $("#hardChromePlating").val("Required");
		 $("#searchToolingReceiptNote").val("");
		 $("#dustCapGroove").val("");
		 $("#poQuantity").val("");
		 $("#receivedQuantity").val("");
         
		 $("#productNamePO").val(""); 
  	     $("#drawingNoPO").val("");	   
  	    // $("#strengthPO").val("");
  	    $("#toolingLotNumberPO").val("");
		 $("#typeOfToolPO").val("");
		 $("#mocPunchesPO").val("");
		 $("#mocDiesPO").val("");
		 $("#shapePO").val("");
		 $("#dimensionsPO").val("");
		 $("#uomPO").val("");
		/*  $("#breaklineUpperPO").val("Required");
		 $("#breaklineLowerPO").val("Required"); */
		// $("#embosingUpperPO").val("");
		// $("#embosingLowerPO").val("");
		 $("#laserMakingPO").val("Required");
		 $("#hardChromePlatingPO").val("Required");
		 $("#searchProductPO").val("");
		 $("#dustCapGroovePO").val("");
         $("#manDate").val("");
       //  $("#expiryDate").val("");
         $("#grnNo").focus();
   }
   
   function funClearAfterProcess()
   {
	     $("#toolingLotNumber").val("");
	     $("#productName").val(""); 
  	     $("#drawingNo").val("");	   
  	   //  $("#strength").val("");
		 $("#typeOfTool").val("");
		 $("#mocPunches").val("");
		 $("#mocDies").val("");
		 $("#shape").val("");
		 $("#dimensions").val("");
		 $("#breakLineUpper").val("Required");
		 $("#breakLineLower").val("Required");
		 $("#embosingUpper").val("");
		 $("#embosingLower").val("");
		// $("#laserMarking").val("Required");
		 $("#hardChromePlating").val("Required");
		 $("#searchToolingReceiptNote").val("");
		 $("#dustCapGroove").val("");
		 $("#poQuantity").val("");
		 $("#receivedQuantity").val("");
         
		 $("#toolingLotNumberPO").val("");
		 $("#productNamePO").val(""); 
  	     $("#drawingNoPO").val("");	   
  	   //  $("#strengthPO").val("");
		 $("#typeOfToolPO").val("");
		 $("#mocPunchesPO").val("");
		 $("#mocDiesPO").val("");
		 $("#shapePO").val("");
		 $("#dimensionsPO").val("");
		 /* $("#breaklineUpperPO").val("Required");
		 $("#breaklineLowerPO").val("Required"); */
		// $("#embosingUpperPO").val("");
		// $("#embosingLowerPO").val("");
		 $("#laserMakingPO").val("Required");
		 $("#hardChromePlatingPO").val("Required");
		 $("#searchProductPO").val("");
		 $("#dustCapGroovePO").val("");
		 $("#uomPO").val("");
		 $("#manDate").val("");
		// $("#expiryDate").val("");
         $("#toolingLotNumber").focus();
   }
   
   function funSearchMinProduct(type)
   {
	   $("#type").val(type);
	   showProductNameListDialog();
   }
   function funGetIndent()
   {
	   $("#productDialog").hide();
	      $("#productNameAlert").text("");
	      $("#indentDialog").fadeIn(300);  
	      $("#overlay").unbind("click");    
	      $("#listIndentProjectNames").html(""); 
	      getIndentDetails();
   }
   function getIndentDetails()
   {
	   //var srchMinProductName = $.trim($("#srchMinProductName").val());
	  // var srchMinProductName = $.trim($("#srchMinProductName").val());
	  /*  if(srchMinProductName == "")
	   {
		   $("#productNameAlert").text("Enter Product Name");
		   $("#srchMinProductName").focus();
	   }
	   else */
	   {
		   $("#productNameAlert").text("");
		   $("#waitloadingProductName").show();
		   $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/searchPendingIndent.jsf",
		        type : "POST",
		        success : function(result) 
		        {
		  		   if(result != "")
		    	   {	
		  			  $("#listIndentProjectNames").html(result); 
		  			  $("#waitloadingProductName").hide();
		    	   }
		        }
		    });
	   }
   }
   
   function showProductNameListDialog()
   {
      $("#productDialog").hide();
      $("#productNameAlert").text("");
      $("#productNameListDialog").fadeIn(300);  
      $("#srchMinProductName").focus();
      $("#overlay").unbind("click");    
      $("#listProjectNames").html(""); 
      $("#srchMinProductName").val("");
      funSearchMinProductName();
   }

   function hideProductNameListDialog()
   {
      $("#productNameListDialog").fadeOut(300);
      $("#productDialog").show();
      $("#type").val("");
   } 
   function hideIndentDialog()
   {
      $("#indentDialog").fadeOut(300);
      $("#productDialog").show();
      $("#type").val("");
   } 
   
   
   function funSearchMinProductName()
   {
	   //var srchMinProductName = $.trim($("#srchMinProductName").val());
	   var srchMinProductName = $.trim($("#srchMinProductName").val());
	  /*  if(srchMinProductName == "")
	   {
		   $("#productNameAlert").text("Enter Product Name");
		   $("#srchMinProductName").focus();
	   }
	   else */
	   {
		   $("#productNameAlert").text("");
		   $("#waitloadingProductName").show();
		   $.ajax({
				dataType : 'html',
		        url : "${pageContext.request.contextPath}/getProductDetailsByDrawingNo.jsf?searchProductName="+srchMinProductName,
		        type : "POST",
		        success : function(result) 
		        {
		  		   if(result != "")
		    	   {	
		  			  $("#listProjectNames").html(result); 
		  			  $("#waitloadingProductName").hide();
		    	   }
		        }
		    });
	   }
   }
   function funIndentId(indentId)
   {

	   $.ajax({
			dataType : 'json',
	        url : "${pageContext.request.contextPath}/getIndentById.jsf?indentId="+indentId,
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
	  	          $("#indentNo").val(trim(result.indentNo));
	  	       	  $("#supplierCode").val(trim(result.supplierCode));
	  	      	 $("#supplierName").val(trim(result.supplierName)); 
	  	         $("#po").val(trim(result.po)); 
	  	         $("#toolingLotNumber").val(trim(result.toolingLotNumberPO)); 
	  	         $("#toolingLotNumberPO").val(trim(result.toolingLotNumberPO));
	  	         $("#punchSetNo").val(trim(result.punchSetNo)); 
	  	         $("#compForce").val(result.compForce);
	  	         $("#productNamePO").val(trim(result.productSerialNo)); 
	  	  	     $("#drawingNoPO").val(trim(result.drawingNo));	   
	  	  	   //  $("#strengthPO").val(trim(result.strength));
	  			 $("#machineTypePO").val(trim(result.machineTypePO));
	  			 $("#typeOfToolPO").val(result.typeOfTool);
	  			 $("#mocPunchesPO").val(trim(result.mocPunches));
	  			 $("#mocDiesPO").val(trim(result.mocDies));
	  			 $("#shapePO").val(trim(result.shape));
	  			 $("#dimensionsPO").val(trim(result.dimensionsPO));
	  			 /* $("#breaklineUpperPO").val(trim(result.breaklineUpper));
	  			 $("#breaklineLowerPO").val(trim(result.breaklineLower)); */
	  			 $("#embosingUpperPO").val(trim(result.embosingUpper));
	  			 $("#embosingLowerPO").val(trim(result.embosingLower));
	  			 
	  			 $("#laserMakingPO").val(trim(result.laserMaking));
	  			 $("#hardChromePlatingPO").val(trim(result.hardCromePlating));
	  			 $("#dustCapGroovePO").val(trim(result.dustCapGroove));
	  			$("#uomPO").val(trim(result.uom));

	  			//$("#exphidden").val(result.expiryDates);
	  		//	var CurrentDate = new Date();
	  		//	CurrentDate.setMonth(CurrentDate.getMonth() + result.expiryDates);
	  		//	$("#expiryDate").val( CurrentDate.getDate( )  + '/' +(CurrentDate.getMonth( ) + 1 )+ '/' + CurrentDate.getFullYear( ));
	  	
	  		
	  			$("#productName").val(trim(result.productSerialNo)); 
	  	  	     $("#drawingNo").val(trim(result.drawingNo));	   
	  	  	   //  $("#strength").val(trim(result.strength));
	  			 $("#machineType").val(trim(result.machineTypePO));
	  			 $("#typeOfTool").val(result.typeOfTool);
	  			 $("#mocPunches").val(trim(result.mocPunches));
	  			 $("#mocDies").val(trim(result.mocDies));
	  			 $("#shape").val(trim(result.shape));
	  			 $("#dimensions").val(trim(result.dimensionsPO));
	  			 $("#breakLineUpper").val(trim(result.breaklineUpper));
	  			 $("#breakLineLower").val(trim(result.breaklineLower));
	  			// $("#embosingUpper").val(trim(result.embosingUpper));
	  			 $("#embosingLower").val(trim(result.embosingLower));
	  			$("#embosingUpper").val(trim(result.embosingUpper));
	  			// $("#laserMarking").val(trim(result.laserMaking));
	  			 $("#hardChromePlating").val(trim(result.hardCromePlating));
	  			 $("#dustCapGroove").val(trim(result.dustCapGroove));
	  			 $("#uomPO").val(trim(result.uom));
	  			 $("#poQuantity").val(trim(result.poQuantity));
	  			 $("#minAcceptedQty").val(trim(result.minQuantity));
	  			 $("#punchSetNo").val(trim(result.punchSetNo));
	  			 $("#compForce").val(trim(result.compForce));
	  	         $("#productName").focus();
	  	       	 hideIndentDialog();
	  	      /*  var expiryDate = result.expiryDates;
		  		return expiryDate; */
	    	   }
	        }
	    }); 
   }
   function funSltProductName(productId)
   {
	   var type = $.trim($("#type").val());
	   if(type == "PO")
	   {
		   funLoadPOProductDetails(productId);
	   }
	   else
	   {
		   funLoadProductDetails(productId);
	   }
   }
   
   function funLoadProductDetails(productId)
   {
	   $.ajax({
			dataType : 'json',
	        url : "${pageContext.request.contextPath}/getProduct.jsf?productId="+productId,
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
	  	  	   //  $("#strength").val(trim(result.strength));
	  			 $("#machineType").val(trim(result.machineType));
	  			 $("#typeOfTool").val(result.typeOfTool);
	  			 $("#mocPunches").val(trim(result.mocPunches));
	  			 $("#mocDies").val(trim(result.mocDies));
	  			 $("#shape").val(trim(result.shape));
	  			 $("#dimensions").val(trim(result.dimensions));
	  			 $("#breakLineUpper").val(trim(result.breaklineUpper));
	  			 $("#breakLineLower").val(trim(result.breaklineLower));
	  			 $("#embosingUpper").val(trim(result.embosingUpper));
	  			 $("#embosingLower").val(trim(result.embosingLower));
	  			// $("#laserMarking").val(trim(result.laserMaking));
	  			 $("#hardChromePlating").val(trim(result.hardChromePlating));
	  			 $("#dustCapGroove").val(trim(result.dustCapGroove));
	  			 $("#uomPO").val(trim(result.uom));
	  	         $("#productName").focus();
	    	   }
	        }
	    });
	   hideProductNameListDialog();
   }
   
   function funLoadPOProductDetails(productId)
   {
	   $.ajax({
			dataType : 'json',
	        url : "${pageContext.request.contextPath}/getProduct.jsf?productId="+productId,
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

	  	         $("#productNamePO").val(trim(result.productName)); 
	  	  	     $("#drawingNoPO").val(trim(result.drawingNo));	   
	  	  	   //  $("#strengthPO").val(trim(result.strength));
	  			 $("#machineTypePO").val(trim(result.machineType));
	  			 $("#typeOfToolPO").val(result.typeOfTool);
	  			 $("#mocPunchesPO").val(trim(result.mocPunches));
	  			 $("#mocDiesPO").val(trim(result.mocDies));
	  			 $("#shapePO").val(trim(result.shape));
	  			 $("#dimensionsPO").val(trim(result.dimensions));
	  			 /* $("#breaklineUpperPO").val(trim(result.breaklineUpper));
	  			 $("#breaklineLowerPO").val(trim(result.breaklineLower)); */
	  			// $("#embosingUpperPO").val(trim(result.embosingUpper));
	  			// $("#embosingLowerPO").val(trim(result.embosingLower));
	  			 
	  			 $("#laserMakingPO").val(trim(result.laserMaking));
	  			 $("#hardChromePlatingPO").val(trim(result.hardChromePlating));
	  			 $("#dustCapGroovePO").val(trim(result.dustCapGroove));
	  			$("#uomPO").val(trim(result.uom));
	  		//	var CurrentDate = new Date();
	  		//	CurrentDate.setMonth(CurrentDate.getMonth() + result.expiryPeriod);
	  		//	$("#expiryDate").val( CurrentDate.getDate( )  + '/' +(CurrentDate.getMonth( ) + 1 )+ '/' + CurrentDate.getFullYear( ));
	  	         $("#productName").focus();
	    	   }
	        }
	    }); 
	   hideProductNameListDialog();
   }
   
   function funShowReceiptList()
   {
	   showReceiptListDialog();
   }
   
   function showReceiptListDialog()
   {
      $("#productDialog").hide();
      $("#receiptListDialog").fadeIn(300);  
      $("#overlay").unbind("click");    
      $("#listReceiptDetails").html("");
      funListReceiptProduct();
   }

   function hideReceiptListDialog()
   {
      $("#receiptListDialog").fadeOut(300);
      $("#productDialog").show();
   } 
   
   function funListReceiptProduct()
   {
	   var toolingReceiptId = trim($("#toolingReceiptId").val());
	   if(toolingReceiptId == "")
	   {
		   toolingReceiptId = "0";
	   }
	   $("#waitloadingReceiptList").show();
	   $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/listReceiptProduct.jsf?toolingReceiptId="+toolingReceiptId,
	        type : "POST",
	        success : function(result) 
	        {
	  		   if(result != "")
	    	   {	
	  			  $("#listReceiptDetails").html(result); 
	  			  $("#waitloadingReceiptList").hide();
	    	   }
	        }
	    });
   }
   function funViewListReceiptProduct(toolingReceiptId)
   {
	  // var toolingReceiptId = trim($("#toolingReceiptId").val());
	   if(toolingReceiptId == "")
	   {
		   toolingReceiptId = "0";
	   }
	   $("#waitloadingReceiptList").show();
	   $.ajax({
			dataType : 'html',
	        url : "${pageContext.request.contextPath}/listReceiptProduct.jsf?toolingReceiptId="+toolingReceiptId,
	        type : "POST",
	        success : function(result) 
	        {
	  		   if(result != "")
	    	   {	
	  			   
	  			  $("#viewListReceiptDetails").html(result); 
	  			  $("#waitloadingReceiptList").hide();
	    	   }
	        }
	    });
   }
   function funEditProduct(productId)
   {
	   hideReceiptListDialog();
	   $("#btnprocess").val("Update");	
	   $("#productId").val(productId);
	   funEditLoadProductDetails(productId);
	   //funLoadPOProductDetails(productId);
   }
   
   function funEditLoadProductDetails(productId)
   {
	   $.ajax({
			dataType : 'json',
	        url : "${pageContext.request.contextPath}/getToolingReceiptProduct.jsf?toolingProductid="+productId,
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
				
	  	         $("#productName1").val(trim(result.productName)); 
	  	  	     $("#drawingNo1").val(trim(result.drawingNo));	  
	  	  	   //  $("#strength").val(trim(result.strength));
	  			 $("#machineType1").val(trim(result.machineType));
	  			 $("#typeOfTool1").val(result.typeOfTool);
	  			 $("#mocPunches1").val(trim(result.mocPunches));
	  			 $("#mocDies1").val(trim(result.mocDies));
	  			 $("#shape1").val(trim(result.shape));
	  			 $("#dimensions1").val(trim(result.dimensions));
	  			 $("#breakLineUpper1").val(trim(result.breakLineUpper));
	  			 $("#breakLineLower1").val(trim(result.breakLineLower));
	  			 $("#embosingUpper1").val(trim(result.embosingUpper));
	  			 $("#embosingLower1").val(trim(result.embosingLower));
	  			// $("#laserMarking").val(trim(result.laserMarking));
	  			 $("#hardChromePlating1").val(trim(result.hardCromePlating));
	  			 $("#dustCapGroove1").val(trim(result.dustCapGroove));
	  			 $("#poQuantity1").val(result.poQuantity);
	  			$("#minAcceptedQty1").val(result.minQuantity);
	  			
	  			 $("#receivedQuantity1").val(result.receivedQuantity);
	  			 $("#toolingLotNumber1").val(result.toolingLotNumber);
	  			$("#toolingCodeNumber1").val(result.toolingCodeNumber);
	  			$("#dqDocument1").val(result.dqDocument);
	  			$("#mocNumber1").val(result.mocNumber);
	  			$("#inspectionReportNumber1").val(result.inspectionReportNumber);
	  			$("#uom1").val(result.uom);
	  	         $("#productName1").focus();
	  	       $("#toolingCodeNumberPO1").val(result.toolingCodeNumber);
	  	       $("#productNamePO1").val(trim(result.productName)); 
	  	  	     $("#drawingNoPO1").val(trim(result.drawingNo));	   
	  	  	   //  $("#strength").val(trim(result.strength));
	  			 $("#machineTypePO1").val(trim(result.machineType));
	  			 $("#typeOfToolPO1").val(result.typeOfTool);
	  			 $("#mocPunchesPO1").val(trim(result.mocPunches));
	  			 $("#mocDiesPO1").val(trim(result.mocDies));
	  			 $("#shapePO1").val(trim(result.shape));
	  			 $("#dimensionsPO1").val(trim(result.dimensions));
	  			 $("#breakLineUpperPO1").val(trim(result.breakLineUpper));
	  			 $("#breakLineLowerPO1").val(trim(result.breakLineLower));
	  		//	 $("#embosingUpperPO1").val(trim(result.embosingUpper));
	  		//	 $("#embosingLowerPO1").val(trim(result.embosingLower));
	  			// $("#laserMarking").val(trim(result.laserMarking));
	  			 $("#hardChromePlatingPO1").val(trim(result.hardCromePlating));
	  			 $("#dustCapGroovePO1").val(trim(result.dustCapGroove));
	  			 $("#poQuantity1").val(result.poQuantity);
	  			 $("#receivedQuantity1").val(result.receivedQuantity);
	  			 $("#toolingLotNumberPO1").val(result.toolingLotNumber);
	  			$("#dqDocumentPO1").val(result.dqDocument);
	  			$("#mocNumberPO1").val(result.mocNumber);
	  			$("#inspectionReportNumberPO1").val(result.inspectionReportNumber);
	  			$("#uomPO1").val(result.uom);
	  			$("#storageLocation").val(result.storageLocation);
	  			$("#upperQnty").val(result.upperQnty);
	  			$("#lowerQnty").val(result.lowerQnty);
	  			$("#dieQnty").val(result.dieQnty);
	  			$("#upperQnty1").val(result.upperQnty);
	  			$("#lowerQnty1").val(result.lowerQnty);
	  			$("#dieQnty1").val(result.dieQnty);
	  			$("#storageLocation1").val(result.storageLocation);
	  			$("#minAcceptedQty1").val(result.minAcceptedQty);
	  			//$("#expiryDate1").val(result.expiryDates);
	  			$("#manDate1").val(result.manDates);
	  			$("#mocLocation").html("<a href="+result.mocUploadImage+" target='_blank'>"+result.mocUploadImage.substring(result.mocUploadImage.lastIndexOf('/')+1)+"</a>");
	  			$("#dqLocation").html("<a href="+result.dqUploadImage+" target='_blank'>"+result.dqUploadImage.substring(result.dqUploadImage.lastIndexOf('/')+1)+"</a>");
	  			$("#inspLocation").html("<a href="+result.inspectionUploadImage+" target='_blank'>"+result.inspectionUploadImage.substring(result.inspectionUploadImage.lastIndexOf('/')+1)+"</a>");
	  			
	    	   }
	        }
	    });
   }
   
</script>

</head>
<body class="body" onload="init();">
<form name="frnReceiptNote" id="frnReceiptNote" method="post" autocomplete="off" enctype="multipart/form-data">
<%@ include file="tiimMenu.jsp"%>
<%
	HashMap<String, RoleVsUser> hmRoleVsUser1 = (HashMap<String, RoleVsUser>)session.getAttribute("RoleVsUser");
	RoleVsUser roleVsUser1 = hmRoleVsUser1.get("Receipt Note");
	if(roleVsUser1 != null)
	{
	 addAccess = roleVsUser1.getAddAccess1();
	 editAccess = roleVsUser1.getEditAccess1();
	 deleteAccess = roleVsUser1.getDeleteAccess1();
	 viewAccess = roleVsUser1.getViewAccess1();
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
	
	<div id="productNameListDialog" class="max_dialog">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			   <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="18%" align="left" class="popuptoptitlelarge">Product Name</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideProductNameListDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
				     <tr>
				        <td class="formlabelblack" align="center" colspan="2">
				             Search Drawing Number&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<input  type="text" name="srchMinProductName" id="srchMinProductName" maxlength="150" value="" class="textmedium" />
	                         &nbsp;<input type="button" value="Go" class="btn btnSMImportant" onclick="funSearchMinProductName();"/>
	                    </td>		        			       	       
				     </tr>
				     <tr>
				         <td colspan="2" align="center">				           
					        <img id="waitloadingProductName" style="display:none;" src="./images/ultraloading.jpg" class="loadingPosition"></img>
					     </td></tr>
			   </table>   
	   		</td>
	   </tr>
	   </table>   
       <div id="listProjectNames" style="height:300px;overflow:auto;"></div>                                       
   </div>
   
   <div id="indentDialog" class="max_dialog">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			   <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="18%" align="left" class="popuptoptitlelarge">Product Name</td>		
				         <td width="60%" align="center" class="popuptopalert"><span id="productNameAlert"></span></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideIndentDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
				     <tr>
				        <td class="formlabelblack" align="center" colspan="2">
				             Search Drawing Number&nbsp;<span class="formlabel">*</span>&nbsp;&nbsp;&nbsp;<input  type="text" name="srchMinProductName" id="srchMinProductName" maxlength="150" value="" class="textmedium" />
	                         &nbsp;<input type="button" value="Go" class="btn btnSMImportant" onclick="funSearchMinProductName();"/>
	                    </td>		        			       	       
				     </tr>
				     <tr>
				         <td colspan="2" align="center">				           
					        <img id="waitloadingProductName" style="display:none;" src="./images/ultraloading.jpg" class="loadingPosition"></img>
					     </td></tr>
			   </table>   
	   		</td>
	   </tr>
	   </table>   
       <div id="listIndentProjectNames" style="height:300px;overflow:auto;"></div>                                       
   </div>
   
   <div id="receiptListDialog" class="max_dialog">
	  <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
	    <tr><td></td></tr>	     
	    <tr>
	        <td>
			   <table style="width:99%" align="center" cellpadding="4" cellspacing="0" border="0">	     
				     <tr>
				        <td width="18%" align="left" class="popuptoptitlelarge">Receipt Note Details</td>		
				         <td width="60%" align="center" class="popuptopalert"></td>	        
				        <td width="10%" align="right" class="popuptoptitle"><a href="javascript:hideReceiptListDialog();" title="Click to Close" class="popupanchor">X</a>&nbsp;</td>
				     </tr>	 
				     <tr><td colspan="2"></td></tr>
				     <tr>
				         <td colspan="2" align="center">				           
					        <img id="waitloadingReceiptList" style="display:none;" src="./images/ultraloading.jpg" class="loadingPosition"></img>
					     </td>
					 </tr>
			   </table>   
	   		</td>
	   </tr>
	   </table>   
       <div id="listReceiptDetails" style="height:350px;overflow:auto;"></div>                                       
   </div>
	
	<div id="productDialog" style="height:auto;margin-top:-300px;" class="max_dialog_Contact"> 
       <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
		   <tr><td></td></tr>	     
		   <tr>
		   		<td>
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">	     
		              <tr>
				          <td width="25%" align="left" class="popuptoptitlelarge">Tooling Receipt Note</td>	
				          <td width="55%" align="center" class="popuptopalert">&nbsp;&nbsp;<span id="techAlert">${message}</span></td>	        
				          <td width="20%" align="right" class="popuptoptitle"><a href="javascript:mstProductCancelClose();" title="Click to Close" class="popupanchor" >X</a>&nbsp;</td>
				      </tr>	 		     
				      <tr><td colspan="2"></td></tr>				      
				   </table>   
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
				      <tr>
				          <td class="formlabelblack" width="15%" align="left">Tool Receipt No&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" width="15%" align="left"><input type="text" name="grnNo" id="grnNo" class="textmin" value="${grnNo}" onkeypress="return funTypeGRNNo(event,this);" autoComplete="off" readonly/></td>
			              <td class="formlabelblack" width="15%" align="left">Tool Receipt Date&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" width="15%" align="left"><input type="text" name="grnDate" id="grnDate" class="textmin" value="${grnDate}" maxlength="15" autoComplete="off" style="text-align:left;BORDER-RIGHT: black 1px groove; BORDER-TOP: black 1px groove; FONT-SIZE: 10pt;BORDER-LEFT: black 1px groove; BORDER-BOTTOM: black 1px groove; FONT-FAMILY: Tahoma;TEXT-DECORATION: none; background-color:#DDDDDD;"  /></td>
			              <td class="formlabelblack" width="20%" align="left">PO No&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" width="20%" align="left"><input type="text" name="po" id="po" class="textmin" value="" maxlength="50" onkeypress="javascript:funTypePO(event,this);" autoComplete="off"/></td>
				     </tr>
				     <%
				     	if("1".equalsIgnoreCase(indent))
				     	{
				     %>
				     <tr>
				          <td class="formlabelblack" width="15%" align="left">Indent No&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left" colspan="5"><input type="text" name="indentNo" id="indentNo" class="textmin" value="" autoComplete="off"/>&nbsp;<input type="button" value="" onclick="javascript:funGetIndent()" class="btnselect"/></td>

				     </tr>
				     <%
				     	}
				     %>
				     <tr>
				     	  <td class="formlabelblack" align="left">Supplier Name&nbsp;<span class="formlabel">*</span></td>
			              <td class="formlabelblack" align="left"><input type="text" name="supplierName" id="supplierName" class="textmin" value="" maxlength="50" onkeypress="javascript:funTypeSupplierName(event,this);" autoComplete="off"/></td>
				        <!--   <td class="formlabelblack" align="left">Supplier Code&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="supplierCode" id="supplierCode" class="textmin" value="" maxlength="50" onkeypress="javascript:funTypeSupplierCode(event,this);" autoComplete="off"/></td> -->
			              
			            <!--   <td class="formlabelblack" align="left">Product S.No as Per PO&nbsp;<span class="formlabel">*</span></td>		
			              <td class="formlabelblack" align="left"><input type="text" name="productSerialNo" id="productSerialNo" class="textmin" value="" maxlength="50" onkeypress="javascript:funTypeProductSNo(event,this);" autoComplete="off"/></td> -->
				     </tr>
				     <tr height="15px">
						<td colspan="6"><span id="supplierStatus" class="popuptopalert"></span><hr class="style-six"></td>
					</tr>
				   </table>
				   <div style="height:350px;overflow:auto;">
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
				      <tr>
				          <td class="formlabelblack" width="24%" align="left"></td>		
			              <td class="formlabelblack" width="36%" align="left"></td>
			              <td class="formlabelblack" width="40%" align="left"></td>
				     </tr>
				          	
			         <tr height="2px"></tr>
				     <tr>
			             <td class="formlabelblack" align="left">&nbsp;</td>		
			             <td class="formlabelblack" align="left">Values as per PO</td>
			             <td class="formlabelblack" align="left"></td>					             			                 
			         </tr> 	
			         <tr height="2px"></tr>
			         <tr>
			             <td class="formlabelblack" align="left">Tooling Lot Number&nbsp;<span class="formlabel">*</span></td>		
			             <%
				     		if("1".equalsIgnoreCase(indent))
				     		{
					     %>
			             <td class="formlabelblack" align="left"><input type="text" name="toolingLotNumberPO" id="toolingLotNumberPO"  class="textsmall" /></td>
			             <td class="formlabelblack" align="left">
			             										 </td>	
			             <%
				     		}else
				     			{
			             %>			
			             <td class="formlabelblack" align="left"><input type="text" name="toolingLotNumberPO" id="toolingLotNumberPO" value="${autoToolingLotNumber}" class="textsmall" /></td>
			             <td class="formlabelblack" align="left">						 </td>
			             <%
				     			}
			             %>	             			                 
			         </tr>   	         
			         <tr height="2px"></tr>
				    <tr>
			             <td class="formlabelblack" align="left">Punch Set No&nbsp;<span class="formlabel">*</span></td>		
			            
			             <td class="formlabelblack" align="left"><input type="text" name="punchSetNo" id="punchSetNo"  class="textsmall" /></td>
			             <td class="formlabelblack" align="left"> </td>	
			                       			                 
			         </tr>   	         
			         <tr height="2px"></tr>
			         <!--  <tr>
			             <td class="formlabelblack" align="left">Tooling Code Number&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="toolingCodeNumberPO" id="toolingCodeNumberPO" value="" class="textsmall" /></td>
			             <td class="formlabelblack" align="left"><input type="text" name="toolingCodeNumber" id="toolingCodeNumber" value="" class="textsmall" onkeypress="return funTypeProductToolingLotNumber(event,this);" tabindex="1"/></td>					             			                 
			         </tr> 	 -->	        	   				        			         
			         <tr height="2px"></tr>
			          <tr>
			             <td class="formlabelblack" align="left">Drawing No.&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="drawingNoPO" id="drawingNoPO" value=""  value="" class="textsmall" maxlength="100"/>&nbsp;
			             <%
					     	if("0".equalsIgnoreCase(indent))
					     	{
					     %>
			             <input type="button" value="" onclick="javascript:funSearchMinProduct('PO');" class="btnselect"/>
			             <%
					     	}
			             %>
			             </td>
			             </td>					             			                 
			         </tr>  
				     <tr height="2px"></tr>
			          <tr>
			             <td class="formlabelblack" align="left">Max Comp Force Limit &nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="compForce" id="compForce" value=""  value="" class="textsmall" maxlength="100"/>&nbsp;
			             </td>					             			                 
			         </tr>  			        			         
			         <tr height="2px"></tr>	
			           <tr>
			             <td class="formlabelblack" align="left">Product Name&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="productNamePO" id="productNamePO" value="" class="textsmall" maxlength="150"/></td>
			             <td class="formlabelblack" align="left">&nbsp;</td>					             			                 
			         </tr>
			         <tr height="2px"></tr> 			          					 
			         
					 <tr>
					     <td class="formlabelblack" align="left">Machine Type&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             	<select name="machineTypePO" id="machineTypePO">
			             		<c:forEach items="${lstMachineType}" var="machineType" varStatus="row">    
			                       <option value="${machineType}">${machineType}</option>
			                    </c:forEach>
		                	</select>
			             </td>
			             <td class="formlabelblack" align="left">
			             	<%-- <select name="machineType" id="machineType" tabindex="4">
			             		<option value="">Select</option>
			             		<c:forEach items="${lstMachineType}" var="machineType" varStatus="row">    
			                       <option value="${machineType}">${machineType}</option>
			                    </c:forEach>
		                	</select> --%>
			             </td>
			           </tr>
			           <tr height="2px"></tr>
			           <tr>  	
			             <td class="formlabelblack" align="left">Type of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="typeOfToolPO" id="typeOfToolPO" value="" class="textsmall" maxlength="150"/></td>				     
			             <td class="formlabelblack" align="left">
			             	<!-- <select name="typeOfTool" id="typeOfTool" onkeypress="javascript:funTypeTool(event,this);" tabindex="5">
			             		<option value="">Select</option>
			                    <option value="D/D">D/D</option>
			                    <option value="B/B">B/B</option>
			                    <option value="D/B"> D/B</option>
			                    <option value="B/BB">B/BB</option>
			                    <option value="B/BBS">B/BBS</option>
			                </select> -->
			             </td>
			         </tr>			                
			         <tr height="2px"></tr>			        			        
			         <tr>
			             <td class="formlabelblack" align="left">MOC - Punches&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="mocPunchesPO" id="mocPunchesPO" value="" class="textsmall" maxlength="50"/></td>
			             <td class="formlabelblack" align="left">
			             <!-- <select name="mocPunches" id="mocPunches" onkeypress="javascript:funTypeMOCPunches(event,this);" tabindex="6">
			             		<option value="">Select</option>
			                    <option value="O1">O1</option>
			                    <option value="S7">S7</option>
			                    <option value="D2">D2</option>
			                    <option value="D3">D3</option>
			                    <option value="S1">S1</option>
			                    <option value="440C">440C</option>
			                    <option value="Others">Others</option>
			                </select> -->
			             <!-- <input type="text" name="mocPunches" id="mocPunches" value="" class="textsmall" onkeypress="javascript:funTypeMOCPunches(event,this);" maxlength="50"/> -->
			             </td>
			         </tr>
			         <tr height="2px"></tr>			        			        
			         <tr>
			         <tr>    
			             <td class="formlabelblack" align="left">MOC - Dies&nbsp;<span class="formlabel">*</span></td>		 
			             <td class="formlabelblack" align="left"><input type="text" name="mocDiesPO" id="mocDiesPO" value="" class="textsmall" maxlength="50"/></td>
			             <td class="formlabelblack" align="left">
			             	<!-- <select name="mocDies" id="mocDies" onkeypress="javascript:funTypeMOCDies(event,this);" tabindex="7">
			             		<option value="">Select</option>
			                    <option value="D3">D3</option>
			                    <option value="D2">D2</option>
			                    <option value="S7">S7</option>
			                    <option value="TC">TC</option>
			                </select> -->
			             <!-- <input type="text" name="mocDies" id="mocDies" value="" class="textsmall" onkeypress="javascript:funTypeMOCDies(event,this);" maxlength="50"/> -->
			             </td>
			         </tr>
			         <tr height="2px"></tr>			         
			         <tr>
			             <td class="formlabelblack" align="left">Shape of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="shapePO" id="shapePO" value="" class="textsmall" maxlength="50"/></td>
			             <td class="formlabelblack" align="left">
			             	<!-- <select name="shape" id="shape" onkeypress="javascript:funTypeShape(event,this);" tabindex="8">
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
			                </select> -->
			             <!-- <input type="text" name="shape" id="shape" value="" class="textsmall" onkeypress="javascript:funTypeShape(event,this);" maxlength="50"/> -->
			             </td>
			         </tr>    
			         <tr height="2px"></tr>	
			         <tr>    
			             <td class="formlabelblack" align="left">Dimensions of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="dimensionsPO" id="dimensionsPO" value="" class="textsmall" maxlength="50"/></td>				                  
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="dimensions" id="dimensions" value="" class="textsmall" onkeypress="javascript:funTypeDimension(event,this);" maxlength="50" tabindex="9"/> --></td>
			         </tr>	
			        
			         <tr height="2px"></tr>			         
			         
			         <tr>
			         	 <td class="formlabelblack" align="left">Embossing Upper&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             	<input type="text" name="embosingUpperPO" id="embosingUpperPO" value="" class="textsmall" maxlength="45" />
				          </td>
			              <td class="formlabelblack" align="left">
		                </td>
			         </tr>	
			         <tr height="2px"></tr>
			         <tr>     
			             <td class="formlabelblack" align="left">Embossing Lower&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			               <input type="text" name="embosingLowerPO" id="embosingLowerPO" value="" class="textsmall" maxlength="45" tabindex="11"/>
			             </td>
			             <td class="formlabelblack" align="left">
			             
			             </td>			                 
			         </tr>		         
			         <tr height="2px"></tr>
			         
			         <tr>    
			             <td class="formlabelblack" align="left">Plating&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             
			             	<select name="hardChromePlatingPO" id="hardChromePlatingPO" tabindex="12">
			             		<option value="">Select</option>
			                    <c:forEach items="${lstPlating}" var="plating" varStatus="row"> 
				             	    <c:set var="selected" scope="request" value="${''}"/>   
				             	    <c:if test="${plating ne NULL && plating.platingName eq hardCromePlating}">
										<c:set var="selected" scope="request" value="${'SELECTED'}"/>  
				  				 	 </c:if>
				                     <option value="${plating.platingName}" ${selected} >${plating.platingName}</option>
			                    </c:forEach>
		                	</select>
			                
			             </td>
			             <td class="formlabelblack" align="left">
			               <!--  <select name="hardCromePlating" id="hardChromePlating" tabindex="12">
			                	<option value="">Select</option>
			                    <option value="no coating">No coating</option>
			                    <option value="HCP">HCP</option>
			                    <option value="Multi-CRN">Multi-CRN</option>
			                    <option value="DLC">DLC</option>
			                    <option value="Others">Others</option>
			                </select> -->
			                
			             </td>			                 
			         </tr>
			         <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Dustcup&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             	<!-- <input type="text" name="dustCapGroovePO" id="dustCapGroovePO" value="" class="textsmall" maxlength="45" /> -->
				               <select name="dustCapGroovePO" id="dustCapGroovePO">
				               		<option value="">Select</option>
				                    <option value="Std">Std</option>
				                    <option value="Bellow">Bellow</option>
				                    <option value="Others">Others</option>
				                </select>  
			             </td>
			             <td class="formlabelblack" align="left">
			             	<!-- <input type="text" name="dustCapGroove" id="dustCapGroove" value="" class="textsmall" onkeypress="javascript:funTypeDustGap(event, this);" maxlength="45" /> -->
			             	<!-- <select name="dustCapGroove" id="dustCapGroove" tabindex="13">
				               		<option value="">Select</option>
				                    <option value="Std">Std</option>
				                    <option value="Bellow">Bellow</option>
				                    <option value="Others">Others</option>
				                </select>   -->
			             </td>
			         </tr>	
			         <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">UOM<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="uomPO" id="uomPO" value="" class="textsmall" maxlength="45" /></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="uomPO" id="uomPO" value="" class="textsmall" onkeypress="javascript:funTypeDustGap(event, this);" maxlength="45" tabindex="14"/> --></td>
			         </tr>	
			         <!-- <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">MOC Certificate Number<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="mocNumber" id="mocNumber" value="" class="textsmall" maxlength="45" /></td>
			             <td class="formlabelblack" align="left"><input type="text" name="mocNumber" id="mocNumber" value="" class="textsmall"  maxlength="45" tabindex="15"/></td>
			         </tr>	
			         <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">MOC Image<span class="formlabel"></span></td>			            
			             <td class="formlabelblack" align="left"><input id="mocFile" name="mocFile" multiple type="file" class="file-loading"/></td>
			             <td class="formlabelblack" align="left"><span id="mocLocation"></span></td>		
			         </tr>	
			         <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">DQ Document Number<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="dqDocument" id="dqDocument" value="" class="textsmall" maxlength="45" /></td>
			             <td class="formlabelblack" align="left"><input type="text" name="dqDocument" id="dqDocument" value="" class="textsmall"  maxlength="45" tabindex="16"/></td>
			         </tr>	
			         <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">DQ Image<span class="formlabel"></span></td>
			            
			             <td class="formlabelblack" align="left"><input id="dqFile" name="dqFile" multiple type="file" class="file-loading"/></td>
			             <td><span id="dqLocation"></span></td>
			         </tr>	 -->
			          <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Inspection Report Number<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="inspectionReportNumber" id="inspectionReportNumber" value="" class="textsmall" maxlength="45" /></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="inspectionReportNumber" id="inspectionReportNumber" value="" class="textsmall"  maxlength="45" tabindex="17"/> --></td>
			         </tr>
			         <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Inspection Report Image<span class="formlabel"></span></td>
			            
			             <td class="formlabelblack" align="left"><input id="dqFile" name="inspFile" multiple type="file" class="file-loading"/></td>
			             <td><span id="inspLocation"></span></td>
			         </tr>	
			         
			            <c:if test="${fn:length(lstSOP) gt 0}">
				           	<tr height="2px"></tr>
				          	<tr>
					         	 <td class="formlabelblack" align="left">Cleaning Done</td>
					            
					             <td class="formlabelblack" align="left" colspan='2'>
					             	<c:forEach items="${lstSOP}" var="lstSOP" varStatus="row">
					             		<input type="checkbox" name="sop" id="sop" value="${lstSOP}">${lstSOP}&nbsp;&nbsp;&nbsp;
					             	 </c:forEach> 
					             </td>
					             
				        	 </tr>	
			       		 </c:if>
			         <tr height="2px"></tr>
			         <tr>   
			             <td class="formlabelblack" align="left">Storage Location&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="storageLocation" id="storageLocation" value="" class="textsmall" tabindex="18"/></td>			                 
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			         <tr height="2px"></tr>
			         <tr>   
			             <td class="formlabelblack" align="left">Order Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="poQuantity" id="poQuantity" value="" class="txtleast" onkeypress="return funTypePOQty(event, this);"  tabindex="18"/></td>			                 
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			        <!--   <tr height="2px"></tr>
			         <tr>   
			             <td class="formlabelblack" align="left">Min Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"></td>			                 
			             <td class="formlabelblack" align="left"><input type="text" name="minQuantity" id="minQuantity" value="" class="txtleast"  tabindex="18"/></td>
			         </tr> -->
			         <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Received Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="receivedQuantity" id="receivedQuantity" value="" class="txtleast" tabindex="19" /></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			         <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Upper Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="upperQnty" id="upperQnty" value="" class="txtleast" tabindex="20" /></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			          <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Lower Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="lowerQnty" id="lowerQnty" value="" class="txtleast" tabindex="20" /></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			          <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Die Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="dieQnty" id="dieQnty" value="" class="txtleast" tabindex="20" /></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Min Accepted Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="minAcceptedQty" id="minAcceptedQty" value="" class="txtleast" tabindex="20" /></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			          <tr  height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Manufacture Date&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="manDates" id="manDate" value="" class="txtleast" tabindex="20" onclick="javascript:funDatePick()" readonly/></td>
			             <td class="formlabelblack" align="left"></td>
			   			        
			           </tr>
			        <!--  <tr  height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Expiry Date&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="expiryDates" id="expiryDate" value="" class="txtleast" tabindex="20" /></td>
			             <td class="formlabelblack" align="left"></td>
			             <td class="formlabelblack" align="left"><input type="hidden" name="exphidden" id="exphidden" value="" class="txtleast" tabindex="20"  /></td>
			         </tr> -->
			           <tr height="2px"></tr>
			         <tr>
			             <td class="formlabelblack" align="left">Status&nbsp;<span class="formlabel">*</span></td>		
			                 <td class="formlabelblack" align="left">
			                  <select  name="receiptStatus" id="receiptStatus" value="" class="textsmall">
			             		<option value="1">Accepted as per PO</option>
			             		<option value="0">Not Accepted as per PO</option>
			             	  </select>
			             	  </td>
			             <td class="formlabelblack" align="left" colspan="2">
			             
			               
			            
			             </td>
			             					             			                 
			         </tr>   
			         <tr height="2px"></tr>
			         <tr>     
			             <td class="formlabelblack" align="left"></td>
			             <td class="formlabelblack" align="left"></td> 
			             <td class="formlabelblack" align="left"></td>                
			         </tr>
			         <tr height="2px"></tr>	
			       </table>
			       </div>
			       <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
			          <tr height="15px">
						<td colspan="2"><hr class="style-six"></td>
					  </tr>
			         <tr>
			             <td width="25%" class="formlabelblack" align="left"><span class="formlabel">*</span>&nbsp;- required fields</td>
			             <td width="75%" class="formlabelblack" align="left"><input  type="button" class="btn btnImportant" id="btnprocess" value="Save" onclick="javascript:processReceiptNote();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btnNormal" id="btnClear" value="Clear" onclick="javascript:funClearProducts();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="btncancel"  class="btn btnCancel"  value="Close" onclick="javascript:mstProductCancelClose();" /></td>
			         </tr>	
			         <tr height="2px"><td colspan="2">&nbsp;</td></tr> 
			        </table>
	            </td>
	       </tr>
	   </table> 
</div>

<!-- ---------------------View Receipt----------------------------- -->
<div id="viewProductDialog" style="height:auto;margin-top:-300px;" class="max_dialog_Contact"> 
       <table style="width:98%" align="center" cellpadding="2" cellspacing="0" border="0">
		   <tr><td></td></tr>	     
		   <tr>
		   		<td>
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">	     
		              <tr>
				          <td width="25%" align="left" class="popuptoptitlelarge">Tooling Receipt Note</td>	
				          <td width="55%" align="center" class="popuptopalert">&nbsp;&nbsp;<span id="techAlert">${message}</span></td>	        
				          <td width="20%" align="right" class="popuptoptitle"><a href="javascript:mstProductCancelClose();" title="Click to Close" class="popupanchor" >X</a>&nbsp;</td>
				      </tr>	 		     
				      <tr><td colspan="2"></td></tr>				      
				   </table>   
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
				      <tr>
				          <td class="formlabelblack" width="15%" align="left">Tool Receipt No&nbsp;</td>		
			              <td width="15%" align="left"><span id="viewgrnNo"></span></td>
			              <td class="formlabelblack" width="15%" align="left">Tool Receipt Date&nbsp;</td>
			              <td width="15%" align="left" ><span id="viewgrnDate">${grnDate}</span></td>
			              <td class="formlabelblack" width="20%" align="left">PO No&nbsp;<span class="formlabel">*</span></td>		
			              <td width="20%" align="left"><span id="viewpo"></span></td>
				     </tr>
				     <tr>
				     	  <td class="formlabelblack" align="left">Supplier Name&nbsp;</td>
			              <td align="left"><span id="viewsupplierName"></span></td>
				         <!--  <td class="formlabelblack" align="left">Supplier Code&nbsp;</td>		
			              <td align="left"><span id="viewsupplierCode"></span></td> -->
			              
			            <!--   <td class="formlabelblack" align="left">Product S.No as Per PO&nbsp;</td>		
			              <td align="left"><span id="viewproductSerialNo"></span></td> -->
				     </tr>
				     <tr height="15px">
						<td colspan="6"><span id="supplierStatus" class="popuptopalert"></span><hr class="style-six"></td>
					</tr>
				   </table>
				   <div style="height:350px;overflow:auto;">
				   <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
				      <tr>
				          <td class="formlabelblack" width="24%" align="left"></td>		
			              <td class="formlabelblack" width="36%" align="left"></td>
			              <td class="formlabelblack" width="40%" align="left"></td>
				     </tr>
				      <tr>
			             <td class="formlabelblack" align="left">Status&nbsp;  <span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left" colspan="2" >
			             <select  name="receiptStatus" id="receiptStatus" value="" class="textsmall">
			             	<option value="1">Accepted as per PO</option>
			             	<option value="0">Not Accepted as per PO</option>
			             </select>
			            
			             </td>
			             					             			                 
			         </tr>       	
			         <tr height="2px"></tr>
				     <tr>
			             <td class="formlabelblack" align="left">&nbsp;</td>		
			             <td class="formlabelblack" align="left">Values as per PO</td>
			             <td class="formlabelblack" align="left"></td>					             			                 
			         </tr> 			        			         
			         <tr height="2px"></tr>
				     <tr>
			             <td class="formlabelblack" align="left">Tooling Lot Number&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="toolingLotNumberPO1" id="toolingLotNumberPO1" value="" class="textsmall"  readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="toolingLotNumber1" id="toolingLotNumber1" value="" class="textsmall" onkeypress="return funTypeProductToolingLotNumber(event,this);" readonly/> --></td>					             			                 
			         </tr>       				        			         
			         <tr height="2px"></tr>
				     <tr>
			             <td class="formlabelblack" align="left">Product Name&nbsp;</td>		
			             <td class="formlabelblack" align="left"><input type="text" name="productNamePO1" id="productNamePO1" value="" class="textsmall" maxlength="150"/>&nbsp;<input type="button" value="" onclick="javascript:funSearchMinProduct('PO');" class="btnselect"readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="productName1" id="productName1" value="" class="textsmall" onkeypress="javascript:funTypeProductName(event,this);" maxlength="150"readonly/> -->&nbsp;</td>					             			                 
			         </tr>       				        			         
			         <tr height="2px"></tr>	
			         <tr>
			             <td class="formlabelblack" align="left">Drawing No.&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="drawingNoPO1" id="drawingNoPO1" value=""  value="" class="textsmall" maxlength="100"readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="drawingNo1" id="drawingNo1" value=""  onkeypress="javascript:funTypeDrawingNo(event,this);" value="" class="textsmall" maxlength="100"readonly/> --></td>					             			                 
			         </tr>    
			         <tr height="2px"></tr> 			          					 
			         
					 <tr>
					     <td class="formlabelblack" align="left">Machine Type&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left" readonly>
			             	<select name="machineTypePO1" id="machineTypePO1">
			             		<c:forEach items="${lstMachineType}" var="machineType" varStatus="row">    
			                       <option value="${machineType}">${machineType}</option>
			                    </c:forEach>
		                	</select>
			             </td>
			             <td class="formlabelblack" align="left">
			             	<%-- <select name="machineType1" id="machineType1">
			             		<option value="">Select</option>
			             		<c:forEach items="${lstMachineType}" var="machineType" varStatus="row">    
			                       <option value="${machineType}">${machineType}</option>
			                    </c:forEach>
		                	</select> --%>
			             </td>
			           </tr>
			           <tr height="2px"></tr>
			           <tr>  	
			             <td class="formlabelblack" align="left">Type of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="typeOfToolPO1" id="typeOfToolPO1" value="" class="textsmall" maxlength="150"readonly/></td>				     
			             <td class="formlabelblack" align="left">
			             	<!-- <select name="typeOfTool" id="typeOfTool" onkeypress="javascript:funTypeTool(event,this);">
			             		<option value="">Select</option>
			                    <option value="D/D">D/D</option>
			                    <option value="B/B">B/B</option>
			                    <option value="D/B"> D/B</option>
			                    <option value="B/BB">B/BB</option>
			                    <option value="B/BBS">B/BBS</option>
			                </select> -->
			             <!-- <input type="text" name="typeOfTool1" id="typeOfTool1" value="" onkeypress="javascript:funTypeTool(event,this);" class="textsmall" maxlength="150"readonly/> --></td>
			         </tr>			                
			         <tr height="2px"></tr>			        			        
			         <tr>
			             <td class="formlabelblack" align="left">MOC - Punches&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="mocPunchesPO1" id="mocPunchesPO1" value="" class="textsmall" maxlength="50"readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="mocPunches1" id="mocPunches1" value="" class="textsmall" onkeypress="javascript:funTypeMOCPunches(event,this);" maxlength="50"readonly/> --></td>
			         </tr>
			         <tr height="2px"></tr>			        			        
			         <tr>
			         <tr>    
			             <td class="formlabelblack" align="left">MOC - Dies&nbsp;<span class="formlabel">*</span></td>		 
			             <td class="formlabelblack" align="left"><input type="text" name="mocDiesPO1" id="mocDiesPO1" value="" class="textsmall" maxlength="50"readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="mocDies1" id="mocDies1" value="" class="textsmall" onkeypress="javascript:funTypeMOCDies(event,this);" maxlength="50"readonly/> --></td>
			         </tr>
			         <tr height="2px"></tr>			         
			         <tr>
			             <td class="formlabelblack" align="left">Shape of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="shapePO1" id="shapePO1" value="" class="textsmall" maxlength="50"readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="shape1" id="shape1" value="" class="textsmall" onkeypress="javascript:funTypeShape(event,this);" maxlength="50"readonly/> --></td>
			         </tr>    
			         <tr height="2px"></tr>	
			         <tr>    
			             <td class="formlabelblack" align="left">Dimensions of Tooling&nbsp;<span class="formlabel">*</span></td>		
			             <td class="formlabelblack" align="left"><input type="text" name="dimensionsPO1" id="dimensionsPO1" value="" class="textsmall" maxlength="50"readonly/></td>				                  
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="dimensions1" id="dimensions1" value="" class="textsmall" onkeypress="javascript:funTypeDimension(event,this);" maxlength="50"readonly/> --></td>
			         </tr>	
			        
			         <tr height="2px"></tr>			         
			         
			         <tr>
			         	 <td class="formlabelblack" align="left">Embossing Upper&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             	 <input type="text" name="embosingUpper1" id="embosingUpper1" value="" class="textsmall" maxlength="45"/>
			              </td>
			              <td class="formlabelblack" align="left">
			             	
			              </td>
			         </tr>	
			         <tr height="2px"></tr>
			         <tr>     
			             <td class="formlabelblack" align="left">Embossing Lower&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             <input type="text" name="embosingLower1" id="embosingLower1" value="" class="textsmall" maxlength="45"/>
			            
			             </td>
			             <td class="formlabelblack" align="left">
			             	
			             </td>			                 
			         </tr>		         
			        <!-- <tr height="2px"></tr>
			         
			          <tr>    
			             <td class="formlabelblack" align="left">Plating&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			                <select name="hardChromePlatingPO" id="hardChromePlatingPO">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select>
			             </td>
			             <td class="formlabelblack" align="left">
			                <select name="hardCromePlating" id="hardChromePlating">
			                    <option value="Required">Required</option>
			                    <option value="Not Required">Not Required</option>
			                </select>
			             </td>			                 
			         </tr> -->
			         <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">DustCup&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left">
			             	<!-- <input type="text" name="dustCapGroovePO" id="dustCapGroovePO" value="" class="textsmall" maxlength="45" /> -->
				               <select name="dustCapGroovePO1" id="dustCapGroovePO1">
				               		<option value="">Select</option>
				                    <option value="Std">Std</option>
				                    <option value="Bellow">Bellow</option>
				                    <option value="Others">Others</option>
				                </select>  
			             </td>
			             <td class="formlabelblack" align="left">
			             	<!-- <input type="text" name="dustCapGroove" id="dustCapGroove" value="" class="textsmall" onkeypress="javascript:funTypeDustGap(event, this);" maxlength="45" /> -->
			             	<!-- <select name="dustCapGroove1" id="dustCapGroove1">
				               		<option value="">Select</option>
				                    <option value="Std">Std</option>
				                    <option value="Bellow">Bellow</option>
				                    <option value="Others">Others</option>
				                </select>   -->
			             </td>
			         </tr>	
			         <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">UOM<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="uomPO1" id="uomPO1" value="" class="textsmall" maxlength="45" readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="uom1" id="uom1" value="" class="textsmall" onkeypress="javascript:funTypeDustGap(event, this);" maxlength="45" readonly/> --></td>
			         </tr>	
			        <!--  <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">MOC Certificate Number<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="mocNumberPO1" id="mocNumberPO1" value="" class="textsmall" maxlength="45" readonly/></td>
			             <td class="formlabelblack" align="left"><input type="text" name="mocNumber1" id="mocNumber1" value="" class="textsmall"  maxlength="45" readonly/></td>
			         </tr>	
			         <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">DQ Document Number<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="dqDocumentPO1" id="dqDocumentPO1" value="" class="textsmall" maxlength="45" readonly/></td>
			             <td class="formlabelblack" align="left"><input type="text" name="dqDocument1" id="dqDocument1" value="" class="textsmall"  maxlength="45" readonly/></td>
			         </tr>	 -->
			          <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Inspection Report Number<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="inspectionReportNumberPO1" id="inspectionReportNumberPO1" value="" class="textsmall" maxlength="45" readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="inspectionReportNumber1" id="inspectionReportNumber1" value="" class="textsmall"  maxlength="45" readonly/> --></td>
			         </tr>
			         <tr height="2px"></tr>
					 <tr>   
			             <td class="formlabelblack" align="left">Storage Location&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="storageLocation1" id="storageLocation1" value="" class="textsmall" tabindex="18"readonly/></td>			                 
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="storageLocation1" id="storageLocation1" value="" class="textsmall" tabindex="18"readonly/> --></td>
			         </tr>
			         <tr>   
			             <td class="formlabelblack" align="left">Order Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="poQuantity1" id="poQuantity1" value="" class="txtleast" onkeypress="return funTypePOQty(event, this);" readonly /></td>			                 
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="poQuantity1" id="poQuantity1" value="" class="txtleast" onkeypress="return funTypePOQty(event, this);" readonly /> --></td>
			         </tr>
			         <!-- <tr height="2px"></tr>
			         <tr>   
			             <td class="formlabelblack" align="left">Min Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"></td>			                 
			             <td class="formlabelblack" align="left"><input type="text" name="minQuantity1" id="minQuantity1" value="" class="txtleast" onkeypress="return funTypePOQty(event, this);" readonly /></td>
			         </tr> -->
			         <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Received Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="receivedQuantity1" id="receivedQuantity1" value="" class="txtleast" readonly /></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="receivedQuantity1" id="receivedQuantity1" value="" class="txtleast" readonly /> --></td>
			         </tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Upper Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="upperQnty1" id="upperQnty1" value="" class="txtleast" tabindex="20" readonly/></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="upperQnty1" id="upperQnty1" value="" class="txtleast" tabindex="20" readonly/> --></td>
			         </tr>
			          <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Lower Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="lowerQnty1" id="lowerQnty1" value="" class="txtleast" tabindex="20"readonly /></td>
			             <td class="formlabelblack" align="left"><!-- <input type="text" name="lowerQnty1" id="lowerQnty1" value="" class="txtleast" tabindex="20"readonly /> --></td>
			         </tr>
			          <tr height="2px"></tr>
			         <tr>
			         	 <td class="formlabelblack" align="left">Die Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="dieQnty1" id="dieQnty1" value="" class="txtleast" tabindex="20" readonly/></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Min Accepted Qty&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="minAcceptedQty1" id="minAcceptedQty1" value="" class="txtleast" tabindex="20" readonly/></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			         <tr height="2px"></tr>
			             <tr>
			         	 <td class="formlabelblack" align="left">Man. Date&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="manDates1" id="manDate1" value="" class="txtleast" tabindex="20"readonly /></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr>
			        <!--  <tr height="2px"></tr>
			          <tr>
			         	 <td class="formlabelblack" align="left">Expiry Date&nbsp;<span class="formlabel">*</span></td>
			             <td class="formlabelblack" align="left"><input type="text" name="expiryDates1" id="expiryDate1" value="" class="txtleast" tabindex="20"readonly /></td>
			             <td class="formlabelblack" align="left"></td>
			         </tr> -->
			         <tr height="2px"></tr>
			         <tr>     
			             <td class="formlabelblack" align="left"></td>
			             <td class="formlabelblack" align="left"></td> 
			             <td class="formlabelblack" align="left"></td>                
			         </tr>
			         <tr height="2px"></tr>	
			       </table>
			       </div>
			       <div id="viewListReceiptDetails" style="height:350px;overflow:auto;"></div>
			       <!-- <table style="width:100%" align="center" cellpadding="4" cellspacing="0" border="0">
			          <tr height="15px">
						<td colspan="2"><hr class="style-six"></td>
					  </tr>
			         <tr>
			             <td width="25%" class="formlabelblack" align="left"><span class="formlabel">*</span>&nbsp;- required fields</td>
			             <td width="75%" class="formlabelblack" align="left"><input  type="button" class="btn btnImportant" id="btnprocess" value="Save" onclick="javascript:processReceiptNote();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btnNormal" id="btnClear" value="Clear" onclick="javascript:funClearProducts();"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="btncancel"  class="btn btnCancel"  value="Close" onclick="javascript:mstProductCancelClose();" />&nbsp;&nbsp;&nbsp;&nbsp;<input  type="button" class="btn btnNormal" id="btnList" value="List" onclick="javascript:funShowReceiptList();" style="display:none;"/></td>
			         </tr>	
			         <tr height="2px"><td colspan="2">&nbsp;</td></tr> 
			        </table> -->
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
	          <td width="25%" class="heading" align="left">&nbsp;Tooling Receipt Note</td> 
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
			  <input type="button" title="Click to Add" class='btn btnSMNormal' id="btnpop" value="Add"/>&nbsp;			
			  <%
			  }else{
			  %>
			  <input type="button" title="Access denied" class='btn btnSMNormalDisable' id="btnpop" value="Add" disabled="disabled"/>
			  <%}%>
			    
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
		          <td class="searchListLabel" align="left">Search&nbsp;&nbsp;<input type="text" name="searchToolingReceiptNote" id="searchToolingReceiptNote" placeholder="Enter GRN No" value="${searchToolingReceiptNote}" class="textmediumlarge" maxlength="50"/>&nbsp;&nbsp;<input type="button" name="btnSearch" id="btnSearch" value="Go" class="btn btnSMImportant"></td>  
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
				          <th width="10%" >Tool Receipt No</th>
				          <th width="15%">Tool Receipt Date</th>
				          <th width="15%">PO No</th>
				          <th width="15%">Supplier Code</th>
				          <th width="30%">Supplier Name</th>
				          <th width="15%">Action</th>
			          </tr>  
			     </thead> 
			     <tbody>
			         <c:forEach items="${lstProductDetail}" var="lstProductDetail" varStatus="row">  
				          <tr>
				             <td >${lstProductDetail.grnNo}</td>   
				             <td>${lstProductDetail.grnDate}</td>
				             <td>${lstProductDetail.po}</td>
				             <td>${lstProductDetail.supplierCode}</td>
				             <td>${lstProductDetail.supplierName}</td>
				             <td align="center" style="text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;
				             	<%-- <a title="Click to Edit" id="editAnchor${row.index}" href="javascript:funedit('${lstProductDetail.toolingReceiptId}','${row.index}','${lstProductDetail.isActive}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;   		             					
								<a title="Click to Delete" href="javascript:fundelete('${lstProductDetail.toolingReceiptId}');" ><img border="0" src="./images/deleteicon.gif"/></a>&nbsp;
								<a title="Click to View" id="viewReport${row.index}" href="javascript:viewReport('${lstProductDetail.toolingReceiptId}','${row.index}','${lstProductDetail.isActive}');"><img border="0" id="showedit" src="./images/view_report-icon.png"/></a>
								 --%>
								<%
								 if("1".equals(editAccess)){
								%> 
									<a title="Click to Edit" id="editAnchor${row.index}" href="javascript:funedit('${lstProductDetail.toolingReceiptId}','${row.index}','${lstProductDetail.isActive}');"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
				             	<%
								 }else{
				             	%>
				             		<a title="Access denied" id="editAnchor${row.index}" href="#"><img border="0" id="showedit" src="./images/pencil.png"/></a>&nbsp;
				             	<%}%>
				             	<%
								if("1".equals(deleteAccess)){
								%>
				             	<a title="Click to Delete" href="javascript:fundelete('${lstProductDetail.toolingReceiptId}');" ><img border="0" src="./images/deleteicon.gif"/></a>&nbsp;
				             	<%
								}else{
				             	%>   
				             	<a title="Access denied" href="#" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>
				             	<%}%> 	
				             	<%
								if("1".equals(viewAccess)){
								%>
				             	<a title="Click to View" id="viewReport${row.index}" href="javascript:viewReport('${lstProductDetail.toolingReceiptId}','${row.index}','${lstProductDetail.isActive}');"><img border="0" id="showedit" src="./images/view_report-icon.png"/></a>
				             	<%
								 }else{
				             	%>
				             	<a title="Access denied" href="#" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/view_report-icon.png"/></a>
				             	<%}%>  	
				             	
				             	          
								
								<%-- <c:choose>
									<c:when test="${lstProductDetail.isActive == 1}">
										<a title="Inactive entry can only be deleted" id="deleteAnchor${row.index}"><img border="0" id="delete${row.index}" src="./images/deleteicon_black.gif"/></a>&nbsp;&nbsp;<a title="Click to make this Inactive" href="javascript:funstatus('${lstProductDetail.productId}','${row.index}','${lstProductDetail.delStatus}');" id="statusAnchor${row.index}" ><img border="0" id="changeStatus${row.index}" style="" src="./images/activate.gif"/></a>
									</c:when>
									<c:when test="${lstProductDetail.delStatus == 0}">
										<a title="Click to Delete" href="javascript:fundelete('${lstProductDetail.productId}');" id="deleteAnchor${row.index}" ><img border="0"  id="delete${row.index}" style="" src="./images/deleteicon.gif"/></a>&nbsp;<a title="Click to make this Active" href="javascript:funstatus('${lstProductDetail.productId}','${row.index}','${lstProductDetail.delStatus}');" id="statusAnchor${row.index}" ><img border="0" id="changeStatus${row.index}" style="" src="./images/deactivate.gif"/></a>
									</c:when>
									<c:otherwise>
									    <a title="Cannot delete, Inspector in use" id="deleteAnchor${row.index}"><img border="0" id="delete${row.index}" src="./images/deleteicon_black.gif"/></a>&nbsp;<a title="Click to make this Active" href="javascript:funstatus('${lstProductDetail.productId}','${row.index}','${lstProductDetail.delStatus}');" id="statusAnchor${row.index}" ><img border="0" style="" id="changeStatus${row.index}" src="./images/deactivate.gif"/></a>
									</c:otherwise>
								</c:choose> --%>
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
<input type="hidden" name="isActive" id="isActive" value="${isActive}"/>

<input type="hidden" name="toolingReceiptId" id="toolingReceiptId" value="${toolingReceiptId}"/>
<input type="hidden" name="toolingProductId" id="productId" value="${toolingProductId}"/>

<input type="hidden" name="deleteStatus" id="deleteStatus" />
<input type="hidden" name="rowId" id="rowId" />
<input type="hidden" name="autoId" id="autoId" />
<input type="hidden" name="cleaningSOP" id="cleaningSOP" />

<input type="hidden" name="type" id="type" />

</form>
</body>
</html>

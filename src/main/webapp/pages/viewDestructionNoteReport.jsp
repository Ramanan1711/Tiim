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
   
   
   
   function checkApproval()
   {
	  // $('.myCheckbox').prop('checked', true);
	  $('input:checkbox[id*=acknowledge]').attr('checked', true);
   }
   
   function addDestruction()
   {  		    	
 		   $("#loadoverlay").fadeIn();  
 		   $("#processloading").fadeIn();   	 			                    	                  
 	 	   $("#frmDestruction").attr("action","addDestructionNoteDetail.jsf");   	                
 	       $("#frmDestruction").submit();	      	 	
   }   	
   
   function approveDestruction(){
	   $("#loadoverlay").fadeIn();  
		   $("#processloading").fadeIn();   	 			                    	                  
	 	   $("#frmDestruction").attr("action","approveDestructionNoteDetail.jsf");   	                
	       $("#frmDestruction").submit();	      
   }
  
  
</script>

</head>
<body class="body" onload="init();">
<form name="frmDestruction" id="frmDestruction" method="post" autocomplete="off" enctype="multipart/form-data">
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
	          <td width="25%" class="heading" align="left">&nbsp;Tool Destruction Note</td> 
	          <td width="50%" class="submenutitlesmall" align="center">
	              
	          </td>
	          <td align="right" width="25%" class="anchorlabel">
			  &nbsp;
			  
			    
			  </td>                             
	     </tr>
	 </table>
       
	<center>           
		<div style=" width: 1000px; padding:0px; margin: 0px;height:425;min-height:420px;">       
		
				             
		  <c:choose>
			<c:when test="${fn:length(toolSerialNumbers) gt 0}">
		        <table cellpadding="0" cellspacing ="0" border="0" align="right"  width="100%" >
					 <tr>
						 <td height="5px" align="right">&nbsp;</td>		
					 </tr>
			    </table>
		        <table cellpadding="0" cellspacing ="0" border="0" align="right"  width="100%" >
					 <tr>
					     <td height="5px" align="right">&nbsp;<input type="button" onclick="approveDestruction()" value="Approve  Destruction" class="btn btnSMImportant"/>&nbsp;&nbsp;&nbsp;
						 <td height="5px" align="right">&nbsp;<input type="button" onclick="addDestruction()" value="Add Destruction" class="btn btnSMImportant"/>&nbsp;&nbsp;&nbsp;
						 <input type="button" onclick="checkApproval()" value="Select All" class="btn btnSMImportant"/>&nbsp;&nbsp;&nbsp;</td>		
					 </tr>
					 <tr>
						 <td height="5px" align="right">&nbsp;</td>		
					 </tr>
			    </table>
		                                
			<table id="lstProduct" name="lstProduct" class="tableHeading">			     
			     <thead>
			          <tr>
				          <th width="10%" >Serial Id</th>
				          <th width="15%">Lot Number</th>
				          <th width="15%">Module</th>
				          <th width="15%">Serial Number</th>
				          <th width="30%">Accepted Qty</th>
				          <th width="15%">Rejected Qty</th>
			          </tr>  
			     </thead> 
			     <tbody>
			         <c:forEach items="${toolSerialNumbers}" var="toolSerialNumber" varStatus="row">  
				          <tr>
				             <td>	
				             <input type="checkbox" name="serialId" id="acknowledge${row.index}" value="${toolSerialNumber.serialId}"/>
				             &nbsp;&nbsp;&nbsp;&nbsp;${toolSerialNumber.serialId}</td>   
				             <td>${toolSerialNumber.lotNumber}</td>
				             <td>${toolSerialNumber.module}</td>
				             <td>${toolSerialNumber.serialNumber}</td>
				             <td>${toolSerialNumber.acceptQty}</td>
				             <td align="center" style="text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;
								${toolSerialNumber.rejectQty}
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
		
	</center>    
   
</form>
</body>
</html>

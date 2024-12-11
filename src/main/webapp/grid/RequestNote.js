$(document).ready(function (){

	//Set the cursor in last position for the giving text box
	$.fn.selectRange = function(end) {
	    return this.each(function() {
	        if (this.setSelectionRange) {
	            this.focus();
	            this.setSelectionRange(end, end);
	        } else if (this.createTextRange) {
	            var range = this.createTextRange();
	            range.collapse(true);
	            range.moveEnd('character', end);
	            range.moveStart('character', end);
	            range.select();
	        }
	    });
	};
	
	//Set the cursor in first position for the giving text box
	$.fn.selectFWDRange = function(end) {
	    return this.each(function() {
	        if (this.setSelectionRange) {
	            this.focus();
	            this.setSelectionRange(end, end);
	        } else if (this.createTextRange) {
	            var range = this.createTextRange();
	            range.collapse(true);
	            range.moveEnd('character', end);
	            range.moveStart('character', end);
	            range.select();
	        }
	    });
	};
	
	var pattern = /[a-zA-Z]+/g;
	var nopattern = /[0-9]+/g;
	
	 /////////////////////////////Validation/////////////////////////////
    $('#requestNoteData input[type=text]').live("keypress", function(event){
				  
			    var currentId = $(this).closest('TR').attr('id');
		   		var cnt = currentId.substring(currentId.indexOf("-")+1);		

		   		var findFocusId =  $(this).attr('id');	
		   		findFocusId = findFocusId.match(pattern);
		   		switch($.trim(findFocusId))
		   		{		   		   
		   			case "serialNo":
				   		
		   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
		   			break;
	   				case "batchQty":
				   		
		   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
		   			break;
	   				case "receivedQty":
		
						return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
					break;
	   				case "inStock":
	   					
						return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
					break;
	   				case "underInspection":
		
						return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
					break;
		   			
		   		}
    });
    //////////////////////////////////////////////////////////////////// 
	 
		 
	 
	 $('#rule').click(function(event) {
		 $("#overlay").show();
		 $("#dialogRule").show();	
	 });
	  
	 $('#ruleClose').click(function(event) {   
		 $("#dialogRule").hide();
		 $("#overlay").hide();
	 });
	 
 });

function funSerialNo(evt,obj)
 {
	 var cnt = $("#count").val(); 
	 $("#txtSerialNo"+cnt).text($("#serialNo"+cnt).val());

	 var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
     if(charCode == 13 )
     {  
    	 $("#toolName"+cnt).focus();
     }
 }

 function funToolName(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtToolName"+cnt).text($("#toolName"+cnt).val());	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {       	     	    	    	 
	    $("#productName"+cnt).selectFWDRange('0');
     }
 }
 function funProductName(evt,obj)
 {
	 var cnt = $("#count").val();  
	 $("#txtProductName"+cnt).text($("#productName"+cnt).val());	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {         	
		  $("#txtProductName"+cnt).text(trim($("#productName"+cnt).val()));
		  $("#machingType"+cnt).selectFWDRange('0');
	 }
 }

 function funMachingType(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtMachingType"+cnt).text($("#machingType"+cnt).val()); 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {   
    	 $("#txtMachingType"+cnt).text(trim($("#machingType"+cnt).val()));
	     $("#drawingNo"+cnt).selectFWDRange('0');
     }
 }
 
 function funDrawingNo(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtDrawingNo"+cnt).text($("#drawingNo"+cnt).val());
	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {   
    	 $("#txtDrawingNo"+cnt).text(trim($("#drawingNo"+cnt).val()));
	     $("#batchQty"+cnt).selectFWDRange('0');
     }
 }
 
 function funBatchQty(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtBatchQty"+cnt).text($("#batchQty"+cnt).val());
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {
		 $("#txtBatchQty"+cnt).text(trim($("#batchQty"+cnt).val()));
	 	 $("#receivedQty"+cnt).selectFWDRange('0');
     }
 }
 
 function funReceivedQty(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtReceivedQty"+cnt).text($("#receivedQty"+cnt).val());	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {         
    	 $("#txtReceivedQty"+cnt).text(trim($("#receivedQty"+cnt).val()));
		 $("#uom"+cnt).selectFWDRange('0');
     }
 }
   
 function funUOM(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtUom"+cnt).text($("#uom"+cnt).val());
	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {      
    	 $("#txtUom"+cnt).text(trim($("#uom"+cnt).val()));
   	     $("#inStock"+cnt).selectFWDRange('0');
     }
 }
 
 function funInStock(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtInStock"+cnt).text($("#inStock"+cnt).val());
	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {   
    	 $("#txtInStock"+cnt).text(trim($("#inStock"+cnt).val()));
		 $("#underInspection"+cnt).selectFWDRange('0');
     }
 }
 
 function funUnderInspection(evt,obj)
 {
	 var cnt = $("#count").val();  
	 $("#txtUnderInspection"+cnt).text($("#underInspection"+cnt).val());
	 var charCode = (evt.which) ? evt.which : evt.keyCode ; 
	 if(charCode == 13 )
     {    
		 if($("#fieldNotesDialog").is(":visible") == false)
		 {
			 var cnt = $("#count").val();		
			 if($("#serialNo"+cnt).val() != "" || $("#typeOfTool"+cnt).val() != "" || $("#productName"+cnt).val() != "" || $("#machingType"+cnt).val() != "" || $("#drawingNo"+cnt).val() != "" || $("#batchQty"+cnt).val() != "" || $("#receivedQty"+cnt).val() != "" || $("#uom"+cnt).val() != ""  || $("#inStock"+cnt).val() != "" || $("#underInspection"+cnt).val() != "")
			 {   
		    	 var orgDridCount = $("#gridOrgCount").val();  //new
		    	 var addnewrow = parseInt(orgDridCount)+1;     //new
		    	 
		    	 $("#count").val(addnewrow);	    	 
		    	 $("#gridOrgCount").val(addnewrow);
		    	 
		    	 
		          var tdProductName = '<input type="text" name="productName" id="productName'+addnewrow+'" class="textsmall"  value="" onkeydown="javascript:funProductName(event,this);" onkeyup="javascript:funFillProductName(event,this);" autocomplete="off"/><span style="display:none;" id="txtProductName'+addnewrow+'" ></span></td>';		    			          
		          var tdMachingType = '<input type="text" name="machingType" id="machingType'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funMachingType(event,this);" autocomplete="off"/><span style="display:none;" id="txtMachingType'+addnewrow+'" ></span></td>';
		          var tdToolName = '<input type="text" name="typeOfTool" id="typeOfTool'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtTypeOfTool'+addnewrow+'" ></span></td>';
		          var tdDrawingNo = '<input type="text" name="drawingNo" id="drawingNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo'+addnewrow+'" ></span></td>';				      
		          var tdBatchQty = '<input type="text" name="batchQty" id="batchQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funBatchQty(event,this);"  maxlength="3" autocomplete="off"/><span style="display:none;" id="txtBatchQty'+addnewrow+'"></span></td>';				     
		          var tdReceivedQty = '<input type="text" name="requestQty" id="receivedQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funReceivedQty(event,this);"  maxlength="3" autocomplete="off"/><span style="display:none;" id="txtReceivedQty'+addnewrow+'" ></span></td>';
		          var tdUOM = '<input type="text" name="UOM" id="uom'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUom'+addnewrow+'" ></span></td>';				      
		          var tdInStock = '<input type="text" name="inStock" id="inStock'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funInStock(event,this);"  maxlength="3" autocomplete="off"/><span style="display:none;" id="txtInStock'+addnewrow+'" ></span></td>';
		          var tdUnderInspection = '<input type="text" name="underInspection" id="underInspection'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funUnderInspection(event,this);" maxlength="3" autocomplete="off"/><span style="display:none;" id="txtUnderInspection'+addnewrow+'" ></span></td>';
			      var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
				
			      $('#requestNoteData').append('<tr id="rowid-'+addnewrow+'" ><td align="center">'+tdProductName+'</td><td align="center">'+tdMachingType+'</td><td align="center">'+tdToolName+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdBatchQty+'</td><td align="center">'+tdReceivedQty+'</td><td align="center">'+tdUOM+'</td><td align="center">'+tdInStock+'</td><td align="center">'+tdUnderInspection+'</td><td align="center">'+tdDelete+'</td></tr>');
						    	

		    	 var ToolName_visible = $("#typeOfTool"+cnt).is(":visible");
		    	 var ProductName_visible = $("#productName"+cnt).is(":visible");
		    	 var MachingType_visible = $("#machingType"+cnt).is(":visible");
		    	 var DrawingNo_visible = $("#drawingNo"+cnt).is(":visible");
		    	 var BatchQty_visible = $("#batchQty"+cnt).is(":visible");
		    	 var ReceivedQty_visible = $("#receivedQty"+cnt).is(":visible");
		    	 var UOM_visible = $("#uom"+cnt).is(":visible");
		    	 var InStock_visible = $("#inStock"+cnt).is(":visible");
				 var UnderInspection_visible = $("#underInspection"+cnt).is(":visible");	
			 		         	          
		    	
		    	 if(ToolName_visible == true)
		    	 {
		    		 $("#typeOfTool"+cnt).hide();
		    		 $("#txtTypeOfTool"+cnt).text($("#typeOfTool"+cnt).val());
		    		 $("#txtTypeOfTool"+cnt).show();
		    	 }
		    	 if(ProductName_visible == true)
		    	 {
		    		 $("#productName"+cnt).hide();
		    		 $("#txtProductName"+cnt).text($("#productName"+cnt).val());
		    		 $("#txtProductName"+cnt).show();
		    	 }
		    	 if(MachingType_visible == true)
		    	 {
		    		 $("#machingType"+cnt).hide();
		    		 $("#txtMachingType"+cnt).text($("#machingType"+cnt).val());
		    		 $("#txtMachingType"+cnt).show();
		    	 }
		    	 if(DrawingNo_visible == true)
		    	 {
		    		 $("#drawingNo"+cnt).hide();
		    		 $("#txtDrawingNo"+cnt).text($("#drawingNo"+cnt).val());
		    		 $("#txtDrawingNo"+cnt).show();
		    	 }
		    	 if(BatchQty_visible == true )
		    	 {
		    		 $("#batchQty"+cnt).hide();
		    		 $("#txtBatchQty"+cnt).text($("#batchQty"+cnt).val());
		    		 $("#txtBatchQty"+cnt).show();
		    	 }
				 if(ReceivedQty_visible == true)
		    	 {
		    		 $("#receivedQty"+cnt).hide();
		    		 $("#txtReceivedQty"+cnt).text($("#receivedQty"+cnt).val());
		    		 $("#txtReceivedQty"+cnt).show();
		    	 }
				 if(UOM_visible == true)
		    	 {
		    		 $("#uom"+cnt).hide();
		    		 $("#txtUom"+cnt).text($("#uom"+cnt).val());
		    		 $("#txtUom"+cnt).show();
		    	 }
				 if(InStock_visible == true)
		    	 {
		    		 $("#inStock"+cnt).hide();
		    		 $("#txtInStock"+cnt).text($("#inStock"+cnt).val());
		    		 $("#txtInStock"+cnt).show();
		    	 }
				 if(UnderInspection_visible == true)
		    	 {
		    		 $("#underInspection"+cnt).hide();
		    		 $("#txtUnderInspection"+cnt).text($("#underInspection"+cnt).val());
		    		 $("#txtUnderInspection"+cnt).show();
		    	 }		 			 
		    	 		    	 
		         $("#productName"+addnewrow).focus();    
			 }
			 else
			 {
				 $("#productName"+cnt).focus(); 
			 }
		 }
     }
 }
 
 function fundelete(val)
 {	
	 $("#delValue").val(val);	
	 $("#overlay").show();
	 $("#confirmDeleteDialog").fadeIn(300);
 }

 function funConfirmDelete()
 {
	 var delvalue =  $("#delValue").val();

	 $("#rowid-"+delvalue).remove();
	 $("delValue").val("");
	 $("#overlay").hide();
     $("#confirmDeleteDialog").fadeOut(300);

     $('#requestNoteData tr:gt(0)').each(function() {
         
	     var findId = $(this).attr('id');
	     cnt = findId.substring(findId.indexOf("-")+1);
	     if($("#typeOfTool"+cnt).val() == "" && $("#productName"+cnt).val() == "" && $("#machingType"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#batchQty"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" && $("#uom"+cnt).val() == "" && $("#underInspection"+cnt).val() == "" && $("#inStock"+cnt).val() == "")
		 {
	    	 $("#rowid-"+cnt).remove();                    //del row  	 
		 } 			 
     });
 
	 var totalRowCount = $('#requestNoteData tr').length; 
	 if(totalRowCount == 1)
	 {
		 var orgGridCount = $("#gridOrgCount").val();  //new
   	     var addnewrow = parseInt(orgGridCount)+1;     //new 
   	     
   	     $("#count").val(addnewrow);
	   	 $("#gridOrgCount").val(addnewrow);

	   	 var cnt = $("#gridOrgCount").val();  

        var tdProductName = '<input type="text" name="productName" id="productName'+addnewrow+'" class="textsmall"  value="" onkeydown="javascript:funProductName(event,this);" onkeyup="javascript:funFillProductName(event,this);" autocomplete="off"/><span style="display:none;" id="txtProductName'+addnewrow+'" ></span></td>';		    			          
        var tdMachingType = '<input type="text" name="machingType" id="machingType'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funMachingType(event,this);" autocomplete="off"/><span style="display:none;" id="txtMachingType'+addnewrow+'" ></span></td>';
        var tdToolName = '<input type="text" name="typeOfTool" id="typeOfTool'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtTypeOfTool'+addnewrow+'" ></span></td>';
        var tdDrawingNo = '<input type="text" name="drawingNo" id="drawingNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo'+addnewrow+'" ></span></td>';				      
        var tdBatchQty = '<input type="text" name="batchQty" id="batchQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funBatchQty(event,this);"  maxlength="3" autocomplete="off"/><span style="display:none;" id="txtBatchQty'+addnewrow+'"></span></td>';				     
        var tdReceivedQty = '<input type="text" name="requestQty" id="receivedQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funReceivedQty(event,this);" maxlength="3" autocomplete="off"/><span style="display:none;" id="txtReceivedQty'+addnewrow+'" ></span></td>';
        var tdUOM = '<input type="text" name="UOM" id="uom'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUom'+addnewrow+'" ></span></td>';				      
        var tdInStock = '<input type="text" name="inStock" id="inStock'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funInStock(event,this);"  maxlength="3" autocomplete="off"/><span style="display:none;" id="txtInStock'+addnewrow+'" ></span></td>';
        var tdUnderInspection = '<input type="text" name="underInspection" id="underInspection'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funUnderInspection(event,this);"  maxlength="3" autocomplete="off"/><span style="display:none;" id="txtUnderInspection'+addnewrow+'" ></span></td>';
	    var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
	 		         	          
	    $('#requestNoteData').append('<tr id="rowid-'+addnewrow+'" ><td align="center">'+tdProductName+'</td><td align="center">'+tdMachingType+'</td><td align="center">'+tdToolName+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdBatchQty+'</td><td align="center">'+tdReceivedQty+'</td><td align="center">'+tdUOM+'</td><td align="center">'+tdInStock+'</td><td align="center">'+tdUnderInspection+'</td><td align="center">'+tdDelete+'</td></tr>');
	     
	    $("#serialNo"+addnewrow).focus();  
	 }
	 var firstTrId = $('#requestNoteData').find('tr:eq(1)').attr('id');
	 var IdNo = firstTrId.substring(firstTrId.indexOf("-")+1);	        
	 if(IdNo > 0)
	    $("#newMidRow"+IdNo).css('visibility', 'hidden');
	 
 }

 function hidedeleteDialog()
 {
	 var cnt = $("#count").val();
	 var totalRowCount = $('#requestNoteData tr').length;
	 
	 if((totalRowCount > 2) && ( $("#typeOfTool"+cnt).val() == "" && $("#productName"+cnt).val() == "" && $("#machingType"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#batchQty"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" && $("#uom"+cnt).val() == "" && $("#inStock"+cnt).val() == "" && $("#underInspection"+cnt).val() == ""))
	 {
    	 $("#rowid-"+cnt).remove();                    //del row
	 } 
	 
	 $("#delValue").val("");
	 
	 $("#overlay").hide();
     $("#confirmDeleteDialog").fadeOut(300);	
     $("delValue").val("");
 }
 
 
 function funAddNewRow()
 {
	 /////////Show span Hide text///////// 
	 $('#requestNoteData input[type=text]').each(function (){
			 
			 if($('#requestNoteData input[type=text]').is(":visible") == true)
			 {
				 $('#requestNoteData input[type=text]').hide();
				 $('#requestNoteData span').show();
			 }
	 });	 
	 ///////////////////////////////////// 

     $('#requestNoteData tr:gt(0)').each(function() {

    	 var findId = $(this).attr('id');
	      cnt = findId.substring(findId.indexOf("-")+1);	

	      if($("#typeOfTool"+cnt).val() == "" && $("#productName"+cnt).val() == "" && $("#machingType"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#batchQty"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" && $("#uom"+cnt).val() == "" && $("#underInspection"+cnt).val() == "" && $("#inStock"+cnt).val() == "")
	 	  {
	     	 $("#rowid-"+cnt).remove();                    //del row
	 	  } 
	  });    

     //if(blnSetNo)
	 //{     	 
		 var addnewrow = parseInt($("#gridOrgCount").val())+1;
		 
		 $("#count").val(addnewrow);	 
		 $("#gridOrgCount").val(addnewrow);

		 var cnt = $("#gridOrgCount").val();
		 
		
         
         var tdProductName = '<input type="text" name="productName" id="productName'+addnewrow+'" class="textsmall"  value="" onkeydown="javascript:funProductName(event,this);" onkeyup="javascript:funFillProductName(event,this);" autocomplete="off"/><span style="display:none;" id="txtProductName'+addnewrow+'" ></span></td>';		    			          
         var tdMachingType = '<input type="text" name="machingType" id="machingType'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funMachingType(event,this);" autocomplete="off"/><span style="display:none;" id="txtMachingType'+addnewrow+'" ></span></td>';
         var tdToolName = '<input type="text" name="typeOfTool" id="typeOfTool'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtTypeOfTool'+addnewrow+'" ></span></td>';
         var tdDrawingNo = '<input type="text" name="drawingNo" id="drawingNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo'+addnewrow+'" ></span></td>';				      
         var tdBatchQty = '<input type="text" name="batchQty" id="batchQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funBatchQty(event,this);" maxlength="3" autocomplete="off"/><span style="display:none;" id="txtBatchQty'+addnewrow+'"></span></td>';				     
         var tdReceivedQty = '<input type="text" name="requestQty" id="receivedQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funReceivedQty(event,this);" maxlength="3" autocomplete="off"/><span style="display:none;" id="txtReceivedQty'+addnewrow+'" ></span></td>';
         var tdUOM = '<input type="text" name="UOM" id="uom'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUom'+addnewrow+'" ></span></td>';				      
         var tdInStock = '<input type="text" name="inStock" id="inStock'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funInStock(event,this);" maxlength="3" autocomplete="off"/><span style="display:none;" id="txtInStock'+addnewrow+'" ></span></td>';
         var tdUnderInspection = '<input type="text" name="underInspection" id="underInspection'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funUnderInspection(event,this);" maxlength="3" autocomplete="off"/><span style="display:none;" id="txtUnderInspection'+addnewrow+'" ></span></td>';
	     var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
	 		         	          
	     $('#requestNoteData').append('<tr id="rowid-'+addnewrow+'" ><td align="center">'+tdProductName+'</td><td align="center">'+tdMachingType+'</td><td align="center">'+tdToolName+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdBatchQty+'</td><td align="center">'+tdReceivedQty+'</td><td align="center">'+tdUOM+'</td><td align="center">'+tdInStock+'</td><td align="center">'+tdUnderInspection+'</td><td align="center">'+tdDelete+'</td></tr>');

	     $("#productName"+addnewrow).focus();
     //}
     var firstTrId = $('#requestNoteData').find('tr:eq(1)').attr('id');
	 var IdNo = firstTrId.substring(firstTrId.indexOf("-")+1);	        
	 if(IdNo > 0)
	    $("#newMidRow"+IdNo).css('visibility', 'hidden');
 }
 
 
    function funOpenRow(cnt)
	{
		if(!$('#requestNoteData tr rowid-'+cnt+' span').is(":visible"))
		 {
	    	  $("#typeOfTool"+cnt).show();
	    	  $("#productName"+cnt).show(); 
	    	  $("#machingType"+cnt).show();
	    	  $("#drawingNo"+cnt).show();
	    	  $("#batchQty"+cnt).show();
	    	  $("#receivedQty"+cnt).show();
	    	  $("#uom"+cnt).show();
	    	  $("#inStock"+cnt).show();
			  $("#underInspection"+cnt).show();    	  
	    	 
			  $("#txtSerialNo"+cnt).hide();
	    	  $("#txtTypeOfTool"+cnt).hide();
	    	  $("#txtProductName"+cnt).hide();
	    	  $("#txtMachingType"+cnt).hide();
	    	  $("#txtDrawingNo"+cnt).hide();
	    	  $("#txtBatchQty"+cnt).hide();
	    	  $("#txtReceivedQty"+cnt).hide();
	    	  $("#txtUom"+cnt).hide();
	    	  $("#txtInStock"+cnt).hide();
			  $("#txtUnderInspection"+cnt).hide();  	  
		 }
	}

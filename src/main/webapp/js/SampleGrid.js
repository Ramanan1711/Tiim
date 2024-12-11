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
	 
	 $('#cylinderFieldData tr:not(:has(th))').live("dblclick", function() {
 		 
			var cnt = $("#count").val();
			var rowCountLength = $('#cylinderFieldData tr:not(:has(th))').length;
			if((rowCountLength > 1) && $("#serialNo"+cnt).val() == "" && $("#toolNo"+cnt).val() == "" && $("#toolName"+cnt).val() == "" && $("#customerPoNo"+cnt).val() == "" && $("#customerPoDate"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#inspReportNo"+cnt).val() == "" && $("#dqDetails"+cnt).val() == "" &&  $("#moc"+cnt).val() == "" && $("#supplierInvoiceNo"+cnt).val() == "" &&  $("#supplierInvoiceDate"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" &&  $("#uom"+cnt).val() == "" && $("#toolSerialNo"+cnt).val() == "" && $("#validationStatus"+cnt).val() == "" && $("#toolSerialNo"+cnt).val() == "")
			{
		    	 $("#rowid-"+cnt).remove();                    //del row
			}
	         ///////////////////////////Hide the prev input text and show the span////////////// 
			 
			 $('#cylinderFieldData input[type=text]').each(function (){
				 
				 if($(this).is(":visible") == true)
				 {
					 $(this).hide();
					 $('#cylinderFieldData span').show();
				 }
			 });		
			 //////////////////////////////////////////////////////////////////////// 
			 
			  var currentId = $(this).closest('TR').attr('id');
	          var index = currentId.substring(currentId.indexOf("-")+1);

			  $("#serialNo"+index).show();
	    	  $("#toolNo"+index).show();
	    	  $("#toolName"+index).show();
	    	  $("#customerPoNo"+index).show(); 
	    	  $("#customerPoDate"+index).show();
	    	  $("#drawingNo"+index).show();
	    	  $("#inspReportNo"+index).show();
	    	  $("#dqDetails"+index).show();
			  $("#moc"+index).show();
	    	  $("#supplierInvoiceNo"+index).show();
	    	  $("#supplierInvoiceDate"+index).show();
	    	  $("#receivedQty"+index).show();
	    	  $("#uom"+index).show();    	  
			  $("#toolSerialNo"+index).show();
	    	  $("#validationStatus"+index).show();
	    	 

	    	  $("#txtSerialNo"+index).hide();
	    	  $("#txtToolNo"+index).hide();
	    	  $("#txtToolName"+index).hide();
	    	  $("#txtCustomerPoNo"+index).hide();
	    	  $("#txtCustomerPoDate"+index).hide();
	    	  $("#txtDrawingNo"+index).hide();	  
	    	  $("#txtInspReportNo"+index).hide();	    	 
	    	  $("#txtDqDetails"+index).hide();
			  $("#txtMOC"+index).hide();
	    	  $("#txtSupplierInvoiceNo"+index).hide();
	    	  $("#txtSupplierInvoiceDate"+index).hide();	    	  
	    	  $("#txtSupplierInvoiceDate"+index).hide();
	    	  $("#txtUOM"+index).hide();	    	  	    	  	    	 
			  $("#txtToolSerialNo"+index).hide();
	    	  $("#txtValidationStatus"+index).hide();
	    	 
	          /*******************Assigning the value from edit to current*****************/
	    	  $("#count").val(index);    //  
	    	  /****************************************************************************/	    			
	 });
	 
	     	 	
	 $('#cylinderFieldData input[type=text]').live("keydown", function(event){
		 		 
		   if(event.which == 37 )                           // For left arrow 
	       {
			    var currentId = $(this).closest('TR').attr('id');
		   		var cnt = currentId.substring(currentId.indexOf("-")+1);		

		   		var findId =  $(this).attr('id');			   			   	
		   		findId = findId.match(pattern);	
		   		findId = $.trim(findId);		   	    	 		    	
		   		   
		   		if(findId == "serialNo")
		   		{		   			
		   			$("#serialNo"+cnt).focus();			   			
		   		}
		   		else if(findId == "toolNo")
		   		{	
			       	if($("#toolNo"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
			       			$("#serialNo"+cnt).focus();	
			       		},400);
		   			}
		   		}
		   		else if(findId == "toolName")
		   		{				       	
			    	if($("#toolName"+cnt).prop('selectionStart') == 0)
		   			{
			    		setTimeout(function() {
		    				$("#toolNo"+cnt).focus();	
		       			},400);
		   			}
		   		}
		   		else if(findId == "customerPoNo")
		   		{				       	
			       	if($("#customerPoNo"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
	   						$("#toolName"+cnt).focus(); 
	   					},400);	   						   					
		   			}
		   		}			  
		   		else if(findId == "customerPoDate")
		   		{				       	
			       	if($("#customerPoDate"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
		       				$("#customerPoNo"+cnt).focus(); 
		       			},400);
		   			}
		   		}
				else if(findId == "drawingNo")
		   		{				       	
			       	if($("#drawingNo"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
			       			$("#customerPoDate"+cnt).focus();
			       		},400);
		   			}
		   		}
				else if(findId == "inspReportNo")
		   		{				       	
			       	if($("#inspReportNo"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
			       			$("#drawingNo"+cnt).focus();
			       		},400);
		   			}
		   		}
				else if(findId == "dqDetails")
		   		{				       	
			       	if($("#dqDetails"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
			       			$("#inspReportNo"+cnt).focus();
			       		},400);	   					
		   			}
		   		}
				else if(findId == "moc")
		   		{		
			    	if($("#moc"+cnt).prop('selectionStart') == 0)
		   			{
			    		setTimeout(function() {
	   						$("#dqDetails"+cnt).focus();
	   					},400);
		   			}
		   		}
				else if(findId == "supplierInvoiceNo")
		   		{				       	
			       	if($("#supplierInvoiceNo"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
	   						$("#moc"+cnt).focus();
	   					},400);		   				
		   			}
		   		}
				else if(findId == "supplierInvoiceDate")
		   		{								
			       	if($("#supplierInvoiceDate"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
	   						$("#supplierInvoiceNo"+cnt).focus();
	   					},400);	   				
		   			}
		   		}		   
				else if(findId == "receivedQty")
		   		{				       	
			       	if($("#receivedQty"+cnt).prop('selectionStart') == 0)
		   			{			       		
			       		setTimeout(function() {
	   						$("#supplierInvoiceDate"+cnt).focus();
	   					},400);	   					
		   			}
		   		}
				else if(findId == "uom")
		   		{				       	
			       	if($("#uom"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
	   						$("#receivedQty"+cnt).focus();
	   					},400);			       	
		   			}
		   		}	
				else if(findId == "validationStatus")
		   		{					
					if($("#validationStatus"+cnt).prop('selectionStart') == 0)
		   			{
						setTimeout(function() {
	   						$("#uom"+cnt).focus();
	   					},400);	   					
		   			}
		   		}
				else if(findId == "toolSerialNo")
		   		{						
					if($("#toolSerialNo"+cnt).prop('selectionStart') == 0)
		   			{
	   					setTimeout(function() {
	   						$("#validationStatus"+cnt).selectRange($("#validationStatus"+cnt).val().length);
	   					},400);
		   			}
		   		}								   		
		   		else
		   		{
		   			$("#location"+cnt).focus();
		   		}
	       }
		   else if(event.which == 38 )                   // For up arrow           
		   {			 			  
			    var currentId = $(this).closest('TR').attr('id');			    
                var findId = currentId.substring(currentId.indexOf("-")+1);	
                var findIdCnt = (findId);	
        		   				
		   		var findPrevId = $(this).parent().parent().prev().attr('id');
		   		var cnt = findPrevId.substring((findPrevId.indexOf("-")+1));

		   		var findFocusId =  $(this).attr('id');	
		   		findFocusId = findFocusId.match(pattern);

		   		/*******************For Navigation(up,down,left,right) Enter key to move next one****************/

		   	     $("#count").val(cnt);		   	             
			   	/************************************************************************************************/
		   		
                if($("#serialNo"+findIdCnt).val() == "" && $("#toolNo"+findIdCnt).val() == "" && $("#toolName"+findIdCnt).val() == "" && $("#customerPoNo"+findIdCnt).val() == "" && $("#customerPoDate"+findIdCnt).val() == "" && $("#drawingNo"+findIdCnt).val() == "" && $("#dqDetails"+findIdCnt).val() == "" &&  $("#moc"+findIdCnt).val() == "" && $("#supplierInvoiceNo"+findIdCnt).val() == "" &&  $("#supplierInvoiceDate"+findIdCnt).val() == "" && $("#receivedQty"+findIdCnt).val() == ""&& $("#uom"+findIdCnt).val() == "" && $("#toolSerialNo"+findIdCnt).val() == "" && $("#validationStatus"+findIdCnt).val() == "")
	 		    {
	 	    	  $("#rowid-"+findIdCnt).remove();                    //del row
	 	    	  findIdCnt = $("#gridOrgCount").val();   	 
	 		    }
                
		   		$('#rowid-'+findIdCnt+' input[type=text]').hide();
				$('#rowid-'+findIdCnt+' span').show();
				   
		   		$('#rowid-'+cnt+' input[type=text]').show();
				$('#rowid-'+cnt+' span').hide();

				findFocusId = $.trim(findFocusId);
		   		if(findFocusId == "serialNo")
		   		{				   			 
		   			$("#serialNo"+cnt).focus();			       			   			
		   		}
		   		else if(findFocusId == "toolNo")
		   		{	
			        $("#toolNo"+cnt).focus();
		   		}
		   		else if(findFocusId == "toolName")
		   		{				       
			       	$("#toolName"+cnt).focus();
		   		}
		   		else if(findFocusId == "customerPoNo")
		   		{				     
			       	$("#customerPoNo"+cnt).focus();
		   		}
		   		else if(findFocusId == "customerPoDate")
		   		{	
			  		$("#customerPoDate"+cnt).focus();
		   		}
				else if(findFocusId == "drawingNo")
		   		{	
			       	$("#drawingNo"+cnt).focus();
		   		}
				else if(findFocusId == "inspReportNo")
		   		{	
			       	$("#inspReportNo"+cnt).focus();
		   		}
				else if(findFocusId == "dqDetails")
		   		{	
			       $("#dqDetails"+cnt).focus();
		   		}
				else if(findFocusId == "moc")
		   		{	
			       $("#moc"+cnt).focus();
		   		}
				else if(findFocusId == "supplierInvoiceNo")
		   		{	
			       $("#supplierInvoiceNo"+cnt).focus();
		   		}
				else if(findFocusId == "supplierInvoiceDate")
		   		{	
			       	$("#supplierInvoiceDate"+cnt).focus();
		   		}
				else if(findFocusId == "receivedQty")
		   		{	
			       $("#receivedQty"+cnt).focus();
		   		}
				else if(findFocusId == "uom")
		   		{	
			       	$("#uom"+cnt).focus();
		   		}		
				else if(findFocusId == "validationStatus")
		   		{	
			       $("#validationStatus"+cnt).focus();
		   		}
				else if(findFocusId == "toolSerialNo")
		   		{	
			       $("#toolSerialNo"+cnt).focus();
		   		}					
		   }
		   else if(event.which == 40)                   // For down arrow           
		   {
			   var currentId = $(this).closest('TR').attr('id');
		   	   var findId = currentId.substring(currentId.indexOf("-")+1);
		   	   var findIdCnt = (findId);		   
         		   				
		   	   var findNextId = $(this).parent().parent().next().attr('id');
		   	   var cnt = findNextId.substring((findNextId.indexOf("-")+1));

		   	   var findFocusId =  $(this).attr('id');				
	   		   findFocusId = findFocusId.match(pattern);

		       /*******************For Navigation(up,down,left,right) Enter key to move next one****************/		   		
		   	     $("#count").val(cnt);		   	             
			   /************************************************************************************************/

		   	   if($("#serialNo"+findIdCnt).val() == "" && $("#toolNo"+findIdCnt).val() == "" && $("#toolName"+findIdCnt).val() == "" && $("#customerPoNo"+findIdCnt).val() == "" && $("#customerPoDate"+findIdCnt).val() == "" && $("#drawingNo"+findIdCnt).val() == "" && $("#dqDetails"+findIdCnt).val() == "" &&  $("#moc"+findIdCnt).val() == "" && $("#supplierInvoiceNo"+findIdCnt).val() == "" &&  $("#supplierInvoiceDate"+findIdCnt).val() == "" && $("#receivedQty"+findIdCnt).val() == "" && $("#uom"+findIdCnt).val() == "" && $("#toolSerialNo"+findIdCnt).val() == "" && $("#validationStatus"+findIdCnt).val() == "")
	 		   {
	 	    	  $("#rowid-"+findIdCnt).remove();                    //del row
	 	    	  findIdCnt = $("#gridOrgCount").val();   	 
	 		   }
		   	   
			   $('#rowid-'+findIdCnt+' input[type=text]').hide();
			   $('#rowid-'+findIdCnt+' span').show();
				   
		   	   $('#rowid-'+cnt+' input[type=text]').show();
			   $('#rowid-'+cnt+' span').hide();

			   findFocusId = $.trim(findFocusId);
			   if(findFocusId == "serialNo")
		   		{				   			 
		   			$("#serialNo"+cnt).focus();			       			   			
		   		}
		   		else if(findFocusId == "toolNo")
		   		{	
			        $("#toolNo"+cnt).focus();
		   		}
		   		else if(findFocusId == "toolName")
		   		{				       
			       	$("#toolName"+cnt).focus();
		   		}
		   		else if(findFocusId == "customerPoNo")
		   		{				     
			       	$("#customerPoNo"+cnt).focus();
		   		}
		   		else if(findFocusId == "customerPoDate")
		   		{	
			  		$("#customerPoDate"+cnt).focus();
		   		}
				else if(findFocusId == "drawingNo")
		   		{	
			       	$("#drawingNo"+cnt).focus();
		   		}
				else if(findFocusId == "inspReportNo")
		   		{	
			       	$("#inspReportNo"+cnt).focus();
		   		}
				else if(findFocusId == "dqDetails")
		   		{	
			       $("#dqDetails"+cnt).focus();
		   		}
				else if(findFocusId == "moc")
		   		{	
			       $("#moc"+cnt).focus();
		   		}
				else if(findFocusId == "supplierInvoiceNo")
		   		{	
			       $("#supplierInvoiceNo"+cnt).focus();
		   		}
				else if(findFocusId == "supplierInvoiceDate")
		   		{	
			       	$("#supplierInvoiceDate"+cnt).focus();
		   		}
				else if(findFocusId == "receivedQty")
		   		{	
			       $("#receivedQty"+cnt).focus();
		   		}
				else if(findFocusId == "uom")
		   		{	
			       	$("#uom"+cnt).focus();
		   		}	
				else if(findFocusId == "validationStatus")
		   		{	
			       $("#validationStatus"+cnt).focus();
		   		}
				else if(findFocusId == "toolSerialNo")
		   		{	
			       $("#toolSerialNo"+cnt).focus();
		   		}				
		   }
		   else if(event.which == 39)                   // For right arrow           
		   {
			    var currentId = $(this).closest('TR').attr('id');
		   		var cnt = currentId.substring(currentId.indexOf("-")+1);		

		   		var findId =  $(this).attr('id');			   			   	
		   		findId = findId.match(pattern);	
		   		findId = $.trim(findId);	   	    	 		    	
		   		   
		   		if(findId == "serialNo")
		   		{				       	
		   			if($("#serialNo"+cnt).prop('selectionStart') >= $("#serialNo"+cnt).val().length)
		   			{
	   					setTimeout(function() {
	   					$("#toolNo"+cnt).selectFWDRange('0');
	   					}, 300);
		   			}		
		   		}
		   		else if(findId == "toolNo")
		   		{	
		   			if($("#toolNo"+cnt).prop('selectionStart') >= $("#toolNo"+cnt).val().length)
		   			{
		   				setTimeout(function() {
	   						$("#toolName"+cnt).selectFWDRange('0');
	   					}, 300);
		   			}
		   		}
		   		else if(findId == "toolName")
		   		{	
		   			if($("#toolName"+cnt).prop('selectionStart') >= $("#toolName"+cnt).val().length)
		   			{
		   				setTimeout(function() {
	   						$("#customerPoNo"+cnt).selectFWDRange('0');
	   					}, 300);
		   			}
		   		}
		   		else if(findId == "customerPoNo")
		   		{	
		   			if($("#customerPoNo"+cnt).prop('selectionStart') >= $("#customerPoNo"+cnt).val().length)
		   			{		   				
		   				setTimeout(function() {
		   					$("#customerPoDate"+cnt).selectFWDRange('0');
		   				}, 300);	   						   					   					
		   			}
		   		}
		   		else if(findId == "customerPoDate")
		   		{	
		   			if($("#customerPoDate"+cnt).prop('selectionStart') >= $("#customerPoDate"+cnt).val().length)
		   			{
		   				setTimeout(function() {
		       				$("#drawingNo"+cnt).selectFWDRange('0');
		       			},300);
		   			}
		   		}
				else if(findId == "drawingNo")
		   		{	
					if($("#drawingNo"+cnt).prop('selectionStart') >= $("#drawingNo"+cnt).val().length)
		   			{
						setTimeout(function() {
			       			$("#inspReportNo"+cnt).selectFWDRange('0');
			       			},300);
		   			}
		   		}
				else if(findId == "inspReportNo")
		   		{	
					if($("#inspReportNo"+cnt).prop('selectionStart') >= $("#inspReportNo"+cnt).val().length)
		   			{
						setTimeout(function() {
			       			$("#dqDetails"+cnt).selectFWDRange('0');
			       			},300);   					
		   			}
		   		}
				else if(findId == "dqDetails")
		   		{	
					if($("#dqDetails"+cnt).prop('selectionStart') >= $("#dqDetails"+cnt).val().length)
		   			{
						setTimeout(function() {
		   					$("#moc"+cnt).selectFWDRange('0');
		   				}, 300);  					
		   			}
		   		}
				else if(findId == "moc")
		   		{	
					if($("#moc"+cnt).prop('selectionStart') >= $("#moc"+cnt).val().length)
		   			{
						setTimeout(function() {
		   					$("#supplierInvoiceNo"+cnt).selectFWDRange('0');
		   				}, 300);	   					
		   			}
		   		}
				else if(findId == "supplierInvoiceNo")
		   		{	
					if($("#supplierInvoiceNo"+cnt).prop('selectionStart') >= $("#supplierInvoiceNo"+cnt).val().length)
		   			{
						setTimeout(function() {
		   					$("#supplierInvoiceDate"+cnt).selectFWDRange('0');
		   				}, 300);		   					
		   			}
		   		}
				else if(findId == "supplierInvoiceDate")
		   		{	
					if($("#supplierInvoiceDate"+cnt).prop('selectionStart') >= $("#supplierInvoiceDate"+cnt).val().length)
		   			{
						setTimeout(function() {
		   					$("#receivedQty"+cnt).selectFWDRange('0');
		   				}, 300);							   					
		   			}
		   		}
				else if(findId == "receivedQty")
		   		{	
					if($("#receivedQty"+cnt).prop('selectionStart') >= $("#receivedQty"+cnt).val().length)
		   			{
						setTimeout(function() {
		   					$("#uom"+cnt).selectFWDRange('0');
		   				}, 300);
		   			}
		   		}
				else if(findId == "uom")
		   		{	
					if($("#uom"+cnt).prop('selectionStart') >= $("#uom"+cnt).val().length)
		   			{
						setTimeout(function() {
		   					$("#validationStatus"+cnt).selectFWDRange('0');
		   				}, 300);	   					
		   			}
		   		}
				else if(findId == "validationStatus")
		   		{		
					if($("#validationStatus"+cnt).prop('selectionStart') >= $("#validationStatus"+cnt).val().length)
		   			{
						setTimeout(function() {
		   					$("#toolSerialNo"+cnt).selectFWDRange('0');
		   				}, 300);	   					
		   			}
		   		}
				else if(findId == "toolSerialNo")
		   		{		
					if($("#toolSerialNo"+cnt).prop('selectionStart') >= $("#toolSerialNo"+cnt).val().length)
		   			{
	   					setTimeout(function() {
	   					$("#waterAdd"+cnt).selectFWDRange('0');
	   					}, 300);
		   			}
		   		}					
		   }
		   else if(event.which == 36)  				    //Home key to go first 
		   {
			   var currentId = $(this).closest('TR').attr('id');
		   	   var cnt = currentId.substring(currentId.indexOf("-")+1);
		   	   $("#serialNo"+cnt).focus();
		   }
		   else if(event.which == 35)    //End key to go last
		   {
			   var currentId = $(this).closest('TR').attr('id');
		   	   var cnt = currentId.substring(currentId.indexOf("-")+1);
		   	   $("#toolSerialNo"+cnt).focus();
		   }
		   	 
	 });

	 $('img[name=midRow]').live("click", function()
	 {
    	 var lastRowId = $("#cylinderFieldData tr:last").attr('id');
    	 var currentImgId = $(this).closest('img').attr('id');
    	 var currentRowId = $(this).closest('TR').attr('id');

    	 var orgCnt = $("#gridOrgCount").val(); 	    
    	 var totalRowCount = $('#cylinderFieldData tr').length;

    	 var cnt = $(this).attr('id');			   			   	
	         cnt = cnt.match(nopattern);	
	         cnt = $.trim(cnt);

	     /*if($("#serialNo"+cnt).val() == "")
	   	 {
	    	 jAlert('Please Enter Ticket No.', 'Alert', function(){	
    			 $("#serialNo"+cnt).focus();								 		 	    	 
 		     });
       	     return false; 
	   	 }
	   	 else
	   	 { */	 
	    	 if(totalRowCount > 2)        ///Check minimum one row is there or not.
	    	 {
	    		 $('#cylinderFieldData tr:gt(0)').each(function() {

				     var findId = $(this).attr('id');
	 		         cnt = findId.substring(findId.indexOf("-")+1);	
	    		
			    	 if($("#serialNo"+cnt).val() == "" && $("#toolNo"+cnt).val() == "" && $("#toolName"+cnt).val() == "" && $("#customerPoNo"+cnt).val() == "" && $("#customerPoDate"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#inspReportNo"+cnt).val() == "" && $("#dqDetails"+cnt).val() == "" &&  $("#moc"+cnt).val() == "" && $("#supplierInvoiceNo"+cnt).val() == "" &&  $("#supplierInvoiceDate"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" && $("#uom"+cnt).val() == ""  && $("#validationStatus"+cnt).val() == "" && $("#toolSerialNo"+cnt).val() == "")
			 		 {
			 	    	 $("#rowid-"+cnt).remove();                    //del row				           		 	    					 	    	
			 		 }
	    		 });	
	 		     var cnt = $("#gridOrgCount").val(); 
			    		 
		   		 var addnewrow = parseInt(cnt)+1;
		   		 $("#count").val(addnewrow);	 
		   		 $("#gridOrgCount").val(addnewrow);
		
		   		 /*********Disable All text box and Enable text box*********************/  		    
		  				 $('#cylinderFieldData input[type=text]').hide();
		  				 $('#cylinderFieldData span').show(); 			
		  		 /**********************************************************************/
			    	 	
				  var tdSerialNo = '<input type="text" name="fldSerialNo" id="serialNo'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);"  autocomplete="off"/><span style="display:none;" id="txtSerialNo'+addnewrow+'" ></span></td>';
		          var tdToolNo = '<input type="text" name="fldToolNo" id="toolNo'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funToolNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolNo'+addnewrow+'" ></span></td>';
		          var tdToolName = '<input type="text" name="fldToolName" id="toolName'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolName'+addnewrow+'" ></span></td>';
		          var tdCustomerPoNo = '<input type="text" name="fldCustomerPoNo" id="customerPoNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoNo'+addnewrow+'" ></span></td>';		    			          
		          var tdCustomerPoDate = '<input type="text" name="fldCustomerPoDate" id="customerPoDate'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoDate'+addnewrow+'" ></span></td>';
		          var tdDrawingNo = '<input type="text" name="fldDrawingNo" id="drawingNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo'+addnewrow+'" ></span></td>';				      
		          var tdInspReportNo = '<input type="text" name="fldInspReportNo" id="inspReportNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funInspReportNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtInspReportNo'+addnewrow+'"></span></td>';				     
		          var tdDqDetails = '<input type="text" name="fldDqDetails" id="dqDetails'+addnewrow+'" class="textsmall"  value="" onkeyup="javascript:funDqDetails(event,this);" autocomplete="off"/><span style="display:none;" id="txtDqDetails'+addnewrow+'" ></span></td>';
		          var tdMOC = '<input type="text" name="fldMOC" id="moc'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funMOC(event,this);" autocomplete="off"/><span style="display:none;" id="txtMOC'+addnewrow+'" ></span></td>';
		          var tdSupplierInvoiceNo = '<input type="text" name="fldSupplierInvoiceNo" id="supplierInvoiceNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceNo'+addnewrow+'" ></span></td>';
		          var tdSupplierInvoiceDate = ' <input type="text" name="fldSupplierInvoiceDate" id="supplierInvoiceDate'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate'+addnewrow+'" ></span></td>';				      
		          var tdReceivedQty = '<input type="text" name="fldReceivedQty" id="receivedQty'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funReceivedQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate'+addnewrow+'" ></span></td>';
		          var tdUOM = '<input type="text" name="fldUOM" id="uom'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUOM'+addnewrow+'" ></span></td>';				      
		          var tdValidationStatus = '<input type="text" name="fldValidationStatus" id="validationStatus'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funValidationStatus(event,this);"  autocomplete="off"/><span style="display:none;" id="txtValidationStatus'+addnewrow+'" ></span></td>';
		          var tdToolSerialNo = '<input type="text" name="fldToolSerialNo" id="toolSerialNo'+addnewrow+'" class="txtleastmin"  value="" onkeyup="javascript:funToolSerialNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolSerialNo'+addnewrow+'" ></span></td>';
			      var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
				
	             $(this).closest('#'+currentRowId).before('<tr id="rowid-'+addnewrow+'" ><td align="center">'+tdSerialNo+'</td><td align="center">'+tdToolNo+'</td><td align="center">'+tdToolName+'</td><td align="center">'+tdCustomerPoNo+'</td><td align="center">'+tdCustomerPoDate+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdInspReportNo+'</td><td align="center">'+tdDqDetails+'</td><td align="center">'+tdMOC+'</td><td align="center">'+tdSupplierInvoiceNo+'</td><td align="center">'+tdSupplierInvoiceDate+'</td><td align="center">'+tdReceivedQty+'</td><td align="center">'+tdUOM+'</td><td align="center">'+tdValidationStatus+'</td><td align="center">'+tdToolSerialNo+'</td><td align="center">'+tdDelete+'</td></tr>');
						    	
	    		 var Serial_visible = $("#serialNo"+cnt).is(":visible");
		    	 var ToolNo_visible = $("#toolNo"+cnt).is(":visible");
		    	 var ToolName_visible = $("#toolName"+cnt).is(":visible");
		    	 var CustomerPoNo_visible = $("#customerPoNo"+cnt).is(":visible");
		    	 var CustomerPoDate_visible = $("#customerPoDate"+cnt).is(":visible");
		    	 var DrawingNo_visible = $("#drawingNo"+cnt).is(":visible");
		    	 var InspReportNo_visible = $("#inspReportNo"+cnt).is(":visible");
				 var DqDetails_visible = $("#dqDetails"+cnt).is(":visible");
		    	 var MOC_visible = $("#moc"+cnt).is(":visible");
				 var SupplierInvoiceNo_visible = $("#supplierInvoiceNo"+cnt).is(":visible");
		    	 var SupplierInvoiceDate_visible = $("#supplierInvoiceDate"+cnt).is(":visible");		    	
		    	 var ReceivedQty_visible = $("#receivedQty"+cnt).is(":visible");
		    	 var UOM_visible = $("#uom"+cnt).is(":visible");
		    	 var ValidationStatus_visible = $("#validationStatus"+cnt).is(":visible");
				 var ToolSerialNo_visible = $("#toolSerialNo"+cnt).is(":visible");	    	  
				
		    	 if(Serial_visible == true)
		    	 {
		    		 $("#serialNo"+cnt).hide();
		    		 $("#txtSerialNo"+cnt).text($("#serialNo"+cnt).val());
		    		 $("#txtSerialNo"+cnt).show();
		    	 }
		    	 if(ToolNo_visible == true)
		    	 {
		    		 $("#toolNo"+cnt).hide();
		    		 $("#txtToolNo"+cnt).text($("#toolNo"+cnt).val());
		    		 $("#txtToolNo"+cnt).show();
		    	 }
		    	 if(ToolName_visible == true)
		    	 {
		    		 $("#toolName"+cnt).hide();
		    		 $("#txtToolName"+cnt).text($("#toolName"+cnt).val());
		    		 $("#txtToolName"+cnt).show();
		    	 }
		    	 if(CustomerPoNo_visible == true)
		    	 {
		    		 $("#customerPoNo"+cnt).hide();
		    		 $("#txtCustomerPoNo"+cnt).text($("#customerPoNo"+cnt).val());
		    		 $("#txtCustomerPoNo"+cnt).show();
		    	 }
		    	 if(CustomerPoDate_visible == true)
		    	 {
		    		 $("#customerPoDate"+cnt).hide();
		    		 $("#txtCustomerPoDate"+cnt).text($("#customerPoDate"+cnt).val());
		    		 $("#txtCustomerPoDate"+cnt).show();
		    	 }
		    	 if(DrawingNo_visible == true)
		    	 {
		    		 $("#drawingNo"+cnt).hide();
		    		 $("#txtDrawingNo"+cnt).text($("#drawingNo"+cnt).val());
		    		 $("#txtDrawingNo"+cnt).show();
		    	 }
		    	 if(InspReportNo_visible == true )
		    	 {
		    		 $("#inspReportNo"+cnt).hide();
		    		 $("#txtInspReportNo"+cnt).text($("#inspReportNo"+cnt).val());
		    		 $("#txtInspReportNo"+cnt).show();
		    	 }
				 if(DqDetails_visible == true)
		    	 {
		    		 $("#dqDetails"+cnt).hide();
		    		 $("#txtDqDetails"+cnt).text($("#dqDetails"+cnt).val());
		    		 $("#txtDqDetails"+cnt).show();
		    	 }
				 if(MOC_visible == true)
		    	 {
		    		 $("#moc"+cnt).hide();
		    		 $("#txtMOC"+cnt).text($("#moc"+cnt).val());
		    		 $("#txtMOC"+cnt).show();
		    	 }
				 if(SupplierInvoiceNo_visible == true)
		    	 {
		    		 $("#supplierInvoiceNo"+cnt).hide();
		    		 $("#txtSupplierInvoiceNo"+cnt).text($("#supplierInvoiceNo"+cnt).val());
		    		 $("#txtSupplierInvoiceNo"+cnt).show();
		    	 }
				 if(SupplierInvoiceDate_visible == true)
		    	 {
		    		 $("#supplierInvoiceDate"+cnt).hide();
		    		 $("#txtSupplierInvoiceDate"+cnt).text($("#supplierInvoiceDate"+cnt).val());
		    		 $("#txtSupplierInvoiceDate"+cnt).show();
		    	 }
				 if(ReceivedQty_visible == true)
		    	 {
		    		 $("#receivedQty"+cnt).hide();
		    		 $("#txtSupplierInvoiceDate"+cnt).text($("#receivedQty"+cnt).val());
		    		 $("#txtSupplierInvoiceDate"+cnt).show();
		    	 }
				 if(UOM_visible == true)
		    	 {
		    		 $("#uom"+cnt).hide();
		    		 $("#txtUOM"+cnt).text($("#uom"+cnt).val());
		    		 $("#txtUOM"+cnt).show();
		    	 }				
				 if(ValidationStatus_visible == true)
		    	 {
		    		 $("#validationStatus"+cnt).hide();
		    		 $("#txtValidationStatus"+cnt).text($("#validationStatus"+cnt).val());
		    		 $("#txtValidationStatus"+cnt).show();
		    	 }
				 if(ToolSerialNo_visible == true)
		    	 {
		    		 $("#toolSerialNo"+cnt).hide();
		    		 $("#txtToolSerialNo"+cnt).text($("#toolSerialNo"+cnt).val());
		    		 $("#txtToolSerialNo"+cnt).show();
		    	 }
	    	     
	    	     $("#serialNo"+addnewrow).focus();
	         }
	         else
	         {
	        	$('#rowid-'+cnt+' input[type=text]').show();
	 			$('#rowid-'+cnt+' span').hide();
	             
	         	$("#serialNo"+cnt).show();
	         	$("#txtSerialNo"+cnt).hide();
	         	$("#serialNo"+cnt).focus();
	         }	    	      
	   	 //}
	});  
	 
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
    	 $("#toolNo"+cnt).focus();
     }
 }

function funToolNo(evt,obj)
{ 
	 var cnt = $("#count").val();  
	 $("#txtToolNo"+cnt).text($("#toolNo"+cnt).val());
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;
	 if(charCode == 13)
     {       
	     var cnt = $("#count").val();   			    	 
       	 $("#toolName"+cnt).selectFWDRange('0');
	 }
}

 function funToolName(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtToolName"+cnt).text($("#toolName"+cnt).val());	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {       	     	    	    	 
	    $("#customerPoNo"+cnt).selectFWDRange('0');
     }
 }
 function funCustomerPoNo(evt,obj)
 {
	 var cnt = $("#count").val();  
	 $("#txtCustomerPoNo"+cnt).text($("#customerPoNo"+cnt).val());	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {         	
		  $("#txtCustomerPoNo"+cnt).text(trim($("#customerPoNo"+cnt).val()));
		  $("#customerPoDate"+cnt).selectFWDRange('0');
	 }
 }

 function funCustomerPoDate(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtCustomerPoDate"+cnt).text($("#customerPoDate"+cnt).val()); 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {   
    	 $("#txtCustomerPoDate"+cnt).text(trim($("#customerPoDate"+cnt).val()));
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
	     $("#inspReportNo"+cnt).selectFWDRange('0');
     }
 }
 
 function funInspReportNo(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtInspReportNo"+cnt).text($("#inspReportNo"+cnt).val());
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {
		 $("#txtInspReportNo"+cnt).text(trim($("#inspReportNo"+cnt).val()));
	 	 $("#dqDetails"+cnt).selectFWDRange('0');
     }
 }
 
 function funDqDetails(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtDqDetails"+cnt).text($("#dqDetails"+cnt).val());
	      
     var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
     if(charCode == 13 )
     {  
    	 $("#txtSlump"+cnt).text(trim($("#dqDetails"+cnt).val()));
		 $("#moc"+cnt).selectFWDRange('0');
     }
 }

 function funMOC(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtMOC"+cnt).text($("#moc"+cnt).val());
	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
     if(charCode == 13 )
     {  
    	 $("#txtMOC"+cnt).text(trim($("#moc"+cnt).val()));
		 $("#supplierInvoiceNo"+cnt).selectFWDRange('0');
     }
 }
 
 function funSupplierInvoiceNo(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtSupplierInvoiceNo"+cnt).text($("#supplierInvoiceNo"+cnt).val());
	 
     var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
     if(charCode == 13 )
     {  
    	 $("#txtSupplierInvoiceNo"+cnt).text(trim($("#supplierInvoiceNo"+cnt).val()));
		 $("#supplierInvoiceDate"+cnt).selectFWDRange('0');
     }
 }
 
 function funSupplierInvoiceDate(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtSupplierInvoiceDate"+cnt).text($("#supplierInvoiceDate"+cnt).val());
	 	 
     var charCode = (evt.which) ? evt.which : evt.keyCode ;  	 
     if(charCode == 13 )
     {  
    	 $("#txtSupplierInvoiceDate"+cnt).text(trim($("#supplierInvoiceDate"+cnt).val()));
		 $("#receivedQty"+cnt).selectFWDRange('0');
     }
 }
 
 function funReceivedQty(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtSupplierInvoiceDate"+cnt).text($("#receivedQty"+cnt).val());	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {         
    	 $("#txtSupplierInvoiceDate"+cnt).text(trim($("#receivedQty"+cnt).val()));
		 $("#uom"+cnt).selectFWDRange('0');
     }
 }
   
 function funUOM(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtUOM"+cnt).text($("#uom"+cnt).val());
	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {      
    	 $("#txtUOM"+cnt).text(trim($("#uom"+cnt).val()));
   	     $("#validationStatus"+cnt).selectFWDRange('0');
     }
 }
 
 function funValidationStatus(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtValidationStatus"+cnt).text($("#validationStatus"+cnt).val());
	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {   
    	 $("#txtValidationStatus"+cnt).text(trim($("#validationStatus"+cnt).val()));
		 $("#toolSerialNo"+cnt).selectFWDRange('0');
     }
 }
 
 function funToolSerialNo(evt,obj)
 {
	 var charCode = (evt.which) ? evt.which : evt.keyCode ; 
	 if(charCode == 13 )
     {    
		 if($("#fieldNotesDialog").is(":visible") == false)
		 {
			 var cnt = $("#count").val();		
			 if($("#serialNo"+cnt).val() != "" || $("#toolNo"+cnt).val() != "" || $("#toolName"+cnt).val() != "" || $("#customerPoNo"+cnt).val() != "" || $("#customerPoDate"+cnt).val() != "" || $("#drawingNo"+cnt).val() != "" || $("#inspReportNo"+cnt).val() != "" || $("#dqDetails"+cnt).val() != "" ||  $("#moc"+cnt).val() != "" || $("#supplierInvoiceNo"+cnt).val() != "" ||  $("#supplierInvoiceDate"+cnt).val() != "" || $("#receivedQty"+cnt).val() != "" || $("#uom"+cnt).val() != ""  || $("#validationStatus"+cnt).val() != "" || $("#toolSerialNo"+cnt).val() != "")
			 {   
		    	 var orgDridCount = $("#gridOrgCount").val();  //new
		    	 var addnewrow = parseInt(orgDridCount)+1;     //new
		    	 
		    	 $("#count").val(addnewrow);	    	 
		    	 $("#gridOrgCount").val(addnewrow);
		    	 
		    	  var tdSerialNo = '<input type="text" name="fldSerialNo" id="serialNo'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);"  autocomplete="off"/><span style="display:none;" id="txtSerialNo'+addnewrow+'" ></span></td>';
		          var tdToolNo = '<input type="text" name="fldToolNo" id="toolNo'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funToolNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolNo'+addnewrow+'" ></span></td>';
		          var tdToolName = '<input type="text" name="fldToolName" id="toolName'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolName'+addnewrow+'" ></span></td>';
		          var tdCustomerPoNo = '<input type="text" name="fldCustomerPoNo" id="customerPoNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoNo'+addnewrow+'" ></span></td>';		    			          
		          var tdCustomerPoDate = '<input type="text" name="fldCustomerPoDate" id="customerPoDate'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoDate'+addnewrow+'" ></span></td>';
		          var tdDrawingNo = '<input type="text" name="fldDrawingNo" id="drawingNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo'+addnewrow+'" ></span></td>';				      
		          var tdInspReportNo = '<input type="text" name="fldInspReportNo" id="inspReportNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funInspReportNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtInspReportNo'+addnewrow+'"></span></td>';				     
		          var tdDqDetails = '<input type="text" name="fldDqDetails" id="dqDetails'+addnewrow+'" class="textsmall"  value="" onkeyup="javascript:funDqDetails(event,this);" autocomplete="off"/><span style="display:none;" id="txtDqDetails'+addnewrow+'" ></span></td>';
		          var tdMOC = '<input type="text" name="fldMOC" id="moc'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funMOC(event,this);" autocomplete="off"/><span style="display:none;" id="txtMOC'+addnewrow+'" ></span></td>';
		          var tdSupplierInvoiceNo = '<input type="text" name="fldSupplierInvoiceNo" id="supplierInvoiceNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceNo'+addnewrow+'" ></span></td>';
		          var tdSupplierInvoiceDate = ' <input type="text" name="fldSupplierInvoiceDate" id="supplierInvoiceDate'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate'+addnewrow+'" ></span></td>';				      
		          var tdReceivedQty = '<input type="text" name="fldReceivedQty" id="receivedQty'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funReceivedQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate'+addnewrow+'" ></span></td>';
		          var tdUOM = '<input type="text" name="fldUOM" id="uom'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUOM'+addnewrow+'" ></span></td>';				      
		          var tdValidationStatus = '<input type="text" name="fldValidationStatus" id="validationStatus'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funValidationStatus(event,this);"  autocomplete="off"/><span style="display:none;" id="txtValidationStatus'+addnewrow+'" ></span></td>';
		          var tdToolSerialNo = '<input type="text" name="fldToolSerialNo" id="toolSerialNo'+addnewrow+'" class="txtleastmin"  value="" onkeyup="javascript:funToolSerialNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolSerialNo'+addnewrow+'" ></span></td>';
			      var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
				
			      $('#cylinderFieldData').append('<tr id="rowid-'+addnewrow+'" ><td align="center">'+tdSerialNo+'</td><td align="center">'+tdToolNo+'</td><td align="center">'+tdToolName+'</td><td align="center">'+tdCustomerPoNo+'</td><td align="center">'+tdCustomerPoDate+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdInspReportNo+'</td><td align="center">'+tdDqDetails+'</td><td align="center">'+tdMOC+'</td><td align="center">'+tdSupplierInvoiceNo+'</td><td align="center">'+tdSupplierInvoiceDate+'</td><td align="center">'+tdReceivedQty+'</td><td align="center">'+tdUOM+'</td><td align="center">'+tdValidationStatus+'</td><td align="center">'+tdToolSerialNo+'</td><td align="center">'+tdDelete+'</td></tr>');
						    	
	    		 var Serial_visible = $("#serialNo"+cnt).is(":visible");
		    	 var ToolNo_visible = $("#toolNo"+cnt).is(":visible");
		    	 var ToolName_visible = $("#toolName"+cnt).is(":visible");
		    	 var CustomerPoNo_visible = $("#customerPoNo"+cnt).is(":visible");
		    	 var CustomerPoDate_visible = $("#customerPoDate"+cnt).is(":visible");
		    	 var DrawingNo_visible = $("#drawingNo"+cnt).is(":visible");
		    	 var InspReportNo_visible = $("#inspReportNo"+cnt).is(":visible");
				 var DqDetails_visible = $("#dqDetails"+cnt).is(":visible");
		    	 var MOC_visible = $("#moc"+cnt).is(":visible");
				 var SupplierInvoiceNo_visible = $("#supplierInvoiceNo"+cnt).is(":visible");
		    	 var SupplierInvoiceDate_visible = $("#supplierInvoiceDate"+cnt).is(":visible");		    	
		    	 var ReceivedQty_visible = $("#receivedQty"+cnt).is(":visible");
		    	 var UOM_visible = $("#uom"+cnt).is(":visible");
		    	 var ValidationStatus_visible = $("#validationStatus"+cnt).is(":visible");
				 var ToolSerialNo_visible = $("#toolSerialNo"+cnt).is(":visible");	
			 		         	          
		    	 if(Serial_visible == true)
		    	 {
		    		 $("#serialNo"+cnt).hide();
		    		 $("#txtSerialNo"+cnt).text($("#serialNo"+cnt).val());
		    		 $("#txtSerialNo"+cnt).show();
		    	 }
		    	 if(ToolNo_visible == true)
		    	 {
		    		 $("#toolNo"+cnt).hide();
		    		 $("#txtToolNo"+cnt).text($("#toolNo"+cnt).val());
		    		 $("#txtToolNo"+cnt).show();
		    	 }
		    	 if(ToolName_visible == true)
		    	 {
		    		 $("#toolName"+cnt).hide();
		    		 $("#txtToolName"+cnt).text($("#toolName"+cnt).val());
		    		 $("#txtToolName"+cnt).show();
		    	 }
		    	 if(CustomerPoNo_visible == true)
		    	 {
		    		 $("#customerPoNo"+cnt).hide();
		    		 $("#txtCustomerPoNo"+cnt).text($("#customerPoNo"+cnt).val());
		    		 $("#txtCustomerPoNo"+cnt).show();
		    	 }
		    	 if(CustomerPoDate_visible == true)
		    	 {
		    		 $("#customerPoDate"+cnt).hide();
		    		 $("#txtCustomerPoDate"+cnt).text($("#customerPoDate"+cnt).val());
		    		 $("#txtCustomerPoDate"+cnt).show();
		    	 }
		    	 if(DrawingNo_visible == true)
		    	 {
		    		 $("#drawingNo"+cnt).hide();
		    		 $("#txtDrawingNo"+cnt).text($("#drawingNo"+cnt).val());
		    		 $("#txtDrawingNo"+cnt).show();
		    	 }
		    	 if(InspReportNo_visible == true )
		    	 {
		    		 $("#inspReportNo"+cnt).hide();
		    		 $("#txtInspReportNo"+cnt).text($("#inspReportNo"+cnt).val());
		    		 $("#txtInspReportNo"+cnt).show();
		    	 }
				 if(DqDetails_visible == true)
		    	 {
		    		 $("#dqDetails"+cnt).hide();
		    		 $("#txtDqDetails"+cnt).text($("#dqDetails"+cnt).val());
		    		 $("#txtDqDetails"+cnt).show();
		    	 }
				 if(MOC_visible == true)
		    	 {
		    		 $("#moc"+cnt).hide();
		    		 $("#txtMOC"+cnt).text($("#moc"+cnt).val());
		    		 $("#txtMOC"+cnt).show();
		    	 }
				 if(SupplierInvoiceNo_visible == true)
		    	 {
		    		 $("#supplierInvoiceNo"+cnt).hide();
		    		 $("#txtSupplierInvoiceNo"+cnt).text($("#supplierInvoiceNo"+cnt).val());
		    		 $("#txtSupplierInvoiceNo"+cnt).show();
		    	 }
				 if(SupplierInvoiceDate_visible == true)
		    	 {
		    		 $("#supplierInvoiceDate"+cnt).hide();
		    		 $("#txtSupplierInvoiceDate"+cnt).text($("#supplierInvoiceDate"+cnt).val());
		    		 $("#txtSupplierInvoiceDate"+cnt).show();
		    	 }
				 if(ReceivedQty_visible == true)
		    	 {
		    		 $("#receivedQty"+cnt).hide();
		    		 $("#txtSupplierInvoiceDate"+cnt).text($("#receivedQty"+cnt).val());
		    		 $("#txtSupplierInvoiceDate"+cnt).show();
		    	 }
				 if(UOM_visible == true)
		    	 {
		    		 $("#uom"+cnt).hide();
		    		 $("#txtUOM"+cnt).text($("#uom"+cnt).val());
		    		 $("#txtUOM"+cnt).show();
		    	 }
				 if(ValidationStatus_visible == true)
		    	 {
		    		 $("#validationStatus"+cnt).hide();
		    		 $("#txtValidationStatus"+cnt).text($("#validationStatus"+cnt).val());
		    		 $("#txtValidationStatus"+cnt).show();
		    	 }
				 if(ToolSerialNo_visible == true)
		    	 {
		    		 $("#toolSerialNo"+cnt).hide();
		    		 $("#txtToolSerialNo"+cnt).text($("#toolSerialNo"+cnt).val());
		    		 $("#txtToolSerialNo"+cnt).show();
		    	 }		 			 
		    	 		    	 
		         $("#serialNo"+addnewrow).focus();    
			 }
			 else
			 {
				 $("#serialNo"+cnt).focus(); 
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

     $('#cylinderFieldData tr:gt(0)').each(function() {
         
	     var findId = $(this).attr('id');
	     cnt = findId.substring(findId.indexOf("-")+1);
	     if($("#serialNo"+cnt).val() == "" && $("#toolNo"+cnt).val() == "" && $("#toolName"+cnt).val() == "" && $("#customerPoNo"+cnt).val() == "" && $("#customerPoDate"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#inspReportNo"+cnt).val() == "" && $("#dqDetails"+cnt).val() == "" &&  $("#moc"+cnt).val() == "" && $("#supplierInvoiceNo"+cnt).val() == "" &&  $("#supplierInvoiceDate"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" && $("#uom"+cnt).val() == "" && $("#toolSerialNo"+cnt).val() == "" && $("#validationStatus"+cnt).val() == "")
		 {
	    	 $("#rowid-"+cnt).remove();                    //del row  	 
		 } 			 
     });
 
	 var totalRowCount = $('#cylinderFieldData tr').length; 
	 if(totalRowCount == 1)
	 {
		 var orgGridCount = $("#gridOrgCount").val();  //new
   	     var addnewrow = parseInt(orgGridCount)+1;     //new 
   	     
   	     $("#count").val(addnewrow);
	   	 $("#gridOrgCount").val(addnewrow);

	   	 var cnt = $("#gridOrgCount").val();  

	   	var tdSerialNo = '<input type="text" name="fldSerialNo" id="serialNo'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);"  autocomplete="off"/><span style="display:none;" id="txtSerialNo'+addnewrow+'" ></span></td>';
        var tdToolNo = '<input type="text" name="fldToolNo" id="toolNo'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funToolNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolNo'+addnewrow+'" ></span></td>';
        var tdToolName = '<input type="text" name="fldToolName" id="toolName'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolName'+addnewrow+'" ></span></td>';
        var tdCustomerPoNo = '<input type="text" name="fldCustomerPoNo" id="customerPoNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoNo'+addnewrow+'" ></span></td>';		    			          
        var tdCustomerPoDate = '<input type="text" name="fldCustomerPoDate" id="customerPoDate'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoDate'+addnewrow+'" ></span></td>';
        var tdDrawingNo = '<input type="text" name="fldDrawingNo" id="drawingNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo'+addnewrow+'" ></span></td>';				      
        var tdInspReportNo = '<input type="text" name="fldInspReportNo" id="inspReportNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funInspReportNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtInspReportNo'+addnewrow+'"></span></td>';				     
        var tdDqDetails = '<input type="text" name="fldDqDetails" id="dqDetails'+addnewrow+'" class="textsmall"  value="" onkeyup="javascript:funDqDetails(event,this);" autocomplete="off"/><span style="display:none;" id="txtDqDetails'+addnewrow+'" ></span></td>';
        var tdMOC = '<input type="text" name="fldMOC" id="moc'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funMOC(event,this);" autocomplete="off"/><span style="display:none;" id="txtMOC'+addnewrow+'" ></span></td>';
        var tdSupplierInvoiceNo = '<input type="text" name="fldSupplierInvoiceNo" id="supplierInvoiceNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceNo'+addnewrow+'" ></span></td>';
        var tdSupplierInvoiceDate = ' <input type="text" name="fldSupplierInvoiceDate" id="supplierInvoiceDate'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate'+addnewrow+'" ></span></td>';				      
        var tdReceivedQty = '<input type="text" name="fldReceivedQty" id="receivedQty'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funReceivedQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate'+addnewrow+'" ></span></td>';
        var tdUOM = '<input type="text" name="fldUOM" id="uom'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUOM'+addnewrow+'" ></span></td>';				      
        var tdValidationStatus = '<input type="text" name="fldValidationStatus" id="validationStatus'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funValidationStatus(event,this);"  autocomplete="off"/><span style="display:none;" id="txtValidationStatus'+addnewrow+'" ></span></td>';
        var tdToolSerialNo = '<input type="text" name="fldToolSerialNo" id="toolSerialNo'+addnewrow+'" class="txtleastmin"  value="" onkeyup="javascript:funToolSerialNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolSerialNo'+addnewrow+'" ></span></td>';
	    var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
	 		         	          
	    $('#cylinderFieldData').append('<tr id="rowid-'+addnewrow+'" ><td align="center">'+tdSerialNo+'</td><td align="center">'+tdToolNo+'</td><td align="center">'+tdToolName+'</td><td align="center">'+tdCustomerPoNo+'</td><td align="center">'+tdCustomerPoDate+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdInspReportNo+'</td><td align="center">'+tdDqDetails+'</td><td align="center">'+tdMOC+'</td><td align="center">'+tdSupplierInvoiceNo+'</td><td align="center">'+tdSupplierInvoiceDate+'</td><td align="center">'+tdReceivedQty+'</td><td align="center">'+tdUOM+'</td><td align="center">'+tdValidationStatus+'</td><td align="center">'+tdToolSerialNo+'</td><td align="center">'+tdDelete+'</td></tr>');
	     
	    $("#serialNo"+addnewrow).focus();  
	 }
	 var firstTrId = $('#cylinderFieldData').find('tr:eq(1)').attr('id');
	 var IdNo = firstTrId.substring(firstTrId.indexOf("-")+1);	        
	 if(IdNo > 0)
	    $("#newMidRow"+IdNo).css('visibility', 'hidden');
	 
 }

 function hidedeleteDialog()
 {
	 var cnt = $("#count").val();
	 var totalRowCount = $('#cylinderFieldData tr').length;
	 
	 if((totalRowCount > 2) && ($("#serialNo"+cnt).val() == "" && $("#toolNo"+cnt).val() == "" && $("#toolName"+cnt).val() == "" && $("#customerPoNo"+cnt).val() == "" && $("#customerPoDate"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#inspReportNo"+cnt).val() == "" && $("#dqDetails"+cnt).val() == "" &&  $("#moc"+cnt).val() == "" && $("#supplierInvoiceNo"+cnt).val() == "" &&  $("#supplierInvoiceDate"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" && $("#uom"+cnt).val() == "" && $("#validationStatus"+cnt).val() == "" && $("#toolSerialNo"+cnt).val() == ""))
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
	 $('#cylinderFieldData input[type=text]').each(function (){
			 
			 if($('#cylinderFieldData input[type=text]').is(":visible") == true)
			 {
				 $('#cylinderFieldData input[type=text]').hide();
				 $('#cylinderFieldData span').show();
			 }
	 });	 
	 ///////////////////////////////////// 

     $('#cylinderFieldData tr:gt(0)').each(function() {

    	 var findId = $(this).attr('id');
	      cnt = findId.substring(findId.indexOf("-")+1);	

	      if($("#serialNo"+cnt).val() == "" && $("#toolNo"+cnt).val() == "" && $("#toolName"+cnt).val() == "" && $("#customerPoNo"+cnt).val() == "" && $("#customerPoDate"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#inspReportNo"+cnt).val() == "" && $("#dqDetails"+cnt).val() == "" &&  $("#moc"+cnt).val() == "" && $("#supplierInvoiceNo"+cnt).val() == "" &&  $("#supplierInvoiceDate"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" && $("#uom"+cnt).val() == "" && $("#toolSerialNo"+cnt).val() == "" && $("#validationStatus"+cnt).val() == "" && $("#waterAdd"+cnt).val() == "")
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
		 
		 var tdSerialNo = '<input type="text" name="fldSerialNo" id="serialNo'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);"  autocomplete="off"/><span style="display:none;" id="txtSerialNo'+addnewrow+'" ></span></td>';
         var tdToolNo = '<input type="text" name="fldToolNo" id="toolNo'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funToolNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolNo'+addnewrow+'" ></span></td>';
         var tdToolName = '<input type="text" name="fldToolName" id="toolName'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funToolName(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolName'+addnewrow+'" ></span></td>';
         var tdCustomerPoNo = '<input type="text" name="fldCustomerPoNo" id="customerPoNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoNo'+addnewrow+'" ></span></td>';		    			          
         var tdCustomerPoDate = '<input type="text" name="fldCustomerPoDate" id="customerPoDate'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funCustomerPoDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtCustomerPoDate'+addnewrow+'" ></span></td>';
         var tdDrawingNo = '<input type="text" name="fldDrawingNo" id="drawingNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funDrawingNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtDrawingNo'+addnewrow+'" ></span></td>';				      
         var tdInspReportNo = '<input type="text" name="fldInspReportNo" id="inspReportNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funInspReportNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtInspReportNo'+addnewrow+'"></span></td>';				     
         var tdDqDetails = '<input type="text" name="fldDqDetails" id="dqDetails'+addnewrow+'" class="textsmall"  value="" onkeyup="javascript:funDqDetails(event,this);" autocomplete="off"/><span style="display:none;" id="txtDqDetails'+addnewrow+'" ></span></td>';
         var tdMOC = '<input type="text" name="fldMOC" id="moc'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funMOC(event,this);" autocomplete="off"/><span style="display:none;" id="txtMOC'+addnewrow+'" ></span></td>';
         var tdSupplierInvoiceNo = '<input type="text" name="fldSupplierInvoiceNo" id="supplierInvoiceNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceNo'+addnewrow+'" ></span></td>';
         var tdSupplierInvoiceDate = ' <input type="text" name="fldSupplierInvoiceDate" id="supplierInvoiceDate'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funSupplierInvoiceDate(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate'+addnewrow+'" ></span></td>';				      
         var tdReceivedQty = '<input type="text" name="fldReceivedQty" id="receivedQty'+addnewrow+'" class="txtleastavg"  value="" onkeyup="javascript:funReceivedQty(event,this);" autocomplete="off"/><span style="display:none;" id="txtSupplierInvoiceDate'+addnewrow+'" ></span></td>';
         var tdUOM = '<input type="text" name="fldUOM" id="uom'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funUOM(event,this);" autocomplete="off"/><span style="display:none;" id="txtUOM'+addnewrow+'" ></span></td>';				      
         var tdValidationStatus = '<input type="text" name="fldValidationStatus" id="validationStatus'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funValidationStatus(event,this);"  autocomplete="off"/><span style="display:none;" id="txtValidationStatus'+addnewrow+'" ></span></td>';
         var tdToolSerialNo = '<input type="text" name="fldToolSerialNo" id="toolSerialNo'+addnewrow+'" class="txtleastmin"  value="" onkeyup="javascript:funToolSerialNo(event,this);" autocomplete="off"/><span style="display:none;" id="txtToolSerialNo'+addnewrow+'" ></span></td>';
	     var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
	 		         	          
	     $('#cylinderFieldData').append('<tr id="rowid-'+addnewrow+'" ><td align="center">'+tdSerialNo+'</td><td align="center">'+tdToolNo+'</td><td align="center">'+tdToolName+'</td><td align="center">'+tdCustomerPoNo+'</td><td align="center">'+tdCustomerPoDate+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdInspReportNo+'</td><td align="center">'+tdDqDetails+'</td><td align="center">'+tdMOC+'</td><td align="center">'+tdSupplierInvoiceNo+'</td><td align="center">'+tdSupplierInvoiceDate+'</td><td align="center">'+tdReceivedQty+'</td><td align="center">'+tdUOM+'</td><td align="center">'+tdValidationStatus+'</td><td align="center">'+tdToolSerialNo+'</td><td align="center">'+tdDelete+'</td></tr>');

	     $("#serialNo"+addnewrow).focus();
     //}
     var firstTrId = $('#cylinderFieldData').find('tr:eq(1)').attr('id');
	 var IdNo = firstTrId.substring(firstTrId.indexOf("-")+1);	        
	 if(IdNo > 0)
	    $("#newMidRow"+IdNo).css('visibility', 'hidden');
 }
 
 
    function funOpenRow(cnt)
	{
		if(!$('#cylinderFieldData tr rowid-'+cnt+' span').is(":visible"))
		 {
			  $("#serialNo"+cnt).show();
	    	  $("#toolNo"+cnt).show();
	    	  $("#toolName"+cnt).show();
	    	  $("#customerPoNo"+cnt).show(); 
	    	  $("#customerPoDate"+cnt).show();
	    	  $("#drawingNo"+cnt).show();
	    	  $("#inspReportNo"+cnt).show();
	    	  $("#dqDetails"+cnt).show();
			  $("#moc"+cnt).show();
	    	  $("#supplierInvoiceNo"+cnt).show();
	    	  $("#supplierInvoiceDate"+cnt).show();
	    	  $("#receivedQty"+cnt).show();
	    	  $("#uom"+cnt).show();
	    	  $("#validationStatus"+cnt).show();
			  $("#toolSerialNo"+cnt).show();    	  
	    	 
			  $("#txtSerialNo"+cnt).hide();
	    	  $("#txtToolNo"+cnt).hide();
	    	  $("#txtToolName"+cnt).hide();
	    	  $("#txtCustomerPoNo"+cnt).hide();
	    	  $("#txtCustomerPoDate"+cnt).hide();
	    	  $("#txtDrawingNo"+cnt).hide();
	    	  $("#txtInspReportNo"+cnt).hide();
	    	  $("#txtDqDetails"+cnt).hide();
			  $("#txtMOC"+cnt).hide();
	    	  $("#txtSupplierInvoiceNo"+cnt).hide();
	    	  $("#txtSupplierInvoiceDate"+cnt).hide();
	    	  $("#txtSupplierInvoiceDate"+cnt).hide();
	    	  $("#txtUOM"+cnt).hide();
	    	  $("#txtValidationStatus"+cnt).hide();
			  $("#txtToolSerialNo"+cnt).hide();  	  
		 }
	}
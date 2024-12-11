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
    $('#periodicData input[type=text]').live("keypress", function(event){
				  
			    var currentId = $(this).closest('TR').attr('id');
		   		var cnt = currentId.substring(currentId.indexOf("-")+1);		

		   		var findFocusId =  $(this).attr('id');	
		   		findFocusId = findFocusId.match(pattern);
		   		switch($.trim(findFocusId))
		   		{		   		   
		   			case "serialNo":
				   		
		   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
		   			break;
	   				case "toolingLotNo":
				   		
		   				return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
		   			break;
	   				case "toolingDueQty":
		
						return (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) ? false : true;
					break;
		   		}
    });
    //////////////////////////////////////////////////////////////////// 
	 
	 $('#periodicData tr:not(:has(th))').live("dblclick", function() {
 		 
			var cnt = $("#count").val();
			var rowCountLength = $('#periodicData tr:not(:has(th))').length;
			if((rowCountLength > 1) && $("#serialNo"+cnt).val() == "" && $("#toolingLotNo"+cnt).val() == ""  &&  $("#toolingSerialNo"+cnt).val() == "" && $("#toolingDueQty"+cnt).val() == "" && $("#toolingHistory"+cnt).val() == "")
			{
		    	 $("#rowid-"+cnt).remove();                    //del row
			}
	         ///////////////////////////Hide the prev input text and show the span////////////// 
			 
			 $('#periodicData input[type=text]').each(function (){
				 
				 if($(this).is(":visible") == true)
				 {
					 $(this).hide();
					 $('#periodicData span').show();
				 }
			 });		
			 //////////////////////////////////////////////////////////////////////// 
			 
			  var currentId = $(this).closest('TR').attr('id');
	          var index = currentId.substring(currentId.indexOf("-")+1);

			  $("#serialNo"+index).show();
	    	  $("#toolingLotNo"+index).show();
	    	  $("#toolingSerialNo"+index).show(); 
	    	  $("#toolingDueQty"+index).show();
	    	  $("#toolingHistory"+index).show();
	    	 
	    	  $("#txtSerialNo"+index).hide();
	    	  $("#txtToolingLotNo"+index).hide();
	    	  $("#txtToolingSerialNo"+index).hide();
	    	  $("#txtToolingDueQty"+index).hide();
	    	  $("#txtToolingHistory"+index).hide();	  
	    	 
	          /*******************Assigning the value from edit to current*****************/
	    	  $("#count").val(index);    //  
	    	  /****************************************************************************/	    			
	 });
	 
	     	 	
	 $('#periodicData input[type=text]').live("keydown", function(event){
		 		 
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
		   		else if(findId == "toolingLotNo")
		   		{				       	
			    	if($("#toolingLotNo"+cnt).prop('selectionStart') == 0)
		   			{
			    		setTimeout(function() {
		    				$("#serialNo"+cnt).focus();	
		       			},400);
		   			}
		   		}
		   		else if(findId == "toolingSerialNo")
		   		{				       	
			       	if($("#toolingSerialNo"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
	   						$("#toolingLotNo"+cnt).focus(); 
	   					},400);	   						   					
		   			}
		   		}			  
		   		else if(findId == "toolingDueQty")
		   		{				       	
			       	if($("#toolingDueQty"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
		       				$("#toolingSerialNo"+cnt).focus(); 
		       			},400);
		   			}
		   		}
				else if(findId == "toolingHistory")
		   		{				       	
			       	if($("#toolingHistory"+cnt).prop('selectionStart') == 0)
		   			{
			       		setTimeout(function() {
			       			$("#toolingDueQty"+cnt).focus();
			       		},400);
		   			}
		   		}
				else
		   		{
		   			$("#toolingHistory"+cnt).focus();
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
		   		
                if($("#serialNo"+findIdCnt).val() == "" && $("#toolingLotNo"+findIdCnt).val() == ""  &&  $("#toolingSerialNo"+findIdCnt).val() == "" && $("#toolingDueQty"+findIdCnt).val() == "" && $("#toolingHistory"+findIdCnt).val() == "")
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
		   		else if(findFocusId == "toolingLotNo")
		   		{				       
			       	$("#toolingLotNo"+cnt).focus();
		   		}
		   		else if(findFocusId == "toolingSerialNo")
		   		{				     
			       	$("#toolingSerialNo"+cnt).focus();
		   		}
		   		else if(findFocusId == "toolingDueQty")
		   		{	
			  		$("#toolingDueQty"+cnt).focus();
		   		}
				else if(findFocusId == "toolingHistory")
		   		{	
			       	$("#toolingHistory"+cnt).focus();
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

		   	  if($("#serialNo"+findIdCnt).val() == "" && $("#toolingLotNo"+findIdCnt).val() == ""  &&  $("#toolingSerialNo"+findIdCnt).val() == "" && $("#toolingDueQty"+findIdCnt).val() == "" && $("#toolingHistory"+findIdCnt).val() == "")
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
		   		else if(findFocusId == "toolingLotNo")
		   		{				       
			       	$("#toolingLotNo"+cnt).focus();
		   		}
		   		else if(findFocusId == "toolingSerialNo")
		   		{				     
			       	$("#toolingSerialNo"+cnt).focus();
		   		}
		   		else if(findFocusId == "toolingDueQty")
		   		{	
			  		$("#toolingDueQty"+cnt).focus();
		   		}
				else if(findFocusId == "toolingHistory")
		   		{	
			       	$("#toolingHistory"+cnt).focus();
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
	   					$("#toolingLotNo"+cnt).selectFWDRange('0');
	   					}, 300);
		   			}		
		   		}
		   		else if(findId == "toolingLotNo")
		   		{	
		   			if($("#toolingLotNo"+cnt).prop('selectionStart') >= $("#toolingLotNo"+cnt).val().length)
		   			{
		   				setTimeout(function() {
	   						$("#toolingSerialNo"+cnt).selectFWDRange('0');
	   					}, 300);
		   			}
		   		}
		   		else if(findId == "toolingSerialNo")
		   		{	
		   			if($("#toolingSerialNo"+cnt).prop('selectionStart') >= $("#toolingSerialNo"+cnt).val().length)
		   			{		   				
		   				setTimeout(function() {
		   					$("#toolingDueQty"+cnt).selectFWDRange('0');
		   				}, 300);	   						   					   					
		   			}
		   		}
		   		else if(findId == "toolingDueQty")
		   		{	
		   			if($("#toolingDueQty"+cnt).prop('selectionStart') >= $("#toolingDueQty"+cnt).val().length)
		   			{
		   				setTimeout(function() {
		       				$("#toolingHistory"+cnt).selectFWDRange('0');
		       			},300);
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
		   	   $("#toolingHistory"+cnt).focus();
		   }
		   	 
	 });

	 $('img[name=midRow]').live("click", function()
	 {
    	 var lastRowId = $("#periodicData tr:last").attr('id');
    	 var currentImgId = $(this).closest('img').attr('id');
    	 var currentRowId = $(this).closest('TR').attr('id');

    	 var orgCnt = $("#gridOrgCount").val(); 	    
    	 var totalRowCount = $('#periodicData tr').length;

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
	    		 $('#periodicData tr:gt(0)').each(function() {

				     var findId = $(this).attr('id');
	 		         cnt = findId.substring(findId.indexOf("-")+1);	
	    		
			    	 if($("#serialNo"+cnt).val() == "" && $("#toolName"+cnt).val() == "" && $("#productName"+cnt).val() == "" && $("#machingType"+cnt).val() == "" && $("#drawingNo"+cnt).val() == "" && $("#batchQty"+cnt).val() == "" && $("#receivedQty"+cnt).val() == "" && $("#uom"+cnt).val() == ""  && $("#inStock"+cnt).val() == "" && $("#underInspection"+cnt).val() == "")
			 		 {
			 	    	 $("#rowid-"+cnt).remove();                    //del row				           		 	    					 	    	
			 		 }
	    		 });	
	 		     var cnt = $("#gridOrgCount").val(); 
			    		 
		   		 var addnewrow = parseInt(cnt)+1;
		   		 $("#count").val(addnewrow);	 
		   		 $("#gridOrgCount").val(addnewrow);
		
		   		 /*********Disable All text box and Enable text box*********************/  		    
		  				 $('#periodicData input[type=text]').hide();
		  				 $('#periodicData span').show(); 			
		  		 /**********************************************************************/
			    	 	
				  var tdSerialNo = '<input type="text" name="fldSerialNo" id="serialNo'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtSerialNo'+addnewrow+'" ></span>';
		          var tdToolingLotNo = '<input type="text" name="toolingLotNo" id="toolingLotNo'+addnewrow+'" class="textverysmall"  value="" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo'+addnewrow+'" ></span>';
		          var tdToolingName = '<font id="txtToolName'+addnewrow+'" ></font>';
		          var tdToolingProductName = '<font id="txtProductName'+addnewrow+'" ></font>';		          
		          var tdDrawingNo = '<font id="txtDrawingNo'+addnewrow+'" ></font>';
		          var tdToolingSerialNo = '<input type="text" name="toolingSerialNo" id="toolingSerialNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingSerialNo(event,this);" maxlength="20" autocomplete="off"/><span style="display:none;" id="txtToolingSerialNo'+addnewrow+'"></span>';
		          var tdTabProducedQty = '<font id="txtTabProducedQty'+addnewrow+'"></font>';
		          var tdUom = '<font id="txtUom'+addnewrow+'"></font>';
		          var tdToolingStatus = '<select name="toolingStatus" id="toolingStatus'+addnewrow+'"><option>Good</option><option>Damaged</option><option>Hold</option></select>';
		          var tdToolingDueQty = '<input type="text" name="toolingDueQty" id="toolingDueQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingDueQty(event,this);" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtToolingDueQty'+addnewrow+'"></span>';
		          var tdToolingHistory = '<input type="text" name="toolingHistory" id="toolingHistory'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingHistory(event,this);" maxlength="100" autocomplete="off"/><span style="display:none;" id="txtToolingHistory'+addnewrow+'"></span><input type="hidden" name="stockId" id="stockId'+addnewrow+'" value="0"/>';				      
			      var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
			      
	             $(this).closest('#'+currentRowId).before('<tr id="rowid-'+addnewrow+'"><td align="center">'+tdSerialNo+'</td><td align="center">'+tdToolingLotNo+'</td><td align="center">'+tdToolingName+'</td><td align="center">'+tdToolingProductName+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdToolingSerialNo+'</td><td align="center">'+tdTabProducedQty+'</td><td align="center">'+tdUom+'</td><td align="center">'+tdToolingStatus+'</td><td align="center">'+tdToolingDueQty+'</td><td align="center">'+tdToolingHistory+'</td><td align="center">'+tdDelete+'</td></tr>');
						    	
	    		 var Serial_visible = $("#serialNo"+cnt).is(":visible");
		    	 var ToolingLotNo_visible = $("#toolingLotNo"+cnt).is(":visible");
		    	 var ToolingSerialNo_visible = $("#toolingSerialNo"+cnt).is(":visible");
		    	 var ToolingDueQty_visible = $("#toolingDueQty"+cnt).is(":visible");
		    	 var ToolingHistory_visible = $("#toolingHistory"+cnt).is(":visible");
				
		    	 if(Serial_visible == true)
		    	 {
		    		 $("#serialNo"+cnt).hide();
		    		 $("#txtSerialNo"+cnt).text($("#serialNo"+cnt).val());
		    		 $("#txtSerialNo"+cnt).show();
		    	 }
		    	 if(ToolingLotNo_visible == true)
		    	 {
		    		 $("#toolingLotNo"+cnt).hide();
		    		 $("#txtToolingLotNo"+cnt).text($("#toolName"+cnt).val());
		    		 $("#txtToolingLotNo"+cnt).show();
		    	 }
		    	 if(ToolingSerialNo_visible == true)
		    	 {
		    		 $("#toolingSerialNo"+cnt).hide();
		    		 $("#txtToolingSerialNo"+cnt).text($("#productName"+cnt).val());
		    		 $("#txtToolingSerialNo"+cnt).show();
		    	 }
		    	 if(ToolingDueQty_visible == true)
		    	 {
		    		 $("#toolingDueQty"+cnt).hide();
		    		 $("#txtToolingDueQty"+cnt).text($("#machingType"+cnt).val());
		    		 $("#txtToolingDueQty"+cnt).show();
		    	 }
		    	 if(ToolingHistory_visible == true)
		    	 {
		    		 $("#toolingHistory"+cnt).hide();
		    		 $("#txtToolingHistory"+cnt).text($("#drawingNo"+cnt).val());
		    		 $("#txtToolingHistory"+cnt).show();
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
    	 $("#toolingLotNo"+cnt).focus();
     }
 }

 function funToolingLotNo(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtToolingLotNo"+cnt).text($("#toolingLotNo"+cnt).val()); 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {   
    	 $("#txtToolingLotNo"+cnt).text(trim($("#toolingLotNo"+cnt).val()));
	     $("#toolingSerialNo"+cnt).selectFWDRange('0');
     }
 }
 
 function funToolingSerialNo(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtToolingSerialNo"+cnt).text($("#toolingSerialNo"+cnt).val());
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {
		 $("#txtToolingSerialNo"+cnt).text(trim($("#toolingSerialNo"+cnt).val()));
	 	 $("#tabProducedQty"+cnt).selectFWDRange('0');
     }
 }
 
 function funToolingProducedQty(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#tabProducedQty"+cnt).text($("#tabProducedQty"+cnt).val());
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {
		 $("#tabProducedQty"+cnt).text(trim($("#tabProducedQty"+cnt).val()));
	 	 $("#toolingStatus"+cnt).selectFWDRange('0');
     }
 }
 
 
 function funToolingDueQty(evt,obj)
 { 
	 var cnt = $("#count").val();  
	 $("#txtToolingDueQty"+cnt).text($("#toolingDueQty"+cnt).val());
	 
	 var charCode = (evt.which) ? evt.which : evt.keyCode ;   	 
     if(charCode == 13 )
     {   
    	 $("#txtToolingDueQty"+cnt).text(trim($("#toolingDueQty"+cnt).val()));
		 $("#toolingHistory"+cnt).selectFWDRange('0');
     }
 }
 
 function funToolingHistory(evt,obj)
 {
	 var cnt = $("#count").val();  
	 $("#txtToolingHistory"+cnt).text($("#toolingHistory"+cnt).val());
	 var charCode = (evt.which) ? evt.which : evt.keyCode ; 
	 if(charCode == 13 )
     {    
			 var cnt = $("#count").val();		
			 if($("#serialNo"+cnt).val() == "" || $("#toolingLotNo"+cnt).val() == ""  ||  $("#toolingSerialNo"+cnt).val() == "" || $("#toolingDueQty"+cnt).val() == "" || $("#toolingHistory"+cnt).val() == "")
			 {   
		    	 var orgDridCount = $("#gridOrgCount").val();  //new
		    	 var addnewrow = parseInt(orgDridCount)+1;     //new
		    	 
		    	 $("#count").val(addnewrow);	    	 
		    	 $("#gridOrgCount").val(addnewrow);
		    	 
		    	  var tdSerialNo = '<input type="text" name="fldSerialNo" id="serialNo'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtSerialNo'+addnewrow+'" ></span>';
		          var tdToolingLotNo = '<input type="text" name="toolingLotNo" id="toolingLotNo'+addnewrow+'" class="textverysmall"  value="" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo'+addnewrow+'" ></span>';
		          var tdToolingName = '<font id="txtToolName'+addnewrow+'" ></font>';
		          var tdToolingProductName = '<font id="txtProductName'+addnewrow+'" ></font>';		          
		          var tdDrawingNo = '<font id="txtDrawingNo'+addnewrow+'" ></font>';
		          var tdToolingSerialNo = '<input type="text" name="toolingSerialNo" id="toolingSerialNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingSerialNo(event,this);" maxlength="20" autocomplete="off"/><span style="display:none;" id="txtToolingSerialNo'+addnewrow+'"></span>';
		          var tdTabProducedQty = '<font id="txtTabProducedQty'+addnewrow+'"></font>';
		          var tdUom = '<font id="txtUom'+addnewrow+'"></font>';
		          var tdToolingStatus = '<select name="toolingStatus" id="toolingStatus'+addnewrow+'"><option>Good</option><option>Damaged</option><option>Hold</option></select>';
		          var tdToolingDueQty = '<input type="text" name="toolingDueQty" id="toolingDueQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingDueQty(event,this);" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtToolingDueQty'+addnewrow+'"></span>';
		          var tdToolingHistory = '<input type="text" name="toolingHistory" id="toolingHistory'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingHistory(event,this);" maxlength="100" autocomplete="off"/><span style="display:none;" id="txtToolingHistory'+addnewrow+'"></span><input type="hidden" name="stockId" id="stockId'+addnewrow+'" value="0"/>';
		    	  var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
				
			      $('#periodicData').append('<tr id="rowid-'+addnewrow+'"><td align="center">'+tdSerialNo+'</td><td align="center">'+tdToolingLotNo+'</td><td align="center">'+tdToolingName+'</td><td align="center">'+tdToolingProductName+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdToolingSerialNo+'</td><td align="center">'+tdTabProducedQty+'</td><td align="center">'+tdUom+'</td><td align="center">'+tdToolingStatus+'</td><td align="center">'+tdToolingDueQty+'</td><td align="center">'+tdToolingHistory+'</td><td align="center">'+tdDelete+'</td></tr>');
						    	
			     var Serial_visible = $("#serialNo"+cnt).is(":visible");
		    	 var ToolingLotNo_visible = $("#toolingLotNo"+cnt).is(":visible");
		    	 var ToolingSerialNo_visible = $("#toolingSerialNo"+cnt).is(":visible");
		    	 var ToolingDueQty_visible = $("#toolingDueQty"+cnt).is(":visible");
		    	 var ToolingHistory_visible = $("#toolingHistory"+cnt).is(":visible");
				
		    	 if(Serial_visible == true)
		    	 {
		    		 $("#serialNo"+cnt).hide();
		    		 $("#txtSerialNo"+cnt).text($("#serialNo"+cnt).val());
		    		 $("#txtSerialNo"+cnt).show();
		    	 }
		    	 if(ToolingLotNo_visible == true)
		    	 {
		    		 $("#toolingLotNo"+cnt).hide();
		    		 $("#txtToolingLotNo"+cnt).text($("#toolName"+cnt).val());
		    		 $("#txtToolingLotNo"+cnt).show();
		    	 }
		    	 if(ToolingSerialNo_visible == true)
		    	 {
		    		 $("#toolingSerialNo"+cnt).hide();
		    		 $("#txtToolingSerialNo"+cnt).text($("#productName"+cnt).val());
		    		 $("#txtToolingSerialNo"+cnt).show();
		    	 }
		    	 if(ToolingDueQty_visible == true)
		    	 {
		    		 $("#toolingDueQty"+cnt).hide();
		    		 $("#txtToolingDueQty"+cnt).text($("#machingType"+cnt).val());
		    		 $("#txtToolingDueQty"+cnt).show();
		    	 }
		    	 if(ToolingHistory_visible == true)
		    	 {
		    		 $("#toolingHistory"+cnt).hide();
		    		 $("#txtToolingHistory"+cnt).text($("#drawingNo"+cnt).val());
		    		 $("#txtToolingHistory"+cnt).show();
		    	 }	 			 
		    	 		    	 
		         $("#serialNo"+addnewrow).focus();    
			 }
			 else
			 {
				 $("#serialNo"+cnt).focus(); 
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

     $('#periodicData tr:gt(0)').each(function() {
         
	     var findId = $(this).attr('id');
	     cnt = findId.substring(findId.indexOf("-")+1);
	     if($("#serialNo"+cnt).val() == "" && $("#toolingLotNo"+cnt).val() == ""  &&  $("#toolingSerialNo"+cnt).val() == "" && $("#toolingDueQty"+cnt).val() == "" && $("#toolingHistory"+cnt).val() == "")
		 {
	    	 $("#rowid-"+cnt).remove();                    //del row  	 
		 } 			 
     });
 
	 var totalRowCount = $('#periodicData tr').length; 
	 if(totalRowCount == 1)
	 {
		 var orgGridCount = $("#gridOrgCount").val();  //new
   	     var addnewrow = parseInt(orgGridCount)+1;     //new 
   	     
   	     $("#count").val(addnewrow);
	   	 $("#gridOrgCount").val(addnewrow);

	   	 var cnt = $("#gridOrgCount").val();  

	   	var tdSerialNo = '<input type="text" name="fldSerialNo" id="serialNo'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtSerialNo'+addnewrow+'" ></span>';
        var tdToolingLotNo = '<input type="text" name="toolingLotNo" id="toolingLotNo'+addnewrow+'" class="textverysmall"  value="" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo'+addnewrow+'" ></span>';
        var tdToolingName = '<font id="txtToolName'+addnewrow+'" ></font>';
        var tdToolingProductName = '<font id="txtProductName'+addnewrow+'" ></font>';		          
        var tdDrawingNo = '<font id="txtDrawingNo'+addnewrow+'" ></font>';
        var tdToolingSerialNo = '<input type="text" name="toolingSerialNo" id="toolingSerialNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingSerialNo(event,this);" maxlength="20" autocomplete="off"/><span style="display:none;" id="txtToolingSerialNo'+addnewrow+'"></span>';
        var tdTabProducedQty = '<font id="txtTabProducedQty'+addnewrow+'"></font>';
        var tdUom = '<font id="txtUom'+addnewrow+'"></font>';
        var tdToolingStatus = '<select name="toolingStatus" id="toolingStatus'+addnewrow+'"><option>Good</option><option>Damaged</option><option>Hold</option></select>';
        var tdToolingDueQty = '<input type="text" name="toolingDueQty" id="toolingDueQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingDueQty(event,this);" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtToolingDueQty'+addnewrow+'"></span>';
        var tdToolingHistory = '<input type="text" name="toolingHistory" id="toolingHistory'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingHistory(event,this);" maxlength="100" autocomplete="off"/><span style="display:none;" id="txtToolingHistory'+addnewrow+'"></span><input type="hidden" name="stockId" id="stockId'+addnewrow+'" value="0"/>';
  	    var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
		
	    $('#periodicData').append('<tr id="rowid-'+addnewrow+'"><td align="center">'+tdSerialNo+'</td><td align="center">'+tdToolingLotNo+'</td><td align="center">'+tdToolingName+'</td><td align="center">'+tdToolingProductName+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdToolingSerialNo+'</td><td align="center">'+tdTabProducedQty+'</td><td align="center">'+tdUom+'</td><td align="center">'+tdToolingStatus+'</td><td align="center">'+tdToolingDueQty+'</td><td align="center">'+tdToolingHistory+'</td><td align="center">'+tdDelete+'</td></tr>');
	     
	    $("#serialNo"+addnewrow).focus();  
	 }
	 var firstTrId = $('#periodicData').find('tr:eq(1)').attr('id');
	 var IdNo = firstTrId.substring(firstTrId.indexOf("-")+1);	        
	 if(IdNo > 0)
	    $("#newMidRow"+IdNo).css('visibility', 'hidden');
	 
 }

 function hidedeleteDialog()
 {
	 var cnt = $("#count").val();
	 var totalRowCount = $('#periodicData tr').length;
	 
	 if((totalRowCount > 2) && ($("#serialNo"+cnt).val() == "" && $("#toolingLotNo"+cnt).val() == ""  &&  $("#toolingSerialNo"+cnt).val() == "" && $("#toolingDueQty"+cnt).val() == "" && $("#toolingHistory"+cnt).val() == ""))
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
	 $('#periodicData input[type=text]').each(function (){
			 
			 if($('#periodicData input[type=text]').is(":visible") == true)
			 {
				 $('#periodicData input[type=text]').hide();
				 $('#periodicData span').show();
			 }
	 });	 
	 ///////////////////////////////////// 

     $('#periodicData tr:gt(0)').each(function() {

    	 var findId = $(this).attr('id');
	      cnt = findId.substring(findId.indexOf("-")+1);	

	      if($("#serialNo"+cnt).val() == "" && $("#toolingLotNo"+cnt).val() == ""  &&  $("#toolingSerialNo"+cnt).val() == "" && $("#toolingDueQty"+cnt).val() == "" && $("#toolingHistory"+cnt).val() == "")
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
		 
		 var tdSerialNo = '<input type="text" name="fldSerialNo" id="serialNo'+addnewrow+'" class="txtleast"  value="" onkeyup="javascript:funSerialNo(event,this);" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtSerialNo'+addnewrow+'" ></span>';
         var tdToolingLotNo = '<input type="text" name="toolingLotNo" id="toolingLotNo'+addnewrow+'" class="textverysmall"  value="" onkeydown="javascript:funToolingLotNo(event,this);"  onkeyup="javascript:funFillStockDetails(event,this);" maxlength="10" autocomplete="off"/><span style="display:none;" id="txtToolingLotNo'+addnewrow+'" ></span>';
         var tdToolingName = '<font id="txtToolName'+addnewrow+'" ></font>';
         var tdToolingProductName = '<font id="txtProductName'+addnewrow+'" ></font>';		          
         var tdDrawingNo = '<font id="txtDrawingNo'+addnewrow+'" ></font>';
         var tdToolingSerialNo = '<input type="text" name="toolingSerialNo" id="toolingSerialNo'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingSerialNo(event,this);" maxlength="20" autocomplete="off"/><span style="display:none;" id="txtToolingSerialNo'+addnewrow+'"></span>';
         var tdTabProducedQty = '<font id="txtTabProducedQty'+addnewrow+'"></font>';
         var tdUom = '<font id="txtUom'+addnewrow+'"></font>';
         var tdToolingStatus = '<select name="toolingStatus" id="toolingStatus'+addnewrow+'"><option>Good</option><option>Damaged</option><option>Hold</option></select>';
         var tdToolingDueQty = '<input type="text" name="toolingDueQty" id="toolingDueQty'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingDueQty(event,this);" maxlength="5" autocomplete="off"/><span style="display:none;" id="txtToolingDueQty'+addnewrow+'"></span>';
         var tdToolingHistory = '<input type="text" name="toolingHistory" id="toolingHistory'+addnewrow+'" class="textverysmall"  value="" onkeyup="javascript:funToolingHistory(event,this);" maxlength="100" autocomplete="off"/><span style="display:none;" id="txtToolingHistory'+addnewrow+'"></span><input type="hidden" name="stockId" id="stockId'+addnewrow+'" value="0"/>';
   	     var tdDelete = '<a title="Click to Add Row in Between" href="#"><img border="0" name="midRow" id="newMidRow'+addnewrow+'" src="./images/up-arrow.png"/></a>&nbsp;<a title="Click to Delete"  href="javascript:fundelete('+addnewrow+');"><img border="0" src="./images/deleteicon.gif"/></a>';
		
	     $('#periodicData').append('<tr id="rowid-'+addnewrow+'"><td align="center">'+tdSerialNo+'</td><td align="center">'+tdToolingLotNo+'</td><td align="center">'+tdToolingName+'</td><td align="center">'+tdToolingProductName+'</td><td align="center">'+tdDrawingNo+'</td><td align="center">'+tdToolingSerialNo+'</td><td align="center">'+tdTabProducedQty+'</td><td align="center">'+tdUom+'</td><td align="center">'+tdToolingStatus+'</td><td align="center">'+tdToolingDueQty+'</td><td align="center">'+tdToolingHistory+'</td><td align="center">'+tdDelete+'</td></tr>');

	     $("#serialNo"+addnewrow).focus();
     //}
     var firstTrId = $('#periodicData').find('tr:eq(1)').attr('id');
	 var IdNo = firstTrId.substring(firstTrId.indexOf("-")+1);	        
	 if(IdNo > 0)
	    $("#newMidRow"+IdNo).css('visibility', 'hidden');
 }
 
 
    function funOpenRow(cnt)
	{
		if(!$('#periodicData tr rowid-'+cnt+' span').is(":visible"))
		 {
			  $("#serialNo"+cnt).show();
	    	  $("#toolingLotNo"+cnt).show();
	    	  $("#toolingSerialNo"+cnt).show(); 
	    	  $("#toolingDueQty"+cnt).show();
	    	  $("#toolingHistory"+cnt).show();
	    	 
			  $("#txtSerialNo"+cnt).hide();
	    	  $("#txtToolingLotNo"+cnt).hide();
	    	  $("#txtToolingSerialNo"+cnt).hide();
	    	  $("#txtToolingDueQty"+cnt).hide();
	    	  $("#txtToolingHistory"+cnt).hide();
		 }
	}

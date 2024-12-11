<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Confirm message  jQuery library-->
<link type="text/css" href="./css/jquery.toastmessage-min.css" rel="stylesheet"/>
<script type="text/javascript" src="./js/jquery.toastmessage-min.js"></script>
<script type="text/javascript">
function showStickySuccessToast(message) {
	$().toastmessage('showToast', {
        text     : message,
        sticky   : false,
        position : 'middle-center',
        opacity	 : '1.0',
        type     : 'success',
        closeText: '',
        stayTime : 5000,
        close    : function () {
        }
    });
}
function showAlert(message,element) {
	//alert(message);
	$().toastmessage('showToast', {
        text     : message,
        sticky   : false,
        position : 'middle-center',
        opacity	 : '1.0',
        type     : 'notice',
        closeText: '',
        stayTime : 3000,
        close    : function () 
        {
        	
        	if(element != "")
        		{
        	 		$(element).focus();		
		 	 		//$(element).val("");
        		}
        }
   });
}
</script>
</head>
<body>
</body>
</html>
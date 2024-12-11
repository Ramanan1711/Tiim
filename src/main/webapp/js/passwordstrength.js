$(document).ready(function() {

	$('#crpnewuserpassword').keyup(function(){
		$('#result').html(checkStrength($('#crpnewuserpassword').val()));
	});	
	
	$('#password').keyup(function(){
		$('#result').html(checkStrength($('#password').val()));
	});
	
	
	function checkStrength(crpnewuserpassword){
    
	//initial strength
    var strength = 0;
	
    //if the password length is less than 6, return message.
    if (crpnewuserpassword.length < 6) { 
		$('#result').removeClass();
		$('#result').addClass('short');
		return 'Short';
	}
    
    //length is ok, lets continue.
	
	//if length is 8 characters or more, increase strength value
	if (crpnewuserpassword.length > 7) strength += 1;
	
	//if password contains both lower and uppercase characters, increase strength value
	if (crpnewuserpassword.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))  strength += 1;
	
	//if it has numbers and characters, increase strength value
	if (crpnewuserpassword.match(/([a-zA-Z])/) && crpnewuserpassword.match(/([0-9])/))  strength += 1; 
	
	//if it has one special character, increase strength value
    if (crpnewuserpassword.match(/([!,%,&,@,#,$,^,*,?,_,~])/))  strength += 1;
	
	//if it has two special characters, increase strength value
    if (crpnewuserpassword.match(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,%,&,@,#,$,^,*,?,_,~])/)) strength += 1;
	
	//now we have calculated strength value, we can return messages
	
	//if value is less than 2
	if (strength < 2 ) {
		$('#result').removeClass();
		$('#result').addClass('weak');
		return 'Weak';			
	} else if (strength == 2 ) {
		$('#result').removeClass();
		$('#result').addClass('good');
		return 'Good';		
	} else if (strength > 3 ){
		$('#result').removeClass();
		$('#result').addClass('strong');
		return 'Strong';
	}
}
});
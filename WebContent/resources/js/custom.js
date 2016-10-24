function disableCustomerID(){
	if ( $('#requestType').val().toLowerCase() == 'registration' ) {
        $('#extUserID').prop("readonly", true);
        $('#extUserID').val('');
    } else {
    	$('#extUserID').prop("readonly", false);
    }
}
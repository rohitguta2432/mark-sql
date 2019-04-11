function check() {
	var email_x = document.getElementById("email").value;
	filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (filter.test(email.value)) {
		document.getElementById("error").innerHTML = "";
		$('#submit-btn').prop('disabled', false);
		return true;
	} else {
		document.getElementById("error").innerHTML = "Enter correct Email Format";
		$('#submit-btn').prop('disabled', true);
		return false;
	}
}

function CheckIndianNumber(IndianNumber) {
	var IndNum = /^\d{10}$/;

	if (IndNum.test(IndianNumber)) {

		document.getElementById("error1").innerHTML = "";
		$('#submit-btn').prop('disabled', false);

	} else {

		document.getElementById("error1").innerHTML = "Enter 10 digit number";
		$('#submit-btn').prop('disabled', true);

	}

}

function CheckIndianZipCode(MyZipCode) {
	var CheckZipCode = /(^\d{6}$)/;

	if (CheckZipCode.test(MyZipCode)) {
		document.getElementById("error2").innerHTML = "";
		$('#submit-btn').prop('disabled', false);

	} else {
		// document.getElementById("error2").classList.add("form-control");
		// $("#error2").addClass("form-control");

		document.getElementById("error2").innerHTML = "Enter 6 digit pincode";
		$('#submit-btn').prop('disabled', true);

	}

}

function is_url(str) {
	regexp = /^(?:(?:https?|ftp):\/\/)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:\/\S*)?$/;
	if (regexp.test(str)) {
		document.getElementById("error3").innerHTML = "";
		$('#submit-btn').prop('disabled', false);
	} else {
		document.getElementById("error3").innerHTML = "Enter correct website format";
		$('#submit-btn').prop('disabled', true);
	}
}

function LandLine(str) {
	expres = /^\d{10}$/;

	if (expres.test(str)) {
		document.getElementById("error4").innerHTML = "";
		$('#submit-btn').prop('disabled', false);
	} else {
		document.getElementById("error4").innerHTML = "Enter 10 digit number";
		$('#submit-btn').prop('disabled', true);
	}
}



$(document).ready(function() {

	$('#success-alert').addClass('showSuccessMsg');
	setTimeout(function() {
		$('#success-alert').removeClass('showSuccessMsg');
	}, 3000);

});

$(document).on('click', '.remove', function() {
	var companyId = this.id;
	$('#hiddenId').val(companyId);
	$("#removeCompany").modal('show');

});

$(document).on('click', '#deleteCompany', function() {
	window.location.href = '/hitech-app/company/delete/' + $('#hiddenId').val();
});

 
	
$("#crn").focusout(function() {
    	
    	var crnNo=$('#crn').val();
     	   $.ajax({ url :"validateBycrnNo/"+crnNo,
                type : "GET",
                success : function(data) {
             	   console.log(data);

                 	 if(data == true){
                 		 $("#CRNnoExitsOrNot").show();
                 		  $('#submit-btn').prop('disabled', true);
                 	 }else if(data == false){

                 		$("#CRNnoExitsOrNot").hide();   
                 		  $('#submit-btn').prop('disabled', false);

                 	 }

             },
                error : function(xhr, status, error) {
             	   $("#loginIdExitsOrNot").hide();
             	   
             	}
            });
    	});



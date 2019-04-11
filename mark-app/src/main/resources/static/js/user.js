$(document).ready(function() {

	if((currentUserRole == 'ROLE_SUPER_ADMIN' || currentUserRole == 'ROLE_ADMIN' )){		
		if(existingUser.authorities == 'ROLE_DRIVER'){
			var crn = existingUser.crn;
			$.ajax({
				type : "GET",
				url : "../get-all-fleet-manager-by-crn/"+crn,
				success : function(response) {
					
					populateFleetManagerComponent(response,existingUser);
				},
				error : function(xhr, status, error) {
					console.log('Error: ' + JSON.stringify(status)+" Error: "+error);
				}
			});
		}
	}
	
	var uploadField = document.getElementById("userProfileFileUpload");

	uploadField.onchange = function() {
		console.log(this.files[0].size);
		if(this.files[0].size > (1000 * 200)){
		       alert("File size should be up to 200kb.");
		       this.value = "";
		    };
	};
	
	$('#success-alert').addClass('showSuccessMsg');
	setTimeout(function() {
		$('#success-alert').removeClass('showSuccessMsg');
	}, 3000);
	
	var start = moment().subtract(18, 'year');
	$('#dob').datetimepicker({
		viewMode : 'years',
		format : 'DD/MM/YYYY',
		maxDate:start,
		  icons: {
		        up: "fa fa-chevron-circle-up",
		        down: "fa fa-chevron-circle-down",
		        next: 'fa fa-chevron-circle-right',
		        previous: 'fa fa-chevron-circle-left'
		    }
	});
	
	$("#email").focusout(function(){
		var email = $("#email").val();
	    filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	    if (filter.test(email)) {
	    	document.getElementById("error").innerHTML = "";
	    	$('#submit-btn').prop('disabled', false);
	        return true;
	    } else {
	        document.getElementById("error").innerHTML = "Enter correct email format";
	        $('#submit-btn').prop('disabled', true);
	        return false;
	    }
	});
	
	$("#username1").focusout(function() {
		var userName = $("#username1").val();

		$.ajax({
			url : "get-user/" + userName,
			method : 'GET',
			dataType : 'json',
			success : function(data,status) {

			if (status === "success") {
				if (data.code === 200){
					$("#UserNameExitsOrNot").show();  
	       		  $('#submit-btn').prop('disabled', true);
				}
				if (data.code === 404){
					$("#UserNameExitsOrNot").hide();  
	       		  $('#submit-btn').prop('disabled', false);
				}
			} else {
				$(
					"#username1")
					.removeClass(
						"alert alert-success")
					.addClass(
						"alert alert-danger");
			}
			console.log("Data: " + data.code + "\nStatus: " + status);
			}
		});
	});
	
	$(document).on('change', '#authorities', function() {
		
		var userRole = $("#authorities").val();
		if(userRole == 'ROLE_DRIVER'){
			var crn = $("#crn").val();
			$.ajax({
				type : "GET",
				url : "../user/get-all-fleet-manager-by-crn/"+crn,
				success : function(response) {
					populateFleetManagerComponent(response);
				},

				error : function(xhr, status, error) {
					console.log('Error: ' + JSON.stringify(status)+" Error: "+error);
				}

			});
		}else{
			$( "#fleetManagerDiv" ).html("");
			$( "#fleetManagerDiv" ).removeClass("col-sm-4 mb-3");
		}
	});
});


function check() {
	
    var email = $("#email").val();
    alert(email);
    filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (filter.test(email.value)) {
    	alert("hello-true");
    	document.getElementById("error").innerHTML = "";
    	$('#submit-btn').prop('disabled', false);
        return true;
    } else {
        document.getElementById("error").innerHTML = "Enter correct email format";
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
		
		document.getElementById("error2").innerHTML = "Enter 6 digit pincode";
		$('#submit-btn').prop('disabled', true);

	}
}

function populateFleetManagerComponent(fleetManagerMap){
	var fleetDataHtml = '<div class="form-group"><div class="float-left w-100"><label class="control-label" for="date">FleetManager</label>'+
						'<select  class="form-control" id="fleetManagers" name="parentId"></select>'+
						'</div></div>';	
	$( "#fleetManagerDiv" ).html("");
	$( "#fleetManagerDiv" ).addClass( "col-sm-4 mb-3" );
	$("#fleetManagerDiv").append(fleetDataHtml);
	$.each(fleetManagerMap, function(i, item) {
		$('#fleetManagers').append($('<option>', {
			value : item.id,
			text : item.fullName
		}));

	});
}

function populateFleetManagerComponent(fleetManagerMap, existingUser){
	
	var fleetDataHtml = '<div class="form-group"><div class="float-left w-100"><label class="control-label" for="date">FleetManager</label>'+
						'<select  class="form-control" id="fleetManagers" name="parentId"><option value="0">Select Fleet</option></select>'+
						'</div></div>';	
	$( "#fleetManagerDiv" ).html("");
	$( "#fleetManagerDiv" ).addClass( "col-sm-4 mb-3" );
	$("#fleetManagerDiv").append(fleetDataHtml);
	$.each(fleetManagerMap, function(i, item) {
		
		$('#fleetManagers').append($('<option>', {
			value : item.id,
			text : item.fullName
		}));		
	});
	$('#fleetManagers [value='+existingUser.parentId+']').attr('selected', 'true');
}

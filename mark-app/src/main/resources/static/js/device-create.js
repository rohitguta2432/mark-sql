/**
 * 
 */
$("#companyName").val($("#crn :selected").attr('companyName'));

 $(document)
.ready(
		function() {
			
			
			var crn = $("#crn :selected").attr('value');
			$("#hiddenCrn").val(crn);
			

$("#crn").on("change", function(){
	
	var companyName=$("#crn :selected").val();
	$("#companyName").val(companyName);
	var crn = $("#crn :selected").attr('value');
	$("#hiddenCrn").val(crn);
	$.ajax({
		url : "/hitech-app/user/get-all-fleet-manager-by-crn/" + crn,
		type : "GET",
		async: false,
		success : function(data) {
			$('#fleetmanager').find('option').remove();
			$.each(data, function(key, value) {
				$('#fleetmanager').append(
						$("<option></option>").attr("value", value.id)
								.text(value.fullName));
				flag= true;
				
			});

		},
		error : function(xhr, status, error) {
			console.log("error while fetching fleet manager list by crn")
			flag= false;
		}
		
	});
	
});

$("#deviceId").on('focusout',function() {

	var deviceId = $('#deviceId').val();
	$.ajax({

		url : "validateByDeviceId/" + deviceId,

		type : "GET",
		success : function(data) {
			console.log(data);

			if (data == true) {
				$("#DeviceIdExitsOrNot").show();
				$('#submit-btn').prop('disabled', true);
			} else if (data == false) {
				$("#DeviceIdExitsOrNot").hide();
				$('#submit-btn').prop('disabled', false);
			}

		},
		error : function(xhr, status, error) {
			$("#loginIdExitsOrNot").hide();

		}
	});
});

	
}); 
$(document).ready(function() {
	
	$(document).on('click', '#myProfile', function () {
		
		var pathname = window.location.host;
		
		$.ajax({
			url :"//"+pathname+"/hitech-app/user/getProfileById",
			method : 'GET',
			dataType : 'json',
			success : function(data) {
			
			$('#profileFirstName').val(data.firstName);
			$('#profileLastName').val(data.lastName);
			$('#profileDob').val(data.dob);
			$('#profileYearExperience').val(data.yearExperience);
			$('#profileAvailability').val(data.availability);
			$('#profilePhone').val(data.phone)
			$('#profileEmail').val(data.email)
			$('#profileCity').val(data.address.city)
			$('#profileCountry').val(data.address.country)
			$('#profileFullAddress').val(data.address.fullAddress)
			$('#profilePincode').val(data.address.pincode)
			$('#profileEmployeeId').val(data.employeeId);
			$('#profileState').val(data.address.state);	
				}
			});
		
		var start = moment().subtract(18, 'year');
		$('#profileDob').datetimepicker({
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
		
		});
	});
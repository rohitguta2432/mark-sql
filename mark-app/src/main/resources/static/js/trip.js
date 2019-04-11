$(document).ready(function(){
	
	$('#success-alert').addClass('showSuccessMsg');
	setTimeout(function() {
		$('#success-alert').removeClass('showSuccessMsg');
	}, 3000);
	

	$(document).on('change', '#companies', function() {
		
		var crn = $("#companies").val();
		
		$.ajax({
			type : "GET",
			url : "../user/get-all-fleet-manager-by-crn/"+crn,
			success : function(response) {
				
				populateFleetManagerComponent(response);
				var d = [];
				populateVehicleAndDriver(d);
			},

			error : function(xhr, status, error) {
				console.log('Error: ' + JSON.stringify(status)+" Error: "+error);
			}

		});
	});
	
	/*for role admin on change fleetmanagerId  vechicle and drive are also changed based on fleetmanagerid*/

	$(document).on('change', '#fleetmanager', function() {
		var fleetManagerId = this.value;
		 
		 $.ajax({
				url : "../trip/get-vehicles-drivers-by-fleetManagerId-for-trip/" + fleetManagerId,
				method : 'GET',
				dataType : 'json',
				success : function(data) {
				
					populateVehicleAndDriver(data);
				} 
			});
	});
	
	function populateVehicleAndDriver(data){
		
		$("#vehicleId option").remove();
		$("#drivers option").remove();
		 
		$.each(data.vehicles, function(i, item) {
					$('#vehicleId').append($('<option>', {
						value : item.id,
						text : item.registrationNo
					}));
				});
			 
			 $.each(data.drivers, function(i, item) {
					$('#drivers').append($('<option>', {
						value : item.id,
						text : item.fullName
					}));
				});
	}
	
	function populateFleetManagerComponent(fleetManagerMap){
		
		$("#fleetmanager option").remove();
		$('#fleetmanager').append("<option value='0'>Select FleetManager</option>");
		$.each(fleetManagerMap, function(i, item) {
			$('#fleetmanager').append($('<option>', {
				value : item.id,
				text : item.fullName
			}));

		});
	}
	
});


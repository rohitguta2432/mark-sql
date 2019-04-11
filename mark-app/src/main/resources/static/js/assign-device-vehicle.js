var fleetManagerId = 111;
var urlDataTable =  "";

$(document).ready(function() {
	
	 myDataTable = $('#data-table').dataTable({
		"ajax" : "get-device-assign-to-vehicle",
	    "dom": 'C<"clear">lfrtip',
	    "bAutoWidth": false,
	      "columns": [
	            { "data": "registrationNo" },
	            { "data": "device.deviceId" },
	            {"mRender": function ( data, type, row ) {
	                return '<a class="removeAssign"  id="'+row.id+'"><i class="fa fa-trash"></i></a>';}
	            }
	        ]
	});
	 
	 $(document).on('click', '.removeAssign', function() {
			var vehicleId = this.id;
			$('#hiddenId').val(vehicleId);
			$("#assignRemove").modal('show');

		});

		$(document).on('click','#attached-detached-device',function() {
							window.location.href = '/hitech-app/operation/remove-device-from-vehicle/'
									+ $('#hiddenId').val();
						});

		
		
		$(document).on('click', '#apply', function() {

			var myObject = new Object();
			myObject.vehicleRegistrationNo = $('#vehicleRegistrationNo').val();
			myObject.deviceId = $('#deviceId').val();

			$.ajax({

				type : "POST",
				url : "save-device-assign-to-vehicle",
				data : myObject,
				success : function(response) {
					
					populateDeviceAndVehicleComponent(response);
					
					if(fleetManagerId != 111){
						myDataTable.api().ajax.url("get-device-assign-to-vehicle?fleetManagerId="+fleetManagerId);
					}
					myDataTable.api().ajax.reload();
				},

				error : function(xhr, status, error) {
					console.log('Error: ' + JSON.stringify(status)+" Error: "+error);
				}

			});

		});
		
		$(document).on('change', '#companies', function() {
			
			var crn = $("#companies").val();
			
			$.ajax({
				type : "GET",
				url : "../user/get-all-fleet-manager-by-crn/"+crn,
				success : function(response) {
					
					var d = [];
					populateDeviceAndVehicleComponent(d);
					populateFleetManagerComponent(response);
					myDataTable.api().clear().draw();
				},

				error : function(xhr, status, error) {
					var d = [];
					populateDeviceAndVehicleComponent(d);
					 myDataTable.api().clear().draw();
					console.log('Error: ' + JSON.stringify(status)+" Error: "+error);
					myDataTable.api().clear().draw();
				}

			});
		});
		
		$(document).on('change', '#fleetManagers', function() {
			
			fleetManagerId = $("#fleetManagers").val();
			
			$.ajax({
				type : "GET",
				url : "fetch-device-vehicle-by-fleetManager/"+fleetManagerId,
				success : function(response) {
					
					populateDeviceAndVehicleComponent(response);
					myDataTable.api().ajax.url("get-device-assign-to-vehicle?fleetManagerId="+fleetManagerId);
					myDataTable.api().ajax.reload();
				},

				error : function(xhr, status, error) {
					var d = [];
					populateDeviceAndVehicleComponent(d);
					console.log('Error: ' + JSON.stringify(status)+" Error: "+error);
					myDataTable.api().clear().draw();
				}

			});
		});
	 
});

function populateDeviceAndVehicleComponent(vehicleDeviceMap){
	$("#vehicleRegistrationNo option").remove();
	$("#deviceId option").remove();
	 $('#vehicleRegistrationNo').append("<option value='0'>Select Vehicle</option>");
	 $('#deviceId').append("<option value='0'>Select Device</option>");
	
		 $.each(vehicleDeviceMap.vehicles, function(i, item) {
				$('#vehicleRegistrationNo').append($('<option>', {
					value : item.registrationNo,
					text : item.registrationNo
				}));
			});
		 
		 $.each(vehicleDeviceMap.devices, function(i, item) {
				$('#deviceId').append($('<option>', {
					value : item.id,
					text : item.deviceId
				}));
			});
	
}

function populateFleetManagerComponent(fleetManagerMap){
	$("#fleetManagers option").remove();
	$('#fleetManagers').append("<option value='0'>Select FleetManager</option>");
	$.each(fleetManagerMap, function(i, item) {
		$('#fleetManagers').append($('<option>', {
			value : item.id,
			text : item.fullName
		}));

	});
}
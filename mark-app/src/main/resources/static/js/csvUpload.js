$(document).ready(function() {

	$('#success-alert').addClass('showSuccessMsg');
	setTimeout(function() {
		$('#success-alert').removeClass('showSuccessMsg');
	}, 3000);


	var uploadField = document.getElementById("csvFile");

	uploadField.onchange = function() {
		//console.log(this.files[0].size);
		if (this.files[0].size > (1000000 * 50)) {
			alert("File size should be up to 50MB.");
			this.value = "";
		}
		;
	};

	$('#videoFiles').on('change', function() {
		var totalSize = 0;
		for (var i = 0; i < this.files.length; i++) {
			totalSize = totalSize + this.files[i].size;
		}
		if (totalSize > (1000000 * 50)) {
			alert("Total Video size should be up to 50MB.");
			this.value = "";
		}
		;
	});


	$(document).on('change', '#fleetManagers', function() {

		fleetManagerId = $("#fleetManagers").val();

		$.ajax({
			type : "GET",
			url : "../device/get-devices-by-fleetmanager?fleetManagerId=" + fleetManagerId,
			success : function(response) {
				populateDeviceComponent(response);
			},

			error : function(xhr, status, error) {
				console.log('Error: ' + JSON.stringify(status) + " Error: " + error);
			}
		});
	});

	$(document).on('change', '#companies', function() {

		var crn = $("#companies").val();

		$.ajax({
			type : "GET",
			url : "../user/get-all-fleet-manager-by-crn/" + crn,
			success : function(response) {

				populateFleetManagerComponent(response);
				var d = [];
				populateDeviceComponent(d);
			},

			error : function(xhr, status, error) {
				console.log('Error: ' + JSON.stringify(status) + " Error: " + error);
				var d = [];
				populateDeviceComponent(d);
			}
		});
	});

	$(document).on('change', '#bulkDataCompanies', function() {

		var crn = $("#bulkDataCompanies").val();

		$.ajax({
			type : "GET",
			url : "../user/get-all-fleet-manager-by-crn/" + crn,
			success : function(response) {

				populateBulkDataFleetManagerComponent(response);
			},

			error : function(xhr, status, error) {
				console.log('Error: ' + JSON.stringify(status) + " Error: " + error);
			}
		});
	});

});

function populateDeviceComponent(deviceMap) {
	$("#deviceId option").remove();
	$('#deviceId').append("<option value='0'>Select Device</option>");
	if (deviceMap.length > 0) {
		$.each(deviceMap, function(i, item) {
			$('#deviceId').append($('<option>', {
				value : item.id,
				text : item.deviceId
			}));
		});
	}
}

function populateFleetManagerComponent(fleetManagerMap) {
	$("#fleetManagers option").remove();
	$('#fleetManagers').append("<option value='0'>Select FleetManager</option>");
	$.each(fleetManagerMap, function(i, item) {
		$('#fleetManagers').append($('<option>', {
			value : item.id,
			text : item.fullName
		}));

	});
}

function populateBulkDataFleetManagerComponent(fleetManagerMap) {
	$("#bulkDataFleetManagers option").remove();
	$('#bulkDataFleetManagers').append("<option value='0'>Select FleetManager</option>");
	$.each(fleetManagerMap, function(i, item) {
		$('#bulkDataFleetManagers').append($('<option>', {
			value : item.id,
			text : item.fullName
		}));

	});
}
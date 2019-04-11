var table;

var baseUrl = "..";

$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
	// Modify options, control originalOptions, store jqXHR, etc
	// alert("ajaxPrefilter "+JSON.stringify(options));
});

$(document)
		.ready(
				function() {

					fleetManagerId=$("#hiddenFltMgrId").val();

					table = $("#data-table")
							.dataTable(
									{
										"order" : [],
										"stateSave" : true,
										"columnDefs" : [ {
											"targets" : 4,
											"orderable" : true,
										} ],
										columns : [
												{
													'data' : 'deviceId'
												},
												{
													'data' : 'crn'
												},
												{
													'data' : 'description'
												},

												{
													"mRender" : function(data,
															type, row) {
														// alert(JSON.stringify(row))
														if (row.status == 0)
															return '<span>&nbsp;<i class="fa fa-remove"></i></span>';
														else
															return '<span>&nbsp;<i class="fa fa-check"></i></span>';
													}
												},

												{
													"mRender" : function(data,
															type, row) {
														if (row.hasVehicle)
															return '<span>&nbsp;<i class="fa fa-check"></i></span>';
														else
															return '<span>&nbsp;<i class="fa fa-remove"></i></span>';

													}
												},

												/*
												 * { 'data' : 'fleetmanagerName' },
												 */

												{
													"mRender" : function(data,
															type, row) {

														if (row.id != null)
															return "<a href='device/update/"
																	+ row.id
																	+ "'><i class='fa fa-edit'></i></a>"
																	+ " <a class='remove' id="
																	+ row.id
																	+ "><i class='fa fa-trash'></i></a>";

													}

												} ],

										"ajax" : {
											"url" : "device/getAllDeviceByFleetManagerId/"
													+ fleetManagerId,
											"type" : "GET",
										}

									});

					$("#fleetmanager").on('change',
							refreshDeviceListWithManager);

					$('#crnSelect').on('change', onCrnChange);

					$('#crn').on('change', getAllFleetManagerByCrn);

				});

$(document).ready(function() {

	$('#success-alert').addClass('showSuccessMsg');
	setTimeout(function() {
		$('#success-alert').removeClass('showSuccessMsg');
	}, 3000);

});

$(document).on('click', '.remove', function() {
	var deviceId = this.id;
	$('#hiddenId').val(deviceId);
	$("#removeDevice").modal('show');

});

$(document).on('click', '#deleteDevice', function() {
	window.location.href = '/hitech-app/device/delete/' + $('#hiddenId').val();
});

function nospaces(t) {
	if (t.value.match(/\s/g)) {
		t.value = t.value.replace(/\s/g, '');
	}
}

var refreshDeviceListWithManager = function() {
	var fleetManagerId = $("#fleetmanager :selected").attr('fleetmgrid');

	// call ajax and get device list by fleet mgr id
	$.ajax({

		url : "device/getAllDeviceByFleetManagerId/" + fleetManagerId,
		type : "GET",
		success : function(data) {
			// refresh data table
			table.api().ajax.url("device/getAllDeviceByFleetManagerId/"
					+ fleetManagerId);
			table.api().ajax.reload();
		},
		error : function(xhr, status, error) {
			alert('error');
		}

	});
}

var onCrnChange = function() {
	var crn = $("#crnSelect :selected").attr('crnId');
	if (crn == "empty") {
		reset()
		return;
	}

	var flag = getAllFleetManagerByCrn(crn);

	if (flag) {
		// after populating fleet list refresh the data table
		var fleetManagerId = $("#fleetmanager :selected").attr('fleetmgrid');
		table.api().ajax.url("device/getAllDeviceByFleetManagerId/"
				+ fleetManagerId);
		table.api().ajax.reload();
	}
}

var getAllFleetManagerByCrn = function(crn) {
	var flag = false;

	$.ajax({
		url : "/hitech-app/user/get-all-fleet-manager-by-crn/" + crn,
		type : "GET",
		async : false,
		success : function(data) {
			$('#fleetmanager').find('option').remove();
			$('#fleetmanager').append(
					$("<option></option>").attr("fleetMgrId", "empty")
							.text("Select Fleet Manager"));
			$.each(data, function(key, value) {
				$('#fleetmanager').append(
						$("<option></option>").attr("fleetMgrId", value.id)
								.text(value.fullName));
				flag = true;

			});

		},
		error : function(xhr, status, error) {
			console.log("error while fetching fleet manager list by crn")
			flag = false;
		}

	});
	return flag;
}

var reset=function(){
	$('#fleetmanager').find('option').remove();
	$('#fleetmanager').append(
			$("<option></option>").attr("fleetMgrId", "empty")
					.text("Select Fleet Manager"));
	
	// reset data table
	
	var fleetManagerId = 0;
	table.api().ajax.url("device/getAllDeviceByFleetManagerId/"
			+ fleetManagerId);
	table.api().ajax.reload();
	
}

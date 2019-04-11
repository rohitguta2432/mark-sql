var myDataTable;
var fleetManagerId = "";
	$(document).ready(function() {
		
		var authType= JSON.parse($('#authType').val());
		
		var dataTableColumn = [
		   		            { "data": "name" },
				            { "data": "registrationNo" },
				            { "data": "brand" },
				            {"mRender": function ( data, type, row ) {
				                return row.hasDevice == true ? '<span>&nbsp;<i class="fa fa-check"></i></span>' : '<span>&nbsp;<i class="fa fa-remove"></i></span>'}
				            },
				            {"mRender": function ( data, type, row ) {
				                return '<a href="/hitech-app/vehicle/update/'+row.id+'" id="'+row.id+'"><i class="fa fa-edit"></i></a> <a class="removeVehicle"  id="'+row.id+'"><i class="fa fa-trash"></i></a>';}
				            }
				        ];
		if(authType[0].authority == 'ROLE_SUPER_ADMIN'){
			dataTableColumn = [
			   		            { "data": "name" },
					            { "data": "registrationNo" },
					            { "data": "crn" },
					            { "data": "brand" },
					            {"mRender": function ( data, type, row ) {
					                return row.hasDevice == true ? '<span>&nbsp;<i class="fa fa-check"></i></span>' : '<span>&nbsp;<i class="fa fa-remove"></i></span>'}
					            },
					            {"mRender": function ( data, type, row ) {
					                return '<a href="/hitech-app/vehicle/update/'+row.id+'" id="'+row.id+'"><i class="fa fa-edit"></i></a> <a class="removeVehicle"  id="'+row.id+'"><i class="fa fa-trash"></i></a>';}
					            }
					        ];
		}
		
		fleetManagerId = $("#fleetmanager").val();
		var url;
		if(fleetManagerId == 0 || fleetManagerId == void(0)){
			url = "vehicle/get-all-vehicle-by-fleet-manager-id";
		}else{
			url = "vehicle/get-all-vehicle-by-fleet-manager-id?fleetManagerId="+fleetManagerId;
		}
		
		 myDataTable = $('#data-table').dataTable({
			"ajax" : url,
		    "dom": 'C<"clear">lfrtip',
		    "bAutoWidth": false,
		      "columns": dataTableColumn
		});
		 
	$('#fleetmanager').on('change', function() {
		fleetManagerId = $("#fleetmanager").val();
		myDataTable.api().ajax.url("vehicle/get-all-vehicle-by-fleet-manager-id?fleetManagerId="+fleetManagerId);
		myDataTable.api().ajax.reload();
	});
 

	});
	
	 $(document).on('click', '.removeVehicle', function() {
			var vehicleId = this.id;
			$('#hiddenId').val(vehicleId);
			$("#removeVehicle").modal('show');

		});
	
	 $(document).on('click','#deleteVehicle',function() {
			   $.ajax({
		           url :"vehicle/delete/"+$('#hiddenId').val(),
		           type : "GET",
		           success : function(data) {
		        	   $("#removeVehicle").modal('hide');
		        	   
		        	   if(fleetManagerId != ''){
		        		   myDataTable.api().ajax.url("vehicle/get-all-vehicle-by-fleet-manager-id?fleetManagerId="+fleetManagerId);
		        	   }
		        	   myDataTable.api().ajax.reload();
		        },
		           error : function(xhr, status, error) {
		          	   console.log("error while deleting vehicle");
		          	   myDataTable.api().clear().draw();
		        	}
		       });
			
		});

$('#crn').on('change', function() {
		var crn = $("#crn").val();
	   $.ajax({
           url :"user/get-all-fleet-manager-by-crn/"+crn,
           type : "GET",
           success : function(data) {
        	   $('#fleetmanager').children('option:not(:first)').remove();
        	   $.each(data, function(key, value) {
        		     $('#fleetmanager')
        		         .append($("<option></option>")
        		         .attr("value",value.id)
        		         .text(value.fullName));
        		});
        	   myDataTable.api().clear().draw();	
        },
           error : function(xhr, status, error) {
          	   console.log("error while fetching fleet manager list");
          	   myDataTable.api().clear().draw();
        	}
       });
});
	
$(document).ready(function() {

	$('#success-alert').addClass('showSuccessMsg');
	setTimeout(function() {
		$('#success-alert').removeClass('showSuccessMsg');
	}, 3000);
});

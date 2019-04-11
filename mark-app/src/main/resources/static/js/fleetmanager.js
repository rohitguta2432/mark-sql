$(document).ready(function() {

	Table = $("#data-table-trip").DataTable({
	    data:[],
	    columns: [
			    	 { "data": "fullName"},
			    	 { "data": "email" },
		             { "data": "phone" },
		             { "data": "employeeId"},
		             { "data": "vehicleRegistrationNo", "defaultContent": "<i>Not Assigned</i>"},
		             {"mRender": function ( data, type, row ) {
		                return '<a class="removeAssign"  id="'+row.id+'"><i class="fa fa-trash"></i></a>';}
		            }
	    ],
	    "deferRender" : true,
		"columnDefs" : [ {
			"targets" : 4	,
			"orderable" : true
		} ],
	    rowCallback: function (row, data) {},
	    filter: false,
	    info: false,
	    ordering: true,
	    processing: true,
	    retrieve: true
	});

	
	/*"order" : [],
	"stateSave" : true,
	"columnDefs" : [ {
		"targets" : 4,
		"orderable" : true,
	} ]*/
	
	
	
	//for driver  assigned truck details

 	$('#pills-assign-tab').click(function(){

 		var parentId=$('#userId').val().replace(/\"/g, "");
 		
 		$.ajax({url: "../driver-list/fleet/"+parentId,
		  	
			  success: function(result){
				  console.log(result);
				  Table.clear().draw();
			      Table.rows.add(result).draw();
			  },
 		error: function (request, status, error) {
 	        console.log(request.responseText);
 	    }
		  });

 	});	
});

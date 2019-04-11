
/*$(document).ready(function() {

	Table = $("#data-table").DataTable({
		data : [],
		columns : [
			{"data" : "vehicle.registrationNo"},
			{"data" : "fileName"},
			{"data" : "severity"},
			{"data" : "driverState"},
			{"data" : "fileUrl",
				"render" : function(data, type, row) {
					return '<div class="video_thumbnail123">'
						+ '<div id="light"><a class="boxclose" id="boxclose" onclick="lightbox_close();"></a>'
						+ '<video id="VisaChipCardVideo" width="500" height="160" controls>'
						+ '<source src="' + data + '" type="video/mp4"></video></div>'
						+ '<div id="fade" onClick="lightbox_close();"></div><div>'
						+ '<a href="#" onclick="lightbox_open();">Watch video</a></div>';
				}
			}

		],
		"deferRender" : true,
		"columnDefs" : [ {
			"targets" : 2,
			"orderable" : true
		} ],
		rowCallback : function(row, data) {},
		filter : false,
		info : false,
		ordering : true,
		processing : true,
		retrieve : true
	});

	Table.rows.add(alertVariable).draw();
	$('#FleetManager').change(function() {

		var fleetMgrId = $("#FleetManager").val();
		$.ajax({
			url : "../alarm/videos-by-fleetManger/" + fleetMgrId,

			success : function(result) {
				console.log(result);
				Table.clear().draw();
				Table.rows.add(result).draw();
			}
		});
	});
});*/
/*---------------------------------------------*/
var myDataTable;
var fleetManagerId = "";
	$(document).ready(function() {
		
		var auth= JSON.parse($('#roleId').val());
		//console.log(auth[0].authority);
		
		var dataTableColumn = [
	    	{"data" : "vehicle.registrationNo"},
			{"data" : "fileName"},
			{"data" : "severity"},
			{"data" : "driverState"},
			{"data" : "fileUrl",
				"render" : function(data, type, row) {
					return '<div class="video_thumbnail123">'
						+ '<div id="light"><a class="boxclose" id="boxclose" onclick="lightbox_close();"></a>'
						+ '<video id="VisaChipCardVideo" width="500" height="160" controls>'
						+ '<source src="' + data + '" type="video/mp4"></video></div>'
						+ '<div id="fade" onClick="lightbox_close();"></div><div>'
						+ '<a href="#" onclick="lightbox_open();">Watch video</a></div>';
				}
			}

		];
		
		if(auth[0].authority == 'ROLE_SUPER_ADMIN'){
		dataTableColumn = [
	    	{"data" :"companyName"},
			{"data" : "vehicle.registrationNo"},
			{"data" : "fileName"},
			{"data" : "severity"},
			{"data" : "driverState"},
			{"data" : "fileUrl",
				"render" : function(data, type, row) {
					return '<div class="video_thumbnail123">'
						+ '<div id="light"><a class="boxclose" id="boxclose" onclick="lightbox_close();"></a>'
						+ '<video id="VisaChipCardVideo" width="500" height="160" controls>'
						+ '<source src="' + data + '" type="video/mp4"></video></div>'
						+ '<div id="fade" onClick="lightbox_close();"></div><div>'
						+ '<a href="#" onclick="lightbox_open();">Watch video</a></div>';
				}
			}

		];}
		
		
		fleetManagerId = $("#fleetmanager").val();
		var url;
		if(fleetManagerId == 0 || fleetManagerId == void(0)){
			url = "alarm/video";
		}else{
			url = "alarm/videos-by-fleetManger/"+fleetManagerId;
		}
		
		 myDataTable = $('#data-table').dataTable({
			"ajax" : url,
		    "dom": 'C<"clear">lfrtip',
		    "bAutoWidth": false,
		    columns : dataTableColumn,
			"deferRender" : true,
			rowCallback : function(row, data) {},
			filter : false,
			info : false,
			ordering : false,
			processing : true,
			retrieve : true
		});
		 
		 
	$('#fleetmanager').on('change', function() {
		fleetManagerId = $("#fleetmanager").val();
		myDataTable.api().ajax.url("alarm/videos-by-fleetManger/"+fleetManagerId);
		myDataTable.api().ajax.reload();
		
	});


$('#crn').on('change', function() {
	var crn = $("#crn").val();
   $.ajax({
       url : "user/get-all-fleet-manager-by-crn/"+crn,
       type : "GET",
       success : function(data) {
    	   $('#fleetmanager').children('option:not(:first)').remove();
    	   $.each(data, function(key, value) {
    		     $('#fleetmanager')
    		         .append($("<option></option>")
    		         .attr("value",value.id)
    		         .text(value.fullName));
    		});
    	   
    },
       error : function(xhr, status, error) {
      	   console.log("error while fetching fleet manager list");
      	  
    	}
   });
  
	});
});
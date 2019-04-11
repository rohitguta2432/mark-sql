/*var baseUrl = "http://localhost:8080/hitech-app";*/
var baseUrl = "..";
$(document).ready(function() {

	Table = $("#data-table-trip").DataTable({
	    data:[],
	    columns: [
	                { "data": "vehicle.registrationNo"  },
	                {"mRender": function ( data, type, row ) {
		                return '<span>'+(row.sources).toUpperCase()+'</span> <b>To</b> <span>'+(row.destination).toUpperCase()+'</span>';}
		            },
	                { "data": "statrtDate" },
	                { "data": "endDate" },

	    ],
	    "deferRender" : true,
		"columnDefs" : [ {
			"targets" : 3,
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
	
	$(".go").click(function(){
		
	  if(flag){
		  flag = false;
		  var userId=$('#userId').val().replace(/\"/g, "");
		  tripId = this.id;
		  /*$.ajax({url: "../drivers-gps-profile/"+userId,*/
		  $.ajax({url: "../drivers-gps-profile/trip/"+tripId+"/user/"+userId,
			  	
			  success: function(result){
				  
				  addData(result);
				  //if(flagInit){
					  initMapBox();
					  flagInit = false;
				  //}
				  $('#mapFilter').show();
			  }
		  });
	  }else{
		  flag = true;
		  //$('#mapFilter').hide();
	  }	
	});
	
	//for driver  assigned truck details

 	$('#pills-assign-tab').click(function(){

 		var userId  = $("#userId").val();
 		$.ajax({url: "../trip-list-by-driver-id/"+userId,
		  	
			  success: function(result){
				  Table.clear().draw();
				  
				  Table.rows.add( result ).draw();
				  
			      //Table.rows.add(result).draw();
			  }
		  });
 		

 	});

	
});
var tripId;
var flag = true;
var majorWarning = [];
var minorWarning = [];
var normWarning = [];
var map;
var driver;
var trip;
var routLive = [];
var flagInit = true;
var origin = {};
var destination = {};

function addData(data) {

	majorWarning = data.majorWarnings;
	minorWarning = data.minorWarnings;
	normWarning = data.normalWarnings;
	driver = data.driver;
	trip = data.trip;
	routLive = data.routLive;
	console.log(JSON.stringify(trip));
}

function initMapBox(){
	
	L.mapbox.accessToken = 'pk.eyJ1Ijoicml0ZXNob3JhbmdlbWFudHJhIiwiYSI6ImNqa2kwaGFlZDEwajYzcXBiY2VtMjM1aWcifQ.KuN1AsybfSzWZcyOE7-BwA';
	 
	var mapBox = null
	
	if(majorWarning != null){
		mapBox = L.mapbox.map('map'+tripId, 'mapbox.streets').addControl(L.mapbox.geocoderControl('mapbox.places', {
	        autocomplete: true
	    })).setView([routLive[0].latDecimal, routLive[0].lngDecimal], 8);
		
		addMarkersInMapBox(mapBox);
	}else{
		mapBox = L.mapbox.map('map', 'mapbox.streets')
	    .setView([20.178532,78.65862], 4);
	}
}

function addMarkersInMapBox(mapBox){
	
    var myLayer = L.mapbox.featureLayer().addTo(mapBox);
	
	var geojson = [];
	var counter = 0;

/*====================Add Normal-Warning Marker=======================*/
	
	$.each(normWarning, function(index, value) {
		dr = value;
		var dateTimeString = new Date(value.timeStamp);
		var myTimeString = dateTimeString.toLocaleDateString()+' '+dateTimeString.getHours()+':'+dateTimeString.getMinutes()+':'+dateTimeString.getMilliseconds(); 
		var contentString = 
	      'Clicked location: <br>' + value.latitudeDecimal + ',' + value.longitudeDecimal +
	      '<br>'+
	      'Time: '+myTimeString;
		
		var geoData = 
		{
	        type: 'Feature',
	        geometry: {
	          type: 'Point',
	          coordinates: [value.longitudeDecimal, value.latitudeDecimal]
	        },
	        properties: {
	          title: 'Normal Warning',
	          description: contentString,
	          "normalwarningmarker": true,
	          "majorwarningmarker": false,
	          "minorwarningmarker": false,
	          "livelocation" : false,
	          "originlocation" : false,
	          'marker-color': '#5FA6D0',
	          'marker-size': 'small',
	          'marker-symbol': ''
	        }
	      };
		
		geojson[counter++] = geoData;
	});	
	
/*====================Add Major-Warning Marker=======================*/
	
	$.each(majorWarning, function(index, value) {
		dr = value;
		var dateTimeString = new Date(value.timeStamp);
		var myTimeString = dateTimeString.toLocaleDateString()+' '+dateTimeString.getHours()+':'+dateTimeString.getMinutes()+':'+dateTimeString.getMilliseconds();
		var contentString = 
	      'Clicked location: <br>' + value.latitudeDecimal + ',' + value.longitudeDecimal +
	      '<br>'+
	      'Time: '+myTimeString;
		
		var geoData = 
		{
	        type: 'Feature',
	        geometry: {
	          type: 'Point',
	          coordinates: [value.longitudeDecimal, value.latitudeDecimal]
	        },
	        properties: {
	          title: 'Major Warning',
	          description: contentString,
	          "normalwarningmarker": false,
	          "majorwarningmarker": true,
	          "minorwarningmarker": false,
	          "livelocation" : false,
	          "originlocation" : false,
	          'marker-color': '#FA8072',
	          'marker-size': 'small',
	          'marker-symbol': ''
	        }
	      };
		
		geojson[counter++] = geoData;
	});
	
/*=====================Add Minor-Warning Marker=======================*/
	
	$.each(minorWarning, function(index, value) {
		dr = value;
		var dateTimeString = new Date(value.timeStamp);
		var myTimeString = dateTimeString.toLocaleDateString()+' '+dateTimeString.getHours()+':'+dateTimeString.getMinutes()+':'+dateTimeString.getMilliseconds();
		var contentString = 
	      'Clicked location: <br>' + value.latitudeDecimal + ',' + value.longitudeDecimal +
	      '<br>'+
	      'Time: '+myTimeString;
		
		var geoData = 
		{
	        type: 'Feature',
	        geometry: {
	          type: 'Point',
	          coordinates: [value.longitudeDecimal, value.latitudeDecimal]
	        },
	        properties: {
	          title: 'Minor Warning',
	          description: contentString,
	          "normalwarningmarker": false,
	          "majorwarningmarker": false,
	          "minorwarningmarker": true,
	          "livelocation" : false,
	          "originlocation" : false,
	          'marker-color': '#FFFF66',
	          'marker-size': 'small',
	          'marker-symbol': ''
	        }
	      };
		
		geojson[counter++] = geoData;
	});
	
/*====================Add LIVE-Location Marker=======================*/	
	
	if(trip.status === 1)
		geojson[counter++] = plotLiveLocation();
	
/*======================Live-Route=======================*/
	if(trip.status === 1)
		addMarkerInMapBoxForLiveRoute(mapBox);
	
	initOriginLocation();
/*====================Add Origin Location Marker=======================*/
	
	var dateTimeOriginString = new Date(origin.timeStamp);
	var myOriginTimeString = dateTimeOriginString.toLocaleDateString()+' '+dateTimeOriginString.getHours()+':'+dateTimeOriginString.getMinutes()+':'+dateTimeOriginString.getMilliseconds();
	var originContentString = '<div class="row trigger">'
		+ '<div class="col-sm-4">'
			+ '<img style="height:50px;weight:50px;" data-image="" src="https://s3.ap-south-1.amazonaws.com/novusaware-test/users/profile/'+driver.hashedUserImage+'">'
		+ '</div>'
		+ '<div class="col-sm-8">'
			+'<b>Live Location: </b></br>'+driver.username
		+ '</div>'
	+ '</div>'
	+'<div class="row">'
		+'<div class="col-sm-12">'+origin.lat+','+origin.lng+'</div>'
	+'</div><br/>'
	+'<div class="row">'
		+'<div class="col-sm-12"><b>Mobile No:</b>'+driver.phone+'</div>'
	+'</div><br/>'
	+'<div class="row">'
		+'<div class="col-sm-12"><b>Vehicle No:</b>'+driver.vehicleRegistrationNo+'</div>'
	+'</div><br />'
	+'<div class="row">'
		+'<div class="col-sm-12"><b>Time:</b>'+myOriginTimeString+'</div>'
	+'</div>';
	
	var originGeoData = 
	{
        type: 'Feature',
        geometry: {
          type: 'Point',
          coordinates: [origin.lng, origin.lat]
        },
        properties: {
          
          description: originContentString,
          "normalwarningmarker": false,
          "majorwarningmarker": false,
          "minorwarningmarker": false,
          "livelocation" : false,
          "originlocation" : true,
          'marker-color': '#ff9933',
          'marker-size': 'large',
          'marker-symbol': 'circle'
        }
      };
	
	geojson[counter++] = originGeoData;
	
/*====================Add Destination Location Marker=======================*/	
	// TODO
	
/*=======================Filter-Markers=======================*/
	myLayer.setGeoJSON(geojson);
	$('.menu-ui a').on('click', function() {
	    
	    var filter = $(this).data('filter');
	    $(this).addClass('active').siblings().removeClass('active');
	    var id = $(this).id;
	    
	    myLayer.setFilter(function(f) {
	        
	        return (filter === 'all') ? true : f.properties[filter] === true;
	    });
	    return false;
	});
	
	
}

function initOriginLocation(){
	
	if(routLive.length > 0){
		origin.lat = routLive[0].latDecimal;
		origin.lng = routLive[0].lngDecimal;
		origin.timeStamp = routLive[0].timestamp;
	}
}

function addMarkerInMapBoxForLiveRoute(mapBox){
	
	var start = {};
	var counter = 0;
	var coordinateList = [];
	$.each(routLive, function(index, value) {
		
		if(counter == 0){
			start.lat = value.latDecimal;
			start.lng = value.lngDecimal;
		} 
		coordinateList[counter++] = [value.lngDecimal , value.latDecimal];
	});
	
	var liverouteLayer = L.mapbox.featureLayer().addTo(mapBox);
	var liveRouteGeojson = [];
	var liveRouteGeoData = 
	{
        type: 'Feature',
        geometry: {
          type: 'LineString',
          coordinates: coordinateList
        }
      };
	liveRouteGeojson[0]= liveRouteGeoData;
	
	liverouteLayer.setGeoJSON(liveRouteGeojson);
}

function plotLiveLocation(){
	var dr = driver;
	var dateTimeLiveLocationString = new Date(dr.currentTimeGps);
	var myLiveLocationTimeString = dateTimeLiveLocationString.toLocaleDateString()+' '+dateTimeLiveLocationString.getHours()+':'+dateTimeLiveLocationString.getMinutes()+':'+dateTimeLiveLocationString.getMilliseconds();
	var liveContentString = '<div class="row trigger">'
		+ '<div class="col-sm-4">'
			+ '<img style="height:50px;weight:50px;" data-image="" src="https://s3.ap-south-1.amazonaws.com/novusaware-test/users/profile/'+dr.hashedUserImage+'">'
		+ '</div>'
		+ '<div class="col-sm-8">'
			+'<b>Live Location: </b></br>'+dr.username
		+ '</div>'
	+ '</div>'
	+'<div class="row">'
		+'<div class="col-sm-12">'+dr.latDecimal+','+dr.lngDecimal+'</div>'
	+'</div><br/>'
	+'<div class="row">'
		+'<div class="col-sm-12"><b>Mobile No:</b>'+dr.phone+'</div>'
	+'</div><br/>'
	+'<div class="row">'
		+'<div class="col-sm-12"><b>Vehicle No:</b>'+dr.vehicleRegistrationNo+'</div>'
	+'</div><br />'
	+'<div class="row">'
		+'<div class="col-sm-12"><b>Time:</b>'+myLiveLocationTimeString+'</div>'
	+'</div>';
	
	var liveGeoData = 
	{
        type: 'Feature',
        geometry: {
          type: 'Point',
          coordinates: [dr.lngDecimal, dr.latDecimal]
        },
        properties: {
          
          description: liveContentString,
          "normalwarningmarker": false,
          "majorwarningmarker": false,
          "minorwarningmarker": false,
          "livelocation" : true,
          "originlocation" : false,
          'marker-color': '#3bb2d0',
          'marker-size': 'large',
          'marker-symbol': 'bus'
        }
      };
	
	return liveGeoData;
}


var baseUrl = "/";

$(document).ready(function() {
	
	 $('#toDob').datetimepicker({
			viewMode : 'years',
			format : 'DD/MM/YYYY',
			  icons: {
			        up: "fa fa-chevron-circle-up",
			        down: "fa fa-chevron-circle-down",
			        next: 'fa fa-chevron-circle-right',
			        previous: 'fa fa-chevron-circle-left'
			    }
		});
		
				
		$('#fromDob').datetimepicker({
			viewMode : 'years',
			format : 'DD/MM/YYYY',
			  icons: {
			        up: "fa fa-chevron-circle-up",
			        down: "fa fa-chevron-circle-down",
			        next: 'fa fa-chevron-circle-right',
			        previous: 'fa fa-chevron-circle-left'
			    }
		});

	
/*######################################################################################################*/
											/*for crn change data */
/*######################################################################################################*/	
	
	$('#crn').on('change',function(){
		
		let crnNumber=$('#crn').val();
		let fleetManager=$('#fleetManagerByCrn').val();
		
		/*ajax call on change of crn*/
		$.ajax({
			url : "dashboard/getFleetmanagerByCrn?crn="+crnNumber,
			method : 'GET',
			success : function(data) {
				$("#fleetManagerByCrn option").remove();
				if(data.length > 0){
					showFleetManagerByCrn(data);	
				}else{
					$('#fleetManagerByCrn').append("<option  value=\"" + 0 + "\">" + 'Select Fleet Manager'+ "</option>");
				}
				
			}
		});
		
		function showFleetManagerByCrn(data){
			//console.log(data);
			$('#fleetManagerByCrn').html("");
			$('#fleetManagerByCrn').append("<option  value=\"" + 0 + "\">" + 'all'+ "</option>");
			for (var i = 0;i < data.length;i++) {
			    var fleetManager = data[i];
			   $('#fleetManagerByCrn').append("<option  value=\"" + fleetManager.id + "\">" + fleetManager.fullName+ "</option>");
			}
		}
		
		$.ajax({
			url : "dashboard/getDashBoardDataByFilterinputs?fleetManagerId="+fleetManager+'&crn='+crnNumber ,
			method : 'GET',
			success : function(data) {
				//onsole.log(data);
				numberOfTrucks(data);
				milesCovered(data);
				drowsyDriving(data);
				distractedDriving(data);
				driverScore(data);
				sefetyScoreFilter(data);
				majorMissWarningCount(data);
				minorMissWarningCount(data);
				fleetManagergraphData(data);
			}
		});
		
	});
	
	
	let defaultCrn=$('#crn').val();
	/*ajax call on change of crn*/
	
	$.ajax({
		url : "dashboard/getFleetmanagerByCrn?crn="+defaultCrn,
		method : 'GET',
		success : function(data) {
			$("#fleetManagerByCrn option").remove();
			if(data.length > 0){
			showFleetManager(data);
			}else{
			$('#fleetManagerByCrn').append("<option  value=\"" + 0 + "\">" + 'Select Fleet Manager'+ "</option>");
			}
		}
	});
	
	function showFleetManager(data){
		
			$('#fleetManagerByCrn').html("");
		 $('#fleetManagerByCrn').append("<option  value=\"" + 0 + "\">" + 'all'+ "</option>");
		for (var i = 0;i < data.length;i++) {
		    var fleetManager = data[i];
		   $('#fleetManagerByCrn').append("<option  value=\"" + fleetManager.id + "\">" + fleetManager.fullName+ "</option>");
		}
	}
	
	
	/*for based on search input */
	/*$('#search').click(function(){*/
	
	$('#fleetManagerByCrn').on('change',function(){
		let crn=$('#crn').val();
		let fleetManagerId=$('#fleetManagerByCrn').val();
		//console.log(fleetManagerId);
		
		$.ajax({
			url : "dashboard/getDashBoardDataByFilterinputs?fleetManagerId="+fleetManagerId+'&crn='+crn ,
			method : 'GET',
			success : function(data) {
				//console.log(data);
				numberOfTrucks(data);
				milesCovered(data);
				drowsyDriving(data);
				distractedDriving(data);
				driverScore(data);
				sefetyScoreFilter(data);
				majorMissWarningCount(data);
				minorMissWarningCount(data);
				fleetManagergraphData(data);
			}
		});
		
	});

/*###############################################################################################*/
							/*for default current year dashboard*/
/*###################################################################################################*/	
	
	var crn=$('#crn').val();
	var fleetManagerId=$('#fleetManagerByCrn').val();
	//console.log(crn);
	
	var urlDashboard = "";
	
	if(fleetManagerId != null){
		$.ajax({
			url : "dashboard/getDashBoardDataByFilterinputs?fleetManagerId="+fleetManagerId+'&crn='+crn,
			method : 'GET',
			success : function(data) {
				//console.log(data);
				numberOfTrucks(data);
				milesCovered(data);
				drowsyDriving(data);
				distractedDriving(data);
				driverScore(data);
				sefetyScoreFilter(data);
				majorMissWarningCount(data);
				minorMissWarningCount(data);
				fleetManagergraphData(data);
			}
		});
	}
	
/*################################################################################################*/
						/* for filtering  dashboard data on date range */
/*#################################################################################################*/
	
	$('#filterDateRange').click(function(){
		let crn=$('#crn').val();
		let fleetManagerId=$('#fleetManagerByCrn').val();
		let toDate=$('#toDob').val().trim();
		let fromDate=$('#fromDob').val().trim();
		
		if((toDate  !== null || fromDate  !== null) && (toDate  !== "" && fromDate  !== "")){
			console.log('enddate ' +toDate);
			console.log('startdate ' +fromDate);
			$.ajax({
				url : "dashboard/getDashBoardDataByFilterinputs?fleetManagerId="+fleetManagerId+'&crn='+crn + '&fromDate='+fromDate +'&toDate='+toDate,
				method : 'GET',
				success : function(data) {
					//console.log(data);
					numberOfTrucks(data);
					milesCovered(data);
					drowsyDriving(data);
					distractedDriving(data);
					driverScore(data);
					sefetyScoreFilter(data);
					majorMissWarningCount(data);
					minorMissWarningCount(data);
				}
			});
		}
	});

	/*######################################################################################*/
								/*dashboardData for filtering*/
	/*#########################################################################################*/
	function numberOfTrucks(data){
		$('#noOfTrucks').text(data.miles_covered.totalTrucks);
	}
	function milesCovered(data){
		$('#milesCovered').text(data.miles_covered.totalCoveredMiles.toFixed(2));
	}
	function drowsyDriving(data){
		$('#drowsyDriving').text(data.Drowsy_driving);
	}
	function distractedDriving(data){
		$('#distractedDriving').text(data.Distracted_driving);
	}
	function driverScore(data) {
		//console.log(data);
		 $('#drivers').children('a').empty();
		 var counter=0;
		
		 if(data.Driver_overview != null){
		for ( var i = 0;i<data.Driver_overview.length;i++) {
		    var driver = data.Driver_overview[i];
		    counter++;
		   $('#drivers').append('<tr><td><span class="counter">' + counter +' </span> ' + driver.fullName + ' </td><td>'+ driver.score +'</td></tr>');
		    $('div#drivers >tr').wrap('<a href="user/getById/'+driver.id +'"> </a>');
			}
		}else{
			$('#drivers').html('<span class="no_driver"> No Driver has Assigned !</span>')
		}
	}
	function sefetyScoreFilter(data){
		$('#safetyScoreId').text(data.avgSafetyScore);
	
	
	var safetyScore = [];
	var deviceIds=[];
	
	var saftyScoreMonth=data.avgSafetyScore;
	
	safetyScore.push(saftyScoreMonth);
	safetyScore.push(100 - saftyScoreMonth);
	
					/*for safetyScore graph*/
			var ctx = document.getElementById("chartSafetyScore").getContext('2d');
									
			ctx.canvas.width = 200;
			ctx.canvas.height = 200;
								
			var myChart = new Chart(ctx,{
				type : 'doughnut',
					data : {
							labels : [
								'average safety score'],
								datasets : [ {
								backgroundColor : [
									"#e74c3c"],
									 borderColor: 'white',
							         borderWidth: 5,
							        data : safetyScore.length == 0 ? '' : safetyScore
									}]
								},
								    options: {
								        tooltips: {
								            enabled: false
								        },
								        responsive: true,
								        legend: {
								            position: 'top',
								            onClick: (e) => e.stopPropagation(),
								        },
								        animation: {
								            animateScale: true,
								            animateRotate: true,
								            
								        }
								    }
							});
			}
	function majorMissWarningCount(data){
		
		$('#majorMisscount').val(data.majorMissWarningCount);
	}
	function minorMissWarningCount(data)
	{
		$('#minorMissCounter').val(data.minorMissWarningCount);
	}
	function fleetManagergraphData(data){
		var yearId = new Date().getFullYear();
		var majorMissData = [];
		var minorMissData = [];
		
		var monthLabels = [ 'jan', 'feb', 'mar', 'apr','may', 'jun',
			'jul', 'aug', 'sep', 'oct', 'nov', 'dec'];
		
		for (var i = 0; i < data.graph_data.length; i++) {
				//console.log(data.graph_data[i]);
			majorMissData.push(data.graph_data[i].majorMissWarningCount);
			minorMissData.push(data.graph_data[i].minorMissWarningCount);
					
		}
		
		/* for major miss graph count */
		var ctx = document.getElementById("chartMajorMiss").getContext("2d");
		ctx.canvas.width = 200;
		ctx.canvas.height = 100;

		var myChart = new Chart(ctx,{
		type : 'line',
		data : {
		labels : monthLabels,
		datasets : [ {
		label : '# YEAR -'+ yearId,
		data : majorMissData,
		backgroundColor : [
		'rgba(255, 99, 132, 0.2)', ],
		borderColor : [
		'rgba(255,99,132,1)', ],
		borderWidth : 1
		} ]
		},
		options : {
				legend: {
				display: false
				},
			scales : {
		yAxes : [ {
	ticks : {
			beginAtZero : true
		}
	} ]
}
}
});

/* for minor miss graph count */
var ctx = document.getElementById("chartMinorMiss").getContext("2d");
							ctx.canvas.width = 200;
							ctx.canvas.height = 100;
							var myChart = new Chart(
									ctx,
									{
									type : 'line',
										data : {
											labels : monthLabels,
											datasets : [ {
												label : '# YEAR -'
														+ yearId,
												data : minorMissData,
												backgroundColor : [ 'rgba(137, 198, 226,0.4)' ],
												borderColor : [ 'rgba(31,112,148,1)' ],
												borderWidth : 1
											} ]
										},
										options : {
											legend: {
										        display: false
										    },
											scales : {
												yAxes : [ {
													ticks : {
														beginAtZero : true
													}
												}]
											}
										}
									});
	}
	
	
	/*######################################################################################################*/
							/*for drop-down year graph*/
	/*######################################################################################################*/
		
		$.ajax({
			url : "dashboard/getGraphAllYear",
			method : 'GET',
			success : function(data) {
				yearListGraph(data);
			}
		});
		
		function yearListGraph(data){
			
			$('#majorMissyearId').html("");
			data.sort().reverse();
			
			for (var i = 0;i < data.length;i++) {
			    var years = data[i];
			   $('#majorMissyearId').append("<option value=\"" + years + "\">" + years+ "</option>");
			}
			$('#minorMissyearId').html("");
			for (var i = 0;i < data.length;i++) {
			    var years = data[i];
			   $('#minorMissyearId').append("<option value=\"" + years + "\">" + years+ "</option>");
			}
		}
	/*#########################################################################################################*/
					/*for counter major minor and safety */ 
	/*#########################################################################################################*/
			setTimeout(function(){
					$('.mainContent .card-stats .title > span.counter').each(function() {
					
								$(this).prop('Counter', 0).animate({
									Counter : $(this).text()
								}, {
									duration : 1000,
									easing : 'swing',
									step : function(now) {
										$(this).text(Math.ceil(now));
									}
								});
							});
						},1000);
			
	/*#########################################################################################################*/						
					/*for graph  changes on year select*/
	/*#########################################################################################################*/				
					
					$('#majorMissyearId').on('change',function(){
						
						let crn=$('#crn').val();
						let fleetmanagerId=$('#fleetManagerByCrn').val();
						
						var monthLabels = [ 'jan', 'feb', 'mar', 'apr','may', 'jun',
							'jul', 'aug', 'sep', 'oct', 'nov', 'dec'];
						
						
						var majorMissYear =$(this).val();
						var majorMissDataByYear = [];
						$.ajax({
							url : "dashboard/majorMissgraphData?yearId=" + majorMissYear+'&fleetmanagerId='+fleetmanagerId+'&crn='+crn,
							method : 'GET',
							dataType : 'json',
							success : function(data) {
					
						for (var i = 0; i < data.majorAndMinor.length; i++) {
							majorMissDataByYear.push(data.majorAndMinor[i].majorMissWarningCount);
						}
						
				/* for major miss graph count */
				var ctx = document.getElementById("chartMajorMiss").getContext("2d");
				ctx.canvas.width = 200;
				ctx.canvas.height = 100;

				var myChart = new Chart(ctx,{
				type : 'line',
				data : {
				labels : monthLabels,
				datasets : [ {
				label : '# YEAR -',
				data : majorMissDataByYear,
				backgroundColor : [
				'rgba(255, 99, 132, 0.2)', ],
				borderColor : [
				'rgba(255,99,132,1)', ],
				borderWidth : 1
				} ]
				},
				options : {
						legend: {
						display: false
						},
					scales : {
				yAxes : [ {
			ticks : {
					beginAtZero : true
				}
			} ]
		}
	}
});


								}
						});
						
					});
	/*###################################################################################################################################*/
										/*for minor miss graph data*/
	/*###################################################################################################################################*/			
					$('#minorMissyearId').on('change',function(){
						//console.log('minorMiss')
						var minorMissDataByYear = [];
						var minorMissYear =$(this).val();
						
						var monthLabels = [ 'jan', 'feb', 'mar', 'apr','may', 'jun',
							'jul', 'aug', 'sep', 'oct', 'nov', 'dec'];
						
						
						let crn=$('#crn').val();
						let fleetManagerId=$('#fleetManagerByCrn').val();
						
						$.ajax({
							url : "dashboard/minorMissgraphData?yearId=" + minorMissYear+'&fleetmanagerId='+fleetManagerId+'&crn='+crn,
							method : 'GET',
							dataType : 'json',
							success : function(data) {
					
						for (var i = 0; i < data.majorAndMinor.length; i++) {
							minorMissDataByYear.push(data.majorAndMinor[i].minorMissWarningCount);
									
								}
						/* for minor miss graph count */
						var ctx = document.getElementById("chartMinorMiss").getContext("2d");
														ctx.canvas.width = 200;
														ctx.canvas.height = 100;
														var myChart = new Chart(
																ctx,
																{
																	type : 'line',
																	data : {
																		labels : monthLabels,
																		datasets : [ {
																			label : '# YEAR -',
																			data : minorMissDataByYear,
																			backgroundColor : [ 'rgba(137, 198, 226,0.4)' ],
																			borderColor : [ 'rgba(31,112,148,1)' ],
																			borderWidth : 1
																		} ]
																	},
																	options : {
																		legend: {
																	        display: false
																	    },
																		scales : {
																			yAxes : [ {
																				ticks : {
																					beginAtZero : true
																				}
																			}]
																		}
																	}
															});
						
												}
								});
						
					});
					
	/*#####################################################################################################################################*/				
											/*for current year data for graph*/
	/*#####################################################################################################################################*/	
	/*			var monthLabels = [ 'jan', 'feb', 'mar', 'apr','may', 'jun',
						'jul', 'aug', 'sep', 'oct', 'nov', 'dec'];
				var majorMissData = [];
				var minorMissData = [];
				
				//console.log($('#crn').val())
				//console.log($('#fleetManagerByCrn').val())
				var yearId = new Date().getFullYear();
					$.ajax({
							url : "dashboard/majorMissgraphData?yearId=" + yearId,
							method : 'GET',
							dataType : 'json',
							success : function(data) {
								//console.log(data);
						for (var i = 0; i < data.majorAndMinor.length; i++) {
							
							majorMissData.push(data.majorAndMinor[i].majorMissWarningCount);
							minorMissData.push(data.majorAndMinor[i].minorMissWarningCount);
									
						}
						
				 for major miss graph count 
				var ctx = document.getElementById("chartMajorMiss").getContext("2d");
				ctx.canvas.width = 200;
				ctx.canvas.height = 100;

				var myChart = new Chart(ctx,{
				type : 'line',
				data : {
				labels : monthLabels,
				datasets : [ {
				label : '# YEAR -'+ yearId,
				data : majorMissData,
				backgroundColor : [
				'rgba(255, 99, 132, 0.2)', ],
				borderColor : [
				'rgba(255,99,132,1)', ],
				borderWidth : 1
				} ]
				},
				options : {
						legend: {
						display: false
						},
					scales : {
				yAxes : [ {
			ticks : {
					beginAtZero : true
				}
			} ]
		}
	}
});

		 for minor miss graph count 
	var ctx = document.getElementById("chartMinorMiss").getContext("2d");
									ctx.canvas.width = 200;
									ctx.canvas.height = 100;
									var myChart = new Chart(
											ctx,
											{
											type : 'line',
												data : {
													labels : monthLabels,
													datasets : [ {
														label : '# YEAR -'
																+ yearId,
														data : minorMissData,
														backgroundColor : [ 'rgba(137, 198, 226,0.4)' ],
														borderColor : [ 'rgba(31,112,148,1)' ],
														borderWidth : 1
													} ]
												},
												options : {
													legend: {
												        display: false
												    },
													scales : {
														yAxes : [ {
															ticks : {
																beginAtZero : true
															}
														}]
													}
												}
											});
									}
						});*/
/*############################################################################################################################################*/					
											/*for geo location*/
/*############################################################################################################################################*/					
		$("#go").click(function() {
			
			let crn=$('#crn').val();
			let fleetManagerId=$('#fleetManagerByCrn').val();
			
		if (flag) {
				flag = false;
				$.ajax({
				url : "dashboard/fleet-manager-drivers-live-location?fleetManagerId="+fleetManagerId+'&crn='+crn,
				success : function(result) {
					//console.log(result);
				addData(result);
				//initMap();
				initMapBox();
				
			}
		});
	} else {
	flag = true;
	}
		});
});

var drivers = [];
var flag = true;


function initMapBox(){
	
	
	
	L.mapbox.accessToken = 'pk.eyJ1Ijoicml0ZXNob3JhbmdlbWFudHJhIiwiYSI6ImNqa2kwaGFlZDEwajYzcXBiY2VtMjM1aWcifQ.KuN1AsybfSzWZcyOE7-BwA';
	
	var mapBox = null
	
	if(drivers != null){
		mapBox = L.mapbox.map('map', 'mapbox.streets').addControl(L.mapbox.geocoderControl('mapbox.places', {
	        autocomplete: true
	    }))
	    .setView([drivers[0].latDecimal, drivers[0].lngDecimal], 3);
		
		addMarkerInMapBox(mapBox);
	}else{
		mapBox = L.mapbox.map('map', 'mapbox.streets')
	    .setView([20.178532,78.65862], 4);
	}
}

function addMarkerInMapBox(mapBox){
	
	var cssIcon = L.divIcon({
		  // Specify a class name we can refer to in CSS.
		  className: 'fa fa-map-marker fa-3x location-red',
		  // Set marker width and height
		  iconSize: [60, 60]
		});
	
    var myLayer = L.mapbox.featureLayer().addTo(mapBox);
	
	var geojson = [];
	var counter = 0;
	$.each(drivers, function(index, value) {
		dr = value;
		var contentString = '<div class="row trigger">'
			+ '<div class="col-sm-4">'
				+ '<img style="height:50px;weight:50px;" data-image="" src="https://s3.ap-south-1.amazonaws.com/novusaware-test/users/profile/'+dr.hashedUserImage+'">'
			+ '</div>'
			+ '<div class="col-sm-8">'
				+'<b>Live Location: </b></br>'+dr.username
			+ '</div>'
		+ '</div>'
		+'<div class="row">'
			+'<div class="col-sm-12">'+value.latDecimal+','+value.lngDecimal+'</div>'
		+'</div><br/>'
		+'<div class="row">'
			+'<div class="col-sm-12"><b>Mobile No:</b>'+dr.phone+'</div>'
		+'</div><br/>'
		+'<div class="row">'
			+'<div class="col-sm-12"><b>Vehicle No:</b>'+dr.vehicleRegistrationNo+'</div>'
		+'</div>';
		
		/*L.marker([value.latDecimal, value.lngDecimal], {icon: cssIcon} , {title : 'Live Location'})
		.bindPopup(contentString).addTo(mapBox);*/
		
		var geroData = 
		{
	        type: 'Feature',
	        geometry: {
	          type: 'Point',
	          coordinates: [value.lngDecimal, value.latDecimal]
	        },
	        properties: {
	          title: 'Live Location of '+dr.username,
	          description: contentString,
	          'marker-color': '#3bb2d0',
	          'marker-size': 'large',
	          'marker-symbol': 'bus'
	        }
	      };
		
		geojson[counter++] = geroData;
		
	});
	
	myLayer.setGeoJSON(geojson);
	
	/*$('#map').on('click', '.trigger', function() {
	    alert('Hello from Toronto!');
	});*/
}

/* for google map api */
function initMap() {

	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : 5,
		center : {
			lat : 41.85,
			lng : -87.65
		}
	});
	setMarkers(map);
}

function addData(data) {
	drivers = data;
}

function setMarkers(map) {
	var image = {
		url : '../images/gtruckicon.png',
		size : new google.maps.Size(50, 65),
		origin : new google.maps.Point(0, 0),
		anchor : new google.maps.Point(0, 32)
	};
	var shape = {
		coords : [ 1, 1, 1, 20, 18, 20, 18, 1 ],
		type : 'poly'
	};

	$.each(drivers, function(index, value) {
		
		var marker = new google.maps.Marker({
			position : {
				lat : value.latDecimal,
				lng : value.lngDecimal
			},
			map : map,
			icon : image,
			shape : shape,
			title : "Driver Location"
		});
		marker.set('driver', value);
		marker.addListener('click', showArrays);
		infoWindow = new google.maps.InfoWindow;
		function showArrays(event) {
			console.log(event+" "+this.get('driver'));
			dr = this.get('driver');
			
			var contentString = '<div class="row">'
									+ '<div class="col-sm-4">'
										+ '<img style="height:50px;weight:50px;" data-image="" src="https://s3.ap-south-1.amazonaws.com/novusaware-test/users/profile/'+dr.hashedUserImage+'">'
									+ '</div>'
									+ '<div class="col-sm-8">'
										+'<b>Live Location</b></br>'+dr.username
									+ '</div>'
								+ '</div>'
								+'<div class="row">'
									+'<div class="col-sm-12">'+event.latLng.lat()+','+event.latLng.lng()+'</div>'
								+'</div><br/>'
								+'<div class="row">'
									+'<div class="col-sm-12"><b>Mobile No:</b>'+dr.phone+'</div>'
								+'</div><br/>'
								+'<div class="row">'
									+'<div class="col-sm-12"><b>Vehicle No:</b>'+dr.vehicleRegistrationNo+'</div>'
								+'</div>'
			infoWindow.setContent(contentString);
			infoWindow.setPosition(event.latLng);
			infoWindow.open(map);
		}
	});
}
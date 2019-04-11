$(document).ready(function() { 
	
$("#registrationNo").focusout(function() {
    	
    	var registrationNo=$('#registrationNo').val();
     	   $.ajax({

                url :"validateByRegistrationNo/"+registrationNo,

                type : "GET",
                success : function(data) {
             	   //console.log(data);

                 	 if(data == true){
                 		 $("#RegistrationExitsOrNot").text("Registration no. Already Exits");
                 		  $('#submit-btn').prop('disabled', true);
                 	 }else if(data == false){
                 		$("#RegistrationExitsOrNot").hide();   
                 		  $('#submit-btn').prop('disabled', false);
                 	 }

             },
                error : function(xhr, status, error) {
             	   $("#loginIdExitsOrNot").hide();
             	   
             	}
            });
    	});

})
    



function nospaces(t) {
	if (t.value.match(/\s/g)) {
		t.value = t.value.replace(/\s/g, '');

	}
}


$('#crn').on('change', function() {
		var crn = $("#crn").val();
	   $.ajax({
           url :"/hitech-app/user/get-all-fleet-manager-by-crn/"+crn,
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
          	   console.log("error while fetching fleet manager list")
        	}
       });
	
	
	});




// Get Sidebar Menu Width and set In mainContent Div
jQuery(window).ready(SidebarMenu_width);
jQuery(window).resize(SidebarMenu_width);
function SidebarMenu_width() {
    var navigation_width = jQuery('.navigations').outerWidth();
    jQuery('.mainContent').css('margin-left', navigation_width);  
}

jQuery(window).on('load', function(){
jQuery('body').css('opacity','1');
});

jQuery(document).ready(function(){
	

// # Profile Page
// Truck Info Accordian
    jQuery(document).on('click','.strip_truck_info', function(e){
        
        var el = jQuery(this);
        el.closest('.strip_accordian_parent').find('.toggleContent').slideToggle(700);
        jQuery(this).toggleClass('openToggleContent');

        if(jQuery(this).hasClass('openToggleContent')){
            //jQuery(this).find('.plus_minus > p img').attr('src','images/minusIcon.png');
            jQuery(this).find('.plus_minus > p i').addClass('fas fa-minus');
        }else {
            //jQuery(this).find('.plus_minus > p img').attr('src','images/plusIcon.png');
            jQuery(this).find('.plus_minus > p i').removeClass('fa-minus');
        }
        
    });

    // Menu of submenu toggle
    // Script for Nav
    jQuery('.navigations .nav>li').on('click', function(){
    	
    	jQuery(this).toggleClass('openMenu');
    	  if (jQuery(this).hasClass('openMenu')){
    			jQuery(this).find('ul.dropdown-menu').slideDown();
    	  } else {
    		  jQuery(this).find('ul.dropdown-menu').slideUp();
    	  }
    
    });
    
   
     // Responsive Menu 
     jQuery(document).on('click', '.mobile_menu_hamburgar', function(){
        jQuery('.mobile_menu_hamburgar i').toggleClass('fa-times');
        jQuery(this).parents('body').find('.navigations').toggleClass('open_menu');
     });

     $("#userId").focusout(function(){
         var userName = $("#userId").val();
       
         //$.get("../user/get-user/"+userName, function(data, status)
         $.ajax({
 			url : "user/get-user/" + userName,
 			method : 'GET',
 			dataType : 'json',
 			success : function(data,status) 	
        		 { 
 			
         	if(status === "success"){
         		if(data.code === 200){
         			$("#UserNameExitsOrNot").hide();  
	       		  $('#submit').prop('disabled', false);
         	}
         		if(data.code === 404){
         			$("#paramError").hide();
         			$("#tokenError").hide();
         			$("#UserNameExitsOrNot").show();  
  	       		  $('#submit').prop('disabled', true);
  				}
         			
         	}else{
         		$( "#userId" ).removeClass( "alert alert-success").addClass( "alert alert-danger" );
         	}
             console.log("Data: " + data.code + "\nStatus: " + status);
        		 }
         });
     });
     
     
     $('.dropdown').on('show.bs.dropdown', function(e){
    	//  alert(1);
    	  $(this).find('.dropdown-menu').first().stop(true, true).slideDown(300);
    	});

    	$('.dropdown').on('hide.bs.dropdown', function(e){
    	  $(this).find('.dropdown-menu').first().stop(true, true).slideUp(200);
    	});
    	
    
    	
});



// Row Wise Equal Height

equalheight = function(container){

var currentTallest = 0,
     currentRowStart = 0,
     rowDivs = new Array(),
     $el,
     topPosition = 0;
 $(container).each(function() {

   $el = $(this);
   $($el).height('auto')
   topPostion = $el.position().top;

   if (currentRowStart != topPostion) {
     for (currentDiv = 0 ; currentDiv < rowDivs.length ; currentDiv++) {
       rowDivs[currentDiv].height(currentTallest);
     }
     rowDivs.length = 0; // empty the array
     currentRowStart = topPostion;
     currentTallest = $el.height();
     rowDivs.push($el);
   } else {
     rowDivs.push($el);
     currentTallest = (currentTallest < $el.height()) ? ($el.height()) : (currentTallest);
  }
   for (currentDiv = 0 ; currentDiv < rowDivs.length ; currentDiv++) {
     rowDivs[currentDiv].height(currentTallest);
   }
 });
}

$(window).on('load', function() {
  equalheight('.mainContent .row .cols_div');
});

$(window).ready(function() {
	  equalheight('.mainContent .row .cols_div');
	});


$(window).resize(function(){
  equalheight('.mainContent .row .cols_div');
});

$(window).on('load', function(){

	 $(".navigations .nav>li").each(function() {
		if($(this).find('.dropdown-menu >li').hasClass('active')){
			$(this).find('.dropdown-menu').css('display','block');
			$(this).closest('li').addClass('openMenu');
		}
	 });

});


	window.document.onkeydown = function(e) {
		  if (!e) {
		    e = event;
		  }
		  if (e.keyCode == 27) {
		    lightbox_close();
		  }
		}

		function lightbox_open() {
		  var lightBoxVideo = document.getElementById("VisaChipCardVideo");
		  window.scrollTo(0, 0);
		  document.getElementById('light').style.display = 'block';
		  document.getElementById('fade').style.display = 'block';
		  lightBoxVideo.play();
		}

		function lightbox_close() {
		  var lightBoxVideo = document.getElementById("VisaChipCardVideo");
		  document.getElementById('light').style.display = 'none';
		  document.getElementById('fade').style.display = 'none';
		  lightBoxVideo.pause();
		}





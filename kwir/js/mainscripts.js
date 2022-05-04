$(window).on('beforeunload', function() {
   $(window).scrollTop(0);
});

// co robimy gdy strona się wczyta
$( document ).ready(function() {
	
	var height = $("#newslink").outerHeight();
	var strheight = "-" + height + "px";
    $("#newslink").css("bottom", strheight);
	$("#newslink").css("display", "block");	
	
	$(".main-photo h1").animate({opacity: "1"}, 1500);
	$(".main-photo hr").animate({opacity: "1"}, 1500);
	$(".main-photo h3").animate({opacity: "1"}, 1500);
	$(".main-photo .pass").animate({opacity: "1"}, 2000);
	$(".main-photo .infobutton").animate({marginLeft: "0px"}, 1000);
	$(".main-photo .facebutton").animate({marginLeft: "0px"}, 1000);
});

//pokazujemy ogłoszenia
$("#infobuttonid").click(function() {
	$("#newslink").animate({bottom: "0px"});
});

//ukrywamy ogłoszenia
$("#closebuttonid").click(function() {
	var height = $("#newslink").outerHeight();
	var strheight = "-" + height + "px";
	$("#newslink").animate({bottom: strheight});
});

$('.scroll_to').click(function(e){
	var jump = $(this).attr('href');
	var new_position = $(jump).offset();
	$('html, body').stop().animate({ scrollTop: new_position.top }, 500);
	e.preventDefault();
});
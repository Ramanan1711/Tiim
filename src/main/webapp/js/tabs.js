/***************************/
//@Author: Adrian "yEnS" Mato Gondelle & Ivan Guardado Castro
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/

$(document).ready(function(){
	$(".menu > li").click(function(e){
		switch(e.target.id){
			case "news":
				//change status & style menu
				$("#news").addClass("active");
				$("#tutorials").removeClass("active");
				$("#links").removeClass("active");
				
				$("#two").removeClass("active");
				$("#three").removeClass("active");
				
				//display selected division, hide others
				$("div.news").fadeIn();
				$("div.tutorials").css("display", "none");
				$("div.links").css("display", "none");
				$("div.two").css("display", "none");
				$("div.three").css("display", "none");
			break;
			case "tutorials":
				//change status & style menu
				$("#news").removeClass("active");
				$("#tutorials").addClass("active");
				$("#links").removeClass("active");
				
				$("#two").removeClass("active");
				$("#three").removeClass("active");
				
				//display selected division, hide others
				$("div.tutorials").fadeIn();
				$("div.news").css("display", "none");
				$("div.links").css("display", "none");
				$("div.two").css("display", "none");
				$("div.three").css("display", "none");
			break;
			case "links":
				//change status & style menu
				$("#news").removeClass("active");
				$("#tutorials").removeClass("active");
				$("#links").addClass("active");
				
				$("#two").removeClass("active");
				$("#three").removeClass("active");
				
				//display selected division, hide others
				$("div.links").fadeIn();
				$("div.news").css("display", "none");
				$("div.tutorials").css("display", "none");
				$("div.two").css("display", "none");
				$("div.three").css("display", "none");
			break;
			case "two":
				//change status & style menu
				$("#news").removeClass("active");
				
				$("#two").addClass("active");
				$("#three").removeClass("active");
				
				$("#links").removeClass("active");
				$("#tutorials").removeClass("active");
				//display selected division, hide others
				$("div.two").fadeIn();
				$("div.news").css("display", "none");
				$("div.links").css("display", "none");
				$("div.tutorials").css("display", "none");
				$("div.three").css("display", "none");
			break;
			case "three":
				//change status & style menu
				$("#news").removeClass("active");
				$("#two").removeClass("active");
				$("#three").addClass("active");
				$("#links").removeClass("active");
				$("#tutorials").removeClass("active");
				//display selected division, hide others
				$("div.three").fadeIn();
				$("div.news").css("display", "none");
				$("div.links").css("display", "none");
				$("div.tutorials").css("display", "none");
				$("div.two").css("display", "none");
			break;
		}
		//alert(e.target.id);
		return false;
	});
});
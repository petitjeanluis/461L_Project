var selectedUser = null;
var selectedWorkout = null;
$(document).ready(function (){
    $("#follow-list li").click(function(){
        $("#follow-list li").removeClass("active-select");
        $(this).addClass("active-select");
        selectedUser = $(this).attr("id");
    });
    $("#workout-table-body tr").click(function(){
        $("#workout-table-body tr").removeClass("active-select");
        $(this).addClass("active-select");
        selectedWorkout = $(this).attr("id");
    });
});

function addFollower() {
	$("#friend-list").append($('#'+selectedUser).removeClass("active"));
	var userEmail = $('#'+selectedUser).html();
	console.log(userEmail);
    $.post("/addfriendservlet",{
    	email : userEmail
    });
}

function addWorkout() {
	var userEmail = $("#"+selectedWorkout +" td:first-child").html();
	var userWorkout = $("#"+selectedWorkout +" td:last-child").html();
	$.post("/addfriendsworkoutservlet",{
    	email : userEmail,
    	workout : userWorkout
    });
}
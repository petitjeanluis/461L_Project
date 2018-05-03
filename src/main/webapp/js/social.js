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
	var userEmail = $('#'+selectedUser).html();
	
	if(userEmail == null) {
		console.log("returing");
		return;
	}
	
	var form = document.getElementById('add-friend-form');

    var input = document.createElement("input");
    input.setAttribute("name","email");
    input.setAttribute("value",userEmail);
    form.appendChild(input);
    
    form.submit();
}

function addWorkout() {
	var userEmail = $("#"+selectedWorkout +" td:first-child").html();
	var userWorkout = $("#"+selectedWorkout +" td:last-child").html();
	
	if(userEmail == null) {
		console.log("returing");
		return;
	}
	
	var form = document.getElementById('add-friend-workout-form');

    var input = document.createElement("input");
    input.setAttribute("name","email");
    input.setAttribute("value",userEmail);
    form.appendChild(input);
    
    var input = document.createElement("input");
    input.setAttribute("name","workout");
    input.setAttribute("value",userWorkout);
    form.appendChild(input);
    
    form.submit();
}
function searchFilter() {
    // Declare variables
    var input, filter, ul, li, i;
    input = document.getElementById('follow-search');
    filter = input.value.toUpperCase();
    ul = document.getElementById("follow-list");
    li = ul.getElementsByTagName("li");

    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < li.length; i++) {
        a = li[i];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}
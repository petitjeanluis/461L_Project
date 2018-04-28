var selected = [];
$(document).ready(function(){
    $(".workout-list li").click(function(){
        if($(this).hasClass("active")) {
            $(this).removeClass("active");
            var index = selected.indexOf($(this).attr("id"));
            selected.splice(index,1);
        } else {
        	var selectedItems = document.getElementsByClassName("active");
        	for(var i = 0; i < selectedItems.length; i++) {
        		var item = selectedItems[i].classList.remove("active");
        	}
        	while(selected.length) {
        		selected.pop();
        	}
            $(this).addClass("active");
            selected.push($(this).attr("id"));
        }
        console.log(selected);
    });
});

function start() {
	var element = document.getElementById(selected[0]);
	var form = document.getElementById('workout-form');
	var input = document.createElement("input");
    input.setAttribute("name","workoutName");
    input.setAttribute("value",element.innerHTML);
    form.appendChild(input);
    form.submit();
}

function searchFilter1() {
    // Declare variables
    var input, filter, ul, li, i;
    input = document.getElementById('myInput1');
    filter = input.value.toUpperCase();
    ul = document.getElementById("my-list");
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
function searchFilter2() {
    // Declare variables
    var input, filter, ul, li, i;
    input = document.getElementById('myInput2');
    filter = input.value.toUpperCase();
    ul = document.getElementById("standard-list");
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
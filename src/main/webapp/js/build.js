var displayRight;
var displayLeft;
var selectedLeft = [];
var selectedRight = []
$(document).ready(function(){
    displayLeft = document.getElementById('exercise-list');
    displayRight = document.getElementById('create-list');

    $("#exercise-list li").click(function(){
        if($(this).hasClass("active")) {
            $(this).removeClass("active");
            var index = selectedLeft.indexOf($(this).attr("id"));
            selectedLeft.splice(index,1);
        } else {
            $(this).addClass("active");
            selectedLeft.push($(this).attr("id"));
        }
    });
});

function removeElements() {
    var exerciseUl = document.getElementById("exercise-list");
    while(exerciseUl.firstChild) {
        exerciseUl.removeChild(exerciseUl.firstChild);
    }
}

function addItem() {
    console.log("adding");
    var size = selectedLeft.length;
    for(var i = 0; i < size; i++) {
        var id = selectedLeft[i];
        var item = document.getElementById(id);
        var newItem = item.cloneNode(true);
        newItem.classList.remove("active");
        item.remove();
        displayRight.appendChild(newItem);
        $("#"+id).click(function(){
            if($(this).hasClass("active")) {
                $(this).removeClass("active");
                var index = selectedRight.indexOf($(this).attr("id"));
                selectedRight.splice(index,1);
            } else {
                $(this).addClass("active");
                selectedRight.push($(this).attr("id"));
            }
        });
    }
    selectedLeft.splice(0,size);
}

function removeItem() {
    console.log("removing");
    var size = selectedRight.length;
    for(var i = 0; i < size; i++) {
        var id = selectedRight[i];
        var item = document.getElementById(id);
        var newItem = item.cloneNode(true);
        item.remove();
        newItem.classList.remove("active");
        displayLeft.appendChild(newItem);
        $("#"+id).click(function(){
            if($(this).hasClass("active")) {
                $(this).removeClass("active");
                var index = selectedLeft.indexOf($(this).attr("id"));
                selectedLeft.splice(index,1);
            } else {
                $(this).addClass("active");
                selectedLeft.push($(this).attr("id"));
            }
        });
    }
    selectedRight.splice(0,size);
}

function create() {
    var form = document.getElementById('exercise-form');
    var size = displayRight.childElementCount;
    if(size == 0) {
    	return;
    }
    var childList = displayRight.children;
    for(var i = 0; i < size; i++) {
        var input = document.createElement("input");
        input.setAttribute("name","Exercise"+i);
        input.setAttribute("value",childList[i].innerHTML);
        form.appendChild(input);
    }
    var input = document.createElement("input");
    input.setAttribute("name","numOfExercises");
    input.setAttribute("value",size);
    form.appendChild(input);
    
    input = document.createElement("input");
    input.setAttribute("name","workoutName");
    var randy = Math.floor(Math.random() * 100);
    input.setAttribute("value","Custom Workout "+randy);
    form.appendChild(input);
    form.submit();
}

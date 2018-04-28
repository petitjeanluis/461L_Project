function updateCollapse(name) {
	//use the id to get the appropiate picture urls
	var img1 = document.getElementById("image1");
	var img2 = document.getElementById("image2");
	img1.setAttribute("src","/img/"+name+"-1.jpg");
	img2.setAttribute("src","/img/"+name+"-2.jpg");
}

function ajax_update(exerciseName, exerciseReps, exerciseWeight, exerciseSet) {
	$.post("/workoutservlet",
			{
				name : exerciseName,
				reps : exerciseReps,
				weight : exerciseWeight,
		        set : exerciseSet
			});
};

function ajax_return(data, status) {
	//var divTag = document.getElementById("data");
	//divTag.innerHTML = status;
	alert("Data loaded was a " + status);
}

//Rest timer and next set logic
/*REST TIMER*/
var intervalTimer;
var minute,second;
var running = false;
$workoutGui = null;
$timerGui = null;

function rest(selected,time) {
    //remove workout-gui and show timer-gui
    $workoutGui = $(selected).parent('.exercise-footer').parent('.workout-gui');
    $timerGui = $workoutGui.parent('.panel-body').find('.timer-gui');
    $playPauseButton = $timerGui.find('.timer-controls').find('.pause-btn');
    increaseSet();
    //show timer
    $workoutGui.hide();
    $timerGui.show();
    //set up timer
    minute = Math.floor(time/60);
    second = time%60;
    $timerDisplay = $timerGui.find('.timer-display');
    $timerDisplay.html(padTime(minute) + " : " + padTime(second));
    //start timer
    running = true;
    intervalTimer = setInterval(secondInterrupt,1000);
}

function next(selected) {
    $workoutGui = $(selected).parent('.exercise-footer').parent('.workout-gui');
    $timerGui = $workoutGui.parent('.panel-body').find('.timer-gui');
    if(increaseSet()) {
        restDone();
    }
}

//must be called after updating $workoutGui, return true if back to 1
function increaseSet() {
    var subtitle =  $workoutGui.find('.subtitle').text();
    var set = parseInt(subtitle.split(" ")[1]);
    set++;
    if(set > 3) {
        $workoutGui.find('.subtitle').html("Set 1");
        sendUpdate();
        return true;
    } else {
        $workoutGui.find('.subtitle').html("Set " + set);
        sendUpdate();
        return false;
    }
}

function padTime(time) {
    if(time < 10) {
        return "0"+time;
    } else {
        return time;
    }
}

function secondInterrupt() {
    console.log("interrupt");
    if (second == 0) {
        if(minute == 0) {
            clearInterval(intervalTimer);
            running = false;
            restDone();
        } else {
            second = 59;
            minute--;
        }
    } else {
        second--;
    }
    $timerDisplay.html(padTime(minute) + " : " + padTime(second));
}

function pausePlay() {
    if(running) {
        running = false;
        clearInterval(intervalTimer);
        $playPauseButton.html("Start");
    } else {
        running = true;
        intervalTimer = setInterval(secondInterrupt,1000);
        $playPauseButton.html("Pause");
    }
}

function done() {
    clearInterval(intervalTimer);
    running = false;
    restDone();
}

function restDone() {
    $timerGui.hide();
    var subtitle =  $workoutGui.find('.subtitle').text();
    var set = parseInt(subtitle.split(" ")[1]);
    if(set == 1) {
        $workoutGui.show();
        nextExercise();
    } else {
        $workoutGui.show();
    }
}

//must be callsed after updating $workoutGUI, or else it sends previous info
function sendUpdate() {
    console.log("sendUpate");
    var exerciseName = $workoutGui.parent('.panel-body').parent('.panel-collapse').parent('.panel').find('.panel-heading').find('.panel-title').find('a').text();
    var exerciseReps = $workoutGui.find('.shifters').find('.left-shifter').find('input').val();
    var exerciseWeight = $workoutGui.find('.shifters').find('.right-shifter').find('input').val();
    var subtitle =  $workoutGui.find('.subtitle').text();
    var exerciseSet = parseInt(subtitle.split(" ")[1]);
    console.log("updating " + exerciseName + ": " + exerciseReps + " reps, " + exerciseWeight +" lbs, on set " + exerciseSet);
    ajax_update(exerciseName, exerciseReps, exerciseWeight, exerciseSet);
}

function nextExercise() {
    //update current exercise in the future
    $workout = $workoutGui.parent('.panel-body').parent('.panel-collapse');
    $nextWorkout = $workout.parent('.panel').next().find('.panel-collapse');
    $workout.removeClass('in');
    $nextWorkout.addClass('in');
}

function changeRep(delta, selected) {
    $input = $(selected).parent('.arrow').parent('.left-shifter').find('input');
    var value = parseInt($input.val());
    value += delta;
    $input.val(value);
}

function changeWeight(delta, selected) {
    $input = $(selected).parent('.arrow').parent('.right-shifter').find('input');
    var value = parseInt($input.val());
    value += delta;
    $input.val(value);
}

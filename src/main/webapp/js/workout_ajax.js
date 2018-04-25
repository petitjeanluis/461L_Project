var ajax_update = function(exerciseName, exerciseReps, exerciseWeight) {
	$.post("/WorkoutServlet",
	{
		name = exerciseName,
		reps = exerciseReps,
		weight = exerciseWeight
	}, ajax_return(data, status)
	);
};

var ajax_return = function(data, status) {
	//var divTag = document.getElementById("data");
	//divTag.innerHTML = status;
	alert("Data loaded was a " + status);
}
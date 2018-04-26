function updateCollapse(id) {
	//use the id to get the appropiate picture urls
	console.log(id);
	var img1 = document.getElementById("image1");
	var img2 = document.getElementById("image2");
	img1.setAttribute("src","/img/"+id+"-1.jpg");
	img2.setAttribute("src","/img/"+id+"-2.jpg");
}

function ajax_update(exerciseName, exerciseReps, exerciseWeight, setNum) {
	$.post("/workoutservlet",
	{
		name : exerciseName,
		reps : exerciseReps,
		weight : exerciseWeight,
		set : setNum
	}, function(data) {
		alert(data);
	});
};

function ajax_return(data) {
	//var divTag = document.getElementById("data");
	//divTag.innerHTML = status;
	alert(data);
}
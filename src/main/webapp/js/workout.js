function updateColapse(id) {
	//use the id to get the appropiate picture urls
	console.log(id);
	var img1 = document.getElementById("image1");
	var img2 = document.getElementById("image2");
	img1.setAttribute("src","/img/"+id+"-1.jpg");
	img2.setAttribute("src","/img/"+id+"-2.jpg");
}
function updateColapse(id) {
	//use the id to get the appropiate picture urls
	var img1 = document.getElementById("image1");
	var img2 = document.getElementById("image2");
	img1.setAttribute("src","/img/pic_"+id+".jpg");
	img2.setAttribute("src","/img/pic_"+id+".jpg");
}
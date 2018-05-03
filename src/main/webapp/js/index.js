function togglePrivacy() {
	if(confirm("WARNING: Allowing Workout Sharing is IRREVERSIBLE!")) {
		window.location.replace("/toggleprivacyservlet");
	} else {
		document.getElementById("toggle-switch").checked = false;
	}
}
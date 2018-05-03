function togglePrivacy() {
	if(confirm("WARNING: Allowing Workout Sharing is IRREVERSIBLE!")) {
		$.post("/toggleprivacyservlet");
		console.log("PrivacyToggled");
	} else {
		document.getElementById("toggle-switch").checked = false;
	}
}
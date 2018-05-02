function togglePrivacy() {
	if(confirm("Making your profile public is IRREVERSIBLE!")) {
		$.post("/toggleprivacyservlet");
		console.log("PrivacyToggled");
	} else {
		document.getElementById("toggle-switch").checked = false;
	}
}
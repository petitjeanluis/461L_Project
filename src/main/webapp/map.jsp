<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
	<link rel="stylesheet" href="style/map.css">
  </head>
  <body>
    <input id="pac-input" class="controls" type="text" placeholder="Search Box">
    <div id="map"></div>
    <script>
      // This example adds a search box to a map, using the Google Place Autocomplete
      // feature. People can enter geographical searches. The search box will return a
      // pick list containing a mix of places and predicted search terms.

      // This example requires the Places library. Include the libraries=places
      // parameter when you first load the API. For example:
      // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">
      
      //https://maps.googleapis.com/maps/api/js?key=AIzaSyBLm9hpR1kCLdc5R9Z6Kgy2GXip2U3B0fs&libraries=places&callback=initAutocomplete
	  //https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyAq7kNUlpz-ljjD0gD1Cm5CD1kdrjwvUdU

      function initAutocomplete() {
        var map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 30.2672, lng: -97.7431},
          zoom: 4,
          mapTypeId: 'roadmap'
        });

        // Create the search box and link it to the UI element.
        var input = document.getElementById('pac-input');
        var searchBox = new google.maps.places.SearchBox(input);
        map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

        // Bias the SearchBox results towards current map's viewport.
        map.addListener('bounds_changed', function() {
          searchBox.setBounds(map.getBounds());
        });

        var markers = [];
        // Listen for the event fired when the user selects a prediction and retrieve
        // more details for that place.
        searchBox.addListener('places_changed', function() {
          var places = searchBox.getPlaces();

          if (places.length == 0) {
            return;
          }

          // Clear out the old markers.
          markers.forEach(function(marker) {
            marker.setMap(null);
          });
          markers = [];

          // For each place, get the icon, name and location.
          var bounds = new google.maps.LatLngBounds();
          places.forEach(function(place) {
        	  //console.log(place.geometry.location.lat);
        	  //console.log(place.geometry.location.lng);
            if (!place.geometry) {
              console.log("Returned place contains no geometry");
              return;
            }
            
            console.log(place.geometry.location.lat());
            console.log(place.geometry.location.lng());
            var latitude = place.geometry.location.lat();
            var longitude = place.geometry.location.lng();
            var url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude.toString() + "," + longitude.toString() + "&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyC1mQG3JvL53Vtdxhz-MJLTd6H2odupgDc";
            console.log(url);
            
            var icon = {
              url: place.icon,
              size: new google.maps.Size(71, 71),
              origin: new google.maps.Point(0, 0),
              anchor: new google.maps.Point(17, 34),
              scaledSize: new google.maps.Size(25, 25)
            };

            // Create a marker for each place.
            markers.push(new google.maps.Marker({
              map: map,
              icon: icon,
              title: place.name,
              position: place.geometry.location
            }));

            if (place.geometry.viewport) {
              // Only geocodes have viewport.
              bounds.union(place.geometry.viewport);
            } else {
              bounds.extend(place.geometry.location);
            }
          });
          map.fitBounds(bounds);
        });
      }

    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBLm9hpR1kCLdc5R9Z6Kgy2GXip2U3B0fs&libraries=places&callback=initAutocomplete"
         async defer></script>
  </body>
</html>
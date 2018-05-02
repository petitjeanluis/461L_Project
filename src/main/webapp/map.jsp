<!DOCTYPE html>
<html>
  <head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Workout</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="style/map.css">
  </head>
  <body>
    <input id="pac-input" class="controls" type="text" placeholder="Search Box">
	
    <div id="map">
    </div>
    
    <script>
      // This example adds a search box to a map, using the Google Place Autocomplete
      // feature. People can enter geographical searches. The search box will return a
      // pick list containing a mix of places and predicted search terms.

      // This example requires the Places library. Include the libraries=places
      // parameter when you first load the API. For example:
      // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">
      
      //https://maps.googleapis.com/maps/api/js?key=AIzaSyBLm9hpR1kCLdc5R9Z6Kgy2GXip2U3B0fs&libraries=places&callback=initAutocomplete
	  //https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyAq7kNUlpz-ljjD0gD1Cm5CD1kdrjwvUdU
	  var infowindow;
      function initAutocomplete() {
        var map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 30.2672, lng: -97.7431},
          zoom: 4,
          mapTypeId: 'roadmap'
        });
        
        infowindow = new google.maps.InfoWindow();

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
          
          if(places.length == 1) {
        	  console.log("one location");
          }

          // Clear out the old markers.
          markers.forEach(function(marker) {
            marker.setMap(null);
          });
          markers = [];

          // For each place, get the icon, name and location.
          var bounds = new google.maps.LatLngBounds();
          places.forEach(function(place) {
            if (!place.geometry) {
              console.log("Returned place contains no geometry");
              return;
            }
            
            
            var latitude = place.geometry.location.lat();
            var longitude = place.geometry.location.lng();
            var url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude.toString() + "," + longitude.toString() + "&radius=1500&type=gym&key=AIzaSyC1mQG3JvL53Vtdxhz-MJLTd6H2odupgDc";
            console.log(url);
            
      	    var exactSpot = new google.maps.LatLng(latitude, longitude);
      	    map.center = exactSpot;
      	    map.zoom = 15;
      	    var request = {
      			  location: exactSpot,
      			  radius: '1500',
      			  type: ['gym']
      	    };
      	    var service = new google.maps.places.PlacesService(map);
      	    service.nearbySearch(request,callback);
            
//             var x = JSON.parse('{ "name":"John", "age":30, "city":"New York"}');
//             console.log(x);
//             $.getJSON(url+"&callback=?", function(data) {
//             	console.log("Data: " + data);
//             });
            
//             $.ajax({
//                 url: url, 
//                 type: "GET",   
//                 dataType: 'jsonp',
//                 cache: false,
//                 success: function(response){                          
//                     console.log(response);                   
//                 }           
//             });
//             var location = new google.maps.LatLng(latitude, longitude);
//             var background = new google.maps.Map()
//             var places = new google.maps.places;
<%--             <% --%>
//             	String s = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude.toString() + "," + longitude.toString() + "&radius=1500&type=gym&key=AIzaSyC1mQG3JvL53Vtdxhz-MJLTd6H2odupgDc";
//             	JSONObject json = new JSONObject(s);
//             	JSONArray array = (JSONArray)json.get("results");
//             	String [] values = new String[array.length()]; 
<%--             %> --%>
            
            
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
      
      function callback(results, status) {
    	  if(status == google.maps.places.PlacesServiceStatus.OK) {
    		  var map = new google.maps.Map(document.getElementById('map'), {
    			 center: results[0].geometry.location,
    			 zoom: 14 
    		  });
    		  
    		  for(var i = 0; i < results.length; i++) {
    			  var place = results[i];
    			  console.log(place.name);
    			  var marker = new google.maps.Marker({
    				  position: place.geometry.location,
    				  map: map,
    				  title: place.name
    			  });
    			  //console.log(place);
    			  //var photo = place.reference;
    			  google.maps.event.addListener(marker, 'click', function() {
    	              infowindow.setContent(this.title);
    	              infowindow.open(map,this);
    				  //window.open("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photo+"&key=AIzaSyC1mQG3JvL53Vtdxhz-MJLTd6H2odupgDc");
    	          });
    			  marker.setMap(map);
    		  }
    	  }
      }

    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBLm9hpR1kCLdc5R9Z6Kgy2GXip2U3B0fs&libraries=places&callback=initAutocomplete"
         async defer></script>
  </body>
</html>
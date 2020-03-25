var map;
var marker;
var markersPlace = [];
function showMap(lat, long) {
  var redIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
  });

  map = L.map('map').setView([lat, long], 10);

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  }).addTo(map);

  marker = L.marker([lat, long], {icon: redIcon}).addTo(map)
    .bindPopup('Your localization')
    .openPopup();

  map.addEventListener("click", function(e) {
      marker.remove();
      marker = new L.marker(e.latlng, {icon: redIcon}).addTo(map)
        .bindPopup("Your point")
        .openPopup();
  })
}

function addPlacesOnMap(places) {
  removePreviousMarkers();
  for (var i = 0; i < places.length; i++) {
    markersPlace[i] = L.marker([places[i].lat, places[i].lng]).addTo(map)
      .bindPopup('<b>' + places[i].name + '</b>'
        + "<br>Address: " + places[i].address
        + "<br>Rating: " + places[i].rating
        + "<br>Number of votes: " + places[i].userRatingsTotal
        + "<br>Opened: " + places[i].openNow)
      .openPopup()
  }
}

function removePreviousMarkers() {
  for(var i=0; i<markersPlace.length; i++){
    markersPlace[i].remove();
  }
  markersPlace = [];
}

function getMarkerLocation() {
  return marker.getLatLng();
}

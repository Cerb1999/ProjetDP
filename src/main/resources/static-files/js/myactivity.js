var macarte = null;
var marker = null;

function initMap(lat, lon) {
    macarte = L.map('map').setView([lat, lon], 11);
    marker = L.marker([lat, lon]).addTo(macarte);
    L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
        attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
        minZoom: 1,
        maxZoom: 20
    }).addTo(macarte);
}

window.onload = function(){
    let lat = document.getElementById("latitude").textContent;
    let lon = document.getElementById("longitude").textContent;
    initMap(lat, lon);
};
var lat = 48.692054;
var lon = 6.184417;
var macarte = null;
var marker = null;

// Fonction d'initialisation de la carte
function initMap() {
    // Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"
    macarte = L.map('map').setView([lat, lon], 11);
    marker = L.marker([lat, lon]).addTo(macarte);
    L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
        // Il est toujours bien de laisser le lien vers la source des données
        attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
        minZoom: 1,
        maxZoom: 20
    }).addTo(macarte);


    macarte.addEventListener('click', function(event) {
        lat = event.latlng.lat;
        lng = event.latlng.lng;
        marker.setLatLng(new L.LatLng(lat, lng));
    });

    invalidateMap();
}
window.onload = function(){
    // Fonction d'initialisation qui s'exécute lorsque le DOM est chargé
    initMap();
};

function mapSearch(event) {
    event.preventDefault()
    var address = $('#postal').val() + " " + $('#city').val() + " " + $('#adr').val();
    $.get(location.protocol + '//api-adresse.data.gouv.fr/search/?q='+address, function(data){
        let coordinates = new L.LatLng(data.features[0].geometry.coordinates[1], data.features[0].geometry.coordinates[0]);
        macarte.panTo(coordinates);
        marker.setLatLng(coordinates);
    });
}


$("#city").autocomplete({
    source: function (request, response) {
        $.ajax({
            url: "https://api-adresse.data.gouv.fr/search/?city="+$("input[name='city']").val()+'&limit=15',
            data: { q: request.term },
            dataType: "json",
            success: function (data) {
                var cities = [];
                response($.map(data.features, function (item) {
                    // Ici on est obligé d'ajouter les villes dans un array pour ne pas avoir plusieurs fois la même
                    if ($.inArray(item.properties.postcode, cities) == -1) {
                        cities.push(item.properties.postcode);
                        return { label: item.properties.postcode + " - " + item.properties.city,
                            postcode: item.properties.postcode,
                            value: item.properties.city
                        };
                    }
                }));
            }
        });
    },
    // On remplit aussi le CP
    select: function(event, ui) {
        $('#postal').val(ui.item.postcode);
    },
    change: function (event, ui) {
        if(ui.item == null || ui.item ==  undefined){
            $("#city").val("");
        }
    }
});

$("#postal").autocomplete({
    source: function (request, response) {
        $.ajax({
            url: 'https://api-adresse.data.gouv.fr/search/?q='+$("input[name='cp']").val()+'&type=municipality&autocomplete=1&limit=15',
            data: { q: request.term },
            dataType: "json",
            success: function (data) {
                response($.map(data.features, function (item) {
                    return {
                        label: item.properties.postcode + " - " + item.properties.city,
                        value: item.properties.postcode
                    };
                }));
            }
        });
    },
    select: function(event, ui) {
        $('#city').val(ui.item.city);
    },
    change: function (event, ui) {
        if(ui.item == null || ui.item ==  undefined){
            $("#postal").val("");
        }
    }
});


$("#adr").autocomplete({
    source: function (request, response) {
        $.ajax({
            url: 'https://api-adresse.data.gouv.fr/search/?name='+$("input[name='adr']"),
            data: { q: request.term },
            dataType: "json",
            success: function (data) {
                response($.map(data.features, function (item) {
                    return { label: item.properties.name + " - " + item.properties.postcode + " - " + item.properties.city, value: item.properties.name};
                }));
            }
        });
    },
    change: function (event, ui) {
        if(ui.item == null || ui.item ==  undefined){
            $("#adr").val("");
        }
    }
});

$("#location").autocomplete({
    source: function (request, response) {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/user/location/get/bycityorname",
            data: { name: request.term, city: request.term },
            cache: false,
            timeout: 600000,
            dataType: "json",
            success: function (data) {
                console.log(data);
                response($.map(data, function (item) {
                    return { label: item.address + " - " + item.city, value: item.address};
                }));
            }
        });
    },
    change: function (event, ui) {
        if(ui.item == null || ui.item ==  undefined){
            $("#location").val("");
        }
    }
});

function invalidateMap() {
    if( $('#map_form').css('display') == 'none') {
        $('#map_button').html("Hide");
        $('#map_form').show('slow');
    } else {
        $('#map_button').html("New location from map");
        $('#map_form').hide('slow');
        macarte.invalidateSize();
    }
}
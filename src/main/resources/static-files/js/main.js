$(document).ready(function () {
    $("#registration-form").submit(function (event) {
        event.preventDefault();
        ajax_registration();
    });
    $("#edit-user-form").submit(function (event) {
        event.preventDefault();
        ajax_edit();
    });
    $("#edit-admin-form").submit(function (event) {
        event.preventDefault();
        ajax_edit();
    });
    $(".add-friend-request").click(function (event) {
        event.preventDefault();
        ajax_friend($(this).attr('id'), "request");
    })
    $(".accept-friend-request").click(function (event) {
        event.preventDefault();
        ajax_friend($(this).attr('id'), "add");
    })
    $(".refuse-friend-request").click(function (event) {
        event.preventDefault();
        ajax_friend($(this).attr('id'), "refuse");
    })
    $(".remove-friend").click(function (event) {
        event.preventDefault();
        ajax_friend($(this).attr('id'), "remove");
    })
    $(".read-notification").click(function (event) {
        event.preventDefault();
        ajax_read_notification($(this).attr('id'));
    })
    $("#location-form").submit(function (event) {
        event.preventDefault();
        ajax_add_location();
    })
    $("#activity-form").submit(function (event) {
        event.preventDefault();
        ajax_add_activity();
    })
});


function ajax_registration() {
    var request = {}
    request["username"] = $("#username").val();
    request["firstname"] = $("#firstname").val();
    request["password"] = $("#password").val();
    request["lastname"] = $("#lastname").val();
    request["birthdate"] = $("#birthdate").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/registration",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            window.location = '/login';
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
        }
    });
}


function ajax_edit() {
    var request = {}
    request["username"] = $("#username").val();
    request["firstname"] = $("#firstname").val();
    request["password"] = $("#password").val();
    request["lastname"] = $("#lastname").val();
    request["birthdate"] = $("#birthdate").val();
    request["role"] = $("#role").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/user/profile/edit",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            window.location = "/user/profile/show";
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
        }
    });
}

function ajax_friend(id, action) {
    let url;
    let location;
    var request = {}
    request["id"] = id;

    switch (action) {
        case "request":
            url = "/user/friend/request/add"; location = "/user/notification/list";
            break;
        case "add":
            url = "/user/friend/add"; location = "/user/friend/list";
            break;
        case "refuse":
            url = "/user/friend/refuse"; location = "/user/friend/requests";
            break;
        case "remove":
            url ="/user/friend/remove"; location = "/user/friend/list";
            break;
        default: break;
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            window.location = location;
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
        }
    });
}

function ajax_add_location() {
    var request = {}
    request["name"] = $("#name").val();
    request["city"] = $("#city").val();
    request["address"] = $("#adr").val() + " " + $("#postal").val() + " " + request["city"];

    $.get(location.protocol + '//api-adresse.data.gouv.fr/search/?q='+request["address"], function(data){
        request["latitude"] = data.features[0].geometry.coordinates[1]
        request["longitude"] = data.features[0].geometry.coordinates[0];

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/user/location/add",
            data: JSON.stringify(request),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                $('#location-form').hide();
            },
            error: function (e) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback-location').html(json);
            }
        });
    });
}

function ajax_add_activity() {
    var request = {}
    request["description"] = $("#description").val();
    request["start"] = $("#start").val();
    request["end"] = $("#end").val();
    request["location"] = $("#location").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/user/activity/add",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            window.location = "/user/activity/list"
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback-activity').html(json);
        }
    });
}

function ajax_read_notification(id) {
    var request = {}
    request["id"] = id;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/user/notification/read",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            window.location = "/user/notification/list";
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
        }
    });
}
$(document).ready(function () {
    $(".delete-user").click(function (event) {
        event.preventDefault();
        ajax_remove($(this).attr('id'), "user");
    })
    $(".delete-activity").click(function (event) {
        event.preventDefault();
        ajax_remove($(this).attr('id'), "activity");
    })
    $(".delete-location").click(function (event) {
        event.preventDefault();
        ajax_remove($(this).attr('id'), "location");
    })
});

function ajax_remove(id, action) {
    var request = {}
    request["id"] = id;
    var urlPost = "/admin/"+action+"/remove";
    var urlSuccess = "/admin/"+action+"/list"

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: urlPost,
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            window.location = urlSuccess;
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>" + 'error while removing object : '
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
        }
    });
}

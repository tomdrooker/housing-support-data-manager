$(document).ready(function() {
    $.ajax({
        url:'http://localhost:8080/housing-support-manager/council'
        }).then(function(data) {
        $(".councilName").append(data.councilName);
        $(".councilPhone").append(data.councilPhone);
    });
});
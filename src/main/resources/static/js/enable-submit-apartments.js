$(document).ready(function() {
    $("[id^=chng-btn-]").on("click", function() {
        let changeButtonId = $(this).attr("id");
        let prefix = "chng-btn-";
        let id = changeButtonId.substring(prefix.length);

        $('#sq-' + id).prop("disabled", false);
        $('#sbmt-btn-' + id).prop("disabled", false);
    });
});
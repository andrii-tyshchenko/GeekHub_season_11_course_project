$(document).ready(function() {
    $("[id^=chng-btn-]").on("click", function() {
        let changeButtonId = $(this).attr("id");
        let prefix = "chng-btn-";
        let id = changeButtonId.substring(prefix.length);

        $('#email-' + id).prop("disabled", false);
        $('#auth-' + id).prop("disabled", false);
        $('#enbld-' + id).prop("disabled", false);
        $('#apt-' + id).prop("disabled", false);
        $('#lnm-' + id).prop("disabled", false);
        $('#fnm-' + id).prop("disabled", false);
        $('#ptr-' + id).prop("disabled", false);
        $('#sbmt-btn-' + id).prop("disabled", false);
    });

    $('select[name="authority"]').on('change', function() {
        let selectId = $(this).attr("id");
        let prefix = "auth-";
        let id = selectId.substring(prefix.length);

        if (this.value === "ROLE_ADMIN") {
            $('#apt-' + id).prop("disabled", true);
            $('#apt-adm-' + id).prop("disabled", true);
        } else {
            $('#apt-' + id).prop("disabled", false);
            $('#apt-adm-' + id).prop("disabled", false);
        }
    });
});
$(document).ready(function() {
    let initialHref = $('#xlsx').attr('href');

    $('#xlsx').on("click", function() {
        let yearMonth = $('#yearMonth_sections_select').val();

        $('#xlsx').attr('href', initialHref + yearMonth);
    });
});
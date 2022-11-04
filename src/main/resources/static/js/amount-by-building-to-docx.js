$(document).ready(function() {
    let initialHref = $('#docx').attr('href');

    $('#docx').on("click", function() {
        let yearMonth = $('#yearMonth_building_select').val();

        $('#docx').attr('href', initialHref + yearMonth);
    });
});
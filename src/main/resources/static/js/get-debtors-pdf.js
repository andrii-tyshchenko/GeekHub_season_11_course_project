$(document).ready(function() {
    let initialHref = $('#pdf').attr('href');

    $('#pdf').on("click", function() {
        let quantity = $('#quantity_select').val();

        $('#pdf').attr('href', initialHref + quantity);
    });
});
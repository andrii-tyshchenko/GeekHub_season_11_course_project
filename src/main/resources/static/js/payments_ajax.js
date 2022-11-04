$(document).ready(function() {
    $('#yearMonth_sections_select').change(function() {
        let yearMonth = this.value;

        $.ajax({
            url: '/admin/payment_history/by_section',
            data: {yearMonth: yearMonth},
            success: function(response) {
                $('#monthly_payments_by_section').replaceWith(response);
            }
        });
    });

    $('#yearMonth_building_select').change(function() {
        let yearMonth = this.value;

        $.ajax({
            url: '/admin/payment_history/by_building',
            data: {yearMonth: yearMonth},
            success: function(response) {
                $('#monthly_payment_of_building').replaceWith(response);
            }
        });
    });
});
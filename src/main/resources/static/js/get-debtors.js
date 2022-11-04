$(document).ready(function() {
    $('#quantity_select').change(function() {
        let quantity = this.value;

        $.ajax({
            url: '/admin/cabinet/debtors',
            data: {quantity: quantity},
            success: function(response) {
                $('#debtors').replaceWith(response);
            }
        });
    });
});
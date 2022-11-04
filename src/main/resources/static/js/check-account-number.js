$(document).ready(function() {
    $("#account_number_btn").on("click", function() {
        let account_number = $("#account_number").val();

        if (account_number > 999999999) {
            alert("Shouldn't be grater than 9 digits");
        } else {
            $.ajax({
                url: '/signup/check_account_number',
                data: {accountNumber: account_number},
                success: function(response) {
                    $.each(response, function(key, value) {
                        if (key === 'true') {
                            $('#account_number').prop("disabled", true);

                            $('#apartment_id').val(value);

                            $('#email').prop("disabled", false);
                            $('#password').prop("disabled", false);
                            $('#confirm_password').prop("disabled", false);
                            $('#lastName').prop("disabled", false);
                            $('#firstName').prop("disabled", false);
                            $('#patronymic').prop("disabled", false);
                            $('#submit').prop("disabled", false);
                        } else {
                            alert(value);
                        }
                    });
                }
            });
        }
    });
});
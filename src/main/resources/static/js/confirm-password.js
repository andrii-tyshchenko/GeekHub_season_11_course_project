$(document).ready(function () {
    let password = $('#password')[0];
    let confirm_password = $('#confirm_password')[0];

    function validatePassword() {
        if(password.value !== confirm_password.value) {
            confirm_password.setCustomValidity("Passwords don't match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
});
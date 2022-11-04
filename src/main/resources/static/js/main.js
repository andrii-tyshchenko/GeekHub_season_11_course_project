$(document).ready(function() {
    let address = '/' + window.location.href.split("/").pop();

    $('a[href$="' + address + '"]').addClass("active");

    $('.scrollup').click(function() {
        $("html, body").animate({
            scrollTop: 0
        }, 1000);
    });

    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.scrollup').fadeIn();
        } else {
            $('.scrollup').fadeOut();
        }
    });
});
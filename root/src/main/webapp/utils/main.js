/**
 * Created by correnti on 16/11/2015.
 */
var startPossition = 900000000;
var pagesPossition = startPossition;
var stickyPossition = startPossition;


var Helper = Helper || {};
Helper.____closeTooTipCart;

Helper.forAllPage = function(){
    $("body").scrollTop(0);
    Helper.UpdatePageFixed();
}

Helper.UpdatePageFixed = function(){
    $('.pages').removeClass('fixed');
    $('.sticky').removeClass('fixed');
    pagesPossition = startPossition;
    stickyPossition = startPossition;
    Helper.checkPagePossitions()
};

Helper.closeToolTipCart = function (){

    var toolTipCart = $('#toolTipCart');
    if(toolTipCart.length > 0)
    {
        toolTipCart.stop().slideUp(function(){
            $('#toolTipCart tbody').animate({ scrollTop: 0, }, 500);
        });
    }
}

Helper.checkPagePossitions = function(){

    if ($('.pages').length > 0) {

        if (pagesPossition < $('body').scrollTop() + $('header').height()) {
            $('.pages').addClass('fixed');
        }
        else {
            pagesPossition = $('.pages').offset().top;
            $('.pages').removeClass('fixed');
        }
    }

    if ($('.sticky').length > 0) {
        if (stickyPossition < $('body').scrollTop()
            + 100 + ($('.pages').length > 0) ? $('.pages').height() : 0 ) {
            $('.sticky').addClass('fixed');
        }
        else {
            stickyPossition = $('.sticky').offset().top;
            $('.sticky').removeClass('fixed');
        }
    }
}

var Main = Main || {};

Main.addAnimPlaceholderEventListener = function(){

    $('.animPlaceholderUp input[type=text], .animPlaceholderUp input[type=password], .animPlaceholderUp .inputtext').focus(function(){
        //$(this).siblings().not("img").not('.validationInfo').animate({'top': '-10px'}, 800, $.bez([0.62,-0.14,0.35,1.34]));
        $(this).siblings().not("img").not('.validationInfo').animate({'top': '-10px'}, 800, $.bez([0.62,-0.14,0.35,1.34]));
        $(this).siblings(".validationInfo").fadeIn();
    });

    $('.animPlaceholderUp input[type=text], .animPlaceholderUp input[type=password], .animPlaceholderUp .inputtext').blur(function(){
        if($(this).val() == '') { $(this).siblings().not("img").not('.validationInfo').animate({'top': '11px'}, 800, $.bez([0.62,-0.14,0.35,1.34])); }
        $(this).siblings(".validationInfo").delay(200).fadeOut();
    });

    $(".animPlaceholderUp label").click(function() {
        $(this).parent().children().not("img").not('.validationInfo').animate({'top': '-10px'}, 800, $.bez([0.62,-0.14,0.35,1.34]));
        $(this).prev('input').focus();
        $(".animPlaceholderUp > .validationInfo").slideDown();
    });


//    $(".validationInfoImg").click(function() {
  //      $(this).parent().find(".validationInfo").fadeToggle(500);
    //});

}


$(document).on({

    ready: function() {



       /* // Mobile section handler
        var mobile_section_moved = $("#mobile-section").width();
        $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
        $(document).on("click", "#mobile-btn", function () {
            $("body").animate({
                left: $("body").css("left") != "0px" ? "0px" : mobile_section_moved
            }, 200);
        });
        // end Mobile section handler
*/



        $(document).on("click", ".containMiniTitle", function () {
            $(this).find(".mini-title").fadeToggle(300);
        });



        var test = 0;

        $(window).on({
        resize: _resize,
        scroll: function () {

            if ($(window).scrollTop() > 300) {
                $('#scrollToTop').fadeIn(300);
            }
            else {
                $('#scrollToTop').fadeOut(300);
            }

            Helper.checkPagePossitions();
            Helper.closeToolTipCart();
        }
        });


        _resize();
        function _resize() {

            $(".mini-title").css("display", "none");
            $("body").css("left", "0px")
        }

    },
});


/**
 * Created by correnti on 16/11/2015.
 */
var startPossition = 900000000;
var pagesPossition = startPossition;
var stickyPossition = startPossition;


var Helper = Helper || {};
Helper.____closeTooTipCart;

Helper.forAllPage = function(){
    Helper.scrollPageUp();
    Helper.UpdatePageFixed();
}

Helper.scrollPageUp = function(){
    $("body").scrollTop(0);
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

Helper.getRandom = function(length){
    var ranVal = '';
    for(var i = 0; i < length; i++){
        ranVal += (Math.floor(Math.random() * 9) + 1)
    }
    return ranVal
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
/*
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

*/
//    $(".validationInfoImg").click(function() {
  //      $(this).parent().find(".validationInfo").fadeToggle(500);
    //});

}


$(document).on({

    ready: function() {


        $(window).on({
        resize: _resize,
        scroll: _scroll,
        });

        function  _scroll(){
            Helper.checkPagePossitions();
        }

        _resize();
        function _resize() {

            $(".mini-title").css("display", "none");
            $("body").css("left", "0px")
        }

    },
});


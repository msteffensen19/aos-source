/**
 * Created by correnti on 16/11/2015.
 */
var startPossition = 900000000;
var pagesPossition = startPossition;
var stickyPossition = startPossition;


var Helper = Helper || {};
Helper.____closeTooTipCart;
Helper.mobile_section_moved;

Helper.forAllPage = function(){
    Helper.scrollPageUp();
    Helper.UpdatePageFixed();
    setTimeout(Helper.footerHandler(), 1000);
}

Helper.footerHandler = function() {

    //if ($("footer").height()) {

        //$("#virtualFooter").height($(window).height() - $("#virtualFooter").offset().top + 80);
        //var miss = $(window).height() - $("#virtualFooter").offset().top;
        //
        //l($(window).height());
        //l($("#virtualFooter").offset().top);
        //
        //console.log("miss = " + miss);
        //$("#virtualFooter").css("box-shadow", "0 0 0 10px red inset")
        //
        //if (miss > 0) {
        //    $("footer").css({ "position": "fixed", 'background-color': '#FF0000' } )
        //    $("#virtualFooter").height(
        //        $("footer").height() + parseInt($("footer").css('margin-top').replace("px", "")));
        //}
        //else {
        //    $("footer").css("position", "static")
        //    $("footer").css({ 'background-color': '#d8d8d8' } )
        //}

    //}
    //else {
        //setTimeout(Helper.footerHandler, 200)
    //}
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

Helper.mobileSectionHandler = function(){

    //$("body").animate({
    //    left: $("body").css("left") != "0px" ? "0px" : Helper.mobile_section_moved
    //}, 200);
    $("#mobile-section").animate({
        left: $("#mobile-section").css("left") != "0px" ? "0px" : "-" + Helper.mobile_section_moved
    }, 200);
}

Helper.mobileSectionClose = function(){
    //$("body").stop().animate({
    //    left: "0px",
    //}, 200);
    $("#mobile-section").stop().animate({
        left: "-" + Helper.mobile_section_moved
    }, 200);
}


var Main = Main || {};

Main.addAnimPlaceholderEventListener = function(){
}


$(document).ready(function() {

    $(window).on({
    resize: _resize,
    scroll: _scroll,
    });

    function  _scroll(){
        Helper.checkPagePossitions();
        _resize();
    }

    _resize();
    function _resize() {

        $(".mini-title").css("display", "none");
        Helper.mobileSectionClose();
    }
});


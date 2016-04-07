/**
 * Created by correnti on 16/11/2015.
 */
var startPossition = 900000000;
var pagesPossition = startPossition;
var stickyPossition = startPossition;


var Helper = Helper || {};
Helper.____closeTooTipCart;
Helper.____showPage;
Helper.mobile_section_moved;

Helper.defaultTimeLoaderToEnable = 200;
Helper.enableLoader = function() {
    $("div.loader").css({display: "block"});
    $("div.loader").stop().animate({opacity: 1}, 300);
};

Helper.disableLoader = function() {
    $("div.loader").stop().animate({opacity: 0}, 300, function(){
        $(this).css({display: "none"});
    });
};

Helper.forAllPage = function(){

    clearTimeout(Helper.____showPage)
    Helper.____showPage = setTimeout(function () {
        $(".waitBackground").stop().animate({opacity: 0,}, 100, function(){
            $(this).css({display: "none",  });
            $("div.loader").stop().animate({opacity: 0}, 700, function(){
                $(this).css({display: "none",  });
            });
        });
    }, 1000);
    Helper.scrollPageUp();
    Helper.UpdatePageFixed();
    setTimeout(Helper.footerHandler, 200);

}

Helper.footerHandler = function() {

    if ($("footer").height()) {

        setTimeout(function(){
            var paddingTop = $(window).width() > 480 ? 90 : 0;
            $("#virtualFooter").height($("footer").height() + paddingTop);
            $("#virtualFooter").css('width' , "100%" );

            var miss = $(window).height() - ($("#virtualFooter").offset().top + $("#virtualFooter").height());
            $("footer").css({ "position": miss > 0 ? "fixed" : "absolute" } )
        }, 200)
    }
    else {
        setTimeout(Helper.footerHandler, 200)
    }
}

Helper.scrollPageUp = function(){
    $("body, html").scrollTop(0);
}

Helper.parseBoolean = function(str){
    return str.toLowerCase() == 'true'
}


Helper.UpdatePageFixed = function(){
    if(document.URL.indexOf("category") == -1){
        $('.pages').removeClass('fixed');
        $('.sticky').removeClass('fixed');
    }
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
    return ranVal;
}

Helper.checkPagePossitions = function(){

    if ($('.pages').length > 0) {

        var scrollTop = $('body').scrollTop() > 0 ? $('body').scrollTop() : $('html').scrollTop();

        if (pagesPossition < scrollTop + $('header').height()) {
            $('.pages').addClass('fixed');
        }
        else {
            pagesPossition = $('.pages').offset().top;
            $('.pages').removeClass('fixed');
        }
    }

    if ($('.sticky').length > 0) {
        if (stickyPossition < scrollTop
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

    if($("#mobile-section").css("left") == "0px"){
        $(".mobileTitle .mini-title").fadeOut();
    }
    $("#mobile-section").animate({
        left: $("#mobile-section").css("left") != "0px" ? "0px" : "-" + Helper.mobile_section_moved
    }, 200, function(){
        $(".mobileTitle").css('left', $(this).width());
    });
}

Helper.mobileSectionClose = function(){

    $("#mobile-section").stop().animate({
        left: "-" + Helper.mobile_section_moved
    }, 200);
    $("#loginMobileMiniTitle").fadeOut();
}

Helper.sortArrayByAbc = function(arr){
    return arr.sort(function (a, b) {
        return a == b ? 0 : a < b ? -1 : 1;
    });
}

Helper.sortAttributesByName = function(attrs){
    return attrs.sort(function (a, b) {
        return a.attributeName == b.attributeName ? 0 : a.attributeName < b.attributeName ? -1 : 1;
    });
}

Helper.sortArrayByColorName = function(colors){
    return colors.sort(function (a, b) {
        return a.name == b.name ? 0 : a.name < b.name ? -1 : 1;
    });
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


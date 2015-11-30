/**
 * Created by correnti on 16/11/2015.
 */

var Main = Main || {};

Main.miniItemPopUp = function(){

    $(".containMiniTitle").click(function(){
        $(this).find(".mini-title").fadeToggle(300);
    });
}

Main.productHover = function(){

}

Main.addAccordionEventListener = function(){


}



$(document).on({

    ready: function() {


        // Mobile section handler
        var mobile_section_moved = $("#mobile-section").width();
        $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
        $("#mobile-btn").click(function () {
            $("body").animate({
                left: $("body").css("left") != "0px" ? "0px" : mobile_section_moved
            }, 200);
        });
        // end Mobile section handler




        $('#scrollToTop').on({
            click: function () {
                $('body, html').animate({ scrollTop: 0 }, 1000);
                $(this).find("p").animate({
                    bottom: -100,
                }, 1000);
            },
            mouseover: function () {
                $(this).find("p").animate({
                    bottom: 0
                }, 400);
            },

        });


        $(window).on({
            resize: _resize,
            scroll: function () {
               if ($(window).scrollTop() > 300) {
                    $('#scrollToTop').fadeIn(300);
                    return;
                }
                $('#scrollToTop').fadeOut(300);
            }
        });




        $('#product_search_img').click(function (e) {
            $('#product_search').css("display", "inline-block");
            $('#product_search').animate({ "width": $('#product_search').width() > 0 ? 0 : "150px" },
                500, function(){
                    if($('#product_search').width() == 0 ){
                        $(this).css("display", "none");
                    }
            } );
        });


        $(document).on("mouseover", ".categoryRight ul li", function(){
            $(this).siblings().stop().animate({ "opacity" : "0.3", }, 500);
        });

        $(document).on("mouseout", ".categoryRight ul li", function(){
            $(this).siblings().stop().animate({ "opacity" : "1", }, 1000);
        });

        $(document).on("click", ".accordion", function() {
            $(this).toggleClass('arrowUp');
            $(this).parent().find(".option").slideToggle(300);
        });

        $(document).on("click", ".productColor ", function() {
            $(this).toggleClass('selected');
        });

        _resize();
        function _resize() {
            $(".mini-title").css("display", "none");
            $("body").css("left", "0px")
            $("#mobile-section").height($(window).height() + "px");
        }

    },
});


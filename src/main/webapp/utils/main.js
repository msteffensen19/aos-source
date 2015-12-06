/**
 * Created by correnti on 16/11/2015.
 */

var Main = Main || {};
Main.miniItemPopUp = function(){ }
Main.productHover = function(){ }
Main.addAccordionEventListener = function(){ }



Main.addAnimPlaceholderEventListener = function(){

    $('.animPlaceholderUp input[type=text], .animPlaceholderUp input[type=password], .animPlaceholderUp .inputtext').focus(function(){
        $(this).siblings().animate({'top': '-10px'}, 800, $.bez([0.62,-0.14,0.35,1.34]));
    })

    $('.animPlaceholderUp input[type=text], .animPlaceholderUp input[type=password], .animPlaceholderUp .inputtext').blur(function(){
        if($(this).val() == '') { $(this).siblings().animate({'top': '11px'}, 800, $.bez([0.62,-0.14,0.35,1.34])); }
    });

    $(".animPlaceholderUp label").click(function() {
        $(this).parent().find("label").animate({'top': '-10px'}, 800, $.bez([0.62,-0.14,0.35,1.34]));
        console.log($(this).parent().find("label, a"))
        $(this).prev('input').focus();
    });
}



$(document).on({

    ready: function() {




        // Mobile section handler
        var mobile_section_moved = $("#mobile-section").width();
        $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
        $(document).on("click", "#mobile-btn", function () {
            $("body").animate({
                left: $("body").css("left") != "0px" ? "0px" : mobile_section_moved
            }, 200);
        });
        // end Mobile section handler


        $(document).on("click", ".containMiniTitle", function () {
            $(this).find(".mini-title").fadeToggle(300);
        });


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


//        $(document).on("mouseover", ".categoryRight ul li", function(){
  //          $(this).siblings().stop().animate({ "opacity" : "0.3", }, 500);
    //    });

//        $(document).on("mouseout", ".categoryRight ul li", function(){
  //          $(this).siblings().stop().animate({ "opacity" : "1", }, 1000);
    //    });

        $(document).on("click", ".accordion", function() {
            $(this).toggleClass('arrowUp');
            $(this).parent().find(".option").slideToggle(300);
        });

        $(document).on("click", ".productColor ", function() {
            $(this).toggleClass('colorSelected');
        });


        _resize();
        function _resize() {
            $(".mini-title").css("display", "none");
            $("body").css("left", "0px")
        }

    },
});


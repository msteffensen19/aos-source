/**
 * Created by correnti on 17/11/2015.
 */


var ____moveSlider;
$(document).on({

    ready: function() {

        ____moveSlider = setInterval(moveSlider, 9910000)

        $(window).on({ resize: _resize });


        $(document).on("click", ".slider-steps > span", function(a){
            clearInterval(____moveSlider)
            $(".slider-steps span").removeClass("selected");
            $(this).addClass("selected");
            $(".slider-length").stop().animate({
                "right" : ($(this).index()) * parseInt($(window).width()) + "px"
            }, 1000, function(){
                ____moveSlider = setInterval(moveSlider, 10000)
            });
        });


        function _resize() {
            $(".slider-length").css("right", "0px");
            var spans = $(".slider-steps span");
            $(spans).removeClass("selected");
            $(spans[0]).addClass("selected");
        }

        function moveSlider()
        {
            try{
                if($(".slider-length").length == 0){ clearInterval(____moveSlider);return; }

                var _windowsLength = $(window).width();
                var int_right = parseInt($(".slider-length").css("right").replace("px", ""));
                var spans = $(".slider-steps span");

                var index =  int_right == 0 ? 0 : (int_right / _windowsLength) == spans.length - 1 ? -1
                    : int_right / _windowsLength;
                $(spans).removeClass("selected");
                $(spans[index + 1]).addClass("selected");


                $(".slider-length").stop().animate({ "right": (int_right) + parseInt(_windowsLength) + "px"}, 1000 , function() {
                    if($(".slider-length").length == 0){ clearInterval(____moveSlider);return; }

                    var _length = $(".slider-length").width();
                    var _right = $(".slider-length").css("right");
                    var moveTo = (parseInt(_right.replace("px", "")) + (parseInt(_windowsLength) *2));
                    if (_length < moveTo) {
                        $(".slider-length").css("right", "0px");
                    }
                });

            }
            catch(e){
                clearInterval(____moveSlider);
            }
        }



    },

});

















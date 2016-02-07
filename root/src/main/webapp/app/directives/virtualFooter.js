/**
 * Created by correnti on 04/02/2016.
 */


define(['./module'], function (directives) {
    'use strict';
    directives.directive('virtualFooter', function () {
            return {
                link: function(s, e) {
                    e.css({
                        display:'block',
                        position: 'relative',
                    });

                    e.attr("id", "virtualFooter")
                    $(document).ready(function(){
                        $(window).resize(resize);
                        function resize(){
                            if($("footer").height()){;
                                $("#virtualFooter").height($("footer").height() +
                                    parseInt($("footer").css('margin-top').replace("px", "")));
                            }
                            else {
                                setTimeout(resize, 200)
                            }
                        }
                        resize();
                    })
                }
            };
        });
});


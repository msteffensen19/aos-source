/**
 * Created by correnti on 19/01/2016.
 */



define(['./module'], function (directives) {
    'use strict';
    directives.directive('iconPhone', function(){
        return{
            restrict: 'A',
            replace: false,
            template: "<div><div class='iconPhoneTop'></div><div class='iconPhoneBottom'></div></div>",
            link: function(s, e, a, ctrl){
                e.addClass("iconCss iconPhone");
            }
        }
    });
});


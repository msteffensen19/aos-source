/**
 * Created by correnti on 03/06/2016.
 */




define(['./module'], function (directives) {
    'use strict';
    directives.directive('infoDemo', function () {
        return {
            replace: true,
            restrict: 'E',
            link: function (s, e, a, ctrl) {

                e.addClass('infoDemo');
                e.css({
                    display: "block",
                    padding: "22px 23px",
                    margin: "34px 75px 0 0",
                    position: "relative",
                    border: "solid 1px #454545"
                });

                var divInner = $("<div></div>");
                divInner.css({
                    "display": "block",
                    "padding": "0px 19px",
                    "position": "relative",
                    "border-left": "solid 2px #454545",
                });

                var h4 = $("<h4>" + a.aTitle + "</h4>");
                h4.css({
                    "background-image": "url(/css/images/Bell.png)",
                    "background-repeat": "no-repeat",
                    "background-position": "0 50%",
                    "font-size": "20px",
                    "font-weight": "500",
                    "text-indent": "23px",
                    "padding": "3px",
                    "top":"-3px",
                    "position": "relative",
                });

                divInner.append(h4);

                var paragraphes = JSON.parse(a.aLines);
                for (var i = 0; i < paragraphes.length; i++) {
                    var p = $("<p class='roboto-light'>" + paragraphes[i] + "</p>");
                    p.css({
                        "font-size": "14px",
                        "font-weight": "300",
                        "margin-top":  i == 0 ? "4px" : "0",
                        "color": "#828282",
                    });
                    divInner.append(p);
                }

                e.append(divInner)
            }
        }
    });
});

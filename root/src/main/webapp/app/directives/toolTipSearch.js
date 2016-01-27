/**
 * Created by correnti on 07/12/2015.
 */

define(['./module'], function (directives) {
    'use strict';
    directives.directive('toolTipSearch', ['$templateCache', 'productService', '$location',
        function ($templateCache, productService, $location) {
        return {
            restrict: 'E',
            replace: true,
            template: $templateCache.get('app/partials/toolTipSearch.html'),
            link: function (s){

                s.categoryFilter = null;
                s.categoryName = '';
                s.allowClosing = true;
                s.checkEnterKey = function(event)
                {
                    if(event.which === 13) {
                        console.log(event)
                        console.log(event.which)
                    }
                }

                var lastRequest = '';
                s.runAutocomplete = function(){
                    lastRequest = s.autoCompleteValue;
                    if(lastRequest == '') {
                        s.autoCompleteResult = {};
                        return;
                    }
                    productService.getProductsBySearch(lastRequest, 10).then(function(result){
                        s.autoCompleteResult = result;
                    });
                }


                s.openSearchProducts = function(){

                    var navsLinks = $("nav ul li a.navLinks");
                    navsLinks.each(function(index){
                        setTimeout(function(_this){
                            $(_this).stop().animate({ opacity : 0 }, 100);
                        }, 100 * index, this);
                    });

                    setTimeout(function(navsLinks){ navsLinks.hide();
                    }, 200 * navsLinks.length, navsLinks);

                    setTimeout(function(){
                        var width = $(document).width() > 1200 ? 780 :
                            $(document).width() > 1000 ? 630 : 400;
                        $('#searchSection .autoCompleteCover').stop().animate({
                            width : width + "px",
                            margin : '0 6px'
                        }, 500).animate({
                            opacity: 1
                        }, 200, function(){
                            $('#autoComplete').focus();
                        });
                        $('#searchSection .autoCompleteCover .iconCss.iconX').fadeIn(300);
                    }, 400);

                }

                s.closeSearchSection = function(force){

                    if(!s.allowClosing && !force){
                        return;
                    }

                    $('#searchSection .autoCompleteCover .iconCss.iconX').fadeOut(300);

                    setTimeout(function(){
                        $('#searchSection .autoCompleteCover')
                            .animate({
                            width : 0 + "px",
                            opacity: 0.5,
                            margin : '0 0'
                        }, 500);
                    }, 400, function(){
                        var navsLinks = $("nav ul li a.navLinks");
                        navsLinks.show();
                        navsLinks.each(function(index){
                            setTimeout(function(_this){
                                $(_this).stop().animate({ opacity : 1 }, 100);
                            }, 100 * index, this);
                        });
                    });

                    s.autoCompleteValue = lastRequest = '';
                    s.autoCompleteResult = {};
                }

                s.allowClosingLeave = function(){
                    $('#autoComplete').focus();
                    s.allowClosing = true;

                }

                $(document).ready(function(){

                    $(window).resize(function(){

                        l($("#searchSection").width());
                        if($("#searchSection").width() > 150){
                            var width = $(document).width() > 1200 ? 780 :
                                    $(document).width() > 1000 ? 630 : 400;
                            $('#searchSection .autoCompleteCover').width(width);
                        }
                    })

                })

                $('#product_search_img').click(function (e) {
                    $('#product_search').css("display", "inline-block");
                    $('#product_search').animate({ "width": $('#product_search').width() > 0 ? 0 : "150px" },
                        500, function(){
                            if($('#product_search').width() == 0 ){
                                $(this).css("display", "none");
                            }
                        } );
                });

                s.searchByCategoryId = function(id, categoryName){
                    console.log(id);
                    s.categoryFilter = id;
                    s.categoryName = categoryName;
                }

            }
        };
    }]);
});


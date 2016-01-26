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
                    $("nav ul li a.navLinks").stop().animate({ opacity : 0 }, 400, function(){
                        $(this).hide();
                    });
                    setTimeout(function(){
                        $('#openSearch').hide();
                        $("#searchSection").css('display', 'block');//.fadeIn(200);
                        $("#autoComplete").focus();
                        $("div#search").animate({
                            left: 0,
                            opacity: 1
                        }, 700);
                        //$("#searchSection div#search").addClass("searchSectionEnabled");
                        $("#searchSection > div > div > span > .img").delay(700).fadeIn(500); // img close
                    }, 400);
                }

                s.closeSearchSection = function(){
                    $("#searchSection").fadeOut(200);
                    $("div#search").animate({
                        left: '920px',
                        opacity: 0
                    }, 700, function(){
                        //$("#searchSection div#search").addClass("searchSectionEnabled");
                        $('#openSearch').show();
                        $("#searchSection > div > div > span > .img").delay(500).fadeOut(500);
                        setTimeout(function(){
                            $("nav ul li a.navLinks").stop().animate({ opacity : 1 }, 400, function(){
                                $(this).show();
                            });
                        }, 400);
                        s.autoCompleteValue = lastRequest = '';
                        s.autoCompleteResult = {};
                    });

                }

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


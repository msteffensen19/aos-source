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
                        console.log(event)
                    });
                }


                s.openSearchProducts = function(){
                    $("nav ul li a.navLinks").stop().animate({ opacity : 0 }, 400);
                    setTimeout(function(_this){
                        $("#searchSection").fadeIn(1000);
                        $("#autoComplete").focus();
                        $("#searchSection > div:first-child > div").addClass("searchSectionEnabled");
                        $("#searchSection > div > div > span > img").delay(500).fadeIn(500); // img close
                        $('#openSearch').stop().animate({ opacity : 0 }, 300)
                    }, 400);
                }

                s.closeSearchSection = function(){
                    $('#openSearch').stop().animate({ opacity : 1 }, 300)
                    $("#searchSection > div > div > span > img").fadeOut(200); // img close
                    setTimeout(function(){
                        $("#searchSection").fadeOut(500);
                        $("#searchSection > div:first-child > div").removeClass("searchSectionEnabled");
                        if($location.$$path == '/')
                        {
                            $("nav ul li a.navLinks").stop().animate({ opacity : 1 }, 400);
                        }
                        s.autoCompleteValue = lastRequest = '';
                        s.autoCompleteResult = {};
                        $("#autoComplete").focusout();
                    }, 200)
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


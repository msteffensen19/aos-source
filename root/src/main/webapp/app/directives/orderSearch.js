/**
 * Created by correnti on 07/12/2015.
 */

define(['./module'], function (directives) {
    'use strict';

    directives.directive('ordersSearch', ['$templateCache', 'productService', '$location', '$state',
        function ($templateCache, productService, $location, $state) {
            return {
                restrict: 'E',
                template: $templateCache.get('app/partials/orderSearch.html'),
                controller: ['$scope', function (s) {

                    s.categoryFilter = null;
                    s.categoryName = '';
                    s.allowClosing = true;
                    s.orders = null;
                    s.firstSearchInitiation = true;

                    var lastRequest = '';
                    s.runAutocomplete = function () {
                        lastRequest = s.autoCompleteValue;
                        if (lastRequest == '') {
                            s.autoCompleteResult = {};
                            return;
                        }
                        s.searchOrder(lastRequest);
                    }

                    s.searchOrder = function(word){
                        if((s.myOrdersCtrl.orders.length < 1 && s.orders.length < 1) || word.length < 1) return;
                        if(s.firstSearchInitiation){
                            s.firstSearchInitiation = false;
                            s.orders = angular.copy(s.myOrdersCtrl.orders);
                        }

                        s.myOrdersCtrl.orders = s.orders.filter(function(d){
                            if(d.orderNumber.indexOf(word) != -1)
                                return true;
                            if(d.products.filter(function(dd){if(dd.ProductName.toLowerCase().indexOf(word.toLowerCase()) != -1)return true;}).length > 0)
                                return true;
                        });

                    }

                    s.openSearchOrders = function () {
                        var orders = s.orders;
                        var navsLinks = $("nav ul li a.navLinks");
                        navsLinks.each(function (index) {
                            setTimeout(function (_this) {
                                $(_this).stop().animate({opacity: 0.1}, 500);
                            }, 200 * index, this);
                        });

                        setTimeout(function (navsLinks) {
                            navsLinks.hide();
                            $('#orderSearchSection .autoCompleteCover').stop().animate({
                                width: getAutocompleateWidth() + "px",
                                margin: '0 6px'
                            }, 500).animate({
                                opacity: 1
                            }, 200, function () {
                                $('#orderAutoComplete').focus();
                            });
                            $('#orderSearchSection .autoCompleteCover .iconCss.iconX').fadeIn(300);
                        }, (200 * navsLinks.length), navsLinks);
                    }

                    s.closeSearchForce = function () {
                        $('#orderSearchSection .autoCompleteCover .iconCss.iconX').fadeOut(300);
                        setTimeout(function () {
                            $('#orderSearchSection .autoCompleteCover')
                                .animate({
                                    width: 0 + "px",
                                    opacity: 0.5,
                                    margin: '0 0'
                                }, 500, function () {
                                    if ($location.path() == '/') {
                                        var navsLinks = $("nav ul li a.navLinks");
                                        navsLinks.show();
                                        navsLinks.each(function (index) {
                                            setTimeout(function (_this) {
                                                $(_this).stop().animate({opacity: 1}, 100);
                                            }, 100 * index, this);
                                        });
                                    }
                                });
                        }, 400);

                        s.autoCompleteValue = lastRequest = '';
                        s.autoCompleteResult = {};
                        if(s.orders != null){
                            s.myOrdersCtrl.orders = angular.copy(s.orders);
                            s.orders = null;
                        }

                        s.firstSearchInitiation = true;
                    }

                    s.closeSearchSection = function () {
                        if (!s.allowClosing || (s.autoCompleteValue != null && s.autoCompleteValue.length > 0)/*|| $location.path().indexOf('/search') != -1*/) {
                            return;
                        }
                        s.closeSearchForce();
                    }

                    s.allowClosingLeave = function () {
                        $('#orderAutoComplete').focus();
                        s.allowClosing = true;
                    }

                    $(document).ready(function () {
                        $(window).resize(function () {
                            if ($("#orderSearchSection").width() > 150) {
                                $('#orderSearchSection .autoCompleteCover').width(getAutocompleateWidth());
                            }
                        });
                    });

                    function getAutocompleateWidth() {
                        return ($(document).width() > 1000 ? 680 :
                                $(document).width() > 1000 ? 530 : 300) -
                            ($(".hi-user").width() * 1.5);
                    }

                    $('#product_search_img').click(function (e) {
                        $('#product_search').css("display", "inline-block");
                        $('#product_search').animate({"width": $('#product_search').width() > 0 ? 0 : "150px"},
                            500, function () {
                                if ($('#product_search').width() == 0) {
                                    $(this).css("display", "none");
                                }
                            });
                    });

                    s.searchByCategoryId = function (id, categoryName) {
                        console.log(id);
                        s.categoryFilter = id;
                        s.categoryName = categoryName;
                    }

                }]
            };
        }]);
});


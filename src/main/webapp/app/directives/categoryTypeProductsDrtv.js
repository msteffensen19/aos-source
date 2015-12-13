/**
 * Created by kubany on 10/29/2015.
 */
/**
 * Created by kubany on 10/14/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('categoryTypeProductsDrtv', ['$filter', '$animate', '$templateCache',
        '$location', 'productsCartService', function ($filter, $animate, $templateCache, $location, cartService) {
        return {
            restrict: 'EA',
            replace: 'true',
            template: $templateCache.get('app/partials/category_type_products_tpl.html'),
            scope: {
                category: '='
            },
            link: function (scope, element, attrs) {

                scope.productsInclude = [];
                scope.attributeChecked = [];

                scope.gotoItem = function(id){
                    $location.path('/product/' + id);
                }

                scope.includeProducts = function($event, attributeVal, attributesName) {

                    var i = $.inArray(attributeVal, scope.productsInclude[attributesName]);
                    if (i > -1) {
                        scope.productsInclude[attributesName].splice(i, 1);
                        if(scope.productsInclude[attributesName].length == 0)
                            delete scope.productsInclude[attributesName];
                    } else {
                        if(scope.productsInclude[attributesName] != undefined)
                            scope.productsInclude[attributesName].push(attributeVal);
                        else
                        {
                            scope.productsInclude[attributesName] = [];
                            scope.productsInclude[attributesName].push(attributeVal);
                        }
                    }
                }

                scope.productsFilter = function(product) {

                    if (Object.keys(scope.productsInclude).length > 0) {
                        var found = 0;
                        for (var key in scope.productsInclude){
                            for(var i = 0; i < scope.productsInclude[key].length; i++)
                                if($.inArray(JSON.stringify($filter('filter')(product.attributes,
                                        {attributeValue: scope.productsInclude[key][i]},
                                        false)[0]),
                                        $.map(product.attributes, JSON.stringify)) > -1)
                                    found++;
                        }
                        if(found == Object.keys(scope.productsInclude).length)
                            return product;
                    }
                    else
                        return product;
                };

                var lastIdAdded = '';
                scope.addProduct = function(product) {
                   $('#toolTipCart').slideDown(function(){
                        cartService.addProduct(product, 1).then(function(result){
                            clearInterval(Helper.____closeTooTipCart);
                            if (lastIdAdded == ('#product' + product.productId))
                            {
                                setToolTipCartSlideUp()
                            }
                            else {
                                lastIdAdded = '#product' + product.productId;
                                $('#toolTipCart tbody').stop().animate({
                                    scrollTop: 0 + 'px',
                                }, 500, function () {
                                    var productId = $('#product' + product.productId);
                                    var top = productId.length > 0 ? ($(productId).offset().top) - ($('#toolTipCart').offset().top)
                                        : $('.lastProduct').offset().top;
                                    $('#toolTipCart tbody').stop().animate({
                                        scrollTop: (top) + 'px',
                                    }, 500);
                                    Helper.____closeTooTipCart = setTimeout(function () {
                                        $('#toolTipCart').stop().delay(700).slideUp();
                                    }, 2000)
                                });
                            }
                        });
                    });
                };

                function setToolTipCartSlideUp() {
                    Helper.____closeTooTipCart = setTimeout(function(){
                        $('#toolTipCart').stop().delay(700).slideUp();
                    }, 2000)
                }


                scope.clearSelection = function(){
                    for (var key in scope.productsInclude) {
                        delete scope.productsInclude[key];
                    }
                    $('.option input[type=checkbox]').each(function(){
                        this.checked = false;
                    })
                    $('.option .productColor').removeClass('colorSelected');
                };


                scope.manipulateProductsByCustomization = function() {
                    scope.businessCustom = [];
                    scope.gamingCustom = [];
                    scope.simplicityCustom = [];
                    angular.forEach(scope.products, function (value, key) {
                        if($filter('filter')(value.attributes, {attributeValue: 'Business'}, false).length > 0)
                            scope.businessCustom.push(value);
                        if($filter('filter')(value.attributes, {attributeValue: 'Gaming'}, false).length > 0)
                            scope.gamingCustom.push(value);
                        if($filter('filter')(value.attributes, {attributeValue: 'Simplicity'}, false).length > 0)
                            scope.simplicityCustom.push(value);

                    });
                };

                scope.$watch('category', function(catData) {
                    if(catData){
                        scope.category = catData;
                        scope.products = catData.products;
                        //scope.productsInclude = catData.products;
                        scope.categoryAttributes = catData.attributes;
                        //console.log("scope.categoryAttributes")
                        //console.log(scope.categoryAttributes)
                        $('.panel-collapse').collapse({toggle: false});
                        $('.carousel').carousel({
                            interval: false
                        })
                        scope.manipulateProductsByCustomization();

                    }

                })



                scope.getStyle = function(index){
                    //var delay = (index * 500);
                    //return {"-webkit-transition-delay": + delay  + "ms;transition-delay ": + delay + 'ms;color:red;"};
                };
                //console.log($animate);
                //scope.productsFilter = function(product) {
                //    //if (scope.productsInclude.length > 0) {
                //    //    if ($.inArray(JSON.stringify($filter('filter')(product.attributes, {attributeValue: attributeVal}, false)[0]),
                //    //            $.map(product.attributes, JSON.stringify) ) < 0)
                //    //        return;
                //    //}
                //    return product;
                //}

            }
        };
    }]);
});
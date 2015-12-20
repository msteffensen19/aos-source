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

                scope.showClear = false;
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

                    scope.showClear = Object.keys(scope.productsInclude).length > 0;
                }

                scope.productsFilter = function(product) {

                    if (Object.keys(scope.productsInclude).length > 0)
                    {
                        var found = 0;
                        for (var key in scope.productsInclude)
                        {
                            for(var i = 0; i < scope.productsInclude[key].length; i++)
                            {
                                if($.inArray(JSON.stringify($filter('filter')(product.attributes,
                                        {attributeValue: scope.productsInclude[key][i]},
                                        false)[0]), $.map(product.attributes, JSON.stringify)) > -1)
                                {
                                    found++;
                                }
                                angular.forEach(product.colors, function(color){
                                    if(color.code == scope.productsInclude[key][i])
                                    {
                                        found++;
                                    }
                                });
                            }
                        }
                        if(found == Object.keys(scope.productsInclude).length)
                        {
                            return product;
                        }
                    }
                    else
                    {
                        return product;
                    }
                };


                scope.clearSelection = function(){
                    for (var key in scope.productsInclude) {
                        delete scope.productsInclude[key];
                    }
                    $('.option input[type=checkbox]').each(function(){
                        this.checked = false;
                    })
                    $('.option .productColor').removeClass('colorSelected');
                    scope.showClear = false;

                };

                scope.toggleColapse = function(id){
                    console.log(id)
                    $('#' + id).siblings('.option').slideToggle(300);
                    $('#' + id).toggleClass('arrowUp');
                }

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
                        scope.categoryAttributes = catData.attributes;
                        scope.manipulateProductsByCustomization();

                        console.log(scope.category);
                        console.log(scope.products);
                        console.log(scope.categoryAttributes);
                    }
                })


            }
        };
    }]);
});
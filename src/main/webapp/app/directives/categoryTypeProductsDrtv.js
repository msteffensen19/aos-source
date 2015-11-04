/**
 * Created by kubany on 10/29/2015.
 */
/**
 * Created by kubany on 10/14/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('categoryTypeProductsDrtv', ['$filter', '$animate', function ($filter, $animate) {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: './app/partials/category_type_products_tpl.html',
            scope: {
                category: '='
            },
            link: function (scope, element, attrs) {
                scope.getStyle = function(index){
                    //var delay = (index * 500);
                    //return {"-webkit-transition-delay": + delay  + "ms;transition-delay ": + delay + 'ms;color:red;"};


                };
                console.log($animate);
                scope.productsInclude = [];
                scope.attributeChecked = [];


                scope.productsFilter = function(product) {
                    //if (scope.productsInclude.length > 0) {
                    //    if ($.inArray(JSON.stringify($filter('filter')(product.attributes, {attributeValue: attributeVal}, false)[0]),
                    //            $.map(product.attributes, JSON.stringify) ) < 0)
                    //        return;
                    //}

                    return product;
                }

                scope.includeProducts = function($event, attributeVal) {

                    var i = $.inArray(attributeVal, scope.productsInclude);
                    if (i > -1) {
                        scope.productsInclude.splice(i, 1);
                    } else {
                        scope.productsInclude.push(attributeVal);
                    }

                }

                scope.productsFilter = function(product) {
                    if (scope.productsInclude.length > 0) {
                        var found = false;
                        for(var i = 0; i < scope.productsInclude.length;i++){
                            if($.inArray(JSON.stringify($filter('filter')(product.attributes, {attributeValue: scope.productsInclude[i]}, false)[0]),
                                    $.map(product.attributes, JSON.stringify)) > 0)
                                return product;
                        }
                    }
                    else
                        return product;

                };

                scope.clearSelection = function(){
                    scope.productsInclude = [];
                    $('.products-attribute').each(function(){
                        this.checked = false;
                    })
                };

                scope.toggleColapse = function(id){
                     if(id){
                         $('#' + id).collapse('toggle');
                         $('#panel-heading-a' + id).hasClass('collapsed') ? $('#panel-heading-a' + id).removeClass('collapsed') : $('#panel-heading-a' + id).addClass('collapsed')
                     }
                };
                //scope.filteredProducts = function(attributeValue){
                //
                //};
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
                        $('.panel-collapse').collapse({toggle: false});
                        $('.carousel').carousel({
                            interval: false
                        })
                        scope.manipulateProductsByCustomization();

                    }

                })
            }
        };
    }]);
});
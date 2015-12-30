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

                var slider;
                scope.showClear = false;
                scope.productsInclude = [];
                scope.attributeChecked = [];
                scope.productsColors = [];
                scope.minPriceToFilter = 0;
                scope.maxPriceToFilter = 0;

                scope.options = {
                    start: [20, 70],
                    range: {min: 0, max: 100}
                }

                scope.gotoItem = function(id){
                    $location.path('/product/' + id);
                }

                scope.toggleColorSelectedClass = function(code) {
                    $("#productsColors" + code).toggleClass('colorSelected');
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


                    scope.showClear =
                        Object.keys(scope.productsInclude).length > 0 ||
                        slider.noUiSlider.start != scope.minPriceToFilter ||
                        slider.noUiSlider.end != scope.maxPriceToFilter;
                }

                scope.clearSelection = function(){

                    for (var key in scope.productsInclude) {
                        delete scope.productsInclude[key];
                    }

                    $('.option input[type=checkbox]').each(function(){
                        this.checked = false;
                    })

                    $('.option .productColor').removeClass('colorSelected');

                    slider.noUiSlider.set([slider.noUiSlider.start, slider.noUiSlider.end]);

                    scope.showClear = false;

                };

                scope.toggleColapse = function(id){
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
                        scope.productsColors = getColorsInProducts(scope.products);
                        setMinAndMaxPriceToFilter(scope.products);
                        configSlider();
                        scope.manipulateProductsByCustomization();
                        scope.productsColors = getColorsInProducts(scope.products);
                    }
                })

                function setMinAndMaxPriceToFilter(products){
                    var maxVal;
                    var minVal = 900000;
                    angular.forEach(products, function(product){
                        if(product.price < minVal){
                            minVal = product.price;
                            if(!maxVal)
                            { maxVal = product.price }
                        }
                        else if(product.price > maxVal){
                            maxVal = product.price;
                        }
                    });

                    scope.minPriceToFilter = Math.round(minVal) - 1 ;
                    scope.maxPriceToFilter = Math.round(maxVal);
                }

                function getColorsInProducts(products){
                    var productsColors = [];
                    angular.forEach(products, function(product){
                        angular.forEach(product.colors, function(color) {
                            var find = false;
                            angular.forEach(productsColors, function(productColor) {
                                if(productColors.code == color.code)
                                {
                                    find= true;
                                }
                            });
                            if(!find){
                                productsColors.push(color)
                            }
                        });
                    });
                    return productsColors;
                }

                function configSlider(){
                    slider = document.getElementById('slider');
                    var step = scope.maxPriceToFilter - scope.minPriceToFilter;
                    noUiSlider.create(slider, {
                        start: [scope.minPriceToFilter, scope.maxPriceToFilter],
                        connect: true,
                        range: {
                            'min': scope.minPriceToFilter,
                            'max': scope.maxPriceToFilter,
                        },
                        step: step < 100 ? 1 : Math.round(step / 100) - 1,
                        margin: 100,

                    });
                    slider.noUiSlider.start = scope.minPriceToFilter;
                    slider.noUiSlider.end = scope.maxPriceToFilter;

                    slider.noUiSlider.on('update', function( values, handle ) {
                        scope.$applyAsync(function(){
                            if (handle == '0') {
                                scope.minPriceToFilter = values[handle];
                            } else {
                                scope.maxPriceToFilter = values[handle];
                            }
                            scope.includeProducts(null, '', 'PRICE');
                        });
                    });
                }




            }
        };
    }])
        .filter('productsFilter', function($filter){
            return function(products, minPrice, maxPrice, productsInclude) {

                    var productsToReturn = [];
                    angular.forEach(products, function(product){

                        if (Object.keys(productsInclude).length > 0)
                        {
                            var found = 0;
                            for (var key in productsInclude)
                            {
                                for(var i = 0; i < productsInclude[key].length; i++)
                                {
                                    var searchMatches = $.inArray(
                                        JSON.stringify($filter('filter')(product.attributes, {attributeValue: productsInclude[key][i]},false)[0]),
                                        $.map(product.attributes, JSON.stringify));
                                    if(searchMatches > -1)
                                    {
                                        found++;
                                    }
                                    angular.forEach(product.colors, function(color){
                                        if(color.code == productsInclude[key][i].code)
                                        {
                                            found++;
                                        }
                                    });
                                }
                            }
                            if(found == Object.keys(productsInclude).length && product.price >= minPrice && product.price <= maxPrice)
                            {
                                productsToReturn.push(product);
                            }
                        }
                        else if(product.price >= minPrice && product.price <= maxPrice)
                        {
                            productsToReturn.push(product);
                        }
                    });
                    return productsToReturn;
                };
        });
});



//
//
//function configSlider(){
//    slider = document.getElementById('slider');
//    var step = scope.maxPriceToFilter - scope.minPriceToFilter;
//    noUiSlider.create(slider, {
//        start: [scope.minPriceToFilter, scope.maxPriceToFilter], // Handle start position
//        connect: true, // Display a colored bar between the handles
//        range: { // Slider can select '0' to '100'
//            'min': scope.minPriceToFilter,
//            'max': scope.maxPriceToFilter,
//        },
//        step: step < 100 ? 1 : (step / 100), // Slider moves in increments of '10'
//        margin: 20, // Handles must be more than '20' apart
//
//
//        //direction: 'rtl', // Put '0' at the bottom of the slider
//        //orientation: 'vertical', // Orient the slider vertically
//        //behaviour: 'tap-drag', // Move handle on tap, bar is draggable
//        //pips: { // Show a scale with the slider
//        //    mode: 'steps', //    density: 2000
//        //}
//
//    });
//    slider.noUiSlider.start = scope.minPriceToFilter;
//    slider.noUiSlider.end = scope.maxPriceToFilter;
//
//    // When the slider value changes, update the input and span
//    slider.noUiSlider.on('update', function( values, handle ) {
//        scope.$applyAsync(function(){
//            if (handle == '0') {
//                scope.minPriceToFilter = values[handle];
//            } else {
//                scope.maxPriceToFilter = values[handle];
//            }
//            scope.includeProducts(null, '', 'PRICE');
//        });
//    });
//
//    // if I have an input - When the input changes, set the slider value
//    //valueInput.addEventListener('change', function(){
//    //  slider.noUiSlider.set([null, this.value]);
//    //});
//}

/**
 * Created by kubany on 10/29/2015.
 */

define(['./module'], function (directives) {
    'use strict';
    directives.directive('categoryTypeProductsDrtv', ['$filter', '$animate', '$templateCache',
        '$location', function ($filter, $animate, $templateCache, $location) {
        return {
            restrict: 'EA',
            replace: 'true',
            template: $templateCache.get('app/partials/category_type_products_tpl.html'),
            scope: {
                paramsToPass: '=',
            },
            link: function (s, element, attrs) {

                var slider;
                s.showClear = false;
                s.productsInclude = [];
                s.attributeChecked = [];
                s.minPriceToFilter = 0;
                s.maxPriceToFilter = 0;
                s.category = s.paramsToPass;
                s.categoriesFilter = s.paramsToPass.categoriesFilter;
                s.viewAll = s.paramsToPass.viewAll;
                s.searchResult = s.paramsToPass.searchResult;
                s.$location = $location;

                s.options = { start: [20, 70], range: {min: 0, max: 100} }

                s.toggleColorSelectedClass = function(code) {
                    $("#productsColors" + code).toggleClass('colorSelected');
                }

                s.includeProducts = function(attributeVal, attributesName) {

                    if(!attributeVal) return;

                    var location = $.inArray(attributeVal, s.productsInclude[attributesName]);
                    if (location > -1)
                    {
                        s.productsInclude[attributesName].splice(location, 1);
                        if(s.productsInclude[attributesName].length == 0)
                        {
                            delete s.productsInclude[attributesName];
                        }
                    }
                    else
                    {
                        if(s.productsInclude[attributesName] == undefined)
                        {
                            s.productsInclude[attributesName] = [];
                        }
                        s.productsInclude[attributesName].push(attributeVal);
                    }

                    s.showClear =
                        Object.keys(s.productsInclude).length > 0 ||
                        slider.noUiSlider.start != s.minPriceToFilter ||
                        slider.noUiSlider.end != s.maxPriceToFilter;

                    s.productToShow = runFilter(s.minPriceToFilter, s.maxPriceToFilter);

                }

                s.clearSelection = function(){

                    for (var key in s.productsInclude) {
                        delete s.productsInclude[key];
                    }

                    $('.option input[type=checkbox]').each(function(){
                        this.checked = false;
                    })

                    $('.option .productColor').removeClass('colorSelected');

                    slider.noUiSlider.set([slider.noUiSlider.start, slider.noUiSlider.end]);

                    s.showClear = false;

                };

                s.toggleColapse = function(id){
                    $('#' + id).siblings('.option').slideToggle(300);
                    $('#' + id).toggleClass('arrowUp');
                }

                configSlider();

                //runFilter([], categories, null, -1);
                //, s.minPriceToFilter, s.maxPriceToFilter, s.productsInclude, s.categories );
                s.productToShow = runFilter(s.minPriceToFilter, s.maxPriceToFilter)
                s.attributesToShow = getAttributesToShow(s.productToShow);
                s.productsColors = getColorsInProducts(s.productToShow)



                function runFilter(minPrice, maxPrice){
                    //_categories, productsInclude) {

                    var categories = angular.copy(s.searchResult);

                    if (s.productsInclude && Object.keys(s.productsInclude).length != 0) {

                        var productsToReturn = [];
                        for (var key in categories) {

                            var productsFilterized = [];
                            for (var index in categories[key].products) {

                                var prd = categories[key].products[index];
                                if (prd.attributes) {
                                    for (var prdAttrIndex in prd.attributes) {
                                        var prdAttr = prd.attributes[prdAttrIndex];

                                        var include = s.productsInclude[prdAttr.attributeName];
                                        var finded = false;
                                        if (include) {
                                            for (var includeIndex in include) {
                                                if (prdAttr.attributeValue == include[includeIndex]) {
                                                    finded = true;
                                                    break;
                                                }
                                            }
                                            if (finded) {
                                                productsFilterized.push(prd);
                                            }
                                        }
                                    }
                                }
                            }
                            categories[key].products = productsFilterized;
                        }
                    }
                    return $filter("filterFullArrayforAutoComplate")([], categories, null, -1);;
                }


                function getAttributesToShow(productToShow){

                    var attributes = []
                    for(var index in productToShow){

                        var prod = productToShow[index];
                        for(var categIndex in prod.attributes)
                        {
                            var categ = prod.attributes[categIndex];
                            if(attributes[categ.attributeName] == undefined){
                                attributes[categ.attributeName] = [];
                            }
                            attributes[categ.attributeName].push(categ.attributeValue)
                        }
                    }
                    for(var index in attributes){
                        attributes[index] = attributes[index].filter(
                            function(val, index, arr){
                                return arr.indexOf(val) == index;
                            });
                    }
                    var attributesToShow = [];
                    for(var name in attributes) {
                        attributesToShow.push({
                            name: name,
                            values: [],
                        });
                        for (var index in attributes[name]) {
                            attributesToShow[attributesToShow.length - 1].values.push(attributes[name][index]);
                        }
                    }
                    return attributesToShow;
                }





                function getColorsInProducts(products){
                    var productsColors = [];
                    angular.forEach(products, function(product){
                        angular.forEach(product.colors, function(color) {
                            var find = false;
                            angular.forEach(productsColors, function(productColor) {
                                if(productColor.code == color.code)
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

                    var maxVal;
                    var minVal = 900000;
                    angular.forEach(s.searchResult, function(category){

                        var products = category.products
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

                    });

                    s.minPriceToFilter = Math.round(minVal) - 1 ;
                    s.maxPriceToFilter = Math.round(maxVal || 999999);

                    slider = document.getElementById('slider');
                    var step = s.maxPriceToFilter - s.minPriceToFilter;
                    noUiSlider.create(slider, {
                        start: [s.minPriceToFilter, s.maxPriceToFilter],
                        connect: true,
                        range: {
                            'min': s.minPriceToFilter,
                            'max': s.maxPriceToFilter,
                        },
                        step: step < 100 ? 1 : Math.round(step / 100) - 1,
                        margin: 100,

                    });
                    slider.noUiSlider.start = s.minPriceToFilter;
                    slider.noUiSlider.end = s.maxPriceToFilter;

                    slider.noUiSlider.on('update', function( values, handle ) {
                        s.$applyAsync(function(){
                            if (handle == '0') {
                                s.minPriceToFilter = values[handle];
                            } else {
                                s.maxPriceToFilter = values[handle];
                            }
                            s.includeProducts(null, '', 'PRICE');
                        });
                    });
                }

            }
        };
    }])
});



//scope.$watch('category', function(catData) {
//
//    if(catData){
//
//
//    }
//})


//s.manipulateProductsByCustomization = function() {
//
//    s.businessCustom = [];
//    s.gamingCustom = [];
//    s.simplicityCustom = [];
//    angular.forEach(s.productToShow, function (value, key) {
//        if(value.attributes)
//        {
//            if($filter('filter')(value.attributes, {attributeValue: 'Business'}, false).length > 0)
//                s.businessCustom.push(value);
//            if($filter('filter')(value.attributes, {attributeValue: 'Gaming'}, false).length > 0)
//                s.gamingCustom.push(value);
//            if($filter('filter')(value.attributes, {attributeValue: 'Simplicity'}, false).length > 0)
//                s.simplicityCustom.push(value);
//        }
//    });
//};

//s.manipulateProductsByCustomization();

//console.log("autoCompleteResult")
//console.log(result)
//console.log(products)
//console.log(categoriesFilter)
//console.log("autoCompleteResult")

//s.category = paramsToPass.category;
//s.categories = paramsToPass.categories;

//productsFilter:minPriceToFilter:maxPriceToFilter:productsInclude

//console.log(attributesName);
//console.log(attributeVal);
//console.log(s.productsInclude);




































//function configSlider(){
//    slider = document.getElementById('slider');
//    var step = s.maxPriceToFilter - s.minPriceToFilter;
//    noUiSlider.create(slider, {
//        start: [s.minPriceToFilter, s.maxPriceToFilter], // Handle start position
//        connect: true, // Display a colored bar between the handles
//        range: { // Slider can select '0' to '100'
//            'min': s.minPriceToFilter,
//            'max': s.maxPriceToFilter,
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
//    slider.noUiSlider.start = s.minPriceToFilter;
//    slider.noUiSlider.end = s.maxPriceToFilter;
//
//    // When the slider value changes, update the input and span
//    slider.noUiSlider.on('update', function( values, handle ) {
//        s.$applyAsync(function(){
//            if (handle == '0') {
//                s.minPriceToFilter = values[handle];
//            } else {
//                s.maxPriceToFilter = values[handle];
//            }
//            s.includeProducts(null, '', 'PRICE');
//        });
//    });
//
//    // if I have an input - When the input changes, set the slider value
//    //valueInput.addEventListener('change', function(){
//    //  slider.noUiSlider.set([null, this.value]);
//    //});
//}

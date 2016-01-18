/**
 * Created by correnti on 10/12/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.filter('productsFilterForCategoriesProduct',  function($filter){
        return
    });
});




//for(var attrib in productsInclude){
//    console.log("")
//    console.log("================   productsInclude ======================")
//    console.log(productsInclude)
//    console.log(i)
//    console.log(productsInclude[i])
//    console.log("================   productsInclude ======================")
//    console.log("")
//}

//angular.forEach(products, function (product) {
//
//    if (Object.keys(productsInclude).length > 0) {
//        var found = 0;
//        for (var key in productsInclude) {
//            for (var i = 0; i < productsInclude[key].length; i++) {
//                var searchMatches = 0;
//                if (product.attributes) {
//                    var filterAttr = $filter('filter')(product.attributes, {attributeValue: productsInclude[key][i]}, false);
//                    var json = JSON.stringify(filterAttr[0]);
//                    var map = $.map(product.attributes, JSON.stringify);
//                    searchMatches = $.inArray(json, map);
//                }
//                if (searchMatches > -1) {
//                    found++;
//                }
//                for (var colorIndex = 0; colorIndex < product.colors.length; colorIndex++) {
//                    if (product.colors[colorIndex].code == productsInclude[key][i].code) {
//                        found++;
//                        break;
//                    }
//                }
//            }
//        }
//        if (found == Object.keys(productsInclude[key]).length && product.price >= minPrice && product.price <= maxPrice) {
//            productsToReturn.push(product);
//        }
//    }
//    else if (product.price >= minPrice && product.price <= maxPrice) {
//        productsToReturn.push(product);
//    }
//});
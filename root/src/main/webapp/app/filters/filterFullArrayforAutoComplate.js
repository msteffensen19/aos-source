/**
 * Created by correnti on 14/12/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.filter('filterFullArrayforAutoComplate', function(){
        return function(_a, autoCompleteResult) {
            if(autoCompleteResult[0] == undefined)
            {
                return _a;
            }

            var products = [];
            angular.forEach(autoCompleteResult.products, function(product){

            })
            for(var i = 0; i < 6 && i < autoCompleteResult[0].products.length; i++)
            {
                products.push(autoCompleteResult[0].products[i]);
            }
            console.log(products)
            return products;
        };
    });
});


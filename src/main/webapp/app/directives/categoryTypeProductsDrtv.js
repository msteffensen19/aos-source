/**
 * Created by kubany on 10/29/2015.
 */
/**
 * Created by kubany on 10/14/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('categoryTypeProductsDrtv', [ function () {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: './app/partials/category_type_products_tpl.html',
            scope: {
                category: '='
            },
            link: function (scope, element, attrs) {
                scope.$watch('category', function(catData) {
                    if(catData){
                        scope.category = catData;
                        scope.products = catData.products;
                    }

                })
            }
        };
    }]);
});
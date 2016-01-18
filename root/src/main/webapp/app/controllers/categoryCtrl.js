/**
 * Created by kubany on 10/18/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoryCtrl', ['$scope', '$stateParams', 'categoryService', 'category',
        function (s, $stateParams, categoryService, paramsToReturn) {

            s.catId = $stateParams.id;

            s.viewAll = paramsToReturn.viewAll;

            s.noProducts = paramsToReturn.products == undefined || paramsToReturn.products.length == 0;

            s.paramsToPass = paramsToReturn;
            l(s.paramsToPass)

            s.categoryData = paramsToReturn[0];

            s.categoriesFilter = paramsToReturn.categoriesFilter

            s.categoryAttributes = paramsToReturn.attributes;

            s.products = paramsToReturn.products;

            s.noProducts = false;

            s.categoryName = paramsToReturn.categoryName;

            $("nav .navLinks").css("display" , "none");

            Helper.forAllPage();

    }]);
});
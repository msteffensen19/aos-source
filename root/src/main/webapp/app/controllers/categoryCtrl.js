/**
 * Created by kubany on 10/18/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoryCtrl', ['$scope', '$stateParams', 'categoryService', 'category',
        function (s, $stateParams, categoryService, paramsToReturn) {

            s.catId = $stateParams.id;

            s.paramsToPass = paramsToReturn;

            s.viewAll = paramsToReturn.viewAll;

            s.categoryData = paramsToReturn.searchResult[0];

            s.noProducts = s.categoryData.products == undefined || s.categoryData.products.length == 0;

            s.categoryName = s.categoryData.categoryName;

            $("nav .navLinks").css("display" , "none");

            Helper.forAllPage();

    }]);
});
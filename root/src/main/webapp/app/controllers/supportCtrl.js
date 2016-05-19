/**
 * Created by correnti on 18/02/2016.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('supportCtrl', ['$scope', 'supportService', 'paramsToResorve', 'categoryService',

        function (s, supportService, paramsToResorve, categoryService) {

            s.hola_mundo = "hola_mundohola_mundohola_mundohola_mundo"













            //l(paramsToResorve.categories)


            //s.supportSuccess = false;
            //s.registerAnswer = { class: "", message: "" }
            //s.supportModel = {
            //    "category": {},
            //    "email": "",
            //    "product": {},
            //    "subject": ""
            //}
            //
            //s.categories = paramsToResorve.categories;
            //s.products;
            //
            //s.categoryChange = function() {
            //    categoryService.getCategoryById(s.supportModel.category.categoryId).
            //    then(function (category) {
            //        l(category.products)
            //        s.products = category.products;
            //        s.supportModel.product = category.products[0];
            //    });
            //}
            //
            //s.productChange = function() {
            //    l("s.supportModel.================")
            //    l(s.supportModel.product);
            //    l(s.supportModel.category.categoryId);
            //    l(s.supportModel.category.categoryName);
            //    l(s.supportModel.text);
            //    l(s.supportModel.product.productId);
            //    l(s.supportModel.product.productName);
            //    l(s.supportModel.subject);
            //    l("s.supportModel.product ++++++++++++++++++++")
            //}
            //
            //s.sendSupportEmail = function(){
            //
            //    supportService.sendSupportEmail(s.supportModel).then(function(res){
            //
            //        s.registerAnswer.class = res.success ? "valid" : "invalid";
            //        s.supportSuccess = res.success;
            //        s.registerAnswer.message = res.reason;
            //
            //    }, function(err){
            //    });
            //
            //}
            //

            //Helper.forAllPage();
        }]);
});


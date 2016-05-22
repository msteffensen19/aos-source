/**
 * Created by correnti on 18/02/2016.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('supportCtrl', ['$scope', 'supportService', 'paramsToResorve', 'categoryService',

        function (s, supportService, paramsToResorve, categoryService) {

            s.hola_mundo = "hola_mundohola_mundohola_mundohola_mundo"

            s.model  = "s@s.sec";
            s.model7 = "t@y.sec";
            s.model8 = "e@e.sec";
            s.model9 = "r@r.sec";

            s.isValid = function (valid) {
                console.log(valid);
            }

            s.require = {
                error: 'this field is required',
                info: '- this field have to have value',
            }

            s.minLength = {
                error: 'Use up of 4 character',
                info: '- Use up of 4 character',
                min: 4,
            }

            s.maxLength = {
                error: 'Use maximum 8 character',
                info: '- Use maximum 8 character',
                max: 8,
            }

            s.email = {
                error : "Your email address isn’t formatted correctly",
                info: "- A valid email required",
                regex : "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$"
            }

            s.compareTo = {
                error : "Same as Username",
                info: "- A param have to be exactly like UserName",
                model : s.model
            }
        }]);
});













































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































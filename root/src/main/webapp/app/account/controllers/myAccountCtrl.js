/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('myAccountCtrl', ['$scope', '$timeout',
        '$location', 'resolveParams', 'accountService',
        function (s, $timeout, $location, resolveParams, accountService) {

            checkLogin();
            function checkLogin(){
                s.checkLogin();
                if($location.path().indexOf('/myAccount') != -1){
                    $timeout(checkLogin, 2000);
                }
            }

            s.accountDetails = resolveParams.accountDetails;
            s.shippingDetails = resolveParams.shippingDetails;
            s.masterCredit = resolveParams.paymentPreferences ?
                resolveParams.paymentPreferences.masterCredit.substring(resolveParams.paymentPreferences.masterCredit.length - 4) : null;

            s.categoriesPromotions = [
                { categoryName : 'Tablets', categoryValue : true, },
                { categoryName : 'Laptops', categoryValue : true, },
                { categoryName : 'Headphones', categoryValue : true, },
                { categoryName : 'Speakers', categoryValue : true, },
                { categoryName : 'Mice', categoryValue : true, },
            ];

            s.allowOffersPromotionChanged = function(){
                accountService.accountUpdate(s.accountDetails);
            }

            Helper.forAllPage();


        }]);
});





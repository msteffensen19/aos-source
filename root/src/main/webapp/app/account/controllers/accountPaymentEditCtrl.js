/**
 * Created by correnti on 02/02/2016.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('accountPaymentEditCtrl', ['$scope', '$timeout',
        '$location', 'resolveParams', 'accountService',
        function (s, $timeout, $location, resolveParams, accountService) {

            checkLogin();
            function checkLogin() {
                s.checkLogin();
                if ($location.path().indexOf('/accountPaymentEdit') != -1) {
                    $timeout(checkLogin, 2000);
                }
            }

            s.imgRadioButton = 1;
            s.years = [];
            var now = new Date();
            for (var i = 0; i < 10; i++) {
                s.years.push((now.getFullYear() + i) + "");
            }

            s.card = { number: '', cvv: '', expirationDate: { month: '', year: '' }, name: '' }
            s.savePay = { username : '', password : '' }

            var masterCredit = resolveParams.paymentPreferences && resolveParams.paymentPreferences.masterCredit ?
                resolveParams.paymentPreferences.masterCredit : null;
            if(masterCredit != null){
                s.card = {
                    number: masterCredit.cardNumber.substring(4),
                    cvv: masterCredit.cvvNumber,
                    expirationDate: {
                        month: masterCredit.expirationDate.substring(0, 2),
                        year: masterCredit.expirationDate.substring(2)
                    },
                    name: masterCredit.customername,
                }
            }
            var safePay = resolveParams.paymentPreferences && resolveParams.paymentPreferences.safePay ?
                resolveParams.paymentPreferences.safePay : null;
            if(safePay != null){
                s.savePay.username = safePay.safepayUsername;
                s.savePay.password = safePay.safepayPassword;
            }

            s.saveMasterCredit = function () {
                var response;
                if (true) {
                    response = accountService.addMasterCreditMethod(s.card)
                }
                else {
                    response = accountService.updateMasterCreditMethod(s.card)
                }
                response.then(function (response) {

                    if(s.preferredPayment_MasterCredit && response && response.REASON && response.SUCCESS == 'true'){
                        accountService.updatePrefferedPaymentMethod(20).then(function(res){
                            setMessage(res);
                        });
                    }
                    else{
                        setMessage(response);
                    }
                });
            }

            s.saveSafePay = function () {

                var response;
                if (true) {
                    response = accountService.addSafePayMethod(s.savePay)
                }
                else {
                    response = accountService.updateSafePayMethod(s.savePay)
                }
                response.then(function (response) {

                    if(s.preferredPayment_SafePay && response && response.REASON && response.SUCCESS == 'true'){
                        accountService.updatePrefferedPaymentMethod(10).then(function(res){
                            setMessage(res);
                        });
                    }
                    else{
                        setMessage(response);
                    }
                });
            }

            function setMessage(response){

                if (response && response.REASON) {
                    s.accountDetailsAnswer = {
                        message: response.REASON,
                        class: response.SUCCESS == 'true' ? 'valid' : 'invalid'
                    }
                    if (response.SUCCESS == 'true') {
                        $location.path('myAccount');
                    }
                    else {
                        $timeout(function () {
                            s.accountDetailsAnswer = {message: '', class: 'invalid'}
                        }, 4000)
                    }
                }
            }

            Helper.forAllPage();

        }]);
});







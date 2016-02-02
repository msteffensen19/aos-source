/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('accountDetailsCtrl', ['$scope', '$timeout',
        '$location', 'resolveParams', 'registerService', 'accountService',
        function (s, $timeout, $location, resolveParams, registerService, accountService) {

            checkLogin();
            function checkLogin(){
                s.checkLogin();
                if($location.path().indexOf('/accountDetails') != -1){
                    $timeout(checkLogin, 2000);
                }
            }

            s.accountDetails = resolveParams.accountDetails;
            s.accountDetailsAnswer = {message: '', class: 'invalid'}
            s.saveAccountDetails = function(){
                accountService.accountUpdate(s.accountDetails).then(function(response){
                    if(response && response.REASON){
                        s.accountDetailsAnswer = {
                            message : response.REASON,
                            class : response.SUCCESS == 'true' ? 'valid' : 'invalid'
                        }
                        if (response.SUCCESS == 'true') {
                            $location.path('myAccount');
                        }
                        else{
                            $timeout(function () {
                                s.accountDetailsAnswer = {message: '', class: 'invalid'}
                            }, 4000)
                        }
                    }
                });
            }

            s.countries = null;
            registerService.getAllCountries().then(function (countries) {
                for(var i  in countries){
                    if(countries[i].id == s.accountDetails.countryId)
                    {
                        s.country = countries[i];
                        break;
                    }
                }
                s.countries = countries;
            });

            s.countryChange = function(country){
                s.accountDetails.countryId = country.id;
                s.accountDetails.countryIsoName = country.isoName;
                s.accountDetails.countryName = country.name;
            }


            $("nav .navLinks").css("display" , "none");

            Helper.forAllPage();

        }]);
});





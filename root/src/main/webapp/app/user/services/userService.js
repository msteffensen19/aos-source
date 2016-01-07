/**
 * Created by kubany on 11/11/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.factory('userService',['$rootScope', '$q', '$http', "resHandleService", "$soap",

        function ($rootScope, $q, $http, responseService, $soap) {

            // I represent the currently active modal window instance.
            var modal = {
                deferred: null,
                params: null
            };


            // Return the public API.
            return ({
                open: open,
                params: params,
                proceedTo: proceedTo,
                reject: reject,
                resolve: resolve,
                login: login,
                createNewAccount: createNewAccount,
                forgotPassword: forgotPassword,
                getConfiguration : getConfiguration,
            });

            function getConfiguration(){



                var request = $http({
                 "method": "get",
                 "url": server.service.getConfiguration(),
                 });
                 return( request.then(
                 responseService.handleSuccess,
                 responseService.handleError
                 ));

            }


            function login (user){

                $soap.post(
                    'http://www.advantageonlineshopping.com/accountservice',
                    'GetAccountByIdRequest',
                    { accountId: 12 }
                ).then(function(response){
                    console.log("angular soap SUCCESS")
                    console.log(response);
                },
                function(response){
                    console.log("angular soap FAILD")
                    console.log(response);
                });;


                //var _url = 'http://www.w3schools.com/webservices/tempconvert.asmx'


                //$soap.post(_url,"CelsiusToFahrenheit", {Celsius : 80}).then(function(response){
                //    console.log(" ")
                //    console.log("angular soap")
                //    console.log(response);
                //});;

               // var d = $q.defer();
                //var _url = 'http://www.advantageonlineshopping.com/accountservice'
                //_url = 'http://www.advantageonlineshopping.com/accountservice'
                //
                //$soap.post(_url,"GetAllAccounts").then(function(response){
                //    console.log(" ")
                //    console.log("angular soap")
                //    console.log(response);
                //});;


                //$.soap({
                //    url: _url + "/GetAllAccounts",
                //    method: 'post',
                //    headers: {
                //        'Content-Type': 'text/xml; charset=UTF-8'
                //    },
                //    success: function (soapResponse) {
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log("jquery ")
                //        console.log(soapResponse)
                //        // do stuff with soapResponse
                //        d.resolve(true)
                //    },
                //    error: function (err){
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log(" ")
                //        console.log("jquery err")
                //        console.log(err)
                //        alert("err")
                //    }
                //});
                //
                //



                //return d.promise
//
//===================================================================================================



                //$.soap({
                //    url: 'http://www.advantageonlineshopping.com/accountservice/accountservice.wsdl',
                //    method: 'login',
                //    data: user,
                //    success: function (soapResponse) {
                //
                //        console.log(soapResponse)
                //        alert()
                //        // do stuff with soapResponse
                //        // if you want to have the response as JSON use soapResponse.toJSON();
                //        // or soapResponse.toString() to get XML string
                //        // or soapResponse.toXML() to get XML DOM
                //    },
                //    error: function (SOAPResponse) {
                //        // show error
                //        console.log(SOAPResponse)
                //    }
                //});


                //$("#btnCallWebService").click(function (event) {
                //    var wsUrl = "http://localhost:8080/accountservice/accountservice.wsdl";
                //    var soapRequest ='<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">   <soap:Body> <getQuote xmlns:impl="http://abc.com/services/soap/server1.php">  <symbol>' + $("#txtName").val() + '</symbol>   </getQuote> </soap:Body></soap:Envelope>';
                //    alert(soapRequest)
                //    $.ajax({
                //        type: "POST",
                //        url: wsUrl,
                //        contentType: "text/xml",
                //        dataType: "xml",
                //        data: soapRequest,
                //        success: processSuccess,
                //        error: processError
                //    });
                //
                //});















                //return $soap.post(
                //    "http://localhost:8080/accountservice/accountservice.wsdl",
                //    "login",
                //    user
                //);

                //return $soap.post(
                //    "http://localhost:8080/accountservice/accountservice.wsdl?login",
                //    "login",
                //    user
                //);


                //$soap.post(
                //    "http://localhost:8080/accountservice/accountservice.wsdl/login",
                //    "login",
                //    JSON.stringify(user)
                //).then(function(response){
                //    console.log(response)
                //    alert("responce")
                //    alert(responce)
                //});

                //
                //var v = $soap.post(
                //    "http://www.advantageonlineshopping.com/accountservice/accountservice.wsdl",
                //    "GetAccountById",
                //    { accountId : 12 }
                //).then(function(response){
                //    console.log("responce")
                //    console.log(response)
                //    alert(response)
                //    console.log("responce")
                //}).then(function(response){
                //    console.log("err")
                //    console.log(response)
                //    alert(response)
                //    console.log("err")
                //});


                //var request = $http({
                //    "Content-Type": "application/json;charset=UTF-8",
                //    "method": "post",
                //    "url": "http://www.advantageonlineshopping.com/accountservice/accountservice.wsdl",
                //
                //    //server.account.login(user),
                //    "data": JSON.stringify(user) ,
                //});
                //return( request.then(
                //    responseService.handleSuccess,
                //    responseService.handleError
                //));


                //var request = $http.post("http://localhost:8080/accountservice/");
                //return( request.then(
                //    responseService.handleSuccess,
                //    responseService.handleError
                //));

                var request = $http({
                    "Content-Type": "application/json;charset=UTF-8",
                    "method": "post",
                    "url": server.account.login(),
                    "data": JSON.stringify(user) ,
                });
                return( request.then(
                    responseService.handleSuccess,
                    responseService.handleError
                ));
            }





            function createNewAccount(){
                var request = $http({
                    method: "post",
                    url:"api/category",
                });
                return( request.then(
                    responseService.handleSuccess,
                    responseService.handleError
                ));
            }





            function forgotPassword(){
                var request = $http({
                    method: "post",
                    url:"api/category",
                });
                return( request.then(
                    responseService.handleSuccess,
                    responseService.handleError
                ));
            }



                // ---
            // PULBIC METHODS.s
            // ---
            // I open a modal of the given type, with the given params. If a modal
            // window is already open, you can optionally pipe the response of the
            // new modal window into the response of the current (cum previous) modal
            // window. Otherwise, the current modal will be rejected before the new
            // modal window is opened.
            function open(type, params, pipeResponse) {
                var previousDeferred = modal.deferred;
                // Setup the new modal instance properties.
                modal.deferred = $q.defer();
                modal.params = params;
                // We're going to pipe the new window response into the previous
                // window's deferred value.
                if (previousDeferred && pipeResponse) {
                    modal.deferred.promise
                        .then(previousDeferred.resolve, previousDeferred.reject)
                    ;
                    // We're not going to pipe, so immediately reject the current window.
                } else if (previousDeferred) {
                    previousDeferred.reject();
                }


                // Since the service object doesn't (and shouldn't) have any direct
                // reference to the DOM, we are going to use events to communicate
                // with a directive that will help manage the DOM elements that
                // render the modal windows.
                // --
                // NOTE: We could have accomplished this with a $watch() binding in
                // the directive; but, that would have been a poor choice since it
                // would require a chronic watching of acute application events.
                $rootScope.$emit("loginModal.open", type);
                return ( modal.deferred.promise );
            }






            // I return the params associated with the current params.
            function params() {
                return ( modal.params || {} );
            }






            // I open a modal window with the given type and pipe the new window's
            // response into the current window's response without rejecting it
            // outright.
            // --
            // This is just a convenience method for .open() that enables the
            // pipeResponse flag; it helps to make the workflow more intuitive.
            function proceedTo(type, params) {
                return ( open(type, params, true) );
            }






            // I reject the current modal with the given reason.
            function reject(reason) {
                if (!modal.deferred) {
                    return;
                }
                modal.deferred.reject(reason);
                modal.deferred = modal.params = null;
                // Tell the modal directive to close the active modal window.
                $rootScope.$emit("modals.close");
            }







            // I resolve the current modal with the given response.
            function resolve(response) {
                if (!modal.deferred) {
                    return;
                }
                modal.deferred.resolve(response);
                modal.deferred = modal.params = null;
                // Tell the modal directive to close the active modal window.
                $rootScope.$emit("modals.close");
            }



        }
    ]);
});
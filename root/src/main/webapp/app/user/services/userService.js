/**
 * Created by kubany on 11/11/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.factory('userService',['$rootScope', '$q', '$http', "resHandleService", "$soap",

        function ($rootScope, $q, $http, responseService, $soap) {

            // Return the public API.
            return ({
                login: login,
                getConfiguration: getConfiguration,
            });

            function getConfiguration() {

                var request = $http({
                    "method": "get",
                    "url": server.service.getConfiguration(),
                });
                return ( request.then(
                    responseService.handleSuccess,
                    responseService.handleError
                ));

            }


            //response.setHeader("Access-Control-Allow-Origin", "*");
            //response.setHeader("Access-Control-Request-Method", "POST, GET, OPTIONS, DELETE");
            //response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            //response.setHeader("Access-Control-Max-Age", "3600");
            //response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Key, soapaction");


            function login(user) {

                var defer = $q.defer();
                var params = server.account.login();

                $soap.post(params.path, params.method, user).
                then(function (response) {
                    defer.resolve(response);

                    console.log("angular LOGIN START")
                    $soap.post(
                        //'http://www.advantageonlineshopping.com/accountservice',
                        'http://localhost:8080/accountservice',
                        'GetAccountByIdRequest',
                        {accountId: 12}
                    ).then(function (response) {
                            console.log("angular soap SUCCESS")
                            console.log(response);
                            defer.resolve(response);
                        },
                        function (response) {
                            console.log(response);
                            defer.reject("Request failed! ");
                        });
                    return defer.promise;
                });
            }
        }]);
});





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


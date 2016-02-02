/**
 * Created by correnti on 13/12/2015.
 */



var fileText;
(function readTextFile(file)
{
    console.log('Extracting file: ' + file)

    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function ()
    {
        if(rawFile.readyState === 4)
        {
            if(rawFile.status === 200 || rawFile.status == 0)
            {
                var allText = rawFile.responseText;
                fileText = allText;
                console.log('Extracted file: ' + file)
                console.log("File: ");
                console.log("");
                console.log("");
                console.log("");
                console.log(allText);
                console.log("");
                console.log("");
                console.log("");
                console.log("end of file");
                console.log("");
                console.log("");
            }
        }
    }
    rawFile.send(null)
})('services.properties');
fileText = fileText.split('');

var _param = '';
var _value = '';
var attr = true;
var arrayApi = [];
var invalidChars = '#';

fileText.forEach(function(a){
    switch (a.charCodeAt(0))
    {
        case 10: case 13:
        var validParam = true;
        for(var i = 0; i < invalidChars.length; i++)
        {
            if(_param.indexOf(invalidChars[i]) != -1)
            {
                validParam = false; break;
            }
        }
        if(validParam && _param != '' && _value != '' )
        {
            arrayApi.push("{\"" + _param.split(".").join("_") +  "\":\"" + _value + "\"}");
            _param = ''; _value = '';
        }
        attr = true;
        break;
        case 61:
            attr = false;
            break;
        default:
            if(attr) { _param += a; } else { _value += a; }
            break;
    }
});

var services_properties = []
arrayApi.forEach(function(a) {
    var jsonObj = JSON.parse(a);
    services_properties[Object.keys(jsonObj)] = jsonObj[Object.keys(jsonObj)];
});


//var catalogKey = "http://localhost:8080/catalog";
var catalogKey = "http://" +
    services_properties['catalog_service_url_host'] + ":" +
    services_properties['catalog_service_url_port'] + "/" +
    services_properties['catalog_service_url_suffix'];
console.log("catalogKey = " + catalogKey);


//var orderKey = "http://localhost:8080/order";
var orderKey = "http://" +
    services_properties['order_service_url_host'] + ":" +
    services_properties['order_service_url_port'] + "/" +
    services_properties['order_service_url_suffix'];
console.log("orderKey = " + orderKey);


//var accountKey = "http://localhost:8080/account";
var accountKey = "http://" +
    services_properties['account_service_url_host'] + ":" +
    services_properties['account_service_url_port'] + "/" +
    services_properties['account_service_url_suffix'];
console.log("accountKey = " + accountKey);


//var serviceKey = "http://localhost:8080/service";
var serviceKey = "http://"+
    services_properties['service_service_url_host'] + ":" +
    services_properties['service_service_url_port'] + "/" +
    services_properties['service_service_url_suffix'];
console.log("serviceKey = " + serviceKey);


//var wsdlPath = 'http://localhost:8080/accountservice';
var wsdlPath = "http://"+
    services_properties['account_soapservice_url_host'] + ":" +
    services_properties['account_soapservice_url_port'] + "/" +
    services_properties['account_soapservice_url_suffix'];
console.log("wsdlPath = " + wsdlPath);


console.log("");
console.log("");
console.log("");


var server = {

    catalog: {

        getKey : function(){
            return catalogKey;
        },
        getPopularProducts : function () {
            return "app/tempFiles/popularProducts.json";
        },

        getCategories: function(){
            return catalogKey + "/categories";
        },

        getCategoryById : function(id) {
            return catalogKey + "/categories/" + id + "/products";
        },

        getDeals : function () {
            return catalogKey + "/deals";
        },

        getDealOfTheDay : function () {
            return catalogKey + "/deals/search?dealOfTheDay=true";
        },

        getProducts : function () {
            return catalogKey + "/products.json";
        },

        getProductById : function (id) {
            return catalogKey + '/products/' + id;
        },

        getProductsBySearch : function (word, quantity) {
            var path = catalogKey + "/products/search?name=" + word;
            if(quantity > 0)
            {
                path += "&quantityPerEachCategory=" + quantity;
            }
            return path;
        }

    },
    order: {

        updateUserCart: function (userId){
            return orderKey + "/carts/" + userId;
        },

        removeProductToUser: function (userId, productId, color){
            return orderKey + "/carts/" + userId +
                "/product/" + productId +
                "/color/" + color;
        },

        updateProductToUser: function (userId, productId, color, quantity, oldColor){
            var path = server.order.addProductToUser(userId, productId, oldColor, quantity);
            return color != oldColor ? path += "&new_color=" + color : path;
        },

        addProductToUser: function (userId, productId, color, quantity){
            return orderKey + "/carts/" + userId + "/product/" + productId +
                "/color/" + color + "?quantity=" + quantity;
        },

        loadCartProducts : function (userId){
            return orderKey + "/carts/" + userId;
        },

        clearCart : function (userId){
            return orderKey + "/carts/" + userId;
        },

        getShippingCost: function(){
            return orderKey + "/shippingcost/" ;
        },

        safePay: function(userId){
            return orderKey + "/orders/users/" + userId ;
        },

        accountUpdate: function(){
            return {
                path: wsdlPath,
                method: 'AccountUpdateRequest'
            }
        },

    },
    account: {

        getAllCountries : function (){
            return {
                path: wsdlPath,
                method: 'GetCountriesRequest'
            }
        },

        register : function(){
            return {
                path: wsdlPath,
                method: 'AccountCreateRequest'
            }
        },

        login : function(){
            return {
                path: wsdlPath,
                method: 'AccountLoginRequest'
            }
        },

        getAccountById: function(){
            return {
                path: wsdlPath,
                method: 'GetAccountByIdRequest'
            }
        },

        getAccountById_new: function(){
            return {
                path: wsdlPath,
                method: 'GetAccountByIdNewRequest'
            }
        },

        getAccountPaymentPreferences: function(){
            return {
                path: wsdlPath,
                method: 'GetAccountPaymentPreferencesRequest'
            }
        },

        getAddressesByAccountId: function(){
            return {
                path: wsdlPath,
                method: 'GetAddressesByAccountIdRequest'
            }
        },

        accountUpdate: function(){
            return {
                path: wsdlPath,
                method: 'AccountUpdateRequest'
            }
        },

        updateMasterCreditMethod: function(){
            return {
                path: wsdlPath,
                method: 'UpdateMasterCreditMethodRequest'
            }
        },

        updateSafePayMethod: function(){
            return {
                path: wsdlPath,
                method: 'UpdateSafePayMethodRequest'
            }
        },

    },
    service: {

        getConfiguration: function () {
            return {
                path: wsdlPath,
                method: 'AccountConfigurationStatusRequest'
            }
        },
    }
}

//var fileText;
//(function readTextFile(file)
//{
//    console.log('Extracting file: ' + file)
//
//    var rawFile = new XMLHttpRequest();
//    rawFile.open("GET", file, false);
//    rawFile.onreadystatechange = function ()
//    {
//        if(rawFile.readyState === 4)
//        {
//            if(rawFile.status === 200 || rawFile.status == 0)
//            {
//                var allText = rawFile.responseText;
//                fileText = allText;
//                console.log('Extracted file: ' + file)
//                console.log("File: ");
//                console.log("");
//                console.log("");
//                console.log("");
//                console.log(allText);
//                console.log("");
//                console.log("");
//                console.log("");
//                console.log("end of file");
//                console.log("");
//                console.log("");
//            }
//        }
//    }
//    rawFile.send(null)
//})('services.properties');
//fileText = fileText.split('');
//
//var _param = '';
//var _value = '';
//var attr = true;
//var arrayApi = [];
//var invalidChars = '#';
//
//fileText.forEach(function(a){
//    switch (a.charCodeAt(0))
//    {
//        case 10: case 13:
//            var validParam = true;
//            for(var i = 0; i < invalidChars.length; i++)
//            {
//                if(_param.indexOf(invalidChars[i]) != -1)
//                {
//                    validParam = false; break;
//                }
//            }
//            if(validParam && _param != '' && _value != '' )
//            {
//                arrayApi.push("{\"" + _param.split(".").join("_") +  "\":\"" + _value + "\"}");
//                _param = ''; _value = '';
//            }
//            attr = true;
//            break;
//        case 61:
//            attr = false;
//            break;
//        default:
//            if(attr) { _param += a; } else { _value += a; }
//            break;
//    }
//});
//
//var services_properties = []
//arrayApi.forEach(function(a) {
//    var jsonObj = JSON.parse(a);
//    services_properties[Object.keys(jsonObj)] = jsonObj[Object.keys(jsonObj)];
//});
//
//
////var catalogKey = "http://localhost:8080/catalog";
//var catalogKey = "http://" +
//    services_properties['catalog_service_url_host'] + ":" +
//    services_properties['catalog_service_url_port'] + "/" +
//    services_properties['catalog_service_url_suffix'];
//console.log("catalogKey = " + catalogKey);
//
//
////var orderKey = "http://localhost:8080/order";
//var orderKey = "http://" +
//    services_properties['order_service_url_host'] + ":" +
//    services_properties['order_service_url_port'] + "/" +
//    services_properties['order_service_url_suffix'];
//console.log("orderKey = " + orderKey);
//
//
////var accountKey = "http://localhost:8080/account";
//var accountKey = "http://" +
//    services_properties['account_service_url_host'] + ":" +
//    services_properties['account_service_url_port'] + "/" +
//    services_properties['account_service_url_suffix'];
//console.log("accountKey = " + accountKey);
//
//
////var serviceKey = "http://localhost:8080/service";
//var serviceKey = "http://"+
//    services_properties['service_service_url_host'] + ":" +
//    services_properties['service_service_url_port'] + "/" +
//    services_properties['service_service_url_suffix'];
//console.log("serviceKey = " + serviceKey);
//
//
////var wsdlPath = 'http://localhost:8080/accountservice';
//var wsdlPath = "http://"+
//    services_properties['account_soapservice_url_host'] + ":" +
//    services_properties['account_soapservice_url_port'] + "/" +
//    services_properties['account_soapservice_url_suffix'];
//console.log("wsdlPath = " + wsdlPath);
//
//
//console.log("");
//console.log("");
//console.log("");
//
//
//var server = {
//
//    catalog: {
//
//        getKey : function(){
//            return catalogKey;
//        },
//        getPopularProducts : function () {
//            return "app/tempFiles/popularProducts.json";
//        },
//
//        getCategories: function(){
//            return catalogKey + "/categories";
//        },
//
//        getCategoryById : function(id) {
//            return catalogKey + "/categories/" + id + "/products";
//        },
//
//        getDeals : function () {
//            return catalogKey + "/deals";
//        },
//
//        getDealOfTheDay : function () {
//            return catalogKey + "/deals/search?dealOfTheDay=true";
//        },
//
//        getProducts : function () {
//            return catalogKey + "/products.json";
//        },
//
//        getProductById : function (id) {
//            return catalogKey + '/products/' + id;
//        },
//
//        getProductsBySearch : function (word, quantity) {
//            var path = catalogKey + "/products/search?name=" + word;
//            if(quantity > 0)
//            {
//                path += "&quantityPerEachCategory=" + quantity;
//            }
//            return path;
//        }
//
//    },
//    order: {
//
//        updateUserCart: function (userId){
//            return orderKey + "/carts/" + userId;
//        },
//
//        removeProductToUser: function (userId, productId, color){
//            return orderKey + "/carts/" + userId +
//                "/product/" + productId +
//                "/color/" + color;
//        },
//
//        updateProductToUser: function (userId, productId, color, quantity, oldColor){
//            var path = server.order.addProductToUser(userId, productId, oldColor, quantity);
//            return color != oldColor ? path += "&new_color=" + color : path;
//        },
//
//        addProductToUser: function (userId, productId, color, quantity){
//            return orderKey + "/carts/" + userId + "/product/" + productId +
//                "/color/" + color + "?quantity=" + quantity;
//        },
//
//        loadCartProducts : function (userId){
//            return orderKey + "/carts/" + userId;
//        },
//
//        clearCart : function (userId){
//            return orderKey + "/carts/" + userId;
//        },
//
//        getShippingCost: function(){
//            return orderKey + "/shippingcost/" ;
//        },
//
//        safePay: function(userId){
//            return orderKey + "/orders/users/" + userId ;
//        },
//
//        accountUpdate: function(){
//            return {
//                path: wsdlPath,
//                method: 'AccountUpdateRequest'
//            }
//        },
//
//    },
//    account: {
//
//        getAllCountries : function (){
//            return {
//                path: wsdlPath,
//                method: 'GetCountriesRequest'
//            }
//        },
//
//        register : function(){
//            return {
//                path: wsdlPath,
//                method: 'AccountCreateRequest'
//            }
//        },
//
//        login : function(){
//            return {
//                path: wsdlPath,
//                method: 'AccountLoginRequest'
//            }
//        },
//
//        getAccountById: function(){
//            return {
//                path: wsdlPath,
//                method: 'GetAccountByIdRequest'
//            }
//        },
//
//        getAccountById_new: function(){
//            return {
//                path: wsdlPath,
//                method: 'GetAccountByIdNewRequest'
//            }
//        },
//
//        getAccountPaymentPreferences: function(){
//            return {
//                path: wsdlPath,
//                method: 'GetAccountPaymentPreferencesRequest'
//            }
//        },
//
//        getAddressesByAccountId: function(){
//            return {
//                path: wsdlPath,
//                method: 'GetAddressesByAccountIdRequest'
//            }
//        },
//
//
//    },
//    service: {
//
//        getConfiguration : function (){
//            return {
//                path: wsdlPath,
//                method: 'AccountConfigurationStatusRequest'
//            }
//        },
//
//    }
//}

/**
 * Created by correnti on 13/12/2015.
 */


var fileText;
(function readTextFile(file)
{
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
            var re = new RegExp('.', 'g');
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


//var orderKey = "http://localhost:8080/order";
var orderKey = "http://" +
    services_properties['order_service_url_host'] + ":" +
    services_properties['order_service_url_port'] + "/" +
    services_properties['order_service_url_suffix'];

//var accountKey = "http://localhost:8080/account";
var accountKey = "http://" +
    services_properties['account_service_url_host'] + ":" +
    services_properties['account_service_url_port'] + "/" +
    services_properties['account_service_url_suffix'];

//var serviceKey = "http://localhost:8080/service";
var serviceKey = "http://"+
    services_properties['service_service_url_host'] + ":" +
    services_properties['service_service_url_port'] + "/" +
    services_properties['service_service_url_suffix'];


var server = {

    catalog: {

        getKey : function(){
        return catalogKey;
        },
        getPopularProducts : function () {
            return "app/jsonFiles/popularProducts.json";
        },

        getCategories: function(){
            return catalogKey + "/api/v1/categories";
        },

        getCategoryById : function(id) {
            return catalogKey + "/api/v1/categories/" + id + "/products";
        },

        getDeals : function () {
            return catalogKey + "/api/v1/deals";
        },

        getDealOfTheDay : function () {
            return catalogKey + "/api/v1/deals/search?dealOfTheDay=true";
        },

        getProducts : function () {
            return catalogKey + "/api/v1/products.json";
        },

        getProductById : function (id) {
            return catalogKey + '/api/v1/products/' + id;
        },

        getProductsBySearch : function (word, quantity) {
            return catalogKey + "/api/v1/products/search?name=" + word +
                "&quantityPerEachCategory=" + quantity;
        }

    },
    order: {


        addProductToUser: function (userId, productId, color, quantity){
            return orderKey + "/api/v1/carts/" + userId +
                "/product/" + productId +
                "/color/" + color +
                "?quantity=" + quantity;
        },

        loadCartProducts : function (userId){
            console.log(orderKey + "/api/v1/carts/" + userId)
            return orderKey + "/api/v1/carts/" + userId;
        }

    },
    account: {

        getAllCountries : function (){
            return accountKey + "/api/v1/countries";
        },

        register : function(model){
            return accountKey + "/api/v1/users";
        },

        login : function(user){
            return accountKey + "/api/v1/login";
        },


    },
    service: {

        getConfiguration : function (){
            return serviceKey + "/api/v1/clientConfiguration";
        },

    }
}
/**
 * Created by correnti on 13/12/2015.
 */

var catalogKey = "http://localhost:8080/catalog/";
var orderKey = "http://localhost:8080/order/";
var accountKey = "http://localhost:8080/account/";
var serviceKey = "http://localhost:8080/service/";

var server = {

    catalog: {

        getCategories: function(){
            return catalogKey + "/api/v1/categories";
        },

        getCategoryById : function(id) {
            return catalogKey + "/api/v1/categories/" + id + "/products";
        },

        getPopularProducts : function () {
            return "/app/popularProducts.json";
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



    },
    account: {

        getAllCountries : function (){
            return accountKey + "/api/v1/countries";
        },

        register : function(model){
            return accountKey + "api/account/users";
        },

        login : function(user){
            return accountKey + "api/account/login";
        },


    },
    service: {

        getConfiguration : function (){
            return serviceKey + "api/v1/clientConfiguration";
        },

    }
}
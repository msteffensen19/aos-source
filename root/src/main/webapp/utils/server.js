/**
 * Created by correnti on 13/12/2015.
 */


var Loger = Loger ? Loger : function () {
};


Loger.Params = function (params, url) {
    spaces(3);
    console.log('Param to pass to ' + url);
    if (params) {
        if (params.orderPaymentInformation && params.orderPaymentInformation.Transaction_SafePay_Password) {
            console.log({
                "Transaction_AccountNumber": params.Transaction_AccountNumber,
                "Transaction_Currency": params.Transaction_Currency,
                "Transaction_CustomerPhone": params.Transaction_CustomerPhone,
                "Transaction_MasterCredit_CVVNumber": params.Transaction_MasterCredit_CVVNumber,
                "Transaction_MasterCredit_CardNumber": params.Transaction_MasterCredit_CardNumber,
                "Transaction_MasterCredit_CustomerName": params.Transaction_MasterCredit_CustomerName,
                "Transaction_MasterCredit_ExpirationDate": params.Transaction_MasterCredit_ExpirationDate,
                "Transaction_PaymentMethod": params.Transaction_PaymentMethod,
                "Transaction_ReferenceNumber": params.Transaction_ReferenceNumber,
                "Transaction_SafePay_UserName": params.Transaction_SafePay_UserName,
                "Transaction_TransactionDate": params.Transaction_TransactionDate,
                "Transaction_Type": params.Transaction_Type
            });
        }
        else if (params.safePayPassword) {
            console.log({safePayUsername: params.safePayUsername, referenceId: params.referenceId});
        }
        else if (params.password) {
            console.log({
                "accountType": params.accountType, "address": params.address,
                "allowOffersPromotion": params.allowOffersPromotion, "cityName": params.cityName,
                "countryId": params.countryId, "email": params.email,
                "firstName": params.firstName, "lastName": params.lastName,
                "loginName": params.loginName, "phoneNumber": params.phoneNumber,
                "stateProvince": params.stateProvince, "zipcode": params.zipcode,
            });
        }
        else {
            console.log(params);
        }
    }
    spaces(1);
};
Loger.Extract = function (file, obj, paths) {
    spaces(3);
    lines(2);
    console.log('Extracted file: ' + file);
    spaces(1);
    console.log("Recieve: ");
    console.log(obj);
    spaces(1);
    for (var i = 0; i < paths.length; i++) {
        console.log(paths[i])
    }
    spaces(1);
    lines(2);
    spaces(3);
};

Loger.CallingWSDL = function (obj) {
    spaces(1);
    console.log("========================   Reading wsdl  =================================")
    console.log("url: " + obj.path);
    console.log("method: " + obj.method);
};

Loger.Calling = function (str) {
    spaces(1);
    console.log("===========================   Calling  ===================================")
    console.log("Path: " + str);
};

Loger.Received = function (str) {
    console.log("============================  Received  ==================================")
    console.log(str)
    spaces(1);
};

function spaces(num) {
    var v = ""
    for (var i = 0; i < num; i++) {
        v += " ";
        console.log("" + v)
    }
}

function lines(num) {
    var v = ""
    for (var i = 0; i < num; i++) {
        v += " ";
        console.log("==========================================================================" + v)
    }
}


var services_properties = []

//var catalogKey = "http://localhost:8080/catalog";
//var orderKey = "http://localhost:8080/order";
//var accountKey = "http://localhost:8080/account";
//var serviceKey = "http://localhost:8080/service";
//var wsdlPath = 'http://localhost:8080/accountservice';
var catalogKey = orderKey = accountKey = serviceKey = wsdlPath = "undefined";

(function readTextFile(file) {
    console.log("Extracting file: " + file);

    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function () {
        if (rawFile.readyState === 4) {
            if (rawFile.status === 200 || rawFile.status == 0) {
                var fileText = rawFile.responseText;
                var rawFile_responseText = fileText;
                fileText = fileText.split('');
                var _param = '';
                var _value = '';
                var attr = true;
                var arrayApi = [];
                var invalidChars = '#';
                fileText.forEach(function (a) {
                    switch (a.charCodeAt(0)) {
                        case 10:
                        case 13:
                            var validParam = true;
                            for (var i = 0; i < invalidChars.length; i++) {
                                if (_param.indexOf(invalidChars[i]) != -1) {
                                    validParam = false;
                                    break;
                                }
                            }
                            if (validParam && _param != '' && _value != '') {
                                arrayApi.push("{\"" + _param.split(".").join("_") + "\":\"" + _value + "\"}");
                                _param = '';
                                _value = '';
                            }
                            attr = true;
                            break;
                        case 61:
                            attr = false;
                            break;
                        default:
                            if (attr) {
                                _param += a;
                            } else {
                                _value += a;
                            }
                            break;
                    }
                });

                arrayApi.forEach(function (a) {
                    var jsonObj = JSON.parse(a);
                    services_properties[Object.keys(jsonObj)] = jsonObj[Object.keys(jsonObj)];
                });

                catalogKey = "http://" + services_properties['catalog_service_url_host'] + ":" +
                    services_properties['catalog_service_url_port'] + "/" + services_properties['catalog_service_url_suffix'];

                orderKey = "http://" + services_properties['order_service_url_host'] + ":" +
                    services_properties['order_service_url_port'] + "/" + services_properties['order_service_url_suffix'];

                accountKey = "http://" + services_properties['account_service_url_host'] + ":" +
                    services_properties['account_service_url_port'] + "/" + services_properties['account_service_url_suffix'];

                serviceKey = "http://" + services_properties['service_service_url_host'] + ":" +
                    services_properties['service_service_url_port'] + "/" + services_properties['service_service_url_suffix'];

                wsdlPath = "http://" +
                    services_properties['account_soapservice_url_host'] + ":" +
                    services_properties['account_soapservice_url_port'] + "/" +
                    services_properties['account_soapservice_url_suffix'];

                console.log("File extracted: " + file);
                Loger.Extract(file, rawFile_responseText, [
                    'Catalog path: ' + catalogKey,
                    'Order path: ' + orderKey,
                    'Account path: ' + accountKey,
                    'Service path: ' + serviceKey,
                    'WSDL (Web Services Description Language) path: ' + wsdlPath,
                ]);

            }
        }
    }
    rawFile.send(null)
})('services.properties');


var server = {

    fileReady: function () {
        var check = catalogKey + orderKey + accountKey + serviceKey + wsdlPath;
        return check.indexOf('undefined') == -1;
    },

    catalog: {

        getKey: function () {
            return catalogKey;
        },
        getPopularProducts: function () {
            var paramToReturn = "app/tempFiles/popularProducts.json";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getCategories: function () {
            var paramToReturn = catalogKey + "/categories";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getCategoryById: function (id) {
            var paramToReturn = catalogKey + "/categories/" + id + "/products";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getDeals: function () {
            var paramToReturn = catalogKey + "/deals";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getDealOfTheDay: function () {
            var paramToReturn = catalogKey + "/deals/search?dealOfTheDay=true";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getProducts: function () {
            var paramToReturn = catalogKey + "/products.json";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getProductById: function (id) {
            var paramToReturn = catalogKey + '/products/' + id;
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getProductsBySearch: function (word, quantity) {
            var paramToReturn = catalogKey + "/products/search?name=" + word;
            if (quantity > 0) {
                paramToReturn += "&quantityPerEachCategory=" + quantity;
            }
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getAllCategoriesAttributes: function () {
            var paramToReturn = catalogKey + "/categories/attributes";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        sendSupportEmail: function () {
            var paramToReturn = catalogKey + "/support/contact_us/email";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getEmailConfiguration: function () {

            var paramToReturn = catalogKey + "/DemoAppConfig/parameters/Email_address_in_login";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getAccountConfiguration: function () {

            var wsdlToReturn = {
                path: wsdlPath,
                method: 'GetAccountConfigurationRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

    },
    order: {

        updateUserCart: function (userId) {
            var paramToReturn = orderKey + "/carts/" + userId;
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        removeProductToUser: function (userId, productId, color) {
            var paramToReturn = orderKey + "/carts/" + userId +
                "/product/" + productId +
                "/color/" + color;
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        updateProductToUser: function (userId, productId, color, quantity, oldColor) {
            var path = server.order.addProductToUser(userId, productId, oldColor, quantity);
            var paramToReturn = color != oldColor ? path += "&new_color=" + color : path;
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        addProductToUser: function (userId, productId, color, quantity) {
            var paramToReturn = orderKey + "/carts/" + userId + "/product/" + productId +
                "/color/" + color + "?quantity=" + quantity;
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        loadCartProducts: function (userId) {
            var paramToReturn = orderKey + "/carts/" + userId;
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        clearCart: function (userId) {
            var paramToReturn = orderKey + "/carts/" + userId;
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        getShippingCost: function () {
            var paramToReturn = orderKey + "/shippingcost/";
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        safePay: function (userId) {
            var paramToReturn = orderKey + "/orders/users/" + userId;
            Loger.Calling(paramToReturn);
            return paramToReturn;
        },

        accountUpdate: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'AccountUpdateRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

    },
    account: {

        getAllCountries: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'GetCountriesRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        register: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'AccountCreateRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        login: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'AccountLoginRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        getAccountById: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'GetAccountByIdRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        getAccountById_new: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'GetAccountByIdNewRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        getAccountPaymentPreferences: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'GetAccountPaymentPreferencesRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        getAddressesByAccountId: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'GetAddressesByAccountIdRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        accountUpdate: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'AccountUpdateRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        updateMasterCreditMethod: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'UpdateMasterCreditMethodRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        addMasterCreditMethod: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'AddMasterCreditMethodRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        updateSafePayMethod: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'UpdateSafePayMethodRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        addSafePayMethod: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'AddSafePayMethodRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        changePassword: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'ChangePasswordRequest'
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        accountLogout: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'AccountLogoutRequest',
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        },

        paymentMethodUpdate: function () {
            var wsdlToReturn = {
                path: wsdlPath,
                method: 'PaymentMethodUpdateRequest',
            }
            Loger.CallingWSDL(wsdlToReturn);
            return wsdlToReturn;
        }
    },
}

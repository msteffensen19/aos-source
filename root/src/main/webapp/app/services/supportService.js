/**
 * Created by correnti on 18/02/2016.
 */


define(['./module'], function (services) {
    'use strict';
    services.service('supportService', ['$filter', '$q', '$http', function($filter, $q, $http){


        return {

            sendSupportEmail: function (supportModel) {

                var paramsToPass = {
                    "categoryId": supportModel.category.categoryId || 0,
                    "email": supportModel.email,
                    "productId": supportModel.product.productId || 0,
                    "text": supportModel.subject,
                }
                var defer = $q.defer()
                $http({
                    method: "post",
                    url: server.catalog.sendSupportEmail(),
                    data: paramsToPass,
                }).success(function (res) {
                    l("res $q")
                    l(res)
                    defer.resolve(res)
                }).error(function (_err) {
                    console.log("sendSupportEmail() rejected!  ====== " + _err)
                    console.log(_err)
                    defer.resolve({
                        "success": false,
                        "reason": $filter('translate')('Problem_sending_mail_please_try_later'),
                        "returnCode": 1
                    });
                });
                return defer.promise;

            }



    }

    }]);
});


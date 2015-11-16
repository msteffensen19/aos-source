/**
 * Created by kubany on 11/11/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('userController', ['$scope', '$uibModal', '$log',
        function ($scope, $uibModal, $log) {

            $scope.items = ['item1', 'item2', 'item3'];

            $scope.open = function (size) {
                var modalInstance;
                var modalScope = $scope.$new();
                modalScope.ok = function () {
                    modalInstance.close(modalScope.selected);
                };
                modalScope.cancel = function () {
                    modalInstance.dismiss('cancel');
                };

                modalInstance = $uibModal.open({
                        template: '<login-modal></login-modal>',
                        size: size,
                        scope: modalScope
                    }
                );

                modalInstance.result.then(function (selectedItem) {
                    $scope.selected = selectedItem;
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };
        }]);
});
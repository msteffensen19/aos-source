/**
 * Created by correnti on 27/07/2016.
 */



define(['./module'], function (controllers) {
    'use strict';
controllers.controller('myOrdersCtrl', ['resolveParams', '$scope', 'ModalService', 'orderService',

    function (resolveParams, $scope, ModalService, OrderService) {

        var vm = this;
        var modalId = 'delete-order-confirmation'
        vm.orders = resolveParams.orders;

        vm.removeProductFromOrders = function(order){
            vm.deleteOrderCandidate = order;
            vm.openConfirmationModal();
        };

        vm.openConfirmationModal = function(id){
            ModalService.Open(modalId);
        }

        $scope.confirmDeleteOrder = function(id){
            OrderService.removeOrder(vm.deleteOrderCandidate).then(function(orders){
                vm.deleteOrderCandidate = null;
                vm.orders = orders;
            });

        }

        $scope.cancelDeleteOrder = function(id){
            vm.deleteOrderCandidate = null;
        }

        // ModalService.SetBtnsCb(modalId, [$scope.confirmDeleteOrder, $scope.cancelDeleteOrder]);

        vm.getTotalPrice = function(arr){
            var total = 0;
            angular.forEach(arr, function(product){
                total += product.PricePerUnit * product.Quantity;
            });
            return total;
        }

    }]);
});

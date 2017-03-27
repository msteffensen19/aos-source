/**
 * Created by kubany on 3/15/2017.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('confirmationModal', ['$templateCache', 'ModalService', '$compile', function ($templateCache, ModalService, $compile) {
        return {
            restrict: 'E',
            scope:{
                btn1Cb:'&',
                btn2Cb:'&',
            },
            template: $templateCache.get('app/partials/confirmation-modal.html'),
            controllerAs: 'confCtrl',
            controller: ["$scope", function (s) {
                this.btnClicked = function ($event) {
                    var btnNum = $event.currentTarget.id.split("confBtn_")[1];
                    btnNum == "1" ? s.btn1Cb() : s.btn2Cb();
                };
            }],
            link: function (scope, element, attrs) {
                // ensure id attribute exists
                if (!attrs.id) {
                    console.error('modal must have an id');
                    return;
                }

                if(attrs.numofbuttons){
                    var numOfBtns = !isNaN(attrs.numofbuttons) ? parseInt(attrs.numofbuttons) : 0;
                    for(var i = 0; i < numOfBtns; i++){
                        var btnTxtBold = attrs["btn" + (i+1) +"Txt"].split(',')[0];
                        var btnTxtRegular = attrs["btn" + (i+1) +"Txt"].split(',')[1];
                        var button = $("<button type='button' class='conf-modal-btn' role='button' data-ng-click='confCtrl.btnClicked($event)' id='confBtn_" +  (i+1) +"' role=''>" +
                            "<label class='conf-btn-bold'>"+ btnTxtBold +"</label><label class='conf-btn-regular'>," + btnTxtRegular + "</label> </button>")
                        $compile(button)(scope);
                        $('.conf-modal-btn-container').append(button);
                    }
                }
                // move element to bottom of page (just before </body>) so it can be displayed above everything else
                //element.appendTo('body');

                // close modal on background click
                $(element).on('click', function (e) {
                    var target = $(e.target);
                    if (!target.closest('.modal-body').length && !target.attr("type")) {
                        scope.$evalAsync(Close);
                    }
                });

                // add self (this modal instance) to the modal service so it's accessible from controllers
                var modal = {
                    id: attrs.id,
                    open: Open,
                    close: Close
                };
                ModalService.Add(modal);

                // remove self from modal service when directive is destroyed
                scope.$on('$destroy', function() {
                    ModalService.Remove(attrs.id);
                    element.remove();
                });

                // open modal
                function Open() {
                    $(element).show();
                    //$('body').addClass('modal-open');
                }

                // close modal
                function Close() {
                    $(element).hide();
                    //$('body').removeClass('modal-open');
                }

                function setBtnsCb(cbs){
                    for(var i = 0; i < cbs.length; i++){

                    }
                }
            }
        };
    }]);
});
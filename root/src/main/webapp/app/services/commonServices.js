/**
 * Created by kubany on 3/15/2017.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('ModalService', [
        function () {

            var cart = null;
            var modals = []; // array of modals on the page
            var service = {};
            return ({
                Add : Add,
                Remove : Remove,
                Open : Open,
                Close : Close,
                SetBtnsCb : SetBtnsCb,
            });

            function Add(modal) {
                // add modal to array of active modals
                modals.push(modal);
            }

            function Remove(id) {
                // remove modal from array of active modals
                var modalToRemove = _.findWhere(modals, { id: id });
                modals = _.without(modals, modalToRemove);
            }

            function Open(id) {
                // open modal specified by id
                var modal = _.findWhere(modals, { id: id });
                modal.open();
            }

            function Close(id) {
                // close modal specified by id
                var modal = _.findWhere(modals, { id: id });
                modal.close();
            }

            function SetBtnsCb(id, cbs) {
                // close modal specified by id
                var modal = _.findWhere(modals, { id: id });
                modal.setBtnsCb(cbs);
            }


        }]);
});
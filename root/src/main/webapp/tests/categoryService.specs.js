define(['app', 'jquery', 'underscore'], function(App, $, _) {
    'use strict';
    var $rootScope, $scope, $controller, userSearchCtrl, categoryService;


    beforeEach(module("aos"));
    beforeEach(inject(function(_$rootScope_, _$controller_, $injector){
        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        $controller = _$controller_;
        categoryService = $injector.get('categoryService')
        userSearchCtrl = $controller('mainCtrl', {'$rootScope' : $rootScope, '$scope': $scope});
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    }));
    describe('just checking', function() {



        it('test promise with jasmine', async () => {
            try {
                var data = await categoryService.getAllData();
            } catch (err) {
                return;
            }

            throw new Error('Promise should not be resolved');
        });

    });

});
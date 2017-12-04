define(['./module'], function (directives) {
    'use strict';
    directives.directive('versionContent', ['$templateCache', 'versionService', function ($templateCache) {
        var contentUrl;
        return {
            link: function(scope) {
                scope.contentUrl = 'app/user/views/version-page-' + scope.release_version_only + '.html';
            },
            template: '<div ng-include="contentUrl"></div>'
        };
    }
    ]);
});
/**
 * Created by kubany on 10/13/2015.
 */
define([],function(){
    function config($translateProvider, $httpProvider, $stateProvider) {
        $httpProvider.defaults.cache = true;
        $translateProvider.useSanitizeValueStrategy('escapeParameters');
        $translateProvider.translations('en', {
            OUR_PRODUCTS : 'OUR PRODUCTS',
            HOT_PRODUCTS : 'Hot Products',
            DEAL_OF_THE_DAY : 'Deal of the Day',
            CONTACT_US : 'Contact Us',
            AOS : 'AOS',
            LOGIN: 'Login',
            LOGOUT: 'This is a paragraph.',
            SPACIAL_OFFER : 'SPECIAL OFFER',
            SPECIAL_OFFER_ITEMS : 'SPECIAL OFFER ITEMS',
            'SHOP_NOW' : 'SHOP NOW',
            POPULAR_ITEMS : 'POPULAR ITEMS',
            FOLLOW_US : 'FOLLOW US',
            'LEGALS_FOOTER' : '© Advantage Inc, 2016.',
            'BY' : 'BY',
            'ADD_TO_CART' : 'ADD TO CART',
            'HOME' : 'HOME',
            'BUY_NOW' : 'BUY NOW',
            'STARTING_AT' : 'Starting at',
            'SEE_OFFER' : 'SEE OFFER'
        });
        $stateProvider.state('view1',{
            url: '/',
            templateUrl: 'app/views/home-page.html',
            controller: 'categoriesCtrl',
            data: {
                requireLogin: false // this property will apply to all children of 'app'
            }
        })
            .state('welcome',{
                url: '/welcome',
                templateUrl: 'app/views/welcome.html',
                data: {
                    requireLogin: false,
                    showWelcome : true// this property will apply to all children of 'app'
                }
            })
            .state('category',{
                url: '/category/:id',
                templateUrl: 'app/views/category-page.html',
                controller: 'categoryCtrl',
                data: {
                    requireLogin: false // this property will apply to all children of 'app'
                },
                resolve : {
                    category: function (categoryService, $stateParams) {
                        return categoryService.getMockData($stateParams.id);
                    }
                }
            })
            .state('404',{
                url: '/404',
                data: {
                    underConstruction: true// this property will apply to all children of 'app'
                }
            });
        //$routeProvider.
        //    when('/AddNewOrder', {
        //        templateUrl: 'templates/add_order.html',
        //        controller: 'AddOrderController'
        //    }).
        //    when('/category/:id', {
        //        controller: 'categoryCtrl',
        //        templateUrl: './app/views/category-page.html',
        //    }).
        //    when('/', {
        //        controller: 'categoriesCtrl',
        //        templateUrl: 'app/views/home-page.html',
        //    }).
        //    when('/welcome', {
        //        templateUrl: 'app/views/welcome.html',
        //    }).
        //    otherwise({
        //        redirectTo: '/',
        //        controller: 'categoriesCtrl',
        //        templateUrl: './app/views/home-page.html',
        //    });
        $translateProvider.preferredLanguage('en');
    }
    config.$inject=['$translateProvider', '$httpProvider', '$stateProvider'];

    return config;
});
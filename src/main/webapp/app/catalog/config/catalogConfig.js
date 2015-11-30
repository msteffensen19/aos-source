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
            'FILTER_BY' : 'FILTER BY',
            'ADD_TO_CART' : 'ADD TO CART',
            'HOME' : 'HOME',
            'BUY_NOW' : 'BUY NOW',
            'STARTING_AT' : 'Starting at',
            'SEE_OFFER' : 'SEE OFFER',
            'ONLINE_SHOPPING' : 'Online shopping',
            'FACEBOOK_SIGN_IN' : 'SIGN IN WITH FACEBOOK',
            'OR' : 'OR',
            'Name' : 'Name',
            'Password' : 'Password',
            'Email' : 'Email',
            'SIGN_IN' : 'SIGN IN',
            'FORGOT_PASSWORD' : 'Forgot your password?',
            'CREATE_NEW_ACCOUNT' : 'CREATE NEW ACCOUNT',
            'REMEMBER_ME' : 'REMEMBER ME',


            'hi' : 'Hi',
            'SIGN_OUT' : 'Sign out',
            'PERSONAL_AREA' : 'Personal area',
            "PRODUCT_SPECIFICATIONS" : "PRODUCT SPECIFICATIONS",
            "View_details" : "View Details",
            'Shop_Now' : 'Shop Now',
            'EXPLORE_NOW' : 'EXPLORE NOW',
            'Quantity:' : 'Quantity:',
            'GO_UP' : 'GO UP',
            'CREATE_ACCOUNT' : 'CREATE ACCOUNT',
            'this_is_a_demo' : 'this is a demo',
            'Please_enter_a_fake_data' : 'Please enter a fake data',
            'NOTIC' : 'NOTIC',
            'Laptops' : 'Laptops',
            'ACCOUNT_DETAILS' : 'ACCOUNT DETAILS',
            'PERSONAL_DETAILS' : 'PERSONAL DETAILS',
            'ADDRESS' : 'ADDRESS',
            'User_Name' : 'User Name',
            'Password' : 'Password',
            'Confirm_Password' : 'Confirm Password',
            'First_Name' : 'First Name',
            'Last_Name' : 'Last Name',
            'Phone_Number' : 'Phone_Number',
            'Country' : 'Country',
            'City' : 'City',
            'Address' : 'Address',
            'Postal_Code' : 'Postal Code', 
            'State__Province__Region' : 'State / Province / Region',
            'offers_promotion' :  'Recieve exclisive offers and promotions from Advantage'

        });

        $stateProvider.state('view1',{
            url: '/',
            templateUrl: 'app/views/home-page.html',
            controller: 'categoriesCtrl',
            data: {
                requireLogin: false // this property will apply to all children of 'app'
            },
        })
            .state('welcome',{
                url: '/welcome',
                templateUrl: 'app/views/welcome.html',
                data: {
                    requireLogin: false,
                    showWelcome : true// this property will apply to all children of 'app'
                }
            })
            .state('register',{
                url: '/register',
                templateUrl: 'app/user/views/register-page.html',
                controller: 'registerCtrl',
                data: {
                    requireLogin: false // this property will apply to all children of 'app'
                },
                resolve : {
                    product: function (productService, $stateParams) {
                    //    return productService.getProductById($stateParams.id);
                    }
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
            .state('product',{
                url: '/product/:id',
                templateUrl: 'app/views/product-page.html',
                controller: 'productCtrl',
                data: {
                    requireLogin: false // this property will apply to all children of 'app'
                },
                resolve : {
                    product: function (productService, $stateParams) {
                        return productService.getProductById($stateParams.id);
                    }
                }
            })
            .state('404',{
                url: '/404',
                templateUrl: 'app/views/404.html',
                data: {
                    underConstruction: true // this property will apply to all children of 'app'
                }
            });

        $translateProvider.preferredLanguage('en');
    }
    config.$inject=['$translateProvider', '$httpProvider', '$stateProvider'];

    return config;
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
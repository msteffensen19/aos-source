/**
 * Created by kubany on 10/13/2015.
 */

define([],function(){

    function config($translateProvider, $stateProvider) {

        $translateProvider.useSanitizeValueStrategy('escapeParameters');
        $translateProvider.translations('en', english);

        $stateProvider.state('default',{
            url: '/',
            templateUrl: 'app/views/home-page.html',
            controller: 'categoriesCtrl',
            data: {
                requireLogin: false,  // this property will apply to all children of 'app'
                beforeLoader: false,
                navLinks: true,
                breadcrumbName: "Home Page",
            },
            resolve : {
            },
        })
        .state('welcome',{
            url: '/welcome',
            templateUrl: 'app/views/welcome.html',
            data: {
                requireLogin: false,
                showWelcome : true, // this property will apply to all children of 'app'
                breadcrumbName: "Welcome",
            }
        })
        .state('register',{
            url: '/register',
            templateUrl: 'app/user/views/register-page.html',
            controller: 'registerCtrl',
            data: {
                requireLogin: false,  // this property will apply to all children of 'app'
                breadcrumbName: "Register",
            },
            resolve : {
                
            }
        })
        .state('shoppingCart',{
            url: '/shoppingCart',
            templateUrl: 'app/views/shoppingCart.html',
            controller: 'shoppingCartCtrl',
            data: {
                requireLogin: false,  // this property will apply to all children of 'app'
                breadcrumbName: "ShoppingCart",
            },
            resolve : {
                category: function (productsCartService, $stateParams) {
                    return productsCartService.loadCartProducts();
                }
            }
        })
        .state('category',{
            url: '/category/:id',
            templateUrl: 'app/views/category-page.html',
            controller: 'categoryCtrl',
            data: {
                requireLogin: false,  // this property will apply to all children of 'app'
                breadcrumbName: "Category"
            },
            resolve : {
                category: function (categoryService, $stateParams) {
                    return categoryService.getCategoryById($stateParams.id);
                },
            }
        })
        .state('product',{
            url: '/product/:id?color&quantity&pageState',
            templateUrl: 'app/views/product-page.html',
            controller: 'productCtrl',
            data: {
                requireLogin: false,  // this property will apply to all children of 'app'
                breadcrumbName:  "Product"
            },
            resolve : {
                selectedColor: function($stateParams){
                    return $stateParams.color;
                },
                quantity: function($stateParams){
                    return $stateParams.quantity;
                },
                pageState: function($stateParams){
                    return $stateParams.pageState;
                },
                product: function (productService, $stateParams) {
                    return productService.getProductById($stateParams.id);
                }
            }
        })
        .state('404',{
            url: '/404',
            templateUrl: 'app/views/404.html',
            data: {
                underConstruction: true,  // this property will apply to all children of 'app'
                breadcrumbName: "Home Page",
            }
        });

        $translateProvider.preferredLanguage('en');
    }

    config.$inject=['$translateProvider', '$stateProvider'];
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
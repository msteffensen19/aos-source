/**
 * Created by kubany on 10/13/2015.
 */

define([],function(){

    function config($translateProvider, $stateProvider, $urlRouterProvider) {

        $translateProvider.useSanitizeValueStrategy('escapeParameters');

        $translateProvider.translations('en', english);

        $urlRouterProvider.otherwise("/#");

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
                resolveParams: function(categoryService, dealService, $q) {
                    var defer = $q.defer();
                    categoryService.getCategories().then(function (categories) {
                        dealService.getDealOfTheDay().then(function(deal) {
                            categoryService.getPopularProducts().then(function(popularProducts){
                                var paramsToReturn = {
                                    categories: categories,
                                    specialOffer: deal,
                                    popularProducts: popularProducts,
                                }
                                defer.resolve(paramsToReturn)
                            })
                        });
                    });
                    return defer.promise;
                }
            }
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
            url: '/category/:id?viewAll',
            templateUrl: 'app/views/category-page.html',
            controller: 'categoryCtrl',
            data: {
                requireLogin: false,  // this property will apply to all children of 'app'
                breadcrumbName: "Category"
            },
            resolve : {
                category: function (categoryService, productService, $stateParams, $q, $filter) {

                    var defer  = $q.defer();
                    categoryService.getCategoryById($stateParams.id).
                    then(function(category){

                        if($stateParams.viewAll) {
                            productService.getProductsBySearch($stateParams.viewAll, -1).then(function (result) {

                                var products = $filter("filterFullArrayforAutoComplate")([], result, $stateParams.id, -1)
                                var categories = {
                                    categoryId: 1,
                                    categoryName: "Search: '" + $stateParams.viewAll + "'",
                                    categoryImageId: category ? category.categoryImageId : "",
                                    promotedProduct: category ? category.promotedProduct : null,
                                    attributes: [],
                                    products: products,
                                }
                                defer.resolve(categories);
                            });
                        }
                        else{
                            defer.resolve(category);
                        }
                    });
                    return defer.promise;
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
                resolveParams: function(productService, categoryService, $stateParams, $q) {
                    var defer = $q.defer();
                    productService.getProductById($stateParams.id).then(function (product) {
                        categoryService.getCategoryById(product.categoryId).then(function (category) {
                            var paramsToReturn = {
                                selectedColor: $stateParams.color,
                                quantity: $stateParams.quantity,
                                pageState: $stateParams.pageState,
                                categoryName: category.categoryName,
                                product: product,
                            }
                            defer.resolve(paramsToReturn)
                        });
                    });
                    return defer.promise;
                }
            }
        }).
        state('404',{
            url: '/404',
            templateUrl: 'app/views/404.html',
            data: {
                underConstruction: true,  // this property will apply to all children of 'app'
                breadcrumbName: "Home Page",
            }
        });

        $translateProvider.preferredLanguage('en');
    }

    config.$inject = ['$translateProvider', '$stateProvider', '$urlRouterProvider'];
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
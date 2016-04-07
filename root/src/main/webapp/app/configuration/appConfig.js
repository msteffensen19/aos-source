/**
 * Created by kubany on 10/13/2015.
 */

define([], function () {

    function config($translateProvider, $stateProvider, $urlRouterProvider, $locationProvider) {

        $translateProvider.useSanitizeValueStrategy('escapeParameters');

        $translateProvider.translations('en', english);

        $urlRouterProvider.otherwise("/#");

        //$locationProvider.html5Mode(true);

        $translateProvider.preferredLanguage('en');

        $stateProvider.state('default', {
                url: '/',
                templateUrl: 'app/views/home-page.html',
                controller: 'categoriesCtrl',
                resolve: {
                    resolveParams: function (categoryService, dealService, $q) {
                        var defer = $q.defer();
                        categoryService.getCategories().then(function (categories) {
                            dealService.getDealOfTheDay().then(function (deal) {
                                categoryService.getPopularProducts().then(function (popularProducts) {
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
            .state('register', {
                url: '/register',
                templateUrl: 'app/user/views/register-page.html',
                controller: 'registerCtrl',
                resolve: {}
            }).
            state('support', {
                url: '/support',
                templateUrl: 'app/views/support-page.html',
                controller: 'supportCtrl',
                controllerAs: 'suppCtrl',
                resolve: {
                    paramsToResorve: function (categoryService, $q) {
                        var defer = $q.defer()
                        categoryService.getCategories().then(function (categories) {
                            defer.resolve({
                                categories: categories,
                            });
                        });
                        return defer.promise;
                    }
                }
            })
            .state('shoppingCart', {
                url: '/shoppingCart',
                templateUrl: 'app/views/shoppingCart.html',
                controller: 'shoppingCartCtrl',
                resolve: {
                    category: function (productsCartService, $stateParams) {
                        return productsCartService.loadCartProducts();
                    }
                }
            })
            .state('category', {
                url: '/category/:id?viewAll',
                templateUrl: 'app/views/category-page.html',
                controller: 'categoryCtrl',
                resolve: {
                    category: function (categoryService, productService, $stateParams, $q) {
                        var defer = $q.defer();
                        categoryService.getCategoryById($stateParams.id).
                        then(function (category) {
                            productService.getAllCategoriesAttributes().then(function (attributes) {
                                defer.resolve({
                                    attributes: attributes,
                                    searchResult: [category],
                                    categoryName: category.categoryName
                                });
                            });
                        });
                        return defer.promise;
                    },
                }
            })
            .state('search', {
                url: '/search/:id?viewAll',
                templateUrl: 'app/views/search-page.html',
                controller: 'searchCtrl',
                resolve: {
                    category: function (categoryService, productService, $stateParams, $q, $filter) {
                        var defer =  $q.defer();
                        productService.getProductsBySearch($stateParams.viewAll, -1).then(function (result) {
                            var categoriesFilter = $filter("getCategoriesInArray")([], result, $stateParams.id)
                            var paramsToReturn = {
                                searchResult: result,
                                viewAll: true,
                                categoryId: 1,
                                searchKey: $stateParams.viewAll ,
                                categoriesFilter: categoriesFilter,
                                attributes: [],
                            }
                            defer.resolve(paramsToReturn);
                        });
                        return defer.promise;
                    },
                }
            })
            .state('product', {
                url: '/product/:id?color&quantity&pageState',
                templateUrl: 'app/views/product-page.html',
                controller: 'productCtrl',
                resolve: {
                    resolveParams: function (productService, categoryService, $stateParams, $q) {
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
        state('404', {
            url: '/404',
            templateUrl: 'app/views/404.html',
            //data: { data used to check fields in runners states ($stateChangeStart, $stateChangeError, ....)
            //    underConstruction: true,  // this property will apply to all children of 'app'
            //    breadcrumbName: "Home Page",
            //}
        });
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
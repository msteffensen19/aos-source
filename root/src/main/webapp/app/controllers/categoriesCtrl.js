/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoriesCtrl', ['$scope', 'categoryService',
        'dealService', '$location', 'resolveParams', 'supportService', '$timeout',
        function (s, categoryService, dealService, $location, resolveParams, supportService, $timeout ) {

            s.categories = resolveParams.categories;
            s.specialOffer= resolveParams.specialOffer;
            s.popularProducts = resolveParams.popularProducts;

            s.images = [
                { imageName : 'Banner1.jpg', imageId : 0, message : "ALL YOU WANT FROM A TABLET", categoryId : 2 },
                { imageName : 'Banner2.jpg', imageId : 1, message : "EXPLORE OUR LATEST <br />INNOVATIVE PRODUCTS", categoryId : 3 },
                { imageName : 'Banner3.jpg', imageId : 2, message : "START EXPLORING HP NOTEBOOKS", categoryId : 0 }
            ];

            /* suport section */

            s.supportSuccess = false;
            s.registerAnswer = { class: "", message: "" }
            s.supportModel = {
                "category": {},
                "email": "",
                "product": {},
                "subject": ""
            }

            s.products;

            s.categoryChange = function() {
                categoryService.getCategoryById(s.supportModel.category.categoryId).
                then(function (category) {
                    l(category.products)
                    s.products = category.products;
                    //s.supportModel.product = category.products[0];
                });
            }

            s.productChange = function() {

            }

            s.sendSupportEmail = function(){

                supportService.sendSupportEmail(s.supportModel).then(function(res){

                    s.registerAnswer.class = res.success ? "valid" : "invalid";
                    s.registerAnswer.message = res.reason;
                    s.supportSuccess = res.success;
                    if(!res.success){
                        $timeout(function(){
                            s.registerAnswer = { class: "", message: "" }
                        }, 2000)
                    }
                    if(res.success){
                        s.supportModel = {
                            "category": {},
                            "email": "",
                            "product": {},
                            "subject": ""
                        }
                        $timeout(function(){
                            s.supportSuccess = false;
                            s.registerAnswer = { class: "", message: "" }
                        }, 10000)
                    }

                }, function(err){

                });


            }


            /* end suport section */




            Slider.AddSliderListener();

            Helper.forAllPage();


            $('#scrollToTop').click(function () {
                $('body, html').animate({ scrollTop: 0 }, 1000);
            });

            $(document).ready(function(){
                $(window).on({
                    scroll: function () {
                        if ($(window).scrollTop() > 800) {
                            $('#scrollToTop').stop().fadeIn(300);
                            return;
                        }
                        $('#scrollToTop').stop().fadeOut(300);
                    },
                    resize: resize,
                });
                function resize(){
                    turnTheOrderOfImagesInCategoriesGrid()
                }
                setTimeout(resize, 0)
                function turnTheOrderOfImagesInCategoriesGrid(){
                    var elemToMove = $("#SpeakersImg");
                    var indexToFind = "TabletsImg";
                    if($(window).width() <= 480){
                        elemToMove = $("#TabletsImg");
                        indexToFind = "SpeakersImg";
                    }
                    if(elemToMove.prev().attr("id") && elemToMove.prev().attr("id").indexOf(indexToFind) != -1){
                        elemToMove.parent().prepend(elemToMove);
                    }
                }
            });

        }]);
});
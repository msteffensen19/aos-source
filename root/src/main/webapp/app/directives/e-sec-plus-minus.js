/**
 * Created by correnti on 27/12/2015.
 */



define(['./module'], function (directives) {
    'use strict';
    directives
        .directive('eSecPlusMinus', ['$templateCache', function($templateCache){
            return{
                restrict: 'E',
                template: $templateCache.get('app/partials/e-sec-plus-minus.html'),
                scope: {
                    numAttr: '=',
                },
                controller: ['$scope', function(s){

                    var maxValue = 999;
                    var minValue = 1;
                    var num = 1
                    var readyToCheck = true;
                    s.num = s.numAttr;

                    s.incrementValue = function(){
                        s.$apply(function(){
                            var newVal = s.numAttr + 1
                            num = s.num = s.numAttr = newVal;
                        });
                        return s.numAttr >= maxValue;
                    }

                    s.decrementValue = function(){
                        s.$apply(function(){
                            var newVal = s.numAttr - 1
                            num = s.num = s.numAttr = newVal;
                        });
                        return s.numAttr <= minValue;
                    }

                    this.saveNumber  = function($event){

                        if(!checkNumber($event))
                        { return false; }

                        if(readyToCheck) {
                            readyToCheck = false;
                            num = s.num;
                        }
                        return true;
                    }

                    this.updateNumber = function(){
                        readyToCheck = true;
                        if(checkNumber()){
                            s.numAttr = s.num;
                        }
                    }

                    function checkNumber(event){
                        var isNumber = (s.num - 0) == s.num && (''+s.num).trim().length > 0;
                        console.log(event)
                        if(!isNumber){
                            s.num = parseInt(num);
                            return false;
                        }
                        if(s.numAttr > maxValue || s.numAttr < minValue ){
                            s.num = parseInt(num);
                            return false;
                        }
                        return true;
                    }

                }],
            }
        }])
        .directive('numbersOnly', function(){
            return{
                restrict: 'A',
                require: '^eSecPlusMinus',
                link: function(s, e, a, ctrl){

                    console.log(ctrl)

                    e.on('keydown', function (event) {
                        if(event.keyCode >= 48 && event.keyCode <= 57 ||
                            event.keyCode >= 96 && event.keyCode <= 105){
                            if(!ctrl.saveNumber()) {
                                event.preventDefault();
                                return false;
                            }
                            return true;
                        }
                        event.preventDefault();
                        return false;
                    });

                    e.on('keyup', function () {
                        s.$apply(function(){
                            ctrl.updateNumber();
                        })
                    });
                }
            }
        })
        .directive('incrementValueAttr', function(){
            return{
                restrict: 'A',
                require: '^eSecPlusMinus',
                link: function(s, e, a, ctrl){

                    e.on('click', function () {
                        if(a.incrementValueAttr == "+"){

                            if(e.hasClass("disableBtn")){ return; }

                            if(s.incrementValue()){
                                e.addClass("disableBtn")
                                return;
                            }

                            $(e).siblings('.minus').removeClass("disableBtn")
                            e.removeClass("disableBtn")

                        }
                        if(a.incrementValueAttr == "-"){

                            if(e.hasClass("disableBtn")){ return; }

                            if(s.decrementValue()){
                                e.addClass("disableBtn")
                                return;
                            }

                            $(e).siblings('.plus').removeClass("disableBtn")
                            e.removeClass("disableBtn")
                        }
                    });
                }
            }
        })

    ;
});


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

                    this.getMaxValue = function(){
                        return maxValue;
                    }

                    this.getMinValue = function(){
                        return minValue;
                    }

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
                        s.checkDisables()
                    }

                    function checkNumber(){
                        var isNumber = (s.num - 0) == s.num && (''+s.num).trim().length > 0;
                        if(!isNumber){
                            s.num = parseInt(num);
                            return false;
                        }
                        if(s.num > maxValue || s.num < minValue ){
                            s.num = parseInt(num);
                            return false;
                        }
                        return true;
                    }
                }],
                link: function(s, e, a, ctrl) {
                    var minValue = ctrl.getMinValue();
                    var maxValue = ctrl.getMaxValue();
                    if (s.numAttr <= minValue) {
                        s.numAttr = minValue;
                        $(e).find('.minus').addClass('disableBtn')
                    }
                    if (s.numAttr >= maxValue) {
                        s.numAttr = maxValue;
                        $(e).find('.plus').addClass('disableBtn')
                    }
                    s.checkDisables = function(){
                        if (s.num <= minValue) {
                            s.num = minValue;
                            $(e).find('.minus').addClass('disableBtn')
                            $(e).find('.plus').removeClass('disableBtn')
                        }
                        else {
                            $(e).find('.minus').removeClass('disableBtn')
                        }

                        if (s.num >= maxValue) {
                            s.num = maxValue;
                            $(e).find('.plus').addClass('disableBtn')
                            $(e).find('.minus').removeClass('disableBtn')
                        }
                        else{
                            $(e).find('.plus').removeClass('disableBtn')
                        }
                    }
                }
            }
        }])
        .directive('numbersOnly', function(){
            return{
                restrict: 'A',
                require: '^eSecPlusMinus',
                link: function(s, e, a, ctrl){

                    console.log(ctrl)

                    e.on('keydown', function (event) {

                        console.log(event.keyCode)
                        switch (event.keyCode){
                            case 37: // left
                            case 39: // rigth
                            case 38: // down
                            case 40: // up
                            case 123: // f12
                            case 13: // enter
                                return true;
                        }

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


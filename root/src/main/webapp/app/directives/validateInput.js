/**
 * Created by correnti on 27/12/2015.
 */



define(['./module'], function (directives) {
    'use strict';
    directives
        .directive('validateInput', ['$templateCache', function($templateCache){
            return{
                restrict: 'E',
                replace: true,
                transclude: true,
                scope: {
                    modelAttr: '=',
                    idAttr: '@'
                },
                template: $templateCache.get('app/partials/validateInput.html'),
                controller: ['$scope', function(s){

                    s.warnings = [];
                    s.id;

                    var labelStartPossition;
                    s.inputFocus = function(id){
                        var input = $('#validateInput' + id);
                        labelStartPossition = input.prev().css('top');
                        console.log(labelStartPossition);
                        input.prev().animate({'top': '-18px'}, 300, 'linear');
                        input.siblings(".validate-info").fadeIn(500);
                    }

                    s.inputBlur = function(id){
                        var input = $('#validateInput' + id);
                        if(input.val() == ''){
                            input.prev().animate({'top': labelStartPossition}, 300, 'linear');
                        }

                        checkValidInput(s.warnings, $('#validateInput' + id));
                        if(s.textToShow.valid){
                        }
                        input.siblings(".validate-info").fadeOut(500);
                    }

                    function checkValidInput(warnings, input){
                        s.textToShow = {
                            text: s.placeHolder,
                            valid: true
                        };

                        angular.forEach(s.warnings, function(warn){
                            if(s.textToShow.valid) {
                                switch (warn.key) {
                                    case 'secRequired':
                                        if (input.val() == '') {
                                            setTextToShow(warn.warning)
                                            return false;
                                        }
                                        break
                                    case 'secMinLength':
                                        if (input.val().length < warn.min) {
                                            setTextToShow(warn.warning)
                                            return false;
                                        }
                                        break
                                    case 'secMaxLength':
                                        if (input.val().length > warn.max) {
                                            setTextToShow(warn.warning)
                                            return false;
                                        }
                                        break
                                    case 'secPattern':
                                        if(!(new RegExp(warn.regex).test(input.val())))
                                        {
                                            setTextToShow(warn.warning)
                                            return false;
                                        }
                                        break
                                    case 'secCompareTo':
                                        var comparedInput = $('#validateInput' + warn.compareId);
                                        console.log(comparedInput.val() == input.val())
                                        console.log(comparedInput.val() + " " + input.val())
                                        if(comparedInput.val() != input.val() && comparedInput.val() != "" && input.val() != "" )
                                        {
                                            setTextToShow(warn.warning)
                                            return false;
                                        }
                                        break
                                    default:
                                        throw warn.key + " key is not defined in checkValidInput(warnings) method (directive <validate-input></validate-input>)";
                                }
                            }
                        })
                    }

                    function setTextToShow(warning){
                        s.textToShow = {
                            text: warning,
                            valid: false
                        };
                    }

                    s.validateLabelClicked = function(id){ $('#validateInput' + id).focus(); }

                    this.addWarningInfo = function(warningInfo){
                        s.warnings.push(warningInfo);
                    }

                    this.setPlaceHolder = function(placeHolder){
                        s.placeHolder = placeHolder;
                        s.textToShow = {
                            text: placeHolder,
                            valid: true
                        };
                    }

                    this.setId = function(id){
                        s.id = id;
                    }

                    this.setInputType = function(inputType){
                        s.inputType = inputType;
                    }

                }],
                link: function(s, e, a, c){
                    e.addClass('validate-directive');
                    if(!a.idAttr){
                        throw "id attribute  in directive <validateInput></validateInput> is must! "
                    }
                    c.setInputType(a.inputTypeAttr || 'text')
                    c.setId(a.idAttr)
                }

            }
        }])
        .directive('secRequired', function(){
            return{
                restrict: 'A',
                priority: 0,
                require: 'validateInput',
                link: function(s, e, a, ctrl){
                    var warning = a.secRequired || 'This field is required'
                    ctrl.addWarningInfo({
                        key : 'secRequired',
                        warning : warning,
                    })
                }
            }
        })
        .directive('secMinLength', function(){
            return{
                restrict: 'A',
                require: 'validateInput',
                priority: 40,
                link: function(s, e, a, ctrl){

                    var min = a.secMinLength - 0;
                    if(!a.idAttr){
                        throw "if secMinLength is used in directive <validateInput></validateInput>, value is must! "
                    }
                    if(!((min - 0) == min && (''+min).trim().length > 0) || min < 0){
                        throw "Invalid value in attribute secMinLength, the value in directive "+
                        "<validateInput></validateInput> must be a positive number! "
                    }
                    var warning = 'Use '+ min +' characters or longer'
                    ctrl.addWarningInfo({
                        key : 'secMinLength',
                        warning : warning,
                        min : min,
                    })
                }
            }
        })
        .directive('secMaxLength', function(){
            return{
                restrict: 'A',
                priority: 50,
                require: 'validateInput',
                link: function(s, e, a, ctrl){
                    var max = a.secMaxLength - 0;
                    if(!a.idAttr){
                        throw "if secMaxLength is used in directive <validateInput></validateInput>, value is must! "
                    }
                    if(!((max - 0) == max && (''+max).trim().length > 0) || max < 0){
                        throw "Invalid value in attribute secMaxLength, the value in directive "+
                        "<validateInput></validateInput> must be a positive number! "
                    }
                    var warning = 'Use up to '+ max +' characters'
                    ctrl.addWarningInfo({
                        key : 'secMaxLength',
                        warning : warning,
                        max : max,
                    })
                }
            }
        })
        .directive('secPattern', function(){
            return{
                restrict: 'A',
                priority: 100,
                require: 'validateInput',
                link: function(s, e, a, ctrl){

                    if(!a.patternErrorAttr){
                        throw "if secPattern is used in directive <validateInput></validateInput>, pattern-error-attr attribute is must! "
                    }
                    ctrl.addWarningInfo({
                        key : 'secPattern',
                        warning : a.patternErrorAttr,
                        regex : a.secPattern,
                    })
                }
            }
        })
        .directive('secEmail', function(){
            return{
                restrict: 'A',
                require: 'validateInput',
                link: function(s, e, a, ctrl){
                    ctrl.addWarningInfo({
                        key : 'secPattern',
                        warning : "Your email address isn’t formatted correctly",
                        regex : a.secEmail || "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$"
                        //"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$",
                    })
                }
            }
        })
        .directive('secCompareTo', function(){
            return{
                restrict: 'A',
                require: 'validateInput',
                link: function(s, e, a, ctrl){
                    var max = a.secMaxLength - 0;
                    if(!a.idAttr){
                        throw "if secCompareTo is used in directive <validateInput></validateInput>, id-attr is must! "
                    }
                    if(!a.secCompareTo){
                        throw "if sec-compare-to is used in directive <validateInput></validateInput>, must to be a comparable id! "
                    }
                    if(!a.compareToName){
                        throw "if sec-compare-to is used in directive <validateInput></validateInput>, must to be compare-to-name attribute! "
                    }
                    console.log("compareTo")
                    console.log("compareTo")
                    console.log("compareTo")
                    console.log("compareTo")
                    console.log(a)
                    var warning = 'Same as ' + a.compareToName;
                    ctrl.addWarningInfo({
                        key : 'secCompareTo',
                        warning : warning,
                        compareId : a.secCompareTo,
                    })
                }
            }
        })






        .directive('secPlaceholder', function(){
            return{
                restrict: 'A',
                require: 'validateInput',
                link: function(s, e, a, ctrl){
                    ctrl.setPlaceHolder(a.secPlaceholder)
                }
            }
        })



    ;
});


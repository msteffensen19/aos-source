/**
 * Created by correnti on 27/12/2015.
 */



define(['./module'], function (directives) {
    'use strict';
    directives

        .directive('secSubmitAttr', function(){
            return{
                restrict: 'A',
                scope:{
                    submit : '&secSubmitAttr'
                },
                require: '^secValidate',
                link: function(s, e, a, ctrl){
                    e.bind('click', function(){
                        if(!ctrl.getInvalidItems() && !e.hasClass("sec-validate-invalid"))
                        {
                            s.submit()
                        }
                    });
                }
            }
        })
        .directive('secValidate', ['$templateCache', function($templateCache){
            return{
                restrict: 'E',
                require: 'secValidate',
                scope: {},
                controller: ['$scope', '$rootScope', function(s, $rootScope){

                    this.addId = function(id){
                        this.id = id;
                    }

                    s.invalidItems = [];
                    this.getInvalidItems = function(){
                        l("s.invalidItems_length")
                        l(s.invalidItems)
                        $rootScope.$emit('invaliditemslengthUpdate', { invalidItems : s.invalidItems.length });
                        return s.invalidItems.length  > 0;
                    }

                    this.getInvalidItemsCount = function(){
                        return s.invalidItems.length;
                    }

                    this.addInvalidField = function(invalidInput){
                        if(searchIndexOfInput(invalidInput) == -1)
                        {
                            s.invalidItems.push(invalidInput);
                        }
                    }

                    this.shiftInvalidField = function(invalidInput){

                        var find = searchIndexOfInput(invalidInput);
                        if(find != -1)
                        {
                            s.invalidItems.splice(find, 1);
                        }
                    }

                    function searchIndexOfInput(invalidInput){

                        for(var i = 0 ; i < s.invalidItems.length; i++)
                        {
                            if(s.invalidItems[i] == invalidInput)
                            {
                                return i;
                            }
                        }
                        return -1;
                    }
                }],
                link: function(s, e, a, ctrl){
                    e.addClass('sec-validate');
                    if(a.idAttr)
                    {
                        e.attr('id', a.idAttr);
                        ctrl.addId(a.idAttr);
                    }
                }
            }
        }])

        .directive('aSecValidateInvalid', function($rootScope){
            return{
                restrict: 'A',
                link: function(s, e, a, ctrl){
                    $rootScope.$on('invaliditemslengthUpdate', function(event, args) {
                        if (args.invalidItems != undefined) {

                            l(args.invalidItems)
                            if(args.invalidItems > 0){
                                e.addClass("sec-validate-invalid")
                            }
                            else{
                                e.removeClass("sec-validate-invalid")
                            }
                        }
                    });
                }
            }
        })
        .directive('secInput', ['$templateCache', '$timeout', '$rootScope', function($templateCache, $timeout, $rootScope){
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                require: ['secInput', '^secValidate'],
                scope: {
                    modelAttr: '=',
                    idAttr: '@'
                },
                template: $templateCache.get('app/partials/secInput.html'),
                controller: ['$scope', function (s) {

                    var labelStartPossition;
                    var ctrlFather;
                    s.warnings = [];
                    s.noRedStar = false;
                    s.id;
                    s.textToShow = {
                        text: "",
                        valid: true
                    };

                    s.inputKeyup = function (id) {
                        var input = $('#secInput_' + id);
                        var invalid = checkValidInput(s.warnings, input, 'keyup');
                        if (invalid) {
                            ctrlFather.addInvalidField(id);
                        }
                        else {
                            ctrlFather.shiftInvalidField(id);
                        }
                        $rootScope.$emit('invaliditemslengthUpdate', { invalidItems : ctrlFather.getInvalidItemsCount()});
                    }

                    s.inputFocus = function (id) {
                        var input = $('#secInput_' + id);
                        s.textToShow = {
                            text: s.placeHolder,
                            valid: true,
                        };
                        checkValidInput(s.warnings, input, 'keyup');
                        labelStartPossition = labelStartPossition || input.prev().css('top');
                        input.prev().animate({'top': '-18px'}, 300, 'linear');
                        input.siblings(".validate-info").show(200);
                    }

                    s.inputBlur = function (id, justTestThisField_do_not_active_her_Field) {

                        var input = $('#secInput_' + id);
                        if (input.val().length == 0) {
                            input.prev().animate({'top': labelStartPossition}, 300, 'linear');
                        }
                        var invalid = checkValidInput(s.warnings, input, 'blur', justTestThisField_do_not_active_her_Field);
                        if (invalid) {
                            ctrlFather.addInvalidField(id);
                        }
                        else {
                            ctrlFather.shiftInvalidField(id);
                        }
                        $rootScope.$emit('invaliditemslengthUpdate', { invalidItems : ctrlFather.getInvalidItemsCount()});
                        input.siblings(".validate-info").hide(200);
                    }

                    s.validateLabelClicked = function (id) {
                        $('#secInput_' + id).focus();
                    }

                    this.setCtrlFather = function (_ctrlFather) {
                        ctrlFather = _ctrlFather;
                    }

                    this.addWarningInfo = function (warningInfo) {
                        s.warnings.push(warningInfo);
                        ctrlFather.addInvalidField(s.id);
                    }

                    this.setPlaceHolder = function (placeHolder) {
                        s.placeHolder = placeHolder;
                        s.textToShow = {
                            text: placeHolder,
                            valid: true,
                            key: ''
                        };
                    }

                    this.setId = function (id) {
                        s.id = id;
                    }

                    this.enableNoRedStar = function () {
                        s.noRedStar = true;
                    }

                    this.setInputType = function (inputType) {
                        s.inputType = inputType;
                    }

                    function setTextToShow(warn, justTestThisField_do_not_active_her_Field) {
                        warn.show = true;
                        if(justTestThisField_do_not_active_her_Field){
                            return;
                        }
                        s.textToShow = {
                            text: warn.warning,
                            valid: false
                        };
                    }

                    function checkValidInput(warnings, input, event, justTestThisField_do_not_active_her_Field) {

                        var invalidToReturn = false;
                        for (var i in s.warnings) {
                            var warn = s.warnings[i];

                            if (s.textToShow.valid) {
                                switch (warn.key) {
                                    case 'secRequired':
                                        var _invalid = (input.val() == '');
                                        invalidToReturn = invalidToReturn ? invalidToReturn : _invalid;
                                        if(event == 'keyup'){
                                            warn.show = _invalid;
                                            if(!invalidToReturn) {ctrlFather.getInvalidItems()}
                                        }
                                        else if (invalidToReturn) {
                                            setTextToShow(warn, justTestThisField_do_not_active_her_Field)
                                        }
                                        break;
                                    case 'secOnlyNumbers':
                                        var _invalid = !((input.val() - 0) == input.val() &&
                                        input.val().indexOf('-') == -1 && input.val().indexOf('+') == -1);
                                        invalidToReturn = invalidToReturn ? invalidToReturn : _invalid;
                                        if(event == 'keyup'){
                                            warn.show = _invalid;
                                            if(!invalidToReturn) {ctrlFather.getInvalidItems()}
                                        }
                                        else if (invalidToReturn) {
                                            setTextToShow(warn, justTestThisField_do_not_active_her_Field)
                                        }
                                        break;
                                    case 'secMinLength':
                                        var _invalid = input.val().length < warn.min;
                                        invalidToReturn = invalidToReturn ? invalidToReturn : _invalid;
                                        if(event == 'keyup'){
                                            warn.show = _invalid;
                                            if(!invalidToReturn) {ctrlFather.getInvalidItems()}
                                        }
                                        else if (invalidToReturn) {
                                            setTextToShow(warn, justTestThisField_do_not_active_her_Field)
                                        }
                                        break;
                                    case 'secMaxLength':
                                        var _invalid = input.val().length > warn.max;
                                        invalidToReturn = invalidToReturn ? invalidToReturn : _invalid;
                                        if(event == 'keyup'){
                                            warn.show = _invalid;
                                            if(!invalidToReturn) {ctrlFather.getInvalidItems()}
                                        }
                                        else if (invalidToReturn) {
                                            setTextToShow(warn, justTestThisField_do_not_active_her_Field)
                                        }
                                        break;
                                    case 'secPattern':
                                        var _invalid = !(new RegExp(warn.regex).test(input.val()));
                                        invalidToReturn = invalidToReturn ? invalidToReturn : _invalid;
                                        if(event == 'keyup'){
                                            warn.show = _invalid;
                                            if(!invalidToReturn) {ctrlFather.getInvalidItems()}
                                        }
                                        else if (invalidToReturn) {
                                            setTextToShow(warn, justTestThisField_do_not_active_her_Field)
                                        }
                                        break;
                                    case 'secCompareTo':
                                        var comparedInput = $('#secInput_' + warn.compareId);
                                        var _invalid = (comparedInput.val() != input.val() && comparedInput.val() != "" && input.val() != "");
                                        invalidToReturn = invalidToReturn ? invalidToReturn : _invalid;
                                        if(event == 'keyup'){
                                            warn.show = _invalid;
                                            if(!invalidToReturn) {ctrlFather.getInvalidItems()}
                                        }
                                        else if (invalidToReturn) {
                                            setTextToShow(warn, justTestThisField_do_not_active_her_Field)
                                        }
                                        break;
                                    default:
                                        throw warn.key + " key is not defined in checkValidInput(warnings) method (directive <validate-input></validate-input>)";
                                }
                            }
                        }
                        return invalidToReturn;
                    }
                }],
                link: {
                    pre: function (s, e, a, ctrls) {

                        var me = ctrls[0];
                        e.addClass('validate-directive');

                        if (!a.idAttr) {
                            throw "id attribute  in directive <secInput></secInput> is must! "
                        }

                        if (a.noRedStar) {
                            me.enableNoRedStar();
                        }
                        if (a.selectTag) {

                            e.bind('click', function () {
                                var checkbox = $($(this).find("input[type=checkbox]"))
                                if (checkbox.length > 0) {
                                    s.$apply(function () {
                                        if (checkbox[0].checked) {
                                            ctrls[1].shiftInvalidField(a.idAttr);
                                            $(this).find(".validateInvalid").fadeOut()
                                        }
                                        else {
                                            ctrls[1].addInvalidField(a.idAttr);
                                            $(this).find(".validateInvalid").fadeIn();
                                        }
                                        ctrls[1].getInvalidItems();
                                        $rootScope.$emit('invaliditemslengthUpdate', { invalidItems : ctrls[1].getInvalidItemsCount()});
                                    });
                                }
                            })

                            e.bind('change', function () {
                                var select = $($(this).find("select"))
                                var _this = this;
                                if (select.length > 0) {
                                    s.$apply(function () {
                                        ctrls[1].shiftInvalidField(a.idAttr);
                                        $($(_this).find(".validate-label")).html('')
                                    });
                                }
                            })
                        }
                        me.setCtrlFather(ctrls[1]);
                        me.setInputType(a.inputTypeAttr || 'text')
                        me.setId(a.idAttr)
                    },
                    post: function(s){
                        $timeout(function(){
                            s.inputBlur(s.id, true);
                        }, 500)
                    }
                }
            }
        }])
        .directive('secRequired', function(){
            return{
                restrict: 'A',
                priority: 0,
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    var warning = a.secRequired || 'This field is required'
                    ctrl.addWarningInfo({
                        key : 'secRequired',
                        warning : warning,
                        info: '',
                        show: false
                    })
                }
            }
        })
        .directive('secOnlyNumbers', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    var warning = a.secOnlyNumbers || 'Only Digits allowed'
                    ctrl.addWarningInfo({
                        key : 'secOnlyNumbers',
                        warning : warning,
                        info: warning,
                        show: false
                    })
                }
            }
        })
        .directive('secMinLength', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                priority: 40,
                link: function(s, e, a, ctrl){
                    var min = a.secMinLength - 0;
                    if(!a.idAttr){
                        throw "if secMinLength is used in directive <secInput></secInput>, value is must! "
                    }
                    if(!((min - 0) == min && (''+min).trim().length > 0) || min < 0){
                        throw "Invalid value in attribute secMinLength, the value in directive "+
                        "<secInput></secInput> must be a positive number! "
                    }
                    var warning = 'Use '+ min +' characters or longer'
                    ctrl.addWarningInfo({
                        key : 'secMinLength',
                        warning : warning,
                        info: warning,
                        min : min,
                        show: false
                    })
                }
            }
        })
        .directive('secMaxLength', function(){
            return{
                restrict: 'A',
                priority: 50,
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    var max = a.secMaxLength - 0;
                    if(!a.idAttr){
                        throw "if secMaxLength is used in directive <secInput></secInput>, value is must! "
                    }
                    if(!((max - 0) == max && (''+max).trim().length > 0) || max < 0){
                        throw "Invalid value in attribute secMaxLength, the value in directive "+
                        "<secInput></secInput> must be a positive number! "
                    }
                    var warning = 'Use up to '+ max +' characters'
                    ctrl.addWarningInfo({
                        key : 'secMaxLength',
                        warning : warning,
                        info: warning,
                        show: false,
                        max : max,
                    })
                }
            }
        })
        .directive('secPattern', function(){
            return{
                restrict: 'A',
                priority: 100,
                require: 'secInput',
                link: function(s, e, a, ctrl){

                    if(!a.patternErrorAttr){
                        throw "if secPattern is used in directive <secInput></secInput>, pattern-error-attr attribute is must! "
                    }
                    ctrl.addWarningInfo({
                        key : 'secPattern',
                        warning : a.patternErrorAttr,
                        info: a.patternErrorAttr,
                        show: false,
                        regex : a.secPattern,
                    })
                }
            }
        })
        .directive('secEmail', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    ctrl.addWarningInfo({
                        key : 'secPattern',
                        warning : "Your email address isn’t formatted correctly",
                        info: "Your email address isn’t formatted correctly",
                        show: false,
                        regex : a.secEmail || "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$"
                    })
                    //"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$",
                }
            }
        })
        .directive('secCompareTo', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    var max = a.secMaxLength - 0;
                    if(!a.idAttr){
                        throw "if secCompareTo is used in directive <secInput></secInput>, id-attr is must! "
                    }
                    if(!a.secCompareTo){
                        throw "if sec-compare-to is used in directive <secInput></secInput>, must to be a comparable id! "
                    }
                    if(!a.compareToName){
                        throw "if sec-compare-to is used in directive <secInput></secInput>, must to be compare-to-name attribute! "
                    }
                    var warning = 'Same as ' + a.compareToName;
                    ctrl.addWarningInfo({
                        key : 'secCompareTo',
                        warning : warning,
                        info : warning,
                        show: true,
                        compareId : a.secCompareTo,
                    })
                }
            }
        })
        .directive('secPlaceholder', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    ctrl.setPlaceHolder(a.secPlaceholder)
                }
            }
        })
    ;
});


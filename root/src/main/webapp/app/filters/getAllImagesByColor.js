/**
 * Created by correnti on 11/04/2016.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.filter('getAllImagesByColor', function(){
        return function(images, colorSelected) {
            return images.filter(function(img){
                var indexSubString = img.indexOf("##");
                if(img.toLowerCase().indexOf(("abcdef")) != -1 || indexSubString == -1){
                    return true;
                }
                var color = img.substring(0, indexSubString);
                if ( colorSelected.code == color ) {
                    return true;
                }
            });
        };
    });
});


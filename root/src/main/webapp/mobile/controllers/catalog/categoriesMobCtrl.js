/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'],
    function categoriesMobCtrl($scope, resolveParams){

    $scope.categories = resolveParams.categories;

    Helper.forAllPage();

});
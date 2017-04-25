'use strict';

angular.module('myApp.addLocation', ['ngRoute', 'ui.bootstrap', 'ngCookies'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/add_location', {
        templateUrl: 'addLocation/addLocation.html',
        controller: 'addLocationController'
    });
}])

.controller('addLocationController', ["$scope", "$location", "$cookies", function($scope, $location, $cookies) {



}]);
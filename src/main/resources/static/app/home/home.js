'use strict';

angular.module('myApp.home', ['ngRoute', 'ui.bootstrap', 'ngCookies'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {
        templateUrl: 'home/home.html',
        controller: 'homeController'
    });
}])

.controller('homeController', ["$scope", "$location", "$cookies", "$http", 'HomeFactory', function($scope, $location, $cookies,$http, HomeFactory) {

    /**
     * get curent created Events
     */
    HomeFactory.getEvents().then(function(response) {
        $scope.existingEvents = response.data;
    });

    /**
     * go to event details page
     */
    $scope.takeEventDetails = function(eventId) {
        $location.path('/event_details').search({ eventId: eventId });
    }

}]);
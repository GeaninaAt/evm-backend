'use strict';

angular.module('myApp.eventDetails', ['ngRoute', 'ui.bootstrap', 'ngCookies'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/event_details', {
        templateUrl: 'events.details/events.details.html',
        controller: 'eventDetailsController'
    });
}])

.controller('eventDetailsController', ["$scope", "$location", "$cookies", 'eventDetailsFactory', function($scope, $location, $cookies, eventDetailsFactory) {
    var eventId = $location.search().eventId;

    eventDetailsFactory.getEventById().then(function(response) {
        $scope.curentEventDetails = response.data;
    })


}]);
'use strict';

angular.module('myApp.addEvent', ['ngRoute', 'ui.bootstrap', 'ngCookies'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/add_event', {
        templateUrl: 'addEvent/addEvent.html',
        controller: 'addEventController'
    });
}])

.controller('addEventController', ["$scope", "$location", "$cookies", 'addEventFactory', function($scope, $location, $cookies, addEventFactory) {

    addEventFactory.getLocations().then(function(response) {
        $scope.locations = []
        for (var i in response) {
            $scope.locations.push({
                id: response[i].id,
                locationName: response[i].locationName
            })
        }
        //console.log($scope.locations)
    });

    addEventFactory.getOrganizers().then(function(response) {
        $scope.organizers = [];
        for (var i in response) {
            $scope.organizers.push({
                id: response[i].id,
                organizerName: response[i].organizerName
            })
        }
        console.log($scope.organizers)
    });
    //console.log($scope.data);
    $scope.addEvent = function(eventName, description, startDate, endDate, location, organizer, category, isFree) {
        var eventObject = {
            "eventName": eventName,
            "description": description,
            "organizerId": findId($scope.organizers, organizer),
            "locationId": findId($scope.locations, location),
            "startDate": startDate,
            "endDate": endDate,
            "category": category,
            "free": (isFree == 'true')
        }
        addEventFactory.addEvent(eventObject).then(function(response) {
            if (response.status == 201) {
                $scope.userCreated = true;
            }
        })

    }

    function findId(obj, valueToFind) {
        var returnedIdForValue;
        for (var i in obj) {
            if (obj[i].locationName == valueToFind || obj[i].organizerName) {
                returnedIdForValue = obj[i].id;
            }
        }
        return returnedIdForValue;
    }

    /**
     * Different date formats
     * for the datepicker
     */
    $scope.formats = ['yyyy-MM-dd', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];

    /**
     * Initialize as false first time 
     * the datepicker open modeal
     */
    $scope.StartDateStatus = {
        opened: false
    };

    /**
     * Change datepicker status 
     * and open it
     */
    $scope.StartDate = function() {
        $scope.StartDateStatus.opened = true;
    };

    /**
     * Initialize datepicker
     * selected date with current
     * date
     */
    $scope.today = function() {
        $scope.dtStart = new Date();
        $scope.dtEnd = new Date();
    };
    $scope.today();

    /**
     * Options for datepicker
     */
    $scope.dateOptionsStartDate = {
        dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        startingDay: 1
    };

    /**
     * Disabled days on datepicker
     */
    function disabled(data) {
        var date = data.date,
            mode = data.mode;
        // return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }


    $scope.EndDate = function() {
        $scope.EndDateStatus.opened = true;
    };



    $scope.EndDateStatus = {
        opened: false
    };

    /**
     * Options for datepicker
     */
    $scope.dateOptionsEndDate = {
        dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        startingDay: 1
    };





}]);
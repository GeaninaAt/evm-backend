'use strict';

angular.module('myApp.addEvent')

.factory("addEventFactory", ["$rootScope", "$http", "$cookies", "$location", function($rootScope, $http, $cookies, $location) {

    var getLocations = function() {
        var url = 'http://localhost:8080/rest/locations';
        //we have to change this
        var config = {
            headers: {
                authorization: "Basic " + btoa('user' + ":" + 'pass')
            }
        }
        return $http.get(url, config).then(function(response) {
            //console.log(response);
            return response.data;

        })
    }

    var getOrganizers = function() {
        var url = 'http://localhost:8080/rest/organizers';
        //we have to change this
        var config = {
            headers: {
                authorization: "Basic " + btoa('user' + ":" + 'pass')
            }
        }
        return $http.get(url, config).then(function(response) {
            //console.log(response);
            return response.data;

        })
    }

    var addEvent = function(eventData) {
        var url = 'http://localhost:8080/rest/events';
        //we have to change this
        var config = {
            headers: {
                authorization: "Basic " + btoa('user' + ":" + 'pass')
            }
        }
        return $http.post(url, eventData, config).then(
            function(response) {
                return response
            },
            function(err) {
                console.log(err);
            })
    }

    return {
        getLocations: getLocations,
        getOrganizers: getOrganizers,
        addEvent: addEvent

    }
}])
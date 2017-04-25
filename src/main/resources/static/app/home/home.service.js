'use strict';

angular.module('myApp.login')

.factory("HomeFactory", ["$rootScope", "$http", "$cookies", "$location", function($rootScope, $http, $cookies, $location) {
    var credentials = {
        "username": "user",
        "password": "pass"
    }
    var config = {
        headers: {
            authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
        }
    }
    var getEvents = function() {
        var url = "http://localhost:8080/rest/events";
        return $http.get(url, config).then(
            function(response) {
                return response;
            },
            function(err) {
                console.log('we got and error ' + err);
            }
        )

    }

    return {
        getEvents: getEvents
    }
}])
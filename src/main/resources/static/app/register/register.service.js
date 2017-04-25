'use strict';

angular.module('myApp.register')

.factory("registerFactory", ["$http", function($http) {
    var config = {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }

    var registerUser = function(bodyData, config) {
        console.log(bodyData);
        var url = "http://localhost:8080/register"

        return $http.post(url, bodyData, config).then(function(response) {
            console.log(response);
            return response.data;
        });
    }

    return {
        registerUser: registerUser
    }


}])
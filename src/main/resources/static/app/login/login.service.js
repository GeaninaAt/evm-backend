'use strict';

angular.module('myApp.login')

.factory("LoginFactory", ["$rootScope", "$http", "$cookies", "$location", function($rootScope, $http, $cookies, $location) {

    var authenticateUser = function(credentials) {

        return $http.post('http://localhost:8080/authenticate?username=' + credentials.username + '&password=' + credentials.password).then(function(response) {
            //console.log(response);
            if (response.data.token) {
                $cookies.put('userLogged', true);
                $cookies.put('userToken', response.data.token);
                $cookies.put('userName', credentials.username)
                $rootScope.loginValidation = false;
                // $location.path('/home');
            } else {
                $cookies.put('userLogged', false);
            }
            $http.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.token;
            return response.data.token;
            // callback && callback($rootScope.authenticated);
        }, function(response) {
            console.log(response)
            $rootScope.loginValidation = true;
            //   $rootScope.authenticated = false;
            //callback && callback(false);
        });

    };


    return {
        authenticateUser: authenticateUser
    }
}])
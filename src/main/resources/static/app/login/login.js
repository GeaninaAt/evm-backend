'use strict';

angular.module('myApp.login', ['ngRoute', 'ui.bootstrap', 'ngCookies'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: 'login/login.html',
        controller: 'loginController'
    });
}])

.controller('loginController', ["$scope", "$location", "$cookies", "LoginFactory", "$http", function($scope, $location, $cookies, LoginFactory, $http) {
    $scope.userName = $cookies.get("userName");

    /**
     * Log the user in application
     */
    $scope.LoginUser = function(userName, password) {
        var credentials = {
            username: userName,
            password: password
        }
        LoginFactory.authenticateUser(credentials).then(function(response) {
            console.log('token set');
            console.log(response);
            var config = {
                headers: {
                    'Authorization': 'Bearer' + ' ' + response,
                    //  "Content-Type": "application/json"
                }
            }
            console.log('token get');
            console.log(config.headers)
                // $httpProvider.defaults.headers.get = {};
            $http.defaults.headers.common.Authorization = 'Bearer ' + response;
            $http.get('http://localhost:8080/users').then(function(response) {
                console.log(1)
                console.log(response);
            }, function(err) {
                console.log(err);
            })

        });
    }
    var favoriteCookie = $cookies.get('userLogged');
    console.log(favoriteCookie);
    /**
     * go to register page
     */
    $scope.goToRegister = function() {
        $location.path('/register')
    }

    $scope.logOut = function() {
        $location.path('/login');
        $cookies.remove("username");
        $cookies.remove('userLogged');
    }


}]);
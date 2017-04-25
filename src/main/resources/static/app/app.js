'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
        'ngRoute',
        'myApp.login',
        'myApp.register',
        'myApp.home',
        'myApp.eventDetails',
        'myApp.addEvent',
        'myApp.addLocation',
        'ui.bootstrap',
        'ngCookies'



    ])
    .config(['$locationProvider', '$routeProvider', '$httpProvider', function($locationProvider, $routeProvider, $httpProvider) {
        //$locationProvider.hashPrefix('!');
        $httpProvider.defaults.headers.common['Access-Control-Allow-Headers'] = '*';
        $routeProvider.otherwise({ redirectTo: '/login' });


    }])
    .run(['$rootScope', '$location', '$cookies', function($rootScope, $location, $cookies) {
        $rootScope.$on('$routeChangeStart', function(event) {
            var userLogged = $cookies.get('userLogged');
            /* if ($cookies.get('userLogged') != "true") {
                 $location.path('/login');
             }*/
        });
    }]);
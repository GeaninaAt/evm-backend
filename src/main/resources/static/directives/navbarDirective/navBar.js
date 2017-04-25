'use strict';

angular.module("myApp")

.directive("navBarDirective", function() {
    return {
        templateUrl: "directives/navbarDirective/navBar.html",
        //  controller: "loginController"
    };
});
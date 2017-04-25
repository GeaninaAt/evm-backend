'use strict';

angular.module("myApp")

.directive("paginationDirective", function() {
    return {
        templateUrl: "directives/pagination/pagination.html",
        //controller: "loginController"
    };
});
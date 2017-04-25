'use strict';

angular.module("myApp")

.directive("footerDirective", function() {
    return {
        templateUrl: "directives/footer/footer.html",
        //controller: "loginController"
    };
});